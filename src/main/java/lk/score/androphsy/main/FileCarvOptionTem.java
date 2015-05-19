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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * @author indeewari
 *
 */
public class FileCarvOptionTem extends Composite {

	private Button btnSqlite;
	private Button btnRealAudioFile;
	private Button btnWav;
	private Button btnPdf;
	private Button btnHtml;
	private Button btnFlash;
	private Button btnMpeg;
	private Button btnAvi;
	private Button btnTiff;
	private Button btnBmp;
	private Button btnPng;
	private Button btnJpg;
	private Button btnGif;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public FileCarvOptionTem(Composite parent, int style) {
		super(parent, style);
		setLayout(null);

		btnGif = new Button(this, SWT.CHECK);
		btnGif.setBounds(10, 20, 68, 14);
		btnGif.setText("GIF");

		btnJpg = new Button(this, SWT.CHECK);
		btnJpg.setBounds(10, 40, 61, 14);
		btnJpg.setText("JPG");

		btnPng = new Button(this, SWT.CHECK);
		btnPng.setBounds(10, 60, 61, 14);
		btnPng.setText("PNG");

		btnBmp = new Button(this, SWT.CHECK);
		btnBmp.setBounds(10, 80, 61, 14);
		btnBmp.setText("BMP");

		btnTiff = new Button(this, SWT.CHECK);
		btnTiff.setBounds(10, 100, 54, 14);
		btnTiff.setText("TIFF");

		Label label = new Label(this, SWT.NONE);
		label.setBounds(0, 0, 0, 0);

		btnAvi = new Button(this, SWT.CHECK);
		btnAvi.setBounds(137, 20, 61, 14);
		btnAvi.setText("AVI");

		btnMpeg = new Button(this, SWT.CHECK);
		btnMpeg.setBounds(137, 40, 83, 14);
		btnMpeg.setText("MPEG");

		btnFlash = new Button(this, SWT.CHECK);
		btnFlash.setBounds(137, 60, 83, 14);
		btnFlash.setText("Flash");

		btnHtml = new Button(this, SWT.CHECK);
		btnHtml.setBounds(10, 159, 75, 14);
		btnHtml.setText("HTML");

		btnPdf = new Button(this, SWT.CHECK);
		btnPdf.setBounds(10, 179, 83, 14);
		btnPdf.setText("PDF");

		btnWav = new Button(this, SWT.CHECK);
		btnWav.setBounds(299, 20, 83, 14);
		btnWav.setText("WAV");

		btnRealAudioFile = new Button(this, SWT.CHECK);
		btnRealAudioFile.setBounds(299, 40, 141, 14);
		btnRealAudioFile.setText("Real Audio File");

		btnSqlite = new Button(this, SWT.CHECK);
		btnSqlite.setBounds(137, 159, 88, 18);
		btnSqlite.setText("SQLite");

		Button btnOk = new Button(this, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				doConfiguration();
				ExtractOptiondlg.close();
			}
		});
		btnOk.setBounds(251, 203, 91, 29);
		btnOk.setText("OK");

		Button btnCancel = new Button(this, SWT.NONE);
		btnCancel.setBounds(348, 203, 91, 29);
		btnCancel.setText("Cancel");
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ExtractOptiondlg.close();
			}
		});

	}

	public String getconf() {
		String outstring = "";

		if (btnGif.getSelection()) {
			outstring +=
			             ("\n# --------------------------- \n# GRAPHIC FILES \n# ---------------------------\n\n");
			outstring += "gif	y	5000000		\\x47\\x49\\x46\\x38\\x37\\x61	\\x00\\x3b";
			outstring += "\ngif	y 	5000000		\\x47\\x49\\x46\\x38\\x39\\x61	\\x00\\x3b";
		}

		if (btnJpg.getSelection()) {
			outstring += "\njpg	y	200000000	\\xff\\xd8\\xff\\xe0\\x00\\x10	\\xff\\xd9";
			outstring += "\njpg	y	5000000		\\xff\\xd8\\xff\\xe1		\\x7f\\xff\\xd9";
		}

		if (btnPng.getSelection()) {
			outstring += "\n\npng	y	20000000	\\x50\\x4e\\x47?	\\xff\\xfc\\xfd\\xfe";
		}

		if (btnBmp.getSelection()) {
			outstring += "\n\nbmp	y	100000	BM??\\x00\\x00\\x00";
		}

		if (btnTiff.getSelection()) {
			outstring += "\n\ntif	y	200000000	\\x49\\x49\\x2a\\x00";
			outstring += "\n \tif	y	200000000	\\x4D\\x4D\\x00\\x2A";
		}

		if (btnMpeg.getSelection()) {
			outstring +=
			             ("\n# --------------------------- \n# MPEG VIDEO FILES \n# --------------------------\n\n-");
			outstring += "mpg	y	50000000	\\x00\\x00\\x01\\xba	\\x00\\x00\\x01\\xb9";
			outstring += "\nmpg     y 	50000000	\\x00\\x00\\x01\\xb3	\\x00\\x00\\x01\\xb7";
		}

		if (btnFlash.getSelection()) {
			outstring +=
			             ("\n# --------------------------- \n# MACROMEDIA FLASH FILES \n# ---------------------------\n\n");
			outstring += "fws	y	4000000	FWS";
		}

		if (btnHtml.getSelection()) {
			outstring +=
			             ("\n# --------------------------- \n# HTML FILES \n# ---------------------------\n\n");
			outstring += "htm	n	50000   <html			</html>";
		}

		if (btnPdf.getSelection()) {
			outstring +=
			             ("\n# --------------------------- \n# PDF FILES \n# ---------------------------\n\n");
			outstring +=
			             "pdf	y	5000000	%PDF  %EOF\\x0d	REVERSE \n pdf	y	5000000	%PDF  %EOF\\x0a	REVERSE";
		}

		if (btnWav.getSelection()) {
			outstring +=
			             ("\n# --------------------------- \n# SOUND FILES \n# ---------------------------\n\n");
			outstring += "wav     y	200000	RIFF????WAVE";
		}

		if (btnRealAudioFile.getSelection()) {
			outstring +=
			             ("\n# --------------------------- \n# REAL AUDIO FILES \n# ---------------------------\n\n");
			outstring += "ra	y	1000000	\\x2e\\x72\\x61\\xfd \n ra	y	1000000	.RMF:";
		}

		if (btnSqlite.getSelection()) {
			outstring +=
			             ("\n# --------------------------- \n# SQLITE DATABSES \n# ---------------------------\n\n");
			outstring += "sqlitedb	y	409600	SQLite\\x20format";
		}
		return outstring;
	}

	public void doConfiguration() {
		File file = new File(AndrospyMain.gb_CasePath + "Acquire/carving.conf");

		try {
			BufferedWriter bufout = new BufferedWriter(new FileWriter(file));
			bufout.write(getconf());
			bufout.flush();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
