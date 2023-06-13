package cc.pptshow.ppt.domain.cut.shape;

import cc.pptshow.ppt.domain.cut.CutShape;

public class EllipseCutShape extends CutShape {
    @Override
    public String getCode() {
        return "ellipse";
    }

    @Override
    public String getXml() {
        return "<a:avLst/>";
    }

    @Override
    public EllipseCutShape clone() {
        return new EllipseCutShape();
    }

}
