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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;


/**
 * @author indeewari
 *
 */
public class Root extends Composite {
	private Text text;
	private Combo comboExploit;
	private ProgressBar progressBar;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public Root(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		
		comboExploit = new Combo(this, SWT.NONE);
		GridData gd_comboExploit = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_comboExploit.widthHint = 268;
		comboExploit.setLayoutData(gd_comboExploit);
		comboExploit.setBounds(10, 10, 292, 29);
		comboExploit.add("Safe Root (CVE-2013-6282)");
		comboExploit.add("Argon Exploit");
		comboExploit.add("Gandalf Exploit");
		comboExploit.add("GingerBreak (CVE-2011-1823)");
		comboExploit.add("zergRush");
		comboExploit.add("psneuter (CVE-2011-1149)");
		comboExploit.add("exploid (CVE-2010-1185)");
		comboExploit.add("RageAgianstCage Exploit");
		
		Button btnExecute = new Button(this, SWT.NONE);
		btnExecute.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
						
				rootDevice(comboExploit.getText());
			}
		});
		btnExecute.setBounds(308, 10, 91, 29);
		btnExecute.setText("Execute");
		
		text = new Text(this, SWT.BORDER | SWT.MULTI);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		text.setBounds(10, 57, 389, 207);
		text.setText("");
		
		progressBar = new ProgressBar(this, SWT.INDETERMINATE);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		progressBar.setVisible(false);
		new Label(this, SWT.NONE);
	}

	protected void rootDevice(String exploit) {
		getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				text.setText("Start rooting...");
				progressBar.setVisible(true);

			}
		});
		
		if(exploit.equals("Safe Root (CVE-2013-6282)")){
			
			System.out.println("inside saferoot");
			
			ProcessBuilder pb = new ProcessBuilder("files/install.sh");
			try {
				Process proc = pb.start();
				
				String line = "", result ="";
				BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				while((line = bufin.readLine()) != null){
					text.append(line + "\n");
				}
				proc.waitFor();
				AdbExecCmd cmd = new AdbExecCmd();
				cmd.killAdb();
				cmd.startAdb();
				MessageBox mbox = new MessageBox(getShell(), SWT.ICON_INFORMATION);
				mbox.setMessage("Device Roots");
				mbox.setText("Please disconnect and Reconnect your device");
				if(mbox.open() == SWT.OK){
					cmd.killAdb();
					cmd.startAdb();
				}
				if(cmd.isRooted()){
					text.append("Device is successfully rooted \n\n");
					cmd.prepareDevice();
					for(int i =0 ; i<5000; i++){
						;
					}
					if(!cmd.isKernelSecure()){
						text.append("Please open Insecure application and Enable Insecure");
					}
					for(int i =0 ; i<5000; i++){
						;
					}					
					DataCollection.postroot();
					SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					AndrospyLog.Logdata(sf.format(new Date()), "Device rooted");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else if(exploit.equals("Argon Exploit")){
			System.out.println("inside argon");
			List<String> cmdlist = new ArrayList<String>();
			cmdlist.add("files/gandalf.sh");
			cmdlist.add("Gandalf");
			ProcessBuilder pb = new ProcessBuilder(cmdlist);
			try {
				Process proc = pb.start();
				
				String line = "", result ="";
				BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
				while((line = bufin.readLine()) != null){
					text.append(line + "\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (exploit.equals("Gandalf Exploit")){
			;
		}
		getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				progressBar.setVisible(false);

			}
		});
	}
}
