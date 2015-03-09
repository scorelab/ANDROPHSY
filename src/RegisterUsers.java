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

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * @author indeewari
 *
 */
public class RegisterUsers extends Composite {
	private Text txtFirstName;
	private Text txtLastName;
	private Text txtConfPw;
	private Text txtPassword;
	private Text txtUserName;
	private Text txtNewPassword;
	private final Canvas imagecanvas;
	private Text txtemail;
	private Combo cmbUserRole;
	private String newpw, repw;
	private Label lblinfo;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public RegisterUsers(Composite parent, int style) {
		super(parent, style);
		
		System.out.println(" 4 " + System.currentTimeMillis());
		
		Label lblFirstName = new Label(this, SWT.NONE);
		lblFirstName.setBounds(10, 77, 84, 17);
		lblFirstName.setText("First Name");
		
		txtFirstName = new Text(this, SWT.BORDER);
		txtFirstName.setEditable(false);
		txtFirstName.setBounds(155, 73, 240, 27);
		
		Label lblLastName = new Label(this, SWT.NONE);
		lblLastName.setBounds(10, 111, 84, 17);
		lblLastName.setText("Last Name");
		
		txtLastName = new Text(this, SWT.BORDER);
		txtLastName.setEditable(false);
		txtLastName.setBounds(155, 107, 240, 27);
		
		Label lblUserName = new Label(this, SWT.NONE);
		lblUserName.setBounds(10, 216, 114, 17);
		lblUserName.setText("User Name");
		
		txtUserName = new Text(this, SWT.BORDER);
		txtUserName.setEditable(false);
		txtUserName.setBounds(155, 206, 240, 27);
		
		Label lblPassword = new Label(this, SWT.NONE);
		lblPassword.setBounds(10, 249, 104, 17);
		lblPassword.setText("Old Password");
		
		txtPassword = new Text(this, SWT.BORDER | SWT.PASSWORD);
		txtPassword.setBounds(155, 239, 240, 27);
		
		Label lblConfirmPassword = new Label(this, SWT.NONE);
		lblConfirmPassword.setBounds(10, 315, 134, 17);
		lblConfirmPassword.setText("Confirm Password");
		
		txtConfPw = new Text(this, SWT.BORDER | SWT.PASSWORD);
		txtConfPw.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				newpw 	= txtNewPassword.getText();
				repw 	= txtConfPw.getText();
				if(!newpw.equals(repw)){
					lblinfo.setText("incorrect!");	
					txtConfPw.setText("");
				}
				else{
					lblinfo.setText("");
				}
			}
		});
		txtConfPw.setBounds(155, 305, 240, 27);
		
		Label lblUserRole = new Label(this, SWT.NONE);
		lblUserRole.setBounds(10, 350, 70, 17);
		lblUserRole.setText("User Role");
		
		cmbUserRole = new Combo(this, SWT.DROP_DOWN);
		cmbUserRole.setBounds(155, 338, 240, 29);
		cmbUserRole.add("Administrator", 0);
		cmbUserRole.add("Investigator", 1);
		cmbUserRole.add("Case Analyst", 2);
		
		Button btnRegisterMe = new Button(this, SWT.NONE);
		btnRegisterMe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				newpw 			= txtNewPassword.getText();
				String oldpw 	= txtPassword.getText();
				changePassword(oldpw, newpw);
			}
		});
		btnRegisterMe.setBounds(155, 381, 134, 29);
		btnRegisterMe.setText("Change Password");
		
		Label lblUserRegistration = new Label(this, SWT.NONE);
		lblUserRegistration.setFont(SWTResourceManager.getFont("Ubuntu", 12, SWT.NORMAL));
		lblUserRegistration.setAlignment(SWT.CENTER);
		lblUserRegistration.setBounds(219, 22, 164, 17);
		lblUserRegistration.setText("User Profile");
		
		imagecanvas = new Canvas(this, SWT.BORDER);
		imagecanvas.setBounds(418, 77, 156, 152);
		
		txtNewPassword = new Text(this, SWT.BORDER | SWT.PASSWORD);
		txtNewPassword.setBounds(155, 272, 240, 27);
		
		Label lblNewPassword = new Label(this, SWT.NONE);
		lblNewPassword.setBounds(10, 282, 114, 17);
		lblNewPassword.setText("New Password");
		
		Button btnUpdateProfilePicture = new Button(this, SWT.NONE);
		btnUpdateProfilePicture.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				savePicture();
			}
		});
		btnUpdateProfilePicture.setBounds(418, 237, 156, 29);
		btnUpdateProfilePicture.setText("Add Picture");
		
		txtemail = new Text(this, SWT.BORDER);
		txtemail.setEditable(false);
		txtemail.setBounds(155, 173, 239, 27);
		
		Label lblEmail = new Label(this, SWT.NONE);
		lblEmail.setBounds(10, 183, 70, 17);
		lblEmail.setText("e-mail");
		
		lblinfo = new Label(this, SWT.NONE);
		lblinfo.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		lblinfo.setBounds(418, 315, 156, 17);
			
	}

	protected void changePassword(String oldpw, String newpw2) {
		ConnectDb condb = new ConnectDb();
		AdbExecCmd cmd = new AdbExecCmd();
		Connection conn = condb.getConnection();
		String pwdb = "";
		String oldpwdigest = cmd.getPasswordEncrption(oldpw);
		String newpwdigest = cmd.getPasswordEncrption(newpw2);
		try {
			Statement stmt 	= conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT password FROM Androspy_User WHERE user_name = '" + 
								AndrospyMain.gb_username + "'");
			if(rs.next()){
				pwdb = rs.getString("password");						
				if(!pwdb.equals(oldpwdigest)){
					lblinfo.setText("current password incorrect!");
					return;
				}
				stmt.executeUpdate("UPDATE Androspy_User SET password = '" + newpwdigest + "' WHERE user_name = '" +
									AndrospyMain.gb_username + "'");
				txtConfPw.setText("");
				txtPassword.setText("");
				txtNewPassword.setText("");
				//log
				String stime = new AdbExecCmd().getCurrenttime();
				AndrospyLog.Logdata(stime, "User " + AndrospyMain.gb_username + "password changed");
				MessageBox msgbox = new MessageBox(getShell(), SWT.ICON_INFORMATION);
				msgbox.setMessage("Password Chaged Successfully!");
				msgbox.setText("Password Change");
				msgbox.open();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	protected void savePicture() {
		FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
		fd.setFilterNames(new String[]{"Graphic Interchange Format (*.gif)", 
				"Join Photograpgic Exchange Group (*.jpeg)", "Portable Network Graphic (*.png)"});
		fd.setFilterExtensions(new String[]{"*.gif", "*.jpeg", "*.jpg", "*.png"});
		String file = fd.open();
		String fname = fd.getFileName();
		String ext = "";
		if(file != null){
			//read file extension
			int index = fname.lastIndexOf(".");
			if(index > 0){
				ext = fname.substring(index+1).toLowerCase();
			}
			try {
				showProfilePic(file);
				//create file on disk
				File sourcefile = new File(file);
				BufferedImage bufimage = ImageIO.read(sourcefile);
				String localpath = "/home/indeewari/"+ AndrospyMain.gb_username +"."+ext;
				File destfile = new File(localpath);
				System.out.println(destfile.getPath());
				ImageIO.write(bufimage, ext, destfile);
				//save path to db
				ConnectDb condb = new ConnectDb();
				Connection conn = condb.getConnection();
				Statement stmt = conn.createStatement();
				String stime = new AdbExecCmd().getCurrenttime();
				AndrospyLog.Logdata(stime, "Changed profile picture");
				stmt.executeUpdate("UPDATE Androspy_User SET user_pic = '" + localpath + "' WHERE user_name = '" + AndrospyMain.gb_username + "'");				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}		
	}
	
	private void showProfilePic(String path){
		FileInputStream fin;
		try {
			fin = new FileInputStream(path);
			Image image = new Image(Display.getCurrent(), fin);
			imagecanvas.setBackgroundImage(image);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}

	
	public void populate(String fname, String lname, String email, String uname, String pic, int role) {
		txtFirstName.setText(fname);
		txtLastName.setText(lname);
		txtemail.setText(email);
		txtUserName.setText(uname);	
		cmbUserRole.select(role);
		if(pic != null){
			showProfilePic(pic);
		}
	}
}
