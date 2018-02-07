/**  
 * @Title: FileUploadApi.java
 * @Package: com.happygou.api.controller.system
 * @Description: TODO
 * @author: VULCAN
 * @date: 2017-2-8
 */
package com.bbsoft.wechat.controller.system;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bbsoft.common.constant.Json;
import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.bbsoft.common.util.DateUtil;
import com.bbsoft.common.util.ImageUtil;
import com.bbsoft.common.util.ResourceUtil;
import com.bbsoft.common.util.ResultUtil;
import com.bbsoft.common.util.StringUtil;
import com.bbsoft.common.util.UuidUtils;
import com.bbsoft.wechat.controller.BaseController;

/**
 * ClassName: FileUploadApi 
 * @Description: TODO
 * @author: VULCAN
 * @date: 2017-2-8
 */

@Controller
public class FileUploadController extends BaseController{

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	private static final long IMG_MAX_SIZE = 1024 * 1024 * 10;
	
	@RequestMapping("/upload100000")
	@ResponseBody
	public Json UploadImg(@RequestParam(value = "file", required = false) MultipartFile imgFile,
    		MultipartHttpServletRequest request,HttpServletResponse response) throws IOException {
		Json json = getSuccessObj();
		byte[] fileData;
		if(imgFile == null || imgFile.isEmpty()){
			json = getFailedObj(MsgeData.PUBLIC_1000010006.getCode(), MsgeData.PUBLIC_1000010006.getMsg());
		}else{
			fileData = imgFile.getBytes();
			//判断图片尺寸
			BufferedImage bi = ImageIO.read(imgFile.getInputStream());
			if (bi!=null) {
				if (fileData.length > IMG_MAX_SIZE){
					json = getFailedObj(MsgeData.PUBLIC_1000010008.getCode(), MsgeData.PUBLIC_1000010008.getMsg());
				}else{
					String ym = DateUtil.formatDateToString(new Date(), "yyyyMM");
					String fixString = StringUtil.getFormatForFile(imgFile); // 获得File后缀名
					String fileName = UuidUtils.getUUid();
					
					String xdPath = "/upload/" + ym + "/" + fileName + "."+ fixString;// 数据库路径  
					String savePath = ResourceUtil.getUploadImgPath()+ "/upload/" + ym;// 物理路径
					File outFiles = new File(savePath);
					if (!outFiles.exists()) {
						outFiles.mkdirs();
					}
					File originalFile = new File(savePath ,fileName + "." + fixString);
					imgFile.transferTo(originalFile);
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("filePath",ResourceUtil.getFileServerPath()+xdPath);
					map.put("fileName",fileName);
					json.setResults(ResultUtil.returnByObj(map));
				}
			}else{
				json = getFailedObj(MsgeData.PUBLIC_1000010007.getCode(), MsgeData.PUBLIC_1000010007.getMsg());
			}
		}
		return json;
	}
	
	@RequestMapping("/cutPic")
	@ResponseBody
    public Json cutPic(String srcFile, int x, int y,int cropWidth,int cropHeight) {  
		Json json = getSuccessObj();
		if(StringUtils.isEmpty(srcFile)){
			throw new ServiceException(MsgeData.FILE_10200.getCode());
		}
		//获取原图物理路径
		srcFile = ResourceUtil.getUploadImgPath()+ResourceUtil.covertImgDomain(srcFile);
		File oldfile = new File(srcFile);
		if(!oldfile.exists()){
			throw new ServiceException(MsgeData.FILE_10203.getCode());
		}
		//这是压缩文件路径
		String ym = DateUtil.formatDateToString(new Date(), "yyyyMM");
		String fileName = UuidUtils.getUUid();
		int lastIndex = srcFile.lastIndexOf(".");
		String fixString = srcFile.substring(lastIndex + 1, srcFile.length()); // 获得File后缀名
		String xdPathOut = "/upload/" + ym + "/" + fileName + "."+ fixString;// 最后文件库路径  
		//剪切图片
		String outFullPath = ResourceUtil.getUploadImgPath()+ xdPathOut;
		Boolean flagOut = ImageUtil.cutPic(srcFile, outFullPath, x, y, cropWidth, cropHeight);
		if(!flagOut){
			json = getFailedObj(MsgeData.FILE_10201.getCode(), MsgeData.FILE_10201.getMsg());
		}else{
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("filePath",ResourceUtil.getFileServerPath()+xdPathOut);
			map.put("fileName",fileName);
			json.setResults(ResultUtil.returnByObj(map));
		}
		return json;
    } 
    
	public static void main(String[] args) {
		File file = new File("D://new");
		if(!file.exists()){
			file.mkdirs();
		}
		File ofile = new File("D://new", "new.jpg");
		try {
			ofile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
