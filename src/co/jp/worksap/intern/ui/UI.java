package co.jp.worksap.intern.ui;

import co.jp.worksap.intern.global.*;
import co.jp.worksap.intern.main.Main;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

public class UI extends JFrame implements Runnable {
	/** UI contains everything visible
	 *  Settings are separated from the main UI*/
	public UI() {
		msgQueue = new MessageQueue();
		status = 0;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(Settings.windowX, Settings.windowY);
		this.setTitle("Hotelier");
		this.setLayout(new FlowLayout());
		
		msgQueue = new MessageQueue();

		//initBackButton();
		//initSettingsWindow();
		
		//initSettingsPanel();
		//this.add(settingsPanel);
		initMainPanel();
		this.add(mainPanel);
		initRMPanel();
		initSMPanel();
		
		this.setVisible(true);
	}

	private void initSMPanel() {
		smPanel = new JPanel(new BorderLayout());
		JPanel northPanel = new JPanel(new FlowLayout());
		initSettingsButton();
		northPanel.add(settingsButton);
		initBackButton();
		northPanel.add(backButton);
		initLoadButton();
		northPanel.add(loadButton);
		initSaveButton();
		northPanel.add(saveButton);
		smPanel.add(northPanel,BorderLayout.NORTH);
		
		//initStaffPanel();
		//smPanel.add(staffPanel,BorderLayout.CENTER);
		//initStaffTable();
		//smPanel.add(staffTable,BorderLayout.CENTER);
	}

	private void initSaveButton() {
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.core.addMessage(new Message(Message.CORE_STAFF, Message.STAFF_SAVE, t.getArrayForMessage()));
			}
		});
	}

	private void initStaffPanel() {
		staffPanel = new JPanel();
		initStaffScroll();
		staffPanel.add(staffScroll);
	}

	private void initStaffScroll() {
		staffScroll = new JScrollPane();
		initStaffTable();
		staffScroll.setViewportView(staffTable);
	}

	private void initLoadButton() {
		loadButton = new JButton("Load");
		loadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.core.addMessage(
						new Message(Message.CORE_STAFF, Message.STAFF_LOAD, 
						new ArrayList<String>(Arrays.asList(Settings.staffFile))));
			}
		});
	}

	private void initStaffTable() {
		UI.this.t.addTableModelListener(new TableModelListener(){
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					for (int i = 0; i < t.getRowCount(); ++i) {
					//for (int i = e.getFirstRow(); i <= e.getLastRow(); ++i) {
						UI.this.t.setValueAt(
								Integer.parseInt(UI.this.t.getValueAt(i, 6)) * (100 - Math.max(Integer.parseInt(UI.this.t.getValueAt(i, 7)) - Settings.permittedVacationPerMonth, 0) * Settings.finePercentForAbsentDays) / 100,
								i, 8, false);
					}
				}
				UI.this.staffScroll.updateUI();
			}
		});
		staffTable = new JTable(UI.this.t);
	}

	private void initSettingsPanel() {
		settingsPanel = new JPanel();
		initSettingsButton();
		settingsPanel.add(settingsButton);
	}

	private void initSettingsWindow(int type) {
		settingsWindow = new SettingsWindow(type);
	}

	private void initSettingsButton() {
		settingsButton = new JButton("Settings");
		settingsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initSettingsWindow(status);
				settingsWindow.setVisible(true);
				updateSettings();
			}
		});
	}
	
	private void updateSettings() {
		//TODO: update settings
		this.msgQueue.updateQueueLength();
		Main.core.updateSettings();
	}

	private void initBackButton() {
		backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				status = 0;
				UI.this.remove(rmPanel);
				UI.this.remove(smPanel);
				UI.this.add(mainPanel);
				UI.this.validate();
				UI.this.repaint();
			}
		});
	}
	
	private void initMainPanel() {
		mainPanel = new JPanel();
		initRMButton();
		mainPanel.add(rmButton);
		initSMButton();
		mainPanel.add(smButton);
	}
	
	private void initSMButton() {
		smButton = new JButton("Staff Manager");
		smButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				status = SettingsWindow.STAFF;
				UI.this.remove(mainPanel);
				UI.this.add(smPanel);
				UI.this.validate();
				UI.this.repaint();
			}
		});
	}

	private void initRMPanel() {
		rmPanel = new JPanel(new FlowLayout());
		initSettingsButton();
		rmPanel.add(settingsButton);
		initBackButton();
		rmPanel.add(backButton);
	}
	
	private void initRMButton() {
		rmButton = new JButton("Room Manager");
		rmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				status = SettingsWindow.ROOM;
				UI.this.remove(mainPanel);
				UI.this.add(rmPanel);
				UI.this.validate();
				UI.this.repaint();
			}
		});
	}

	public void addMessage(Message msg) {
		msgQueue.addMessage(msg);
	}
	
	@Override
	public void run() {
		while (true) {
			System.err.println("THREAD----UI  : waiting for message");
			Message msg = msgQueue.getMessage();
			System.err.println("THREAD----UI  : get a message");
			//TODO: handle the message
			switch (msg.getDest()) {
			case Message.UI:
				//TODO: handle the message
				switch (msg.getType()) {
				case Message.STAFF_LOAD:
					if (msg.getInfo()[0].equals("FAIL")) {
						JOptionPane.showMessageDialog(null, msg.getInfo()[1], msg.getInfo()[0], JOptionPane.ERROR_MESSAGE); 
					} else {
						t = new Table(msg.getInfo());
						
						try {
							smPanel.remove(staffPanel);
						} catch (NullPointerException e) {
							System.err.println("MAIN------UI  : first time to show the table");
						}
						initStaffPanel();
						smPanel.add(staffPanel);
						this.validate();
						this.repaint();
						//TODO: show the file
					}
					break;
				case Message.STAFF_SAVE:
					JOptionPane.showMessageDialog(null, msg.getInfo()[1], msg.getInfo()[0], JOptionPane.PLAIN_MESSAGE); 
					break;
				default:
					Main.fault();
				}
				break;
			default:
				Main.fault();
			}
		}
	}
	
	private SettingsWindow settingsWindow;
	
	private MessageQueue msgQueue;
	
	private JPanel rmPanel;
	private JPanel smPanel;
	private JPanel mainPanel;
	private JPanel settingsPanel;
	
	private JButton rmButton;
	private JButton smButton;
	
	private JButton backButton;
	
	private JButton settingsButton;
	
	private JButton loadButton;
	private JButton saveButton;
	private JPanel staffPanel;
	private JScrollPane staffScroll;
	private JTable staffTable;
	private Table t;
	
	private static int status;
}
