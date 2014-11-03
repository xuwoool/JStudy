package com.jstudy.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密
 * 
 * @author xuxb
 */
public class Encrypt {
	
	private static final Logger LOG = LoggerFactory.getLogger(Encrypt.class);
	
	private static final String ALGORITHM_MD5 = "MD5";

	/**
	 * 使用MD5算法加密
	 * 
	 * @param plaintext
	 * @return
	 */
	public static String md5(String plaintext) {
		return digest(plaintext, ALGORITHM_MD5);
	}

	private static String digest(String plaintext, String algorithm) {
		// 根据algorithm算法生成MessageDigest对象
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance(algorithm);
			byte[] srcBytes = plaintext.getBytes();
			// 使用srcBytes更新摘要
			messageDigest.update(srcBytes);

			return hex(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			throw Exceptions.unchecked(e);
		}
	}

	// 返回十六进制字符串
	private static String hex(byte[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1,3));
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		String plaintext = "123";
		LOG.debug("明文=" + plaintext);
		String ciphertext = Encrypt.md5(plaintext);
		LOG.debug("密文=" + ciphertext);
	}
}
