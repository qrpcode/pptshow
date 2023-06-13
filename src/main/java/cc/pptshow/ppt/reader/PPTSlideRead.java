package cc.pptshow.ppt.reader;

import cc.pptshow.ppt.constant.Constant;
import cc.pptshow.ppt.domain.*;
import cc.pptshow.ppt.domain.background.*;
import cc.pptshow.ppt.domain.border.Border;
import cc.pptshow.ppt.domain.border.ColorBorder;
import cc.pptshow.ppt.domain.shape.SelfShape;
import cc.pptshow.ppt.domain.shape.Shape;
import cc.pptshow.ppt.element.PPTElement;
import cc.pptshow.ppt.element.impl.*;
import cc.pptshow.ppt.exception.PPTReadException;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import javafx.util.Pair;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static cc.pptshow.ppt.constant.Constant.*;

/**
 * 针对已经解压后的Slide XML读取
 */
public class PPTSlideRead {

    private static final String C_SLD = "cSld";
    private static final String NV_GRP_SP_PR = "nvGrpSpPr";
    private static final String GRP_SP_PR = "grpSpPr";
    private static final String SP = "sp";
    private static final String PIC = "pic";
    private static final String CXN_SP = "cxnSp";
    private static final String GRP_SP = "grpSp";
    private static final String SP_PR = "spPr";
    private static final String XFRM = "xfrm";
    private static final String FLIP_H = "flipH";
    private static final String CUST_GEOM = "custGeom";
    private static final String PRST_GEOM = "prstGeom";
    private static final String T = "t";
    private static final String A = "a";
    private static final String P = "p";
    private static final String R = "r";
    private static final String X = "x";
    private static final String Y = "y";
    private static final String CX = "cx";
    private static final String CY = "cy";
    private static final String W = "w";
    private static final String SP_TREE = "spTree";
    private static final String NO_FILL = "noFill";
    private static final String TX_BODY = "txBody";
    private static final String R_PR = "rPr";
    private static final String SZ = "sz";
    private static final String P_PR = "pPr";
    private static final String ALGN = "algn";
    private static final String LN_SPC = "lnSpc";
    private static final String SPC_PCT = "spcPct";
    private static final String VAL = "val";
    private static final String ROT = "rot";
    private static final String SOLID_FILL = "solidFill";
    private static final String GRAD_FILL = "gradFill";
    private static final String BLIP_FILL = "blipFill";
    private static final String GRP_FILL = "grpFill";
    private static final String SRGB_CLR = "srgbClr";
    private static final String ALPHA = "alpha";
    private static final String SCHEMA_CLR = "schemeClr";
    private static final String LN = "ln";
    private static final String GS_LST = "gsLst";
    private static final String POS = "pos";
    private static final String LIN = "lin";
    private static final String ANG = "ang";
    private static final String OFF = "off";
    private static final String EXT = "ext";
    private static final String CH_OFF = "chOff";
    private static final String CH_EXT = "chExt";

    private static final double DEFAULT_LINE_WIDTH = 0.5;
    private static final String DEFAULT_LINE_COLOR = "333333";

    private final Document document;

    public PPTSlideRead(String xmlPath) {
        this(new File(xmlPath));
    }

    public PPTSlideRead(File xmlFile) {
        try {
            //1.创建SAXBuilder对象
            SAXBuilder saxBuilder = new SAXBuilder();
            //2.创建输入流
            InputStream is = new FileInputStream(xmlFile);
            //3.将输入流加载到build中
            document = saxBuilder.build(is);
        } catch (Throwable t) {
            throw new PPTReadException(t);
        }
    }

    public List<PPTElement> queryElement() {
        List<Element> elements = findElements();
        if (CollectionUtils.isEmpty(elements)) {
            return Lists.newArrayList();
        }
        List<Element> trees = querySpTreeElements(elements.get(0));
        if (CollectionUtils.isEmpty(trees)) {
            return Lists.newArrayList();
        }
        List<Element> pptRealElements = filterRealElement(trees.get(0).getChildren());
        return buildPPTElementsByElements(pptRealElements, 0, 0, 0, 0);
    }

