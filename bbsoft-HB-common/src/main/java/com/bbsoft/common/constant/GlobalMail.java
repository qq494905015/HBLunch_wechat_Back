package com.bbsoft.common.constant;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.bbsoft.common.domain.ServiceException;

/**
 * 
 * ClassName: GlobalMail 
 * @Description: 邮箱配置项
 * @author: VULCAN
 * @date: 2017-2-9
 */
public class GlobalMail implements Serializable{
	 
	private static Logger logger = LoggerFactory.getLogger(GlobalMail.class);
	private static final long serialVersionUID = 11563731495348493L;
	
	public static final String PREFIX = "sys_mail_";
	public static final String MAIL_SMTP_HOST = PREFIX + "smtpHost";
	public static final String MAIL_SMTP_PORT = PREFIX + "smtpPort";
	public static final String MAIL_SMTP_AUTH = PREFIX + "smtpAuth";
	public static final String MAIL_SMTP_SSL = PREFIX + "smtpSsl";
	public static final String MAIL_SMTP_TIMEOUT = PREFIX + "smtpTimeout";
	public static final String MAIL_SMTP_USERNAME = PREFIX + "smtpUsername";
	public static final String MAIL_SMTP_PASSWORD = PREFIX + "smtpPassword";
	public static final String MAIL_FROM = PREFIX + "from";
	public static final String MAIL_TEST_TO = PREFIX + "testTo";
	public static final String MAIL_TEST_SUBJTCT = PREFIX + "testSubject";
	public static final String MAIL_TEST_TEXT = PREFIX + "testText";
	public static final String MAIL_PIC_NAME="二维码.png";

	private Map<String, String> customs;

	public GlobalMail() {
	}

	public GlobalMail(Map<String, String> customs) {
		this.customs = customs;
	}
	
	public void init(String host,String from,String username,String pwd){
		HashMap<String, String> custom=new HashMap<String, String>();
		custom.put(MAIL_SMTP_HOST, host);
		custom.put(MAIL_SMTP_AUTH, "true");
		custom.put(MAIL_FROM, from);
		custom.put(MAIL_SMTP_USERNAME, username);
		custom.put(MAIL_SMTP_PASSWORD, pwd);
		custom.put(MAIL_SMTP_PORT, "465");//25端口不需要ssl校验
		custom.put(MAIL_SMTP_SSL, "true");
		custom.put(MAIL_SMTP_TIMEOUT, "5000");
		this.customs = custom;
	}

