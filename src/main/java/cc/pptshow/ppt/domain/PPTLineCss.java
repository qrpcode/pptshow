package cc.pptshow.ppt.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PPTLineCss extends LocationCss implements Cloneable {
    private double left;
    private double top;

    private String color = "333333";

    private double lineWidth = 0.5;

    private LineType type = LineType.LINE;

    /**
     * 如果是竖线请将本项目置0
     */
    private double width = 0;

    /**
     * 如果是横线请将本项目置0
     */
    private double height = 0;

    @Override
    public PPTLineCss clone() {
        PPTLineCss pptLineCss = new PPTLineCss()
                .setColor(color)
                .setLineWidth(width)
                .setType(type)
                .setWidth(width)
                .setHeight(height);
        pptLineCss.setLeft(getLeft());
        pptLineCss.setTop(getTop());
        return pptLineCss;
    }

    @Getter
    @AllArgsConstructor
    public enum LineType {
        LINE("line"),
        TOP_RIGHT_BOTTOM_LEFT("1");

        private final String code;

    }

}
