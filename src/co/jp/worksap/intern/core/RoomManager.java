package co.jp.worksap.intern.core;

import java.util.ArrayList;

import co.jp.worksap.intern.global.Floor;

public class RoomManager {
	//TODO The Room Manager module
	public void addAFloor() {
		floors.add(new Floor(floors.size() + 1));
	}
	
	private ArrayList<Floor> floors;
}
