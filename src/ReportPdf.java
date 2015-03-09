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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author indeewari
 *
 */
public class ReportPdf {
	private boolean bflagall 			= true;
	private String gen_date				= "";
	private static Font TitleFont 		= new Font(Font.FontFamily.TIMES_ROMAN, 18,	Font.UNDERLINE);
	private static Font HeadingFont 	= new Font(Font.FontFamily.TIMES_ROMAN, 16,	Font.BOLD);
	private static Font SubHeadingFont 	= new Font(Font.FontFamily.TIMES_ROMAN, 14,	Font.BOLD);
	
	class TableHeader extends PdfPageEventHelper{
		String header;
		PdfTemplate total;
		Phrase[] footer = new Phrase[2];
		
		public void setHeader(String header){
			this.header = header;
		}
		
		public void onOpenDocument(PdfWriter writer, Document document){
			total 			= writer.getDirectContent().createTemplate(30, 16);			
		}
		
		public void onEndPage(PdfWriter writer, Document document){
			PdfPTable table = new PdfPTable(3); 
			try {
				table.setWidths(new int[] {24,24,2});
				table.setTotalWidth(527);
				table.setLockedWidth(true);
				table.getDefaultCell().setFixedHeight(20);
				table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
				
				table.addCell(header);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(String.format("Page %d of", writer.getPageNumber()));
				PdfPCell cell = new PdfPCell(Image.getInstance(total));
				cell.setBorder(Rectangle.NO_BORDER);
				table.addCell(cell);
				table.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
				
				Connection con  = new ConnectDb().getConnection();
				Statement stmt 	= con.createStatement();
				ResultSet rs 	= stmt.executeQuery("SELECT d.device_name, d.imei" +
						" FROM Androspy_Case c, Androspy_Device d" +
						" WHERE c.case_id = " + AndrospyMain.gb_CaseId + " AND d.device_id = " + AndrospyMain.gb_DeviceId);
				while (rs.next()){
					footer[0] 	= new Phrase("Device:" + rs.getString("device_name") 
							+ ", IMEI:" + rs.getString("imei"));
					break;
				}
				footer[1] = new Phrase(AndrospyMain.gb_username + " " + gen_date);
				
				
				ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, 
					new Phrase(footer[0]), document.left(), document.bottom(), 0);
				ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT,  
						new Phrase(footer[1]), document.right(), document.bottom(), 0);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void onCloseDocument(PdfWriter writer, Document document){
			ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber() -1)), 2, 2, 0);
		}
	}

	public void generatePdf(String feature) {
		Document document = new Document();
		System.out.println("report : " + feature);		
		try {
			PdfWriter.getInstance(document, new FileOutputStream(
					AndrospyMain.gb_CasePath + "Analysis/" + feature + ".pdf"));
			SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			AndrospyLog.Logdata(sf.format(new Date()), "Generate report: " + feature + ".pdf");
			document.open();
			addMetaData(document);
			addTitlePage(document);
			addDevicePage(document);
			addContent(document, feature);
			document.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String generatePdf(ArrayList<String> featurelist) {
		File repdir = new File(AndrospyMain.gb_CasePath + "Analysis/Reports");
		if(!repdir.exists()){
			repdir.mkdir();
		} 
		
		
		Document document 	= new Document(PageSize.A4, 36, 36, 54, 36);
		Date date 			= new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		gen_date 			= sf.format(date);
		String name = AndrospyMain.gb_CasePath + "Analysis/Reports/" + "Report_" + gen_date + ".pdf";
		
		SimpleDateFormat sf2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		AndrospyLog.Logdata(sf2.format(new Date()), "Generate report: " + name);
		
		try {
			PdfWriter writer 	= PdfWriter.getInstance(document, new FileOutputStream(name));
			TableHeader event 	= new TableHeader();
			writer.setPageEvent(event);
			event.setHeader("Androspy Evidence Report - Confidential");
			document.open();
			addMetaData(document);
			addTitlePage(document);
			addDevicePage(document);
			
			
			Iterator<String> it = featurelist.iterator();
			while(it.hasNext()){
				String feature = it.next();
				System.out.println("report : " + feature);
				addContent(document, feature);
			}	
					
			document.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return name;
	}

	private void addDevicePage(Document document){
		Anchor anchor = new Anchor("Device Information");
		anchor.setName("Device Information");
		Chapter chap = new Chapter(new Paragraph(anchor), 1);

		Paragraph subpara = new Paragraph("Device Information", HeadingFont);
		subpara.setAlignment(Paragraph.ALIGN_CENTER);
		addEmptyLine(subpara, 1);
		createTableRowFormat(subpara, "DeviceBasicInfo");

		try {
			document.add(subpara);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void addContent(Document document, String feature) {
		String title = "";

		if (feature == "User Credentials"){
			title = "User Credentials";
			
		} else if (feature == "SMS") {
			title = "Short Message Service (SMS)";

		} else if (feature == "Phone Book") {
			title = "Contact Numbers/Phone Book";

		} else if (feature == "Gmail") {
			title = "Gmail Information";

		} else if (feature == "Calendar") {
			title = "Calendar Events";

		} else if (feature == "YouTube") {
			title = "YouTube History";

		} else if (feature.equals("FACEBOOK_MESSAGES")) {
			title = "Facebook Messages";
			
		} else if (feature.equals("FACEBOOK_CONTACTS")) {
			title = "Facebook Contacts";
			
		} else if (feature.equals("FACEBOOK_NOTIFICATIONS")) {
			title = "Facebook Notifications";
			
		} else if (feature.equals("LINKEDIN_PROFILE")) {
			title = "LinkedIn Profile";
			
		} else if (feature.equals("LINKEDIN_CONNECTIONS")) {
			title = "LinkedIn Connections";
			
		} else if (feature.equals("LINKEDIN_INVITATIONS")) {
			title = "LinkedIn Invitations";
			
		} else if (feature.equals("LINKEDIN_MESSAGES")) {
			title = "LinkedIn Messages";
			
		} else if (feature.equals("DEFAULT_WEB_FORM_DATA")) {
			title = "Interacted Web Forms and Supplied Parameter Values";

		} else if (feature.equals("DEFAULT_COOKIES")) {
			title = "Default Browser Cookie Information";

		} else if (feature.equals("DEFAULT_WEB_SEARCH")) {
			title = "Default Browser Web Search Text";

		} else if (feature.equals("DEFAULT_BOOKMARKS")) {
			title = "Bookmarked URL Information";

		} else if (feature.equals("DEFAULT_BROWSER_HISTORY")) {
			title = "Default Browser History";
			
		} else if (feature.equals("CHROME_BROWSER_HISTORY")) {
			title = "Chrome Browser History";
			
		} else if (feature.equals("CHROME_COOKIES")) {
			title = "Chrome Browser Cookie Information";

		} else if (feature.equals("CHROME_WEB_SEARCH")) {
			title = "Chrome Web Search Text";

		} else if (feature.equals("CHROME_CREDIT_CARD")) {
			title = "Chrome Credit Card Information";

		} else if (feature.equals("Firefox_COOKIES")) {
			title = "Firefox Browser Cookies";
			
		} else if (feature.equals("Firefox_WEB_SEARCH")) {
			title = "Firefox WebSearch";
			
		} else if (feature.equals("Firefox_BOOKMARKS")) {
			title = "Firefox Bookmark";
			
		} else if (feature.equals("Firefox_BROWSER_HISTORYy")) {
			title = "Firefox Browser History";
			
		} else if (feature.equals("Call Logs")) {
			title = "Call Logs";

		} else if (feature.equals("SMS Logs")) {
			title = "SMS Logs";

		} else if (feature.equals("MMS Logs")) {
			title = "MMS Logs";

		} else if (feature == "Wi-Fi Access Points") {
			title = "Wi-Fi Access Points";

		} else if (feature.equals("Wi-Fi Access Points")) {
			title = "Wi-Fi Access Points";

		} else if (feature == "Bluetooth Data") {
			title = "Bluetooth Information";

		} else if (feature.equals("Bluetooth Data")) {
			title = "Bluetooth Information";

		} else if (feature == "Skype Account"){
			title = "Skype Account";
			
		} else if (feature == "Skype Contacts"){
			title = "Skype Contacts";
			
		} else if (feature == "Skype Calls"){
			title = "Skype Call";
			
		} else if (feature == "Skype Messages"){
			title = "Skype Message";
			
		} 

		try {
			document.newPage();

			Paragraph subpara = new Paragraph(title, HeadingFont);
			subpara.setAlignment(Paragraph.ALIGN_CENTER);
			addEmptyLine(subpara, 1);
			
			if(feature == "User Credentials"){
				Paragraph p1 =new Paragraph("Primary Account Detail", SubHeadingFont);
				addEmptyLine(p1, 1);
				CreateTable(p1, "PRIMARY_ACC");
				addEmptyLine(p1, 1);
				subpara.add(p1);
				
				Paragraph p2 =new Paragraph("Secondary Account Detail", SubHeadingFont);
				addEmptyLine(p2, 1);
				CreateTable(p2, "OTHER_ACC");
				subpara.add(p2);
				
			} else if (feature.equals("Call Logs")){
				Paragraph p1 =new Paragraph("Incomming Calls Log", SubHeadingFont);
				addEmptyLine(p1, 1);
				CreateTable(p1, "INCOMING_CALLS");
				addEmptyLine(p1, 1);
				subpara.add(p1);
				
				Paragraph p2 =new Paragraph("Missed Calls Log", SubHeadingFont);
				addEmptyLine(p2, 1);
				CreateTable(p2, "MISSED_CALLS");
				subpara.add(p2);
				
				Paragraph p3 =new Paragraph("Cancelled Calls Log", SubHeadingFont);
				addEmptyLine(p3, 1);
				CreateTable(p3, "CANCELLED_CALLS");
				addEmptyLine(p3, 1);
				subpara.add(p3);
				
				Paragraph p4 =new Paragraph("Outgoing Calls Log", SubHeadingFont);
				addEmptyLine(p4, 1);
				CreateTable(p4, "OUTGOING_CALLS");
				subpara.add(p4);
				
			} else if (feature.equals("SMS Logs")){
				Paragraph p1 =new Paragraph("Incomming SMS Log", SubHeadingFont);
				addEmptyLine(p1, 1);
				CreateTable(p1, "SMS_INBOX");
				addEmptyLine(p1, 1);
				subpara.add(p1);
				
				Paragraph p2 =new Paragraph("Outgoing SMS Log", SubHeadingFont);
				addEmptyLine(p2, 1);
				CreateTable(p2, "SMS_OUTBOX");
				subpara.add(p2);
				
			} else if (feature.equals("MMS Logs")){
				
				Paragraph p1 =new Paragraph("Incomming MMS Log", SubHeadingFont);
				addEmptyLine(p1, 1);
				CreateTable(p1, "MMS_INBOX");
				addEmptyLine(p1, 1);
				subpara.add(p1);
				
				Paragraph p2 =new Paragraph("Outgoing MMS Log", SubHeadingFont);
				addEmptyLine(p2, 1);
				CreateTable(p2, "MMS_OUTBOX");
				subpara.add(p2);
				
			} else if(feature == "Bluetooth Data"){
				Paragraph p1 =new Paragraph("Nearby Bluetooth Device Information", SubHeadingFont);
				addEmptyLine(p1, 1);
				CreateTable(p1, "BT_SEEN");
				addEmptyLine(p1, 1);
				subpara.add(p1);
				
				Paragraph p2 =new Paragraph("Contacted Bluetooth Device Information", SubHeadingFont);
				addEmptyLine(p2, 1);
				CreateTable(p2, "BT_USED");
				subpara.add(p2);
				
			} else if(feature.equals("Bluetooth Data")){
				Paragraph p1 =new Paragraph("Nearby Bluetooth Device Information", SubHeadingFont);
				addEmptyLine(p1, 1);
				CreateTable(p1, "BT_SEEN");
				addEmptyLine(p1, 1);
				subpara.add(p1);
				
				Paragraph p2 =new Paragraph("Contacted Bluetooth Device Information", SubHeadingFont);
				addEmptyLine(p2, 1);
				CreateTable(p2, "BT_USED");
				subpara.add(p2);
			} else {
				CreateTable(subpara, feature);
			}		

			document.add(subpara);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void CreateTable(Paragraph subpara, String feature) {
		System.out.println("Report Create Table: " + feature);
		try {
			String query = "";
			Connection conn = new ConnectDb().getConnection();
			Statement stmt = conn.createStatement();

			if (feature == "PRIMARY_ACC") {
				query = "SELECT host AS 'Account Type', username AS 'User Name',"
						+ " password AS 'Password (HASHED)'"
						+ " FROM Androspy_Account"
						+ " WHERE case_id = "
						+ AndrospyMain.gb_CaseId
						+ " AND device_id = "
						+ AndrospyMain.gb_DeviceId + " AND primary_acc = " + 1;

			} else if (feature == "OTHER_ACC") {
				query = "SELECT host AS 'Account Type', username AS 'User Name',"
						+ " password AS 'Password'"
						+ " FROM Androspy_Account"
						+ " WHERE case_id = "
						+ AndrospyMain.gb_CaseId
						+ " AND device_id = "
						+ AndrospyMain.gb_DeviceId
						+ " AND primary_acc != " + 1;

			} else if (feature == "SMS") {
				query = "SELECT subject AS 'Subject', body AS 'Message Body',"
						+ " type AS 'Message Type', date_received AS 'Received Date', date_sent AS 'Send Date'"
						+ " FROM Sms" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId;

			} else if (feature == "Phone Book") {
				query = "SELECT contact_no AS 'Contact No', name AS 'Name',"
						+ " times_contacted AS 'Times Contacted', last_time_contacted AS 'Last Contacted Date'"
						+ " FROM Contacts" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId;

			}  else if (feature == "Gmail") {
				query = "SELECT fromaddress AS 'From Address',"
						+ " toaddress AS 'To Address', ccaddress AS 'CC Address', bccaddress AS 'BCC Address',"
						+ " replytoaddress AS 'Reply To Address', subject AS 'Subject', snippet As 'Message Body', "
						+ "datesent AS 'Send Date', datereceive AS 'Received Date'"
						+ " FROM Gmail_Store" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId;

			} else if (feature == "Calendar") {
				query = "SELECT title AS 'Title', description AS 'Event', location AS 'Location'," +
						" start_time AS 'Start Time', end_time AS 'End Time', created_by AS 'Creator'"
						+ " FROM Calendar_Event" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId;

			} else if (feature == "YouTube") {
				query = "SELECT query AS 'Search' , search_date AS 'Time Stamp'"
						+ " FROM Youtube_History" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId;

			} else if (feature.equals("FACEBOOK_MESSAGES")) {
				query = "SELECT IFNULL(sender, '') AS 'Sender',"
						+ " IFNULL(text, '') AS 'Message',"
						+ " IFNULL(conversation_date, '') AS 'Date',"
						+ " IFNULL(logitude, '') AS 'Longitude',"
						+ " IFNULL(latitude, '') AS 'Latitude'"
						+ " FROM Facebook_Message"
						+ " WHERE case_id = "
						+ AndrospyMain.gb_CaseId
						+ " AND device_id = "
						+ AndrospyMain.gb_DeviceId;
				
			} else if (feature.equals("FACEBOOK_CONTACTS")) {
				query = "SELECT IFNULL(first_name, '') AS 'First Name',"
						+ " IFNULL(last_name, '') AS 'Last Name',"
						+ " IFNULL(display_name, '') AS 'Display Name',"
						+ " IFNULL(added_time, '') AS 'Time Stamp',"
						+ " IFNULL(type, '') AS 'Contact Type'"
						+ " FROM Fb_Contacts"
						+ " WHERE case_id = "
						+ AndrospyMain.gb_CaseId
						+ " AND device_id = "
						+ AndrospyMain.gb_DeviceId;
				
			} else if (feature.equals("FACEBOOK_NOTIFICATIONS")) {
				query = "SELECT IFNULL(notification, '') AS 'Notification',"
						+ " IFNULL(update_date, '') AS 'Date'"
						+ " FROM Facebook_Notifications"
						+ " WHERE case_id = "
						+ AndrospyMain.gb_CaseId
						+ " AND device_id = "
						+ AndrospyMain.gb_DeviceId;
				
			} else if (feature.equals("LINKEDIN_PROFILE")) {
				query = "SELECT first_name AS 'First Name', last_name AS 'Last Name', display_name AS 'Display Name'," +
						"country AS 'Country', join_date AS 'Join Date', connections AS 'No of Connections'"
						+ " FROM Linkedin_Profile" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId;
				
			} else if (feature.equals("LINKEDIN_CONNECTIONS")) {
				query = "SELECT IFNULL(first_name, '') AS 'First Name',"
						+ " IFNULL(last_name, '') AS 'Last Name',"
						+ " IFNULL(display_name, '') AS 'Display Name',"
						+ " IFNULL(title, '') AS 'Title'"
						+ " FROM Linkedin_Connections"
						+ " WHERE case_id = "
						+ AndrospyMain.gb_CaseId
						+ " AND device_id = "
						+ AndrospyMain.gb_DeviceId;
				
			} else if (feature.equals("LINKEDIN_INVITATIONS")) {
				query = "SELECT IFNULL(from_display_name, '') AS 'Sender Name',"
						+ " IFNULL(title, '') AS 'Title',"
						+ " IFNULL(invitation_date, '') AS 'Invitation Date'"
						+ " FROM Linkedin_Invitation"
						+ " WHERE case_id = "
						+ AndrospyMain.gb_CaseId
						+ " AND device_id = "
						+ AndrospyMain.gb_DeviceId;
				
			} else if (feature.equals("LINKEDIN_MESSAGES")) {
				query = "SELECT from_display_name AS 'Remote Party', from_title AS 'Remote Party Title'," +
						" message_date AS 'Message Date', subject AS Subject, message_body AS Message"
						+ " FROM Linkedin_Message" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId;
				
			} else if (feature.equals("DEFAULT_BROWSER_HISTORY")) {
				query = "SELECT title AS Title, url AS URL, "
						+ "last_visit_date AS 'Last Visit Date', visits AS 'No of Visitis'"
						+ " FROM Browser_History" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId
						+ " AND browser = 'DEFAULTB'";
				
			} else if (feature.equals("DEFAULT_BOOKMARKS")) {
				query = "SELECT title AS Title, url AS URL, "
						+ "created_date AS 'Creaded Date', modified_date AS 'Modified Date', deleted AS Deleted,"
						+ "account_name AS 'Created By', account_type AS 'Account Type'"
						+ " FROM Androspy_Bookmark" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId
						+ " AND browser = 'DEFAULTB'";

			} else if (feature.equals("DEFAULT_WEB_FORM_DATA")) {
				query = "SELECT url AS 'URL', name AS 'Parameter Name', value AS 'Parameter Value'"
						+ " FROM WebForm f, WebFormData d"
						+ " WHERE f.case_id = d.case_id AND f.device_id = d.device_id"
						+ " AND d.urlid = f.id"
						+ " AND d.case_id = "
						+ AndrospyMain.gb_CaseId
						+ " AND d.device_id = "
						+ AndrospyMain.gb_DeviceId;

			} else if (feature.equals("DEFAULT_COOKIES")) {
				query = "SELECT host AS 'Host', "
						+ "cookie_name AS 'Cookie Name', cookie_value AS 'Cookie Value', path AS Path, "
						+ "secure AS Secure, httponly AS 'Http Only', created_date AS 'Created Date', "
						+ "expire_date AS 'Expire Date', last_access_date AS 'Last Access Date'"
						+ " FROM Browser_Cookies" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId
						+ " AND browser = 'DEFAULTB'";

			} else if (feature.equals("DEFAULT_WEB_SEARCH")) {
				query = "SELECT search_text AS 'Search Text', "
						+ "search_date AS 'Search Date'" + " FROM WebSearch"
						+ " WHERE case_id = " + AndrospyMain.gb_CaseId
						+ " AND device_id = " + AndrospyMain.gb_DeviceId
						+ " AND browser = 'DEFAULTB'";
				
			} else if (feature.equals("CHROME_BROWSER_HISTORY")) {
				query = "SELECT title AS Title, url AS URL, "
						+ "last_visit_date AS 'Last Visit Date', visits AS 'No of Visitis'"
						+ " FROM Browser_History" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId
						+ " AND browser = 'CHROME'";
				
			} else if (feature.equals("CHROME_COOKIES")) {
				query = "SELECT host AS 'Host', "
						+ "cookie_name AS 'Cookie Name', cookie_value AS 'Cookie Value', path AS Path, "
						+ "secure AS Secure, httponly AS 'Http Only', created_date AS 'Created Date', "
						+ "expire_date AS 'Expire Date', last_access_date AS 'Last Access Date'"
						+ " FROM Browser_Cookies" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId
						+ " AND browser = 'CHROME'";

			} else if (feature.equals("CHROME_WEB_SEARCH")) {
				query = "SELECT search_text AS 'Search Text', "
						+ "search_date AS 'Search Date'" + " FROM WebSearch"
						+ " WHERE case_id = " + AndrospyMain.gb_CaseId
						+ " AND device_id = " + AndrospyMain.gb_DeviceId
						+ " AND browser = 'CHROME'";
				
			} else if (feature.equals("CHROME_CREDIT_CARD")) {
				query = "SELECT name AS Name, expire_month AS 'Expire Month', expire_year AS 'Exire Year', "
						+ "modified_date AS 'Modified Date'" + " FROM Browser_Credit_Card"
						+ " WHERE case_id = " + AndrospyMain.gb_CaseId
						+ " AND device_id = " + AndrospyMain.gb_DeviceId
						+ " AND browser = 'CHROME'";
				
			} else if (feature.equals("Firefox_BROWSER_HISTORY")) {
				query = "SELECT title AS Title, url AS URL, "
						+ "last_visit_date AS 'Last Visit Date', visits AS 'No of Visitis'"
						+ " FROM Browser_History" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId
						+ " AND browser = 'Firefox'";
				
			} else if (feature.equals("Firefox_BOOKMARKS")) {
				query = "SELECT title AS Title, url AS URL, "
						+ "created_date AS 'Creaded Date', modified_date AS 'Modified Date', deleted AS Deleted,"
						+ "account_name AS 'Created By', account_type AS 'Account Type'"
						+ " FROM Androspy_Bookmark" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId
						+ " AND browser = 'Firefox'";

			} else if (feature.equals("Firefox_WEB_SEARCH")) {
				query = "SELECT search_text AS 'Search Text', "
						+ "search_date AS 'Search Date'" + " FROM WebSearch"
						+ " WHERE case_id = " + AndrospyMain.gb_CaseId
						+ " AND device_id = " + AndrospyMain.gb_DeviceId
						+ " AND browser = 'Firefox'";
				
			} else if (feature.equals("Firefox_COOKIES")) {
				query = "SELECT host AS 'Host', "
						+ "cookie_name AS 'Cookie Name', cookie_value AS 'Cookie Value', path AS Path, "
						+ "secure AS Secure, httponly AS 'Http Only', created_date AS 'Created Date', "
						+ "expire_date AS 'Expire Date', last_access_date AS 'Last Access Date'"
						+ " FROM Browser_Cookies" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId
						+ " AND browser = 'Firefox'";

			} else if (feature == "ALL_CALL_LOG") {
				query = "SELECT contact_no AS 'Contact No', name AS 'Name',"
						+ " date AS 'Contacted Date', duration AS 'Call Duration', "
						+ "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'"
						+ " FROM Event_Log" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId + " AND logtype = " + 100;

			} else if (feature == "INCOMING_CALLS") {
				query = "SELECT contact_no AS 'Contact No', name AS 'Name',"
						+ " date AS 'Contacted Date', duration AS 'Call Duration', "						
						+ "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'"
						+ " FROM Event_Log" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId + " AND logtype = " + 100
						+ " AND type = " + 1;

			} else if (feature == "MISSED_CALLS") {
				query = "SELECT contact_no AS 'Contact No', name AS 'Name',"
						+ " date AS 'Contacted Date', duration AS 'Call Duration', "
						+ "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'"
						+ " FROM Event_Log" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId + " AND logtype = " + 100
						+ " AND type = " + 3;

			} else if (feature == "CANCELLED_CALLS") {
				query = "SELECT contact_no AS 'Contact No', name AS 'Name',"
						+ " date AS 'Contacted Date', duration AS 'Call Duration', "
				 		+ "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'"
						+ " FROM Event_Log" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId + " AND logtype = " + 500;
				
			} else if (feature == "OUTGOING_CALLS") {
				query = "SELECT contact_no AS 'Contact No', name AS 'Name',"
						+ " date AS 'Contacted Date', duration AS 'Call Duration', "
						+ "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'"
						+ " FROM Event_Log" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId + " AND logtype = " + 100
						+ " AND type = " + 2;

			} else if (feature == "ALL_SMS_LOG") {
				query = "SELECT contact_no AS 'Contact No', name AS 'Name',"
						+ " date AS 'Contacted Date', "
						+ "messageid AS 'Message_Id', m_subject AS 'Message Subject', m_content AS 'Message Body', "
						+ "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'"
						+ " FROM Event_Log" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId + " AND logtype = " + 300;

			} else if (feature == "SMS_INBOX") {
				query = "SELECT contact_no AS 'Contact No', name AS 'Name',"
						+ " date AS 'Contacted Date', "
						+ "messageid AS 'Message_Id', m_subject AS 'Message Subject', m_content AS 'Message Body', "
						+ "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'"
						+ " FROM Event_Log" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId + " AND logtype = " + 300
						+ " AND type = " + 1;

			} else if (feature == "SMS_OUTBOX") {
				query = "SELECT contact_no AS 'Contact No', name AS 'Name',"
						+ " date AS 'Contacted Date', "
						+ "messageid AS 'Message_Id', m_subject AS 'Message Subject', m_content AS 'Message Body', "
						+ "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'"
						+ " FROM Event_Log" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId + " AND logtype = " + 300
						+ " AND type != " + 1;

			} else if (feature == "ALL_MMS_LOG") {
				query = "SELECT contact_no AS 'Contact No', name AS 'Name',"
						+ " date AS 'Contacted Date', "
						+ "messageid AS 'Message_Id', m_subject AS 'Message Subject', m_content AS 'Message Body', "
						+ "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'"
						+ " FROM Event_Log" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId + " AND logtype = " + 200;

			} else if (feature == "MMS_INBOX") {
				query = "SELECT contact_no AS 'Contact No', name AS 'Name',"
						+ " date AS 'Contacted Date', "
						+ "messageid AS 'Message_Id', m_subject AS 'Message Subject', m_content AS 'Message Body', "
						+ "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'"
						+ " FROM Event_Log" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId + " AND logtype = " + 200
						+ " AND type = " + 1;

			} else if (feature == "MMS_OUTBOX") {
				query = "SELECT contact_no AS 'Contact No', name AS 'Name',"
						+ " date AS 'Contacted Date', "
						+ "messageid AS 'Message_Id', m_subject AS 'Message Subject', m_content AS 'Message Body', "
						+ "countryiso AS 'Country Code', geolocationcode AS 'Geo Location'"
						+ " FROM Event_Log" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId + " AND logtype = " + 200
						+ " AND type != " + 1;

			} else if (feature == "Wi-Fi Access Points") {
				query = "SELECT ssid AS 'SSID', password AS 'Password', key_mgmt AS 'Key Management'"
						+ " FROM Wifi_Info"
						+ " WHERE case_id = "
						+ AndrospyMain.gb_CaseId
						+ " AND device_id = "
						+ AndrospyMain.gb_DeviceId;

			} else if (feature.equals("Wi-Fi Access Points")) {
				query = "SELECT ssid AS 'SSID', password AS 'Password', key_mgmt AS 'Key Management'"
						+ " FROM Wifi_Info"
						+ " WHERE case_id = "
						+ AndrospyMain.gb_CaseId
						+ " AND device_id = "
						+ AndrospyMain.gb_DeviceId;

			} else if (feature == "BT_USED") {
				query = "SELECT device_address AS 'Target Device BT Address', mimetype AS 'MIME Type', "
						+ " in_file_name AS 'File Name', totalbytes AS 'File Size', location AS Location, direction AS Direction, visibility AS Visibility, confirm AS Confirm,"
						+ " status AS 'Transfer Status', date AS 'Transfer Date', scanned As Scanned"
						+ " FROM Bluetooth_Tranfer"
						+ " WHERE case_id = "
						+ AndrospyMain.gb_CaseId
						+ " AND device_id = "
						+ AndrospyMain.gb_DeviceId;

			} else if (feature == "BT_SEEN") {
				query = "SELECT bmac AS 'Bluetooth Address',"
						+ " device_name AS 'Device Name', seen_date AS 'Last Seen Date', 'used_date' AS 'Last_Paired_Date'"
						+ " FROM Bluetooth_Device" + " WHERE case_id = "
						+ AndrospyMain.gb_CaseId + " AND device_id = "
						+ AndrospyMain.gb_DeviceId;

			}  else if (feature == "Skype Account"){
				System.out.println("Skype Account");
				query = "SELECT skypename, fullname, birthday, country, city reg_date, gender"
						+ " FROM Skype_Account"
						+ " WHERE case_id = "
						+ AndrospyMain.gb_CaseId
						+ " AND device_id = "
						+ AndrospyMain.gb_DeviceId;
				
			} else if (feature == "Skype Contacts"){
				System.out.println("Skype Contacts");
				query = "SELECT skypename, fullname, birthday, displayname, country, city, gender, phone"
						+ " FROM Skype_Contacts"
						+ " WHERE case_id = "
						+ AndrospyMain.gb_CaseId
						+ " AND device_id = "
						+ AndrospyMain.gb_DeviceId;
				
			} else if (feature == "Skype Calls"){
				System.out.println("Skype Calls");
				query = "SELECT identity, display_name, call_duration, start_time_stamp"
						+ " FROM Skype_Call"
						+ " WHERE case_id = "
						+ AndrospyMain.gb_CaseId
						+ " AND device_id = "
						+ AndrospyMain.gb_DeviceId;
				
			} else if (feature == "Skype Messages"){
				System.out.println("Skype Messages");
				query = "SELECT author, from_display_name, dialog_partner, message, time_stamp"
						+ " FROM Skype_Message"
						+ " WHERE case_id = "
						+ AndrospyMain.gb_CaseId
						+ " AND device_id = "
						+ AndrospyMain.gb_DeviceId;
				
			}
			
			if (query != ("")) {
				if (!bflagall) {
					query += " AND favourite = " + 1;
				}
				
				System.out.println("query: " + query);

				ResultSet rs 				= stmt.executeQuery(query);
				ResultSetMetaData rsmeta 	= rs.getMetaData();
				int colcount 				= rsmeta.getColumnCount();
				PdfPTable pdftable 			= new PdfPTable(colcount);
				pdftable.setWidthPercentage(100/colcount);
				pdftable.setTotalWidth(527);
				pdftable.setLockedWidth(true);

				for (int i = 1; i <= colcount; i++) {
					PdfPCell c1 = new PdfPCell(new Phrase(
							rsmeta.getColumnLabel(i)));
					c1.setHorizontalAlignment(Element.ALIGN_CENTER);
					pdftable.addCell(c1);
				}
				pdftable.setHeaderRows(1);

				while (rs.next()) {
					for (int i = 1; i <= colcount; i++) {
						System.out.println("add table cell report "
								+ rs.getString(i));
						pdftable.addCell(rs.getString(i));
					}
				}

				subpara.add(pdftable);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addTitlePage(Document document) {
		String creator_name = "";
		try {
			Connection con 	= new ConnectDb().getConnection();
			Statement stmt 	= con.createStatement();
			ResultSet rs 	= stmt.executeQuery("SELECT first_name, last_name FROM Androspy_User WHERE user_name = '" 
					+ AndrospyMain.gb_username + "'");
			while(rs.next()){
				creator_name = rs.getString("first_name") + " " + rs.getString("last_name");
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Paragraph preface = new Paragraph();
		addEmptyLine(preface, 1);

		Paragraph title = new Paragraph("ANDROSPY SOFTWARE", TitleFont);
		addEmptyLine(title, 1);
		title.add(new Paragraph("Report Genereated By: "
				+ creator_name));
		title.add(new Paragraph("Date: " + gen_date));
		title.setAlignment(Paragraph.ALIGN_CENTER);

		preface.add(title);
		addEmptyLine(preface, 2);

		preface.add(new Paragraph("Case Basic Detail:"));
		addEmptyLine(preface, 1);

		createTableRowFormat(preface, "CaseInfo");

		try {
			document.add(preface);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.newPage();
	}

	private void createTableRowFormat(Paragraph preface, String feature) {
		try {
			String query = "";
			Connection conn = new ConnectDb().getConnection();
			Statement stmt = conn.createStatement();

			if(feature == "CaseInfo"){
				query = "SELECT case_id AS 'Case ID', case_name AS 'Case Name', investigator_id AS 'Investigatort', IFNULL(analyst_id, '') as 'Analyst' "
						+ "FROM Androspy_Case "
						+ "WHERE case_id = "
						+ AndrospyMain.gb_CaseId;
			} else if (feature == "DeviceBasicInfo") {
				query = "SELECT IFNULL(device_name, '') AS 'Device Name', IFNULL(model, '') AS 'Model', IFNULL(manufacturer, '') AS 'Manufacturer', IFNULL(os, '') AS OS, IFNULL(build_number, '') AS 'Build Number', IFNULL(sw_number, '') AS 'Software Number', IFNULL(serial_no,'') As 'Serial No'"
						+ " FROM Androspy_Device"
						+ " WHERE case_id = "
						+ AndrospyMain.gb_CaseId
						+ " AND device_id = "
						+ AndrospyMain.gb_DeviceId;
			}

			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmeta = rs.getMetaData();
			int colcount = rsmeta.getColumnCount();
			PdfPTable pdftable = new PdfPTable(2);

			while (rs.next()) {
				for (int i = 1; i <= colcount; i++) {
					String value = rs.getString(i).equals(null) ? "" : rs
							.getString(i);
					PdfPCell c1 = new PdfPCell(new Phrase(
							rsmeta.getColumnLabel(i) + ":"));
					PdfPCell c2 = new PdfPCell(new Phrase(value));
					c1.setBorder(Rectangle.NO_BORDER);
					c2.setBorder(Rectangle.NO_BORDER);
					pdftable.addCell(c1);
					pdftable.addCell(c2);
				}
			}

			preface.add(pdftable);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addEmptyLine(Paragraph paragraph, int count) {
		for (int i = 0; i < count; i++) {
			paragraph.add(new Paragraph(" "));
		}

	}

	private void addMetaData(Document document) {
		document.addTitle("Androspy Reporting");
		document.addSubject("Evidence Report");
		document.addKeywords("Androspy, Evidence");
		document.addCreator("Androspy");
		document.addAuthor(AndrospyMain.gb_username);
		document.addCreationDate();

	}

	public void setFlagAll(boolean b) {
		bflagall = b;

	}
}
