
window.initChart = function (id) {
	var ajax = new $ax(Feng.ctxPath + "/mst/organization/getMstMemberCadreCount", function (data) {
		var memberCount = 0,cadreCount=0,maleManCount=0,memberOfCadreCount=0;
		if(data.memberCount) {
			memberCount = parseInt(data.memberCount);//会员总数
		}
		if(data.cadreCount) {
			cadreCount = parseInt(data.cadreCount);//干部总数
		}
		if(data.maleManCount) {
			maleManCount = parseInt(data.maleManCount);//会员中男性总人数
		}
		if(data.memberOfCadreCount){
			memberOfCadreCount = parseInt(data.memberOfCadreCount);//会员中干部的人数
		}
		initMemberChart(memberCount,memberOfCadreCount);
		initMemberCadreChart(memberCount,maleManCount);
		$("#memberCount").val(memberCount);
		$("#memberCadreCount").val(cadreCount);
		$("#chairmanName").val(data.chairmanName);//主席名称
    }, function (data) {});
    ajax.set("deptId",id);
    ajax.start();
}
function initMemberChart(memberCount,memberOfCadreCount) {
	var memberChart = echarts.init(document.getElementById('memberChart'));
	var memberOption = {
	    title : {
	        text: '会员干部统计图',
	        subtext: '会员总人数：'+memberCount+'人',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	        data: ['会员中干部人数','会员中非干部人数']
	    },
	    series : [
	        {
	            name: '访问来源',
	            type: 'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            data:[
	                {value:memberOfCadreCount, name:'会员中干部人数'},
	                {value:memberCount-memberOfCadreCount, name:'会员中非干部人数'}
	            ],
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};
	memberChart.setOption(memberOption);
}
function initMemberCadreChart(maleCount,maleManCount) {
	var memberChart = echarts.init(document.getElementById('memberCadreChart'));
	var memberOption = {
	    title : {
	        text: '会员男女比例图',
	        subtext: '会员总人数：'+maleCount+'人',
	        x:'center'
	    },
	    tooltip : {
	        trigger: 'item',
	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	        data: ['男','女']
	    },
	    series : [
	        {
	            name: '访问来源',
	            type: 'pie',
	            radius : '55%',
	            center: ['50%', '60%'],
	            data:[
	                {value:maleManCount, name:'男'},
	                {value:maleCount-maleManCount, name:'女'}
	            ],
	            itemStyle: {
	                emphasis: {
	                    shadowBlur: 10,
	                    shadowOffsetX: 0,
	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	                }
	            }
	        }
	    ]
	};
	memberChart.setOption(memberOption);
}