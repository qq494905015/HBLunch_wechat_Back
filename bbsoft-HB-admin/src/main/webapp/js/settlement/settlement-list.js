/**
 * 结算报表模块
 * @author Chris.Zhang
 * @date 2017-6-6 14:55:55 
 */
var settlementModel = {
		init: function(){
			//初始化下拉框
			$('#search-status').combobox();
			this.bindEvent();
			this.render();
			this.initParams();
			this.loadData();
		},
		initParams:function(){
			settlementModel.params = {
	            	'pageNum' : 1,
	            	'pageSize': 10
	            };   
		},
		bindEvent: function(){
			var self = this;
			$(document).on('click', '#search-btn', function() {
				var search = $("#search").val();
				settlementModel.initParams();
				settlementModel.params.search = search;
				settlementModel.loadData();
	        });
			
			$(document).on('click', '#reset-btn', function() {
				settlementModel.reset();
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
			delete settlementModel.params.shopName;
			delete settlementModel.params.phone;
			delete settlementModel.params.status;
			delete settlementModel.params.startTime;
			delete settlementModel.params.endTime;
			var shopName = $('#search-shop-name').val();
			var phone = $('#search-phone').val();
			var status = $('#search-status').combobox('getValue');
			var startTime = $('#search-start-time').val();
			var endTime = $('#search-end-time').val();
			if(typeof status !== 'undefined' && status != '' && status != '-1'){
				settlementModel.params.status = status;
			}
			if(typeof shopName !== 'undefined' && shopName != ''){
				settlementModel.params.shopName = shopName;
			}
			if(typeof phone !== 'undefined' && phone != ''){
				settlementModel.params.phone = phone;
			}
			if(typeof startTime !== 'undefined' && startTime != ''){
				settlementModel.params.startTime = startTime;
			}
			if(typeof endTime !== 'undefined' && endTime != ''){
				settlementModel.params.endTime = endTime;
			}
			AJAX.settlementmgr.shoptotal200000(settlementModel.params, function(data){
				rows = settlementModel.convertList(data.results.items);
				var obj = { total: data.results.total, rows: rows };
				$("#pg").pagination({
					total:obj.total,
					onSelectPage:function(pageNum, pageSize){
						settlementModel.params.pageNum = pageNum;
						settlementModel.params.pageSize = pageSize;
						settlementModel.loadData();
					}
				});
                $('#dg').datagrid('loadData',obj);
			});
		},
		
		reset:function(){
			clearFormValue('search-form');
			settlementModel.initParams();
			settlementModel.loadData();
		},
		//结算处理
		settlementDeal: function(rowIndex){
			if(typeof rowIndex !== 'undefined'){
				$('#dg').datagrid('selectRow',rowIndex);// 关键在这里  
				var row = $('#dg').datagrid('getSelected');
				var params = {
						'id' : row.id,
						'status':'2'
				};
				layer.confirm('确认结算此门店？', {icon: 3, title:'提示'}, function(cindex){
					AJAX.settlementmgr.shoptotal200001(params, function(data){
						if(data && data.results){
							layer.alert('操作成功');
							settlementModel.loadData();
						}
					});
				});
			}else{
				layer.alert('请选择需要结算的记录');
			}
		},
		settlementDetail: function(id){
			if(typeof id !== 'undefined'){
				location.href = 'settlement-detail.html?id=' + id;
			}else{
				layer.alert('请选择需要查看详情的记录');
			}
		},
		convertList:function(items){
			if(typeof items !== 'undefined'){
				for(var i = 0; i < items.length; i++){
					var item = items[i];
					
					switch(item.status){
						case '0':
							item.statusName = '待提交';
							break;
						case '1':
							item.statusName = '待结算';
							break;
						case '2':
							item.statusName = '已结算';
							break;
						default:
							item.statusName = '未知状态';
							break;
					}
					
					if(typeof item.total !== 'undefined' && item.total != ''){
						item.total = (item.total * 0.01).toFixed(2);
					}
				}
			}
			return items;
		},
		getOperate:function(val,row,index){
			var operDoc = '<a href="javascript:;" style="color:green;" onclick="settlementModel.settlementDetail('+row.id+')">详情</a> &nbsp;&nbsp;';
			if(row.status == '1'){
				operDoc += '<a href="javascript:;" style="color:red;" onclick="settlementModel.settlementDeal('+index+')">结算</a> &nbsp;&nbsp;';
			}
			return operDoc;
		}	
}

settlementModel.init();