package co.jp.worksap.intern.global;

public class Room {
	/** Info of rooms */
	public Room(int no) {
		this.setNo(no);
		this.setEmpty(true);
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	private int floor;
	private int no;
	private boolean empty;
}
