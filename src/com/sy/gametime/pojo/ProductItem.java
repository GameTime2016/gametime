package com.sy.gametime.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductItem {
	
	@Expose
	@SerializedName("Code")
	private String productCode;
	
	@Expose
	@SerializedName("ItemSeq")
	private int productItemSeq;
	
	@Expose
	@SerializedName("Platform")
	private String platformName;
	
	@Expose
	@SerializedName("Language")
	private String languageName;
	
	@Expose
	@SerializedName("Storage")
	private String hardwareStorage;
	
	@Expose
	@SerializedName("Video")
	private String hardwareGPU;
	
	@Expose
	@SerializedName("Vibrate")
	private String hardwareVibrate;
	
	@Expose
	@SerializedName("Remote")
	private String hardwareRemote;
	
	@Expose
	@SerializedName("VideoUrl")
	private String gamevideoAddress;
	
	@Expose
	@SerializedName("Price")
	private double productItemPrice;
	
	@Expose
	@SerializedName("Rent")
	private double productItemRent;
	
	@Expose
	@SerializedName("Stock")
	private int productItemQty;
	
	@Expose
	@SerializedName("Rate")
	private double productRate;
	
	@Expose
	@SerializedName("AgeLimit")
	private int ageFrom;
	
	private int ageTo;

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public int getProductItemSeq() {
		return productItemSeq;
	}

	public void setProductItemSeq(int productItemSeq) {
		this.productItemSeq = productItemSeq;
	}

	public String getPlatformName() {
		return platformName;
	}

	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public String getHardwareStorage() {
		return hardwareStorage;
	}

	public void setHardwareStorage(String hardwareStorage) {
		this.hardwareStorage = hardwareStorage;
	}

	public String getHardwareGPU() {
		return hardwareGPU;
	}

	public void setHardwareGPU(String hardwareGPU) {
		this.hardwareGPU = hardwareGPU;
	}

	public String getHardwareVibrate() {
		return hardwareVibrate;
	}

	public void setHardwareVibrate(String hardwareVibrate) {
		this.hardwareVibrate = hardwareVibrate;
	}

	public String getHardwareRemote() {
		return hardwareRemote;
	}

	public void setHardwareRemote(String hardwareRemote) {
		this.hardwareRemote = hardwareRemote;
	}

	public String getGamevideoAddress() {
		return gamevideoAddress;
	}

	public void setGamevideoAddress(String gamevideoAddress) {
		this.gamevideoAddress = gamevideoAddress;
	}

	public double getProductItemPrice() {
		return productItemPrice;
	}

	public void setProductItemPrice(double productItemPrice) {
		this.productItemPrice = productItemPrice;
	}

	public double getProductItemRent() {
		return productItemRent;
	}

	public void setProductItemRent(double productItemRent) {
		this.productItemRent = productItemRent;
	}

	public int getProductItemQty() {
		return productItemQty;
	}

	public void setProductItemQty(int productItemQty) {
		this.productItemQty = productItemQty;
	}

	public double getProductRate() {
		return productRate;
	}

	public void setProductRate(double productRate) {
		this.productRate = productRate;
	}

	public int getAgeFrom() {
		return ageFrom;
	}

	public void setAgeFrom(int ageFrom) {
		this.ageFrom = ageFrom;
	}

	public int getAgeTo() {
		return ageTo;
	}

	public void setAgeTo(int ageTo) {
		this.ageTo = ageTo;
	}
}
