package cc.pptshow.ppt.domain.animation;

import lombok.Data;

import java.util.Objects;

import static org.apache.xerces.util.XMLSymbols.EMPTY_STRING;

/**
 * 可以使用动画的标记类
 */
@Data
public class AnimationElement {

    private InAnimation inAnimation;

    private OutAnimation outAnimation;

}
