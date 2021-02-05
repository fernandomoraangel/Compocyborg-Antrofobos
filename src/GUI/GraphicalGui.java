package GUI;



import org.jfree.data.time.DateRange;
import org.jfree.data.xy.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JTable;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;


	/**
	 *
	 * @author  Roberto Leon Cruz
	 */
	public class GraphicalGui extends JFrame  {
		private double x;
		private double r2;
		private double r1;
		private NumberAxis domainAxis;
		private JTable table;
		private int YValues;
		private XYLineAndShapeRenderer rendered;
		private static Color COLOR_SERIE_1 = new Color(1, 1, 1);
		private double zoomFactor;
	    private static Color COLOR_FONDO_GRAFICA = Color.white;
		private XYSeriesCollection juegoDatos1,juegoDatos2;
		private double min;
		private double max;
		private NumberAxis rangeAxis;
		private XYPlot[]  plot;
		private String Yname;
	
		
	    //constructor
	    public GraphicalGui(JTable table, int y, String Yname) {
	        super("");
	        this.setIconImage(Toolkit.getDefaultToolkit().getImage(mainGui.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
	        this.table=table;
	        this.YValues=y;
	        this.Yname=Yname;
	        XYSeries serie1 = new XYSeries("");
	        min=Double.parseDouble((String) table.getValueAt(0, YValues));
	        max=Double.parseDouble((String) table.getValueAt(0, YValues));

	        for(int i=0;i<table.getRowCount();i++)
	        {
	        	if(Double.parseDouble((String) table.getValueAt(i, YValues))<min)
	        	{
	        		min=Double.parseDouble((String) table.getValueAt(i, YValues));
	        	}
	        	if(Double.parseDouble((String) table.getValueAt(i, YValues))>max)
	        	{
	        		max=Double.parseDouble((String) table.getValueAt(i, YValues));
	        	}
	        	serie1.add(i,Double.parseDouble((String) table.getValueAt(i, YValues)));

	        	
	        }
	        if(table.getRowCount()<11)
	        {
	        	zoomFactor=1.1;
	        }
	        if(table.getRowCount()>10 & table.getRowCount()<101)
	        {
	        	zoomFactor=5;
	        }
	        if(table.getRowCount()>100 & table.getRowCount()<1001 )
	        {
	        	zoomFactor=10;
	        }
	        if(table.getRowCount()>1000 & table.getRowCount()<10001 )
	        {
	        	zoomFactor=100;
	        }
	       
	        //se crea un objeto XYDataset requerido mas adelante por el metodo que grafica
	        juegoDatos1= new XYSeriesCollection(serie1);

	        // This will create the dataset 
	       
	        // based on the dataset we create the chart
	        JFreeChart chart = createChart();
	        
	        // we put the chart into a panel
	        ChartPanel chartPanel = new ChartPanel(chart);
	        // default size
	        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	        // add it to our application
	       
	        getContentPane().add(chartPanel);
	        getContentPane().add(getScrollBar(domainAxis), BorderLayout.NORTH);
	        this.pack();
	        
	       
	    }
	    
	    
	    private JScrollBar getScrollBar(final ValueAxis domainAxis){
	        final double r1 =domainAxis.getLowerBound();
	        final double r2 = domainAxis.getUpperBound();
	        JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, table.getRowCount()-(int)(table.getRowCount()/zoomFactor));
	        
	        scrollBar.addAdjustmentListener( new AdjustmentListener() {
	            private int x;

				public void adjustmentValueChanged(AdjustmentEvent e) {
	                x = e.getValue();
	               domainAxis.setRange(r1+x, r2+x);
	            }
	        });
	        return scrollBar;
	    }
              
        private JFreeChart createChart()
        {
        	//"generación"+" vs "+Yname,"generación"
        	domainAxis = new NumberAxis("");
        	NumberAxis rangeAxis = new NumberAxis("");
        	domainAxis.setAutoRange(false);
            domainAxis.setRange(new DateRange(0, table.getRowCount()/zoomFactor));
        	 //XYBarRenderer renderer = new XYBarRenderer(0.1);
             //domainAxis.setTickMarkPosition(NumberTickMarkPosition.MIDDLE);
            rendered= new XYLineAndShapeRenderer();
            
        	XYPlot plot =  new XYPlot(juegoDatos1, domainAxis, rangeAxis, rendered );
             JFreeChart chart = new JFreeChart(null, null, plot, false);  
             
             ChartPanel chartPanel = new ChartPanel(chart);
             chartPanel.setPreferredSize(new Dimension(600, 600));   
         	plot.setDomainGridlinesVisible(true);
        	// color de fondo de la gráfica
            chart.setBackgroundPaint(COLOR_FONDO_GRAFICA);
           
            //domainAxis.setAutoRange(false);
            plot.setBackgroundAlpha(0.9f);
            plot.setDomainGridlinesVisible(true);
            //domainAxis.setRange(0, 100);
            plot.setRangeCrosshairVisible(true);
            
            configureRendered(rendered);
            rangeAxis = (NumberAxis)plot.getRangeAxis();
            configureRangeAxis(rangeAxis);
            //domainAxis.setRange(0.0, 100.0);
            //domainAxis = plot.getDomainAxis();
            
            configurarDomainAxis(domainAxis);
			return chart;
        }

         
        // configuramos el eje y de la gráfica (números enteros de dos en dos y rango entre 120 y 135)
        private void configureRangeAxis (NumberAxis rangeAxis) {
        	rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
            //rangeAxis.setTickUnit(new NumberTickUnit(1));
            rangeAxis.setRange(min, max);
            rangeAxis.setLabel(Yname);
        }
        private void configureRendered (XYLineAndShapeRenderer rendered2) {
            rendered2.setSeriesPaint(0, COLOR_SERIE_1);


        }
	  
         //configuramos el eje X de la gráfica (se muestran números enteros y de uno en uno)
        private void configurarDomainAxis (ValueAxis domainAxis2DateAxis ) {
           domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
           domainAxis.setLabel("Generaciones");
            domainAxis.setTickUnit(new NumberTickUnit(5));
            
            
        }
	        
	        
	       
	        



}
	
