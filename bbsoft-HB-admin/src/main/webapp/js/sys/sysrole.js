/**
 * 角色模块
 * @author Chris.Zhang
 * @date 2017-6-6 14:55:55 
 */
var sysroleModel = {
		
		init: function(){
			this.bindEvent();
			this.render();
			this.initParams();
			this.loadData();
		},
		initParams:function(){
			sysroleModel.params = {
	            	'pageNum' : 1,
	            	'pageSize': 10,
	            	'search': ''
	            };   
		},
		//绑定事件
		bindEvent: function(){
			var self = this;
			$(document).on('click', '#search-btn', function() {
				var search = $("#search").val();
				sysroleModel.initParams();
				sysroleModel.params.search = search;
				sysroleModel.loadData();
	        });
			
			$(document).on('click', '#reset-btn', function() {
				sysroleModel.reset();
			});
			
			$(document).on('click', '#add-sysrole-btn', function(){
				sysroleModel.editRole();
			});
			
		},
		
		render: function(){
		},
		
		loadData:function(){
			var rows = [];
			AJAX.sysmgr.role200002(sysroleModel.params, function(data){
				rows = data.results.items;
				var obj = { total: data.results.total, rows: rows };
				$("#pg").pagination({
					total:obj.total,
					onSelectPage:function(pageNum, pageSize){
						sysroleModel.params.pageNum = pageNum;
						sysroleModel.params.pageSize = pageSize;
						sysroleModel.loadData();
					}
				});
                $('#dg').datagrid('loadData',obj);
			});
		},
		
		reset:function(){
			$("#search-form").form('clear');
			sysroleModel.initParams();
			sysroleModel.loadData();
		},
		editRole: function(rowIndex){
			var row;
			var title = '新增角色信息';
			if(typeof rowIndex !== 'undefined'){
				$('#dg').datagrid('selectRow', rowIndex);// 关键在这里  
				row = $('#dg').datagrid('getSelected');  
				title = '修改角色信息';
			}
		    layer.open({
	    	  type: 1,
	    	  title: title,
	    	  closeBtn: 1, //不显示关闭按钮
	    	  btn:['确认', '取消'],
	    	  shade: [0],
	    	  area: ['1000px', '620px'],
	    	  offset: '20px', //右下角弹出
	    	  content: $('#editRole'), //iframe的url，no代表不显示滚动条
	    	  success:function(layero, index){
	    		 if(typeof row !== 'undefined'){
	    			 //表单赋值
	    			 setFormValue("edit-role-form", row);
	    		 }else{
	    			//清空表单
	    			 clearFormValue('edit-role-form');
	    		 }
	    		var authList = $('#auth-list');
	    		authList.html('');
	  	        AJAX.sysmgr.menu200001(function(data){
    			  if(data && data.results){
    				  if(data.results.length > 0){
    					  for(var i = 0; i < data.results.length; i++){
    						  var menu = data.results[i];
    						  // 权限列表左边主模块
    						  var authLeft = $('<div class="auth-left"></div>');
    						  var authLeftInput = $('<input type="checkbox" name="auth-' + menu.id + '" lay-skin="primary" title="' + menu.name + '" value="' + menu.id + '"></input>');
    						  
    						  // 权限列表右边子模块
    						  var authRight = $('<div class="auth-right"></div>');
    						  if(typeof menu.childMenu !== 'undefined' && menu.childMenu.length > 0){
    							  var menuChilds = menu.childMenu;
    							  for(var j = 0; j < menuChilds.length; j ++){
    								  var menuChild = menuChilds[j];
    								  var authRightInput = $('<input type="checkbox" name="auth-' + menuChild.id + '" lay-skin="primary" title="' + menuChild.name + '" value="' + menuChild.id + '"></input>')
    								  authRight.append(authRightInput);
    							  }
    						  }
    						  authLeft.append(authLeftInput);
    						  authList.append(authLeft);
    						  authList.append(authRight);
    						  authList.append($('<div style="float:both;"></div>'));
    					  }
    					  if(typeof rowIndex !== 'undefined'){
    						  AJAX.sysmgr.menu200003({'roleId' : row.id},function(menuData){
    							  console.info(menuData.results)
    							  if(menuData && menuData.results){
    								  authList.find('input').each(function(){
    									  var $this = $(this);
    									  for(var i = 0; i < menuData.results.length; i++){
    										  var menu = menuData.results[i];
    										  if($this.val() == menu.id){
    											  $this.attr('checked', 'checked');
    											  break;
    										  }
    									  }
    								  });
    							  }
    							  //加载layui form
    							  layui.use('form', function(){
    								  var form = layui.form(); //只有执行了这一步，部分表单元素才会修饰成功
    								  form.render('checkbox'); 
    							  });
    						  });
    					  }else{
    						//加载layui form
							  layui.use('form', function(){
								  var form = layui.form(); //只有执行了这一步，部分表单元素才会修饰成功
								  form.render('checkbox'); 
							  });
    					  }
    				  }
    			  }
	  	        });
	    	  },
	    	  yes:function(index){
	    		  var roleName = $('#edit-role-form input[name=roleName]').val();
	    		  var description = $('#edit-role-form input[name=description]').val();
	    		  var auths = new Array();
	    		  $("#auth-list input:checkbox:checked").each(function() { 
	    			  auths.push($(this).val());  // 每一个被选中项的值
	    		 });
	    		  var params = {
	    			'roleName' : roleName,
	    			'description' : description,
	    			'menu' : auths.join(',')
	    		  };
	    		  if(typeof row !== 'undefined'){
	    			  params.id = row.id;
	    		  }
    			  if(roleName == ''){
    				  layer.alert('角色名称不能为空');
    				  return;
    			  }
	    		  sysroleModel.saveRole(params, index);
	    	  },
	    	  no:function(index){
	    		  layer.close(index);
	    	  }
	    	});
		},
		//保存角色信息
		saveRole: function(params, index){
			if(!params){
				layer.alert('请输入需要填写的信息');
				return;
			}
			if(params.id){
				//修改
				AJAX.sysmgr.role200001(params, function(data){
					if(data && data.results){
						layer.alert('修改成功');
						layer.close(index);
						sysroleModel.loadData();
					}
				});
			}else{
				//新增
				AJAX.sysmgr.role200000(params, function(data){
					if(data && data.results){
						layer.alert('新增成功');
						layer.close(index);
						sysroleModel.loadData();
					}
				});
			}
			
		},
		deleteRole:function(rowIndex){
			if(typeof rowIndex !== 'undefined'){
				$('#dg').datagrid('selectRow',rowIndex);// 关键在这里  
				var row = $('#dg').datagrid('getSelected');
				var params = {
						'id' : row.id
				};
				layer.confirm('确认删除此信息？', {icon: 3, title:'提示'}, function(cindex){
					AJAX.sysmgr.role200005(params, function(data){
						if(data && data.results){
							layer.alert('删除成功');
							sysroleModel.loadData();
						}
					});
				});
			}else{
				layer.alert('请选择需要删除的角色');
				return;
			}
		},
		getOperate:function(val,row,index){
			return '<a href="javascript:;" style="color:green;" onclick="sysroleModel.editRole('+index+')">修改</a> &nbsp;&nbsp;'
				+ '<a href="javascript:;" style="color:red;" onclick="sysroleModel.deleteRole('+index+')">删除</a>';
		}	
}

sysroleModel.init();