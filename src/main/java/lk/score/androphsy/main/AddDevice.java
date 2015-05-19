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

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author indeewari
 *
 */
public class AddDevice {
	Shell shell;
	CaseTemplate casetemplate;

	public AddDevice(Shell shell, CaseTemplate casetemplate) {
		this.shell = shell;
		this.casetemplate = casetemplate;
	}

	public void run() {
		WizardDialog wizarddlg = new WizardDialog(shell, new AddDeviceWizard(casetemplate));
		wizarddlg.open();
	}
}

class AddDeviceWizard extends Wizard {
	public AddDeviceWizard(CaseTemplate casetemplate) {
		setWindowTitle("Add Secondary Investigation Device");
		DeviceTemplate devicetemplate = new DeviceTemplate();
		addPage(new CaseDetailPage(casetemplate));
		addPage(new ConnectDevice(casetemplate, devicetemplate));
		addPage(new SecondDevicePage(casetemplate, devicetemplate));
	}

	@Override
	public boolean performFinish() {
		return true;
	}
}

class CaseDetailPage extends WizardPage {
	private CaseTemplate casetemplate;
	private String location;
	private Text txtLocation;

	protected CaseDetailPage() {
		super("Case");
	}

	protected CaseDetailPage(CaseTemplate casetemplate) {
		super("Case");
		setTitle("Case Detail");
		setDescription("Please verify case information");
		this.casetemplate = casetemplate;
	}

	@Override
	public void createControl(Composite parent) {
		GridData gd_txt = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_txt.widthHint = 200;

		GridData gd_desc = new GridData(SWT.FILL, SWT.FILL, false, false, 2, 4);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));

		new Label(composite, SWT.NONE).setText("Case ID");
		Text txtCaseId = new Text(composite, SWT.BORDER);
		txtCaseId.setLayoutData(gd_txt);
		txtCaseId.setText(casetemplate.getCase_id() + "");
		txtCaseId.setEditable(false);
		new Label(composite, SWT.NONE);

		new Label(composite, SWT.LEFT).setText("Case Name:");
		Text txtCaseName = new Text(composite, SWT.BORDER);
		txtCaseName.setLayoutData(gd_txt);
		txtCaseName.setText(casetemplate.getCase_name());
		txtCaseName.setEditable(false);
		new Label(composite, SWT.NONE);

		new Label(composite, SWT.LEFT).setText("Case Description:   ");
		Text txtCaseDesc = new Text(composite, SWT.BORDER | SWT.MULTI);
		txtCaseDesc.setLayoutData(gd_desc);
		txtCaseDesc.setText(casetemplate.getCase_desc());
		txtCaseDesc.setEditable(false);
		new Label(composite, SWT.LEFT);
		new Label(composite, SWT.LEFT);
		new Label(composite, SWT.LEFT);

		new Label(composite, SWT.LEFT).setText("Case Location");
		txtLocation = new Text(composite, SWT.BORDER);
		gd_txt.widthHint = 300;
		txtLocation.setLayoutData(gd_txt);
		txtLocation.setText(casetemplate.getCase_path());
		txtLocation.setEditable(false);
		new Label(composite, SWT.LEFT);

		new Label(composite, SWT.LEFT).setText("Investigator ID:");
		Text txtInvIdText = new Text(composite, SWT.BORDER);
		txtInvIdText.setLayoutData(gd_txt);
		txtInvIdText.setText(AndrospyMain.gb_username);
		casetemplate.setInvestigator_id(AndrospyMain.gb_username);
		txtInvIdText.setEditable(false);
		new Label(composite, SWT.NONE);

		new Label(composite, SWT.LEFT).setText("Analyst ID:");
		Text txtAnalystText = new Text(composite, SWT.BORDER);
		txtAnalystText.setLayoutData(gd_txt);
		txtAnalystText.setText(casetemplate.getAnalyst_id());
		txtAnalystText.setEditable(false);
		new Label(composite, SWT.NONE);

		setControl(composite);
	}
}

