package com.gobestsoft.common.modular.dao.model;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("app_integral_update")
public class AppIntegralUpdatePojo extends Model<AppIntegralUpdatePojo> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    private Integer id;

    private String auid;
    private Integer type;
    private Integer jifen;
    private String tid;
    private String goodsMessage;

    public AppIntegralUpdatePojo() {
    }

    public AppIntegralUpdatePojo(int type, String tid) {
        this.type = type;
        this.tid = tid;
    }

    public AppIntegralUpdatePojo(String auid, Integer type, Integer jifen, String tid, String goodsMessage) {
        this.auid = auid;
        this.type = type;
        this.jifen = jifen;
        this.tid = tid;
        this.goodsMessage = goodsMessage;
    }
}
