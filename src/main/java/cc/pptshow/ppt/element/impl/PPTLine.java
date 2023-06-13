package cc.pptshow.ppt.element.impl;

import cc.pptshow.ppt.domain.PPTElementXml;
import cc.pptshow.ppt.domain.PPTLineCss;
import cc.pptshow.ppt.element.PPTElement;
import cc.pptshow.ppt.util.XmlCodeUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static cc.pptshow.ppt.constant.Constant.MULTIPLE_POUND;
import static cc.pptshow.ppt.domain.PPTLineCss.LineType.TOP_RIGHT_BOTTOM_LEFT;

/**
 * PPT里面的直线
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PPTLine extends PPTElement {

    private PPTLineCss css;

    private static final String FLIPH = " flipH=\"1\"";

    private static final String CXN_SP = " <p:cxnSp><p:nvCxnSpPr><p:cNvPr id=\"";
    private static final String CXN_SP_2 = "\" name=\"直接连接符\"/><p:cNvCxnSpPr/><p:nvPr/>" +
            "</p:nvCxnSpPr><p:spPr><a:xfrm";
    private static final String CXN_SP_3 = ">";
    private static final String PRST_GEOM = "</a:xfrm><a:prstGeom prst=\"line\"><a:avLst/></a:prstGeom>";
    private static final String LN_1 = "<a:ln w=\"";
    private static final String LN_2 = "\"><a:solidFill><a:srgbClr val=\"";
    private static final String LN_3 = "\"/></a:solidFill></a:ln></p:spPr><p:style><a:lnRef idx=\"1\"><a:schemeClr " +
            "val=\"accent1\"/></a:lnRef><a:fillRef idx=\"0\"><a:schemeClr val=\"accent1\"/></a:fillRef><a:effectRef " +
            "idx=\"0\"><a:schemeClr val=\"accent1\"/></a:effectRef><a:fontRef idx=\"minor\"><a:schemeClr val=\"tx1\"/>" +
            "</a:fontRef></p:style></p:cxnSp>";

    @Override
    public PPTElementXml toXml(int relIdBegin, int mediaIdBegin) {
        PPTElementXml xml = new PPTElementXml();
        StringBuilder side = new StringBuilder();

        side.append(CXN_SP).append(getId()).append(CXN_SP_2);
        if (css.getType().equals(TOP_RIGHT_BOTTOM_LEFT)) {
            side.append(FLIPH);
        }
        side.append(CXN_SP_3);
        side.append(XmlCodeUtil.position(css.getLeft(), css.getTop(), css.getWidth(), css.getHeight()));
        side.append(PRST_GEOM);
        side.append(LN_1).append((long) (css.getLineWidth() * MULTIPLE_POUND)).append(LN_2).append(css.getColor()).append(LN_3);

        xml.setSideXml(side);
        xml.setRelXml(new StringBuilder());
        return xml;
    }

    @Override
    public PPTElement clone() {
        PPTLine pptLine = new PPTLine();
        pptLine.setCss(css.clone());
        pptLine.setRemark(getRemark());
        if (Objects.nonNull(getInAnimation())) {
            pptLine.setInAnimation(getInAnimation().clone());
        }
        if (Objects.nonNull(getOutAnimation())) {
            pptLine.setOutAnimation(getOutAnimation().clone());
        }
        pptLine.setId(getId());
        return pptLine;
    }
}
