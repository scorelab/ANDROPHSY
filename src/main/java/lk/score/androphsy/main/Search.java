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
import java.sql.SQLException;
import java.sql.Statement;

import lk.score.androphsy.database.ConnectDb;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * @author indeewari
 *
 */
public class Search extends Composite {
	private Text txtSearchField;
	private Combo combo;
	private Composite composite;
	private StackLayout stacklayout = new StackLayout();
	private TableComposite tblcmp;
	private Label lblTextSearch;
	private Button btnCaseSensitive;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Search(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, false));

		lblTextSearch = new Label(this, SWT.NONE);
		lblTextSearch.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblTextSearch.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 3, 1));
		lblTextSearch.setText("Text lk.score.androphsy.main.Search");

		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		combo = new Combo(this, SWT.NONE);
		GridData gd_combo = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_combo.widthHint = 156;
		combo.setLayoutData(gd_combo);

		combo.add("keyword");
		combo.add("telephone number");
		combo.add("email address");
		combo.add("contact name");
		combo.add("phonography");
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (combo.getText().equals("phonography")) {
					txtSearchField.setEnabled(false);
					btnCaseSensitive.setEnabled(false);
				} else {
					txtSearchField.setEnabled(true);
					btnCaseSensitive.setEnabled(true);
				}
			}
		});

		txtSearchField = new Text(this, SWT.BORDER);
		GridData gd_txtSearchField = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtSearchField.widthHint = 353;
		txtSearchField.setLayoutData(gd_txtSearchField);

		Button btnSearch = new Button(this, SWT.NONE);
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				search(combo.getText(), txtSearchField.getText());
			}
		});
		GridData gd_btnSearch = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnSearch.widthHint = 94;
		btnSearch.setLayoutData(gd_btnSearch);
		btnSearch.setText("lk.score.androphsy.main.Search");
		new Label(this, SWT.NONE);

		btnCaseSensitive = new Button(this, SWT.CHECK);
		btnCaseSensitive.setText("Case Sensitive");
		new Label(this, SWT.NONE);

		composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		composite.setLayout(stacklayout);

		tblcmp = new TableComposite(composite, SWT.NONE);
		stacklayout.topControl = tblcmp;
	}

	private void search(String type, String srch) {
		boolean bcasesensitive = false;
		bcasesensitive = btnCaseSensitive.getSelection();
		try {
			System.out.println("lk.score.androphsy.main.Search  >> " + type + " / " + srch);
			String query = "";
			Connection con = new ConnectDb().getConnection();
			Statement stmt = con.createStatement();

			if (type.equals("keyword")) {
				tblcmp.setKeyword(true);
				tblcmp.setHexVisible(true);
				tblcmp.readOffset(srch, btnCaseSensitive.getSelection());

			} else if (type.equals("telephone number")) {
				stacklayout.topControl = tblcmp;
				query =
				        "SELECT 'Phone Book' AS type, contact_no, name, last_time_contacted"
				                + " FROM Contacts WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId +
				                " AND contact_no like '%" +
				                srch +
				                "%'" +
				                " UNION " +
				                "SELECT CASE " +
				                "WHEN type = 1 AND logtype = 100 THEN 'Incomming Call Log' " +
				                "WHEN type = 2 AND logtype = 100 THEN 'Outgoing Call Log' " +
				                "WHEN type = 3 AND logtype = 100 THEN 'Missed Call Log' " +
				                "WHEN type = 2 AND logtype = 500 THEN 'Cancelled Call Log' " +
				                "WHEN type = 1 AND logtype = 200 THEN 'Incomming MMS Log' " +
				                "WHEN type != 1 AND logtype = 200 THEN 'Outgoing MMS Log' " +
				                "WHEN type = 1 AND logtype = 300 THEN 'Incomming SMS Log' " +
				                "WHEN type != 1 AND logtype = 300 THEN 'Outgoing SMS Log' " +
				                "ELSE ' ' END AS type," +
				                " contact_no, name, date FROM Event_Log WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId + " AND contact_no like '%" + srch + "%'";
			} else if (type.equals("email address")) {
				stacklayout.topControl = tblcmp;
				query =
				        "SELECT 'Gmail' AS type, fromaddress AS 'Remote Part', datesent, datereceive" +
				                " FROM Gmail_Store WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId +
				                " AND fromaddress ";
				if (bcasesensitive) {
					query += "COLLATE latin1_general_cs";
				}
				query +=
				         " like '%" +
				                 srch +
				                 "%'" +
				                 " UNION " +
				                 "SELECT 'Gmail' AS type, toaddress AS 'Remote Part', datesent, datereceive" +
				                 " FROM Gmail_Store WHERE case_id = " + AndrospyMain.gb_CaseId +
				                 " AND device_id = " + AndrospyMain.gb_DeviceId + " AND toaddress ";
				if (bcasesensitive) {
					query += "COLLATE latin1_general_cs";
				}
				query +=
				         " like '%" +
				                 srch +
				                 "%'" +
				                 " UNION " +
				                 "SELECT 'Gmail' AS type, ccaddress AS 'Remote Part', datesent, datereceive" +
				                 " FROM Gmail_Store WHERE case_id = " + AndrospyMain.gb_CaseId +
				                 " AND device_id = " + AndrospyMain.gb_DeviceId + " AND ccaddress ";
				if (bcasesensitive) {
					query += "COLLATE latin1_general_cs";
				}
				query +=
				         " like '%" +
				                 srch +
				                 "%'" +
				                 " UNION " +
				                 "SELECT 'Gmail' AS type, replytoaddress AS 'Remote Part', datesent, datereceive" +
				                 " FROM Gmail_Store WHERE case_id = " + AndrospyMain.gb_CaseId +
				                 " AND device_id = " + AndrospyMain.gb_DeviceId +
				                 " AND replytoaddress ";
				if (bcasesensitive) {
					query += "COLLATE latin1_general_cs";
				}
				query +=
				         " like '%" +
				                 srch +
				                 "%'" +
				                 " UNION " +
				                 "SELECT 'Gmail' AS type, bccaddress AS 'Remote Part', datesent, datereceive" +
				                 " FROM Gmail_Store WHERE case_id = " + AndrospyMain.gb_CaseId +
				                 " AND device_id = " + AndrospyMain.gb_DeviceId +
				                 " AND bccaddress ";
				if (bcasesensitive) {
					query += "COLLATE latin1_general_cs";
				}
				query += " like '%" + srch + "%'";

			} else if (type.equals("contact name")) {
				stacklayout.topControl = tblcmp;
				query =
				        "SELECT 'Phone Book' AS Type, name AS 'Remote Party', last_time_contacted AS 'Timestamp'" +
				                " FROM Contacts WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " + AndrospyMain.gb_DeviceId + " AND name ";
				if (bcasesensitive) {
					query += "COLLATE latin1_general_cs";
				}
				query +=
				         " like '%" +
				                 srch +
				                 "%'" +
				                 " UNION " +
				                 "SELECT CASE " +
				                 "WHEN type = 1 AND logtype = 100 THEN 'Incomming Call Log' " +
				                 "WHEN type = 2 AND logtype = 100 THEN 'Outgoing Call Log' " +
				                 "WHEN type = 3 AND logtype = 100 THEN 'Missed Call Log' " +
				                 "WHEN type = 2 AND logtype = 500 THEN 'Cancelled Call Log' " +
				                 "WHEN type = 1 AND logtype = 200 THEN 'Incomming MMS Log' " +
				                 "WHEN type != 1 AND logtype = 200 THEN 'Outgoing MMS Log' " +
				                 "WHEN type = 1 AND logtype = 300 THEN 'Incomming SMS Log' " +
				                 "WHEN type != 1 AND logtype = 300 THEN 'Outgoing SMS Log' " +
				                 "ELSE ' ' END AS Type," +
				                 " name AS 'Remote Party', date AS 'Timestamp' FROM Event_Log WHERE case_id = " +
				                 AndrospyMain.gb_CaseId + " AND device_id = " +
				                 AndrospyMain.gb_DeviceId + " AND name ";
				if (bcasesensitive) {
					query += "COLLATE latin1_general_cs";
				}
				query +=
				         " like '%" +
				                 srch +
				                 "%'" +
				                 " UNION " +
				                 "SELECT 'Gmail' AS type, fromaddress AS 'Remote Party', datereceive AS 'Timestamp'" +
				                 " FROM Gmail_Store WHERE case_id = " + AndrospyMain.gb_CaseId +
				                 " AND device_id = " + AndrospyMain.gb_DeviceId +
				                 " AND fromaddress ";
				if (bcasesensitive) {
					query += "COLLATE latin1_general_cs";
				}
				query +=
				         " like '%" +
				                 srch +
				                 "%'" +
				                 " UNION " +
				                 "SELECT 'Gmail' AS type, toaddress AS 'Remote Party', datereceive AS 'Timestamp'" +
				                 " FROM Gmail_Store WHERE case_id = " + AndrospyMain.gb_CaseId +
				                 " AND device_id = " + AndrospyMain.gb_DeviceId + " AND toaddress ";
				if (bcasesensitive) {
					query += "COLLATE latin1_general_cs";
				}
				query +=
				         " like '%" +
				                 srch +
				                 "%'" +
				                 " UNION " +
				                 "SELECT 'Gmail' AS type, ccaddress AS 'Remote Party', datereceive AS 'Timestamp'" +
				                 " FROM Gmail_Store WHERE case_id = " + AndrospyMain.gb_CaseId +
				                 " AND device_id = " + AndrospyMain.gb_DeviceId + " AND ccaddress ";
				if (bcasesensitive) {
					query += "COLLATE latin1_general_cs";
				}
				query +=
				         " like '%" +
				                 srch +
				                 "%'" +
				                 " UNION " +
				                 "SELECT 'Gmail' AS type, replytoaddress AS 'Remote Party', datereceive AS 'Timestamp'" +
				                 " FROM Gmail_Store WHERE case_id = " + AndrospyMain.gb_CaseId +
				                 " AND device_id = " + AndrospyMain.gb_DeviceId +
				                 " AND replytoaddress ";
				if (bcasesensitive) {
					query += "COLLATE latin1_general_cs";
				}
				query +=
				         " like '%" +
				                 srch +
				                 "%'" +
				                 " UNION " +
				                 "SELECT 'Gmail' AS type, bccaddress AS 'Remote Party', datereceive AS 'Timestamp'" +
				                 " FROM Gmail_Store WHERE case_id = " + AndrospyMain.gb_CaseId +
				                 " AND device_id = " + AndrospyMain.gb_DeviceId +
				                 " AND bccaddress ";
				if (bcasesensitive) {
					query += "COLLATE latin1_general_cs";
				}
				query += " like '%" + srch + "%'";

			} else if (type.equals("phonography")) {
				query =
				        "SELECT CASE WHEN browser = 'DEFAULTB' THEN 'Default Bookmark'"
				                + " WHEN browser = 'CHROME' THEN 'Chrome Bookmark' END AS type, title AS 'Title', url AS 'URL/Text', modified_date AS 'Date'"
				                + " FROM Androspy_Bookmark " + " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId +
				                " AND (title like '%sex%' OR url like '%sex%') OR" +
				                " (title like '%xxx%' OR url like '%xxx%') OR" +
				                " (title like '%porn%' OR url like '%porn%') OR" +
				                " (title like '%boob%' OR url like '%boob%')" +
				                " UNION " +
				                "SELECT CASE WHEN browser = 'DEFAULTB' THEN 'Default History'" +
				                " WHEN browser = 'CHROME' THEN 'Chrome History' END AS type, title AS 'Title', url AS 'URL/Text', last_visit_date AS 'Date'" +
				                " FROM Browser_History " +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId +
				                " AND (title like '%sex%' OR url like '%sex%') OR" +
				                " (title like '%xxx%' OR url like '%xxx%') OR" +
				                " (title like '%porn%' OR url like '%porn%') OR" +
				                " (title like '%boob%' OR url like '%boob%')" +
				                " UNION " +
				                "SELECT CASE WHEN browser = 'DEFAULTB' THEN 'Default Search'" +
				                " WHEN browser = 'CHROME' THEN 'Chrome Search' END AS type, title AS 'Title', search_text AS 'URL/Text', search_date AS 'Date'" +
				                " FROM WebSearch " +
				                " WHERE case_id = " +
				                AndrospyMain.gb_CaseId +
				                " AND device_id = " +
				                AndrospyMain.gb_DeviceId +
				                " AND (title like '%sex%' OR url like '%sex%') OR" +
				                " (title like '%xxx%' OR url like '%xxx%') OR" +
				                " (title like '%porn%' OR url like '%porn%') OR" +
				                " (title like '%boob%' OR url like '%boob%')";
			}

			System.out.println("query " + query);
			if ((query != "") && !(type.equals("keyword"))) {
				tblcmp.setKeyword(false);
				tblcmp.setHexVisible(false);
				ResultSet rs = stmt.executeQuery(query);
				tblcmp.populate(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
