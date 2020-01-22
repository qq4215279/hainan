  /**
   * 初始化表单验证
   */
   var CancelDlg= {
	   LegalCancelData: {},
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
   CancelDlg.close = function() {
      // parent.layer.close(window.parent.LegalCancel.layerIndex);
       App.closeSelfIframe();
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
   * 审核通过不通过方法
   */
   CancelDlg.addSubmit = function(status) {
       if (!this.validate()) {
     	  errorFocus();
          return;
       }
       var showMessage = "审核通过";
       if(status == 2){
    	   showMessage = "审核不通过";
       }
       var ajax = new $ax(App.ctxPath + "legal/cancel/checkSubmit",
 		  function(data){
     	  	  App.success(showMessage+"成功!");
     	  	  window.parent.LegalCancel.table.refresh();
     	  	  CancelDlg.close();
 	      },function(data){
 	          App.error(showMessage+"失败!",data.message);
 	      });
       ajax.set("logStatus",status);
       ajax.set("id",$("#id").val());
       ajax.set("checkOpinion",$("#checkOpinion").val());
       ajax.start();
   }
   
	$(function() {
		App.initValidator("CancelForm", CancelDlg.validateFields);
	});

