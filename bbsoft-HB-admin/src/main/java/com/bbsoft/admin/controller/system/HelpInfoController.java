package com.bbsoft.admin.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.admin.controller.BaseController;
import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.core.system.domain.HelpInfo;
import com.bbsoft.core.system.service.HelpInfoService;

/**
 * 官网帮助中心信息接口
 * @author Chris.Zhang
 * @date 2017-6-7 14:32:28
 *
 */
@Controller
public class HelpInfoController extends BaseController{

	@Autowired
	private HelpInfoService helpInfoService;
	
	/**
	 * 新增官网帮助中心信息
	 * @param memberNotes 会员须知
	 * @param memberNotesUrl 会员须知链接
	 * @param commonProblem 常见问题
	 * @param commonProblemUrl 常见问题链接
	 * @param aboutUs 关于我们
	 * @param aboutUsUrl 关于我们链接
	 * @return
	 */
	@RequestMapping("helpInfo200000")
	@ResponseBody
	public Json addHelpInfo(
			String memberNotes, String memberNotesUrl,
			String commonProblem, String commonProblemUrl,
			String aboutUs, String aboutUsUrl
			){
		HelpInfo helpInfo = new HelpInfo(memberNotes, memberNotesUrl, commonProblem, commonProblemUrl, aboutUs, aboutUsUrl);
		helpInfoService.addHelpInfo(helpInfo);
		return getSuccessObj();
	}
	
	/**
	 * 修改官网帮助中心信息
	 * @param id 唯一标识
	 * @param memberNotes 会员须知
	 * @param memberNotesUrl 会员须知链接
	 * @param commonProblem 常见问题
	 * @param commonProblemUrl 常见问题链接
	 * @param aboutUs 关于我们
	 * @param aboutUsUrl 关于我们链接
	 * @return
	 */
	@RequestMapping("helpInfo200001")
	@ResponseBody
	public Json updateHelpInfo(
			Long id, String memberNotes, String memberNotesUrl,
			String commonProblem, String commonProblemUrl,
			String aboutUs, String aboutUsUrl
			){
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		HelpInfo helpInfo = new HelpInfo(id, memberNotes, memberNotesUrl, commonProblem, commonProblemUrl, aboutUs, aboutUsUrl);
		helpInfoService.updateHelpInfo(helpInfo);
		return getSuccessObj();
	}
	
	/**
	 * 获取当前配置的官网帮助中心信息
	 * @return
	 */
	@RequestMapping("helpInfo200002")
	@ResponseBody
	public Json getHelpInfo(){
		HelpInfo helpInfo = helpInfoService.getShowHelp(null);
		if(helpInfo == null){
			return getSuccessObj();
		}else{
			return getSuccessObj(ResultUtil.returnByObj(helpInfo));
		}
	}
	
}
