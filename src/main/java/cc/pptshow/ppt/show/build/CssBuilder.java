package cc.pptshow.ppt.show.build;

import java.util.Map;

public interface CssBuilder {

    /**
     * css本质上会以一个Map的形式存储，所有css设定的builder都需要包含一个获取用户设定map的方法
     * 我们不建议您直接获取map对象并向其中添加内容
     * 虽然这样确实能运行 :)
     *
     * @return 返回css map
     */
    Map<String, String> getCssMap();

}
