package com.sy.gametime.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserAddress {
	@Expose
	@SerializedName("Code")
	private String userCode;
	@Expose
	@SerializedName("ID")
	private String userAddressID;
	@Expose
	@SerializedName("AddressDistrictsID")
	private String userAddressDistrictsID;
	@Expose
	@SerializedName("AddressDesc")
	private String userAddressDesc;
	@Expose
	@SerializedName("AddressZipCode")
	private String userAddressZipCode;
	@Expose
	@SerializedName("Phone")
	private String userAddressPhone;
	@Expose
	@SerializedName("Contact")
	private String userAddressName;
	@Expose
	@SerializedName("AddressDefault")
	private String userAddressDefault;
	@Expose
	@SerializedName("AddressCrtDate")
	private String userAddressCrtDate;
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String usercode) {
		this.userCode = usercode;
	}
	public String getUserAddressID() {
		return userAddressID;
	}
	public void setUserAddressID(String useraddressID) {
		this.userAddressID = useraddressID;
	}
	public String getUserAddressDistrictsID() {
		return userAddressDistrictsID;
	}
	public void setUserAddressDistrictsID(String useraddressdistrictsID) {
		this.userAddressDistrictsID= useraddressdistrictsID;
	}
	public String getAddressDesc() {
		return userAddressDesc;
	}
	public void setAddressDesc(String useraddressdesc) {
		this.userAddressDesc = useraddressdesc;
	}
	public String getAddressZipCode() {
		return userAddressZipCode;
	}
	public void setAddressZipCode(String useraddresszipcode) {
		this.userAddressZipCode = useraddresszipcode;
	}
	public String getAddressPhone() {
		return userAddressPhone;
	}
	public void setAddressPhone(String useraddressphone) {
		this.userAddressPhone = useraddressphone;
	}
	public String getAddressName() {
		return userAddressName;
	}
	public void setAddressName(String useraddressname) {
		this.userAddressName = useraddressname;
	}
	public String getAddressDefault() {
		return userAddressDefault;
	}
	public void setAddressDefault(String useraddressdefault) {
		this.userAddressDefault = useraddressdefault;
	}
	public String getAddressCrtDate() {
		return userAddressCrtDate;
	}
	public void setAddressCrtDate(String useraddresscrtdate) {
		this.userAddressCrtDate = useraddresscrtdate;
	}
	
}

