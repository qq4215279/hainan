  /**
   * 初始对话框
   */
   var OrganizationAuditDlg = {
	   organizationAuditData: {},
	   validateFields: {
		   reason: {
	           validators: {
	               notEmpty: {
	                   message: '审核意见不能为空！'
	               }
	           }
		   }
	   }
	};

  /**
   * 清除数据
   */
   OrganizationAuditDlg.clearData = function() {
      this.organizationAuditData = {};
  }

  /**
   * 设置对话框中的数据
   * param key 数据的名称
   * param val 数据的具体值
   */
   OrganizationAuditDlg.set = function(key, val) {
      this.organizationAuditData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
      return this;
  }

  /**
   * 关闭此对话框
   */
   OrganizationAuditDlg.close = function() {
      parent.layer.close(window.parent.DeptOrganization.layerIndex);
  }

  /**
   * 收集数据
   */
   OrganizationAuditDlg.collectData = function() {
	   this.set('id').set('reason');
  }

  /**
   * 验证数据是否为空
   */
   OrganizationAuditDlg.validate = function () {
      $('#organizationDlgForm').data("bootstrapValidator").resetForm();
      $('#organizationDlgForm').bootstrapValidator('validate');
      return $("#organizationDlgForm").data('bootstrapValidator').isValid();
  }


  /**
   * 添加企业信息并提交建会事情
   */
   OrganizationAuditDlg.addSubmit = function(status) {
      this.clearData();
      this.collectData();
      if (!this.validate()) {
    	  errorFocus();
          return;
      }
      var showMessage = getShowMessage(status);
      //提交信息
      var ajax = new $ax(App.ctxPath + "organization/check/saveAuthen", function(data){
          App.success(showMessage+"成功 !");
          window.parent.DeptOrganization.table.refresh();
          OrganizationAuditDlg.close();
      },function(data){
          App.error(showMessage+"失败!");
      });
      ajax.set(this.organizationAuditData);
      ajax.set('status',status);
      ajax.start();
  }
  
  function getShowMessage(status){
	  var showMessage = "审核通过";
      if(status == '3'){
    	  showMessage = "审核不通过";
      }
      return showMessage;
  }
//初始化时间类型的字段
  function initTimeFun(field) {
  	if(field) {
  	    return field.substring(0,4)+"-"+field.substring(4,6)+"-"+field.substring(6,8);
  	}
  	return field;
  }

$(function() {
    App.initValidator("organizationDlgForm", OrganizationAuditDlg.validateFields);
    var createunionTime = $("#createunionTime").val();
    $("#createunionTime").val(initTimeFun(createunionTime));
});