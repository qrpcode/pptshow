package cc.pptshow.ppt.show;

import cc.pptshow.ppt.constant.Constant;
import cc.pptshow.ppt.domain.FileCopy;
import cc.pptshow.ppt.domain.PPTElementXml;
import cc.pptshow.ppt.domain.PPTShowSideXml;
import cc.pptshow.ppt.domain.PPTSideCss;
import cc.pptshow.ppt.domain.animation.AnimationVo;
import cc.pptshow.ppt.domain.animation.ShowAnimationType;
import cc.pptshow.ppt.element.PPTElement;
import cn.hutool.core.io.FileUtil;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.util.Strings;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.xerces.util.XMLSymbols.EMPTY_STRING;

/**
 * ppt里面的一个页面
 */
@Data
public class PPTShowSide {

    @Getter
    @Setter
    private PPTSideCss css = new PPTSideCss();

    /**
     * 自动换页面的时间
     * 单位毫秒，默认为0表示不会自动换页
     */
    @Getter
    @Setter
    private long autoPagerTime = 0L;

    /**
     * 背景音乐文件
     */
    @Getter
    @Setter
    private String backgroundMusic;

    private PageSwitchingType pageSwitchingType = PageSwitchingType.DEFAULT;

    private final List<PPTElement> elements = Lists.newArrayList();

    private static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n";
    private static final String REL_HEAD = XML_HEAD +
            "<Relationships xmlns=\"http://schemas.openxmlformats.org/package/2006/relationships\">" +
            "<Relationship Id=\"rId1\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/slideLayout\" " +
            "Target=\"../slideLayouts/slideLayout1.xml\"/>";
    private static final String REL_FOOT = "</Relationships>";

    private static final String P_SLD_LEFT = "<p:sld xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\" " +
            "xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\" " +
            "xmlns:p=\"http://schemas.openxmlformats.org/presentationml/2006/main\">";
    private static final String P_SLD_RIGHT = "</p:sld>";
    private static final String P_C_SLD_LEFT = "<p:cSld>";
    private static final String P_C_SLD_RIGHT = "</p:cSld>";
    private static final String P_BG_LEFT = "<p:bg>";
    private static final String P_BG_RIGHT = "</p:bg>";
    private static final String P_BG_PR_LEFT = "<p:bgPr>";
    private static final String P_BG_PR_RIGHT = "</p:bgPr>";
    private static final String P_A_NO_FILL = "<a:noFill/>";
    private static final String P_A_EFFECT_LST = "<a:effectLst/>";
    private static final String P_SP_TREE_LEFT = "<p:spTree>";
    private static final String P_SP_TREE_RIGHT = "</p:spTree>";
    private static final String P_CLR_MAP_OVR_LEFT = "<p:clrMapOvr>";
    private static final String P_CLR_MAP_OVR_RIGHT = "</p:clrMapOvr>";
    private static final String A_MASTER_CLR_MAPPING = "<a:masterClrMapping/>";
    private static final String DEFAULT_P = "<p:nvGrpSpPr><p:cNvPr id=\"1\" name=\"\"/><p:cNvGrpSpPr/><p:nvPr/></p:nvGrpSpPr>" +
            "<p:grpSpPr><a:xfrm><a:off x=\"0\" y=\"0\"/><a:ext cx=\"0\" cy=\"0\"/><a:chOff x=\"0\" y=\"0\"/>" +
            "<a:chExt cx=\"0\" cy=\"0\"/></a:xfrm></p:grpSpPr>";

    /**
     * extLst 元素指定扩展列表，在该列表中定义元素类型 ext 的所有未来扩展。扩展列表以及相应的未来扩展用于扩展 PresentationML 框架的存储功能。
     * 有关 extLst 标签的详细说明，请参阅 ISO/IEC 29500
     * （http://www.iso.org/iso/home/store/catalogue_ics/catalogue_detail_ics.htm?csnumber=61750）
     * 的第 19.2.1.11 节和第 19.2.1.12 节
     */
    private static final String DEFAULT_END_P = "<p:extLst><p:ext uri=\"{BB962C8B-B14F-4D97-AF65-F5344CB8AC3E}\">" +
            "<p14:creationId xmlns:p14=\"http://schemas.microsoft.com/office/powerpoint/2010/main\" xmlns=\"\" val=\"2988139268\"/>" +
            "</p:ext></p:extLst>";

