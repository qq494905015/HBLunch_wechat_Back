/*
	获取键盘按键值e == event
*/
function keypress(e) {
	e = window.event || e;
	var keyCode = e.keyCode || e.which;
	return keyCode;
}

/**
 * 赋值表单
 * @param form 表单ID
 * @param obj 赋值对象
 */
function setFormValue(form, obj){
	if(typeof obj !== "undefined"){
		$("#" + form + " input").each(function(index){
			if($(this).attr("type") == "text"){
				$(this).val(obj[$(this).attr("name")]);
			}
		});
	}
}

/**
 * 清空表单
 * @param form 表单ID
 */
function clearFormValue(form){
	$('#' + form).form('reset');
////	$('#' + form + " input").each(function(index){
////		if($(this).attr("type") == "text"){
////			$(this).val('');
////		}
////	});
//	$('#' + form + " select").each(function(index){
//		var oneOption = $($(this).children('option')[0]);
//		$('#' + $(this).attr('id')).combobox('setValue', oneOption.val())
////		$('#' + form + ' input[name=' + $(this).attr('name') + ']').val(oneOption.val());
//	});
}

/**
 * 给下拉框赋值
 * @param id 下拉框标识
 * @param array 
 */
function createSelect(id, array, selected){
     var defaultArray = [{"value" : 0, "text" : "请选择"}];
	 if(typeof id === "undefined" || id == ""){
		 return;
	 }else{
		if(typeof array === "undefined" || array == null){
			array = defaultArray
		}else{
			if(typeof array === "Array"){
				if(arrayl.length == 0){
					array = defaultArray
				}
			}
		}
		
		var select = $("#" + id);
		for(var i = 0; i < array.length; i++){
			var option = $("<option></option>");
			option.val(array[i].value);
			option.text(array[i].text);
			if(typeof selected !== "undefined" && selected == array[i].value){
				option.attr("selected", "selected");
			}
			option.appendTo(select);
		}
	 }
	 
}

/**
 * 校验是否为金额数字
 * @param v
 * @returns {Boolean}
 */
function checkIsMoney(v){
	var a= /^[0-9]*(\.[0-9]{1,2})?$/;
	if(!a.test(v)){
		return false;
	}else{
		return true;
	}
}






/**********************公共方法****************************/

/**
 * 截取访问地址栏后的参数，传入参数则直接取参数解析
 * @param url 路径地址
 * @returns {Object}
 */
function getParameters(url) { 
	if(typeof url === 'undefined') {
		url = decodeURI(location.search);//取访问地址url?后的部分
	}else{
		if(isNumber(url)) {
			return url;
		}
	}
	var obj = new Object();	//返回对象
	var str;	//截取后的字符串
	if(url.charAt(0) == "?") {
		url = url.substring(1, url.length);
		//两个参数以上的情况
		if(url.indexOf("&") != -1) {
			str = url.split("&");
			for(var i = 0; i < str.length; i++) {
				var str1 = str[i].split("=");
				if(str1.length == 2 && str1[0] != '') {
					obj[str1[0]] = str1[1];
				}				
			}
		} 
		//一个参数的情况
		else {
			str = url.split("=");
			if(str.length == 2 && str[0] != '') {
				obj[str[0]] = str[1];
			}			
		}
	}
	return obj;
}