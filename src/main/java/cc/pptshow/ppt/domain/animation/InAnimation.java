package cc.pptshow.ppt.domain.animation;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

/**
 * PPT进入动画
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class InAnimation extends Animation {

    private ShowAnimationType showAnimationType = ShowAnimationType.SAME_TIME;

    private InAnimationType inAnimationType;

    private AnimationAttribute animationAttribute;

    /**
     * 动画持续时间，毫秒。
     * 不填也有默认值
     */
    private Integer timeMs;

    @Override
    public InAnimation clone() {
        InAnimation inAnimation = new InAnimation();
        inAnimation.setShowAnimationType(showAnimationType);
        inAnimation.setInAnimationType(inAnimationType);
        inAnimation.setAnimationAttribute(animationAttribute);
        inAnimation.setIndex(getIndex());
        inAnimation.setTimeMs(timeMs);
        return inAnimation;
    }

    @Override
    public int appendXml(int realId, int elementId, StringBuilder sb, int delayTime, ShowAnimationType type) {
        if (Objects.isNull(inAnimationType)) {
            return realId;
        }
        initialization();
        String typeStr = "withEffect";
        if (type.equals(ShowAnimationType.CLICK)) {
            typeStr = "clickEffect";
        } else if (type.equals(ShowAnimationType.AFTER)) {
            typeStr = "afterEffect";
        }
        if (Objects.isNull(timeMs)) {
            timeMs = inAnimationType.getDefaultTime();
        }
        if (inAnimationType == InAnimationType.BLINDS) {
            realId = toBlindsXml(realId, elementId, sb, delayTime, typeStr);
        } else if (inAnimationType == InAnimationType.WIPE) {
            realId = toWipeXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.APPEAR) {
            realId = toAppearXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.FLY_INTO) {
            realId = toFlyIntoXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.BOX) {
            realId = toBoxXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.SLOW_ENTRY) {
            realId = toSlowEntryXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.LADDER) {
            realId = toLadderXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.DIAMOND) {
            realId = toDiamondXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.WHEEL) {
            realId = toWheelXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.SPLITTING) {
            realId = toSplittingXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.BOARD) {
            realId = toBoardXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.CUT) {
            realId = toCutXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.FLASH) {
            realId = toFlashXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.SECTOR) {
            realId = toSectorXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.EXPANSION) {
            realId = toExpansionXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.LINE) {
            realId = toLineXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.DISSOLVE) {
            realId = toDissolveXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.CIRCULAR) {
            realId = toCircularXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.GRADIENT) {
            realId = toGradientXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.CYCLOTRON_GRADIENT) {
            realId = toCyclotronGradientXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.ZOOM_GRADIENT) {
            realId = toZoomGradientXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.UNFOLD) {
            realId = toUnfoldXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.FLIP) {
            realId = toFlipXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.CYCLOTRON) {
            realId = toCyclotronXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.GRADUALLY_INTO) {
            realId = toGraduallyIntoXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.RISING_UP) {
            realId = toRisingUpXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.STRETCH) {
            AnimationAttribute attribute = findValidTypeIn4TiltDirectionAndAcross();
            if (attribute.equals(AnimationAttribute.ACROSS)) {
                realId = toAcrossStretchXml(realId, elementId, sb, typeStr);
            } else {
                realId = toNotAcrossStretchXml(realId, elementId, sb, typeStr, attribute);
            }
        } else if (inAnimationType == InAnimationType.RISING) {
            realId = toRisingXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.ZOOM) {
            realId = toZoomXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.FALLING) {
            realId = toFaillingXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.COMPRESSION) {
            realId = toCompressionXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.COLOR_TYPEWRITER) {
            realId = toColorTypewriterXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.SPREAD) {
            realId = toSpreadXml(realId, elementId, sb, typeStr);
        } else if (inAnimationType == InAnimationType.FLOATING) {
            realId = toFloatingXml(realId, elementId, sb, typeStr);
        }
        return realId;
    }

    private int toCutXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        AnimationAttribute attribute = findValidTypeIn4Direction();
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"12\" presetClass=\"entr\" presetSubtype=\"");
        sb.append(attribute.getCode());
        sb.append("\" fill=\"hold\" grpId=\"0\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:to><p:strVal val=\"visible\"/></p:to></p:set><p:anim calcmode=\"lin\" valueType=\"num\">" +
                "<p:cBhvr additive=\"base\"><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst>");
        if (attribute.equals(AnimationAttribute.LEFT) || attribute.equals(AnimationAttribute.RIGHT)) {
            sb.append("<p:attrName>ppt_x</p:attrName>");
        } else {
            sb.append("<p:attrName>ppt_y</p:attrName>");
        }
        sb.append("</p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val>");
        if (attribute.equals(AnimationAttribute.LEFT)) {
            sb.append("<p:strVal val=\"#ppt_x-#ppt_w*1.125000\"/>");
        } else if (attribute.equals(AnimationAttribute.RIGHT)) {
            sb.append("<p:strVal val=\"#ppt_x+#ppt_w*1.125000\"/>");
        } else if (attribute.equals(AnimationAttribute.BOTTOM)) {
            sb.append("<p:strVal val=\"#ppt_y+#ppt_h*1.125000\"/>");
        } else {
            sb.append("<p:strVal val=\"#ppt_y-#ppt_h*1.125000\"/>");
        }
        sb.append("</p:val></p:tav><p:tav tm=\"100000\"><p:val>");
        if (attribute.equals(AnimationAttribute.LEFT) || attribute.equals(AnimationAttribute.RIGHT)) {
            sb.append("<p:strVal val=\"#ppt_x\"/>");
        } else {
            sb.append("<p:strVal val=\"#ppt_y\"/>");
        }
        sb.append("</p:val></p:tav></p:tavLst></p:anim><p:animEffect transition=\"in\" filter=\"wipe(");
        sb.append(attribute.getEnName());
        sb.append(")\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl></p:cBhvr></p:animEffect></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toFloatingXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"30\" presetClass=\"entr\" presetSubtype=\"0\" fill=\"hold\" grpId=\"0\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:to><p:strVal val=\"visible\"/></p:to></p:set><p:animEffect transition=\"in\" filter=\"fade\">" +
                "<p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" decel=\"100000\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl></p:cBhvr></p:animEffect><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr>" +
                "<p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" decel=\"100000\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.rotation</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:tavLst><p:tav tm=\"0\"><p:val><p:fltVal val=\"-90\"/></p:val></p:tav><p:tav tm=\"100000\">" +
                "<p:val><p:fltVal val=\"0\"/></p:val></p:tav></p:tavLst></p:anim><p:anim calcmode=\"lin\" " +
                "valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" decel=\"100000\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_x</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst>" +
                "<p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_x+0.4\"/></p:val></p:tav><p:tav tm=\"100000\"><p:val>" +
                "<p:strVal val=\"#ppt_x-0.05\"/></p:val></p:tav></p:tavLst></p:anim><p:anim calcmode=\"lin\" " +
                "valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" decel=\"100000\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_y</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst>" +
                "<p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_y-0.4\"/></p:val></p:tav><p:tav tm=\"100000\">" +
                "<p:val><p:strVal val=\"#ppt_y+0.1\"/></p:val></p:tav></p:tavLst></p:anim><p:anim calcmode=\"lin\"" +
                " valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" accel=\"100000\" fill=\"hold\"><p:stCondLst><p:cond delay=\"800\"/></p:stCondLst>" +
                "</p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_x</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:tavLst><p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_x-0.05\"/></p:val></p:tav><p:tav tm=\"100000\">" +
                "<p:val><p:strVal val=\"#ppt_x\"/></p:val></p:tav></p:tavLst></p:anim><p:anim calcmode=\"lin\" " +
                "valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" accel=\"100000\" fill=\"hold\"><p:stCondLst><p:cond delay=\"800\"/>" +
                "</p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_y</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:tavLst><p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_y+0.1\"/></p:val></p:tav><p:tav tm=\"100000\">" +
                "<p:val><p:strVal val=\"#ppt_y\"/></p:val></p:tav></p:tavLst></p:anim></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toSpreadXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"40\" presetClass=\"entr\" presetSubtype=\"0\" fill=\"hold\" grpId=\"0\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:iterate type=\"lt\"><p:tmPct val=\"10000\"/>" +
                "</p:iterate><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:to><p:strVal val=\"visible\"/></p:to></p:set><p:animEffect transition=\"in\" filter=\"fade\">" +
                "<p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl></p:cBhvr></p:animEffect><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_x</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst>" +
                "<p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_x-.1\"/></p:val></p:tav><p:tav tm=\"100000\"><p:val>" +
                "<p:strVal val=\"#ppt_x\"/></p:val></p:tav></p:tavLst></p:anim><p:anim calcmode=\"lin\" valueType=\"num\">" +
                "<p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_y</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:tavLst><p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_y\"/></p:val></p:tav><p:tav tm=\"100000\">" +
                "<p:val><p:strVal val=\"#ppt_y\"/></p:val></p:tav></p:tavLst></p:anim></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toColorTypewriterXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"27\" presetClass=\"entr\" presetSubtype=\"0\" fill=\"hold\" grpId=\"0\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:iterate type=\"lt\"><p:tmPct val=\"50000\"/>" +
                "</p:iterate><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:to><p:strVal val=\"visible\"/></p:to></p:set><p:anim calcmode=\"discrete\" valueType=\"clr\">" +
                "<p:cBhvr override=\"childStyle\"><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.color</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:tavLst><p:tav tm=\"0\"><p:val><p:clrVal><a:schemeClr val=\"accent2\"/></p:clrVal></p:val>" +
                "</p:tav><p:tav tm=\"50000\"><p:val><p:clrVal><a:schemeClr val=\"hlink\"/></p:clrVal></p:val>" +
                "</p:tav></p:tavLst></p:anim><p:anim calcmode=\"discrete\" valueType=\"clr\"><p:cBhvr>" +
                "<p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>fillcolor</p:attrName></p:attrNameLst>" +
                "</p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val><p:clrVal><a:schemeClr val=\"accent2\"/>" +
                "</p:clrVal></p:val></p:tav><p:tav tm=\"50000\"><p:val><p:clrVal><a:schemeClr val=\"hlink\"/>" +
                "</p:clrVal></p:val></p:tav></p:tavLst></p:anim><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>fill.type</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:to><p:strVal val=\"solid\"/></p:to></p:set></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toCompressionXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"50\" presetClass=\"entr\" presetSubtype=\"0\" decel=\"100000\" fill=\"hold\" " +
                "grpId=\"0\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:to><p:strVal val=\"visible\"/></p:to></p:set><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr>" +
                "<p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_w</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst>" +
                "<p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_w+.3\"/></p:val></p:tav><p:tav tm=\"100000\"><p:val>" +
                "<p:strVal val=\"#ppt_w\"/></p:val></p:tav></p:tavLst></p:anim><p:anim calcmode=\"lin\" " +
                "valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_h</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst>" +
                "<p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_h\"/></p:val></p:tav><p:tav tm=\"100000\"><p:val>" +
                "<p:strVal val=\"#ppt_h\"/></p:val></p:tav></p:tavLst></p:anim><p:animEffect transition=\"in\" " +
                "filter=\"fade\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl></p:cBhvr></p:animEffect></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toFaillingXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"47\" presetClass=\"entr\" presetSubtype=\"0\" fill=\"hold\" grpId=\"0\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr>" +
                "<p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:to><p:strVal val=\"visible\"/></p:to></p:set><p:animEffect transition=\"in\" filter=\"fade\">" +
                "<p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl></p:cBhvr></p:animEffect><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr>" +
                "<p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_x</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst>" +
                "<p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_x\"/></p:val></p:tav><p:tav tm=\"100000\"><p:val>" +
                "<p:strVal val=\"#ppt_x\"/></p:val></p:tav></p:tavLst></p:anim><p:anim calcmode=\"lin\" " +
                "valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_y</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst>" +
                "<p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_y-.1\"/></p:val></p:tav><p:tav tm=\"100000\"><p:val>" +
                "<p:strVal val=\"#ppt_y\"/></p:val></p:tav></p:tavLst></p:anim></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toZoomXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"23\" presetClass=\"entr\" presetSubtype=\"");
        sb.append(AnimationAttribute.IN.getCode());
        sb.append("\" fill=\"hold\" grpId=\"0\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr>" +
                "<p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl>" +
                "<p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst>" +
                "</p:cBhvr><p:to><p:strVal val=\"visible\"/></p:to></p:set><p:anim calcmode=\"lin\" valueType=\"num\">" +
                "<p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_w</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst>" +
                "<p:tav tm=\"0\"><p:val><p:fltVal val=\"0\"/></p:val></p:tav><p:tav tm=\"100000\"><p:val><p:strVal " +
                "val=\"#ppt_w\"/></p:val></p:tav></p:tavLst></p:anim><p:anim calcmode=\"lin\" valueType=\"num\">" +
                "<p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_h</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst>" +
                "<p:tav tm=\"0\"><p:val><p:fltVal val=\"0\"/></p:val></p:tav><p:tav tm=\"100000\"><p:val>" +
                "<p:strVal val=\"#ppt_h\"/></p:val></p:tav></p:tavLst></p:anim></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toRisingXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"37\" presetClass=\"entr\" presetSubtype=\"0\" fill=\"hold\" grpId=\"0\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl>" +
                "<p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:to><p:strVal val=\"visible\"/></p:to></p:set><p:animEffect transition=\"in\" filter=\"fade\">" +
                "<p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl></p:cBhvr></p:animEffect><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr>" +
                "<p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_x</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst>" +
                "<p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_x\"/></p:val></p:tav><p:tav tm=\"100000\"><p:val>" +
                "<p:strVal val=\"#ppt_x\"/></p:val></p:tav></p:tavLst></p:anim><p:anim calcmode=\"lin\" " +
                "valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" decel=\"100000\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_y</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst>" +
                "<p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_y+1\"/></p:val></p:tav><p:tav tm=\"100000\"><p:val>" +
                "<p:strVal val=\"#ppt_y-.03\"/></p:val></p:tav></p:tavLst></p:anim><p:anim calcmode=\"lin\" " +
                "valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs / 10);
        sb.append("\" accel=\"100000\" fill=\"hold\"><p:stCondLst><p:cond delay=\"");
        sb.append(timeMs - (timeMs / 10));
        sb.append("\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_y</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst>" +
                "<p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_y-.03\"/></p:val></p:tav><p:tav tm=\"100000\"><p:val>" +
                "<p:strVal val=\"#ppt_y\"/></p:val></p:tav></p:tavLst></p:anim></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toNotAcrossStretchXml(int realId, int elementId, StringBuilder sb, String typeStr, AnimationAttribute attribute) {
        String x = "#ppt_x", y = "#ppt_y";
        if (attribute.equals(AnimationAttribute.LEFT)) {
            x = "#ppt_x-#ppt_w/2";
        } else if (attribute.equals(AnimationAttribute.RIGHT)) {
            x = "#ppt_x+#ppt_w/2";
        } else if (attribute.equals(AnimationAttribute.TOP)) {
            y = "#ppt_y-#ppt_h/2";
        } else if (attribute.equals(AnimationAttribute.BOTTOM)) {
            y = "#ppt_y+#ppt_h/2";
        }
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"17\" presetClass=\"entr\" presetSubtype=\"");
        sb.append(attribute.getCode());
        sb.append("\" fill=\"hold\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr " +
                "additive=\"base\"><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl>" +
                "<p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "style.visibility</p:attrName></p:attrNameLst></p:cBhvr><p:to><p:strVal val=\"visible\"/>" +
                "</p:to></p:set><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr additive=\"base\">" +
                "<p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_x</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\" fmla=\"\"><p:val>");
        sb.append("<p:strVal val=\"");
        sb.append(x);
        sb.append("\"/></p:val></p:tav><p:tav tm=\"100000\" fmla=\"\"><p:val><p:strVal val=\"#ppt_x\"/></p:val>" +
                "</p:tav></p:tavLst></p:anim><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr additive=\"base\">" +
                "<p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_y</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\" fmla=\"\"><p:val>" +
                "<p:strVal val=\"");
        sb.append(y);
        sb.append("\"/></p:val></p:tav><p:tav tm=\"100000\" fmla=\"\"><p:val><p:strVal val=\"#ppt_y\"/></p:val>" +
                "</p:tav></p:tavLst></p:anim><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr additive=\"base\">" +
                "<p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_w</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\" fmla=\"\"><p:val>");
        if (isTopOrBottom(attribute)) {
            sb.append("<p:strVal val=\"#ppt_w\"/>");
        } else {
            sb.append("<p:fltVal val=\"0.000000\"/>");
        }
        sb.append("</p:val></p:tav><p:tav tm=\"100000\" fmla=\"\"><p:val><p:strVal val=\"#ppt_w\"/></p:val>" +
                "</p:tav></p:tavLst></p:anim><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr additive=\"base\">" +
                "<p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_h</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\" fmla=\"\"><p:val>");
        if (isTopOrBottom(attribute)) {
            sb.append("<p:fltVal val=\"0.000000\"/>");
        } else {
            sb.append("<p:strVal val=\"#ppt_h\"/>");
        }
        sb.append("</p:val></p:tav><p:tav tm=\"100000\" fmla=\"\"><p:val><p:strVal val=\"#ppt_h\"/></p:val>" +
                "</p:tav></p:tavLst></p:anim></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private boolean isTopOrBottom(AnimationAttribute attribute) {
        return attribute.equals(AnimationAttribute.TOP) || attribute.equals(AnimationAttribute.BOTTOM);
    }

    private int toAcrossStretchXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"17\" presetClass=\"entr\" presetSubtype=\"");
        sb.append(AnimationAttribute.ACROSS.getCode());
        sb.append("\" fill=\"hold\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst>" +
                "<p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "style.visibility</p:attrName></p:attrNameLst></p:cBhvr><p:to><p:strVal val=\"visible\"/></p:to>" +
                "</p:set><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_w</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val><p:fltVal val=\"0\"/>" +
                "</p:val></p:tav><p:tav tm=\"100000\"><p:val><p:strVal val=\"#ppt_w\"/></p:val></p:tav></p:tavLst>" +
                "</p:anim><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst>" +
                "<p:attrName>ppt_h</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val>" +
                "<p:strVal val=\"#ppt_h\"/></p:val></p:tav><p:tav tm=\"100000\"><p:val><p:strVal val=\"#ppt_h\"/>" +
                "</p:val></p:tav></p:tavLst></p:anim></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toRisingUpXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"42\" presetClass=\"entr\" presetSubtype=\"0\" fill=\"hold\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst>" +
                "<p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr><p:to><p:strVal val=\"visible\"/>" +
                "</p:to></p:set><p:animEffect transition=\"in\" filter=\"fade\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl></p:cBhvr></p:animEffect>" +
                "<p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_x</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_x\"/>" +
                "</p:val></p:tav><p:tav tm=\"100000\"><p:val><p:strVal val=\"#ppt_x\"/></p:val></p:tav></p:tavLst>" +
                "</p:anim><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst>" +
                "<p:attrName>ppt_y</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val>" +
                "<p:strVal val=\"#ppt_y+.1\"/></p:val></p:tav><p:tav tm=\"100000\"><p:val><p:strVal val=\"#ppt_y\"/>" +
                "</p:val></p:tav></p:tavLst></p:anim></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toGraduallyIntoXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"29\" presetClass=\"entr\" presetSubtype=\"0\" fill=\"hold\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "style.visibility</p:attrName></p:attrNameLst></p:cBhvr><p:to><p:strVal val=\"visible\"/></p:to>" +
                "</p:set><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_x</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_x-.2\"/>" +
                "</p:val></p:tav><p:tav tm=\"100000\"><p:val><p:strVal val=\"#ppt_x\"/></p:val></p:tav></p:tavLst>" +
                "</p:anim><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_y</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_y\"/>" +
                "</p:val></p:tav><p:tav tm=\"100000\"><p:val><p:strVal val=\"#ppt_y\"/></p:val></p:tav></p:tavLst>" +
                "</p:anim><p:animEffect transition=\"in\" filter=\"wipe(right)\" prLst=\"gradientSize: 0.1\"><p:cBhvr>" +
                "<p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl></p:cBhvr></p:animEffect>" +
                "</p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toCyclotronXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"49\" presetClass=\"entr\" presetSubtype=\"0\" decel=\"100000\" fill=\"hold\" " +
                "nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set>" +
                "<p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "style.visibility</p:attrName></p:attrNameLst></p:cBhvr><p:to><p:strVal val=\"visible\"/></p:to>" +
                "</p:set><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_w</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val><p:fltVal val=\"0\"/>" +
                "</p:val></p:tav><p:tav tm=\"100000\"><p:val><p:strVal val=\"#ppt_w\"/></p:val></p:tav></p:tavLst>" +
                "</p:anim><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_h</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val><p:fltVal val=\"0\"/>" +
                "</p:val></p:tav><p:tav tm=\"100000\"><p:val><p:strVal val=\"#ppt_h\"/></p:val></p:tav></p:tavLst>" +
                "</p:anim><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "style.rotation</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val><p:fltVal " +
                "val=\"360\"/></p:val></p:tav><p:tav tm=\"100000\"><p:val><p:fltVal val=\"0\"/></p:val></p:tav>" +
                "</p:tavLst></p:anim><p:animEffect transition=\"in\" filter=\"fade\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl></p:cBhvr></p:animEffect>" +
                "</p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toFlipXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"31\" presetClass=\"entr\" presetSubtype=\"0\" fill=\"hold\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "style.visibility</p:attrName></p:attrNameLst></p:cBhvr><p:to><p:strVal val=\"visible\"/></p:to>" +
                "</p:set><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_w</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val><p:fltVal val=\"0\"/>" +
                "</p:val></p:tav><p:tav tm=\"100000\"><p:val><p:strVal val=\"#ppt_w\"/></p:val></p:tav></p:tavLst>" +
                "</p:anim><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_h</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val><p:fltVal val=\"0\"/>" +
                "</p:val></p:tav><p:tav tm=\"100000\"><p:val><p:strVal val=\"#ppt_h\"/></p:val></p:tav></p:tavLst>" +
                "</p:anim><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "style.rotation</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val>" +
                "<p:fltVal val=\"90\"/></p:val></p:tav><p:tav tm=\"100000\"><p:val><p:fltVal val=\"0\"/>" +
                "</p:val></p:tav></p:tavLst></p:anim><p:animEffect transition=\"in\" filter=\"fade\"><p:cBhvr>" +
                "<p:cTn id=\"10\" dur=\"1000\"/><p:tgtEl><p:spTgt spid=\"4\"><p:txEl><p:pRg st=\"0\" end=\"0\"/>" +
                "</p:txEl></p:spTgt></p:tgtEl></p:cBhvr></p:animEffect></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toUnfoldXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"55\" presetClass=\"entr\" presetSubtype=\"0\" fill=\"hold\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "style.visibility</p:attrName></p:attrNameLst></p:cBhvr><p:to><p:strVal val=\"visible\"/></p:to></p:set>" +
                "<p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_w</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_w*0.70\"/>" +
                "</p:val></p:tav><p:tav tm=\"100000\"><p:val><p:strVal val=\"#ppt_w\"/></p:val></p:tav></p:tavLst>" +
                "</p:anim><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_h</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_h\"/>" +
                "</p:val></p:tav><p:tav tm=\"100000\"><p:val><p:strVal val=\"#ppt_h\"/></p:val></p:tav></p:tavLst>" +
                "</p:anim><p:animEffect transition=\"in\" filter=\"fade\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"1000\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl></p:cBhvr></p:animEffect>" +
                "</p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toZoomGradientXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"53\" presetClass=\"entr\" presetSubtype=\"16\" fill=\"hold\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "style.visibility</p:attrName></p:attrNameLst></p:cBhvr><p:to><p:strVal val=\"visible\"/></p:to>" +
                "</p:set><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_w</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val><p:fltVal val=\"0\"/>" +
                "</p:val></p:tav><p:tav tm=\"100000\"><p:val><p:strVal val=\"#ppt_w\"/></p:val></p:tav></p:tavLst>" +
                "</p:anim><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_h</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val><p:fltVal val=\"0\"/>" +
                "</p:val></p:tav><p:tav tm=\"100000\"><p:val><p:strVal val=\"#ppt_h\"/></p:val></p:tav></p:tavLst>" +
                "</p:anim><p:animEffect transition=\"in\" filter=\"fade\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl></p:cBhvr></p:animEffect>" +
                "</p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toCyclotronGradientXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"45\" presetClass=\"entr\" presetSubtype=\"0\" fill=\"hold\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "style.visibility</p:attrName></p:attrNameLst></p:cBhvr><p:to><p:strVal val=\"visible\"/></p:to>" +
                "</p:set><p:animEffect transition=\"in\" filter=\"fade\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl></p:cBhvr></p:animEffect>" +
                "<p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_w</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\" fmla=\"#ppt_w*sin(2.5*pi*$)\">" +
                "<p:val><p:fltVal val=\"0\"/></p:val></p:tav><p:tav tm=\"100000\"><p:val><p:fltVal val=\"1\"/>" +
                "</p:val></p:tav></p:tavLst></p:anim><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr>" +
                "<p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "ppt_h</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst><p:tav tm=\"0\"><p:val><p:strVal val=\"#ppt_h\"/>" +
                "</p:val></p:tav><p:tav tm=\"100000\"><p:val><p:strVal val=\"#ppt_h\"/></p:val></p:tav></p:tavLst>" +
                "</p:anim></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toGradientXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"10\" presetClass=\"entr\" presetSubtype=\"0\" fill=\"hold\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "style.visibility</p:attrName></p:attrNameLst></p:cBhvr><p:to><p:strVal val=\"visible\"/></p:to>" +
                "</p:set><p:animEffect transition=\"in\" filter=\"fade\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl></p:cBhvr></p:animEffect>" +
                "</p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toCircularXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        AnimationAttribute attribute = findValidTypeInOrOut();
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"6\" presetClass=\"entr\" presetSubtype=\"");
        sb.append(attribute.getCode());
        sb.append("\" fill=\"hold\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr>" +
                "<p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl>" +
                "<p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst>" +
                "<p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr><p:to><p:strVal val=\"visible\"/>" +
                "</p:to></p:set><p:animEffect transition=\"in\" filter=\"circle(");
        sb.append(attribute.getEnName());
        sb.append(")\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl></p:cBhvr></p:animEffect>" +
                "</p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toDissolveXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"9\" presetClass=\"entr\" presetSubtype=\"0\" fill=\"hold\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl><p:attrNameLst><p:attrName>" +
                "style.visibility</p:attrName></p:attrNameLst></p:cBhvr><p:to><p:strVal val=\"visible\"/>" +
                "</p:to></p:set><p:animEffect transition=\"in\" filter=\"dissolve\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"><p:txEl><p:pRg st=\"0\" end=\"0\"/></p:txEl></p:spTgt></p:tgtEl></p:cBhvr></p:animEffect>" +
                "</p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toLineXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        AnimationAttribute attribute = findValidTypeHorizontalOrVertical();
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"14\" presetClass=\"entr\" presetSubtype=\"");
        sb.append(attribute.getCode());
        sb.append("\" fill=\"hold\" grpId=\"1\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:to><p:strVal val=\"visible\"/></p:to></p:set><p:animEffect transition=\"in\" filter=\"randombar(");
        sb.append(attribute.getEnName());
        sb.append(")\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl></p:cBhvr></p:animEffect></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toExpansionXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        AnimationAttribute attribute = findValidTypeInOrOut();
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"13\" presetClass=\"entr\" presetSubtype=\"");
        sb.append(attribute.getCode());
        sb.append("\" fill=\"hold\" grpId=\"1\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:to><p:strVal val=\"visible\"/></p:to></p:set><p:animEffect transition=\"in\" filter=\"plus(");
        sb.append(attribute.getEnName());
        sb.append(")\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl></p:cBhvr></p:animEffect></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toSectorXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"20\" presetClass=\"entr\" presetSubtype=\"0\" fill=\"hold\" grpId=\"1\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:to><p:strVal val=\"visible\"/></p:to></p:set><p:animEffect transition=\"in\" filter=\"wedge\">" +
                "<p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl></p:cBhvr></p:animEffect></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toFlashXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"11\" presetClass=\"entr\" presetSubtype=\"0\" fill=\"hold\" grpId=\"0\" " +
                "nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:to><p:strVal val=\"visible\"/></p:to></p:set></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toBoardXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        AnimationAttribute attribute = AnimationAttribute.ACROSS;
        if (Objects.nonNull(animationAttribute)
                && Lists.newArrayList(AnimationAttribute.ACROSS, AnimationAttribute.BOTTOM).contains(animationAttribute)) {
            attribute = animationAttribute;
        }
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"5\" presetClass=\"entr\" presetSubtype=\"");
        sb.append(attribute.getCode());
        sb.append("\" fill=\"hold\" grpId=\"0\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:to><p:strVal val=\"visible\"/></p:to></p:set><p:animEffect transition=\"in\" filter=\"checkerboard(");
        sb.append(attribute.getEnName());
        sb.append(")\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl></p:cBhvr></p:animEffect></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toSplittingXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        AnimationAttribute attribute = findValidTypeInStretch();
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"16\" presetClass=\"entr\" presetSubtype=\"");
        sb.append(attribute.getCode());
        sb.append("\" fill=\"hold\" grpId=\"0\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:to><p:strVal val=\"visible\"/></p:to></p:set><p:animEffect transition=\"in\" filter=\"barn(");
        sb.append(attribute.getEnName());
        sb.append(")\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl></p:cBhvr></p:animEffect></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private AnimationAttribute findValidTypeInStretch() {
        AnimationAttribute attribute = AnimationAttribute.IN_VERTICAL;
        if (Objects.nonNull(animationAttribute)
                && Lists.newArrayList(AnimationAttribute.IN_VERTICAL, AnimationAttribute.IN_HORIZONTAL,
                AnimationAttribute.OUT_VERTICAL, AnimationAttribute.OUT_HORIZONTAL).contains(animationAttribute)) {
            attribute = animationAttribute;
        }
        return attribute;
    }

    private int toWheelXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        AnimationAttribute attribute = findValidTypeInNumber();
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"21\" presetClass=\"entr\" presetSubtype=\"");
        sb.append(attribute.getCode());
        sb.append("\" fill=\"hold\" grpId=\"0\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr><p:to>" +
                "<p:strVal val=\"visible\"/></p:to></p:set><p:animEffect transition=\"in\" filter=\"wheel(");
        sb.append(attribute.getEnName());
        sb.append(")\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl></p:cBhvr></p:animEffect></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private AnimationAttribute findValidTypeInNumber() {
        AnimationAttribute attribute = AnimationAttribute.NUM_1;
        if (Objects.nonNull(animationAttribute)
                && Lists.newArrayList(AnimationAttribute.NUM_1, AnimationAttribute.NUM_2, AnimationAttribute.NUM_3,
                AnimationAttribute.NUM_4, AnimationAttribute.NUM_8).contains(animationAttribute)) {
            attribute = animationAttribute;
        }
        return attribute;
    }

    private int toDiamondXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        AnimationAttribute attribute = findValidTypeInOrOut();
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"8\" presetClass=\"entr\" presetSubtype=\"");
        sb.append(attribute.getCode());
        sb.append("\" fill=\"hold\" grpId=\"0\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:to><p:strVal val=\"visible\"/></p:to></p:set><p:animEffect transition=\"in\" filter=\"diamond(");
        sb.append(attribute.getEnName());
        sb.append(")\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl></p:cBhvr></p:animEffect></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toLadderXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        AnimationAttribute attribute = findValidTypeIn4TiltDirection();
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"18\" presetClass=\"entr\" presetSubtype=\"");
        sb.append(attribute.getCode());
        sb.append("\" fill=\"hold\" grpId=\"0\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/>" +
                "</p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl>" +
                "<p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst>" +
                "</p:cBhvr><p:to><p:strVal val=\"visible\"/></p:to></p:set><p:animEffect transition=\"in\" " +
                "filter=\"strips(");
        sb.append(attribute.getEnName());
        sb.append(")\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl></p:cBhvr></p:animEffect></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toSlowEntryXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        AnimationAttribute attribute = findValidTypeIn4Direction();
        String x = findXTextByAttribute(attribute);
        String y = findYTextByAttribute(attribute);
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"7\" presetClass=\"entr\" presetSubtype=\"");
        sb.append(attribute.getCode());
        sb.append("\" fill=\"hold\" grpId=\"0\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr><p:to>" +
                "<p:strVal val=\"visible\"/></p:to></p:set><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr " +
                "additive=\"base\"><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_x</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst>" +
                "<p:tav tm=\"0\" fmla=\"\"><p:val><p:strVal val=\"");
        sb.append(x);
        sb.append("\"/></p:val></p:tav><p:tav tm=\"100000\" fmla=\"\"><p:val><p:strVal val=\"#ppt_x\"/></p:val>" +
                "</p:tav></p:tavLst></p:anim><p:anim calcmode=\"lin\" valueType=\"num\"><p:cBhvr additive=\"base\">" +
                "<p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_y</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst>" +
                "<p:tav tm=\"0\" fmla=\"\"><p:val><p:strVal val=\"");
        sb.append(y);
        sb.append("\"/></p:val></p:tav><p:tav tm=\"100000\" fmla=\"\"><p:val><p:strVal val=\"#ppt_y\"/></p:val>" +
                "</p:tav></p:tavLst></p:anim></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toBoxXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        AnimationAttribute attribute = findValidTypeInOrOut();
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"4\" presetClass=\"entr\" presetSubtype=\"");
        sb.append(attribute.getCode());
        sb.append("\" fill=\"hold\" grpId=\"0\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/>" +
                "</p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl>" +
                "<p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst>" +
                "</p:cBhvr><p:to><p:strVal val=\"visible\"/></p:to></p:set><p:animEffect transition=\"in\" filter=\"box(");
        sb.append(attribute.getEnName());
        sb.append(")\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl></p:cBhvr></p:animEffect></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private AnimationAttribute findValidTypeHorizontalOrVertical() {
        AnimationAttribute attribute = AnimationAttribute.HORIZONTAL;
        if (Objects.nonNull(animationAttribute)
                && Lists.newArrayList(AnimationAttribute.HORIZONTAL, AnimationAttribute.VERTICAL).contains(animationAttribute)) {
            attribute = animationAttribute;
        }
        return attribute;
    }

    private AnimationAttribute findValidTypeInOrOut() {
        AnimationAttribute attribute = AnimationAttribute.IN;
        if (Objects.nonNull(animationAttribute)
                && Lists.newArrayList(AnimationAttribute.IN, AnimationAttribute.OUT).contains(animationAttribute)) {
            attribute = animationAttribute;
        }
        return attribute;
    }

    private int toFlyIntoXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        AnimationAttribute attribute = AnimationAttribute.LEFT;
        if (Objects.nonNull(animationAttribute)
                && Lists.newArrayList(AnimationAttribute.BOTTOM, AnimationAttribute.TOP, AnimationAttribute.LEFT,
                AnimationAttribute.RIGHT, AnimationAttribute.LEFT_TOP, AnimationAttribute.LEFT_BOTTOM,
                AnimationAttribute.RIGHT_TOP, AnimationAttribute.RIGHT_BOTTOM).contains(animationAttribute)) {
            attribute = animationAttribute;
        }
        String x = findXTextByAttribute(attribute);
        String y = findYTextByAttribute(attribute);
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"2\" presetClass=\"entr\" presetSubtype=\"");
        sb.append(attribute.getCode());
        sb.append("\" fill=\"hold\" grpId=\"0\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/>" +
                "</p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"1\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl>" +
                "<p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst></p:cBhvr>" +
                "<p:to><p:strVal val=\"visible\"/></p:to></p:set><p:anim calcmode=\"lin\" valueType=\"num\">" +
                "<p:cBhvr additive=\"base\"><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_x</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst>" +
                "<p:tav tm=\"0\"><p:val><p:strVal val=\"");
        sb.append(x);
        sb.append("\"/></p:val></p:tav><p:tav tm=\"100000\"><p:val>" +
                "<p:strVal val=\"#ppt_x\"/></p:val></p:tav></p:tavLst></p:anim><p:anim calcmode=\"lin\" valueType=\"num\">" +
                "<p:cBhvr additive=\"base\"><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>ppt_y</p:attrName></p:attrNameLst></p:cBhvr><p:tavLst>" +
                "<p:tav tm=\"0\"><p:val><p:strVal val=\"");
        sb.append(y);
        sb.append("\"/></p:val></p:tav><p:tav tm=\"100000\">" +
                "<p:val><p:strVal val=\"#ppt_y\"/></p:val></p:tav></p:tavLst></p:anim></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private String findYTextByAttribute(AnimationAttribute attribute) {
        String y = "";
        if (AnimationAttribute.RIGHT_TOP.equals(attribute) || AnimationAttribute.LEFT_TOP.equals(attribute)
                || AnimationAttribute.TOP.equals(attribute)) {
            y = "0-#ppt_h/2";
        } else if (AnimationAttribute.RIGHT_BOTTOM.equals(attribute) || AnimationAttribute.LEFT_BOTTOM.equals(attribute)
                || AnimationAttribute.BOTTOM.equals(attribute)) {
            y = "1+#ppt_h/2";
        } else {
            y = "#ppt_y";
        }
        return y;
    }

    private String findXTextByAttribute(AnimationAttribute attribute) {
        String x = "";
        if (AnimationAttribute.LEFT.equals(attribute) || AnimationAttribute.LEFT_TOP.equals(attribute)
                || AnimationAttribute.LEFT_BOTTOM.equals(attribute)) {
            x = "0-#ppt_w/2";
        } else if (AnimationAttribute.RIGHT.equals(attribute) || AnimationAttribute.RIGHT_TOP.equals(attribute)
                || AnimationAttribute.RIGHT_BOTTOM.equals(attribute)) {
            x = "1+#ppt_w/2";
        } else {
            x = "#ppt_x";
        }
        return x;
    }

    private int toAppearXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"1\" presetClass=\"entr\" presetSubtype=\"0\" fill=\"hold\" grpId=\"0\"" +
                " nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst><p:childTnLst>" +
                "<p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"1\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn>" +
                "<p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName></p:attrNameLst>" +
                "</p:cBhvr><p:to><p:strVal val=\"visible\"/></p:to></p:set></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private int toWipeXml(int realId, int elementId, StringBuilder sb, String typeStr) {
        AnimationAttribute attribute = findValidTypeIn4Direction();
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"22\" presetClass=\"entr\" presetSubtype=\"");
        sb.append(attribute.getCode());
        sb.append("\" fill=\"hold\" grpId=\"0\" nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"0\"/>" +
                "</p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl>" +
                "<p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName>" +
                "</p:attrNameLst></p:cBhvr><p:to><p:strVal val=\"visible\"/></p:to></p:set><p:animEffect" +
                " transition=\"in\" filter=\"wipe(");
        sb.append(attribute.getEnName());
        sb.append(")\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl></p:cBhvr></p:animEffect></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private AnimationAttribute findValidTypeIn4TiltDirectionAndAcross() {
        AnimationAttribute attribute = AnimationAttribute.ACROSS;
        if (Objects.nonNull(animationAttribute)
                && Lists.newArrayList(AnimationAttribute.ACROSS, AnimationAttribute.LEFT_TOP, AnimationAttribute.LEFT_BOTTOM,
                AnimationAttribute.RIGHT_BOTTOM, AnimationAttribute.RIGHT_TOP).contains(animationAttribute)) {
            attribute = animationAttribute;
        }
        return attribute;
    }

    private AnimationAttribute findValidTypeIn4TiltDirection() {
        AnimationAttribute attribute = AnimationAttribute.LEFT_TOP;
        if (Objects.nonNull(animationAttribute)
                && Lists.newArrayList(AnimationAttribute.LEFT_TOP, AnimationAttribute.LEFT_BOTTOM,
                AnimationAttribute.RIGHT_BOTTOM, AnimationAttribute.RIGHT_TOP).contains(animationAttribute)) {
            attribute = animationAttribute;
        }
        return attribute;
    }

    private AnimationAttribute findValidTypeIn4Direction() {
        AnimationAttribute attribute = AnimationAttribute.BOTTOM;
        if (Objects.nonNull(animationAttribute)
                && Lists.newArrayList(AnimationAttribute.BOTTOM, AnimationAttribute.TOP,
                AnimationAttribute.LEFT, AnimationAttribute.RIGHT).contains(animationAttribute)) {
            attribute = animationAttribute;
        }
        return attribute;
    }

    private int toBlindsXml(int realId, int elementId, StringBuilder sb, int delayTime, String typeStr) {
        sb.append("<p:par><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" presetID=\"3\" presetClass=\"entr\" presetSubtype=\"10\" fill=\"hold\" grpId=\"0\" " +
                "nodeType=\"");
        sb.append(typeStr);
        sb.append("\"><p:stCondLst><p:cond delay=\"");
        sb.append(delayTime);
        sb.append("\"/></p:stCondLst><p:childTnLst><p:set><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\" fill=\"hold\"><p:stCondLst><p:cond delay=\"0\"/></p:stCondLst></p:cTn><p:tgtEl>" +
                "<p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl><p:attrNameLst><p:attrName>style.visibility</p:attrName>" +
                "</p:attrNameLst></p:cBhvr><p:to><p:strVal val=\"visible\"/></p:to></p:set><p:animEffect " +
                "transition=\"in\" filter=\"blinds(horizontal)\"><p:cBhvr><p:cTn id=\"");
        sb.append(++realId);
        sb.append("\" dur=\"");
        sb.append(timeMs);
        sb.append("\"/><p:tgtEl><p:spTgt spid=\"");
        sb.append(elementId);
        sb.append("\"/></p:tgtEl></p:cBhvr></p:animEffect></p:childTnLst></p:cTn></p:par>");
        return realId;
    }

    private void initialization() {
        if (Objects.isNull(this.inAnimationType)) {
            this.inAnimationType = InAnimationType.WIPE;
        }
    }
}
