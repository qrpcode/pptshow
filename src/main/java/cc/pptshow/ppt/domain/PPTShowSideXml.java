package cc.pptshow.ppt.domain;

import lombok.Data;

import java.util.List;

/**
 * 一个页面里里面的XML
 */
@Data
public class PPTShowSideXml {
    private StringBuilder side;
    private StringBuilder rel;
    private List<FileCopy> file;
}
