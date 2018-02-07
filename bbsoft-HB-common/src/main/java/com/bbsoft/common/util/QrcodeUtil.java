package com.bbsoft.common.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import com.bbsoft.common.constant.MsgeData;
import com.bbsoft.common.domain.ServiceException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QrcodeUtil {
	private static final int BLACK = 0xFF000000; 
   private static final int WHITE = 0xFFFFFFFF; 
    
   private QrcodeUtil() {} 
    
   public static BufferedImage toBufferedImage(BitMatrix matrix) { 
     int width = matrix.getWidth(); 
     int height = matrix.getHeight(); 
     BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
     for (int x = 0; x < width; x++) { 
       for (int y = 0; y < height; y++) { 
         image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE); 
       } 
     } 
     return image; 
   } 
    
      
   public static void writeToFile(BitMatrix matrix, String format, File file) 
       throws IOException { 
     BufferedImage image = toBufferedImage(matrix); 
     if (!ImageIO.write(image, format, file)) { 
       throw new IOException("Could not write an image of format " + format + " to " + file); 
     } 
   } 
    
      
   public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) 
       throws IOException { 
     BufferedImage image = toBufferedImage(matrix); 
     if (!ImageIO.write(image, format, stream)) { 
       throw new IOException("Could not write an image of format " + format); 
     } 
   } 
   
   /**
    * 生成二维码
    * @param text
    * @return
    */
   @SuppressWarnings({ "unchecked", "rawtypes" })
   public static Map<String, Object> produceCode(String text, boolean isHead){
       int width = 300; 
       int height = 300; 
       //二维码的图片格式 
       String format = "gif"; 
       Hashtable hints = new Hashtable(); 
       //内容所使用编码 
       hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); 
       hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
       hints.put(EncodeHintType.MARGIN, 1);
       BitMatrix bitMatrix;
       String codeId = UUID.randomUUID().toString();
       String filePath = "/code.gif";
//       String filePath = "D://code.gif";
       Map<String, Object> resultMap = new HashMap<String, Object>();
       resultMap.put("codeId", codeId);
       try {
    	   bitMatrix = new MultiFormatWriter().encode(text + "," + codeId, 
		           BarcodeFormat.QR_CODE, width, height, hints);
    	   //生成二维码 
    	   File outputFile = new File(filePath); 
    	   QrcodeUtil.writeToFile(bitMatrix, format, outputFile);
    	   //通过流写入文件，不需要flush()  
           OutputStream os1 = new FileOutputStream(filePath);  
           MatrixToImageWriter.writeToStream(bitMatrix, format, os1);  
           BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);  
           ByteArrayOutputStream os = new ByteArrayOutputStream();//新建流。  
           ImageIO.write(image, format, os);//利用ImageIO类提供的write方法，将bi以png图片的数据模式写入流。  
           byte b[] = os.toByteArray();//从流中获取数据数组。  
           String codeStr = Base64.encode(b);  
           if(isHead){
        	   codeStr = "data:image/gif;base64," + codeStr;
           }
           resultMap.put("codeStr", codeStr);
           resultMap.put("createTime", System.currentTimeMillis());
       } catch (Exception e) {
    	   throw new ServiceException(MsgeData.SYSTEM_10100.getCode());
       }
       return resultMap;
   }
   
   public static void main(String[] args) {
	   produceCode("123456", false);
}
}
