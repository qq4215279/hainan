/**
 * 初始化表格对象
 */
var LawProblem = {
    id: "LawProblemTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
LawProblem.initColumn = function () {
    return [

        {field: 'selectItem', radio: true},
        {title: '法律问题标题', field: 'title', align: 'center', valign: 'middle'},
        {title: '法律问题描述', field: 'content', align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', formatter: initTimeField},
          {
            title: '操作', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                var detail = '<a href="javascript:LawProblem.detail(' + row.id + ')">查看详情</a>';
                return detail ;
                // var log = '<a href="javascript:LawProblem.log(' + row.id + ')">查看流程</a>';
                // var edit='<a href="javascript:LawProblem.edit('+row.id+')">编辑</a>'
                // if(!row.status){
                //   return edit+"&nbsp;&nbsp;&nbsp;"+detail;
                // }
                //
            }
        }
    ];
}

//初始化列表的时间类型的字段
function initTimeField(value, row, index) {
    var time = row.createTime;
    if (time) {
        return time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8) + " " + time.substring(8, 10) + ":" + time.substring(10, 12);
    }
}

//初始化审核状态
// function initStatusField(value, row, index) {
//    var status = row.status;
//     if (!status) {
//         return "草稿";
//     }
//     if (status == 0) {
//         return "待审核";
//     }
//     if (status == 1) {
//         return "审核中";
//     }
//     if (status == 2) {
//         return "审核通过";
//     }
//     if (status == 3) {
//         return "审核不通过";
//     }
// }

/**
 * 点击新增
 */
LawProblem.add = function () {
    // App.loadingAjax({
        // url:App.ctxPath + 'law/problem/checkAddApply',
    //     success:function(){
            var index = layer.open({
                 type: 2,
                title: '添加问题',
                area: ['100%', '100%'], //宽高
                fix: false, //不固定
                maxmin: true,
                content: App.ctxPath + 'law/problem/add'
            });
            this.layerIndex = index;
};

/**
 * 点击编辑
 */
LawProblem.edit = function (id) {
    if (this.check()) {
        var id = LawProblem.seItem.id;
        var index = layer.open({
            type: 2,
            title: '编辑问题',
            area: ['100%', '100%'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: App.ctxPath + 'law/problem/edit?id=' + id
        });
        this.layerIndex = index;
    }
};

/**
 * 点击查看详情
 */
LawProblem.detail = function (id) {

    var index = layer.open({
        type: 2,
        title: '查看详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: App.ctxPath + 'law/problem/detail?id=' + id
    });
    this.layerIndex = index;

}

/**
 * 点击查看流程
 */
// LawProblem.log = function (id) {
//     var index = layer.open({
//         type: 2,
//         title: '查看流程',
//         area: ['100%', '100%'], //宽高
//         fix: false, //不固定
//         maxmin: true,
//         content: App.ctxPath + 'legal/change/log?id=' + id
//     });
//     this.layerIndex = index;
// }

/**
 * 点击删除
 */
LawProblem.del = function () {

    if (this.check()) {
        var status =LawProblem.seItem.status;
        var id = LawProblem.seItem.id;
        var fun = function () {
            var ajax = new $ax(App.ctxPath + "law/problem/remove?id=" + id, function (data) {
                App.success("成功删除！", '');
            }, function (data) {
                App.error("删除失败!");
            });
            ajax.start();
            LawProblem.table.refresh({query: LawProblem.formParams()});
        }
        App.confirm("确定要删除吗？", fun);
    }
};

/**
 * 检查是否选中
 */
LawProblem.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        App.info("请先选中表格中的某一记录！");
        return false;
    } else {
        LawProblem.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 */
LawProblem.formParams = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    // queryData['agentName'] = $("#agentName").val();
    queryData['deptName'] = $("#deptName").val();
    return queryData;
}

/**
 * 查询列表
 */
LawProblem.search = function () {
    LawProblem.table.refresh({query: LawProblem.formParams()});
};

$(function () {
    var defaultColunms = LawProblem.initColumn();
    var table = new BSTable(LawProblem.id, "law/problem/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(LawProblem.formParams());
    LawProblem.table = table.init();
});