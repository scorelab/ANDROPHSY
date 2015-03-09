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

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Tree;


/**
 * @author indeewari
 *
 */
public class SqliteViwer extends Composite {

	Connection conn = null;
	Statement stmt = null;
	private Combo cmbTables;
	private Text txtQuery;
	private Table table;
	private Text txtConsole;
	private static Tree tree;
	private Label lblTableList;
	//private static List list;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SqliteViwer(Composite parent, int style) {
		super(parent, style);
		GridLayout gridLayout = new GridLayout(4, false);
		gridLayout.marginBottom = 3;
		gridLayout.marginRight = 3;
		gridLayout.marginLeft = 3;
		gridLayout.marginTop = 3;
		gridLayout.marginWidth = 1;
		gridLayout.marginHeight = 1;
		
		this.setLayout(gridLayout);
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		GridData gd_list = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 6);
		gd_list.widthHint = 107;
		gd_list.heightHint = 332;
		
		tree = new Tree(this, SWT.BORDER);
		tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TreeItem selitem[] = tree.getSelection();
				if(selitem != null && (selitem.length > 0)){
					File selfile = (File) selitem[0].getData();
					if(selfile.isFile()){
						String dbfile = selfile.getPath();
						System.out.println(" >> " + dbfile);
						stmt = createConnection(dbfile);
						getTables(dbfile);
					}
				}
			}
		});
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tree.setLayoutData(gd_list);
		
		lblTableList = new Label(this, SWT.NONE);
		lblTableList.setText("Table List");
		
		cmbTables = new Combo(this, SWT.NONE);
		cmbTables.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String selitem =cmbTables.getItem(cmbTables.getSelectionIndex());
				getSchema(selitem);
			}
		});
		
		GridData gd_cmbTables = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_cmbTables.widthHint = 132;
		cmbTables.setLayoutData(gd_cmbTables);
		new Label(this, SWT.NONE);
		
		Group grpConsole = new Group(this, SWT.NONE);
		grpConsole.setLayout(new GridLayout(1, false));
		GridData gd_grpConsole = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
		gd_grpConsole.heightHint = 81;
		grpConsole.setLayoutData(gd_grpConsole);
		grpConsole.setText("Schema");
		
		txtConsole = new Text(grpConsole, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		txtConsole.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		
		txtQuery = new Text(this, SWT.BORDER);
		GridData gd_txtQuery = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_txtQuery.heightHint = 46;
		txtQuery.setLayoutData(gd_txtQuery);
		
		Button btnQuery = new Button(this, SWT.NONE);
		btnQuery.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent arg0) {
				String q = txtQuery.getText();
				if(!q.equals(null)){					
					executeQuery(q);
				}				
			}
		});
		GridData gd_btnQuery = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_btnQuery.minimumWidth = 10;
		btnQuery.setLayoutData(gd_btnQuery);
		btnQuery.setText("Query");
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 3));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
	}
	
	public static void initTree(String path) {
		File rootFile = new File(path);
		TreeItem rootItem = new TreeItem(tree, SWT.CHECK);
		rootItem.setText("/Application");
		rootItem.setData(rootFile);
		createTree(rootItem, rootFile);		
	}

	public static void createTree(TreeItem rootNode, File parent) {

		if (parent.isDirectory() && parent.canRead() && parent.canExecute()) {
			for (File file : parent.listFiles()) {
				String tmp = file.getPath() + "/databases";				
				File tmpfile = new File(tmp);
				
				if(tmpfile.exists()){
					System.out.println("1 ->  " + file.getName());
					TreeItem treeNode = new TreeItem(rootNode, SWT.CHECK);
					treeNode.setData(file);
					treeNode.setText(file.getName());
					
					System.out.println("2 ->  " + tmpfile.getPath());
					TreeItem dbNode = new TreeItem(treeNode, SWT.CHECK);
					dbNode.setData(tmpfile);
					dbNode.setText(tmpfile.getName());
					
					for(File dbfile : tmpfile.listFiles()){
						System.out.println("3 parent " + dbNode.getText());
						System.out.println("3 ->  " + dbfile.getName());
						TreeItem dbfileNode = new TreeItem(dbNode, SWT.NONE);
						dbfileNode.setData(dbfile);
						dbfileNode.setText(dbfile.getName());
						
					}
				}
			}
		}
	}

	public static void getDbList(String rootDirectory, String ext){
		String filename = null;
		File f = new File(rootDirectory);
		boolean flag_directory = f.isDirectory();
		if(flag_directory){
			File sf[] = f.listFiles();
			for(int i = 0; i < sf.length; i++){
				if(sf[i].isDirectory()){
					String tmp = sf[i].getPath() + "/databases";
					System.out.println("tmp file " + tmp);
					File tmpfile = new File(tmp);
					if(tmpfile.exists()){
						//list.add(tmpfile.getName());
					}
					getDbList(sf[i].getPath(), ext);
				}
				else{
					filename = sf[i].getName();
					if(filename.endsWith(".db")){
						//list.setData(filename, sf[i].getPath());
						//list.add(filename);
					}
				}
			}
		}
	}
	
	protected void executeQuery(String query){
		try {
			ResultSet rs 			= stmt.executeQuery(query);
			ResultSetMetaData rsm 	= rs.getMetaData();
			int colcount 			= rsm.getColumnCount();
			String rowval[] 		= new String[colcount];
			
			table.removeAll();
			//create columns
			for(int i = 1 ; i <= colcount ; i++){
				String cn = rsm.getColumnLabel(i);				
				System.out.print(cn + ", ");
				TableColumn tc = new TableColumn(table, SWT.BORDER);
				tc.setText(cn);				
				tc.setWidth(60);
			}
			while(rs.next()){
				for(int i = 1 ; i <= colcount ; i++){
					if(rsm.getColumnLabel(i).equals("date")){
						long lepoch = Long.parseLong(rs.getString(i));
						
						String sdate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(lepoch));
						rowval[i-1] = sdate;
					}
					else{
						rowval[i-1] = rs.getString(i);
					}
				}
				TableItem ti = new TableItem(table, SWT.BORDER);
				ti.setText(rowval);
			}
		} catch (SQLException e) {
			//e.printStackTrace();
			MessageBox msgbox = new MessageBox(getShell(), SWT.ICON_ERROR);
			msgbox.setText("Query Error");
			msgbox.setMessage("Error in execution query. Check Schema first");
			msgbox.open();
		}
	}
	
	protected void getSchema(String tname) {
		try {
			System.out.println("schema table: " + tname);
			ResultSet rs = stmt.executeQuery("SELECT IFNULL(sql, '') AS sql FROM sqlite_master WHERE tbl_name = '" + tname + "'");
			while(rs.next()){
				String schema = rs.getString("sql");
				System.out.println("schema " + schema);
				txtConsole.setText(schema);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void getTables(String dbfile){
		if(stmt.equals(null)){
			stmt = createConnection(dbfile);
		}
		ResultSet rs;
		cmbTables.removeAll();
		try {
			rs = stmt.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table'");
			while(rs.next()){
				String tname = rs.getString("name");
				cmbTables.add(tname);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	protected Statement createConnection(String dbfile){
		Statement stmt = null;
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + dbfile);
			stmt = conn.createStatement();
			stmt.setQueryTimeout(10);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}
}
