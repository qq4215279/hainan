package com.gobestsoft.core.node;

import lombok.Data;

/**
 * jquery ztree 插件的节点
 *
 * @author gobestsoft
 * @date 2017年2月17日 下午8:25:14
 */
public class ZTreeNode {

    private Integer id;    //节点id

    private Integer pId;//父节点id

    private String code;

    private String name;//节点名称

    private Boolean open;//是否打开节点

    private Boolean checked;//是否被选中

    /**
     * 0：组织  1：部门
     */
    private Integer deptType;

    /**
     * 工会级别（省总：0，市总：1，区县总：2，街道：3, 基层：4）
     */
    private Integer deptLevel;
    /**
     * 排序
     */
    private Integer sort;
    private String iconSkin;//自定义图标

    /**
     * 是否有下级
     */
    private Boolean isParent;

    /**
     * 组织数
     */
    public Integer deptCnt;

    /**
     * 会员数
     */
    public Integer memberCnt;

    public String getIconSkin() {
        return iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public Integer getDeptType() {
        return deptType;
    }

    public void setDeptType(Integer deptType) {
        this.deptType = deptType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getOpen() {
        return open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getIsOpen() {
        return open;
    }

    public void setIsOpen(Boolean open) {
        this.open = open;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public static ZTreeNode createParent() {
        ZTreeNode zTreeNode = new ZTreeNode();
        zTreeNode.setChecked(false);
        zTreeNode.setId(0);
        zTreeNode.setName("顶级");
        zTreeNode.setOpen(false);
        zTreeNode.setpId(0);
        return zTreeNode;
    }

    public static ZTreeNode createMenuParent() {
        ZTreeNode zTreeNode = new ZTreeNode();
        zTreeNode.setChecked(false);
        zTreeNode.setId(0);
        zTreeNode.setName("顶级");
        zTreeNode.setOpen(false);
        zTreeNode.setpId(1);
        return zTreeNode;
    }

    public Integer getDeptLevel() {
        return deptLevel;
    }

    public void setDeptLevel(Integer deptLevel) {
        this.deptLevel = deptLevel;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public Integer getDeptCnt() {
        return deptCnt;
    }

    public void setDeptCnt(Integer deptCnt) {
        this.deptCnt = deptCnt;
    }

    public Integer getMemberCnt() {
        return memberCnt;
    }

    public void setMemberCnt(Integer memberCnt) {
        this.memberCnt = memberCnt;
    }
}
