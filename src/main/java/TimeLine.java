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
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * @author indeewari
 *
 */
public class TimeLine extends Composite {

	private DateTime dateTimeFrom;
	private DateTime dateTimeTo;
	private TimeLineDummy timelinedummy;
	private TimeLineDummy timelineevent;
	private Button btnBeforeDate;
	private Button btnAfterDate;
	private TimeLineDummy timelinesms;
	private TimeLineDummy timelinebrowserhist;
	private TimeLineDummy timelinebookmark;
	private TimeLineDummy timelinewebsearch;
	private DateTime timeFrom;
	private DateTime timeTo;
	private TimeLineDummy timelinecalendar;
	private TimeLineDummy timelinegmail;
	private TimeLineDummy timelineyoutube;
	private TimeLineDummy timelinefbcontact;
	private TimeLineDummy timelinefbmessage;
	private TimeLineDummy timelinefbnotification;
	private TimeLineDummy timelinelinkedinprofile;
	private TimeLineDummy timelinelinkedinconns;
	private TimeLineDummy timelinelinkedinmsg;
	private TimeLineDummy timelinelinkedininv;
	private TimeLineDummy timelinedefbrowsercookie;
	private TimeLineDummy timelinechromehist;
	private TimeLineDummy timelinechromews;
	private TimeLineDummy timelinechromecookie;
	private TimeLineDummy timelinechromecc;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public TimeLine(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(5, true));

