package cc.pptshow.ppt.element;

import cc.pptshow.ppt.domain.PPTElementXml;

public interface PPTInnerElement extends Cloneable {

    PPTElementXml toXml();

    PPTInnerElement clone();

}
