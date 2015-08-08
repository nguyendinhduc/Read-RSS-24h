package com.ducnd.my_item;

public class MyItem_Link {
	private String name;
	private int idIcon;
	private String linkRSS;

	public MyItem_Link(int idIcon, String name, String linkRSS) {
		this.idIcon = idIcon;
		this.name = name;
		this.linkRSS = linkRSS;
	}

	public MyItem_Link(int idIcon, String name) {
		this.idIcon = idIcon;
		this.name = name;
		this.linkRSS = null;
	}

	public MyItem_Link(String name, String linkRSS) {
		this.idIcon = -1;
		this.name = name;
		this.linkRSS = linkRSS;
	}

	public MyItem_Link(String name) {
		this.idIcon = -1;
		this.name = name;
		this.linkRSS = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIdIcon() {
		return idIcon;
	}

	public void setIdIcon(int idIcon) {
		this.idIcon = idIcon;
	}

	public String getlinkRSS() {
		return linkRSS;
	}

	public void setlinkRSS(String linkRSS) {
		this.linkRSS = linkRSS;
	}

}