		btnBeforeDate = new Button(this, SWT.CHECK);
		btnBeforeDate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 2, 1));
		btnBeforeDate.setText("Before Date");

		btnAfterDate = new Button(this, SWT.CHECK);
		btnAfterDate.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 2, 1));
		btnAfterDate.setText("After Date");
		new Label(this, SWT.NONE);

		dateTimeFrom = new DateTime(this, SWT.DROP_DOWN | SWT.BORDER);
		dateTimeFrom.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));

		timeFrom = new DateTime(this, SWT.BORDER | SWT.TIME | SWT.LONG);

		dateTimeTo = new DateTime(this, SWT.DROP_DOWN | SWT.BORDER);
		dateTimeTo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));

		timeTo = new DateTime(this, SWT.BORDER | SWT.TIME);
		
		Button btnFilter = new Button(this, SWT.NONE);
		btnFilter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				boolean bAfter = btnAfterDate.getSelection();
				boolean bBefore = btnBeforeDate.getSelection();

				String sfromdate = dateTimeFrom.getYear() + "/"
						+ (dateTimeFrom.getMonth() + 1) + "/"
						+ dateTimeFrom.getDay();
				
				String sfromtime = timeFrom.getHours() + ":"
						+ timeFrom.getMinutes() + ":"
						+ timeFrom.getSeconds();
				
				String stodate = dateTimeTo.getYear() + "/"
						+ (dateTimeTo.getMonth() + 1) + "/"
						+ dateTimeTo.getDay();

				String stotime = timeTo.getHours() + ":"
						+ timeTo.getMinutes() + ":"
						+ timeTo.getSeconds();
				
				createTimeLine(bBefore, bAfter, sfromdate + " " + sfromtime, stodate + " " + stotime);
			}
		});
		btnFilter.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
				1, 1));
		btnFilter.setText("Filter");
		
		TabFolder tabFolder = new TabFolder(this, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 5,
				1));

		TabItem tbtmList = new TabItem(tabFolder, SWT.NONE);
		tbtmList.setText("List");
		timelinedummy = new TimeLineDummy(tabFolder, SWT.NONE);
		tbtmList.setControl(timelinedummy);

		TabItem tbtmEventLog = new TabItem(tabFolder, SWT.NONE);
		tbtmEventLog.setText("Event Log");
		timelineevent = new TimeLineDummy(tabFolder, SWT.NONE);
		tbtmEventLog.setControl(timelineevent);

		TabItem tbtSms = new TabItem(tabFolder, SWT.NONE);
		tbtSms.setText("SMS");
		timelinesms = new TimeLineDummy(tabFolder, SWT.NONE);
		tbtSms.setControl(timelinesms);

		TabItem tbtCalendar = new TabItem(tabFolder, SWT.NONE);
		tbtCalendar.setText("Caledar Event");
		timelinecalendar= new TimeLineDummy(tabFolder, SWT.NONE);
		tbtCalendar.setControl(timelinecalendar);
		
		TabItem tbtGmail = new TabItem(tabFolder, SWT.NONE);
		tbtGmail.setText("Gmail");
		timelinegmail= new TimeLineDummy(tabFolder, SWT.NONE);
		tbtGmail.setControl(timelinegmail);
		
		TabItem tbtYoutube = new TabItem(tabFolder, SWT.NONE);
		tbtYoutube.setText("YouTube");
		timelineyoutube= new TimeLineDummy(tabFolder, SWT.NONE);
		tbtYoutube.setControl(timelineyoutube);
		
		TabItem tbtfbcontact = new TabItem(tabFolder, SWT.NONE);
		tbtfbcontact.setText("Facebook Contact");
		timelinefbcontact= new TimeLineDummy(tabFolder, SWT.NONE);
		tbtfbcontact.setControl(timelinefbcontact);
		
		TabItem tbtfbmessage = new TabItem(tabFolder, SWT.NONE);
		tbtfbmessage.setText("Facebook Message");
		timelinefbmessage = new TimeLineDummy(tabFolder, SWT.NONE);
		tbtfbmessage.setControl(timelinefbmessage);
		
		TabItem tbtfbnotification = new TabItem(tabFolder, SWT.NONE);
		tbtfbnotification.setText("Facebook Notification");
		timelinefbnotification= new TimeLineDummy(tabFolder, SWT.NONE);
		tbtfbnotification.setControl(timelinefbnotification);
		
		TabItem tbtlinkedinProfile = new TabItem(tabFolder, SWT.NONE);
		tbtlinkedinProfile.setText("Linkedin Profile");
		timelinelinkedinprofile= new TimeLineDummy(tabFolder, SWT.NONE);
		tbtlinkedinProfile.setControl(timelinelinkedinprofile);
		
		TabItem tbtlinkedinInv = new TabItem(tabFolder, SWT.NONE);
		tbtlinkedinInv.setText("Linkedin Invitations");
		timelinelinkedininv= new TimeLineDummy(tabFolder, SWT.NONE);
		tbtlinkedinInv.setControl(timelinelinkedininv);
		
		TabItem tbtlinkedinMsg = new TabItem(tabFolder, SWT.NONE);
		tbtlinkedinMsg.setText("Linkedin Messages");
		timelinelinkedinmsg= new TimeLineDummy(tabFolder, SWT.NONE);
		tbtlinkedinMsg.setControl(timelinelinkedinmsg);
		
		TabItem tbtBrowserHist = new TabItem(tabFolder, SWT.NONE);
		tbtBrowserHist.setText("Default Browser History");
		timelinebrowserhist = new TimeLineDummy(tabFolder, SWT.NONE);
		tbtBrowserHist.setControl(timelinebrowserhist);

		TabItem tbtBookmark = new TabItem(tabFolder, SWT.NONE);
		tbtBookmark.setText("Default Browser Bookmark");
		timelinebookmark = new TimeLineDummy(tabFolder, SWT.NONE);
		tbtBookmark.setControl(timelinebookmark);

		TabItem tbtWebsearch = new TabItem(tabFolder, SWT.NONE);
		tbtWebsearch.setText("Default Browser Web Search");
		timelinewebsearch = new TimeLineDummy(tabFolder, SWT.NONE);
		tbtWebsearch.setControl(timelinewebsearch);

		TabItem tbtDefBrowserCookie = new TabItem(tabFolder, SWT.NONE);
		tbtDefBrowserCookie.setText("Default Browser Cookies");
		timelinedefbrowsercookie = new TimeLineDummy(tabFolder, SWT.NONE);
		tbtDefBrowserCookie.setControl(timelinedefbrowsercookie);
	
		TabItem tbtChromeHistory = new TabItem(tabFolder, SWT.NONE);
		tbtChromeHistory.setText("Chrome History");
		timelinechromehist = new TimeLineDummy(tabFolder, SWT.NONE);
		tbtChromeHistory.setControl(timelinechromehist);
		
		TabItem tbtChromeWS = new TabItem(tabFolder, SWT.NONE);
		tbtChromeWS.setText("Chrome Web Search");
		timelinechromews = new TimeLineDummy(tabFolder, SWT.NONE);
		tbtChromeWS.setControl(timelinechromews);
		
		TabItem tbtChromeBC = new TabItem(tabFolder, SWT.NONE);
		tbtChromeBC.setText("Chrome Cookies");
		timelinechromecookie = new TimeLineDummy(tabFolder, SWT.NONE);
		tbtChromeBC.setControl(timelinechromecookie);
		
		TabItem tbtChromeCC = new TabItem(tabFolder, SWT.NONE);
		tbtChromeCC.setText("Chrome Credit Cards");
		timelinechromecc = new TimeLineDummy(tabFolder, SWT.NONE);
		tbtChromeCC.setControl(timelinechromecc);
	}

	private void createTimeLine(boolean bBefore, boolean bAfter,
			String sfromdate, String stodate) {
		timelinedummy.listAllEvents("ALL", bBefore, bAfter, sfromdate, stodate);
		timelineevent.listEvents("EVENTLOG", bBefore, bAfter, sfromdate,
				stodate);
		timelinesms.listEvents("SMS", bBefore, bAfter, sfromdate, stodate);
		timelinecalendar.listEvents("CALENDAR", bBefore, bAfter, sfromdate, stodate);
		timelinegmail.listEvents("GMAIL", bBefore, bAfter, sfromdate, stodate);
		timelineyoutube.listEvents("YOUTUBE", bBefore, bAfter, sfromdate, stodate);
		timelinefbcontact.listEvents("FBCONTACT", bBefore, bAfter, sfromdate, stodate);
		timelinefbmessage.listEvents("FBMESSAGE", bBefore, bAfter, sfromdate, stodate);
		timelinefbnotification.listEvents("FBNOTIFICATION", bBefore, bAfter, sfromdate, stodate);
		timelinelinkedinprofile.listEvents("LINKEDPROFILE", bBefore, bAfter, sfromdate, stodate);
		timelinelinkedininv.listEvents("LINKEDINV", bBefore, bAfter, sfromdate, stodate);
		timelinelinkedinmsg.listEvents("LINKEDMSG", bBefore, bAfter, sfromdate, stodate);
		timelinebrowserhist.listEvents("DEFAULTBROWSERHISTORY", bBefore, bAfter,
				sfromdate, stodate);
		timelinebookmark.listEvents("DEFAULTBOOKMARK", bBefore, bAfter, sfromdate,
				stodate);
		timelinewebsearch.listEvents("DEFAULTWEBSEARCH", bBefore, bAfter, sfromdate,
				stodate);
		timelinedefbrowsercookie.listEvents("DEFAULTCOOKIE", bBefore, bAfter, sfromdate, stodate);
		timelinechromehist.listEvents("CHROMEHIST", bBefore, bAfter, sfromdate, stodate);
		timelinechromews.listEvents("CHROMEWS", bBefore, bAfter, sfromdate, stodate);
		timelinechromecookie.listEvents("CHROMECOOKIE", bBefore, bAfter, sfromdate, stodate);
		timelinechromecc.listEvents("CHROMECC", bBefore, bAfter, sfromdate, stodate);
	}

}
