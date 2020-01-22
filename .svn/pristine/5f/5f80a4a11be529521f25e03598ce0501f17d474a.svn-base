package com.gobestsoft.api.modular.basic;

import lombok.Data;

/**
 * 接口范围定义规范类型
 */
@Data
public class BaseResult {

    public BaseResult() {
    }

    public BaseResult(int code, String message, Object data) {
        if (data == null) {
            data = new Object();
        }
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResult(ResultCode code, Object data) {
        if (data == null) {
            data = new Object();
        }
        this.code = code.getCode();
        this.message = code.getName();
        this.data = data;
    }

    private int code;

    private String message;

    private Object data;

    /**
     * 错误状态码
     * 用300以上定义
     */
    public enum ResultCode {
        ERROR100("用户未登录", 100),
        ERROR100_2("您的信息已变更，请重新登录", 100),
        ERROR101("手机号并非登录用户手机号", 101),
        ERROR102("帐号不存在", 102),
        ERROR103("用户名或密码错误", 103),
        ERROR104("手机号或验证码错误", 104),
        ERROR105("当前账号未绑定会员，请先进行身份认证", 105),
        ERROR400("服务器异常", 400),
        ERROR401("当前时间与服务器时间不符", 401),
        ERROR402("签名验证失败", 402),
        ERROR403("签名验证失败，签名未传递", 402),
        ERROR500("服务器异常", 500),
        ERROR501("签权失败", 501),
        ERROR502("未检索到被叫号码", 502),
        ERROR503("被叫号码过多", 503),
        ERROR504("内容未签名", 504),
        ERROR505("内容过长", 505),
        ERROR506("余额不足", 506),
        ERROR507("暂停发送", 507),
        ERROR508("保留", 508),
        ERROR509("定时发送时间格式错误", 509),
        ERROR510("下发内容为空", 510),
        ERROR511("账户无效", 511),
        ERROR512("Ip地址非法", 512),
        ERROR513("操作频率快", 513),
        ERROR514("操作失败", 514),
        ERROR515("拓展码无效", 515),
        ERROR516("取消定时,SeqId错误", 516),
        ERROR517("未开通报告", 517),
        ERROR518("暂留", 518),
        ERROR519("未开通上行", 519),
        ERROR520("暂留", 520),
        ERROR521("包含屏蔽词", 521),
        ERROR522("系统错误", 522),
        ERROR523("手机号错误", 523),
        ERROR523_2("获取验证码次数过多，请稍后再试",523),
        ERROR524("账号已存在", 524),
        ERROR525("验证码错误", 525);
        // 成员变量
        private String name;
        private int code;

        // 构造方法
        private ResultCode(String name, int code) {
            this.name = name;
            this.code = code;
        }

        // 普通方法
        public static String getName(int index) {
            for (ResultCode c : ResultCode.values()) {
                if (c.getCode() == index) {
                    return c.name;
                }
            }
            return null;
        }

        // get set 方法
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
