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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ExpandBar;
import org.eclipse.swt.widgets.ExpandItem;

/**
 * @author indeewari
 *
 */
public class VolatileCollection extends Composite {
	private Text text;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public VolatileCollection(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(2, false));

		Label lblInstructions = new Label(this, SWT.WRAP);
		GridData gd_lblInstructions = new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1);
		gd_lblInstructions.widthHint = 628;
		lblInstructions.setLayoutData(gd_lblInstructions);
		lblInstructions.setText("Collecting Volatile Memory involve several steps."
		                        + "Please Read Instructions Carefully.\n");

		ExpandBar expandBar = new ExpandBar(this, SWT.NONE);
		GridData gd_expandBar = new GridData(SWT.FILL, SWT.FILL, false, false, 2, 8);
		gd_expandBar.heightHint = 183;
		expandBar.setLayoutData(gd_expandBar);

		ExpandItem xpndtmInstructions = new ExpandItem(expandBar, SWT.NONE);
		xpndtmInstructions.setText("INSTRUCTIONS");

		Label lblPleaseSelectLoadable = new Label(this, SWT.NONE);
		lblPleaseSelectLoadable.setText("Please Select Loadable Kernal Module");

		Label lblInstruction = new Label(expandBar, SWT.WRAP);
		lblInstruction.setText("Create LKM:\n\n"
		                       + "1. Install Java Development Kit (JDK).\n2.Install appropiate version of Android SDK (SDK)"
		                       + "\n3.Install Adroid NDK\n4. Download target device kernel source for Android Version\n"
		                       + "5. Download Lime Source\n"
		                       + "6. Compile Kernal source for arm Architecture\n"
		                       + "7. Cross Compile Lime Source with kernel source to get Loadable Kernal Module");

		xpndtmInstructions.setControl(lblInstruction);
		xpndtmInstructions.setHeight(500);
		xpndtmInstructions.setExpanded(true);
		expandBar.setSpacing(8);
		new Label(this, SWT.NONE);

		text = new Text(this, SWT.BORDER);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_text.widthHint = 496;
		text.setLayoutData(gd_text);

		Button btnBrowsse = new Button(this, SWT.NONE);
		btnBrowsse.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnBrowsse.setText("Browsse");

		Button btnCollectVolatileData = new Button(this, SWT.NONE);
		btnCollectVolatileData.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		btnCollectVolatileData.setText("Collect Volatile Data");

	}
}
