package lk.score.androphsy.main;// Copyright 2015 Indeewari Akarawita
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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import lk.score.androphsy.report.ReportPdf;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.widgets.Group;

/**
 * @author indeewari
 *
 */
public class PresentationUtil extends Composite {
	private Table table;
	private String featureName = "";
	private Button btnRefresh;
	private Group grpReportGeneration;
	private Button btnAllData;
	private Button btnIncludeonlyImportantEvedence;
	private Button btnGeneratePdfReport;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public PresentationUtil(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));

		table = new Table(this, SWT.BORDER | SWT.CHECK | SWT.H_SCROLL);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_table.heightHint = 184;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		btnRefresh = new Button(this, SWT.NONE);
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				PopulateData(featureName);
			}
		});
		GridData gd_btnRefresh = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnRefresh.widthHint = 108;
		btnRefresh.setLayoutData(gd_btnRefresh);
		btnRefresh.setText("Refresh");

		Button btnSaveFavorites = new Button(this, SWT.NONE);
		GridData gd_btnSaveFavorites = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnSaveFavorites.widthHint = 125;
		btnSaveFavorites.setLayoutData(gd_btnSaveFavorites);
		btnSaveFavorites.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				saveFavouriteEvidence(featureName);
			}
		});
		btnSaveFavorites.setText("Save Favorites");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

	}

	protected void generateReport(String feature) {
		ReportPdf rppdf = new ReportPdf();

		if (btnIncludeonlyImportantEvedence.getSelection()) {
			rppdf.setFlagAll(false);
		} else {
			rppdf.setFlagAll(true);
		}

		rppdf.generatePdf(feature);
	}

	protected void saveFavouriteEvidence(String feature) {
		String tablename = "", bwhere = "";
		try {
			if ((feature == "PRIMARY_ACC") || (feature == "OTHER_ACC")) {
				tablename = "Androspy_Account";
			} else if (feature == "SMS") {
				tablename = "Sms";
			} else if (feature == "Phone Book") {
				tablename = "Contacts";
			} else if ((feature == "ALL_CALL_LOG") || (feature == "INCOMING_CALLS") ||
			           (feature == "OUT_CALLS") || (feature == "MISSED CALLS") ||
			           (feature == "CANCELLED_CALLS") || (feature == "ALL_SMS_LOG") ||
			           (feature == "SMS_INBOX") || (feature == "SMS_OUTBOX") ||
			           (feature == "ALL_MMS_LOG") || (feature == "MMS_INBOX") ||
			           (feature == "MMS_OUTBOX")) {
				tablename = "Event_Log";
			} else if (feature == "Gmail") {
				tablename = "Gmail_Store";
			} else if (feature == "Wi-Fi Access Points") {
				tablename = "Wifi_Info";
			} else if (feature == "BT_USED") {
				tablename = "Bluetooth_Tranfer";
			} else if (feature == "BT_SEEN") {
				tablename = "Bluetooth_Device";
			} else if (feature == "Web Form and Data") {
				tablename = "WebFormData";
			} else if (feature == "Browser Cookies") {
				tablename = "Browser_Cookies";
			} else if (feature == "Web lk.score.androphsy.main.Search") {
				tablename = "WebSearch";
			} else if (feature == "Bookmarked URLs") {
				tablename = "Androspy_Bookmark";
			} else if (feature == "Browser History") {
				tablename = "Browser_History";
			} else if (feature == "Facebook") {
				tablename = "Fb_Contacts";
			} else if (feature == "Skype Account") {
				tablename = "Skype_Account";
			} else if (feature == "Skype Contacts") {
				tablename = "Skype_Contacts";
			} else if (feature == "Skype Calls") {
				tablename = "Skype_Call";
			} else if (feature == "Skype Messages") {
				tablename = "Skype_Message";
			} else if (feature == "DEFAULT_BROWSER_WEB_FORM") {
				tablename = "WebFormData";
			} else if (feature == "DEFAULT_BROWSER_COOKIES") {
				tablename = "Browser_Cookies";
				bwhere = " AND browser = 'DEFAULTB'";
			} else if (feature == "DEFAULT_BROWSER_SEARCH") {
				tablename = "WebSearch";
				bwhere = " AND browser = 'DEFAULTB'";
			} else if (feature == "DEFAULT_BROWSER_BOOKMARK") {
				tablename = "Androspy_Bookmark";
				bwhere = " AND browser = 'DEFAULTB'";
			} else if (feature == "DEFAULT_BROWSER_HISTORY") {
				tablename = "Browser_History";
				bwhere = " AND browser = 'DEFAULTB'";
			} else if (feature == "Firefox Cookies") {
				tablename = "Browser_Cookies";
				bwhere = " AND browser = 'Firefox'";
			} else if (feature == "Firefox lk.score.androphsy.main.Search") {
				tablename = "WebSearch";
				bwhere = " AND browser = 'Firefox'";
			} else if (feature == "Firefox Bookmarks") {
				tablename = "Androspy_Bookmark";
				bwhere = " AND browser = 'Firefox'";
			} else if (feature == "Firefox Browser History") {
				tablename = "Browser_History";
				bwhere = " AND browser = 'Firefox'";
			} else if (feature == "CHROME_BROWSER_COOKIES") {
				tablename = "Browser_Cookies";
				bwhere = " AND browser = 'Chrome'";
			} else if (feature == "CHROME_BROWSER_SEARCH") {
				tablename = "WebSearch";
				bwhere = " AND browser = 'Chrome'";
			} else if (feature == "CHROME_CREDIT_CARD") {
				tablename = "Browser_Credit_Card";
			} else if (feature == "CHROME_BROWSER_HISTORY") {
				tablename = "Browser_History";
				bwhere = " AND browser = 'Chrome'";
			}

			Connection conn = new ConnectDb().getConnection();
			Statement stmt = conn.createStatement();

			ArrayList<String> checked = new ArrayList<String>();
			ArrayList<String> notchecked = new ArrayList<String>();

			TableItem items[] = table.getItems();

			String temp = "";

			for (TableItem tableItem : items) {
				if (tableItem.getChecked()) {
					System.out.println("checked item id: " + tableItem.getText(1));
					checked.add(tableItem.getText(1));
				} else {
					notchecked.add(tableItem.getText(1));
				}
			}

			Iterator<String> it = checked.iterator();
			while (it.hasNext()) {
				temp += "id = " + it.next();
				if (it.hasNext()) {
					temp += " OR ";
				}
			}
			temp += bwhere;
			temp = "UPDATE " + tablename + " SET favourite = 1 WHERE " + temp;
			System.out.println("update check " + temp);
			int count = stmt.executeUpdate(temp);

			temp = "";
			it = notchecked.iterator();
			while (it.hasNext()) {
				temp += "id = " + it.next();
				if (it.hasNext()) {
					temp += " OR ";
				}
			}

			temp = "UPDATE " + tablename + " SET favourite = 0 WHERE " + temp;
			System.out.println("update not check " + temp);
			count = stmt.executeUpdate(temp);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void PopulateData(String sModel) {
		featureName = sModel;
		table.removeAll();
		table.setItemCount(0);
		System.out.println("col count " + table.getColumnCount());
		removeColumns(table);
		System.out.println("col count " + table.getColumnCount());
		try {
			String query = "";
			Connection conn = new ConnectDb().getConnection();
			Statement stmt = conn.createStatement();
			if (sModel == "PRIMARY_ACC") {
				System.out.println("Primary Acc");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, " +
				                "IFNULL(host, '') AS 'Account Type'," +
				                " IFNULL(username, '') AS 'User Name'," +
				                " IFNULL(password, '') AS 'Password (HASHED)'" +
				                " FROM Androspy_Account" + " WHERE case_id = " +
				                AndrospyMain.gb_CaseId + " AND device_id = " +
				                AndrospyMain.gb_DeviceId + " AND primary_acc = " + 1;

			} else if (sModel == "OTHER_ACC") {
				System.out.println("Primary Acc");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, " +
				                "IFNULL(host, '') AS 'Account Type'," +
				                " IFNULL(username, '') AS 'User Name'," +
				                " IFNULL(password, '') AS 'Password'" + " FROM Androspy_Account" +
				                " WHERE case_id = " + AndrospyMain.gb_CaseId + " AND device_id = " +
				                AndrospyMain.gb_DeviceId + " AND primary_acc != " + 1;

			} else if (sModel == "Phone Book") {
				System.out.println("Contacts");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id," +
				                " IFNULL(contact_no, '') AS 'Contact No'," +
				                " IFNULL(name, '') AS 'Name'," +
				                " IFNULL(times_contacted, '') AS 'Times Contacted'," +
				                " IFNULL(last_time_contacted, '') AS 'Last Contacted Date'" +
				                " FROM Contacts" + " WHERE case_id = " + AndrospyMain.gb_CaseId +
				                " AND device_id = " + AndrospyMain.gb_DeviceId;
			} else if (sModel == "SMS") {
				System.out.println("SMS");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id," +
				                " IFNULL(subject, '') AS 'Subject'," +
				                " IFNULL(body, '') AS 'Message Body'," +
				                "CASE WHEN (type = 1) THEN 'INBOX' ELSE '' END AS 'Message Type'," +
				                " IFNULL(date_received,'') AS 'Received Date'," +
				                " IFNULL(date_sent,'') AS 'Send Date'" + " FROM Sms" +
				                " WHERE case_id = " + AndrospyMain.gb_CaseId + " AND device_id = " +
				                AndrospyMain.gb_DeviceId;
			} else if (sModel == "Calendar") {
				System.out.println("Calendar");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id," +
				                " IFNULL(title, '') AS Title, " +
				                "IFNULL(description, '') AS 'Event Description'," +
				                " IFNULL(location, '') AS 'Location'," +
				                " IFNULL(start_time, '') AS 'Start Time'," +
				                " IFNULL(end_time, '') AS 'End Time'," +
				                " IFNULL(created_by, '') AS 'Creaded By'," +
				                " CASE WHEN (deleted = 1) THEN 'Yes' ELSE 'No' END AS 'Deleted'" +
				                " FROM Calendar_Event" + " WHERE case_id = " +
				                AndrospyMain.gb_CaseId + " AND device_id = " +
				                AndrospyMain.gb_DeviceId;
			} else if (sModel == "Gmail") {
				System.out.println("Gmain Inbox");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id," +
				                " IFNULL(fromaddress, '') AS 'From Address'," +
				                " IFNULL(toaddress, '') AS 'To Address'," +
				                " IFNULL(ccaddress, '') AS 'CC Address'," +
				                " IFNULL(bccaddress,'') AS 'BCC Address'," +
				                " IFNULL(replytoaddress, '') AS 'Reply To Address'," +
				                " IFNULL(subject, '') AS 'Subject'," +
				                " IFNULL(snippet, '') As 'Message Body'," +
				                " IFNULL(datesent, '') AS 'Send Date'," +
				                " IFNULL(datereceive, '') AS 'Received Date'" +
				                " FROM Gmail_Store" + " WHERE case_id = " + AndrospyMain.gb_CaseId +
				                " AND device_id = " + AndrospyMain.gb_DeviceId;
			} else if (sModel == "YouTube") {
				System.out.println("Youtube");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id," +
				                " IFNULL(query, '') AS 'Title', IFNULL(search_date, '') AS 'Date'" +
				                " FROM Youtube_History" + " WHERE case_id = " +
				                AndrospyMain.gb_CaseId + " AND device_id = " +
				                AndrospyMain.gb_DeviceId;
			} else if (sModel == "Google Map") {
				System.out.println("Google Map");

			} else if (sModel == "FB_CONTACT") {
				System.out.println("Facebook Contacts");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id," +
				                " IFNULL(first_name, '') AS 'First Name'," +
				                " IFNULL(last_name, '') AS 'Last Name'," +
				                " IFNULL(display_name, '') AS 'Display Name'," +
				                " IFNULL(added_time, '') AS 'Time Stamp'," +
				                " IFNULL(type, '') AS 'Contact Type'" + " FROM Fb_Contacts" +
				                " WHERE case_id = " + AndrospyMain.gb_CaseId + " AND device_id = " +
				                AndrospyMain.gb_DeviceId + " ORDER BY display_name ASC";
			} else if (sModel == "FB_MESSAGE") {
				System.out.println("Facebook Messages");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id," +
				                " IFNULL(sender, '') AS 'Sender'," +
				                " IFNULL(text, '') AS 'Message'," +
				                " IFNULL(conversation_date, '') AS 'Date'," +
				                " IFNULL(logitude, '') AS 'Longitude'," +
				                " IFNULL(latitude, '') AS 'Latitude'" + " FROM Facebook_Message" +
				                " WHERE case_id = " + AndrospyMain.gb_CaseId + " AND device_id = " +
				                AndrospyMain.gb_DeviceId;
			} else if (sModel == "FB_NOTIFICATION") {
				System.out.println("Facebook Notification");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id," +
				                " IFNULL(notification, '') AS 'Notification'," +
				                " IFNULL(update_date, '') AS 'Date'" +
				                " FROM Facebook_Notifications" + " WHERE case_id = " +
				                AndrospyMain.gb_CaseId + " AND device_id = " +
				                AndrospyMain.gb_DeviceId;
			} else if (sModel == "LINKEDIN_PROFILE") {
				System.out.println("LinkedIn Profile");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id," +
				                " IFNULL(first_name, '') AS 'First Name'," +
				                " IFNULL(last_name, '') AS 'Last Name'," +
				                " IFNULL(display_name, '') AS 'Display Name'," +
				                " IFNULL(join_date, '') AS 'Join Date'," +
				                " IFNULL(country, '') AS Country," +
				                " IFNULL(connections, 0) AS 'No. of Connections'" +
				                " FROM Linkedin_Profile" + " WHERE case_id = " +
				                AndrospyMain.gb_CaseId + " AND device_id = " +
				                AndrospyMain.gb_DeviceId;
			} else if (sModel == "LINKEDIN_CONNECTIONS") {
				System.out.println("linkedin connections");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id," +
				                " IFNULL(first_name, '') AS 'First Name'," +
				                " IFNULL(last_name, '') AS 'Last Name'," +
				                " IFNULL(display_name, '') AS 'Display Name'," +
				                " IFNULL(title, '') AS 'Title'" + " FROM Linkedin_Connections" +
				                " WHERE case_id = " + AndrospyMain.gb_CaseId + " AND device_id = " +
				                AndrospyMain.gb_DeviceId + " ORDER By display_name ASC";

			} else if (sModel == "LINKEDIN_INVITATIONS") {
				System.out.println("Linkedin Invitations");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id," +
				                " IFNULL(from_display_name, '') AS 'Sender Name'," +
				                " IFNULL(title, '') AS 'Title'," +
				                " IFNULL(invitation_date, '') AS 'Invitation Date'" +
				                " FROM Linkedin_Invitation" + " WHERE case_id = " +
				                AndrospyMain.gb_CaseId + " AND device_id = " +
				                AndrospyMain.gb_DeviceId + " ORDER BY invitation_date ASC";

			} else if (sModel == "LINKEDIN_MESSAGES") {
				System.out.println("Linkedin Messages");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id," +
				                " IFNULL(from_display_name, '') AS 'Sender Name'," +
				                " IFNULL(from_title, '') AS 'Sender Title'," +
				                " IFNULL(message_date, '') AS 'Message Date'," +
				                " IFNULL(subject, '') AS 'Subject'," +
				                " IFNULL(message_body, '') AS 'Message'" +
				                " FROM Linkedin_Message" + " WHERE case_id = " +
				                AndrospyMain.gb_CaseId + " AND device_id = " +
				                AndrospyMain.gb_DeviceId + " ORDER BY message_date ASC";

			} else if ((sModel == "DEFAULT_BROWSER_HISTORY") ||
			           (sModel == "CHROME_BROWSER_HISTORY") ||
			           (sModel == "Firefox Browser History")) {
				System.out.println("Browser History");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id," +
				                " IFNULL(title, '') AS Title," + " IFNULL(url, '') AS URL," +
				                " IFNULL(last_visit_date, '') AS 'Last Visit Date'," +
				                " IFNULL(visits, '') AS 'No of Visitis'" + " FROM Browser_History" +
				                " WHERE case_id = " + AndrospyMain.gb_CaseId + " AND device_id = " +
				                AndrospyMain.gb_DeviceId;

				if (sModel == "CHROME_BROWSER_HISTORY") {
					query += " AND browser = 'CHROME'";
				} else if (sModel == "DEFAULT_BROWSER_HISTORY") {
					query += " AND browser = 'DEFAULTB'";
				} else if (sModel == "Firefox Browser History") {
					query += " AND browser = 'Firefox'";
				}

			} else if ((sModel == "DEFAULT_BROWSER_SEARCH") ||
			           (sModel == "CHROME_BROWSER_SEARCH") ||
			           (sModel == "Firefox lk.score.androphsy.main.Search")) {
				System.out.println("Web lk.score.androphsy.main.Search");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id," +
				                " IFNULL(search_text, '') AS 'Search Text'," +
				                " IFNULL(search_date, '') AS 'Search Date'" + " FROM WebSearch" +
				                " WHERE case_id = " + AndrospyMain.gb_CaseId + " AND device_id = " +
				                AndrospyMain.gb_DeviceId;

				if (sModel == "CHROME_BROWSER_SEARCH") {
					query += " AND browser = 'CHROME'";
				} else if (sModel == "DEFAULT_BROWSER_SEARCH") {
					query += " AND browser = 'DEFAULTB'";
				} else if (sModel == "Firefox lk.score.androphsy.main.Search") {
					query += " AND browser = 'Firefox'";
				}

			} else if ((sModel == "DEFAULT_BROWSER_BOOKMARK") || (sModel == "Firefox Bookmarks")) {
				System.out.println("Bookmarked URLs");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id," +
				                " IFNULL(title, '') AS Title," + " IFNULL(url, '') AS URL," +
				                " IFNULL(created_date, '') AS 'Creaded Date'," +
				                " IFNULL(modified_date, '') AS 'Modified Date'," +
				                " IFNULL(deleted, '') AS Deleted," +
				                " IFNULL(account_name, '') AS 'Created By'," +
				                " IFNULL(account_type, '') AS 'Account Type'" +
				                " FROM Androspy_Bookmark" + " WHERE case_id = " +
				                AndrospyMain.gb_CaseId + " AND device_id = " +
				                AndrospyMain.gb_DeviceId;

				if (sModel == "DEFAULT_BROWSER_BOOKMARK") {
					query += " AND browser = 'DEFAULTB'";
				} else if (sModel == "Firefox Bookmarks") {
					query += " AND browser = 'Firefox'";
				}

			} else if ((sModel == "DEFAULT_BROWSER_COOKIES") ||
			           (sModel == "CHROME_BROWSER_COOKIES") || (sModel == "firefox Cookies")) {
				System.out.println("Browser Cookies");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id," +
				                " IFNULL(host, '') AS 'Host'," +
				                " IFNULL(cookie_name, '') AS 'Cookie Name'," +
				                " IFNULL(cookie_value, '') AS 'Cookie Value'," +
				                " IFNULL(path,'') AS Path, " + " IFNULL(secure, '') AS Secure," +
				                " IFNULL(httponly, '') AS 'Http Only'," +
				                " IFNULL(created_date, '') AS 'Created Date'," +
				                " IFNULL(expire_date, '') AS 'Expire Date'," +
				                " IFNULL(last_access_date, '') AS 'Last Access Date'" +
				                " FROM Browser_Cookies" + " WHERE case_id = " +
				                AndrospyMain.gb_CaseId + " AND device_id = " +
				                AndrospyMain.gb_DeviceId;

				if (sModel == "CHROME_BROWSER_COOKIES") {
					query += " AND browser = 'CHROME'";
				} else if (sModel == "DEFAULT_BROWSER_COOKIES") {
					query += " AND browser = 'DEFAULTB'";
				} else if (sModel == "firefox Cookies") {
					query += " AND browser = 'Firefox'";
				}

			} else if (sModel == "DEFAULT_BROWSER_WEB_FORM") {
				System.out.println("DEFAULT_BROWSER_WEB_FORM");
				query =
				        "SELECT d.favourite as Favourite, d.id, d.case_id, d.device_id," +
				                " IFNULL(url, '') AS 'URL'," +
				                " IFNULL(name, '') AS 'Parameter Name'," +
				                " IFNULL(value, '') AS 'Parameter Value'" +
				                " FROM WebForm f, WebFormData d" +
				                " WHERE f.case_id = d.case_id AND f.device_id = d.device_id" +
				                " AND d.urlid = f.id" + " AND d.case_id = " +
				                AndrospyMain.gb_CaseId + " AND d.device_id = " +
				                AndrospyMain.gb_DeviceId;
			} else if (sModel == "CHROME_CREDIT_CARD") {
				System.out.println("chrome credit cards");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id," +
				                " IFNULL(name, '') AS Name," +
				                " IFNULL(expire_month, '') AS 'Expire Month'," +
				                " IFNULL(expire_year, '') AS 'Expire Year'," +
				                " IFNULL(date_modified, '') AS 'Last Used Date'" +
				                " FROM Browser_Credit_Card" + " WHERE case_id = " +
				                AndrospyMain.gb_CaseId + " AND device_id = " +
				                AndrospyMain.gb_DeviceId + " AND browser = 'CHROME'";
			} else if (sModel == "ALL_CALL_LOG") {
				System.out.println("ALL_CALL_LOG");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, contact_no AS 'Contact No', name AS 'Name'," +
				                " date AS 'Contacted Date', duration AS 'Call Duration', " +
				                "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'" +
				                " FROM Event_Log" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId +
				                " AND logtype = " +
				                100;
			} else if (sModel == "INCOMING_CALLS") {
				System.out.println("incomming calls");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, contact_no AS 'Contact No', name AS 'Name'," +
				                " date AS 'Contacted Date', duration AS 'Call Duration', " +
				                "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'" +
				                " FROM Event_Log" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId +
				                " AND logtype = " +
				                100 + " AND type = " + 1;
			} else if (sModel == "OUT_CALLS") {
				System.out.println("outgoign calls");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, contact_no AS 'Contact No', name AS 'Name'," +
				                " date AS 'Contacted Date', duration AS 'Call Duration', " +
				                "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'" +
				                " FROM Event_Log" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId +
				                " AND logtype = " +
				                100 + " AND type = " + 2;
			} else if (sModel == "MISSED_CALLS") {
				System.out.println("missed calls");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, contact_no AS 'Contact No', name AS 'Name'," +
				                " date AS 'Contacted Date', duration AS 'Call Duration', " +
				                "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'" +
				                " FROM Event_Log" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId +
				                " AND logtype = " +
				                100 + " AND type = " + 3;
			} else if (sModel == "CANCELLED_CALLS") {
				System.out.println("missed calls");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, contact_no AS 'Contact No', name AS 'Name'," +
				                " date AS 'Contacted Date', duration AS 'Call Duration', " +
				                "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'" +
				                " FROM Event_Log" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId +
				                " AND logtype = " +
				                500 + " AND type = " + 2;
			} else if (sModel == "ALL_SMS_LOG") {
				System.out.println("ALL_SMS_LOG");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, contact_no AS 'Contact No', name AS 'Name'," +
				                " date AS 'Contacted Date', m_subject AS 'Message Subject', m_content AS 'Message Body', " +
				                "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'" +
				                " FROM Event_Log" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId +
				                " AND logtype = " +
				                300;
			} else if (sModel == "SMS_INBOX") {
				System.out.println("SMS Inbox");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, contact_no AS 'Contact No', name AS 'Name'," +
				                " date AS 'Contacted Date', m_subject AS 'Message Subject', m_content AS 'Message Body', " +
				                "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'" +
				                " FROM Event_Log" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId +
				                " AND logtype = " +
				                300 + " AND type = " + 1;
			} else if (sModel == "SMS_OUTBOX") {
				System.out.println("SMS_Outbox");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, contact_no AS 'Contact No', name AS 'Name'," +
				                " date AS 'Contacted Date', m_subject AS 'Message Subject', m_content AS 'Message Body', " +
				                "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'" +
				                " FROM Event_Log" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId +
				                " AND logtype = " +
				                300 + " AND type != " + 1;
			} else if (sModel == "ALL_MMS_LOG") {
				System.out.println("ALL_CALL_LOG");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, contact_no AS 'Contact No', name AS 'Name'," +
				                " date AS 'Contacted Date', m_subject AS 'Message Subject', m_content AS 'Message Body', " +
				                "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'" +
				                " FROM Event_Log" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId +
				                " AND logtype = " +
				                200;
			} else if (sModel == "MMS_INBOX") {
				System.out.println("MMS Inbox");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, contact_no AS 'Contact No', name AS 'Name'," +
				                " date AS 'Contacted Date', m_subject AS 'Message Subject', m_content AS 'Message Body', " +
				                "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'" +
				                " FROM Event_Log" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId +
				                " AND logtype = " +
				                200 + " AND type = " + 1;
			} else if (sModel == "MMS_OUTBOX") {
				System.out.println("mms outbox");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, contact_no AS 'Contact No', name AS 'Name'," +
				                " date AS 'Contacted Date', m_subject AS 'Message Subject', m_content AS 'Message Body', " +
				                "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'" +
				                " FROM Event_Log" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId +
				                " AND logtype = " +
				                200 + " AND type != " + 1;
			} else if (sModel == "Wi-Fi Access Points") {
				System.out.println("Wi-Fi Password");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, ssid AS 'SSID', password AS 'Password'," +
				                " key_mgmt AS 'Key Management'" +
				                " FROM Wifi_Info" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId;
			} else if (sModel == "BT_USED") {
				System.out.println("Bluetooth Transfers");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, device_address AS 'Target Device BT Address', mimetype AS 'MIME Type', " +
				                " in_file_name AS 'File Name', totalbytes AS 'File Size', location AS Location, direction AS Direction, visibility AS Visibility, confirm AS Confirm," +
				                " status AS 'Transfer Status', date AS 'Transfer Date', scanned As Scanned" +
				                " FROM Bluetooth_Tranfer" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId;
			} else if (sModel == "BT_SEEN") {
				System.out.println("Bluetooth Device");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, bmac AS 'Bluetooth Address'," +
				                " IFNULL(device_name, '') AS 'Device Name', IFNULL(seen_date, '') AS 'Last Seen Date', used_date AS 'Last_Paired_Date'" +
				                " FROM Bluetooth_Device" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId;
			} else if (sModel == "Skype Account") {
				System.out.println("Skype Account");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, skypename, fullname, birthday, country, city reg_date, gender" +
				                " FROM Skype_Account" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId;

			} else if (sModel == "Skype Contacts") {
				System.out.println("Skype Contacts");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id,skypename, fullname, birthday, displayname, country, city, gender, phone" +
				                " FROM Skype_Contacts" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId;

			} else if (sModel == "Skype Calls") {
				System.out.println("Skype Calls");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, identity, display_name, call_duration, start_time_stamp" +
				                " FROM Skype_Call" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " + AndrospyMain.gb_DeviceId;

			} else if (sModel == "Skype Messages") {
				System.out.println("Skype Messages");
				query =
				        "SELECT favourite as Favourite, id, case_id, device_id, author, from_display_name, dialog_partner, message, time_stamp" +
				                " FROM Skype_Message" +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId;

			}

			if (query == "") {
				return;
			}
			System.out.println("iua " + query);
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmeta = rs.getMetaData();
			int count = rsmeta.getColumnCount();

			for (int i = 1; i <= count; i++) {
				TableColumn tcol = new TableColumn(table, SWT.NONE);
				String colname = rsmeta.getColumnLabel(i);
				tcol.setText(colname);
				tcol.setAlignment(SWT.LEFT);
				tcol.setResizable(true);
				if (colname.equals("Favourite")) {
					tcol.setWidth(60);
					tcol.setAlignment(SWT.CENTER);
				} else if ((colname.equals("id")) || (colname.equals("case_id")) ||
				           (colname.equals("device_id"))) {
					tcol.setWidth(0);
				} else {
					tcol.setWidth(200);
				}
			}

			// addItemsCombo(sModel);

			String values[] = new String[count];
			while (rs.next()) {
				TableItem titem = new TableItem(table, SWT.NONE);
				for (int i = 1; i < count; i++) {

					values[i] = rs.getString((i + 1));
				}
				titem.setText(values);

				if (rs.getString(1).equals("1")) {
					titem.setChecked(true);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void removeColumns(Table intable) {
		TableColumn cols[] = intable.getColumns();
		for (TableColumn tableColumn : cols) {
			tableColumn.dispose();
		}

	}
}
