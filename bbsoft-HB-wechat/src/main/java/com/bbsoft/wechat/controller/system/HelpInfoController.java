package com.bbsoft.wechat.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.common.constant.Json;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.core.system.domain.HelpInfo;
import com.bbsoft.core.system.service.HelpInfoService;
import com.bbsoft.wechat.controller.BaseController;

/**
 * 帮助中心接口
 * @author Chris.Zhang
 * @date 2017-6-19 16:46:06
 */
@Controller
public class HelpInfoController extends BaseController{

	@Autowired
	private HelpInfoService helpInfoService;
	
	/**
	 * 获取帮助中心配置信息
	 * @return
	 */
	@RequestMapping("help100000")
	@ResponseBody
	public Json getHelpInfo(){
		HelpInfo helpInfo = helpInfoService.getShowHelp("0");
		return getSuccessObj(ResultUtil.returnByObj(helpInfo));
	}
	
}
