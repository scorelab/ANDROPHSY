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
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;

/**
 * @author indeewari
 *
 */
public class HexViewer extends Composite {
	private Text txtHexText;
	private Label lblProgress;
	private String rawFile;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public HexViewer(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));

		txtHexText = new Text(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL);
		txtHexText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		txtHexText.setEnabled(false);
		txtHexText.setEditable(false);
		new Label(this, SWT.NONE);

		lblProgress = new Label(this, SWT.NONE);
		lblProgress.setBounds(107, 43, 320, 26);
		lblProgress.setText("");
	}

	public void getHexdump(String offsetlist) {
		long firstindex = Long.parseLong(offsetlist, 10) - 25;

		try {
			Process proc =
			               Runtime.getRuntime().exec("hexdump -s " + firstindex + " -n 200 " +
			                                                 " -C " + AndrospyMain.gb_CasePath +
			                                                 "Acquire/data.dd");
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = "", result = "";
			while ((line = bufin.readLine()) != null) {
				System.out.println(line);
				if ((line.trim()).length() > 10) {
					result += line.substring(0, 8) + "\t\t";
					if (line.substring(8).length() >= 25) {
						result += line.substring(10, 33) + "\t\t";
					} else {
						result += line.substring(10) + "\t\t";
						continue;
					}
					if (line.substring(33).length() >= 17) {
						result += line.substring(35, 58) + "\t\t";
					} else {
						result += line.substring(35) + "\t\t";
						continue;
					}
					if (line.substring(58).length() > 0) {
						result += line.substring(60);
					}
				} else {
					result += line.substring(0) + "\t\t";
				}

				result += "\n";
			}

			setHexText(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void ViewHexDump(String filePath) {
		String line, result = "";
		System.out.println("test2 " + filePath);
		txtHexText.setText("");
		try {
			Process proc = Runtime.getRuntime().exec("hexdump -C " + filePath);
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			line = bufin.readLine();
			while (line != null) {
				System.out.println("-----------------------\n" + line + "\n-----------------------");
				if ((line.trim()).length() > 10) {
					result += line.substring(0, 8) + "\t\t";
					if (line.substring(8).length() >= 25) {
						result += line.substring(10, 33) + "\t\t";
					} else {
						result += line.substring(10) + "\t\t";
						continue;
					}
					if (line.substring(33).length() >= 17) {
						result += line.substring(35, 58) + "\t\t";
					} else {
						result += line.substring(35) + "\t\t";
						continue;
					}
					if (line.substring(58).length() > 0) {
						result += line.substring(60);
					}
				} else {
					result += line.substring(0) + "\t\t";
				}

				result += "\n";
				System.out.print(result);
				line = bufin.readLine();
			}
			System.out.println("lllll");
			txtHexText.setText(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setHexText(String strhex) {
		txtHexText.setText(strhex);
	}
}
