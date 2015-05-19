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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * @author indeewari
 *
 */
public class SocialNetOptionTemp extends Composite {

	private Button btnProfile;
	private Button btnConnections;
	private Button btnInvitations;
	private Button btnMessages;
	private Button btnCancel;
	private String parentOption;
	private ArrayList<String> checked;
	private Button btnFbNotification;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SocialNetOptionTemp(Composite parent, int style, String baseoption,
	                           ArrayList<String> list) {
		super(parent, style);

		this.parentOption = baseoption;
		this.checked = list;

		if (baseoption == "LINKEDIN") {
			btnProfile = new Button(this, SWT.CHECK);
			btnProfile.setText("Profile");
			if (checked.indexOf("LINKEDIN_PROFILE") == -1) {
				btnProfile.setSelection(false);
			} else {
				btnProfile.setSelection(true);
			}
			btnProfile.setBounds(10, 10, 280, 24);
		}
		setLayout(null);

		btnConnections = new Button(this, SWT.CHECK);
		if (baseoption == "LINKEDIN") {
			btnConnections.setText("Connections");
			btnConnections.setBounds(10, 34, 280, 24);
			if (checked.indexOf("LINKEDIN_CONNECTIONS") == -1) {
				btnConnections.setSelection(false);
			} else {
				btnConnections.setSelection(true);
			}
		} else if (baseoption == "FACEBOOK") {
			btnConnections.setText("Contacts");
			btnConnections.setBounds(10, 10, 280, 24);
			if (checked.indexOf("FACEBOOK_CONTACTS") == -1) {
				btnConnections.setSelection(false);
			} else {
				btnConnections.setSelection(true);
			}
		}

		if (baseoption == "LINKEDIN") {
			btnInvitations = new Button(this, SWT.CHECK);
			btnInvitations.setText("Invitations");
			if (checked.indexOf("LINKEDIN_INVITATIONS") == -1) {
				btnInvitations.setSelection(false);
			} else {
				btnInvitations.setSelection(true);
			}
			btnInvitations.setBounds(10, 58, 280, 24);
		}

		btnMessages = new Button(this, SWT.CHECK);
		btnMessages.setBounds(10, 31, 112, 24);
		btnMessages.setText("Messages");
		if (baseoption == "LINKEDIN") {
			btnMessages.setBounds(10, 82, 280, 24);
			if (checked.indexOf("LINKEDIN_MESSAGES") == -1) {
				btnMessages.setSelection(false);
			} else {
				btnMessages.setSelection(true);
			}
		} else if (baseoption == "FACEBOOK") {
			btnMessages.setBounds(10, 34, 280, 24);
			if (checked.indexOf("FACEBOOK_MESSAGES") == -1) {
				btnMessages.setSelection(false);
			} else {
				btnMessages.setSelection(true);
			}
		}

		if (baseoption == "FACEBOOK") {
			btnFbNotification = new Button(this, SWT.CHECK);
			btnFbNotification.setText("Notification");
			if (checked.indexOf("FACEBOOK_NOTIFICATIONS") == -1) {
				btnFbNotification.setSelection(false);
			} else {
				btnFbNotification.setSelection(true);
			}
			btnFbNotification.setBounds(10, 58, 280, 24);
		}

		Button btnOk = new Button(this, SWT.NONE);
		btnOk.setBounds(241, 134, 89, 29);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getSelectedItems();
				getParent().dispose();
			}
		});
		GridData gd_btnOk = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd_btnOk.widthHint = 98;
		btnOk.setLayoutData(gd_btnOk);
		btnOk.setText("OK");

		btnCancel = new Button(this, SWT.NONE);
		btnCancel.setBounds(337, 134, 82, 29);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getParent().dispose();
			}
		});
		GridData gd_btnCancel = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_btnCancel.widthHint = 99;
		btnCancel.setLayoutData(gd_btnCancel);
		btnCancel.setText("Cancel");

	}

	protected void getSelectedItems() {
		if ((parentOption == "LINKEDIN") && (btnProfile.getSelection())) {
			checked.add(parentOption + "_PROFILE");
		} else {
			checked.remove(parentOption + "_PROFILE");
		}
		if ((parentOption == "LINKEDIN") && (btnConnections.getSelection())) {
			checked.add(parentOption + "_CONNECTIONS");
		} else {
			checked.remove(parentOption + "_CONNECTIONS");
		}
		if ((parentOption == "LINKEDIN") && (btnInvitations.getSelection())) {
			checked.add(parentOption + "_INVITATIONS");
		} else {
			checked.remove(parentOption + "_INVITATIONS");
		}
		if (btnMessages.getSelection()) {
			checked.add(parentOption + "_MESSAGES");
		} else {
			checked.remove(parentOption + "_MESSAGES");
		}
		if ((parentOption == "FACEBOOK") && (btnConnections.getSelection())) {
			checked.add(parentOption + "_CONTACTS");
		} else {
			checked.remove(parentOption + "_CONTACTS");
		}
		if ((parentOption == "FACEBOOK") && (btnFbNotification.getSelection())) {
			checked.add(parentOption + "_NOTIFICATIONS");
		} else {
			checked.remove(parentOption + "_NOTIFICATIONS");
		}
	}
}
