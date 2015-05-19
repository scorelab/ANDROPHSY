package lk.score.androphsy.report;// Copyright 2015 Indeewari Akarawita
//
// This file is a part of ANDROPHSY
//
// ANDROPHSY is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.wb.swt.SWTResourceManager;

import lk.score.androphsy.main.*;

/**
 * @author indeewari
 *
 */
public class ReportGUITop extends Composite {

	private Text txtExtractStatus;
	private Button btnHexDump;
	private ArrayList<String> checked = new ArrayList<String>();
	private Button btnKeywordSearch;
	private Button btnExtractString;
	private Button btnUserAccountDetails;
	private Label lblBrowserArtifacts;
	private Button btnSms;
	private Button btnEventLog;
	private Button btnBluetoothData;
	private Label lblBrowser;
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
	private Label lblDataExtraction;
	private Label lblPrimaryExtraction;
	private Label lblBasicApplicationData;
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
	private Button btnFirefox;
	private Button btnFirefoxDlg;
	private Button btnLinkedinDlg;
	private Button btnLinkedin;
	private Button btnFacebookDlg;
	private Button btnWhatsApp;
	private static Tree tree;
	private Label label;
	private Label lblReportHeaderSettings;
	private Button btnTitlePage;
	private Button btnDeviceBasicInformation;
	private List list;
	private Button btnUp;
	private Button btnDown;
	private Button btnIncludeOnlyFavourite;
	private Button btnIncludeAllData;
	private Button btnEventLogDlg;
	private Button btnSkypeDlg;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ReportGUITop(Composite parent, int style) {
		super(parent, style);

		SelectionListener btnsellistener = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Button btnModel = ((Button) arg0.widget);
				if (btnModel != null) {
					String sModel = ((Button) arg0.widget).getText();
					if (((Button) arg0.widget).getSelection()) {

						if (sModel == "Google Chrome") {
							ExtractOptiondlg optiondlg =
							                             new ExtractOptiondlg(getShell(), SWT.NONE,
							                                                  "CHROME", checked);
							optiondlg.open();
							btnModel.setSelection(false);
						} else if (sModel == "Default Browser") {
							ExtractOptiondlg optiondlg =
							                             new ExtractOptiondlg(getShell(), SWT.NONE,
							                                                  "DEFAULT", checked);
							optiondlg.open();
						} else if (sModel.equals("LinkedIn")) {
							ExtractOptiondlg optiondlg =
							                             new ExtractOptiondlg(getShell(), SWT.NONE,
							                                                  "LINKEDIN", checked);
							optiondlg.open();
						} else {
							checked.add(sModel);
							list.add(sModel);
						}
					} else {
						checked.remove(sModel);
					}
					populateList(checked);
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		};
		setLayout(new GridLayout(5, false));

		Composite composite = new Composite(this, SWT.H_SCROLL | SWT.V_SCROLL);
		composite.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true, 1, 4));
		composite.setLayout(null);

		Label lblUserAccoutnsAnd_1 = new Label(composite, SWT.NONE);
		lblUserAccoutnsAnd_1.setBounds(10, 10, 107, 20);
		lblUserAccoutnsAnd_1.setText("User Passwords");

		btnUserAccountDetails = new Button(composite, SWT.CHECK);
		btnUserAccountDetails.setBounds(10, 30, 137, 20);
		btnUserAccountDetails.setText("User Credentials");
		btnUserAccountDetails.addSelectionListener(btnsellistener);

		Label lblBasicApplicationData_1 = new Label(composite, SWT.NONE);
		lblBasicApplicationData_1.setBounds(10, 60, 151, 20);
		lblBasicApplicationData_1.setText("Basic Application Data");

		btnContacts = new Button(composite, SWT.CHECK);
		btnContacts.setBounds(10, 80, 137, 20);
		btnContacts.setText("Phone Book");
		btnContacts.addSelectionListener(btnsellistener);

		btnSms = new Button(composite, SWT.CHECK);
		btnSms.setBounds(10, 100, 55, 20);
		btnSms.setText("SMS");
		btnSms.addSelectionListener(btnsellistener);

		btnCalendar = new Button(composite, SWT.CHECK);
		btnCalendar.setBounds(10, 120, 86, 20);
		btnCalendar.setText("Calendar");
		btnCalendar.addSelectionListener(btnsellistener);

		btnGmail = new Button(composite, SWT.CHECK);
		btnGmail.setBounds(10, 140, 65, 20);
		btnGmail.setText("Gmail");
		btnGmail.addSelectionListener(btnsellistener);

		btnYoutube = new Button(composite, SWT.CHECK);
		btnYoutube.setBounds(10, 160, 94, 20);
		btnYoutube.setText("YouTube");
		btnYoutube.addSelectionListener(btnsellistener);

		btnMap = new Button(composite, SWT.CHECK);
		btnMap.setBounds(10, 180, 117, 20);
		btnMap.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnMap.setText("Google Map");
		btnMap.addSelectionListener(btnsellistener);

		Label lblSocialNetworkingApplications = new Label(composite, SWT.NONE);
		lblSocialNetworkingApplications.setBounds(10, 210, 266, 20);
		lblSocialNetworkingApplications.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
		                                                           false, 3, 1));
		lblSocialNetworkingApplications.setText("Social Networking Applications");

		btnFacebook = new Button(composite, SWT.CHECK);
		btnFacebook.setBounds(10, 230, 100, 20);
		btnFacebook.setText("Facebook");
		btnFacebook.addSelectionListener(btnsellistener);

		btnFacebookDlg = new Button(composite, SWT.NONE);
		btnFacebookDlg.setFont(SWTResourceManager.getFont("Ubuntu", 10, SWT.NORMAL));
		btnFacebookDlg.setText("customize");
		btnFacebookDlg.setBounds(116, 230, 72, 20);
		btnFacebookDlg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ExtractOptiondlg optiondlg =
				                             new ExtractOptiondlg(getShell(), SWT.NONE, "FACEBOOK",
				                                                  checked);
				optiondlg.open();
				btnFacebook.setSelection(optiondlg.checkFacebook());
				populateList(checked);
			}
		});

		btnLinkedin = new Button(composite, SWT.CHECK);
		btnLinkedin.setBounds(10, 250, 100, 20);
		btnLinkedin.setText("LinkedIn");
		btnLinkedin.addSelectionListener(btnsellistener);

		btnLinkedinDlg = new Button(composite, SWT.NONE);
		btnLinkedinDlg.setFont(SWTResourceManager.getFont("Ubuntu", 10, SWT.NORMAL));
		btnLinkedinDlg.setText("customize");
		btnLinkedinDlg.setBounds(116, 250, 72, 20);
		btnLinkedinDlg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ExtractOptiondlg optiondlg =
				                             new ExtractOptiondlg(getShell(), SWT.NONE, "LINKEDIN",
				                                                  checked);
				optiondlg.open();
				btnLinkedin.setSelection(optiondlg.checkLinkedin());
				populateList(checked);
			}
		});

		btnSkype = new Button(composite, SWT.CHECK);
		btnSkype.setBounds(10, 270, 100, 20);
		btnSkype.setText("Skype");
		btnSkype.addSelectionListener(btnsellistener);

		btnSkypeDlg = new Button(composite, SWT.NONE);
		btnSkypeDlg.setFont(SWTResourceManager.getFont("Ubuntu", 10, SWT.NORMAL));
		btnSkypeDlg.setText("customize");
		btnSkypeDlg.setBounds(116, 270, 72, 20);
		btnSkypeDlg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ExtractOptiondlg optiondlg =
				                             new ExtractOptiondlg(getShell(), SWT.NONE, "SKYPE",
				                                                  checked);
				optiondlg.open();
				btnSkype.setSelection(optiondlg.checkLinkedin());
				populateList(checked);
			}
		});

		lblBrowserArtifacts = new Label(composite, SWT.NONE);
		lblBrowserArtifacts.setBounds(10, 305, 151, 20);
		lblBrowserArtifacts.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblBrowserArtifacts.setText("Browser Artifacts");

		btnDefaultBrowser = new Button(composite, SWT.CHECK);
		btnDefaultBrowser.setBounds(10, 325, 137, 20);
		btnDefaultBrowser.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnDefaultBrowser.setText("Default Browser");
		btnDefaultBrowser.addSelectionListener(btnsellistener);

		btnDefBrowserDlg = new Button(composite, SWT.NONE);
		btnDefBrowserDlg.setFont(SWTResourceManager.getFont("Ubuntu", 10, SWT.NORMAL));
		btnDefBrowserDlg.setText("customize");
		btnDefBrowserDlg.setBounds(153, 325, 72, 20);
		btnDefBrowserDlg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ExtractOptiondlg optiondlg =
				                             new ExtractOptiondlg(getShell(), SWT.NONE, "DEFAULT",
				                                                  checked);
				optiondlg.open();
				btnDefaultBrowser.setSelection(optiondlg.checkDefaultBrowser());
				populateList(checked);
			}
		});

		btnGoogleChrome = new Button(composite, SWT.CHECK);
		btnGoogleChrome.setBounds(10, 345, 137, 20);
		btnGoogleChrome.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnGoogleChrome.setText("Google Chrome");
		btnGoogleChrome.addSelectionListener(btnsellistener);

		btnChromeDlg = new Button(composite, SWT.NONE);
		btnChromeDlg.setFont(SWTResourceManager.getFont("Ubuntu", 10, SWT.NORMAL));
		btnChromeDlg.setText("customize");
		btnChromeDlg.setLocation(153, 345);
		btnChromeDlg.setSize(72, 20);
		btnChromeDlg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ExtractOptiondlg optiondlg =
				                             new ExtractOptiondlg(getShell(), SWT.NONE, "CHROME",
				                                                  checked);
				optiondlg.open();
				btnGoogleChrome.setSelection(optiondlg.checkChrome());

				populateList(checked);
			}
		});

		btnFirefox = new Button(composite, SWT.CHECK);
		btnFirefox.setBounds(10, 365, 100, 20);
		btnFirefox.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnFirefox.setText("Firefox");
		btnFirefox.addSelectionListener(btnsellistener);

		btnFirefoxDlg = new Button(composite, SWT.NONE);
		btnFirefoxDlg.setFont(SWTResourceManager.getFont("Ubuntu", 10, SWT.NORMAL));
		btnFirefoxDlg.setText("customize");
		btnFirefoxDlg.setBounds(153, 365, 72, 20);
		btnFirefoxDlg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ExtractOptiondlg optiondlg =
				                             new ExtractOptiondlg(getShell(), SWT.NONE, "Firefox",
				                                                  checked);
				optiondlg.open();
				btnFirefox.setSelection(true);
				populateList(checked);
			}
		});

		lblLogs = new Label(composite, SWT.NONE);
		lblLogs.setBounds(10, 399, 100, 20);
		lblLogs.setText("Logs");

		btnEventLog = new Button(composite, SWT.CHECK);
		btnEventLog.setBounds(10, 419, 100, 20);
		btnEventLog.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnEventLog.setText("Event Logs");
		btnEventLog.addSelectionListener(btnsellistener);

		btnEventLogDlg = new Button(composite, SWT.NONE);
		btnEventLogDlg.setFont(SWTResourceManager.getFont("Ubuntu", 10, SWT.NORMAL));
		btnEventLogDlg.setBounds(153, 419, 72, 20);
		btnEventLogDlg.setText("customize");
		btnEventLogDlg.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ExtractOptiondlg optiondlg =
				                             new ExtractOptiondlg(getShell(), SWT.NONE, "EVENTLOG",
				                                                  checked);
				optiondlg.open();
				btnEventLog.setSelection(optiondlg.checkEventLog());
				populateList(checked);
			}
		});
		lblWirelessNetworkArtifacts = new Label(composite, SWT.NONE);
		lblWirelessNetworkArtifacts.setBounds(10, 455, 219, 20);
		lblWirelessNetworkArtifacts.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false,
		                                                       3, 1));
		lblWirelessNetworkArtifacts.setText("Wireless Network Artifacts");

		btnBluetoothData = new Button(composite, SWT.CHECK);
		btnBluetoothData.setBounds(10, 475, 191, 20);
		btnBluetoothData.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 2));
		btnBluetoothData.setText("Bluetooth Data");
		btnBluetoothData.addSelectionListener(btnsellistener);

		btnWifiPasswordswpapskwep = new Button(composite, SWT.CHECK);
		btnWifiPasswordswpapskwep.setBounds(10, 500, 250, 20);
		btnWifiPasswordswpapskwep.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2,
		                                                     1));
		btnWifiPasswordswpapskwep.setText("Wi-Fi Access Points");
		btnWifiPasswordswpapskwep.addSelectionListener(btnsellistener);

		btnIncludeAllData = new Button(this, SWT.RADIO);
		btnIncludeAllData.setText("Include All Data");
		new Label(this, SWT.NONE);

		new Label(this, SWT.NONE);

		tree = new Tree(this, SWT.BORDER);
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {

				TreeItem treeitem[] = ((Tree) arg0.widget).getSelection();
				File file = (File) treeitem[0].getData();
				System.out.println("Rep tree double " + file.getName());
				openFile(file);
			}
		});
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4));

		btnIncludeOnlyFavourite = new Button(this, SWT.RADIO);
		btnIncludeOnlyFavourite.setText("Include only favourite Data");

		Button btnGenerateReport = new Button(this, SWT.NONE);
		btnGenerateReport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				generateReport(checked);
				checked.clear();
				populateList(checked);
			}
		});
		btnGenerateReport.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnGenerateReport.setText("Generate Report");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Group grpRepportSections = new Group(this, SWT.NONE);
		grpRepportSections.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));
		grpRepportSections.setText("Report Sections");
		grpRepportSections.setLayout(new GridLayout(3, false));

		lblReportHeaderSettings = new Label(grpRepportSections, SWT.NONE);
		lblReportHeaderSettings.setText("Report Header Settings");
		new Label(grpRepportSections, SWT.NONE);
		new Label(grpRepportSections, SWT.NONE);

		btnTitlePage = new Button(grpRepportSections, SWT.CHECK);
		btnTitlePage.setEnabled(false);
		btnTitlePage.setSelection(true);
		btnTitlePage.setText("Title Page");
		new Label(grpRepportSections, SWT.NONE);
		new Label(grpRepportSections, SWT.NONE);

		btnDeviceBasicInformation = new Button(grpRepportSections, SWT.CHECK);
		btnDeviceBasicInformation.setSelection(true);
		btnDeviceBasicInformation.setEnabled(false);
		GridData gd_btnDeviceBasicInformation =
		                                        new GridData(SWT.LEFT, SWT.CENTER, false, false, 1,
		                                                     1);
		gd_btnDeviceBasicInformation.widthHint = 258;
		btnDeviceBasicInformation.setLayoutData(gd_btnDeviceBasicInformation);
		btnDeviceBasicInformation.setText("Device Basic Information");
		new Label(grpRepportSections, SWT.NONE);
		new Label(grpRepportSections, SWT.NONE);
		new Label(grpRepportSections, SWT.NONE);
		new Label(grpRepportSections, SWT.NONE);
		new Label(grpRepportSections, SWT.NONE);

		Label lblReportSections = new Label(grpRepportSections, SWT.NONE);
		lblReportSections.setText("Report Sections");

		btnUp = new Button(grpRepportSections, SWT.ARROW | SWT.UP);
		btnUp.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnUp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int selindex = list.getSelectionIndex();
				if (selindex > 0) {
					String sselection = list.getItem(selindex);
					String soneUp = list.getItem(selindex - 1);
					System.out.println("--- " + soneUp + " sel " + sselection);
					list.remove(selindex);
					list.add(soneUp, selindex);
					list.remove(selindex - 1);
					list.add(sselection, (selindex - 1));

					checked.remove(selindex);
					checked.add(selindex, soneUp);
					checked.remove((selindex - 1));
					checked.add((selindex - 1), sselection);
					System.out.println("--------- " + checked.indexOf(sselection));
				}
			}
		});

		btnDown = new Button(grpRepportSections, SWT.ARROW | SWT.DOWN);
		btnDown.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int selindex = list.getSelectionIndex();
				int lastindex = list.getItemCount() - 1;
				if (selindex < lastindex) {
					String sselection = list.getItem(selindex);
					String soneDown = list.getItem(selindex + 1);
					list.remove(selindex);
					list.add(soneDown, selindex);
					list.remove(selindex + 1);
					list.add(sselection, (selindex + 1));

					checked.remove(selindex);
					checked.add(selindex, soneDown);
					checked.remove((selindex + 1));
					checked.add((selindex + 1), sselection);
				}
			}
		});
		btnDown.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		list = new List(grpRepportSections, SWT.BORDER);
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
		new Label(this, SWT.NONE);

	}

	protected void generateReport(ArrayList<String> optlist) {
		ReportPdf rppdf = new ReportPdf();

		if (btnIncludeOnlyFavourite.getSelection()) {
			rppdf.setFlagAll(false);
		} else {
			rppdf.setFlagAll(true);
		}

		String name = rppdf.generatePdf(optlist);
		refreshReportTree();
		openFile(new File(name));
	}

	private void openFile(final File file) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				if (Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().open(file);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		});
		t.start();
	}

	public static void initTree() {
		String path = AndrospyMain.gb_CasePath + "Analysis/Reports/";
		File rootFile = new File(path);
		TreeItem rootItem = new TreeItem(tree, SWT.CHECK);
		rootItem.setText("/reports");
		rootItem.setData(rootFile);

		if (rootFile.exists()) {
			for (File file : rootFile.listFiles()) {
				if (file.isFile()) {
					TreeItem treeNode = new TreeItem(rootItem, SWT.CHECK);
					treeNode.setData(file);
					treeNode.setText(file.getName());
				}
			}
		}
	}

	private void refreshReportTree() {
		tree.removeAll();
		initTree();
	}

	protected void populateList(ArrayList<String> opt) {
		list.removeAll();
		Iterator<String> it = opt.iterator();
		while (it.hasNext()) {
			list.add(it.next());
		}

	}
}
