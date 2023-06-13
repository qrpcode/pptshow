package cc.pptshow.ppt.domain.border;

import lombok.Data;

@Data
public abstract class Border implements Cloneable {

    public abstract String aLnGet();

    public abstract Border clone();

}
