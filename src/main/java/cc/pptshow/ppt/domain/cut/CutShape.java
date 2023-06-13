package cc.pptshow.ppt.domain.cut;

public abstract class CutShape implements Cloneable {

    public abstract String getCode();

    public abstract String getXml();

    public abstract CutShape clone();

}
