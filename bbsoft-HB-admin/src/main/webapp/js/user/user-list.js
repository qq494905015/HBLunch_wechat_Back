/**
 * 会员模块
 * @author Chris.Zhang
 * @date 2017-6-6 14:55:55 
 */
var userlistModel = {
		init: function(){
			this.bindEvent();
			this.render();
			this.initParams();
			this.loadData();
		},
		initParams:function(){
			userlistModel.params = {
	            	'pageNum' : 1,
	            	'pageSize': 10,
	            	'search': ''
	            };   
		},
		bindEvent: function(){
			var self = this;
			$(document).on('click', '#search-btn', function() {
				var search = $("#search").val();
				userlistModel.initParams();
				userlistModel.params.search = search;
				userlistModel.loadData();
	        });
			
			$(document).on('click', '#reset-btn', function() {
				userlistModel.reset();
			});
			
			$(document).on('click', '#add-sysuser-btn', function(){
				userlistModel.editUser();
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
			var startTime = $('#search-start-time').val();
			var endTime = $('#search-end-time').val();
			if(typeof startTime !== 'undefined' && startTime != ''){
				userlistModel.params.startTime = startTime;
			}
			if(typeof endTime !== 'undefined' && endTime != ''){
				userlistModel.params.endTime = endTime;
			}
			AJAX.usermgr.user200000(userlistModel.params, function(data){
				rows = userlistModel.convertList(data.results.items);
				var obj = { total: data.results.total, rows: rows };
				$("#pg").pagination({
					total:obj.total,
					onSelectPage:function(pageNum, pageSize){
						userlistModel.params.pageNum = pageNum;
						userlistModel.params.pageSize = pageSize;
						userlistModel.loadData();
					}
				});
                $('#dg').datagrid('loadData',obj);
			});
		},
		
		reset:function(){
			$("#search-form").form('clear');
			userlistModel.initParams();
			userlistModel.loadData();
		},
		//编辑
		editUser: function(rowIndex){
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
	    	  content: $('#editUser'), //iframe的url，no代表不显示滚动条
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
	    		  console.info(params);
	    		  if(typeof row !== 'undefined'){
	    			  params.id = row.id;
	    		  }
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
	    		  userlistModel.saveUser(params, index);
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
						userlistModel.loadData();
					}
				});
			}else{
				//新增
				AJAX.usermgr.user200002(params, function(data){
					if(data && data.results){
						layer.alert('新增成功');
						layer.close(index);
						userlistModel.loadData();
					}
				});
			}
			
		},
		//设置用户为店铺管理员
		setShopMgr : function(rowIndex){
			var row;
			if(typeof rowIndex !== 'undefined'){
				$('#dg').datagrid('selectRow',rowIndex);// 关键在这里  
				row = $('#dg').datagrid('getSelected');  
			}else{
				layer.alert('请选择用户');
				return;
			}
			
			layer.open({
		    	  type: 1,
		    	  title: '设置店铺管理员',
		    	  closeBtn: 1, //不显示关闭按钮
		    	  btn:['确认', '取消'],
		    	  shade: [0],
		    	  area: ['500px', '250px'],
		    	  offset: '100px', //右下角弹出
		    	  content: $('#editShopDialog'), //iframe的url，no代表不显示滚动条
		    	  success:function(layero, index){
		    		  $('#edit-shop-user-form input[name=nickName]').val(row.nickName);
		    		  AJAX.shopmgr.shop200005(function(data){
		    			  if(data && data.results){
		    				  var selectShop = $('#edit-shop-user-form select[name=shop]');
		    				  for(var i = 0; i < data.results.length; i++){
		    					  var option = $('<option value="'+ data.results[i].id +'">' + data.results[i].name + '</option>');
		    					  if(typeof row !== 'undefined'){
			    					  if(row.shopId == data.results[i].id){
			    						  option.attr('selected', '');
			    					  }
		    					  }else{
		    						  if(i == 0){
		    							  option.attr('selected', '');
		    						  }
		    					  }
		    					  selectShop.append(option);
		    				  }
		    				  //加载layui form
				  	          layui.use('form', function(){
				  	        	var form = layui.form(); //只有执行了这一步，部分表单元素才会修饰成功
				  			  });
		    			  }
			  	        });
		    	  },
		    	  yes:function(index){
		    		  var shopId = $('#edit-shop-user-form select[name=shop]').val();
		    		  var params = {
		    			'id'	   : row.id,
		    			'shopId'   : shopId
		    		  };
		    		  userlistModel.saveUser(params, index);
		    	  },
		    	  no:function(index){
		    		  layer.close(index);
		    	  }
		    	});
		},
		editBalance : function(rowIndex){
			var row;
			if(typeof rowIndex !== 'undefined'){
				$('#dg').datagrid('selectRow',rowIndex);// 关键在这里  
				row = $('#dg').datagrid('getSelected');  
			}else{
				layer.alert('请选择用户');
				return;
			}
			
			layer.open({
	    	  type: 1,
	    	  title: '修改用户会员卡余额',
	    	  closeBtn: 1, //不显示关闭按钮
	    	  btn:['确认', '取消'],
	    	  shade: [0],
	    	  area: ['500px', '250px'],
	    	  offset: '100px', //右下角弹出
	    	  content: $('#editBalanceDialog'), //iframe的url，no代表不显示滚动条
	    	  success:function(layero, index){
	    		  $('#edit-balance-form input[name=nickName]').val(row.nickName);
	    		  $('#edit-balance-form input[name=balance]').val(row.balance);
	    	  },
	    	  yes:function(index){
	    		  var balance = $('#edit-balance-form input[name=balance]').val();
	    		  if(typeof balance === 'undefined' || balance < 0){
	    			  layer.alert('请输入正确的余额');
	    			  return;
	    		  }else{
	    			  balance = balance * 100;
	    		  }
	    		  var params = {
	    			'id'	   : row.id,
	    			'balance'   : balance
	    		  };
	    		  AJAX.usermgr.user200004(params, function(data){
	    			  if(data && data.results){
							layer.alert('修改成功');
							layer.close(index);
							userlistModel.loadData();
						}
					});
	    		  
	    	  },
	    	  no:function(index){
	    		  layer.close(index);
	    	  }
	    	});
		},
		editCommision : function(rowIndex){
			var row;
			if(typeof rowIndex !== 'undefined'){
				$('#dg').datagrid('selectRow',rowIndex);// 关键在这里  
				row = $('#dg').datagrid('getSelected');  
			}else{
				layer.alert('请选择用户');
				return;
			}
			
			layer.open({
				type: 1,
				title: '修改用户会员佣金',
				closeBtn: 1, //不显示关闭按钮
				btn:['确认', '取消'],
				shade: [0],
				area: ['500px', '250px'],
				offset: '100px', //右下角弹出
				content: $('#editCommisionDialog'), //iframe的url，no代表不显示滚动条
				success:function(layero, index){
					$('#edit-commision-form input[name=nickName]').val(row.nickName);
					$('#edit-commision-form input[name=commision]').val(row.commision);
				},
				yes:function(index){
					var commision = $('#edit-commision-form input[name=commision]').val();
					if(typeof commision === 'undefined' || commision < 0){
						layer.alert('请输入正确的佣金');
						return;
					}else{
						commision = commision * 100;
					}
					var params = {
							'id'	   : row.id,
							'commision'   : commision
					};
					AJAX.usermgr.user200005(params, function(data){
						if(data && data.results){
							layer.alert('修改成功');
							layer.close(index);
							userlistModel.loadData();
						}
					});
					
				},
				no:function(index){
					layer.close(index);
				}
			});
		},
		editBindCard : function(rowIndex){
			var row;
			if(typeof rowIndex !== 'undefined'){
				$('#dg').datagrid('selectRow',rowIndex);// 关键在这里  
				row = $('#dg').datagrid('getSelected');  
			}else{
				layer.alert('请选择用户');
				return;
			}
			
			layer.open({
	    	  type: 1,
	    	  title: '一键绑定会员卡信息',
	    	  closeBtn: 1, //不显示关闭按钮
	    	  btn:['确认', '取消'],
	    	  shade: [0],
	    	  area: ['500px', '600px'],
	    	  offset: '100px', //右下角弹出
	    	  content: $('#editBindCardDialog'), //iframe的url，no代表不显示滚动条
	    	  success:function(layero, index){
	    		  $('#edit-bind-card-form input[name=userId]').val(row.id);
	    	  },
	    	  yes:function(index){
	    		  var cardNo = $('#edit-bind-card-form input[name=cardNo]').val();
	    		  var surname = $('#edit-bind-card-form input[name=surname]').val();
	    		  var name = $('#edit-bind-card-form input[name=name]').val();
	    		  var price = $('#edit-bind-card-form input[name=price]').val();
	    		  var recommendPhone = $('#edit-bind-card-form input[name=recommendPhone]').val();
	    		  var realName = $('#edit-bind-card-form input[name=realName]').val();
	    		  var phone = $('#edit-bind-card-form input[name=phone]').val();
	    		  var address = $('#edit-bind-card-form input[name=address]').val();
	    		  
	    		  if(typeof price === 'undefined' || price < 0 || !checkIsMoney(price)){
	    			  layer.alert('请输入正确的余额');
	    			  return;
	    		  }else{
	    			  price = price * 100;
	    		  }
	    		  if(typeof cardNo === 'undefined' || cardNo == ''){
	    			  layer.alert('请输入卡号');
	    			  return;
	    		  }
	    		  if(typeof realName === 'undefined' || realName == ''){
	    			  layer.alert('请输入收件人姓名');
	    			  return;
	    		  }
	    		  if(typeof phone === 'undefined' || phone == ''){
	    			  layer.alert('请输入收件人电话');
	    			  return;
	    		  }
	    		  if(typeof address === 'undefined' || address == ''){
	    			  layer.alert('请输入收件人地址');
	    			  return;
	    		  }
	    		  var params = {
	    			'userId'  : row.id,
	    			'cardNo'  : cardNo,
	    			'surname' : surname,
	    			'name'    : name,
	    			'recommendPhone'  : recommendPhone,
	    			'realName': realName,
	    			'phone'   : phone,
	    			'address' : address,
	    			'price' : price
	    		  };
	    		  AJAX.cardmgr.card200006(params, function(data){
	    			  if(data && data.results){
							layer.alert('绑定成功');
							layer.close(index);
							userlistModel.loadData();
						}
					});
	    		  
	    	  },
	    	  no:function(index){
	    		  layer.close(index);
	    	  }
	    	});
		},
		convertList:function(items){
			if(typeof items !== 'undefined' && items.length > 0){
				for(var i = 0; i < items.length; i++){
					var item = items[i];
					item.isShop = '';
					if(typeof item.shopId !== 'undefined' && item.shopId != '' && item.shopId != '0'){
						item.isShop = '已设店铺管理';
					}
					if(typeof item.balance !== 'undefined' && item.balance != ''){
						item.balance = (item.balance * 0.01).toFixed(2);
					}
					if(typeof item.commision !== 'undefined' && item.commision != ''){
						item.commision = (item.commision * 0.01).toFixed(2);
					}
				}
			}
			return items;
		},
		getOperate:function(val,row,index){
			var operStr = '<a href="javascript:;" style="color:green;" onclick="userlistModel.editUser('+index+')">修改</a> &nbsp;&nbsp;'
			 	 + '<a href="javascript:;" style="color:red;" onclick="userlistModel.setShopMgr('+index+')">设置店铺管理员</a> &nbsp;&nbsp;'
			 	 + '<a href="javascript:;" style="color:red;" onclick="userlistModel.editBalance('+index+')">修改余额</a> &nbsp;&nbsp;'
				 + '<a href="javascript:;" style="color:red;" onclick="userlistModel.editCommision('+index+')">修改佣金</a> &nbsp;&nbsp;';
			if(row.cardNo == '未绑定'){
				operStr += '<a href="javascript:;" style="color:green;" onclick="userlistModel.editBindCard('+ index +')">一键绑卡</a> &nbsp;&nbsp;';
			}
			return operStr;
		}	
}

userlistModel.init();