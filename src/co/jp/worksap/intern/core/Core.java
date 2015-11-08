package co.jp.worksap.intern.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import co.jp.worksap.intern.global.*;
import co.jp.worksap.intern.main.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;

public class Core implements Runnable {
	/** Core contains everything run invisible
	 *  There are modules in the core, Room Manager, Staff Manager, etc.*/
	public Core() {
		msgQueue = new MessageQueue();
		rm = new RoomManager();
		sm = new StaffManager();
	}
	
	public Message readFile(String directory, String filename) {
		if (!Pattern.matches(".*\\.[Cc][Ss][Vv]", filename)) {
			System.err.println("THREAD----CORE: not a csv file");
			String[] info = {"FAIL", "The file should be in '.csv' format"};
			return new Message(Message.UI, Message.STAFF_LOAD, info);
		}
		
		//TODO: read the file
		try {
			CSVReader csvReader = new CSVReader(new FileReader(new File(directory + filename)));
			String[] head = csvReader.readNext();
			List<String[]> list = csvReader.readAll();
			csvReader.close();
			return new Message(Message.UI, Message.STAFF_LOAD, new Table(head, (String[][])list.toArray(new String[list.size()][])).getArrayForMessage());
		} catch (FileNotFoundException e) {
			String[] info = {"FAIL", e.getMessage()};
			return new Message(Message.UI, Message.STAFF_LOAD, info);
		} catch (IOException e) {
			e.printStackTrace();
			Main.fault();
		}
		//We should never arrive here
		Main.fault();
		return null;
	}

	private Message saveFile(String[] array) {
		if (!Pattern.matches(".*\\.[Cc][Ss][Vv]", filePath[1])) {
			System.err.println("THREAD----CORE: not a csv file");
			String[] info = {"FAIL", "The file should be in '.csv' format"};
			return new Message(Message.UI, Message.STAFF_SAVE, info);
		}
		try {
			CSVWriter csvWriter = new CSVWriter(new FileWriter(new File(filePath[0] + filePath[1])));
			Table t = new Table(array);
			csvWriter.writeNext(t.getHead());
			csvWriter.writeAll(Arrays.asList(t.getTable()));
			csvWriter.close();
			String[] info = {"SUCCESS", "File '" + filePath[0] + filePath[1] + "' is saved."};
			return new Message(Message.UI,Message.STAFF_SAVE, info);
		} catch (FileNotFoundException e) {
			String[] info = {"FAIL", e.getMessage()};
			return new Message(Message.UI, Message.STAFF_SAVE, info);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			Main.fault();
		}
		//We should never arrive here
		Main.fault();
		return null;
	}

	public void updateSettings() {
		msgQueue.updateQueueLength();
	}
	
	public void addMessage(Message msg) {
		msgQueue.addMessage(msg);
	}
	
	@Override
	public void run() {
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
					filePath[0] = msg.getInfo()[0];
					filePath[1] = msg.getInfo()[1];
					Main.ui.addMessage(readFile(filePath[0],filePath[1]));
					break;
				case Message.STAFF_SAVE:
					Main.ui.addMessage(saveFile(msg.getInfo()));
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
	
	private String[] filePath = new String[2];
	
	private RoomManager rm;
	private StaffManager sm;
}