    private static final String TIMING_BEGIN = "<p:timing><p:tnLst><p:par><p:cTn id=\"1\" dur=\"indefinite\" " +
            "restart=\"never\" nodeType=\"tmRoot\"><p:childTnLst><p:seq concurrent=\"1\" nextAc=\"seek\"><p:cTn id=\"2\" " +
            "dur=\"indefinite\" nodeType=\"mainSeq\"><p:childTnLst>";

    private static final String TIMING_END_1 = "</p:childTnLst></p:cTn><p:prevCondLst><p:cond evt=\"onPrev\" delay=\"0\">" +
            "<p:tgtEl><p:sldTgt/></p:tgtEl></p:cond></p:prevCondLst><p:nextCondLst><p:cond evt=\"onNext\" delay=\"0\">" +
            "<p:tgtEl><p:sldTgt/></p:tgtEl></p:cond></p:nextCondLst></p:seq>";

    private static final String MUSIC_CHILD_1 = "<p:audio><p:cMediaNode numSld=\"999\" showWhenStopped=\"0\"><p:cTn id=\"";
    private static final String MUSIC_CHILD_2 = "\" repeatCount=\"indefinite\" fill=\"hold\" display=\"1\"><p:stCondLst>" +
            "<p:cond delay=\"indefinite\"/></p:stCondLst><p:endCondLst><p:cond evt=\"onStopAudio\"><p:tgtEl><p:sldTgt/>" +
            "</p:tgtEl></p:cond></p:endCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"2\"/></p:tgtEl></p:cMediaNode></p:audio>";

    private static final String TIMING_END_2 = "</p:childTnLst></p:cTn></p:par></p:tnLst><p:bldLst>";
    private static final String TIMING_END_3 = "</p:bldLst></p:timing>";

    private static final String CLICK_FIRST_HEAD = "<p:par><p:cTn id=\"3\" fill=\"hold\"><p:stCondLst><p:cond " +
            "delay=\"indefinite\"/><p:cond evt=\"onBegin\" delay=\"0\"><p:tn val=\"2\"/></p:cond></p:stCondLst>" +
            "<p:childTnLst>";


    private static final String CLICK_HEAD_1 = "<p:par><p:cTn id=\"";
    private static final String CLICK_HEAD_2 = "\" fill=\"hold\"><p:stCondLst><p:cond delay=\"indefinite\"/>" +
            "<p:cond evt=\"onBegin\" delay=\"0\"><p:tn val=\"2\"/></p:cond></p:stCondLst><p:childTnLst>";

    private static final String CLICK_FOOT = "</p:childTnLst></p:cTn></p:par>";

    private static final String NORMAL_BEGIN_1 = "<p:par><p:cTn id=\"";
    private static final String NORMAL_BEGIN_2 = "\" fill=\"hold\"><p:stCondLst><p:cond delay=\"indefinite\"/>" +
            "<p:cond evt=\"onBegin\" delay=\"0\"><p:tn val=\"2\"/></p:cond><p:cond delay=\"indefinite\"/>" +
            "</p:stCondLst><p:childTnLst>";

    private static final String PAR_LIST_BEGIN_1 = "<p:par><p:cTn id=\"";
    private static final String PAR_LIST_BEGIN_2 = "\" fill=\"hold\"><p:stCondLst><p:cond delay=\"";
    private static final String PAR_LIST_BEGIN_3 = "\"/></p:stCondLst><p:childTnLst>";

    private static final String PAR_LIST_END = "</p:childTnLst></p:cTn></p:par>";

    private static final String AUTO_PAGER_1 = "<p:transition advTm=\"";
    private static final String AUTO_PAGER_2 = "\"/>";


