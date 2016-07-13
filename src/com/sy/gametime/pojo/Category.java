package com.sy.gametime.pojo;

import com.google.gson.annotations.SerializedName;

public class  Category{
	
	@SerializedName("ID")
	private String ID;
	@SerializedName("Name")
	private String Name;

	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
}
