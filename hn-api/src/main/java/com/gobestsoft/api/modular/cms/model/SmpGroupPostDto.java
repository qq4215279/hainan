package com.gobestsoft.api.modular.cms.model;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * create by gutongwei
 * on 2018/6/6 下午3:55
 */
@Data
public class SmpGroupPostDto implements Serializable {
    /**
     * 帖子ID
     */
    private int post_id;

    /**
     * 用户id
     */
    private String auid;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 标题
     */
    private String title;

    /**
     * 动态简介
     */
    private String content;

    /**
     * 封面图片
     */
    private String cover;

    /**
     * 创建时间
     */
    private String create_time;

    /**
     * 发布主题时间戳 (到January 1, 1970, 00:00:00毫秒）
     */
    private long create_timestamp;

    /**
     * 动态评论数量
     */
    private Integer comment_num;

    private int show_type;

}
