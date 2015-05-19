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

import java.util.ArrayList;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;

/**
 * @author indeewari
 *
 */
public class EventLogViewer extends Composite {

	private Button btnCallLog;
	private Button btnSmsLog;
	private Button btnMmsLog;
	private ArrayList<String> checked;
	private String parentOption;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public EventLogViewer(Composite parent, int style, String baseoption, ArrayList<String> list) {
		super(parent, style);
		setLayout(null);
		this.parentOption = baseoption;
		this.checked = list;

		btnCallLog = new Button(this, SWT.CHECK);
		btnCallLog.setBounds(10, 38, 115, 24);
		btnCallLog.setText("Call Log");
		if (checked.indexOf("Call Logs") == -1) {
			btnCallLog.setSelection(false);
		} else {
			btnCallLog.setSelection(true);
		}

		btnSmsLog = new Button(this, SWT.CHECK);
		btnSmsLog.setBounds(10, 63, 115, 24);
		btnSmsLog.setText("SMS Log");
		if (checked.indexOf("SMS Logs") == -1) {
			btnSmsLog.setSelection(false);
		} else {
			btnSmsLog.setSelection(true);
		}

		btnMmsLog = new Button(this, SWT.CHECK);
		btnMmsLog.setBounds(10, 88, 115, 24);
		btnMmsLog.setText("MMS Log");
		if (checked.indexOf("MMS Logs") == -1) {
			btnMmsLog.setSelection(false);
		} else {
			btnMmsLog.setSelection(true);
		}

		Label lblNewLabel = new Label(this, SWT.NONE);
		lblNewLabel.setBounds(160, 10, 129, 17);
		lblNewLabel.setText("Event Log Options");

		Button btnCancel = new Button(this, SWT.NONE);
		btnCancel.setBounds(336, 215, 91, 29);
		btnCancel.setText("Cancel");
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getParent().dispose();
			}
		});

		Button btnOk = new Button(this, SWT.NONE);
		btnOk.setBounds(238, 215, 91, 29);
		btnOk.setText("OK");
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getSelectedItems();
				getParent().dispose();
			}
		});

	}

	protected void getSelectedItems() {
		if (btnCallLog.getSelection()) {
			checked.add("Call Logs");
		} else {
			checked.remove("Call Logs");
		}

		if (btnSmsLog.getSelection()) {
			checked.add("SMS Logs");
		} else {
			checked.remove("SMS Logs");
		}

		if (btnMmsLog.getSelection()) {
			checked.add("MMS Logs");
		} else {
			checked.remove("MMS Logs");
		}

	}
}
