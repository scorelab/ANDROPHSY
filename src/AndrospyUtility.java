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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author indeewari
 *
 */
public class AndrospyUtility {

	private static Connection conn;
	private static Connection mysqlconn;
	private static Statement mysqlsmt;

	public static Statement createConnection(String dbfile) {
		mysqlconn = new ConnectDb().getConnection();
		try {
			mysqlsmt = mysqlconn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbfile);
			stmt = conn.createStatement();
			stmt.setQueryTimeout(10);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}

	//extract evidence from content providers
	protected void readRecords(String name) {
		String appPath = AndrospyMain.gb_CasePath + "Acquire/data/data/";
		String tableName = "";
		String query = "";

		if (name.equals("DEFAULT_BROWSER_HISTORY")) {
			appPath += "com.android.browser/databases/browser2.db";
			query = "SELECT _id, IFNULL(title, '') AS title, IFNULL(url, '') AS url, "
					+ "IFNULL(date, '') AS 'date', IFNULL(visits, '') As visits"
					+ " FROM history";
			insertRecords("DEFAULT_BROWSER_HISTORY", appPath, query);

		} else if (name.equals("DEFAULT_WEB_SEARCH")) {
			appPath += "com.android.browser/databases/browser2.db";
			query = "SELECT _id, IFNULL(search, '') AS search, IFNULL(date,'') AS 'date'"
					+ " FROM searches";
			insertRecords("DEFAULT_WEB_SEARCH", appPath, query);

		} else if (name.equals("DEFAULT_BOOKMARKS")) {
			appPath += "com.android.browser/databases/browser2.db";
			query = "SELECT _id, IFNULL(title, '') AS title, IFNULL(url, '') AS url,"
					+ " IFNULL(deleted, '') AS 'deleted', IFNULL(account_name, '') AS account_name,"
					+ " IFNULL(account_type,'') AS account_type, "
					+ "IFNULL(created,'') AS created, IFNULL(modified,'') AS modified"
					+ " FROM bookmarks";
			insertRecords("DEFAULT_BOOKMARKS", appPath, query);

		} else if (name.equals("DEFAULT_COOKIES")) {
			appPath += "com.android.browser/databases/webviewCookiesChromium.db";
			query = "SELECT datetime((creation_utc/1000000 -11644473600), \"unixepoch\", \"localtime\") AS creation_utc, IFNULL(host_key,'') AS host_key,"
					+ " IFNULL(name,'') AS name, IFNULL(value,'') AS value, IFNULL(path, '') As path,"
					+ " IFNULL(expires_utc, '') AS expires_utc, IFNULL(secure, '') AS secure,"
					+ " IFNULL(httponly, '') AS httponly, IFNULL(last_access_utc,'') AS last_access_utc"
					+ " FROM cookies";
			insertRecords("DEFAULT_COOKIES", appPath, query);

		} else if (name.equals("DEFAULT_WEB_FORM_DATA")) {
			appPath += "com.android.browser/databases/webview.db";
			// form urls
			query = "SELECT _id, IFNULL(url,'') AS url FROM formurl";
			insertRecords("FormUrl", appPath, query);
			// form data
			query = "SELECT _id, IFNULL(urlid,'') AS urlid, IFNULL(name,'') AS name, IFNULL(value,'') AS value"
					+ " FROM formdata";
			insertRecords("FormData", appPath, query);

		} else if (name.equals("CHROME_BROWSER_HISTORY")) {
			appPath += "com.android.chrome/app_chrome/Default/History";
			query = "SELECT v.id, IFNULL(u.title, '') AS title, IFNULL(u.url, '') AS url,"
					+ "  datetime((v.visit_time/1000000 -11644473600), \"unixepoch\", \"localtime\") AS visit_time, IFNULL(u.visit_count, '') AS visit_count "
					+ "FROM urls u, visits v WHERE v.url = u.id";
			insertRecords("CHROME_BROWSER_HISTORY", appPath, query);

		} else if (name.equals("CHROME_WEB_SEARCH")) {
			appPath += "com.android.chrome/app_chrome/Default/History";
			query = "SELECT IFNULL(k.url_id,'') AS url_id, IFNULL(k.term, '') AS term,"
					+ " datetime((u.last_visit_time/1000000 -11644473600), \"unixepoch\", \"localtime\") AS last_visit_time"
					+ " FROM keyword_search_terms k, urls u WHERE k.url_id = u.id";
			insertRecords("CHROME_WEB_SEARCH", appPath, query);

		} else if (name.equals("CHROME_COOKIES")) {
			appPath += "com.android.chrome/app_chrome/Default/Cookies";
			query = "SELECT datetime((creation_utc/1000000 -11644473600), \"unixepoch\", \"localtime\") AS creation_utc, IFNULL(host_key,'') AS host_key,"
					+ " IFNULL(name,'') AS name, IFNULL(value,'') AS value, IFNULL(path,'') AS path,"
					+ " datetime((expires_utc/1000000 -11644473600), \"unixepoch\", \"localtime\") AS expires_utc, IFNULL(secure,'') AS secure, "
					+ "IFNULL(httponly,'') AS httponly, datetime((last_access_utc/1000000 -11644473600), \"unixepoch\", \"localtime\") AS last_access_utc"
					+ " FROM cookies";
			insertRecords("CHROME_COOKIES", appPath, query);

		} else if (name.equals("CHROME_CREDIT_CARD")) {
			appPath += "com.android.chrome/app_chrome/Default/Web Data";
			query = "SELECT guid, IFNULL(name_on_card, '') AS name_on_card, "
					+ "IFNULL(expiration_month,'') AS expiration_month, IFNULL(expiration_year,'') AS expiration_year,"
					+ " datetime((date_modified/1000000 -11644473600), \"unixepoch\", \"localtime\") AS date_modified"
					+ " FROM credit_cards";
			insertRecords("CHROME_CREDIT_CARD", appPath, query);

		} else if (name.equals("Firefox_BROWSER_HISTORY")) {
			appPath += "org.mozilla.firefox/files/mozilla/9bmal7g9.default/browser.db";
			query = "SELECT _id, title, url, modified, visits FROM history";
			insertRecords("Firefox_BROWSER_HISTORY", appPath, query);
			
		} else if (name.equals("Firefox_BOOKMARKS")) {
			appPath += "org.mozilla.firefox/files/mozilla/9bmal7g9.default/browser.db";
			query = "SELECT _id, title, url, deleted, created, modified FROM bookmarks";
			insertRecords("Firefox_BOOKMARKS", appPath, query);
			
		} else if (name.equals("Firefox_WEB_SEARCH")) {
			appPath += "org.mozilla.firefox/files/mozilla/9bmal7g9.default/browser.db";
			query = "SELECT _id, query, date FROM searchhistory";
			insertRecords("Firefox_WEB_SEARCH", appPath, query);
			
		} else if (name.equals("Firefox_COOKIES")) {
			appPath += "org.mozilla.firefox/files/mozilla/9bmal7g9.default/cookies.sqlite";
			query = "SELECT name, value, host, path, expiry, lastAccessed, creationTime, " +
					"isSecure, isHttpOnly FROM moz_cookies";
			insertRecords("Firefox_COOKIES", appPath, query);
			
		} else if (name.equals("OPERA_COOKIES")) {
			appPath += "com.opera.browser/app_opera/cookies";
			query = "SELECT name, value, host_key, path, expires_utc, last_access_utc, creation_utc, " +
					"secure, httponly FROM cookies";
			insertRecords("OPERA_COOKIES", appPath, query);
			
		} else if (name.equals("LINKEDIN_CONNECTIONS")) {
			appPath += "com.linkedin.android/databases/linkedin.db";
			query = "SELECT _id, IFNULL(first_name, '') AS first_name, IFNULL(last_name, '') AS last_name,"
					+ " IFNULL(display_name, '') AS display_name, IFNULL(headline, '') AS headline"
					+ " FROM connections";
			insertRecords("LINKEDIN_CONNECTIONS", appPath, query);

		} else if (name.equals("LINKEDIN_INVITATIONS")) {
			appPath += "com.linkedin.android/databases/linkedin.db";
			query = "SELECT _id, IFNULL(from_display_name, '') AS from_display_name, "
					+ "IFNULL(from_headline, '') AS from_headline, IFNULL(timestamp, '') AS timestamp"
					+ " FROM invitations";
			insertRecords("LINKEDIN_INVITATIONS", appPath, query);

		} else if (name.equals("LINKEDIN_MESSAGES")) {
			appPath += "com.linkedin.android/databases/linkedin.db";
			query = "SELECT _id, IFNULL(from_display_name, '') AS from_display_name,"
					+ " IFNULL(from_headline, '') AS from_headline, IFNULL(timestamp, '') AS timestamp,"
					+ " IFNULL(subject, '') AS subject, IFNULL(body, '') AS body"
					+ " FROM messages";
			insertRecords("LINKEDIN_MESSAGES", appPath, query);

		} else if (name.equals("LINKEDIN_PROFILE")) {
			System.out.println("linkedin profile");
			appPath += "com.linkedin.android/databases/linkedin.db";
			query = "SELECT _id, IFNULL(person, '') AS person, IFNULL(connections, '') AS connections"
					+ " FROM profile";
			insertRecords("LINKEDIN_PROFILE", appPath, query);

		} else if (name.equals("EventLog")) {
			appPath += "com.sec.android.provider.logsprovider/databases/logs.db";
			query = "SELECT _id, IFNULL(number, '') AS number, IFNULL(date, '') AS 'date', "
					+ "IFNULL(duration, '') AS duration, IFNULL(type, '') AS type, IFNULL(name, '') AS name,"
					+ " IFNULL(countryiso, '') AS countryiso, IFNULL(geocoded_location, '') AS geocoded_location, "
					+ "IFNULL(messageid, '') AS messageid, IFNULL(m_subject, '') AS m_subject, "
					+ "IFNULL(m_content, '') AS m_content, IFNULL(logtype, '') AS logtype"
					+ " FROM logs";
			insertRecords("EventLog", appPath, query);
		} else if (name.equals("Locations")) { // htc specific
			appPath += "com.htc.provider.weather/databases/weather.db";
			tableName = "location";
		} else if (name.equals("Contacts")) {
			appPath += "/com.android.providers.contacts/databases/contacts2.db";
			query = "select name_raw_contact_id, IFNULL(times_contacted, '') AS times_contacted, "
					+ "IFNULL(last_time_contacted, '') AS last_time_contacted, IFNULL(d.data1, '') AS contact_no,"
					+ " IFNULL(x.data1, '') AS name "
					+ "from contacts, data d, data x "
					+ "where name_raw_contact_id = d.raw_contact_id "
					+ "AND d.raw_contact_id = x.raw_contact_id "
					+ "AND d.mimetype_id = 5 " + "AND x.mimetype_id = 7";
			insertRecords("Contacts", appPath, query);
		} else if (name.equals("Gmail")) {
			String gmail = new AdbExecCmd().getPrimaryMail();
			appPath += "com.google.android.gm/databases/mailstore." + gmail
					+ ".db";
			System.out.println("Gmail Path " + appPath);
			File tmp = new File(appPath);
			if (tmp.exists()) {
				System.out.println("Gmail Exists");
				query = "select c._id, IFNULL(m.fromAddress, '') AS fromAddress, IFNULL(m.toAddresses, '') AS toAddresses,"
						+ " IFNULL(m.ccAddresses, '') AS ccAddresses, IFNULL(m.bccAddresses, '') AS bccAddresses, "
						+ "IFNULL(m.replyToAddresses, '') AS replyToAddresses, IFNULL(m.dateSentMs, '') AS dateSentMs,"
						+ " IFNULL(m.dateReceivedMs, '') AS dateReceivedMs, IFNULL(m.subject, '') AS subject, IFNULL(m.snippet, '') AS snippet "
						+ "from conversations c, messages m where c._id = m.conversation";
				insertRecords("Gmail", appPath, query);
			}
		} else if (name.equals("Map")) {
			appPath += "com.google.android.apps.maps/databases/search_history.db";
			System.out.println("Map reading");
			query = "SELECT IFNULL(data1, '')AS data1, IFNULL(latitude, '') AS latitude, IFNULL(longitude, '') AS longitude,"
					+ " IFNULL(timestamp, '') AS timestamp"
					+ " FROM suggestions";
			insertRecords("Map", appPath, query);

		} else if (name.equals("Calendar")) {
			appPath += "com.android.providers.calendar/databases/calendar.db";
			query = "SELECT _id, IFNULL(title, '') AS title, IFNULL(eventlocation, '') AS eventlocation,"
					+ " IFNULL(description, '') AS description, IFNULL(dtstart, '') AS dtstart,"
					+ " IFNULL(dtend, '') AS dtend, IFNULL(organizer, '') AS organizer, IFNULL(deleted, '') AS deleted"
					+ " FROM Events";
			insertRecords("Calendar", appPath, query);

		} else if (name.equals("YouTube")) {
			appPath += "com.google.android.youtube/databases/history.db";
			query = "SELECT _id, IFNULL(query, '') AS query, IFNULL(date, '') AS 'date'"
					+ " FROM suggestions";
			insertRecords("YouTube", appPath, query);

		} else if (name.equals("Accounts")) {
			appPath += "com.android.browser/databases/webview.db";
			query = "SELECT _id, IFNULL(host, '') AS host, IFNULL(username, '') AS username, IFNULL(password, '') AS password"
					+ " FROM password";
			insertRecords("Accounts", appPath, query);

			String path = AndrospyMain.gb_CasePath
					+ "Acquire/data/system/users/0/accounts.db";
			query = "SELECT _id, IFNULL(type, '') AS type, IFNULL(name, '') AS name, IFNULL(password, '') AS password"
					+ " FROM accounts";
			insertRecords("AccountPrimary", path, query);
		} else if (name.equals("Bluetooth")) {
			appPath += "com.android.bluetooth/databases/btopp.db";
			query = "SELECT _id, IFNULL(mimetype, '') AS mimetype, IFNULL(hint, '') AS hint, IFNULL(_data, '') AS _data,"
					+ " IFNULL(direction, '') AS direction, IFNULL(destination, '') AS destination, IFNULL(visibility, '') AS visibility,"
					+ " IFNULL(confirm,'') AS confirm, IFNULL(status,'') AS status, IFNULL(total_bytes,'') AS total_bytes,"
					+ " IFNULL(timestamp,'') AS timestamp, IFNULL(scanned,'') AS scanned"
					+ " FROM btopp";
			insertRecords("Bluetooth", appPath, query);
		} else if (name.equals("SMS")) {
			appPath += "com.android.providers.telephony/databases/mmssms.db";
			query = "SELECT _id, IFNULL(address,'') AS address, IFNULL(date,'') AS 'date', IFNULL(date_sent, '') AS 'date_sent',"
					+ " IFNULL(subject, '') AS subject, IFNULL(body, '') AS body, IFNULL(service_center, '') AS service_center, IFNULL(type, '') AS type"
					+ " FROM sms";
			insertRecords("SMS", appPath, query);

		} else if (name.equals("FACEBOOK_CONTACTS")) {
			appPath += "com.facebook.katana/databases/contacts_db2";
			System.out.println("path " + appPath);
			query = "SELECT internal_id, IFNULL(first_name,'') AS first_name, IFNULL(last_name,'') AS last_name, IFNULL(display_name,'') AS display_name,"
					+ " IFNULL(added_time_ms,'') AS added_time_ms ,"
					+ "CASE WHEN link_type == '1' THEN 'Owner'"
					+ "ELSE 'Friend' END AS type " + " FROM contacts";
			insertRecords("FACEBOOK_CONTACTS", appPath, query);

		} else if (name.equals("FACEBOOK_MESSAGES")) {
			appPath += "com.facebook.katana/databases/threads_db2";
			query = "SELECT msg_id, IFNULL(text,'') AS text, IFNULL(sender,'') AS sender, IFNULL(timestamp_ms,'') AS timestamp_ms,"
					+ " IFNULL(coordinates,'') AS coordinates"
					+ " FROM messages" + " group by thread_id order by msg_id";
			insertRecords("FACEBOOK_MESSAGES", appPath, query);

		} else if (name.equals("FACEBOOK_NOTIFICATIONS")) {
			appPath += "com.facebook.katana/databases/notifications_db";
			query = "SELECT _id, IFNULL(updated,'') AS updated, IFNULL(graphql_text_with_entities,'') AS graphql_text_with_entities"
					+ " FROM gql_notifications";
			insertRecords("FACEBOOK_NOTIFICATIONS", appPath, query);

		} else if (name.equals("Skype Account")) {
			ArrayList<String> skypacc = getSkypeAccoountName();
			Iterator<String> it = skypacc.iterator();
			while(it.hasNext()){
				String skypename = it.next();
				appPath += "com.skype.raider/files/" + skypename + "/main.db";
				
				query = "SELECT id, skypename, IFNULL(fullname,'') AS fullname, IFNULL(birthday, '') AS birthday,"
						+ " IFNULL(country, '') AS country, IFNULL(city, '') AS city, IFNULL(registration_timestamp, '') AS registration_timestamp, "
						+ "CASE WHEN gender == 2 THEN 'female' ELSE 'male' END AS gender"
						+ " FROM Accounts;";
				insertRecords("Skype Account", appPath, query);
			}			

		} else if (name.equals("Skype Calls")) {
			ArrayList<String> skypacc = getSkypeAccoountName();
			Iterator<String> it = skypacc.iterator();
			while(it.hasNext()){
				String skypename = it.next();
				appPath += "com.skype.raider/files/" + skypename + "/main.db";
				
				query = "SELECT id, identity, IFNULL(dispname, '') AS displayname, IFNULL(call_duration,'') AS call_duration,"
						+ " IFNULL(start_timestamp, '') AS start_timestamp"
						+ " FROM CallMembers";
				insertRecords("Skype Calls", appPath, query);
			}
			
		} else if (name.equals("Skype Contacts")) {
			ArrayList<String> skypacc = getSkypeAccoountName();
			Iterator<String> it = skypacc.iterator();
			while(it.hasNext()){
				String skypename = it.next();
				appPath += "com.skype.raider/files/" + skypename + "/main.db";
				
				query = "SELECT id, skypename, IFNULL(fullname,'') AS fullname, IFNULL(birthday, '') AS birthday,"
						+ " IFNULL(displayname, '') AS displayname, IFNULL(phone_mobile, '') AS phone_mobile,"
						+ " IFNULL(country, '') AS country, IFNULL(city, '') AS city, "
						+ "CASE WHEN gender == 2 THEN 'female' ELSE 'male' END AS gender"
						+ " FROM Contacts";
				insertRecords("Skype Contacts", appPath, query);
			}

		} else if (name.equals("Skype Messages")) {
			ArrayList<String> skypacc = getSkypeAccoountName();
			Iterator<String> it = skypacc.iterator();
			while(it.hasNext()){
				String skypename = it.next();
				appPath += "com.skype.raider/files/" + skypename + "/main.db";
				
				query = "SELECT id, author, IFNULL(from_dispname,'') AS from_dispname, IFNULL( dialog_partner, '') AS  dialog_partner,"
						+ " IFNULL(timestamp, '') AS timestamp, IFNULL(body_xml, '') AS body_xml"
						+ " FROM Messages";
				insertRecords("Skype Messages", appPath, query);
			}
			
		} else {
			System.out.print("Not Support");
		}

	}

