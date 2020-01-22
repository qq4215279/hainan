/**
 * 初始化表格对象
 */
var LegalChange = {
    id: "LegalChangeTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
LegalChange.initColumn = function () {
    return [

        {field: 'selectItem', radio: true},
        {title: '工会名称', field: 'deptName', align: 'center', valign: 'middle'},

        {title: '工会地址', field: 'unitAddress', align: 'center', valign: 'middle'},
        {title: '工会邮编', field: 'unitZipCode', align: 'center', valign: 'middle'},
        {title: '法定代表人', field: 'name', align: 'center', valign: 'middle'},
        //   {title: '身份证', field: 'certificateNo', align: 'center', valign: 'middle'},
        //    {title: '经办人', field: 'agentName', align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', formatter: initTimeField},
        {title: '状态', field: 'status', align: 'center', valign: 'middle', formatter: initStatusField},
        {
            title: '操作', align: 'center', valign: 'middle',
            formatter: function (value, row, index) {
                var detail = '<a href="javascript:LegalChange.detail(' + row.id + ')">查看详情</a>';
                var log = '<a href="javascript:LegalChange.log(' + row.id + ')">查看流程</a>';
                var edit='<a href="javascript:LegalChange.edit('+row.id+')">编辑</a>'
                if(!row.status){
                    return edit+"&nbsp;&nbsp;&nbsp;"+detail;
                }
                return detail + "&nbsp;&nbsp;&nbsp;" + log;
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
function initStatusField(value, row, index) {
   var status = row.status;
    if (!status) {
        return "草稿";
    }
    if (status == 0) {
        return "待审核";
    }
    if (status == 1) {
        return "审核中";
    }
    if (status == 2) {
        return "审核通过";
    }
    if (status == 3) {
        return "审核不通过";
    }
}

/**
 * 点击新增
 */
LegalChange.add = function () {
    App.loadingAjax({
        url:App.ctxPath + 'legal/change/checkAddApply',
        success:function(){
            var index = layer.open({
                type: 2,
                title: '法人资格变更登记',
                area: ['100%', '100%'], //宽高
                fix: false, //不固定
                maxmin: true,
                content: App.ctxPath + 'legal/change/add'
            });
            this.layerIndex = index;
        }

    });

};

/**
 * 点击编辑
 */
LegalChange.edit = function (id) {
    var index = layer.open({
        type: 2,
        title: '法人资格变更编辑',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: App.ctxPath + 'legal/change/edit?id=' + id
    });
    this.layerIndex = index;
};

/**
 * 点击查看详情
 */
LegalChange.detail = function (id) {
    var index = layer.open({
        type: 2,
        title: '查看详情',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: App.ctxPath + 'legal/change/detail?id=' + id
    });
    this.layerIndex = index;
}

/**
 * 点击查看流程
 */
LegalChange.log = function (id) {
    var index = layer.open({
        type: 2,
        title: '查看流程',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: App.ctxPath + 'legal/change/log?id=' + id
    });
    this.layerIndex = index;
}

/**
 * 点击删除
 */
LegalChange.del = function () {

    if (this.check()) {
        var status =LegalChange.seItem.status;
        if(status && status != '3'){
            App.error("法人资格申请已经提交，不能再删除！");
            return;
        }
        var id = LegalChange.seItem.id;
        var fun = function () {
            var ajax = new $ax(App.ctxPath + "legal/change/remove?id=" + id, function (data) {
                App.success("成功删除！", '');
            }, function (data) {
                App.error("删除失败!");
            });
            ajax.start();
            LegalChange.table.refresh({query: LegalChange.formParams()});
        }
        App.confirm("确定要删除吗？", fun);
    }
};

/**
 * 检查是否选中
 */
LegalChange.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        App.info("请先选中表格中的某一记录！");
        return false;
    } else {
        LegalChange.seItem = selected[0];
        return true;
    }
};

/**
 * 查询表单提交参数对象
 */
LegalChange.formParams = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    // queryData['agentName'] = $("#agentName").val();
    queryData['deptName'] = $("#deptName").val();
    return queryData;
}

/**
 * 查询列表
 */
LegalChange.search = function () {
    LegalChange.table.refresh({query: LegalChange.formParams()});
};

$(function () {
    var defaultColunms = LegalChange.initColumn();
    var table = new BSTable(LegalChange.id, "legal/change/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(LegalChange.formParams());
    LegalChange.table = table.init();
});