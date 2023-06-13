package cc.pptshow.ppt.domain.shape;

import com.google.common.base.Strings;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户自定义图形
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SelfShape extends Shape{

    /**
     * 用户自己定义的一段XML
     */
    private String custGeom;

    @Override
    public String prstGeomXmlGet() {
        if(Strings.isNullOrEmpty(custGeom)) {
            throw new RuntimeException("自定义图形必须显示指明custGemo");
        }
        return custGeom;
    }

    @Override
    public SelfShape clone() {
        return new SelfShape().setCustGeom(custGeom);
    }
}
