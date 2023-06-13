package cc.pptshow.ppt.util;

import cc.pptshow.ppt.constant.Constant;
import cc.pptshow.ppt.constant.PPTNameConstant;
import cc.pptshow.ppt.domain.Gradient;
import cc.pptshow.ppt.domain.PPTInnerTextCss;
import cc.pptshow.ppt.domain.Shadow;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 多处使用的公共XML拼装
 */
public class XmlCodeUtil {
    private static long code;

    private static final String TEMP_A_SRGB_CLR_LEFT = "<a:srgbClr val=\"";
    private static final String TEMP_A_SRGB_CLR_RIGHT = "\"/>";
    private static final String TEMP_A_SOLID_FILL_RIGHT = "</a:solidFill>";
    private static final String TEMP_A_SOLID_FILL_NULL = "<a:noFill/>";
    private static final String TEMP_A_EFFECT_LST = "<a:effectLst/>";
    private static final String TEMP_A_LATIN_1 = "<a:latin typeface=\"";
    private static final String TEMP_A_LATIN_2 = "\"/>";
    private static final String TEMP_A_EA_1 = "<a:ea typeface=\"";
    private static final String TEMP_A_EA_2 = "\"/>";
    private static final String TEMP_A_SOLID_FILL_LEFT = "<a:solidFill>";
    private static final String TEMP_A_SCHEME_CLR_DEFAULT = "<a:schemeClr val=\"tx1\"/>";

    private static final String REL_IMG_1 = "<Relationship Id=\"rId";
    private static final String REL_IMG_2 = "\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/image\" Target=\"../media/";
    private static final String REL_IMG_3 = "\"/>";

    private static final String REL_FONT_1 = "<Relationship Id=\"rId";
    private static final String REL_FONT_2 = "\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/font\" Target=\"fonts/font";
    private static final String REL_FONT_3 = ".fntdata\"/>";

    private static final String TEMP_A_OFF_1 = "<a:off x=\"";
    private static final String TEMP_A_OFF_2 = "\" y=\"";
    private static final String TEMP_A_OFF_3 = "\"/>";
    private static final String TEMP_A_EXT_1 = "<a:ext cx=\"";
    private static final String TEMP_A_EXT_2 = "\" cy=\"";
    private static final String TEMP_A_EXT_3 = "\"/>";

    private static final String TEMP_A_GS_1 = "<a:gs pos=\"";
    private static final String TEMP_A_GS_2 = "\"><a:srgbClr val=\"";
    private static final String TEMP_A_GS_3 = "\">";
    private static final String TEMP_A_GS_END = "</a:gs>";
    private static final String TEMP_A_GS_ONLY_COLOR_END = "\"/>";
    private static final String TEMP_A_GS_COLOR_END = "</a:srgbClr>";
    private static final String TEMP_A_ALPHA_LEFT = "<a:alpha val=\"";
    private static final String TEMP_A_ALPHA_RIGHT = "\"/>";
    private static final String TEMP_A_LUM_M_LEFT = "<a:lumMod val=\"";
    private static final String TEMP_A_LUM_M_RIGHT = "\"/>";
    private static final String TEMP_A_LUM_O_LEFT = "<a:lumOff val=\"";
    private static final String TEMP_A_LUM_O_RIGHT = "\"/>";

    private static final String TEMP_A_GRAD_FILL_LEFT = "<a:gradFill>";
    private static final String TEMP_A_GRAD_FILL_RIGHT = "</a:gradFill>";
    private static final String TEMP_A_GS_LIST_LEFT = "<a:gsLst>";
    private static final String TEMP_A_GS_LIST_RIGHT = "</a:gsLst>";
    private static final String TEMP_A_LIN_LEFT = "<a:lin ang=\"";
    private static final String TEMP_A_LIN_RIGHT = "\" scaled=\"0\"/>";

    private static final String A_XFRM_LEFT = "<a:xfrm>";
    private static final String A_XFRM_ROT_LEFT_1 = "<a:xfrm rot=\"";
    private static final String A_XFRM_ROT_LEFT_2 = "\">";

