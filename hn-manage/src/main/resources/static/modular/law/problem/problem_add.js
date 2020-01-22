/**
 * 初始化表单验证
 */
var ChangeDlg = {
    problemChangeData: {},
    validateFields: {
        title: {
            validators: {
                notEmpty: {
                    message: '问题名称不能为空！'
                }
            }
        },
        content: {
            validators: {
                notEmpty: {
                    message: '问题描述不能为空！'
                }
            }
        }
    }
}

/**
 * 清除数据
 */
ChangeDlg.clearData = function() {
    this.problemChangeData = {};
}

/**
 * 设置对话框中的数据
 * param key 数据的名称
 * param val 数据的具体值
 */
ChangeDlg.set = function(key, val) {
    this.problemChangeData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
return this;
}

/**
 * 关闭此对话框
 */
ChangeDlg.close = function() {
    App.closeSelfIframe();
}

/**
 * 收集数据
 */
ChangeDlg.collectData = function() {
    this.set('title')
        .set('content')
}

/**
 * 验证数据是否为空
 */
ChangeDlg.validate = function () {
    $('#ChangeForm').data("bootstrapValidator").resetForm();
    $('#ChangeForm').bootstrapValidator('validate');
    return $("#ChangeForm").data('bootstrapValidator').isValid();
}

/**
 * 保存和提交方法
 */
ChangeDlg.addSubmit = function(status) {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        errorFocus();
        return;
    }
    var showMessage = "添加";
    if(status == 2){
        this.problemChangeData["status"] = 0;
        var fun = function (){
            ChangeDlg.addStartSubmit("提交");
        }
        App.confirm("确定要提交审核吗？", fun);
        return;
    }
    ChangeDlg.addStartSubmit(showMessage);
}

//提交保存
ChangeDlg.addStartSubmit = function (showMessage){
    // var ajax = new $ax(App.ctxPath + "legal/change/save",
    //     function(data){
    //         App.success(showMessage+"成功!");
    //         window.parent.LegalChange.table.refresh();
    //         ChangeDlg.close();
    //     },function(data){
    //         App.error(showMessage+"失败!",data.message);
    //     });
    // ajax.start();
    App.loadingAjax({
        url:App.ctxPath + "law/problem/save",
        data:this.problemChangeData,
        type:'post',
        success:function(){
            App.success(showMessage+"成功!");
            window.parent.LawProblem.table.refresh();
            ChangeDlg.close();
        }
    });
}

$(function() {
    App.initValidator("ChangeForm", ChangeDlg.validateFields);
});

