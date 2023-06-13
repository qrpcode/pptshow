package cc.pptshow.ppt.element.impl;

import cc.pptshow.ppt.constant.Constant;
import cc.pptshow.ppt.domain.FileCopy;
import cc.pptshow.ppt.domain.PPTElementXml;
import cc.pptshow.ppt.domain.PPTShapeCss;
import cc.pptshow.ppt.element.PPTElement;
import cc.pptshow.ppt.util.XmlCodeUtil;
import com.google.common.collect.Lists;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PPTShape extends PPTElement {

    @Getter
    @Setter
    private PPTShapeCss css;

    private static final String P_NV_SP_PR_LEFT = "<p:nvSpPr>";
    private static final String P_NV_SP_PR_RIGHT = "</p:nvSpPr>";
    private static final String P_C_NV_PR_1 = "<p:cNvPr id=\"";
    private static final String P_C_NV_PR_2 = "\" name=\"";
    private static final String P_C_NV_PR_3 = "\"/>";
    private static final String P_C_NV_SP_PR = "<p:cNvSpPr/>";
    private static final String P_C_NV_PR = "<p:nvPr/>";
    private static final String P_SP_PR_LEFT = "<p:spPr>";
    private static final String P_SP_PR_RIGHT = "</p:spPr>";
    private static final String P_A_XFRM_LEFT = "<a:xfrm>";
    private static final String P_A_XFRM_LEFT_1 = "<a:xfrm";
    private static final String P_A_XFRM_LEFT_2 = ">";
    private static final String P_A_XFRM_ROT_LEFT_1 = " rot=\"";
    private static final String P_A_XFRM_ROT_LEFT_2 = "\"";
    private static final String P_A_XFRM_FLIT_H_1 = " flipH=\"";
    private static final String P_A_XFRM_FLIT_H_2 = "\"";
    private static final String P_A_XFRM_FLIT_V_1 = " flipV=\"";
    private static final String P_A_XFRM_FLIT_V_2 = "\"";
    private static final String P_A_XFRM_RIGHT = "</a:xfrm>";
    private static final String P_STYLE = "<p:style><a:lnRef idx=\"2\"><a:schemeClr val=\"accent1\"><a:shade val=\"50000\"/></a:schemeClr></a:lnRef><a:fillRef idx=\"1\"><a:schemeClr val=\"accent1\"/></a:fillRef><a:effectRef idx=\"0\"><a:schemeClr val=\"accent1\"/></a:effectRef><a:fontRef idx=\"minor\"><a:schemeClr val=\"lt1\"/></a:fontRef></p:style>";
    private static final String P_TX_BODY = "<p:txBody><a:bodyPr rtlCol=\"0\" anchor=\"ctr\"/><a:lstStyle/><a:p><a:pPr algn=\"ctr\"/><a:endParaRPr lang=\"zh-CN\" altLang=\"en-US\"/></a:p></p:txBody>";
    private static final String P_SP_LEFT = "<p:sp>";
    private static final String P_SP_RIGHT = "</p:sp>";
    private static final String BORDER_DEFAULT = "<a:ln><a:noFill/></a:ln>";

    @Override
    public PPTElementXml toXml(int relIdBegin, int mediaIdBegin) {
        PPTElementXml xml = new PPTElementXml();
        StringBuilder side = new StringBuilder();
        StringBuilder rel = new StringBuilder();
        List<FileCopy> copy = Lists.newArrayList();

        side.append(P_SP_LEFT);

        side.append(P_NV_SP_PR_LEFT);
        side.append(P_C_NV_PR_1);
        side.append(getId());
        side.append(P_C_NV_PR_2);
        side.append(css.getName());
        side.append(P_C_NV_PR_3);
        side.append(P_C_NV_SP_PR);
        side.append(P_C_NV_PR);
        side.append(P_NV_SP_PR_RIGHT);

        side.append(P_SP_PR_LEFT);
        side.append(P_A_XFRM_LEFT_1);
        if (css.getAngle() != 0) {
            side.append(P_A_XFRM_ROT_LEFT_1);
            side.append(Math.round(css.getAngle() * Constant.ANGLE));
            side.append(P_A_XFRM_ROT_LEFT_2);
        }
        if (css.isFlipX()) {
            side.append(P_A_XFRM_FLIT_H_1);
            side.append(1);
            side.append(P_A_XFRM_FLIT_H_2);
        }
        if (css.isFlipY()) {
            side.append(P_A_XFRM_FLIT_V_1);
            side.append(1);
            side.append(P_A_XFRM_FLIT_V_2);
        }
        side.append(P_A_XFRM_LEFT_2);
        side.append(XmlCodeUtil.position(css.getLeft(), css.getTop(), css.getWidth(), css.getHeight()));
        side.append(P_A_XFRM_RIGHT);
        side.append(css.getShape().prstGeomXmlGet());

        PPTElementXml fillGet = css.getBackground().solidFillGet(mediaIdBegin, relIdBegin);
        side.append(fillGet.getSideXml());
        rel.append(fillGet.getRelXml());
        copy.addAll(fillGet.getFile());

        if (Objects.nonNull(css.getBorder())) {
            side.append(css.getBorder().aLnGet());
        } else {
            side.append(BORDER_DEFAULT);
        }
        side.append(P_SP_PR_RIGHT);

        side.append(P_STYLE);
        side.append(P_TX_BODY);

        side.append(P_SP_RIGHT);

        xml.setSideXml(side);
        xml.setRelXml(rel);
        xml.setFile(copy);
        xml.setIdAdd(copy.size());
        return xml;
    }

    @Override
    public PPTShape clone() {
        PPTShape pptShape = new PPTShape(css.clone());
        pptShape.setRemark(getRemark());
        if (Objects.nonNull(getInAnimation())) {
            pptShape.setInAnimation(getInAnimation().clone());
        }
        if (Objects.nonNull(getOutAnimation())) {
            pptShape.setOutAnimation(getOutAnimation().clone());
        }
        pptShape.setId(getId());
        return pptShape;
    }

    public PPTShape(PPTShapeCss css) {
        this.css = css;
    }

    public static PPTShape build(PPTShapeCss css) {
        return new PPTShape(css);
    }
}
