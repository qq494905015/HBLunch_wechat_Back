var mainPlatform = {

	init: function(){
		var menus = [
		             {'id' : '100000', 'title' : '系统管理', 'icon' : 'toggle-icon', 'isCurrent' : true, 
		            	 'childMenu' : [
		            	             {'id' : '100001', 'title' : '管理员管理', 'icon' : 'toggle-icon', 'href' : 'pages/sys/sysuser.html', 'isCurrent' : false},
		            	             {'id' : '100002', 'title' : '角色管理', 'icon' : 'toggle-icon', 'href' : 'login.html', 'isCurrent' : false},
		            	             ]
		             },
		             {'id' : '200000', 'title' : '用户管理', 'icon' : 'toggle-icon'},
		             ];
		this.getLoginUser();
		this.getLoginMenu();
		this.bindEvent();
	},

	bindEvent: function(){
		var self = this;
		// 顶部大菜单单击事件
		$(document).on('click', '.pf-nav-item', function() {
            $('.pf-nav-item').removeClass('current');
            $(this).addClass('current');

            // 渲染对应侧边菜单
            var m = $(this).data('menu');
            self.render(menu[m]);
        });

        $(document).on('click', '.sider-nav li', function() {
            $('.sider-nav li').removeClass('current');
            $(this).addClass('current');
            $('iframe').attr('src', $(this).data('src'));
        });

        //左侧菜单收起
        $(document).on('click', '.toggle-icon', function() {
            $(this).closest("#pf-bd").toggleClass("toggle");
            setTimeout(function(){
            	$(window).resize();
            },300)
        });
        
        //查看当前登录用户信息
        $(document).on('click', '.pf-user-info', function() {
        	 layer.open({
   	    	  type: 1,
   	    	  title: '修改用户信息',
   	    	  closeBtn: 1, //不显示关闭按钮
   	    	  btn:['确认'],
   	    	  shade: [0],
   	    	  area: ['500px', '500px'],
   	    	  offset: '100px', //右下角弹出
   	    	  content: $('#u-show-user-info'), //iframe的url，no代表不显示滚动条
   	    	  success:function(layero, index){
   	    		  if(mainPlatform.loginUser){
   	    			  $('#u-nickName').html(mainPlatform.loginUser.nickName);
   	    			  $('#u-userName').html(mainPlatform.loginUser.userName);
   	    			  $('#u-email').html(mainPlatform.loginUser.email);
   	    			  $('#u-phone').html(mainPlatform.loginUser.phone);
   	    			  $('#u-qq').html(mainPlatform.loginUser.qq);
   	    			  $('#u-lastLoginIp').html(mainPlatform.loginUser.lastLoginIp);
   	    			  $('#u-lastLoginTime').html(mainPlatform.loginUser.lastLoginTime);
   	    		  }
   	    	  },
   	    	  yes:function(index){
   	    		  layer.close(index);
   	    		  return;
   	    	  }
   	    	});
        });
        
        //修改当前登录用户密码
        $(document).on('click', '.pf-modify-pwd', function() {
        	 layer.open({
  	    	  type: 1,
  	    	  title: '修改用户信息',
  	    	  closeBtn: 1, //不显示关闭按钮
  	    	  btn:['确认', '取消'],
  	    	  shade: [0],
  	    	  area: ['400px', '300px'],
  	    	  offset: '100px', //右下角弹出
  	    	  content: $('#u-edit-user-pwd'), //iframe的url，no代表不显示滚动条
  	    	  yes:function(index){
  	    		 if(mainPlatform.loginUser){
  	    			 var oldPwd = $('#edit-user-pwd-form input[name=oldPwd]').val();
  	    			 var newPwd = $('#edit-user-pwd-form input[name=newPwd]').val();
  	    			 var commitPwd = $('#edit-user-pwd-form input[name=commitPwd]').val();
  	    			 if(newPwd != commitPwd){
  	    				 layer.alert('两次密码输入不一致');
  	    				 return;
  	    			 }
  	    			 var reqParams = {
  	    				'oldPwd' : oldPwd,
  	    				'newPwd' : newPwd,
  	    				'commitPwd' : commitPwd
  	    			 };
  	    			 layer.confirm('是否修改当前登录用户密码？', {icon: 3, title:'提示'}, function(cindex){
  	    				 layer.close(cindex);
  	    				 AJAX.sysmgr.sysUser200006(reqParams, function(data){
  	    					 if(data && data.results){
  	    						 layer.alert('修改成功');
  	    						 layer.close(index);
  	    					 }
  	    				 });
  	    			 });
  	    		  }
  	    	  },
  	    	  no:function(index){
  	    		  layer.close(index);
  	    		  return;
  	    	  }
        	 });
        });
        
        //退出登录
        $(document).on('click', '.pf-logout', function() {
            layer.confirm('您确定要退出吗？', {
              icon: 4,
			  title: '确定退出' //按钮
			}, function(){
				AJAX.sysmgr.sysUser200008(function(){
					location.href= 'login.html'; 
				});
			});
        });
	},

	render: function(menus){
		
		var menuMainTitle = "功能菜单";
		var current,
			html = '<h2 class="pf-model-name"><span class="iconfont"> </span><span class="pf-name">'+ menuMainTitle +'</span><span class="toggle-icon"></span></h2>';
		html += '<ul class="sider-nav">';
		if(menus && menus.length > 0){
			for(var i = 0; i < menus.length; i++){
				if(menus[i].isCurrent){
					html += '<li class="current" title="'+ menus[i].name +'">';
				}else{
					html += '<li title="'+ menus[i].name +'">';
				}
				html += '<a href="javascript:;"><span class="iconfont sider-nav-icon">' + menus[i].iconClass + '</span><span class="sider-nav-title">'+ menus[i].name +'</span><i class="iconfont">&#xe642;</i></a>';
				
				// 子菜单
				if(menus[i].childMenu && menus[i].childMenu.length > 0){
					var childs = menus[i].childMenu;
					html += '<ul class="sider-nav-s">';
					for(var j = 0; j < childs.length; j++){
						if(childs[j].isCurrent){
							current = childs[j];
							html += '<li class="active">';
						}else{
							html += '<li>';
						}
						html += '<a href="javascript:;" class="menu_a" data-id = "' + childs[j].id + '"data-href="'+ childs[j].url+'">' + childs[j].name + '</a></li>';
					}
					html += '</ul>';
				}
				html += '</li>';
			}
		}
		html += '</ul>';
		$('#pf-sider').html(html);
		$(document).on('click', '.menu_a', function() {
			var dataId = $(this).attr('data-id');
			var dataHref = $(this).attr('data-href');
			var title = $(this).html();
			
			if ($('.easyui-tabs1').tabs('exists', title)){
				$('.easyui-tabs1').tabs('select', title);
			} else {
				var content = '<iframe class="page-iframe" src="'+dataHref+'" frameborder="no" border="no" height="100%" width="100%" scrolling="auto"></iframe>';
				$('.easyui-tabs1').tabs('add',{
					title:title,
					content:content,
					closable:true
				});
				$('.panel-body').css('padding', '10px 5px 5px 10px');
			}
        });
	},
	//获取当前登录用户
	getLoginUser: function(){
		AJAX.sysmgr.sysUser200007(function(data){
			if(data.results){
				var loginUser = data.results;
				mainPlatform.loginUser = loginUser;
				$('#login-user-name').html(loginUser.nickName);
				$('#login-user-name').attr('data-id', loginUser.id);
			}
		});
	},
	//获取当前登录用户的菜单信息
	getLoginMenu: function(){
		AJAX.sysmgr.menu200001(function(data){
			if(data.results){
				mainPlatform.menus = data.results;
				mainPlatform.render(mainPlatform.menus);
			}
		});
	}
};

mainPlatform.init();