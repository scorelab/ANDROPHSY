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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TableColumn;


/**
 * @author indeewari
 *
 */
public class AppListDummy extends Composite {
	private Table table;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AppListDummy(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnApplicatonName = new TableColumn(table, SWT.NONE);
		tblclmnApplicatonName.setWidth(139);
		tblclmnApplicatonName.setText("Applicaton Name");
		
		TableColumn tblclmnPackage = new TableColumn(table, SWT.NONE);
		tblclmnPackage.setWidth(100);
		tblclmnPackage.setText("Package");

	}

	public void populate(String type){
		String line = "";
		String values[] = new String[2];
		String filename = AndrospyMain.gb_CasePath + "Analysis/";
		if(type == "Enable"){
			filename += "pkg_list_enabled.txt";
		} else if (type == "Disable"){
			filename += "pkg_list_disabled.txt";
		} else if (type == "3p"){
			filename += "pkg_list_3p.txt";
		} else if (type == "System"){
			filename += "pkg_list_system.txt";
		} else if (type == "Uninstall"){
			filename += "pkg_list_uninstalled.txt";
		}
		
		File file = new File(filename);
		try {
			System.out.println(filename);
			if (file.exists()) {
				BufferedReader bufin = new BufferedReader(
						new InputStreamReader(new FileInputStream(file)));
				while ((line = bufin.readLine()) != null) {
					String token[] = line.split("=");
					values[0] = token[1];
					token = token[0].split(":");
					values[1] = token[1];
					TableItem item = new TableItem(table, SWT.NONE);
					item.setText(values);
				}
				table.setLinesVisible(true);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
