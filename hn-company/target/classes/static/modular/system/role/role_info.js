/**
 * 角色详情对话框（可用于添加和修改对话框）
 */
var RolInfoDlg = {
    roleInfoData: {},
    deptZtree: null,
    pNameZtree: null,
    validateFields: {
        name: {
            validators: {
                notEmpty: {
                    message: '用户名不能为空'
                }
            }
        },
        tips: {
            validators: {
                notEmpty: {
                    message: '别名不能为空'
                }
            }
        },
        pName: {
            validators: {
                notEmpty: {
                    message: '父级名称不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
RolInfoDlg.clearData = function () {
    this.roleInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RolInfoDlg.set = function (key, val) {
    this.roleInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
RolInfoDlg.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
RolInfoDlg.close = function () {
    parent.layer.close(window.parent.Role.layerIndex);
};

/**
 * 点击部门input框时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
RolInfoDlg.onClickDept = function (e, treeId, treeNode) {
    $("#deptName").attr("value", RolInfoDlg.deptZtree.getSelectedVal());
    $("#deptid").attr("value", treeNode.id);
};
RolInfoDlg.onDblClickDept = function (e, treeId, treeNode) {
    $("#deptName").attr("value", RolInfoDlg.deptZtree.getSelectedVal());
    $("#deptid").attr("value", treeNode.id);
    $("#deptContent").fadeOut("fast");
};

/**
 * 点击父级菜单input框时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
RolInfoDlg.onClickPName = function (e, treeId, treeNode) {
    $("#pName").attr("value", RolInfoDlg.pNameZtree.getSelectedVal());
    $("#pid").attr("value", treeNode.id);
};

/**
 * 显示部门选择的树
 *
 * @returns
 */
RolInfoDlg.showDeptSelectTree = function () {
    App.showInputTree("deptName", "deptContent");
};

/**
 * 显示父级菜单的树
 *
 * @returns
 */
RolInfoDlg.showPNameSelectTree = function () {
    App.showInputTree("pName", "pNameContent");
};


/**
 * 显示权限菜单选择的树
 * @returns
 */
//function beforeClick(treeId, treeNode) {
//	var zTree = $.fn.zTree.getZTreeObj("roleTreeDemo");
//	zTree.checkNode(treeNode, !treeNode.checked, null, true);
//	return false;
//}
//function onCheck(e, treeId, treeNode) {
	//var zTree = $.fn.zTree.getZTreeObj("roleTreeDemo"),
	//nodes = zTree.getCheckedNodes(true),
	//vId = "";
	//for (var i=0, l=nodes.length; i<l; i++) {
	//	vId += nodes[i].id + ","
	//}
	//if (vId.length > 0 ) vId = vId.substring(0, vId.length-1);
	//$("#menuIds").val(vId);
//}

/**
 * 隐藏权限选择的树
 */
RolInfoDlg.hideUserSelectTree = function () {
    $("#userContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};


/**
 * 收集数据
 */
RolInfoDlg.collectData = function () {
    this.set('id').set('name').set('pid').set('deptid').set('tips').set('sort');
};

/**
 * 验证数据是否为空
 */
RolInfoDlg.validate = function () {
    $('#roleInfoForm').data("bootstrapValidator").resetForm();
    $('#roleInfoForm').bootstrapValidator('validate');
    return $("#roleInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加用户
 */
RolInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
    	errorFocus();
        return;
    }
//	 var menuIds = App.zTreeCheckedNodes("roleTreeDemo");
    //提交信息
    var ajax = new $ax(App.ctxPath + "/role/add", function (data) {
        App.success("添加成功!");
        window.parent.Role.table.refresh();
        RolInfoDlg.close();
    }, function (data) {
        App.error("添加失败!" + data.responseJSON.message + "!");
    });
//    ajax.set("menuIds", menuIds);
    ajax.set(this.roleInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
RolInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }
// 	var menuIds = App.zTreeCheckedNodes("roleTreeDemo");
    //提交信息
    var ajax = new $ax(App.ctxPath + "/role/edit", function (data) {
        App.success("修改成功!");
        window.parent.Role.table.refresh();
        RolInfoDlg.close();
    }, function (data) {
        App.error("修改失败!" + data.responseJSON.message + "!");
    });
//    ajax.set("menuIds", menuIds);
    ajax.set(this.roleInfoData);
    ajax.start();
};

$(function () {
    App.initValidator("roleInfoForm", RolInfoDlg.validateFields);

    var deptTree = new $ZTree("deptTree", "/dept/tree");
    deptTree.bindOnClick(RolInfoDlg.onClickDept);
    deptTree.bindOnDblClick(RolInfoDlg.onDblClickDept)
    deptTree.init();
    RolInfoDlg.deptZtree = deptTree;

    var pNameTree = new $ZTree("pNameTree", "/role/roleTreeList");
    pNameTree.bindOnClick(RolInfoDlg.onClickPName);
    pNameTree.init();
    RolInfoDlg.pNameZtree = pNameTree;
  
  	//展示菜单选择树  
//    var uid = $("#id").val();
//    var setting = {
//            check: {
//                enable: true,
//                chkboxType: { "Y": "ps", "N": "ps" }
//            },
//            data: {
//                simpleData: {
//                    enable: true
//                }
//            },
//			callback: {
//				beforeClick: beforeClick,
//				onCheck: onCheck
//			}
//        };
//    var menuztree = new $ZTree("roleTreeDemo", "/menu/menuTreeListByRoleId/"+uid);
//    menuztree.setSettings(setting);
//    menuztree.init();
});
