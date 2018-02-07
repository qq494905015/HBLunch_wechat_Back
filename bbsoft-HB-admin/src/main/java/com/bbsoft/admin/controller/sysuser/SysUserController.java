package com.bbsoft.admin.controller.sysuser;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.admin.controller.BaseController;
import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.BeanToMapUtil;
import com.bbsoft.common.util.EncryUtil;
import com.bbsoft.common.util.PageUtil;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.core.sysuser.domain.SysUser;
import com.bbsoft.core.sysuser.service.SysUserService;
import com.google.code.kaptcha.Constants;

/**
 * 后台系统用户接口
 * @author Chris.Zhang
 * @date 2017年2月28日
 */
@Controller
public class SysUserController extends BaseController{

	private static final String EDIT_SYSUSER= "system/sysuser/sysuser_edit";
	@Autowired
	private SysUserService sysUserService;
	
	/**
	 * 新增用户信息
	 * @param userName 用户登录账号
	 * @param nickName 用户昵称
	 * @param password 用户密码
	 * @param email 邮箱
	 * @param phone 手机号 
	 * @param status 状态 状态  是否禁用(1:禁用 0：启用)
	 * @param roleId 角色ID
	 * @return
	 */
	@RequestMapping(value = "sysUser200000")
	@ResponseBody
	public Json addSysUser(
			String userName, String nickName, String password, 
			String email, String phone, String status, String roleId){
		SysUser sysUser = new SysUser(userName, nickName, password, email, phone, status, roleId);
		//更新用户信息
		sysUserService.addUser(sysUser);
		return getSuccessObj();
	}
	
	/**
	 * 后台管理员用户登录
	 * @param userName 账号号
	 * @param password 登录密码
	 * @return
	 */
	@RequestMapping(value = "sysUser200001")
	@ResponseBody
	public Json login(HttpServletRequest request, String userName, String password, String code){
		if(StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)){
			throw new ServiceException(MsgeData.PUBLIC_1000010010.getCode());
		}
		if(StringUtil.isEmpty(code)){
			throw new ServiceException(MsgeData.PUBLIC_1000010013.getCode());
		}
		Object sessionCode = request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if(!code.equals(sessionCode)){
			throw new ServiceException(MsgeData.PUBLIC_1000010005.getCode());
		}else{
			request.getSession().removeAttribute(Constants.KAPTCHA_SESSION_KEY);
		}
		if(password.length() < 6){
			throw new ServiceException(MsgeData.PUBLIC_1000010012.getCode());
		}
		SysUser sysUser = sysUserService.getUserByLogin(userName, EncryUtil.md5s(password));
		if(sysUser == null){
			throw new ServiceException(MsgeData.USER_1000020003.getCode());
		}
		sysUser.setLastLoginTime(new Date());
		sysUser.setPassword(null);
		sysUserService.updateUser(sysUser);
		//存入Session
		pushSession(request, sysUser);
		
