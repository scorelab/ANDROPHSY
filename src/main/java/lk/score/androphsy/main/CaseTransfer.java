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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lk.score.androphsy.database.ConnectDb;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.wb.swt.SWTResourceManager;

/**
 * @author indeewari
 *
 */
public class CaseTransfer extends Composite {
	private Text txtInvId;
	private Text txtAnalystId;
	private Text txtLocation;
	private Text txtCaseDesc;
	private Text txtCaseName;
	private Text txtCaseId;
	private Text txtNewInv;
	private Text txtNewAnalyst;
	private Connection con;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public CaseTransfer(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(4, true));

		ConnectDb condb = new ConnectDb();
		con = condb.getConnection();

		Label lblCaseTransferPanel = new Label(this, SWT.NONE);
		lblCaseTransferPanel.setFont(SWTResourceManager.getFont("Ubuntu", 14, SWT.NORMAL));
		lblCaseTransferPanel.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false, 4, 1));
		lblCaseTransferPanel.setText("Case Transfer Panel");

		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Label lblCaseId = new Label(this, SWT.NONE);
		lblCaseId.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblCaseId.setText("Case ID");

		txtCaseId = new Text(this, SWT.BORDER);
		txtCaseId.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));

		Button btnsearch = new Button(this, SWT.NONE);
		btnsearch.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnsearch.setText("lk.score.androphsy.main.Search");
		btnsearch.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int caseid = Integer.parseInt(txtCaseId.getText());
				SearchCase(caseid);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		Label lblCaseName = new Label(this, SWT.NONE);
		lblCaseName.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false, 1, 1));
		lblCaseName.setText("Case Name");
		lblCaseName.setBounds(8, 10, 93, 17);

		txtCaseName = new Text(this, SWT.BORDER);
		txtCaseName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		txtCaseName.setEditable(false);
		txtCaseName.setBounds(139, 10, 253, 27);
		new Label(this, SWT.NONE);

		Label lblCaseDesc = new Label(this, SWT.NONE);
		lblCaseDesc.setText("Description");
		lblCaseDesc.setBounds(8, 43, 85, 17);

		txtCaseDesc = new Text(this, SWT.BORDER | SWT.MULTI);
		txtCaseDesc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3, 3));
		txtCaseDesc.setEditable(false);
		txtCaseDesc.setBounds(139, 43, 253, 100);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Label lblCasePath = new Label(this, SWT.NONE);
		GridData gd_lblCasePath = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_lblCasePath.widthHint = 118;
		lblCasePath.setLayoutData(gd_lblCasePath);
		lblCasePath.setText("Case Location");

		txtLocation = new Text(this, SWT.BORDER);
		txtLocation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 2, 1));
		txtLocation.setEditable(false);
		new Label(this, SWT.NONE);

		Label lblInvId = new Label(this, SWT.NONE);
		GridData gd_lblInvId = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_lblInvId.widthHint = 102;
		lblInvId.setLayoutData(gd_lblInvId);
		lblInvId.setText("Investigator ID");
		lblInvId.setBounds(8, 149, 108, 17);
		txtInvId = new Text(this, SWT.BORDER);
		txtInvId.setEditable(false);
		txtInvId.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));

		Label lblTransferTo = new Label(this, SWT.NONE);
		lblTransferTo.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTransferTo.setText("Transfer To:");

		txtNewInv = new Text(this, SWT.BORDER);
		txtNewInv.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblAnalystId = new Label(this, SWT.NONE);
		GridData gd_lblAnalystId = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_lblAnalystId.widthHint = 86;
		lblAnalystId.setLayoutData(gd_lblAnalystId);
		lblAnalystId.setText("Analyst ID");

		txtAnalystId = new Text(this, SWT.BORDER);
		txtAnalystId.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		txtAnalystId.setEditable(false);

		Label label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label.setText("Transfer To:");

		txtNewAnalyst = new Text(this, SWT.BORDER);
		txtNewAnalyst.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Button btnTransferCase = new Button(this, SWT.NONE);
		btnTransferCase.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				TransferCase(txtNewInv.getText(), txtNewAnalyst.getText());
			}
		});
		btnTransferCase.setText("Transfer Case");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
	}

	protected void SearchCase(int caseid) {
		try {
			Statement stmt = con.createStatement();
			String query = "SELECT * FROM Androspy_Case WHERE case_id = " + caseid;
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				txtCaseName.setText(rs.getString("case_name"));
				txtCaseDesc.setText(rs.getString("case_desc"));
				txtLocation.setText(rs.getString("case_path"));
				txtInvId.setText(rs.getString("investigator_id"));
				txtAnalystId.setText(rs.getString("analyst_id").equals("null")
				                                                              ? ""
				                                                              : rs.getString("analyst_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void TransferCase(String inv, String analyst) {
		try {
			String query = "UPDATE Androspy_Case SET ";
			if (!inv.equals(null)) {
				query += "investigator_id = '" + inv + "'";
				if (!analyst.equals(null)) {
					query += ", analyst_id = '" + analyst + "'";
				}
			} else {
				if (!analyst.equals(null)) {
					query += "analyst_id = " + analyst;
				} else {
					query = "";
				}
			}
			if (query != "") {
				query += "WHERE case_id = " + Integer.parseInt(txtCaseId.getText());
				Statement stmt = con.createStatement();
				int count = stmt.executeUpdate(query);
				if (count == 1) {
					MessageBox msgbox = new MessageBox(getShell(), SWT.ICON_INFORMATION);
					msgbox.setText("Case Transfer");
					msgbox.setMessage("Case Transfered Successfully");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
