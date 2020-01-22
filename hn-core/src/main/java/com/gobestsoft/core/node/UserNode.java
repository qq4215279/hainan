package com.gobestsoft.core.node;

/**
 * 发邮箱用户节点
 * @author ke
 *
 */

public class UserNode {

	private String id;//节点id
	
	private String pId;//父节点id
	
	private String name;//节点名称
	
	private String account;//账号
	
	private Boolean isopen;//是否打开节点
	
	private Boolean checked;//是否被选中
	
	private String iconSkin;//自定义图标
	
	private Integer isUser;//0组织部门 1用户

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}


	
	public Boolean getIsopen() {
		return isopen;
	}

	public void setIsopen(Boolean isopen) {
		this.isopen = isopen;
	}

	public String getIconSkin() {
		return iconSkin;
	}

	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}

	public Integer getIsUser() {
		return isUser;
	}

	public void setIsUser(Integer isUser) {
		this.isUser = isUser;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	
	
	
}
