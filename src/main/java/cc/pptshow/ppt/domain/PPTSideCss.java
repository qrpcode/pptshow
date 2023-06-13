package cc.pptshow.ppt.domain;

import cc.pptshow.ppt.domain.background.Background;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PPTSideCss {

    private Background background;

    public static PPTSideCss build() {
        return new PPTSideCss();
    }

}
