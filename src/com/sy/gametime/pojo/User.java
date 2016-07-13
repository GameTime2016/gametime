package com.sy.gametime.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
	
	private String userCode;
	
	@Expose
	@SerializedName("Account")
	private String userAcount;
	
	@Expose
	@SerializedName("NickNam")
	private String userNickName;
	
	@Expose
	@SerializedName("Phone")
	private String userPhone;
	
	@Expose
	@SerializedName("Email")
	private String userEmail;
	
	@Expose
	@SerializedName("Sex")
	private String userSex;
	
	@Expose
	@SerializedName("Birthday")
	private String userBirthday;
	
	@Expose
	@SerializedName("UserName")
	private String userName;
	
	@Expose
	@SerializedName("IDCard")
	private String userIDCard;
	
	private String userZipCode;
	
	@Expose
	@SerializedName("Address")
	private String userAddress;
	
	@Expose
	@SerializedName("Credit")
	private String userCreditRating;
	
	private String userLockState;
	
	private String userCrtDate;
	
	private String userUpdDate;
	
	private String userPassword;
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserAcount() {
		return userAcount;
	}
	public void setUserAcount(String userAcount) {
		this.userAcount = userAcount;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserIDCard() {
		return userIDCard;
	}
	public void setUserIDCard(String userIDCard) {
		this.userIDCard = userIDCard;
	}
	public String getUserZipCode() {
		return userZipCode;
	}
	public void setUserZipCode(String userZipCode) {
		this.userZipCode = userZipCode;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public String getUserCreditRating() {
		return userCreditRating;
	}
	public void setUserCreditRating(String userCreditRating) {
		this.userCreditRating = userCreditRating;
	}
	public String getUserLockState() {
		return userLockState;
	}
	public void setUserLockState(String userLockState) {
		this.userLockState = userLockState;
	}
	public String getUserCrtDate() {
		return userCrtDate;
	}
	public void setUserCrtDate(String userCrtDate) {
		this.userCrtDate = userCrtDate;
	}
	public String getUserUpdDate() {
		return userUpdDate;
	}
	public void setUserUpdDate(String userUpdDate) {
		this.userUpdDate = userUpdDate;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	@Override
	public String toString() {
		return "User [userCode=" + userCode + ", userAcount=" + userAcount + ", userNickName=" + userNickName
				+ ", userPhone=" + userPhone + ", userEmail=" + userEmail + ", userSex=" + userSex + ", userBirthday="
				+ userBirthday + ", userName=" + userName + ", userIDCard=" + userIDCard + ", userZipCode="
				+ userZipCode + ", userAddress=" + userAddress + ", userCreditRating=" + userCreditRating
				+ ", userLockState=" + userLockState + ", userCrtDate=" + userCrtDate + ", userUpdDate=" + userUpdDate
				+ ", userPassword=" + userPassword + "]";
	}
	
	
}
