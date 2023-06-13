package cc.pptshow.ppt.show.build.impl;

import cc.pptshow.ppt.constant.SideConstant;
import cc.pptshow.ppt.show.build.CssBuilder;
import com.google.common.collect.Maps;

import java.util.Map;

public class SideCssBuilder implements CssBuilder {

    private final Map<String, String> CSS_MAP = Maps.newHashMap();

    @Override
    public Map<String, String> getCssMap() {
        return CSS_MAP;
    }

    public static SideCssBuilder build() {
        return new SideCssBuilder();
    }

    /**
     * 设定默认底色
     * @param color 颜色值，16进制表示法
     * @return 返回builder
     */
    public SideCssBuilder background(String color) {
        CSS_MAP.put(SideConstant.BACKGROUND_COLOR, color);
        return this;
    }
}
