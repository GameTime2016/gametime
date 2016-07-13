package com.sy.gametime.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Picture {
	
	private String picCode;
	
	private String productCode;
	
	private String picType;
	
	private String picName;
	
	private String picDesc;
	
	@Expose
	@SerializedName("PictureUrl")
	private String picUrl;
	
	private String picCrtDate;

	public String getPicCode() {
		return picCode;
	}

	public void setPicCode(String picCode) {
		this.picCode = picCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getPicType() {
		return picType;
	}

	public void setPicType(String picType) {
		this.picType = picType;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getPicDesc() {
		return picDesc;
	}

	public void setPicDesc(String picDesc) {
		this.picDesc = picDesc;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getPicCrtDate() {
		return picCrtDate;
	}

	public void setPicCrtDate(String picCrtDate) {
		this.picCrtDate = picCrtDate;
	}
}
