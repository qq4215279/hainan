    //获取首页展示图所需数据
    function getChartJsonData() {
    	initLeftTopChart();
    	initRightTopChart();
        initChartLeftMiddle(["海南省总工会","海口市总工会","三沙市总工会","三亚市总工会",""]);
        initChartLeftBottom(["海南省总工会","海口市总工会","三沙市总工会","三亚市总工会",""]);
    	// $.ajax({
         //    url: ctxPath +'/mst/organization/list',
         //    type: 'GET',
         //    data: {"deptId":1,"limit":20,"offset":20},
         //    success: function(data) {
         //    	var newArray = [];
         //    	var newArray2 = [];
         //    	$.each(data.rows ,function (i,d){
         //    		if(i != 0) {
         //   				if(i==11){
         //   					return false;
         //   				}
			// 			newArray.push(d.unionSimpleName);
         //    		}
         //    	});
         //    	$.each(data.rows ,function (i,d){
         //    		if(i != 0) {
         //   				if(i==6){
         //   					return false;
         //   				}
         //   				newArray2.push(d.unionSimpleName);
         //    		}
         //    	});
         //    	initChartLeftMiddle(newArray);
         //    	initChartLeftBottom(newArray2);
         //    }
   		// });
    }
    function initLeftTopChart() {
    	var leftbottom = echarts.init(document.getElementById('chartLeftTop'));
        var option = {
        	    title : {
        	        text: '',
        	        subtext: '',
        	        x:'center'
        	    },
        	    tooltip : {
        	        trigger: 'item',
        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
        	    },
        	    legend: {
        	        orient : 'vertical',
        	        x : 'left',
        	        data:['会员男','会员女','干部男','干部女']
        	    },
        	    toolbox: {
        	        show : true,
        	        feature : {
        	            mark : {show: true},
        	            dataView : {show: true, readOnly: false},
        	            magicType : {
        	                show: true, 
        	                type: ['pie', 'funnel']
        	            },
        	            restore : {show: true}
        	        }
        	    },
        	    calculable : false,
        	    series : [
        	        {
        	        	name:'访问来源',
        	            type:'pie',
        	            selectedMode: 'single',
        	            radius : [0, 40],
        	            x: '20%',
        	            width: '40%',
        	            funnelAlign: 'right',
        	            itemStyle : {
        	                normal : {
        	                    label : {
        	                        position : 'inner'
        	                    },
        	                    labelLine : {
        	                        show : false
        	                    }
        	                }
        	            },
        	            color:['#7DA8F8','rgb(57, 219, 185)'],
        	            data:[
        	                {value:25, name:'干部男',selected:true},
        	                {value:10, name:'干部女'}
        	                
        	            ]
        	        },
        	        {
        	            name:'访问来源',
        	            type:'pie',
        	            radius : [55, 90],
        	            x: '60%',
        	            width: '35%',
        	            funnelAlign: 'left',
        	            color:['#F3BC46','#e4b9b9'],
        	            data:[
        	                {value:40, name:'会员男'},
        	                {value:90, name:'会员女'}
        	            ]
        	        }
        	    ]
        	};
        leftbottom.setOption(option);
    }
    function initRightTopChart(){
        var lefttopChart = echarts.init(document.getElementById('chartRightTop'));
       var  option = {
            title : {
                text: '',
                subtext: '累计注册人数1045人，其中入会人数543人'
            },
            tooltip : {
                trigger: 'axis'
            },
            legend: {
                data:['注册人员','入会会员']
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    restore : {show: true}
                }
            },
            calculable : true,
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : ['2017-06','2017-07','2017-08','2017-09','2017-10','2017-11','2017-12','2018-01','2018-02','2018-03','2018-04','2018-05']
                }
            ],
            yAxis : [
                {
                    type : 'value',
                    axisLabel : {
                        formatter: '{value} 人'
                    }
                }
            ],
            series : [
                {
                    name:'注册人员',
                    type:'line',
                    color:['#ae81ff'],
                    data:[10, 10, 15, 10,20, 22, 24, 16, 19, 21, 20,22],
                    itemStyle: {normal: {areaStyle: {type: 'default'}}}
                },
                {
                    name:'入会会员',
                    type:'line',
                    color:['#8470FF'],
                    data:[1, 4, 5, 8,11, 11, 15, 13, 12, 13, 10,8],
                    itemStyle: {normal: {areaStyle: {type: 'default'}}}
                }
            ]
        };
        lefttopChart.setOption(option);
    }
    
    function initChartLeftMiddle(data) {
    	var lefttopChart = echarts.init(document.getElementById('chartLeftMiddle'));
    	var option = {
    		    tooltip : {
    		        trigger: 'axis'
    		    },
    		    legend: {
    		        data:['会员总量','男','女']
    		    },
    		    grid: {
    		        left: '3%',
    		        right: '4%',
    		        bottom: '3%',
    		        containLabel: true
    		    },
    		    toolbox: {
    		        show : true,
    		        feature : {
    		            mark : {show: true},
    		            dataView : {show: true, readOnly: false},
    		            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
    		            restore : {show: true}
    		        }
    		    },
    		    calculable : true,
    		    xAxis : [
    		        {
    		            type : 'category',
    		            data : data
    		        }
    		    ],
    		    yAxis : [
    		        {
    		            type : 'value',
        	            axisLabel : {
        	                formatter: '{value} 人'
        	            }
    		        }
    		    ],
    		    series : [
    		        {
    		            name:'会员总量',
    		            type:'bar',
    		            color:['#87CEFF'],
    		            data:[100, 95, 90, 85, 80, 75, 70, 65, 60, 55]
    		        },
    		        {
    		            name:'男',
    		            type:'bar',
    		            stack: '广告',
    		            color:['#90EE90'],
    		            data:[60, 60, 50, 50, 40, 30, 40, 30, 40, 20]
    		        },
    		        {
    		            name:'女',
    		            type:'bar',
    		            stack: '广告',
    		            color:['#e4b9b9'],
    		            data:[40, 35, 40, 35, 40, 45, 30,35,20,35]
    		        }
    		    ]
    		};
    	lefttopChart.setOption(option);
    }
    
    function initChartLeftBottom(data) {
    	var lefttopChart = echarts.init(document.getElementById('chartLeftBottom'));
		var option = {
			    title : {
			        text: '',
			        subtext: ''
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:['新增入会人数', '新增干部人数']
			    },
			    toolbox: {
			        show : true,
			        feature : {
			            mark : {show: true},
			            dataView : {show: true, readOnly: false},
			            magicType: {show: true, type: ['line', 'bar']},
			            restore : {show: true}
			        }
			    },
			    calculable : true,
			    xAxis : [
			        {
			            type : 'value',
			            boundaryGap : [0, 0.01]
			        }
			    ],
			    yAxis : [
			        {
			            type : 'category',
			            data : data
			        }
			    ],
			    series : [
			        {
			            name:'新增入会人数',
			            type:'bar',
			            color:['#ae81ff'],
			            data:[20, 22, 24, 28, 30]
			        },
			        {
			            name:'新增干部人数',
			            type:'bar',
			            color:['#3A90BA'],
			            data:[1, 2, 3, 4, 5]
			        }
			    ]
			};
    	lefttopChart.setOption(option);
    }
