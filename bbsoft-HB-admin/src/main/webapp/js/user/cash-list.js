/**
 * 用户提现模块
 * @author Chris.Zhang
 * @date 2017-8-7 14:41:54 
 */
var cashListModel = {
		init: function(){
			//初始化下拉框
			$('#search-record-status').combobox();
			this.bindEvent();
			this.render();
			this.initParams();
			this.loadData();
		},
		initParams:function(){
			cashListModel.params = {
	            	'pageNum' : 1,
	            	'pageSize': 10,
	            	'search': ''
	            };   
		},
		bindEvent: function(){
			var self = this;
			$(document).on('click', '#search-btn', function() {
				var search = $("#search").val();
				cashListModel.initParams();
				cashListModel.params.userId = search;
				cashListModel.loadData();
	        });
			
			$(document).on('click', '#reset-btn', function() {
				cashListModel.reset();
			});
			
			layui.use('laydate', function(){
				  var laydate = layui.laydate;
				  
				  var start = {
				    min: '1979-01-01 00:00:00'
				    ,max: '2099-06-16 23:59:59'
			    	,format:'YYYY-MM-DD hh:MM:ss'
				    ,istime:true
				    ,istoday: false
				    ,choose: function(datas){
				      end.min = datas; //开始日选好后，重置结束日的最小日期
				      end.start = datas //将结束日的初始值设定为开始日
				    }
				  };
				  
				  var end = {
				    min: '1979-01-01 00:00:00'
				    ,max: '2099-06-16 23:59:59'
			    	,format:'YYYY-MM-DD hh:MM:ss'
				    ,istime:true
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
			delete cashListModel.params.status;
			var status = $('#search-record-status').combobox('getValue');
			var startTime = $('#search-start-time').val();
			var endTime = $('#search-end-time').val();
			if(typeof status !== 'undefined' && status != '' && status != '0'){
				if(status == '1'){
					cashListModel.params.status = '0';
				}
				if(status == '2'){
					cashListModel.params.status = '1';
				}
			}
			if(typeof startTime !== 'undefined' && startTime != ''){
				cashListModel.params.startTime = startTime;
			}
			if(typeof endTime !== 'undefined' && endTime != ''){
				cashListModel.params.endTime = endTime;
			}
			AJAX.usermgr.cash200000(cashListModel.params, function(data){
				rows = cashListModel.convertList(data.results.items);
				var obj = { total: data.results.total, rows: rows };
				$("#pg").pagination({
					total:obj.total,
					onSelectPage:function(pageNum, pageSize){
						cashListModel.params.pageNum = pageNum;
						cashListModel.params.pageSize = pageSize;
						cashListModel.loadData();
					}
				});
                $('#dg').datagrid('loadData',obj);
			});
		},
		editRecord:function(rowIndex){
			if(typeof rowIndex !== 'undefined'){
				$('#dg').datagrid('selectRow',rowIndex);// 关键在这里  
				var row = $('#dg').datagrid('getSelected');
				var params = {
						'id' : row.id
				};
				layer.confirm('确认此用户提现信息？', {icon: 3, title:'提示'}, function(cindex){
					AJAX.usermgr.cash200001(params, function(data){
						if(data && data.results){
							layer.alert('操作成功');
							cashListModel.loadData();
						}
					});
				});
			}else{
				layer.alert('请选择需要操作的记录');
				return;
			}
		},
		reset:function(){
			$("#search-form").form('clear');
			cashListModel.initParams();
			cashListModel.loadData();
		},
		convertList:function(items){
			if(typeof items !== 'undefined' && items.length > 0){
				for(var i = 0; i < items.length; i++){
					var item = items[i];
					switch (item.status) {
					case '0':
						item.statusName = '提现中';
						break;
					case '1':
						item.statusName = '提现成功';
						break;
					default:
						item.statusName = '不明状态';
						break;
					}
					if(typeof item.money !== 'undefined' && item.money != ''){
						item.money = (item.money * 0.01).toFixed(2);
					}
				}
			}
			return items;
		},
		getOperate:function(val,row,index){
			var operStr = '<a href="javascript:;" style="color:green;" onclick="cashListModel.editRecord('+index+')">确认提现</a> &nbsp;&nbsp;';
			return operStr;
		}	
}

cashListModel.init();