package com.bbsoft.wechat.controller.user;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.user.domain.UserBank;
import com.bbsoft.core.user.service.UserBankService;
import com.bbsoft.wechat.controller.BaseController;

/**
 * 用户银行卡号信息接口
 * @author Administrator
 *
 */
@Controller
public class UserBankController extends BaseController{

	@Autowired
	private UserBankService userBankService;
	
	/**
	 * 新增银行卡信息
	 * @param request
	 * @param bankNo 银行卡号
	 * @param realName 真实姓名
	 * @param bank 所属银行
	 * @param phone 联系电话
	 * @return
	 */
	@RequestMapping("userbank100000")
	@ResponseBody
	public Json adUserBank(HttpServletRequest request, 
			String bankNo, String realName, String bank,
			String phone){
		
		String userId = getUserId(request);
		if(StringUtil.isEmpty(bankNo) 
				|| StringUtil.isEmpty(realName) 
				|| StringUtil.isEmpty(bank)
				|| StringUtil.isEmpty(phone)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		
		UserBank userBank = new UserBank(userId, bankNo, realName, bank, phone, new Date(), "0");
		userBankService.addUserBank(userBank);
		return getSuccessObj();
	}
	
	/**
	 * 修改银行卡信息
	 * @param request
	 * @param id 银行卡记录ID
	 * @param bankNo 卡号
	 * @param realName 真实姓名
	 * @param bank 所属银行
	 * @param phone 联系方式
	 * @return
	 */
	@RequestMapping("userbank100001")
	@ResponseBody
	public Json updateUserBank(HttpServletRequest request,
			Long id, String bankNo, String realName, 
			String bank, String phone){
		String userId = getUserId(request);
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		UserBank userBank = new UserBank(id, userId, bankNo, realName, bank, phone, new Date(), null);
		userBankService.updateUserBank(userBank);
		return getSuccessObj();
	}
	
	/**
	 * 删除银行卡信息
	 * @param request
	 * @param id 银行卡记录ID
	 * @return
	 */
	@RequestMapping("userbank100002")
	@ResponseBody
	public Json deleteUserBank(HttpServletRequest request,
			Long id){
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		userBankService.deleteUserBank(id);
		return getSuccessObj();
	}
	
	/**
	 * 获取指定银行卡信息
	 * @param request
	 * @param id 银行卡记录ID
	 * @return
	 */
	@RequestMapping("userbank100003")
	@ResponseBody
	public Json getUserBankById(HttpServletRequest request,
			Long id){
		if(id == null){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode());
		}
		UserBank userBank = userBankService.getUserBankById(id, true);
		return getSuccessObj(ResultUtil.returnByObj(userBank));
	}
	
	/**
	 * 获取当前登录用户的银卡信息列表
	 * @param request
	 * @return
	 */
	@RequestMapping("userbank100004")
	@ResponseBody
	public Json getUserBanks(HttpServletRequest request){
		String userId = getUserId(request);
		List<UserBank> banks = userBankService.getListByUser(userId);
		return getSuccessObj(ResultUtil.returnByList(banks));
	}
}
