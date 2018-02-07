/**
 * 收入报表模块
 * @author Chris.Zhang
 * @date 2017-6-14 11:15:32
 */
var incomeReportModel = {
		init: function(){
			this.bindEvent();
			this.render();
			this.initParams();
			this.loadData();
		},
		initParams:function(){
			incomeReportModel.params = {
	            	'pageNum' : 1,
	            	'pageSize': 10,
	            	'search': ''
	            };   
		},
		bindEvent: function(){
			var self = this;
			$(document).on('click', '#search-btn', function() {
				var search = $("#search").val();
				incomeReportModel.initParams();
				incomeReportModel.params.search = search;
				incomeReportModel.loadData();
	        });
			
			$(document).on('click', '#reset-btn', function() {
				incomeReportModel.reset();
			});
			layui.use('laydate', function(){
			  var laydate = layui.laydate;
			  
			  var start = {
			    min: '1979-01-01 00:00:00'
			    ,max: '2099-06-16 23:59:59'
			    ,istoday: false
			    ,choose: function(datas){
			      end.min = datas; //开始日选好后，重置结束日的最小日期
			      end.start = datas //将结束日的初始值设定为开始日
			    }
			  };
			  
			  var end = {
			    min: '1979-01-01 00:00:00'
			    ,max: '2099-06-16 23:59:59'
			    ,istoday: false
			    ,choose: function(datas){
			      start.max = datas; //结束日选好后，重置开始日的最大日期
			    }
			  };
			  document.getElementById('search-start-time').onclick = function(){
			    start.elem = this;
			    laydate(start);
			  }
			  document.getElementById('search-end-time').onclick = function(){
			    end.elem = this
			    laydate(end);
			  }
			  
			});
		},
		
		render: function(){
		},
		
		loadData:function(){
			var rows = [];
			delete incomeReportModel.params.startTime;
			delete incomeReportModel.params.endTime;
			var startTime = $('#search-start-time').val();
			var endTime = $('#search-end-time').val();
			if(typeof startTime !== 'undefined' && startTime != ''){
				incomeReportModel.params.startTime = startTime;
			}
			if(typeof endTime !== 'undefined' && endTime != ''){
				incomeReportModel.params.endTime = endTime;
			}
			AJAX.platemgr.plateform200000(incomeReportModel.params, function(data){
				rows = incomeReportModel.convertList(data.results.items);
				var obj = { total: data.results.total, rows: rows };
				$("#pg").pagination({
					total:obj.total,
					onSelectPage:function(pageNum, pageSize){
						incomeReportModel.params.pageNum = pageNum;
						incomeReportModel.params.pageSize = pageSize;
						incomeReportModel.loadData();
					}
				});
                $('#dg').datagrid('loadData',obj);
			});
		},
		
		reset:function(){
			clearFormValue('search-form');
			incomeReportModel.initParams();
			incomeReportModel.loadData();
		},
		convertList:function(items){
			if(typeof items !== 'undefined'){
				for(var i = 0; i < items.length; i++){
					var item = items[i];
					if(typeof item.totalMoney !== 'undefined' && item.totalMoney != ''){
						item.totalMoney = (item.totalMoney * 0.01).toFixed(2);
					}
				}
			}
			return items;
		}
}

incomeReportModel.init();