    private static final String A_EFFECT_LST_LEFT = "<a:effectLst>";
    private static final String A_EFFECT_LST_RIGHT = "</a:effectLst>";
    private static final String A_OUTER_SHDW_LEFT_1 = "<a:outerShdw ";
    private static final String A_OUTER_SHDW_LEFT_2 = " algn=\"tl\" rotWithShape=\"0\">";
    private static final String A_OUTER_SHDW_LEFT_BLUR = "blurRad=\"";
    private static final String A_OUTER_SHDW_LEFT_DISIT = "dist=\"";
    private static final String A_OUTER_SHDW_LEFT_DIR = "dir=\"";
    private static final String A_OUTER_SHDW_LEFT_SX = "sx=\"";
    private static final String A_OUTER_SHDW_LEFT_SY = "sy=\"";
    private static final String A_OUTER_SHDW_LEFT_ONCE_END = "\" ";
    private static final String A_OUTER_SHDW_RIGHT = "</a:outerShdw>";
    private static final String A_SRGB_CLR_LEFT_1 = "<a:srgbClr val=\"";
    private static final String A_SRGB_CLR_LEFT_2 = "\">";
    private static final String A_SRGB_CLR_RIGHT = "</a:srgbClr>";
    private static final String A_ALPHA_1 = "<a:alpha val=\"";
    private static final String A_ALPHA_2 = "\"/>";

    public static void aFontGet(PPTInnerTextCss pptInnerTextCss, StringBuilder sb) {
        if (PPTNameConstant.NO_FILL.equalsIgnoreCase(pptInnerTextCss.getColor())) {
            sb.append(TEMP_A_SOLID_FILL_NULL);
        } else {
            sb.append(TEMP_A_SOLID_FILL_LEFT);
            if (Objects.isNull(pptInnerTextCss.getColor())) {
                sb.append(TEMP_A_SCHEME_CLR_DEFAULT);
            } else {
                sb.append(TEMP_A_SRGB_CLR_LEFT);
                sb.append(pptInnerTextCss.getColor());
                sb.append(TEMP_A_SRGB_CLR_RIGHT);
            }
            sb.append(TEMP_A_SOLID_FILL_RIGHT);
        }
        sb.append(TEMP_A_EFFECT_LST);
        sb.append(TEMP_A_LATIN_1);
        sb.append(pptInnerTextCss.getFontFamily());
        sb.append(TEMP_A_LATIN_2);
        sb.append(TEMP_A_EA_1);
        sb.append(pptInnerTextCss.getFontFamily());
        sb.append(TEMP_A_EA_2);
    }

    public static String imgGet(int relId, String imgName) {
        return REL_IMG_1 + relId + REL_IMG_2 + imgName + REL_IMG_3;
    }

    public static String fontGet(int relId, int fontId) {
        return REL_FONT_1 + relId + REL_FONT_2 + fontId + REL_FONT_3;
    }

    public static StringBuilder position(double left, double top, double width, double height) {
        left = Math.max(0, left);
        top = Math.max(0, top);
        width = Math.max(0, width);
        height = Math.max(0, height);
        StringBuilder sb = new StringBuilder();
        sb.append(TEMP_A_OFF_1);
        sb.append(XmlCodeUtil.toTimes(left));
        sb.append(TEMP_A_OFF_2);
        sb.append(XmlCodeUtil.toTimes(top));
        sb.append(TEMP_A_OFF_3);
        sb.append(TEMP_A_EXT_1);
        sb.append(XmlCodeUtil.toTimes(width));
        sb.append(TEMP_A_EXT_2);
        sb.append(XmlCodeUtil.toTimes(height));
        sb.append(TEMP_A_EXT_3);
        return sb;
    }

