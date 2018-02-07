/**
 * 留言反馈模块
 * @author Chris.Zhang
 * @date 2017-7-27 17:31:05 
 */
var feedbackModel = {
		init: function(){
			this.bindEvent();
			this.render();
			this.initParams();
			this.loadData();
		},
		initParams:function(){
			feedbackModel.params = {
	            	'pageNum' : 1,
	            	'pageSize': 10,
	            	'search': ''
	            };   
		},
		bindEvent: function(){
			var self = this;
			$(document).on('click', '#search-btn', function() {
				var search = $("#search").val();
				feedbackModel.initParams();
				feedbackModel.params.search = search;
				feedbackModel.loadData();
	        });
			
			$(document).on('click', '#reset-btn', function() {
				feedbackModel.reset();
			});
		},
		
		render: function(){
		},
		
		loadData:function(){
			var rows = [];
			var startTime = $('#search-start-time').val();
			var endTime = $('#search-end-time').val();
			if(typeof startTime !== 'undefined' && startTime != ''){
				feedbackModel.params.startTime = startTime;
			}
			if(typeof endTime !== 'undefined' && endTime != ''){
				feedbackModel.params.endTime = endTime;
			}
			AJAX.feedbackmgr.feedback200001(feedbackModel.params, function(data){
				rows = feedbackModel.convertList(data.results.items);
				var obj = { total: data.results.total, rows: rows };
				$("#pg").pagination({
					total:obj.total,
					onSelectPage:function(pageNum, pageSize){
						feedbackModel.params.pageNum = pageNum;
						feedbackModel.params.pageSize = pageSize;
						feedbackModel.loadData();
					}
				});
                $('#dg').datagrid('loadData',obj);
			});
		},
		
		reset:function(){
			$("#search-form").form('clear');
			feedbackModel.initParams();
			feedbackModel.loadData();
		},
		convertList:function(items){
			if(typeof items !== 'undefined' && items.length > 0){
				for(var i = 0; i < items.length; i++){
					var item = items[i];
				}
			}
			return items;
		},
		getOperate:function(val,row,index){
			var operStr = '';
			return operStr;
		}	
}

feedbackModel.init();