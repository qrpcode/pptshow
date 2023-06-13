package cc.pptshow.ppt.domain.cut.shape;

import cc.pptshow.ppt.constant.Constant;
import cc.pptshow.ppt.domain.cut.CutShape;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class RoundRectCutShape extends CutShape {
    /**
     * 圆角比例，最小值是0最大值是50
     * 因为一个叫弯曲50%长度了，两个角加一块就已经100%了
     */
    private double fillet;

    @Override
    public String getCode() {
        return "roundRect";
    }

    @Override
    public String getXml() {
        return "<a:avLst><a:gd name=\"adj\" fmla=\"val " + Math.round(fillet * Constant.MULTIPLE_3) + "\"/></a:avLst>";
    }

    @Override
    public RoundRectCutShape clone() {
        return new RoundRectCutShape(fillet);
    }
}
