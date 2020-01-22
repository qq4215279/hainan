	/**
     * 画面初期处理
     */
    $(function () {
        App.initValidator("infoForm", InfoDlg.validateFields);
        //初始化日期类型的字典
        var memberChangeDate = $("#memberChangeDate").val();
        if(memberChangeDate){
        	memberChangeDate = memberChangeDate.substring(0,4)+"-"+memberChangeDate.substring(4,6)+"-"+memberChangeDate.substring(6,8);
        	$("#memberChangeDate").val(memberChangeDate)
        }
    });

    /**
     * 初始化详情对话框
     */
    var InfoDlg = {
        infoData: {},
        zTreeInstance: null,
        validateFields: {
            checkDesc: {
                validators: {
                    notEmpty: {
                        message: '审核意见不能为空'
                    },
                    stringLength: {
                        max: 200
                    }
                }
            }
        }
    };

    /**
     * 清除数据
     */
    InfoDlg.clearData = function () {
        this.infoData = {};
    }

    /**
     * 设置对话框中的数据
     */
    InfoDlg.set = function (key, val) {
        this.infoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
        return this;
    }

    /**
     * 设置对话框中的数据
     *
     */
    InfoDlg.get = function (key) {
        return $("#" + key).val();
    }

    /**
     * 关闭此对话框
     */
    InfoDlg.close = function () {
        parent.layer.close(window.parent.IndexPage.layerIndex);
    }

    /**
     * 收集数据
     */
    InfoDlg.collectData = function () {
        this.set('id').set('checkDesc');
    }

    /**
     * 验证数据是否为空
     */
    InfoDlg.validate = function () {
        $('#infoForm').data("bootstrapValidator").resetForm();
        $('#infoForm').bootstrapValidator('validate');
        return $("#infoForm").data('bootstrapValidator').isValid();
    }

    /**
     * 通过与不通过
     */
    InfoDlg.approval = function (status) {
        this.clearData();
        this.collectData();
        if (!this.validate()) {
       	  	errorFocus();
            return;
        }
        var showMessage = getShowMessage(status);
        //提交信息
        var ajax = new $ax(App.ctxPath + "approve/transfer/approval", function (data) {
            App.success(showMessage+"成功!");
            window.parent.IndexPage.table.refresh();
            InfoDlg.close();
        },
        function (data) {
            App.error(showMessage+"失败!");
        });
        ajax.set(this.infoData);
        ajax.set("status",status);
        ajax.start();
    }
    
    //根据传入的审核状态，返回提示信息
    function getShowMessage(status){
    	if(status == '1'){
    		return '通过';
    	}
    	return '拒绝';
    }

