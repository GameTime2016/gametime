package com.sy.gametime.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

	@Expose
	@SerializedName("Code")
	private String  productsCode;
	
	@Expose
	@SerializedName("Name")
	private String productsName;
	
	@Expose
	@SerializedName("Issue")
	private String productsIssue;
	
	@Expose
	@SerializedName("Company")
	private String productsCompany;
	
	@Expose
	@SerializedName("Players")
	private String productsPlayers;
	
	@Expose
	@SerializedName("Desc")
	private String productsDesc;
	
	@Expose
	@SerializedName("Category")
	private String productsCategoryName;
	
	@Expose
	@SerializedName("Picture")
	private String picture;

	public String getProductsCode() {
		return productsCode;
	}

	public void setProductsCode(String productsCode) {
		this.productsCode = productsCode;
	}

	public String getProductsName() {
		return productsName;
	}

	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}

	public String getProductsIssue() {
		return productsIssue;
	}

	public void setProductsIssue(String productsIssue) {
		this.productsIssue = productsIssue;
	}

	public String getProductsCompany() {
		return productsCompany;
	}

	public void setProductsCompany(String productsCompany) {
		this.productsCompany = productsCompany;
	}

	public String getProductsPlayers() {
		return productsPlayers;
	}

	public void setProductsPlayers(String productsPlayers) {
		this.productsPlayers = productsPlayers;
	}

	public String getProductsDesc() {
		return productsDesc;
	}

	public void setProductsDesc(String productsDesc) {
		this.productsDesc = productsDesc;
	}

	public String getProductsCategoryName() {
		return productsCategoryName;
	}

	public void setProductsCategoryName(String productsCategoryName) {
		this.productsCategoryName = productsCategoryName;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
}
