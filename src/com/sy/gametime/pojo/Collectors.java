package com.sy.gametime.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Collectors {
	@Expose
	@SerializedName("torsCode")
	private String collectorsCode;
	@Expose
	@SerializedName("UserCode")
	private String collectorsUserCode;
	@Expose
	@SerializedName("Code")
	private String collectorsPrdCode;
	@Expose
	@SerializedName("CrtDate")
	private String collectorsCrtDate;
	@Expose
	@SerializedName("Note")
	private String collectorsNote;
	public String getCollectorsCode() {
		return collectorsCode;
	}
	public void setCollectorsCode(String collectorscode) {
		this.collectorsCode = collectorscode;
	}
	public String getCollectorsUserCode() {
		return collectorsUserCode;
	}
	public void setCollectorsUserCode(String collectorsusercode) {
		this.collectorsUserCode = collectorsusercode;
	}
	public String getCollectorsPrdCode() {
		return collectorsPrdCode;
	}
	public void setCollectorsPrdCode(String collectorsprdcode) {
		this.collectorsPrdCode = collectorsprdcode;
	}
	public String getCollectorsCrtDate() {
		return collectorsCrtDate;
	}
	public void setCollectorsCrtDate(String collectorscrtdate) {
		this.collectorsCrtDate = collectorscrtdate;
	}
	public String getCollectorsNote() {
		return collectorsNote;
	}
	public void setCollectorsNote(String collectorsnote) {
		this.collectorsNote = collectorsnote;
	}
}
