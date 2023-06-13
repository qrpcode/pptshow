package cc.pptshow.ppt.domain.animation;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 如果一组index中包含一个CLICK那么整个组都会和这个点击事件的时候同时出现
 */
@Getter
@AllArgsConstructor
public enum ShowAnimationType {
    CLICK("click", "点击触发"),
    AFTER("after", "之后出现"),
    SAME_TIME("sameTime", "同时出现")

    ;
    private final String code;
    private final String name;
}
