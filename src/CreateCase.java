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

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


/**
 * @author indeewari
 *
 */
public class CreateCase {
	Shell shell;
	
	public CreateCase(Shell shell){
		this.shell = shell;
	}
	
	public void run(){
		WizardDialog wizarddlg = new WizardDialog(shell, new CreateCaseWizard());
		wizarddlg.open();
	}
}

class CaseTemplate{
	private int case_id;
	private String case_name;
	private String case_desc;
	private String case_path;
	private String analyst_id;
	private String investigator_id;
	
	public int getCase_id(){
		return case_id;
	}
	
	public void set_Case_id(int case_id) {
		this.case_id = case_id;
	}
	public String getCase_name() {
		return case_name;
	}
	
	public void setCase_name(String case_name) {
		this.case_name = case_name;
	}
	
	public String getCase_desc() {
		return case_desc;
	}
	
	public void setCase_desc(String case_desc) {
		this.case_desc = case_desc;
	}
	
	public String getAnalyst_id() {
		return analyst_id;
	}
	
	public void setAnalyst_id(String analyst_id) {
		this.analyst_id = analyst_id;
	}
	
	public String getInvestigator_id() {
		return investigator_id;
	}
	
	public void setInvestigator_id(String investigator_id) {
		this.investigator_id = investigator_id;
	}

	public String getCase_path() {
		return case_path;
	}

	public void setCase_path(String case_path) {
		this.case_path = case_path;
	}
}




class CreateCaseWizard extends Wizard{
	public CreateCaseWizard(){
		setWindowTitle("Register New Case Wizard");	
		CaseTemplate casetemplate = new CaseTemplate();
		DeviceTemplate devicetemplate = new DeviceTemplate();
		addPage(new CasePage(casetemplate));
		addPage(new ConnectDevice(casetemplate, devicetemplate));
		addPage(new DevicePage(casetemplate, devicetemplate));
		addPage(new LastPage(casetemplate, devicetemplate));
	}

	@Override
	public boolean performFinish() {
		return true;
	}
	
}


class CasePage extends WizardPage{
	private CaseTemplate casetemplate;
	private String location;
	private Text txtLocation;
	protected CasePage(){
		super("Case");
	}
	
	protected CasePage(CaseTemplate casetemplate){
		super("Case");
		setTitle("Create New Case");
		setDescription("Enter Case details");
		this.casetemplate = casetemplate;
	}

	@Override
	public void createControl(Composite parent) {
		ModifyListener modifyListener = new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				String txt = ((Text)arg0.widget).getText();
				System.out.println(txt);
				
			}
		}; 
		
		GridData gd_txt = new GridData(SWT.FILL, SWT.FILL, false, false, 1,1);
		gd_txt.widthHint = 200;
		
		GridData gd_desc = new GridData(SWT.FILL, SWT.FILL, false, false, 2,4);
		//gd_desc.widthHint = ;
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(3, false));
				 
		new Label(composite, SWT.NONE).setText("Case ID");
		Text txtCaseId = new Text(composite, SWT.BORDER);
		txtCaseId.setLayoutData(gd_txt);
		txtCaseId.setEditable(false);
		new Label(composite, SWT.NONE);
		
		new Label(composite, SWT.LEFT).setText("Case Name:");
		Text txtCaseName = new Text(composite, SWT.BORDER);
		txtCaseName.setLayoutData(gd_txt);
		txtCaseName.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				casetemplate.setCase_name(((Text)arg0.widget).getText());
				getWizard().getContainer().updateButtons();
			}
		});
		new Label(composite, SWT.NONE);
		
		new Label(composite, SWT.LEFT).setText("Case Description:   ");
		Text txtCaseDesc = new Text(composite, SWT.BORDER | SWT.MULTI);
		txtCaseDesc.setLayoutData(gd_desc);
		txtCaseDesc.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				casetemplate.setCase_desc(((Text)arg0.widget).getText());				
			}
		});
		new Label(composite, SWT.LEFT);
		new Label(composite, SWT.LEFT);
		new Label(composite, SWT.LEFT);
		
		new Label(composite, SWT.LEFT).setText("Case Location");
		txtLocation = new Text(composite, SWT.BORDER);
		gd_txt.widthHint = 300;
		txtLocation.setLayoutData(gd_txt);
		Button btnloc = new Button(composite, SWT.PUSH);
		btnloc.setText("...");
		btnloc.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DirectoryDialog dialog = new DirectoryDialog(getShell());
				location = dialog.open();
				txtLocation.setText(location);
				casetemplate.setCase_path(location);
				System.out.println("location " + location);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
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
		txtAnalystText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent arg0) {
				casetemplate.setAnalyst_id(((Text)arg0.widget).getText());				
			}
		});
		new Label(composite, SWT.NONE);
				
		setControl(composite);
		
	}

	public IWizardPage getNextPage(){
		System.out.println("...... " + casetemplate.getCase_name());
		if(casetemplate.getCase_name() != null){
			IWizardPage page = super.getNextPage();
			return page;
		}
		return null;		
	}
}


