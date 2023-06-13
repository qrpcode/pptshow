package cc.pptshow.ppt.element.impl;

import cc.pptshow.ppt.constant.Constant;
import cc.pptshow.ppt.domain.*;
import cc.pptshow.ppt.element.PPTElement;
import cc.pptshow.ppt.util.XmlCodeUtil;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cc.pptshow.ppt.constant.Constant.*;

/**
 * PPT文字模块
 * 实际上这里是用艺术字的模板做的，因为这样能够支持更多样式
 * 为了不花时间在XML解析组装上，我们直接使用了字符串手动拼接，希望没写错什么 :)
 */
@EqualsAndHashCode(callSuper = false)
public class PPTText extends PPTElement {

    @Setter
    @Getter
    private List<PPTInnerLine> lineList = Lists.newArrayList();

    @Setter
    @Getter
    private PPTTextCss css = new PPTTextCss();

    private static final String TEMP_SP_TREE_LEFT = "<p:sp>";
    private static final String TEMP_SP_TREE_RIGHT = "</p:sp>";
    private static final String TEMP_NV_SP_PR_LEFT = "<p:nvSpPr>";
    private static final String TEMP_NV_SP_PR_RIGHT = "</p:nvSpPr>";
    private static final String TEMP_CN_PR_1 = "<p:cNvPr id=\"";
    private static final String TEMP_CN_PR_2 = "\" name=\"";
    private static final String TEMP_CN_PR_3 = "\"/>";
    private static final String TEMP_CN_SP_PR = "<p:cNvSpPr/>";
    private static final String TEMP_NV_PR = "<p:nvPr/>";
    private static final String TEMP_SP_PR_LEFT = "<p:spPr>";
    private static final String TEMP_SP_PR_RIGHT = "</p:spPr>";
    private static final String TEMP_A_XFRM_RIGHT = "</a:xfrm>";
    private static final String TEMP_A_PRST_GEOM = "<a:prstGeom prst=\"rect\"><a:avLst/></a:prstGeom>";
    private static final String TEMP_A_NO_FILL = "<a:noFill/>";
    private static final String TEMP_A_LN = "<a:ln><a:noFill/></a:ln>";
    private static final String TEMP_TX_BODY_LEFT = "<p:txBody>";
    private static final String TEMP_TX_BODY_RIGHT = "</p:txBody>";
    private static final String TEMP_A_BODY_PR_LEFT = "<a:bodyPr wrap=\"none\" rtlCol=\"0\" anchor=\"t\">";
    private static final String TEMP_A_BODY_PR_SQUARE_LEFT = "<a:bodyPr wrap=\"square\" rtlCol=\"0\" anchor=\"t\">";
    private static final String TEMP_A_SP_AUTO_FIT = "<a:spAutoFit/>";
    private static final String TEMP_A_BODY_PR_RIGHT = "</a:bodyPr>";

    private int relIdBegin;
    private int mediaIdBegin;

    /**
     * 生成XML文本信息
     *
     * @return 返回生成的xml
     */
    @Override
    public PPTElementXml toXml(int relIdBegin, int mediaIdBegin) {
        this.relIdBegin = relIdBegin;
        this.mediaIdBegin = mediaIdBegin;

        if (Objects.isNull(css)) {
            css = new PPTTextCss();
        }

        if (css.getWidth() == 0) {
            double width = Constant.MULTIPLE_CM * findMinWidthSize();
            css.setWidth(width);
        }

        if (css.getHeight() == 0) {
            double height = Constant.MULTIPLE_CM * findMinHeightSize();
            css.setHeight(height);
        }

        StringBuilder sb = new StringBuilder();
        StringBuilder rel = new StringBuilder();
        List<FileCopy> fileCopies = Lists.newArrayList();
        spTreeGet(sb, rel, fileCopies);

        PPTElementXml pptElementXML = new PPTElementXml();
        pptElementXML.setSideXml(sb);
        pptElementXML.setRelXml(rel);
        pptElementXML.setFile(fileCopies);
        pptElementXML.setIdAdd(fileCopies.size());
        return pptElementXML;
    }

    public double findMinWidthSize() {
        double width = 0L;
        for (PPTInnerLine pptInnerLine : lineList) {
            width = Math.max(width, pptInnerLine.toWidth());
        }
        return width / Constant.MULTIPLE_CM;
    }

    public double findMinHeightSize() {
        double height = 0;
        for (PPTInnerLine pptInnerLine : lineList) {
            if (pptInnerLine.toWidth() > (css.getWidth() * Constant.MULTIPLE_CM)) {
                height += Math.ceil(pptInnerLine.toWidth() / (css.getWidth() * Constant.MULTIPLE_CM)) * pptInnerLine.toHeight();
            } else {
                height += pptInnerLine.toHeight();
            }
        }
        return height / Constant.MULTIPLE_CM + PPT_TEXT_TOP_BOTTOM_SLIDES;
    }

