package com.enorth.cms.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

public class MD5Util {

	private static String byteHEX(byte ib) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		char[] ob = new char[2];
		ob[0] = Digit[(ib >>> 4 & 0xF)];
		ob[1] = Digit[(ib & 0xF)];
		String s = new String(ob);
		return s;
	}

	public static String getMD5(String str) {
		return getMD5(str.getBytes());
	}

	public static String getMD5(String str, String charset) throws UnsupportedEncodingException {
		return getMD5(str.getBytes(charset));
	}

	public static String getMD5(byte[] raw) {
		String result = "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(raw);
			byte[] bytes = md5.digest();
			for (int i = 0; i < bytes.length; i++)
				result = result + byteHEX(bytes[i]);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getMD5(InputStream in) throws IOException {
		String result = "";
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] data = new byte[8192];
			int readed = in.read(data);
			while (readed > 0) {
				md5.update(data, 0, readed);
				readed = in.read(data);
			}

			byte[] bytes = md5.digest();
			for (int i = 0; i < bytes.length; i++)
				result = result + byteHEX(bytes[i]);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getMD5(File file) throws IOException {
		if (file == null) {
			return "";
		}
		if (!file.exists()) {
			throw new FileNotFoundException(file.getAbsolutePath() + " not found.");
		}
		if (file.isDirectory()) {
			throw new IllegalArgumentException(
					"Argument must be a file, but " + file.getAbsolutePath() + " is a directory.");
		}

		FileInputStream in = null;
		try {
			in = new FileInputStream(file);
			String str = getMD5(in);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null)
				in.close();
		}
		return null;
	}

	public static String getMD5CheckSum(byte[] data, byte[] seed, int length) {
		if ((seed == null) || (seed.length == 0)) {
			throw new IllegalArgumentException("seed==null or seed.length==0");
		}
		if (data == null) {
			data = new byte[0];
		}
		byte[] temp = new byte[data.length + seed.length];

		int i = 0;
		for (; i < data.length; i++) {
			temp[i] = data[i];
		}
		for (int j = 0; j < seed.length; i++) {
			temp[i] = seed[j];

			j++;
		}

		String md5 = getMD5(temp);
		return md5.substring(0, length);
	}
}
