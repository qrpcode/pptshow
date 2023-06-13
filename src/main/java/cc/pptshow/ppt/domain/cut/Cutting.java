package cc.pptshow.ppt.domain.cut;

import cc.pptshow.ppt.domain.cut.shape.RectCutShape;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@Accessors(chain = true)
public class Cutting implements Cloneable {
    /**
     * 剪切掉左侧部分的百分比 0-100
     */
    private double left;

    /**
     * 剪切掉顶侧部分的百分比 0-100
     */
    private double top;

    /**
     * 剪切掉右侧部分的百分比 0-100
     */
    private double right;

    /**
     * 剪切掉底侧部分的百分比 0-100
     */
    private double bottom;

    private CutShape cutShape = new RectCutShape();

    public static Cutting build() {
        return new Cutting();
    }

    public Cutting clone() {
        Cutting cutting = new Cutting();
        cutting.setBottom(bottom);
        cutting.setLeft(left);
        cutting.setTop(top);
        cutting.setRight(right);
        if (Objects.nonNull(cutShape)) {
            cutting.setCutShape(cutShape.clone());
        }
        return cutting;
    }

}
