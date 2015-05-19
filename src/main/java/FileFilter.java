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
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.wb.swt.SWTResourceManager;


/**
 * @author indeewari
 *
 */
public class FileFilter extends Composite {

	private Table tblGood;
	private Table tblBad;
	private Table tblUnkown;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FileFilter(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		Label lblApplicationFileFilter = new Label(this, SWT.NONE);
		lblApplicationFileFilter.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblApplicationFileFilter.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 1, 1));
		lblApplicationFileFilter.setText("Application File Filter");
		
		Button btnFilterApplicationFiles = new Button(this, SWT.NONE);
		btnFilterApplicationFiles.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				Filter();
			}
		});
		btnFilterApplicationFiles.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnFilterApplicationFiles.setText("Filter Application Files (.apk)");
		
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TabItem tbtmKnownGoodFiles = new TabItem(tabFolder, SWT.NONE);
		tbtmKnownGoodFiles.setText("Known Good Files");
		
		Composite cmpGood = new Composite(tabFolder, SWT.NONE);
		tbtmKnownGoodFiles.setControl(cmpGood);
		cmpGood.setLayout(new GridLayout(1, false));
		
		tblGood = new Table(cmpGood, SWT.BORDER | SWT.FULL_SELECTION);
		tblGood.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tblGood.setHeaderVisible(true);
		tblGood.setLinesVisible(true);
		
		TableColumn tblclmnHashValue = new TableColumn(tblGood, SWT.NONE);
		tblclmnHashValue.setWidth(200);
		tblclmnHashValue.setText("Hash Value");
		
		TableColumn tblclmnFile = new TableColumn(tblGood, SWT.NONE);
		tblclmnFile.setWidth(100);
		tblclmnFile.setText("File");
		
		TabItem tbtmKnownMaliciousFiles = new TabItem(tabFolder, SWT.NONE);
		tbtmKnownMaliciousFiles.setText("Known Malicious Files");
		
		Composite cmpBad = new Composite(tabFolder, SWT.NONE);
		tbtmKnownMaliciousFiles.setControl(cmpBad);
		cmpBad.setLayout(new GridLayout(1, false));
		
		tblBad = new Table(cmpBad, SWT.BORDER | SWT.FULL_SELECTION);
		tblBad.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tblBad.setHeaderVisible(true);
		tblBad.setLinesVisible(true);
		
		tblclmnHashValue = new TableColumn(tblBad, SWT.NONE);
		tblclmnHashValue.setWidth(200);
		tblclmnHashValue.setText("Hash Value");
		
		tblclmnFile = new TableColumn(tblBad, SWT.NONE);
		tblclmnFile.setWidth(100);
		tblclmnFile.setText("File");
		
		TabItem tbtmUnknownFile = new TabItem(tabFolder, SWT.NONE);
		tbtmUnknownFile.setText("Unknown Files");
		
		Composite cmpUnknown = new Composite(tabFolder, SWT.NONE);
		tbtmUnknownFile.setControl(cmpUnknown);
		cmpUnknown.setLayout(new GridLayout(1, false));
		
		tblUnkown = new Table(cmpUnknown, SWT.BORDER | SWT.FULL_SELECTION);
		tblUnkown.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tblUnkown.setHeaderVisible(true);
		tblUnkown.setLinesVisible(true);

		tblclmnHashValue = new TableColumn(tblUnkown, SWT.NONE);
		tblclmnHashValue.setWidth(200);
		tblclmnHashValue.setText("Hash Value");
		
		tblclmnFile = new TableColumn(tblUnkown, SWT.NONE);
		tblclmnFile.setWidth(100);
		tblclmnFile.setText("File");
	}

	protected void Filter() {
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				Filter_Dispatch();
				
			}
		};
		BusyIndicator.showWhile(AndrospyMain.shell.getDisplay(), runnable);	
	}
	
	protected void Filter_Dispatch() {
		try {
			File dir = new File(AndrospyMain.gb_CasePath + "Acquire/system/app");
			if(!dir.exists()){
				return;
			}
			
			String line = "", result ="", err = "";
			String values[] = new String[2];
			
			tblGood.removeAll();
			tblGood.setItemCount(0);			
			Process proc = Runtime.getRuntime().exec("md5deep -r " + AndrospyMain.gb_CasePath 
					+ "Acquire/system/app/ -M files/known_good_hash.txt -b");
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while((line = bufin.readLine()) != null){
				TableItem item = new TableItem(tblGood, SWT.NONE);
				System.out.println("line:" + line);
				values = line.split("  ");
				System.out.println(values[0] + "line:" + values[1]);
				item.setText(values);
				tblGood.setLinesVisible(true);
			}
			
			tblBad.removeAll();
			tblBad.setItemCount(0);
			proc = Runtime.getRuntime().exec("md5deep -r " + AndrospyMain.gb_CasePath 
					+ "Acquire/system/app/ -M files/known_bad_hash.txt -b");
			bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while((line = bufin.readLine()) != null){
				TableItem item = new TableItem(tblBad, SWT.NONE);
				values = line.split("  ");
				item.setText(values);
				tblBad.setLinesVisible(true);
			}
			
			tblUnkown.removeAll();
			tblUnkown.setItemCount(0);
			proc = Runtime.getRuntime().exec("md5deep -r " + AndrospyMain.gb_CasePath 
					+ "Acquire/system/app/ -X files/known_good_hash.txt -X files/known_bad_hash.txt -b");
			bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while((line = bufin.readLine()) != null){
				TableItem item = new TableItem(tblUnkown, SWT.NONE);
				values = line.split("  ");
				item.setText(values);
				tblUnkown.setLinesVisible(true);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
