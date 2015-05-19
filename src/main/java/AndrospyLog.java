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
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


/**
 * @author indeewari
 *
 */
public class AndrospyLog extends Composite {
	private static Table table;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AndrospyLog(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnTimeStamp = new TableColumn(table, SWT.NONE);
		tblclmnTimeStamp.setWidth(100);
		tblclmnTimeStamp.setText("Time Stamp");
		
		TableColumn tblclmnUserName = new TableColumn(table, SWT.NONE);
		tblclmnUserName.setWidth(100);
		tblclmnUserName.setText("User Name");
		
		TableColumn tblclmnDescription = new TableColumn(table, SWT.NONE);
		tblclmnDescription.setWidth(100);
		tblclmnDescription.setText("Description");
		
		Button btnRefresh = new Button(this, SWT.NONE);
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				showLog();
			}
		});
		GridData gd_btnRefresh = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnRefresh.widthHint = 109;
		btnRefresh.setLayoutData(gd_btnRefresh);
		btnRefresh.setText("Refresh");

	}
	
	protected void showLog() {
		table.removeAll();
		Connection con = new ConnectDb().getConnection();
		String value[] = new String[3];
		try {
			String query = "SELECT timestamp, username, description FROM Androspy_Log " +
					"WHERE case_id = "+ AndrospyMain.gb_CaseId + 
					" AND device_id = " + AndrospyMain.gb_DeviceId;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				value[0] = rs.getString("timestamp");
				value[1] = rs.getString("username");
				value[2] = rs.getString("description");
				TableItem ti = new TableItem(table, SWT.NONE);
				ti.setText(value);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public static void Logdata(String time, String description){
		Connection con = null;
		try {
			con = new ConnectDb().getConnection();
			Statement stmt = con.createStatement();
			
			String query = "INSERT INTO Androspy_Log (case_id, device_id, username, timestamp, description) VALUES(" 
			+ AndrospyMain.gb_CaseId + ", " + AndrospyMain.gb_DeviceId + ", '" + AndrospyMain.gb_username + "', '" + time + "', '" + description + "' "
			+ ")";
			
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void getLog(String username, int case_id, int device_number){
		Connection con = null;
		try {
			con = new ConnectDb().getConnection();
			Statement stmt = con.createStatement();
			String query = "SELECT username, timestamp, description" +
					" FROM Androspy_Log" +
					" WHERE case_id = " + AndrospyMain.gb_CaseId + 
					" AND device_id = " + AndrospyMain.gb_DeviceId;			
			ResultSet rs = stmt.executeQuery(query);
			String values[] = new String[3];
			while(rs.next()){
				values[0] = rs.getString("timestamp");
				values[1] = rs.getString("username");
				values[2] = rs.getString("description");
				TableItem titem = new TableItem(table, SWT.NONE);
				System.out.println(values[0] + ", " + values[1] + ", " + values[2]);
				titem.setText(values);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	} 
}
