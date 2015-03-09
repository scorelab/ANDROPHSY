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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.events.SelectionAdapter;


/**
 * @author indeewari
 *
 */
public class BrowserOptionLayout extends Composite {
	private ArrayList<String> checked;
	private Button btnWebFormAnd;
	private Button btnCreditCard;
	private Button btnCookies;
	private Button btnBookmarkedUrls;
	private Button btnWebSearch;
	private Button btnBrowserHistory;
	private String parentOption;
	
	private Button btnCancel;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public BrowserOptionLayout(Composite parent, int style, String baseoption, ArrayList<String> list) {
		super(parent, style);
		this.parentOption = baseoption;
		this.checked = list;
		setLayout(null);
				
		btnBrowserHistory = new Button(this, SWT.CHECK);
		btnBrowserHistory.setBounds(10, 10, 156, 24);
		btnBrowserHistory.setText("Browser History");
		
		if(baseoption == "CHROME"){
			if(checked.indexOf("CHROME_BROWSER_HISTORY") == -1){
				btnBrowserHistory.setSelection(false);
			} else {
				btnBrowserHistory.setSelection(true);
			}
		} else if(baseoption == "DEFAULT"){
			if(checked.indexOf("DEFAULT_BROWSER_HISTORY") == -1){
				btnBrowserHistory.setSelection(false);
			} else {
				btnBrowserHistory.setSelection(true);
			}
		} else if(baseoption == "Firefox"){
			if(checked.indexOf("Firefox_BROWSER_HISTORY") == -1){
				btnBrowserHistory.setSelection(false);
			} else {
				btnBrowserHistory.setSelection(true);
			}
		} 
		

		btnWebSearch = new Button(this, SWT.CHECK);
		btnWebSearch.setBounds(10, 34, 240, 24);
		btnWebSearch.setText("Web Search");
		if(baseoption == "CHROME"){
			if(checked.indexOf("CHROME_WEB_SEARCH") == -1){
				btnWebSearch.setSelection(false);
			} else {
				btnWebSearch.setSelection(true);
			}
		} else if(baseoption == "DEFAULT"){
			if(checked.indexOf("DEFAULT_WEB_SEARCH") == -1){
				btnWebSearch.setSelection(false);
			} else {
				btnWebSearch.setSelection(true);
			}
		} else if(baseoption == "Firefox"){
			if(checked.indexOf("Firefox_WEB_SEARCH") == -1){
				btnWebSearch.setSelection(false);
			} else {
				btnWebSearch.setSelection(true);
			}
		}
				
		if(baseoption != "CHROME"){
			btnBookmarkedUrls = new Button(this, SWT.CHECK);
			btnBookmarkedUrls.setText("Bookmarked URLs");
			if(baseoption == "DEFAULT"){
				if(checked.indexOf("DEFAULT_BOOKMARKS") == -1){
					btnBookmarkedUrls.setSelection(false);
				} else {
					btnBookmarkedUrls.setSelection(true);
				}
			} else if(baseoption == "Firefox"){
				if(checked.indexOf("Firefox_BOOKMARKS") == -1){
					btnBookmarkedUrls.setSelection(false);
				} else {
					btnBookmarkedUrls.setSelection(true);
				}
			}
			btnBookmarkedUrls.setBounds(10, 58, 240, 24);
		}
		
		btnCookies = new Button(this, SWT.CHECK);
		btnCookies.setText("Cookies");
		if(baseoption == "CHROME"){
			btnCookies.setBounds(10, 58, 240, 24);
			if(checked.indexOf("CHROME_COOKIES") == -1){
				btnCookies.setSelection(false);
			} else {
				btnCookies.setSelection(true);
			}
			
		} else if(baseoption == "DEFAULT"){
			btnCookies.setBounds(10, 82, 240, 24);
			if(checked.indexOf("DEFAULT_COOKIES") == -1){
				btnCookies.setSelection(false);
			} else {
				btnCookies.setSelection(true);
			}
		} else if(baseoption == "Firefox"){
			btnCookies.setBounds(10, 82, 240, 24);
			if(checked.indexOf("Firefox_COOKIES") == -1){
				btnCookies.setSelection(false);
			} else {
				btnCookies.setSelection(true);
			}
		} else if(baseoption == "OPERA"){
			btnCookies.setBounds(10, 82, 240, 24);
			if(checked.indexOf("OPERA_COOKIES") == -1){
				btnCookies.setSelection(false);
			} else {
				btnCookies.setSelection(true);
			}
		}

		if(baseoption =="CHROME"){
			btnCreditCard = new Button(this, SWT.CHECK);
			btnCreditCard.setText("Credit Card Details");
			btnCreditCard.setBounds(10, 82, 240, 24);
			if(checked.indexOf("CHROME_CREDIT_CARD") == -1){
				btnCreditCard.setSelection(false);
			} else {
				btnCreditCard.setSelection(true);
			}			
		} else if(baseoption == "DEFAULT"){
			btnWebFormAnd = new Button(this, SWT.CHECK);
			btnWebFormAnd.setText("Web Form and Data");
			btnWebFormAnd.setBounds(10, 106, 240, 24);
			if(baseoption == "DEFAULT"){
				if(checked.indexOf("DEFAULT_WEB_FORM_DATA") == -1){
					btnWebFormAnd.setSelection(false);
				} else {
					btnWebFormAnd.setSelection(true);
				}
			}
		}
		
		Button btnOk = new Button(this, SWT.NONE);
		btnOk.setBounds(225, 146, 89, 29);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getSelectedItems();
				getParent().dispose();
			}
		});
		GridData gd_btnOk = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnOk.widthHint = 100;
		btnOk.setLayoutData(gd_btnOk);
		btnOk.setText("OK");
		
		btnCancel = new Button(this, SWT.NONE);
		btnCancel.setBounds(323, 146, 94, 29);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getParent().dispose();
			}
		});
		GridData gd_btnCancel = new GridData(SWT.RIGHT, SWT.CENTER, true, false, 1, 1);
		gd_btnCancel.widthHint = 100;
		btnCancel.setLayoutData(gd_btnCancel);
		btnCancel.setText("Cancel");

	}

	protected void getSelectedItems() {
		if(btnBrowserHistory.getSelection()){			
			checked.add(parentOption + "_BROWSER_HISTORY");			
		} else {
			checked.remove(parentOption + "_BROWSER_HISTORY");	
		}
		if(btnWebSearch.getSelection()){
			checked.add(parentOption + "_WEB_SEARCH");
		}else {
			checked.remove(parentOption + "_WEB_SEARCH");
		}
		if((parentOption != "CHROME") && (btnBookmarkedUrls.getSelection())){
			checked.add(parentOption + "_BOOKMARKS");
		}else {
			checked.remove(parentOption + "_BOOKMARKS");
		}
		if(btnCookies.getSelection()){
			checked.add(parentOption + "_COOKIES");
		}else {
			checked.remove(parentOption + "_COOKIES");
		}
		if((parentOption == "DEFAULT") && (btnWebFormAnd.getSelection())){
			checked.add(parentOption + "_WEB_FORM_DATA");
		}else {
			checked.remove(parentOption + "_WEB_FORM_DATA");
		}
		if((parentOption == "CHROME") && (btnCreditCard.getSelection())){
			checked.add(parentOption + "_CREDIT_CARD");
		}else {
			checked.remove(parentOption + "_CREDIT_CARD");
		}
	}

}
