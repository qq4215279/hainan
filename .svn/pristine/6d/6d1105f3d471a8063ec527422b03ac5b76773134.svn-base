package com.gobestsoft.common.modular.contract.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.Data;

/**
 * 集体合同母版表
 */
@Data
@TableName("collective_contract_master")
public class CollectiveContractMaster extends Model<CollectiveContractMaster> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 母版名称
     */
    private String name;
    /**
     * 模板编号
     */
    private String code;

    private String createUser;
    private String createTime;
    private String updateUser;
    private String updateTime;

    /**
     * 是否删除字典： 0:为删除。1：已删除。
     */
    private Integer delFlg;

    /**
     * 母版Id master_id
     */
    private Integer masterId;
    /**
     * 组织ID union_id
     */
    private Integer unionId;


    private String filePath;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