class ConnectDevice extends WizardPage{

	private Label lblStatus;
	private boolean bnext = false;
	private CaseTemplate casetemplate; 
	private DeviceTemplate devicetemplate;
	AdbExecCmd cmd = new AdbExecCmd();

	protected ConnectDevice(){
		super("Connect Device");
	}
	
	
	protected ConnectDevice(CaseTemplate casetemplate, DeviceTemplate devicetemplate) {
		super("Connect Device");
		setTitle("Primary Investigation Device Details");
		setDescription("Please connect device to workstation and click on Connect Device");
		this.casetemplate 	= casetemplate;
		this.devicetemplate = devicetemplate;
	}

	@Override
	public void createControl(Composite parent) {	
		
		Composite cmpFirstPage = new Composite(parent, SWT.NONE);
		cmpFirstPage.setLayout(new GridLayout(2, true));
						
		Label label = new Label(cmpFirstPage, SWT.CENTER);
		label.setText("Please connect your device");
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2,1));
		
		Button btnConnect = new Button(cmpFirstPage, SWT.CENTER);
		btnConnect.setText("Connect Device");
		GridData gd_btnConnect = new GridData(SWT.CENTER, SWT.FILL, false, false, 2,1);
		gd_btnConnect.heightHint = 100;
		btnConnect.setLayoutData(gd_btnConnect);
		btnConnect.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Connect(devicetemplate);				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		new Label(cmpFirstPage, SWT.NONE);
		lblStatus = new Label(cmpFirstPage, SWT.CENTER);
		lblStatus.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2,1));
		setControl(cmpFirstPage);
	}
	
	protected void Connect(DeviceTemplate devicetemplate) {
		AdbExecCmd cmd = new AdbExecCmd();
		if(cmd.isDeviceConnected()){
			System.out.println("### connected");
			lblStatus.setText("Device Connected.");
			bnext = true;
			getDeviceInfo(devicetemplate);
		}
		else{
			System.out.println("### connected");
			lblStatus.setText("Device Connection Fails...\n Wait ...\n");
			cmd.killAdb();
			lblStatus.setText("Retrying...");
			cmd.startAdb();
			if(cmd.isDeviceConnected()){
				lblStatus.setText("Device Connected");
				bnext = true;
				getDeviceInfo(devicetemplate);
			}
			else{
				lblStatus.setText("Device Connection Failed.\nReplug your USB and Continue...");
				bnext = false;
			}
		}
		System.out.println("bnext 0 " + bnext);
		getWizard().getContainer().updateButtons();
	}

	private void getDeviceInfo(DeviceTemplate devicetemplate) {
		if(!cmd.getDeviceBasicInfo(devicetemplate)){		
			bnext = false;			
			getWizard().getContainer().updateButtons();
			lblStatus.setText("Device Connection Fail \n Check USB connection and retry");
		}
	}

	public IWizardPage getNextPage(){
		System.out.println("bnext " + bnext);
		if(bnext && cmd.isDeviceConnected()){
			IWizardPage page = super.getNextPage();
			System.out.println("### " + page.getName());
			return page;
		}
		return null;		
	}
}


class DevicePage extends WizardPage{
	private DeviceTemplate devicetemplate;
	private CaseTemplate casetemplate;
	private Text txtManf;
	private Text txtModel;
	private Text txtSerial;
	private Text txtDeviceId;
	private Text txtDeviceDesc;
	private boolean bInsert = false;
	
	protected DevicePage(){
		super("Add Device");
	}
	
	
	protected DevicePage(CaseTemplate casetemplate, DeviceTemplate devicetemplate){
		super("Add Device");
		setDescription("Enter Device Details");
		this.devicetemplate = devicetemplate;
		this.casetemplate = casetemplate;
	}

