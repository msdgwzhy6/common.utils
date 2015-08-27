package com.pqpo.utils.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class MD5Utils {
	private static final String MD5="md5";
	private static final String CHARTSET = "utf-8";

	private MD5Utils(){}
	
	private volatile static MessageDigest md5;
	
	private static MessageDigest MD5() throws NoSuchAlgorithmException{
		if(md5==null){
			synchronized (MD5Utils.class) {
				if(md5==null){
					md5 = MessageDigest.getInstance(MD5);
				}
			}
		}
		return md5;
	}
	
	public static byte[] digest(byte[] input) throws NoSuchAlgorithmException{
		MessageDigest md5 = MD5();
		return md5.digest(input);
	}
	
	public static String digest(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		byte[] digest = digest(input.getBytes(CHARTSET));
		return bytesToHex(digest);
	}
	
	/**
	 * 二进制转十六进制
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuffer md5str = new StringBuffer();
		//把数组每一字节换成16进制连成md5字符串
		int digital;
		for (int i = 0; i < bytes.length; i++) {
			 digital = bytes[i];

			if(digital < 0) {
				digital += 256;
			}
			if(digital < 16){
				md5str.append("0");
			}
			md5str.append(Integer.toHexString(digital));
		}
		return md5str.toString().toUpperCase();
	}
}
