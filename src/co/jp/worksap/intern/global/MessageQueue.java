package co.jp.worksap.intern.global;

import java.util.concurrent.ArrayBlockingQueue;

public class MessageQueue {
	/** A queue for storing Messages.
	 *  This is the pipe between modules */
	public MessageQueue() {
		this.message = new ArrayBlockingQueue<Message>(Settings.blockingQueueLength); 
	}
	
	public void updateQueueLength() {
		while (!this.message.isEmpty());
		this.message = new ArrayBlockingQueue<Message>(Settings.blockingQueueLength);
	}
	
	public Message getMessage() { 
		Message msg;
		try {
			while ((msg = message.poll()) == null) {
					Thread.sleep(50);
			}
			return msg;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			System.err.println("           after poll");
		}
	}
	
	public void addMessage(Message msg) {
		try {
			while (!message.offer(msg)) {
				Thread.sleep(50);
			};
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			System.err.println("           after offer");
		}
	}
	
	private ArrayBlockingQueue<Message> message;
}
