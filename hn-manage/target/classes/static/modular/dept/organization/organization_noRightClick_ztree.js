//该工会组织树js是用于组织树用于查看，不用于右击添加、编辑数据
//该js可用于后续的工会会员树
//展示左侧工会组织结构树
	var setting = { 
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
			onClick:onclickTree
		}
	}
    //ztree的点击事件
    function onclickTree(e, treeId, treeNode) {
    	$("#deptId").val( treeNode.id);
    	//下面一句js;根据具体业务再进行处理
	    //OptOrganization.search();
    }
	$(function () {
    	$.ajax({  
            async : false,  
            cache:false,  
            type: 'POST',  
            dataType : "json",  
            url: App.ctxPath + "dept/belowTree",//请求的action路径
            error: function () {//请求失败处理函数  
                alert('请求失败');  
            },  
            success:function(data){ //请求成功后处理函数。    
            	var t = $("#deptTree");
             	$.fn.zTree.init(t, setting, data);
	    		//工会组织结构树调用成功后，再加载工会组织的table;根据具体业务再进行处理
	    		//initTableFun();
            }  
        });
    });