/**
 * 菜单详情对话框
 */
var MenuInfoDlg = {
    menuInfoData: {},
    ztreeInstance: null,
    validateFields: {
        ismenu: {
            validators: {
                notEmpty: {
                    message: '是否是菜单不能为空'
                }
            }
        },
        name: {
            validators: {
                notEmpty: {
                    message: '菜单名称不能为空'
                }
            }
        },
        code: {
            validators: {
                notEmpty: {
                    message: '菜单编号不能为空'
                }
            }
        },
        pcodeName: {
            validators: {
                notEmpty: {
                    message: '父菜单不能为空'
                }
            }
        },
        /*url: {
            validators: {
                notEmpty: {
                    message: '请求地址不能为空'
                }
            }
        },*/
        num: {
            validators: {
                notEmpty: {
                    message: '序号不能为空'
                }
            }
        },
        platform: {
            validators: {
                callback: {
                    message: '菜单显示范围不能为空',
                    callback: function (value, validator) {
                        var ismenu = $("#ismenu").val();
                        if (ismenu == 1 && (value == null || value == '')) {
                            return false;
                        }
                        return true;
                    }
                }
            }
        }
    }
}

/**
 * 清除数据
 */
MenuInfoDlg.clearData = function () {
    this.menuInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MenuInfoDlg.set = function (key, val) {
    this.menuInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MenuInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
MenuInfoDlg.close = function () {
    parent.layer.close(window.parent.Menu.layerIndex);
}

/**
 * 收集数据
 */
MenuInfoDlg.collectData = function () {
    this.set('id').set('name').set('code').set('pcode').set('url').set('sort').set('levels').set('icon').set("ismenu").set("platform");
}

/**
 * 验证数据是否为空
 */
MenuInfoDlg.validate = function () {
    $('#menuInfoForm').data("bootstrapValidator").resetForm();
    $('#menuInfoForm').bootstrapValidator('validate');
    return $("#menuInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加用户
 */
MenuInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    App.loadingAjax({
        url: App.ctxPath + "/menu/add",
        data: this.menuInfoData,
        type: "post",
        success: function (result) {
            App.success("修改成功!");
            window.parent.Menu.search();
            MenuInfoDlg.close();
        }
    })
}

/**
 * 提交修改
 */
MenuInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    App.loadingAjax({
        url: App.ctxPath + "/menu/edit",
        data: this.menuInfoData,
        type: "post",
        success: function (result) {
            App.success("修改成功!");
            window.parent.Menu.search();
            MenuInfoDlg.close();
        }
    })

}

/**
 * 点击父级编号input框时
 */
MenuInfoDlg.onClickDept = function (e, treeId, treeNode) {
    $("#pcodeName").attr("value", treeNode.name);
    if (treeNode.code) {
        $("#pcode").attr("value", treeNode.code);
    } else {
        $("#pcode").val(0);
    }
};


/**
 * 显示父级菜单选择的树
 */
MenuInfoDlg.showMenuSelectTree = function () {
    App.showInputTree("pcodeName", "pcodeTreeDiv");
};

$(function () {
    App.initValidator("menuInfoForm", MenuInfoDlg.validateFields);

    var ztree = new $ZTree("pcodeTree", "/menu/selectMenuTreeList");
    ztree.bindOnClick(MenuInfoDlg.onClickDept);
    ztree.init();
    MenuInfoDlg.ztreeInstance = ztree;
});
