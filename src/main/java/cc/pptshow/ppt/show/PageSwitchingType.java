package cc.pptshow.ppt.show;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 具体效果参考WPS或者OFFICE，名字都是一样的
 */
@Getter
@AllArgsConstructor
public enum PageSwitchingType {
    DEFAULT("切出（默认效果）", PageSwitchingType.DEFAULT_XML),
    SMOOTH("平滑", PageSwitchingType.SMOOTH_XML),
    FADE_OUT("淡出", PageSwitchingType.FADE_OUT_XML),
    ERASE("擦除", PageSwitchingType.ERASE_XML),
    SHAPE("形状", PageSwitchingType.SHAPE_XML),
    DISSOLVE("溶解", PageSwitchingType.DISSOLVE_XML),
    NEWS_FLASH("新闻快报", PageSwitchingType.NEWS_FLASH_XML),
    SPOKES("轮辐", PageSwitchingType.SPOKES_XML),
    BLIND("百叶窗", PageSwitchingType.BLIND_XML),
    COMB("梳理", PageSwitchingType.COMB_XML),
    TAKING("抽出", PageSwitchingType.TAKING_XML),
    SEGMENTATION("分割", PageSwitchingType.SEGMENTATION_XML),
    LINE("线条", PageSwitchingType.LINE_XML),
    BOARD("棋盘", PageSwitchingType.BOARD_XML),
    LAUNCH("推出", PageSwitchingType.LAUNCH_XML),
    INSERT("插入", PageSwitchingType.INSERT_XML),
    PAGE_CURLING("页面卷曲", PageSwitchingType.PAGE_CURLING_XML),
    CUBE("立方体", PageSwitchingType.CUBE_XML),
    BOX("框", PageSwitchingType.BOX_XML),
    PLAIN("飞机", PageSwitchingType.PLAIN_XML),
    OPEN_DOOR("开门", PageSwitchingType.OPEN_DOOR_XML),
    STRIPPING("剥离", PageSwitchingType.STRIPPING_XML),
    RANDOM("随机", PageSwitchingType.RANDOM_XML)
    ;

    public static final String ADV_TM = "{{advTm}}";

    public static final String SMOOTH_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-compa" +
            "tibility/2006\" xmlns:p159=\"http://schemas.microsoft.com/office/powerpoint/2015/09/main\"><mc:Choice Requi" +
            "res=\"p159\"><p:transition xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" p14:dur=\"" +
            "500\"{{advTm}}><p159:morph option=\"byObject\"/></p:transition></mc:Choice><mc:Fallback><p:transition{{advTm}}>" +
            "<p:fade/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String FADE_OUT_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-compa" +
            "tibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" Requires=\"p14\">" +
            "<p:transition p14:dur=\"500\"{{advTm}}><p:fade/></p:transition></mc:Choice><mc:Fallback><p:transition{{advTm}}>" +
            "<p:fade/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String DEFAULT_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-comp" +
            "atibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\"><p:transition p14:dur=\"500\"{{advTm}}><p:cut/></p:transition></mc:Choice><mc:Fallback>" +
            "<p:transition{{advTm}}><p:cut/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String ERASE_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-compati" +
            "bility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" Requires=\"p14\">" +
            "<p:transition p14:dur=\"500\"{{advTm}}><p:wipe/></p:transition></mc:Choice><mc:Fallback><p:transition{{advTm}}>" +
            "<p:wipe/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String SHAPE_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\"><p:transition p14:dur=\"500\"{{advTm}}><p:wedge/></p:transition></mc:Choice><mc:Fallback>" +
            "<p:transition{{advTm}}><p:wedge/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String DISSOLVE_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\"><p:transition p14:dur=\"500\"{{advTm}}><p:dissolve/></p:transition></mc:Choice><mc:Fallback>" +
            "<p:transition{{advTm}}><p:dissolve/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String NEWS_FLASH_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\"><p:transition p14:dur=\"500\"{{advTm}}><p:newsflash/></p:transition></mc:Choice><mc:Fallback>" +
            "<p:transition{{advTm}}><p:newsflash/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String SPOKES_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\"><p:transition p14:dur=\"500\"{{advTm}}><p:wheel spokes=\"8\"/></p:transition></mc:Choice>" +
            "<mc:Fallback><p:transition{{advTm}}><p:wheel spokes=\"8\"/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String BLIND_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\"><p:transition p14:dur=\"500\"{{advTm}}><p:blinds/></p:transition></mc:Choice><mc:Fallback>" +
            "<p:transition{{advTm}}><p:blinds/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String COMB_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\"><p:transition p14:dur=\"500\"{{advTm}}><p:comb/></p:transition></mc:Choice>" +
            "<mc:Fallback><p:transition{{advTm}}><p:comb/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String TAKING_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\"><p:transition p14:dur=\"500\"{{advTm}}><p:pull/></p:transition></mc:Choice><mc:Fallback>" +
            "<p:transition{{advTm}}><p:pull/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String SEGMENTATION_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\"><p:transition p14:dur=\"500\"{{advTm}}><p:split orient=\"vert\" dir=\"in\"/>" +
            "</p:transition></mc:Choice><mc:Fallback><p:transition{{advTm}}><p:split orient=\"vert\" dir=\"in\"/>" +
            "</p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String LINE_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\"><p:transition p14:dur=\"500\"{{advTm}}><p:randomBar dir=\"vert\"/></p:transition>" +
            "</mc:Choice><mc:Fallback><p:transition{{advTm}}><p:randomBar dir=\"vert\"/></p:transition></mc:Fallback>" +
            "</mc:AlternateContent>";

