package cc.pptshow.ppt.domain;

import lombok.Getter;

@Getter
public class LocationCss {
    private double left;
    private double top;
    private double width;
    private double height;

    public Object setLeft(double left) {
        throw new RuntimeException("请复制一份使用！");
    }

    public Object setTop(double top) {
        throw new RuntimeException("请复制一份使用！");
    }

    public Object setWidth(double width) {
        throw new RuntimeException("请复制一份使用！");
    }

    public Object setHeight(double height) {
        throw new RuntimeException("请复制一份使用！");
    }
}
