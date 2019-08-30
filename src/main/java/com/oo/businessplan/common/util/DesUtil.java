package com.oo.businessplan.common.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public final class DesUtil {
	
	private volatile static DesUtil util = null;
	
	private DesUtil() {}
	
    private static final String ALGORITHM = "DES";
	
    private static final String TRANSFORMATION = "DES/ECB/PKCS5Padding";
	
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
	public String encrypt (byte[] data, byte[] key) {
		
		try {
			Cipher cipher = Cipher.getInstance(TRANSFORMATION);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
			KeySpec keySpec = new DESKeySpec(key);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey,new SecureRandom());
            byte[] enMsgBytes = cipher.doFinal(data);
            return new String(enMsgBytes);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidKeySpecException | IllegalBlockSizeException | BadPaddingException e) {
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
	public String decrypt(byte[] encryptedData, byte[] key) {
		try {
            //解密
            //Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            Cipher deCipher = Cipher.getInstance(TRANSFORMATION);
            SecretKeyFactory deDecretKeyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            KeySpec deKeySpec = new DESKeySpec(key);
            SecretKey deSecretKey = deDecretKeyFactory.generateSecret(deKeySpec);
            deCipher.init(Cipher.DECRYPT_MODE, deSecretKey,new SecureRandom());
            return new String(deCipher.doFinal(encryptedData));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
	}

}
