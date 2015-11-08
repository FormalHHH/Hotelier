package co.jp.worksap.intern.core;

import java.util.ArrayList;

import co.jp.worksap.intern.global.Floor;
import co.jp.worksap.intern.global.Room;

public class RoomManager {
	
	public void addAFloor() {
		floors.add(new Floor(floors.size() + 1));
	}
	
	private ArrayList<Floor> floors;
}
