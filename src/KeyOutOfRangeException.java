public class KeyOutOfRangeException extends Exception {
    private Integer errorCode;
    public KeyOutOfRangeException(String message) {
        super(message);
    }

    public KeyOutOfRangeException(Throwable cause) {
        super(cause);
    }

    public KeyOutOfRangeException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public KeyOutOfRangeException(String message, Throwable cause, ErrorCodes errorCode) {
        super(message, cause);
        this.errorCode = errorCode.getCode();
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
