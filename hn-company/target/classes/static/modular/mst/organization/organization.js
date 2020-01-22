 	/**
     * 初始化表格对象
     */
    var OptOrganization = {
        id: "OrganizationTable",	//表格id
        seItem: null,		//选中的条目
        table: null,
        layerIndex: -1
    };
    /**
     * 初始化表格的列
     */
    OptOrganization.initColumn = function () {
        return [
            {field: 'selectItem', radio: true},
            {title: '工会名称', field: 'unionSimpleName', align: 'center', valign: 'middle'},
            {title: '工会编号', field: 'unionNumber', align: 'center', valign: 'middle'},
            {title: '序号', field: 'sort', align: 'center', valign: 'middle'},
            {title: '创建人', field: 'createUser', align: 'center', valign: 'middle'},
            {title: '建会时间', field: 'createUnionTime', align: 'center', valign: 'middle',formatter: initTimeField},
            {title: '操作', align: 'center',valign: 'middle',formatter: function (value, row, index) {
            		var lookMember = '<a href="javascript:OptOrganization.lookMember(' + row.deptId + ')">查看会员</a>',
            			lookMemberCadre = '<a href="javascript:OptOrganization.lookMemberCadre(' + row.deptId + ')">查看干部</a>';
                    return lookMember+'&nbsp;&nbsp;&nbsp;'+lookMemberCadre;
                }
            }
        ];
    }
    /**
     * 初始化创建日期字段的展示格式
     */
     function initTimeField(value, row, index){
     	if(row.createTime){
     		return returnTimeStr(row.createTime);
     	}
     }
     /**
      * 根据传入的时间，返回组装好的日期字符串
      */
     function returnTimeStr(time) {
    	 return time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8);
     }
    /**
     * 点击编辑
     */
    OptOrganization.edit = function () {
        if (this.check()) {
            var index = layer.open({
                type: 2,
                title: '修改组织信息',
                area: ['100%', '100%'], //宽高
                fix: false, //不固定
                maxmin: true,
                content: Feng.ctxPath + '/mst/organization/edit?id=' + OptOrganization.seItem.organizationId
            });
            this.layerIndex = index;
        }
    };
    
    /**
     * 打开批量导入添加的窗口
     */
    OptOrganization.openUpload = function () {
    	var index = layer.open({
            type: 2,
            title: '批量导入工会信息',
            area: ['100%', '100%'], //宽高
            fix: true, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/mst/organization/organization_upload'
        });
        layer.full(index); 
        this.layerIndex = index;
    };
    /**
     * 点击查看会员操作
     */
    OptOrganization.lookMember = function (id) {
        var index = layer.open({
            type: 2,
            title: '查看会员信息',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/mst/member?unionId='+id
        });
        this.layerIndex = index;
    }
    /**
     * 点击查看干部操作
     */
    OptOrganization.lookMemberCadre = function (id) {
        var index = layer.open({
            type: 2,
            title: '查看干部信息',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/mst/membercadre?unionId='+id
        });
        this.layerIndex = index;
    };
    /**
     * 检查是否选中
     */
    OptOrganization.check = function () {
        var selected = $('#' + this.id).bootstrapTable('getSelections');
        if (selected.length == 0) {
            Feng.info("请先选中表格中的某一记录！");
            return false;
        } else {
            OptOrganization.seItem = selected[0];
            return true;
        }
    };
    /**
     * 查询表单提交参数对象
     *
     */
    OptOrganization.formParams = function () {
        var queryData = {};
        queryData['unionName'] = $("#unionName").val();//工会名称
        queryData['unionNumber'] = $("#unionNumber").val();//工会编号
        queryData['deptId'] = $("#deptId").val();//上级工会id
        return queryData;
    }
    /**
     * 查询列表
     */
    OptOrganization.search = function () {
        OptOrganization.table.refresh({query: OptOrganization.formParams()});
    }
    
    //初始化工会组织的table
    function initTableFun() {
    	 var defaultColunms = OptOrganization.initColumn();
         var table = new BSTable(OptOrganization.id, "/mst/organization/list", defaultColunms);
         table.setPaginationType("server");
         table.setQueryParams(OptOrganization.formParams());
         OptOrganization.table = table.init();
       var loginDeptId = $("#loginOrgId").val();
         //初始化工作组织详情
    	window.initShowDetailDiv(loginDeptId);
    	//初始化统计图
    	window.initChart(loginDeptId);
    }
    $(function () {
    	//初始化工会组织树
    	initZtreeByJsonData();
        key =$("#key");
        key.value="";
        key.bind("focus", focusKey)
            .bind("blur", blurKey)
            .bind("propertychange", searchNode) //property(属性)change(改变)的时候，触发事件
            .bind("input", searchNode);
    });

    var key;
    
