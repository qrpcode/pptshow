package cc.pptshow.ppt.exception;

public class PPTShowCreateException extends RuntimeException{

    public PPTShowCreateException() {
        super();
    }

    public PPTShowCreateException(String msg) {
        super(msg);
    }

    public PPTShowCreateException(Throwable cause) {
        super(cause);
    }
}
