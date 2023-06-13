package cc.pptshow.ppt.constant;

import java.io.File;

public class Constant {

    public static final String EMPTY = "";
    public static final String LINE = "-";
    public static final String BACKSLASH = "\\";
    public static final String SLASH = "/";
    public static final String DOT = ".";
    public static final String NEW_LINE = "\n";

    public static final String SEPARATOR = File.separator;

    /**
     * 默认缓存文件路径
     * 注意这里如果使用系统 SEPARATOR 可能导致错报错
     */
    public static final String DEFAULT_ZIP_SLASH = "file" + SLASH + "default.zip";
    public static final String CACHE_ZIP = "show.zip";
    public static final String PATH = "pptShow";
    public static final String PPTX = ".pptx";
    public static final String SVG = ".svg";
    public static final String PNG = ".png";
    public static final String FONT = ".fntdata";
    public static final String MEDIA = "ppt" + SEPARATOR + "media" + SEPARATOR;
    public static final String MEDIA_NAME = "media";
    public static final String IMAGE_NAME = "image";
    public static final String SUFFIX_POINT = ".";

    public static final String SIDE_PATH = "ppt" + SEPARATOR + "slides";
    public static final String FONT_PATH = "ppt" + SEPARATOR + "fonts";
    public static final String APP_PATH = "docProps" + SEPARATOR + "app.xml";
    public static final String PRESENTATION_PATH = "ppt" + SEPARATOR + "presentation.xml";
    public static final String REL_PATH = SIDE_PATH + SEPARATOR + "_rels";
    public static final String PRESENTATION_REL_PATH = "ppt" + SEPARATOR + "_rels" + SEPARATOR + "presentation.xml.rels";
    public static final String SIDE_NAME_LEFT = "slide";
    public static final String SIDE_NAME_RIGHT = ".xml";
    public static final String REL_NAME_LEFT = "slide";
    public static final String REL_NAME_RIGHT = ".xml.rels";

    public static final String TRUE = "true";
    public static final String FALSE = "false";

    /**
     * 如果用户没有定义宽度，文字的默认宽度将是文字自身长度的多少倍
     */
    public static final double DEFAULT_X_MULTIPLE = 1.5;
    public static final double DEFAULT_Y_MULTIPLE = 1.7;

    /**
     * 文本框左右两侧不是顶住的，包含一部分空白尺寸
     */
    public static final double ONE_LINE_BOTH_SLIDES = 0.402;
    /**
     * 一个单位宽度的汉字占用宽度
     */
    public static final double ONE_SIZE_WIDTH_SIZE = 0.03652569;
    /**
     * 字符间隔为12磅
     */
    public static final double FONT_BLANK_SIZE = 0.03527;
    /**
     * 文本框上下两侧不是正好的，包含一部分空白尺寸
     */
    public static final double PPT_TEXT_TOP_BOTTOM_SLIDES = 0.26;
    /**
     * 一个单位宽度的汉字占用高度
     */
    public static final double ONE_SIZE_HEIGHT_SIZE = 0.0425;

    public static final long MULTIPLE_5 = 100000L;
    public static final long MULTIPLE_4 = 10000L;
    public static final long MULTIPLE_3 = 1000L;
    public static final long MULTIPLE_2 = 100L;
    public static final int MULTIPLE_INT_5 = 100000;
    public static final int MULTIPLE_INT_4 = 10000;
    public static final int MULTIPLE_INT_3 = 1000;
    public static final int MULTIPLE_INT_2 = 100;
    public static final long MULTIPLE_CM = 360000;
    public static final long MULTIPLE_POUND = 12700;
    public static final long ANGLE = 60000;
    public static final int ANGLE_MIN = 0;
    public static final int ANGLE_MAX = 360;

    public static final double DOUBLE_ZERO = 0.0;

    public static final String XMLNS = "xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\"";

    public static final String MUSIC_ICON_PATH = "D:\\ppt\\image1.png";

}
