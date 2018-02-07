package com.bbsoft.core.system.service.impl;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.common.wechat.WeChatConfig;
import com.bbsoft.common.wechat.utils.WeMenuUtil;
import com.bbsoft.core.system.domain.WeMenu;
import com.bbsoft.core.system.mapper.WeMenuMapper;
import com.bbsoft.core.system.service.WeMenuService;

import net.sf.json.JSONObject;

@Service
public class WeMenuServiceImpl implements WeMenuService {
	
	@Autowired
	private WeMenuMapper weMenuMapper;
	private Logger logger = LoggerFactory.getLogger(getClass());

	public int addWeMenu(WeMenu weMenu) {
		if(weMenu == null 
			|| StringUtil.isEmpty(weMenu.getIsAuth())
			|| StringUtil.isEmpty(weMenu.getName())
			|| weMenu.getParentId() == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		int childNum = weMenuMapper.selectCountByParent(weMenu.getParentId()); 
		if(childNum >= 5){
			throw new ServiceException(MsgeData.WECHAT_1000050001.getCode());
		}
		
		if(weMenu.getParentId() == 0){
			int rootNum = weMenuMapper.selectCountByParent(0L);
			if(rootNum >= 3){
				throw new ServiceException(MsgeData.WECHAT_1000050002.getCode());
			}
		}else{
			if(StringUtil.isEmpty(weMenu.getUrl())){
				throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
			}
		}
		if(weMenu.getCreateTime() == null){
			weMenu.setCreateTime(new Date());
		}
		return weMenuMapper.insertWeMenu(weMenu);
	}

	public int updateWeMenu(WeMenu weMenu) {
		if(weMenu == null
			|| weMenu.getId() == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return weMenuMapper.updateWeMenu(weMenu);
	}

	public List<WeMenu> getAll() {
		return weMenuMapper.selectWeMenus();
	}

	public int deleteAll() {
		return weMenuMapper.deleteAll();
	}

	public int deleteWeMenu(Long id) {
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		return weMenuMapper.deleteWeMenu(id);
	}

	@SuppressWarnings("deprecation")
	public JSONObject updateToWechat() {
		List<WeMenu> menus = weMenuMapper.selectWeMenus();
		JSONObject jsonObject = null;
		if(menus != null && menus.size() > 0){
			Map<String, Object> menuMap = new HashMap<String, Object>();
			List<Map<String, Object>> button = new ArrayList<Map<String,Object>>();
			//一级菜单
			for(WeMenu menu : menus){
				if(menu.getParentId() == 0 && !StringUtil.isEmpty(menu.getName())){
					Map<String, Object> item = new HashMap<String, Object>();
					item.put("name", menu.getName());
					List<Map<String, Object>> itemBtns = new ArrayList<Map<String,Object>>();
					//二级菜单
					for(WeMenu menu1 : menus){
						if(menu1.getParentId().equals(menu.getId()) && !StringUtil.isEmpty(menu1.getName()) && !StringUtil.isEmpty(menu1.getUrl())){
							Map<String, Object> itemButton = new HashMap<String,Object>();
							itemButton.put("type", "view");
							itemButton.put("name", menu1.getName());
							if("1".equals(menu1.getIsAuth())){
								menu1.setUrl(URLEncoder.encode(menu1.getUrl()));
//								String domain = "http://wx.wan-we.com/HeBenWechat#/";
								String redirectUri = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + WeChatConfig.APP_ID 
									+ "&redirect_uri=CUS_URL&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
								itemButton.put("url", redirectUri.replace("CUS_URL", menu1.getUrl()));
							}else{
								itemButton.put("url", menu1.getUrl());
							}
							itemBtns.add(itemButton);
						}
					}
					item.put("sub_button", itemBtns);
					button.add(item);
				}
			}
			menuMap.put("button", button);
			logger.debug("参数：{}", menuMap);
			WeMenuUtil.remove();
			jsonObject = WeMenuUtil.create(menuMap);
		}
		return jsonObject;
	}

	public int updateMenus(List<WeMenu> menus) {
		int result = 0;
		if(menus != null && menus.size() > 0){
			for(WeMenu menu : menus){
				result = weMenuMapper.updateWeMenu(menu);
			}
		}
		return result;
	}
}
