public enum ErrorCodes {
    KEY_OUT_OF_RANGE_ERROR(1),
    NOT_FOUND_ELEMENT_ERROR(2),
    NOT_FOUND_FILE_ERROR(3);

    private int code;

    ErrorCodes(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
