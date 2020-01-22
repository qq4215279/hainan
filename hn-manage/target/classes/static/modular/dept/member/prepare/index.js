	/**
	 * 初始化设定
	 */
	var IndexPage = {
		id : "OrgMemberTable", //表格id
		seItem : null, //选中的条目
		table : null,
		dlgLayerIndex : -1,
		layerIndex : -1
	};

	/**
	 * 初始化表格的列
	 */
	IndexPage.initColumn = function() {
		return [
				{field: 'selectItem', radio: true},
				{title: '单位名称', field: 'unit_name', align: 'center', valign: 'middle'},
				{title: '用户名称', field: 'name', align: 'center', valign: 'middle'},
				{title: '性别', field: 'sex', align: 'center', valign: 'middle',formatter:initSexFun},
				{title: '申请时间',field: 'create_time' ,align: 'center', valign: 'middle',formatter:initCreateTimeFun},
				{title: '操作',align: 'center', valign: 'middle',
					formatter:function (value,row,index){
						return '<a href="#" onclick="IndexPage.openDetail(\''+row.id+'\')">查看详情</a>';
					}
				}
		];
	};
	
	//初始化性别
	function initSexFun(value,row,index){
		var sex = row.sex;
		if(sex == 1){
			return "男";
		}
		if(sex == 2){
			return "女";
		}
	}
	
	//初始化申请时间
	function initCreateTimeFun(value,row,index){
		var create_time = row.create_time;
		return create_time.substring(0,4)+"-"+create_time.substring(4,6)+"-"+create_time.substring(6,8)+" "+create_time.substring(8,10)+":"+create_time.substring(10,12);
	}
	
	/**
	 * 查询表单提交参数对象 
	 * 
	 */
	IndexPage.formParams = function() {
		var queryData = {};
		queryData['name'] = $("#name").val();
		queryData['unitName'] = $("#unitName").val();
		queryData['certificateNum'] = $("#certificateNum").val();
		return queryData;
	}

	/**
	 * 查询日志列表
	 */
	IndexPage.search = function() {
		IndexPage.table.refresh({
			query : IndexPage.formParams()
		});
	};
	
	/**
	 * 打开查看详情
	 */
	IndexPage.openDetail = function(id) {
		var index = layer.open({
			type : 2,
			title : '查看详情',
			area : [ '100%', '100%' ], //宽高 
			fix : false, //不固定
			maxmin : true,
			content : App.ctxPath + 'prepare/member/detail/' + id
		});

		this.layerIndex = index;
	};
	
	/**
	 * 加载列表数据
	 */
	$(function() {
		var defaultColunms = IndexPage.initColumn();
		var table = new BSTable(IndexPage.id, "prepare/member/list",defaultColunms);
		table.setPaginationType("server");
		table.setQueryParams(IndexPage.formParams());
		IndexPage.table = table.init();
	});

	
