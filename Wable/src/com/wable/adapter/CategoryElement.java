package com.wable.adapter;

public class CategoryElement {

	private int id;
	private String title;
	private String description;
	private int price;
	private String photo;
	private int type;
	private int order;
	private String due_time;
	private String parent_id;
	
	
	public int getId() {
		return id;
	}
	public CategoryElement setId(int id) {
		this.id = id;
		return this;
	}
	public String getTitle() {
		return title;
	}
	public CategoryElement setTitle(String title) {
		this.title = title;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public CategoryElement setDescription(String description) {
		this.description = description;
		return this;
	}
	public int getPrice() {
		return price;
	}
	public CategoryElement setPrice(int price) {
		this.price = price;
		return this;
	}
	public String getPhoto() {
		return photo;
	}
	public CategoryElement setPhoto(String photo) {
		this.photo = photo;
		return this;
	}
	public int getType() {
		return type;
	}
	public CategoryElement setType(int type) {
		this.type = type;
		return this;
	}
	public int getOrder() {
		return order;
	}
	public CategoryElement setOrder(int order) {
		this.order = order;
		return this;
	}
	public String getDue_time() {
		return due_time;
	}
	public CategoryElement setDue_time(String due_time) {
		this.due_time = due_time;
		return this;
	}
	public String getParent_id() {
		return parent_id;
	}
	public CategoryElement setParent_id(String parent_id) {
		this.parent_id = parent_id;
		return this;
	}
	

}
