package com.gobestsoft.common.modular.smp.model;

import com.gobestsoft.core.util.WebSiteUtil;
import com.vdurmont.emoji.EmojiParser;
import lombok.Data;

import java.io.Serializable;

/**
 * create by gutongwei
 * on 2018/6/6 下午2:14
 */
@Data
public class SmpGroupMemberEntity implements Serializable {

    private String auid;

    private String nickname;

    private String avatar;

    public String getAvatar() {
        return WebSiteUtil.getBrowseUrl(avatar);
    }

    public String getNickname() {
        return EmojiParser.parseToUnicode(nickname);
    }
}
