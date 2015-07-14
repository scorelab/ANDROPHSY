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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;

/**
 * @author indeewari
 *
 */
public class TableComposite extends Composite {
	private Table table;
	private HexViewer hv;
	private boolean isKeyword;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public TableComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));

		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event arg0) {
				TableItem itm = table.getItem(table.getSelectionIndex());
				System.out.println("TableItem " + itm);
				if (itm != null && isKeyword) {
					String off = itm.getText(0);
					System.out.println("offff " + off);
					getHexdump(off);
				}
			}
		});

		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		hv = new HexViewer(this, SWT.NONE);
		hv.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		hv.setVisible(false);
	}

	public void setHexVisible(boolean b) {
		hv.setVisible(b);
	}

	public void populate(ResultSet rs) throws SQLException {
		table.removeAll();
		table.setItemCount(0);
		TableColumn cols[] = table.getColumns();
		for (TableColumn c : cols) {
			c.dispose();
		}

		ResultSetMetaData rsm = rs.getMetaData();
		int colcount = rsm.getColumnCount();

		for (int i = 1; i <= colcount; i++) {
			TableColumn tcol = new TableColumn(table, SWT.NONE);
			tcol.setText(rsm.getColumnLabel(i));
			tcol.setWidth(100);
		}

		String values[] = new String[colcount];

		while (rs.next()) {
			TableItem titem = new TableItem(table, SWT.NONE);
			for (int i = 1; i <= colcount; i++) {
				values[(i - 1)] = rs.getString(i);
				System.out.println("inside table " + values[(i - 1)]);
			}
			titem.setText(values);
			table.setLinesVisible(true);
		}
	}

	/**
	 * populates the results table with the SOLR results
	 * @param results
	 * @throws SQLException
	 */
	public void populate(Map<String,Set<String>> results) throws SQLException {
		if(results.size() < 1)
			return;
		table.removeAll();
		table.setItemCount(0);
		TableColumn cols[] = table.getColumns();
		for (TableColumn c : cols) {
			c.dispose();
		}

		final int no_columns = 2;
		final String[] columnLabels = {"Doc id", "Matching hex_values"};

		for (int i = 0; i < no_columns; i++) {
			TableColumn tcol = new TableColumn(table, SWT.NONE);
			tcol.setText(columnLabels[i]);
			tcol.setWidth(100);
		}

		String values[] = new String[no_columns];

		for (String key : results.keySet()) {
			TableItem titem = new TableItem(table, SWT.NONE);
			for (int i = 1; i <= no_columns; i++) {
				values[(i - 1)] = key;
			}
			values[0] = key;
			values[1] = results.get(key).toString();
			titem.setText(values);
			table.setLinesVisible(true);

		}
	}

	protected void readOffset(String srch, boolean casesensitive) {
		try {
			table.removeAll();
			table.setItemCount(0);
			TableColumn cols[] = table.getColumns();
			for (TableColumn c : cols) {
				c.dispose();
			}

			System.out.println("... search key word");
			String line = "", errline = "";
			String values[] = new String[2];

			TableColumn tcoloff = new TableColumn(table, SWT.NONE);
			tcoloff.setText("Offset");
			tcoloff.setWidth(100);

			TableColumn tcolval = new TableColumn(table, SWT.NONE);
			tcolval.setText("Keyword occurance");
			tcolval.setWidth(500);

			String srchcmd = "";
			if (!casesensitive) {
				srchcmd = "grep -i ";
			} else {
				srchcmd = "grep ";
			}
			srchcmd += srch + " " + AndrospyMain.gb_CasePath + "Analysis/keywords.txt";
			Process proc = Runtime.getRuntime().exec(srchcmd);
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));

			while ((line = bufin.readLine()) != null) {
				TableItem titem = new TableItem(table, SWT.NONE);

				line = line.trim();
				int firstspace = line.indexOf(" ");
				values[0] = line.substring(0, firstspace);
				values[1] = line.substring(firstspace);
				System.out.println("> " + values[0] + " > " + values[1]);
				titem.setText(values);
				table.setLinesVisible(true);
			}
			BufferedReader buferr =
			                        new BufferedReader(new InputStreamReader(proc.getErrorStream()));
			while ((errline = buferr.readLine()) != null) {
				System.out.println(errline);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void getHexdump(String offsetlist) {
		long firstindex = Long.parseLong(offsetlist, 16) - 25;
		System.out.println("start index: " + firstindex);

		try {
			Process proc =
			               Runtime.getRuntime().exec("hexdump -s " + firstindex + " -n 200 " +
			                                                 " -C " + AndrospyMain.gb_CasePath +
			                                                 "Acquire/data.dd");
			BufferedReader bufin = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = "", result = "";
			while ((line = bufin.readLine()) != null) {
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

			hv.setHexText(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setKeyword(boolean b) {
		isKeyword = b;
	}
}
