package com.thx.bizcat.adapter;

public class ChatElement {

	private int type;
	private long chattime;	
	private String text;
	
	
	public int getType() {
		return type;
	}
	public ChatElement setType(int type) {
		this.type = type;
		return this;
	}
	public long getChattime() {
		return chattime;
	}
	public ChatElement setChattime(long chattime) {
		this.chattime = chattime;
		return this;
	}
	public String getText() {
		return text;
	}
	public ChatElement setText(String text) {
		this.text = text;
		return this;
	}
	
	
}