	public void sendMail(String[] to, String subject, String text, File file) {
		logger.debug("开始发送邮件=======================================");
		String smtpHost = getSmtpHost();
		if (StringUtils.isBlank(smtpHost)) {
			return;
		}
		Properties prop = new Properties();
		Boolean smtpAuth = getSmtpAuth();
		if (smtpAuth != null) {
			prop.put("mail.smtp.auth", smtpAuth.toString());
		}
		Boolean smtpSsl = getSmtpSsl();
		if (smtpSsl != null && smtpSsl) {
			prop.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		}
		Integer smtpTimeout = getSmtpTimeout();
		if (smtpTimeout != null) {
			prop.put("mail.smtp.timeout", smtpTimeout.toString());
		}

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setDefaultEncoding("UTF-8");
		mailSender.setJavaMailProperties(prop);
		mailSender.setHost(smtpHost);
		Integer port = getSmtpPort();
		if (port != null) {
			mailSender.setPort(port);
		}
		mailSender.setUsername(getSmtpUsername());
		mailSender.setPassword(getSmtpPassword());
		MimeMessage msg = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
			helper.setFrom(getFrom());
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text, true);	
			//是否存在附件
			if(file != null){
				try {
					helper.addAttachment(MimeUtility.encodeWord(file.getName()), file);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					logger.debug("邮件添加附件失败，不支持的类型.....");
					throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
				}
			}
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		mailSender.send(msg);
		logger.debug("结束发送邮件=======================================");
	}
	
	

	public String getSmtpHost() {
		return getCustoms().get(MAIL_SMTP_HOST);
	}

	public void setSmtpHost(String smtpHost) {
		if (StringUtils.isNotBlank(smtpHost)) {
			getCustoms().put(MAIL_SMTP_HOST, smtpHost);
		} else {
			getCustoms().remove(MAIL_SMTP_HOST);
		}
	}

	public Integer getSmtpPort() {
		String smtpPort = getCustoms().get(MAIL_SMTP_PORT);
		if (StringUtils.isNotBlank(smtpPort)) {
			return Integer.parseInt(smtpPort);
		} else {
			return null;
		}
	}

	public void setSmtpPort(Integer smtpPort) {
		if (smtpPort != null) {
			getCustoms().put(MAIL_SMTP_PORT, smtpPort.toString());
		} else {
			getCustoms().remove(MAIL_SMTP_PORT);
		}
	}

	public Boolean getSmtpAuth() {
		String smtpAuth = getCustoms().get(MAIL_SMTP_AUTH);
		if (StringUtils.isNotBlank(smtpAuth)) {
			return Boolean.parseBoolean(smtpAuth);
		} else {
			return null;
		}
	}

	public void setSmtpAuth(Boolean smtpAuth) {
		if (smtpAuth != null) {
			getCustoms().put(MAIL_SMTP_AUTH, smtpAuth.toString());
		} else {
			getCustoms().remove(MAIL_SMTP_AUTH);
		}
	}

	public Boolean getSmtpSsl() {
		String smtpSsl = getCustoms().get(MAIL_SMTP_SSL);
		if (StringUtils.isNotBlank(smtpSsl)) {
			return Boolean.parseBoolean(smtpSsl);
		} else {
			return null;
		}
	}

	public void setSmtpSsl(Boolean smtpSsl) {
		if (smtpSsl != null) {
			getCustoms().put(MAIL_SMTP_SSL, smtpSsl.toString());
		} else {
			getCustoms().remove(MAIL_SMTP_SSL);
		}
	}

	public Integer getSmtpTimeout() {
		String smtpTimeout = getCustoms().get(MAIL_SMTP_TIMEOUT);
		if (StringUtils.isNotBlank(smtpTimeout)) {
			return Integer.decode(smtpTimeout);
		} else {
			return null;
		}
	}

	public void setSmtpTimeout(Integer smtpTimeout) {
		if (smtpTimeout != null) {
			getCustoms().put(MAIL_SMTP_TIMEOUT, smtpTimeout.toString());
		} else {
			getCustoms().remove(MAIL_SMTP_TIMEOUT);
		}
	}

	public String getFrom() {
		return getCustoms().get(MAIL_FROM);
	}

	public void setFrom(String from) {
		if (StringUtils.isNotBlank(from)) {
			getCustoms().put(MAIL_FROM, from);
		} else {
			getCustoms().remove(MAIL_FROM);
		}
	}

	public String getSmtpUsername() {
		return getCustoms().get(MAIL_SMTP_USERNAME);
	}

	public void setSmtpUsername(String username) {
		if (StringUtils.isNotBlank(username)) {
			getCustoms().put(MAIL_SMTP_USERNAME, username);
		} else {
			getCustoms().remove(MAIL_SMTP_USERNAME);
		}
	}

	public String getSmtpPassword() {
		return getCustoms().get(MAIL_SMTP_PASSWORD);
	}

	public void setSmtpPassword(String password) {
		if (StringUtils.isNotBlank(password)) {
			getCustoms().put(MAIL_SMTP_PASSWORD, password);
		} else {
			getCustoms().remove(MAIL_SMTP_PASSWORD);
		}
	}

	public String getTestTo() {
		return getCustoms().get(MAIL_TEST_TO);
	}

	public void setTestTo(String testTo) {
		if (StringUtils.isNotBlank(testTo)) {
			getCustoms().put(MAIL_TEST_TO, testTo);
		} else {
			getCustoms().remove(MAIL_TEST_TO);
		}
	}

	public String getTestSubject() {
		return getCustoms().get(MAIL_TEST_SUBJTCT);
	}

	public void setTestSubject(String testSubject) {
		if (StringUtils.isNotBlank(testSubject)) {
			getCustoms().put(MAIL_TEST_SUBJTCT, testSubject);
		} else {
			getCustoms().remove(MAIL_TEST_SUBJTCT);
		}
	}

	public String getTestText() {
		return getCustoms().get(MAIL_TEST_TEXT);
	}

	public void setTestText(String testText) {
		if (StringUtils.isNotBlank(testText)) {
			getCustoms().put(MAIL_TEST_TEXT, testText);
		} else {
			getCustoms().remove(MAIL_TEST_TEXT);
		}
	}

	public Map<String, String> getCustoms() {
		if (customs == null) {
			customs = new HashMap<String, String>();
		}
		return customs;
	}

	public void setCustoms(Map<String, String> customs) {
		this.customs = customs;
	}

	public String getPrefix() {
		return PREFIX;
	}
}
