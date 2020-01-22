  /**
   * 初始化表单验证
   */
   var OrganizationDlg = {
	   organizationData: {},
	   deptZtree: null,
	   pNameZtree: null,
	   validateFields: {
	    	pName: {
	             validators: {
	                 notEmpty: {
	                     message: '上级工会不能为空！'
	                 }
	             }
	         },
	         unitName: {
	        	validators: {
	                 notEmpty: {
	                     message: '单位名称不能为空！'
	                 }
	             }
	        },
	        unionName: {
                validators: {
                    notEmpty: {
                        message: '工会全称不能为空！'
                    }
                }
            },
            unionSimpleName: {
                validators: {
                    notEmpty: {
                        message: '工会简称不能为空！'
                    }
                }
            },
            unionType: {
	             validators: {
	                 notEmpty: {
	                     message: '工会类型不能为空！'
	                 }
	             }
	         },
	         membership: {
	             validators: {
	                 notEmpty: {
	                     message: '会员人数不能为空！'
	                 }
	             }
	         },
	         chairmanName: {
	             validators: {
	                 notEmpty: {
	                     message: '主席名称不能为空！'
	                 }
	             }
	         },
            unitOrgCode: {
                validators: {
                    notEmpty: {
                        message: '组织机构代码不能为空！'
                    }
                }
            },
            unitDistrict: {
                validators: {
                    notEmpty: {
                        message: '单位所在行政区不能为空！'
                    }
                }
            },
            unitIndustry: {
                validators: {
                    notEmpty: {
                        message: '单位所属行业不能为空！'
                    }
                }
            },
            unitNature: {
                validators: {
                    notEmpty: {
                        message: '单位性质类别不能为空！'
                    }
                }
            },
            unitEconomicType: {
                validators: {
                    notEmpty: {
                        message: '经济类型不能为空！'
                    }
                }
            },
            unitNumber: {
                validators: {
                    notEmpty: {
                        message: '职工人数不能为空！'
                    }
                }
            },
            unitAddress: {
                validators: {
                    notEmpty: {
                        message: '单位地址不能为空！'
                    }
                }
            }
	    }
	}

  /**
   * 清除数据
   */
   OrganizationDlg.clearData = function() {
      this.organizationData = {};
  }

  /**
   * 设置对话框中的数据
   * param key 数据的名称
   * param val 数据的具体值
   */
   OrganizationDlg.set = function(key, val) {
      this.organizationData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
      return this;
  }

  /**
   * 关闭此对话框
   */
   OrganizationDlg.close = function() {
	   parent.layer.close(window.parent.DeptOrganization.layerIndex);
  }

  /**
   * 收集数据
   */
   OrganizationDlg.collectData = function() {
      this.set('id')
      .set('pId')
      .set('pName')
      .set('unionName')
      .set('unionSimpleName')
      .set('unionType')
      .set('membership')
      .set('unionEmail')
      .set('unitZipCode')
      .set('cadresNumber')
      .set('chairmanName')
      .set('unionLeader')
      .set('unionLeaderPhone')
      .set('unitName')
      .set('unitOrgCode')
      .set('othersOrgCode')
      .set('unitDistrict')
      .set('unitIndustry')
      .set('unitNature')
      .set('unitEconomicType')
      .set('unitNumber')
      .set('unitAddress')
      .set('chairmanSession')
      .set('chairmanMobile');
      var createunionTime = $("#createunionTime").val();
      if(createunionTime){
    	  this.organizationData["createunionTime"] = createunionTime.replaceAll("-","");
      }
  }

  /**
   * 验证数据是否为空
   */
   OrganizationDlg.validate = function () {
      $('#organizationForm').data("bootstrapValidator").resetForm();
      $('#organizationForm').bootstrapValidator('validate');
      return $("#organizationForm").data('bootstrapValidator').isValid();
  }

  /**
   * 申请建会
   */
   OrganizationDlg.addSubmit = function() {
       this.clearData();
       this.collectData();
       if (!this.validate()) {
     	  errorFocus();
           return;
      }
      var ajax = new $ax(App.ctxPath + "company/organization/saveBuild",
		  function(data){
    	  	  App.success("申请建会提交成功!");
    	  	  //window.parent.DeptOrganization.table.refresh();
    	  	  window.parent.location.reload();
    	  	  OrganizationDlg.close();
	      },function(data){
	          App.error("申请建会提交失败!" + data.message + "!");
	      });
      ajax.set(this.organizationData);
      ajax.start();
  }
   
   /**
    * 显示父级菜单下拉框
    * @returns
    */
   OrganizationDlg.showPNameSelectTree = function () {
       App.showInputTree("pName", "pNameContent");
    };
    
    /**
     * 点击父级菜单下拉框input框时
     * @param e
     * @param treeId
     * @param treeNode
     * @returns
     */
    OrganizationDlg.onClickPName = function (e, treeId, treeNode) {
        $("#pName").attr("value", OrganizationDlg.pNameZtree.getSelectedVal());
        $("#pId").attr("value", treeNode.id);
    };
    
	$(function() {
		App.initValidator("organizationForm", OrganizationDlg.validateFields);
		//初始化父级菜单下拉框
		var pNameTree = new $ZTree("pNameTree", "dept/tree");
	    pNameTree.bindOnClick(OrganizationDlg.onClickPName);
	    pNameTree.init();
	    OrganizationDlg.pNameZtree = pNameTree;
	    var createunionTime = $("#createunionTime").val();
        $("#createunionTime").val(initTimeFun(createunionTime));
	});
	
	//初始化时间类型的字段
    function initTimeFun(field) {
        if(field) {
            return field.substring(0,4)+"-"+field.substring(4,6)+"-"+field.substring(6,8);
        }
        return field;
    }

