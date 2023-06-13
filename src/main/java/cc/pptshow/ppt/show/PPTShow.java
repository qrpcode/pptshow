package cc.pptshow.ppt.show;

import cc.pptshow.ppt.constant.Constant;
import cc.pptshow.ppt.domain.FileCopy;
import cc.pptshow.ppt.domain.PPTFont;
import cc.pptshow.ppt.domain.PPTShowSideXml;
import cc.pptshow.ppt.exception.PPTShowCreateException;
import cc.pptshow.ppt.util.FileUtil;
import cc.pptshow.ppt.util.XmlCodeUtil;
import cc.pptshow.ppt.util.ZipUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class PPTShow implements Closeable {

    @Getter
    List<PPTShowSide> sides = Lists.newArrayList();

    private final String uuid = FileUtil.uuid();
    private final String cachePath;

    private static final String Presentation_head_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<p:presentation xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" " +
            "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" " +
            "xmlns:p=\"http://schemas.openxmlformats.org/presentationml/2006/main\"><p:sldMasterIdLst>" +
            "<p:sldMasterId id=\"2147483648\" r:id=\"rId1\"/></p:sldMasterIdLst><p:sldIdLst>";
    private static final String Presentation_head_2 =  "</p:sldIdLst><p:sldSz cx=\"12192000\" cy=\"6858000\"/><p:notesSz cx=\"6858000\" cy=\"9144000\"/>";
    private static final String SIDE_REL_LEFT_1 = "<p:sldId id=\"";
    private static final String SIDE_REL_LEFT_2 = "\" r:id=\"rId";
    private static final String SIDE_REL_LEFT_3 = "\"/>";

    private static final String Presentation_end = "<p:defaultTextStyle><a:defPPr><a:defRPr lang=\"zh-CN\"/></a:defPPr><a:lvl1pPr marL=\"0\" " +
            "algn=\"l\" defTabSz=\"914400\" rtl=\"0\" eaLnBrk=\"1\" latinLnBrk=\"0\" hangingPunct=\"1\"><a:defRPr sz=\"1800\" " +
            "kern=\"1200\"><a:solidFill><a:schemeClr val=\"tx1\"/></a:solidFill><a:latin typeface=\"+mn-lt\"/><a:ea typeface=\"+mn-ea\"/>" +
            "<a:cs typeface=\"+mn-cs\"/></a:defRPr></a:lvl1pPr><a:lvl2pPr marL=\"457200\" algn=\"l\" defTabSz=\"914400\" " +
            "rtl=\"0\" eaLnBrk=\"1\" latinLnBrk=\"0\" hangingPunct=\"1\"><a:defRPr sz=\"1800\" kern=\"1200\"><a:solidFill>" +
            "<a:schemeClr val=\"tx1\"/></a:solidFill><a:latin typeface=\"+mn-lt\"/><a:ea typeface=\"+mn-ea\"/><a:cs " +
            "typeface=\"+mn-cs\"/></a:defRPr></a:lvl2pPr><a:lvl3pPr marL=\"914400\" algn=\"l\" defTabSz=\"914400\" rtl=\"0\" " +
            "eaLnBrk=\"1\" latinLnBrk=\"0\" hangingPunct=\"1\"><a:defRPr sz=\"1800\" kern=\"1200\"><a:solidFill><a:schemeClr " +
            "val=\"tx1\"/></a:solidFill><a:latin typeface=\"+mn-lt\"/><a:ea typeface=\"+mn-ea\"/><a:cs typeface=\"+mn-cs\"/>" +
            "</a:defRPr></a:lvl3pPr><a:lvl4pPr marL=\"1371600\" algn=\"l\" defTabSz=\"914400\" rtl=\"0\" eaLnBrk=\"1\" latinLnBrk=\"0\" " +
            "hangingPunct=\"1\"><a:defRPr sz=\"1800\" kern=\"1200\"><a:solidFill><a:schemeClr val=\"tx1\"/></a:solidFill><a:latin " +
            "typeface=\"+mn-lt\"/><a:ea typeface=\"+mn-ea\"/><a:cs typeface=\"+mn-cs\"/></a:defRPr></a:lvl4pPr><a:lvl5pPr marL=\"1828800\"" +
            " algn=\"l\" defTabSz=\"914400\" rtl=\"0\" eaLnBrk=\"1\" latinLnBrk=\"0\" hangingPunct=\"1\"><a:defRPr sz=\"1800\" kern=\"1200\">" +
            "<a:solidFill><a:schemeClr val=\"tx1\"/></a:solidFill><a:latin typeface=\"+mn-lt\"/><a:ea typeface=\"+mn-ea\"/>" +
            "<a:cs typeface=\"+mn-cs\"/></a:defRPr></a:lvl5pPr><a:lvl6pPr marL=\"2286000\" algn=\"l\" defTabSz=\"914400\" " +
            "rtl=\"0\" eaLnBrk=\"1\" latinLnBrk=\"0\" hangingPunct=\"1\"><a:defRPr sz=\"1800\" kern=\"1200\"><a:solidFill>" +
            "<a:schemeClr val=\"tx1\"/></a:solidFill><a:latin typeface=\"+mn-lt\"/><a:ea typeface=\"+mn-ea\"/><a:cs " +
            "typeface=\"+mn-cs\"/></a:defRPr></a:lvl6pPr><a:lvl7pPr marL=\"2743200\" algn=\"l\" defTabSz=\"914400\" rtl=\"0\" " +
            "eaLnBrk=\"1\" latinLnBrk=\"0\" hangingPunct=\"1\"><a:defRPr sz=\"1800\" kern=\"1200\"><a:solidFill><a:schemeClr " +
            "val=\"tx1\"/></a:solidFill><a:latin typeface=\"+mn-lt\"/><a:ea typeface=\"+mn-ea\"/><a:cs typeface=\"+mn-cs\"/>" +
            "</a:defRPr></a:lvl7pPr><a:lvl8pPr marL=\"3200400\" algn=\"l\" defTabSz=\"914400\" rtl=\"0\" eaLnBrk=\"1\" " +
            "latinLnBrk=\"0\" hangingPunct=\"1\"><a:defRPr sz=\"1800\" kern=\"1200\"><a:solidFill><a:schemeClr val=\"tx1\"/>" +
            "</a:solidFill><a:latin typeface=\"+mn-lt\"/><a:ea typeface=\"+mn-ea\"/><a:cs typeface=\"+mn-cs\"/></a:defRPr></a:" +
            "lvl8pPr><a:lvl9pPr marL=\"3657600\" algn=\"l\" defTabSz=\"914400\" rtl=\"0\" eaLnBrk=\"1\" latinLnBrk=\"0\" " +
            "hangingPunct=\"1\"><a:defRPr sz=\"1800\" kern=\"1200\"><a:solidFill><a:schemeClr val=\"tx1\"/></a:solidFill>" +
            "<a:latin typeface=\"+mn-lt\"/><a:ea typeface=\"+mn-ea\"/><a:cs typeface=\"+mn-cs\"/></a:defRPr></a:lvl9pPr>" +
            "</p:defaultTextStyle></p:presentation>";

    private static final String Relationships_Head = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">";

    private static final String Relationships_End_1 = "<Relationship Id=\"rId6\" Type=\"http://schemas.openxmlformats.org/" +
            "officeDocument/2006/relationships/tableStyles\" Target=\"tableStyles.xml\"/><Relationship Id=\"rId5\" " +
            "Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/viewProps\" Target=\"viewProps.xml\"/>" +
            "<Relationship Id=\"rId4\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/presProps\" " +
            "Target=\"presProps.xml\"/>";
    private static final String Relationships_End_2 = "<Relationship Id=\"rId2\" Type=\"http://schemas.openxml" +
            "formats.org/officeDocument/2006/relationships/theme\" Target=\"theme/theme1.xml\"/><Relationship Id=\"rId1\" " +
            "Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/slideMaster\" Target=\"slideMasters" +
            "/slideMaster1.xml\"/></Relationships>";

    private static final String FONT_LST_LEFT = "<p:embeddedFontLst>";
    private static final String FONT_LST_RIGHT = "</p:embeddedFontLst>";
    private static final String FONT_LEFT = "<p:embeddedFont>";
    private static final String FONT_RIGHT = "</p:embeddedFont>";
    private static final String FONT_TYPE_1 = "<p:font typeface=\"";
    private static final String FONT_TYPE_2 = "\" charset=\"-122\"/>";
    private static final String FONT_REGULAR_1 = "<p:regular r:id=\"rId";
    private static final String FONT_REGULAR_2 = "\"/>";
    private static final String FONT_FILE_1 = "font";
    private static final String FONT_FILE_2 = ".fntdata";

    private static final String APP_LEFT_1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Properties xmlns=\"http://schemas.openxmlformats.org/officeDocument/2006/extended-properties\" " +
            "xmlns:vt=\"http://schemas.openxmlformats.org/officeDocument/2006/docPropsVTypes\"><TotalTime>0</TotalTime>" +
            "<Words>0</Words><Application>PPTShow</Application><PresentationFormat>宽屏</PresentationFormat><Paragraphs>" +
            "0</Paragraphs><Slides>";
    private static final String APP_LEFT_2 = "</Slides><Notes>0</Notes><HiddenSlides>0</HiddenSlides><MMClips>0</MMClips>" +
            "<ScaleCrop>false</ScaleCrop><HeadingPairs><vt:vector size=\"6\" baseType=\"variant\"><vt:variant>" +
            "<vt:lpstr>已用的字体</vt:lpstr></vt:variant><vt:variant><vt:i4>6</vt:i4></vt:variant><vt:variant>" +
            "<vt:lpstr>主题</vt:lpstr></vt:variant><vt:variant><vt:i4>1</vt:i4></vt:variant><vt:variant>" +
            "<vt:lpstr>幻灯片标题</vt:lpstr></vt:variant><vt:variant><vt:i4>2</vt:i4></vt:variant></vt:vector>" +
            "</HeadingPairs><TitlesOfParts><vt:vector size=\"9\" baseType=\"lpstr\"><vt:lpstr>Arial</vt:lpstr><vt:lpstr>" +
            "宋体</vt:lpstr><vt:lpstr>Wingdings</vt:lpstr><vt:lpstr>Arial Unicode MS</vt:lpstr><vt:lpstr>Calibri</vt:lpstr>" +
            "<vt:lpstr>微软雅黑</vt:lpstr><vt:lpstr>Office 主题</vt:lpstr><vt:lpstr>PowerPoint 演示文稿</vt:lpstr>" +
            "<vt:lpstr>PowerPoint 演示文稿</vt:lpstr></vt:vector></TitlesOfParts><LinksUpToDate>false</LinksUpToDate>" +
            "<SharedDoc>false</SharedDoc><HyperlinksChanged>false</HyperlinksChanged><AppVersion>14.0000</AppVersion>" +
            "</Properties>";

    private static final String PPT_REL_SIDE_1 = "<Relationship Id=\"rId";
    private static final String PPT_REL_SIDE_2 = "\" Type=\"http://schemas.open" +
            "xmlformats.org/officeDocument/2006/relationships/slide\" Target=\"slides/slide";
    private static final String PPT_REL_SIDE_3 = ".xml\"/>";

    @Setter
    @Getter
    private List<PPTFont> fonts = Lists.newArrayList();

    public PPTShow() {
        File directory = new File(FileUtil.tmpdir() + Constant.BACKSLASH + uuid);
        boolean mkdirs = directory.mkdirs();
        if (!mkdirs) {
            throw new PPTShowCreateException("抱歉，PPTShow初始化临时目录失败！");
        }
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(Constant.DEFAULT_ZIP_SLASH);
        if (Objects.isNull(resourceAsStream)) {
            throw new PPTShowCreateException("抱歉，PPTShow获取系统默认组装文件失败！");
        }
        cachePath = directory.getPath();
        String zipCachePath = cachePath + Constant.SEPARATOR + Constant.CACHE_ZIP;
        FileUtil.traditionalCopyFromStream(resourceAsStream, zipCachePath);
        ZipUtil.zipUncompress(zipCachePath, cachePath);
        boolean delete = new File(zipCachePath).delete();
        if (!delete) {
            throw new PPTShowCreateException("抱歉，PPTShow删除拼装过程临时文件失败！");
        }
    }

    /**
     * 强迫症专用方法，其实和new没啥区别
     *
     * @return 返回一个新的pptShow对象
     */
    public static PPTShow build() {
        return new PPTShow();
    }

    public void generate() {
        String uuid = this.uuid;
    }

    /**
     * 插入一张PPT
     *
     * @param pptShowSide ppt页面对象
     */
    public void add(PPTShowSide pptShowSide) {
        sides.add(pptShowSide);
    }

    /**
     * 插入一张PPT
     *
     * @param pptShowSide      ppt页面对象
     * @param beforeThisSideId 这一页前一页的id
     */
    public void add(PPTShowSide pptShowSide, int beforeThisSideId) {
        sides.add(beforeThisSideId, pptShowSide);
    }

    public void toFile(String path) {
        File root = new File(cachePath + Constant.SEPARATOR +
                Constant.REL_PATH + Constant.SEPARATOR);
        root.mkdirs();

        //ppt内页
        int media = 1;
        for (int i = 1; i <= sides.size(); i++) {
            PPTShowSideXml pptShowSideXML = sides.get(i - 1).toXml(media);
            media += pptShowSideXML.getFile().size();
            FileUtil.write(cachePath + Constant.SEPARATOR +
                    Constant.SIDE_PATH + Constant.SEPARATOR +
                    Constant.SIDE_NAME_LEFT + i + Constant.SIDE_NAME_RIGHT, pptShowSideXML.getSide().toString());
            FileUtil.write(cachePath + Constant.SEPARATOR +
                    Constant.REL_PATH + Constant.SEPARATOR +
                    Constant.REL_NAME_LEFT + i + Constant.REL_NAME_RIGHT, pptShowSideXML.getRel().toString());
            new File(cachePath + Constant.SEPARATOR + Constant.MEDIA).mkdirs();
            for (FileCopy fileCopy : pptShowSideXML.getFile()) {
                FileUtil.traditionalCopy(fileCopy.getFrom(), cachePath + Constant.SEPARATOR + fileCopy.getTo());
            }
        }

        //字体打包
        buildFont();

        if (!path.endsWith(Constant.PPTX)) {
            path += Constant.PPTX;
        }

        //app.xml
        buildAppXml();

        ZipUtil.zipMultiFile(cachePath, path);
    }

    private void buildAppXml() {
        String app = APP_LEFT_1 + sides.size() + APP_LEFT_2;
        FileUtil.write(cachePath + Constant.SEPARATOR + Constant.APP_PATH, app);
    }

    private void buildFont() {
        int relBegin = 7;
        int pageBegin = 255;
        //创建目录
        new File(cachePath + Constant.SEPARATOR + Constant.FONT_PATH).mkdirs();
        //写入
        StringBuilder pre = new StringBuilder();
        StringBuilder rel = new StringBuilder();
        int relBeginId = 7;
        int fontBeginId = 1;

        pre.append(Presentation_head_1);
        for (int i = 1; i <= sides.size(); i++) {
            pre.append(SIDE_REL_LEFT_1);
            pre.append(pageBegin + i);
            pre.append(SIDE_REL_LEFT_2);
            pre.append(relBegin + i);
            pre.append(SIDE_REL_LEFT_3);
        }
        pre.append(Presentation_head_2);
        pre.append(FONT_LST_LEFT);
        rel.append(Relationships_Head);
        for (PPTFont font : fonts) {
            pre.append(FONT_LEFT);

            pre.append(FONT_TYPE_1);
            pre.append(font.getName());
            pre.append(FONT_TYPE_2);
            pre.append(FONT_REGULAR_1);
            pre.append(relBeginId);
            pre.append(FONT_REGULAR_2);

            pre.append(FONT_RIGHT);

            FileUtil.traditionalCopy(font.getFileUri(), cachePath + Constant.SEPARATOR +
                    Constant.FONT_PATH + Constant.SEPARATOR + FONT_FILE_1 + fontBeginId + FONT_FILE_2);

            rel.append(XmlCodeUtil.fontGet(relBeginId, fontBeginId));

            fontBeginId++;
            relBeginId++;
        }
        rel.append(Relationships_End_1);
        for (int i = 1; i <= sides.size(); i++) {
            rel.append(PPT_REL_SIDE_1);
            rel.append(relBegin + i);
            rel.append(PPT_REL_SIDE_2);
            rel.append(i);
            rel.append(PPT_REL_SIDE_3);
        }
        rel.append(Relationships_End_2);
        pre.append(FONT_LST_RIGHT);
        pre.append(Presentation_end);

        FileUtil.write(cachePath + Constant.SEPARATOR + Constant.PRESENTATION_PATH, pre.toString());
        FileUtil.write(cachePath + Constant.SEPARATOR + Constant.PRESENTATION_REL_PATH, rel.toString());
    }

    private boolean checkFont() {
        for (PPTFont font : fonts) {
            if (Strings.isNullOrEmpty(font.getFileUri()) || Strings.isNullOrEmpty(font.getName())) {
                return false;
            }
            if (!font.getFileUri().endsWith(Constant.FONT)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 手动关闭缓存
     */
    @Override
    public void close() throws IOException {
        cn.hutool.core.io.FileUtil.del(cachePath);
    }

    private StringBuilder getPresentation() {
        return null;
    }
}
