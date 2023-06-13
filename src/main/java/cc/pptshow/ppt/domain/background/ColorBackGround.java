package cc.pptshow.ppt.domain.background;

import cc.pptshow.ppt.domain.Gradient;
import cc.pptshow.ppt.domain.PPTElementXml;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ColorBackGround extends Background{

    private static final String TEMP_A_SOLID_FILL_1 = "<a:solidFill><a:srgbClr val=\"";
    private static final String TEMP_A_SOLID_FILL_2 = "\"/></a:solidFill>";
    private static final String TEMP_A_SOLID_FILL_ALPHA_3 = "\"><a:alpha val=\"";
    private static final String TEMP_A_SOLID_FILL_ALPHA_4 = "\"/></a:srgbClr></a:solidFill>";

    private String color;

    /**
     * 透明度 1-100，数字越大越透明
     */
    private Double alpha;

    public ColorBackGround setAlpha(Double alpha) {
        if (Objects.nonNull(alpha) && alpha > 0 && alpha <= 100) {
            this.alpha = alpha;
        }
        return this;
    }

    /**
     * color不要带#号
     */
    public static ColorBackGround buildByColor(String color) {
        return new ColorBackGround().setColor(color);
    }

    public static ColorBackGround buildByColor(String color, double alpha) {
        return new ColorBackGround().setColor(color).setAlpha(alpha);
    }

    @Override
    public PPTElementXml solidFillGet(int mediaId, int relId) {
        PPTElementXml xml = new PPTElementXml();
        if (Objects.isNull(alpha) || alpha < 1 || alpha > 100) {
            xml.setSideXml(new StringBuilder(TEMP_A_SOLID_FILL_1 + color + TEMP_A_SOLID_FILL_2));
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(TEMP_A_SOLID_FILL_1)
                    .append(color)
                    .append(TEMP_A_SOLID_FILL_ALPHA_3)
                    .append(Math.round((100 - alpha) * 1000))
                    .append(TEMP_A_SOLID_FILL_ALPHA_4);
            xml.setSideXml(sb);
        }
        return xml;
    }

    @Override
    public ColorBackGround clone() {
        return new ColorBackGround().setColor(color).setAlpha(alpha);
    }
}