    @Override
    public PPTText clone() {
        List<PPTInnerLine> lines = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(lineList)) {
            lines = lineList.stream().map(PPTInnerLine::clone).collect(Collectors.toList());
        }
        PPTText pptText = PPTText.build(lines, css.clone());
        pptText.setRemark(getRemark());
        if (Objects.nonNull(getInAnimation())) {
            pptText.setInAnimation(getInAnimation().clone());
        }
        if (Objects.nonNull(getOutAnimation())) {
            pptText.setOutAnimation(getOutAnimation().clone());
        }
        pptText.setId(getId());
        return pptText;
    }

    private void spTreeGet(StringBuilder sb, StringBuilder rel, List<FileCopy> fileCopies) {
        sb.append(TEMP_SP_TREE_LEFT);
        nvSpPrGet(sb);
        spPrGet(sb, rel, fileCopies);
        txBodyGet(sb);
        sb.append(TEMP_SP_TREE_RIGHT);
    }

    private void txBodyGet(StringBuilder sb) {
        sb.append(TEMP_TX_BODY_LEFT);
        aBodyPrGet(sb);
        for (PPTInnerLine pptInnerLine : lineList) {
            sb.append(pptInnerLine.toXml().getSideXml());
        }
        sb.append(TEMP_TX_BODY_RIGHT);
    }

    private void aBodyPrGet(StringBuilder sb) {
//        List<String> dists = lineList.stream().map(PPTInnerLine::getCss)
//                .map(PPTInnerLineCss::getAlign).filter(ALIGN_DIST::equals).collect(Collectors.toList());
        sb.append(TEMP_A_BODY_PR_SQUARE_LEFT);
        sb.append(TEMP_A_SP_AUTO_FIT);
        sb.append(TEMP_A_BODY_PR_RIGHT);
    }

    private void spPrGet(StringBuilder sb, StringBuilder rel, List<FileCopy> fileCopies) {
        sb.append(TEMP_SP_PR_LEFT);
        xfrmGet(sb);
        sb.append(TEMP_A_PRST_GEOM);
        if (Objects.isNull(css.getBackground())) {
            sb.append(TEMP_A_NO_FILL);
        } else {
            PPTElementXml pptElementXml = css.getBackground().solidFillGet(mediaIdBegin, relIdBegin);
            mediaIdBegin++;
            relIdBegin++;
            sb.append(pptElementXml.getSideXml());
            rel.append(pptElementXml.getRelXml());
            fileCopies.addAll(pptElementXml.getFile());
        }
        sb.append(TEMP_A_LN);
        sb.append(TEMP_SP_PR_RIGHT);
    }

    private void xfrmGet(StringBuilder sb) {
        sb.append(XmlCodeUtil.xfrmGet(css.getAngle()));
        sb.append(XmlCodeUtil.position(css.getLeft(), css.getTop(), css.getWidth(), css.getHeight()));
        sb.append(TEMP_A_XFRM_RIGHT);
    }

    private void nvSpPrGet(StringBuilder sb) {
        sb.append(TEMP_NV_SP_PR_LEFT);
        cnPrGet(sb);
        sb.append(TEMP_CN_SP_PR);
        sb.append(TEMP_NV_PR);
        sb.append(TEMP_NV_SP_PR_RIGHT);
    }

    private void cnPrGet(StringBuilder sb) {
        sb.append(TEMP_CN_PR_1);
        sb.append(getId());
        sb.append(TEMP_CN_PR_2);
        sb.append(css.getName());
        sb.append(TEMP_CN_PR_3);
    }

    public PPTText() {
    }

    public PPTText(List<PPTInnerLine> lineList) {
        this.lineList = lineList;
    }

    public PPTText(PPTInnerLine line) {
        this.lineList = Lists.newArrayList(line);
    }

    public PPTText(List<PPTInnerLine> lineList, PPTTextCss css) {
        this.lineList = lineList;
        this.css = css;
    }

    public PPTText(PPTInnerLine line, PPTTextCss css) {
        this.lineList = Lists.newArrayList(line);
        this.css = css;
    }

    public static PPTText build() {
        return new PPTText();
    }

    public static PPTText build(PPTInnerLine text) {
        return new PPTText(text);
    }

    public static PPTText build(List<PPTInnerLine> textList) {
        return new PPTText(textList);
    }

    public static PPTText build(PPTInnerLine text, PPTTextCss css) {
        return new PPTText(text, css);
    }

    public static PPTText build(List<PPTInnerLine> textList, PPTTextCss css) {
        return new PPTText(textList, css);
    }

    public PPTText add(PPTInnerLine text) {
        this.lineList.add(text);
        return this;
    }

    public PPTText addAll(Collection<PPTInnerLine> text) {
        this.lineList.addAll(text);
        return this;
    }

    public String findAllText() {
        List<String> lineTexts = this.lineList.stream()
                .map(PPTInnerLine::getTextList)
                .map(line -> Joiner.on(EMPTY)
                        .join(line.stream()
                                .map(PPTInnerText::getText)
                                .collect(Collectors.toList())))
                .collect(Collectors.toList());
        return Joiner.on(NEW_LINE).join(lineTexts).trim();
    }

}
