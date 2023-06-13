package cc.pptshow.ppt.domain;

import cc.pptshow.ppt.constant.PPTNameConstant;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class PPTInnerLineCss implements Cloneable {
    private double lineHeight = 1.0;
    private String align = PPTNameConstant.ALIGN_LEFT;

    private static final List<String> ALIGNS =
            Lists.newArrayList(PPTNameConstant.ALIGN_LEFT, PPTNameConstant.ALIGN_RIGHT,
                    PPTNameConstant.ALIGN_CENTER, PPTNameConstant.ALIGN_DIST, PPTNameConstant.ALIGN_JUST);

    public PPTInnerLineCss setAlign(String align) {
        if (ALIGNS.contains(align)) {
            this.align = align;
        }
        return this;
    }

    public static PPTInnerLineCss build() {
        return new PPTInnerLineCss();
    }

    @Override
    public PPTInnerLineCss clone() {
        PPTInnerLineCss pptInnerLineCss = new PPTInnerLineCss();
        pptInnerLineCss.setLineHeight(lineHeight);
        pptInnerLineCss.setAlign(align);
        return pptInnerLineCss;
    }

}
