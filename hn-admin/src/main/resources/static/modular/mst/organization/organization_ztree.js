//该工会组织结构树的js,右击ztree节点时可添加部门、工会信息
//注意：当前js,只用于工会组织管理页面使用；其他页面使用只是用于展示，不进行部门、工会数据的录入
	var setting = { //展示左侧工会组织结构树
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false
		},
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: ""
			}
		},
		callback: {
			onClick:onclickTree,
			onRightClick: OnRightClick,
            onAsyncSuccess:zTreeOnAsyncSuccess
		},
        async : {

            enable : true,// 开启异步加载

            url : App.ctxPath + "/dept/belowTreeByOrgId",//对应的后台请求路径

            dataType : "json",

            autoParam : [ "id=parentId" ]// 异步加载时需要自动提交父节点属性的参数

        },
	}
    //ztree的点击事件
    function onclickTree(e, treeId, treeNode) {
    	$("#deptId").val(treeNode.id);
	    OptOrganization.search();
	    //更新右侧工会组织列表时；也更新工会组织详情;
	    window.initShowDetailDiv(treeNode.id);
	    //初始化统计图;干部信息等
    	window.initChart(treeNode.id);
    	//更新干部列表
    	OptMstMemberCadre.search();
    }
    //初始化工会组织详情展示的数据
    window.initShowDetailDiv = function (id){
    	var ajax = new $ax(App.ctxPath + "/mst/organization/getMstOrganizationByDeptId", function (data) {
            updateDetailValFun(data);
        }, function (data) {});
        ajax.set("deptId",id);
        ajax.start();
    }
    //更新工会组织详情展示的数据
    function updateDetailValFun(data){
    	$("#orgFullName").val(data.unionName);//工会全称
    	$("#orgSinName").val(data.unionSimpleName);//工会简称
    	$("#orgNum").val(data.unionNumber);//工会编号
    	if(!data.createUnionTime){
    		$("#orgCreateTime").val(initTimeFun(data.createTime));//建会时间
    	}else{
    		$("#orgCreateTime").val(initTimeFun(data.createUnionTime));//建会时间
    	}
    	//更新统计图
    }

    //异步加载成功后调用的方法
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){

		var treeObj = $.fn.zTree.getZTreeObj(treeId);

		var nodes = treeObj.getNodes();

		if (nodes.length>0) {

			for(var i=0;i<nodes.length;i++){

				treeObj.expandNode(nodes[i], true, false, false);//默认展开第一级节点

			}

		}

	}

    //初始化时间类型的字段
  	function initTimeFun(field) {
	   if(field) {
	 	  return field.substring(0,4)+"-"+field.substring(4,6)+"-"+field.substring(6,8);
	   }
	   return field;
  	}
    //ztree的右击鼠标事件
    function OnRightClick(e, treeId, treeNode) {
    	//判断当前登录用户是否具有添加、编辑、删除工会的权限；没有权限则直接返回false
    	if(!$("#addRelationByUser").val()){
    		return false;
    	}
    	if (treeNode && !treeNode.noR) {
    		zTree.selectNode(treeNode);
    		var deptId = zTree.getSelectedNodes()[0].id;
    		var deptType = zTree.getSelectedNodes()[0].deptType;
    		var loginDeptId = $("#loginOrgId").val();
    		if(deptId==loginDeptId){
    			$("#rMenu ul li:eq(3)").css({"display": "none"});//隐藏删除按钮
    		}else{
    			$("#rMenu ul li:eq(3)").css({"display": ""});
    		}
    		if(deptType==1){
    			$("#rMenu ul li:eq(0)").css({"display": "none"});//部门
    			$("#rMenu ul li:eq(1)").css({"display": "none"});
    		}else{
    			$("#rMenu ul li:eq(0)").css({"display": ""});//工会组织
    			$("#rMenu ul li:eq(1)").css({"display": ""});
    		}
    		showRMenu(event.clientX, event.clientY);
    	}
    }
    //显示右击菜单
    function showRMenu( x, y) {
    	$("#rMenu ul").show();
    	y = y + document.documentElement.scrollTop-80;
        x = x + document.documentElement.scrollLeft-15;
        rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});
    }
    //隐藏右键菜单
    function hideRMenu() {
    	if (rMenu) rMenu.css({"visibility": "hidden"});
    };
    //点击页面隐藏右键菜单
    $("body").click(function(){  
    	hideRMenu();
    });
    //添加下级工会、本级部门
    function addTreeNode(value) {
    	hideRMenu();//隐藏右键菜单
    	var height = '580px';
    	var title = '添加下级工会';
    	if(value==1){
    		height = '450px';
    		title = '添加本级部门';
    	}
    	var url =  '/mst/organization/addOrganizationOrDept?id='+zTree.getSelectedNodes()[0].id+'&deptType='+value;
    	var index = layer.open({
            type: 2,
            title: title,
            area: ['500px', height], //宽高
            fix: false, //不固定
            maxmin: true,
            content: App.ctxPath +url
        });
    };
    
	//修改下级工会
	function editTreeNode(){
		var value = zTree.getSelectedNodes()[0].deptType;
		hideRMenu();
		var height = '580px';
		var title = '修改下级工会';
		if(value==1){
			height = '450px';
			title = '修改本级部门';
		}
		var url =  '/mst/organization/editOrganizationOrDept?id='+zTree.getSelectedNodes()[0].id+'&deptType='+value;
		var index = layer.open({
	        type: 2,
	        title: title,
	        area: ['500px', height], //宽高
	        fix: false, //不固定
	        maxmin: true,
	        content: App.ctxPath + url
	    });
	}
	//删除部门信息或工会组织信息
	function removeTreeNode() {
		hideRMenu();
		var nodes = zTree.getSelectedNodes();
		var msg = "是否确认刪除?";
		if (nodes && nodes.length>0) {
			if (nodes[0].children && nodes[0].children.length > 0) {
				msg = "删除该数据，将删除该数据下的所有数据!是否确认删除？";
			} 
			var operation = function(){
	            var ajax = new $ax(App.ctxPath + "/mst/organization/removeOrganizationAndDept", function () {
	                App.success("删除成功!");
	                zTree.removeNode(nodes[0]);
	            }, function (data) {
	                App.error("删除失败!" + data.responseJSON.message + "!");
	            });
	            ajax.set("deptId",nodes[0].id);
	            ajax.start();
	        };
	        App.confirm(msg, operation);
		}
	}
    var zTree,rMenu;
    //初始化组织结构左侧的tree
    function initZtreeByJsonData(dept_id) {
    	$.ajax({  
            async : false,  
            cache:false,  
            type: 'POST',  
            dataType : "json",  
            url: App.ctxPath + "/dept/belowTreeByOrgId",//请求的action路径
            error: function () {//请求失败处理函数  
                alert('请求失败');  
            },  
            success:function(data){ //请求成功后处理函数。    
            	var t = $("#deptTree");
             	$.fn.zTree.init(t, setting, data);
            	zTree = $.fn.zTree.getZTreeObj("deptTree");
            	rMenu = $("#rMenu");
            	
            	//判断id是否存在；存在则表示当前是部门或工会添加页面执行的刷新操作
		    	if(dept_id) {
		    		$("#deptId").val(dept_id);
		    	    OptOrganization.search();
		    	}else {
		    		//工会组织结构树调用成功后，再加载工会组织的table
		    		initTableFun();
		    	}
            }  
        });
    }
	var lastValue = "", nodeList = [], fontCss = {};
	function callNumber(){
		var zTree = $.fn.zTree.getZTreeObj("deptTree");
		if(nodeList.length){
			zTree.selectNode(nodeList[0],false );
			document.getElementById("key").focus();

			clickCount=1;

			//显示当前所在的是第一条
			document.getElementById("number").innerHTML="["+clickCount+"/"+nodeList.length+"]";

		}else if(nodeList.length == 0){
			document.getElementById("number").innerHTML="[0/0]";
			zTree.cancelSelectedNode(); //取消焦点
		}
		//如果输入框中没有搜索内容，则清空标签框
		if(document.getElementById("key").value ==""){
			document.getElementById("number").innerHTML="";
			zTree.cancelSelectedNode();
		}
	}
	function focusKey(e) {
		if (key.hasClass("empty")) {
			key.removeClass("empty");
		}
	}
	function blurKey(e) {
		if (key.get(0).value === "") {
			key.addClass("empty");
		}
	}
	//搜索节点
	function searchNode(e) {
		var zTree = $.fn.zTree.getZTreeObj("deptTree");
		var value = $.trim(key.get(0).value);
		var keyType = "name";

		if (key.hasClass("empty")) {
			value = "";
		}
		if (lastValue === value) return;
		lastValue = value;
		if (value === "") {
			updateNodes(false);
			return;
		}
		;
		nodeList = zTree.getNodesByParamFuzzy(keyType, value);
	}

	function updateNodes(highlight) {
		var zTree = $.fn.zTree.getZTreeObj("deptTree");
		for( var i=0, l=nodeList.length; i<l; i++) {
			nodeList[i].highlight = highlight;
			zTree.expandNode(nodeList[i].getParentNode(), true, false, false);
			zTree.updateNode(nodeList[i]);
		}
	}
	function getFontCss(treeId, treeNode) {
		return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
	}

	function clickUp(){
		var zTree = $.fn.zTree.getZTreeObj("deptTree");
		if(nodeList.length==0){
			App.info("没有搜索结果！");
			return ;
		}else if(clickCount==1) {
			App.info("您已位于第一条记录上！");
			return;
		}else{
			zTree.selectNode(nodeList[clickCount], false);
			clickCount --;
		}
		document.getElementById("key").focus();
		//显示当前所在的是条数
		document.getElementById("number").innerHTML = "[" + clickCount + "/" + nodeList.length + "]";
	}
	//点击向上按钮时，将焦点移向下一条数据
	function clickDown(){
		var zTree = $.fn.zTree.getZTreeObj("deptTree");
		if(nodeList.length==0){
			App.info("没有搜索结果！");
			return ;
		}else if(nodeList.length==clickCount)
		{
			App.info("您已位于最后一条记录上！")
			return;
		}else{
			zTree.selectNode(nodeList[clickCount], false)
			clickCount ++;
		}
		document.getElementById("key").focus();
		//显示当前所在的条数
		document.getElementById("number").innerHTML = "[" + clickCount + "/" + nodeList.length + "]";
	}