	@Override
	public void createControl(Composite parent) {
		System.out.println("@@ " + devicetemplate.getModel());
		GridData gd_txt = new GridData(SWT.LEFT, SWT.FILL, false, false, 1,1);
		gd_txt.widthHint = 200;
		GridData gd_grp = new GridData(SWT.FILL, SWT.FILL, false, false, 1,1);
		gd_grp.widthHint = 500;
		GridData gd_desc = new GridData(SWT.FILL, SWT.FILL, false, false, 1,3);
		
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
		btnCreateCase.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1,1));
		btnCreateCase.setText("Create New Case");
		btnCreateCase.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				CreateCase();			
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		setControl(composite);
	}
	 
	
	protected void CreateCase() {
		ConnectDb condb = new ConnectDb();
		Connection con = condb.getConnection();
		InsertCase(con);
		if(bInsert){
			if(CreateStore(casetemplate.getCase_path(), casetemplate.getCase_id())){
				bInsert = true;
			}
			else{
				bInsert = false;
			}
		}
		
		getWizard().getContainer().updateButtons();
	}


	//save case detail into db
	protected void InsertCase(Connection con){		
		Statement stmt		= null;
		String case_name	= casetemplate.getCase_name();
		String case_desc	= casetemplate.getCase_desc();
		String inv_id		= casetemplate.getInvestigator_id();
		String analyst_id	= casetemplate.getAnalyst_id();
		String query = "INSERT INTO Androspy_Case (case_name, case_desc, investigator_id, analyst_id)" +
				       " VALUES ('" + case_name + "','"	+ case_desc+ "', '" + inv_id + "', '" + analyst_id + "')";
		try {
			
			stmt = con.createStatement();
			int count = stmt.executeUpdate(query);
			if(count == 1){
				System.out.println("inserted");
				InserDevice(con);
				int case_id = getCaseId(con);
				casetemplate.set_Case_id(case_id);
			}
			else{
				bInsert  = false;
				System.out.println("not inserted");
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void InserDevice(Connection con) {
		String serial = devicetemplate.getSerialNo();
		int device_id = Integer.parseInt(txtDeviceId.getText());
		devicetemplate.setDeviceId(device_id);
		int case_id = getCaseId(con);
		String query = "INSERT INTO Androspy_Device (case_id, device_id, serial_no, device_desc ) " +
				       "VALUES (" + case_id + "," + device_id + ", '" + serial + "', '" + txtDeviceDesc.getText() + "')";
		try {
			Statement stmt = con.createStatement();
			int count = stmt.executeUpdate(query);
			if(count == 1){
				bInsert = true;
				System.out.println("inserted");				
			}
			else{
				bInsert = false;
				System.out.println("not inserted");
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	private int getCaseId(Connection con) {
		String query = "SELECT LAST_INSERT_ID()";
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()){
				System.out.println(rs.getInt(1));
				return rs.getInt(1);
			}
			return -1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}	
		
	}

	
	protected boolean CreateStore(String root, int caseId){
		String path = root + "/" + caseId;
		File dir = new File(path);
		if(dir.mkdir()){
			System.out.println("directory "+ caseId + " created.");
			File dirAcq = new File(path + "//Acquire");
			File dirAnl = new File(path +"//Analysis");
			if((dirAcq.mkdir()) && (dirAnl.mkdir()) && (updateCasePath(path, caseId))){
				casetemplate.setCase_path(path);
				return true;
			}
			return false;
		}
		else{
			System.out.println("directory "+ caseId + " creation fail.");
			return false;
		}		
	}
	
	
	private boolean updateCasePath(String path, int caseId) {
		ConnectDb condb = new ConnectDb();
		Connection con = condb.getConnection();
		String query = "UPDATE Androspy_Case SET case_path = '" + path + "/', case_created = 1 WHERE case_id = " + caseId;
		try {
			Statement stmt = con.createStatement();
			int count = stmt.executeUpdate(query);
			if(count == 1){
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}		
	}


	public IWizardPage getNextPage(){
		if(bInsert){
			IWizardPage page = super.getNextPage();
			return page;
		}
		return null;		
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


class LastPage extends WizardPage{
	private CaseTemplate casetemplate;
	private DeviceTemplate devicetemplate;
	private Label lblmsg;
	protected LastPage(){
		super("Last");
	}
	
	protected LastPage(CaseTemplate casetemplate, DeviceTemplate devicetemplate){
		super("Last");
		this.casetemplate = casetemplate;
		this.devicetemplate = devicetemplate;
	}

	@Override
	public void createControl(Composite parent) {
		GridData gd_txt = new GridData(SWT.LEFT, SWT.FILL, false, false, 1,1);
		gd_txt.widthHint = 200;
		
		GridData gd_desc = new GridData(SWT.FILL, SWT.FILL, false, false, 1,4);
		gd_desc.widthHint = 375;
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(1, false));
				 
		lblmsg = new Label(composite, SWT.NONE);
		lblmsg.setText("");
		Button btnchk = new Button(composite, SWT.CHECK);
		btnchk.setText("Open this case for investigation");
		btnchk.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(((Button)arg0.widget).getSelection()){
					OpenCase();
				}				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				;
			}
		});
		setControl(composite);
		
	
	}

	protected void OpenCase() {
		System.out.println("Open Case");
		AndrospyMain.gb_CasePath = casetemplate.getCase_path();
		AndrospyMain.gb_CaseId 	 = casetemplate.getCase_id();
		AndrospyMain.gb_DeviceId = devicetemplate.getDeviceId();
		System.out.println("global path " + AndrospyMain.gb_CasePath);		
		Androspy.Creatabs();
	}

	@Override
	public void setVisible(boolean visible) {
		int case_id = casetemplate.getCase_id();
		
		setTitle("Case Created Successfully");
		setMessage("Case Id: " + casetemplate.getCase_id());
		
		System.out.println("new case id last page " + case_id);
		if(case_id != -1){
			lblmsg.setText("Case Created Successfully: " + case_id);
		}
		super.setVisible(visible);
		getWizard().getContainer().updateMessage();
	}
	
	
}
