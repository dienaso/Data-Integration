/**
 * 
 */
package com.epweike.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.epweike.common.Constants;

/**
 * @author wuxp
 */
public class MD5Utils {
	// MD5加密。32位
	public static String getMD5(String source) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		md5.setEncodeHashAsBase64(true);
		return md5.encodePassword(source, Constants.SALT);
	}

	public static void main(String args[]) {
		System.out.println("加密后的：" + getMD5("admin"));
	}
}
