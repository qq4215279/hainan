package com.gobestsoft.common.modular.smp.model;

import java.io.Serializable;
import java.util.List;

import com.gobestsoft.core.util.EmojiUtil;

import com.gobestsoft.core.util.DateUtil;
import com.gobestsoft.core.util.WebSiteUtil;
import com.vdurmont.emoji.EmojiParser;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * create by gutongwei
 * on 2018/6/6 下午2:08
 */
@Data
public class SmpGroupDetailEntity implements Serializable {

    /**
     * 小组id
     */
    private int id;

    /**
     * 小组名称
     */
    private String name;
    
    /**
     * 创建人id(即：组长id)
     */
    private String auid;

    /**
     * 小组头像
     */
    private String cover;

    /**
     * 小组简介
     */
    private String introduce;

    /**
     * 小组已有成员数量
     */
    private int member_num;

    /**
     * 小组最大人数
     */
    private int max_member;

    /**
     * 创建时间
     */
    private String create_time;

    /**
     * 小组类型
     */
    private int type;

    private String type_desc;

    /**
     * 是否置顶（0:否;1:是）
     */
    private int is_top;

    /**
     * 模式类型
     */
    private int display_type;

    private String display_type_desc;

    private int join_type;

    /**
     * 小组状态【0：待审核】【1：审核通过】
     */
    private int status;

    /**
     * 小组已有成员
     */
    private List<SmpGroupMemberEntity> members;


    public String getCreate_time() {
        if (StringUtils.isEmpty(this.create_time)) {
            return "";
        }
        return DateUtil.getTimeDistance(create_time, "yyyyMMddHHmmss");
    }

    public String getName() {
        return EmojiUtil.parseToUnicode(name);
    }

    public String getCover() {
        return WebSiteUtil.getBrowseUrl(cover);
    }

    public String getIntroduce() {
        return EmojiParser.parseToUnicode(introduce);
    }

    public String getDisplay_type_desc() {
        return display_type == 0 ? "公开模式" : "小组模式";
    }


    public int getJoin_type() {
        return member_num >= max_member ? 2 : join_type;
    }
}