    public PPTShowSideXml toXml(int mediaIdBegin) {
        flushIdInElements(elements);
        PPTShowSideXml pptShowSideXML = new PPTShowSideXml();
        int relIdBegin = 2;
        List<FileCopy> files = Lists.newArrayList();

        StringBuilder sb = new StringBuilder();
        StringBuilder rel = new StringBuilder(REL_HEAD);
        sb.append(XML_HEAD).append(P_SLD_LEFT)
                .append(P_C_SLD_LEFT)
                .append(P_BG_LEFT)
                .append(P_BG_PR_LEFT);
        if (Objects.nonNull(css.getBackground())) {
            PPTElementXml elementXml = css.getBackground().solidFillGet(mediaIdBegin, relIdBegin);
            sb.append(elementXml.getSideXml());
            relIdBegin += elementXml.getIdAdd();
            mediaIdBegin += elementXml.getIdAdd();
            rel.append(elementXml.getRelXml());
            files.addAll(elementXml.getFile());
        } else {
            sb.append(P_A_NO_FILL);
        }
        sb.append(P_A_EFFECT_LST).append(P_BG_PR_RIGHT).append(P_BG_RIGHT);
        sb.append(P_SP_TREE_LEFT);
        sb.append(DEFAULT_P);
        if (isHaveMusic()) {
            PPTElementXml musicXml = toMusicXml(relIdBegin, mediaIdBegin);
            relIdBegin += musicXml.getIdAdd();
            mediaIdBegin += musicXml.getIdAdd();
            xmlAndFileBuild(files, sb, rel, musicXml);
        }
        for (PPTElement element : elements) {
            PPTElementXml pptElementXml = element.toXml(relIdBegin, mediaIdBegin);
            relIdBegin += pptElementXml.getIdAdd();
            mediaIdBegin += pptElementXml.getIdAdd();
            xmlAndFileBuild(files, sb, rel, pptElementXml);
        }
        sb.append(DEFAULT_END_P);
        sb.append(P_SP_TREE_RIGHT);
        sb.append(P_C_SLD_RIGHT);
        sb.append(P_CLR_MAP_OVR_LEFT).append(A_MASTER_CLR_MAPPING).append(P_CLR_MAP_OVR_RIGHT);
        sb.append(pageSwitching());
        sb.append(animationToXml());
        sb.append(P_SLD_RIGHT);

        rel.append(REL_FOOT);

        pptShowSideXML.setFile(files);
        pptShowSideXML.setRel(rel);
        pptShowSideXML.setSide(sb);
        return pptShowSideXML;
    }

    private void xmlAndFileBuild(List<FileCopy> files,
                                 StringBuilder sb,
                                 StringBuilder rel,
                                 PPTElementXml pptElementXml) {
        sb.append(pptElementXml.getSideXml());
        rel.append(pptElementXml.getRelXml());
        files.addAll(pptElementXml.getFile());
    }

