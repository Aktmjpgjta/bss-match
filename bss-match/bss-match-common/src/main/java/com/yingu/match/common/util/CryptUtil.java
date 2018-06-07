package com.yingu.match.common.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.ws.security.util.Base64;


/**
 * 
 * Description: 加解密工具类（兼容PHP）
 * 
 * @author liufei
 * @version 1.0
 * 
 *          <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------
 * 2015年5月18日    liufei       1.0        1.0 Version
 * </pre>
 */
public final class CryptUtil {
	private static final String KEY_TYPE = "DES";

	/**
	 * 
	 * Description: DES加密
	 * @param
	 * @return String
	 * @throws
	 * @Author liufei
	 * Create Date: 2015年5月21日 下午5:01:43
	 */
	public static String encrypt(String input, String key) throws Exception {
		return base64Encode(
			desEncrypt(
				input.getBytes("UTF-8"), 
				key.getBytes("UTF-8")
			)
		);
	}

	/**
	 * 
	 * Description: DES解密
	 * @param
	 * @return String
	 * @throws
	 * @Author liufei
	 * Create Date: 2015年5月21日 下午5:01:52
	 */
	public static String decrypt(String input, String key) throws Exception {
		byte[] result = base64Decode(input);
		return new String(
			desDecrypt(
				result, 
				key.getBytes("UTF-8")
			)
		);
	}

	/*================================== helpers ===============================*/
	/**
	 * 
	 * Description: DES加密
	 * 
	 * @param
	 * @return byte[]
	 * @throws
	 * @Author liufei Create Date: 2015年5月21日 下午5:00:00
	 */
	private static byte[] desEncrypt(byte[] plainText, byte[] desKey) throws Exception {
		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = desKey;
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_TYPE);
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(KEY_TYPE);
		cipher.init(Cipher.ENCRYPT_MODE, key, sr);
		byte data[] = plainText;
		byte encryptedData[] = cipher.doFinal(data);
		return encryptedData;
	}

	/**
	 * 
	 * Description: DES解密
	 * 
	 * @param
	 * @return byte[]
	 * @throws
	 * @Author liufei Create Date: 2015年5月21日 下午5:00:20
	 */
	private static byte[] desDecrypt(byte[] encryptText, byte[] desKey) throws Exception {
		SecureRandom sr = new SecureRandom();
		byte rawKeyData[] = desKey;
		DESKeySpec dks = new DESKeySpec(rawKeyData);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_TYPE);
		SecretKey key = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(KEY_TYPE);
		cipher.init(Cipher.DECRYPT_MODE, key, sr);
		byte encryptedData[] = encryptText;
		byte decryptedData[] = cipher.doFinal(encryptedData);
		return decryptedData;
	}

	/**
	 * 
	 * Description: Base64编码
	 * @param
	 * @return String
	 * @throws
	 * @Author liufei
	 * Create Date: 2015年6月16日 上午11:32:04
	 */
	public static String base64Encode(byte[] s) {
		if (s == null)
			return null;
		return Base64.encode(s);
	}

	/**
	 * 
	 * Description: Base64解码
	 * @param
	 * @return byte[]
	 * @throws
	 * @Author liufei
	 * Create Date: 2015年6月16日 上午11:32:31
	 */
	public static byte[] base64Decode(String s) throws Exception {
		if (s == null)
			return null;
		return Base64.decode(s);
	}
}
