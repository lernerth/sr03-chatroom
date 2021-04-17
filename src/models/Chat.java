package models;

import java.util.Arrays;

public class Chat {

	private String name;
	private int id;
	private int ownerId;

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

	@Override
	public String toString() {
		return "Chat [name=" + name + ", id=" + id + ", ownerId=" + ownerId + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
		    return true;
		}
	
		if (!(obj instanceof Chat)) {
		    return false;
		}
	
		Chat other = (Chat) obj;
	
		return name.equals(other.name) && id == other.id && ownerId== other.ownerId;
	}
}
