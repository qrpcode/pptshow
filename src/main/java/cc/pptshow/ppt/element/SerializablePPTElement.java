package cc.pptshow.ppt.element;

import cc.pptshow.ppt.domain.PPTImgCss;
import cc.pptshow.ppt.domain.PPTShapeCss;
import cc.pptshow.ppt.domain.PPTTextCss;
import cc.pptshow.ppt.domain.background.Background;
import cc.pptshow.ppt.domain.background.SerializableBackground;
import cc.pptshow.ppt.domain.border.Border;
import cc.pptshow.ppt.domain.border.ColorBorder;
import cc.pptshow.ppt.domain.border.GradientBorder;
import cc.pptshow.ppt.domain.shape.*;
import cc.pptshow.ppt.element.impl.PPTImg;
import cc.pptshow.ppt.element.impl.PPTLine;
import cc.pptshow.ppt.element.impl.PPTShape;
import cc.pptshow.ppt.element.impl.PPTText;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SerializablePPTElement {

    private PPTElementType pptElementType;

    private String json;

    private BorderType border;

    private SerializableBackground background;

    private ShapeType shape;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public enum ShapeType {
        Ellipse("Ellipse"),
        Parallelogram("Parallelogram"),
        Rect("Rect"),
        RoundRect("RoundRect"),
        SelfShape("SelfShape");
        private String code;

        private static ShapeType toShapeType(Shape shape) {
            if (Objects.isNull(shape)) {
                return null;
            } else if (shape instanceof Ellipse) {
                return Ellipse;
            } else if (shape instanceof Parallelogram) {
                return Parallelogram;
            } else if (shape instanceof Rect) {
                return Rect;
            } else if (shape instanceof RoundRect) {
                return RoundRect;
            } else if (shape instanceof SelfShape) {
                return SelfShape;
            }
            return null;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public enum BorderType {
        ColorBorder("ColorBorder"),
        GradientBorder("GradientBorder");
        private String code;

        public static BorderType toBorderType(Border border) {
            if (Objects.isNull(border)) {
                return null;
            } else if (border instanceof ColorBorder) {
                return ColorBorder;
            } else if (border instanceof GradientBorder) {
                return GradientBorder;
            }
            return null;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public enum PPTElementType {
        PPTImg("PPTImg"),
        PPTLine("PPTLine"),
        PPTShape("PPTShape"),
        PPTText("PPTText");
        private String code;

        public static PPTElementType objectToPPTElementType(PPTElement pptElement) {
            if (pptElement instanceof PPTImg) {
                return PPTImg;
            } else if (pptElement instanceof PPTLine) {
                return PPTLine;
            } else if (pptElement instanceof PPTText) {
                return PPTText;
            } else if (pptElement instanceof PPTShape) {
                return PPTShape;
            }
            return null;
        }
    }

    public static String toJson(PPTElement pptElement) {
        SerializablePPTElement element = buildSerializablePPTElement(pptElement);
        return JSON.toJSONString(element);
    }

    public static String toJson(List<PPTElement> pptElements) {
        List<SerializablePPTElement> elements = pptElements.stream()
                .map(SerializablePPTElement::buildSerializablePPTElement)
                .collect(Collectors.toList());
        return JSON.toJSONString(elements);
    }

    public static List<PPTElement> buildPPTElements(List<SerializablePPTElement> serializablePPTElements) {
        return serializablePPTElements.stream()
                .map(SerializablePPTElement::buildPPTElement)
                .collect(Collectors.toList());
    }

    public static PPTElement buildPPTElement(SerializablePPTElement serializableElement) {
        if (Objects.isNull(serializableElement)) {
            return null;
        }
        if (serializableElement.getPptElementType().equals(PPTElementType.PPTShape)) {
            PPTShape pptShape = JSON.parseObject(serializableElement.getJson(), PPTShape.class);
            pptShape.getCss().setBorder(buildBorder(serializableElement));
            if (Objects.nonNull(serializableElement.getBackground())) {
                pptShape.getCss().setBackground(serializableElement.getBackground().buildBackground());
            }
            pptShape.getCss().setShape(buildShape(serializableElement));
            return pptShape;
        } else if (serializableElement.getPptElementType().equals(PPTElementType.PPTImg)) {
            PPTImg pptImg = JSON.parseObject(serializableElement.getJson(), PPTImg.class);
            pptImg.getCss().setBorder(buildBorder(serializableElement));
            return pptImg;
        } else if (serializableElement.getPptElementType().equals(PPTElementType.PPTText)) {
            PPTText pptText = JSON.parseObject(serializableElement.getJson(), PPTText.class);
            if (Objects.nonNull(serializableElement.getBackground())) {
                pptText.getCss().setBackground(serializableElement.getBackground().buildBackground());
            }
            return pptText;
        } else if (serializableElement.getPptElementType().equals(PPTElementType.PPTLine)) {
            return JSON.parseObject(serializableElement.getJson(), PPTLine.class);
        }
        throw new RuntimeException("命中了未知元素");
    }

    private static Border buildBorder(SerializablePPTElement serializablePPTElement) {
        String borderJson = getBorderJson(serializablePPTElement);
        if (Strings.isEmpty(borderJson)) {
            return null;
        } else if (serializablePPTElement.getBorder().equals(BorderType.ColorBorder)) {
            return JSON.parseObject(borderJson, ColorBorder.class);
        } else if (serializablePPTElement.getBorder().equals(BorderType.GradientBorder)) {
            return JSON.parseObject(borderJson, GradientBorder.class);
        }
        return null;
    }

    private static Shape buildShape(SerializablePPTElement serializablePPTElement) {
        String shapeJson = getShapeJson(serializablePPTElement);
        if (Strings.isEmpty(shapeJson)) {
            return null;
        } else if (serializablePPTElement.getShape().equals(ShapeType.Ellipse)) {
            return JSON.parseObject(shapeJson, Ellipse.class);
        } else if (serializablePPTElement.getShape().equals(ShapeType.Parallelogram)) {
            return JSON.parseObject(shapeJson, Parallelogram.class);
        } else if (serializablePPTElement.getShape().equals(ShapeType.Rect)) {
            return JSON.parseObject(shapeJson, Rect.class);
        } else if (serializablePPTElement.getShape().equals(ShapeType.RoundRect)) {
            return JSON.parseObject(shapeJson, RoundRect.class);
        } else if (serializablePPTElement.getShape().equals(ShapeType.SelfShape)) {
            return JSON.parseObject(shapeJson, SelfShape.class);
        }
        return null;
    }

    private static String getShapeJson(SerializablePPTElement serializableElement) {
        return Optional.ofNullable(((JSONObject)
                JSON.parseObject(serializableElement.getJson())
                        .get("css")))
                .map(jsonObject -> jsonObject.get("shape"))
                .map(Object::toString)
                .orElse(null);
    }

    private static String getBorderJson(SerializablePPTElement serializableElement) {
        return Optional.ofNullable(((JSONObject)
                JSON.parseObject(serializableElement.getJson())
                        .get("css")))
                .map(jsonObject -> jsonObject.get("border"))
                .map(Object::toString)
                .orElse(null);
    }

    public static SerializablePPTElement buildSerializablePPTElement(PPTElement pptElement) {
        String json = JSON.toJSONString(pptElement);
        PPTElementType type = PPTElementType.objectToPPTElementType(pptElement);
        SerializablePPTElement serializablePPTElement = new SerializablePPTElement();
        if (pptElement instanceof PPTShape) {
            PPTShapeCss css = ((PPTShape) pptElement).getCss();
            serializablePPTElement.setBackground(
                    SerializableBackground.buildByBackground(css.getBackground()));
            serializablePPTElement.setBorder(BorderType.toBorderType(css.getBorder()));
            serializablePPTElement.setShape(ShapeType.toShapeType(css.getShape()));
        } else if (pptElement instanceof PPTImg) {
            PPTImgCss css = ((PPTImg) pptElement).getCss();
            serializablePPTElement.setBorder(BorderType.toBorderType(css.getBorder()));
        } else if (pptElement instanceof PPTText) {
            PPTTextCss css = ((PPTText) pptElement).getCss();
            serializablePPTElement.setBackground(
                    SerializableBackground.buildByBackground(css.getBackground()));
        }
        serializablePPTElement.setPptElementType(type);
        serializablePPTElement.setJson(json);
        return serializablePPTElement;
    }

    public static List<SerializablePPTElement> buildSerializablePPTElements(List<PPTElement> pptElements) {
        return pptElements.stream()
                .map(SerializablePPTElement::buildSerializablePPTElement)
                .collect(Collectors.toList());
    }

}
