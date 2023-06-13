package cc.pptshow.ppt.domain.background;

import cc.pptshow.ppt.domain.PPTElementXml;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public abstract class Background implements Cloneable {

    public abstract PPTElementXml solidFillGet(int mediaId, int relId);

    public abstract Background clone();

}
