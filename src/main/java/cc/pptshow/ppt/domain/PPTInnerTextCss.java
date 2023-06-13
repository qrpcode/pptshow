package cc.pptshow.ppt.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PPTInnerTextCss implements Cloneable {
    /**
     * 字体大小，和MSOffice、WPS等现实的大小数值相同比例
     */
    private int fontSize = 18;
    /**
     * 字符间距，单位：磅
     */
    private double spacing = 0;
    private String fontFamily = "宋体";
    private String color = "000000";
    private boolean italic;
    private boolean bold;
    private boolean textDecoration;
    private String textDecorationColor;

    public static PPTInnerTextCss build() {
        return new PPTInnerTextCss();
    }

    public PPTInnerTextCss clone() {
        PPTInnerTextCss pptInnerTextCss = new PPTInnerTextCss();
        pptInnerTextCss.setFontSize(fontSize);
        pptInnerTextCss.setSpacing(spacing);
        pptInnerTextCss.setFontFamily(fontFamily);
        pptInnerTextCss.setColor(color);
        pptInnerTextCss.setItalic(italic);
        pptInnerTextCss.setBold(bold);
        pptInnerTextCss.setTextDecoration(textDecoration);
        pptInnerTextCss.setTextDecorationColor(textDecorationColor);
        return pptInnerTextCss;
    }

}
