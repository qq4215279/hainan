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
        {title: '工会名称', field: 'dept_name', align: 'center', valign: 'middle'},
        {title: '法定代表人', field: 'name', align: 'center', valign: 'middle'},
        {title: '经办人', field: 'agent_name', align: 'center', valign: 'middle'},
        {title: '状态', field: 'status', align: 'center', valign: 'middle',formatter: initStatusField},
        {title: '创建时间', field: 'create_time', align: 'center', valign: 'middle',formatter: initTimeField},
        {title: '操作', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                var check = '<a href="javascript:LegalApply.check(' + row.id + ')">审核</a>',
                    log = '<a href="javascript:LegalApply.log(' + row.id + ')">查看流程</a>',
                    detail = '<a href="javascript:LegalApply.detail(' + row.id + ')">查看详情</a>';
                if(row.status == '3' || row.bool == '0'){
                    return detail+"&nbsp;&nbsp;&nbsp;"+log;
                }
                return check+"&nbsp;&nbsp;&nbsp;"+log;
            }
        }
    ];
}
//初始化列表的时间类型的字段
function initTimeField(value, row, index){
    var time = row.create_time;
    if(time){
        return time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8)+" "+time.substring(8,10)+":"+time.substring(10,12);
    }
}
//初始化审核状态
function initStatusField(value, row, index){
    if(row.status == '3'){
        return "审核不通过";
    }
    if(row.bool == '0'){
        return "审核通过";
    }
    return "待审核";
}

/**
 * 审核
 */
LegalApply.check = function (id) {
    var index = layer.open({
        type: 2,
        title: '审核',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: App.ctxPath + 'legal/apply/checkDetail?id='+id
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
    var table = new BSTable(LegalApply.id, "legal/apply/checkList", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(LegalApply.formParams());
    LegalApply.table = table.init();
});