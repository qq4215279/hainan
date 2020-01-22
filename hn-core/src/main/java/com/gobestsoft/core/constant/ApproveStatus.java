package com.gobestsoft.core.constant;

/**
 * 审核状态的枚举
 *
 * @author caoy
 * @date 2017-11-27
 */
public enum ApproveStatus {

	VERIFYING(-1, "待审核"),
	REJECTED(0, "审核拒绝"),
	ACCEPTED(1, "审核通过");

    int code;
    String message;

    ApproveStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String valueOf(Integer status) {
        if (status == null) {
            return "";
        } else {
            for (ApproveStatus s : ApproveStatus.values()) {
                if (s.getCode() == status) {
                    return s.getMessage();
                }
            }
            return "";
        }
    }
}
