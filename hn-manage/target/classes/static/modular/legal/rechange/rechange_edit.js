  /**
   * 初始化表单验证
   */
   var ReChangeDlg = {
	   LegalReChangeData: {},
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
	         unitTel: {
	        	validators: {
	                 notEmpty: {
	                     message: '联系电话不能为空！'
	                 }
	             }
	        },
	        unitAddress: {
              validators: {
                  notEmpty: {
                      message: '住所地址不能为空！'
                  }
              }
          },
          cerNo: {
              validators: {
                  notEmpty: {
                      message: '工会法人资格证书统一社会信用代码不能为空！'
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
          agentMobile: {
              validators: {
                  notEmpty: {
                      message: '经办人手机不能为空！'
                  }
              }
          },
          agentCertificateNo: {
              validators: {
                  notEmpty: {
                      message: '经办人身份证号码不能为空！'
                  },
                  regexp: {
                      regexp: '^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$',
                      message: '请输入正确的身份证号！'
                  }
              }
          },
          lossNatice: {
              validators: {
                  notEmpty: {
                      message: '遗失声明公示情况不能为空！'
                  }
              }
          },
          lossReason: {
              validators: {
                  notEmpty: {
                      message: '遗失原因不能为空！'
                  }
              }
          }
	    }
	}

  /**
   * 清除数据
   */
   ReChangeDlg.clearData = function() {
      this.LegalReChangeData = {};
  }

  /**
   * 设置对话框中的数据
   * param key 数据的名称
   * param val 数据的具体值
   */
   ReChangeDlg.set = function(key, val) {
      this.LegalReChangeData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
      return this;
  }

  /**
   * 关闭此对话框
   */
  ReChangeDlg.close = function() {
      parent.layer.close(window.parent.LegalReChange.layerIndex);
  }

  /**
   * 收集数据
   */
   ReChangeDlg.collectData = function() {
      this.set('id')
      .set('deptName')
      .set('name')
      .set('unitTel')
      .set('unitAddress')
      .set('cerNo')
      .set('agentName')
      .set('agentMobile')
      .set('agentCertificateNo')
      .set('lossNatice')
      .set('lossReason');
  }

  /**
   * 验证数据是否为空
   */
   ReChangeDlg.validate = function () {
      $('#ReChangeForm').data("bootstrapValidator").resetForm();
      $('#ReChangeForm').bootstrapValidator('validate');
      return $("#ReChangeForm").data('bootstrapValidator').isValid();
  }

  /**
   * 保存和提交方法
   */
   ReChangeDlg.addSubmit = function(status) {
       this.clearData();
       this.collectData();
       if (!this.validate()) {
     	  errorFocus();
          return;
      }
      var showMessage = "编辑";
      if(status == '2'){
    	  this.LegalReChangeData["status"] = 0;
    	  var fun = function (){
        	  ReChangeDlg.addStartSubmit("提交");
          }
    	  App.confirm("确定要提交审核吗？", fun);
    	  return;
      }
      ReChangeDlg.addStartSubmit(showMessage);
  }
   
   //提交保存
   ReChangeDlg.addStartSubmit = function (showMessage){
 	  var ajax = new $ax(App.ctxPath + "legal/rechange/save",
 		  function(data){
     	  	  App.success(showMessage+"成功!");
     	  	  window.parent.LegalReChange.table.refresh();
     	  	  ReChangeDlg.close();
 	      },function(data){
 	          App.error(showMessage+"失败!",data.message);
 	      });
       ajax.set(this.LegalReChangeData);
       ajax.start();
   }
   
	$(function() {
		App.initValidator("ReChangeForm", ReChangeDlg.validateFields);
	});

