/**
 * 
 */
package com.epweike.util;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * @author wuxp
 */
public class MD5Utils {
	// MD5加密。32位
	public static String getMD5(String source, String salt) {
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		return md5.encodePassword(source, salt);
	}

	public static void main(String args[]) {
		System.out.println("加密后的：" + getMD5("admin", "ping6"));
	}
}
