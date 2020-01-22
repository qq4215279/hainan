/**
 * 系统管理--用户管理的单例对象
 */
var MgrUser = {
    id: "managerTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    deptid: 0
};

/**
 * 初始化表格的列
 */
MgrUser.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '账号', field: 'account', align: 'center', valign: 'middle', sortable: false},
        {title: '姓名', field: 'name', align: 'center', valign: 'middle', sortable: false},
        {title: '性别', field: 'sexName', align: 'center', valign: 'middle', sortable: false},
        // {title: '角色', field: 'roleName', align: 'center', valign: 'middle', sortable: false},
        {title: '职务', field: 'duty', align: 'center', valign: 'middle', sortable: false},
        {title: '工作职责', field: 'dutyofwork', align: 'center', valign: 'middle', sortable: false},
        {title: '电话', field: 'phone', align: 'center', valign: 'middle', sortable: false},
        {
            title: '创建时间', field: 'createtime', align: 'center', valign: 'middle', sortable: false,
            formatter: function (value, row, index) {
                var a = row.createtime;
                if (typeof a == "undefined") {
                    a = "-";
                } else {
                    a = dateDisplay(row.createtime);
                }
                return a;
            }
        },
        {title: '状态', field: 'statusName', align: 'center', valign: 'middle', sortable: false}];
    return columns;
};

/**
 * 检查是否选中
 */
