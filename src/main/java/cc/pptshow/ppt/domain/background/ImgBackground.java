package cc.pptshow.ppt.domain.background;

import cc.pptshow.ppt.constant.Constant;
import cc.pptshow.ppt.domain.FileCopy;
import cc.pptshow.ppt.domain.PPTElementXml;
import cc.pptshow.ppt.util.FileUtil;
import cc.pptshow.ppt.util.XmlCodeUtil;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ImgBackground extends Background{

    private String img;

    private static final String TEMP_A_IMG_FILL_1 = "<a:blipFill rotWithShape=\"1\"><a:blip r:embed=\"rId";
    private static final String TEMP_A_IMG_FILL_2 = "\"/><a:stretch><a:fillRect/></a:stretch></a:blipFill>";

    @Override
    public PPTElementXml solidFillGet(int mediaId, int relId) {
        PPTElementXml xml = new PPTElementXml();
        String media = FileUtil.getMediaImgName(mediaId, img);
        xml.setRelXml(new StringBuilder(XmlCodeUtil.imgGet(relId, media)));
        xml.setSideXml(new StringBuilder(TEMP_A_IMG_FILL_1 + relId + TEMP_A_IMG_FILL_2));
        xml.setFile(Lists.newArrayList(new FileCopy().setFrom(img).setTo(Constant.MEDIA + media)));
        xml.setIdAdd(1);
        return xml;
    }

    @Override
    public Background clone() {
        return new ImgBackground().setImg(img);
    }
}
