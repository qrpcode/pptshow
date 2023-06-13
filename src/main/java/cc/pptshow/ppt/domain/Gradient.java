package cc.pptshow.ppt.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Objects;

@Data
@Accessors(chain = true)
public class Gradient implements Cloneable, Serializable {

    private String color;

    /**
     * 占比位置 1-100
     */
    private double proportion;

    /**
     * 透明度 1-100
     */
    private Double alpha;

    /**
     * 亮度 1-100
     */
    private Double lum;

    public Gradient setAlpha(Double alpha) {
        if (Objects.nonNull(alpha) && alpha > 0 && alpha <= 100) {
            this.alpha = alpha;
        }
        return this;
    }

    public static Gradient build() {
        return new Gradient();
    }

    @Override
    public Gradient clone() {
        return new Gradient().setAlpha(alpha).setColor(color).setLum(lum).setProportion(proportion);
    }

}
