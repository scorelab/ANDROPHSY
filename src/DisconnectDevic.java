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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;


/**
 * @author indeewari
 *
 */
public class DisconnectDevic extends Composite {
	private Text text;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public DisconnectDevic(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		text = new Text(this, SWT.BORDER | SWT.READ_ONLY | SWT.MULTI);
		GridData gd_text = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_text.heightHint = 235;
		text.setLayoutData(gd_text);
		
		Composite composite = new Composite(this, SWT.NONE);
		GridData gd_composite = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_composite.heightHint = 17;
		composite.setLayoutData(gd_composite);

	}
	
	public void showProgress(String msg){
		text.append(msg);
	}

	public void resetProgressPane() {
		text.setText("");		
	}

}
