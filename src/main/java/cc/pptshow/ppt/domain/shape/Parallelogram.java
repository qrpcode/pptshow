package cc.pptshow.ppt.domain.shape;

import cc.pptshow.ppt.constant.Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 平行四边形
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
public class Parallelogram extends Shape{

    /**
     * 百分比，最大100最小0
     */
    private double fillet;

    @Override
    public String prstGeomXmlGet() {
        if (fillet == 0) {
            //注意，这里是默认比例，不是没有斜边！
            return "<a:prstGeom prst=\"parallelogram\"><a:avLst/></a:prstGeom>";
        }
        return "<a:prstGeom prst=\"parallelogram\"><a:avLst><a:gd name=\"adj\" fmla=\"val " +
                Math.round(fillet * Constant.MULTIPLE_3)+ "\"/></a:avLst></a:prstGeom>";
    }

    @Override
    public Parallelogram clone() {
        return new Parallelogram(fillet);
    }
}
