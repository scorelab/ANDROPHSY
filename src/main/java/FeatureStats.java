// Copyright 2015 Indeewari Akarawita
//
//    This file is a part of ANDROPHSY
//
//    ANDROPHSY is free software: you can redistribute it and/or modify
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
import java.util.ArrayList;
import java.util.Iterator;

import org.csstudio.swt.xygraph.dataprovider.CircularBufferDataProvider;
import org.csstudio.swt.xygraph.figures.Trace;
import org.csstudio.swt.xygraph.figures.Trace.TraceType;
import org.csstudio.swt.xygraph.figures.XYGraph;
import org.csstudio.swt.xygraph.linearscale.AbstractScale.LabelSide;
import org.csstudio.swt.xygraph.util.XYGraphMediaFactory;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.TableItem;


/**
 * @author indeewari
 *
 */
public class FeatureStats extends Composite {
	private Table table;
	private int index;
	private double max;
	private Canvas canvas;
		

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FeatureStats(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		Label lblNewLabel = new Label(this, SWT.NONE);
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_table.widthHint = 421;
		gd_table.heightHint = 161;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnIndex = new TableColumn(table, SWT.NONE);
		tblclmnIndex.setWidth(55);
		tblclmnIndex.setText("Index");
		
		TableColumn tblclmnValue = new TableColumn(table, SWT.NONE);
		tblclmnValue.setWidth(295);
		tblclmnValue.setText("Value");
		
		TableColumn tblclmnFrequency = new TableColumn(table, SWT.NONE);
		tblclmnFrequency.setWidth(86);
		tblclmnFrequency.setText("Frequency");
		
		canvas = new Canvas(this, SWT.NONE);
		GridData gd_canvas = new GridData(SWT.CENTER, SWT.FILL, false, false, 1, 1);
		gd_canvas.heightHint = 350;
		gd_canvas.widthHint = 875;
		canvas.setLayoutData(gd_canvas);
		canvas.setSize(599, 169);
	}
	
	protected void generateStatistics(String name, String filename){
		ArrayList<String> xval = new ArrayList<String>();
		ArrayList<String> yval = new ArrayList<String>();
		File file = new File(filename);
		index 			= 1;		
		double temp;
		max 			= Double.MIN_VALUE;
		String freq 		= "-1";
		String statData[] 	= new String[3];
		
		this.table.removeAll();
		this.table.setItemCount(0);	
	    
		try {
			FileReader fr = new FileReader(file);
			BufferedReader buf 	= new BufferedReader(fr);
		    String lines 		= buf.readLine();
		    while(lines != null){
		    	if(lines.startsWith("n=")){
		    		if(index > 20){
		    			break;
		    		}
		    		String content[] = lines.split("\t");
		    		if(content.length <= 1){
		    			lines = buf.readLine();
		    			continue;
		    		}
					freq 	  		 = content[0].substring(2);
					temp 			 = Double.parseDouble(freq);
					if(max < temp){
					   max = temp;
					}
				    statData[2] 	= freq;
				    
				    if(name == "Facebook Profile ID"){
				    	statData[1] 	= "https://facebook.com/profile.php?id=" + content[1];
				    }
				    else{
				    	statData[1] 	= content[1];
				    }
				    statData[0] 	= String.valueOf(index++);
				    xval.add(statData[0]);
				    yval.add(statData[2]);
				    TableItem item 	= new TableItem(this.table, SWT.NONE);
				    item.setText(statData);
				    this.table.setLinesVisible(true);
				 }
			   lines = buf.readLine();
		    }
		    plotGraph(name, xval, yval, index, max); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	protected void plotGraph(String graphName, ArrayList< String> x, ArrayList<String> y, int xmax, double ymax){
		double xvalues[] = new double[xmax];
		double yvalues[] = new double[xmax];
		int i = 0;
		int bar = 5;
		Iterator<String> itx = x.iterator();
		while(itx.hasNext()){
			xvalues[i] = Double.parseDouble(itx.next());			
			i++;
		}
		
		i = 0;
		Iterator<String> ity = y.iterator();
		while(ity.hasNext()){
			yvalues[i] = Double.parseDouble(ity.next());
			i++;
		}
		
		LightweightSystem lws = new LightweightSystem(this.canvas);
		XYGraph xygraph = new XYGraph();
		xygraph.setTitle("Graph: Top 20 " + graphName + " Information");
		lws.setContents(xygraph);
		
		xygraph.primaryXAxis.setShowMajorGrid(true);
		xygraph.primaryXAxis.setMajorTickMarkStepHint(50);
		xygraph.primaryXAxis.setRange(0, xmax);
		xygraph.primaryXAxis.setTickLableSide(LabelSide.Primary);
		xygraph.primaryXAxis.setTitle("Index");
		xygraph.primaryYAxis.setRange(0, ymax);
		xygraph.primaryYAxis.setTitle("Frequency");
		CircularBufferDataProvider traceDataProvider = new CircularBufferDataProvider(false);
		traceDataProvider.setBufferSize(50);
		traceDataProvider.setCurrentXDataArray(xvalues);
		traceDataProvider.setCurrentYDataArray(yvalues);
		
		Trace trace = new Trace(graphName + " data", xygraph.primaryXAxis, xygraph.primaryYAxis, traceDataProvider);
		trace.setTraceType(TraceType.BAR);
		
		trace.setLineWidth(10);
		trace.setAreaAlpha(100);
		trace.setTraceColor(XYGraphMediaFactory.getInstance().getColor(XYGraphMediaFactory.COLOR_BLUE));
		
		xygraph.addTrace(trace);
	}

}
