/**
 * 系统设置-关于我们
 * @author Chris.Zhang
 * @date 2017-6-6 14:55:55 
 */
var aboutUsModel = {
		init: function(){
			this.bindEvent();
			this.render();
			this.loadData();
		},

		bindEvent: function(){
			var self = this;
			$(document).on('click', '#search-btn', function() {
				var search = $("#search").val();
				aboutUsModel.params.search = search;
				aboutUsModel.loadData();
	        });
			
			$(document).on('click', '#reset-btn', function() {
				aboutUsModel.reset();
			});
			
			$(document).on('click', '#save-info-btn', function(){
				aboutUsModel.saveInfo();
			});
			//加载layui form
	        layui.use('form', function(){
	        	var form = layui.form(); //只有执行了这一步，部分表单元素才会修饰成功
			});
	          
	       
	        layui.use('layedit', function(){
	        	var layedit = layui.layedit;
	        	layedit.set({
	        		uploadImage: {
	        		    url: HOST_URL + "/upload100001.do"
	        		  }
	        	});
	        	aboutUsModel.editIndex = layedit.build('layui_editor'); //建立编辑器
	        });
			
		},
		
		render: function(){
		},
		
		loadData:function(){
			AJAX.sysset.helpInfo200002(function(data){
				if(data && data.results){
					$('#layui_editor').val('');
			        if(typeof data.results.memberNotes !== 'undefined'){
			        	$('#layui_editor').val(data.results.aboutUs);
			        	$('#about-us-url').val(data.results.aboutUsUrl);
			        	aboutUsModel.helpInfo = data.results;
			        }
				}
			});
		},
		
		saveInfo: function(){
			var aboutUs = layui.layedit.getContent(aboutUsModel.editIndex);
			var aboutUsUrl = $('#about-us-url').val();
			var params = {
				'aboutUs' : aboutUs,
				'aboutUsUrl' : aboutUsUrl 
			}
			
			//修改
			if(typeof aboutUsModel.helpInfo !== 'undefined'){
				params.id = aboutUsModel.helpInfo.id;
				AJAX.sysset.helpInfo200001(params, function(data){
					if(data && data.results){
						layer.alert('保存成功');
						location.reload();
					}
				});
			}else{//新增
				AJAX.sysset.helpInfo200000(params, function(data){
					if(data && data.results){
						layer.alert('保存成功');
						location.reload();
					}
				});
			}
			
		}
		
}

aboutUsModel.init();