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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

/**
 * @author indeewari
 *
 */
public class FeatureOffset extends Composite {
	private Text txtSrch;
	private StyledText text;
	private StyleRange stylerange;
	private Label lblOffset;
	private Text txtOffset;
	private Composite composite;
	private Button btnHexView;
	private HexViewer hv;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public FeatureOffset(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(3, true));

		stylerange = new StyleRange();
		stylerange.background = AndrospyMain.shell.getDisplay().getSystemColor(
				SWT.COLOR_YELLOW);

		txtSrch = new Text(this, SWT.BORDER);
		txtSrch.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				2, 1));

		Button btnSearch = new Button(this, SWT.NONE);
		btnSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				String srch = txtSrch.getText();
				String fulltext = text.getText();
				if (srch != "" && fulltext != "") {
					find(srch, fulltext);
				}
			}
		});
		GridData gd_btnSearch = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_btnSearch.widthHint = 104;
		btnSearch.setLayoutData(gd_btnSearch);
		btnSearch.setText("Search");

		text = new StyledText(this, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL
				| SWT.H_SCROLL);
		GridData gd_text = new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1);
		gd_text.heightHint = 230;
		text.setLayoutData(gd_text);

		lblOffset = new Label(this, SWT.NONE);
		lblOffset.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		lblOffset.setText("Offset:");

		txtOffset = new Text(this, SWT.BORDER);
		txtOffset.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		
		btnHexView = new Button(this, SWT.NONE);
		btnHexView.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(txtOffset.getText().equals("")){
					MessageBox msbox = new MessageBox(getShell(),SWT.ICON_ERROR);
					msbox.setMessage("ERROR - Hex View");
					msbox.setText("Offset value cannot be null");
					msbox.open();
					return;
				}
				hv.getHexdump(txtOffset.getText());
			}
		});
		btnHexView.setText("Hex View");

		hv = new HexViewer(this, SWT.NONE);
		hv.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
	}

	private void find(String search, String string) {
		int index = string.indexOf(search, 0);
		while (index != -1) {
			highlight(index, search);
			index = string.indexOf(search, index + 1);
		}
	}

	private void highlight(int start, String search) {
		stylerange.start = start;
		stylerange.length = search.length();
		text.setStyleRange(stylerange);
	}

	// process extracted data into categories
	public void showDataExtracts(String casepath) {
		File file = null;
		file = new File(casepath);
		generateOffset(this.text, file);
	}

	// read feature file and extract offset data
	protected void generateOffset(StyledText txtoffset, File filename) {
		String line = null;
		String result = "";
		String tokens[] = new String[3];
		try {
			txtoffset
					.setText("Offset \t\t\t Extracted Information\n"
							+ "---------------------------------------------------------------------------\n");
			FileReader fr = new FileReader(filename);
			BufferedReader buf = new BufferedReader(fr);
			line = buf.readLine();
			while (line != null) {
				if (line.startsWith("#")) {
					line = buf.readLine();
					continue;
				}
				tokens = line.split("\t");
				if (tokens.length <= 1) {
					line = buf.readLine();
					continue;
				}
				result = tokens[0] + "\t\t" + tokens[1] + "\n";
				line = buf.readLine();
				txtoffset.append(result);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
