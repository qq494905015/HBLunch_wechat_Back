/**
 * 管理员用户模块
 * @author Chris.Zhang
 * @date 2017-6-6 14:55:55
 */
var sysuserModel = {
		init: function(){
			this.bindEvent();
			this.render();
			this.initParams();
			this.loadData();
		},
		
		// 初始化参数
		initParams:function(){
			sysuserModel.params = {
	            	'pageNum' : 1,
	            	'pageSize': 10,
	            	'search': ''
	            };   
		},
		// 绑定事件
		bindEvent: function(){
			var self = this;
			$(document).on('click', '#search-btn', function() {
				var search = $("#search").val();
				sysuserModel.initParams();
				sysuserModel.params.search = search;
				sysuserModel.loadData();
	        });
			
			$(document).on('click', '#reset-btn', function() {
				sysuserModel.reset();
			});
			
			$(document).on('click', '#add-sysuser-btn', function(){
				sysuserModel.editUser();
			});
			
		},
		
		render: function(){
		},
		
		// 加载数据
		loadData:function(){
			var rows = [];
			AJAX.sysmgr.sysUser200002(sysuserModel.params, function(data){
				rows = data.results.items;
				var obj = { total: data.results.total, rows: rows };
				$("#pg").pagination({
					total:obj.total,
					onSelectPage:function(pageNum, pageSize){
						sysuserModel.params.pageNum = pageNum;
						sysuserModel.params.pageSize = pageSize;
						sysuserModel.loadData();
					}
				});
                $('#dg').datagrid('loadData',obj);
			});
		},
		
		// 重置查询
		reset:function(){
			$("#search-form").form('clear');
			sysuserModel.initParams();
			sysuserModel.loadData();
		},
		// 编辑用户
		editUser: function(rowIndex){
			var row;
			var title = '新增管理员信息';
			if(typeof rowIndex !== 'undefined'){
				$('#dg').datagrid('selectRow',rowIndex);// 关键在这里  
				row = $('#dg').datagrid('getSelected');  
				title = '修改管理员信息';
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
	    			 setFormValue("edit-user-form", row);
	    		 }else{
	    			 //清空表单
	    			 clearFormValue('edit-user-form');
	    		 }
	  	        AJAX.sysmgr.role200004(function(data){
    			  if(data && data.results){
    				  var selectRole = $('#edit-user-form select[name=role]');
    				  for(var i = 0; i < data.results.length; i++){
    					  var option = $('<option value="'+ data.results[i].id +'">' + data.results[i].roleName + '</option>');
    					  if(typeof row !== 'undefined'){
	    					  if(row.roleId == data.results[i].id){
	    						  option.attr('selected', '');
	    					  }
    					  }else{
    						  if(i == 0){
    							  option.attr('selected', '');
    						  }
    					  }
    					  selectRole.append(option);
    				  }
    				  var statusDoc = $('#edit-card-form input:checkbox[name=status]');
    				  if(typeof row !== 'undefined'){
    					  if(row.status == '1'){
    						  statusDoc.removeAttr('checked');
    					  }else{
    						  statusDoc.attr('checked', 'checked');
    					  }
    				  }else{
    					  statusDoc.attr('checked', 'checked');
    				  }
    				  //加载layui form
		  	          layui.use('form', function(){
		  	        	var form = layui.form(); //只有执行了这一步，部分表单元素才会修饰成功
		  			  });
    			  }
	  	        });
	    	  },
	    	  yes:function(index){
	    		  var userName = $('#edit-user-form input[name=userName]').val();
	    		  var nickName = $('#edit-user-form input[name=nickName]').val();
	    		  var password = $('#edit-user-form input[name=pwd]').val();
	    		  var commitPwd = $('#edit-user-form input[name=commitPwd]').val();
	    		  var email = $('#edit-user-form input[name=email]').val();
	    		  var phone = $('#edit-user-form input[name=phone]').val();
	    		  var status = $('#edit-user-form input[name=status]').val();
	    		  var roleId = $('#edit-user-form select[name=role]').val();
	    		  var status = '';//$('#edit-user-form input[name=status]').val();
	    		  $("input:checkbox[name=status]:checked").each(function() { // 遍历name=test的多选框
	    			  status =  $(this).val();  // 每一个被选中项的值
	    		 });
	    		  if(status == 'on'){
	    			  status = '0';
	    		  }else{
	    			  status = '1';
	    		  }
	    		  var params = {
	    			'userName' : userName,
	    			'nickName' : nickName,
	    			'password' : password,
	    			'email' : email,
	    			'phone' : phone,
	    			'status' : status,
	    			'roleId' : roleId
	    		  };
	    		  if(typeof row !== 'undefined'){
	    			  params.id = row.id;
	    		  }
    			  if(userName == ''){
    				  layer.alert('用户名不能为空');
    				  return;
    			  }
    			  if(nickName == ''){
    				  layer.alert('昵称不能为空');
    				  return;
    			  }
    			  if(phone == ''){
    				  layer.alert('手机号不能为空');
    				  return;
    			  }
    			  if(roleId == ''){
    				  layer.alert('请选择角色');
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
	    		  sysuserModel.saveUser(params, index);
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
				AJAX.sysmgr.sysUser200004(params, function(data){
					if(data && data.results){
						layer.alert('修改成功');
						layer.close(index);
						sysuserModel.loadData();
					}
				});
			}else{
				//新增
				AJAX.sysmgr.sysUser200000(params, function(data){
					if(data && data.results){
						layer.alert('新增成功');
						layer.close(index);
						sysuserModel.loadData();
					}
				});
			}
			
		},
		
		// 删除用户
		deleteUser:function(rowIndex){
			if(typeof rowIndex !== 'undefined'){
				$('#dg').datagrid('selectRow',rowIndex);// 关键在这里  
				var row = $('#dg').datagrid('getSelected');
				var params = {
						'id' : row.id
				};
				layer.confirm('确认删除此用户信息？', {icon: 3, title:'提示'}, function(cindex){
					AJAX.sysmgr.sysUser200005(params, function(data){
						if(data && data.results){
							layer.alert('删除成功');
							sysuserModel.loadData();
						}
					});
				});
			}else{
				layer.alert('请选择需要删除的用户');
				return;
			}
		},
		
		// 获取列表操作按钮
		getOperate:function(val,row,index){
			return '<a href="javascript:;" style="color:green;" onclick="sysuserModel.editUser('+index+')">修改</a> &nbsp;&nbsp;'
				+ '<a href="javascript:;" style="color:red;" onclick="sysuserModel.deleteUser('+index+')">删除</a>';
		}	
}

sysuserModel.init();