    /**
     * scale参数解释：
     * 在组合图形中，会采用某种比例进行表示，比如原始长度 1000 比例是 500 那么就会写成2
     * 这就导致直接采用原始比例会出现错误，所以必须使用比例尺进行纠正
     */
    private List<PPTElement> buildPPTElementsByElements(List<Element> pptRealElements,
                                                        long scaleOffX, long scaleOffY,
                                                        long scaleExtX, long scaleExtY) {
        return pptRealElements.stream()
                .map(pptRealElement -> {
                    if (SP.equals(pptRealElement.getName())) {
                        //图形元素，一般是图形或者是文字
                        return Lists.newArrayList(toPPTShapeOrText(pptRealElement,
                                scaleOffX, scaleOffY, scaleExtX, scaleExtY));
                    } else if (PIC.equals(pptRealElement.getName())) {
                        //图片元素
                        return Lists.newArrayList(toPPTImg(pptRealElement,
                                scaleOffX, scaleOffY, scaleExtX, scaleExtY));
                    } else if (CXN_SP.equals(pptRealElement.getName())) {
                        //直线元素
                        return Lists.newArrayList(toPPTLine(pptRealElement,
                                scaleOffX, scaleOffY, scaleExtX, scaleExtY));
                    } else if (GRP_SP.equals(pptRealElement.getName())) {
                        long queryScaleOffX = queryScaleOffX(pptRealElement) + scaleOffX;
                        long queryScaleOffY = queryScaleOffY(pptRealElement) + scaleOffY;
                        long queryScaleExtX = queryScaleExtX(pptRealElement) + scaleExtX;
                        long queryScaleExtY = queryScaleExtY(pptRealElement) + scaleExtY;
                        return buildPPTElementsByElements(pptRealElement.getChildren(),
                                queryScaleOffX, queryScaleOffY, queryScaleExtX, queryScaleExtY);
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private PPTElement toPPTLine(Element pptRealElement, long scaleOffX, long scaleOffY, long scaleExtX, long scaleExtY) {
        PPTLine pptLine = new PPTLine();
        PPTLineCss pptLineCss = new PPTLineCss();
        elementToLocationCss(pptRealElement, pptLineCss, scaleOffX, scaleOffY, scaleExtX, scaleExtY);
        pptLineCss.setLineWidth(findLineWidth(pptRealElement));
        pptLineCss.setColor(findColor(pptRealElement));

        Element spPr = findChildByName(pptRealElement, SP_PR);
        Element xfrm = findChildByName(spPr, XFRM);
        if (Objects.nonNull(xfrm)) {
            Attribute flip = xfrm.getAttribute(FLIP_H);
            if (Objects.nonNull(flip)) {
                PPTLineCss.LineType lineType = Arrays.stream(PPTLineCss.LineType.values())
                        .filter(l -> l.getCode().equals(flip.getValue()))
                        .findFirst()
                        .orElse(null);
                if (Objects.nonNull(lineType)) {
                    pptLineCss.setType(lineType);
                }
            }
        }
        pptLine.setCss(pptLineCss);
        return pptLine;
    }

    private String findColor(Element pptRealElement) {
        Element ln = findLn(pptRealElement);
        if (Objects.isNull(ln)) {
            return DEFAULT_LINE_COLOR;
        }
        Element solid = findChildByName(ln, SOLID_FILL);
        return Optional.ofNullable(solid)
                .map(PPTSlideRead::findColorAndAlphaInElement)
                .map(Pair::getKey)
                .orElse(DEFAULT_LINE_COLOR);
    }

    private double findLineWidth(Element pptRealElement) {
        Element ln = findLn(pptRealElement);
        if (ln == null) return DEFAULT_LINE_WIDTH;
        return Optional.ofNullable(ln.getAttribute(W))
                .map(Attribute::getValue)
                .map(Double::parseDouble)
                .map(w -> w / MULTIPLE_POUND)
                .orElse(DEFAULT_LINE_WIDTH);
    }

    private Element findLn(Element pptRealElement) {
        Element spPr = findChildByName(pptRealElement, SP_PR);
        if (Objects.isNull(spPr)) {
            return null;
        }
        Element ln = findChildByName(spPr, LN);
        if (Objects.isNull(ln)) {
            return null;
        }
        return ln;
    }

    private PPTElement toPPTImg(Element pptRealElement, long scaleOffX, long scaleOffY, long scaleExtX, long scaleExtY) {
        Shape shapeXml = findShapeXml(pptRealElement);
        if (Objects.isNull(shapeXml)) {
            PPTImg pptImg = new PPTImg();
            PPTImgCss pptImgCss = new PPTImgCss();
            elementToLocationCss(pptRealElement, pptImgCss, scaleOffX, scaleOffY, scaleExtX, scaleExtY);
            pptImgCss.setAngle(findAngle(pptRealElement));
            pptImgCss.setBorder(findBorder(pptRealElement));
            pptImg.setCss(pptImgCss);
            return pptImg;
        } else {
            PPTShape pptShape = new PPTShape();
            PPTShapeCss pptShapeCss = PPTShapeCss.build();
            pptShape.setCss(pptShapeCss);
            pptShapeCss.setShape(shapeXml);
            elementToLocationCss(pptRealElement, pptShapeCss, scaleOffX, scaleOffY, scaleExtX, scaleExtY);
            pptShapeCss.setBorder(findBorder(pptRealElement));
            ImgBackground imgBackground = new ImgBackground();
            imgBackground.setImg("x");
            pptShapeCss.setBackground(imgBackground);
            return pptShape;
        }
    }

    private void elementToLocationCss(Element elementChildHaveSpPrElement,
                                             LocationCss locationCss,
                                             long scaleOffX, long scaleOffY,
                                             long scaleExtX, long scaleExtY) {
        Element spPr = findChildByName(elementChildHaveSpPrElement, SP_PR);
        assert Objects.nonNull(spPr);
        Element xfrm = findChildByName(spPr, XFRM);
        if (Objects.isNull(xfrm)) {
            locationCss.setLeft(0);
            locationCss.setTop(0);
            locationCss.setWidth(0);
            locationCss.setHeight(0);
        } else {
            locationCss.setLeft(findLength("x", xfrm, 0, scaleOffX));
            locationCss.setTop(findLength("y", xfrm, 0, scaleOffY));
            locationCss.setWidth(findLength("cx", xfrm, 1, scaleExtX));
            locationCss.setHeight(findLength("cy", xfrm, 1, scaleExtY));
        }
    }

    private long queryScaleExtX(Element pptRealElement) {
        return queryScale(pptRealElement, EXT, CH_EXT, CX);
    }

    private long queryScaleExtY(Element pptRealElement) {
        return queryScale(pptRealElement, EXT, CH_EXT, CY);
    }

    private long queryScale(Element pptRealElement, String normalSize, String innerSize, String sizeKey) {
        Element grpSpPr = findChildByName(pptRealElement, GRP_SP_PR);
        Element xfrm = Optional.ofNullable(grpSpPr).map(g -> findChildByName(g, XFRM)).orElse(null);
        if (Objects.isNull(xfrm)) {
            throw new RuntimeException("计算比例尺时发现找不到比例！因为grpSpPr为空");
        }
        Element off = findChildByName(xfrm, normalSize);
        Element chOff = findChildByName(xfrm, innerSize);
        if (Objects.isNull(off) || Objects.isNull(chOff)) {
            throw new RuntimeException("计算比例尺时发现找不到比例！因为off或chOff为空");
        }
        return Long.parseLong(off.getAttribute(sizeKey).getValue()) -
                Long.parseLong(chOff.getAttribute(sizeKey).getValue());
    }

    private long queryScaleOffX(Element pptRealElement) {
        return queryScale(pptRealElement, OFF, CH_OFF, X);
    }

    private long queryScaleOffY(Element pptRealElement) {
        return queryScale(pptRealElement, OFF, CH_OFF, Y);
    }


    private List<Element> querySpTreeElements(Element element) {
        return element.getChildren().stream()
                .filter(e -> e.getName().equals(PPTSlideRead.SP_TREE))
                .collect(Collectors.toList());
    }

    private List<Element> findElements() {
        List<Element> children = document.getRootElement().getChildren();
        return children.stream().filter(c -> C_SLD.equals(c.getName())).collect(Collectors.toList());
    }

    private List<Element> filterRealElement(List<Element> pptElements) {
        return pptElements.stream()
                .filter(p -> !NV_GRP_SP_PR.equals(p.getName()) && !GRP_SP_PR.equals(p.getName()))
                .collect(Collectors.toList());
    }

    private List<Element> filterNotElementClass(Element pptRealElement) {
        return pptRealElement.getContent().stream()
                .filter(c -> Element.class.equals(c.getClass()))
                .map(inner -> (Element) inner)
                .collect(Collectors.toList());
    }

    private Element findElementByName(List<Element> elements, String name) {
        return elements.stream()
                .filter(i -> name.equals(i.getName()))
                .findFirst()
                .orElse(null);
    }

    private PPTElement toPPTShapeOrText(Element pptRealElement,
                                        long scaleOffX, long scaleOffY,
                                        long scaleExtX, long scaleExtY) {
        if (Strings.isNullOrEmpty(findAllString(pptRealElement))) {
            return toPPTShape(pptRealElement, scaleOffX, scaleOffY, scaleExtX, scaleExtY);
        }
        return toPPTText(pptRealElement, scaleOffX, scaleOffY, scaleExtX, scaleExtY);
    }

    private PPTText toPPTText(Element pptRealElement,
                              long scaleOffX, long scaleOffY,
                              long scaleExtX, long scaleExtY) {
        Element xfrm = findXfrm(pptRealElement);
        PPTText pptText = new PPTText();
        PPTTextCss pptTextCss = new PPTTextCss();
        pptTextCss.setLeft(findLength("x", xfrm, 0, scaleOffX));
        pptTextCss.setTop(findLength("y", xfrm, 0, scaleOffY));
        pptTextCss.setWidth(findLength("cx", xfrm, 1, scaleExtX));
        pptTextCss.setHeight(findLength("cy", xfrm, 1, scaleExtY));
        pptText.setCss(pptTextCss);
        Element aP = findAp(pptRealElement);

        PPTInnerText pptInnerText = new PPTInnerText();
        pptInnerText.setText(findAllString(pptRealElement));
        PPTInnerTextCss innerTextCss = findFontCss(aP);
        pptInnerText.setCss(innerTextCss);

        PPTInnerLine pptInnerLine = new PPTInnerLine();
        pptInnerLine.add(pptInnerText);
        pptInnerLine.setCss(findLineCss(pptRealElement, aP));

        pptText.add(pptInnerLine);
        return pptText;
    }

    private PPTInnerLineCss findLineCss(Element pptRealElement, Element aP) {
        if (Objects.isNull(aP)) {
            return new PPTInnerLineCss();
        }
        Element pPr = findChildByName(aP, P_PR);
        Attribute attribute = Optional.ofNullable(pPr)
                .map(p -> p.getAttribute(ALGN))
                .orElse(null);
        PPTInnerLineCss pptInnerLineCss = new PPTInnerLineCss();
        if (Objects.nonNull(attribute)) {
            pptInnerLineCss.setAlign(attribute.getValue());
        }
        if (Objects.nonNull(pPr)) {
            Element lnSpc = findChildByName(pPr, LN_SPC);
            Element spcPct = Optional.ofNullable(lnSpc)
                    .map(a -> findChildByName(a, SPC_PCT))
                    .orElse(null);
            if (Objects.nonNull(spcPct)) {
                pptInnerLineCss.setLineHeight(Integer.parseInt(spcPct.getAttribute(VAL).getValue()) * 1.00 / MULTIPLE_INT_5);
            }
        }
        return pptInnerLineCss;
    }

    private PPTInnerTextCss findFontCss(Element aP) {
        Element aR = Optional.ofNullable(aP)
                .map(a -> findChildByName(a, R))
                .orElse(null);
        Element rPr = Optional.ofNullable(aR)
                .map(a -> findChildByName(a, R_PR))
                .orElse(null);
        if (Objects.isNull(rPr)) {
            return new PPTInnerTextCss();
        }
        PPTInnerTextCss pptInnerTextCss = new PPTInnerTextCss();
        pptInnerTextCss.setFontSize(
                Integer.parseInt(
                        Optional.ofNullable(rPr.getAttribute(SZ))
                                .map(Attribute::getValue)
                                .orElse("1600")) / MULTIPLE_INT_2);
        return pptInnerTextCss;
    }

    private Element findAp(Element pptRealElement) {
        Element body = Optional.ofNullable(pptRealElement)
                .map(p -> findChildByName(p, TX_BODY))
                .orElse(null);
        return Optional.ofNullable(body).map(a -> findChildByName(a, P)).orElse(null);
    }

    private static boolean checkIsNoFillAndNoBorderElement(Element pptRealElement) {
        Background background = findBackground(pptRealElement);
        Element spPr = findChildByName(pptRealElement, SP_PR);
        if (Objects.isNull(spPr)) {
            return true;
        }
        Element ln = findChildByName(spPr, LN);
        Element borderFill = Optional.ofNullable(ln).map(s -> findChildByName(s, NO_FILL)).orElse(null);

        return background instanceof NoBackground && Objects.isNull(borderFill);
    }

    private static Element findChildByName(Element element, String name) {
        if (Objects.isNull(element)) {
            return null;
        }
        List<Element> elements = element.getChildren().stream()
                .filter(e -> e.getName().equals(name))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(elements)) {
            return null;
        }
        return elements.get(0);
    }

    private PPTShape toPPTShape(Element pptRealElement,
                                long scaleOffX, long scaleOffY,
                                long scaleExtX, long scaleExtY) {
        Element xfrm = findXfrm(pptRealElement);
        PPTShape pptShape = new PPTShape();
        PPTShapeCss pptShapeCss = new PPTShapeCss();
        if (Objects.isNull(xfrm)) {
            pptShapeCss.setLeft(0).setTop(0).setWidth(0).setHeight(0);
            pptShape.setCss(pptShapeCss);
            return pptShape;
        }
        pptShapeCss.setLeft(findLength("x", xfrm, 0, scaleOffX));
        pptShapeCss.setTop(findLength("y", xfrm, 0, scaleOffY));
        pptShapeCss.setWidth(findLength("cx", xfrm, 1, scaleExtX));
        pptShapeCss.setHeight(findLength("cy", xfrm, 1, scaleExtY));
        pptShapeCss.setName(findAllString(pptRealElement));
        pptShapeCss.setShape(findShapeXml(pptRealElement));
        pptShapeCss.setAngle(findAngle(pptRealElement));
        pptShapeCss.setBackground(findBackground(pptRealElement));
        pptShapeCss.setBorder(findBorder(pptRealElement));
        pptShapeCss.setFlipX(findIsFlipX(xfrm));
        pptShapeCss.setFlipY(findIsFlipY(xfrm));
        pptShape.setCss(pptShapeCss);
        return pptShape;
    }

    private boolean findIsFlipY(Element xfrm) {
        Attribute flipH = xfrm.getAttribute("flipV");
        return Objects.nonNull(flipH) && StringUtils.equals(flipH.getValue(), "1");
    }

    private boolean findIsFlipX(Element xfrm) {
        Attribute flipV = xfrm.getAttribute("flipH");
        return Objects.nonNull(flipV) && StringUtils.equals(flipV.getValue(), "1");
    }

    private Border findBorder(Element pptRealElement) {
        Element spPr = findChildByName(pptRealElement, SP_PR);
        Element ln = findChildByName(spPr, LN);
        Element noFill = findChildByName(ln, NO_FILL);
        if (Objects.isNull(ln) || Objects.nonNull(noFill)) {
            return new ColorBorder().setWidth(0);
        }
        String w = Optional.ofNullable(ln.getAttribute("w"))
                .map(Attribute::getValue)
                .orElse(null);
        if (Strings.isNullOrEmpty(w)) {
            return new ColorBorder().setWidth(1);
        }
        double width = Integer.parseInt(w) * 1.00 / MULTIPLE_POUND;
        return new ColorBorder().setWidth(width);
    }

    private static Background findBackground(Element pptRealElement) {
        try {
            Element spPr = findChildByName(pptRealElement, SP_PR);
            if (Objects.isNull(spPr)) {
                return null;
            }
            Element noFill = findChildByName(spPr, NO_FILL);
            if (Objects.nonNull(noFill)) {
                return NoBackground.builder().build();
            }
            Element solidFill = findChildByName(spPr, SOLID_FILL);
            //即使是覆盖填充也可能存在使用标准色可能
            if (Objects.nonNull(solidFill)) {
                return findColorBackground(solidFill);
            }
            //渐变色的获取
            Element gradFill = findChildByName(spPr, GRAD_FILL);
            if (Objects.nonNull(gradFill)) {
                return findGradientBackground(gradFill);
            }
            //图片背景获取
            Element blipFill = findChildByName(spPr, BLIP_FILL);
            if (Objects.nonNull(blipFill)) {
                return new ImgBackground().setImg("null");
            }
            //特殊覆盖生成（这种不知道啥意思）
            Element grpFill = findChildByName(spPr, GRP_FILL);
            if (Objects.nonNull(grpFill)) {
                return new ColorBackGround().setColor("000000");
            }
            //如果没定义实际上就是默认色
            return NoBackground.builder().build();
        } catch (Throwable t) {
            return null;
        }
    }

    private static Background findGradientBackground(Element gradFill) {
        Element gsLst = findChildByName(gradFill, GS_LST);
        if (Objects.isNull(gsLst)) {
            return null;
        }
        List<Gradient> gradients = findGradients(gsLst);
        Element childByName = findChildByName(gradFill, LIN);
        Double gradientDirection = findGradientDirection(childByName);

        GradientBackground gradientBackground = new GradientBackground();
        gradientBackground.setGradientDirection(gradientDirection);
        gradientBackground.setGradient(gradients);
        return gradientBackground;
    }

    private static List<Gradient> findGradients(Element gsLst) {
        return gsLst.getChildren().stream()
                .map(gs -> {
                    Gradient gradient = new Gradient();
                    String pos = gs.getAttribute(POS).getValue();
                    gradient.setProportion(Long.parseLong(pos) * 1.0 / MULTIPLE_3);
                    Pair<String, Double> colorAndAlphaInElement = findColorAndAlphaInElement(gs);
                    gradient.setColor(colorAndAlphaInElement.getKey());
                    gradient.setAlpha(colorAndAlphaInElement.getValue());
                    return gradient;
                }).collect(Collectors.toList());
    }

    private static Double findGradientDirection(Element childByName) {
        return Optional.ofNullable(childByName)
                .map(c -> c.getAttribute(ANG))
                .map(Attribute::getValue)
                .map(Long::parseLong)
                .map(num -> num * 1.0 / ANGLE)
                .orElse(null);
    }

    private static ColorBackGround findColorBackground(Element solidFill) {
        Pair<String, Double> colorAndAlphaInElement = findColorAndAlphaInElement(solidFill);
        ColorBackGround colorBackGround = new ColorBackGround();
        colorBackGround.setColor(Optional.ofNullable(colorAndAlphaInElement.getKey()).orElse(null));
        colorBackGround.setAlpha(Optional.ofNullable(colorAndAlphaInElement.getValue()).orElse(null));
        return colorBackGround;
    }

    private static Pair<String, Double> findColorAndAlphaInElement(Element fillElement) {
        Element srgbClr = findChildByName(fillElement, SRGB_CLR);
        Element schemaClr = findChildByName(fillElement, SCHEMA_CLR);
        String color = null;
        Double alpha = null;
        if (Objects.nonNull(srgbClr)) {
            color = srgbClr.getAttribute(VAL).getValue();
            alpha = findAlphaFromElement(srgbClr);
        } else if (Objects.nonNull(schemaClr)) {
            color = schemaClr.getAttribute(VAL).getValue();
            alpha = findAlphaFromElement(schemaClr);
        }
        return new Pair<>(color, alpha);
    }

    private static Double findAlphaFromElement(Element srgbClr) {
        return Optional.ofNullable(findChildByName(srgbClr, ALPHA))
                .map(e -> e.getAttribute(VAL))
                .map(Attribute::getValue)
                .map(Double::parseDouble)
                .orElse(null);
    }

    private double findAngle(Element pptRealElement) {
        Element spPr = findChildByName(pptRealElement, SP_PR);
        if (Objects.isNull(spPr)) {
            return DOUBLE_ZERO;
        }
        Element xfrm = findChildByName(spPr, XFRM);
        return Optional.ofNullable(xfrm)
                .map(x -> {
                    Attribute attribute = x.getAttribute(ROT);
                    if (Objects.nonNull(attribute)) {
                        try {
                            return attribute.getLongValue() * 1.0 / ANGLE;
                        } catch (DataConversionException e) {
                            return DOUBLE_ZERO;
                        }
                    }
                    return DOUBLE_ZERO;
                })
                .orElse(DOUBLE_ZERO);
    }

    private Shape findShapeXml(Element pptRealElement) {
        Element spPr = findElementByName(pptRealElement.getChildren(), SP_PR);
        List<Element> elements = spPr.getChildren();
        boolean isFind = false;
        for (Element element : elements) {
            if (isFind) {
                return new SelfShape().setCustGeom(new XMLOutputter().outputString(element).replace(XMLNS, EMPTY));
            }
            if (element.getName().equals(XFRM)) {
                isFind = true;
            }
        }
        return null;
    }

    private String findAllString(Element pptRealElement) {
        StringBuilder sb = new StringBuilder();
        findAllString(pptRealElement, sb);
        return sb.toString();
    }

    private void findAllString(Element element, StringBuilder sb) {
        if (Objects.isNull(element)) {
            return;
        }
        if (T.equals(element.getName()) && A.equals(element.getNamespace().getPrefix())) {
            sb.append(element.getText());
        }
        if (CollectionUtils.isNotEmpty(element.getChildren())) {
            element.getChildren().forEach(e -> this.findAllString(e, sb));
        }
    }

    private Element findXfrm(Element pptRealElement) {
        Element spPr = findElementByName(filterNotElementClass(pptRealElement), SP_PR);
        if (Objects.isNull(spPr)) {
            return null;
        }
        return findElementByName(spPr.getChildren(), XFRM);
    }

    public double findLength(String key, Element xfrm, int fromNum, long scale) {
        try {
            return (xfrm.getChildren().get(fromNum).getAttribute(key).getLongValue() * 1.000 + scale) / Constant.MULTIPLE_CM;
        } catch (Throwable e) {
            return 0;
        }
    }

    public static void main(String[] args) {
        List<PPTElement> elements =
                new PPTSlideRead("C:\\Users\\qrp19\\Desktop\\img\\ppt\\slides\\slide1.xml")
                        .queryElement();
        for (PPTElement element : elements) {
            System.out.println(element.getClass().getSimpleName() + "   " + JSON.toJSONString(element));
        }
    }

}
