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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;

/**
 * @author indeewari
 *
 */
public class TimeLineDummy extends Composite {
	private Table table;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public TimeLineDummy(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, true));

		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnTimeStamp = new TableColumn(table, SWT.NONE);
		tblclmnTimeStamp.setWidth(100);
		tblclmnTimeStamp.setText("Time Stamp");

		TableColumn tblclmnType = new TableColumn(table, SWT.NONE);
		tblclmnType.setWidth(100);
		tblclmnType.setText("Type");

		TableColumn tblclmnRemotePartName = new TableColumn(table, SWT.NONE);
		tblclmnRemotePartName.setWidth(138);
		tblclmnRemotePartName.setText("Remote Party");

	}

	public void listAllEvents(String feature, boolean bBefore, boolean bAfter,
			String sfromdate, String stodate) {
		// -----------
		String query_all = "SELECT 'Calendar Event' AS EventType, c.title AS 'Remote Party Contact', c.start_time AS tldate"
				+ " FROM Calendar_Event c"
				+ " WHERE c.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND c.device_id = "
				+ AndrospyMain.gb_DeviceId;

		if (bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( c.start_time, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && bAfter) {
			query_all += " AND STR_TO_DATE( c.start_time, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( c.start_time, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " AND STR_TO_DATE( c.start_time, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T') ";
		} else if (bBefore && bAfter) {
			query_all += " AND (STR_TO_DATE( c.start_time, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " OR STR_TO_DATE( c.start_time, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')) ";
		}

		query_all += " UNION ";

		query_all += "SELECT 'Gmail Inbox' AS EventType, g.toaddress AS 'Remote Party Contact', g.datereceive AS tldate"
				+ " FROM Gmail_Store g"
				+ " WHERE g.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND g.device_id = "
				+ AndrospyMain.gb_DeviceId;

		if (bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( g.datereceive, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && bAfter) {
			query_all += " AND STR_TO_DATE( g.datereceive, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( g.datereceive, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " AND STR_TO_DATE( g.datereceive, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T') ";
		} else if (bBefore && bAfter) {
			query_all += " AND (STR_TO_DATE( g.datereceive, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " OR STR_TO_DATE( g.datereceive, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')) ";
		}

		query_all += " UNION ";

		query_all += "SELECT 'Youtube' AS EventType, y.query AS 'Remote Party Contact', y.search_date AS tldate"
				+ " FROM Youtube_History y"
				+ " WHERE y.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND y.device_id = "
				+ AndrospyMain.gb_DeviceId;

		if (bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( y.search_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && bAfter) {
			query_all += " AND STR_TO_DATE( y.search_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( y.search_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " AND STR_TO_DATE( y.search_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T') ";
		} else if (bBefore && bAfter) {
			query_all += " AND (STR_TO_DATE( y.search_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " OR STR_TO_DATE( y.search_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')) ";
		}

		query_all += " UNION ";

		query_all = "SELECT 'Facebook Contact' AS EventType, fc.display_name AS 'Remote Party Contact', fc.added_time AS tldate"
				+ " FROM Fb_Contacts fc"
				+ " WHERE fc.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND fc.device_id = "
				+ AndrospyMain.gb_DeviceId;

		if (bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( fc.added_time, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && bAfter) {
			query_all += " AND STR_TO_DATE( fc.added_time, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( fc.added_time, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " AND STR_TO_DATE( fc.added_time, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T') ";
		} else if (bBefore && bAfter) {
			query_all += " AND (STR_TO_DATE( fc.added_time, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " OR STR_TO_DATE( fc.added_time, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')) ";
		}

		query_all += " UNION ";

		query_all = "SELECT 'Facebook Message' AS EventType, fm.sender AS 'Remote Party Contact', fm.conversation_date AS tldate"
				+ " FROM Facebook_Message fm"
				+ " WHERE fm.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND fm.device_id = "
				+ AndrospyMain.gb_DeviceId;

		if (bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( fm.conversation_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && bAfter) {
			query_all += " AND STR_TO_DATE( fm.conversation_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( fm.conversation_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " AND STR_TO_DATE( fm.conversation_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T') ";
		} else if (bBefore && bAfter) {
			query_all += " AND (STR_TO_DATE( fm.conversation_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " OR STR_TO_DATE( fm.conversation_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')) ";
		}

		query_all += " UNION ";

		query_all = "SELECT 'Facebook Notification' AS EventType, fn.notification AS 'Remote Party Contact', fn.update_date AS tldate"
				+ " FROM Facebook_Notifications fn"
				+ " WHERE fn.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND fn.device_id = "
				+ AndrospyMain.gb_DeviceId;

		if (bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( fn.update_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && bAfter) {
			query_all += " AND STR_TO_DATE( fn.update_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( fn.update_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " AND STR_TO_DATE( fn.update_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T') ";
		} else if (bBefore && bAfter) {
			query_all += " AND (STR_TO_DATE( fn.update_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " OR STR_TO_DATE( fn.update_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')) ";
		}

		query_all += " UNION ";

		query_all = "SELECT 'Linkedin Profile' AS EventType, lp.display_name AS 'Remote Party Contact', lp.join_date AS tldate"
				+ " FROM Linkedin_Profile lp"
				+ " WHERE lp.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND lp.device_id = "
				+ AndrospyMain.gb_DeviceId;

		if (bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( lp.join_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && bAfter) {
			query_all += " AND STR_TO_DATE( lp.join_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( lp.join_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " AND STR_TO_DATE( lp.join_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T') ";
		} else if (bBefore && bAfter) {
			query_all += " AND (STR_TO_DATE( lp.join_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " OR STR_TO_DATE( lp.join_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')) ";
		}

		query_all += " UNION ";

		query_all = "SELECT 'Linkedin Invitations' AS EventType, li.from_display_name AS 'Remote Party Contact', li.invitation_date AS tldate"
				+ " FROM Linkedin_Invitation li"
				+ " WHERE li.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND li.device_id = "
				+ AndrospyMain.gb_DeviceId;

		if (bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( li.invitation_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && bAfter) {
			query_all += " AND STR_TO_DATE( li.invitation_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( li.invitation_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " AND STR_TO_DATE( li.invitation_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T') ";
		} else if (bBefore && bAfter) {
			query_all += " AND (STR_TO_DATE( li.invitation_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " OR STR_TO_DATE( li.invitation_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')) ";
		}

		query_all += " UNION ";

		query_all = "SELECT 'Linkedin Messages' AS EventType, lm.from_display_name AS 'Remote Party Contact', lm.message_date AS tldate"
				+ " FROM Linkedin_Message lm"
				+ " WHERE lm.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND lm.device_id = "
				+ AndrospyMain.gb_DeviceId;

		if (bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( lm.message_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && bAfter) {
			query_all += " AND STR_TO_DATE( lm.message_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( lm.message_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " AND STR_TO_DATE( lm.message_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T') ";
		} else if (bBefore && bAfter) {
			query_all += " AND (STR_TO_DATE( lm.message_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " OR STR_TO_DATE( lm.message_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')) ";
		}

		query_all += " UNION ";

		query_all += "SELECT CASE WHEN (browser = 'DEFAULTB') THEN 'Default Browser Bookmark'"
				+ "				  WHEN (browser = 'CHROME') THEN 'Chrome Bookmark' END AS EventType, b.url AS 'Remote Party Contact', b.modified_date AS tldate"
				+ " FROM Androspy_Bookmark b"
				+ " WHERE b.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND b.device_id = "
				+ AndrospyMain.gb_DeviceId;

		if (bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( b.modified_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && bAfter) {
			query_all += " AND STR_TO_DATE( b.modified_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( b.modified_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " AND STR_TO_DATE( b.modified_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T') ";
		} else if (bBefore && bAfter) {
			query_all += " AND (STR_TO_DATE( b.modified_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " OR STR_TO_DATE( b.modified_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')) ";
		}

		// query_all += " ORDER BY b.modified_date";

		query_all += " UNION ";

		query_all += "SELECT CASE WHEN (browser = 'DEFAULTB') THEN 'Default Browser History'"
				+ "				  WHEN (browser = 'CHROME') THEN 'Chrome History' END AS EventType, bh.url AS 'Remote Party Contact', bh.last_visit_date AS tldate"
				+ " FROM Browser_History bh"
				+ " WHERE bh.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND bh.device_id = "
				+ AndrospyMain.gb_DeviceId;

		if (bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( bh.last_visit_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && bAfter) {
			query_all += " AND STR_TO_DATE( bh.last_visit_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( bh.last_visit_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " AND STR_TO_DATE( bh.last_visit_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T') ";
		} else if (bBefore && bAfter) {
			query_all += " AND (STR_TO_DATE( bh.last_visit_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " OR STR_TO_DATE( bh.last_visit_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')) ";
		}
		query_all += " UNION ";

		query_all += "SELECT CASE WHEN (browser = 'DEFAULTB') THEN 'Default Browser Websearch'"
				+ "				  WHEN (browser = 'CHROME') THEN 'Chrome Websearch' END AS EventType, ws.search_text AS 'Remote Party Contact', ws.search_date AS tldate"
				+ " FROM WebSearch ws"
				+ " WHERE ws.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND ws.device_id = "
				+ AndrospyMain.gb_DeviceId;

		if (bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( ws.search_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && bAfter) {
			query_all += " AND STR_TO_DATE( ws.search_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( ws.search_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " AND STR_TO_DATE( ws.search_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T') ";
		} else if (bBefore && bAfter) {
			query_all += " AND (STR_TO_DATE( ws.search_date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " OR STR_TO_DATE( ws.search_date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')) ";
		}
		
		query_all += " UNION ";

		query_all += "SELECT CASE WHEN (bcc.browser = 'DEFAULTB') THEN 'Default Browser Websearch'"
				+ "				  WHEN (bcc.browser = 'CHROME') THEN 'Chrome Websearch' END AS EventType, " 
				+ "bcc.name AS 'Remote Party Contact', bcc.date_modified AS 'TimeStamp'" 
				+ " FROM Browser_Credit_Card bcc"
				+ " WHERE bcc.case_id = " + AndrospyMain.gb_CaseId
				+ " AND bcc.device_id = " + AndrospyMain.gb_DeviceId;
		
		if (bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( bcc.date_modified, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && bAfter) {
			query_all += " AND STR_TO_DATE( bcc.date_modified, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( bcc.date_modified, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " AND STR_TO_DATE( bcc.date_modified, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T') ";
		} else if (bBefore && bAfter) {
			query_all += " AND (STR_TO_DATE( bcc.date_modified, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " OR STR_TO_DATE( bcc.date_modified, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')) ";
		}
		
		query_all += " UNION ";
		
		query_all += "SELECT 'SMS' AS EventType, s.address AS 'Remote Party Contact', s.date_received AS 'TimeStamp'"
				+ " FROM Sms s"
				+ " WHERE s.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND s.device_id = "
				+ AndrospyMain.gb_DeviceId;
		if (bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( s.date_received, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && bAfter) {
			query_all += " AND STR_TO_DATE( s.date_received, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( s.date_received, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " AND STR_TO_DATE( s.date_received, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T') ";
		} else if (bBefore && bAfter) {
			query_all += " AND (STR_TO_DATE( s.date_received, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " OR STR_TO_DATE( s.date_received, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')) ";
		}

		query_all += " UNION ";
		query_all += "SELECT " + "CASE "
				+ "WHEN type = 1 AND logtype = 100 THEN 'Incomming Call Log' "
				+ "WHEN type = 2 AND logtype = 100 THEN 'Outgoing Call Log' "
				+ "WHEN type = 3 AND logtype = 100 THEN 'Missed Call Log' "
				+ "WHEN type = 2 AND logtype = 500 THEN 'Cancelled Call Log' "
				+ "WHEN type = 1 AND logtype = 200 THEN 'Incomming MMS Log' "
				+ "WHEN type != 1 AND logtype = 200 THEN 'Outgoing MMS Log' "
				+ "WHEN type = 1 AND logtype = 300 THEN 'Incomming SMS Log' "
				+ "WHEN type != 1 AND logtype = 300 THEN 'Outgoing SMS Log' "
				+ "ELSE ' ' " + "END AS 'EventType',"
				+ "el.contact_no AS 'Remote Party Contact', el.date AS tldate"
				+ " FROM Event_Log el" + " WHERE el.case_id = "
				+ AndrospyMain.gb_CaseId + " AND el.device_id = "
				+ AndrospyMain.gb_DeviceId;

		if (bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( el.date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && bAfter) {
			query_all += " AND STR_TO_DATE( el.date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')";
		} else if (!bBefore && !bAfter) {
			query_all += " AND STR_TO_DATE( el.date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " AND STR_TO_DATE( el.date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T') ";
		} else if (bBefore && bAfter) {
			query_all += " AND (STR_TO_DATE( el.date, '%m/%d/%Y %T') <= STR_TO_DATE('"
					+ sfromdate
					+ "', '%Y/%m/%d %T')"
					+ " OR STR_TO_DATE( el.date, '%m/%d/%Y %T') >= STR_TO_DATE('"
					+ stodate + "', '%Y/%m/%d %T')) ";
		}

		query_all += " ORDER BY STR_TO_DATE( tldate, '%m/%d/%Y %T') ASC";

		table.removeAll();
		table.setItemCount(0);

		System.out.println("All Select: " + query_all);

		try {
			Connection conn = new ConnectDb().getConnection();
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery(query_all);

			String values[] = new String[3];
			while (rs.next()) {
				TableItem titem = new TableItem(table, SWT.NONE);
				values[0] = rs.getString("tldate");
				values[1] = rs.getString("EventType");
				values[2] = rs.getString("Remote Party Contact");

				titem.setText(values);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ------------

	}

	public void listEvents(String feature, boolean bBefore, boolean bAfter,
			String sfromdate, String stodate) {
		System.out.println("from " + sfromdate + " to " + stodate);
		String query = "", sdatecol = "";

		String query_event = "SELECT "
				+ "CASE "
				+ "WHEN type = 1 AND logtype = 100 THEN 'Incomming Call Log' "
				+ "WHEN type = 2 AND logtype = 100 THEN 'Outgoing Call Log' "
				+ "WHEN type = 3 AND logtype = 100 THEN 'Missed Call Log' "
				+ "WHEN type = 2 AND logtype = 500 THEN 'Cancelled Call Log' "
				+ "WHEN type = 1 AND logtype = 200 THEN 'Incomming MMS Log' "
				+ "WHEN type != 1 AND logtype = 200 THEN 'Outgoing MMS Log' "
				+ "WHEN type = 1 AND logtype = 300 THEN 'Incomming SMS Log' "
				+ "WHEN type != 1 AND logtype = 300 THEN 'Outgoing SMS Log' "
				+ "ELSE ' ' "
				+ "END AS 'EventType',"
				+ "el.contact_no AS 'Remote Party Contact', el.name AS 'Name', el.date AS tldate"
				+ " FROM Event_Log el" + " WHERE el.case_id = "
				+ AndrospyMain.gb_CaseId + " AND el.device_id = "
				+ AndrospyMain.gb_DeviceId;

		String query_sms = "SELECT 'SMS' AS EventType, s.address AS 'Remote Party Contact', s.date_received AS tldate"
				+ " FROM Sms s"
				+ " WHERE s.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND s.device_id = "
				+ AndrospyMain.gb_DeviceId;

		String query_calendar = "SELECT 'Calendar Event' AS EventType, c.title AS 'Remote Party Contact', c.start_time AS tldate"
				+ " FROM Calendar_Event c"
				+ " WHERE c.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND c.device_id = "
				+ AndrospyMain.gb_DeviceId;

		String query_gmail = "SELECT 'Gmail Inbox' AS EventType, g.toaddress AS 'Remote Party Contact', g.datereceive AS tldate"
				+ " FROM Gmail_Store g"
				+ " WHERE g.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND g.device_id = "
				+ AndrospyMain.gb_DeviceId;

		String query_youtube = "SELECT 'Youtube' AS EventType, y.query AS 'Remote Party Contact', y.search_date AS tldate"
				+ " FROM Youtube_History y"
				+ " WHERE y.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND y.device_id = "
				+ AndrospyMain.gb_DeviceId;

		String query_fbcontact = "SELECT 'Facebook Contact' AS EventType, fc.display_name AS 'Remote Party Contact', fc.added_time AS tldate"
				+ " FROM Fb_Contacts fc"
				+ " WHERE fc.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND fc.device_id = "
				+ AndrospyMain.gb_DeviceId;

		String query_fbmessage = "SELECT 'Facebook Message' AS EventType, fm.sender AS 'Remote Party Contact', fm.conversation_date AS tldate"
				+ " FROM Facebook_Message fm"
				+ " WHERE fm.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND fm.device_id = "
				+ AndrospyMain.gb_DeviceId;

		String query_fbnotification = "SELECT 'Facebook Notification' AS EventType, fn.notification AS 'Remote Party Contact', fn.update_date AS tldate"
				+ " FROM Facebook_Notifications fn"
				+ " WHERE fn.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND fn.device_id = "
				+ AndrospyMain.gb_DeviceId;

		String query_lnprofile = "SELECT 'Linkedin Profile' AS EventType, lp.display_name AS 'Remote Party Contact', lp.join_date AS tldate"
				+ " FROM Linkedin_Profile lp"
				+ " WHERE lp.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND lp.device_id = "
				+ AndrospyMain.gb_DeviceId;

		String query_lninv = "SELECT 'Linkedin Invitations' AS EventType, li.from_display_name AS 'Remote Party Contact', li.invitation_date AS tldate"
				+ " FROM Linkedin_Invitation li"
				+ " WHERE li.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND li.device_id = "
				+ AndrospyMain.gb_DeviceId;

		String query_lnmsg = "SELECT 'Linkedin Messages' AS EventType, lm.from_display_name AS 'Remote Party Contact', lm.message_date AS tldate"
				+ " FROM Linkedin_Message lm"
				+ " WHERE lm.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND lm.device_id = "
				+ AndrospyMain.gb_DeviceId;

		String query_Def_BrowserHist = "SELECT 'Default Browser Browser History' AS EventType, bh.url AS 'Remote Party Contact', bh.last_visit_date AS tldate"
				+ " FROM Browser_History bh"
				+ " WHERE bh.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND bh.device_id = "
				+ AndrospyMain.gb_DeviceId + " AND bh.browser = 'DEFAULTB'";

		String query_def_websearch = "SELECT 'Default Browser Web Search' AS EventType, ws.search_text AS 'Remote Party Contact', ws.search_date AS tldate"
				+ " FROM WebSearch ws"
				+ " WHERE ws.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND ws.device_id = "
				+ AndrospyMain.gb_DeviceId + " AND ws.browser = 'DEFAULTB'";

		String query_Def_Bookmark = "SELECT 'Default Browser Bookmark' AS EventType, b.url AS 'Remote Party Contact', b.modified_date AS tldate"
				+ " FROM Androspy_Bookmark b"
				+ " WHERE b.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND b.device_id = "
				+ AndrospyMain.gb_DeviceId;

		String query_Def_Cookies = "SELECT 'Default Browser Cookies' AS EventType, CONCAT_WS ('/', bc.host, bc.cookie_name) AS 'Remote Party Contact', bc.last_access_date AS tldate"
				+ " FROM Browser_Cookies bc"
				+ " WHERE bc.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND bc.device_id = "
				+ AndrospyMain.gb_DeviceId + " AND bc.browser = 'DEFAULTB'";

		String query_chrome_hist = "SELECT 'Default Browser Browser History' AS EventType, bh.url AS 'Remote Party Contact', bh.last_visit_date AS tldate"
				+ " FROM Browser_History bh"
				+ " WHERE bh.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND bh.device_id = "
				+ AndrospyMain.gb_DeviceId + " AND bh.browser = 'CHROME'";

		String query_chrome_ws = "SELECT 'Default Browser Web Search' AS EventType, ws.search_text AS 'Remote Party Contact', ws.search_date AS tldate"
				+ " FROM WebSearch ws"
				+ " WHERE ws.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND ws.device_id = "
				+ AndrospyMain.gb_DeviceId + " AND ws.browser = 'CHROME'";

		String query_chrome_Cookies = "SELECT 'Default Browser Cookies' AS EventType, CONCAT_WS ('/', bc.host, bc.cookie_name) AS 'Remote Party Contact', bc.last_access_date AS tldate"
				+ " FROM Browser_Cookies bc"
				+ " WHERE bc.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND bc.device_id = "
				+ AndrospyMain.gb_DeviceId + " AND bc.browser = 'CHROME'";

		String query_credit_card = "SELECT 'Chrome Credit Card' AS EventType, cc.name AS 'Remote Party Contact', cc.date_modified AS tldate"
				+ " FROM Browser_Credit_Card cc"
				+ " WHERE cc.case_id = "
				+ AndrospyMain.gb_CaseId
				+ " AND cc.device_id = "
				+ AndrospyMain.gb_DeviceId + " AND cc.browser = 'CHROME'";

		table.removeAll();
		table.setItemCount(0);

		System.out.println("******************" + feature);
		if (feature == "ALL") {
			query = "";
		} else if (feature == "EVENTLOG") {
			query = query_event;
			sdatecol = "el.date";
		} else if (feature == "SMS") {
			query = query_sms;
			sdatecol = "s.date_received";
		} else if (feature == "CALENDAR") {
			query = query_calendar;
			sdatecol = "c.start_time";
		} else if (feature == "GMAIL") {
			query = query_gmail;
			sdatecol = "g.datereceive";
		} else if (feature == "YOUTUBE") {
			query = query_youtube;
			sdatecol = "y.search_date";
		} else if (feature == "FBCONTACT") {
			query = query_fbcontact;
			sdatecol = "fc.added_time";
		} else if (feature == "FBMESSAGE") {
			query = query_fbmessage;
			sdatecol = "fm.conversation_date";
		} else if (feature == "FBNOTIFICATION") {
			System.out.println("FBNOTIFICATION inside");
			query = query_fbnotification;
			sdatecol = "fn.update_date";
		} else if (feature == "LINKEDPROFILE") {
			query = query_lnprofile;
			sdatecol = "lp.join_date";
		} else if (feature == "LINKEDINV") {
			query = query_lninv;
			sdatecol = "li.invitation_date";
		} else if (feature == "LINKEDMSG") {
			query = query_lnmsg;
			sdatecol = "lm.message_date";
		} else if (feature == "DEFAULTBROWSERHISTORY") {
			query = query_Def_BrowserHist;
			sdatecol = "bh.last_visit_date";
		} else if (feature == "DEFAULTWEBSEARCH") {
			query = query_def_websearch;
			sdatecol = "ws.search_date";
		} else if (feature == "DEFAULTBOOKMARK") {
			query = query_Def_Bookmark;
			sdatecol = "b.modified_date";
		} else if (feature == "DEFAULTCOOKIE") {
			query = query_Def_Cookies;
			sdatecol = "bc.last_access_date";
		} else if (feature == "CHROMEHIST") {
			query = query_chrome_hist;
			sdatecol = "bh.last_visit_date";
		} else if (feature == "CHROMEWS") {
			query = query_chrome_ws;
			sdatecol = "ws.search_date";
			;
		} else if (feature == "CHROMECOOKIE") {
			query = query_chrome_Cookies;
			sdatecol = "bc.last_access_date";
		} else if (feature == "CHROMECC") {
			query = query_credit_card;
			sdatecol = "cc.date_modified";
		}

		try {
			Connection conn = new ConnectDb().getConnection();
			Statement stmt = conn.createStatement();

			if (bBefore && !bAfter) {
				query += " AND STR_TO_DATE( " + sdatecol
						+ ", '%m/%d/%Y %T') <= STR_TO_DATE('" + sfromdate
						+ "', '%Y/%m/%d %T')";
			} else if (!bBefore && bAfter) {
				query += " AND STR_TO_DATE( " + sdatecol
						+ ", '%m/%d/%Y %T') >= STR_TO_DATE('" + stodate
						+ "', '%Y/%m/%d %T')";
			} else if (!bBefore && !bAfter) {
				query += " AND STR_TO_DATE( " + sdatecol
						+ ", '%m/%d/%Y %T') >= STR_TO_DATE('" + sfromdate
						+ "', '%Y/%m/%d %T')" + " AND STR_TO_DATE( " + sdatecol
						+ ", '%m/%d/%Y %T') <= STR_TO_DATE('" + stodate
						+ "', '%Y/%m/%d %T') ";
			} else if (bBefore && bAfter) {
				query += " AND (STR_TO_DATE( " + sdatecol
						+ ", '%m/%d/%Y %T') <= STR_TO_DATE('" + sfromdate
						+ "', '%Y/%m/%d %T')" + " OR STR_TO_DATE(" + sdatecol
						+ ", '%m/%d/%Y %T') >= STR_TO_DATE('" + stodate
						+ "', '%Y/%m/%d %T')) ";
			}

			query += " ORDER BY STR_TO_DATE( tldate, '%m/%d/%Y %T') ASC";

			System.out.println(feature + " select: " + query);
			ResultSet rs = stmt.executeQuery(query);

			String values[] = new String[3];
			while (rs.next()) {
				TableItem titem = new TableItem(table, SWT.NONE);
				values[0] = rs.getString("tldate");
				values[1] = rs.getString("EventType");
				values[2] = rs.getString("Remote Party Contact");

				titem.setText(values);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
