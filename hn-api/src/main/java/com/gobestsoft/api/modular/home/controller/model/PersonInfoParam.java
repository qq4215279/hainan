package com.gobestsoft.api.modular.home.controller.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by duanmu on 2018/10/9.
 */
@Data
public class PersonInfoParam {

    @NotEmpty(message = "姓名不能为空")
    private String name;

    @NotEmpty(message = "性别不能为空")
    private String sex;

    @NotEmpty(message = "出生日期不能为空")
    private String birthday;

    @NotEmpty(message = "民族不能为空")
    private String nation;

    @NotEmpty(message = "就业状况不能为空")
    private String workSituation;

    @NotEmpty(message = "身份证号不能为空")
    private String certificateNum;

    @NotEmpty(message = "学历不能为空")
    private String education;

    @NotEmpty(message = "技术等级不能为空")
    private String technologyLevel;

    @NotEmpty(message = "手机号不能为空")
    private String mobile;

    @NotEmpty(message = "户籍类型不能为空")
    private String household;

    private String domicile;

    @NotEmpty(message = "政治面貌不能为空")
    private String politicalStatus;

    @NotEmpty(message = "所在单位不能为空")
    private String workUnit;

    private Integer deptId;

    @NotEmpty(message = "是否为农民工不能为空")
    private String isFarmer;

}
