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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;


/**
 * @author indeewari
 *
 */
public class Login extends Composite {
	private Text txtUserName;
	private Text txtPassword;
	private Androspy androspy;
	private String fname, lname, email, pw, pic_path, userRole, user_name;
	private int user_id, role, flag_new;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public Login(Composite parent, int style) {
		super(parent, style);
		
		Label lblUserName = new Label(this, SWT.NONE);
		lblUserName.setBounds(55, 100, 88, 17);
		lblUserName.setText("User Name:");
		
		txtUserName = new Text(this, SWT.BORDER);
		txtUserName.setBounds(166, 90, 201, 27);
		
		Label lblPassword = new Label(this, SWT.NONE);
		lblPassword.setBounds(55, 136, 70, 17);
		lblPassword.setText("Password:");
		
		txtPassword = new Text(this, SWT.BORDER | SWT.PASSWORD);
		txtPassword.setBounds(166, 126, 201, 27);
		
		Button btnLogin = new Button(this, SWT.NONE);
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				ButtonAction(((Button)arg0.widget).getText());
			}
		});		
		btnLogin.setBounds(166, 169, 91, 29);
		btnLogin.setText("Login");
		
		Label lblstatus = new Label(this, SWT.NONE);
		lblstatus.setAlignment(SWT.CENTER);
		lblstatus.setBounds(69, 56, 302, 17);
		lblstatus.setText("Hello Guest! Please Log");
		
		Label lblLogin = new Label(this, SWT.NONE);
		lblLogin.setAlignment(SWT.CENTER);
		lblLogin.setBounds(74, 20, 302, 17);
		lblLogin.setText("USER LOGIN");		
	}

	protected void ButtonAction(String txt) {
		if(txt.equals("Login")){			
			user_name = txtUserName.getText();
			System.out.println("pass: " + txtPassword.getText());
			boolean ret = AuthenticateUser(user_name, txtPassword.getText());
			if(ret){
				this.setVisible(false);
			}
			txtPassword.setText("");
		}
		
	}
	
	private void getUserDetail(String userName){	
		ConnectDb condb = new ConnectDb();
		Connection conn	= condb.getConnection();
		try {
			Statement stmt 	= conn.createStatement();
			ResultSet rs 	= stmt.executeQuery("SELECT user_id, first_name, last_name, " +
							"user_mail, new_user, password, user_role, user_pic FROM Androspy_User " + 
							"WHERE user_name = '" +userName+ "'");
			if(rs.next()){
				user_id		= Integer.parseInt(rs.getString("user_id"));
				role		= Integer.parseInt(rs.getString("user_role"));
				flag_new	= Integer.parseInt(rs.getString("new_user"));
				fname 		= rs.getString("first_name");
				lname 		= rs.getString("last_name");
				email		= rs.getString("user_mail");
				pw			= rs.getString("password");
				pic_path	= rs.getString("user_pic");
				
				switch (role){
					case 0:
						userRole = "ADMIN";
						break;
					case 1:
						userRole = "INVESTIGATOR";
						break;
					case 2:
						userRole = "CASEANALYST";
						break;
				}
				AndrospyMain.gb_UserRole = userRole;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	private boolean AuthenticateUser(String userName, String password){		
		getUserDetail(userName);		
		String pwdigest = new AdbExecCmd().getPasswordEncrption(password);
		System.out.println("Hashed pw: " + pwdigest);
		try {
			ConnectDb condb = new ConnectDb();
			Connection conn = condb.getConnection();
			Statement stmt 	= conn.createStatement();
			System.out.println(pw + " pass: " + pwdigest);
			if(pw.equals(pwdigest)){
				SetUserView(userRole);
				//set global user name
				AndrospyMain.gb_username = userName;
				//log
				String stime = new AdbExecCmd().getCurrenttime();
				AndrospyLog.Logdata(stime, "User " + user_name + " Logged");
				if(flag_new == 1){
					//first time login
					stmt.executeUpdate("UPDATE Androspy_User SET new_user = 0 WHERE user_name = '" + userName + "'");
					MessageBox msgbox = new MessageBox(getShell(), SWT.ICON_INFORMATION);
					msgbox.setMessage("Welcome " + userName + "\n" + "Please change default password");
					msgbox.setText("First Login - Change Password");
					msgbox.open();
				}
					return true;
			}
			String stime = new AdbExecCmd().getCurrenttime();
			AndrospyLog.Logdata(stime, "User " + user_name + " Logging attempt failed");
			MessageBox msgbox = new MessageBox(getShell(), SWT.ICON_ERROR);
			msgbox.setMessage("Incorrect credentials \n If you have not sign up please contact Admin");
			msgbox.setText("Login Fail");
			msgbox.open();
			return false;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}			
	}

	private void SetUserView(String userRole) {
		if(userRole.equals("ADMIN")){
			Shell sh = getShell();
			androspy = new Androspy(sh, SWT.NONE, "ADMIN");	
			sh.setLayout(new GridLayout());
			sh.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			androspy.setBounds(0, 0,sh.getSize().x, sh.getSize().y);
			androspy.setVisible(true);
		}
		else if(userRole.equals("INVESTIGATOR")){
			androspy = new Androspy(getShell(), SWT.NONE, "INVESTIGATOR");	
			getShell().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			androspy.setBounds(0, 0,1000, 1000);
			androspy.setVisible(true);
		}
		else if(userRole.equals("CASEANALYST")){
			androspy = new Androspy(getShell(), SWT.NONE, "CASEANALYST");	
			getShell().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			androspy.setBounds(0, 0,1000, 1000);
			androspy.setVisible(true);
		}
		else{
			//create error
		}
		if(!androspy.equals(null)){
			androspy.populateUserProfile(fname, lname, email, user_name, pic_path, role);
		}
		
	}
}
