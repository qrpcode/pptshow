package cc.pptshow.ppt.domain.shape;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Ellipse extends Shape{

    @Override
    public String prstGeomXmlGet() {
        return "<a:prstGeom prst=\"ellipse\"><a:avLst/></a:prstGeom>";
    }

    @Override
    public Ellipse clone() {
        return new Ellipse();
    }
}
