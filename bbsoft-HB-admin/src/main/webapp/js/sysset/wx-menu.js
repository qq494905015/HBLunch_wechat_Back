/**
 * 系统设置-微菜单配置
 * @author Chris.Zhang
 * @date 2017-7-24 14:45:54 
 */
var wxMenuModel = {
		init: function(){
			this.bindEvent();
			this.render();
			this.loadData();
		},

		bindEvent: function(){
			var self = this;
			//保存
			$(document).on('click', '#to-save-btn', function() {
				wxMenuModel.saveMenu();
			});
			//同步到微信
			$(document).on('click', '#to-wechat-btn', function() {
				wxMenuModel.updateToWechat();
	        });
		},
		
		render: function(){
		},
		
		loadData:function(){
			AJAX.wemenumgr.weMenu200002(function(data){
				
				if(data && data.results){
					var menus = data.results;
					var menuList = new Array();
					//处理数据结构
					for(var i = 0; i < menus.length; i++){
						var menu = menus[i];
						if(menu.parentId == 0){
							menu.childMenus = new Array();
							menuList.push(menu);
							
						}
					}
					if(menuList && menuList.length > 0){
						for(var i = 0; i < menuList.length; i++){
							var firstMenu = menuList[i];
							for(var j = 0; j < menus.length; j++){
								var secondMenu = menus[j];
								if(secondMenu.parentId == firstMenu.id){
									firstMenu.childMenus.push(secondMenu);
								}
							}
						}
						//渲染
						for(var i = 0; i < menuList.length; i++){
							var menuFirst = menuList[i];
							var menuFirstDom = $('#menu-table input[name=menu-first-' + (i + 1) + ']');
							menuFirstDom.val(menuFirst.name);
							menuFirstDom.attr('data-id', menuFirst.id);
							menuFirstDom.attr('data-parent-id', menuFirst.parentId);
							var childMenus = menuFirst.childMenus;
							if(childMenus && childMenus.length > 0){
								for(var j = 0; j < childMenus.length; j++){
									var secondMenu = childMenus[j];
									var menuSecondNameDom = $('#menu-table input[name=second-name' + (i + 1) + '-' + (j + 1) +']');
									var menuSecondUrlDom = $('#menu-table input[name=second-url' + (i + 1) + '-' + (j + 1) +']');
									var menuSecondAuthDom = $('#menu-table input:checkbox[name=second-auth' + (i + 1) + '-' + (j + 1) +']');
									menuSecondNameDom.attr('data-id', secondMenu.id);
									menuSecondNameDom.attr('data-parent-id', secondMenu.parentId);
									menuSecondNameDom.val(secondMenu.name);
									menuSecondUrlDom.val(secondMenu.url);
									if(secondMenu.isAuth == '0'){
										menuSecondAuthDom.removeAttr('checked');
									}else{
										menuSecondAuthDom.attr('checked', 'checked');
									}
								}
							}
						}
						//加载layui form
			  	        layui.use('form', function(){
			  	        	var form = layui.form(); //只有执行了这一步，部分表单元素才会修饰成功
			  			});
					}
					
				}
			});
		},
		updateToWechat: function(){
			AJAX.wemenumgr.weMenu200003(function(data){
				if(data && data.results){
					layer.alert('同步成功');
				}
			})
		},
		saveMenu: function(){
			var reqMenu = new Array();
			//一级菜单
			for(var i = 1; i <=3 ; i++){
				var menuFirstDom = $('#menu-table input[name=menu-first-' + i + ']');
				var firstMenuName = menuFirstDom.val();
				var firstMenuId = menuFirstDom.attr('data-id');
				var firstMenu = {'id' : firstMenuId, 'name' : firstMenuName, 'isAuth' : '0'};
				//二级菜单
				for(var j = 1; j <= 5; j++){
					var menuSecondNameDom = $('#menu-table input[name=second-name' + i + '-' + j +']');
					var menuSecondUrlDom = $('#menu-table input[name=second-url' + i + '-' + j +']');
					var menuSecondId =  menuSecondNameDom.attr('data-id');
					var menuSecondName =  menuSecondNameDom.val();
					var menuSecondUrl =  menuSecondUrlDom.val();
					var isAuth = '';
					$('#menu-table input:checkbox[name=second-auth' + i + '-' + j +']:checked').each(function() { // 遍历name=test的多选框
						isAuth =  $(this).val();  // 每一个被选中项的值
		    		});
					if(isAuth == 'on'){
						isAuth = '1';
					}else{
	    			  isAuth = '0';
					}
					var secondMenu = {'id' : menuSecondId,'name' : menuSecondName, 'url' : menuSecondUrl, 'isAuth' : isAuth};
					reqMenu.push(secondMenu);
				}
				
				//存入一级菜单
				reqMenu.push(firstMenu);
			}
			
			AJAX.wemenumgr.weMenu200004({'reqMenu' : JSON.stringify(reqMenu)}, function(data){
				if(data && data.results){
					window.location.reload();
					layer.alert('编辑成功');
				}
			});
		}
		
}

wxMenuModel.init();