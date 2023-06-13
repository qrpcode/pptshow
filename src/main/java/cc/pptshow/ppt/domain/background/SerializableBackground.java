package cc.pptshow.ppt.domain.background;

import cc.pptshow.ppt.domain.Gradient;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 读取的背景色bean
 */
@Data
public class SerializableBackground implements Serializable {

    private int backgroundType;

    /**
     * GRADIENT使用，0~360 度
     */
    private Double gradientDirection;

    /**
     * GRADIENT使用，渐变色
     */
    private List<Gradient> gradientList;

    /**
     * COLOR使用，渐变色
     */
    private String color;

    /**
     * COLOR使用，透明度 1-100，数字越大越透明
     */
    private Double alpha;


    public enum BackgroundType {
        COLOR(1, "纯色背景"),
        GRADIENT(2, "渐变配色"),
        IMG(3, "图片背景"),
        NO_FILL(4, "无填充");
        private final int code;
        private final String name;

        BackgroundType(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public static BackgroundType buildByCode(int code) {
            for (BackgroundType value : BackgroundType.values()) {
                if (value.code == code) {
                    return value;
                }
            }
            return null;
        }
    }

    public Background buildBackground() {
        BackgroundType backgroundType = BackgroundType.buildByCode(this.backgroundType);
        assert backgroundType != null;
        if (backgroundType.equals(BackgroundType.COLOR)) {
            if (Objects.isNull(alpha)) {
                return ColorBackGround.buildByColor(color);
            }
            return ColorBackGround.buildByColor(color, 100 - (alpha / 1000));
        }
        if (backgroundType.equals(BackgroundType.GRADIENT)) {
            GradientBackground gradientBackground = new GradientBackground();
            gradientBackground.setGradient(gradientList);
            gradientBackground.setGradientDirection(gradientDirection);
            return gradientBackground;
        }
        if (backgroundType.equals(BackgroundType.IMG)) {
            return new ImgBackground();
        }
        if (backgroundType.equals(BackgroundType.NO_FILL)) {
            return NoBackground.builder().build();
        }
        return null;
    }

    public static SerializableBackground buildByBackground(Background background) {
        if (background instanceof ColorBackGround) {
            SerializableBackground serializableBackground = new SerializableBackground();
            if (Objects.nonNull(((ColorBackGround) background).getAlpha())) {
                serializableBackground.setAlpha(1000 * (100 - ((ColorBackGround) background).getAlpha()));
            }
            serializableBackground.setColor(((ColorBackGround) background).getColor());
            serializableBackground.setBackgroundType(BackgroundType.COLOR.code);
            return serializableBackground;
        } else if (background instanceof GradientBackground) {
            SerializableBackground serializableBackground = new SerializableBackground();
            serializableBackground.setBackgroundType(BackgroundType.GRADIENT.code);
            serializableBackground.setGradientList(((GradientBackground) background).getGradient());
            serializableBackground.setGradientDirection(((GradientBackground) background).getGradientDirection());
            return serializableBackground;
        } else if (background instanceof NoBackground) {
            SerializableBackground serializableBackground = new SerializableBackground();
            serializableBackground.setBackgroundType(BackgroundType.NO_FILL.code);
            return serializableBackground;
        } else if (background instanceof ImgBackground) {
            SerializableBackground serializableBackground = new SerializableBackground();
            serializableBackground.setBackgroundType(BackgroundType.IMG.code);
            return serializableBackground;
        }
        return null;
    }
}
