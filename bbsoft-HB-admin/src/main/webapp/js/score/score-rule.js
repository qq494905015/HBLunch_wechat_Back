/**
 * 积分规则模块
 * @author Chris.Zhang
 * @date 2017-6-6 14:55:55 
 */
var scoreRuleModel = {
		init: function(){
			this.bindEvent();
			this.render();
			scoreRuleModel.params = {};
			this.loadData();
		},

		bindEvent: function(){
			var self = this;
			
			$(document).on('click', '#add-rule-btn', function(){
				scoreRuleModel.editRule();
			});
			
		},
		
		render: function(){
		},
		
		loadData:function(){
			var rows = [];
			AJAX.rulemgr.scoreruel200003({}, function(data){
				rows = scoreRuleModel.convertList(data.results);
				var obj = { total: data.results.length, rows: rows };
				$("#pg").pagination({
					total:obj.total,
					onSelectPage:function(pageNum, pageSize){
						scoreRuleModel.params.pageNum = pageNum;
						scoreRuleModel.params.pageSize = pageSize;
						scoreRuleModel.loadData();
					}
				});
                $('#dg').datagrid('loadData',obj);
			});
		},
		
		reset:function(){
			$("#search-form").form('clear');
			scoreRuleModel.loadData();
		},
		//编辑
		editRule: function(rowIndex){
			var row;
			var title = '新增积分规则信息';
			if(typeof rowIndex !== 'undefined'){
				$('#dg').datagrid('selectRow',rowIndex);// 关键在这里  
				row = $('#dg').datagrid('getSelected');  
				title = '修改积分规则信息';
			}
		    layer.open({
	    	  type: 1,
	    	  title: title,
	    	  closeBtn: 1, //不显示关闭按钮
	    	  btn:['确认', '取消'],
	    	  shade: [0],
	    	  area: ['500px', '400px'],
	    	  offset: '100px', //右下角弹出
	    	  content: $('#editRule'), //iframe的url，no代表不显示滚动条
	    	  success:function(layero, index){
	    		 if(typeof row !== 'undefined'){
	    			 //表单赋值
	    			 setFormValue('edit-rule-form', row);
	    		 }else{
	    			 //清空表单
	    			 clearFormValue('edit-rule-form');
	    		 }
	    		 var statusDoc = $('#edit-rule-form input:checkbox[name=status]');
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
	    	  },
	    	  yes:function(index){
	    		  var consumeAmount = $('#edit-rule-form input[name=consumeAmount]').val();
	    		  var token = $('#edit-rule-form input[name=token]').val();
	    		  var score = $('#edit-rule-form input[name=score]').val();
	    		  var description = $('#edit-rule-form input[name=description]').val();
	    		  var status = '1';
	    		  $("input:checkbox[name=status]:checked").each(function() { // 遍历name=test的多选框
	    			  status =  $(this).val();  // 每一个被选中项的值
	    		  });
	    		  if(status == 'on'){
	    			  status = '0';
	    		  }else{
	    			  status = '1';
	    		  }
	    		  var params = {
	    			'consumeAmount' : consumeAmount,
	    			'token' : token,
	    			'score'    : score,
	    			'description' : description,
	    			'status' 	  : status
	    		  };
	    		  console.info(params);
	    		  if(typeof row !== 'undefined'){
	    			  params.id = row.id;
	    		  }
    			  if(token == ''){
    				  layer.alert('调用不能为空');
    				  return;
    			  }
    			  if(score == ''){
    				  layer.alert('可获积分不能为空');
    				  return;
    			  }
    			  if(description == ''){
    				  layer.alert('规则描述不能为空');
    				  return;
    			  }
    			  if(status == ''){
	    			  layer.alert('请选择请用状态');
	    			  return;
	    		  }
    			  if(typeof params.consumeAmount !== 'undefined' && params.consumeAmount != ''){
    				  params.consumeAmount = params.consumeAmount * 100; 
    			  }
	    		  scoreRuleModel.saveRule(params, index);
	    	  },
	    	  no:function(index){
	    		  layer.close(index);
	    	  }
	    	});
		},
		//保存用户信息
		saveRule: function(params, index){
			if(!params){
				layer.alert('请输入需要填写的信息');
				return;
			}
			if(params.id){
				//修改
				AJAX.rulemgr.scoreruel200001(params, function(data){
					if(data && data.results){
						layer.alert('修改成功');
						layer.close(index);
						scoreRuleModel.loadData();
					}
				});
			}else{
				//新增
				AJAX.rulemgr.scoreruel200000(params, function(data){
					if(data && data.results){
						layer.alert('新增成功');
						layer.close(index);
						scoreRuleModel.loadData();
					}
				});
			}
			
		},
		convertList:function(items){
			if(typeof items !== 'undefined'){
				for(var i = 0; i < items.length; i++){
					var item = items[i];
					
					switch(item.status){
						case '0':
							item.statusName = '启用';
							break;
						case '1':
							item.statusName = '禁用';
							break;
						default:
							item.statusName = '未知状态';
							break;
					}
					
					if(typeof item.consumeAmount !== 'undefined' && item.consumeAmount != ''){
						item.consumeAmount = (item.consumeAmount * 0.01).toFixed(2);
					}
				}
			}
			return items;
		},
		getOperate:function(val,row,index){
			return '<a href="javascript:;" style="color:green;" onclick="scoreRuleModel.editRule('+index+')">修改</a> &nbsp;&nbsp;';
		}	
}

scoreRuleModel.init();