/**
 * 初始化表单验证
 */
var ChangeDlg = {
    LegalChangeData: {},
    validateFields: {
        deptName: {
            validators: {
                notEmpty: {
                    message: '变更基层工会名称不能为空！'
                }
            }
        },
        unitAddress: {
            validators: {
                notEmpty: {
                    message: '变更住所不能为空！'
                }
            }
        },
        unitZipCode: {
            validators: {
                notEmpty: {
                    message: '变更邮编不能为空！'
                }
            }
        },
        name: {
            validators: {
                notEmpty: {
                    message: '变更法定代表人不能为空！'
                }
            }
        },
        incomeAccumulativeLastYear: {
            validators: {
                regexp: {
                    regexp: '^[1-9]\\d*$|^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$',
                    message: '请输入正确的数值！'
                }
            }
        },
        incomeAnnualDues: {
            validators: {
                regexp: {
                    regexp: '^[1-9]\\d*$|^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$',
                    message: '请输入正确的数值！'
                }
            }
        },
        incomeTradeUnionFunds: {
            validators: {
                regexp: {
                    regexp: '^[1-9]\\d*$|^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$',
                    message: '请输入正确的数值！'
                }
            }
        },
        incomeOther: {
            validators: {
                regexp: {
                    regexp: '^[1-9]\\d*$|^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$',
                    message: '请输入正确的数值！'
                }
            }
        },
        capitalTotal: {
            validators: {
                regexp: {
                    regexp: '^[1-9]\\d*$|^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$',
                    message: '请输入正确的数值！'
                }
            }
        },
        capitalFixedFunds: {
            validators: {
                regexp: {
                    regexp: '^[1-9]\\d*$|^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$',
                    message: '请输入正确的数值！'
                }
            }
        },
        capitalWorking: {
            validators: {
                regexp: {
                    regexp: '^[1-9]\\d*$|^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$',
                    message: '请输入正确的数值！'
                }
            }
        },
        capitalOther: {
            validators: {
                regexp: {
                    regexp: '^[1-9]\\d*$|^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$',
                    message: '请输入正确的数值！'
                }
            }
        },
        placeTotal: {
            validators: {
                regexp: {
                    regexp: '^[1-9]\\d*$|^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$',
                    message: '请输入正确的数值！'
                }
            }
        },
        placeActivity: {
            validators: {
                regexp: {
                    regexp: '^[1-9]\\d*$|^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$',
                    message: '请输入正确的数值！'
                }
            }
        },
        placeOfficeArea: {
            validators: {
                regexp: {
                    regexp: '^[1-9]\\d*$|^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$',
                    message: '请输入正确的数值！'
                }
            }
        },
        placeOther: {
            validators: {
                regexp: {
                    regexp: '^[1-9]\\d*$|^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$',
                    message: '请输入正确的数值！'
                }
            }
        },
        changeReason: {
            validators: {
                notEmpty: {
                    message: '变更原因不能为空！'
                }
            }
        },
        certificateNo: {
            validators: {
                regexp: {
                     regexp:  '(^\\d{18}$)|(^\\d{15}$)',
                    message: '身份证输入有误！'
                }
            }
        },

        // rePUnionName: {
        //       validators: {
        //           notEmpty: {
        //               message: '变更隶属工会名称不能为空！'
        //           }
        //       }
        //   }
    }
}

/**
 * 清除数据
 */
ChangeDlg.clearData = function() {
    this.LegalChangeData = {};
}

/**
 * 设置对话框中的数据
 * param key 数据的名称
 * param val 数据的具体值
 */
ChangeDlg.set = function(key, val) {
    this.LegalChangeData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
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
    this.set('id')
        .set('deptName')
        .set('unitAddress')
        .set('unitZipCode')
        .set('name')
        .set('pDeptFullname')
        .set('cacellationBasis')
        .set('unitTel')
        .set('approveNo')
        .set('membership')
        .set('unitNumber')
        .set('officeTerm')
        .set('changeReason')
  //      .set('createunionTime')
        .set('incomeAccumulativeLastYear')
        .set('incomeAnnualDues')
        .set('incomeTradeUnionFunds')
        .set('incomeOther')
        .set('capitalTotal')
        .set('capitalFixedFunds')
        .set('capitalWorking')
        .set('capitalOther')
        .set('placeTotal')
        .set('placeActivity')
        .set('placeOfficeArea')
        .set('placeOther')
        .set('certificateNumber')
        .set('personCertificateNumber')
        .set('reason')
        .set('name')
        .set('nation')
        .set('education')
        .set('nativePlace')
        .set('sex')
        .set('politicalOutlook')
        .set('workPosition')
        .set('certificateNo')
        .set('partTimeJob')
        .set('joinTime')
        .set('otherPosition')
        .set('legalMobile')
        .set('cerTime')
        .set('cerNo');


    var tnureStartDate = $("#tnureStartDate").val();
    if(tnureStartDate){
        this.LegalChangeData["tnureStartDate"] = tnureStartDate.replaceAll("-","");
    }
    var electionDate = $("#electionDate").val();
    if(electionDate){
        this.LegalChangeData["electionDate"] = electionDate.replaceAll("-","");
    }
    var birthday = $("#birthday").val();
    if(birthday){
        this.LegalChangeData["birthday"] = birthday.replaceAll("-","");
    }
    var createunionTime = $("#createunionTime").val();
    if(createunionTime){
        this.LegalChangeData["createunionTime"] = createunionTime.replaceAll("-","");
    }

    var joinTime = $("#joinTime").val();
    if(joinTime){
        this.LegalChangeData["joinTime"] = joinTime.replaceAll("-","");
    }
    var cerTime = $("#cerTime").val();
    if(cerTime){
        this.LegalChangeData["cerTime"] = cerTime.replaceAll("-","");
    }
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
    var showMessage = "编辑";
    if(status == '2'){
        this.LegalChangeData["status"] = 0;
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
    var ajax = new $ax(App.ctxPath + "legal/change/save",
        function(data){
            App.success(showMessage+"成功!");
            window.parent.LegalChange.table.refresh();
            ChangeDlg.close();
        },function(data){
            App.error(showMessage+"失败!",data.message);
        });
    ajax.set(this.LegalChangeData);
    ajax.start();
}

$(function() {
    App.initValidator("ChangeForm", ChangeDlg.validateFields);
});

//
// /**
//  * 保存和提交方法
//  */
// ChangeDlg.addSubmit = function(status) {
//     this.clearData();
//     this.collectData();
//     if (!this.validate()) {
//         errorFocus();
//         return;
//     }
//     var showMessage = "编辑";
//     if(status == '2'){
//         this.LegalChangeData["status"] = 0;
//         var fun = function (){
//             ChangeDlg.addStartSubmit("提交");
//         }
//         App.confirm("确定要提交审核吗？", fun);
//         return;
//     }
//     ChangeDlg.addStartSubmit(showMessage);
// }
//
// //提交保存
// ChangeDlg.addStartSubmit = function (showMessage){
//
//     var ajax = new $ax(App.ctxPath + "legal/change/save",
//         function(data){
//             App.success(showMessage+"成功!");
//             window.parent.LegalChange.table.refresh();
//             ChangeDlg.close();
//         },function(data){
//             App.error(showMessage+"失败!",data.message);
//         });
//     ajax.set(this.LegalChangeData);
//     ajax.start();
// }
//
// $(function() {
//     App.initValidator("ChangeForm", ChangeDlg.validateFields);
// });
//