    public static final String BOARD_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\"><p:transition p14:dur=\"500\"{{advTm}}><p:checker/></p:transition></mc:Choice><mc:Fallback>" +
            "<p:transition{{advTm}}><p:checker/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String LAUNCH_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\"><p:transition p14:dur=\"500\"{{advTm}}><p:push dir=\"u\"/></p:transition></mc:Choice>" +
            "<mc:Fallback><p:transition{{advTm}}><p:push dir=\"u\"/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String INSERT_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\"><p:transition p14:dur=\"500\"{{advTm}}><p:cover dir=\"d\"/></p:transition>" +
            "</mc:Choice><mc:Fallback><p:transition{{advTm}}><p:cover dir=\"d\"/></p:transition></mc:Fallback>" +
            "</mc:AlternateContent>";

    public static final String PAGE_CURLING_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p15=\"http://schemas.microsoft.com/office/powerpoint/2012/main\" " +
            "Requires=\"p15\"><p:transition xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\" p14:dur=\"500\"{{advTm}}><p15:prstTrans prst=\"pageCurlDouble\"/></p:transition>" +
            "</mc:Choice><mc:Fallback><p:transition{{advTm}}><p:fade/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String CUBE_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\"><p:transition p14:dur=\"500\"{{advTm}}><p14:prism/></p:transition></mc:Choice>" +
            "<mc:Fallback><p:transition{{advTm}}><p:fade/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String BOX_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\"><p:transition p14:dur=\"500\"{{advTm}}><p14:prism isInverted=\"1\"/></p:transition>" +
            "</mc:Choice><mc:Fallback><p:transition{{advTm}}><p:fade/></p:transition></mc:Fallback>" +
            "</mc:AlternateContent>";

    public static final String PLAIN_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p15=\"http://schemas.microsoft.com/office/powerpoint/2012/main\" " +
            "Requires=\"p15\"><p:transition xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\" p14:dur=\"500\"{{advTm}}><p15:prstTrans prst=\"airplane\"/></p:transition></mc:Choice>" +
            "<mc:Fallback><p:transition{{advTm}}><p:fade/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String OPEN_DOOR_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\"><p:transition p14:dur=\"500\"{{advTm}}><p14:doors dir=\"vert\"/></p:transition></mc:Choice>" +
            "<mc:Fallback><p:transition{{advTm}}><p:fade/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String STRIPPING_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p15=\"http://schemas.microsoft.com/office/powerpoint/2012/main\" " +
            "Requires=\"p15\"><p:transition xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\" p14:dur=\"500\"{{advTm}}><p15:prstTrans prst=\"peelOff\"/></p:transition></mc:Choice>" +
            "<mc:Fallback><p:transition{{advTm}}><p:fade/></p:transition></mc:Fallback></mc:AlternateContent>";

    public static final String RANDOM_XML = "<mc:AlternateContent xmlns:mc=\"http://schemas.openxmlformats.org/markup-" +
            "compatibility/2006\"><mc:Choice xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" " +
            "Requires=\"p14\"><p:transition p14:dur=\"500\"{{advTm}}><p:random/></p:transition></mc:Choice><mc:Fallback>" +
            "<p:transition{{advTm}}><p:random/></p:transition></mc:Fallback></mc:AlternateContent>";



    private final String name;
    private final String xml;

}
