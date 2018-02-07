/**
 * 系统设置-会员须知
 * @author Chris.Zhang
 * @date 2017-6-6 14:55:55 
 */
var memberNotesModel = {
		init: function(){
			this.bindEvent();
			this.render();
			this.loadData();
		},

		bindEvent: function(){
			var self = this;
			$(document).on('click', '#search-btn', function() {
				var search = $("#search").val();
				memberNotesModel.params.search = search;
				memberNotesModel.loadData();
	        });
			
			$(document).on('click', '#reset-btn', function() {
				memberNotesModel.reset();
			});
			
			$(document).on('click', '#save-info-btn', function(){
				memberNotesModel.saveInfo();
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
	        	memberNotesModel.editIndex = layedit.build('layui_editor'); //建立编辑器
	        });
			
		},
		
		render: function(){
		},
		
		loadData:function(){
			AJAX.sysset.helpInfo200002(function(data){
				if(data && data.results){
					$('#layui_editor').val('');
			        if(typeof data.results.memberNotes !== 'undefined'){
			        	$('#layui_editor').val(data.results.memberNotes);
			        	$('#member-notes-url').val(data.results.memberNotesUrl);
			        	memberNotesModel.helpInfo = data.results;
			        }
				}
			});
		},
		
		saveInfo: function(){
			var memberNotes = layui.layedit.getContent(memberNotesModel.editIndex);
			var memberNotesUrl = $('#member-notes-url').val();
			var params = {
				'memberNotes' : memberNotes,
				'memberNotesUrl' : memberNotesUrl 
			}
			
			//修改
			if(typeof memberNotesModel.helpInfo !== 'undefined'){
				params.id = memberNotesModel.helpInfo.id;
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

memberNotesModel.init();