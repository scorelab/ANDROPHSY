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

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

/**
 * @author indeewari
 *
 */
public class LogCollection extends Composite {

	private Button btnKernelLogs;
	private Button btnAccountLogs;
	private Button btnLocationLog;
	private Button btnmeminfo;
	private Button btnLastUserActivity;
	private ProgressBar progressBar;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public LogCollection(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Label lblKernelEventsAnd = new Label(this, SWT.NONE);
		lblKernelEventsAnd.setText("Kernel Events and Activity Logs");
		new Label(this, SWT.NONE);

		btnKernelLogs = new Button(this, SWT.CHECK);
		btnKernelLogs.setText("Kernel Logs");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Label lblMemoryInformationLogs = new Label(this, SWT.NONE);
		lblMemoryInformationLogs.setText("Memory Information Logs");
		new Label(this, SWT.NONE);

		btnmeminfo = new Button(this, SWT.CHECK);
		btnmeminfo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		btnmeminfo.setText("Memory Information  Summary Log");

		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Label lblSystemAndApplication = new Label(this, SWT.NONE);
		lblSystemAndApplication.setText("System and Application Logs");
		new Label(this, SWT.NONE);

		btnLastUserActivity = new Button(this, SWT.CHECK);
		btnLastUserActivity.setText("Last User Activity Log");
		new Label(this, SWT.NONE);

		btnAccountLogs = new Button(this, SWT.CHECK);
		btnAccountLogs.setText("Account and Sync Log");
		new Label(this, SWT.NONE);

		btnLocationLog = new Button(this, SWT.CHECK);
		btnLocationLog.setText("Location Log");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Button btnExtractLog = new Button(this, SWT.NONE);
		btnExtractLog.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				doExtract();
			}
		});

		btnExtractLog.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnExtractLog.setText("Extract Log");

		progressBar = new ProgressBar(this, SWT.NONE);
		progressBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

	}

	private String[] getProcessList() {
		try {
			String tmp[] = null;
			Process proc = Runtime.getRuntime().exec("adb shell ps");
			DataInputStream data = new DataInputStream(proc.getInputStream());
			byte buf[] = new byte[1024];
			int len = data.read(buf);
			System.out.println(len + "tmp " + new String(buf));
			String list[] = new String(buf, 0, len).split("\n");

			if (list.length > 1) {
				String pslist[] = new String[list.length - 1];
				for (int i = 1; i < list.length; i++) {
					tmp = list[i].split("\t");
					pslist[i - 1] = tmp[7];
					System.out.println(pslist[i - 1]);
				}
				return pslist;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected void doExtract() {
		getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				progressBar.setVisible(true);

			}
		});
		SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		AndrospyLog.Logdata(sf.format(new Date()), "Kernel Log Collection");
		if (btnKernelLogs.getSelection()) {
			KernelLog();
		}
		if (btnAccountLogs.getSelection()) {
			Dump("account");
		}
		if (btnLocationLog.getSelection()) {
			Dump("location");
		}
		if (btnmeminfo.getSelection()) {
			Dump("meminfo");
		}
		if (btnLastUserActivity.getSelection()) {
			Dump("activity activities");
		}
		getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				progressBar.setVisible(false);

			}
		});
	}

	private void Dump(String opt) {
		String line = "", err = "", name = "";
		if (opt.equals("activity activities")) {
			name = "activitylog.txt";
		} else if (opt.equals("meminfo")) {
			name = "meminfolog.txt";
		} else if (opt.equals("account")) {
			name = "accountlog.txt";
		} else if (opt.equals("location")) {
			name = "locationlog.txt";
		}

		try {
			File logdir = new File(AndrospyMain.gb_CasePath + "Analysis/Logs");
			if (!logdir.exists()) {
				if (!logdir.mkdir()) {
					return;
				}
			}
			ArrayList<String> cmdlist = new ArrayList<String>();
			cmdlist.add("files/dumplog.sh");
			cmdlist.add(opt);
			cmdlist.add(AndrospyMain.gb_CasePath + "Analysis/Logs/" + name);
			ProcessBuilder pb = new ProcessBuilder(cmdlist);
			Process proc = pb.start();

			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while ((line = bufin.readLine()) != null) {
				System.out.println(line);
			}

			BufferedReader buferr =
			                        new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			while ((err = buferr.readLine()) != null) {
				System.out.println(err);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void KernelLog() {
		String line = "", err = "";
		try {
			File logdir = new File(AndrospyMain.gb_CasePath + "Analysis/Logs");
			if (!logdir.exists()) {
				if (!logdir.mkdir()) {
					return;
				}
			}

			ArrayList<String> cmdlist = new ArrayList<String>();
			cmdlist.add("files/kernellog.sh");
			cmdlist.add(AndrospyMain.gb_CasePath + "Analysis/Logs/kernellog.txt");
			ProcessBuilder pb = new ProcessBuilder(cmdlist);
			Process proc = pb.start();

			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			while ((line = bufin.readLine()) != null) {
				System.out.println(line);
			}

			BufferedReader buferr =
			                        new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			while ((err = buferr.readLine()) != null) {
				System.out.println(err);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
