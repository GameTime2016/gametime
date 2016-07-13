package com.sy.gametime.util;

import org.apache.commons.codec.digest.DigestUtils;

public final class MD5Encrypt {
	
	private static final ThreadLocal<MD5Encrypt> local = new ThreadLocal<MD5Encrypt>();
	
	private MD5Encrypt() {
		super();
	}
	
	public static MD5Encrypt getEncrypt() {
		MD5Encrypt encrypt = local.get();
		if(encrypt == null) {
			encrypt = new MD5Encrypt();
			local.set(encrypt);
		}
		return encrypt;
	}
	
	public static String encode(String s) {
		if(s == null) {
			return null;
		} else {
			return DigestUtils.md5Hex(DicCons.GAMETIME_KEY + s);
		}
	}
	
	public static void main (String args[]) {
		String password = "PLAYGAME_SHOWTIME!";
		System.out.println(encode(password));
		System.out.println(DigestUtils.md5Hex("PLAYGAME_SHOWTIME!"));
	}
}
