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
public class AppDummyTab extends Composite {

	private PresentationUtil cmpAllItems;
	private TabFolder tabFolder;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public AppDummyTab(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));

		tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		TabItem tbtmAllCalls = new TabItem(tabFolder, SWT.NONE);
		tbtmAllCalls.setText("ALL");

		cmpAllItems = new PresentationUtil(tabFolder, SWT.NONE);
		tbtmAllCalls.setControl(cmpAllItems);

	}

	public void PopulateData(String sMode) {
		TabItem item[] = tabFolder.getItems();
		for (int i = 1; i < item.length; i++) {
			item[i].dispose();
		}
		if (sMode == "User Credentials") {
			item[0].dispose();

			TabItem tbtmprimaryacc = new TabItem(tabFolder, SWT.NONE);
			tbtmprimaryacc.setText("Primary Account");
			PresentationUtil cmpprimaryAcc = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmprimaryacc.setControl(cmpprimaryAcc);

			TabItem tbtmotheracc = new TabItem(tabFolder, SWT.NONE);
			tbtmotheracc.setText("Other Accounts");
			PresentationUtil cmpotheracct = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmotheracc.setControl(cmpotheracct);

			cmpprimaryAcc.PopulateData("PRIMARY_ACC");
			cmpotheracct.PopulateData("OTHER_ACC");

		} else if (sMode == "Facebook") {
			item[0].dispose();

			TabItem tbtmFbContact = new TabItem(tabFolder, SWT.NONE);
			tbtmFbContact.setText("Facebook Contact");
			PresentationUtil cmpfbcontact = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmFbContact.setControl(cmpfbcontact);

			TabItem tbtmFbMessages = new TabItem(tabFolder, SWT.NONE);
			tbtmFbMessages.setText("Facebook Message");
			PresentationUtil cmpfbmessage = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmFbMessages.setControl(cmpfbmessage);

			TabItem tbtmFbNotify = new TabItem(tabFolder, SWT.NONE);
			tbtmFbNotify.setText("Facebook Notifications");
			PresentationUtil cmpfbnotify = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmFbNotify.setControl(cmpfbnotify);

			cmpfbcontact.PopulateData("FB_CONTACT");
			cmpfbmessage.PopulateData("FB_MESSAGE");
			cmpfbnotify.PopulateData("FB_NOTIFICATION");

		} else if (sMode == "LinkedIn") {
			item[0].dispose();

			TabItem tbtmLinkedProfile = new TabItem(tabFolder, SWT.NONE);
			tbtmLinkedProfile.setText("LinkedIn Profile");
			PresentationUtil cmplinkedprofile = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmLinkedProfile.setControl(cmplinkedprofile);

			TabItem tbtmLinkedConns = new TabItem(tabFolder, SWT.NONE);
			tbtmLinkedConns.setText("LinkedIn Connections");
			PresentationUtil cmplinkedcons = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmLinkedConns.setControl(cmplinkedcons);

			TabItem tbtmLinkedInv = new TabItem(tabFolder, SWT.NONE);
			tbtmLinkedInv.setText("LinkedIn Invitations");
			PresentationUtil cmplinkedinv = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmLinkedInv.setControl(cmplinkedinv);

			TabItem tbtmLinkedMsg = new TabItem(tabFolder, SWT.NONE);
			tbtmLinkedMsg.setText("LinkedIn Messages");
			PresentationUtil cmplinkedmsg = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmLinkedMsg.setControl(cmplinkedmsg);

			cmplinkedprofile.PopulateData("LINKEDIN_PROFILE");
			cmplinkedcons.PopulateData("LINKEDIN_CONNECTIONS");
			cmplinkedinv.PopulateData("LINKEDIN_INVITATIONS");
			cmplinkedmsg.PopulateData("LINKEDIN_MESSAGES");

		} else if (sMode == "Skype") {
			item[0].dispose();

			TabItem tbtmSkypeProfile = new TabItem(tabFolder, SWT.NONE);
			tbtmSkypeProfile.setText("Skype Account");
			PresentationUtil cmplinkedprofile = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmSkypeProfile.setControl(cmplinkedprofile);

			TabItem tbtmSkypeContacts = new TabItem(tabFolder, SWT.NONE);
			tbtmSkypeContacts.setText("Skype Contacts");
			PresentationUtil cmpskypecons = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmSkypeContacts.setControl(cmpskypecons);

			TabItem tbtmSkypeCalls = new TabItem(tabFolder, SWT.NONE);
			tbtmSkypeCalls.setText("Skype Calls");
			PresentationUtil cmpskypecalls = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmSkypeCalls.setControl(cmpskypecalls);

			TabItem tbtmSkypeMsg = new TabItem(tabFolder, SWT.NONE);
			tbtmSkypeMsg.setText("Skype Messages");
			PresentationUtil cmpskypemsg = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmSkypeMsg.setControl(cmpskypemsg);

			cmplinkedprofile.PopulateData("Skype Account");
			cmpskypecons.PopulateData("Skype Contacts");
			cmpskypecalls.PopulateData("Skype Calls");
			cmpskypemsg.PopulateData("Skype Messages");

		} else if (sMode == "Default Browser") {
			item[0].dispose();

			TabItem tbtmBrowserHistory = new TabItem(tabFolder, SWT.NONE);
			tbtmBrowserHistory.setText("Default Browser History");
			PresentationUtil cmpdefbrowhistory = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmBrowserHistory.setControl(cmpdefbrowhistory);

			TabItem tbtmWebSearch = new TabItem(tabFolder, SWT.NONE);
			tbtmWebSearch.setText("Web lk.score.androphsy.main.Search");
			PresentationUtil cmpwebsearch = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmWebSearch.setControl(cmpwebsearch);

			TabItem tbtnbookmark = new TabItem(tabFolder, SWT.NONE);
			tbtnbookmark.setText("Default Browser Bookmarks");
			PresentationUtil cmpbookmark = new PresentationUtil(tabFolder, SWT.NONE);
			tbtnbookmark.setControl(cmpbookmark);

			TabItem tbtmcookies = new TabItem(tabFolder, SWT.NONE);
			tbtmcookies.setText("Default Browser Cookies");
			PresentationUtil cmpcookies = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmcookies.setControl(cmpcookies);

			TabItem tbtmwebformdata = new TabItem(tabFolder, SWT.NONE);
			tbtmwebformdata.setText("Web Form and Data");
			PresentationUtil cmpformdata = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmwebformdata.setControl(cmpformdata);

			cmpdefbrowhistory.PopulateData("DEFAULT_BROWSER_HISTORY");
			cmpwebsearch.PopulateData("DEFAULT_BROWSER_SEARCH");
			cmpbookmark.PopulateData("DEFAULT_BROWSER_BOOKMARK");
			cmpcookies.PopulateData("DEFAULT_BROWSER_COOKIES");
			cmpformdata.PopulateData("DEFAULT_BROWSER_WEB_FORM");

		} else if (sMode == "Chrome") {
			item[0].dispose();

			TabItem tbtmBrowserHistory = new TabItem(tabFolder, SWT.NONE);
			tbtmBrowserHistory.setText("Chrome Browser History");
			PresentationUtil cmpdefbrowhistory = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmBrowserHistory.setControl(cmpdefbrowhistory);

			TabItem tbtmWebSearch = new TabItem(tabFolder, SWT.NONE);
			tbtmWebSearch.setText("Web lk.score.androphsy.main.Search");
			PresentationUtil cmpwebsearch = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmWebSearch.setControl(cmpwebsearch);

			TabItem tbtmcookies = new TabItem(tabFolder, SWT.NONE);
			tbtmcookies.setText("Chrome Browser Cookies");
			PresentationUtil cmpcookies = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmcookies.setControl(cmpcookies);

			TabItem tbtbrowcreditcard = new TabItem(tabFolder, SWT.NONE);
			tbtbrowcreditcard.setText("Used Credit Cards");
			PresentationUtil cmpbrowcreditcard = new PresentationUtil(tabFolder, SWT.NONE);
			tbtbrowcreditcard.setControl(cmpbrowcreditcard);

			cmpdefbrowhistory.PopulateData("CHROME_BROWSER_HISTORY");
			cmpwebsearch.PopulateData("CHROME_BROWSER_SEARCH");
			cmpcookies.PopulateData("CHROME_BROWSER_COOKIES");
			cmpbrowcreditcard.PopulateData("CHROME_CREDIT_CARD");

		} else if (sMode == "Firefox") {
			item[0].dispose();

			TabItem tbtmBrowserHistory = new TabItem(tabFolder, SWT.NONE);
			tbtmBrowserHistory.setText("Firefox Browser History");
			PresentationUtil cmpdefbrowhistory = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmBrowserHistory.setControl(cmpdefbrowhistory);

			TabItem tbtmWebSearch = new TabItem(tabFolder, SWT.NONE);
			tbtmWebSearch.setText("Web lk.score.androphsy.main.Search");
			PresentationUtil cmpwebsearch = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmWebSearch.setControl(cmpwebsearch);

			TabItem tbtbrowbookmark = new TabItem(tabFolder, SWT.NONE);
			tbtbrowbookmark.setText("Firefox Bookmarks");
			PresentationUtil cmpfirefoxbookmark = new PresentationUtil(tabFolder, SWT.NONE);
			tbtbrowbookmark.setControl(cmpfirefoxbookmark);

			TabItem tbtmcookies = new TabItem(tabFolder, SWT.NONE);
			tbtmcookies.setText("Firefox Browser Cookies");
			PresentationUtil cmpcookies = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmcookies.setControl(cmpcookies);

			cmpdefbrowhistory.PopulateData("Firefox Browser History");
			cmpwebsearch.PopulateData("Firefox lk.score.androphsy.main.Search");
			cmpcookies.PopulateData("firefox Cookies");
			cmpfirefoxbookmark.PopulateData("Firefox Bookmarks");

		} else if (sMode == "Call Log") {
			TabItem tbtmReceivedCalls = new TabItem(tabFolder, SWT.NONE);
			tbtmReceivedCalls.setText("Incomming Calls");
			PresentationUtil cmpReceivedCalls = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmReceivedCalls.setControl(cmpReceivedCalls);

			TabItem tbtmOutGoingCalls = new TabItem(tabFolder, SWT.NONE);
			tbtmOutGoingCalls.setText("Out Going Calls");
			PresentationUtil cmpOutCalls = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmOutGoingCalls.setControl(cmpOutCalls);

			TabItem tbtmMissedCalls = new TabItem(tabFolder, SWT.NONE);
			tbtmMissedCalls.setText("Missed Calls");
			PresentationUtil cmpMissedCalls = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmMissedCalls.setControl(cmpMissedCalls);

			TabItem tbtmCancelledCalls = new TabItem(tabFolder, SWT.NONE);
			tbtmCancelledCalls.setText("Cancelled Calls");
			PresentationUtil cmpCancelledCalls = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmCancelledCalls.setControl(cmpCancelledCalls);

			cmpAllItems.PopulateData("ALL_CALL_LOG");
			cmpReceivedCalls.PopulateData("INCOMING_CALLS");
			cmpMissedCalls.PopulateData("MISSED_CALLS");
			cmpOutCalls.PopulateData("OUT_CALLS");
			cmpCancelledCalls.PopulateData("CANCELLED_CALLS");

		} else if (sMode == "SMS Log") {
			TabItem tbtmReceivedCalls = new TabItem(tabFolder, SWT.NONE);
			tbtmReceivedCalls.setText("SMS Inbox");
			PresentationUtil cmpsmsin = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmReceivedCalls.setControl(cmpsmsin);

			TabItem tbtmOutGoingCalls = new TabItem(tabFolder, SWT.NONE);
			tbtmOutGoingCalls.setText("SMS Outbox");
			PresentationUtil cmpsmsout = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmOutGoingCalls.setControl(cmpsmsout);

			cmpAllItems.PopulateData("ALL_SMS_LOG");
			cmpsmsin.PopulateData("SMS_INBOX");
			cmpsmsout.PopulateData("SMS_OUTBOX");

		} else if (sMode == "MMS Log") {
			TabItem tbtmReceivedCalls = new TabItem(tabFolder, SWT.NONE);
			tbtmReceivedCalls.setText("Received Calls");
			PresentationUtil cmpmmsin = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmReceivedCalls.setControl(cmpmmsin);

			TabItem tbtmOutGoingCalls = new TabItem(tabFolder, SWT.NONE);
			tbtmOutGoingCalls.setText("Out Going Calls");
			PresentationUtil cmpmmsout = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmOutGoingCalls.setControl(cmpmmsout);

			cmpAllItems.PopulateData("ALL_MMS_LOG");
			cmpmmsin.PopulateData("MMS_INBOX");
			cmpmmsout.PopulateData("MMS_OUTBOX");

		} else if (sMode == "Bluetooth") {
			item[0].dispose();

			TabItem tbtmReceivedCalls = new TabItem(tabFolder, SWT.NONE);
			tbtmReceivedCalls.setText("Devices In Visinity");
			PresentationUtil cmpbtvisinity = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmReceivedCalls.setControl(cmpbtvisinity);

			TabItem tbtmOutGoingCalls = new TabItem(tabFolder, SWT.NONE);
			tbtmOutGoingCalls.setText("Devices Contacted");
			PresentationUtil cmpbtused = new PresentationUtil(tabFolder, SWT.NONE);
			tbtmOutGoingCalls.setControl(cmpbtused);

			cmpbtvisinity.PopulateData("BT_SEEN");
			cmpbtused.PopulateData("BT_USED");
		}

	}

}
