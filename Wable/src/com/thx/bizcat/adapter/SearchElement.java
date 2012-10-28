package com.thx.bizcat.adapter;

import android.graphics.Bitmap;

public class SearchElement  {

	private Bitmap bitmap;
	private String title;
	private String contents;
	private String price;
	private String idx;
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	public SearchElement setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
		return this;
	}
	public String getTitle() {
		return title;
	}
	public SearchElement setTitle(String title) {
		this.title = title;
		return this;
	}
	public String getContents() {
		return contents;
	}
	public SearchElement setContents(String contents) {
		this.contents = contents;
		return this;
	}
	public String getPrice() {
		return price;
	}
	public SearchElement setPrice(String price) {
		this.price = price;
		return this;
	}
	public String getIdx() {
		return idx;
	}
	public SearchElement setIdx(String idx) {
		this.idx = idx;
		return this;
	}
	
	
	

}
