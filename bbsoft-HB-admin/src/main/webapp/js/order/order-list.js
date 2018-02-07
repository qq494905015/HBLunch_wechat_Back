/**
 * 订单模块
 * @author Chris.Zhang
 * @date 2017-6-6 14:55:55 
 */
var orderlistModel = {
		init: function(){
			//初始化下拉框
			$('#search-order-type').combobox();
			$('#search-status').combobox();
			this.bindEvent();
			this.render();
			this.initParams();
			this.loadData();
		},
		initParams:function(){
			orderlistModel.params = {
	            	'pageNum' : 1,
	            	'pageSize': 10,
	            	'search': ''
	            };   
		},
		bindEvent: function(){
			var self = this;
			$(document).on('click', '#search-btn', function() {
				var search = $("#search").val();
				orderlistModel.initParams();
				orderlistModel.params.search = search;
				orderlistModel.loadData();
	        });
			
			$(document).on('click', '#reset-btn', function() {
				orderlistModel.reset();
			});
			
			$(document).on('click', '#add-sysuser-btn', function(){
				orderlistModel.editOrder();
			});
			
			$(document).on('click', '#export-btn', function(){
				var search = $("#search").val();
				orderlistModel.initParams();
				orderlistModel.params.search = search;
				orderlistModel.exportData();
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
			delete orderlistModel.params.orderType;
			delete orderlistModel.params.status;
			delete orderlistModel.params.startTime;
			delete orderlistModel.params.endTime;
			
			var orderType = $('#search-order-type').combobox('getValue');
			var status = $('#search-status').combobox('getValue');
			var startTime = $('#search-start-time').val();
			var endTime = $('#search-end-time').val();
			if(typeof orderType !== 'undefined' && orderType != '' && orderType != '0'){
				orderlistModel.params.orderType = orderType;
			}
			if(typeof status !== 'undefined' && status != '' && status != '0'){
				orderlistModel.params.status = status;
			}
			if(typeof startTime !== 'undefined' && startTime != ''){
				orderlistModel.params.startTime = startTime;
			}
			if(typeof endTime !== 'undefined' && endTime != ''){
				orderlistModel.params.endTime = endTime;
			}
			AJAX.ordermgr.order200000(orderlistModel.params, function(data){
				rows = orderlistModel.convertOrders(data.results.items);
				var obj = { total: data.results.total, rows: rows };
				$("#pg").pagination({
					total:obj.total,
					onSelectPage:function(pageNum, pageSize){
						orderlistModel.params.pageNum = pageNum;
						orderlistModel.params.pageSize = pageSize;
						orderlistModel.loadData();
					}
				});
                $('#dg').datagrid('loadData',obj);
			});
		},
		
		exportData:function(){
			var rows = [];
			delete orderlistModel.params.orderType;
			delete orderlistModel.params.status;
			delete orderlistModel.params.startTime;
			delete orderlistModel.params.endTime;
			
			var orderType = $('#search-order-type').combobox('getValue');
			var status = $('#search-status').combobox('getValue');
			var startTime = $('#search-start-time').val();
			var endTime = $('#search-end-time').val();
			if(typeof orderType !== 'undefined' && orderType != '' && orderType != '0'){
				orderlistModel.params.orderType = orderType;
			}
			if(typeof status !== 'undefined' && status != '' && status != '0'){
				orderlistModel.params.status = status;
			}
			if(typeof startTime !== 'undefined' && startTime != ''){
				orderlistModel.params.startTime = startTime;
			}
			if(typeof endTime !== 'undefined' && endTime != ''){
				orderlistModel.params.endTime = endTime;
			}
			window.location.href=HOST_URL+"/order200003.do"
		},
		
		reset:function(){
			clearFormValue('search-form');
			orderlistModel.initParams();
			orderlistModel.loadData();
		},
		//编辑
		editOrder: function(rowIndex){
			var row;
			var title = '新增会员信息';
			if(typeof rowIndex !== 'undefined'){
				$('#dg').datagrid('selectRow',rowIndex);// 关键在这里  
				row = $('#dg').datagrid('getSelected');  
				title = '修改会员信息';
			}
		    layer.open({
	    	  type: 1,
	    	  title: title,
	    	  closeBtn: 1, //不显示关闭按钮
	    	  btn:['确认', '取消'],
	    	  shade: [0],
	    	  area: ['500px', '550px'],
	    	  offset: '100px', //右下角弹出
	    	  content: $('#editOrder'), //iframe的url，no代表不显示滚动条
	    	  success:function(layero, index){
	    		 if(typeof row !== 'undefined'){
	    			 //表单赋值
	    			 setFormValue('edit-user-form', row);
	    		 }else{
	    			 //清空表单
	    			 clearFormValue('edit-user-form');
	    		 }
	    		 //加载layui form
	  	          layui.use('form', function(){
	  	        	var form = layui.form(); //只有执行了这一步，部分表单元素才会修饰成功
	  			  });
	    	  },
	    	  yes:function(index){
	    		  var userName = $('#edit-user-form input[name=userName]').val();
	    		  var nickName = $('#edit-user-form input[name=nickName]').val();
	    		  var phone = $('#edit-user-form input[name=phone]').val();
	    		  var password = $('#edit-user-form input[name=pwd]').val();
	    		  var commitPwd = $('#edit-user-form input[name=commitPwd]').val();
	    		  var sex = $('#edit-user-form input[name=sex]:checked').val();
	    		  var birthday = $('#edit-user-form input[name=birthday]').val();
	    		  var realName = $('#edit-user-form input[name=realName]').val();
	    		  var idCard = $('#edit-user-form input[name=idCard]').val();
//	    		  $("input:checkbox[name='status']:checked").each(function() { // 遍历name=test的多选框
//	    			  status =  $(this).val();  // 每一个被选中项的值
//	    		 });
	    		  var params = {
	    			'userName' : userName,
	    			'nickName' : nickName,
	    			'phone'    : phone,
	    			'password' : password,
	    			'sex' 	   : sex,
	    			'birthday' : birthday,
	    			'realName' : realName,
	    			'idCard'   : idCard
	    		  };
	    		  if(typeof row !== 'undefined'){
	    			  params.id = row.id;
	    		  }else{
	    			  if(userName == ''){
	    				  layer.alert('用户名不能为空');
	    				  return;
	    			  }
	    			  if(phone == ''){
	    				  layer.alert('手机号不能为空');
	    				  return;
	    			  }
	    			  if(password == '' || commitPwd == ''){
	    				  layer.alert('密码不能为空');
	    				  return;
	    			  }
	    			  if(password != commitPwd){
		    			  layer.alert('两次密码输入不一致');
		    			  return;
		    		  }
	    		  }
	    		  orderlistModel.saveUser(params, index);
	    	  },
	    	  no:function(index){
	    		  layer.close(index);
	    	  }
	    	});
		},
		//保存用户信息
		saveUser: function(params, index){
			if(!params){
				layer.alert('请输入需要填写的信息');
				return;
			}
			if(params.id){
				//修改
				AJAX.usermgr.user200003(params, function(data){
					if(data && data.results){
						layer.alert('修改成功');
						layer.close(index);
						orderlistModel.loadData();
					}
				});
			}else{
				//新增
				AJAX.usermgr.user200002(params, function(data){
					if(data && data.results){
						layer.alert('新增成功');
						layer.close(index);
						orderlistModel.loadData();
					}
				});
			}
			
		},
		convertOrders:function(items){
			if(typeof items !== 'undefined'){
				for(var i = 0; i < items.length; i++){
					var item = items[i];
					switch(item.orderType){
						case '1':
							item.orderTypeName = '会员卡';
							break;
						case '2':
							item.orderTypeName = '充值';
							break;
						default:
							item.orderTypeName = '其它';
							break;
					}
					
					switch(item.status){
						case '1':
							item.statusName = '待支付';
							break;
						case '2':
							item.statusName = '支付中';
							break;
						case '3':
							item.statusName = '支付成功';
							break;
						case '4':
							item.statusName = '订单完成';
							break;
						case '5':
							item.statusName = '订单失效';
							break;
						default:
							item.statusName = '未知状态';
							break;
					}
					
					if(typeof item.price !== 'undefined' && item.price != ''){
						item.price = (item.price * 0.01).toFixed(2);
					}
					if(typeof item.payPrice !== 'undefined' && item.payPrice != ''){
						item.payPrice = (item.payPrice * 0.01).toFixed(2);
					}
				}
			}
			return items;
		},
		getOperate:function(val,row,index){
			return '';
//			return '<a href="javascript:;" style="color:green;" onclick="orderlistModel.editOrder('+index+')">修改</a> &nbsp;&nbsp;';
		}	
}

orderlistModel.init();