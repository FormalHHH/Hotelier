package co.jp.worksap.intern.global;

import java.util.ArrayList;

public class Floor {
	/** Info of floors with rooms on this floor */
	public Floor(int no) {
		this.setFloor(no);
		rooms = new ArrayList<Room>();
	}
	
	public Room searchARoom(int no) {
		for (Room room:rooms) {
			if (room.getNo() == no) return room;
		}
		return null;
	}
	
	public void addARoom(int no) {
		rooms.add(new Room(no));
	}
	
	public void addAllRoom(int max) {
		for (int no = 1; no <= max; ++no) {
			this.addARoom(no);
		}
	}
	
	public void deleteARoom(int no) throws NullPointerException {
		rooms.remove(this.searchARoom(no));
	}
	
	public void cleanAllRoom() {
		rooms.clear();
	}
	
	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	private ArrayList<Room> rooms;
	private int floor;
}
