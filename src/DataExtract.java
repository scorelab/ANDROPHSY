// Copyright 2015 Indeewari Akarawita
//
//    This file is a part of Androspy
//
//    Androspy is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    This program is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with this program.  If not, see <http://www.gnu.org/licenses/>.

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * @author indeewari
 *
 */
public class DataExtract extends Composite {
	private Text txtExtractStatus;
	private ProgressBar pb1;
	private Button btnHexDump;
	private ArrayList<String> checked = new ArrayList<String>();
	private Button btnKeywordSearch;
	private Button btnExtractString;
	private Button btnUserAccountDetails;
	private Button lblBrowserArtifacts;
	private Button btnSms;
	private Button btnEvenLog;
	private Button btnBluetoothData;
	private Label lblBrowser;
	private Button lblWirelessNetworkArtifacts;
	private Button btnContacts;
	private Button btnYoutube;
	private Button btnLogs;
	private Button btnFacebook;
	private Button btnMap;
	private Button btnGmail;
	private Button btnCalendar;
	private Button btnSkype;
	private Button btnViber;
	private Label lblDataExtraction;
	private Button lblPrimaryExtraction;
	private Button lblBasicApplicationData;
	private Label lblLogs;
	private Button btnFileCarving;
	private Label lblUserAccoutnsAnd;
	private Button btnGoogleChrome;
	private Button btnDefaultBrowser;
	private Button btnChromeDlg;
	private Button btnDefBrowserDlg;
	private Button btnWifiPasswordswpapskwep;
	private Button btnFileCarvingDlg;
	private Button btnExtractStringDlg;
	private Button btnLinkedinDlg;
	private Button btnLinkedin;
	private Button btnFacebookDlg;
	private Button btnWhatsApp;
	private Button btnSaveSelectionTemplate;
	private Button lblSocialNetworkingApplications;
	private Button btnFirefox;
	private Button btnFirefoxDlg;
	private Button btnSkypeDlg;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public DataExtract(Composite parent, int style) {
		super(parent, style);

		SelectionListener btnsellistener = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Button btnModel = ((Button) arg0.widget);
				if (btnModel != null) {
					String sModel = ((Button) arg0.widget).getText();
					if (((Button) arg0.widget).getSelection()) {
						if (sModel == "Google Chrome") {
							ExtractOptiondlg optiondlg = new ExtractOptiondlg(
									getShell(), SWT.NONE, "CHROME", checked);
							optiondlg.open();
							btnModel.setSelection(false);
						} else if (sModel == "Default Browser") {
							ExtractOptiondlg optiondlg = new ExtractOptiondlg(
									getShell(), SWT.NONE, "DEFAULT", checked);
							optiondlg.open();
						} else if (sModel.equals("LinkedIn")){
							ExtractOptiondlg optiondlg = new ExtractOptiondlg(
									getShell(), SWT.NONE, "LINKEDIN", checked);
							optiondlg.open();
						} else if (sModel.equals("Skype")){
							ExtractOptiondlg optiondlg = new ExtractOptiondlg(
									getShell(), SWT.NONE, "SKYPE", checked);
							optiondlg.open();
						} else {
							checked.add(sModel);
						}
					} else {
						checked.remove(sModel);
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		};
		setLayout(null);

		lblDataExtraction = new Label(this, SWT.NONE);
		lblDataExtraction.setBounds(352, 10, 141, 24);
		lblDataExtraction.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblDataExtraction.setText("Data Extraction");

		lblPrimaryExtraction = new Button(this, SWT.CHECK);
		lblPrimaryExtraction.setFont(SWTResourceManager.getFont("Ubuntu", 11, SWT.NORMAL));
		lblPrimaryExtraction.setBounds(5, 49, 170, 17);
		lblPrimaryExtraction.setText("Primary Extraction");

		btnKeywordSearch = new Button(this, SWT.CHECK);
		btnKeywordSearch.setBounds(27, 72, 133, 24);
		btnKeywordSearch.setText("Keyword Search");
		btnKeywordSearch.addSelectionListener(btnsellistener);

		btnFileCarving = new Button(this, SWT.CHECK);
		btnFileCarving.setBounds(27, 96, 103, 24);
		btnFileCarving.setText("File Carving");
		btnFileCarving.addSelectionListener(btnsellistener);

		btnFileCarvingDlg = new Button(this, SWT.NONE);
		btnFileCarvingDlg.setFont(SWTResourceManager.getFont("Ubuntu", 10, SWT.NORMAL));
		btnFileCarvingDlg.setText("customize");
		btnFileCarvingDlg.setBounds(158, 96, 72, 23);
		btnFileCarvingDlg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ExtractOptiondlg optiondlg = new ExtractOptiondlg(
						getShell(), SWT.NONE, "CARVING", checked);
				optiondlg.open();				
				File file = new File(AndrospyMain.gb_CasePath + "Acquire/carving.conf");
				if(file.exists() && (file.length() > 0)){
					btnFileCarving.setSelection(true);
					checked.add("FILE_CARVING");
				}				
			}
		});
		
		btnExtractString = new Button(this, SWT.CHECK);
		btnExtractString.setBounds(27, 122, 120, 24);
		btnExtractString.setText("Extract String");
		btnExtractString.addSelectionListener(btnsellistener);
		
		btnExtractStringDlg = new Button(this, SWT.NONE);
		btnExtractStringDlg.setFont(SWTResourceManager.getFont("Ubuntu", 10, SWT.NORMAL));
		btnExtractStringDlg.setText("customize");
		btnExtractStringDlg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ExtractOptiondlg optiondlg = new ExtractOptiondlg(
						getShell(), SWT.NONE, "EXTRACTSTRING", checked);
				optiondlg.open();
				btnExtractString.setSelection(true);
			}
		});
		btnExtractStringDlg.setBounds(158, 123, 72, 23);
		
		
		btnUserAccountDetails = new Button(this, SWT.CHECK);
		btnUserAccountDetails.setBounds(300, 49, 170, 17);
		btnUserAccountDetails.setText("User Credentials");
		btnUserAccountDetails.addSelectionListener(btnsellistener);

		lblBasicApplicationData = new Button(this, SWT.CHECK);
		lblBasicApplicationData.setBounds(5, 182, 183, 17);
		lblBasicApplicationData.setText("Basic Application Data");

		btnContacts = new Button(this, SWT.CHECK);
		btnContacts.setBounds(27, 205, 86, 24);
		btnContacts.setText("Contacts");
		btnContacts.addSelectionListener(btnsellistener);

		btnSms = new Button(this, SWT.CHECK);
		btnSms.setBounds(27, 229, 55, 24);
		btnSms.setText("SMS");
		btnSms.addSelectionListener(btnsellistener);

		btnCalendar = new Button(this, SWT.CHECK);
		btnCalendar.setBounds(27, 253, 86, 24);
		btnCalendar.setText("Calendar");
		btnCalendar.addSelectionListener(btnsellistener);

		btnGmail = new Button(this, SWT.CHECK);
		btnGmail.setBounds(27, 277, 65, 24);
		btnGmail.setText("Gmail");
		btnGmail.addSelectionListener(btnsellistener);

		btnYoutube = new Button(this, SWT.CHECK);
		btnYoutube.setBounds(27, 299, 84, 24);
		btnYoutube.setText("YouTube");
		btnYoutube.addSelectionListener(btnsellistener);

		btnMap = new Button(this, SWT.CHECK);
		btnMap.setBounds(27, 323, 107, 24);
		btnMap.setText("Google Map");
		btnMap.addSelectionListener(btnsellistener);
		
		lblSocialNetworkingApplications = new Button(this, SWT.CHECK);
		lblSocialNetworkingApplications.setBounds(300, 182, 226, 17);
		lblSocialNetworkingApplications.setText("Social Networking Applications");
		
		btnFacebook = new Button(this, SWT.CHECK);
		btnFacebook.setBounds(325, 205, 91, 24);
		btnFacebook.setText("Facebook");
		btnFacebook.addSelectionListener(btnsellistener);

		btnFacebookDlg = new Button(this, SWT.NONE);
		btnFacebookDlg.setFont(SWTResourceManager.getFont("Ubuntu", 10, SWT.NORMAL));
		btnFacebookDlg.setText("customize");
		btnFacebookDlg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ExtractOptiondlg optiondlg = new ExtractOptiondlg(
						getShell(), SWT.NONE, "FACEBOOK", checked);
				optiondlg.open();
				btnFacebook.setSelection(true);
			}
		});
		btnFacebookDlg.setBounds(422, 205, 72, 23);
		
		btnLinkedin = new Button(this, SWT.CHECK);
		btnLinkedin.setBounds(325, 229, 91, 24);
		btnLinkedin.setText("LinkedIn");
		btnLinkedin.addSelectionListener(btnsellistener);
		
		btnLinkedinDlg = new Button(this, SWT.CENTER);
		btnLinkedinDlg.setFont(SWTResourceManager.getFont("Ubuntu", 10, SWT.NORMAL));
		btnLinkedinDlg.setText("customize");
		btnLinkedinDlg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ExtractOptiondlg optiondlg = new ExtractOptiondlg(
						getShell(), SWT.NONE, "LINKEDIN", checked);
				optiondlg.open();
				btnLinkedin.setSelection(true);
			}
		});
		btnLinkedinDlg.setBounds(421, 230, 72, 23);
		
		final Button btnSkype = new Button(this, SWT.CHECK);
		btnSkype.setBounds(325, 253, 78, 24);
		btnSkype.setText("Skype");
		btnSkype.addSelectionListener(btnsellistener);
		
		btnSkypeDlg = new Button(this, SWT.CENTER);
		btnSkypeDlg.setFont(SWTResourceManager.getFont("Ubuntu", 10, SWT.NORMAL));
		btnSkypeDlg.setText("customize");
		btnSkypeDlg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ExtractOptiondlg optiondlg = new ExtractOptiondlg(
						getShell(), SWT.NONE, "SKYPE", checked);
				optiondlg.open();
				btnSkype.setSelection(true);
			}
		});
		btnSkypeDlg.setBounds(421, 255, 72, 23);
		
		lblBrowserArtifacts = new Button(this, SWT.CHECK);
		lblBrowserArtifacts.setBounds(617, 182, 158, 17);
		lblBrowserArtifacts.setText("Browser Artifacts");
		
		btnDefaultBrowser = new Button(this, SWT.CHECK);
		btnDefaultBrowser.setBounds(640, 205, 135, 24);
		btnDefaultBrowser.setText("Default Browser");
		btnDefaultBrowser.addSelectionListener(btnsellistener);
		
		btnDefBrowserDlg = new Button(this, SWT.NONE);
		btnDefBrowserDlg.setBounds(788, 205, 72, 23);
		btnDefBrowserDlg.setText("customize");
		btnDefBrowserDlg.setFont(SWTResourceManager.getFont("Ubuntu", 10, SWT.NORMAL));
		btnDefBrowserDlg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ExtractOptiondlg optiondlg = new ExtractOptiondlg(
						getShell(), SWT.NONE, "DEFAULT", checked);
				optiondlg.open();
				btnDefaultBrowser.setSelection(true);
			}
		});
		
		btnGoogleChrome = new Button(this, SWT.CHECK);
		btnGoogleChrome.setBounds(640, 229, 130, 24);
		btnGoogleChrome.setText("Google Chrome");
		btnGoogleChrome.addSelectionListener(btnsellistener);

		btnChromeDlg = new Button(this, SWT.NONE);
		btnChromeDlg.setFont(SWTResourceManager.getFont("Ubuntu", 10, SWT.NORMAL));
		btnChromeDlg.setBounds(788, 229, 72, 23);
		btnChromeDlg.setText("customize");
		btnChromeDlg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ExtractOptiondlg optiondlg = new ExtractOptiondlg(
						getShell(), SWT.NONE, "CHROME", checked);
				optiondlg.open();
				btnGoogleChrome.setSelection(true);
			}
		});
		
		btnFirefox = new Button(this, SWT.CHECK);
		btnFirefox.setBounds(640, 253, 115, 24);
		btnFirefox.setText("Fire Fox");
		btnFirefox.addSelectionListener(btnsellistener);
		
		btnFirefoxDlg = new Button(this, SWT.RIGHT);
		btnFirefoxDlg.setFont(SWTResourceManager.getFont("Ubuntu", 10, SWT.NORMAL));
		btnFirefoxDlg.setText("customize");
		btnFirefoxDlg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ExtractOptiondlg optiondlg = new ExtractOptiondlg(
						getShell(), SWT.NONE, "Firefox", checked);
				optiondlg.open();
				btnFirefox.setSelection(true);
			}
		});
		btnFirefoxDlg.setBounds(788, 253, 72, 23);
		
		lblWirelessNetworkArtifacts = new Button(this, SWT.CHECK);
		lblWirelessNetworkArtifacts.setBounds(5, 397, 225, 17);
		lblWirelessNetworkArtifacts.setText("Wireless Network Artifacts");

		btnBluetoothData = new Button(this, SWT.CHECK);
		btnBluetoothData.setBounds(27, 420, 128, 24);
		btnBluetoothData.setText("Bluetooth Data");
		btnBluetoothData.addSelectionListener(btnsellistener);

		btnWifiPasswordswpapskwep = new Button(this, SWT.CHECK);
		btnWifiPasswordswpapskwep.setBounds(27, 444, 257, 24);
		btnWifiPasswordswpapskwep.setText("Wi-Fi Passwords (WPA-PSK/WEP)");
		btnWifiPasswordswpapskwep.addSelectionListener(btnsellistener);
		
		btnEvenLog = new Button(this, SWT.CHECK);
		btnEvenLog.setBounds(300, 390, 133, 24);
		btnEvenLog.setText("Event Logs");
		btnEvenLog.addSelectionListener(btnsellistener);
		
		Button btnExtractFeature = new Button(this, SWT.NONE);
		btnExtractFeature.setBounds(676, 517, 116, 37);
		btnExtractFeature.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (checked.size() <= 0) {
					return;
				}
				System.out.println("inside listener " + checked.size());
				if(btnSaveSelectionTemplate.getSelection()){
					SaveTemplate(checked);
				}
				pb1.setSelection(1);
				pb1.setMaximum(checked.size() + 1);
				Operation operation = new Operation(AndrospyMain.shell, pb1,
						checked);
				operation.start();	
			}
		});

		btnExtractFeature.setText("Extract");

		txtExtractStatus = new Text(this, SWT.BORDER | SWT.MULTI);
		txtExtractStatus.setBounds(5, 584, 935, 60);

		pb1 = new ProgressBar(this, SWT.HORIZONTAL | SWT.SMOOTH);
		pb1.setBounds(5, 560, 935, 14);
		pb1.setMinimum(0);
		
		btnSaveSelectionTemplate = new Button(this, SWT.CHECK);
		btnSaveSelectionTemplate.setBounds(463, 525, 206, 29);
		btnSaveSelectionTemplate.setText("Save Selection as Template");
		
		Button btnAll = new Button(this, SWT.RADIO);
		btnAll.setBounds(617, 33, 115, 17);
		btnAll.setText("All");
		
		Button btnSelectFromTemplate = new Button(this, SWT.RADIO);
		btnSelectFromTemplate.setBounds(617, 49, 183, 17);
		btnSelectFromTemplate.setText("Select from Template");
		btnSelectFromTemplate.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					String line = "";
					File file = new File("files/selectiontemplate.txt");
					if(file.exists()){
						BufferedReader buf = new BufferedReader(new FileReader(file));
						while((line = buf.readLine())!=null){
							checkSelection(line);
							checked.add(line);
						}
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});	
	
	}

	
	protected void checkSelection(String line) {
		if(line.equals("DEFAULT_BROWSER_HISTORY")){
			btnDefaultBrowser.setSelection(true);
		} else if(line == "DEFAULT_BROWSER_HISTORY"){
			System.out.println("==");
			btnDefaultBrowser.setSelection(true);
		}
	}

	protected void SaveTemplate(ArrayList<String> opt) {
		try {
			PrintWriter writer = new PrintWriter("files/selectiontemplate.txt");
			Iterator<String> it = opt.iterator();
			while(it.hasNext()){
				writer.println(it.next());				
			}	
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}

class Operation extends Thread {
	private ProgressBar pb;
	private Shell shell;
	private ArrayList<String> checked;

	public Operation(Shell shell, ProgressBar p, ArrayList<String> checked) {
		this.pb = p;
		this.shell = shell;
		System.out.println("count inside constructor " + checked.size());
		this.checked = checked;

	}

	protected void startExtract() {
		int count = checked.size();
		System.out.println("count " + count);
		Iterator<String> it = checked.iterator();
		while (it.hasNext()) {
			String option = it.next();
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			AndrospyLog.Logdata(sf.format(new Date()), "Extract " + option);

			if (option == "Hex dump") {
				System.out.println("Generating hex file");
				new AdbExecCmd().generateHexdump(AndrospyMain.gb_CasePath);
				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Summary") {
				extractData(AndrospyMain.gb_CasePath + "Acquire/data.dd");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Keyword Search") {
				System.out.println("Generate String file");
				new AdbExecCmd()
						.CreateStringFile(AndrospyMain.gb_CasePath + "Acquire/data.dd");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "FILE_CARVING") {
				new AdbExecCmd().recoverDeleted(AndrospyMain.gb_CasePath
						+ "Acquire/data.dd");
				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});

			} else if (option == "Device Information") {
				AdbExecCmd cmd = new AdbExecCmd();
				DeviceTemplate devicetemplate = new DeviceTemplate();
				cmd.getDeviceBasicInfo(devicetemplate);
				cmd.getPackageList();

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Application List") {
				new AdbExecCmd().getPackageList();
				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "User Credentials") {
				new AndrospyUtility().readRecords("Accounts");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Contacts") {
				new AndrospyUtility().readRecords("Contacts");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "SMS") {
				new AndrospyUtility().readRecords("SMS");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Gmail") {
				new AndrospyUtility().readRecords("Gmail");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Calendar") {
				new AndrospyUtility().readRecords("Calendar");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "YouTube") {
				new AndrospyUtility().readRecords("YouTube");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Google Map") {
				new AndrospyUtility().readRecords("Map");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("FACEBOOK_MESSAGES")) {
				new AndrospyUtility().readRecords("FACEBOOK_MESSAGES");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("FACEBOOK_NOTIFICATIONS")) {
				new AndrospyUtility().readRecords("FACEBOOK_NOTIFICATIONS");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("FACEBOOK_CONTACTS")) {
				new AndrospyUtility().readRecords("FACEBOOK_CONTACTS");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("LINKEDIN_CONNECTIONS")){
				new AndrospyUtility().readRecords("LINKEDIN_CONNECTIONS");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("LINKEDIN_INVITATIONS")){
				new AndrospyUtility().readRecords("LINKEDIN_INVITATIONS");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("LINKEDIN_MESSAGES")){
				new AndrospyUtility().readRecords("LINKEDIN_MESSAGES");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("LINKEDIN_PROFILE")){
				new AndrospyUtility().readRecords("LINKEDIN_PROFILE");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("Skype Account")) {
				new AndrospyUtility().readRecords("Skype Account");
				
				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
				
			} else if (option.equals("Skype Calls")) {
				new AndrospyUtility().readRecords("Skype Calls");
				
				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
				
			} else if (option.equals("Skype Chats")) {
				new AndrospyUtility().readRecords("Skype Chats");
				
				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
				
			} else if (option.equals("Skype Contacts")) {
				new AndrospyUtility().readRecords("Skype Contacts");
				
				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
				
			} else if (option.equals("Skype Messages")) {
				new AndrospyUtility().readRecords("Skype Messages");
				
				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
				
			} else if (option.equals("DEFAULT_BROWSER_HISTORY")) {
				new AndrospyUtility().readRecords("DEFAULT_BROWSER_HISTORY");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("Firefox_BROWSER_HISTORY")) {
				new AndrospyUtility().readRecords("Firefox_BROWSER_HISTORY");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("CHROME_BROWSER_HISTORY")) {
				new AndrospyUtility().readRecords("CHROME_BROWSER_HISTORY");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("DEFAULT_BOOKMARKS")) {
				new AndrospyUtility().readRecords("DEFAULT_BOOKMARKS");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("Firefox_BOOKMARKS")) {
				new AndrospyUtility().readRecords("Firefox_BOOKMARKS");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
				
			} else if (option.equals("DEFAULT_COOKIES")) {
				new AndrospyUtility().readRecords("DEFAULT_COOKIES");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("CHROME_COOKIES")) {
				new AndrospyUtility().readRecords("CHROME_COOKIES");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("Firefox_COOKIES")) {
				new AndrospyUtility().readRecords("Firefox_COOKIES");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("OPERA_COOKIES")) {
				new AndrospyUtility().readRecords("OPERA_COOKIES");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("DEFAULT_WEB_SEARCH")) {
				new AndrospyUtility().readRecords("DEFAULT_WEB_SEARCH");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("CHROME_WEB_SEARCH")) {
				new AndrospyUtility().readRecords("CHROME_WEB_SEARCH");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("Firefox_WEB_SEARCH")) {
				new AndrospyUtility().readRecords("Firefox_WEB_SEARCH");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
				
			} else if (option.equals("DEFAULT_WEB_FORM_DATA")) {
				new AndrospyUtility().readRecords("DEFAULT_WEB_FORM_DATA");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option.equals("CHROME_CREDIT_CARD")) {
				new AndrospyUtility().readRecords("CHROME_CREDIT_CARD");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Event Logs") {
				new AndrospyUtility().readRecords("EventLog");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Bluetooth Data") {
				readBlutoothInfo();
				new AndrospyUtility().readRecords("Bluetooth");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Wi-Fi Passwords (WPA-PSK/WEP)") {
				readWifiData();

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (pb.isDisposed()) {
							return;
						}
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			}
		}
		checked.clear();
	}

	public String processSpecialCharacter(String in) {
		String out = in.replaceAll("'", "\'");
		return out;
	}

	private void readBlutoothInfo() {
		String bmac = "", line = "";
		String filename = AndrospyMain.gb_CasePath
				+ "Acquire/data/misc/bluetoothd/";
		Map<String, String[]> map = new HashMap<String, String[]>();
		File dir = new File(filename);
		String temp[] = dir.list();
		if (temp != null) {
			bmac = temp[0];
		}
		String tmpfilename = filename + bmac + "/names";
		try {
			// read bt device names
			BufferedReader bufin = new BufferedReader(new FileReader(
					tmpfilename));
			while ((line = bufin.readLine()) != null) {
				String prop[] = new String[3];
				String mac = line.substring(0, 16);
				prop[0] = line.substring(18);
				map.put(mac, prop);
			}

			// read last seen
			tmpfilename = filename + bmac + "/lastseen";
			bufin = new BufferedReader(new FileReader(tmpfilename));
			while ((line = bufin.readLine()) != null) {
				String mac = line.substring(0, 16);
				String prop[] = map.get(mac);
				prop[1] = (line.substring(18).equals(null))? "" : line.substring(18);
				map.put(mac, prop);
			}

			// read last used
			tmpfilename = filename + bmac + "/lastused";
			bufin = new BufferedReader(new FileReader(tmpfilename));
			while ((line = bufin.readLine()) != null) {
				String mac = line.substring(0, 16);
				String prop[] = map.get(mac);
				prop[2] = (line.substring(18).equals(""))? "" : line.substring(18);
				map.put(mac, prop);
			}

			// save to Androspydb
			Connection con = new ConnectDb().getConnection();
			Statement stmt = con.createStatement();
			String query = "";
			Iterator<String> itkey = map.keySet().iterator();
			while (itkey.hasNext()) {
				String key = itkey.next();
				String prop[] = map.get(key);
				prop[1] = (prop[1] == null)? "" : prop[1];
				prop[2] = (prop[2] == null)? "" : prop[2];
				query = "INSERT INTO Bluetooth_Device (case_id, device_id, bmac, device_name, seen_date, used_date, favourite) VALUES('"
						+ AndrospyMain.gb_CaseId + "', '" + AndrospyMain.gb_DeviceId + "', '" + key + "', '"
						+ prop[0] + "', '" + prop[1] + "', '" + prop[2] + "'," + 0 + ")";
				int count = stmt.executeUpdate(query);
				if (count > 0) {
					System.out.println("Added BT");
				} else {
					System.out.println("cannot add bt");
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readWifiData() {
		String line = "", ssid = "", psk = "", keymgmt = "";
		boolean start = false;
		String filename = AndrospyMain.gb_CasePath
				+ "Acquire/data/misc/wifi/wpa_supplicant.conf";
		File file = new File(filename);
		try {
			BufferedReader bufin = new BufferedReader(new FileReader(file));
			while ((line = bufin.readLine()) != null) {
				if (line.startsWith("network=")) {
					start = true;
					continue;
				}
				if (line.endsWith("}")) {
					start = false;
					String query = "INSERT INTO Wifi_Info (case_id, device_id, ssid, password, key_mgmt, favourite) "
							+ "VALUES( '"
							+ AndrospyMain.gb_CaseId
							+ "', '"
							+ AndrospyMain.gb_DeviceId
							+ "', '"
							+ ssid
							+ "', '"
							+ processSpecialCharacter(psk)
							+ "', '"
							+ keymgmt
							+ "'," + 0 + ")";
					ConnectDb condb = new ConnectDb();
					Connection conn = condb.getConnection();
					Statement stmt = conn.createStatement();
					int count = stmt.executeUpdate(query);
					if (count > 0) {
						System.out.println("Added");
					} else {
						System.out.println("not added");
					}
					continue;
				}
				if (start) {
					int index = line.indexOf("=");					
					String key = line.substring(0, index);					
					String value = line.substring(index +1);					
					if(value.charAt(0) == '"'){
						value = value.substring(1);
					}
					int len = value.length();
					if(value.charAt(len-1) == '"'){
						value = value.substring(0,(len - 1));
					}
					if (key.endsWith("ssid")) {
						ssid = value;
					} else if (key.endsWith("psk")) {
						psk = value;
					} else if (key.endsWith("key_mgmt")) {
						keymgmt = value;
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// bulk_extractor
	protected void extractData(String imagePath) {
		final String outpath = Androspy.globalPath + "Acquire/be_output";
		String result = "";
		System.out.println("global path " + Androspy.globalPath);
		try {
			String cmd = "bulk_extractor -o " + outpath + " " + imagePath;
			System.out.println(cmd);
			Process proc = Runtime.getRuntime().exec(cmd);
			BufferedReader bufin = new BufferedReader(new InputStreamReader(
					proc.getInputStream()));
			String line = bufin.readLine();
			while (line != null) {
				result += line + "\n";
				line = bufin.readLine();

			}
			proc.waitFor();
			MessageBox msgbox = new MessageBox(shell, SWT.ICON_INFORMATION);
			msgbox.setMessage("Data Extraction Succeess\n Check Output folder");
			msgbox.setText("Operation Finish");
			msgbox.open();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// string

	public void run() {
		startExtract();
	}
}
