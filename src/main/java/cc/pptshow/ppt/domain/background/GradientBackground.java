package cc.pptshow.ppt.domain.background;

import cc.pptshow.ppt.domain.Gradient;
import cc.pptshow.ppt.domain.PPTElementXml;
import cc.pptshow.ppt.util.XmlCodeUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.compress.utils.Lists;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GradientBackground extends Background {

    private List<Gradient> gradient;

    /**
     * 0~360 度
     */
    private Double gradientDirection;

    @Override
    public PPTElementXml solidFillGet(int mediaId, int relId) {
        PPTElementXml xml = new PPTElementXml();
        xml.setSideXml(XmlCodeUtil.gradientGet(gradient, gradientDirection));
        return xml;
    }

    @Override
    public GradientBackground clone() {
        List<Gradient> clone = Lists.newArrayList();
        if (Objects.nonNull(gradient)) {
            clone = gradient.stream().map(Gradient::clone).collect(Collectors.toList());
        }
        return new GradientBackground().setGradient(clone).setGradientDirection(gradientDirection);
    }


}