    private PPTElementXml toMusicXml(int relIdBegin, int mediaIdBegin) {
        PPTElementXml pptElementXml = new PPTElementXml();
        //复制音乐文件
        String musicName = Constant.MEDIA_NAME + mediaIdBegin + Constant.SUFFIX_POINT + FileUtil.getSuffix(backgroundMusic);
        FileCopy copy = new FileCopy().setFrom(backgroundMusic).setTo(Constant.MEDIA + musicName);
        pptElementXml.getFile().add(copy);
        //复制图标文件
        mediaIdBegin++;
        String iconName = Constant.IMAGE_NAME + mediaIdBegin + Constant.SUFFIX_POINT + FileUtil.getSuffix(Constant.MUSIC_ICON_PATH);
        FileCopy iconCopy = new FileCopy().setFrom(Constant.MUSIC_ICON_PATH).setTo(Constant.MEDIA + iconName);
        pptElementXml.getFile().add(iconCopy);
        //增加音乐图标代码
        StringBuilder sb = new StringBuilder();
        sb.append("<p:pic><p:nvPicPr><p:cNvPr id=\"2\" name=\"Study and Relax\">" +
                "<a:hlinkClick r:id=\"\" action=\"ppaction://media\"/></p:cNvPr><p:cNvPicPr/><p:nvPr>" +
                "<a:audioFile r:link=\"rId");
        sb.append(relIdBegin);
        sb.append("\"/><p:extLst><p:ext uri=\"{DAA4B4D4-6D71-4841-9C94-3DE7FCFB9230}\"><p14:media xmlns:p14=" +
                "\"http://schemas.microsoft.com/office/powerpoint/2010/main\" r:embed=\"rId");
        sb.append(++relIdBegin);
        sb.append("\"/></p:ext></p:extLst></p:nvPr></p:nvPicPr><p:blipFill><a:blip r:embed=\"rId");
        sb.append(++relIdBegin);
        sb.append("\"/><a:stretch><a:fillRect/></a:stretch></p:blipFill><p:spPr><a:xfrm><a:off x=\"0\" y=\"-521335\"/>" +
                "<a:ext cx=\"412750\" cy=\"412750\"/></a:xfrm><a:prstGeom prst=\"rect\"><a:avLst/></a:prstGeom>" +
                "</p:spPr></p:pic>");
        pptElementXml.setSideXml(sb);
        //增加资源链接代码
        StringBuilder rel = new StringBuilder();
        rel.append("<Relationship Id=\"rId");
        rel.append(relIdBegin);
        rel.append("\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/image\" " +
                "Target=\"../media/");
        rel.append(iconName);
        rel.append("\"/><Relationship Id=\"rId");
        rel.append(--relIdBegin);
        rel.append("\" Type=\"http://schemas.microsoft.com/office/2007/relationships/media\" Target=\"../media/");
        rel.append(musicName);
        rel.append("\"/><Relationship Id=\"rId");
        rel.append(--relIdBegin);
        rel.append("\" Type=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships/audio\" " +
                "Target=\"../media/");
        rel.append(musicName);
        rel.append("\"/>");
        pptElementXml.setRelXml(rel);
        pptElementXml.setIdAdd(3);
        return pptElementXml;
    }

    /**
     * 自动换页的XML
     */
    private String pageSwitching() {
        if ((Objects.isNull(pageSwitchingType) || pageSwitchingType.equals(PageSwitchingType.DEFAULT)) && autoPagerTime <= 0) {
            return EMPTY_STRING;
        }
        String timeStr = EMPTY_STRING;
        if (autoPagerTime > 0) {
            if (Objects.isNull(pageSwitchingType) || pageSwitchingType.equals(PageSwitchingType.DEFAULT)) {
                return AUTO_PAGER_1 + autoPagerTime + AUTO_PAGER_2;
            }
            timeStr = " advTm=\"" + autoPagerTime + "\"";
        }
        return pageSwitchingType.getXml().replace(PageSwitchingType.ADV_TM, timeStr);
    }

    /**
     * 每个元素实际上都包含了页面中展示的id，这里直接刷新一下
     * id从2开始，1是页面
     */
    private void flushIdInElements(List<PPTElement> elements) {
        for (int i = 0; i < elements.size(); i++) {
            elements.get(i).setId(i + 4);
        }
    }

    private String animationToXml() {
        if (isWithoutAnimation() && !isHaveMusic()) {
            return EMPTY_STRING;
        }
        return buildAnimationXml();
    }

    private boolean isHaveMusic() {
        File file = FileUtil.file(backgroundMusic);
        return Strings.isNotBlank(backgroundMusic) && Objects.nonNull(file) && file.exists();
    }

