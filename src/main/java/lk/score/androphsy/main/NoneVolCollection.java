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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;

/**
 * @author indeewari
 *
 */
public class NoneVolCollection extends Composite {
	private Button btnPhysical;
	private Button btnLogical;
	private Button btnSdCard;
	private Button btnCache;
	private Button btnSystem;
	private Button btnData;
	private Group grpBasicDeviceInformation;
	private Button btnDeviceProperties;
	private Button btnInstalledApplicationList;
	private Label lblsdhash;
	private Label lblcachehash;
	private Label lblsystemhash;
	private Label lbldatahash;
	private ProgressBar progressBar;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public NoneVolCollection(Composite parent, int style) {
		super(parent, style);
		// setLayout(null);
		setLayout(new GridLayout(2, false));

		Group grpNoneVolatileData = new Group(this, SWT.NONE);
		GridData gd_grpNoneVolatileData = new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1);
		gd_grpNoneVolatileData.heightHint = 217;
		grpNoneVolatileData.setLayoutData(gd_grpNoneVolatileData);
		grpNoneVolatileData.setBounds(10, 10, 505, 176);
		grpNoneVolatileData.setText("None Volatile Data lk.score.androphsy.main.Acquisition");

		btnData = new Button(grpNoneVolatileData, SWT.CHECK);
		btnData.setBounds(10, 29, 64, 14);
		btnData.setText("data");

		btnSystem = new Button(grpNoneVolatileData, SWT.CHECK);
		btnSystem.setBounds(10, 49, 94, 20);
		btnSystem.setText("system");

		btnCache = new Button(grpNoneVolatileData, SWT.CHECK);
		btnCache.setBounds(10, 75, 94, 14);
		btnCache.setText("cache");

		btnSdCard = new Button(grpNoneVolatileData, SWT.CHECK);
		btnSdCard.setBounds(10, 95, 104, 14);
		btnSdCard.setText("SD Card");
		new Label(grpNoneVolatileData, SWT.NONE);

		btnLogical = new Button(grpNoneVolatileData, SWT.CHECK);
		btnLogical.setBounds(10, 138, 362, 16);
		btnLogical.setText("Acquire at File System Level (Logical Acquisiton)");

		btnPhysical = new Button(grpNoneVolatileData, SWT.CHECK);
		btnPhysical.setBounds(10, 158, 362, 16);
		btnPhysical.setText("Acquire bit-to-bit copy (Physical lk.score.androphsy.main.Acquisition)");

		Button btnAcquire = new Button(grpNoneVolatileData, SWT.NONE);
		btnAcquire.setBounds(10, 194, 129, 32);
		btnAcquire.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (btnData.getSelection() && btnLogical.getSelection()) {
					LogicalAcquisition("DATA");
				}
				if (btnSystem.getSelection() && btnLogical.getSelection()) {
					LogicalAcquisition("SYSTEM");
				}
				if (btnCache.getSelection() && btnLogical.getSelection()) {
					LogicalAcquisition("CACHE");
				}
				if (btnSdCard.getSelection() && btnLogical.getSelection()) {
					// LogicalAcquisition("SYSTEM");
				}

