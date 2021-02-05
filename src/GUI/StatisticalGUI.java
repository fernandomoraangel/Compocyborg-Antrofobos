package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;

import javax.swing.border.EtchedBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;

import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.table.DefaultTableModel;

import music.Melody;
import Utilities.ExcelExporter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Vector;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ScrollPaneConstants;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class StatisticalGUI extends JFrame {
private int columns=0;
private int selectedRow=0;
private String phenotipe="";
private JTable table;
private int stateclick=0;
private boolean [] viewved;

	/**
	 * Create the frame.
	 * @param vectorColumnNames 
	 * @param currentExecution 
	 */
	@SuppressWarnings("serial")
	public StatisticalGUI(final String[][] data, Vector vectorColumnNames, final int currentExecution) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				mainGui.setMusicPosition();
			}
		});
this.setIconImage(Toolkit.getDefaultToolkit().getImage(mainGui.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		
		final String[] columnNames = new String[vectorColumnNames.size()];
		columns=vectorColumnNames.size();
		for(int i=0;i<vectorColumnNames.size();i++)
		{
			columnNames[i]=vectorColumnNames.elementAt(i).toString();
		}
		DefaultTableModel dtm= new DefaultTableModel(data,columnNames);
		table = new JTable(dtm);
	
		viewved=new boolean[table.getRowCount()];
		double max=Double.parseDouble((String) table.getValueAt(0,1));
		int maxIndex=0;
		for(int i=0;i<table.getRowCount();i++)
		{
			if(Double.parseDouble((String) table.getValueAt(i,1))>max)
			{
				max=Double.parseDouble((String) table.getValueAt(i,1));
				viewved[i]=false;
				maxIndex=i;
			}
			table.setRowSelectionInterval(maxIndex, maxIndex);
		}
		table.setAutoCreateRowSorter(true);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				selectedRow= table.getSelectedRow();
				if (!arg0.isMetaDown() &stateclick==1)//click izquierdo
				{		
					if(!viewved[selectedRow])
					{
						phenotipe= data[table.getSelectedRow()][columns];
						Melody myMelody = new Melody(phenotipe,Integer.parseInt(GUI.mainGui.getMyProperty("Tempo")),
								Integer.parseInt(GUI.mainGui.getMyProperty("Melody_expected_time_signature_numerator")),
								Integer.parseInt(GUI.mainGui.getMyProperty("Melody_expected_time_signature_denominator")));
						myMelody.saveToMidi(table.getSelectedRow(), GUI.mainGui.getMyProperty("Work_path")+"\\"+GUI.mainGui.getMyProperty("Midi_prefix"));
						myMelody.playAndNotate(table.getSelectedRow(), false,  "ejecución "+currentExecution+", generación "+table.getSelectedRow()+ ", Fitness "+ myMelody.calculeFitness());
						viewved[selectedRow]=true;
					}
					
					
				}
				else if(arg0.isMetaDown() & stateclick==1 )
				{
						String name=table.getColumnName(table.getSelectedColumn());
						GraphicalGui graphicFrame= new GraphicalGui(table,table.getSelectedColumn(),name);
						graphicFrame.setSize(450,450);
						graphicFrame.setVisible(true);
				}
				if(stateclick==0)
				{
					stateclick=1;
				}
				else
				{
					stateclick=0;
				}
				
			}
		});
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setAutoscrolls(true);
		getContentPane().add(scrollPane, BorderLayout.NORTH);
		
		table.setPreferredScrollableViewportSize(new Dimension(600, 200));
		table.setAutoResizeMode(1);
		JButton btnExportarMidi = new JButton("Exportar MIDI");
		btnExportarMidi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveMidiFile();
			}
		});
		
		
		JButton btnExportarAExcel = new JButton("Exportar a Excel");
		btnExportarAExcel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File f=new File(GUI.mainGui.getMyProperty("Work_path")+"\\Informe estadístico.xls");
				ExcelExporter ee =new ExcelExporter(table,f,"Melodías generadas",columnNames);
				if(ee.export())
				{
					JOptionPane
					//.showConfirmDialog(parentComponent, "Exportado", "Exportado", optionType, messageType, icon)
					.showMessageDialog(null, "Archivo exportado con éxito en "+GUI.mainGui.getMyProperty("Work_path")+"\\Informe estadístico.xls");
				}
				else
				{
					JOptionPane
					.showMessageDialog(null, "Problemas al exportar");
				}
			}
		});
		getContentPane().add(btnExportarAExcel, BorderLayout.CENTER);
		getContentPane().add(btnExportarMidi, BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);
	}
	private void saveMidiFile()
	{
		String path = null;
		JFileChooser chooser = new JFileChooser(GUI.mainGui.getMyProperty("Work_path"));
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("MIDI",".mid");
	    chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {

	    		path = chooser.getSelectedFile().getPath();
			Melody myMelody = new Melody(phenotipe,Integer.parseInt(GUI.mainGui.getMyProperty("Tempo")),
					Integer.parseInt(GUI.mainGui.getMyProperty("Melody_expected_time_signature_numerator")),
					Integer.parseInt(GUI.mainGui.getMyProperty("Melody_expected_time_signature_denominator")));
			myMelody.saveToMidi(path+".mid");
		} 
	}

	}

