package cc.pptshow.ppt.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Shadow {
    /**
     * 颜色，默认黑色
     */
    private String color = "000000";

    /**
     * 透明度 1-100
     */
    private double alpha = 0;

    /**
     * 横向大小，单位：百分比
     */
    private double sizeX = 100;

    /**
     * 纵向大小，单位：百分比
     */
    private double sizeY = 100;

    /**
     * 距离，单位：磅
     */
    private double distance = 5;

    /**
     * 模糊，单位：磅
     */
    private double blur = 10;

    /**
     * 角度，0 <= angle < 360
     */
    private double angle = 0;

    public static Shadow build() {
        return new Shadow();
    }

}
