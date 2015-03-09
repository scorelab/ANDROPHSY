import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;


public class FileTreeViewer extends Composite {
	File rootFile = null;
	TreeItem rootItem = null;
	private Table table;
	List<TreeItem> allTreeItems = new ArrayList<TreeItem>();
	private HexViewer hex;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FileTreeViewer(Composite parent, int style) {
		super(parent, style);
		GridLayout gridLayout = new GridLayout(2,false);
		setLayout(gridLayout);
		setLayoutData(new  GridData(SWT.FILL, SWT.FILL, true, true));
		
		/*tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(arg0.detail == SWT.CHECK){			
					TreeItem selectedNode[] = getSelectedItems(tree);
					File selectedFile = null;
//					for(int i = 0 ; i<selectedNode.length ; i++){
//						selectedFile = (File)selectedNode[i].getData();	
//						showFiles(selectedFile);
//					}
			}
			}
		});*/
		
		rootFile = new File("/home/indeewari/2");
		
		
		final Tree tree = new Tree(this, SWT.BORDER | SWT.CHECK);
		GridData gd_tree = new GridData();
		gd_tree.heightHint = 257;
		gd_tree.widthHint = 166;
		gd_tree.horizontalAlignment = GridData.FILL;
		gd_tree.verticalAlignment = GridData.FILL;
		gd_tree.verticalSpan = 2;
		tree.setLayoutData(gd_tree);
		tree.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				if(arg0.detail == SWT.CHECK){
					table.clearAll();					
					table.setItemCount(0);
					Display.getDefault().syncExec(new Runnable() {
						
						@Override
						public void run() {
							populateTable(tree);
						}
					});					
				}
			}
		});
		rootItem = new TreeItem(tree, SWT.CHECK);
		rootItem.setText("/");
		rootItem.setData(rootFile);
		createTree(rootItem, rootFile);
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_table.widthHint = 245;
		gd_table.heightHint = 240;
		table.setLayoutData(gd_table);
		/*GridData gd_table = new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1);
		gd_table.heightHint = 142;
		gd_table.widthHint = 562;
		table.setLayoutData(gd_table);*/
		//table.setBounds(169, 0, 505, 158);
		table.setLinesVisible(true);
		table.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				TableItem row = table.getItem(table.getSelectionIndex());
				final String path = row.getText(1);
				Display.getDefault().syncExec(new Runnable() {
					
					@Override
					public void run() {
						//hex.ViewHexDump(path);	
						showHexDump(path);
					}
				});				
			}
		});
		
		TableColumn tblColFileName = new TableColumn(table, SWT.NONE);
		tblColFileName.setWidth(250);
		TableColumn tblColFilePath = new TableColumn(table, SWT.NONE);
		tblColFilePath.setWidth(250);
				
		hex = new HexViewer(this, SWT.BORDER);
		GridData gd_hex = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_hex.heightHint = 332;
		gd_hex.widthHint = 445;
		hex.setLayoutData(gd_hex);
	}
	
	private void getAllItems(Tree tree, List<TreeItem> list){
		list.clear();
		for(TreeItem items: tree.getItems()){
			list.add(items);
			getAllItems(items, list);
		}
	}
	
	private void getAllItems(TreeItem parent, List<TreeItem> list){
		TreeItem children[] = parent.getItems();
		for(int i=0; i<children.length; i++){
			list.add(children[i]);
			getAllItems(children[i], list);
		}
	}
	
	private TreeItem[] getSelectedItems(Tree tree){
		TreeItem current = null;
		int index = 0;
		getAllItems(tree, allTreeItems);
		TreeItem selected[] = new TreeItem[allTreeItems.size()];
		Iterator<TreeItem> it = allTreeItems.iterator();
		while(it.hasNext()){
			current = (TreeItem)it.next();
			if(current.getChecked()){
				System.out.println(current.getText());
				selected[index] = current;
				index ++ ;
			}			
		}
		return selected;
	}
	
	protected void showHexDump(final String filePath){
		
		Display.getDefault().syncExec(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("test 1 " + filePath);
				hex.ViewHexDump(filePath);
				System.out.println("test 5");
				System.out.println("calling to hex dump");				
			}
		});	
	}
	
	private void populateTable(Tree tree){
		TreeItem selectedNode[] = getSelectedItems(tree);
		File selectedFile = null;
		
		for(int i = 0 ; i<selectedNode.length ; i++){
			if(selectedNode[i] != null){
				selectedFile = (File)selectedNode[i].getData();	
				showFiles(selectedFile);
			}						
		}	
	}
	
	private void showFiles(File file){
		TableItem tableItem = null;
		String columns[] = new String[2];
		File currentFile[] = file.listFiles();
		for(int i=0; i<currentFile.length ; i++){
			if(currentFile[i].isFile()){
				tableItem = new TableItem(table, SWT.NONE);
				columns[0] = currentFile[i].getName();
				columns[1] = currentFile[i].getPath();
				tableItem.setText(columns);
				table.setLinesVisible(true);
			}
		}
		
	}

	protected void createTree(TreeItem rootNode, File parent){
		for(File file: parent.listFiles()){
			//System.out.println(file.getPath());
			if(file.isDirectory()){
				TreeItem treeNode = new TreeItem(rootNode, SWT.CHECK);
				treeNode.setData(file);
				treeNode.setText(file.getName());
				createTree(treeNode, file);
			}
		}
	}
	
}
