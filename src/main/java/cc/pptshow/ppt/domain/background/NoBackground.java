package cc.pptshow.ppt.domain.background;

import cc.pptshow.ppt.domain.PPTElementXml;
import lombok.Builder;

@Builder
public class NoBackground extends Background {

    @Override
    public PPTElementXml solidFillGet(int mediaId, int relId) {
        PPTElementXml xml = new PPTElementXml();
        xml.setSideXml(new StringBuilder("<a:noFill/>"));
        return xml;
    }

    @Override
    public Background clone() {
        return new NoBackground();
    }
}
