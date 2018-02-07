/**  
 * @Title: SystemController.java
 * @Package: com.happygou.api365.controller.system
 * @Description: TODO
 * @author: VULCAN
 * @date: 2017-3-16
 */
package com.bbsoft.admin.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bbsoft.admin.controller.BaseController;
import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;

/**
 * ClassName: SystemController 
 * @Description: TODO
 * @author: VULCAN
 * @date: 2017-3-16
 */
@Controller
public class SystemController extends BaseController{

	/**
	 * @Description: 错误页面处理
	 * @param: @return   
	 * @return: Json  
	 * @throws
	 * @author: VULCAN
	 * @date: 2017-3-13
	 */
	@RequestMapping("/404100000")
	@ResponseBody
	public Json get404Error() {//404
		throw new ServiceException(MsgeData.SYSTEM_10122.getCode());
	}
	
	@RequestMapping("/405100000")
	@ResponseBody
	public Json get405Error() {//405
		throw new ServiceException(MsgeData.SYSTEM_10123.getCode());
	}
	
	@RequestMapping("/500100000")
	@ResponseBody
	public Json get500Error() {//500
		throw new ServiceException(MsgeData.SYSTEM_10124.getCode());
	}
	
	@RequestMapping(value = "jsp/{p1}")
	public String jsp(@PathVariable("p1") String p1) {
		return p1;
	}

	@RequestMapping(value = "jsp/{p1}/{p2}")
	public String jsp(@PathVariable("p1") String p1,
			@PathVariable("p2") String p2) {
		return p1 + "/" + p2;
	}

	@RequestMapping(value = "jsp/{p1}/{p2}/{p3}")
	public String jsp(@PathVariable("p1") String p1,
			@PathVariable("p2") String p2, @PathVariable("p3") String p3) {
		System.out.println(p1 + "/" + p2 + "/" + p3);
		return p1 + "/" + p2 + "/" + p3;
	}
}
