package cc.pptshow.ppt.domain.animation;

import lombok.Data;

/**
 * 所有动画公用的父类
 */
@Data
public abstract class Animation implements Cloneable {

    /**
     * 动画的次序，应该从0开始
     */
    private int index;

    public abstract int appendXml(int realId, int elementId, StringBuilder sb, int delayTime, ShowAnimationType type);

}