MgrUser.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        App.info("请先选中表格中的某一记录！");
        return false;
    } else {
        MgrUser.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加管理员
 */
MgrUser.openAddMgr = function () {
    if (this.checkSelectDept()) {

        var index = App.openIframe({
            type: 2,
            title: '添加管理员',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: App.ctxPath + 'mgr/user_add/' + zTree.getSelectedNodes()[0].id
        });
        this.layerIndex = index;
    }
};
/**
 * 验证是否有选择部门
 */
MgrUser.checkSelectDept = function () {
    if (zTree.getSelectedNodes()[0]) {
        return true;
    } else {
        App.info("请先选择部门！");
        return false;
    }
}

/**
 * 点击修改按钮时
 * @param userId 管理员id
 */
MgrUser.openChangeUser = function () {
    if (this.check()) {
        var index = App.openIframe({
            type: 2,
            title: '编辑管理员',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: App.ctxPath + 'mgr/user_edit/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 点击角色分配
 * @param
 */
MgrUser.roleAssign = function () {
    if (this.check()) {
        var index = App.openIframe({
            type: 2,
            title: '角色分配',
            area: ['300px', '400px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: App.ctxPath + 'mgr/role_assign/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除用户
 */
MgrUser.delMgrUser = function () {
    if (this.check()) {

        var operation = function () {
            var userId = MgrUser.seItem.id;
            var ajax = new $ax(App.ctxPath + "/mgr/delete", function () {
                App.success("删除成功!");
                MgrUser.table.refresh();
            }, function (data) {
                App.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("userId", userId);
            ajax.start();
        };

        App.confirm("是否删除用户" + MgrUser.seItem.account + "?", operation);
    }
};

/**
 * 冻结用户账户
 * @param userId
 */
MgrUser.freezeAccount = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        var ajax = new $ax(App.ctxPath + "/mgr/freeze", function (data) {
            App.success("冻结成功!");
            MgrUser.table.refresh();
        }, function (data) {
            App.error("冻结失败!" + data.responseJSON.message + "!");
        });
        ajax.set("userId", userId);
        ajax.start();
    }
};

/**
 * 解除冻结用户账户
 * @param userId
 */
MgrUser.unfreeze = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        var ajax = new $ax(App.ctxPath + "mgr/unfreeze", function (data) {
            App.success("解除冻结成功!");
            MgrUser.table.refresh();
        }, function (data) {
            App.error("解除冻结失败!");
        });
        ajax.set("userId", userId);
        ajax.start();
    }
}

/**
 * 重置密码
 */
MgrUser.resetPwd = function () {
    if (this.check()) {
        var userId = this.seItem.id;
        parent.layer.confirm('是否重置密码为111111？', {
            btn: ['确定', '取消'],
            shade: false //不显示遮罩
        }, function () {
            var ajax = new $ax(App.ctxPath + "mgr/reset", function (data) {
                App.success("重置密码成功!");
            }, function (data) {
                App.error("重置密码失败!");
            });
            ajax.set("userId", userId);
            ajax.start();
        });
    }
};

MgrUser.resetSearch = function () {
    $("#name").val("");
    $("#beginTime").val("");
    $("#endTime").val("");
    MgrUser.search();
}

MgrUser.formParams = function () {
    var queryData = {};
    queryData['deptid'] = MgrUser.deptId;
    queryData['name'] = $("#name").val();
    queryData['beginTime'] = $("#beginTime").val();
    queryData['endTime'] = $("#endTime").val();
    return queryData;
}

MgrUser.search = function () {
    MgrUser.table.refresh({query: MgrUser.formParams()});
}

MgrUser.onClickDept = function (e, treeId, treeNode) {
    MgrUser.deptId = treeNode.id;
    MgrUser.search();
};

var setting = {
    view: {
        dblClickExpand: false,
        showLine: true,
        selectedMulti: false
    },
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "pId",
            rootPId: ""
        }
    },
    callback: {
        onClick: MgrUser.onClickDept,
        onRightClick: OnRightClick
    }
};

var zTree, rMenu;
$(function () {
    //初始化登录用户的orgId
    initLoginorgId();
    //初始化左侧工会组织树
    initZtreeByJsonData();
});

function initLoginorgId() {
    $.ajax({
        async: false,
        cache: false,
        type: "post",
        dataType: 'json',
        url: App.ctxPath + "mgr/getLoginOrgId",
        error: function () {
            alert('获取当前登录用户的orgId请求失败');
        },
        success: function (data) {
            $("#loginDeptId").val(data);//将登陆人的orgId存正在页面上
            MgrUser.deptId = data;
        }
    })
}

//初始化组织结构左侧的tree
function initZtreeByJsonData(dept_id) {
    $.ajax({
        async: false,
        cache: false,
        type: 'POST',
        dataType: "json",
        url: App.ctxPath + "dept/belowTree",//请求的action路径
        error: function () {//请求失败处理函数  
            alert('获取工会组织树，请求失败');
        },
        success: function (data) { //请求成功后处理函数。
            var t = $("#deptTree");
            $.fn.zTree.init(t, setting, data);
            zTree = $.fn.zTree.getZTreeObj("deptTree");
            rMenu = $("#rMenu");
            //判断id是否存在；存在则表示当前是部门或工会添加页面执行的刷新操作
            if (dept_id) {
                MgrUser.deptId = dept_id;
                MgrUser.search();
            } else {
                //工会组织结构树调用成功后，再加载工会组织的table
                initTableFun();
            }
        }
    });

    //初始化table
    function initTableFun() {
        var defaultColunms = MgrUser.initColumn();
        var table = new BSTable("managerTable", "mgr/list", defaultColunms);
        table.setPaginationType("server");
        table.setQueryParams(MgrUser.formParams());
        MgrUser.table = table.init();
    }
}

/**
 * 鼠标右键事件
 * @param event
 * @param treeId
 * @param treeNode
 * @returns
 */
function OnRightClick(event, treeId, treeNode) {
    // if (treeNode && !treeNode.noR) {
    //     zTree.selectNode(treeNode);
    //     var deptId = zTree.getSelectedNodes()[0].id;
    //     var deptType = zTree.getSelectedNodes()[0].deptType;
    //     var loginDeptId = $("#loginDeptId").val();
    //     if (deptId == loginDeptId) {
    //         $("#rMenu ul li:eq(3)").css({"display": "none"});
    //     } else {
    //         $("#rMenu ul li:eq(3)").css({"display": ""});
    //     }
    //     if (deptType == 1) {
    //         $("#rMenu ul li:eq(0)").css({"display": "none"});
    //         $("#rMenu ul li:eq(1)").css({"display": "none"});
    //     } else {
    //         $("#rMenu ul li:eq(0)").css({"display": ""});
    //         $("#rMenu ul li:eq(1)").css({"display": ""});
    //     }
    //     showRMenu("node", event.clientX, event.clientY);
    // }
};

/**
 * 显示右键菜单
 * @param type
 * @param x
 * @param y
 * @returns
 */
function showRMenu(type, x, y) {

    $("#rMenu ul").show();
    y = y + document.documentElement.scrollTop - 80;
    x = x + document.documentElement.scrollLeft - 15;
    rMenu.css({"top": y + "px", "left": x + "px", "visibility": "visible"});
};

/**
 * 隐藏右键菜单
 * @returns
 */
function hideRMenu() {
    if (rMenu) rMenu.css({"visibility": "hidden"});
};

/**
 * 添加下级工会
 * @param value
 * @returns
 */
function addTreeNode(value) {
    hideRMenu();
    var height = '580px';
    var title = '添加下级工会';
    if (value == 1) {
        height = '450px';
        title = '添加本级部门';
    }
    var map = {
        pid: zTree.getSelectedNodes()[0].id,
        deptType: value
    };
    var index = layer.open({
        type: 2,
        title: title,
        area: ['500px', height], //宽高
        fix: false, //不固定
        maxmin: true,
        content: App.ctxPath + 'mst/organization/addOrganizationOrDept?id=' + map.pid + '&deptType=' + map.deptType
    });
};

/**
 * 修改下级工会
 * @returns
 */
function edit() {
    var value = zTree.getSelectedNodes()[0].deptType;
    hideRMenu();
    var height = '580px';
    var title = '修改下级工会';
    if (value == 1) {
        height = '450px';
        title = '修改本级部门';
    }
    var map = {
        pid: zTree.getSelectedNodes()[0].id,
        deptType: value
    };
    var index = layer.open({
        type: 2,
        title: title,
        area: ['400px', height], //宽高
        fix: false, //不固定
        maxmin: true,
        content: App.ctxPath + 'mst/organization/editOrganizationOrDept?id=' + map.pid + '&deptType=' + map.deptType
    });
};

/**
 * 删除工会组织
 * @returns
 */
function removeTreeNode() {
    hideRMenu();
    var nodes = zTree.getSelectedNodes();
    var msg = "是否确认刪除?";
    if (nodes && nodes.length > 0) {
        if (nodes[0].children && nodes[0].children.length > 0) {
            msg = "删除该数据，将删除该数据下的所有数据!是否确认删除？";
        }
        var operation = function () {
            var ajax = new $ax(App.ctxPath + "mst/organization/removeOrganizationAndDept", function () {
                App.success("删除成功!");
                zTree.removeNode(nodes[0]);
            }, function (data) {
                App.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("deptId", nodes[0].id);
            ajax.start();
        };
        App.confirm(msg, operation);
    }
};
/**
 * 点击页面隐藏右键菜单
 */
$("body").click(function () {
    hideRMenu();
});
