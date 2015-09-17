package com.pqpo.utils.test;

import com.pqpo.utils.security.BASE64;
import com.pqpo.utils.security.MD5Utils;
import com.pqpo.utils.security.RSAUtils;
import com.pqpo.utils.security.RSAUtils.RASKeys;

public class SecurityTest {

	public static void main(String[] args) throws Exception {
		String str = "觉得离开房间啊放假了大数据库发生的浪费";
		RASKeys keys = RSAUtils.generateKey();
		String privateKey = RSAUtils.getPrivateKey(keys);
		String publicKey = RSAUtils.getPublicKey(keys);
		
		 byte[] encryptByPrivateKey = RSAUtils.encryptByPrivateKey(str.getBytes(), privateKey);
		 String bytesToHex = MD5Utils.bytesToHex(encryptByPrivateKey);
		 String encode = BASE64.encode(encryptByPrivateKey);
		 System.out.println(bytesToHex);
		 System.out.println(encode);
	}
}
