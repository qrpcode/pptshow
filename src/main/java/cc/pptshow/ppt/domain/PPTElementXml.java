package cc.pptshow.ppt.domain;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * 获取解析后的XML信息
 */
@Data
public class PPTElementXml {
    private StringBuilder sideXml;
    private StringBuilder relXml = new StringBuilder();
    private List<FileCopy> file = Lists.newArrayList();

    private int idAdd = 0;
}
