package com.gobestsoft.common.modular.cms.model;

import com.gobestsoft.core.util.DateUtil;
import com.vdurmont.emoji.EmojiParser;

public class ReplyContent {

	private int id;

	private String userId;

	private String avatar;

	private String nickname;

	private String reply_content;

	private int is_give_thumbs_up;

	private int thumbs_up_total;

	private String create_time;

	public int getThumbs_up_total() {
		return thumbs_up_total;
	}

	public void setThumbs_up_total(int thumbs_up_total) {
		this.thumbs_up_total = thumbs_up_total;
	}

	public int getId() {
		return id;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return EmojiParser.parseToUnicode(nickname);
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getReply_content() {
		return EmojiParser.parseToUnicode(reply_content);
	}

	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}

	public int getIs_give_thumbs_up() {
		return is_give_thumbs_up;
	}

	public void setIs_give_thumbs_up(int is_give_thumbs_up) {
		this.is_give_thumbs_up = is_give_thumbs_up;
	}

	public String getCreate_time() {
		return DateUtil.format(DateUtil.parse(this.create_time, "yyyyMMddHHmmss"), "yyyy-MM-dd HH:mm");
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

}
