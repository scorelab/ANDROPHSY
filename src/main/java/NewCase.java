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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
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
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * @author indeewari
 *
 */
public class NewCase extends Composite {
	private static Text txtCaseDesc;
	private static Text txtCaseName;
	private static Text txtInvId;
	private Text txtCaseId;
	private static Table table;
	private static String caseId;
	private boolean bCaseOpen;
	private TabFolder tabfolder;
	private Text txtLocation;
	private Text txtDeviceId;
	private Text txtAnalystId;
	private CaseTemplate casetemplate;

	/**
	 * Create the composite.
	 * @param this
	 * @param style
	 */
	public NewCase(Composite parent, int style) {
		super(parent, style);

		casetemplate = new CaseTemplate();
		
		tabfolder = (TabFolder) parent.getParent().getParent();
		GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 6;
		gridLayout.horizontalSpacing = 7;
		gridLayout.numColumns = 6;
		this.setLayout(gridLayout);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Label lblOpenAndManage = new Label(this, SWT.NONE);
		lblOpenAndManage.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblOpenAndManage.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 6, 1));
		lblOpenAndManage.setText("Open and Manage Existing Case");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, false, false, 6, 1);
		gd_table.widthHint = 623;
		gd_table.heightHint = 211;
		table.setLayoutData(gd_table);
		table.setBounds(0, 0, 416, 319);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if(table.getSelectionCount()> 0){
					TableItem item[] = table.getSelection();
					for(int i = 0 ; i < item.length ; i++){
						txtCaseId.setText(item[0].getText(0));
						txtCaseName.setText(item[0].getText(1));
						txtCaseDesc.setText(item[0].getText(2).equals("null")?"":item[0].getText(2));
						txtLocation.setText(item[0].getText(3));
						txtDeviceId.setText(item[0].getText(5));					
						txtInvId.setText(item[0].getText(6));
						txtAnalystId.setText(item[0].getText(7).equals("null")?"":item[0].getText(7));
						
						casetemplate.set_Case_id(Integer.parseInt(item[0].getText(0)));
						casetemplate.setCase_name(item[0].getText(1));
						casetemplate.setCase_desc(item[0].getText(2).equals("null")?"":item[0].getText(2));
						casetemplate.setCase_path(item[0].getText(3));
						casetemplate.setInvestigator_id(item[0].getText(6));
						casetemplate.setAnalyst_id(item[0].getText(7).equals("null")?"":item[0].getText(7));
					}
				}
				else{
					clearCaseData();
				}
			}
		});

		TableColumn tblclmnCaseId = new TableColumn(table, SWT.NONE);
		tblclmnCaseId.setWidth(0);
		tblclmnCaseId.setText("Case Id");

		TableColumn tblclmnCaseName = new TableColumn(table, SWT.NONE);
		tblclmnCaseName.setWidth(90);
		tblclmnCaseName.setText("Case Name");

		TableColumn tblclmnCaseDesc = new TableColumn(table, SWT.NONE);
		tblclmnCaseDesc.setWidth(0);
		tblclmnCaseDesc.setText("Case Desc");

		TableColumn tblclmCasePath = new TableColumn(table, SWT.NONE);
		tblclmCasePath.setWidth(244);
		tblclmCasePath.setText("Path");

		TableColumn tblclmCaseCreated = new TableColumn(table, SWT.NONE);
		tblclmCaseCreated.setWidth(0);
		tblclmCaseCreated.setText("Created");

		TableColumn tblclmnDeviceId = new TableColumn(table, SWT.NONE);
		tblclmnDeviceId.setWidth(114);
		tblclmnDeviceId.setText("Device ID");

		TableColumn tblclmnInvId = new TableColumn(table, SWT.NONE);
		tblclmnInvId.setWidth(0);
		tblclmnInvId.setText("Investigator ID");

		TableColumn tblclmnAnalystId = new TableColumn(table, SWT.NONE);
		tblclmnAnalystId.setWidth(0);
		tblclmnAnalystId.setText("Analyst ID");		

		Button btnRefresh = new Button(this, SWT.NONE);
		GridData gd_btnRefresh = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_btnRefresh.widthHint = 100;
		btnRefresh.setLayoutData(gd_btnRefresh);
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				PopulateCases(new ConnectDb().getConnection());
			}
		});
		btnRefresh.setText("Refresh");
		btnRefresh.setBounds(0, 325, 85, 29);

		Button button = new Button(this, SWT.NONE);
		GridData gd_button = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_button.widthHint = 83;
		button.setLayoutData(gd_button);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				OpenCase();
				bCaseOpen = true;
				
				Runnable runnable = new Runnable() {
					
					@Override
					public void run() {
						File file = new File(AndrospyMain.gb_CasePath + "Analysis/Quick_Extract");
						if(file.exists()){
							System.out.println("exist");
							QuickView.populateData();
							AppList.populateData();
							BasicInfo.populateData();
							SqliteViwer.initTree(AndrospyMain.gb_CasePath + "Acquire/data/data");
							//SqliteViwer.getDbList(AndrospyMain.gb_CasePath + "Acquire/data/data", ".db");
							FileTreeViewer.initTree(AndrospyMain.gb_CasePath + "Acquire/data");
							ReportGUITop.initTree();
							RecoverData.initTree();
							AndrospyLog.getLog(AndrospyMain.gb_username, AndrospyMain.gb_CaseId, AndrospyMain.gb_DeviceId);
						}
						
					}
				};
				BusyIndicator.showWhile(AndrospyMain.shell.getDisplay(), runnable);				
			}
		});
		button.setText("Open Case");
		button.setBounds(89, 325, 85, 29);

		Button btnAddDevice = new Button(this, SWT.NONE);
		btnAddDevice.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnAddDevice.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				new AddDevice(getShell(), casetemplate).run();
			}
		});
		btnAddDevice.setText("Add Device");
		new Label(this, SWT.NONE);

		Button btnBackUp = new Button(this, SWT.NONE);
		GridData gd_btnBackUp = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_btnBackUp.widthHint = 101;
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
		btnArchiveCase.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnArchiveCase.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					makeCompress(AndrospyMain.gb_CasePath);
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
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);



		Label lblCaseId = new Label(this, SWT.NONE);
		lblCaseId.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblCaseId.setText("Case ID");

		txtCaseId = new Text(this, SWT.BORDER);
		txtCaseId.setEditable(false);
		txtCaseId.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Label lblCaseName = new Label(this, SWT.NONE);
		lblCaseName.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblCaseName.setText("Case Name");
		lblCaseName.setBounds(8, 10, 93, 17);

		txtCaseName = new Text(this, SWT.BORDER);
		txtCaseName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		txtCaseName.setEditable(false);
		txtCaseName.setBounds(139, 10, 253, 27);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Label lblCaseDesc = new Label(this, SWT.NONE);
		lblCaseDesc.setText("Description");
		lblCaseDesc.setBounds(8, 43, 85, 17);

		txtCaseDesc = new Text(this, SWT.BORDER | SWT.MULTI);
		txtCaseDesc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3, 3));
		txtCaseDesc.setEditable(false);
		txtCaseDesc.setBounds(139, 43, 253, 100);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Label lblCasePath = new Label(this, SWT.NONE);
		lblCasePath.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblCasePath.setText("Case Location");

		txtLocation = new Text(this, SWT.BORDER);
		GridData gd_txtLocation = new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1);
		gd_txtLocation.widthHint = 313;
		txtLocation.setLayoutData(gd_txtLocation);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Label lblDeviceId = new Label(this, SWT.NONE);
		GridData gd_lblDeviceId = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_lblDeviceId.widthHint = 106;
		lblDeviceId.setLayoutData(gd_lblDeviceId);
		lblDeviceId.setText("Device ID");

		txtDeviceId = new Text(this, SWT.BORDER);
		GridData gd_txtDeviceId = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_txtDeviceId.widthHint = 92;
		txtDeviceId.setLayoutData(gd_txtDeviceId);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Label lblInvId = new Label(this, SWT.NONE);
		GridData gd_lblInvId = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_lblInvId.widthHint = 102;
		lblInvId.setLayoutData(gd_lblInvId);
		lblInvId.setText("Investigator ID");
		lblInvId.setBounds(8, 149, 108, 17);
		txtInvId = new Text(this, SWT.BORDER);
		txtInvId.setEditable(false);
		GridData gd_txtInvId = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_txtInvId.widthHint = 95;
		txtInvId.setLayoutData(gd_txtInvId);

		Label lblAnalystId = new Label(this, SWT.NONE);
		GridData gd_lblAnalystId = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_lblAnalystId.widthHint = 86;
		lblAnalystId.setLayoutData(gd_lblAnalystId);
		lblAnalystId.setText("Analyst ID");

		txtAnalystId = new Text(this, SWT.BORDER);
		txtAnalystId.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
	}




	protected void extractDeviceInfo() {		
		AdbExecCmd execcmd = new AdbExecCmd();
		execcmd.getDeviceProperty(new DeviceTemplate());
	}

	protected void createTabs() {
		int index = -1;
		if(AndrospyMain.gb_UserRole == "ADMIN"){
			index = 3;
		}
		else{
			index = 2;
		}
		TabItem tbtprevAcq = tabfolder.getItem(index);
		System.out.println(tbtprevAcq.getText());
		if(tbtprevAcq.getText() == "Acquisition"){
			tbtprevAcq.dispose();
		}
		//
		
		TabItem tbtmAcquisition = new TabItem(tabfolder, SWT.NONE, index);
		tbtmAcquisition.setText("Acquisition");

		AcquisitionTop actop = new AcquisitionTop(tabfolder, SWT.NONE);
		tbtmAcquisition.setControl(actop);

	}


	protected void AddSecodaryDevice(int case_id, int deviceid, String devicedesc) {
		if((devicedesc.equals(null))){
			MessageBox msgbox = new MessageBox(getShell(), SWT.ICON_ERROR);
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
				//log
				String stime = new AdbExecCmd().getCurrenttime();
				AndrospyLog.Logdata(stime, "Added secondary device " + deviceid + " to case " + case_id);
				MessageBox msgbox = new MessageBox(getShell(), SWT.ICON_INFORMATION);
				msgbox.setText("Add Device");
				msgbox.setMessage(msg);
				msgbox.open();
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	protected void clearCaseData() {	
		txtCaseId.setText("");
		txtCaseName.setText("");
		txtCaseDesc.setText("");
		txtLocation.setText("");
		txtDeviceId.setText("");
		txtInvId.setText("");
		txtAnalystId.setText("");
	}
	protected static void enableDisableCase(boolean flag) {		
		txtCaseName.setEnabled(flag);
		txtCaseDesc.setEnabled(flag);
	}

	protected void OpenCase(){	
		System.out.println("open case");
		String query = "";
		String path = "";
		int index = table.getSelectionIndex();
		if(index == -1){
			MessageBox msgbox = new MessageBox(getShell(), SWT.OK);
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
			DirectoryDialog dialog = new DirectoryDialog(getShell());
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
				AndrospyMain.gb_CaseId 	 = Integer.parseInt(caseId);
				AndrospyMain.gb_DeviceId = Integer.parseInt(row.getText(5));
				AndrospyMain.gb_CasePath = path;
				createTabs();
				
				//log
				String stime = new AdbExecCmd().getCurrenttime();
				AndrospyLog.Logdata(stime, "Open case id " + caseId);
			}
			else{
				System.out.println("careation fail");
			}
		}	
		else{
			AndrospyMain.gb_CaseId 	 = Integer.parseInt(caseId);
			AndrospyMain.gb_DeviceId = Integer.parseInt(row.getText(5));
			AndrospyMain.gb_CasePath = row.getText(3);
			createTabs();
		}
		System.out.println("global path" + AndrospyMain.gb_CasePath + 
						"global case " + AndrospyMain.gb_CaseId + 
						"global device " + AndrospyMain.gb_DeviceId);
	}

	protected void PopulateCases(Connection con){
		System.out.println(AndrospyMain.gb_username);
		clearCaseData();
		table.clearAll();
		table.setItemCount(0);
		Statement stmt = null;
		String query = "SELECT c.*, d.device_id FROM Androspy_Case c, Androspy_Device d " +
				"WHERE c.case_id = d.case_id AND c.investigator_id = '" + AndrospyMain.gb_username + "'";
		String [] values = new String[8];
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				TableItem row = new TableItem(table, SWT.NONE);
				values[0] = rs.getString("case_id");
				values[1] = rs.getString("case_name");
				values[2] = rs.getString("case_desc");				
				values[3] = rs.getString("case_path");
				values[4] = rs.getString("case_created");
				values[5] = rs.getString("device_id");
				values[6] = rs.getString("investigator_id");
				values[7] = rs.getString("analyst_id");
				row.setText(values);
				System.out.println("Case id" + rs.getString("case_id") + "/"+rs.getString("analyst_id"));
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
		
		//log
		String stime = new AdbExecCmd().getCurrenttime();
		AndrospyLog.Logdata(stime, "Case folders deleted " + AndrospyMain.gb_CaseId);
	}
}


