package com.bbsoft.wechat;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.bbsoft.common.wechat.autoReply.MessageResponse;
import com.bbsoft.common.wechat.robot.TulingApiProcess;
import com.bbsoft.common.wechat.utils.MessageUtil;
import com.bbsoft.common.wechat.utils.WeMenuUtil;
import com.bbsoft.core.user.domain.WechatUser;
import com.bbsoft.core.user.service.WechatUserService;

public class WechatCoreService {
	
	static Logger logger = Logger.getLogger(WechatCoreService.class);
	
	@Autowired
	private WechatUserService wechatUserService;
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析
			// 调用消息工具类MessageUtil解析微信发来的xml格式的消息，解析的结果放在HashMap里；
			Map<String, String> requestMap = MessageUtil.parseXml(request);

			// 从HashMap中取出消息中的字段；
			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 消息内容
			String content = requestMap.get("Content");
			// 从HashMap中取出消息中的字段；
			
			logger.info("fromUserName is:" +fromUserName+" toUserName is:" +toUserName+" msgType is:" +msgType);

			// 文本消息
			if (MessageUtil.REQ_MESSAGE_TYPE_TEXT.equals(msgType)) {
				//微信聊天机器人测试 2015-3-31
				if(content!=null){
					respContent = TulingApiProcess.getTulingResult(content);
					if(respContent==""||null==respContent){
						MessageResponse.getTextMessage(fromUserName , toUserName , "服务号暂时无法回复，请稍后再试！");
					}
					//return FormatXmlProcess.formatXmlAnswer(toUserName, fromUserName, respContent);
					return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
				}
//				else if (content.startsWith("ZY")) {//查作业
//					String keyWord = content.replaceAll("^ZY" , "").trim();
//					if ("".equals(keyWord)) {
//						respContent = AutoReply.getXxtUsage("24");
//					} else {
//						return XxtService.getHomework("24" , fromUserName , toUserName , keyWord);
//					}
//				} else if (content.startsWith("SJX")) {//收件箱
//					String keyWord = content.replaceAll("^SJX" , "").trim();
//					if ("".equals(keyWord)) {
//						respContent = AutoReply.getXxtUsage("25");
//					} else {
//						return XxtService.getRecvBox("25" , fromUserName , toUserName , keyWord);
//					}
//				}
//				return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
			} else if (MessageUtil.REQ_MESSAGE_TYPE_EVENT.equals(msgType)) {// 事件推送
				String eventType = requestMap.get("Event");// 事件类型
				
				if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {// 订阅
					respContent = "您好！欢迎关注";
					WechatUser wechatUser = new WechatUser();
					wechatUser.setCreateTime(new Date());
					wechatUser.setOpenId(fromUserName);
//					wechatUserService.addWechatUser(wechatUser);
					
					return MessageResponse.getTextMessage(fromUserName , toUserName , respContent);
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {// 取消订阅
					// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
				} else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {// 自定义菜单点击事件
					String eventKey = requestMap.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
					logger.info("eventKey is:" +eventKey);
					return WeMenuUtil.getClickResponse(eventKey , fromUserName , toUserName);
				} else if(MessageUtil.REQ_MESSAGE_TYPE_LOCATION.equals(eventType)){//上报地理位置
					// 经度
					String latitude = requestMap.get("Latitude");
					String longitude = requestMap.get("Longitude");
					WechatUser wechatUser = new WechatUser();
					wechatUser.setOpenId(fromUserName);
					wechatUser.setLat(latitude);
					wechatUser.setLng(longitude);
//					wechatUserService.addWechatUser(wechatUser);
				}
			}
			
			//开启微信声音识别测试 2015-3-30
			else if(MessageUtil.REQ_MESSAGE_TYPE_VOICE.equals(msgType))
			{
				String recvMessage = requestMap.get("Recognition");
				//respContent = "收到的语音解析结果："+recvMessage;
				if(recvMessage!=null){
					respContent = TulingApiProcess.getTulingResult(recvMessage);
				}else{
					respContent = "您说的太模糊了，能不能重新说下呢？";
				}
				return MessageResponse.getTextMessage(fromUserName , toUserName , respContent); 
			}
			//拍照功能
			else if("pic_sysphoto".equals(msgType))
			{
				
			}
			else
			{
				return MessageResponse.getTextMessage(fromUserName , toUserName , "返回为空"); 
			}
			
			
			
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}
}
