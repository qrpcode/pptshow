package cc.pptshow.ppt.element.impl;

import cc.pptshow.ppt.constant.Constant;
import cc.pptshow.ppt.domain.PPTElementXml;
import cc.pptshow.ppt.domain.PPTInnerTextCss;
import cc.pptshow.ppt.element.PPTInnerElement;
import cc.pptshow.ppt.util.XmlCodeUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * PPT行结束对象
 * PPT每一行结束的地方都会包裹一个结束对象，您在这一行后面输入新的文本时将会使用这个样式
 * 如果没啥特殊需要不需要额外添加，系统会在每一行最后自动生成添加结束样式，此样式将和最后一小节一致
 */
public class PPTInnerTextEnd implements PPTInnerElement {

    @Getter
    @Setter
    private PPTInnerTextCss css;

    private static final String TEMP_A_END_PARA_RPR_LEFT_1 = "<a:endParaRPr lang=\"zh-CN\" altLang=\"en-US\" sz=\"";
    private static final String TEMP_A_END_PARA_RPR_LEFT_2 = "\">";
    private static final String TEMP_A_END_PARA_RPR_RIGHT = "</a:endParaRPr>";

    @Override
    public PPTElementXml toXml() {
        if (Objects.isNull(css)) {
            css = new PPTInnerTextCss();
        }

        StringBuilder sb = new StringBuilder();
        PPTElementXml pptElementXML = new PPTElementXml();

        endXml(sb);

        pptElementXML.setSideXml(sb);
        return pptElementXML;
    }

    @Override
    public PPTInnerTextEnd clone() {
        return new PPTInnerTextEnd(css.clone());
    }

    private void endXml(StringBuilder sb) {
        sb.append(TEMP_A_END_PARA_RPR_LEFT_1);
        sb.append(css.getFontSize() * Constant.MULTIPLE_2);
        sb.append(TEMP_A_END_PARA_RPR_LEFT_2);
        XmlCodeUtil.aFontGet(css, sb);
        sb.append(TEMP_A_END_PARA_RPR_RIGHT);
    }

    public PPTInnerTextEnd() {
    }

    public static PPTInnerTextEnd build() {
        return new PPTInnerTextEnd();
    }

    public PPTInnerTextEnd(PPTInnerTextCss css) {
        this.css = css;
    }

    public PPTInnerTextEnd build(PPTInnerTextCss css) {
        return new PPTInnerTextEnd(css);
    }
}
