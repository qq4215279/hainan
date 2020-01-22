package com.gobestsoft.common.modular.cms.model;

import java.util.ArrayList;
import java.util.List;

import com.gobestsoft.core.util.DateUtil;
import com.vdurmont.emoji.EmojiParser;

/**
 * 评论 及 评论回复
 * 
 * @author gutongwei
 *
 *         2017年11月24日
 */
public class Comment {
	public Comment() {
		this.reply_contents = new ArrayList<ReplyContent>();
	}
	

	private int id;

	private String userId;

	private String nickname;

	private String content;

	private String avatar;

	private int thumbs_up_total;

	private String create_time;

	private int reply_total;

	private int is_give_thumbs_up;

	private List<ReplyContent> reply_contents;

	public int getId() {
		return id;
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

	public String getContent() {
		return EmojiParser.parseToUnicode(content);
	}

	public void setContent(String content) {
		this.content = content;
	}

	

	public int getThumbs_up_total() {
		return thumbs_up_total;
	}

	public void setThumbs_up_total(int thumbs_up_total) {
		this.thumbs_up_total = thumbs_up_total;
	}

	public String getCreate_time() {
		return DateUtil.format(DateUtil.parse(create_time, "yyyyMMddHHmmss"), "yyyy-MM-dd HH:mm:ss");
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public int getReply_total() {
		return reply_total;
	}

	public void setReply_total(int reply_total) {
		this.reply_total = reply_total;
	}

	public List<ReplyContent> getReply_contents() {
		return reply_contents;
	}

	public void setReply_contents(List<ReplyContent> reply_contents) {
		this.reply_contents = reply_contents;
	}

	public int getIs_give_thumbs_up() {
		return is_give_thumbs_up;
	}

	public void setIs_give_thumbs_up(int is_give_thumbs_up) {
		this.is_give_thumbs_up = is_give_thumbs_up;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

}
