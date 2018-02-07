package com.bbsoft.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密验证工具
 * 
 * @author
 * 
 */
public class EncryUtil {

	public static String md5s(String plainText) {
		String str = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return str;
	}

	// 获取6位随机数
	public static String getRandom() {
		return String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(md5s(""));
		System.out.println(md5s("Aa123456"));
		System.out.println(Math.random());
		System.out.println(Math.random()*9);
		System.out.println(Math.random()*9+1);
		
	}

}
