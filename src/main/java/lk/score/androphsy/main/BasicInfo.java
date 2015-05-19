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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

/**
 * @author indeewari
 *
 */
public class BasicInfo extends Composite {
	private static Text txtDeviceName;
	private static Text txtModel;
	private static Text txtManufacturer;
	private static Text txtAndroidVersion;
	private static Text txtBuildVersion;
	private static Text txtIemi;
	private static Text txtImsi;
	private static Text txtProcessorSpeed;
	private static Text txtRam;
	private static Text txtBtMac;
	private static Text txtWifiMac;
	private static Text txtKernalSource;
	private static Text txtUsbDebug;
	private Text txtPrimaryAcc;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public BasicInfo(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, true));

		Group grpBasicInformation = new Group(this, SWT.NONE);
		grpBasicInformation.setLayout(new GridLayout(2, false));
		grpBasicInformation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 2));
		grpBasicInformation.setText("Basic Information");

		Label lblDeviceName = new Label(grpBasicInformation, SWT.NONE);
		lblDeviceName.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblDeviceName.setText("Device Name");

		txtDeviceName = new Text(grpBasicInformation, SWT.BORDER);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text.widthHint = 171;
		txtDeviceName.setLayoutData(gd_text);

		Label lblModel = new Label(grpBasicInformation, SWT.NONE);
		lblModel.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblModel.setText("Model");

		txtModel = new Text(grpBasicInformation, SWT.BORDER);
		GridData gd_text_1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text_1.widthHint = 171;
		txtModel.setLayoutData(gd_text_1);

		Label lblManufacturer = new Label(grpBasicInformation, SWT.NONE);
		lblManufacturer.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblManufacturer.setText("Manufacturer");

		txtManufacturer = new Text(grpBasicInformation, SWT.BORDER);
		GridData gd_text_2 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text_2.widthHint = 171;
		txtManufacturer.setLayoutData(gd_text_2);

		Label lblAndroidVersion = new Label(grpBasicInformation, SWT.NONE);
		lblAndroidVersion.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblAndroidVersion.setText("Android Version");

		txtAndroidVersion = new Text(grpBasicInformation, SWT.BORDER);
		GridData gd_text_3 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text_3.widthHint = 171;
		txtAndroidVersion.setLayoutData(gd_text_3);

		Label lblBuildVersion = new Label(grpBasicInformation, SWT.NONE);
		lblBuildVersion.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblBuildVersion.setText("Build Version");

		txtBuildVersion = new Text(grpBasicInformation, SWT.BORDER);
		txtBuildVersion.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblImei = new Label(grpBasicInformation, SWT.NONE);
		lblImei.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblImei.setText("IMEI");

		txtIemi = new Text(grpBasicInformation, SWT.BORDER);
		txtIemi.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblImsi = new Label(grpBasicInformation, SWT.NONE);
		lblImsi.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblImsi.setText("IMSI");

		txtImsi = new Text(grpBasicInformation, SWT.BORDER);
		txtImsi.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Group grpHarwareProfile = new Group(this, SWT.NONE);
		grpHarwareProfile.setLayout(new GridLayout(2, false));
		grpHarwareProfile.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		grpHarwareProfile.setText("Hardware Profile");

		Label lblProcessorSpeed = new Label(grpHarwareProfile, SWT.NONE);
		lblProcessorSpeed.setText("Processor Speed");

		txtProcessorSpeed = new Text(grpHarwareProfile, SWT.BORDER);
		txtProcessorSpeed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblRamCapacity = new Label(grpHarwareProfile, SWT.NONE);
		lblRamCapacity.setText("RAM Capacity");

		txtRam = new Text(grpHarwareProfile, SWT.BORDER);
		txtRam.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblBluetoothMac = new Label(grpHarwareProfile, SWT.NONE);
		lblBluetoothMac.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblBluetoothMac.setText("Bluetooth MAC");

		txtBtMac = new Text(grpHarwareProfile, SWT.BORDER);
		txtBtMac.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblWifiMac = new Label(grpHarwareProfile, SWT.NONE);
		lblWifiMac.setText("Wi-Fi MAC");

		txtWifiMac = new Text(grpHarwareProfile, SWT.BORDER);
		txtWifiMac.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Group grpForensicInformation = new Group(this, SWT.NONE);
		grpForensicInformation.setLayout(new GridLayout(2, false));
		grpForensicInformation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		grpForensicInformation.setText("Forensic Information");

		Label lblKernelSecure = new Label(grpForensicInformation, SWT.NONE);
		lblKernelSecure.setText("Kernel Secure");

		txtKernalSource = new Text(grpForensicInformation, SWT.BORDER);
		txtKernalSource.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblUsbDebuggingEnabled = new Label(grpForensicInformation, SWT.NONE);
		lblUsbDebuggingEnabled.setText("USB Debugging Enabled");

		txtUsbDebug = new Text(grpForensicInformation, SWT.BORDER);
		txtUsbDebug.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Group grpDevicePrimaryAccount = new Group(this, SWT.NONE);
		grpDevicePrimaryAccount.setLayout(new GridLayout(1, false));
		grpDevicePrimaryAccount.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		grpDevicePrimaryAccount.setText("Device Primary Account");

		txtPrimaryAcc = new Text(grpDevicePrimaryAccount, SWT.BORDER);
		txtPrimaryAcc.setEditable(false);
		txtPrimaryAcc.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);

		populateData();
	}

	public static void populateData() {
		Connection conn = new ConnectDb().getConnection();
		try {
			Statement stmt = conn.createStatement();
			String query =
			               "SELECT IFNULL(device_name, '') AS device_name, IFNULL(model, '') AS model," +
			                       " IFNULL(manufacturer, '') AS manufacturer, IFNULL(os, '') AS os," +
			                       " IFNULL(build_number, '') AS build_number, IFNULL(imei, '') AS imei," +
			                       " IFNULL(imsi, '') AS imsi, IFNULL(bt_mac, '') AS bt_mac, IFNULL(processor_speed, '') AS processor_speed," +
			                       " IFNULL(ram, '') AS ram, IFNULL(wifi_mac, '') AS wifi_mac," +
			                       " IFNULL(kernel_secure, '') AS kernel_secure, IFNULL(usb_debug, '') AS usb_debug" +
			                       " FROM Androspy_Device " +
			                       "WHERE case_id = " +
			                       AndrospyMain.gb_CaseId +
			                       " AND device_id = " +
			                       AndrospyMain.gb_DeviceId;
			System.out.println("$$ " + query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				txtDeviceName.setText(rs.getString("device_name"));
				txtModel.setText(rs.getString("model"));
				txtManufacturer.setText(rs.getString("manufacturer"));
				txtAndroidVersion.setText(rs.getString("os"));
				txtBuildVersion.setText(rs.getString("build_number"));
				txtIemi.setText(rs.getString("imei"));
				txtImsi.setText(rs.getString("imsi"));
				txtBtMac.setText(rs.getString("bt_mac"));
				txtProcessorSpeed.setText(rs.getString("processor_speed"));
				txtRam.setText(rs.getString("ram"));
				txtWifiMac.setText(rs.getString("wifi_mac"));
				txtKernalSource.setText(rs.getString("kernel_secure"));
				txtUsbDebug.setText(rs.getString("usb_debug"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
