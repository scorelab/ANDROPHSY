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
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


/**
 * @author indeewari
 *
 */
public class CaseManagement extends Composite {

	private NewCase composite;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CaseManagement(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
				
		ToolBar toolBar = new ToolBar(this, SWT.FILL);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		ToolItem tltmNew = new ToolItem(toolBar, SWT.NONE);
		tltmNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				new CreateCase(getShell()).run();
				composite.PopulateCases(new ConnectDb().getConnection());
			}
		});
		tltmNew.setText("New Case");
		
		ToolItem tltmOpenManage = new ToolItem(toolBar, SWT.NONE);
		tltmOpenManage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				composite.setVisible(true);
			}
		});
		tltmOpenManage.setText("Open & Manage Existing Case");
		
		composite = new NewCase(this, SWT.NONE);
		GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		composite.setVisible(true);
		composite.setLayoutData(gd_composite);
		
	}
}
