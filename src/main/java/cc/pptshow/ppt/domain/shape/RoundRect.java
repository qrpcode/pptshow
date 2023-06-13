package cc.pptshow.ppt.domain.shape;

import cc.pptshow.ppt.constant.Constant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class RoundRect extends Shape{
    /**
     * 圆角比例，最小值是0最大值是50
     * 因为一个叫弯曲50%长度了，两个角加一块就已经100%了
     */
    private double fillet;

    @Override
    public String prstGeomXmlGet() {
        if (fillet == 0) {
            //注意，这里是默认圆角比例，不是没有圆角！
            return "<a:prstGeom prst=\"roundRect\"><a:avLst/></a:prstGeom>";
        }
        return "<a:prstGeom prst=\"roundRect\"><a:avLst><a:gd name=\"adj\" fmla=\"val " +
                Math.round(fillet * Constant.MULTIPLE_3)+ "\"/></a:avLst></a:prstGeom>";
    }

    @Override
    public RoundRect clone() {
        return new RoundRect().setFillet(fillet);
    }
}
