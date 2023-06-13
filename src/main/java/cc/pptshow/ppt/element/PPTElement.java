package cc.pptshow.ppt.element;

import cc.pptshow.ppt.domain.PPTElementXml;
import cc.pptshow.ppt.domain.animation.AnimationElement;
import lombok.Getter;
import lombok.Setter;

public abstract class PPTElement extends AnimationElement implements Cloneable {

    /**
     * 这个是元素在页面中的唯一id标识
     * 比如让元素进行指定的开场动画等场景都需要使用，所以必须全局唯一
     * 使用的时候不需要手动指定，系统会自动在生成文件的时候进行分发id
     */
    @Getter
    @Setter
    private int id;

    /**
     * 标识，没有任何含义，不会写入到生成的文件中
     */
    @Getter
    @Setter
    private String remark;

    public abstract PPTElementXml toXml(int relIdBegin, int mediaIdBegin);

    @Override
    public abstract PPTElement clone();

}
