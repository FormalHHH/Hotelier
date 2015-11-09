package co.jp.worksap.intern.global;

import java.util.*;

public class Message {
	/** Messages between the UI and the core*/
	public Message(int dest,int type, String[] info) {
		this.setDest(dest);
		this.setType(type);
		this.setInfo(info);
	}
	public Message(int dest, int type, ArrayList<String> info) {
		this.setDest(dest);
		this.setType(type);
		this.setInfo(info);
	}
	
	public int getType() {
		return this.type;
	}
	
	public String[] getInfo() {
		return this.info;
	}
	
	public int getDest() {
		return this.dest;
	}

	public void setDest(int dest) {
		this.dest = dest;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setInfo(ArrayList<String> info) {
		this.info = new String[info.size()];
		info.toArray(this.info);
	}
	
	public void setInfo(String[] info) {
		this.info = info;
	}

	private int dest;
	private int type;
	private String[] info;

	public final static int UI = 0;
	public final static int CORE_ROOM = 1;
	public final static int CORE_STAFF = 2;
	
	public final static int ROOM_LOAD = 0000;
	public final static int ROOM_SAVE = 0001;
	public final static int ROOM_ADD = 0002;
	public final static int ROOM_MODIFY = 0003;
	public final static int ROOM_DELETE = 0004;
	
	public final static int STAFF_LOAD = 1000;
	public final static int STAFF_SAVE = 1001;
	public final static int STAFF_ADD = 1002;
	public final static int STAFF_MODIFY = 1003;
	public final static int STAFF_DELETE = 1004;
}
