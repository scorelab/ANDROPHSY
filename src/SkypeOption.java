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

import java.util.ArrayList;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;


/**
 * @author indeewari
 *
 */
public class SkypeOption extends Composite {

	private String parentOption;
	private ArrayList<String> checked;
	private Button btnAccounts;
	private Button btnContacts;
	private Button btnCalls;
	private Button btnChats;
	private Button btnMessages;
	private Button btnCancel;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SkypeOption(Composite parent, int style, String baseoption, ArrayList<String> list) {
		super(parent, style);
		
		this.parentOption = baseoption;
		this.checked = list;
		
		Label lblSkypeOptions = new Label(this, SWT.NONE);
		lblSkypeOptions.setBounds(176, 10, 97, 17);
		lblSkypeOptions.setText("Skype Options");
		
		btnAccounts = new Button(this, SWT.CHECK);
		btnAccounts.setBounds(10, 41, 115, 24);
		btnAccounts.setText("Accounts");
		
		btnContacts = new Button(this, SWT.CHECK);
		btnContacts.setBounds(10, 65, 115, 24);
		btnContacts.setText("Contacts");
		
		btnCalls = new Button(this, SWT.CHECK);
		btnCalls.setBounds(10, 89, 115, 24);
		btnCalls.setText("Calls");
		
		btnMessages = new Button(this, SWT.CHECK);
		btnMessages.setBounds(10, 113, 115, 24);
		btnMessages.setText("Messages");

		Button btnOk = new Button(this, SWT.NONE);
		btnOk.setBounds(244, 171, 89, 29);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getSelectedItems();
				getParent().dispose();
			}
		});
		btnOk.setText("OK");
		
		btnCancel = new Button(this, SWT.NONE);
		btnCancel.setBounds(339, 171, 82, 29);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getParent().dispose();
			}
		});
		btnCancel.setText("Cancel");
	}

	protected void getSelectedItems() {
		if(btnAccounts.getSelection()){
			checked.add("Skype Account");
		} else{
			checked.remove("Skype Account");
		}
		if(btnCalls.getSelection()){
			checked.add("Skype Calls");
		} else{
			checked.remove("Skype Calls");
		}		
		if(btnContacts.getSelection()){
			checked.add("Skype Contacts");
		} else{
			checked.remove("Skype Contacts");
		}
		if(btnMessages.getSelection()){
			checked.add("Skype Messages");
		} else{
			checked.remove("Skype Messages");
		}
		
	}
	
}
