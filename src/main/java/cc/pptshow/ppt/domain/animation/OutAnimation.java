package cc.pptshow.ppt.domain.animation;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * PPT离开动画
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OutAnimation extends Animation {

    private ShowAnimationType showAnimationType = ShowAnimationType.SAME_TIME;

    private OutAnimationType outAnimationType;

    @Override
    public OutAnimation clone() {
        OutAnimation outAnimation = new OutAnimation();
        outAnimation.setShowAnimationType(showAnimationType);
        outAnimation.setOutAnimationType(outAnimationType);
        outAnimation.setIndex(getIndex());
        return outAnimation;
    }

    @Override
    public int appendXml(int realId, int elementId, StringBuilder sb, int delayTime, ShowAnimationType type) {
        return 0;
    }
}
