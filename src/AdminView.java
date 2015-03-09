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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.custom.StackLayout;


/**
 * @author indeewari
 *
 */
public class AdminView extends Composite {

	private Composite composite;
	private UserManagement usermngt;
	private CaseTransfer casetransfer;
	private StackLayout stacklayout;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AdminView(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		stacklayout = new StackLayout();
		
		ToolBar toolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		GridData gd_toolBar = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_toolBar.heightHint = 29;
		toolBar.setLayoutData(gd_toolBar);
		
		ToolItem tltmUserManagement = new ToolItem(toolBar, SWT.NONE);
		tltmUserManagement.setText("User Management");
		tltmUserManagement.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				stacklayout.topControl = usermngt;
				composite.layout();
			}
		});
		
		ToolItem tltmCaseManagement = new ToolItem(toolBar, SWT.NONE);
		tltmCaseManagement.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				stacklayout.topControl = casetransfer;
				composite.layout();
			}
		});
		tltmCaseManagement.setText("Case Management");
		
		new Label(this, SWT.NONE);
		composite = new Composite(this, SWT.NONE);
		composite.setLayout(stacklayout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		usermngt = new UserManagement(composite, SWT.NONE);
		casetransfer = new CaseTransfer(composite, SWT.NONE);
		
		stacklayout.topControl = usermngt;
	}
}
