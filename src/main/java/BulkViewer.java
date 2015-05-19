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

import java.io.File;
import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;



/**
 * @author indeewari
 *
 */
public class BulkViewer extends Composite {
	

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public BulkViewer(Composite parent, int style) {
		super(parent, style);
		setLayout(null);
		
		Button btnCreditCardNumbers = new Button(this, SWT.CHECK);
		btnCreditCardNumbers.setBounds(10, 10, 187, 24);
		btnCreditCardNumbers.setText("Credit Card Numbers");
		
		Button btnTelephoneNumbers = new Button(this, SWT.CHECK);
		btnTelephoneNumbers.setBounds(10, 34, 172, 24);
		btnTelephoneNumbers.setText("Telephone Numbers");
		
		Button btnEmail = new Button(this, SWT.CHECK);
		btnEmail.setGrayed(true);
		btnEmail.setBounds(10, 58, 159, 24);
		btnEmail.setText("Email Addresses");
		
		Button btnInternetDomainInformation = new Button(this, SWT.CHECK);
		btnInternetDomainInformation.setBounds(10, 82, 275, 24);
		btnInternetDomainInformation.setText("Internet Domain Information");
		
		Button btnUrlInformation = new Button(this, SWT.CHECK);
		btnUrlInformation.setBounds(10, 106, 115, 24);
		btnUrlInformation.setText("URL information");
		
		Button btnMacAddress = new Button(this, SWT.CHECK);
		btnMacAddress.setBounds(10, 130, 115, 24);
		btnMacAddress.setText("MAC Address");
		
		Button btnIpAddress = new Button(this, SWT.CHECK);
		btnIpAddress.setBounds(10, 154, 115, 24);
		btnIpAddress.setText("IP Address");
		
		Button btnCancel = new Button(this, SWT.NONE);
		btnCancel.setBounds(327, 198, 91, 29);
		btnCancel.setText("Cancel");
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getParent().dispose();
			}
		});
		
		Button btnOk = new Button(this, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				getParent().dispose();
			}
		});
		btnOk.setBounds(225, 198, 91, 29);
		btnOk.setText("OK");
		
		
	}
	
	private String manageOptionList(ArrayList<String> checklist){
		String soptlist = null;
		return soptlist;
	}
	
}

class PatternMetaData{
	private File offsetFile, histoFile;
	private byte data; //1-stat only, 2-data only, 3- both 
	
	public PatternMetaData(File of, File hf , byte b){
		this.offsetFile = of;
		this.histoFile = hf;
		this.data = b;
	}
	
	public void setOffsetFile(File f){
		this.offsetFile = f;
	}
	
	public File getOffsetFile(){
		return offsetFile;
	}

	public void setHistoFile(File f){
		this.histoFile = f;
	}
	
	public File getHistoFile(){
		return histoFile;
	}
	
	public void setData(byte b){
		this.data = b;
	}
	
	public byte getData(){
		return data;
	}
}