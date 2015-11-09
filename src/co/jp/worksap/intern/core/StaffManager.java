package co.jp.worksap.intern.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import co.jp.worksap.intern.global.Message;
import co.jp.worksap.intern.global.Table;
import co.jp.worksap.intern.main.Main;

public class StaffManager {
	public StaffManager() {
		
	}

	public Message readFile(String directory, String filename) {
		/** Read and send the whole file within a Message */
		dir = directory;
		file = filename;
		if (!Pattern.matches(".*\\.[Cc][Ss][Vv]", filename)) {
			System.err.println("THREAD----CORE: not a csv file");
			String[] info = {"FAIL", "The file should be in '.csv' format"};
			return new Message(Message.UI, Message.STAFF_LOAD, info);
		}
		
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

	public Message saveFile(String[] array) {
		/** Save the file */
		if (!Pattern.matches(".*\\.[Cc][Ss][Vv]", file)) {
			System.err.println("THREAD----CORE: not a csv file");
			String[] info = {"FAIL", "The file should be in '.csv' format"};
			return new Message(Message.UI, Message.STAFF_SAVE, info);
		}
		try {
			CSVWriter csvWriter = new CSVWriter(new FileWriter(new File(dir + file)));
			Table t = new Table(array);
			csvWriter.writeNext(t.getHead());
			csvWriter.writeAll(Arrays.asList(t.getTable()));
			csvWriter.close();
			String[] info = {"SUCCESS", "File '" + dir + file + "' is saved."};
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

	private String dir, file;
	
}
