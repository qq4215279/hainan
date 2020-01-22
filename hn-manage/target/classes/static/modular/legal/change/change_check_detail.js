  /**
   * 初始化表单验证
   */
   var ChangeDlg = {
	   LegalChangeData: {},
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
   ChangeDlg.close = function() {
      // parent.layer.close(window.parent.LegalChange.layerIndex);
       App.closeSelfIframe();
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
   * 审核通过不通过方法
   */
   ChangeDlg.addSubmit = function(status) {
       if (!this.validate()) {
     	  errorFocus();
          return;
       }
       var showMessage = "审核通过";
       if(status == 2){
    	   showMessage = "审核不通过";
       }

       var ajax = new $ax(App.ctxPath + "legal/change/checkSubmit",

           function(data){
               if(data.code==200){
                   App.success(showMessage+"成功!");
                   window.parent.LegalReChange.table.refresh();
                   ReChangeDlg.close();

               }else{
                   App.error(data.message);
               }
           },function(data){
 	          App.error(showMessage+"失败!",data.message);
 	      });
       ajax.set("logStatus",status);
       ajax.set("id",$("#id").val());
       ajax.set("checkOpinion",$("#checkOpinion").val());
       ajax.start();

   }
   
	$(function() {
		App.initValidator("ChangeForm", ChangeDlg.validateFields);
	});

