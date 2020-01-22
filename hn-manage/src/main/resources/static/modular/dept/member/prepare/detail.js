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
        }
    };

    /**
     * 关闭此对话框
     */
    InfoDlg.close = function () {
        parent.layer.close(window.parent.IndexPage.layerIndex);
    }

