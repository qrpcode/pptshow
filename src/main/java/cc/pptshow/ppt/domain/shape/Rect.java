package cc.pptshow.ppt.domain.shape;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Rect extends Shape{

    @Override
    public String prstGeomXmlGet() {
        return "<a:prstGeom prst=\"rect\"><a:avLst/></a:prstGeom>";
    }

    @Override
    public Rect clone() {
        return new Rect();
    }
}
