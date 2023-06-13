package cc.pptshow.ppt.domain.cut.shape;

import cc.pptshow.ppt.domain.cut.CutShape;

public class RectCutShape extends CutShape {
    @Override
    public String getCode() {
        return "rect";
    }

    @Override
    public String getXml() {
        return "<a:avLst/>";
    }

    public RectCutShape clone() {
        return new RectCutShape();
    }
}
