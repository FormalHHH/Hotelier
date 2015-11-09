package co.jp.worksap.intern.main;

import co.jp.worksap.intern.core.Core;
//import co.jp.worksap.intern.io.IO;
import co.jp.worksap.intern.ui.UI;

public class Main {
	/** My software is mainly divided to two parts: UI and core
	 *  Both run in a main thread and a support thread
	 *  Use 'Message' to communicate*/
	public Main() {
		//io = new IO();
		ui = new UI();
		core = new Core();
		//new Thread(io).start();
		new Thread(ui).start();
		new Thread(core).start();
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
	public static void fault() {
		System.err.println("###FATAL ERROR### Exiting.");
		System.exit(1);
	}
	
	//public static IO io;
	public static UI ui;
	public static Core core;
}
