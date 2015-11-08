package co.jp.worksap.intern.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import co.jp.worksap.intern.global.Settings;
import co.jp.worksap.intern.main.Main;


public class SettingsWindow extends JFrame {
	public SettingsWindow(int type) {
		this.type = type;
		this.setTitle("Settings");
		//this.setLayout(new BorderLayout(40,30));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(640, 280);
		initMainPanel();
		this.add(mainPanel);
	}
	
	private void initMainPanel() {
		mainPanel = new JPanel(new BorderLayout());
		switch (type) {
		case ROOM:
			initQueuePanel();
			mainPanel.add(queuePanel,BorderLayout.CENTER);
			break;
		case STAFF:
			initFilePanel("Staff info");
			mainPanel.add(filePanel,BorderLayout.CENTER);
			break;
		default:
			Main.fault();
		}
		initButtonPanel();
		mainPanel.add(buttonPanel,BorderLayout.SOUTH);
	}
	
	private void initFilePanel(String string) {
		filePanel = new JPanel(new FlowLayout());
		filePanel.add(new JLabel(string));
		initFileTextField();
		filePanel.add(fileTextField);
		initbrowseButton();
		filePanel.add(browseButton);
	}

	private void initbrowseButton() {
		browseButton = new JButton("...");
		browseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) { 
				loadFile = new FileDialog(SettingsWindow.this,"",FileDialog.LOAD);
				loadFile.setVisible(true);
				loadFile.addWindowListener(new WindowAdapter() {
					@SuppressWarnings("unused")
					public void WindowClosing(WindowEvent e) {
						setVisible(false);
					}
				});
				fileTextField.setText(loadFile.getDirectory() + loadFile.getFile());
			}
		});
	}

	private void initFileTextField() {
		fileTextField = new JTextField(50);
		fileTextField.setEditable(false);
		fileTextField.setText(Settings.staffFile[0] + Settings.staffFile[1]);
		fileTextField.setHorizontalAlignment(JTextField.RIGHT);
	}

	private void initButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		initOKButton();
		buttonPanel.add(okButton);
		initCancelButton();
		buttonPanel.add(cancelButton);
		initApplyButton();
		buttonPanel.add(applyButton);
	}

	private void initApplyButton() {
		applyButton = new JButton("Apply");
		applyButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				applyFunction();
			}
		});
	}

	private void initCancelButton() {
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelFunction();
			}
		});
	}

	private void initOKButton() {
		okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				okFunction();
			}
		});
	}
	
	private void initQueuePanel() {
		queuePanel = new JPanel();
		queuePanel.setLayout(new BorderLayout());
		initQueueLabel();
		queuePanel.add(queueLabel);
		initQueueTextField();
		queuePanel.add(queueTextField,BorderLayout.EAST);
		/*queuePanel.add(new JPanel(),BorderLayout.NORTH);
		queuePanel.add(new JLabel(),BorderLayout.SOUTH);*/
	}

	private void initQueueLabel() {
		queueLabel = new JLabel("BlockingQueue Length:");
	}

	private void initQueueTextField() {
		queueTextField = new JTextField(20);
		queueTextField.setHorizontalAlignment(JTextField.RIGHT);
		queueTextField.setText(new Integer(Settings.blockingQueueLength).toString());
	}
	
	private void okFunction() {
		applyFunction();
		cancelFunction();
	}
	
	private void cancelFunction() {
		this.dispose();
	}
	
	private void applyFunction() {
		switch (type) {
		case ROOM:
			Settings.blockingQueueLength = Integer.parseInt(queueTextField.getText());
			break;
		case STAFF:
			//TODO: accept staff csv file
			Settings.staffFile[0] = loadFile.getDirectory();
			Settings.staffFile[1] = loadFile.getFile();
			break;
		default:
			Main.fault();
		}
	}
	
	private JTextField queueTextField;
	private JLabel queueLabel;
	private JPanel queuePanel;
	
	private JTextField fileTextField;
	private JLabel describeLabel;
	private JButton browseButton;
	private JPanel filePanel;
	
	private JButton okButton;
	private JButton cancelButton;;
	private JButton applyButton;
	private JPanel buttonPanel;
	
	private JPanel mainPanel;
	
	private int type;
	
	private FileDialog loadFile;
	
	public static final int ROOM = 1;
	public static final int STAFF = 2;
}