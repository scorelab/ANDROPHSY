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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Composite;

/**
 * @author indeewari
 *
 */
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
	private static AdbExecCmd adbcmd;
	private RegisterUsers reguser;
	private Text text_1;
	private Text text_2;
	private SqliteViwer sv;
	private TabItem tbtmAndroLog;
	private static TabFolder tabFolder;
	
	public Androspy(Composite parent, int arg1, String role) {
		super(parent, SWT.NONE);
		setLayout(new GridLayout(1, true));
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		adbcmd = new AdbExecCmd();
		
		ToolBar toolBar_1 = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		GridData gd_toolBar_1 = new GridData(SWT.RIGHT, SWT.FILL, false, false, 1, 1);
		gd_toolBar_1.heightHint = 25;
		toolBar_1.setLayoutData(gd_toolBar_1);
		
		ToolItem tltmLogOut = new ToolItem(toolBar_1, SWT.NONE);
		tltmLogOut.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Logout();
			}
		});
		tltmLogOut.setText("Log Out");
			
		tabFolder = new TabFolder(this, SWT.NONE);
		GridData gd_tabFolder = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_tabFolder.widthHint = 761;
		gd_tabFolder.heightHint = 700;
		tabFolder.setLayoutData(gd_tabFolder);
				
		TabItem tbtProfile = new TabItem(tabFolder, SWT.NONE);
		tbtProfile.setText("User Profile");
		
		Composite cmpProf = new Composite(tabFolder, SWT.NONE);
		tbtProfile.setControl(cmpProf);
		cmpProf.setLayout(new GridLayout(1, true));
		
		reguser = new RegisterUsers(cmpProf,  SWT.NONE);
		GridData gd_reguser = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_reguser.widthHint = 576;
		reguser.setLayoutData(gd_reguser);
		
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
		
		Composite caseComposite = new Composite(tabFolder, SWT.NONE);
		caseComposite.setLayout(new GridLayout(1, true));
		tbtmManageCase.setControl(caseComposite);		
		
		CaseManagement nc = new CaseManagement(caseComposite, SWT.NONE);
		GridData gd_nc = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_nc.heightHint = 600;
		nc.setLayoutData(gd_nc);
				
		//Case Management Tab - FINISH
		
				
		//START
		TabFolder tabFolderAnalysis = new TabFolder(tabFolder, SWT.NONE);
		tabFolderAnalysis.setLayout(new GridLayout(1, true));
		tabFolderAnalysis.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setText("Analyzer");		
		tabItem.setControl(tabFolderAnalysis);
			
		TabItem tabDeviceInfo = new TabItem(tabFolderAnalysis, SWT.NONE);
		tabDeviceInfo.setText("Device Detail");
		DeviceProfile deviceprofile = new DeviceProfile(tabFolderAnalysis, SWT.NONE);
		tabDeviceInfo.setControl(deviceprofile);
		
		TabItem tabExtract = new TabItem(tabFolderAnalysis, SWT.NONE);
		tabExtract.setText("Extract Strings");
		Composite cmpExtract = new Composite(tabFolderAnalysis, SWT.NONE);
		tabExtract.setControl(cmpExtract);
		cmpExtract.setLayout(new GridLayout(1, false));
		cmpExtract.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		QuickView bulkv = new QuickView(cmpExtract, SWT.BORDER);
		bulkv.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		 
		TabItem tabItemfv = new TabItem(tabFolderAnalysis, SWT.NONE);
		tabItemfv.setText("File Browser");
		
		Composite cmpFolderView = new Composite(tabFolderAnalysis, SWT.NONE);
		tabItemfv.setControl(cmpFolderView);
		cmpFolderView.setLayout(new GridLayout(1, false));
		cmpFolderView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		FileTreeViewer fv = new FileTreeViewer(cmpFolderView, SWT.BORDER);
		
		TabItem tabAppAnlyzer = new TabItem(tabFolderAnalysis, SWT.NONE);
		tabAppAnlyzer.setText("Application Analysis");
		
		Composite cmpAppAnalizer = new Composite(tabFolderAnalysis, SWT.NONE);
		tabAppAnlyzer.setControl(cmpAppAnalizer);
		cmpAppAnalizer.setLayout(new GridLayout(1, false));
		cmpAppAnalizer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		AppAnalysis apps = new AppAnalysis(cmpAppAnalizer, SWT.BORDER);
		
		TabItem tabTimeLine = new TabItem(tabFolderAnalysis, SWT.NONE);
		tabTimeLine.setText("Time Line");
		TimeLine timeline = new TimeLine(tabFolderAnalysis, SWT.NONE);
		tabTimeLine.setControl(timeline);
		
		TabItem tabSearch = new TabItem(tabFolderAnalysis, SWT.NONE);
		tabSearch.setText("Search and Filter");
		SearchFilterBase cmpsearchfilter = new SearchFilterBase(tabFolderAnalysis, SWT.NONE);
		tabSearch.setControl(cmpsearchfilter);
		
		TabItem tabCarvedAnalysis = new TabItem(tabFolderAnalysis, SWT.NONE);
		tabCarvedAnalysis.setText("Recovered Data");
		RecoverData cmprecovereddata = new RecoverData(tabFolderAnalysis, SWT.NONE);
		tabCarvedAnalysis.setControl(cmprecovereddata);
		
		TabItem tabAnalyzer_2 =  new TabItem(tabFolderAnalysis, SWT.NONE);
		tabAnalyzer_2.setText("SQLite Viewer");
		
		Composite cmpSqliteView = new Composite(tabFolderAnalysis, SWT.NONE);
		tabAnalyzer_2.setControl(cmpSqliteView);
		cmpSqliteView.setLayout(new GridLayout(1, false));
		cmpSqliteView.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		sv = new SqliteViwer(cmpSqliteView, SWT.BORDER);		
		
		
		tbtmReport = new TabItem(tabFolder, SWT.NONE);
		tbtmReport.setText("Report");
		ReportGUITop reportgui = new ReportGUITop(tabFolder, SWT.NONE);
		tbtmReport.setControl(reportgui);
		
		tbtmAndroLog = new TabItem(tabFolder, SWT.NONE);
		tbtmAndroLog.setText("Androspy Log");
		AndrospyLog androloggui = new AndrospyLog(tabFolder, SWT.NONE);
		tbtmAndroLog.setControl(androloggui);
						
	}

	protected void Logout() {
		AndrospyMain.gb_CaseId = 0;
		AndrospyMain.gb_CasePath = "";
		AndrospyMain.gb_DeviceId = 0;
		AndrospyMain.gb_username = "";
		AndrospyMain.gb_UserRole = "";
		
		Login login = new Login(AndrospyMain.shell, SWT.BORDER);
		login.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));		

		AndrospyMain.shell.layout();
		dispose();
	}

	public void populateUserProfile(String fname, String lname, String email, String uname, String pic, int role){
    	reguser.populate(fname, lname, email, uname, pic, role);
		
	}

	protected void SearchString(String searchStr, String file) {
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

	/*public static void createContent() {			
			
	}*/

	public static void Creatabs() {
		int index = -1;
		if(AndrospyMain.gb_UserRole == "ADMIN"){
			index = 3;
		}
		else{
			index = 2;
		}
		TabItem tbtmAcquisition = new TabItem(tabFolder, SWT.NONE, index);
		tbtmAcquisition.setText("Acquisition");

		AcquisitionTop actop = new AcquisitionTop(tabFolder, SWT.NONE);
		tbtmAcquisition.setControl(actop);
		
	}
}
