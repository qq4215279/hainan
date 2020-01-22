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
				{title: '申请用户', field: 'name', align: 'center', valign: 'middle'},
				{title: '性别', field: 'sex', align: 'center', valign: 'middle',formatter:initSexFun},
				{title: '申请类型', field: 'apply_type', align: 'center', valign: 'middle',formatter:initApplyTypeFun}, 
				{title: '申请时间',field: 'create_time' ,align: 'center', valign: 'middle',formatter:initCreateTimeFun},
				{title: '申请状态', field: 'apply_status', align: 'center', valign: 'middle',formatter:initApplyStatusFun},
				{title: '操作',align: 'center', valign: 'middle',
					formatter:function (value,row,index){
						return '<a href="#" onclick="IndexPage.openDetail(\''+row.id+'\')">查看信息</a>'+"&nbsp;&nbsp;&nbsp;"+
						'<a href="#" onclick="IndexPage.openLog(\''+row.id+'\')">查看流程</a>';
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
	
	//初始化申请类型
	function initApplyTypeFun(value,row,index){
		var apply_type = row.apply_type;
		if(apply_type == 1){
			return "入会申请";
		}
		if(apply_type == 2){
			return "转会申请";
		}
		if(apply_type == 3){
			return "办卡申请";
		}
	}
	
	//初始化申请时间
	function initCreateTimeFun(value,row,index){
		var create_time = row.create_time;
		return create_time.substring(0,4)+"-"+create_time.substring(4,6)+"-"+create_time.substring(6,8)+" "+create_time.substring(8,10)+":"+create_time.substring(10,12);
	}
	
	//初始化申请状态
	function initApplyStatusFun(value,row,index){
		var apply_status = row.apply_status;
		if(apply_status == 0){
			return '拒绝';
		}
		if(apply_status == 1){
			return '通过';
		}
		var lognum = row.lognum;
		if(apply_status == '-1' && parseInt(lognum) == 1){
			return '待审核';
		}
		return "通过";
	}

	/**
	 * 检查是否选中
	 */
	IndexPage.check = function() {
		var selected = $('#' + this.id).bootstrapTable('getSelections');
		if (selected.length == 0) {
			App.info("请先选中至少一条表格中的记录！");
			return false;
		} else {
			IndexPage.seItem = selected;
			return true;
		}
	};

	/**
	 * 查询表单提交参数对象 
	 * 
	 */
	IndexPage.formParams = function() {
		var queryData = {};
		queryData['applyType'] = $("#applyType").val();
		queryData['name'] = $("#name").val();
		queryData['status'] = "";
		var createTime = $("#createTime").val();
		if(createTime){
			createTime = createTime.replaceAll("-","");
		}
		queryData['createTime'] = createTime;
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
			title : '查看信息',
			area : [ '100%', '100%' ], //宽高 
			fix : false, //不固定
			maxmin : true,
			content : App.ctxPath + 'approve/transfer/detail/' + id
		});

		this.layerIndex = index;
	};
	
	/**
	 * 打开查看审核流程
	 */
	IndexPage.openLog = function(id) {
		var index = layer.open({
			type : 2,
			title : '查看流程',
			area : [ '100%', '100%' ], //宽高 
			fix : false, //不固定
			maxmin : true,
			content : App.ctxPath + 'approve/transfer/log/' + id
		});

		this.layerIndex = index;
	};

	/**
	 * 加载列表数据
	 */
	$(function() {
		var defaultColunms = IndexPage.initColumn();
		var table = new BSTable(IndexPage.id, "approve/transfer/list",defaultColunms);
		table.setPaginationType("server");
		table.setQueryParams(IndexPage.formParams());
		IndexPage.table = table.init();
	});

	
