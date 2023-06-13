package cc.pptshow.ppt.domain;

import cc.pptshow.ppt.domain.border.Border;
import cc.pptshow.ppt.domain.cut.Cutting;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(chain = true)
public class PPTImgCss extends LocationCss implements Cloneable{
    private double left;
    private double top;
    private double width;
    private double height;

    private Cutting cutting = new Cutting();
    private Border border;
    private Shadow shadow;
    private String name = "图片";
    private String describe = "image";
    private double angle;
    /**
     * 只针对非png图片
     */
    private String removeColor;

    public static PPTImgCss build() {
        return new PPTImgCss();
    }

    @Override
    public PPTImgCss clone() {
        PPTImgCss pptImgCss = new PPTImgCss();
        pptImgCss.setLeft(getLeft());
        pptImgCss.setTop(getTop());
        pptImgCss.setWidth(getWidth());
        pptImgCss.setHeight(getHeight());
        pptImgCss.setAngle(angle);
        if (Objects.nonNull(cutting)) {
            pptImgCss.setCutting(cutting.clone());
        }
        return pptImgCss;
    }

}
