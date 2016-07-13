package com.sy.gametime.util;

public final class CommonUtil {
	public static boolean checkWebKey(String webkey) {
		return DicCons.WEB_KEY_CODE.equals(webkey);
	}
}
