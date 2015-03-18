package com.epweike.util;

import java.util.regex.Pattern;

/**
 * @author wuxp
 */
public class ValidateUtils {

	public ValidateUtils() {
	}

	// 邮政编码
	public static Pattern PATTERN_POSTCODE = Pattern.compile("^\\d{6}$");

	// 邮件地址
	public static Pattern PATTERN_EMAIL = Pattern
			.compile("^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$");

	// 固定电话（区号+主机号+分机号）
	public static Pattern PATTERN_TEL = Pattern
			.compile("^(\\d{3,4}-)?\\d{7,8}(-\\d{3,4})?$");

	// 移动电话
	public static Pattern PATTERN_MOBILE = Pattern
			.compile("^(\\+86)?0?(13|14|15|18)[0-9]{9}$");

	// 字母
	public static Pattern PATTERN_ALPHA = Pattern.compile("^[A-Za-z]+$");

	// 数字、字母、下划线
	public static Pattern PATTERN_ALPHAS = Pattern.compile("^[A-Za-z0-9_]+$");

	// 数字
	public static Pattern PATTERN_DIGITAL = Pattern.compile("^\\d+$");

	// 浮点型正则表达式
	public static Pattern PATTERN_DOUBLE = Pattern.compile("^\\d+(\\.\\d+)?$");

	// 中文
	public static Pattern PATTERN_CHINESE = Pattern
			.compile("^[\\u4E00-\\u9FA5]+$");

	// 15位身份证格式
	public static Pattern PATTERN_IDCARD_15 = Pattern
			.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");

	// 18位身份证格式
	public static Pattern PATTERN_IDCARD_18 = Pattern
			.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}[\\d|x|X]$");

	// IP格式
	public static Pattern PATTERN_IP = Pattern
			.compile("^((00\\d|1?\\d?\\d|(2([0-4]\\d|5[0-5])))\\.){3}(00\\d|1?\\d?\\d|(2([0-4]\\d|5[0-5])))$");

	// 日期格式如 如：2011-11-11
	public static Pattern PATTERN_TIME = Pattern
			.compile("^([0-9]{4}-[0-9]{2}-[0-9]{2})$");

	// 人民币格式
	public static Pattern PATTERN_RMB = Pattern
			.compile("^[0-9]{1,10}(\\.[0-9]{0,3})?$");

	// 由字母开头和数字、字母、下划线组成
	public static Pattern PATTERN_ALPHASFIRST = Pattern
			.compile("^[a-zA-Z]{1}[A-Za-z0-9_]+$");

	// 数字、字母、下划线组成6-20位
	public static Pattern PATTERN_ALPHASLIMTLENGTH = Pattern
			.compile("^[0-9a-zA-Z_]{6,32}");

	// URL格式
	public static Pattern PATTERN_URL = Pattern
			.compile("^((https|http|ftp|rtsp|mms)?://)"
					+ "+(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?"
					+ "(([0-9]{1,3}\\.){3}[0-9]{1,3}" + "|"
					+ "([0-9a-z_!~*'()-]+\\.)*"
					+ "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." + "[a-z]{2,6})"
					+ "(:[0-9]{1,4})?" + "((/?)|"
					+ "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$");

	// 用户名
	public static Pattern PATTERN_USERNAME = Pattern
			.compile("^[A-Za-z0-9_\\u4E00-\\u9FA5]+$");

	public static Pattern PATTERN_PASSWD = Pattern
			.compile("^[^\\s\\u4e00-\\u9fa5]+$");

	// 输入验证是否通过true:通过，false:不通过
	public boolean ifValidatePass = true;

	// 校验人民币格式
	public synchronized static boolean isRmb(String rmb) {
		if (rmb == null)
			return true;
		else
			return !PATTERN_RMB.matcher(rmb).matches();
	}

	// 校验由字母开头和数字、字母、下划线组成格式，主要是用于注册用户名
	public synchronized static boolean isAlphasFirst(String username) {
		if (username == null)
			return true;
		else
			return !PATTERN_ALPHASFIRST.matcher(username).matches();
	}

	// 校验 数字、字母、下划线组成6-20位 格式 ,主要是用于验证密码长度
	public synchronized static boolean isAlphasLimtLength(String pwd) {
		if (pwd == null)
			return true;
		else
			return !PATTERN_ALPHASLIMTLENGTH.matcher(pwd).matches();
	}

	// 非空验证
	public synchronized static boolean isRequired(String name) {
		if (name == null)
			return true;
		name = name.trim();
		if (name.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	// 校验email格式
	public synchronized static boolean isEmail(String email) {
		if (email == null)
			return true;
		else
			return !PATTERN_EMAIL.matcher(email).matches();
	}

	// 验证电话格式
	public synchronized static boolean isTelephone(String telephone) {
		if (telephone == null)
			return true;
		else
			return !PATTERN_TEL.matcher(telephone).matches();
	}

	// 验证手机格式
	public synchronized static boolean isMobile(String mobile) {
		if (mobile == null)
			return true;
		else
			return !PATTERN_MOBILE.matcher(mobile).matches();
	}

	// 验证是否字母
	public synchronized static boolean isAlpha(String alpha) {
		if (alpha == null)
			return true;
		else
			return !PATTERN_ALPHA.matcher(alpha).matches();
	}

	// 验证是否为18位有效身份证
	public synchronized static boolean isIdcard_18(String idcard) {
		if (idcard == null)
			return true;
		else
			return !PATTERN_IDCARD_18.matcher(idcard).matches();
	}

	// 验证是否为数字、字母、下划线
	public synchronized static boolean isAlphas(String alpha) {
		if (alpha == null)
			return true;
		else
			return !PATTERN_ALPHAS.matcher(alpha).matches();
	}

	// 验证是否为数字
	public synchronized static boolean isDigital(String digital) {
		if (digital == null)
			return true;
		else
			return !PATTERN_DIGITAL.matcher(digital).matches();
	}

	// 验证是否为浮点型
	public synchronized static boolean isDouble(String Double) {
		if (Double == null)
			return true;
		else
			return !PATTERN_DOUBLE.matcher(Double).matches();
	}

	// 验证是否为中文
	public synchronized static boolean isChinese(String chinese) {
		if (chinese == null)
			return true;
		else
			return !PATTERN_CHINESE.matcher(chinese).matches();
	}

	// 验证时间 时间可以不写前面的0 如 9:3:1
	public synchronized static boolean isTime(String time) {
		if (time == null)
			return true;
		else
			return !PATTERN_TIME.matcher(time).matches();
	}

	// 校验IP格式
	public synchronized static boolean isIP(String ip) {
		if (ip == null)
			return true;
		else
			return !PATTERN_IP.matcher(ip).matches();
	}

	// 校验URL
	public synchronized static boolean isURL(String url) {
		if (url == null)
			return true;
		else
			return !PATTERN_URL.matcher(url).matches();
	}

	// 校验邮政编码
	public synchronized static boolean isPostCode(String PostCode) {
		if (PostCode == null) {
			return true;
		} else {
			return !PATTERN_POSTCODE.matcher(PostCode).matches();
		}
	}

	public static void main(String[] args) {

	}

}
