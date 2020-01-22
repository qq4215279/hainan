  /**
   * 初始化表单验证
   */
   var ReChangeDlg = {
	   LegalReChangeData: {},
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
   ReChangeDlg.close = function() {
      // parent.layer.close(window.parent.LegalReChange.layerIndex);
    App.closeSelfIframe();
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
   * 审核通过不通过方法
   */
   ReChangeDlg.addSubmit = function(status) {
       if (!this.validate()) {
     	  errorFocus();
          return;
       }
       var showMessage = "审核通过";
       if(status == 2){
    	   showMessage = "审核不通过";
       }
       var ajax = new $ax(App.ctxPath + "legal/rechange/checkSubmit",
 		  function(data){
     	  	  if(data.code==200){
                  App.success(showMessage+"成功!");
                  window.parent.LegalReChange.table.refresh();
                  ReChangeDlg.close();

              }else{
                  App.error(data.message);
              }
 	      },function(data){

 	      });
       ajax.set("logStatus",status);
       ajax.set("id",$("#id").val());
       ajax.set("checkOpinion",$("#checkOpinion").val());
       ajax.start();

   }
   
	$(function() {
		App.initValidator("ReChangeForm", ReChangeDlg.validateFields);
	});

