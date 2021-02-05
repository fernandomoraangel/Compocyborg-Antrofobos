package GUI;

import java.awt.EventQueue;
import java.io.*;
import java.text.NumberFormat;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JCheckBoxMenuItem;

import music.Melody;
import evolution.Evolutionator;
import evolution.MyStatisticsCollectionOperation;

public class mainGui {

	public JFrame frmCompocyborgAntrofobosV;
	
	
	private static JButton btnGenerarMelodias_1;
	private static JButton btnConfigurarAjuste;
	private static JButton btnConfigurarEvolucion;
	private static int [] musicPosition={610,-140};
	private Evolutionator evolutionator;
	private boolean viewStats=true;
	private String[][] data;
	private static Properties properties;
	private static Properties propertiesFile;
	private int bestGen=0;
	private int currentExecution=1;
	private static String propertiesFileName;
	private static int displayedWindows=0;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainGui window = new mainGui();
					window.frmCompocyborgAntrofobosV.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		try {
			File f0= new File("propertiesFile.properties");
			if(!f0.exists())
			{
				FileWriter fw=new FileWriter("propertiesFile.properties",true);
				fw.write("File_properties_name=Evolution.properties");
				fw.close();
			}
			propertiesFile=new Properties();
			propertiesFile.load(new FileInputStream("propertiesFile.properties"));
			properties=new Properties();
			File f= new File(propertiesFile.getProperty("File_properties_name"));
			propertiesFileName=propertiesFile.getProperty("File_properties_name");
			if(f.exists())
			{
				FileInputStream foo  = new FileInputStream(propertiesFile.getProperty("File_properties_name"));
				properties.load(foo);
				foo.close();
			}
			else
			{
				try {
					FileOutputStream fos = new FileOutputStream("propertiesFile.properties");
					propertiesFile.store(fos,null);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				File fi= new File(propertiesFile.getProperty("File_properties_name"));
				if(!fi.exists())
				{
					
					storeProperties();
					
				}

				if(f.exists())
				{
					FileInputStream foo = null;
					try {
						foo = new FileInputStream(propertiesFile.getProperty("File_properties_name"));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						properties.load(foo);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				properties.setProperty("Sharp_direction_index", "0");
				properties.setProperty("Melody_expected_length_W", "0");
				properties.setProperty("Intervalic_variety_W", "0");
				properties.setProperty("population_size_W", "100");
				properties.setProperty("Avoid_interval_1_index", "0");
				properties.setProperty("Tonic", "C");
				properties.setProperty("Melodic_range_W", "0");
				properties.setProperty("crossover_probability", "0.5");
				properties.setProperty("Lowest_note_octave", "5");
				properties.setProperty("null", "Integer (32 bit)");
				properties.setProperty("tournament_size", "5");
				properties.setProperty("Reiterate_of_the_dominant_note_W", "0");
				properties.setProperty("Alterations_count_W", "0");
				properties.setProperty("initialiser", "Operator.Initialiser");
				properties.setProperty("Active_degree_3_direction", "3");
				properties.setProperty("Melody_expected_time_signature_denominator", "4");
				properties.setProperty("evaluate_elites", "false");
				properties.setProperty("Avoid_tritone_jump_W", "0");
				properties.setProperty("Melody_expected_time_signature_numerator", "4");
				properties.setProperty("Resolve_active_degrees_4_W", "0");
				properties.setProperty("Active_degree_4", "2");
				properties.setProperty("Active_degree_3", "6");
				properties.setProperty("Upward_movement_W", "0");
				properties.setProperty("Active_degree_2", "4");
				properties.setProperty("Active_degree_1_save", "false");
				properties.setProperty("Just_time_init_W", "0");
				properties.setProperty("Resolve_active_degrees_3_W", "0");
				properties.setProperty("Active_degree_1", "7");
				properties.setProperty("Lowest_note", "C");
				properties.setProperty("Resolve_active_degrees_2_W", "0");
				properties.setProperty("generation_gap", "0.1");
				properties.setProperty("Resolve_active_degrees_1_W", "0");
				properties.setProperty("Active_degree_1_direction", "0");
				properties.setProperty("initial_chromosome_size", "10");
				properties.setProperty("Melodic_variety_W", "0");
				properties.setProperty("Initial_pickup_beat_W", "0");
				properties.setProperty("Sharp_resolution_W", "0");
				properties.setProperty("Tonic_end_W", "0");
				properties.setProperty("Only_n_climax_W", "0");
				properties.setProperty("Avoid_interval_2_W", "1");
				properties.setProperty("Active_degree_2_save", "false");
				properties.setProperty("Avoid_interval_1_W", "0");
				properties.setProperty("Rhythmic_variety_W", "0");
				properties.setProperty("Only_n_climax_N", "1");
				properties.setProperty("replacement_type", "generational");
				properties.setProperty("generations", "10");
				properties.setProperty("Dominant", "G");
				properties.setProperty("max_wraps", "3");
				properties.setProperty("fixed_point_crossover", "true");
				properties.setProperty("elite_size", "5");
				properties.setProperty("Prohibit_the_repetition_of_sounds_W", "0");
				properties.setProperty("Active_degree_3_save", "false");
				properties.setProperty("Syncopes_W", "0");
				properties.setProperty("Active_degree_4_direction", "3");
				properties.setProperty("Make_series", "false");
				properties.setProperty("Tempo", "120");
				properties.setProperty("selection_operation", "Operator.Operations.TournamentSelect");
				properties.setProperty("crossover_operation", "Operator.Operations.ShapeGrammarSelect");
				properties.setProperty("mutation_operation", "Operator.Operations.IntFlipByteMutation");
				properties.setProperty("Melody_expected_length", "8");
				properties.setProperty("Save_midi", "true");
				properties.setProperty("derivation_tree", "Mapper.DerivationTree");
				properties.setProperty("Rest_abusse_W", "0");
				properties.setProperty("Joint_degrees_movement_W", "0");
				properties.setProperty("max_depth", "50");
				properties.setProperty("Flat_direction_index", "0");
				properties.setProperty("Tonic_start_W", "0");
				properties.setProperty("Avoid_interval_1_name", "2m");
				properties.setProperty("Highest_note", "B");
				properties.setProperty("Flat_resolution_W", "0");
				properties.setProperty("Active_degree_4_save", "false");
				properties.setProperty("grammar_file", "melodias.bnf");
				properties.setProperty("mutation_probability", "0.5");
				properties.setProperty("tail_percentage", "0.1");
				properties.setProperty("Initial_contretemp_W", "0");
				properties.setProperty("Downward_movement_W", "0");
				properties.setProperty("Dominant_start_W", "0");
				properties.setProperty("Active_degree_2_direction", "2");
				properties.setProperty("stopWhenSolved", "false");
				properties.setProperty("fitness_function", "music.MusicEval");
				properties.setProperty("Only_n_cenit_W", "0");
				properties.setProperty("grow_probability", "0.2");
				properties.setProperty("Reiterate_of_the_tonic_note_W", "0");
				properties.setProperty("Avoid_interval_2_name", "2m");
				properties.setProperty("Only_n_cenit_N", "1");
				properties.setProperty("Avoid_interval_2_index", "0");
				properties.setProperty("HighestNoteOctave", "5");
				properties.setProperty("Work_path", "C\\:\\Users\\Fernando Mora Angel\\Documents");
				properties.setProperty("Midi_prefix","");
				properties.setProperty("population_size","100");
				
				storeProperties();
				FileInputStream foo  = new FileInputStream(propertiesFile.getProperty("File_properties_name"));
				properties.load(foo);
				foo.close();
			}
			
			if(properties.getProperty("Work_path").equals(""))
			{
				JOptionPane.showMessageDialog(null,"Debe seleccionar un directorio de trabajo antes de continuar");
			}
			else
			{
				File dir= new File(properties.getProperty("Work_path"));
				if(!dir.exists() | !dir.isDirectory())
				{
					JOptionPane.showMessageDialog(null,"Debe seleccionar un directorio de trabajo antes de continuar");
						JFileChooser chooser = new JFileChooser(properties.getProperty("Work_path"));
						chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int returnVal = chooser.showOpenDialog(null);
					    if(returnVal == JFileChooser.APPROVE_OPTION) { 	
					    		properties.setProperty("Work_path", chooser.getSelectedFile().getPath());
						} 
					    
					    storeProperties();
				}	
			}
				
		} 
		 catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frmCompocyborgAntrofobosV = new JFrame();
		frmCompocyborgAntrofobosV.setIconImage(Toolkit.getDefaultToolkit().getImage(mainGui.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		frmCompocyborgAntrofobosV.setFont(new Font("Dialog", Font.PLAIN, 5));
		frmCompocyborgAntrofobosV.setTitle("Compocyborg Antrofobos v. 1.3.2");
		frmCompocyborgAntrofobosV.setResizable(false);
		frmCompocyborgAntrofobosV.setBounds(100, 100, 292, 210);
		frmCompocyborgAntrofobosV.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCompocyborgAntrofobosV.getContentPane().setLayout(null);
		btnConfigurarEvolucion = new JButton("Configurar Evoluci\u00F3n");
		btnConfigurarEvolucion.setToolTipText("Selecci\u00F3n de los par\u00E1metros que afectan el algor\u00EDtmo evolutivo");
		btnConfigurarEvolucion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				ConfigEvolutionPropertiesGUI evolutionPropertiesFrame = new ConfigEvolutionPropertiesGUI();
				evolutionPropertiesFrame.setVisible(true);
			}
		});
		btnConfigurarEvolucion.setBounds(30, 24, 218, 24);
		frmCompocyborgAntrofobosV.getContentPane().add(btnConfigurarEvolucion);
		
		btnConfigurarAjuste = new JButton("Configurar ajuste");
		btnConfigurarAjuste.setToolTipText("Selecci\u00F3n de los par\u00E1metros musicales que permiten valorar las melod\u00EDas generadas");
		btnConfigurarAjuste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConfigMusicPropertiesGUI musicPropertiesFrame =new  ConfigMusicPropertiesGUI();
				musicPropertiesFrame.setVisible(true);
			}
		});
		btnConfigurarAjuste.setBounds(30, 67, 218, 24);
		frmCompocyborgAntrofobosV.getContentPane().add(btnConfigurarAjuste);
		
		btnGenerarMelodias_1 = new JButton("Generar melod\u00EDas");
		btnGenerarMelodias_1.setToolTipText("Ordena la generaci\u00F3n de melod\u00EDas");

		btnGenerarMelodias_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateMelodies();
			}
		});
		btnGenerarMelodias_1.setBounds(30, 110, 218, 24);
		frmCompocyborgAntrofobosV.getContentPane().add(btnGenerarMelodias_1);
		
		JMenuBar menuBar = new JMenuBar();
		frmCompocyborgAntrofobosV.setJMenuBar(menuBar);
		
		JMenu mnPreferencias = new JMenu("Preferencias");
		mnPreferencias.setOpaque(true);
		mnPreferencias.setBorderPainted(false);
		menuBar.add(mnPreferencias);
		
		JMenuItem mntmDirectorioDeTrabajo = new JMenuItem("Definir directorio de trabajo");
		mntmDirectorioDeTrabajo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser(properties.getProperty("Work_path"));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = chooser.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) { 	
			    		properties.setProperty("Work_path", chooser.getSelectedFile().getPath());
				} 
			    
			    storeProperties();
			}
		});
		
		JMenuItem mntmGuardarConfiguracinDe = new JMenuItem("Asignar archivo de configuraci\u00F3n");
		mntmGuardarConfiguracinDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				defineConfigPath();
				//propertiesFileName=propertiesFile.getProperty("File_properties_name");
			}
		});
		mnPreferencias.add(mntmGuardarConfiguracinDe);
		
		JMenuItem mntmDefinirGramticaPor = new JMenuItem("Asignar archivo de gram\u00E1tica");
		mntmDefinirGramticaPor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(properties.getProperty("Work_path"));
			    FileNameExtensionFilter filter = new FileNameExtensionFilter("Gramáticas","BNF");
			    chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(null);
			    if(returnVal == JFileChooser.APPROVE_OPTION) {
			    	properties.setProperty("grammar_file",chooser.getSelectedFile().getPath().replace("\\", "\\\\"));
			    }
			    storeProperties();
			}
		});
		mnPreferencias.add(mntmDefinirGramticaPor);
		mnPreferencias.add(mntmDirectorioDeTrabajo);
		
		JMenuItem mntmPrefijoArchivosMidi = new JMenuItem("Definir prefijo archivos MIDI");
		mntmPrefijoArchivosMidi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				String tmp=JOptionPane.showInputDialog("", properties.getProperty("Midi_prefix",""));
				if(tmp!=null)
				{
					properties.setProperty("Midi_prefix",tmp);
				}
				
			       storeProperties();

			}
			
		});
		mnPreferencias.add(mntmPrefijoArchivosMidi);
		
		JMenuItem mntmTempo = new JMenuItem("Definir tempo");
		mntmTempo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String tmp=JOptionPane.showInputDialog("", properties.getProperty("Tempo",""));
				if(tmp!=null)
				{
					properties.setProperty("Tempo",tmp);
				}
				
			       storeProperties();
			}
			
		});
		mnPreferencias.add(mntmTempo);
		
		final JCheckBoxMenuItem chckbxmntmVerEstadsticas = new JCheckBoxMenuItem("Ver estad\u00EDsticas");
		chckbxmntmVerEstadsticas.setSelected(true);
		chckbxmntmVerEstadsticas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxmntmVerEstadsticas.isSelected())
				{
					viewStats=true;	
				}
				else
				{
					viewStats=false;
				}
			}
		});
		
		final JCheckBoxMenuItem chckbxmntmGuardarArchivosMidi = new JCheckBoxMenuItem("Guardar archivos MIDI autom\u00E1ticamente");
		chckbxmntmGuardarArchivosMidi.setSelected(true);
		
		if(properties.getProperty("Save_midi", "false").equals("true"))
		{
			chckbxmntmGuardarArchivosMidi.setSelected(true);
		}
		else
		{
			chckbxmntmGuardarArchivosMidi.setSelected(false);
		}
		chckbxmntmGuardarArchivosMidi.addActionListener(new ActionListener() {
		

			public void actionPerformed(ActionEvent e) {
				if(chckbxmntmGuardarArchivosMidi.isSelected())
				{
					properties.setProperty("Save_midi", "true");
				}
				else
				{
					properties.setProperty("Save_midi", "false");
				}
				storeProperties();
			}
		});
		mnPreferencias.add(chckbxmntmGuardarArchivosMidi);
		mnPreferencias.add(chckbxmntmVerEstadsticas);
		JMenu mnHelp = new JMenu("Ayuda");
		mnHelp.setBorder(null);
		menuBar.add(mnHelp);
		JMenuItem mntmAbout = new JMenuItem("Acerca de");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "Fernando Mora Ángel, UdeA, 2014");
			}
		});
		mnHelp.add(mntmAbout);
		JMenuItem mntmAyuda = new JMenuItem("Ayuda");
		mnHelp.add(mntmAyuda);
	}
	

	public static String getPropertiesFileName() {
		return propertiesFileName;
	}

	public void setPropertiesFileName(String propertiesFileName) {
		this.propertiesFileName = propertiesFileName;
	}

	protected static void storeProperties() {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(propertiesFile.getProperty("File_properties_name"));
			properties.store(fos,null);
			fos.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	protected void generateMelodies() {
		currentExecution=1;
		for (int k = 0; k < GUI.ConfigEvolutionPropertiesGUI.getExecutions(); k++) {
			evolutionator = new Evolutionator();
			evolutionator.experiment();
			
			double fitness=0;
			double bestFitness=0;
			for (int i = 0; i < Integer
					.parseInt(properties.getProperty("generations")); i++)
			{

				fitness=((MyStatisticsCollectionOperation) evolutionator.getCollector().getOperation())
						.getStats()
						.getBestIndividualOfGenerations()
						.get(i).getFitness()
						.getDouble();
				if(fitness>bestFitness)
				{
					bestFitness=fitness;
					bestGen=i;
				}
			}
			
			try {
				Melody myMelody = new Melody(
						((MyStatisticsCollectionOperation) evolutionator
								.getCollector().getOperation())
								.getStats()
								.getBestIndividualOfGenerations()
								.get(bestGen)
								.getPhenotype().toString(),
						Integer.parseInt(properties.getProperty("Tempo")),
						Integer.parseInt(properties.getProperty("Melody_expected_time_signature_numerator","4")),
						Integer.parseInt(properties.getProperty("Melody_expected_time_signature_denominator","4")));
				
						if(properties.getProperty("Save_midi").equals("true"))
						{
							
							myMelody.saveToMidi(bestGen, properties.getProperty("Work_path","")+"\\"+properties.getProperty("Midi_prefix"));
						}
						else
						{
							
							myMelody.saveToMidi(bestGen, properties.getProperty("Work_path","")+"\\"+"tmp");
							
						}
						
					myMelody.playAndNotate(bestGen, false, "Ejecución "+currentExecution+", generación "+bestGen+ ", Fitness "+ myMelody.calculeFitness());
					if(properties.getProperty("Save_midi").equals("false"))
				{
					File archivoMidi = new File(properties.getProperty("Work_path")+"\\"+bestGen+"tmp.mid");
					archivoMidi.delete();
				}
				
				if (viewStats) {
					
					@SuppressWarnings("rawtypes")
					Vector vectorColumnNames = new Vector(3, 1);
					vectorColumnNames.addElement("Gen.");
					vectorColumnNames.addElement("Fitness");
					vectorColumnNames.addElement("Compases");

					if (!properties.getProperty("Melody_expected_length_W").equals("0")) {
						vectorColumnNames.addElement("P. Longitud ("
								+ properties.getProperty("Melody_expected_length_W")
								+ ")");
					}
					
					if (!properties.getProperty("Melodic_variety_W").equals("0")) {
						vectorColumnNames.addElement("V. Melódica ("
								+ properties.getProperty("Melodic_variety_W") + ")");
					}

					if (!properties.getProperty("Rhythmic_variety_W").equals("0")) {
						vectorColumnNames.addElement("V. Rítmica ("
								+ properties.getProperty("Rhythmic_variety_W") + ")");
					}

					if (!properties.getProperty("Intervalic_variety_W").equals("0")) {
						vectorColumnNames.addElement("V. Interválica ("
								+ properties.getProperty("Intervalic_variety_W") + ")");
					}

					if (!properties.getProperty("Avoid_tritone_jump_W").equals("0")) {
						vectorColumnNames.addElement("Ev. tritono ("
								+ properties.getProperty("Avoid_tritone_jump_W") + ")");
					}

					if (!properties.getProperty("Avoid_interval_1_W").equals("0")) {
						vectorColumnNames.addElement("Evitar "
								+ properties.getProperty("Avoid_interval_1_name")
								+ " ("
								+ properties.getProperty("Avoid_interval_1_W") + ")");
					}
					
					if (!properties.getProperty("Avoid_interval_2_W").equals("0")) {
						vectorColumnNames.addElement("Evitar "
								+ properties.getProperty("Avoid_interval_2_name")
								+ " ("
								+ properties.getProperty("Avoid_interval_2_W") + ")");
					}

					if (!properties.getProperty("Joint_degrees_movement_W").equals("0")) {
						vectorColumnNames.addElement("Pref. grados conjuntos ("
								+ properties.getProperty("Joint_degrees_movement_W")
								+ ")");
					}

					if (!properties.getProperty("Prohibit_the_repetition_of_sounds_W").equals("0")) {
						vectorColumnNames.addElement("Ev. notas repetidas consc. ("
								+ properties.getProperty("Prohibit_the_repetition_of_sounds_W")
								+ ")");
					}

					if (!properties.getProperty("Upward_movement_W").equals("0")) {
						vectorColumnNames
								.addElement("Pref. mov. ascendente ("
										+ properties.getProperty("Upward_movement_W")
										+ ")");
					}

					if (!properties.getProperty("Downward_movement_W").equals("0")) {
						vectorColumnNames
								.addElement("Pref. mov. descendente ("
										+ properties.getProperty("Downward_movement_W")
										+ ")");
					}

					if (!properties.getProperty("Tonic_start_W").equals("0")) {
						vectorColumnNames
								.addElement("Iniciar en tónica ("
										+ properties.getProperty("Tonic_start_W") + ")");
					}

					if (!properties.getProperty("Dominant_start_W").equals("0")) {
						vectorColumnNames
								.addElement("Iniciar en dominante ("
										+ properties.getProperty("Dominant_start_W")
										+ ")");
					}

					if (!properties.getProperty("Tonic_end_W").equals("0")) {
						vectorColumnNames.addElement("Fin. en tónica ("
								+ properties.getProperty("Tonic_end_W") + ")");
					}

					if (!properties.getProperty("Reiterate_of_the_tonic_note_W").equals("0")) {
						vectorColumnNames.addElement("Reiterar la tón. ("
								+ properties.getProperty("Reiterate_of_the_tonic_note_W")
								+ ")");
					}

					if (!properties.getProperty("Reiterate_of_the_dominant_note_W").equals("0")) {
						vectorColumnNames.addElement("Reiterar la dom. ("
								+ properties.getProperty("Reiterate_of_the_dominant_note_W")
								+ ")");
					}

					if (!properties.getProperty("Rest_abusse_W").equals("0")) {
						vectorColumnNames
								.addElement("Limitar silencios ("
										+ properties.getProperty("Rest_abusse_W") + ")");
					}

					if (!properties.getProperty("Just_time_init_W").equals("0")) {
						vectorColumnNames
								.addElement("Inicio a tiempo ("
										+ properties.getProperty("Just_time_init_W")
										+ ")");
					}

					if (!properties.getProperty("Initial_contretemp_W").equals("0")) {
						vectorColumnNames
								.addElement("Inicio a contratiempo ("
										+ properties.getProperty("Initial_contretemp_W")
										+ ")");
					}

					if (!properties.getProperty("Initial_pickup_beat_W").equals("0")) {
						vectorColumnNames
								.addElement("Inicio en anacruza ("
										+ properties.getProperty("Initial_pickup_beat_W")
										+ ")");
					}

					if (!properties.getProperty("Syncopes_W").equals("0")) {
						vectorColumnNames.addElement("Síncopas ("
								+ properties.getProperty("Syncopes_W") + ")");
					}

					if (!properties.getProperty("Alterations_count_W").equals("0")) {
						vectorColumnNames
								.addElement("Abundantes alteraciones ("
										+ properties.getProperty("Alterations_count_W")
										+ ")");
					}

					if (!properties.getProperty("Sharp_resolution_W").equals("0")) {
						vectorColumnNames
								.addElement("Res. de sostenidos ("
										+ properties.getProperty("Sharp_resolution_W")
										+ ")");
					}

					if (!properties.getProperty("Flat_resolution_W").equals("0")) {
						vectorColumnNames
								.addElement("Res. de bemoles ("
										+ properties.getProperty("Flat_resolution_W")
										+ ")");
					}

					if (!properties.getProperty("Resolve_active_degrees_1_W").equals("0")) {
						vectorColumnNames.addElement("Res. de grado "
								+ properties.getProperty("Active_degree_1")
								+ " ("
								+ properties.getProperty("Resolve_active_degrees_1_W")
								+ ")");
					}

					if (!properties.getProperty("Resolve_active_degrees_2_W").equals("0")) {
						vectorColumnNames.addElement("Res. de grado "
								+ properties.getProperty("Active_degree_2")
								+ " ("
								+ properties.getProperty("Resolve_active_degrees_2_W")
								+ ")");
					}

					if (!properties.getProperty("Resolve_active_degrees_3_W").equals("0")) {
						vectorColumnNames.addElement("Res. de grado "
								+ properties.getProperty("Active_degree_3")
								+ " ("
								+ properties.getProperty("Resolve_active_degrees_3_W")
								+ ")");
					}
					
					if (!properties.getProperty("Resolve_active_degrees_4_W").equals("0")) {
						vectorColumnNames.addElement("Res. de grado "
								+ properties.getProperty("Active_degree_4")
								+ " ("
								+ properties.getProperty("Resolve_active_degrees_4_W")
								+ ")");
					}
					

					if (!properties.getProperty("Only_n_climax_W").equals("0")) {
						vectorColumnNames.addElement("Alcanzar "
								+ properties.getProperty("Only_n_climax_N")
								+ " Clímax ("
								+ properties.getProperty("Only_n_climax_W") + ")");
					}

					if (!properties.getProperty("Only_n_cenit_W").equals("0")) {
						vectorColumnNames.addElement("Alcanzar "
								+ properties.getProperty("Only_n_cenit_N")
								+ " Cénit ("
								+ properties.getProperty("Only_n_cenit_W") + ")");
					}

					if (properties.getProperty("Make_Series","false").equals("true")) {
						vectorColumnNames.addElement("Generar series");
					}

					data = new String[Integer
							.parseInt(properties.getProperty("generations"))][vectorColumnNames
							.size()+1];
					
					for (int i = 0; i < Integer
							.parseInt(properties.getProperty("generations")); i++) {

						NumberFormat nf = NumberFormat.getInstance();
						nf.setMaximumFractionDigits(2);
						Melody m = new Melody(
								((MyStatisticsCollectionOperation) evolutionator
										.getCollector().getOperation())
										.getStats()
										.getBestIndividualOfGenerations()
										.get(i).getPhenotype()
										.toString());
						data[i][0] = i + "";
						data[i][1] = nf
								.format(((MyStatisticsCollectionOperation) evolutionator
										.getCollector().getOperation())
										.getStats()
										.getBestIndividualOfGenerations()
										.get(i).getFitness()
										.getDouble()).replace(',', '.');
						data[i][2] = nf.format(m.countMeasures())
								.replace(',', '.');
						int countColumns = 3;
						
						if (!properties.getProperty("Melody_expected_length_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalMelodyExpectedLength())
									.replace(',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Melodic_variety_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalMelodicVariety()).replace(
									',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Rhythmic_variety_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalRhythmicVariety()).replace(
									',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Intervalic_variety_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalIntervalicVariety()).replace(
									',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Avoid_tritone_jump_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalAvoidTritoneJump()).replace(
									',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Avoid_interval_1_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalAvoidInterval1()).replace(
									',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Avoid_interval_2_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalAvoidInterval2()).replace(
									',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Joint_degrees_movement_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalJointDegreesMovement())
									.replace(',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Prohibit_the_repetition_of_sounds_W").equals("0")) {
							data[i][countColumns] = nf
									.format(m
											.EvalProhibitTheRepetitionOfSounds())
									.replace(',', '.');
							countColumns++;
						}
					
						if (!properties.getProperty("Upward_movement_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalupwardMovement()).replace(
									',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Downward_movement_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalDownwardMovement()).replace(
									',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Tonic_start_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalTonicStart()).replace(',',
									'.');
							countColumns++;
						}

						if (!properties.getProperty("Dominant_start_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalDominantStart()).replace(',',
									'.');
							countColumns++;
						}

						if (!properties.getProperty("Tonic_end_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalTonicEnd()).replace(',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Reiterate_of_the_tonic_note_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalReiterateOfTheTonicNote())
									.replace(',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Reiterate_of_the_dominant_note_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalReiterateOfTheDominantNote())
									.replace(',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Rest_abusse_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalRestAbusse()).replace(',',
									'.');
							countColumns++;
						}

						if (!properties.getProperty("Just_time_init_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalJustTimeInit()).replace(',',
									'.');
							countColumns++;
						}

						if (!properties.getProperty("Initial_contretemp_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalInitialContretemp()).replace(
									',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Initial_pickup_beat_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalInitialPickupBeat()).replace(
									',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Syncopes_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalSyncopes()).replace(',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Alterations_count_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalAlterationsCount()).replace(
									',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Sharp_resolution_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalSharpResolution()).replace(
									',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Flat_resolution_W").equals("0")) {
							data[i][countColumns] = nf.format(
									m.EvalFlatResolution()).replace(
									',', '.');
							countColumns++;
						}

						if (!properties.getProperty("Resolve_active_degrees_1_W").equals("0")) {
							data[i][countColumns] = nf
									.format(m
											.EvalResolveActiveDegrees(
													Integer.parseInt(properties.getProperty("Active_degree_1")),
													Integer.parseInt(properties.getProperty("Active_degree_1_direction")),
													properties.getProperty("Active_degree_1_save")));
							countColumns++;
						}

						if (!properties.getProperty("Resolve_active_degrees_2_W").equals("0")) {
							data[i][countColumns] = nf
									.format(m
											.EvalResolveActiveDegrees(
													Integer.parseInt(properties.getProperty("Active_degree_2")),
													Integer.parseInt(properties.getProperty("Active_degree_2_direction")),
													properties.getProperty("Active_degree_2_save")));
							countColumns++;
						}

						if (!properties.getProperty("Resolve_active_degrees_3_W").equals("0")) {
							data[i][countColumns] = nf
									.format(m
											.EvalResolveActiveDegrees(
													Integer.parseInt(properties.getProperty("Active_degree_3")),
													Integer.parseInt(properties.getProperty("Active_degree_3_direction")),
													properties.getProperty("Active_degree_3_save")));
							countColumns++;
						}

						if (!properties.getProperty("Resolve_active_degrees_4_W").equals("0")) {
							data[i][countColumns] = nf
									.format(m
											.EvalResolveActiveDegrees(
													Integer.parseInt(properties.getProperty("Active_degree_4")),
													Integer.parseInt(properties.getProperty("Active_degree_4_direction")),
													properties.getProperty("Active_degree_4_save")));
							countColumns++;
						}

						if (!properties.getProperty("Only_n_climax_W").equals("0")) {
							data[i][countColumns] = nf
									.format(m
											.EvaOnlyNClimax(Integer.parseInt(properties.getProperty("Only_n_climax_N"))));
							countColumns++;
						}

						if (!properties.getProperty("Only_n_cenit_W").equals("0")) {
							data[i][countColumns] = nf
									.format(m
											.EvaOnlyNCenit(Integer.parseInt(properties.getProperty("Only_n_cenit_N"))));
							countColumns++;
						}

						if (properties.getProperty("Make_Series","false").equals("true")) {
							data[i][countColumns] = nf.format(
									m.EvalSeries()).replace(',', '.');
							countColumns++;
						}
						data[i][countColumns] = ((MyStatisticsCollectionOperation) evolutionator
								.getCollector().getOperation())
								.getStats()
								.getBestIndividualOfGenerations()
								.get(i).getPhenotype()
								.toString();

						countColumns++;
					}

					StatisticalGUI frameStats = new StatisticalGUI(
							data, vectorColumnNames, currentExecution);
					frameStats.setTitle("Estadísticas, ejecución "+currentExecution);
					frameStats.setVisible(true);
					currentExecution++;
				}

			} catch (Exception ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();

			}
		}// end for
		
	}
	
	public static int[] getMusicPosition() {
		
		if(displayedWindows%5==0 &&displayedWindows!=0)
		{
			musicPosition[1]=0+displayedWindows+16;	
		}
		else
		{
			musicPosition[1]=musicPosition[1]+140;	
		}
		displayedWindows++;
		return musicPosition;
	}

	private void defineConfigPath() {
		String path = null;
		JFileChooser chooser = new JFileChooser(properties.getProperty("Work_path"));
    	FileNameExtensionFilter filter = new FileNameExtensionFilter("Listas de propiedades","properties");
	    chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    		path = chooser.getSelectedFile().getPath();
	    		propertiesFile.setProperty("File_properties_name", path);
			
		} 
	    
		try {
			FileOutputStream fos = new FileOutputStream("propertiesFile.properties");
			propertiesFile.store(fos,null);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File fi= new File(propertiesFile.getProperty("File_properties_name"));
		if(!fi.exists())
		{
			
			storeProperties();
			
		}
		File f= new File(propertiesFile.getProperty("File_properties_name"));
		if(f.exists())
		{
			FileInputStream foo = null;
			try {
				foo = new FileInputStream(propertiesFile.getProperty("File_properties_name"));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				properties.load(foo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	public static void setMusicPosition() {
		musicPosition[1]=0;
	}
	public static String getMyProperty(String p)
	{
		if(!properties.getProperty(p).equals(""))
		{
			return properties.getProperty(p);
		}
		else
		{
			return "0";
		}

	}
	
	public static void setMyProperty(String p, String v)
	{
		properties.setProperty(p, v);
		storeProperties();
	}

	
	
	
}
