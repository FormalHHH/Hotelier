package co.jp.worksap.intern.io;

import java.io.*;
import java.util.*;

import co.jp.worksap.intern.global.*;

public class IO implements Runnable {
	// Obsoleted
	public IO() {
		fileNames = new ArrayList<String>();
	}
	
	public ArrayList<String> getFileNames() {
		return fileNames;
	}

	public void setFileNames(ArrayList<String> fileNames) {
		this.fileNames = fileNames;
	}

	@Override
	public void run() {
		while (true) {
			Message msg = msgQueue.getMessage();
			//TODO: handle the message
		}
		
	}

	private ArrayList<String> fileNames;
	
	private MessageQueue msgQueue;
}