    private String buildAnimationXml() {
        List<PPTElement> elements = findHaveAnimationElements();
        int minId = findMinElement(elements);
        int maxId = findMaxElement(elements);
        StringBuilder sb = new StringBuilder();
        sb.append(TIMING_BEGIN);
        //真实使用的递增id
        int realId = 2;
        boolean isInClick = false;
        int afterTime = 0;
        StringBuilder elementListStr = new StringBuilder();
        for (int i = minId; i <= maxId; i++) {
            if (isEmptyNumberList(elements, i)) {
                continue;
            }

            boolean clickAnimation = isClickAnimation(elements, i);
            if (clickAnimation) {
                //点击事件开始
                isInClick = true;
                realId = addClickBegin(sb, realId);
            } else if (isInClick) {
                //点击事件结束
                addClickEnd(sb);
            } else if (i == minId) {
                //普通事件开始
                realId = addNormalBegin(sb, realId);
            }

            AnimationVo animationVo = addAnimationPars(sb, elements, i, realId, afterTime, clickAnimation);
            realId = animationVo.getRealId();
            afterTime = animationVo.getDelay();
            elementListStr.append(animationVo.getElementStr());

            if (i == maxId) {
                if (isHaveMusic()) {
                    realId = musicParXml(sb, realId);
                }
                addClickEnd(sb);
            }
        }
        sb.append(TIMING_END_1);
        if (isHaveMusic()) {
            sb.append(MUSIC_CHILD_1);
            sb.append(++realId);
            sb.append(MUSIC_CHILD_2);
        }
        sb.append(TIMING_END_2);
        if (isHaveMusic()) {
            sb.append(addElementListStr(2));
        }
        sb.append(elementListStr);
        sb.append(TIMING_END_3);
        return sb.toString();
    }

    private boolean isEmptyNumberList(List<PPTElement> elements, int i) {
        for (PPTElement element : elements) {
            if (Objects.nonNull(element.getInAnimation()) && element.getInAnimation().getIndex() == i) {
                return false;
            }
            if (Objects.nonNull(element.getOutAnimation()) && element.getOutAnimation().getIndex() == i) {
                return false;
            }
        }
        return true;
    }

