package com.oo.businessplan.common.util;

import java.security.SecureRandom;


import javax.crypto.Cipher;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;


import org.apache.commons.codec.binary.Base64;


public final class DesUtil {
	
	private volatile static DesUtil util = null;
	
	private DesUtil() {}
	
    private static final String ALGORITHM = "DES";
	
    private static final String TRANSFORMATION = "DES/ECB/PKCS5Padding";
    
    private static final String CODE_TYPE = "UTF-8";
	
	public static DesUtil getInstance() {
		
		if (util == null) {
			synchronized (DesUtil.class) {
				if (util == null) {
					util = new DesUtil();
				}				
			}		
		}
		
		return util;
	}
	
	/**
	 * use the des encryotion method to encrypt the data
	 * the key which used in encrypt is 8 bite 64
	 * @param data
	 * @param key
	 * @return
	 */
    public String encrypt (String data, String key){
    	try {
			//Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			//创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
			DESKeySpec desKey = new DESKeySpec(key.getBytes(CODE_TYPE));
			SecretKey securekey = keyFactory.generateSecret(desKey); 
			//用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, new SecureRandom());
			//现在，获取数据并加密
			byte[] temp = Base64.encodeBase64(cipher.doFinal(data.getBytes(CODE_TYPE)));
			return new String(temp, CODE_TYPE);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
	}
    
    /**
	 * decrypt the encryptedData whick encrypt by des
	 * @param encryptedData
	 * @param key
	 * @return
	 */
    public String decrypt(String encryptedData, String key){
    	try {
	    	// DES算法要求有一个可信任的随机数源
	
	        // 创建一个DESKeySpec对象
	        DESKeySpec desKey = new DESKeySpec(key.getBytes(CODE_TYPE));
	        // 创建一个密匙工厂
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
	        // 将DESKeySpec对象转换成SecretKey对象
	        SecretKey securekey = keyFactory.generateSecret(desKey);
	        // Cipher对象实际完成解密操作
	        Cipher cipher = Cipher.getInstance("DES");
	        // 用密匙初始化Cipher对象
	        cipher.init(Cipher.DECRYPT_MODE, securekey, new SecureRandom());
	        // 真正开始解密操作
	        return new String(cipher.doFinal(Base64.decodeBase64(encryptedData)), CODE_TYPE);
    	} catch(Exception e) {
    		e.printStackTrace();
			return null;
    	}
	}


}
