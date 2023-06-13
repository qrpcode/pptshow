package cc.pptshow.ppt.exception;

public class PPTShowGenerateException extends RuntimeException{

    public PPTShowGenerateException() {
        super();
    }

    public PPTShowGenerateException(String msg) {
        super(msg);
    }

    public PPTShowGenerateException(Throwable cause) {
        super(cause);
    }
}
