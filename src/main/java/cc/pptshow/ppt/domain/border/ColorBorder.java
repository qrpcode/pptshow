package cc.pptshow.ppt.domain.border;

import cc.pptshow.ppt.constant.Constant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ColorBorder extends Border {
    private String color = "000000";
    private double width = 1.0;

    private static final String LN_1 = "<a:ln w=\"";
    private static final String LN_2 = "\" cmpd=\"sng\"><a:solidFill><a:srgbClr val=\"";
    private static final String LN_3 = "\"/></a:solidFill><a:prstDash val=\"solid\"/></a:ln>";

    @Override
    public String aLnGet() {
        return LN_1 + Math.round(width * Constant.MULTIPLE_POUND) + LN_2 + color + LN_3;
    }

    @Override
    public ColorBorder clone() {
        return new ColorBorder().setColor(color).setWidth(width);
    }
}
