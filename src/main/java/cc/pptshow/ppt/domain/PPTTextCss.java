package cc.pptshow.ppt.domain;

import cc.pptshow.ppt.domain.background.Background;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(chain = true)
public class PPTTextCss extends LocationCss implements Cloneable {
    private double left;
    private double top;
    private double width;
    private double height;

    private double lineHeight;
    private Background background;
    /**
     * 0 <= 整体旋转角度 < 360
     */
    private double angle;

    private String name = "文本";

    public static PPTTextCss build() {
        return new PPTTextCss();
    }

    @Override
    public PPTTextCss clone() {
        PPTTextCss pptTextCss = new PPTTextCss();
        pptTextCss.setLeft(getLeft());
        pptTextCss.setTop(getTop());
        pptTextCss.setWidth(getWidth());
        pptTextCss.setHeight(getHeight());
        pptTextCss.setLineHeight(lineHeight);
        if (Objects.nonNull(background)) {
            pptTextCss.setBackground(background.clone());
        }
        return pptTextCss;
    }

}
