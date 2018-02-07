/**
 * 商品模块
 * @author Chris.Zhang
 * @date 2017-6-6 14:55:55
 */
var productListModel = {
		init: function(){
			this.bindEvent();
			this.render();
			this.initParams();
			this.loadData();
		},
		
		// 初始化参数
		initParams:function(){
			productListModel.params = {
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
				productListModel.initParams();
				productListModel.params.search = search;
				productListModel.loadData();
	        });
			
			$(document).on('click', '#reset-btn', function() {
				productListModel.reset();
			});
			
			$(document).on('click', '#add-sysuser-btn', function(){
				productListModel.editProduct();
			});
			
		},
		
		render: function(){
		},
		
		loadData:function(){
			var rows = [];
			AJAX.productmgr.product200003(productListModel.params, function(data){
				rows = data.results.items;
				var obj = { total: data.results.total, rows: rows };
				$("#pg").pagination({
					total:obj.total,
					onSelectPage:function(pageNum, pageSize){
						productListModel.params.pageNum = pageNum;
						productListModel.params.pageSize = pageSize;
						productListModel.loadData();
					}
				});
                $('#dg').datagrid('loadData',obj);
			});
		},
		
		reset:function(){
			$("#search-form").form('clear');
			productListModel.initParams();
			productListModel.loadData();
		},
		//查看商品信息
		showProduct: function(rowIndex){
			layer.alert('不知道如何展示');
		},
		//编辑
		editProduct: function(rowIndex){
			var row;
			var title = '新增商品信息';
			if(typeof rowIndex !== 'undefined'){
				$('#dg').datagrid('selectRow',rowIndex);// 关键在这里  
				row = $('#dg').datagrid('getSelected');  
				title = '修改商品信息';
			}
		    layer.open({
	    	  type: 1,
	    	  title: title,
	    	  closeBtn: 1, //不显示关闭按钮
	    	  btn:['确认', '取消'],
	    	  shade: [0],
	    	  area: ['700px', '700px'],
	    	  offset: '100px', //右下角弹出
	    	  content: $('#editProduct'), //iframe的url，no代表不显示滚动条
	    	  success:function(layero, index){
	    		  $('.self-site-upload img').remove();
	    		  $('.edit-product-form texarea').html('');
	    		 if(typeof row !== 'undefined'){
	    			 //表单赋值
	    			 setFormValue('edit-product-form', row);
	    			 var img = $('<img id="mainImg" style="width:500px;height:300px;margin-top:10px;" src="' + row.mainImg + '"/>');
	    	    	 $('.self-site-upload').append(img);
	    		 }else{
	    			 //清空表单
	    			 clearFormValue('edit-product-form');
	    		 }
	    		 //加载layui form
	  	          layui.use('form', function(){
	  	        	var form = layui.form(); //只有执行了这一步，部分表单元素才会修饰成功
	  			  });
	  	          
	  	        $('#layui_editor').val('');
	  	        if(typeof row !== 'undefined'){
	  	        	$('#layui_editor').val(row.description);
	  	        }
	  	        layui.use('layedit', function(){
	  	        	var layedit = layui.layedit;
	  	        	layedit.set({
	  	        		uploadImage: {
	  	        		    url: HOST_URL + "/upload100001.do"
	  	        		  }
	  	        	});
	  	        	productListModel.editIndex = layedit.build('layui_editor'); //建立编辑器
	  	        });
	  	       
	  	      layui.use('upload', function(){
	  	        layui.upload({
	  	    	  url: HOST_URL + "/upload100000.do",
	  	    	  success: function(data){
	  	    	    if(data && data.results){
	  	    	    	if($('.self-site-upload img').width() == null){
	  	    	    		var img = $('<img id="mainImg" style="width:500px;height:300px;margin-top:10px;" src="' + data.results.filePath + '" title="' + data.results.fileName + '"/>');
	  	    	    		$('.self-site-upload').append(img);
	  	    	    	}else{
	  	    	    		$('.self-site-upload img').attr('src', data.results.filePath);
	  	    	    	}
	  	    	    }
	  	    	  }
	  	    	});  
	  	      });
	    	  },
	    	  yes:function(index){
	    		  var name = $('#edit-product-form input[name=name]').val();
	    		  var price = $('#edit-product-form input[name=price]').val();
	    		  var mainImg = $('#mainImg').attr('src');
	    		  var description = layui.layedit.getContent(productListModel.editIndex);
	    		  var params = {
	    			'name' : name,
	    			'price' : price,
	    			'mainImg'    : mainImg,
	    			'description' : description
	    		  };
	    		  console.info(params);
	    		  if(typeof row !== 'undefined'){
	    			  params.id = row.id;
	    		  }else{
	    			  if(name == ''){
	    				  layer.alert('商品名称不能为空');
	    				  return;
	    			  }
	    			  if(price == ''){
	    				  layer.alert('商品售价不能为空');
	    				  return;
	    			  }
	    			  if(mainImg == ''){
	    				  layer.alert('商品主图不能为空');
	    				  return;
	    			  }
	    			  if(description == ''){
	    				  layer.alert('商品详情不能为空');
	    				  return;
	    			  }
	    		  }
	    		  productListModel.saveProduct(params, index);
	    	  },
	    	  no:function(index){
	    		  layer.close(index);
	    	  }
	    	});
		},
		//保存商品信息
		saveProduct: function(params, index){
			if(!params){
				layer.alert('请输入需要填写的信息');
				return;
			}
			if(params.id){
				//修改
				AJAX.productmgr.product200001(params, function(data){
					if(data && data.results){
						layer.alert('修改成功');
						layer.close(index);
						productListModel.loadData();
					}
				});
			}else{
				//新增
				AJAX.productmgr.product200000(params, function(data){
					if(data && data.results){
						layer.alert('新增成功');
						layer.close(index);
						productListModel.loadData();
					}
				});
			}
			
		},
		deleteProduct: function(rowIndex){
			if(typeof rowIndex !== 'undefined'){
				$('#dg').datagrid('selectRow',rowIndex);// 关键在这里  
				var row = $('#dg').datagrid('getSelected');
				var params = {
						'id' : row.id
				};
				layer.confirm('确认删除商品信息？', {icon: 3, title:'提示'}, function(cindex){
					AJAX.productmgr.product200002(params, function(data){
						if(data && data.results){
							layer.alert('删除成功');
							productListModel.loadData();
						}
					});
				});
			}else{
				layer.alert('请选择需要删除的商品');
			}
		},
		getOperate:function(val,row,index){
			return '<a href="javascript:;" style="color:blue;" onclick="productListModel.showProduct('+index+')">查看</a> &nbsp;&nbsp;' + 
			'<a href="javascript:;" style="color:green;" onclick="productListModel.editProduct('+index+')">修改</a> &nbsp;&nbsp;' +
			'<a href="javascript:;" style="color:green;" onclick="productListModel.deleteProduct('+index+')">删除</a>';
		}	
}

productListModel.init();