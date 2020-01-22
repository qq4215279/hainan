/**
 * 初始化
 */
var DeptOrganization = {
    id: "OrganizationTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
 DeptOrganization.initColumn = function () {
	 return [
			 {field: 'selectItem', radio: true},
			 {title: '单位名称', field: 'unitName', align: 'center', valign: 'middle'}, 
	         {title: '统一社会信用代码', field: 'unitOrgCode', align: 'center', valign: 'middle'},
	         {title: '职工人数',field: 'unitNumber' ,align: 'center', valign: 'middle'},
	         {title: '单位地址', field: 'unitAddress', align: 'center', valign: 'middle'},
		     {title: '状态',field: 'status' ,align: 'center', valign: 'middle',formatter:initStatusFun},
	         {title: '操作',align: 'center', valign: 'middle',
	        	formatter:function (value,row,index){
	        		var status = row.status;
	        		var detail = '<a href="javascript:DeptOrganization.openDetail('+row.id+')">查看详情</a>',
		        		log = '<a href="javascript:DeptOrganization.log('+row.id+')">查看流程</a>',
		        		check = '<a href="javascript:DeptOrganization.openAdd('+row.id+')">审核</a>';
	        		if(status == 1){
	        			return check+"&nbsp;&nbsp;&nbsp;"+detail;
	        		}else{
	        			return detail+"&nbsp;&nbsp;&nbsp;"+log;
	        		}
	        	}
	        }
	  ];
};

//初始化审核状态
function initStatusFun(value,row,index){
	var status=row.status;
	if(status == 1 ){
		return '待审核';
	}
	if(status == 2){
		return '审核通过'
	}
	if(status == 3){
		return '审核不通过'
	}
}

/**
 * 打开查看信息的窗口
 */
DeptOrganization.openDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '查看详情',
        area: ['100%', '100%'], //宽高
        fix: true, //不固定
        maxmin: true,
        content: App.ctxPath + 'organization/check/detail?id='+id
    });
    layer.full(index);  
    this.layerIndex = index;
};

/**
 * 打开申请建会审核
 */
DeptOrganization.openAdd = function (id) {
    var index = layer.open({
        type: 2,
        title: '申请建会审核',
        area: ['100%', '100%'], //宽高
        fix: true, //不固定
        maxmin: true,
        content: App.ctxPath + 'organization/check/openAudit/'+id
    });
    layer.full(index);  
    this.layerIndex = index;
}; 

//打开查看流程的窗口
DeptOrganization.log = function (id){
   var index = layer.open({
        type: 2,
        title: '查看流程',
        area: ['100%', '100%'], //宽高
        fix: true, //不固定
        maxmin: true,
        content: App.ctxPath + 'organization/check/log/'+id
    });
    layer.full(index);  
    this.layerIndex = index;
}

/**
 * 查询表单提交参数对象 
 * 
 */
 DeptOrganization.formParams = function() {
    var queryData = {};
    queryData['status'] = $("#status").val();
    queryData['unitName'] = $("#unitName").val();
    return queryData;
}

/**
 * 查询安全事故列表
 */
 DeptOrganization.search = function () {
	 DeptOrganization.table.refresh({query: DeptOrganization.formParams()});
};

$(function () {
    var defaultColunms = DeptOrganization.initColumn();
    var table = new BSTable(DeptOrganization.id, "organization/check/auditList", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(DeptOrganization.formParams());
    DeptOrganization.table = table.init();
});