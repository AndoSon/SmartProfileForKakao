package com.hanyang.swengineering.item;


public class FriendListItem {
	
	boolean group;
	
	String groupTitle;
	
	String name;
	int imageResourceId;
	
	public FriendListItem(boolean group, String groupTitle) {
		this.group = group;
		this.groupTitle = groupTitle;
	} 
	
	public FriendListItem(boolean group, String name, int imageResourceId) {
		this.name = name;
		this.imageResourceId = imageResourceId;
	}
	
	public boolean isGroup() {
		return group==true;
	}
	
	public String getGroupTitle() {
		return groupTitle;
	}
	
	public String getName() {
		return name;
	}
	public int getImageResourceId() {
		return imageResourceId;
	}

}
