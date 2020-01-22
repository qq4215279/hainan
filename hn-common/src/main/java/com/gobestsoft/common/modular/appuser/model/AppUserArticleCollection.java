package com.gobestsoft.common.modular.appuser.model;

import lombok.Data;

/**
 * 用户收藏资讯
 * @author Administrator
 *
 */
@Data
public class AppUserArticleCollection {

	private long id;

	private String  relation_id;

	private String cover;
	
	private String title;

	private String collect_time;

	private String category;

	private String jump_url;

	private int type;
	
}
