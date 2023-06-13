package cc.pptshow.ppt.domain.animation;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 属性信息
 */
@Getter
@AllArgsConstructor
public enum AnimationAttribute {
    BOTTOM("自底部", "down","4"),
    TOP("自顶部", "up","1"),
    LEFT("自左侧", "left","8"),
    RIGHT("自右侧", "right", "2"),

    LEFT_TOP("自左上侧", "upLeft", "9"),
    LEFT_BOTTOM("自左下侧", "downLeft", "12"),
    RIGHT_TOP("自右上侧", "upRight", "3"),
    RIGHT_BOTTOM("自右下侧", "downRight", "6"),

    IN("向内部", "in", "16"),
    OUT("向外部", "out", "32"),

    NUM_1("1轮辐", "1", "1"),
    NUM_2("2轮辐", "2", "2"),
    NUM_3("3轮辐", "3", "3"),
    NUM_4("4轮辐", "4", "4"),
    NUM_8("8轮辐", "8", "8"),

    IN_VERTICAL("左右向中央收缩", "inVertical", "21"),
    IN_HORIZONTAL("上下向中央收缩", "inHorizontal", "26"),
    OUT_VERTICAL("中央向左右展开", "outVertical", "37"),
    OUT_HORIZONTAL("中央向上下展开", "outHorizontal", "42"),

    ACROSS("跨越", "across", "10"),

    HORIZONTAL("水平", "horizontal", "10"),
    VERTICAL("垂直", "vertical", "5"),

    ;

    private final String name;

    private final String enName;

    private final String code;

}
