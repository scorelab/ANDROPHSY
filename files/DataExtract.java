import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;

public class DataExtract extends Composite {
	private Text txtExtractStatus;
	private ProgressBar pb1;
	private Button btnHexDump;
	private ArrayList<String> checked = new ArrayList<String>();
	private Button btnStrings;
	private Button btnSummary;
	private Button btnUserAccountDetails;
	private Button btnBrowserHistory;
	private Button btnBookmarkUrls;
	private Button btnBrowserCookies;
	private Button btnWebSearch;
	private Label lblBrowserArtifacts;
	private Label lblBrowserArtifacts_1;
	private Button btnWebFormData;
	private Button btnSms;
	private Button btnEvenLog;
	private Button btnBluetoothData;
	private Button btnWifiPasswords;
	private Label label;
	private Label lblWirelessNetworkArtifacts;
	private Button btnContacts;
	private Button btnYoutube;
	private Button btnLogs;
	private Button btnFacebook;
	private Button btnMap;
	private Button btnGmail;
	private Button btnCalendar;
	private Button btnSkype;
	private Button btnViber;
	private Button btnReport;
	private Label lblDataExtraction;
	private Label lblPrimaryExtraction;
	private Label label_1;
	private Label label_2;
	private Label lblBasicApplicationData;
	private Label lblLogs;
	private Label label_3;
	private Label label_4;
	private Button btnDeletedDataRecovery;
	private Label lblUserAccoutnsAnd;
	private Label label_5;
	private Button btnGoogleChrome;
	private Button btnDefaultBrowser;
	private Button btnTest;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public DataExtract(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(5, true));

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

		lblDataExtraction = new Label(this, SWT.NONE);
		lblDataExtraction.setFont(SWTResourceManager.getFont("Ubuntu", 12,
				SWT.BOLD));
		lblDataExtraction.setLayoutData(new GridData(SWT.CENTER, SWT.FILL,
				false, false, 2, 1));
		lblDataExtraction.setText("Data Extraction");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		lblPrimaryExtraction = new Label(this, SWT.NONE);
		lblPrimaryExtraction.setLayoutData(new GridData(SWT.LEFT, SWT.FILL,
				false, false, 1, 1));
		lblPrimaryExtraction.setText("Primary Extraction");

		label_2 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 4,
				1));

		btnHexDump = new Button(this, SWT.CHECK);
		btnHexDump.setText("Hex dump");
		btnHexDump.addSelectionListener(btnsellistener);

		btnStrings = new Button(this, SWT.CHECK);
		btnStrings.setText("Keyword Search");
		btnStrings.addSelectionListener(btnsellistener);
		new Label(this, SWT.NONE);

		btnDeletedDataRecovery = new Button(this, SWT.CHECK);
		btnDeletedDataRecovery.setText("File Carving");
		btnDeletedDataRecovery.addSelectionListener(btnsellistener);

		btnSummary = new Button(this, SWT.CHECK);
		btnSummary.setText("Summary");
		btnSummary.addSelectionListener(btnsellistener);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		lblUserAccoutnsAnd = new Label(this, SWT.NONE);
		lblUserAccoutnsAnd.setText("User Passwords");

		label_5 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_5.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 4,
				1));

		btnUserAccountDetails = new Button(this, SWT.CHECK);
		btnUserAccountDetails.setText("User Account Details");
		btnUserAccountDetails.addSelectionListener(btnsellistener);
		
		btnTest = new Button(this, SWT.ARROW | SWT.RIGHT);
		GridData gd_btnTest = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnTest.widthHint = 39;
		btnTest.setLayoutData(gd_btnTest);
		//btnTest.setText("test");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		lblBasicApplicationData = new Label(this, SWT.NONE);
		lblBasicApplicationData.setLayoutData(new GridData(SWT.LEFT, SWT.FILL,
				false, false, 1, 1));
		lblBasicApplicationData.setText("Basic Application Data");

		lblBrowserArtifacts = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblBrowserArtifacts.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				false, false, 4, 1));
		lblBrowserArtifacts.setText("Browser Artifacts");

		btnContacts = new Button(this, SWT.CHECK);
		btnContacts.setText("Contacts");
		btnContacts.addSelectionListener(btnsellistener);

		btnSms = new Button(this, SWT.CHECK);
		btnSms.setText("SMS");
		btnSms.addSelectionListener(btnsellistener);

		btnCalendar = new Button(this, SWT.CHECK);
		btnCalendar.setText("Calendar");

		btnGmail = new Button(this, SWT.CHECK);
		btnGmail.setText("Gmail");
		btnGmail.addSelectionListener(btnsellistener);

		btnYoutube = new Button(this, SWT.CHECK);
		btnYoutube.setText("YouTube");

		btnFacebook = new Button(this, SWT.CHECK);
		btnFacebook.setText("Facebook");
		btnFacebook.addSelectionListener(btnsellistener);

		btnSkype = new Button(this, SWT.CHECK);
		btnSkype.setText("Skype");

		btnViber = new Button(this, SWT.CHECK);
		btnViber.setText("Viber");

		btnMap = new Button(this, SWT.CHECK);
		btnMap.setText("Map");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		lblBrowserArtifacts_1 = new Label(this, SWT.NONE);
		lblBrowserArtifacts_1.setLayoutData(new GridData(SWT.LEFT, SWT.FILL,
				false, false, 1, 1));
		lblBrowserArtifacts_1.setText("Browser Artifacts");

		label_1 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 4,
				1));

		btnDefaultBrowser = new Button(this, SWT.CHECK);
		btnDefaultBrowser.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false,
				false, 1, 1));
		btnDefaultBrowser.setText("Default Browser");

		/*
		 * btnWebSearch = new Button(this, SWT.CHECK);
		 * btnWebSearch.setText("Web Search");
		 * btnWebSearch.addSelectionListener(btnsellistener);
		 * 
		 * btnBookmarkUrls = new Button(this, SWT.CHECK);
		 * btnBookmarkUrls.setText("Bookmark URLs");
		 * btnBookmarkUrls.addSelectionListener(btnsellistener);
		 * 
		 * btnBrowserCookies = new Button(this, SWT.CHECK);
		 * btnBrowserCookies.setText("Browser Cookies");
		 * btnBrowserCookies.addSelectionListener(btnsellistener);
		 * 
		 * btnWebFormData = new Button(this, SWT.CHECK);
		 * btnWebFormData.setText("Web Form and Data");
		 * btnWebFormData.addSelectionListener(btnsellistener);
		 */
