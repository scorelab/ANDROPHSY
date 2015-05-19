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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

/**
 * @author indeewari
 *
 */
public class FileTreeViewer extends Composite {
	// File rootFile = null;
	// TreeItem rootItem = null;
	private Table table;
	List<TreeItem> allTreeItems = new ArrayList<TreeItem>();
	private HexViewer hex;
	private Table tableDb;
	private Table tableImage;
	private Table tableDoc;
	private Table tableRst;
	private Table tableVideo;
	private static Tree tree;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public FileTreeViewer(Composite parent, int style) {
		super(parent, style);
		GridLayout gridLayout = new GridLayout(2, false);
		setLayout(gridLayout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Listener tblListener = new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				Table source = (Table) arg0.widget;
				TableItem item = source.getItem(source.getSelectionIndex());
				final String path = item.getText(1);
				System.out.println("@path " + path);
				Display.getDefault().syncExec(new Runnable() {

					@Override
					public void run() {
						showHexDump(path);
					}
				});
			}
		};

		tree = new Tree(this, SWT.BORDER | SWT.CHECK);
		GridData gd_tree = new GridData();
		gd_tree.heightHint = 471;
		gd_tree.widthHint = 166;
		gd_tree.horizontalAlignment = GridData.FILL;
		gd_tree.verticalAlignment = GridData.FILL;
		gd_tree.verticalSpan = 2;
		tree.setLayoutData(gd_tree);
		tree.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (arg0.detail == SWT.CHECK) {
					resetTables();
					Display.getDefault().syncExec(new Runnable() {

						@Override
						public void run() {
							populateTable(tree);
						}
					});
				}
			}
		});

		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		GridData gd_tabFolder = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_tabFolder.widthHint = 350;
		gd_tabFolder.heightHint = 177;
		tabFolder.setLayoutData(gd_tabFolder);

		TabItem tbtmSelectedFolder = new TabItem(tabFolder, SWT.NONE);
		tbtmSelectedFolder.setText("Selected Folder");

		table = new Table(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
		tbtmSelectedFolder.setControl(table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				TableItem row = table.getItem(table.getSelectionIndex());
				final String path = row.getText(1);
				showHexDump(path);

			}
		});

		TableColumn tblclmnFileName = new TableColumn(table, SWT.NONE);
		tblclmnFileName.setWidth(137);
		tblclmnFileName.setText("File Name");

		TableColumn tblclmnFilePath = new TableColumn(table, SWT.NONE);
		tblclmnFilePath.setWidth(228);
		tblclmnFilePath.setText("File Path");

		TableColumn tblclmnFileSize = new TableColumn(table, SWT.NONE);
		tblclmnFileSize.setWidth(100);
		tblclmnFileSize.setText("File Size");

		TabItem tbtmDatabase = new TabItem(tabFolder, SWT.NONE);
		tbtmDatabase.setText("Database");

		tableDb = new Table(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
		tbtmDatabase.setControl(tableDb);
		tableDb.setHeaderVisible(true);
		tableDb.setLinesVisible(true);
		tableDb.addListener(SWT.Selection, tblListener);

		TableColumn tbldbclmnFileName = new TableColumn(tableDb, SWT.NONE);
		tbldbclmnFileName.setWidth(137);
		tbldbclmnFileName.setText("File Name");

		TableColumn tbldbclmnFilePath = new TableColumn(tableDb, SWT.NONE);
		tbldbclmnFilePath.setWidth(228);
		tbldbclmnFilePath.setText("File Path");

		TableColumn tbldbclmnFileSize = new TableColumn(tableDb, SWT.NONE);
		tbldbclmnFileSize.setWidth(100);
		tbldbclmnFileSize.setText("File Size");

		TabItem tbtmImages = new TabItem(tabFolder, SWT.NONE);
		tbtmImages.setText("images");

		tableImage = new Table(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
		tbtmImages.setControl(tableImage);
		tableImage.setHeaderVisible(true);
		tableImage.setLinesVisible(true);
		tableImage.addListener(SWT.Selection, tblListener);

		TableColumn tblImgclmnFileName = new TableColumn(tableImage, SWT.NONE);
		tblImgclmnFileName.setWidth(137);
		tblImgclmnFileName.setText("File Name");

		TableColumn tblImgclmnFilePath = new TableColumn(tableImage, SWT.NONE);
		tblImgclmnFilePath.setWidth(228);
		tblImgclmnFilePath.setText("File Path");

		TableColumn tblImgclmnFileSize = new TableColumn(tableImage, SWT.NONE);
		tblImgclmnFileSize.setWidth(100);
		tblImgclmnFileSize.setText("File Size");

		TabItem tbtmAudio = new TabItem(tabFolder, SWT.NONE);
		tbtmAudio.setText("Video");

		tableVideo = new Table(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
		tbtmAudio.setControl(tableVideo);
		tableVideo.setHeaderVisible(true);
		tableVideo.setLinesVisible(true);
		tableVideo.addListener(SWT.Selection, tblListener);

		TableColumn tblVideoclmnFileName = new TableColumn(tableVideo, SWT.NONE);
		tblVideoclmnFileName.setWidth(137);
		tblVideoclmnFileName.setText("File Name");

		TableColumn tblVideoclmnFilePath = new TableColumn(tableVideo, SWT.NONE);
		tblVideoclmnFilePath.setWidth(228);
		tblVideoclmnFilePath.setText("File Path");

		TableColumn tblVideoclmnFileSize = new TableColumn(tableVideo, SWT.NONE);
		tblVideoclmnFileSize.setWidth(100);
		tblVideoclmnFileSize.setText("File Size");

		TabItem tbtmDocuments = new TabItem(tabFolder, SWT.NONE);
		tbtmDocuments.setText("Documents");

		tableDoc = new Table(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
		tbtmDocuments.setControl(tableDoc);
		tableDoc.setHeaderVisible(true);
		tableDoc.setLinesVisible(true);
		tableDoc.addListener(SWT.Selection, tblListener);

		TableColumn tblDocclmnFileName = new TableColumn(tableDoc, SWT.NONE);
		tblDocclmnFileName.setWidth(137);
		tblDocclmnFileName.setText("File Name");

		TableColumn tblDocclmnFilePath = new TableColumn(tableDoc, SWT.NONE);
		tblDocclmnFilePath.setWidth(228);
		tblDocclmnFilePath.setText("File Path");

		TableColumn tblDocclmnFileSize = new TableColumn(tableDoc, SWT.NONE);
		tblDocclmnFileSize.setWidth(100);
		tblDocclmnFileSize.setText("File Size");

		TabItem tbtmOther = new TabItem(tabFolder, SWT.NONE);
		tbtmOther.setText("Other");

		tableRst = new Table(tabFolder, SWT.BORDER | SWT.FULL_SELECTION);
		tbtmOther.setControl(tableRst);
		tableRst.setHeaderVisible(true);
		tableRst.setLinesVisible(true);
		tableRst.addListener(SWT.Selection, tblListener);

		TableColumn tblRstclmnFileName = new TableColumn(tableRst, SWT.NONE);
		tblRstclmnFileName.setWidth(137);
		tblRstclmnFileName.setText("File Name");

		TableColumn tblRstclmnFilePath = new TableColumn(tableRst, SWT.NONE);
		tblRstclmnFilePath.setWidth(228);
		tblRstclmnFilePath.setText("File Path");

		TableColumn tblRstclmnFileSize = new TableColumn(tableRst, SWT.NONE);
		tblRstclmnFileSize.setWidth(100);

		hex = new HexViewer(this, SWT.NONE);
		GridData gd_hex = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_hex.heightHint = 332;
		gd_hex.widthHint = 439;
		hex.setLayoutData(gd_hex);
	}

	protected void resetTables() {
		table.clearAll();
		table.setItemCount(0);

		tableDb.clearAll();
		tableDb.setItemCount(0);

		tableImage.clearAll();
		tableImage.setItemCount(0);

		tableDoc.clearAll();
		tableDoc.setItemCount(0);

		tableRst.clearAll();
		tableRst.setItemCount(0);
	}

	private void showFiles(File file) {
		TableItem tableItem = null;
		String fileName = null;
		String filePath = null;
		float fileSize = -1;
		String columns[] = new String[3];

		if (file.canRead() && file.canExecute() && file.isDirectory()) {
			File currentFile[] = file.listFiles();
			for (int i = 0; i < currentFile.length; i++) {
				if (currentFile[i].isFile()) {
					tableItem = new TableItem(table, SWT.NONE);
					fileName = currentFile[i].getName();
					filePath = currentFile[i].getPath();
					fileSize = currentFile[i].length();
					columns[0] = fileName;
					columns[1] = filePath;
					if (fileSize >= 512) {
						columns[2] = fileSize / 1024 + "KB";
					} else {
						columns[2] = fileSize + "Bytes";
					}
					tableItem.setText(columns);
					table.setLinesVisible(true);
					// database file
					if (fileName.endsWith(".db") || fileName.endsWith(".db-journal") ||
					    fileName.endsWith(".db-wal")) {
						tableItem = new TableItem(tableDb, SWT.NONE);
						tableItem.setText(columns);
						tableDb.setLinesVisible(true);
					} else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
					           fileName.endsWith(".png")) {
						tableItem = new TableItem(tableImage, SWT.NONE);
						tableItem.setText(columns);
						tableImage.setLinesVisible(true);
					} else if (fileName.endsWith(".txt") || fileName.endsWith(".pdf") ||
					           fileName.endsWith(".xml")) {
						tableItem = new TableItem(tableDoc, SWT.NONE);
						tableItem.setText(columns);
						tableImage.setLinesVisible(true);
					} else if (fileName.endsWith(".mp3") || fileName.endsWith("mp4") ||
					           fileName.endsWith("mpeg")) {
						tableItem = new TableItem(tableVideo, SWT.NONE);
						tableItem.setText(columns);
						tableImage.setLinesVisible(true);
					} else {
						tableItem = new TableItem(tableRst, SWT.NONE);
						tableItem.setText(columns);
						tableImage.setLinesVisible(true);
					}
				}
			}
		}

	}

	private void populateTable(Tree tree) {
		TreeItem selectedNode[] = getSelectedItems(tree);
		File selectedFile = null;

		for (int i = 0; i < selectedNode.length; i++) {
			if (selectedNode[i] != null) {
				selectedFile = (File) selectedNode[i].getData();
				showFiles(selectedFile);
			}
		}
	}

	private void getAllItems(Tree tree, List<TreeItem> list) {
		list.clear();
		for (TreeItem items : tree.getItems()) {
			list.add(items);
			getAllItems(items, list);
		}
	}

	private void getAllItems(TreeItem parent, List<TreeItem> list) {
		TreeItem children[] = parent.getItems();
		for (int i = 0; i < children.length; i++) {
			list.add(children[i]);
			getAllItems(children[i], list);
		}
	}

	private TreeItem[] getSelectedItems(Tree tree) {
		TreeItem current = null;
		int index = 0;
		getAllItems(tree, allTreeItems);
		TreeItem selected[] = new TreeItem[allTreeItems.size()];
		Iterator<TreeItem> it = allTreeItems.iterator();
		while (it.hasNext()) {
			current = it.next();
			if (current.getChecked()) {
				System.out.println(current.getText());
				selected[index] = current;
				index++;
			}
		}
		return selected;
	}

	protected void showHexDump(final String filePath) {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				hex.ViewHexDump(filePath);
			}
		};
		BusyIndicator.showWhile(AndrospyMain.shell.getDisplay(), runnable);
	}

	public static void initTree(String path) {
		File rootFile = new File(AndrospyMain.gb_CasePath + "Acquire/data");
		if (rootFile.exists()) {
			TreeItem rootItem = new TreeItem(tree, SWT.CHECK);
			rootItem.setText("/data");
			rootItem.setData(rootFile);
			createTree(rootItem, rootFile);
		}

		File rootFilesystem = new File(AndrospyMain.gb_CasePath + "Acquire/system");
		if (rootFilesystem.exists()) {
			TreeItem rootItemsystem = new TreeItem(tree, SWT.CHECK);
			rootItemsystem.setText("/system");
			rootItemsystem.setData(rootFilesystem);
			createTree(rootItemsystem, rootFilesystem);
		}

		File rootFilecache = new File(AndrospyMain.gb_CasePath + "Acquire/cache");
		if (rootFilecache.exists()) {
			TreeItem rootItemcache = new TreeItem(tree, SWT.CHECK);
			rootItemcache.setText("/cache");
			rootItemcache.setData(rootFilecache);
			createTree(rootItemcache, rootFilecache);
		}
	}

	public static void createTree(TreeItem rootNode, File parent) {

		if (parent.isDirectory() && parent.canRead() && parent.canExecute()) {
			for (File file : parent.listFiles()) {
				if (file.isDirectory()) {
					TreeItem treeNode = new TreeItem(rootNode, SWT.CHECK);
					treeNode.setData(file);
					treeNode.setText(file.getName());
					createTree(treeNode, file);
				}
			}
		}
	}

}
