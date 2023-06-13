package cc.pptshow.ppt.domain.animation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimationVo {

    /**
     * 流水序号
     */
    private int realId;

    /**
     * 延迟时间
     */
    private int delay;

    /**
     * 最后的统计序列
     */
    private StringBuilder elementStr;

}
