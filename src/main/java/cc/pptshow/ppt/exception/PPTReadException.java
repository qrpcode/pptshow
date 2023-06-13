package cc.pptshow.ppt.exception;

public class PPTReadException extends RuntimeException{

    public PPTReadException() {
        super();
    }

    public PPTReadException(String msg) {
        super(msg);
    }

    public PPTReadException(Throwable cause) {
        super(cause);
    }
}
