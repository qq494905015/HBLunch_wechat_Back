/**
 * 会员卡模块
 * @author Chris.Zhang
 * @date 2017-6-6 14:55:55
 */
var cardListModel = {
		init: function(){
			this.bindEvent();
			this.render();
			this.initParams();
			this.loadData();
		},
		initParams:function(){
			cardListModel.params = {
	            	'pageNum' : 1,
	            	'pageSize': 10,
	            	'search': ''
	            };   
		},
		bindEvent: function(){
			var self = this;
			$(document).on('click', '#search-btn', function() {
				var search = $("#search").val();
				cardListModel.initParams();
				cardListModel.params.search = search;
				cardListModel.loadData();
	        });
			
			$(document).on('click', '#reset-btn', function() {
				cardListModel.reset();
			});
			
			$(document).on('click', '#add-card-btn', function(){
				cardListModel.editCard();
			});
			
			$(document).on('click', '#import-btn', function(){
				cardListModel.importCard();
			});
			
			$(document).on('click', '#down-xlsx', function(){
				cardListModel.downloadTem();
			});
			
		},
		
		render: function(){
		},
		
		loadData:function(){
			var rows = [];
			AJAX.cardmgr.card200003(cardListModel.params, function(data){
				rows = cardListModel.convertList(data.results.items);
				var obj = { total: data.results.total, rows: rows };
				$("#pg").pagination({
					total:obj.total,
					onSelectPage:function(pageNum, pageSize){
						cardListModel.params.pageNum = pageNum;
						cardListModel.params.pageSize = pageSize;
						cardListModel.loadData();
					}
				});
                $('#dg').datagrid('loadData',obj);
			});
		},
		
		reset:function(){
			$("#search-form").form('clear');
			cardListModel.initParams();
			cardListModel.loadData();
		},
		
		//下载模板
		downloadTem: function(){
			window.location.href = HOST_URL +'/pages/cardlist.xlsx';
		},
		
		//导入卡号
		importCard: function(){
		    layer.open({
	    	  type: 1,
	    	  title: '导入卡号',
	    	  closeBtn: 1, //不显示关闭按钮
	    	  btn:['确认'],
	    	  shade: [0],
	    	  area: ['500px', '350px'],
	    	  offset: '100px', //右下角弹出
	    	  content: $('#importCard'), //iframe的url，no代表不显示滚动条
	    	  success:function(layero, index){
    		     $('.self-site-upload #errorMsg').remove();
    			 //清空表单
    			 clearFormValue('import-card-form');
    			 layui.use('form', function(){
    				 var form = layui.form(); //只有执行了这一步，部分表单元素才会修饰成功
  			  });
	  	      layui.use('upload', function(){
	  	        layui.upload({
	  	    	  url: HOST_URL + "/card200005.do",
	  	    	  success: function(data){
	  	    		  var msg = $('<div id="errorMsg" style="width:200px;margin-top:10px;">'+data.errorMsg+'</div>');
	  	    		  $('.self-site-upload').append(msg);
	  	    	  }
	  	    	});  
	  	      });
	    	  },
	    	  yes:function(index){
	    		  layer.close(index);
	    	  }
	    	});
		},
		//编辑
		editCard: function(rowIndex){
			var row;
			var title = '新增会员卡信息';
			if(typeof rowIndex !== 'undefined'){
				$('#dg').datagrid('selectRow',rowIndex);// 关键在这里  
				row = $('#dg').datagrid('getSelected');  
				title = '修改会员卡信息';
			}
		    layer.open({
	    	  type: 1,
	    	  title: title,
	    	  closeBtn: 1, //不显示关闭按钮
	    	  btn:['确认', '取消'],
	    	  shade: [0],
	    	  area: ['500px', '350px'],
	    	  offset: '100px', //右下角弹出
	    	  content: $('#editCard'), //iframe的url，no代表不显示滚动条
	    	  success:function(layero, index){
	    		 if(typeof row !== 'undefined'){
	    			 //表单赋值
	    			 setFormValue('edit-card-form', row);
	    		 }else{
	    			 //清空表单
	    			 clearFormValue('edit-card-form');
	    		 }
	    		 AJAX.productmgr.product200004(function(data){
    			  if(data && data.results){
    				  var selectRole = $('#edit-card-form select[name=product]');
    				  for(var i = 0; i < data.results.length; i++){
    					  var option = $('<option value="'+ data.results[i].id +'">' + data.results[i].name + '</option>');
    					  if(typeof row !== 'undefined'){
	    					  if(row.productId == data.results[i].id){
	    						  option.attr('selected', '');
	    					  }
    					  }else{
    						  if(i == 0){
    							  option.attr('selected', '');
    						  }
    					  }
    					  selectRole.append(option);
    				  }
    				  var isGoodsDoc = $('#edit-card-form input:checkbox[name=isGoods]');
    				  if(typeof row !== 'undefined'){
    					  if(row.isGoods == '0'){
    						  isGoodsDoc.removeAttr('checked');
    					  }else{
    						  isGoodsDoc.attr('checked', 'checked');
    					  }
    				  }else{
    					  isGoodsDoc.removeAttr('checked');
    				  }
    				  //加载layui form
		  	          layui.use('form', function(){
		  	        	var form = layui.form(); //只有执行了这一步，部分表单元素才会修饰成功
		  			  });
    			  }
	  	        });
	    	  },
	    	  yes:function(index){
	    		  var cardNo = $('#edit-card-form input[name=cardNo]').val();
	    		  var productId = $('#edit-card-form select[name=product]').val();
	    		  var price = $('#edit-card-form input[name=price]').val();
	    		  var isGoods = '';
	    		  $("input:checkbox[name=isGoods]:checked").each(function() { // 遍历name=test的多选框
	    			  isGoods =  $(this).val();  // 每一个被选中项的值
	    		  });
	    		  if(isGoods == 'on'){
	    			  isGoods = '1';
	    		  }else{
	    			  isGoods = '0';
	    		  }
	    		  var params = {
	    			'cardNo' : cardNo,
	    			'cardName' : '万味卡',
	    			'productId' : productId,
	    			'price'    : price,
	    			'isGoods' : isGoods
	    		  };
	    		  if(typeof row !== 'undefined'){
	    			  params.id = row.id;
	    		  }
    			  if(cardNo == ''){
    				  layer.alert('卡号不能为空');
    				  return;
    			  }
    			  if(productId == ''){
    				  layer.alert('请选择所属商品');
    				  return;
    			  }
    			  if(price == ''){
    				  layer.alert('请输入售价');
    				  return;
    			  }
    			  if(!checkIsMoney(price)){
    				  layer.alert('请输入正确格式额度金额数');
    				  return;
    			  }
    			  params.price = params.price * 100;
	    		  cardListModel.saveCard(params, index);
	    	  },
	    	  no:function(index){
	    		  layer.close(index);
	    	  }
	    	});
		},
		//保存会员卡信息
		saveCard: function(params, index){
			if(!params){
				layer.alert('请输入需要填写的信息');
				return;
			}
			if(params.id){
				//修改
				AJAX.cardmgr.card200001(params, function(data){
					if(data && data.results){
						layer.alert('修改成功');
						layer.close(index);
						cardListModel.loadData();
					}
				});
			}else{
				//新增
				AJAX.cardmgr.card200000(params, function(data){
					if(data && data.results){
						layer.alert('新增成功');
						layer.close(index);
						cardListModel.loadData();
					}
				});
			}
			
		},
		deleteCard:function(id){
			if(typeof id !== 'undefined'){
				var params = {
						'id' : id
				};
				layer.confirm('确认删除此会员卡信息？', {icon: 3, title:'提示'}, function(cindex){
					AJAX.cardmgr.card200004(params, function(data){
						if(data && data.results){
							layer.alert('删除成功');
							sysuserModel.loadData();
						}
					});
				});
			}else{
				layer.alert('请选择需要删除的会员卡');
				return;
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
						case '2':
							item.statusName = '未支付禁用';
							break;
						case '3':
							item.statusName = '已付未激活';
							break;
						case '4':
							item.statusName = '已付已激活';
							break;
						default:
							item.statusName = '未知状态';
							break;
					}
					switch(item.isGoods){
						case '0':
							item.isGoodsName = '否';
							break;
						case '1':
							item.isGoodsName = '是';
							break;
						default:
							item.isGoodsName = '未知';
							break;
					}
					
					if(typeof item.price !== 'undefined' && item.price != ''){
						item.price = (item.price * 0.01).toFixed(2);
					}
					if(typeof item.balance !== 'undefined' && item.balance != ''){
						item.balance = (item.balance * 0.01).toFixed(2);
					}
				}
			}
			return items;
		},
		getOperate:function(val,row,index){
			return '<a href="javascript:;" style="color:green;" onclick="cardListModel.editCard('+index+')">修改</a> &nbsp;&nbsp;' + 
				   '<a href="javascript:;" style="color:green;" onclick="cardListModel.deleteCard('+row.id+')">删除</a> &nbsp;&nbsp;';
		}
}


cardListModel.init();