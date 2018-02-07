/**
 * 登录模块
 */
var loginModel = {
		init: function(){
			this.bindEvent();
			this.render();
		},

		bindEvent: function(){
			var self = this;
			
			$(document).on('change', '#userName,#password', function(){
				if($(this).val() != ''){
					$("#valida-error").hide();
				}
			});
			
			$("#login-btn").keydown(function(event){
				if(event.keyCode==13){
					$("#login-btn").click();
				};
			});
			
			// 点击登录
			$(document).on('click', '#login-btn', function() {
				var userName = $("#userName").val();
				var password = $("#password").val();
				var code = $("#code").val();
				if(userName == '' || password == ''){
					$("#valida-error").show();
					return;
				}
				var reqParams = {
					'userName' : userName,
					'password' : password,
					'code' : code
				}
				//http://119.29.139.49:9999/bbsoft-HB-admin/sysUser200001.do
				$.ajax({
					url:'sysUser200001.do',
					async:false,
					dataType:'json',
					type:'POST',
					data:reqParams,
					success:function(data){
						if(data && data.errorCode == '0'){
							window.location.href = "main.html";
						}else{
							$('#kaptchaImg').attr('src','cap100000.do?t=' + Math.random())
							layer.alert(data.errorMsg);
						}
					}
				});
	        });
		},

		render: function(){

		}
}

loginModel.init();