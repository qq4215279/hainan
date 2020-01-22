/**
 * 初始化
 */
var OptMstMemberCadre = {
    id: "OptMstMemberCadreTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
/**
 * 初始化表格的列
 */
OptMstMemberCadre.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '姓名', field: 'name',visible: true, align: 'center', valign: 'middle'},
        {title: '工会职务', field: 'union_position', align: 'center', valign: 'middle',formatter: resultUnionPositionField},
        {title: '专/兼职工会干部', field: 'full_time_union', align: 'center', valign: 'middle',formatter: initFullTimeUnionField},
        {
            title: '操作', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                return '<a href="javascript:OptMstMemberCadre.detail(' + row.cadre_id + ')">&nbsp;详情&nbsp;</a>';
            }
        }
    ];
};
 function initFullTimeUnionField(value, row, index){
	if(!row.full_time_union){
		return;
	}
  	if(row.full_time_union == 1){
  		return "专职";
  	}
  	if(row.full_time_union == 2){
  		return "兼职";
  	}
  }
 function resultUnionPositionField(value, row, index){
	 var position = row.union_position;
	 if(!position){
  		return;
  	 }
 	 if(position == 1){
 		 return "工会主席";
 	 }
 	 if(position == 2){
 		 return "工会副主席";
 	 }
 	 if(position == 3){
 		 return "工会常委";
 	 }
 	 if(position == 4){
 		 return "工会委员";
 	 }
 	 if(position == 5){
 		 return "经审委主任";
 	 }
 	 if(position == 6){
 		 return "经审委副主任";
 	 }
 	 if(position == 7){
 		 return "经审委委员";
 	 }
 	 if(position == 8){
 		 return "女工委主任";
 	 }
 	 if(position == 9){
 		 return "女工委副主任";
 	 }
 	 if(position == 10){
 		 return "女工委委员";
 	 }
 	 if(position == 11){
		 return "工会工委主任";
	 }
 	 if(position == 12){
   		 return "工会工委委员";
   	 }
 	 if(position == 13){
   		 return "工会支会主席";
   	 }
 }
 /**
  * 返回组装好的日期字符串
  */
 function returnTimeStr(time) {
	 return time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8);
 }
 
/**
 * 查询表单提交参数对象
 *
 */
OptMstMemberCadre.formParams = function () {
    var queryData = {};
    queryData['unionId'] = $("#deptId").val();
    return queryData;
}

/**
 * 点击查看详情操作
 */
OptMstMemberCadre.detail = function (id) {
    var index = layer.open({
        type: 2,
        title: '查看详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/mst/membercadre/detail?id='+id
    });
    this.layerIndex = index;
}

/**
 * 查询列表
 */
OptMstMemberCadre.search = function () {
    OptMstMemberCadre.table.refresh({query: OptMstMemberCadre.formParams()});
};

$(function () {
    var defaultColunms = OptMstMemberCadre.initColumn();
    var table = new BSTable(OptMstMemberCadre.id, "/mst/membercadre/selectList", defaultColunms);
    table.setPaginationType("client");
    table.setQueryParams(OptMstMemberCadre.formParams());
    table.pagination=false;
    table.height = "100%";
    OptMstMemberCadre.table = table.init();
});