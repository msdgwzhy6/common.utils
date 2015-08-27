package com.pqpo.utils.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public final class AESUtils {
	
	private AESUtils(){}

	private static final String ALGORITHM = "AES";
	private static final int KEY_SIZE = 128;
	private static final String CHARSET = "UTF-8";
	private volatile static KeyGenerator keyGen;
	
	/**
	 * 加密
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String content,String key) throws Exception{
		byte[] encrypt = encrypt(content.getBytes(CHARSET),key);
		String result = BASE64.encode(encrypt);
		return result; 
	}
	
	/**
	 * 加密
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static byte[] encrypt(byte[] content,String key) throws Exception{
		KeyGenerator keygen = getKeyGen();
		keygen.init(KEY_SIZE,new SecureRandom(key.getBytes(CHARSET)));
		SecretKey secretKey = keygen.generateKey();
		byte[] encoded = secretKey.getEncoded();
		SecretKeySpec secretKeySpec = new SecretKeySpec(encoded, ALGORITHM);
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		byte[] result = cipher.doFinal(content);
		return result; 
	}
	
	/**
	 * 解密
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String content,String key) throws Exception{
		byte[] decode = BASE64.decode(content);
		byte[] decrypt = decrypt(decode, key);
		return new String(decrypt,CHARSET); 
	}
	
	/**
	 * 解密
	 * @param content
	 * @param key
	 * @return
	 * @throws Exception 
	 */
	public static byte[] decrypt(byte[] content,String key) throws Exception{
		KeyGenerator keygen = getKeyGen();
		keygen.init(KEY_SIZE,new SecureRandom(key.getBytes(CHARSET)));
		SecretKey secretKey = keygen.generateKey();
		byte[] encoded = secretKey.getEncoded();
		SecretKeySpec secretKeySpec = new SecretKeySpec(encoded, ALGORITHM);
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
		byte[] result = cipher.doFinal(content);
		return result; 
	}
	
	
	protected static KeyGenerator getKeyGen() throws NoSuchAlgorithmException {
		if(keyGen==null){
			synchronized (KeyGenerator.class) {
				if(keyGen==null){
					keyGen = KeyGenerator.getInstance(ALGORITHM);
				}
			}
		}
		return keyGen;
	}
}
