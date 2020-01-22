/**
 * 初始化表单验证
 */
var ApplyDlg = {
    LegalApplyData: {},
    validateFields: {
        checkOpinion: {
            validators: {
                notEmpty: {
                    message: '审核意见不能为空！'
                }
            }
        }
    }
}

/**
 * 关闭此对话框
 */
ApplyDlg.close = function() {
    parent.layer.close(window.parent.LegalApply.layerIndex);
}

/**
 * 验证数据是否为空
 */
ApplyDlg.validate = function () {
    $('#ApplyForm').data("bootstrapValidator").resetForm();
    $('#ApplyForm').bootstrapValidator('validate');
    return $("#ApplyForm").data('bootstrapValidator').isValid();
}

/**
 * 审核通过不通过方法
 */
ApplyDlg.addSubmit = function(status) {
    if (!this.validate()) {
        errorFocus();
        return;
    }
    var showMessage = "审核通过";
    if(status == 2){
        showMessage = "审核不通过";
    }
    var ajax = new $ax(App.ctxPath + "legal/apply/checkSubmit",
        function(data){
            App.success(showMessage+"成功!");
            window.parent.LegalApply.table.refresh();
            ApplyDlg.close();
        },function(data){
            App.error(showMessage+"失败!",data.message);
        });
    ajax.set("logStatus",status);
    ajax.set("id",$("#id").val());
    ajax.set("checkOpinion",$("#checkOpinion").val());
    ajax.start();
}

$(function() {
    App.initValidator("ApplyForm", ApplyDlg.validateFields);
});