				if (btnData.getSelection() && btnPhysical.getSelection()) {
					PhysicalAcquisition("DATA");
				}
				if (btnSystem.getSelection() && btnPhysical.getSelection()) {
					PhysicalAcquisition("SYSTEM");
				}
				if (btnCache.getSelection() && btnPhysical.getSelection()) {
					PhysicalAcquisition("CACHE");
				}
				if (btnSdCard.getSelection() && btnPhysical.getSelection()) {
					// PhysicalAcquisition("SYSTEM");
				}
			}
		});
		GridData gd_btnAcquire = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAcquire.widthHint = 143;
		btnAcquire.setLayoutData(gd_btnAcquire);
		btnAcquire.setText("Acquire");

		lbldatahash = new Label(grpNoneVolatileData, SWT.NONE);
		lbldatahash.setBounds(143, 29, 362, 17);

		lblsystemhash = new Label(grpNoneVolatileData, SWT.NONE);
		lblsystemhash.setBounds(143, 52, 362, 17);

		lblcachehash = new Label(grpNoneVolatileData, SWT.NONE);
		lblcachehash.setBounds(143, 75, 362, 17);

		lblsdhash = new Label(grpNoneVolatileData, SWT.NONE);
		lblsdhash.setBounds(143, 95, 362, 17);

		FormData fd_progressBar = new FormData();
		fd_progressBar.bottom = new FormAttachment(0);
		fd_progressBar.right = new FormAttachment(0);
		fd_progressBar.top = new FormAttachment(0);
		fd_progressBar.left = new FormAttachment(0);

		progressBar = new ProgressBar(this, SWT.INDETERMINATE);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		progressBar.setVisible(false);
		grpBasicDeviceInformation = new Group(this, SWT.NONE);
		GridData gd_grpBasicDeviceInformation =
		                                        new GridData(SWT.FILL, SWT.CENTER, false, false, 1,
		                                                     1);
		gd_grpBasicDeviceInformation.widthHint = 358;
		gd_grpBasicDeviceInformation.heightHint = 102;
		grpBasicDeviceInformation.setLayoutData(gd_grpBasicDeviceInformation);
		grpBasicDeviceInformation.setText("Basic Device Information");

		btnDeviceProperties = new Button(grpBasicDeviceInformation, SWT.CHECK);
		btnDeviceProperties.setBounds(10, 28, 177, 16);
		btnDeviceProperties.setText("Device Properties ");

		btnInstalledApplicationList = new Button(grpBasicDeviceInformation, SWT.CHECK);
		btnInstalledApplicationList.setBounds(10, 50, 217, 16);
		btnInstalledApplicationList.setText("Installed Application List");
		new Label(grpBasicDeviceInformation, SWT.NONE);

		Button btnReadInformation = new Button(grpBasicDeviceInformation, SWT.NONE);
		btnReadInformation.setBounds(10, 81, 130, 30);
		btnReadInformation.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (btnDeviceProperties.getSelection()) {
					System.out.println("propery");
					AdbExecCmd cmd = new AdbExecCmd();
					DeviceTemplate devicetemplate = new DeviceTemplate();
					cmd.getDeviceProperty(devicetemplate);
					saveDeviceProperty(devicetemplate);
					System.out.println("property read finish");
					BasicInfo.populateData();
				}
				if (btnInstalledApplicationList.getSelection()) {
					new AdbExecCmd().getPackageList();
				}
			}
		});

		btnReadInformation.setText("Read Information");
		GridData gd_btnReadInformation = new GridData(SWT.LEFT, SWT.TOP, false, false, 1, 1);
		gd_btnReadInformation.heightHint = 28;
		gd_btnReadInformation.widthHint = 138;

		Button btnScreenCapture = new Button(this, SWT.NONE);
		GridData gd_btnScreenCapture = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_btnScreenCapture.widthHint = 131;
		btnScreenCapture.setLayoutData(gd_btnScreenCapture);
		btnScreenCapture.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				File dir = new File(AndrospyMain.gb_CasePath + "Analysis/ScreenCapture");
				if (!dir.exists()) {
					dir.mkdir();
				}
				new AdbExecCmd().captureScreen(AndrospyMain.gb_CasePath + "Analysis/ScreenCapture");
			}
		});
		btnScreenCapture.setText("Screen Capture");

		showHashes();

	}

	private void showHashes() {

		try {
			File file = new File(AndrospyMain.gb_CasePath + "Acquire/Hashes/data.dd.hash");
			if (file.exists()) {
				BufferedReader bufin = new BufferedReader(new FileReader(file));
				lbldatahash.setText("Hash: " + bufin.readLine());
			}

			file = new File(AndrospyMain.gb_CasePath + "Acquire/Hashes/system.dd.hash");
			if (file.exists()) {
				BufferedReader bufin = new BufferedReader(new FileReader(file));
				lblsystemhash.setText("Hash: " + bufin.readLine());
			}

			file = new File(AndrospyMain.gb_CasePath + "Acquire/Hashes/cache.dd.hash");
			if (file.exists()) {
				BufferedReader bufin = new BufferedReader(new FileReader(file));
				lblcachehash.setText("Hash: " + bufin.readLine());
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void saveDeviceProperty(DeviceTemplate devicetemplate) {
		try {
			Connection conn = new ConnectDb().getConnection();
			Statement stmt = conn.createStatement();

			String query =
			               "Update Androspy_Device SET device_name = '" +
			                       devicetemplate.getDeviceName() + "', model = '" +
			                       devicetemplate.getModel() + "', manufacturer = '" +
			                       devicetemplate.getManufacturer() + "', os = '" +
			                       devicetemplate.getOs() + "', build_number = '" +
			                       devicetemplate.getBuildNumber() + "', sw_number = '" +
			                       devicetemplate.getSwVersion() + "', processor_speed = '" +
			                       devicetemplate.getProcessorSpeed() + "', ram = '" +
			                       devicetemplate.getRam() + "', wifi = '" +
			                       devicetemplate.getWifi() + "', wifi_mac = '" +
			                       devicetemplate.getWifiMac() + "', bluetooth = '" +
			                       devicetemplate.getBluetooth() + "', bt_mac = '" +
			                       devicetemplate.getBtMac() + "', kernel_secure = '" +
			                       devicetemplate.getKernelSecure() + "', usb_debug = '" +
			                       devicetemplate.getUsbDebugEnabled() + "', adb_enable = '" +
			                       devicetemplate.getAdbEnabled() + "', imei = '" +
			                       devicetemplate.getImei() + "', imsi = '" +
			                       devicetemplate.getImsi() + "' " + "WHERE case_id = " +
			                       AndrospyMain.gb_CaseId + " AND device_id = " +
			                       AndrospyMain.gb_DeviceId;

			System.out.println(query);
			int count = stmt.executeUpdate(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void PhysicalAcquisition(final String partition) {
		String devicestr = "";
		ArrayList<String> mountdata;
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		AndrospyLog.Logdata(sf.format(new Date()), "Physical acquisition on " + partition);
		try {
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						getDisplay().syncExec(new Runnable() {

							@Override
							public void run() {
								progressBar.setVisible(true);

							}
						});

						String line = "", spath = "";
						java.util.List<String> templist = new ArrayList<String>();
						templist.clear();
						templist.add("files/c.sh");

						if (partition == "SYSTEM") {
							System.out.println(partition);
							spath = AndrospyMain.gb_CasePath + "Acquire/System.dd";
						} else if (partition == "DATA") {
							System.out.println(partition);
							spath = AndrospyMain.gb_CasePath + "Acquire/Data.dd";
						} else if (partition == "CACHE") {
							System.out.println(partition);
							spath = AndrospyMain.gb_CasePath + "Acquire/Cache.dd";
						} else {
							return;
						}

						templist.add(spath);
						ProcessBuilder pb1 = new ProcessBuilder(templist);
						System.out.println("before");
						Process proc1;
						for (int i = 0; i < 10000000; i++) {
							System.out.println(i);
						}

						proc1 = pb1.start();

						System.out.println("after");
						BufferedReader buf1 =
						                      new BufferedReader(
						                                         new InputStreamReader(
						                                                               proc1.getInputStream()));
						while ((line = buf1.readLine()) != null) {
							System.out.println(line);
						}

						BufferedReader buf21 =
						                       new BufferedReader(
						                                          new InputStreamReader(
						                                                                proc1.getErrorStream()));
						while ((line = buf21.readLine()) != null) {
							System.out.println(line);
						}

						String shash = new AdbExecCmd().generateFileHash(spath);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getDisplay().syncExec(new Runnable() {

						@Override
						public void run() {
							progressBar.setVisible(false);

						}
					});
				}
			});

			String line = "";
			java.util.List<String> templist = new ArrayList<String>();
			templist.add("files/a.sh");
			ProcessBuilder pbforward = new ProcessBuilder(templist);
			Process pforward = pbforward.start();
			BufferedReader buf =
			                     new BufferedReader(
			                                        new InputStreamReader(pforward.getInputStream()));
			while ((line = buf.readLine()) != null) {
				System.out.println("forward: " + line);
			}

			BufferedReader buf2 =
			                      new BufferedReader(
			                                         new InputStreamReader(
			                                                               pforward.getErrorStream()));
			while ((line = buf2.readLine()) != null) {
				System.out.println("forward error: " + line);
			}

			for (int i = 0; i < 1000000; i++) {
			}

			System.out.println("start adb shell listnening");

			if (partition == "SYSTEM") {
				mountdata = new AdbExecCmd().getMounts("SYSTEM");
			} else if (partition == "DATA") {
				mountdata = new AdbExecCmd().getMounts("DATA");
			} else if (partition == "CACHE") {
				mountdata = new AdbExecCmd().getMounts("CACHE");
			} else {
				return;
			}

			devicestr = mountdata.get(0);
			System.out.println("mnt device: " + devicestr);

			templist.clear();
			templist.add("files/b.sh");
			templist.add(devicestr);
			ProcessBuilder pb = new ProcessBuilder(templist);
			System.out.println("starting");
			Process proc = pb.start();
			System.out.println("after starting");
			t.start();
			System.out.println("thrad state " + t.getState());
			buf = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while ((line = buf.readLine()) != null) {
				System.out.println("x " + line);
			}

			buf2 = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			while ((line = buf2.readLine()) != null) {
				System.out.println("x error " + line);
			}

			System.out.println("redirect test finish");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void LogicalAcquisition(final String partition) {

		final String path = AndrospyMain.gb_CasePath + "Acquire";
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		AndrospyLog.Logdata(sf.format(new Date()), "File system partition acquisition - " +
		                                           partition);
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						progressBar.setVisible(true);

					}
				});

				AdbExecCmd cmd = new AdbExecCmd();
				System.out.println("long");

				cmd.pullRoot(path, partition);

				if (partition == "DATA") {
					cmd.generateHash(path + "/data");
				} else if (partition == "SYSTEM") {
					cmd.generateHash(path + "/system");
				} else if (partition == "CACHE") {
					cmd.generateHash(path + "/cache");
				} else {
					cmd.generateHash(path + "/root");
				}

				getDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						progressBar.setVisible(false);

					}
				});
			}
		});
		t.start();
	}
}
