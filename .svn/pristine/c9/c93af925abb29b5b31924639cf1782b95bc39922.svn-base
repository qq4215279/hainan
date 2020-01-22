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
var classes=['color-green','color-green-light','color-yellow','color-orange','color-red'];
OptOrganization.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {
            title: '工会名称',
            field: 'union_name',
            align: 'center',
            valign: 'middle',
            formatter: function (value, row, index) {
                var result = '<a href="javascript:OptOrganization.watch(' + row.id + ')" class="bolid '+classes[row.union_type-1]+'">' + value + '</a>';
                return result;
            }
        },
        {title: '工会负责人', field: 'union_leader', align: 'center', valign: 'middle'},
        {title: '联系电话', field: 'union_leader_phone', align: 'center', valign: 'middle'},
        {title: '会员人数', field: 'member_count', align: 'center', valign: 'middle'},
        {title: '创建人', field: 'createUser', align: 'center', valign: 'middle'},
        {title: '建会时间', field: 'createunion_time', align: 'center', valign: 'middle', formatter: initTimeField},
        {
            title: '操作', align: 'center', valign: 'middle', formatter: function (value, row, index) {
                var lookMember = '<a href="javascript:OptOrganization.lookMember(' + row.dept_id + ')">查看会员</a>';
                return lookMember;
            }
        }
    ];
}

/**
 * 初始化创建日期字段的展示格式
 */
function initTimeField(value, row, index) {
    if (row.createunion_time) {
        return returnTimeStr(row.createunion_time);
    }
}

/**
 * 根据传入的时间，返回组装好的日期字符串
 */
function returnTimeStr(time) {
    return time.substring(0, 4) + "-" + time.substring(4, 6) + "-" + time.substring(6, 8);
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
            content: App.ctxPath + 'dept/organization/edit?id=' + OptOrganization.seItem.id,
            end: function () {
                OptOrganization.search();
                initZtreeByJsonData(OptOrganization.formParams.deptId);
            }
        });
        this.layerIndex = index;
    }
};

/**
 * 点击编辑
 */
OptOrganization.watch = function (id) {
    var index = layer.open({
        type: 2,
        title: '修改组织信息',
        area: ['100%', '100%'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: App.ctxPath + 'dept/organization/watch?id=' + id
    });
    this.layerIndex = index;
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
        content: App.ctxPath + 'dept/organization/organization_upload',
        end: function () {
            $("#unionName").val("");
            $("#deptNo").val("");
            // $("#deptId").val("1");
            initZtreeByJsonData();
        }
    });
    layer.full(index);
    this.layerIndex = index;
};


OptOrganization.exportList=function(){
    var queryData=OptOrganization.formParams();
    var form=$("<form method='post' action='"+App.ctxPath+"/dept/organization/exportList'></form>");
    form.append("<input type='hidden' name='unionName' value='"+ queryData['unionName']+"'>");
    form.append("<input type='hidden' name='deptNo' value='"+ queryData['deptNo'] +"'>");
    form.append("<input type='hidden' name='deptId' value='"+ queryData['deptId']+"'>");
    $('body').append(form);
    form.submit();
}
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
        content: App.ctxPath + 'dept/member?deptId=' + id,
        end: function () {
            OptOrganization.search();
        }
    });
    this.layerIndex = index;
}
/**
 * 检查是否选中
 */
OptOrganization.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        App.info("请先选中表格中的某一记录！");
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
    queryData['deptNo'] = $("#deptNo").val();//工会编号
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
    var table = new BSTable(OptOrganization.id, "dept/organization/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(OptOrganization.formParams());
    OptOrganization.table = table.init();
}

$(function () {
    //初始化工会组织树
    initZtreeByJsonData();
    key = $("#key");
    key.value = "";
    key.bind("focus", focusKey)
        .bind("blur", blurKey)
        .bind("propertychange", searchNode) //property(属性)change(改变)的时候，触发事件
        .bind("input", searchNode);
});

var key;
    
