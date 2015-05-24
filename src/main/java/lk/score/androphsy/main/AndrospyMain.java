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

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;

/**
 * @author indeewari
 *
 */
public class AndrospyMain {

	public static Shell shell = new Shell();
	public static String gb_username = "";
	public static String gb_UserRole = "";
	public static String gb_CasePath = "";
	private static Login login;
	public static int gb_CaseId;
	public static int gb_DeviceId;
	public static String gb_PrimaryGmail;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	protected Control createControl(Composite parent) {
		return parent;
	}

	public static void main(String[] args) {
		Display display = Display.getDefault();
		shell.setMaximized(true);
		shell.setText("Androspy");

		GridLayout gridLayout = new GridLayout(1, true);
		shell.setLayout(gridLayout);

		// menu bar
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem menu_file = new MenuItem(menu, SWT.CASCADE);
		menu_file.setText("File");

		Menu menu_1 = new Menu(menu_file);
		menu_file.setMenu(menu_1);

		MenuItem mntmNewItem = new MenuItem(menu_1, SWT.NONE);
		mntmNewItem.setText("Exit");

		// lk.score.androphsy.main.Login - first view
		login = new Login(shell, SWT.None);
		login.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));

		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
