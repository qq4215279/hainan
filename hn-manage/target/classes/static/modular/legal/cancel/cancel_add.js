  /**
   * 初始化表单验证
   */
   var CancelDlg = {
	   LegalCancelData: {},
	   validateFields: {
		   deptName: {
	             validators: {
	                 notEmpty: {
	                     message: '工会名称不能为空！'
	                 }
	             }
	         },
	         name: {
	        	validators: {
	                 notEmpty: {
	                     message: '法定代表人不能为空！'
	                 }
	             }
	        },
	        cerNo: {
                validators: {
                    notEmpty: {
                        message: '证书证号不能为空！'
                    }
                }
            },
            agentName: {
                validators: {
                    notEmpty: {
                        message: '经办人不能为空！'
                    }
                }
            },
            cancellationReason: {
                validators: {
                    notEmpty: {
                        message: '注销原因不能为空！'
                    }
                }
            },
            cacellationBasis: {
                validators: {
                    notEmpty: {
                        message: '注销依据不能为空！'
                    }
                }
            },
            pDeptFullname: {
                validators: {
                    notEmpty: {
                        message: '隶属工会名称不能为空！'
                    }
                }
            },
            assetsHanding: {
                validators: {
                    notEmpty: {
                        message: '资产处理情况不能为空！'
                    }
                }
            },
            debtHanding: {
                validators: {
                    notEmpty: {
                        message: '债权债务处理情况不能为空！'
                    }
                }
            }
	    }
	}

  /**
   * 清除数据
   */
   CancelDlg.clearData = function() {
      this.LegalCancelData = {};
  }

  /**
   * 设置对话框中的数据
   * param key 数据的名称
   * param val 数据的具体值
   */
   CancelDlg.set = function(key, val) {
      this.LegalCancelData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
      return this;
  }

  /**
   * 关闭此对话框
   */
  CancelDlg.close = function() {
      App.closeSelfIframe();
  }

  /**
   * 收集数据
   */
  CancelDlg.collectData = function() {
      this.set('deptName')
      .set('name')
      .set('cerNo')
      .set('agentName')
      .set('cancellationReason')
      .set('cacellationBasis')
      .set('pDeptFullname')
      .set('assetsHanding')
      .set('debtHanding')
      ;
  }

  /**
   * 验证数据是否为空
   */
   CancelDlg.validate = function () {
      $('#CancelForm').data("bootstrapValidator").resetForm();
      $('#CancelForm').bootstrapValidator('validate');
      return $("#CancelForm").data('bootstrapValidator').isValid();
  }

  /**
   * 保存和提交方法
   */
 	CancelDlg.addSubmit = function(status) {
       this.clearData();
       this.collectData();
       if (!this.validate()) {
     	  errorFocus();
          return;
      }
      var showMessage = "添加";
      if(status == 2){
    	  this.LegalCancelData["status"] = 0;
    	  var fun = function (){
        	  CancelDlg.addStartSubmit("提交");
          }
    	  App.confirm("确定要提交审核吗？", fun);
    	  return;
      }
     	CancelDlg.addStartSubmit(showMessage);
   }
  
  //提交保存
  CancelDlg.addStartSubmit = function (showMessage){
      App.loadingAjax({
          url:App.ctxPath + "legal/cancel/save",
          data:this.LegalCancelData,
          type:'post',
          success:function(){
              window.parent.LegalCancel.table.refresh();
              CancelDlg.close();
          }

      })
  }
  
$(function() {
	App.initValidator("CancelForm", CancelDlg.validateFields);
});