		Object result = ResultUtil.returnByObj(sysUser); 
		return getSuccessObj(result);
	}
	
	/**
	 * 分页查询系统管理信息
	 * @param request
	 * @param pageNum 当前页
	 * @param pageSize 页大小
	 * @param search 关键字搜索条件
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "sysUser200002")
	@ResponseBody
	public Json getSysUserByPage(Integer pageNum,Integer pageSize,String search,HttpServletRequest request){
		if(StringUtil.isEmpty(search)){
			search = null;
		}
		String id = getSessionId(request);
		int total = sysUserService.getUserCount(id, search);
		PageUtil<Map> pageMap = new PageUtil<Map>(pageNum, pageSize, total);
		List<SysUser> users = sysUserService.getUserByPage(pageMap.getStartItem(), pageMap.getPageSize(), id, search);
		pageMap.setItems(BeanToMapUtil.convertList(users));
//		DataGrid dg = new DataGrid();
//		dg.setTotal((long)total);
//		dg.setRows(BeanToMapUtil.convertList(users, false));
//		return dg;
		return getSuccessObj(ResultUtil.returnByObj(pageMap));
	}
	
	/**
	 * 获取指定系统管理员信息
	 * @param id 管理员ID
	 * @return
	 */
	@RequestMapping(value = "sysUser200003")
	@ResponseBody
	public Json getSysUserById(String id){
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10103.getCode());
		}
		SysUser sysUser = sysUserService.getUserById(id,true);
		if(sysUser == null){
			throw new ServiceException(MsgeData.USER_1000020003.getCode());
		}
		Object result = ResultUtil.returnByObj(sysUser);
		return getSuccessObj(result);
	}
	
	/**
	 * 更新用户信息
	 * @param id 用户ID
	 * @param userName 用户登录账号
	 * @param nickName 用户昵称
	 * @param password 用户密码
	 * @param email 邮箱
	 * @param phone 手机号 
	 * @param status 状态 状态  是否禁用(1:禁用 0：启用)
	 * @param roleId 角色ID
	 * @return
	 */
	@RequestMapping(value = "sysUser200004")
	@ResponseBody
	public Json updateSysUser(
			String id, String userName, String nickName, String password, 
			String email, String phone, String status, String roleId){
		SysUser sysUser = new SysUser(id, userName, nickName, password, email, phone, status, roleId);
		//更新用户信息
		sysUserService.updateUser(sysUser);
		return getSuccessObj();
	}
	
	/**
	 * 删除用户信息
	 * @param request
	 * @param id 用户ID
	 * @return
	 */
	@RequestMapping(value = "sysUser200005")
	@ResponseBody
	public Json deleteSysUser(HttpServletRequest request, String id){
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10103.getCode());
		}
		sysUserService.deleteUser(id);
		return getSuccessObj();
	}
	
	/**
	 * 修改当前登录用户的密码
	 * @param request
	 * @param oldPwd 旧密码
	 * @param newPwd 新密码
	 * @param commitPwd 确认新密码
	 * @return
	 */
	@RequestMapping(value = "sysUser200006")
	@ResponseBody
	public Json updatePwd(HttpServletRequest request, String oldPwd, String newPwd, String commitPwd){
		String id = getSessionId(request);
		if(StringUtil.isEmpty(oldPwd) || StringUtil.isEmpty(newPwd) || StringUtil.isEmpty(commitPwd)){
			throw new ServiceException(MsgeData.SYSTEM_10104.getCode(), 
					MsgeData.SYSTEM_10104.getMsg() + 
					"【id: " + id + "," + 
					"oldPwd: " + oldPwd + "," + 
					"newPwd: " + newPwd + "," + 
					"commitPwd: " + commitPwd + "】" 
					);
		}
		if(oldPwd.length() < 6 || newPwd.length() < 6 || commitPwd.length() < 6){
			throw new ServiceException(MsgeData.PUBLIC_1000010012.getCode());
		}
		SysUser sysUser = sysUserService.getUserById(id,false);
		if(sysUser == null){
			throw new ServiceException(MsgeData.USER_1000020003.getCode());
		}
		//旧密码验证
		if(!sysUser.getPassword().equals(EncryUtil.md5s(oldPwd))){
			throw new ServiceException(MsgeData.PUBLIC_1000010014.getCode());
		}
		//两次输入密码验证
		if(!EncryUtil.md5s(newPwd).equals(EncryUtil.md5s(commitPwd))){
			throw new ServiceException(MsgeData.PUBLIC_1000010015.getCode());
		}
		sysUser.setPassword(commitPwd);
		sysUserService.updateUser(sysUser);
		return getSuccessObj();
	}
	
	/**
	 * 获取当前登录的用户信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "sysUser200007")
	@ResponseBody
	public Json getCurrUser(HttpServletRequest request){
		String userId = getSessionId(request);
		SysUser sysUser = sysUserService.getUserById(userId,false);
		return getSuccessObj(ResultUtil.returnByObj(sysUser));
	}
	
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "sysUser200008")
	@ResponseBody
	public Json logout(HttpServletRequest request){
		clearSession(request);
		return getSuccessObj();
	}
	
	@RequestMapping(value = "/sysUser200009")
	public String userEdit(String id, ModelMap modelMap) {
		if(StringUtil.isEmpty(id)){
			throw new ServiceException(MsgeData.SYSTEM_10103.getCode());
		}
		SysUser sysUser = sysUserService.getUserById(id,true);
		modelMap.addAttribute("bean", ResultUtil.returnByObj(sysUser));
		return EDIT_SYSUSER;
	}
	
	/**
	 * @Description: 首页
	 * @author: VULCAN
	 * @date: 2017-4-26
	 */
	@RequestMapping(value = "index")
	public String index(Model modelMap,HttpServletRequest request) {
		String sysUserId = getSessionId(request);
		SysUser sysUser = sysUserService.getUserById(sysUserId,true);
		modelMap.addAttribute("userName", sysUser.getUserName());
		return "index";
	}
	
}
