/**
     * 初始化表格对象
     */
    var OptDeptMember = {
        id: "OptDeptMemberTable",	//表格id
        seItem: null,		//选中的条目
        table: null,
        layerIndex: -1
    };

    /**
     * 初始化表格的列
     */
     OptDeptMember.initColumn = function () {
        return [
            {field: 'selectItem', radio: true},
            {title: '姓名', field: 'name', align: 'center', valign: 'middle'},
            {title: '性别', field: 'sex', align: 'center', valign: 'middle',formatter: initSexField},
            {title: '会员卡号', field: 'member_card', align: 'center', valign: 'middle'},
            {title: '是否农民工', field: 'is_farmer', align: 'center', valign: 'middle',formatter: initIfFarmerField},
            {title: '创建时间', field: 'create_time', align: 'center', valign: 'middle',formatter: initTimeField},
            {title: '操作', align: 'center', valign: 'middle',
                formatter: function (value, row, index) {
                    return '<a href="javascript:OptDeptMember.detail(' + row.member_id + ')">查看详情</a>';
                }
            }
        ];
    }
    //初始化是否农民工字段
    function initIfFarmerField(value, row, index) {
    	if(row.is_farmer && row.is_farmer == '1'){
    		return '是';
    	}
    	return '否';
    }
    /**
     * 初始化列表的时间类型的字段
     */
     function initTimeField(value, row, index){
     	if(row.create_time){
     		return returnTimeStr(row.create_time);
     	}
     }
     /**
      * 返回组装好的日期字符串
      */
     function returnTimeStr(time) {
    	 return time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8);
     }
     /**
      * 初始化性别字段
      */
     function initSexField(value, row, index) {
    	 if(row.sex && row.sex == 1) {
    		 return '男';
    	 }
    	 return '女';
     }
    /**
     * 点击新增
     */
     OptDeptMember.add = function () {
        var index = layer.open({
            type: 2,
            title: '新增会员信息',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: App.ctxPath + 'company/member/add'
        });
        this.layerIndex = index;
    };
    
    /**
     * 点击编辑
     */
     OptDeptMember.edit = function () {
        if (this.check()) {
            var index = layer.open({
                type: 2,
                title: '修改会员信息',
                area: ['100%', '100%'], //宽高
                fix: false, //不固定
                maxmin: true,
                content: App.ctxPath + 'company/member/edit?id=' + OptDeptMember.seItem.member_id
            });
            this.layerIndex = index;
        }
    };

    /**
     * 检查是否选中
     */
     OptDeptMember.check = function () {
        var selected = $('#' + this.id).bootstrapTable('getSelections');
        if (selected.length == 0) {
            App.info("请先选中表格中的某一记录！");
            return false;
        } else {
        	OptDeptMember.seItem = selected[0];
            return true;
        }
    };
    /**
     * 点击删除
     */
     OptDeptMember.del = function () {
        if (this.check()) {
        	var id =  OptDeptMember.seItem.member_id;
    		var fun = function (){
    		    var ajax = new $ax(App.ctxPath+"company/member/remove?id=" + id, function (data) {
    		        App.success("成功删除！", '');
    		    }, function (data) {
    		        App.error("删除失败!");
    		    });
    		    ajax.start();
    		    OptDeptMember.table.refresh({query: OptDeptMember.formParams()});
    		}
    		App.confirm("确定要删除吗？", fun);
        }
    };
    
    /**
     * 查看详情
     */
     OptDeptMember.detail = function (id) {
        var index = layer.open({
            type: 2,
            title: '查看详情',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: App.ctxPath + 'company/member/detail?id='+id
        });
        this.layerIndex = index;
    }
    
    /**
     * 查询表单提交参数对象
     *
     */
     OptDeptMember.formParams = function () {
        var queryData = {};
        queryData['name'] = $("#name").val();
        queryData['memberCard'] =$("#memberCard").val();
        return queryData;
    }

    /**
     * 查询列表
     */
     OptDeptMember.search = function () {
    	 OptDeptMember.table.refresh({query: OptDeptMember.formParams()});
    };

    $(function () {
        var defaultColunms = OptDeptMember.initColumn();
        var table = new BSTable(OptDeptMember.id, "company/member/list", defaultColunms);
        table.setPaginationType("server");
        table.setQueryParams(OptDeptMember.formParams());
        OptDeptMember.table = table.init();
    });