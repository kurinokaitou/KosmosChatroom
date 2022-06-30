package serializable;

public enum ResponseStatus {
    SUCCESS,    // 成功
    FAILED,     // 一般是访问资源不可访问造成的失败
    INTERNAL_ERROR,
    NOT_FOUND   // 一般是访问资源不存在造成的失败
}