    public static StringBuilder gsLstGet(Gradient gradient) {
        StringBuilder sb = new StringBuilder();
        sb.append(TEMP_A_GS_1);
        sb.append(Math.round(gradient.getProportion() * Constant.MULTIPLE_3));
        sb.append(TEMP_A_GS_2);
        sb.append(gradient.getColor());
        if (Objects.nonNull(gradient.getAlpha()) || Objects.nonNull(gradient.getLum())) {
            sb.append(TEMP_A_GS_3);
            if (Objects.nonNull(gradient.getAlpha())) {
                sb.append(TEMP_A_ALPHA_LEFT);
                sb.append(Math.round((100 - gradient.getAlpha()) * 1000));
                sb.append(TEMP_A_ALPHA_RIGHT);
            }
            if (Objects.nonNull(gradient.getLum())) {
                sb.append(TEMP_A_LUM_M_LEFT);
                sb.append(Math.round((100 - gradient.getLum()) * 1000));
                sb.append(TEMP_A_LUM_M_RIGHT);
                sb.append(TEMP_A_LUM_O_LEFT);
                sb.append(Math.round(gradient.getLum() * 1000));
                sb.append(TEMP_A_LUM_O_RIGHT);
            }
            sb.append(TEMP_A_GS_COLOR_END);
        } else {
            sb.append(TEMP_A_GS_ONLY_COLOR_END);
        }
        sb.append(TEMP_A_GS_END);
        return sb;
    }

    public static StringBuilder gradientGet(List<Gradient> gradients, Double direction) {
        StringBuilder sb = new StringBuilder();
        sb.append(TEMP_A_GRAD_FILL_LEFT);
        sb.append(TEMP_A_GS_LIST_LEFT);
        gradients.stream().map(XmlCodeUtil::gsLstGet).collect(Collectors.toList()).forEach(sb::append);
        sb.append(TEMP_A_GS_LIST_RIGHT);
        if (Objects.nonNull(direction) && direction >= Constant.ANGLE_MIN && direction <= Constant.ANGLE_MAX) {
            sb.append(TEMP_A_LIN_LEFT);
            sb.append(Math.round(Constant.ANGLE * direction));
            sb.append(TEMP_A_LIN_RIGHT);
        }
        sb.append(TEMP_A_GRAD_FILL_RIGHT);
        return sb;
    }

    public static String xfrmGet(double angle) {
        if (angle > 0 && angle < 360) {
            return A_XFRM_ROT_LEFT_1 + Math.round(angle * Constant.ANGLE) + A_XFRM_ROT_LEFT_2;
        } else {
            return A_XFRM_LEFT;
        }
    }

    public static String shadowGet(Shadow shadow) {
        if (Objects.isNull(shadow)) {
            return Constant.EMPTY;
        }

        return A_EFFECT_LST_LEFT
                + A_OUTER_SHDW_LEFT_1
                + A_OUTER_SHDW_LEFT_BLUR + Math.round(shadow.getBlur() * Constant.MULTIPLE_POUND) + A_OUTER_SHDW_LEFT_ONCE_END
                + A_OUTER_SHDW_LEFT_DISIT + Math.round(shadow.getDistance() * Constant.MULTIPLE_POUND) + A_OUTER_SHDW_LEFT_ONCE_END
                + A_OUTER_SHDW_LEFT_DIR + Math.round(shadow.getAngle() * Constant.ANGLE) + A_OUTER_SHDW_LEFT_ONCE_END
                + A_OUTER_SHDW_LEFT_SX + Math.round(shadow.getSizeX() * Constant.MULTIPLE_3) + A_OUTER_SHDW_LEFT_ONCE_END
                + A_OUTER_SHDW_LEFT_SY + Math.round(shadow.getSizeY() * Constant.MULTIPLE_3) + A_OUTER_SHDW_LEFT_ONCE_END
                + A_OUTER_SHDW_LEFT_2 + A_SRGB_CLR_LEFT_1 + shadow.getColor() + A_SRGB_CLR_LEFT_2
                + A_ALPHA_1 + Math.round((100 - shadow.getAlpha()) * Constant.MULTIPLE_3) + A_ALPHA_2 +
                A_SRGB_CLR_RIGHT + A_OUTER_SHDW_RIGHT + A_EFFECT_LST_RIGHT;
    }

    public synchronized static long code() {
        return code++;
    }

    public static long toTimes(double number) {
        return Math.round(number * Constant.MULTIPLE_CM);
    }

}
