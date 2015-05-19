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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


/**
 * @author indeewari
 *
 */
public class AcquisitionTop extends Composite {

	private StackLayout stacklayout;
	private DataCollection datacollection;
	private Composite composite;
	private DataExtract dataextract;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public AcquisitionTop(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		stacklayout = new StackLayout();
		
		
		ToolBar toolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		
		ToolItem tltmDataCollection = new ToolItem(toolBar, SWT.NONE);
		tltmDataCollection.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				stacklayout.topControl = datacollection;
				composite.layout();
			}
		});
		tltmDataCollection.setText("Data Collection");
		
		
		ToolItem tltmDataExtraction = new ToolItem(toolBar, SWT.NONE);
		tltmDataExtraction.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				stacklayout.topControl = dataextract;
				composite.layout();
			}
		});
		
		tltmDataExtraction.setText("Data Extraction");

		composite = new Composite(this, SWT.NONE);
		composite.setLayout(stacklayout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		datacollection 	= new DataCollection(composite, SWT.NONE);
		dataextract 	= new DataExtract(composite, SWT.NONE);

		stacklayout.topControl = datacollection;
		composite.layout();
	}

}
