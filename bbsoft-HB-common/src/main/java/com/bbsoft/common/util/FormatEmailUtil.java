/**  
 * @Title: FormatEmailUtil.java
 * @Package: com.bbsoft.common.util
 * @Description: TODO
 * @author: VULCAN
 * @date: 2017-2-9
 */
package com.bbsoft.common.util;

/**
 * ClassName: FormatEmailUtil 
 * @Description: 格式化邮件
 * @author: VULCAN
 * @date: 2017-2-9
 */
public class FormatEmailUtil {
	
	public static String validateCodeContent(String validateCode){
		StringBuffer sb = new StringBuffer();
		sb.append("")
				.append("<html>")
				.append("<head>")
				.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">")
				.append("<style type=\"text/css\">")
				.append(".test{font-family:\"Microsoft Yahei\";font-size: 18px;color: red;}")
				.append("</style>")
				.append("</head>")
				.append("<body>")
				.append("<p>您好，这封信是由 泰国伴游 发送的。</p>")
				.append("<p>您收到这封邮件，是由于这个邮箱地址在 泰国伴游 被登记为用户邮箱，且该用户请求使用 Email 密码重置功能所致。</p>")
				.append("<p>----------------------------------------------------------------------</p>")
				.append("<p>重要！</p>")
				.append("<p>----------------------------------------------------------------------</p>")
				.append("<p>如果您没有提交密码重置的请求或不是 泰国伴游 的注册用户，请立即忽略并删除这封邮件。只有在您确认需要重置密码的情况下，才需要继续阅读下面的内容。</p>")
				.append("<p>----------------------------------------------------------------------</p>")
				.append("<p>密码重置说明！</p>")
				.append("<p>----------------------------------------------------------------------</p>")
				.append("<p>您需在提交请求后的半小时内，输入下面的验证码：<span style=\"font-size: 25px;color: red\">"+validateCode+"</span></p>")
				.append("<p>在密码重置页面提交，您即可重置密码。</p>")
				.append("<p></p>")
				.append("<p>此致</p>")
				.append("<p>泰国伴游 管理团队.</p>")
				.append("http://www.bbsoft.con")
				.append("</body>")
				.append("</html>");
		return sb.toString();
	}

}
