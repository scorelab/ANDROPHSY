import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class NewCase extends Composite {
	private static Text txtCaseDesc;
	private static Text txtCaseName;
	private static Text txtInvId;
	private Text txtCaseId;
	private Text txtNewAnalyst;
	private Text txtNewInv;
	private Text txtSecDeviceId;
	private Text txtSecDevDesc;
	private Button btnAddDevice;
	private Button btnTransfer;
	private static Table table;
	private static String globalPath;
	private static String caseId;
	private boolean bCaseOpen;
	private TabFolder tabfolder;
	private Button btnSave;

	/**
	 * Create the composite.
	 * @param this
	 * @param style
	 */
	public NewCase(Composite parent, int style) {
		super(parent, style);
		
		tabfolder = (TabFolder) parent.getParent().getParent();
		GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 6;
		gridLayout.horizontalSpacing = 7;
		gridLayout.numColumns = 9;
		this.setLayout(gridLayout);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, false, false, 4, 11);
		gd_table.widthHint = 369;
		table.setLayoutData(gd_table);
		table.setBounds(0, 0, 416, 319);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addListener(SWT.DefaultSelection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				TableItem item[] = table.getSelection();
				for(int i = 0 ; i < item.length ; i++){
					txtCaseId.setText(item[0].getText(0));
					txtCaseName.setText(item[0].getText(1));
					txtCaseDesc.setText(item[0].getText(2));
					txtInvId.setText(item[0].getText(6));
				}
				
			}
		});
		
		TableColumn tblclmnCaseId = new TableColumn(table, SWT.NONE);
		tblclmnCaseId.setWidth(0);
		tblclmnCaseId.setText("Case Id");
		
		TableColumn tblclmnCaseName = new TableColumn(table, SWT.NONE);
		tblclmnCaseName.setWidth(85);
		tblclmnCaseName.setText("Case Name");
		
		TableColumn tblclmnDeviceId = new TableColumn(table, SWT.NONE);
		tblclmnDeviceId.setWidth(80);
		tblclmnDeviceId.setText("Device ID");
		
		TableColumn tblclmCasePath = new TableColumn(table, SWT.NONE);
		tblclmCasePath.setWidth(100);
		tblclmCasePath.setText("Path");
		
		TableColumn tblclmCaseCreated = new TableColumn(table, SWT.NONE);
		tblclmCaseCreated.setWidth(0);
		tblclmCaseCreated.setText("Created");
		new Label(this, SWT.NONE);

		
		
		Label lblCaseId = new Label(this, SWT.NONE);
		lblCaseId.setText("Case ID");
		
		txtCaseId = new Text(this, SWT.BORDER);
		txtCaseId.setEditable(false);
		txtCaseId.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1));
		
		Label label_1 = new Label(this, SWT.NONE);
		GridData gd_label_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_label_1.widthHint = 32;
		label_1.setLayoutData(gd_label_1);
		
		Label lblCaseName = new Label(this, SWT.NONE);
		lblCaseName.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		lblCaseName.setText("Case Name");
		lblCaseName.setBounds(8, 10, 93, 17);
		
		txtCaseName = new Text(this, SWT.BORDER);
		GridData gd_txtCaseName = new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1);
		gd_txtCaseName.widthHint = 120;
		txtCaseName.setLayoutData(gd_txtCaseName);
		txtCaseName.setEnabled(false);
		txtCaseName.setBounds(139, 10, 253, 27);
		new Label(this, SWT.NONE);
		
		Label lblCaseDesc = new Label(this, SWT.NONE);
		lblCaseDesc.setText("Description");
		lblCaseDesc.setBounds(8, 43, 85, 17);
		
		txtCaseDesc = new Text(this, SWT.BORDER | SWT.MULTI);
		GridData gd_txtCaseDesc = new GridData(SWT.FILL, SWT.FILL, false, false, 3, 8);
		gd_txtCaseDesc.widthHint = 81;
		gd_txtCaseDesc.heightHint = 150;
		txtCaseDesc.setLayoutData(gd_txtCaseDesc);
		txtCaseDesc.setEnabled(false);
		txtCaseDesc.setBounds(139, 43, 253, 100);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblInvId = new Label(this, SWT.NONE);
		lblInvId.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false, 1, 1));
		lblInvId.setText("Investigator ID");
		lblInvId.setBounds(8, 149, 108, 17);
		txtInvId = new Text(this, SWT.BORDER);
		txtInvId.setEnabled(false);
		txtInvId.setEditable(false);
		txtInvId.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1));
		
		Button btnRefresh = new Button(this, SWT.NONE);
		GridData gd_btnRefresh = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnRefresh.widthHint = 87;
		btnRefresh.setLayoutData(gd_btnRefresh);
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				LoadCases(new ConnectDb().getConnection());
			}
		});
		btnRefresh.setText("Refresh");
		btnRefresh.setBounds(0, 325, 85, 29);
		
		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				OpenCase();
				txtNewAnalyst.setEditable(true);
				txtNewInv.setEditable(true);
				bCaseOpen = true;
				btnAddDevice.setEnabled(true);
				//extractDeviceInfo();
				createTabs();
			}
		});
		button.setText("Open Case");
		button.setBounds(89, 325, 85, 29);
		new Label(this, SWT.NONE);
		
		Button btnAddDevice_1 = new Button(this, SWT.NONE);
		btnAddDevice_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		btnAddDevice_1.setText("Add Device");
		new Label(this, SWT.NONE);
		
		
		
		Button btnNew = new Button(this, SWT.NONE);
		GridData gd_btnNew = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNew.widthHint = 105;
		btnNew.setLayoutData(gd_btnNew);
		btnNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				enableDisableCase(true);
				txtInvId.setText(AndrospyMain.gb_username);
			}
		});
		btnNew.setBounds(8, 290, 91, 29);
		btnNew.setText("New");
				
		Button btnReset = new Button(this, SWT.NONE);
		GridData gd_btnReset = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnReset.widthHint = 105;
		btnReset.setLayoutData(gd_btnReset);
		btnReset.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				clearCaseData();
			}
		});
		btnReset.setBounds(136, 325, 91, 29);
		btnReset.setText("Reset");
		
		Button btnCancel = new Button(this, SWT.NONE);
		GridData gd_btnCancel = new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1);
		gd_btnCancel.widthHint = 130;
		btnCancel.setLayoutData(gd_btnCancel);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				clearCaseData();
				enableDisableCase(false);
			}
		});
		btnCancel.setBounds(301, 325, 91, 29);
		btnCancel.setText("Cancel");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		btnSave = new Button(this, SWT.NONE);
		GridData gd_btnSave = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnSave.widthHint = 105;
		btnSave.setLayoutData(gd_btnSave);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				InsertCase(new ConnectDb().getConnection());	
				System.out.println("xxx");
				clearCaseData();
				enableDisableCase(false);
			}	
		});
		btnSave.setBounds(8, 325, 91, 29);
		btnSave.setText("Save");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		//new Label(this, SWT.NONE);
		
		Button btnBackUp = new Button(this, SWT.NONE);
		GridData gd_btnBackUp = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnBackUp.widthHint = 92;
		btnBackUp.setLayoutData(gd_btnBackUp);
		btnBackUp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//TODO
				;
			}
		});
		btnBackUp.setBounds(160, 325, 85, 29);
		btnBackUp.setText("Back Up");
		
		Button btnArchiveCase = new Button(this, SWT.NONE);
		btnArchiveCase.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnArchiveCase.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					makeCompress(globalPath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnArchiveCase.setText("Archive Case");
		btnArchiveCase.setBounds(319, 325, 90, 29);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Group grpTransferCase = new Group(this, SWT.NONE);
		GridData gd_grpTransferCase = new GridData(SWT.FILL, SWT.FILL, false, false, 4, 1);
		gd_grpTransferCase.heightHint = 144;
		grpTransferCase.setLayoutData(gd_grpTransferCase);
		grpTransferCase.setText("Transfer Case To");
		grpTransferCase.setBounds(0, 361, 416, 105);
		
		Label lblTransferInvestigator = new Label(grpTransferCase, SWT.NONE);
		lblTransferInvestigator.setBounds(10, 37, 93, 17);
		lblTransferInvestigator.setText("Investigator");
		
		Label lblInvestigator = new Label(grpTransferCase, SWT.NONE);
		lblInvestigator.setBounds(10, 69, 93, 17);
		lblInvestigator.setText("Case Analyst");
		
		txtNewInv = new Text(grpTransferCase, SWT.BORDER);
		txtNewInv.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(bCaseOpen && !((txtNewInv.getText()).equals(null))){
					btnTransfer.setEnabled(true);
				}
				else{
					btnTransfer.setEnabled(false);
				}
			}
		});
		txtNewInv.setEditable(false);
		txtNewInv.setBounds(148, 27, 216, 27);
		
		txtNewAnalyst = new Text(grpTransferCase, SWT.BORDER);
		txtNewAnalyst.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				if(bCaseOpen && !((txtNewAnalyst.getText()).equals(null))){
					btnTransfer.setEnabled(true);
				}
				else{
					btnTransfer.setEnabled(false);
				}
			}
		});
		txtNewAnalyst.setEditable(false);
		txtNewAnalyst.setBounds(148, 59, 216, 27);
		
		btnTransfer = new Button(grpTransferCase, SWT.NONE);
		btnTransfer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {				
				TransferCase(txtNewInv.getText(), txtNewAnalyst.getText());
			}
		});
		btnTransfer.setEnabled(false);
		btnTransfer.setBounds(148, 98, 91, 29);
		btnTransfer.setText("Transfer");
		new Label(this, SWT.NONE);
		
		Group grpAddSecondaryDevice = new Group(this, SWT.NONE);
		grpAddSecondaryDevice.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 4, 1));
		grpAddSecondaryDevice.setText("Add Android Device");
		
		Label lblDeviceId_1 = new Label(grpAddSecondaryDevice, SWT.NONE);
		lblDeviceId_1.setBounds(10, 33, 70, 17);
		lblDeviceId_1.setText("Device ID");
		
		txtSecDeviceId = new Text(grpAddSecondaryDevice, SWT.BORDER);
		txtSecDeviceId.setBounds(110, 23, 241, 27);
		
		Label lblDescription = new Label(grpAddSecondaryDevice, SWT.NONE);
		lblDescription.setBounds(10, 64, 87, 17);
		lblDescription.setText("Description");
		
		txtSecDevDesc = new Text(grpAddSecondaryDevice, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtSecDevDesc.setBounds(110, 56, 241, 64);
		
		btnAddDevice = new Button(grpAddSecondaryDevice, SWT.NONE);
		btnAddDevice.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				int case_id = Integer.parseInt(caseId);
				int dev_id = Integer.parseInt(txtSecDeviceId.getText());
				AddSecodaryDevice(case_id, dev_id, txtSecDevDesc.getText());
			}
		});
		btnAddDevice.setEnabled(false);
		btnAddDevice.setBounds(110, 126, 91, 29);
		btnAddDevice.setText("Add Device");
		
		if(AndrospyMain.gb_UserRole == "ADMIN"){
			btnTransfer.setEnabled(true);
			txtNewInv.setEnabled(true);
			txtNewAnalyst.setEnabled(true);
		}
		else{
			btnTransfer.setEnabled(false);
			txtNewInv.setEnabled(false);
			txtNewAnalyst.setEnabled(false);
		}

	}
	
	
	
	
	protected void extractDeviceInfo() {		
		AdbExecCmd execcmd = new AdbExecCmd();
		execcmd.getDeviceProperty();
	}

	protected void createTabs() {
		int index = -1;
		if(AndrospyMain.gb_UserRole == "ADMIN"){
			index = 3;
		}
		else{
			index = 2;
		}
		TabItem tbtmAcquisition = new TabItem(tabfolder, SWT.NONE, index);
		tbtmAcquisition.setText("Acquisition");
		
		DataCollection dc = new DataCollection(tabfolder, 0);
		tbtmAcquisition.setControl(dc);
		
	}


	protected void AddSecodaryDevice(int case_id, int deviceid, String devicedesc) {
		if((devicedesc.equals(null))){
			MessageBox msgbox = new MessageBox(AndrospyMain.shell, SWT.ICON_ERROR);
			msgbox.setText("Add Device");
			msgbox.setMessage("Please provide short description about target device");
			msgbox.open();
			return;
		}
		ConnectDb condb = new ConnectDb();
		Connection conn = condb.getConnection();
		String msg = "Device Added Successfully";
		try{
			Statement stmt = conn.createStatement();
			String query = "INSERT INTO Androspy_Device VALUES (" + deviceid + ", " + 
						   "'" + devicedesc + "' , " + case_id + ")";
			System.out.println(query);
			int c = stmt.executeUpdate(query);
			if(c == 1){
				MessageBox msgbox = new MessageBox(AndrospyMain.shell, SWT.ICON_INFORMATION);
				msgbox.setText("Add Device");
				msgbox.setMessage(msg);
				msgbox.open();
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	protected void TransferCase(String inv, String analyst) {		
		ConnectDb condb = new ConnectDb();
		Connection conn = condb.getConnection();
		String msg = "Case Successfully Transffered\n\n";
		try {
			Statement stmt = conn.createStatement();
			String query = "UPDATE Androspy_Case SET ";
			if(!inv.equals(null)){
				query += "investigator_id = '" + inv + "'";
				msg += "Investigator: " + inv + "\n";
			}
			else{
				if(!analyst.equals(null)){
					query += "analyst_id = '" + analyst + "'";
					msg += "Case Analyst: " + analyst + "\n";
				}
				else{
					return;
				}
			}
			if(!analyst.equals(null)){
				query += ", analyst_id = '" + analyst + "'";
				msg += "Case Analyst: " + analyst + "\n";
			}
			query += " WHERE case_id = " + caseId;
			System.out.println(query);
			int cnt = stmt.executeUpdate(query);
			System.out.println("#@ " + cnt);
			if(cnt == 1){
				MessageBox msgbox = new MessageBox(AndrospyMain.shell, SWT.ICON_INFORMATION);
				msgbox.setText("Case Transfer");
				msgbox.setMessage(msg);
				msgbox.open();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	protected static void clearCaseData() {		
		txtCaseName.setText("");
		txtCaseDesc.setText("");
		txtInvId.setText("");
	}
	protected static void enableDisableCase(boolean flag) {		
		txtCaseName.setEnabled(flag);
		txtCaseDesc.setEnabled(flag);
	}
	
	protected void InsertCase(Connection con){		
		Statement stmt = null;
		String case_name = txtCaseName.getText();
		String case_desc = txtCaseDesc.getText();
		String query = "INSERT INTO Androspy_Case (case_name, case_desc) VALUES ('" + case_name + "','"
						+ case_desc+ "')";
		try {
			
			stmt = con.createStatement();
			int count = stmt.executeUpdate(query);
			if(count == 1){
				System.out.println("inserted");
			}
			else{
				System.out.println("not inserted");
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			btnSave.setEnabled(false);
		}
	}
	
	protected static void OpenCase(){		
		String query = "";
		String path = "";
		int index = table.getSelectionIndex();
		if(index == -1){
			MessageBox msgbox = new MessageBox(AndrospyMain.shell, SWT.OK);
			msgbox.setText("Error");
			msgbox.setMessage("Please select case");
			msgbox.open();
			return;
		}
		TableItem row = table.getItem(index);
		caseId = row.getText(0);
		System.out.println("set case id "+ caseId);
		int caseCreated = Integer.parseInt(row.getText(4));
		if(caseCreated == 0){
			DirectoryDialog dialog = new DirectoryDialog(AndrospyMain.shell);
			path = dialog.open();
			if(CreateStore(path, caseId)){
				path = path + "/"+caseId;
				query = "UPDATE Androspy_Case SET case_path = '" + path +
										                 "', case_created = 1" +				 
							   " WHERE case_id = " + caseId;
				System.out.println(path);
				System.out.println(query);
				new ConnectDb().UpdateTable(query);
				System.out.println("case storage created");

				//set global variables
				globalPath = path;
			}
			else{
				System.out.println("careation fail");
			}
		}	
		else{
			query = "SELECT case_path FROM Androspy_Case WHERE case_id = " + caseId;
			try {
				Statement stmt = new ConnectDb().getConnection().createStatement();
				ResultSet rs = stmt.executeQuery(query);
				while(rs.next()){
					globalPath = rs.getString("case_path");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		System.out.println("global path" + globalPath);
	}
	
	protected static void LoadCases(Connection con){
		System.out.println(AndrospyMain.gb_username);
		table.clearAll();
		table.setItemCount(0);
		Statement stmt = null;
		String query = "SELECT c.case_id, c.case_name, d.device_id, c.case_path, c.case_created FROM Androspy_Case c, Androspy_Device d " +
					   "WHERE c.case_id = d.case_id AND c.investigator_id = '" + AndrospyMain.gb_username + "'";
		String [] values = new String[5];
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				TableItem row = new TableItem(table, SWT.NONE);
				values[0] = rs.getString("case_id");
				values[1] = rs.getString("case_name");
				values[2] = rs.getString("device_id");
				values[3] = rs.getString("case_path");
				values[4] = rs.getString("case_created");
				row.setText(values);
				System.out.println("Case id" + rs.getString("case_id"));
				System.out.println("Case name" + rs.getString("case_name"));
				System.out.println("device_id" + rs.getString("device_id"));
				System.out.println("Case path" + rs.getString("case_path"));
				System.out.println("Case created" + rs.getString("case_created"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	protected static boolean CreateStore(String root, String caseId){
		String path = root + "//" + caseId;
		File dir = new File(path);
		if(dir.mkdir()){
			System.out.println("directory "+ caseId + " created.");
			File dirAcq = new File(path + "//Acquire");
			File dirAnl = new File(path +"//Analysis");
			if((dirAcq.mkdir()) && (dirAnl.mkdir())){
				return true;
			}
			return false;
		}
		else{
			System.out.println("directory "+ caseId + " creation fail.");
			return false;
		}		
	}
	
	protected void makeCompress(String srcFile) throws IOException {
   		srcFile = "/home/indeewari/Test/A";
   		System.out.println("src: " + srcFile);
   		File outFile = new File(srcFile + ".tar.gz");
		ArrayList<File> list = new ArrayList<File>(1);
		File file = new File(srcFile);
		list.add(file);
		compressFiles(list, outFile);		
	}

	private void compressFiles(ArrayList<File> list, File outFile) throws IOException {
		FileOutputStream fos = new FileOutputStream(outFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);	
		GZIPOutputStream gos = new GZIPOutputStream(bos);
		TarArchiveOutputStream taos = new TarArchiveOutputStream(gos);

		taos.setBigNumberMode(TarArchiveOutputStream.BIGNUMBER_STAR);
		taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
		
		for(File f : list){				
			addFileToCompression(taos, f, "");
		}
		taos.close();
		fos.close();
		//gos.close();
		//bos.close();
	}

	
	private void addFileToCompression(TarArchiveOutputStream taos, File f, String dir) throws IOException {
		System.out.println(f.getName() + " dir : " + dir);
		TarArchiveEntry tae = new TarArchiveEntry(f, dir);		
		taos.putArchiveEntry(tae);
		if(f.isDirectory()){
			/*TarArchiveEntry tae = new TarArchiveEntry(f, dir + "/");		
			taos.putArchiveEntry(tae);*/
			System.out.println("is a directory");
			taos.closeArchiveEntry();
			for(File childFile : f.listFiles()){
				System.out.println("child: " + childFile.getName());
				addFileToCompression(taos, childFile, dir + "/" + childFile.getName());
			}
		}
		else{
			System.out.println("is a file " + f.getName());
			FileInputStream fis = new FileInputStream(f);
			IOUtils.copy(fis, taos);
			System.out.println("file added");
			taos.flush();
			taos.closeArchiveEntry();
		}
	}
	
	private void deleteCaseSore(File parent){
		if(parent.isDirectory()){
			File f[] = parent.listFiles();
			for(int i = 0 ; i < f.length ; i++){
				deleteCaseSore(f[i]);
			}
			if(parent.exists()){
				parent.delete();
			}
		}
		else{
			if(parent.exists())
				parent.delete();
		}
	}
}


