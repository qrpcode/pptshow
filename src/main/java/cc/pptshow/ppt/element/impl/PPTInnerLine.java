package cc.pptshow.ppt.element.impl;

import cc.pptshow.ppt.constant.Constant;
import cc.pptshow.ppt.domain.PPTElementXml;
import cc.pptshow.ppt.domain.PPTInnerLineCss;
import cc.pptshow.ppt.element.PPTInnerElement;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * PPT文本的段落
 *
 * PPT每一行都是一个PPTInnerLine
 * 它的外层是PPTText
 * 它的内层是PPTInnerText
 */
public class PPTInnerLine implements PPTInnerElement {

    @Setter
    @Getter
    private List<PPTInnerText> textList = Lists.newArrayList();

    @Setter
    @Getter
    private PPTInnerTextEnd end = null;

    @Setter
    @Getter
    PPTInnerLineCss css = new PPTInnerLineCss();

    /**
     * 是否是空行
     * 如果您设置了这一项，除了【行距】之外，其他所有设置都会【自动忽略】
     */
    @Setter
    @Getter
    private boolean empty = false;

    /**
     * 空几行
     * 只有在空行的时候才生效，这几行都会用一样的格式，不一样格式请分别创建添加
     */
    @Setter
    @Getter
    private int emptyLine = 1;

    private static final String TEMP_A_P_LEFT = "<a:p>";
    private static final String TEMP_A_P_RIGHT = "</a:p>";
    private static final String TEMP_A_PRP_LEFT_1 = "<a:pPr algn=\"";
    private static final String TEMP_A_PRP_LEFT_2 = "\">";
    private static final String TEMP_A_PRP_LEFT_ONLY_2 = "\"/>";
    private static final String TEMP_A_PRP_RIGHT = "</a:pPr>";
    private static final String TEMP_A_LN_SPC_LEFT = "<a:lnSpc>";
    private static final String TEMP_A_LN_SPC_RIGHT = "</a:lnSpc>";
    private static final String TEMP_A_SPC_PCT_1 = "<a:spcPct val=\"";
    private static final String TEMP_A_SPC_PCT_2 = "\"/>";

    @Override
    public PPTElementXml toXml() {
        if (Objects.isNull(css)) {
            css = new PPTInnerLineCss();
        }

        StringBuilder sb = new StringBuilder();
        PPTElementXml pptElementXML = new PPTElementXml();

        if (Objects.isNull(end)) {
            end = CollectionUtils.isNotEmpty(textList) ?
                    textList.get(textList.size() - 1).toEnd() : new PPTInnerTextEnd();
        }

        if (empty) {
            for (int i = 0; i < emptyLine; i++) {
                aPGet(sb);
            }
        } else {
            aPGet(sb);
            pptElementXML.setSideXml(sb);
        }

        return pptElementXML;
    }

    @Override
    public PPTInnerLine clone() {
        List<PPTInnerText> texts = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(textList)) {
            texts = textList.stream().map(PPTInnerText::clone).collect(Collectors.toList());
        }
        PPTInnerLine pptInnerLine = new PPTInnerLine();
        pptInnerLine.setEmpty(empty);
        pptInnerLine.setCss(css.clone());
        pptInnerLine.addAll(texts);
        return pptInnerLine;
    }

    private void aPGet(StringBuilder sb) {
        sb.append(TEMP_A_P_LEFT);
        sb.append(TEMP_A_PRP_LEFT_1);
        sb.append(css.getAlign());
        if (css.getLineHeight() != 0L) {
            sb.append(TEMP_A_PRP_LEFT_2);
            sb.append(TEMP_A_LN_SPC_LEFT);
            sb.append(TEMP_A_SPC_PCT_1);
            sb.append((int) (css.getLineHeight() * Constant.MULTIPLE_5));
            sb.append(TEMP_A_SPC_PCT_2);
            sb.append(TEMP_A_LN_SPC_RIGHT);
            sb.append(TEMP_A_PRP_RIGHT);
        } else {
            sb.append(TEMP_A_PRP_LEFT_ONLY_2);
        }
        if (!empty) {
            for (PPTInnerText pptInnerText : textList) {
                sb.append(pptInnerText.toXml().getSideXml());
            }
        }
        sb.append(end.toXml().getSideXml());
        sb.append(TEMP_A_P_RIGHT);
    }

    public PPTInnerLine() {

    }

    public PPTInnerLine(PPTInnerText text) {
        this.textList = Lists.newArrayList(text);
    }

    public PPTInnerLine(List<PPTInnerText> textList) {
        this.textList = textList;
    }

    public PPTInnerLine(PPTInnerText text, PPTInnerLineCss css) {
        this.textList = Lists.newArrayList(text);
        this.css = css;
    }

    public PPTInnerLine(List<PPTInnerText> textList, PPTInnerLineCss css) {
        this.textList = textList;
        this.css = css;
    }

    public static PPTInnerLine build() {
        return new PPTInnerLine();
    }

    public static PPTInnerLine build(PPTInnerText text) {
        return new PPTInnerLine(text);
    }

    public static PPTInnerLine build(List<PPTInnerText> textList) {
        return new PPTInnerLine(textList);
    }

    public static PPTInnerLine build(PPTInnerText text, PPTInnerLineCss css) {
        return new PPTInnerLine(text, css);
    }

    public static PPTInnerLine build(List<PPTInnerText> textList, PPTInnerLineCss css) {
        return new PPTInnerLine(textList, css);
    }

    public static PPTInnerLine buildEmptyLine() {
        PPTInnerLine pptInnerLine = new PPTInnerLine();
        pptInnerLine.setEmpty(true);
        return pptInnerLine;
    }

    public static PPTInnerLine buildEmptyLine(int num) {
        PPTInnerLine pptInnerLine = new PPTInnerLine();
        pptInnerLine.setEmpty(true);
        pptInnerLine.setEmptyLine(num);
        return pptInnerLine;
    }

    public PPTInnerLine addAll(Collection<PPTInnerText> textList) {
        this.textList.addAll(textList);
        return this;
    }

    public PPTInnerLine add(PPTInnerText text) {
        this.textList.add(text);
        return this;
    }

    public PPTInnerLine add(PPTInnerText text, int beforeThisElementId) {
        this.textList.add(beforeThisElementId, text);
        return this;
    }

    public PPTInnerLine end(PPTInnerTextEnd pptInnerTextEnd) {
        this.end = pptInnerTextEnd;
        return this;
    }

    public double toWidth() {
        if (this.empty) {
            return Constant.MULTIPLE_CM;
        }
        double width = 0;
        for (PPTInnerText pptInnerText : textList) {
            width += pptInnerText.toWidth();
        }
        return width + (Constant.ONE_LINE_BOTH_SLIDES * Constant.MULTIPLE_CM);
    }

    public double toHeight() {
        if (this.empty) {
            return Constant.MULTIPLE_CM;
        }
        double height = 0;
        for (PPTInnerText pptInnerText : textList) {
            height = Math.max(pptInnerText.toHeight(), height);
        }
        double lineHeight = css.getLineHeight();
        return height * lineHeight;
    }
}
