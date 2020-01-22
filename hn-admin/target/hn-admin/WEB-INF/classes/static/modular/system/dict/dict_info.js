/**
 * 初始化字典详情对话框
 */
var DictInfoDlg = {
    count: $("#itemSize").val(),
    dictName: '',			//字典的名称
    groupCode: '',          //
    entrys: [],		//拼接字符串内容(拼接字典条目)
    itemTemplate: $("#itemTemplate").html(),
    thirdTemplate: $("#thirdTemplate").html(),
};

/**
 * item获取新的id
 */
DictInfoDlg.newId = function () {
    if (this.count == undefined) {
        this.count = 0;
    }
    this.count = this.count + 1;
    return "dictItem" + this.count;
};

/**
 * 关闭此对话框
 */
DictInfoDlg.close = function () {
    parent.layer.close(window.parent.Dict.layerIndex);
};

/**
 * 添加条目
 */
DictInfoDlg.addItem = function () {
    var item = $(this.itemTemplate);
    $("#itemsArea").append(item);
};


/**
 * 清除为空的item Dom
 */
DictInfoDlg.clearNullDom = function () {
    this.entrys = [];
};

/**
 * 收集添加字典的数据
 */
DictInfoDlg.collectData = function () {
    this.clearNullDom();
    var entrys = [];
    $("[name='dictItem']").each(function () {
        var entry = {};
        entry.name = $(this).find("[name='itemName']").val();
        entry.code = $(this).find("[name='itemNum']").val();
        entry.entrys=[];
        $(this).find("[name='thirdItem']").each(function () {
            var entryThird = {};
            entryThird.name = $(this).find("[name='itemName']").val();
            entryThird.code = $(this).find("[name='itemNum']").val();
            entry.entrys.push(entryThird);
        });
        entrys.push(entry);
    });
    this.entrys = entrys;
    this.dictName = $("#dictName").val();
    this.groupCode = $("#groupCode").val();
};


/**
 * 提交添加字典
 */
DictInfoDlg.addSubmit = function () {
    if ($("[name='dictItem']").length <= 0) {
        App.error("请输入至少一个属性值");
        return;
    }
    this.collectData();

    App.loadingAjax({
        url: App.ctxPath + "dict/renew",
        type: "POST",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        data: JSON.stringify({dictName: this.dictName, groupCode: this.groupCode, entrys: this.entrys}),
        success: function (result) {
            if (result.code = 200) {
                App.success("添加成功!");
                window.parent.Dict.table.refresh();
                DictInfoDlg.close();
            } else {
                App.error(result.message + "!");
            }
        }, error: function () {
            App.error("添加失败!" + data.responseJSON.message + "!");
        }

    });
};


$(function () {
    $("#itemsArea").on("click", ".add-third", function () {
        var item = $(DictInfoDlg.thirdTemplate);
        $(this).parents(".dictItem").find(".third").append(item);
    });

    $("#itemsArea").on("click", ".remove-btn", function () {
        $(this).parents(".dictItem").remove();
    });

    $("#itemsArea").on("click", ".third-remove-btn", function () {
        $(this).parents(".thirdItem").remove();
    });
});