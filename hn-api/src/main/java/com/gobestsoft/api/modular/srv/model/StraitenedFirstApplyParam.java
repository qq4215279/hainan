package com.gobestsoft.api.modular.srv.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 困难帮扶初审表
 * create by gutongwei
 * on 2018/7/16 下午4:31
 */
@Data
public class StraitenedFirstApplyParam {

    private String id;

    /**
     * 姓名
     */
    @NotEmpty(message = "姓名不能为空")
    private String name;

    /**
     * 身份证人像面
     */
    private String identityFace;

    /**
     * 身份证国徽面
     */
    private String cardNationalEmblem;

    /**
     * 承诺书
     */
    private String commitLetter;

    /**
     * 所在单位证明
     */
    private String unitProve;

    /**
     * 附件材料
     */
    private String attachments;

    private String attachmentsPath;

    /**
     * 身份证
     */
    @NotEmpty(message = "身份证不能为空")
    private String certificateNum;


    /**
     * 工作单位
     */
    private String workUnit;
}