	private  ArrayList<String> getSkypeAccoountName() {
		ArrayList<String> accnmaelist = new ArrayList<String>();
		String name;
		String appPath = AndrospyMain.gb_CasePath + "Acquire/data/data/com.skype.raider/files/";
		File dir = new File(appPath);
		for (File file : dir.listFiles()) {
			if(file.isFile()){
				String filename = file.getName();
				if(filename.endsWith(".ecs")){
					name = filename.substring(0, (filename.lastIndexOf(".ecs")));
					accnmaelist.add(name);
				}
			}
		}
		return accnmaelist;
		
	}

	//store records in Androspydb
	protected void insertRecords(String identifier, String dbfile, String query) {
		try {
			String sinsert = "";
			Statement stmt = AndrospyUtility.createConnection(dbfile);
			ResultSet rs = stmt.executeQuery(query);

			if (identifier == "DEFAULT_BROWSER_HISTORY") {
				while (rs.next()) {
					Date dlastvisit = new Date(rs.getLong("date"));
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					String formatdate = sf.format(dlastvisit);
					sinsert = "INSERT INTO Browser_History VALUES ("
							+ rs.getInt("_id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ processSpecialCharacter(rs.getString("title"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("url"))
							+ "', '" + formatdate + "', " + rs.getInt("visits")
							+ ", " + 0 + ", 'DEFAULTB' )";
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "DEFAULT_WEB_SEARCH") {
				while (rs.next()) {
					Date dlastvisit = new Date(rs.getLong("date"));
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					String formatdate = sf.format(dlastvisit);
					sinsert = "INSERT INTO WebSearch VALUES ("
							+ rs.getInt("_id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ processSpecialCharacter(rs.getString("search"))
							+ "', '" + formatdate + "', " + 0
							+ ", 'DEFAULTB' )";
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "DEFAULT_BOOKMARKS") {
				while (rs.next()) {
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");

					String createddate = "";
					if (rs.getLong("created") != 0) {
						Date dcreated = new Date(rs.getLong("created"));
						createddate = sf.format(dcreated);						
					}

					Date dmodified = new Date(rs.getLong("modified"));
					String modifieddate = sf.format(dmodified);
					
					sinsert = "INSERT INTO Androspy_Bookmark VALUES ("
							+ rs.getInt("_id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ processSpecialCharacter(rs.getString("title"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("url"))
							+ "', " + rs.getInt("deleted") + ", '"
							+ rs.getString("account_name") + "', '"
							+ rs.getString("account_type") + "', '"
							+ createddate + "', '" + modifieddate + "', " + 0
							+ " )";
					
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "DEFAULT_COOKIES") {
				while (rs.next()) {
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					
					Date tmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("creation_utc"));
					String sdate = sf.format(tmp);

					tmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("expires_utc"));
					String sexpire = sf.format(tmp);
					
					tmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("last_access_utc"));
					String slast = sf.format(tmp);

					sinsert = "INSERT INTO Browser_Cookies (case_id, device_id, created_date, host, "
							+ "cookie_name, cookie_value, path, expire_date, secure, httponly, "
							+ "last_access_date, favourite, browser) VALUES ("
							+ AndrospyMain.gb_CaseId
							+ ", "
							+ AndrospyMain.gb_DeviceId
							+ ", '"
							+ sdate
							+ "', '"
							+ rs.getString("host_key")
							+ "', '"
							+ processSpecialCharacter(rs.getString("name"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("value"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("path"))
							+ "', '"
							+ sexpire
							+ "', "
							+ rs.getInt("secure")
							+ ", "
							+ rs.getInt("httponly")
							+ ", '"
							+ slast
							+ "', "
							+ 0
							+ ", 'DEFAULTB' " + " )";
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "FormData") {
				while (rs.next()) {
					sinsert = "INSERT INTO WebFormData VALUES ("
							+ rs.getInt("_id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", "
							+ rs.getInt("urlid") + ", '"
							+ processSpecialCharacter(rs.getString("name"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("value"))
							+ "', " + 0 + " )";
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "FormUrl") {
				while (rs.next()) {
					sinsert = "INSERT INTO WebForm VALUES (" + rs.getInt("_id")
							+ ", " + AndrospyMain.gb_CaseId + ", "
							+ AndrospyMain.gb_DeviceId + ", '"
							+ processSpecialCharacter(rs.getString("url"))
							+ "' " + ")";
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "CHROME_BROWSER_HISTORY") {
				while (rs.next()) {
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					Date tmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("visit_time"));
					
					String sdate = sf.format(tmp);

					sinsert = "INSERT INTO Browser_History VALUES ("
							+ rs.getInt("id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ processSpecialCharacter(rs.getString("title"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("url"))
							+ "', '" + sdate + "', "
							+ rs.getInt("visit_count") + ", " + 0
							+ ", 'CHROME' )";
					
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "CHROME_WEB_SEARCH") {
				while (rs.next()) {
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					Date tmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("last_visit_time"));
					String sdate = sf.format(tmp);
					
					sinsert = "INSERT INTO WebSearch VALUES ("
							+ rs.getInt("url_id") + ", "
							+ AndrospyMain.gb_CaseId + ", "
							+ AndrospyMain.gb_DeviceId + ", '"
							+ processSpecialCharacter(rs.getString("term"))
							+ "', '" + sdate + "', " + 0 + ", 'CHROME' )";
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "CHROME_COOKIES") {
				while (rs.next()) {
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					
					Date tmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("creation_utc"));
					String sdate = sf.format(tmp);

					tmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("expires_utc"));
					String sexpire = sf.format(tmp);
					
					tmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("last_access_utc"));
					String slast = sf.format(tmp);
					
					sinsert = "INSERT INTO Browser_Cookies (case_id, device_id, created_date, host, "
							+ "cookie_name, cookie_value, path, expire_date, secure, httponly, "
							+ "last_access_date, favourite, browser) VALUES ("
							+ AndrospyMain.gb_CaseId
							+ ", "
							+ AndrospyMain.gb_DeviceId
							+ ", '"
							+ sdate
							+ "', '"
							+ rs.getString("host_key")
							+ "', '"
							+ processSpecialCharacter(rs.getString("name"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("value"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("path"))
							+ "', '"
							+ sexpire
							+ "', "
							+ rs.getInt("secure")
							+ ", "
							+ rs.getInt("httponly")
							+ ", '"
							+ slast
							+ "', "
							+ 0
							+ ", 'CHROME' " + " )";
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "CHROME_CREDIT_CARD") {
				while (rs.next()) {
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					
					Date tmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("date_modified"));
					String sdate = sf.format(tmp);

					sinsert = "INSERT INTO Browser_Credit_Card VALUES ("
							+ rs.getInt("guid")
							+ ", "
							+ AndrospyMain.gb_CaseId
							+ ", "
							+ AndrospyMain.gb_DeviceId
							+ ", '"
							+ processSpecialCharacter(rs
									.getString("name_on_card")) + "', "
							+ rs.getInt("expiration_month") + ", "
							+ rs.getInt("expiration_year") + ", '" + sdate
							+ "')";
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}

			} else if (identifier == "Firefox_BROWSER_HISTORY"){
				while(rs.next()){
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					
					String sdate = sf.format(new Date(rs.getLong("modified")));

					sinsert = "INSERT INTO Browser_History VALUES ("
							+ rs.getInt("_id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ processSpecialCharacter(rs.getString("title")) + "', '"
							+ processSpecialCharacter(rs.getString("url")) + "', '"
							+ sdate + "', " + rs.getInt("visits") + ", " + 0 + ", 'Firefox')";
					
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
				
			} else if (identifier == "Firefox_BOOKMARKS") {
				while (rs.next()){
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");

					String createddate = "";
					if (rs.getLong("created") != 0) {
						Date dcreated = new Date(rs.getLong("created"));
						createddate = sf.format(dcreated);
					}

					Date dmodified = new Date(rs.getLong("modified"));
					String modifieddate = sf.format(dmodified);

					sinsert = "INSERT INTO Androspy_Bookmark (id, case_id, device_id, title, url, deleted, created_date, modified_date, favourite, browser)" +
							" VALUES ("
							+ rs.getInt("_id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ processSpecialCharacter(rs.getString("title"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("url"))
							+ "', " + rs.getInt("deleted") + ", '"
							+ createddate + "', '" + modifieddate + "', " + 0 + ", 'Firefox'"
							+ " )";
					
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
				
			} else if (identifier == "Firefox_WEB_SEARCH") {
				while (rs.next()) {
					Date dlastvisit = new Date(rs.getLong("date"));
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					String formatdate = sf.format(dlastvisit);
					sinsert = "INSERT INTO WebSearch VALUES ("
							+ rs.getInt("_id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ processSpecialCharacter(rs.getString("query"))
							+ "', '" + formatdate + "', " + 0
							+ ", 'Firefox' )";
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "Firefox_COOKIES") {
				while (rs.next()) {
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					
					Date tmp = new Date(rs.getLong("creationTime")/1000);
					String sdate = sf.format(tmp);

					tmp = new Date(rs.getLong("expiry")/1000);
					String sexpire = sf.format(tmp);
					
					tmp = new Date(rs.getLong("lastAccessed")/1000);
					String slast = sf.format(tmp);
					
					sinsert = "INSERT INTO Browser_Cookies (case_id, device_id, created_date, host, "
							+ "cookie_name, cookie_value, path, expire_date, secure, httponly, "
							+ "last_access_date, favourite, browser) VALUES ("
							+ AndrospyMain.gb_CaseId
							+ ", "
							+ AndrospyMain.gb_DeviceId
							+ ", '"
							+ sdate
							+ "', '"
							+ rs.getString("host")
							+ "', '"
							+ processSpecialCharacter(rs.getString("name"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("value"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("path"))
							+ "', '"
							+ sexpire
							+ "', "
							+ rs.getInt("isSecure")
							+ ", "
							+ rs.getInt("isHttpOnly")
							+ ", '"
							+ slast
							+ "', "
							+ 0
							+ ", 'Firefox' " + " )";
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
				
			} else if (identifier == "OPERA_COOKIES") {
				while (rs.next()) {
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					
					Date tmp = new Date(rs.getLong("creation_utc")/10000);
					String sdate = sf.format(tmp);

					tmp = new Date(rs.getLong("expires_utc")/10000);
					String sexpire = sf.format(tmp);
					
					tmp = new Date(rs.getLong("last_access_utc")/10000);
					String slast = sf.format(tmp);
					
					sinsert = "INSERT INTO Browser_Cookies (case_id, device_id, created_date, host, "
							+ "cookie_name, cookie_value, path, expire_date, secure, httponly, "
							+ "last_access_date, favourite, browser) VALUES ("
							+ AndrospyMain.gb_CaseId
							+ ", "
							+ AndrospyMain.gb_DeviceId
							+ ", '"
							+ sdate
							+ "', '"
							+ rs.getString("host_key")
							+ "', '"
							+ processSpecialCharacter(rs.getString("name"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("value"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("path"))
							+ "', '"
							+ sexpire
							+ "', "
							+ rs.getInt("secure")
							+ ", "
							+ rs.getInt("httponly")
							+ ", '"
							+ slast
							+ "', "
							+ 0
							+ ", 'OPERA' " + " )";
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "Skype Account") {
				while (rs.next()) {
					Date dregdate = new Date(rs.getLong("registration_timestamp")*1000);
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					String formatdate = sf.format(dregdate);
					
					sinsert = "INSERT INTO Skype_Account  VALUES ("
							+ rs.getInt("id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ rs.getString("skypename") + "', '"
							+ rs.getString("fullname") + "', '"
							+ rs.getString("birthday") + "', '"
							+ rs.getString("country") + "', '" + rs.getString("city") 
							+ "', '" + formatdate + "', '" + rs.getString("gender") + "' "
							+ ")";

					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "Skype Contacts") {
				while (rs.next()) {
					sinsert = "INSERT INTO Skype_Contacts  VALUES ("
							+ rs.getInt("id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ rs.getString("skypename") + "', '"
							+ rs.getString("fullname") + "', '"
							+ rs.getString("birthday") + "', '"
							+ rs.getString("displayname") + "', '"
							+ rs.getString("country") + "', '" + rs.getString("city") 
							+ "', '" + rs.getString("gender") + "', '"
							+ rs.getString("phone_mobile") + "' "
							+ ")";

					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
				
			} else if (identifier == "Skype Messages") {
				while (rs.next()) {
					Date dregdate = new Date(rs.getLong("timestamp")*1000);
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					String formatdate = sf.format(dregdate);
					formatdate = "";
					
					sinsert = "INSERT INTO Skype_Message VALUES ("
							+ rs.getInt("id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ rs.getString("author") + "', '"
							+ rs.getString("from_dispname") + "', '"
							+ rs.getString("dialog_partner") + "', '"
							+ formatdate + "', '"
							+ rs.getString("body_xml") + "' " 
							+ ")";

					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
				
			} else if (identifier == "Skype Calls") {
				while (rs.next()) {
					Date dregdate = new Date(rs.getLong("start_timestamp")*1000);
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					String formatdate = sf.format(dregdate);
					
					sinsert = "INSERT INTO Skype_Call  VALUES ("
							+ rs.getInt("id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ rs.getString("identity") + "', '"
							+ rs.getString("displayname") + "', '"
							+ rs.getString("call_duration") + "', '" 
							+ formatdate + "' "
							+ ")";

					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
				
			} else if (identifier == "LINKEDIN_CONNECTIONS") {
				while (rs.next()) {
					sinsert = "INSERT INTO Linkedin_Connections  VALUES ("
							+ rs.getInt("_id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ rs.getString("first_name") + "', '"
							+ rs.getString("last_name") + "', '"
							+ rs.getString("display_name") + "', '"
							+ rs.getString("headline") + "', " + 0 + ")";

					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "LINKEDIN_INVITATIONS") {
				while (rs.next()) {
					Date dlastvisit = new Date(rs.getLong("timestamp"));
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					String formatdate = sf.format(dlastvisit);

					sinsert = "INSERT INTO Linkedin_Invitation VALUES ("
							+ rs.getInt("_id")
							+ ", "
							+ AndrospyMain.gb_CaseId
							+ ", "
							+ AndrospyMain.gb_DeviceId
							+ ", '"
							+ rs.getString("from_display_name")
							+ "', '"
							+ processSpecialCharacter(rs
									.getString("from_headline")) + "', '"
							+ formatdate + "', " + 0 + ")";

					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "LINKEDIN_MESSAGES") {
				while (rs.next()) {
					Date dlastvisit = new Date(rs.getLong("timestamp"));
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					String formatdate = sf.format(dlastvisit);

					sinsert = "INSERT INTO Linkedin_Message  VALUES ("
							+ rs.getInt("_id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ rs.getString("from_display_name") + "', '"
							+ rs.getString("from_headline") + "', '"
							+ formatdate + "', '"
							+ processSpecialCharacter(rs.getString("subject"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("body"))
							+ "', " + 0 + ")";

					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "LINKEDIN_PROFILE") {
				while (rs.next()) {
					String person_json = rs.getString("person");
					String conn_json = rs.getString("connections");

					try {
						JSONObject jobject = new JSONObject(conn_json);
						int conn_count = jobject.getInt("total");

						jobject = new JSONObject(person_json);
						String lastname = jobject.getString("lastName");
						String firtname = jobject.getString("firstName");
						String displayname = jobject.getString("formattedName");
						String location = jobject.getString("location");

						Date dlastvisit = new Date(jobject.getLong("timestamp"));
						SimpleDateFormat sf = new SimpleDateFormat(
								"MM/dd/yyyy HH:mm:ss");
						String formatdate = sf.format(dlastvisit);

						sinsert = "INSERT INTO Linkedin_Profile  VALUES ("
								+ rs.getInt("_id") + ", "
								+ AndrospyMain.gb_CaseId + ", "
								+ AndrospyMain.gb_DeviceId + ", '" + firtname
								+ "', '" + lastname + "', '" + displayname
								+ "', '" + formatdate + "', '" + location
								+ "', " + conn_count + ", " + 0 + ")";

						int count = mysqlsmt.executeUpdate(sinsert);
						if (count > 0) {
							System.out.println("Added " + count + "Rows to "
									+ identifier);
						} else {
							System.out.println("Insert into " + identifier
									+ " failed");
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

			if (identifier == "Accounts") {
				while (rs.next()) {
					sinsert = "INSERT INTO Androspy_Account VALUES ("
							+ rs.getInt("_id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ processSpecialCharacter(rs.getString("host"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("username"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("password"))
							+ "', " + 0 + ", " + 0 + ")";
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "AccountPrimary") {
				while (rs.next()) {
					sinsert = "INSERT INTO Androspy_Account VALUES ("
							+ rs.getInt("_id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ rs.getString("type") + "', '"
							+ processSpecialCharacter(rs.getString("name"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("password"))
							+ "', " + 1 + ", " + 0 + ")";
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "Contacts") {
				while (rs.next()) {
					String formatdate =""; 
					long ltmp = rs.getLong("last_time_contacted");
					if(ltmp > 0l){
						Date dlastcontact = new Date(ltmp);
						SimpleDateFormat sf = new SimpleDateFormat(
								"dd/MM/yyyy HH:mm:ss");
						formatdate = sf.format(dlastcontact);
					} 
					sinsert = "INSERT INTO Contacts VALUES ("
							+ rs.getInt("name_raw_contact_id") + ", "
							+ AndrospyMain.gb_CaseId + ", "
							+ AndrospyMain.gb_DeviceId + ", '"
							+ rs.getString("contact_no") + "', '"
							+ rs.getString("name") + "', "
							+ rs.getInt("times_contacted") + ", '"
							+ formatdate + "', " + 0 + ")";
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "SMS") {
				SimpleDateFormat sf = new SimpleDateFormat(
						"MM/dd/yyyy HH:mm:ss");
				while (rs.next()) {
					Date datein = new Date(rs.getLong("date"));
					String sdatein = sf.format(datein);

					Date datesent = new Date(rs.getLong("date_sent"));
					String sdatesent = sf.format(datesent);

					sinsert = "INSERT INTO Sms VALUES (" + rs.getInt("_id")
							+ ", " + AndrospyMain.gb_CaseId + ", "
							+ AndrospyMain.gb_DeviceId + ", '"
							+ rs.getString("address") + "', '" + sdatein
							+ "', '" + sdatesent + "', '"
							+ processSpecialCharacter(rs.getString("subject"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("body"))
							+ "', '" + rs.getString("service_center") + "', "
							+ rs.getInt("type") + ", " + 0 + ")";
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "Gmail") {
				while (rs.next()) {
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");

					Date dsent = new Date(rs.getLong("dateSentMs"));
					String sdsent = sf.format(dsent);
					
					Date dreceived = new Date(rs.getLong("dateReceivedMs"));
					String srecived = sf.format(dreceived);
					
					sinsert = "INSERT INTO Gmail_Store (case_id, device_id, fromaddress, toaddress, ccaddress, bccaddress, replytoaddress, "
							+ "datesent, datereceive, subject, snippet, favourite) VALUES ("
							// + rs.getString("_id") + ", "
							+ AndrospyMain.gb_CaseId
							+ ", "
							+ AndrospyMain.gb_DeviceId
							+ ", '"
							+ rs.getString("fromAddress")
							+ "', '"
							+ rs.getString("toAddresses")
							+ "', '"
							+ rs.getString("ccAddresses")
							+ "', '"
							+ rs.getString("bccAddresses")
							+ "', '"
							+ rs.getString("replyToAddresses")
							+ "', '"
							+ sdsent
							+ "', '"
							+ srecived
							+ "', '"
							+ processSpecialCharacter(rs.getString("subject"))
							+ "', '"
							+ processSpecialCharacter(rs.getString("snippet"))
							+ "', " + 0 + ")";

					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "Calendar") {
				while (rs.next()) {
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");

					Date dsent = new Date(rs.getLong("dtstart"));
					String sdstart = sf.format(dsent);

					Date dreceived = new Date(rs.getLong("dtend"));
					String sdend = sf.format(dreceived);

					sinsert = "INSERT INTO Calendar_Event VALUES("
							+ rs.getInt("_id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ rs.getString("title") + "', '"
							+ rs.getString("description") + "', '"
							+ rs.getString("eventLocation") + "', '" + sdstart
							+ "', '" + sdend + "', '"
							+ rs.getString("organizer") + "', "
							+ rs.getInt("deleted") + ", " + 0 + ")";

					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}

			} else if (identifier == "YouTube") {
				while (rs.next()) {
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");

					Date dsent = new Date(rs.getLong("date"));
					String sdstart = sf.format(dsent);

					sinsert = "INSERT INTO Youtube_History VALUES("
							+ rs.getInt("_id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ rs.getString("query") + "', '" + sdstart + "', "
							+ 0 + ")";

					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}

			} else if (identifier == "Map") {
				while (rs.next()) {
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");

					Date dsent = new Date(rs.getLong("timestamp"));
					String sdstart = sf.format(dsent);
					
					String slatitude = Double.toString(rs.getLong("latitude"));
					String slongitude = Double.toString(rs.getLong("longitude"));
					sinsert = "INSERT INTO Map_Location (case_id, device_id, place, latitude, longitude, timestamp) VALUES("
							+ AndrospyMain.gb_CaseId
							+ ", "
							+ AndrospyMain.gb_DeviceId
							+ ", '"
							+ rs.getString("data1")
							+ "', '"
							+ slatitude
							+ "', '"
							+ slongitude
							+ "', '"
							+ sdstart + "', " + 0 + ")";

					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}

			} else if (identifier == "FACEBOOK_CONTACTS") {
				while (rs.next()) {
					Date addedtime = new Date(rs.getLong("added_time_ms"));
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					String formatdate = sf.format(addedtime);

					sinsert = "INSERT INTO Fb_Contacts VALUES ("
							+ rs.getInt("internal_id") + ", "
							+ AndrospyMain.gb_CaseId + ", "
							+ AndrospyMain.gb_DeviceId + ", '"
							+ rs.getString("first_name") + "', '"
							+ rs.getString("last_name")+ "', '"
							+ rs.getString("display_name") + "', '"
							+ formatdate + "', '" + rs.getString("type")
							+ "', " + 0 + ")";

					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "FACEBOOK_MESSAGES") {
				while (rs.next()) {
					Date addedtime = new Date(rs.getLong("timestamp_ms"));
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					String formatdate = sf.format(addedtime);

					try {
						String latitude = "", longitude = "";
						String sender_json = rs.getString("sender");
						JSONObject jobject = new JSONObject(sender_json);
						String name = jobject.getString("name");

						String coordinate_json = rs.getString("coordinates");
						if (coordinate_json != null) {
							jobject = new JSONObject(coordinate_json);
							latitude = Double.toString(jobject
									.getDouble("latitude"));
							longitude = Double.toString(jobject
									.getDouble("longitude"));
						} else {
							latitude = "";
							longitude = "";
						}

						sinsert = "INSERT INTO Facebook_Message VALUES ("
								+ rs.getInt("msg_id") + ", "
								+ AndrospyMain.gb_CaseId + ", "
								+ AndrospyMain.gb_DeviceId + ", '" + name
								+ "', '"
								+ processSpecialCharacter(rs.getString("text"))
								+ "', '" + formatdate + "', '" + longitude
								+ "', '" + latitude + "', " + 0 + ")";

						int count = mysqlsmt.executeUpdate(sinsert);
						if (count > 0) {
							System.out.println("Added " + count + "Rows to "
									+ identifier);
						} else {
							System.out.println("Insert into " + identifier
									+ " failed");
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else if (identifier == "FACEBOOK_NOTIFICATIONS") {
				while (rs.next()) {
					Date addedtime = new Date(rs.getLong("updated"));
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					String formatdate = sf.format(addedtime);

					try {
						String notifi_json = rs
								.getString("graphql_text_with_entities");
						JSONObject jobject = new JSONObject(notifi_json);
						String notification = jobject.getString("text");

						sinsert = "INSERT INTO Facebook_Notifications VALUES ("
								+ rs.getInt("_id") + ", "
								+ AndrospyMain.gb_CaseId + ", "
								+ AndrospyMain.gb_DeviceId + ", '"
								+ processSpecialCharacter(notification)
								+ "', '" + formatdate + "', " + 0 + ")";

						int count = mysqlsmt.executeUpdate(sinsert);
						if (count > 0) {
							System.out.println("Added " + count + "Rows to "
									+ identifier);
						} else {
							System.out.println("Insert into " + identifier
									+ " failed");
						}

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} else if (identifier == "EventLog") {
				while (rs.next()) {
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					Date dcreated = new Date(rs.getLong("date"));
					String createddate = sf.format(dcreated);

					sinsert = "INSERT INTO Event_Log VALUES ("
							+ rs.getInt("_id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ rs.getString("number") + "', '"
							+ rs.getString("name") + "', '" + createddate
							+ "', " + rs.getInt("duration") + ", "
							+ rs.getInt("type") + ", " + rs.getInt("logtype")
							+ ", " + rs.getInt("messageid") + ", '"
							+ rs.getString("m_subject") + "', '"
							+ rs.getString("m_content") + "', '"
							+ rs.getString("countryiso") + "', '"
							+ rs.getString("geocoded_location") + "', " + 0
							+ " )";
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			} else if (identifier == "Bluetooth") {
				while (rs.next()) {
					SimpleDateFormat sf = new SimpleDateFormat(
							"MM/dd/yyyy HH:mm:ss");
					Date dcreated = new Date(rs.getLong("timestamp"));
					String createddate = sf.format(dcreated);

					sinsert = "INSERT INTO Bluetooth_Tranfer VALUES ("
							+ rs.getInt("_id") + ", " + AndrospyMain.gb_CaseId
							+ ", " + AndrospyMain.gb_DeviceId + ", '"
							+ rs.getString("mimetype") + "', '"
							+ rs.getString("hint") + "', '"
							+ rs.getString("_data") + "', "
							+ rs.getInt("direction") + ", '"
							+ rs.getString("destination") + "',"
							+ rs.getInt("visibility") + ", "
							+ rs.getInt("confirm") + ", "
							+ rs.getString("status") + ", '"
							+ rs.getString("total_bytes") + "', '"
							+ createddate + "', " + rs.getString("scanned")
							+ ", " + 0 + " )";
					int count = mysqlsmt.executeUpdate(sinsert);
					if (count > 0) {
						System.out.println("Added " + count + "Rows to "
								+ identifier);
					} else {
						System.out.println("Insert into " + identifier
								+ " failed");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("SQL ERROR");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String processSpecialCharacter(String in) {
		if (in != null) {
			System.out.println("in: " + in);
			String out = in.replaceAll("'", "\\\\'");
			System.out.println("out: " + out);
			return out;
		}
		return "";
	}
}
