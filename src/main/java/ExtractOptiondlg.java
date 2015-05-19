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

import java.util.ArrayList;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.SWT;


/**
 * @author indeewari
 *
 */
public class ExtractOptiondlg extends Dialog {

	protected String result;
	protected static Shell shell;
	protected String parentOption;
	protected ArrayList<String> checked;
	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public ExtractOptiondlg(Shell parent, int style, String parentoption, ArrayList<String> list) {
		super(parent, style);
		setText("Choose Option Dialog");
		this.parentOption = parentoption;
		this.checked = list;
		System.out.println("parent option " + parentoption);
	}


	/**
	 * Open the dialog.
	 * @return the result
	 */
	public String open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		Shell parent  = getParent();
		StackLayout stacklayout = new StackLayout();
		
		shell = new Shell(parent, SWT.TITLE | SWT.APPLICATION_MODAL | SWT.BORDER);
		shell.setSize(450, 300);
		shell.setText(getText());
		shell.setLayout(stacklayout);
		
		System.out.println("create content " + parentOption);
		
		if((parentOption == "CHROME") || (parentOption == "DEFAULT") || (parentOption == "Firefox") || (parentOption == "OPERA")){
			BrowserOptionLayout browseroption = new BrowserOptionLayout(shell, SWT.NONE, parentOption, checked);
			stacklayout.topControl = browseroption;			
			shell.layout();
			
		} else if ((parentOption == "FACEBOOK") || (parentOption == "LINKEDIN")){
			SocialNetOptionTemp socialnwtemp = new SocialNetOptionTemp(shell, SWT.NONE, parentOption, checked);
			stacklayout.topControl = socialnwtemp;
			shell.layout();
			
		} else if (parentOption == "SKYPE"){
			SkypeOption skypeot = new SkypeOption(shell, SWT.NONE, parentOption, checked);
			stacklayout.topControl = skypeot;
			shell.layout();
			
		} else if (parentOption == "CARVING"){
			FileCarvOptionTem filecarvingtemplate = new FileCarvOptionTem(shell, SWT.NONE);
			stacklayout.topControl = filecarvingtemplate;
			shell.layout();
			
		} else if (parentOption == "EXTRACTSTRING"){
			BulkViewer bulkviewer = new BulkViewer(shell, SWT.NONE);
			stacklayout.topControl = bulkviewer;
			shell.layout();
			
		} else if (parentOption == "EVENTLOG"){
			System.out.println("event log");
			EventLogViewer eventlog = new EventLogViewer(shell, SWT.NONE, parentOption, checked);
			stacklayout.topControl = eventlog;
			shell.layout();
		}
		

	}

	public boolean checkChrome() {
		if (checked.contains("CHROME_BROWSER_HISTORY")
				|| checked.contains("CHROME_WEB_SEARCH")
				|| checked.contains("CHROME_COOKIES")
				|| checked.contains("CHROME_CREDIT_CARD")) {
			return true;
		}
		return false;
	}
	
	public boolean checkDefaultBrowser(){
		if (checked.contains("DEFAULT_BROWSER_HISTORY")
				|| checked.contains("DEFAULT_WEB_SEARCH")
				|| checked.contains("DEFAULT_BOOKMARKS")
				|| checked.contains("DEFAULT_COOKIES")
				|| checked.contains("DEFAULT_WEB_FORM_DATA")) {
			return true;
		}
		return false;
	}

	public boolean checkEventLog(){
		if (checked.contains("Call Logs")
				|| checked.contains("SMS Logs")
				|| checked.contains("MMS Logs")) {
			return true;
		}
		return false;
	}
	
	public boolean checkFacebook(){
		if (checked.contains("FACEBOOK_CONTACTS")
				|| checked.contains("FACEBOOK_MESSAGES")
				|| checked.contains("FACEBOOK_NOTIFICATIONS")) {
			return true;
		}
		return false;
	}
	
	public boolean checkLinkedin(){
		if (checked.contains("LINKEDIN_PROFILE")
				|| checked.contains("LINKEDIN_CONNECTIONS")
				|| checked.contains("LINKEDIN_INVITATIONS")
				|| checked.contains("LINKEDIN_MESSAGES")) {
			return true;
		}
		return false;
	}
	
	public boolean checkCarving() {
		if (checked.contains("GIF") || checked.contains("JPG")
				|| checked.contains("PNG") || checked.contains("BMP")
				|| checked.contains("TIFF") || checked.contains("AVI")
				|| checked.contains("MPEG") || checked.contains("Flash")
				|| checked.contains("HTML") || checked.contains("PDF")
				|| checked.contains("WAV")
				|| checked.contains("Real Audio File")
				|| checked.contains("SQLite")) {
			return true;
		}
		return false;
	}
	
	public boolean checkExtract() {
		if (checked.contains("Credit Card Numbers")
				|| checked.contains("Telephone Numbers")
				|| checked.contains("Email Addresses")
				|| checked.contains("Internet Domain Information")
				|| checked.contains("URL information")
				|| checked.contains("MAC Address")
				|| checked.contains("IP Address")) {
			return true;
		}
		return false;
	}
	
	public static void close() {
		shell.dispose();
		
	}
	
}
