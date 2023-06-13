package cc.pptshow.ppt.domain;

import cc.pptshow.ppt.domain.background.Background;
import cc.pptshow.ppt.domain.border.Border;
import cc.pptshow.ppt.domain.shape.Rect;
import cc.pptshow.ppt.domain.shape.Shape;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(chain = true)
public class PPTShapeCss extends LocationCss implements Cloneable {
    private double left;
    private double top;
    private double width;
    private double height;

    /**
     * 图形，默认是矩形
     */
    private Shape shape = new Rect();

    private Background background;

    private Border border = null;
    /**
     * 0 <= 整体旋转角度 < 360
     */
    private double angle;

    private String name = "图形";

    /**
     * 是否进行水平翻转
     */
    private boolean flipX = false;

    /**
     * 是否进行垂直翻转
     */
    private boolean flipY = false;

    public static PPTShapeCss build() {
        return new PPTShapeCss();
    }

    @Override
    public PPTShapeCss clone() {
        PPTShapeCss pptShapeCss = new PPTShapeCss();
        pptShapeCss.setLeft(getLeft());
        pptShapeCss.setTop(getTop());
        pptShapeCss.setWidth(getWidth());
        pptShapeCss.setHeight(getHeight());
        pptShapeCss.setAngle(angle);
        pptShapeCss.setName(name);
        pptShapeCss.setFlipX(flipX);
        pptShapeCss.setFlipY(flipY);
        if (Objects.nonNull(background)) {
            pptShapeCss.setBackground(background.clone());
        }
        if (Objects.nonNull(border)) {
            pptShapeCss.setBorder(border.clone());
        }
        if (Objects.nonNull(shape)) {
            pptShapeCss.setShape(shape.clone());
        }
        return pptShapeCss;
    }

}
