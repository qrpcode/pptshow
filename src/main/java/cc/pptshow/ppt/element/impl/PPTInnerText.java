package cc.pptshow.ppt.element.impl;

import cc.pptshow.ppt.constant.Constant;
import cc.pptshow.ppt.domain.PPTElementXml;
import cc.pptshow.ppt.domain.PPTInnerTextCss;
import cc.pptshow.ppt.domain.TextCount;
import cc.pptshow.ppt.element.PPTInnerElement;
import cc.pptshow.ppt.util.TextUtil;
import cc.pptshow.ppt.util.XmlCodeUtil;
import com.google.common.xml.XmlEscapers;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static cc.pptshow.ppt.constant.Constant.*;

/**
 * 一个text的文本框中可能包含不同的样式
 * 而这个组件的粒度就是其中的一个“小节”
 * 比如：Hello World中，Hello是黑色，World是红色
 * 那么它们就是两个小节
 *
 * 注意：当前粒度不能指定行距、位置等信息，您需要在PPTText中指定
 */
public class PPTInnerText implements PPTInnerElement {

    @Setter
    @Getter
    private String text = null;

    /**
     * 样式表对象
     */
    @Setter
    @Getter
    private PPTInnerTextCss css = new PPTInnerTextCss();

    private static final String TEMP_A_R_LEFT = "<a:r>";
    private static final String TEMP_A_R_RIGHT = "</a:r>";
    private static final String TEMP_A_RPR_LEFT_1 = "<a:rPr lang=\"zh-CN\" altLang=\"en-US\" sz=\"";
    private static final String TEMP_A_RPR_LEFT_2 = "\"";
    private static final String TEMP_A_RPR_LEFT_3 = ">";
    private static final String TEMP_A_RPR_LEFT_I = " i=\"1\"";
    private static final String TEMP_A_RPR_LEFT_B = " b=\"1\"";
    private static final String TEMP_A_RPR_LEFT_SPC_1 = " spc=\"";
    private static final String TEMP_A_RPR_LEFT_SPC_2 = "\" ";
    private static final String TEMP_A_RPR_RIGHT = "</a:rPr>";
    private static final String TEMP_A_T_LEFT = "<a:t>";
    private static final String TEMP_A_T_RIGHT = "</a:t>";

    @Override
    public PPTElementXml toXml() {
        if (Objects.isNull(css)) {
            css = new PPTInnerTextCss();
        }

        StringBuilder sb = new StringBuilder();
        PPTElementXml pptElementXML = new PPTElementXml();
        aRGet(sb);
        pptElementXML.setSideXml(sb);
        return pptElementXML;
    }

    @Override
    public PPTInnerText clone() {
        PPTInnerText pptInnerText = new PPTInnerText();
        pptInnerText.setText(text);
        pptInnerText.setCss(css.clone());
        return pptInnerText;
    }

    private void aRGet(StringBuilder sb) {
        sb.append(TEMP_A_R_LEFT);
        sb.append(TEMP_A_RPR_LEFT_1);
        sb.append(css.getFontSize() * Constant.MULTIPLE_2);
        sb.append(TEMP_A_RPR_LEFT_2);
        if (css.isItalic()) {
            sb.append(TEMP_A_RPR_LEFT_I);
        }
        if (css.isBold()) {
            sb.append(TEMP_A_RPR_LEFT_B);
        }
        if (css.getSpacing() != 0) {
            sb.append(TEMP_A_RPR_LEFT_SPC_1);
            sb.append(css.getSpacing() * Constant.MULTIPLE_2);
            sb.append(TEMP_A_RPR_LEFT_SPC_2);
        }
        sb.append(TEMP_A_RPR_LEFT_3);
        XmlCodeUtil.aFontGet(css, sb);
        sb.append(TEMP_A_RPR_RIGHT);

        sb.append(TEMP_A_T_LEFT);
        //看了guava实现，虽然是@beta的但是看起来没啥问题，xml其实把<和&替换了就成
        sb.append(Objects.isNull(text) ? Constant.EMPTY : XmlEscapers.xmlAttributeEscaper().escape(text));
        sb.append(TEMP_A_T_RIGHT);

        sb.append(TEMP_A_R_RIGHT);
    }

    public PPTInnerText() {
    }

    public PPTInnerText(String text) {
        this.text = text;
    }

    public PPTInnerText(String text, PPTInnerTextCss pptInnerTextCss) {
        this.text = text;
        this.css = pptInnerTextCss;
    }

    public static PPTInnerText build() {
        return new PPTInnerText();
    }

    public static PPTInnerText build(String text) {
        return new PPTInnerText(text);
    }

    public static PPTInnerText build(String text, PPTInnerTextCss pptInnerTextCss) {
        return new PPTInnerText(text, pptInnerTextCss);
    }

    /**
     * 基本准确，不是绝对准确
     */
    public double toWidth() {
        TextCount textCount = TextUtil.findTextCount(text);
        double chineseSize = textCount.getChineseCount() * ONE_SIZE_WIDTH_SIZE * css.getFontSize();
        double otherSize = (textCount.getExceptChineseCount() * 1.0 * 0.78) * ONE_SIZE_WIDTH_SIZE * css.getFontSize();
        return (chineseSize + otherSize + ((textCount.getAllCount() - 1) * FONT_BLANK_SIZE)) * Constant.MULTIPLE_CM ;
    }

    /**
     * 获取粗略高度，并不保证完全准确
     */
    public double toHeight() {
        return css.getFontSize() * ONE_SIZE_HEIGHT_SIZE * Constant.MULTIPLE_CM;
    }

    public PPTInnerTextEnd toEnd() {
        PPTInnerTextEnd innerTextEnd = PPTInnerTextEnd.build();
        innerTextEnd.setCss(PPTInnerTextCss.build()
                .setFontSize(css.getFontSize())
                .setColor(css.getColor())
        );
        return innerTextEnd;
    }


}
