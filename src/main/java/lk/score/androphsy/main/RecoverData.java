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
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;

/**
 * @author indeewari
 *
 */
public class RecoverData extends Composite {
	private Table table;
	private HexViewer hexview;
	private Canvas canvas;
	private Label label;
	private static Tree tree;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public RecoverData(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, false));

		tree = new Tree(this, SWT.BORDER);
		GridData gd_tree = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 2);
		gd_tree.widthHint = 150;
		tree.setLayoutData(gd_tree);
		tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				table.removeAll();
				table.setItemCount(0);

				Display.getDefault().syncExec(new Runnable() {

					@Override
					public void run() {
						populateTable(tree);
					}
				});
			}
		});

		ScrolledComposite scrolledComposite =
		                                      new ScrolledComposite(this, SWT.BORDER |
		                                                                  SWT.H_SCROLL |
		                                                                  SWT.V_SCROLL);
		GridData gd_scrolledComposite = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_scrolledComposite.widthHint = 140;
		gd_scrolledComposite.heightHint = 56;
		scrolledComposite.setLayoutData(gd_scrolledComposite);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		table = new Table(scrolledComposite, SWT.BORDER | SWT.FULL_SELECTION);
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TableItem row = table.getItem(table.getSelectionIndex());
				final String path = row.getText(2);
				Display.getDefault().syncExec(new Runnable() {

					@Override
					public void run() {
						// hex.ViewHexDump(path);
						showHexDump(path);
						showPic(path);
					}
				});
			}
		});

		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnFileName = new TableColumn(table, SWT.NONE);
		tblclmnFileName.setWidth(100);
		tblclmnFileName.setText("File Name");

		TableColumn tblclmnSize = new TableColumn(table, SWT.NONE);
		tblclmnSize.setWidth(50);
		tblclmnSize.setText("Size");

		TableColumn tblclmnPath = new TableColumn(table, SWT.NONE);
		tblclmnPath.setWidth(-245);
		tblclmnPath.setText("Path");

		scrolledComposite.setContent(table);
		scrolledComposite.setMinSize(table.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		canvas = new Canvas(this, SWT.BORDER);
		canvas.setLayout(new GridLayout(1, false));
		GridData gd_scrolledComposite_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_scrolledComposite_1.widthHint = 119;
		canvas.setLayoutData(gd_scrolledComposite_1);

		label = new Label(canvas, SWT.BORDER);
		label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		hexview = new HexViewer(this, SWT.NONE);
		hexview.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 2, 1));

	}

	public static void initTree() {
		File rootFile = new File(AndrospyMain.gb_CasePath + "Analysis/File_Carved");
		if (rootFile.exists()) {
			TreeItem rootItem = new TreeItem(tree, SWT.CHECK);
			rootItem.setText("/File Carved");
			rootItem.setData(rootFile);
			createTree(rootItem, rootFile);
		}
	}

	public static void createTree(TreeItem rootNode, File parent) {

		if (parent.isDirectory() && parent.canRead() && parent.canExecute()) {
			for (File file : parent.listFiles()) {
				if (file.isDirectory()) {
					TreeItem treeNode = new TreeItem(rootNode, SWT.CHECK);
					treeNode.setData(file);
					treeNode.setText(file.getName());

					System.out.println(file.getPath());
					createTree(treeNode, file);
				}
			}
		}
	}

	private void populateTable(Tree tree) {
		TreeItem selectedNode[] = tree.getSelection();
		File selectedFile = null;

		for (int i = 0; i < selectedNode.length; i++) {
			if (selectedNode[i] != null) {
				selectedFile = (File) selectedNode[i].getData();
				showFiles(selectedFile);
			}
		}
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
					if (fileSize >= 512) {
						columns[1] = fileSize / 1024 + "KB";
					} else {
						columns[1] = fileSize + "Bytes";
					}
					columns[2] = filePath;
					tableItem.setText(columns);
					table.setLinesVisible(true);
				}
			}

		}
	}

	private void showPic(String path) {
		FileInputStream fin;
		try {
			fin = new FileInputStream(path);
			Image image = new Image(Display.getCurrent(), fin);

			label.setText("test");
			label.setImage(image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SWTException e) {
			e.printStackTrace();
			label.setText("Unable to load Image");
		}
	}

	protected void showHexDump(final String filePath) {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				hexview.ViewHexDump(filePath);
			}
		};
		BusyIndicator.showWhile(AndrospyMain.shell.getDisplay(), runnable);
	}
}
