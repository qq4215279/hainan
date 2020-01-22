package com.gobestsoft.core.util;

import java.util.Random;

/**
 * 32位UUID生成器
 * 
 * @version V1.0
 * @author Administrator
 * @date 2014年9月10日
 */
public class UUIDUtil {

	/**
	 * 生成不带-的UUID
	 * 
	 * @return UUID
	 */
	public static String getUUID() {
		// 返回用UUID
		String uuidResult = "";
		// 临时用UUID
		String uuidTemp = "";
		// 取得UUID存储到临时用UUID
		uuidTemp = java.util.UUID.randomUUID().toString();

		// 替换掉所有-字符
		uuidResult = uuidTemp.replaceAll("-", "");

		// 返回UUID
		return uuidResult;
	}

	/**
	 * java生成随机数字和字母组合
	 * 
	 * @param length
	 *            [生成随机数的长度]
	 * @return
	 */
	public static String getCharAndNumr(int length) {
		String val = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			// 输出字母还是数字
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 字符串
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 取得大写字母还是小写字母
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

	/**
	 * 随机生成纯数字
	 * 
	 * @param length
	 * @return
	 */
	public static String getNumber(int length) {
		char[] chars = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i <= length; i++) {
			sb.append(chars[random.nextInt(chars.length)]);
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(UUIDUtil.getCharAndNumr(16));
		System.out.println(UUIDUtil.getUUID());
	}
}