class SecondDevicePage extends WizardPage {
	private DeviceTemplate devicetemplate;
	private CaseTemplate casetemplate;
	private Text txtManf;
	private Text txtModel;
	private Text txtSerial;
	private Text txtDeviceId;
	private Text txtDeviceDesc;
	private boolean bInsert = false;

	protected SecondDevicePage() {
		super("Add Device");
	}

	protected SecondDevicePage(CaseTemplate casetemplate, DeviceTemplate devicetemplate) {
		super("Add Device");
		setDescription("Enter Device Details");
		this.devicetemplate = devicetemplate;
		this.casetemplate = casetemplate;
	}

	@Override
	public void createControl(Composite parent) {
		System.out.println("@@ " + devicetemplate.getModel());
		GridData gd_txt = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_txt.widthHint = 200;
		GridData gd_grp = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_grp.widthHint = 500;
		GridData gd_desc = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 3);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		new Label(composite, SWT.LEFT).setText("Model:");
		txtModel = new Text(composite, SWT.BORDER);
		txtModel.setEditable(false);
		txtModel.setLayoutData(gd_txt);

		new Label(composite, SWT.LEFT).setText("Manufacturer:");
		txtManf = new Text(composite, SWT.BORDER);
		txtManf.setEditable(false);
		txtManf.setLayoutData(gd_txt);

		new Label(composite, SWT.LEFT).setText("Device Serial:");
		txtSerial = new Text(composite, SWT.BORDER);
		txtSerial.setEditable(false);
		txtSerial.setLayoutData(gd_txt);

		new Label(composite, SWT.LEFT).setText("Device ID:");
		txtDeviceId = new Text(composite, SWT.BORDER);
		txtDeviceId.setLayoutData(gd_txt);

		new Label(composite, SWT.LEFT).setText("Device Description:");
		txtDeviceDesc = new Text(composite, SWT.BORDER | SWT.MULTI);
		gd_desc.widthHint = 350;
		txtDeviceDesc.setLayoutData(gd_desc);

		new Label(composite, SWT.LEFT);
		new Label(composite, SWT.LEFT);
		new Label(composite, SWT.LEFT);
		new Label(composite, SWT.LEFT);
		new Label(composite, SWT.LEFT);
		Button btnCreateCase = new Button(composite, SWT.PUSH);
		btnCreateCase.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnCreateCase.setText("Add Device");
		btnCreateCase.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				AttachDevice();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		setControl(composite);
	}

	protected void AttachDevice() {
		ConnectDb condb = new ConnectDb();
		Connection con = condb.getConnection();
		InserDevice(con);

		getWizard().getContainer().updateButtons();
	}

	private void InserDevice(Connection con) {
		String serial = devicetemplate.getSerialNo();
		int device_id = Integer.parseInt(txtDeviceId.getText());
		int case_id = casetemplate.getCase_id();
		String query =
		               "INSERT INTO Androspy_Device (case_id, device_id, serial_no, device_desc ) " +
		                       "VALUES (" +
		                       case_id +
		                       "," +
		                       device_id +
		                       ", '" +
		                       serial +
		                       "', '" +
		                       txtDeviceDesc.getText() + "')";
		try {
			Statement stmt = con.createStatement();
			int count = stmt.executeUpdate(query);
			if (count == 1) {
				createStorage(case_id, device_id);
				System.out.println("inserted");
			} else {
				System.out.println("not inserted");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createStorage(int case_id, int device_id) {
		File dir = new File(AndrospyMain.gb_CasePath + device_id);
		if (dir.mkdir()) {
			/*
			 * String query = "UPDATE Androspy_Case SET case_path = '" +
			 * dir.getPath() + "'" +
			 * " WHERE case_id = " + case_id + " AND devi"
			 */
		}

	}

	@Override
	public void setVisible(boolean visible) {
		txtManf.setText(devicetemplate.getManufacturer());
		txtModel.setText(devicetemplate.getModel());
		txtSerial.setText(devicetemplate.getSerialNo());
		System.out.println("setVisible " + devicetemplate.getModel());
		super.setVisible(visible);

	}
}