    private int musicParXml(StringBuilder sb, int realId) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"1\" presetClass=\"mediacall\" presetSubtype=\"0\" fill=\"hold\" nodeType=\"afterEffect\">" +
                "<p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:cmd type=\"call\" " +
                "cmd=\"playFrom(0.0)\"><p:cBhvr additive=\"base\"><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"1\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"2\"/></p:tgtEl></p:cBhvr></p:cmd>" +
                "</p:childTnLst></p:cTn></p:par></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private AnimationVo addAnimationPars(StringBuilder sb,
                                         List<PPTElement> elements,
                                         int i,
                                         int realId,
                                         int afterTime,
                                         boolean clickAnimation) {
        int delay = 0;
        StringBuilder elementListStr = new StringBuilder();
        boolean afterAnimation = isAfterAnimation(elements, i);
        if (afterAnimation) {
            delay = afterTime + 500;
        }

        ShowAnimationType type = ShowAnimationType.SAME_TIME;
        if (clickAnimation) {
            type = ShowAnimationType.CLICK;
        } else if (afterAnimation) {
            type = ShowAnimationType.AFTER;
        }

        realId = addParListBegin(sb, realId, afterTime);
        for (PPTElement element : elements) {
            if (Objects.nonNull(element.getInAnimation()) && element.getInAnimation().getIndex() == i) {
                if (!ShowAnimationType.SAME_TIME.equals(type)) {
                    //如果整组需要点击出现，那么我们不需要用户定义的详细细节，直接第一个点击出现，其他同时出现，效果是一样的
                    realId = element.getInAnimation().appendXml(realId, element.getId(), sb, delay, type);
                    type = ShowAnimationType.SAME_TIME;
                    elementListStr.append(addElementListStr(element.getId()));
                } else {
                    realId = element.getInAnimation().appendXml(realId, element.getId(), sb, delay, type);
                    elementListStr.append(addElementListStr(element.getId()));
                }
            }
            if (Objects.nonNull(element.getOutAnimation()) && element.getOutAnimation().getIndex() == i) {
                if (!ShowAnimationType.SAME_TIME.equals(type)) {
                    realId = element.getOutAnimation().appendXml(realId, element.getId(), sb, delay, type);
                    type = ShowAnimationType.SAME_TIME;
                    elementListStr.append(addElementListStr(element.getId()));
                } else {
                    realId = element.getOutAnimation().appendXml(realId, element.getId(), sb, delay, type);
                    elementListStr.append(addElementListStr(element.getId()));
                }
            }
        }
        addParListEnd(sb);

        return new AnimationVo(realId, delay, elementListStr);
    }

    private String addElementListStr(int id) {
        return "<p:bldP spid=\"" + id + "\" grpId=\"0\"/>";
    }

    private void addParListEnd(StringBuilder sb) {
        sb.append(PAR_LIST_END);
    }

    private int addParListBegin(StringBuilder sb, int realId, int afterTime) {
        sb.append(PAR_LIST_BEGIN_1);
        sb.append(++realId);
        sb.append(PAR_LIST_BEGIN_2);
        sb.append(afterTime);
        sb.append(PAR_LIST_BEGIN_3);
        return realId;
    }

    private boolean isAfterAnimation(List<PPTElement> elements, int i) {
        return isClickAnimation(elements, i, ShowAnimationType.AFTER);
    }

    private int addNormalBegin(StringBuilder sb, int realId) {
        sb.append(NORMAL_BEGIN_1);
        sb.append(++realId);
        sb.append(NORMAL_BEGIN_2);
        return realId;
    }

    private void addClickEnd(StringBuilder sb) {
        sb.append(CLICK_FOOT);
    }

    private int addClickBegin(StringBuilder sb, int i) {
        sb.append(CLICK_HEAD_1);
        sb.append(++i);
        sb.append(CLICK_HEAD_2);
        return i;
    }

    private boolean isClickAnimation(List<PPTElement> elements, int i) {
        return isClickAnimation(elements, i, ShowAnimationType.CLICK);
    }

    private boolean isClickAnimation(List<PPTElement> elements, int i, ShowAnimationType type) {
        return elements.stream()
                .anyMatch(e -> (Objects.nonNull(e.getInAnimation())
                        && type.equals(e.getInAnimation().getShowAnimationType())
                        && e.getInAnimation().getIndex() == i)
                        || (Objects.nonNull(e.getOutAnimation())
                        && type.equals(e.getOutAnimation().getShowAnimationType())
                        && e.getOutAnimation().getIndex() == i));
    }

    private int findMaxElement(List<PPTElement> elements) {
        Integer max = null;
        for (PPTElement element : elements) {
            if (Objects.nonNull(element.getInAnimation())
                    && (Objects.isNull(max) || max < element.getInAnimation().getIndex())) {
                max = element.getInAnimation().getIndex();
            }
            if (Objects.nonNull(element.getOutAnimation())
                    && (Objects.isNull(max) || max < element.getOutAnimation().getIndex())) {
                max = element.getOutAnimation().getIndex();
            }
        }
        return Objects.isNull(max) ? 0 : max;
    }

    private int findMinElement(List<PPTElement> elements) {
        Integer min = null;
        for (PPTElement element : elements) {
            if (Objects.nonNull(element.getInAnimation())
                    && (Objects.isNull(min) || min > element.getInAnimation().getIndex())) {
                min = element.getInAnimation().getIndex();
            }
            if (Objects.nonNull(element.getOutAnimation())
                    && (Objects.isNull(min) || min > element.getOutAnimation().getIndex())) {
                min = element.getOutAnimation().getIndex();
            }
        }
        return Objects.isNull(min) ? 0 : min;
    }

    private boolean isWithoutAnimation() {
        if (CollectionUtils.isEmpty(elements)) {
            return true;
        }
        List<PPTElement> elements = findHaveAnimationElements();
        return CollectionUtils.isEmpty(elements);
    }

    private List<PPTElement> findHaveAnimationElements() {
        return this.elements.stream()
                .filter(e -> (Objects.nonNull(e.getInAnimation()) || Objects.nonNull(e.getOutAnimation()))
                )
                .collect(Collectors.toList());
    }

    public List<PPTElement> getElements() {
        return elements;
    }

    public PPTShowSide() {

    }

    public static PPTShowSide build() {
        return new PPTShowSide();
    }

    public void add(PPTElement pptElement) {
        elements.add(pptElement);
    }

    public void add(PPTElement pptElement, int beforeThisElementId) {
        elements.add(beforeThisElementId, pptElement);
    }

    public void addAll(List<PPTElement> pptElements) {
        elements.addAll(pptElements);
    }

}
