package com.pqpo.utils.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

/**
 * 最大加密长度为 117 bytes，大于117 bytes 需要分段加密。</br>
 * 非对称加密一般都用于加密对称加密算法的密钥，
 * 而不是直接加密内容。
 * @author qiulinmin
 *
 */
public class RSAUtils {

	private static final String ALGORITHM = "RSA";
	private static final String CHARTSET = "utf-8";
	private static final int KEY_SIZE = 1024;

	private RSAUtils(){}

	/**
	 * 获取密钥
	 * @return
	 * @throws Exception
	 */
	public static RASKeys generateKey() throws Exception {
		KeyPairGenerator keyPairGenerator =  KeyPairGenerator.getInstance(ALGORITHM);
		keyPairGenerator.initialize(KEY_SIZE);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();
		RASKeys rasKeys = new RASKeys();
		rasKeys.setPublicKey(publicKey);
		rasKeys.setPrivateKey(privateKey);
		return rasKeys;
	}

	/**
	 * 获取公钥
	 * @param keys
	 * @return
	 */
	public static String getPublicKey(RASKeys keys){
		return BASE64.encode(keys.getPublicKey().getEncoded());
	}

	/**
	 * 获取私钥
	 * @param keys
	 * @return
	 */
	public static String getPrivateKey(RASKeys keys){
		return BASE64.encode(keys.getPrivateKey().getEncoded());
	}

	/**
	 * 私钥加密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	public static String encryptByPrivateKey(String data,String key) throws Exception, Exception{
		byte[] results = encryptByPrivateKey(data.getBytes(CHARTSET),key);
		return BASE64.encode(results);
	}

	/**
	 * 私钥加密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data,String key) throws Exception{

		if(data.length>(KEY_SIZE/8-11)){
			throw new IllegalBlockSizeException("Data must not be longer than "+(KEY_SIZE/8-11)+" bytes.Data length:"+data.length);
		}

		byte[] keyBytes = BASE64.decode(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	
	/**
	 * 公钥加密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String data,String key) throws Exception{
		byte[] encrypt = encryptByPublicKey(data.getBytes(CHARTSET), key);
		return BASE64.encode(encrypt);
	}
	
	/**
	 * 公钥加密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data,String key) throws Exception{
		
		if(data.length>(KEY_SIZE/8-11)){
			throw new IllegalBlockSizeException("Data must not be longer than "+(KEY_SIZE/8-11)+" bytes.Data length:"+data.length);
		}
		
		byte[] keyBytes = BASE64.decode(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	
	/*********************************************解密*********************************************************/
	
	/**
	 * 私钥解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(String data,String key) throws Exception{
		byte[] dataBytes = BASE64.decode(data);
		byte[] resultBytes = decryptByPrivateKey(dataBytes, key);
		return new String(resultBytes,CHARTSET);
	}
	
	/**
	 * 私钥解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data,String key) throws Exception{
		byte[] keyBytes = BASE64.decode(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}


	/**
	 * 公钥解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPublicKey(String data,String key) throws Exception{
		byte[] decode = BASE64.decode(data);
		byte[] byPublicKey = decryptByPublicKey(decode,key);
		return new String(byPublicKey,CHARTSET);
	}
	
	/**
	 * 公钥解密
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] data,String key) throws Exception{
		byte[] keyBytes = BASE64.decode(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	


	public static class RASKeys{
		private PublicKey publicKey;
		private PrivateKey privateKey;
		public PublicKey getPublicKey() {
			return publicKey;
		}
		public void setPublicKey(PublicKey publicKey) {
			this.publicKey = publicKey;
		}
		public PrivateKey getPrivateKey() {
			return privateKey;
		}
		public void setPrivateKey(PrivateKey privateKey) {
			this.privateKey = privateKey;
		}
	}
}
