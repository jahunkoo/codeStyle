package com.android.jahhunkoo.codestyle.dto;

public class BigRegionDTO {

	private int code;
	private String regionName;
	
	public BigRegionDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BigRegionDTO(int code, String regionName) {
		super();
		this.code = code;
		this.regionName = regionName;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	@Override
	public String toString() {
		return "BigRegion [code=" + code + ", regionName=" + regionName + "]";
	}
	
	
	
}
