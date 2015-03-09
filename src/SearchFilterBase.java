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
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.custom.StackLayout;


/**
 * @author indeewari
 *
 */
public class SearchFilterBase extends Composite {

	private StackLayout stacklayout = new StackLayout();
	private FileFilter filefilter;
	private Search search;
	private Composite composite;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SearchFilterBase(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		ToolBar toolBar = new ToolBar(this, SWT.FLAT | SWT.RIGHT);
		toolBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		
		ToolItem tltmFilterApplicationFiles = new ToolItem(toolBar, SWT.NONE);
		tltmFilterApplicationFiles.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				stacklayout.topControl = filefilter;
				composite.layout();
			}
		});
		tltmFilterApplicationFiles.setText("Filter Application Files");
		
		ToolItem tltmSearch = new ToolItem(toolBar, SWT.NONE);
		tltmSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				stacklayout.topControl = search;
				composite.layout();
			}
		});
		tltmSearch.setText("Search");
		
		composite = new Composite(this, SWT.NONE);
		composite.setLayout(stacklayout );
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));

		filefilter = new FileFilter(composite, SWT.NONE);
		search = new Search(composite, SWT.NONE);
		
		stacklayout.topControl = search;
		composite.layout();
	}
}
