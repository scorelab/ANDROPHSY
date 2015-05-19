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
public class Acquisition extends Composite {

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Acquisition(Composite parent, int style) {
		super(parent, SWT.V_SCROLL);
		setLayout(new GridLayout(1, false));

		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TabItem tbtmPersistantStorage = new TabItem(tabFolder, SWT.NONE);
		tbtmPersistantStorage.setText("Persistant Storage");
		tbtmPersistantStorage.setControl(new NoneVolCollection(tabFolder, SWT.NONE));

		TabItem tbtmVolatileMemory = new TabItem(tabFolder, SWT.NONE);
		tbtmVolatileMemory.setText("Volatile Memory");
		tbtmVolatileMemory.setControl(new VolatileCollection(tabFolder, SWT.NONE));

		TabItem tbtmLogcollection = new TabItem(tabFolder, SWT.NONE);
		tbtmLogcollection.setText("Log Collection");
		tbtmLogcollection.setControl(new LogCollection(tabFolder, SWT.BORDER));
	}
}
