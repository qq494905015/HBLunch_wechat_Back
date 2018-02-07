/**
 * 系统设置-常见问题
 * @author Chris.Zhang
 * @date 2017-6-6 14:55:55 
 */
var commonProblemModel = {
		init: function(){
			this.bindEvent();
			this.render();
			this.loadData();
		},

		bindEvent: function(){
			var self = this;
			$(document).on('click', '#search-btn', function() {
				var search = $("#search").val();
				commonProblemModel.params.search = search;
				commonProblemModel.loadData();
	        });
			
			$(document).on('click', '#reset-btn', function() {
				commonProblemModel.reset();
			});
			
			$(document).on('click', '#save-info-btn', function(){
				commonProblemModel.saveInfo();
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
	        	commonProblemModel.editIndex = layedit.build('layui_editor'); //建立编辑器
	        });
			
		},
		
		render: function(){
		},
		
		loadData:function(){
			AJAX.sysset.helpInfo200002(function(data){
				if(data && data.results){
					$('#layui_editor').val('');
			        if(typeof data.results.memberNotes !== 'undefined'){
			        	$('#layui_editor').val(data.results.commonProblem);
			        	$('#common-problem-url').val(data.results.commonProblemUrl);
			        	commonProblemModel.helpInfo = data.results;
			        }
				}
			});
		},
		
		saveInfo: function(){
			var commonProblem = layui.layedit.getContent(commonProblemModel.editIndex);
			var commonProblemUrl = $('#common-problem-url').val();
			var params = {
				'commonProblem' : commonProblem,
				'commonProblemUrl' : commonProblemUrl 
			}
			
			//修改
			if(typeof commonProblemModel.helpInfo !== 'undefined'){
				params.id = commonProblemModel.helpInfo.id;
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

commonProblemModel.init();