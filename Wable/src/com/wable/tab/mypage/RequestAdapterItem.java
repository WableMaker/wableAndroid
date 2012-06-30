package com.wable.tab.mypage;

public class RequestAdapterItem {
	
	private int callCode;
	private int revcCode;
	
	private String time;
	private String tel;
	
	private int image;
	
	public int getCallCode() {
		return callCode;
	}
	public RequestAdapterItem setCallCode(int callCode) {
		this.callCode = callCode;
		return this;
	}
	public int getRevcCode() {
		return revcCode;
	}
	public RequestAdapterItem setRevcCode(int revcCode) {
		this.revcCode = revcCode;
		return this;
	}
	public String getTime() {
		return time;
	}
	public RequestAdapterItem setTime(String time) {
		this.time = time;
		return this;
	}
	public String getTel() {
		return tel;
	}
	public RequestAdapterItem setTel(String tel) {
		this.tel = tel;
		return this;
	}
	public int getImage() {
		return image;
	}
	public RequestAdapterItem setImage(int image) {
		this.image = image;
		return this;
	}


}