/*
		btnBrowserHistory = new Button(this, SWT.CHECK);
		btnBrowserHistory.setText("Browser History");
		btnBrowserHistory.addSelectionListener(btnsellistener);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
*/
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		btnGoogleChrome = new Button(this, SWT.CHECK);
		btnGoogleChrome.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false,
				false, 1, 1));
		btnGoogleChrome.setText("Google Chrome");
		btnGoogleChrome.addSelectionListener(btnsellistener);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		lblWirelessNetworkArtifacts = new Label(this, SWT.NONE);
		lblWirelessNetworkArtifacts.setLayoutData(new GridData(SWT.LEFT,
				SWT.FILL, false, false, 1, 1));
		lblWirelessNetworkArtifacts.setText("Wireless Network Artifacts");
		label_3 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 4,
				1));

		btnBluetoothData = new Button(this, SWT.CHECK);
		btnBluetoothData.setText("Bluetooth Data");
		btnBluetoothData.addSelectionListener(btnsellistener);

		btnWifiPasswords = new Button(this, SWT.CHECK | SWT.WRAP);
		btnWifiPasswords.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false, 2, 1));
		btnWifiPasswords.setText("Wi-Fi Passwords (WPA-PSK/WEP)");
		btnWifiPasswords.addSelectionListener(btnsellistener);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		lblLogs = new Label(this, SWT.NONE);
		lblLogs.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1,
				1));
		lblLogs.setText("Logs");

		label_4 = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label_4.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 4,
				1));

		btnEvenLog = new Button(this, SWT.CHECK);
		btnEvenLog.setText("Event Logs");
		btnEvenLog.addSelectionListener(btnsellistener);

		btnLogs = new Button(this, SWT.CHECK);
		btnLogs.setText("System Logs");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Button btnExtractFeature = new Button(this, SWT.NONE);
		btnExtractFeature.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER,
				false, false, 2, 1));
		btnExtractFeature.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (checked.size() <= 0) {
					return;
				}
				System.out.println("inside listener " + checked.size());
				pb1.setMaximum(checked.size());
				Operation operation = new Operation(AndrospyMain.shell, pb1,
						checked);
				operation.start();
			}
		});

		btnExtractFeature.setText("Extract Feature");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		txtExtractStatus = new Text(this, SWT.BORDER | SWT.MULTI);
		GridData gd_txtExtractStatus = new GridData(SWT.FILL, SWT.FILL, false,
				false, 5, 1);
		gd_txtExtractStatus.heightHint = 58;
		txtExtractStatus.setLayoutData(gd_txtExtractStatus);

		pb1 = new ProgressBar(this, SWT.HORIZONTAL | SWT.SMOOTH);
		GridData gd_pb1 = new GridData(GridData.FILL_HORIZONTAL);
		gd_pb1.horizontalSpan = 5;
		gd_pb1.verticalAlignment = SWT.FILL;
		gd_pb1.grabExcessHorizontalSpace = false;
		pb1.setLayoutData(gd_pb1);
		pb1.setMinimum(0);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		btnReport = new Button(this, SWT.NONE);
		btnReport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new ReportPdf().generatePdf("");
			}
		});
		btnReport.setText("Report");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		label = new Label(this, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1,
				1));
		label.setText("Browser Artifacts");
		new Label(this, SWT.NONE);
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
			System.out.println("@ " + option);

			if (option == "Hex dump") {
				System.out.println("Generating hex file");
				new AdbExecCmd().generateHexdump("/home/indeewari/1/");
				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Summary") {
				extractData(AndrospyMain.gb_CasePath + "Acquire/data.dd");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Keyword Search") {
				System.out.println("Generate String file");
				new AdbExecCmd()
						.CreateStringFile("/home/indeewari/1/Acquire/data.dd");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "File Carving") {
				new AdbExecCmd().recoverDeleted(AndrospyMain.gb_CasePath
						+ "Acquire/data.dd");

			} else if (option == "Device Information") {
				System.out.println("propery");
				AdbExecCmd cmd = new AdbExecCmd();
				DeviceTemplate devicetemplate = new DeviceTemplate();
				cmd.getDeviceBasicInfo(devicetemplate);
				cmd.getPackageList();

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Application List") {
				new AdbExecCmd().getPackageList();
				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "User Account Details") {
				System.out.println("User Account Details");
				new AndrospyUtility().readRecords("Accounts");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Contacts") {
				System.out.println("Contacts");
				new AndrospyUtility().readRecords("Contacts");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "SMS") {
				System.out.println("SMS");
				new AndrospyUtility().readRecords("SMS");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Gmail") {
				System.out.println("Gmail");
				new AndrospyUtility().readRecords("Gmail");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Facebook") {
				System.out.println("Facebook");
				new AndrospyUtility().readRecords("FacebookContacts");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "DEFAULT_BROWSER_HISTORY") {
				System.out.println("Browser History");
				new AndrospyUtility().readRecords("DEFAULT_BROWSER_HISTORY");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "CHROME_BROWSER_HISTORY") {
				System.out.println(option);
				new AndrospyUtility().readRecords("CHROME_BROWSER_HISTORY");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "DEFAULT_BOOKMARKS") {
				System.out.println("DEFAULT_BOOKMARKS");
				new AndrospyUtility().readRecords("DEFAULT_BOOKMARKS");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "DEFAULT_COOKIES") {
				System.out.println("DEFAULT_COOKIES");
				new AndrospyUtility().readRecords("DEFAULT_COOKIES");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "CHROME_COOKIES") {
				System.out.println("CHROME_COOKIES");
				new AndrospyUtility().readRecords("CHROME_COOKIES");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "DEFAULT_WEB_SEARCH") {
				System.out.println("DEFAULT_WEB_SEARCH");
				new AndrospyUtility().readRecords("DEFAULT_WEB_SEARCH");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "CHROME_WEB_SEARCH") {
				System.out.println("CHROME_WEB_SEARCH");
				new AndrospyUtility().readRecords("CHROME_WEB_SEARCH");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "DEFAULT_WEB_FORM_DATA") {
				System.out.println("DEFAULT_WEB_FORM_DATA");
				new AndrospyUtility().readRecords("DEFAULT_WEB_FORM_DATA");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "CHROME_CREDIT_CARD") {
				System.out.println("CHROME_CREDIT_CARD");
				new AndrospyUtility().readRecords("CHROME_CREDIT_CARD");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Event Logs") {
				System.out.println("Event Logs");
				new AndrospyUtility().readRecords("EventLog");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Bluetooth Data") {
				System.out.println("Bluetooth");
				readBlutoothInfo();
				new AndrospyUtility().readRecords("Bluetooth");

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			} else if (option == "Wi-Fi Passwords (WPA-PSK/WEP)") {
				System.out.println("Wi-Fi");
				readWifiData();

				shell.getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						System.out.println("**** before if");
						if (pb.isDisposed()) {
							return;
						}
						System.out.println("****" + pb.getSelection());
						pb.setSelection(pb.getSelection() + 1);
					}
				});
			}
		}
	}

	public String processSpecialCharacter(String in) {
		System.out.println("in: " + in);
		String out = in.replaceAll("'", "\'");
		System.out.println("out: " + out);
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
		System.out.println("MAC " + bmac);
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
				prop[1] = line.substring(18);
				map.put(mac, prop);
			}

			// read last used
			tmpfilename = filename + bmac + "/lastused";
			bufin = new BufferedReader(new FileReader(tmpfilename));
			while ((line = bufin.readLine()) != null) {
				String mac = line.substring(0, 16);
				String prop[] = map.get(mac);
				prop[2] = line.substring(18);
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
				query = "INSERT INTO Bluetooth_Device (case_id, device_id, bmac, device_name, seen_date, used_date, favourite) VALUES('"
						+ AndrospyMain.gb_CaseId
						+ "', '"
						+ AndrospyMain.gb_DeviceId
						+ "', '"
						+ key
						+ "', '"
						+ prop[0]
						+ "', '"
						+ prop[1]
						+ "', '"
						+ prop[2]
						+ "',"
						+ 0 + ")";
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
				System.out.println(line);
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
					System.out.println("*" + line);
					int index = line.indexOf("=");
					String key = line.substring(0, index);
					String value = line.substring(index + 1);
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
		imagePath = "/home/indeewari/dd";
		final String outpath = "/home/indeewari/be_output";
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
