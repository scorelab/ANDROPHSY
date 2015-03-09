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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TabItem;


/**
 * @author indeewari
 *
 */
public class AppList extends Composite {

	private static AppListDummy appdummyuninstall;
	private static AppListDummy appdummysystem;
	private static AppListDummy appdummy3p;
	private static AppListDummy appdummydisable;
	private static AppListDummy appdummyenable;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AppList(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		
		TabItem tbtmEnabledApplications = new TabItem(tabFolder, SWT.NONE);
		tbtmEnabledApplications.setText("Enabled Applications");
		appdummyenable = new AppListDummy(tabFolder, SWT.NONE);
		tbtmEnabledApplications.setControl(appdummyenable);
		
		TabItem tbtmDisabledApplications = new TabItem(tabFolder, SWT.NONE);
		tbtmDisabledApplications.setText("Disabled Applications");
		appdummydisable = new AppListDummy(tabFolder, SWT.NONE);
		tbtmDisabledApplications.setControl(appdummydisable);
		
		TabItem tbtmrdPartyApplications = new TabItem(tabFolder, SWT.NONE);
		tbtmrdPartyApplications.setText("3rd Party Applications");
		appdummy3p = new AppListDummy(tabFolder, SWT.NONE);
		tbtmrdPartyApplications.setControl(appdummy3p);
		
		TabItem tbtmSystemApplication = new TabItem(tabFolder, SWT.NONE);
		tbtmSystemApplication.setText("System Application");
		appdummysystem = new AppListDummy(tabFolder, SWT.NONE);
		tbtmSystemApplication.setControl(appdummysystem);
		
		TabItem tbtmUninstalledApplications = new TabItem(tabFolder, SWT.NONE);
		tbtmUninstalledApplications.setText("Uninstalled Applications");
		appdummyuninstall = new AppListDummy(tabFolder, SWT.NONE);
		tbtmUninstalledApplications.setControl(appdummyuninstall);
		
		populateData();
	}

	public static void populateData(){
		appdummyenable.populate("Enable");
		appdummydisable.populate("Disable");
		appdummy3p.populate("3p");
		appdummysystem.populate("System");
		appdummyuninstall.populate("Uninstall");
	}
}
