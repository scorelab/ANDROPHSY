import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

public class Androspy extends Composite {
	
	private static Text txtAcqConsole;
	private static Text text;
	private Text txtSearch;
	private Text txtConsole;
	private TabItem tbtmAcquisition;
	private TabItem tbtmAnalysis;
	private TabItem tabItem;
	private TabItem tbtmReport;
	public static String caseId;
	static String globalPath;
	private static Table table;
	private static AdbExecCmd adbcmd;
	private RegisterUsers reguser;
	private Text text_1;
	private Text text_2;
	//final private int shellWidth = AndrospyMain.shell.getBounds().width; 
	//final private int shellHeight = AndrospyMain.shell.getBounds().height;
	
	public Androspy(Composite parent, int arg1, String role) {
		super(parent, SWT.NONE);
		//this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		adbcmd = new AdbExecCmd();
		setLayout(new GridLayout(1, false));
			
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabFolder.setBounds(0, 0, 831, 400);
		tabFolder.setLayout(new GridLayout());
		
		TabItem tbtProfile = new TabItem(tabFolder, SWT.NONE);
		tbtProfile.setText("User Profile");
		
		Composite cmpProf = new Composite(tabFolder, SWT.NONE);
		tbtProfile.setControl(cmpProf);
		cmpProf.setLayout(new GridLayout(1, true));
		
		reguser = new RegisterUsers(cmpProf,  SWT.NONE);
		
		if(role.equals("ADMIN")){
			//Admin Panel Tab - START
			TabItem tbtmAdminPanel = new TabItem(tabFolder, SWT.NONE);
			tbtmAdminPanel.setText("Admin Panel");
			
			Composite cmpAdmin = new Composite(tabFolder, SWT.NONE);
			tbtmAdminPanel.setControl(cmpAdmin);
			cmpAdmin.setLayout(new GridLayout(1, true));
			
			AdminView adminview = new AdminView(cmpAdmin, SWT.NONE);
			GridData gd_usermgmt = new GridData(SWT.FILL, SWT.FILL, false, false);
			adminview.setLayoutData(gd_usermgmt);
			//Admin Panel Tab - FINISH
		}
		
		//Case Management TAB - START
		TabItem tbtmManageCase = new TabItem(tabFolder, SWT.NONE);
		tbtmManageCase.setText("Manage Case");
		
		SashForm sashForm = new SashForm(tabFolder, SWT.NONE);
		tbtmManageCase.setControl(sashForm);
		sashForm.setSashWidth(4);
		
		Composite caseComposite = new Composite(sashForm, SWT.NONE);

		/*table = new Table(caseComposite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(0, 0, 416, 319);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnCaseId = new TableColumn(table, SWT.NONE);
		tblclmnCaseId.setWidth(0);
		tblclmnCaseId.setText("Case Id");
		
		TableColumn tblclmnCaseName = new TableColumn(table, SWT.NONE);
		tblclmnCaseName.setWidth(97);
		tblclmnCaseName.setText("Case Name");
		
		TableColumn tblclmnDeviceId = new TableColumn(table, SWT.NONE);
		tblclmnDeviceId.setWidth(100);
		tblclmnDeviceId.setText("Device ID");
		
		TableColumn tblclmCaseCreated = new TableColumn(table, SWT.NONE);
		tblclmCaseCreated.setWidth(0);
		tblclmCaseCreated.setText("Created");

		TableColumn tblclmCasePath = new TableColumn(table, SWT.NONE);
		tblclmCasePath.setWidth(100);
		tblclmCasePath.setText("Path");
		
		Button btnArchiveCase = new Button(caseComposite, SWT.NONE);
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
		btnArchiveCase.setBounds(319, 325, 97, 29);
		
		Button button = new Button(caseComposite, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				OpenCase();
			}
		});
		button.setText("Open Case");
		button.setBounds(89, 325, 83, 29);
		
		Button btnRefresh = new Button(caseComposite, SWT.NONE);
		btnRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				LoadCases(new ConnectDb().getConnection());
			}
		});
		btnRefresh.setText("Refresh");
		btnRefresh.setBounds(0, 325, 83, 29);
		
		Button btnBackUp = new Button(caseComposite, SWT.NONE);
		btnBackUp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				//TODO
				;
			}
		});
		btnBackUp.setBounds(224, 325, 91, 29);
		btnBackUp.setText("Back Up");
		
		Group grpTransferCase = new Group(caseComposite, SWT.NONE);
		grpTransferCase.setText("Transfer Case");
		grpTransferCase.setBounds(0, 361, 416, 105);
		
		Label lblInvestigator = new Label(grpTransferCase, SWT.NONE);
		lblInvestigator.setBounds(10, 41, 107, 17);
		lblInvestigator.setText("Investigator");
		
		text_1 = new Text(grpTransferCase, SWT.BORDER);
		text_1.setBounds(256, 31, 75, 27);
		
		Label lblTransferInvestigator = new Label(grpTransferCase, SWT.NONE);
		lblTransferInvestigator.setBounds(10, 78, 204, 17);
		lblTransferInvestigator.setText("Transfer Investigator");
		
		text_2 = new Text(grpTransferCase, SWT.BORDER);
		text_2.setBounds(255, 68, 151, 27);
		
		*/

		new NewCase(sashForm, SWT.NONE);
		sashForm.setWeights(new int[] {341, 246});
		//Case Management Tab - FINISH
		
		//Acquisition Tab - START
		tbtmAcquisition = new TabItem(tabFolder, SWT.NONE);
		tbtmAcquisition.setText("Acquisition");
		
		Canvas canvas = new Canvas(tabFolder, SWT.NONE);
		tbtmAcquisition.setControl(canvas);
		
		ToolBar toolBarAcq = new ToolBar(canvas, SWT.FLAT | SWT.RIGHT);
		toolBarAcq.setBounds(0, 0, 796, 31);
		
		ToolItem tltmCheckconnection = new ToolItem(toolBarAcq, SWT.NONE);
		tltmCheckconnection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(adbcmd.isDeviceConnected()){
					txtAcqConsole.setText("Device Connection Successful");
				}
				else{
					txtAcqConsole.setText("Device Connection fail");
				}
			}
		});
		tltmCheckconnection.setText("CheckConnection");
		
		ToolItem toolItemSeprator1 = new ToolItem(toolBarAcq, SWT.SEPARATOR);
		
		ToolItem tltmRoot = new ToolItem(toolBarAcq, SWT.NONE);
		tltmRoot.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.out.println("root 2");
				adbcmd.root();
			}
		});
		tltmRoot.setText("Root");
		
		ToolItem tltmUnroot = new ToolItem(toolBarAcq, SWT.NONE);
		tltmUnroot.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				adbcmd.unroot();
			}
		});
		tltmUnroot.setText("Unroot");
		
		ToolItem tltmChkRoot = new ToolItem(toolBarAcq, SWT.NONE);
		tltmChkRoot.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(adbcmd.isRooted()){
					txtAcqConsole.setText("The Device is Rooted");
				}
				else{
					txtAcqConsole.setText("The Device is Not Rooted");
				}
			}
		});
		tltmChkRoot.setText("CheckRoot");
		
		ToolItem toolItemSeprator2 = new ToolItem(toolBarAcq, SWT.SEPARATOR);
		//toolItem.setSelection(true);
		
		ToolItem tltmVolatileMemory = new ToolItem(toolBarAcq, SWT.NONE);
		tltmVolatileMemory.setText("Volatile Memory");
		
		ToolItem tltmLogical = new ToolItem(toolBarAcq, SWT.NONE);
		tltmLogical.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				adbcmd.pullRoot(globalPath);
			}
		});
		tltmLogical.setText("Logical");
		
		ToolItem tltmPhysical = new ToolItem(toolBarAcq, SWT.NONE);
		tltmPhysical.setText("Physical");
		
		txtAcqConsole = new Text(canvas, SWT.BORDER);
		txtAcqConsole.setBounds(0, 37, 460, 27);
		// Acquisition Tab - FINISH
		
		//Analysis Tab - START
		tbtmAnalysis = new TabItem(tabFolder, SWT.NONE);
		tbtmAnalysis.setText("Analysis");
		
		Canvas canvasAnalysis = new Canvas(tabFolder, SWT.NONE);
		tbtmAnalysis.setControl(canvasAnalysis);
		
		//to be moved
		ToolBar toolBar = new ToolBar(canvasAnalysis, SWT.FLAT | SWT.WRAP | SWT.RIGHT);
		toolBar.setBounds(0, 0, 817, 31);
		
		ToolItem tltmDeviceInfo = new ToolItem(toolBar, SWT.NONE);
		tltmDeviceInfo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
			}
		});
		tltmDeviceInfo.setText("Device Info");
		
		ToolItem tltmScreenCapture = new ToolItem(toolBar, SWT.NONE);
		tltmScreenCapture.setText("Screen Capture");
		
		ToolItem tltmDeleted = new ToolItem(toolBar, SWT.NONE);
		tltmDeleted.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.out.println(globalPath);
				String imgPath = globalPath+"/Analysis/Deleted";
				System.out.println(imgPath);
				adbcmd.recoverDeleted(imgPath);
				text.setText("Deleted Data Recovered Successfully: "+imgPath);
							
			}
		});
		tltmDeleted.setText("Deleted ");
		
		ToolItem tltmGenerateStrings = new ToolItem(toolBar, SWT.NONE);
		tltmGenerateStrings.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				final String file ="";
				Display.getDefault().syncExec(new Runnable() {
					
					@Override
					public void run() {
						CreateStringFile(file);
						
					}
				});
			}
		});
		tltmGenerateStrings.setText("Generate Strings");
		
		text = new Text(canvasAnalysis, SWT.BORDER);
		text.setBounds(0, 37, 350, 203);
		
		txtSearch = new Text(canvasAnalysis, SWT.BORDER);
		txtSearch.setBounds(356, 33, 364, 27);
		
		Button btnSearch = new Button(canvasAnalysis, SWT.NONE);
		btnSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				final String searchStr = txtSearch.getText();
				final String file = "/home/indeewari/stringdd.txt";
				Display.getDefault().syncExec(new Runnable() {
					
					@Override
					public void run() {
						SearchString(searchStr, file);						
					}
				});
				
			}
		});
		btnSearch.setBounds(726, 33, 91, 29);
		btnSearch.setText("Search");
		
		txtConsole = new Text(canvasAnalysis, SWT.BORDER | SWT.READ_ONLY | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		txtConsole.setBounds(356, 66, 461, 258);
		//Analysis Tab - Finish
		
		//START
		tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("Analyzer");
		
		TabFolder tabFolder_1 = new TabFolder(tabFolder, SWT.NONE);
		tabItem.setControl(tabFolder_1);
			
		TabItem tabItemfv = new TabItem(tabFolder_1, SWT.NONE);
		tabItemfv.setText("Hex View");
		
		Composite cmpFolderView = new Composite(tabFolder_1, SWT.NONE);
		tabItemfv.setControl(cmpFolderView);
		cmpFolderView.setLayout(new GridLayout(1, false));
		cmpFolderView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		FileTreeViewer fv = new FileTreeViewer(cmpFolderView, SWT.BORDER);
		
		TabItem tabAnalyzer_2 =  new TabItem(tabFolder_1, SWT.NONE);
		tabAnalyzer_2.setText("SQLite Viewer");
		
		Composite cmpSqliteView = new Composite(tabFolder_1, SWT.NONE);
		tabAnalyzer_2.setControl(cmpSqliteView);
		cmpSqliteView.setLayout(new GridLayout(1, false));
		cmpSqliteView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		SqliteViwer sv = new SqliteViwer(cmpSqliteView, SWT.BORDER);
		
		TabItem tabAppAnlyzer = new TabItem(tabFolder_1, SWT.NONE);
		tabAppAnlyzer.setText("Application Analysis");
		
		Composite cmpAppAnalizer = new Composite(tabFolder_1, SWT.NONE);
		tabAppAnlyzer.setControl(cmpAppAnalizer);
		cmpAppAnalizer.setLayout(new GridLayout(1, false));
		cmpAppAnalizer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		AppAnalysis apps = new AppAnalysis(cmpAppAnalizer, SWT.BORDER);
		

		TabItem tabExtract = new TabItem(tabFolder_1, SWT.NONE);
		tabExtract.setText("String Extraction");
		
		Composite cmpExtract = new Composite(tabFolder_1, SWT.NONE);
		tabExtract.setControl(cmpExtract);
		cmpExtract.setLayout(new GridLayout(1, false));
		cmpExtract.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		BulkViewer bulkv = new BulkViewer(cmpExtract, SWT.BORDER);
		
		tbtmReport = new TabItem(tabFolder, SWT.NONE);
		tbtmReport.setText("Report");
		
		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setLayout(new GridLayout(1, true));
		//composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		tbtmReport.setControl(composite);
		
		ReportModule repmod = new ReportModule(composite, SWT.NONE);
		//AppAnalysis aa = new AppAnalysis(composite, SWT.NONE);
		//BulkViewer bv = new BulkViewer(composite, SWT.NONE);
		
	}

	/*
   	protected void makeCompress(String srcFile) throws IOException {
   		srcFile = "/home/indeewari/Project1/test/test1.txt";
   		System.out.println("src: " + srcFile);
   		File outFile = new File(srcFile + ".tar.gz");
		ArrayList<File> list = new ArrayList<File>(1);
		File file = new File(srcFile);
		list.add(file);
		compressFiles(list, outFile);
		System.out.println("archive src: " + file.getName() + "out: " + outFile.getName());
	}

	private void compressFiles(ArrayList<File> list, File outFile) throws IOException {
		FileOutputStream fos = new FileOutputStream(outFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		GZIPOutputStream gos = new GZIPOutputStream(bos);
		TarArchiveOutputStream taos = new TarArchiveOutputStream(gos);
		taos.setBigNumberMode(TarArchiveOutputStream.BIGNUMBER_STAR);
		taos.setLongFileMode(TarArchiveOutputStream.LONGFILE_GNU);
		
		for(File f : list){
			System.out.println("send to cmpress: " + f.getName());
			addFileToCompression(taos, f, ".");
		}
		taos.close();
		fos.close();
		//gos.close();
		//bos.close();
	}

	private void addFileToCompression(TarArchiveOutputStream taos, File f, String dir) throws IOException {
		System.out.println("dir : " + dir);
		TarArchiveEntry tae = new TarArchiveEntry(f, dir + "/");		
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
			BufferedInputStream bis = new BufferedInputStream(fis);
			
			byte data[] = new byte[2048];
			int count = -1;
			while((count = bis.read(data)) != -1){
				taos.write(data, 0 , count);
			}
			//IOUtils.copy(bis, taos);
			taos.flush();
			taos.closeArchiveEntry();
			bis.close();
		}
	}
*/
	public void populateUserProfile(String fname, String lname, String contact, String email, String uname, String pic, int role){
    	reguser.populate(fname, lname, contact, email, uname, pic, role);
		
	}

	protected void CreateStringFile(String rawImagePath){
		String result = "";
		try {
			Process proc = Runtime.getRuntime().exec("strings " + rawImagePath);
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			System.out.println(globalPath);
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(globalPath + "/strings_file.txt")));
			String line = bufin.readLine();
			while(line != null){
				result += line +"\n";
				writer.write(result);
				line = bufin.readLine();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void SearchString(String searchStr, String file) {
		//CreateStringFile("/home/indeewari/dd");
		String result = "", line = "";
		try {
			Process proc = Runtime.getRuntime().exec("grep " + searchStr + " " + file);
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			line = bufin.readLine();
			while(line != null){
				result += line +"\n";
				line = bufin.readLine();				
			}

			txtConsole.setText(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createContent() {			
		//to be moved
		/*Button btnDeviceInfo = new Button(shlAndrospy, SWT.NONE);
		btnDeviceInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				AdbExecCmd execmd = new AdbExecCmd();	
				String res = execmd.execute("getprop");
				txtDisplay.setText("Read Device Info " + res);
				
			}
		});
		btnDeviceInfo.setBounds(738, 323, 86, 29);
		btnDeviceInfo.setText("Device Info");*/
		//shlAndrospy.setLayout(new StackLayout());		
	}
	
	/*protected static void OpenCase(){		
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
		int caseCreated = Integer.parseInt(row.getText(3));
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
		table.clearAll();
		table.setItemCount(0);
		Statement stmt = null;
		String query = "SELECT * FROM Androspy_Case";
		String [] values = new String[4];
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()){
				TableItem row = new TableItem(table, SWT.NONE);
				values[0] = rs.getString("case_id");
				values[1] = rs.getString("case_name");
				values[2] = rs.getString("case_desc");
				values[3] = rs.getString("case_path");
				values[3] = rs.getString("case_created");
				row.setText(values);
				System.out.println("Case id" + rs.getString("case_id"));
				System.out.println("Case name" + rs.getString("case_name"));
				System.out.println("Case desc" + rs.getString("case_desc"));
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
	*/
}
