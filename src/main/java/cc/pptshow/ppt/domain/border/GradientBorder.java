package cc.pptshow.ppt.domain.border;

import cc.pptshow.ppt.constant.Constant;
import cc.pptshow.ppt.domain.Gradient;
import cc.pptshow.ppt.util.XmlCodeUtil;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GradientBorder extends Border{

    private List<Gradient> gradients = Lists.newArrayList();
    /**
     * 宽度，单位：磅
     */
    private double width = 1.0;
    /**
     * 0 - 360
     */
    private double gradientDirection = 90;

    private static final String LN_1 = "<a:ln w=\"";
    private static final String LN_2 = "\" cmpd=\"sng\">";
    private static final String LN_3 = "<a:prstDash val=\"solid\"/></a:ln>";

    @Override
    public String aLnGet() {
        if (CollectionUtils.isEmpty(gradients)) {
            return Constant.EMPTY;
        }
        return LN_1 + Math.round(width * Constant.MULTIPLE_POUND) + LN_2 +
                XmlCodeUtil.gradientGet(gradients, gradientDirection) +
                LN_3;
    }

    @Override
    public Border clone() {
        List<Gradient> cloneGradients = gradients.stream().map(g -> g.clone()).collect(Collectors.toList());
        return new GradientBorder().setGradientDirection(gradientDirection).setWidth(width).setGradients(cloneGradients);
    }
}
