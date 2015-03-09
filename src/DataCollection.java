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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.SelectionAdapter;

/**
 * @author indeewari
 *
 */
public class DataCollection extends Composite {
	private static AdbExecCmd adbcmd = new AdbExecCmd();
	private static DeviceTemplate devicetemplate = new DeviceTemplate();
	private static Button btnCollectRawData;
	private Button btnRoot;
	private Button btnRestoreDevice;
	private Label label;
	private Label lblStep;
	private static Label lblConsole;
	private Composite composite;
	private Composite cmpProgress;
	private StackLayout stacklayout;
	private Acquisition acquisition;
	private Label lblDataColletionPanel;
	private Root rootcmp;
	private DisconnectDevic disconn;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public DataCollection(Composite parent, int style) {
		super(parent, style);
		GridLayout gridLayout = new GridLayout(7, true);
		setLayout(gridLayout);

		stacklayout = new StackLayout();

		lblDataColletionPanel = new Label(this, SWT.NONE);
		lblDataColletionPanel.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblDataColletionPanel.setLayoutData(new GridData(SWT.CENTER, SWT.FILL,
				false, false, 7, 1));
		lblDataColletionPanel.setText("Data Colletion Panel");

		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Label lblStep1 = new Label(this, SWT.CENTER);
		lblStep1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		lblStep1.setText("Step 1");
		new Label(this, SWT.NONE);
		Label lblStep2 = new Label(this, SWT.NONE);
		lblStep2.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false));
		lblStep2.setText("Step 2");
		new Label(this, SWT.NONE);
		Label lblStep3 = new Label(this, SWT.NONE);
		lblStep3.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false));
		lblStep3.setText("Step 3");
		new Label(this, SWT.NONE);
		lblStep = new Label(this, SWT.NONE);
		lblStep.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false));
		lblStep.setText("Step 4");

		Button btnConnectDeviceAnd = new Button(this, SWT.WRAP);
		GridData gd_btnConnectDeviceAnd = new GridData(SWT.FILL, SWT.CENTER,
				false, false, 1, 1);
		gd_btnConnectDeviceAnd.widthHint = 62;
		gd_btnConnectDeviceAnd.heightHint = 83;
		btnConnectDeviceAnd.setLayoutData(gd_btnConnectDeviceAnd);
		btnConnectDeviceAnd.setText("Connect Device");
		btnConnectDeviceAnd.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ConnectDevice();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		Label lblArrow2 = new Label(this, SWT.NONE);
		lblArrow2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false));
		lblArrow2.setText(">>");
		lblArrow2.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));

		btnRoot = new Button(this, SWT.WRAP);
		btnRoot.setEnabled(false);
		btnRoot.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1,
				1));
		btnRoot.setText("Root Device");
		btnRoot.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				stacklayout.topControl = rootcmp;
				composite.layout();
				RootDevice();

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		Label lblArrow1 = new Label(this, SWT.NONE);
		lblArrow1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false));
		lblArrow1.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));
		lblArrow1.setText(">>");

		btnCollectRawData = new Button(this, SWT.WRAP);
		btnCollectRawData.setEnabled(false);
		GridData gd_btnCollectRawData = new GridData(SWT.FILL, SWT.FILL, false,
				false);
		gd_btnCollectRawData.widthHint = 122;
		btnCollectRawData.setLayoutData(gd_btnCollectRawData);
		btnCollectRawData.setText("Collect Raw Data");
		btnCollectRawData.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				stacklayout.topControl = acquisition;
				composite.layout();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		label.setText(">>");
		label.setFont(SWTResourceManager.getFont("Ubuntu", 20, SWT.NORMAL));

		btnRestoreDevice = new Button(this, SWT.WRAP);
		btnRestoreDevice.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				stacklayout.topControl = disconn;
				composite.layout();
				Disconnect();
			}
		});
		
		GridData gd_btnRestoreDevice = new GridData(SWT.FILL, SWT.FILL, false,
				false);
		gd_btnRestoreDevice.widthHint = 128;
		btnRestoreDevice.setLayoutData(gd_btnRestoreDevice);
		btnRestoreDevice.setText("Disconnect Device");

		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		lblConsole = new Label(this, SWT.WRAP);
		lblConsole.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
				2, 1));

		composite = new Composite(this, SWT.NONE);
		composite.setLayout(stacklayout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
				5, 1));

		acquisition = new Acquisition(composite, SWT.NONE);
		rootcmp = new Root(composite, SWT.NONE);
		disconn = new DisconnectDevic(composite, SWT.NONE);

		/*
		 * stacklayout.topControl = acquisition; composite.layout();
		 */
		cmpProgress = new Composite(this, SWT.NONE);
		GridData gd_composite_1 = new GridData(SWT.FILL, SWT.FILL, false,
				false, 7, 1);
		gd_composite_1.heightHint = 21;
		cmpProgress.setLayoutData(gd_composite_1);

	}

	protected void Disconnect() {
		disconn.resetProgressPane();
		if(adbcmd.isRooted()){
			disconn.showProgress("\nStart restore device process...\n\n Re-patch and restore adb deamon...\n\n");
			adbcmd.removeKernelSecure();
			disconn.showProgress("Unroot..\n\n Uninstall supersu app\n\n");
			for(int i=0; i<1000; i++){
				;
			}
			adbcmd.unroot();
			for(int i=0; i<1000; i++){
				;
			}
			disconn.showProgress("Finish device restore...\n\n");
		}
		disconn.showProgress("\nPlease disconnect device before further process due to safety methods...\n\n");
	}

	protected void RootDevice() {
		if (adbcmd.isRooted()) {
			btnCollectRawData.setEnabled(true);

		} else {
			btnCollectRawData.setEnabled(false);
		}
	}

	protected void ConnectDevice() {
		String msg = "";
		if (adbcmd.isDeviceConnected()) {
			msg = "Device Connected Successfully.\n";
			if (!adbcmd.getDeviceBasicInfo(devicetemplate)) {
				msg = "Device Not Connected.\n "
						+ "Please check replug the device to workstation\n";
				
				btnRoot.setEnabled(false);
				btnCollectRawData.setEnabled(false);
			} else {
				msg += "\n"
						+ "Model:\t" + devicetemplate.getModel() + "\n"
						+ "Manufacturer:\t" + devicetemplate.getManufacturer()
						+ "\n\n";
				/*
				 * add os version, build version, kernel version information
				 */
				btnRoot.setEnabled(true);
				btnCollectRawData.setEnabled(false);
				
				if(adbcmd.isRooted()){ 
					msg += "\n Device is already rooted. \nGo to step 3";
					btnCollectRawData.setEnabled(true); 
				}else{ 
					msg += "\n Device is not rooted. \nGo to step 2";
					btnCollectRawData.setEnabled(false); 
				}				
			}

		} else {
			msg = "Device Not Connected.\n "
					+ "Please check replug the device to workstation\n";
			btnRoot.setEnabled(false);
			btnCollectRawData.setEnabled(false);
		}

		lblConsole.setText(msg);
	}

	public static void postroot() {
		if(adbcmd.isDeviceConnected()){
			if(adbcmd.isRooted()){
				btnCollectRawData.setEnabled(true);
			} 
			else{
				btnCollectRawData.setEnabled(false);
			}
			String msgtext = "Device is connected \n";
			msgtext += "\n"
					+ "Model:\t" + devicetemplate.getModel() + "\n"
					+ "Manufacturer:\t" + devicetemplate.getManufacturer()
					+ "\n\n";
			msgtext += "Device is rooted succefully...\nContinue to step 3: Data Collection\n";
			lblConsole.setText(msgtext);
		}
	}
}
