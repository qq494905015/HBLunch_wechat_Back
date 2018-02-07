/**
 * 结算报表明细模块
 * @author Chris.Zhang
 * @date 2017-6-6 14:55:55 
 */
var settDetailModel = {
		init: function(){
			this.bindEvent();
			this.render();
			this.initParams();
			this.loadData();
		},
		initParams:function(){
			var urlParams = getParameters();
			settDetailModel.params = {
	            	'pageNum' : 1,
	            	'pageSize': 10,
	            	'shopTotalId': urlParams.id,
	            	'phone' : ''
	            };   
		},
		bindEvent: function(){
			var self = this;
			$(document).on('click', '#search-btn', function() {
				var phone = $("#search-phone").val();
				settDetailModel.initParams();
				settDetailModel.params.phone = phone;
				settDetailModel.loadData();
	        });
			
			$(document).on('click', '#reset-btn', function() {
				settDetailModel.reset();
			});
			
			$(document).on('click', '#back-btn', function() {
				location.href = 'settlement-list.html';
			});
			
		},
		
		render: function(){
		},
		
		loadData:function(){
			var rows = [];
			AJAX.settlementmgr.shoptotal200002(settDetailModel.params, function(data){
				rows = settDetailModel.convertList(data.results.items);
				var obj = { total: data.results.total, rows: rows };
				$("#pg").pagination({
					total:obj.total,
					onSelectPage:function(pageNum, pageSize){
						settDetailModel.params.pageNum = pageNum;
						settDetailModel.params.pageSize = pageSize;
						settDetailModel.loadData();
					}
				});
                $('#dg').datagrid('loadData',obj);
			});
		},
		
		reset:function(){
			clearFormValue('search-form');
			settDetailModel.initParams();
			settDetailModel.loadData();
		},
		convertList:function(items){
			if(typeof items !== 'undefined'){
				for(var i = 0; i < items.length; i++){
					var item = items[i];
					
					switch(item.status){
						case '0':
							item.statusName = '成功';
							break;
						case '1':
							item.statusName = '成功';
							break;
						default:
							item.statusName = '未知状态';
							break;
					}
					
					if(typeof item.money !== 'undefined' && item.money != ''){
						item.money = (item.money * 0.01).toFixed(2);
					}
				}
			}
			return items;
		}
}

settDetailModel.init();