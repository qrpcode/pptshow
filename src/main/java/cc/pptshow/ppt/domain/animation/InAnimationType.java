package cc.pptshow.ppt.domain.animation;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InAnimationType {
    BLINDS("百叶窗", 500),
    WIPE("擦除", 500),
    APPEAR("出现", 1),
    FLY_INTO("飞入", 500),
    BOX("盒状", 2000),
    SLOW_ENTRY("缓慢进入", 5000),
    LADDER("阶梯状", 500),
    DIAMOND("菱形", 2000),
    WHEEL("轮子", 2000),
    SPLITTING("劈裂", 500),
    BOARD("棋盘", 500),
    CUT("切入", 500),
    FLASH("闪烁一次", 500),
    SECTOR("扇形展开", 2000),
    EXPANSION("十字形拓展", 2000),
    LINE("随机线条", 500),
    DISSOLVE("向内溶解", 500),
    CIRCULAR("圆形拓展", 2000),
    GRADIENT("渐变", 500),
    CYCLOTRON_GRADIENT("渐变式回旋", 2000),
    ZOOM_GRADIENT("渐变式缩放", 500),
    UNFOLD("展开", 1000),
    FLIP("翻转式由远及近", 1000),
    CYCLOTRON("回旋", 500),
    GRADUALLY_INTO("渐入", 1000),
    RISING_UP("上升", 1000),
    STRETCH("伸展", 500),
    RISING("升起", 1000),
    ZOOM("缩放", 500),
    FALLING("下降", 1000),
    COMPRESSION("压缩", 1000),
    COLOR_TYPEWRITER("颜色打字机", 80),
    SPREAD("展开", 1000),
    //ROTATION_CENTER("中心旋转", 1000),
    //BOUNCE("弹跳", 2000),
    //AMPLIFICATION("放大", 2000),
    //WHIRL("飞旋", 1000),
    FLOATING("浮动", 1000),
//    LIGHT("光速", 1000),
//    GLIDING("滑翔", 500),
//    WIELD_WHIPS("挥鞭式", 500),
//    WAVING("挥鞭式", 1000),
//    OUR_COUNTRY("空翻", 1000),
//    SPIRAL_ENTRY("螺旋飞入", 1000),
//    CURVE_UP("曲线向上", 1000),
//    THROWING("投掷", 1000),
//    WINDMILL("玩具风车", 2000),
//    LINEAR("线形", 500),
//    ROTATING("旋转", 5000),
//    FOLDING("折叠", 500),
//    SUBTITLES("字幕式", 15000),
    ;
    private final String name;

    private final int defaultTime;

}


























