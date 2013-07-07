
package exceptions;


public class VException extends RuntimeException {
    
    public VException(String message, int errorCode) {
        super(message);
    }
    
    public static class ErrorResult {
        public String error;
    }
    
    public ErrorResult toResult() {
        ErrorResult r = new ErrorResult();
        r.error = getMessage();
        return r;
    }
}
