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
import java.text.SimpleDateFormat;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.wb.swt.SWTResourceManager;


/**
 * @author indeewari
 *
 */
public class UserManagement extends Composite {

	private Text txtUserName;
	private Text txtUserId;
	private Text txtEmail;
	private Text txtFirstName;
	private Text txtLastName;
	private Text txtPassword;
	private Text txtConfPw;
	private Combo cmbUserRole;
	private Connection conn;
	private static boolean bnewUser = false;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public UserManagement(Composite parent, int style) {
		super(parent, style);
		//create database connection
		ConnectDb condb = new ConnectDb();		 
		conn = condb.getConnection();
	
		Label lblUserRegistration = new Label(this, SWT.NONE);
		lblUserRegistration.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblUserRegistration.setAlignment(SWT.CENTER);
		lblUserRegistration.setBounds(134, 10, 164, 27);
		lblUserRegistration.setText("User Registration");
		
		Label lblImportantNoteAll = new Label(this, SWT.NONE);
		lblImportantNoteAll.setBounds(10, 43, 385, 17);
		lblImportantNoteAll.setText("Important Note: All fields are mandatory");
		
		Label lblFirstName = new Label(this, SWT.NONE);
		lblFirstName.setBounds(10, 88, 84, 17);
		lblFirstName.setText("First Name");
		
		txtFirstName = new Text(this, SWT.BORDER);
		txtFirstName.setEnabled(false);
		txtFirstName.setEditable(false);
		txtFirstName.setBounds(155, 84, 240, 27);
		
		Label lblLastName = new Label(this, SWT.NONE);
		lblLastName.setBounds(10, 122, 84, 17);
		lblLastName.setText("Last Name");
		
		txtLastName = new Text(this, SWT.BORDER);
		txtLastName.setEnabled(false);
		txtLastName.setEditable(false);
		txtLastName.setBounds(155, 118, 240, 27);
		
		Label lblNic = new Label(this, SWT.NONE);
		lblNic.setBounds(10, 161, 70, 17);
		lblNic.setText("E-Mail");
		
		txtEmail = new Text(this, SWT.BORDER);
		txtEmail.setBounds(155, 151, 240, 27);
		
		Label lblUserName = new Label(this, SWT.NONE);
		lblUserName.setBounds(10, 261, 114, 17);
		lblUserName.setText("User Name");
		
		txtUserName = new Text(this, SWT.BORDER);
		txtUserName.setBounds(155, 251, 240, 27);
		
		Label lblPassword = new Label(this, SWT.NONE);
		lblPassword.setBounds(10, 294, 104, 17);
		lblPassword.setText("Password");
		
		txtPassword = new Text(this, SWT.BORDER | SWT.PASSWORD);
		txtPassword.setEnabled(false);
		txtPassword.setEditable(false);
		txtPassword.setBounds(155, 284, 240, 27);
		
		Label lblConfirmPassword = new Label(this, SWT.NONE);
		lblConfirmPassword.setBounds(10, 327, 134, 17);
		lblConfirmPassword.setText("Confirm Password");
		
		txtConfPw = new Text(this, SWT.BORDER | SWT.PASSWORD);
		txtConfPw.setEnabled(false);
		txtConfPw.setEditable(false);
		txtConfPw.setBounds(155, 317, 240, 27);
		
		Label lblUserRole = new Label(this, SWT.NONE);
		lblUserRole.setBounds(10, 362, 70, 17);
		lblUserRole.setText("User Role");
		
		cmbUserRole = new Combo(this, SWT.DROP_DOWN);
		cmbUserRole.setEnabled(false);
		cmbUserRole.setBounds(155, 350, 240, 29);
		cmbUserRole.add("Administrator", 0);
		cmbUserRole.add("Investigator", 1);
		cmbUserRole.add("Case Analyst", 2);
		
		Button btnNew = new Button(this, SWT.NONE);
		btnNew.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				bnewUser = true;
				setFieldsEnable(true);
			}
		});
		btnNew.setBounds(10, 399, 91, 29);
		btnNew.setText("New");
		
		Button btnSave = new Button(this, SWT.NONE);
		btnSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				String role, query = "";
				String db_role = "-1";
				MessageBox msgbox = new MessageBox(getShell(), SWT.OK);
				SimpleDateFormat sdf = new SimpleDateFormat("yyy-mm-dd");
				
				if((txtEmail.getText().indexOf("@")) == -1){
					MessageBox mbError = new MessageBox(getShell(), SWT.ERROR);
					mbError.setText("Error");
					mbError.setMessage("Invalid Email");
					mbError.open();
					return;
				}
				
				try {
					Statement stmt = conn.createStatement();
					if(bnewUser){
						bnewUser = false;
						role = cmbUserRole.getText().toUpperCase();
						if(role.equals("ADMIN")){
							db_role = "0";
						}
						else if(role.equals("INVESTIGATOR")){
							db_role = "1";
						}
						else if(role.equals("CASE ANALYST")){
							db_role = "2";
						}
						String pwdigest = new AdbExecCmd().getPasswordEncrption(txtPassword.getText());
						query = "INSERT INTO Androspy_User (first_name, last_name, nic, "+
								"user_name, password, user_role) Values ('" + txtFirstName.getText() + "',"+
																		"'" + txtLastName.getText() + "'," +
																	//	date + "," + 
																		"'" + txtEmail.getText() + "'," + 
																		"'" + txtUserName.getText() + "'," +
																		"'" + pwdigest + "'," + 
																		"'" +  db_role + "'" +
																		");";
	
						int c = stmt.executeUpdate(query);
						if(c > 0 ){
							String stime = new AdbExecCmd().getCurrenttime();
							AndrospyLog.Logdata(stime, "Added New User " + txtUserName.getText() + " role " + db_role);
						}
						clearFields();
						setFieldsEnable(false);
					}
					else{
						query = "UPDATE Androspy_User SET user_role = '" + cmbUserRole.getText() + "'";
						query = query + "WHERE user_id = '" + txtUserId.getText() + "'";
						System.out.println("Update Query " + query);
						stmt.executeUpdate(query);
						String stime = new AdbExecCmd().getCurrenttime();
						AndrospyLog.Logdata(stime, "Update user role " + txtUserName + " new role " + cmbUserRole.getText());
						msgbox.setText("Update User: " + txtUserName);
						msgbox.setMessage("User Updated Successfully");
						msgbox.open();
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}					
			}
		});
		btnSave.setBounds(10, 434, 91, 29);
		btnSave.setText("Save");
		
		Button btnSearch = new Button(this, SWT.NONE);
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				//select only by nic and contact name
				String email = txtEmail.getText();
				String username = txtUserName.getText();
				if(!email.equals("")|| !username.equals("")){
					String query = "SELECT * FROM Androspy_User WHERE ";
					if(!email.equals("")){
						query = query + "nic = '" + email + "' AND ";
					}
					
					if(!username.equals("")){
						query += "user_name = '" + username + "'";
					}
					else{
						query += " 1=1";
					}
					try {
						Statement stmt = conn.createStatement();
						System.out.println("search query " + query);
						ResultSet rs = stmt.executeQuery(query);
						if(rs.next()){
							txtUserId.setText(rs.getString("user_id"));
							txtFirstName.setText(rs.getString("first_name"));
							txtLastName.setText(rs.getString("last_name"));
							txtEmail.setText(rs.getString("nic"));
							txtUserName.setText(rs.getString("user_name"));
							int i = Integer.parseInt(rs.getString("user_role"));
							cmbUserRole.select(i);
						}
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnSearch.setBounds(155, 399, 91, 29);
		btnSearch.setText("Search");
		btnSearch.setToolTipText("Search user by E-Mail, Contact, Username");
		
		
		Button btnDelete = new Button(this, SWT.NONE);
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				try {
					String user_name = txtUserName.getText();
					Statement stmt = conn.createStatement();
					int count = stmt.executeUpdate("DELETE FROM Androspy_User where user_id = '"+ txtUserId.getText()+ "'");
					MessageBox msg = new MessageBox(getShell(), SWT.OK);
					msg.setText("Deleted User");
					if(count == 1){						
						msg.setMessage("User Deleted successfully: " + user_name);
					}
					else{
						msg.setMessage("Unable to deleted user: " + user_name + "/n Please try again later");
					}
					clearFields();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		btnDelete.setBounds(304, 399, 91, 29);
		btnDelete.setText("Delete");
		
		//hidden field
		txtUserId = new Text(this, SWT.BORDER);
		txtUserId.setEditable(false);
		txtUserId.setBounds(304, 434, 91, 27);
	}

	protected void clearFields() {
		txtFirstName.setText("");
		txtLastName.setText("");
		txtEmail.setText("");
		txtUserName.setText("");
		txtUserId.setText("");
		txtPassword.setText("");
		txtConfPw.setText("");
		cmbUserRole.clearSelection();
	}

	protected void setFieldsEnable(boolean b) {
		txtFirstName.setEditable(b);
		txtFirstName.setEnabled(b);
		
		txtLastName.setEditable(b);
		txtLastName.setEnabled(b);
		
		txtEmail.setEditable(b);
		txtEmail.setEditable(b);
		
		txtUserName.setEditable(b);
		txtUserName.setEnabled(b);
		
		txtPassword.setEditable(b);
		txtPassword.setEnabled(b);
		
		txtConfPw.setEditable(b);
		txtConfPw.setEnabled(b);
		
		cmbUserRole.setEnabled(b);
	}
}
