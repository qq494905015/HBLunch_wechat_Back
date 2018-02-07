/**
 * 店铺模块
 * @author Chris.Zhang
 * @date 2017-6-6 14:55:55 
 */
var shopListModel = {
		init: function(){
			this.bindEvent();
			this.render();
			this.initParams();
			this.loadData();
		},
		initParams:function(){
			shopListModel.params = {
	            	'pageNum' : 1,
	            	'pageSize': 10,
	            	'search': ''
	            };   
		},
		bindEvent: function(){
			var self = this;
			$(document).on('click', '#search-btn', function() {
				var search = $("#search").val();
				shopListModel.initParams();
				shopListModel.params.search = search;
				shopListModel.loadData();
	        });
			$(document).on('click', '#reset-btn', function() {
				shopListModel.reset();
			});
			
			$(document).on('click', '#add-shop-btn', function(){
				shopListModel.editShop();
			});
			
			var map = new BMap.Map("map-container");
			map.centerAndZoom('深圳', 15);
			map.enableScrollWheelZoom();    //启用滚轮放大缩小，默认禁用
			map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
			map.addControl(new BMap.NavigationControl());  //添加默认缩放平移控件
			map.addControl(new BMap.OverviewMapControl()); //添加默认缩略地图控件
			var localSearch = new BMap.LocalSearch(map);
			localSearch.enableAutoViewport(); //允许自动调节窗体大小
		    var geolocation = new BMap.Geolocation();
		    geolocation.getCurrentPosition(function(r){
	    	 if(this.getStatus() == BMAP_STATUS_SUCCESS){
	    		 //表示获取成功那么 r 这个参数就包含有当前的地理位置经纬度
	    		 //逆地址解析，就是要把当前的经纬度转为当前具体地理位置
	    		 //逆地址解析
	    		 var marker = new BMap.Marker(r.point);  // 创建标注，为要的地方对应的经纬度
	    		 map.addOverlay(marker);
	    		 map.panTo(r.point)
	    		 var geoc = new BMap.Geocoder();
	    		 geoc.getLocation(r.point, function (rs) {
    	　　　　　　　　	var addComp = rs.addressComponents;
    	　　　　　　　　	//对应的省市区、县街道，街道号
	    		 });
	    	}else {
	    	  alert('failed'+this.getStatus());
	    	} 
		    },{enableHighAccuracy: true});
		    
		    $(document).on('input', '#address', function(){
		    	map.clearOverlays();//清空原来的标注
			    var keyword = $('#address').val();
			    localSearch.setSearchCompleteCallback(function (searchResult) {
			    	
			        if( typeof searchResult !== 'undefined' && typeof searchResult.getPoi(0) !== 'undefined'){
			        	var poi = searchResult.getPoi(0);
				        map.centerAndZoom(poi.point, 13);
				        var marker = new BMap.Marker(new BMap.Point(poi.point.lng, poi.point.lat));  // 创建标注，为要查询的地方对应的经纬度
				        map.addOverlay(marker);
				        shopListModel.point = poi.point;
				        var content = keyword + "<br/><br/>经度：" + poi.point.lng + "<br/>纬度：" + poi.point.lat;
				        var infoWindow = new BMap.InfoWindow("<p style='font-size:14px;'>" + content + "</p>");
				        marker.addEventListener("click", function () { this.openInfoWindow(infoWindow); });
				        marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
			        }
			    });
			    localSearch.search(keyword);
			});
			
		},
		
		render: function(){
			
		},
		loadData:function(){
			var rows = [];
			AJAX.shopmgr.shop200003(shopListModel.params, function(data){
				rows = shopListModel.convertShop(data.results.items);
				var obj = { total: data.results.total, rows: rows };
				$("#pg").pagination({
					total:obj.total,
					onSelectPage:function(pageNum, pageSize){
						shopListModel.params.pageNum = pageNum;
						shopListModel.params.pageSize = pageSize;
						shopListModel.loadData();
					}
				});
                $('#dg').datagrid('loadData',obj);
			});
		},
		
		reset:function(){
			$("#search-form").form('clear');
			shopListModel.initParams();
			shopListModel.loadData();
		},
		//编辑
		editShop: function(rowIndex){
			var row;
			var title = '新增门店信息';
			if(typeof rowIndex !== 'undefined'){
				$('#dg').datagrid('selectRow',rowIndex);// 关键在这里  
				row = $('#dg').datagrid('getSelected');  
				title = '修改门店信息';
			}
		    layer.open({
	    	  type: 1,
	    	  title: title,
	    	  closeBtn: 1, //不显示关闭按钮
	    	  btn:['确认', '取消'],
	    	  shade: [0],
	    	  area: ['800px', '760px'],
	    	  offset: '20px', //右下角弹出
	    	  content: $('#editUser'), //iframe的url，no代表不显示滚动条
	    	  success:function(layero, index){
	    		 if(typeof row !== 'undefined'){
	    			 //表单赋值
	    			 setFormValue('edit-shop-form', row);
	    			 if($('.shop-logo-show').children('img').length > 0){
	    			 	$('.shop-logo-show').children('img').remove();
	    			 }	
	    			 if(typeof row.logo !== 'undefined'){
	    				 var img = $('<img class="shop-logo-img" style="width:200px;height:200px;margin-top:10px;margin-right:10px;cursor:pointer;" src="' + row.logo + '" title="' + row.logo + '"/>');
	    				 img.dblclick(function(){
	  	    	    		$(this).remove();
	  	    	    	 });
	  	    	    	$('.shop-logo-show').append(img);
	    			 }
//	    			 shopListModel.point = {
//	    				'lng' : row.longitude,
//	    				'lat' : row.latitude
//	    			 }; 
	    			 $('#address').trigger('input');
	    		 }else{
	    			 //清空表单
	    			 clearFormValue('edit-shop-form');
	    		 }
	    		 //加载layui form
	  	          layui.use('form', function(){
	  	        	var form = layui.form(); //只有执行了这一步，部分表单元素才会修饰成功
	  			  });
		  	      layui.use('upload', function(){
		  	    	  layui.upload({
			  	    	  elem:'#logo-file',
			  	    	  url: HOST_URL + "/upload100000.do",
			  	    	  success: function(data){
			  	    	    if(data && data.results){
			  	    	    	if($('.shop-logo-show').children('img').length > 0){
			 	    			 	$('.shop-logo-show').children('img').remove();
			 	    			}	
		  	    	    		var img = $('<img class="shop-logo-img" style="width:200px;height:200px;margin-top:10px;margin-right:10px;cursor:pointer;" src="' + data.results.filePath + '" title="' + data.results.fileName + '"/>');
		  	    	    		img.dblclick(function(){
		  	    	    			$(this).remove();
		  	    	    		});
		  	    	    		$('.shop-logo-show').append(img);
			  	    	    }
			  	    	  }
		  	    	  });
			  	  });
		  	    $('#layui_editor').val('');
	  	        if(typeof row !== 'undefined' && typeof row.description !== 'undefined'){
	  	        	$('#layui_editor').val(row.description);
	  	        }
	  	        layui.use('layedit', function(){
	  	        	var layedit = layui.layedit;
	  	        	layedit.set({
	  	        		uploadImage: {
	  	        		    url: HOST_URL + "/upload100001.do"
	  	        		  }
	  	        	});
	  	        	shopListModel.editIndex = layedit.build('layui_editor'); //建立编辑器
	  	        });
	    	  },
	    	  yes:function(index){
	    		  var name = $('#edit-shop-form input[name=name]').val();
	    		  var linkName = $('#edit-shop-form input[name=linkName]').val();
	    		  var phone = $('#edit-shop-form input[name=phone]').val();
	    		  var address = $('#edit-shop-form input[name=address]').val();
	    		  var description = layui.layedit.getContent(shopListModel.editIndex);
	    		  var params = {
	    			'name' : name,
	    			'shortName' : name,
	    			'linkName' : linkName,
	    			'phone' : phone,
	    			'address' : address,
	    			'description' : description
	    		  };
	    		  if(typeof shopListModel.point !== 'undefined'){
	    			  params.longitude = shopListModel.point.lng; 
	    			  params.latitude = shopListModel.point.lat; 
	    		  }else{
	    			  layer.alert('定位未获取到，请重新获取');
	    			  return;
	    		  }
	    		  params.logo = $('.shop-logo-img').attr('src');
	    		  if(typeof row !== 'undefined'){
	    			  params.id = row.id;
	    		  }else{
	    			  if(name == ''){
	    				  layer.alert('商家名不能为空');
	    				  return;
	    			  }
	    			  if(linkName == ''){
	    				  layer.alert('负责人姓名不能为空');
	    				  return;
	    			  }
	    			  if(phone == ''){
	    				  layer.alert('联系电话不能为空');
	    				  return;
	    			  }
	    			  if(params.logo == '' || typeof params.logo === 'undefined'){
	    				  layer.alert('请上传商家LOGO');
	    				  return;
	    			  }
	    			  if(address == ''){
		    			  layer.alert('商家地址不能为空');
		    			  return;
		    		  }
	    		  }
	    		  shopListModel.saveShop(params, index);
	    	  },
	    	  no:function(index){
	    		  layer.close(index);
	    	  }
	    	});
		},
		//保存店铺信息
		saveShop: function(params, index){
			if(!params){
				layer.alert('请输入需要填写的信息');
				return;
			}
			if(params.id){
				//修改
				AJAX.shopmgr.shop200001(params, function(data){
					if(data && data.results){
						layer.alert('修改成功');
						layer.close(index);
						shopListModel.loadData();
					}
				});
			}else{
				//新增
				AJAX.shopmgr.shop200000(params, function(data){
					if(data && data.results){
						layer.alert('新增成功');
						layer.close(index);
						shopListModel.loadData();
					}
				});
			}
			
		},
		//删除店铺信息
		deleteShop:function(rowIndex){
			if(typeof rowIndex !== 'undefined'){
				$('#dg').datagrid('selectRow',rowIndex);// 关键在这里  
				var row = $('#dg').datagrid('getSelected');
				var params = {
						'id' : row.id
				};
				layer.confirm('确认删除此信息？', {icon: 3, title:'提示'}, function(cindex){
					AJAX.shopmgr.shop200002(params, function(data){
						if(data && data.results){
							layer.alert('删除成功');
							shopListModel.loadData();
						}
					});
				});
			}else{
				layer.alert('请选择需要删除的店铺');
				return;
			}
		},
		//格式化店铺信息
		convertShop: function(items){
			if(typeof items !== 'undefined'){
				for(var i = 0; i < items.length; i++){
					var item = items[i];
					if(item.isOnline == '0'){
						item.isOnlineName = '未上线';
					}else{
						item.isOnlineName = '已上线';
					}
				}
			}
			return items;
		},
		getOperate:function(val,row,index){
			return '<a href="javascript:;" style="color:green;" onclick="shopListModel.editShop('+index+')">修改</a> &nbsp;&nbsp;'
			+ '<a href="javascript:;" style="color:red;" onclick="shopListModel.deleteShop('+index+')">删除</a>';
		}	
}

shopListModel.init();