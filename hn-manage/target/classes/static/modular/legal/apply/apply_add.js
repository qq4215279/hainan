  /**
   * 初始化表单验证
   */
   var ApplyDlg = {
	   LegalApplyData: {},
	   validateFields: {
		   name: {
	             validators: {
	                 notEmpty: {
	                     message: '姓名不能为空！'
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
           createunionTime: {
                validators: {
                    notEmpty: {
                        message: '工会组织成立时间不能为空！'
                    }
                }
            },
           electionDate: {
                validators: {
                    notEmpty: {
                        message: '本届选举日期不能为空！'
                    }
                }
            },
           sex: {
                validators: {
                    notEmpty: {
                        message: '法人性别不能为空！'
                    }
                }
            },
            nation: {
                validators: {
                    notEmpty: {
                        message: '民族不能为空！'
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
           placeOther: {
               validators: {
                   regexp: {
                       regexp: '^[1-9]\\d*$|^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$',
                       message: '请输入正确的数值！'
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
           }


	    }
	}

  /**
   * 清除数据
   */
   ApplyDlg.clearData = function() {
      this.LegalApplyData = {};
  }

  /**
   * 设置对话框中的数据
   * param key 数据的名称
   * param val 数据的具体值
   */
   ApplyDlg.set = function(key, val) {
      this.LegalApplyData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
      return this;
  }

  /**
   * 关闭此对话框
   */
  ApplyDlg.close = function() {
      App.closeSelfIframe();
  }

  /**
   * 收集数据
   */
   ApplyDlg.collectData = function() {
      this.set('deptName')
       .set('unitTel')
       .set('unitAddress')
       .set('unitZipCode')
       .set('approveNo')
       .set('unitNumber')
       .set('membership')
       .set('cadresNumber')
       .set('chairmanName')
       .set('chairmanSession')
       .set('chairmanMobile')
       .set('incomeAccumulativeLastYear')
       .set('incomeAnnualDues')
       .set('incomeTradeUnionFunds')
       .set('incomeOther')
       .set('capitalTotal')
       .set('capitalFixedFunds')
       .set('capitalWorking')
       .set('capitalOther')
       .set('placeTotal')
       .set('placeOther')
       .set('abilityToBear')
       .set('cerNo')
       .set('name')
       .set('sex')
       .set('nation')
       // .set('chairmanSession')
       .set('education')
       .set('politicalOutlook')
       .set('nativePlace')
       .set('workPosition')
       .set('partTimeJob')
       .set('certificateNo')
       .set('otherPosition')
       .set('approveCompany')
       .set('placeOfficeArea')
       .set('placeActivity');
       var createunionTime =  $("#createunionTime").val();
       if(createunionTime){
           this.LegalApplyData["createunionTime"] = createunionTime.replaceAll('-','');
       }
       var electionDate =  $("#electionDate").val();
       if(electionDate){
          this.LegalApplyData["electionDate"] = electionDate.replaceAll('-','');
       }
       var birthday =  $("#birthday").val();
       if(birthday){
           this.LegalApplyData["birthday"] = birthday.replaceAll('-','');
       }
       var tnureStartDate =  $("#tnureStartDate").val();
       if(tnureStartDate){
           this.LegalApplyData["tnureStartDate"] = tnureStartDate.replaceAll('-','');
       }
       var joinTime =  $("#joinTime").val();
       if(joinTime){
           this.LegalApplyData["joinTime"] = joinTime.replaceAll('-','');
       }
       var cerTime =  $("#cerTime").val();
       if(cerTime){
           this.LegalApplyData["cerTime"] = cerTime.replaceAll('-','');
       }
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
   * 保存和提交方法
   */
   ApplyDlg.addSubmit = function(status) {
       this.clearData();
       this.collectData();
       if (!this.validate()) {
     	  errorFocus();
          return;
       }
      var showMessage = "添加";
      if(status == 2){
    	  this.LegalApplyData["status"] = 0;
    	  var fun = function (){
        	  ApplyDlg.addStartSubmit("提交");
          }
    	  App.confirm("确定要提交审核吗？", fun);
    	  return;
      }
      ApplyDlg.addStartSubmit(showMessage);
   }
  
  //提交保存
  ApplyDlg.addStartSubmit = function (showMessage){
      App.loadingAjax({
          url:App.ctxPath + "legal/apply/save",
          data:this.LegalApplyData,
          type:'post',
          success:function(){
              window.parent.LegalApply .table.refresh();
              ApplyDlg.close();
          }
      });
  }
  
$(function() {
	App.initValidator("ApplyForm", ApplyDlg.validateFields);
});

