package models;

import java.util.Arrays;

public class Chat {

	private String name;
	private int id;
	private int ownerId;
	private String desc;
	private String dateStr;

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	@Override
	public String toString() {
		return "Chat [name=" + name + ", id=" + id + ", ownerId=" + ownerId + ", desc=" + desc + ", dateStr=" + dateStr
				+ "]";
	}

}
