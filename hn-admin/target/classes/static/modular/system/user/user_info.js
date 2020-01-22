/**
 * 用户详情对话框（可用于添加和修改对话框）
 */
var UserInfoDlg = {
    userInfoData: {},
    validateFields: {
        account: {
            validators: {
                notEmpty: {
                    message: '账户不能为空'
                }
            }
        },
        name: {
            validators: {
                notEmpty: {
                    message: '姓名不能为空'
                }
            }
        },
        citySel: {
            validators: {
                notEmpty: {
                    message: '部门不能为空'
                }
            }
        },
        password: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                },
                identical: {
                    field: 'rePassword',
                    message: '两次密码不一致'
                },
            }
        },
        rePassword: {
            validators: {
                notEmpty: {
                    message: '密码不能为空'
                },
                identical: {
                    field: 'password',
                    message: '两次密码不一致'
                },
            }
        }
    }
};

/**
 * 清除数据
 */
UserInfoDlg.clearData = function () {
    this.userInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserInfoDlg.set = function (key, val) {
    this.userInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
UserInfoDlg.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
UserInfoDlg.close = function () {
    parent.layer.close(window.parent.MgrUser.layerIndex);
};


/**
 * 显示权限菜单选择的树
 *
 * @returns
 */
UserInfoDlg.showUserMenuSelectTree = function () {
    var cityObj = $("#userMenu");
    var cityOffset = $("#userMenu").offset();
    var userId = $("#id").val();
    $("#userContent").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");
    
    $("body").bind("mousedown", onBodyDown);
};

/**
 * 隐藏权限选择的树
 */
UserInfoDlg.hideUserSelectTree = function () {
    $("#userContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};

/**
 * 收集数据
 */
UserInfoDlg.collectData = function () {
    this.set('id').set('account').set('sex').set('password').set('avatar')
        .set('email').set('name').set('birthday').set('rePassword').set('deptid')
        .set('phone').set("duty").set("dutyOfWork").set("isResponsible").set("userType");
};

/**
 * 验证两个密码是否一致
 */
UserInfoDlg.validatePwd = function () {
    var password = this.get("password");
    var rePassword = this.get("rePassword");
    if (password == rePassword) {
        return true;
    } else {
        return false;
    }
};

/**
 * 验证数据是否为空
 */
UserInfoDlg.validate = function () {
    $('#userInfoForm').data("bootstrapValidator").resetForm();
    $('#userInfoForm').bootstrapValidator('validate');
    return $("#userInfoForm").data('bootstrapValidator').isValid();
};
/**
 * 显示角色选择的树
 *
 * @returns
 */
UserInfoDlg.showRoleSelectTree = function () {
    if($("#roleContent").length<=0){
        return;
    }
//   / $("body").bind("mousedown", onBodyDown);
    var setting = {
    	    check: {
    	        enable: true,
    	        chkboxType: {
    	            "Y": "",
    	            "N": ""
    	        }
    	    },
    	    data: {
    	        simpleData: {
    	            enable: true
    	        }
    	    }
    	};
    	var userId = $("#id").val();
    	var url = "roleTreeListByUserId/"+userId;
    	if($("#tempType").val()=="add"){
    		url = "roleTreeList";
    	}
    	var roleztree = new $ZTree("roleTreeDemo", "role/"+url);
    	roleztree.setSettings(setting);
    	roleztree.init();
};
/**
 * 提交添加用户
 */
UserInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
    	errorFocus();
        return;
    }

    if (!this.validatePwd()) {
        App.error("两次密码输入不一致");
        return;
    }
    var roleIds = App.zTreeCheckedNodes("roleTreeDemo");
    if(document.getElementById("userType").checked ){
        this.userInfoData.userType="1";
    }
    //提交信息
    var ajax = new $ax(App.ctxPath + "/mgr/add", function (data) {
        App.success("添加成功!");
        parent.initZtreeByJsonData($("#deptid").val());
        UserInfoDlg.close();
    }, function (data) {
        App.error("添加失败!");
    });

    ajax.set(this.userInfoData);
    ajax.set("roleIds", roleIds);
    ajax.set("userId", $("#id").val());
    ajax.start();
};

/**
 * 个人信息提交修改
 */
UserInfoDlg.editSelfSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
    	errorFocus();
        return;
    };
    var roleIds = App.zTreeCheckedNodes("roleTreeDemo");
    //提交信息
    var ajax = new $ax(App.ctxPath + "/mgr/editSelf", function (data) {
        App.success("修改成功!");
        if (window.parent.MgrUser != undefined) {
            parent.initZtreeByJsonData($("#deptid").val());
            UserInfoDlg.close();
        }
    }, function (data) {
        App.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userInfoData);
    ajax.set("roleIds", roleIds);
    ajax.set("userId", $("#id").val());
    ajax.start();
};

/**
 * 提交修改
 */
UserInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
    	errorFocus();
        return;
    };
    var roleIds = App.zTreeCheckedNodes("roleTreeDemo");
    if(document.getElementById("userType").checked ){
        this.userInfoData.userType="1";
    }else {
        this.userInfoData.userType="0";
    }
    //提交信息
    var ajax = new $ax(App.ctxPath + "/mgr/edit", function (data) {
        App.success("修改成功!");
        if (window.parent.MgrUser != undefined) {
            parent.initZtreeByJsonData($("#deptid").val());
            UserInfoDlg.close();
        }
    }, function (data) {
        App.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.userInfoData);
    ajax.set("roleIds", roleIds);
    ajax.set("userId", $("#id").val());
    ajax.start();
};

/**
 * 修改密码
 */
UserInfoDlg.chPwd = function () {
    var ajax = new $ax(App.ctxPath + "/mgr/changePwd", function (data) {
        App.success("修改成功!");
    }, function (data) {
        App.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set("oldPwd");
    ajax.set("newPwd");
    ajax.set("rePwd");
    ajax.start();

};

function onBodyDown(event) {
    if (!( event.target.id == "userContent" || $(event.target).parents("#userContent").length > 0)) {
        UserInfoDlg.hideUserSelectTree();
    }
}

$(function () {
    App.initValidator("userInfoForm", UserInfoDlg.validateFields);
//    var ztree = new $ZTree("treeDemo", "/dept/tree");
    UserInfoDlg.showRoleSelectTree();
//    ztree.bindOnClick(UserInfoDlg.onClickDept);
//    ztree.init();
//    instance = ztree;

    //初始化性别选项
    $("#sex").val($("#sexValue").val());
    $("#isResponsible").val($("#isResponsibleValue").val());
    // 初始化头像上传
    var avatarUp = new $WebUpload("avatar");
    avatarUp.setUploadBarId("progressBar");
    avatarUp.init();


});
