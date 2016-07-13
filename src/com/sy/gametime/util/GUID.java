package com.sy.gametime.util;

import java.util.UUID;

public final class GUID {
	private static final ThreadLocal<GUID> local = new ThreadLocal<GUID>();
	
	private GUID() {
		super();
	}
	
	public static GUID getGUID() {
		GUID guid = local.get();
		if(guid == null) {
			guid = new GUID();
			local.set(guid);
		}
		return guid;
	}
	
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static void main(String[] args) {
		System.out.println(getUUID());
	}
}
