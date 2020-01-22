package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * 帮助互助表
 *
 * @author xiashasha
 */
@TableName("srv_straitened_first")
@Data
public class SrvStraitenedFirstPojo extends Model<SrvStraitenedFirstPojo> {


    /**
     * 困难帮扶初审ID
     */
    private Integer id;
    /**
     * 申请用户id
     */
    private String auid;

    /**
     * 审查状态【0：待查看，可撤回状态】【1：待处理】【2：通过】【3：拒绝】
     */
    private Integer status;
    /**
     * 姓名
     */
    private String name;
    /**
     * 身份证号
     */
    private String certificateNum;
    /**
     * 工作单位
     */
    private String workUnit;

    /**
     * 身份证人像面 identity_face
     */
    private String identityFace;

    /**
     * 身份证国徽面 card_national_emblem
     */
    private String cardNationalEmblem;

    /**
     * 承诺书 commit_letter
     */
    private String commitLetter;

    /**
     * 所在单位证明 unit_prove
     */
    private String unitProve;
    /**
     * 附件材料
     */
    private String attachments;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 困难帮扶类型
     * @return
     */
    private String straitenedType;

    private String evidence;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
