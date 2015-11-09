package co.jp.worksap.intern.core;

import co.jp.worksap.intern.global.*;
import co.jp.worksap.intern.main.Main;

public class Core implements Runnable {
	/** Core contains everything run invisible
	 *  There are modules in the core, Room Manager, Staff Manager, etc. */
	public Core() {
		msgQueue = new MessageQueue();
		rm = new RoomManager();
		sm = new StaffManager();
	}
	
	public void updateSettings() {
		msgQueue.updateQueueLength();
	}
	
	public void addMessage(Message msg) {
		/** Add a new Message into my queue. Used by the other module */
		msgQueue.addMessage(msg);
	}
	
	@Override
	public void run() {
		/** The main function for the thread of receiving Messages
		 *  Outputs are for debugging */
		while (true) {
			System.err.println("THREAD----CORE: waiting for message");
			Message msg = msgQueue.getMessage();
			System.err.println("THREAD----CORE: get a message");
			//TODO: handle the message
			switch (msg.getDest()) {
			case Message.CORE_ROOM:
				//TODO:
				break;
			case Message.CORE_STAFF:
				switch (msg.getType()) {
				case Message.STAFF_LOAD:
					Main.ui.addMessage(sm.readFile(msg.getInfo()[0],msg.getInfo()[1]));
					break;
				case Message.STAFF_SAVE:
					Main.ui.addMessage(sm.saveFile(msg.getInfo()));
					break;
				}
				break;
			//case Message.UI:
			default:
				Main.fault();
			}
		}
	}
	
	private MessageQueue msgQueue;
	
	private RoomManager rm;
	private StaffManager sm;
}
