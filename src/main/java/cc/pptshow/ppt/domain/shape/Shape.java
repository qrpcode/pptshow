package cc.pptshow.ppt.domain.shape;

public abstract class Shape {

    /**
     * 获取prstGeom节点的xml
     */
    public abstract String prstGeomXmlGet();

    public abstract Shape clone();

}
