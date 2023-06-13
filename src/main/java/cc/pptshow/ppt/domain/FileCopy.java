package cc.pptshow.ppt.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FileCopy {
    private String from;
    private String to;
}
