package cc.pptshow.ppt.element.impl;

import cc.pptshow.ppt.constant.Constant;
import cc.pptshow.ppt.domain.cut.Cutting;
import cc.pptshow.ppt.domain.FileCopy;
import cc.pptshow.ppt.domain.PPTElementXml;
import cc.pptshow.ppt.domain.PPTImgCss;
import cc.pptshow.ppt.domain.cut.shape.RectCutShape;
import cc.pptshow.ppt.element.PPTElement;
import cc.pptshow.ppt.util.FileUtil;
import cc.pptshow.ppt.util.XmlCodeUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.*;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class PPTImg extends PPTElement {

    @Getter
    @Setter
    private PPTImgCss css;

    private static final String P_PIC_LEFT = "<p:pic>";
    private static final String P_PIC_RIGHT = "</p:pic>";
    private static final String P_NV_PIC_PR_LEFT = "<p:nvPicPr>";
    private static final String P_NV_PIC_PR_RIGHT = "</p:nvPicPr>";
    private static final String P_C_NV_PR_1 = "<p:cNvPr id=\"";
    private static final String P_C_NV_PR_2 = "\" name=\"";
    private static final String P_C_NV_PR_3 = "\" descr=\"";
    private static final String P_C_NV_PR_4 = "\"/>";
    private static final String P_C_NV_PIC_PR_LEFT = "<p:cNvPicPr>";
    private static final String P_C_NV_PIC_PR_RIGHT = "</p:cNvPicPr>";
    private static final String A_PIC_LOCKS = "<a:picLocks noChangeAspect=\"1\"/>";
    private static final String P_NV_PR = "<p:nvPr/>";
    private static final String P_BLIP_FILL_LEFT = "<p:blipFill>";
    private static final String P_BLIP_FILL_RIGHT = "</p:blipFill>";
    private static final String A_BLIP_RID_1 = "<a:blip r:embed=\"rId";
    private static final String A_BLIP_RID_2 = "\"/>";
    private static final String A_BLIP_RID_REMOVE_1 = "<a:blip r:embed=\"rId";
    private static final String A_BLIP_RID_REMOVE_2 = "\"><a:clrChange><a:clrFrom><a:srgbClr val=\"";
    private static final String A_BLIP_RID_REMOVE_3 = "\"><a:alpha val=\"100000\"/></a:srgbClr></a:clrFrom><a:clrTo><a:srgbClr val=\"";
    private static final String A_BLIP_RID_REMOVE_4 = "\"><a:alpha val=\"100000\"/><a:alpha val=\"0\"/></a:srgbClr></a:clrTo></a:clrChange></a:blip>";
    private static final String A_BLIP_RID_SVG_1 = "<a:blip r:embed=\"rId";
    private static final String A_BLIP_RID_SVG_2 = "\"><a:extLst><a:ext><asvg:svgBlip xmlns:asvg=\"http://schemas.microsoft.com/office/drawing/2016/SVG/main\" r:embed=\"rId";
    private static final String A_BLIP_RID_SVG_3 = "\"/></a:ext></a:extLst></a:blip>";
    private static final String A_SRC_RECT_1 = "<a:srcRect ";
    private static final String A_SRC_RECT_L = "l=\"";
    private static final String A_SRC_RECT_R = "r=\"";
    private static final String A_SRC_RECT_T = "t=\"";
    private static final String A_SRC_RECT_B = "b=\"";
    private static final String A_SRC_RECT_2 = "\" ";
    private static final String A_SRC_RECT_3 = "/>";
    private static final String A_STRETCH_LEFT = "<a:stretch>";
    private static final String A_STRETCH_RIGHT = "</a:stretch>";
    private static final String A_FILL_RECT = "<a:fillRect/>";
    private static final String P_SP_PR_LEFT = "<p:spPr>";
    private static final String P_SP_PR_RIGHT = "</p:spPr>";
    private static final String A_XFRM_RIGHT = "</a:xfrm>";
    private static final String A_PRST_GROM_LEFT_1 = "<a:prstGeom prst=\"";
    private static final String A_PRST_GROM_LEFT_2 = "\">";
    private static final String A_PRST_GROM_RIGHT = "</a:prstGeom>";

    private String file;

    @Override
    public PPTElementXml toXml(int relIdBegin, int mediaIdBegin) {
        if (Objects.isNull(css)) {
            this.css = new PPTImgCss();
        }
        if (Strings.isNullOrEmpty(file)) {
            throw new RuntimeException("您必须先设置插入图片的file信息");
        }
        StringBuilder side = new StringBuilder();
        StringBuilder rel = new StringBuilder();
        List<FileCopy> file = Lists.newArrayList();
        PPTElementXml xml = new PPTElementXml();
        picGet(side, relIdBegin);
        relGet(rel, file, mediaIdBegin, relIdBegin);
        xml.setRelXml(rel);
        xml.setSideXml(side);
        xml.setFile(file);
        xml.setIdAdd(file.size());
        return xml;
    }

    @Override
    public PPTImg clone() {
        PPTImg pptImg = new PPTImg();
        pptImg.setFile(file);
        pptImg.setCss(css.clone());
        pptImg.setRemark(getRemark());
        if (Objects.nonNull(getInAnimation())) {
            pptImg.setInAnimation(getInAnimation().clone());
        }
        if (Objects.nonNull(getOutAnimation())) {
            pptImg.setOutAnimation(getOutAnimation().clone());
        }
        pptImg.setId(getId());
        return pptImg;
    }

    private void relGet(StringBuilder rel, List<FileCopy> file, int mediaIdBegin, int relIdBegin) {
        String imgName = FileUtil.getMediaImgName(mediaIdBegin, this.file);
        FileCopy copy = new FileCopy().setFrom(this.file)
                .setTo(Constant.MEDIA + imgName);
        file.add(copy);
        rel.append(XmlCodeUtil.imgGet(relIdBegin, imgName));
        if (imgName.endsWith(Constant.SVG)) {
            imgName = FileUtil.getMediaImgName(mediaIdBegin + 1, this.file);
            String svgImage = imgName.substring(0, imgName.length() - Constant.SVG.length()) + Constant.PNG;
            FileCopy svgCopy = new FileCopy().setFrom(this.file).setTo(Constant.MEDIA + svgImage);
            file.add(svgCopy);
            rel.append(XmlCodeUtil.imgGet(relIdBegin + 1, svgImage));
        }
    }

    private void picGet(StringBuilder side, int relIdBegin) {
        side.append(P_PIC_LEFT);
        nvPicPrGet(side);
        blipFillGet(side, relIdBegin);
        pSpPrGet(side);
        side.append(P_PIC_RIGHT);
    }

    private void pSpPrGet(StringBuilder side) {
        side.append(P_SP_PR_LEFT);
        side.append(XmlCodeUtil.xfrmGet(css.getAngle()));
        side.append(XmlCodeUtil.position(css.getLeft(), css.getTop(), css.getWidth(), css.getHeight()));
        side.append(A_XFRM_RIGHT);
        side.append(A_PRST_GROM_LEFT_1);
        if (Objects.isNull(css.getCutting())) {
            css.setCutting(new Cutting());
        }
        if (Objects.isNull(css.getCutting().getCutShape())) {
            css.getCutting().setCutShape(new RectCutShape());
        }
        side.append(css.getCutting().getCutShape().getCode());
        side.append(A_PRST_GROM_LEFT_2);
        side.append(css.getCutting().getCutShape().getXml());
        side.append(A_PRST_GROM_RIGHT);
        if (Objects.nonNull(css.getBorder())) {
            side.append(css.getBorder().aLnGet());
        }
        if (Objects.nonNull(css.getShadow())) {
            side.append(XmlCodeUtil.shadowGet(css.getShadow()));
        }
        side.append(P_SP_PR_RIGHT);
    }

    private void blipFillGet(StringBuilder side, int relIdBegin) {
        side.append(P_BLIP_FILL_LEFT);
        if (file.endsWith(Constant.SVG)) {
            side.append(A_BLIP_RID_SVG_1);
            side.append(relIdBegin);
            side.append(A_BLIP_RID_SVG_2);
            side.append(relIdBegin + 1);
            side.append(A_BLIP_RID_SVG_3);
        } else {
            if (Strings.isNullOrEmpty(css.getRemoveColor())) {
                side.append(A_BLIP_RID_1);
                side.append(relIdBegin);
                side.append(A_BLIP_RID_2);
            } else {
                side.append(A_BLIP_RID_REMOVE_1);
                side.append(relIdBegin);
                side.append(A_BLIP_RID_REMOVE_2);
                side.append(css.getRemoveColor());
                side.append(A_BLIP_RID_REMOVE_3);
                side.append(css.getRemoveColor());
                side.append(A_BLIP_RID_REMOVE_4);
            }
        }
        Cutting cutting = css.getCutting();
        if (Objects.isNull(cutting)) {
            cutting = new Cutting();
            css.setCutting(cutting);
        }
        if (cutting.getLeft() > 0 || cutting.getTop() > 0
                || cutting.getRight() > 0 || cutting.getBottom() > 0) {
            side.append(A_SRC_RECT_1);
            if (cutting.getLeft() > 0) {
                side.append(A_SRC_RECT_L);
                side.append(Math.round(cutting.getLeft() * Constant.MULTIPLE_3));
                side.append(A_SRC_RECT_2);
            }
            if (cutting.getTop() > 0) {
                side.append(A_SRC_RECT_T);
                side.append(Math.round(cutting.getTop() * Constant.MULTIPLE_3));
                side.append(A_SRC_RECT_2);
            }
            if (cutting.getRight() > 0) {
                side.append(A_SRC_RECT_R);
                side.append(Math.round(cutting.getRight() * Constant.MULTIPLE_3));
                side.append(A_SRC_RECT_2);
            }
            if (cutting.getBottom() > 0) {
                side.append(A_SRC_RECT_B);
                side.append(Math.round(cutting.getBottom() * Constant.MULTIPLE_3));
                side.append(A_SRC_RECT_2);
            }
            side.append(A_SRC_RECT_3);
        }
        side.append(A_STRETCH_LEFT);
        side.append(A_FILL_RECT);
        side.append(A_STRETCH_RIGHT);
        side.append(P_BLIP_FILL_RIGHT);
    }

    private void nvPicPrGet(StringBuilder side) {
        side.append(P_NV_PIC_PR_LEFT);
        cNvPrGet(side);
        side.append(P_NV_PR);
        side.append(P_NV_PIC_PR_RIGHT);
    }

    private void cNvPrGet(StringBuilder side) {
        side.append(P_C_NV_PR_1);
        side.append(getId());
        side.append(P_C_NV_PR_2);
        side.append(css.getName());
        side.append(P_C_NV_PR_3);
        side.append(css.getDescribe());
        side.append(P_C_NV_PR_4);
        side.append(P_C_NV_PIC_PR_LEFT);
        side.append(A_PIC_LOCKS);
        side.append(P_C_NV_PIC_PR_RIGHT);
    }

    public String getFile() {
        return file;
    }

    public PPTImg setFile(String file) {
        this.file = file;
        return this;
    }

    public PPTImg(String file) {
        this.file = file;
    }

    public PPTImg(String file, PPTImgCss imgCss) {
        this.file = file;
        this.css = imgCss;
    }

    public static PPTImg build() {
        return new PPTImg();
    }

    public static PPTImg build(String file) {
        return new PPTImg(file);
    }

    public static PPTImg build(String file, PPTImgCss imgCss) {
        return new PPTImg(file, imgCss);
    }
}
