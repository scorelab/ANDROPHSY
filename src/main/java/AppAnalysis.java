// Copyright 2015 Indeewari Akarawita
//
//    This file is a part of ANDROPHSY
//
//    ANDROPHSY is free software: you can redistribute it and/or modify
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.custom.StackLayout;

/**
 * @author indeewari
 *
 */
public class AppAnalysis extends Composite {
	private ScrolledComposite scrolledComposite;
	private String globalpath = "/home/indeewari/pull1/";
	private Statement stmt = null;
	private Table table;
	private DateTime dtFrom;
	private DateTime dtTo;
	private StackLayout stacklayout = new StackLayout();
	private PresentationUtil pres;
	private Composite cmbBase;;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public AppAnalysis(Composite parent, int style) {

		super(parent, style);

		this.setLayout(new GridLayout(3, false));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		SelectionListener selectionlistener = new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Button btnModel = ((Button) arg0.widget);
				if (btnModel != null) {
					String sModel = ((Button) arg0.widget).getText();
					if (((Button) arg0.widget).getSelection()) {
						if (sModel == "User Credentials") {
							AppDummyTab cmpDummyTab = new AppDummyTab(cmbBase,
									SWT.NONE);
							stacklayout.topControl = cmpDummyTab;
							cmpDummyTab.PopulateData(sModel);
						} else if (sModel == "Facebook") {
							AppDummyTab cmpDummyTab = new AppDummyTab(cmbBase,
									SWT.NONE);
							stacklayout.topControl = cmpDummyTab;
							cmpDummyTab.PopulateData(sModel);
						} else if (sModel == "LinkedIn"){
							AppDummyTab cmpDummyTab = new AppDummyTab(cmbBase,
									SWT.NONE);
							stacklayout.topControl = cmpDummyTab;
							cmpDummyTab.PopulateData(sModel);
						} else if (sModel == "Skype"){
							AppDummyTab cmpDummyTab = new AppDummyTab(cmbBase,
									SWT.NONE);
							stacklayout.topControl = cmpDummyTab;
							cmpDummyTab.PopulateData(sModel);
						} else if (sModel == "Default Browser"){
							AppDummyTab cmpDummyTab = new AppDummyTab(cmbBase,
									SWT.NONE);
							stacklayout.topControl = cmpDummyTab;
							cmpDummyTab.PopulateData(sModel);
						} else if (sModel == "Chrome"){
							AppDummyTab cmpDummyTab = new AppDummyTab(cmbBase,
									SWT.NONE);
							stacklayout.topControl = cmpDummyTab;
							cmpDummyTab.PopulateData(sModel);
						} else if (sModel == "Firefox"){
							AppDummyTab cmpDummyTab = new AppDummyTab(cmbBase,
									SWT.NONE);
							stacklayout.topControl = cmpDummyTab;
							cmpDummyTab.PopulateData(sModel);
						} else if (sModel == "Call Log") {
							AppDummyTab cmpDummyTab = new AppDummyTab(cmbBase,
									SWT.NONE);
							stacklayout.topControl = cmpDummyTab;
							cmpDummyTab.PopulateData(sModel);
						} else if (sModel == "SMS Log") {
							AppDummyTab cmpDummyTab = new AppDummyTab(cmbBase,
									SWT.NONE);
							stacklayout.topControl = cmpDummyTab;
							cmpDummyTab.PopulateData(sModel);
						} else if (sModel == "MMS Log") {
							AppDummyTab cmpDummyTab = new AppDummyTab(cmbBase,
									SWT.NONE);
							stacklayout.topControl = cmpDummyTab;
							cmpDummyTab.PopulateData(sModel);
						} else if (sModel == "Bluetooth") {
							AppDummyTab cmpDummyTab = new AppDummyTab(cmbBase,
									SWT.NONE);
							stacklayout.topControl = cmpDummyTab;
							cmpDummyTab.PopulateData(sModel);
						} else {
							stacklayout.topControl = pres;
							pres.PopulateData(sModel);
						}
						cmbBase.layout();
					}
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		};

		Composite composite = new Composite(this, SWT.V_SCROLL);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		composite.setLayout(new GridLayout(1, false));

		Button btnUserAccounts = new Button(composite, SWT.RADIO);
		GridData gd_btnUserAccounts = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnUserAccounts.heightHint = 20;
		btnUserAccounts.setLayoutData(gd_btnUserAccounts);
		btnUserAccounts.setText("User Credentials");
		btnUserAccounts.addSelectionListener(selectionlistener);
		
		Label lblApplicationAnalysis = new Label(composite, SWT.NONE);
		lblApplicationAnalysis.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 1, 1));
		lblApplicationAnalysis.setText("Basic Application Analysis");

		Button btnPhoneBook = new Button(composite, SWT.RADIO);
		GridData gd_btnPhoneBook = new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1);
		gd_btnPhoneBook.heightHint = 20;
		btnPhoneBook.setLayoutData(gd_btnPhoneBook);
		btnPhoneBook.setText("Phone Book");
		btnPhoneBook.addSelectionListener(selectionlistener);

		Button btnMessages = new Button(composite, SWT.RADIO);
		GridData gd_btnMessages = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnMessages.heightHint = 20;
		btnMessages.setLayoutData(gd_btnMessages);
		btnMessages.setText("SMS");
		btnMessages.addSelectionListener(selectionlistener);
		
		Button btnCalendar = new Button(composite, SWT.RADIO);
		GridData gd_btnCalendar = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnCalendar.heightHint = 20;
		btnCalendar.setLayoutData(gd_btnCalendar);
		btnCalendar.setText("Calendar");
		btnCalendar.addSelectionListener(selectionlistener);

		Button btnGmail = new Button(composite, SWT.RADIO);
		GridData gd_btnGmail = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnGmail.heightHint = 20;
		btnGmail.setLayoutData(gd_btnGmail);
		btnGmail.setText("Gmail");
		btnGmail.addSelectionListener(selectionlistener);

		Button btnYouTube = new Button(composite, SWT.RADIO);
		GridData gd_btnYouTube = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnYouTube.heightHint = 20;
		btnYouTube.setLayoutData(gd_btnYouTube);
		btnYouTube.setText("YouTube");
		btnYouTube.addSelectionListener(selectionlistener);
		
		Button btnGmap = new Button(composite, SWT.RADIO);
		GridData gd_btnGmap = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnGmap.heightHint = 20;
		btnGmap.setLayoutData(gd_btnGmap);
		btnGmap.setText("Google Map");
		btnGmap.addSelectionListener(selectionlistener);
		
		Label lblSocialNwAnalysis = new Label(composite, SWT.NONE);
		lblSocialNwAnalysis.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1,
				1));
		lblSocialNwAnalysis.setText("Social Networking Apps");
		
		Button btnFb = new Button(composite, SWT.RADIO);
		GridData gd_btnFb = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnFb.heightHint = 20;
		btnFb.setLayoutData(gd_btnFb);
		btnFb.setText("Facebook");
		btnFb.addSelectionListener(selectionlistener);
		
		Button btnLinkedin = new Button(composite, SWT.RADIO);
		GridData gd_btnLinkedin = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnLinkedin.heightHint = 20;
		btnLinkedin.setLayoutData(gd_btnLinkedin);
		btnLinkedin.setText("LinkedIn");
		btnLinkedin.addSelectionListener(selectionlistener);
		
		Button btnSkype = new Button(composite, SWT.RADIO);
		GridData gd_btnSkype = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnSkype.heightHint = 21;
		btnSkype.setLayoutData(gd_btnSkype);
		btnSkype.setText("Skype");
		btnSkype.addSelectionListener(selectionlistener);
		
		Label lblBrowserArtifacts = new Label(composite, SWT.NONE);
		lblBrowserArtifacts.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1,
				1));
		lblBrowserArtifacts.setText("Browser Artifact Analysis");

		Button btnDefaultBrowser = new Button(composite, SWT.RADIO);
		GridData gd_btnDefaultBrowser = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnDefaultBrowser.heightHint = 20;
		btnDefaultBrowser.setLayoutData(gd_btnDefaultBrowser);
		btnDefaultBrowser.setText("Default Browser");
		btnDefaultBrowser.addSelectionListener(selectionlistener);

		Button btnChrome = new Button(composite, SWT.RADIO);
		GridData gd_btnChrome = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnChrome.heightHint = 20;
		btnChrome.setLayoutData(gd_btnChrome);
		btnChrome.setText("Chrome");
		btnChrome.addSelectionListener(selectionlistener);

		Button btnFirefox = new Button(composite, SWT.RADIO);
		GridData gd_btnFirefox = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnFirefox.heightHint = 20;
		btnFirefox.setLayoutData(gd_btnFirefox);
		btnFirefox.setText("Firefox");
		btnFirefox.addSelectionListener(selectionlistener);				
		
		Label lblLogAnalysis = new Label(composite, SWT.NONE);
		lblLogAnalysis.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		lblLogAnalysis.setText("Event Log Analysis");

		Button btnCallLog = new Button(composite, SWT.RADIO);
		GridData gd_btnCallLog = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnCallLog.heightHint = 20;
		btnCallLog.setLayoutData(gd_btnCallLog);
		btnCallLog.setText("Call Log");
		btnCallLog.addSelectionListener(selectionlistener);

		Button btnSMSLog = new Button(composite, SWT.RADIO);
		GridData gd_btnSMSLog = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnSMSLog.heightHint = 20;
		btnSMSLog.setLayoutData(gd_btnSMSLog);
		btnSMSLog.setText("SMS Log");
		btnSMSLog.addSelectionListener(selectionlistener);

		Button btnMmsLog = new Button(composite, SWT.RADIO);
		GridData gd_btnMmsLog = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnMmsLog.heightHint = 20;
		btnMmsLog.setLayoutData(gd_btnMmsLog);
		btnMmsLog.setText("MMS Log");
		btnMmsLog.addSelectionListener(selectionlistener);
		
		Label lblWirelessNetworkAnalysis = new Label(composite, SWT.NONE);
		lblWirelessNetworkAnalysis.setLayoutData(new GridData(SWT.FILL,
				SWT.CENTER, false, false, 1, 1));
		lblWirelessNetworkAnalysis.setText("Wireless Network Analysis");

		Button btnBluetoothInfo = new Button(composite, SWT.RADIO);
		GridData gd_btnBluetoothInfo = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnBluetoothInfo.heightHint = 20;
		btnBluetoothInfo.setLayoutData(gd_btnBluetoothInfo);
		btnBluetoothInfo.setText("Bluetooth");
		btnBluetoothInfo.addSelectionListener(selectionlistener);

		Button btnWifiData = new Button(composite, SWT.RADIO);
		GridData gd_btnWifiData = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnWifiData.heightHint = 20;
		btnWifiData.setLayoutData(gd_btnWifiData);
		btnWifiData.setText("Wi-Fi Access Points");
		btnWifiData.addSelectionListener(selectionlistener);

		cmbBase = new Composite(this, SWT.BORDER_DASH);
		cmbBase.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		cmbBase.setLayout(stacklayout);
		
		pres = new PresentationUtil(cmbBase, SWT.NONE);
		GridLayout gridLayout = (GridLayout) pres.getLayout();
		gridLayout.makeColumnsEqualWidth = false;
		
		stacklayout.topControl = pres;
		new Label(this, SWT.NONE);
		cmbBase.layout();

	}

	protected void PopulateData(String sModel) {
		try {
			String query = "";
			Connection conn = new ConnectDb().getConnection();
			Statement stmt = conn.createStatement();
			if (sModel == "Browser History") {
				System.out.println("Browser History");
				query = "SELECT id, case_id, device_id, title AS Title, url AS URL, "
						+ "last_visit_date AS Last Visit Date, visits AS No of Visitis";
			}
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmeta = rs.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
