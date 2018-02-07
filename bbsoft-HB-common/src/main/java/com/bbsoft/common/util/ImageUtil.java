/**  
 * @Title: ImageUtil.java
 * @Package: com.qcloud365.common.utils
 * @Description: TODO
 * @author: VULCAN
 * @date: 2017-3-18
 */
package com.bbsoft.common.util;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;


/**
 * ClassName: ImageUtil 
 * @Description: 图片剪切压缩
 * @author: VULCAN
 * @date: 2017-3-18
 */
public class ImageUtil {

	
	  /** 
     * @param srcFile源文件 
     * @param outFile输出文件 
     * @param x坐标 
     * @param y坐标 
     * @param width宽度 
     * @param height高度 
     * @return 
     * @作者 jorge.zheng
     * @描述 —— 裁剪图片 
     */ 
	 public static boolean cutPic(String srcFile, String outFile, int x, int y,  
	            int width, int height) {  
//	    	outFile = "D:\\sysfile\\4ed4ed88855847f0be0cfd7d29218a98.jpg";
	        FileInputStream is = null;  
	        ImageInputStream iis = null;  
	        try {  
	            // 如果源图片不存在  
	            if (!new File(srcFile).exists()) {  
	                return false;  
	            }  
	            // 读取图片文件  
	            is = new FileInputStream(srcFile);  
	            // 获取文件格式  
	            String ext = srcFile.substring(srcFile.lastIndexOf(".") + 1);  
	            // ImageReader声称能够解码指定格式  
	            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(ext);  
	            ImageReader reader = it.next();  
	            // 获取图片流  
	            iis = ImageIO.createImageInputStream(is);  
	            // 输入源中的图像将只按顺序读取  
	            reader.setInput(iis, true);  
	            // 描述如何对流进行解码  
	            ImageReadParam param = reader.getDefaultReadParam();  
	            // 图片裁剪区域  
	            Rectangle rect = new Rectangle(x, y, width, height);  
	            // 提供一个 BufferedImage，将其用作解码像素数据的目标  
	            param.setSourceRegion(rect);  
	            // 使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象  
	            BufferedImage bi = reader.read(0, param);  
	            // 保存新图片  
	            File tempOutFile = new File(outFile);  
	            if (!tempOutFile.exists()) {  
	                tempOutFile.mkdirs();  
	            }  
	            ImageIO.write(bi, ext, new File(outFile));  
	            return true;  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	            return false;  
	        } finally {  
	            try {  
	                if (is != null) {  
	                    is.close();  
	                }  
	                if (iis != null) {  
	                    iis.close();  
	                }  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	                return false;  
	            }  
	        }  
	    } 
	    

	 	/**
	 	 * @Description: 高保真图片压缩
	 	 * @param: @param srcfile：源文件
	 	 * @param: @param toWidth
	 	 * @param: @param toHeight
	 	 * @param: @return   
	 	 * @return: BufferedImage  
	 	 * @throws
	 	 * @author: VULCAN
	 	 * @date: 2017-3-18
	 	 */
	    public static BufferedImage zoomImage(File srcfile,int toWidth,int toHeight) {  
	        BufferedImage result = null;  
	        try {  
	            BufferedImage im = ImageIO.read(srcfile);  
	            /* 新生成结果图片 */  
	            result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);  
	            result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight,java.awt.Image.SCALE_SMOOTH), 0, 0, null);  
	        } catch (Exception e) {  
	            System.out.println("创建缩略图发生异常" + e.getMessage());  
	        }  
	        return result;  
	    }  
	      
	    /**
	     * @Description: 处理缩放后的图片
	     * 需要修改 myEclipse配置; Windows-Preferences-Java-Complicer-Errors/Warnings里面的Deprecated and restricted API中的Forbidden references(access rules)选为Warning就可以编译通过
	     * @param: @param im
	     * @param: @param fileFullPath 新图物理路径
	     * @param: @return   
	     * @return: boolean  
	     * @throws
	     * @author: VULCAN
	     * @date: 2017-3-18
	     */
	    public static boolean writeHighQuality(BufferedImage im, String fileFullPath) {  
	            try {  
	                /*输出到文件流*/  
	                FileOutputStream newimage = new FileOutputStream(fileFullPath);  
//	                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);  
//	                JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(im);  
//	                /* 压缩质量 */  
//	                jep.setQuality(0.9f, true);  
//	                encoder.encode(im, jep);  
	               /*近JPEG编码*/  
	                newimage.close();  
	                return true;  
	            } catch (Exception e) {  
	                return false;  
	            }  
	       }  
	 
	    public static void main(String[] args) {
//	    	String inputFoler = "D:\\sysfile\\upload\\201703\\6ff3a457e8c34bfba935883cc6843363.jpg" ;  
//	    	File srcFile = new File(inputFoler);
//	    	/*这儿填写你存放要缩小图片的文件夹全地址*/  
//	    	String outputFolder = "D:\\07.jpg";    
//	    	/*这儿填写你转化后的图片存放的文件夹*/  
//	    	writeHighQuality(zoomImage(srcFile,300,400), outputFolder); 
	    	
			boolean b = cutPic("D:\\sysfile\\upload\\201703\\4bfffb96548443938d01d089ea5e8013.jpg","D:\\01.jpg", 100, 100, 600, 474);
			System.out.println(b);
		} 
	
	
}
