/**
     * 初始化表格对象
     */
    var LegalApply = {
        id: "LegalApplyTable",	//表格id
        seItem: null,		//选中的条目
        table: null,
        layerIndex: -1
    };

    /**
     * 初始化表格的列
     */
    LegalApply.initColumn = function () {
        return [

            {field: 'selectItem', radio: true},
            {title: '工会名称', field: 'deptName', align: 'center', valign: 'middle'},
            {title: '法定代表人', field: 'name', align: 'center', valign: 'middle'},
            {title: '法人性别', field: 'sex', align: 'center', valign: 'middle'},
            {title: '法人民族', field: 'nation', align: 'center', valign: 'middle'},
            {title: '法人身份证', field: 'certificateNo', align: 'center', valign: 'middle'},
            {title: '状态', field: 'status', align: 'center', valign: 'middle',formatter: initStatusField},
            {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle',formatter: initTimeField},
            {title: '操作', align: 'center', valign: 'middle',
                formatter: function (value, row, index) {
                	var detail = '<a href="javascript:LegalApply.detail(' + row.id + ')">查看详情</a>';
                	var log = '<a href="javascript:LegalApply.log(' + row.id + ')">查看流程</a>';
                	var edit='<a href="javascript:LegalApply.edit('+row.id+')">编辑</a>'
                    if(!row.status){
                    	return edit+"&nbsp;&nbsp;&nbsp;"+detail;
                    }
                    return detail+"&nbsp;&nbsp;&nbsp;"+log;
                }
            }
        ];
    }
     //初始化列表的时间类型的字段
     function initTimeField(value, row, index){
    	var time = row.createTime;
     	if(time){
     		return time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8)+" "+time.substring(8,10)+":"+time.substring(10,12);
     	}
     }
     //初始化审核状态
     function initStatusField(value, row, index){
    	 var status = row.status;
    	 if(!status){
    		 return "草稿";
    	 }
    	 if(status == 0){
    		 return "待审核";
    	 }
    	 if(status == 1){
    		 return "审核中";
    	 }
    	 if(status == 2){
    		 return "审核通过";
    	 }
    	 if(status == 3){
    		 return "审核不通过";
    	 }
     }
    /**
     * 点击新增
     */
    LegalApply.add = function () {
        App.loadingAjax({
            url:App.ctxPath + 'legal/apply/checkAddApply',
            type:'post',
            success:function(data){
                var index = layer.open({
                    type: 2,
                    title: '法人资格申请登记',
                    area: ['100%', '100%'], //宽高
                    fix: false, //不固定
                    maxmin: true,
                    content: App.ctxPath + 'legal/apply/add'
                });
                this.layerIndex = index;
            }
        });


    };



        /**
         * 点击编辑
         */
        LegalApply.edit = function (id) {
            var index = layer.open({
                type: 2,
                title: '法人资格申请编辑',
                area: ['100%', '100%'], //宽高
                fix: false, //不固定
                maxmin: true,
                content: App.ctxPath + 'legal/apply/edit?id=' + id
            });
            this.layerIndex = index;
        };


    /**
     * 点击查看详情
     */
    LegalApply.detail = function (id) {
        var index = layer.open({
            type: 2,
            title: '查看详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: App.ctxPath + 'legal/apply/detail?id='+id
        });
        this.layerIndex = index;
    }
     
     /**
      * 点击查看流程
      */
     LegalApply.log = function (id) {
         var index = layer.open({
             type: 2,
             title: '查看流程',
             area: ['100%', '100%'], //宽高
             fix: false, //不固定
             maxmin: true,
             content: App.ctxPath + 'legal/apply/log?id='+id
         });
         this.layerIndex = index;
     }



        /**
         * 点击删除
         */
        LegalApply.del = function () {

            if (this.check()) {
                var status =LegalApply.seItem.status;
                if(status=="0"||status=="1"||status=="2"){
                    App.error("法人资格申请已经提交，不能再删除！");
                    return;
                }
                var id = LegalApply.seItem.id;
                var fun = function () {
                    var ajax = new $ax(App.ctxPath + "legal/apply/remove?id=" + id, function (data) {
                        App.success("成功删除！", '');
                    }, function (data) {
                        App.error("删除失败!");
                    });
                    ajax.start();
                    LegalApply.table.refresh({query: LegalApply.formParams()});
                }
                App.confirm("确定要删除吗？", fun);
            }
        };


    /**
     * 检查是否选中
     */
    LegalApply.check = function () {
        var selected = $('#' + this.id).bootstrapTable('getSelections');
        if (selected.length == 0) {
            App.info("请先选中表格中的某一记录！");
            return false;
        } else {
            LegalApply.seItem = selected[0];
            return true;
        }
    };
    
    /**
     * 查询表单提交参数对象
     */
    LegalApply.formParams = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        queryData['agentName'] =$("#agentName").val();
        return queryData;
    }

    /**
     * 查询列表
     */
    LegalApply.search = function () {
        LegalApply.table.refresh({query: LegalApply.formParams()});
    };

    $(function () {
        var defaultColunms = LegalApply.initColumn();
        var table = new BSTable(LegalApply.id, "legal/apply/list", defaultColunms);
        table.setPaginationType("server");
        table.setQueryParams(LegalApply.formParams());
        LegalApply.table = table.init();
    });