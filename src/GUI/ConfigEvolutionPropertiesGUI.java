package GUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import java.awt.ComponentOrientation;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import Utilities.Validator;

import javax.swing.JSeparator;
public class ConfigEvolutionPropertiesGUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblTamaoDeLa;
	private JLabel lblProbabilidadDeCruce;
	private JTextField crossoverProbabilityTxt;
	private JLabel lblProbabilidadDeMutacion;
	private JTextField mutationProbabilityTxt;
	private JLabel lblProbabilidadDeCrecimiento;
	private JTextField growProbabilityTxt;
	private JLabel lblPorcentajeEnLa;
	private JTextField tailPercentageTxt;
	private JLabel lblProfundidadMxima;
	private JTextField maxDepthTxt;
	private JLabel lblTamaoDelite;
	private JLabel lblTamaoDeTorneo;
	private JTextField eliteSizeTxt;
	private JTextField tournamentSizeTxt;
	private JLabel lblBrechaGeneracional;
	private JTextField generationGapTxt;
	private JLabel lblReusosDelCromosoma;
	private JTextField maxWrapsTxt;
	private JLabel lblCruceEnPunto;
	private JLabel lblEvaluarElites;
	private JCheckBox evaluateElitesCheckBox;
	private JTextField populationSizeTxt;
	private static JTextField generationsTxt;
	private JTextField initialChromosomeSizeTxt;
	private JCheckBox fixedPointCrossoverCheckBox;
	private static JButton btnOk;

	Color am=new Color(246, 245, 197);
	
	
	private Validator val  = new Validator();
	private JSeparator separator;
	private JTextField executionsTxt;
	private static int executions;
	

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
					ConfigEvolutionPropertiesGUI frame = new ConfigEvolutionPropertiesGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	private void ChangeIfValError( boolean b,JTextComponent c)
	
	{
		if(b)
		{			
			c.setBackground(Color.white);
		}
		else
		{
			c.setText("");
			c.setBackground(am);
			c.requestFocus();
			
		}
	}
	public ConfigEvolutionPropertiesGUI() {
		setTitle("Propiedades de evoluci\u00F3n");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(mainGui.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		setResizable(false);
		setBounds(100, 100, 566, 360);
		JPanel CEPGUIJPanel = new JPanel();
		CEPGUIJPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(CEPGUIJPanel);
		CEPGUIJPanel.setLayout(null);
		JLabel lblDimensinInicialDel = new JLabel("Dimensi\u00F3n inicial del cromosoma");
		lblDimensinInicialDel.setBounds(10, 15, 188, 16);
		CEPGUIJPanel.add(lblDimensinInicialDel);
		
		
		initialChromosomeSizeTxt = new JTextField();
		initialChromosomeSizeTxt.addFocusListener(new FocusAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void focusLost(FocusEvent arg0) {
				ChangeIfValError(val.ValidateInt(initialChromosomeSizeTxt.getText(), 1, 200),initialChromosomeSizeTxt);
			}
			@Override
			public void focusGained(FocusEvent e) {
				btnOk.setEnabled(true);
			}
		});
		initialChromosomeSizeTxt.addInputMethodListener(new InputMethodListener() {
			public void caretPositionChanged(InputMethodEvent arg0) {
			}
			public void inputMethodTextChanged(InputMethodEvent arg0) {
				
				
			}
		});
		
			initialChromosomeSizeTxt.setText(GUI.mainGui.getMyProperty("initial_chromosome_size"));
		
		initialChromosomeSizeTxt.setBounds(216, 15, 42, 20);
		CEPGUIJPanel.add(initialChromosomeSizeTxt);
		initialChromosomeSizeTxt.setColumns(10);
		
		JLabel lblNmeroDeGeneraciones = new JLabel("N\u00FAmero de generaciones");
		lblNmeroDeGeneraciones.setBounds(10, 54, 188, 16);
		CEPGUIJPanel.add(lblNmeroDeGeneraciones);
		
		generationsTxt = new JTextField();
		generationsTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ChangeIfValError(val.ValidateInt(generationsTxt.getText(), 1, 1000000),generationsTxt);
			}

		});
		
			generationsTxt.setText(GUI.mainGui.getMyProperty("generations"));
		
		generationsTxt.setBounds(216, 54, 42, 20);
		CEPGUIJPanel.add(generationsTxt);
		generationsTxt.setColumns(10);
		
		
		lblTamaoDeLa = new JLabel("Tama\u00F1o de la poblaci\u00F3n");
		lblTamaoDeLa.setBounds(10, 93, 188, 16);
		CEPGUIJPanel.add(lblTamaoDeLa);
		
		populationSizeTxt = new JTextField();
		populationSizeTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ChangeIfValError(val.ValidateInt(populationSizeTxt.getText(), 1, 1000000),populationSizeTxt);
			}
		});
		
			populationSizeTxt.setText(GUI.mainGui.getMyProperty("population_size"));
		
		populationSizeTxt.setBounds(216, 93, 42, 20);
		CEPGUIJPanel.add(populationSizeTxt);
		populationSizeTxt.setColumns(10);
		
		lblProbabilidadDeCruce = new JLabel("Probabilidad de cruce");
		lblProbabilidadDeCruce.setBounds(10, 132, 188, 16);
		CEPGUIJPanel.add(lblProbabilidadDeCruce);
		
		crossoverProbabilityTxt = new JTextField();
		crossoverProbabilityTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ChangeIfValError(val.ValidateDouble(crossoverProbabilityTxt.getText(), 0, 1),crossoverProbabilityTxt);
			}		
		});
		
		crossoverProbabilityTxt.setText(GUI.mainGui.getMyProperty("crossover_probability"));
		
		crossoverProbabilityTxt.setBounds(216, 132, 42, 20);
		CEPGUIJPanel.add(crossoverProbabilityTxt);
		crossoverProbabilityTxt.setColumns(10);
		
		lblProbabilidadDeMutacion = new JLabel("Probabilidad de mutaci\u00F3n");
		lblProbabilidadDeMutacion.setBounds(10, 171, 188, 16);
		CEPGUIJPanel.add(lblProbabilidadDeMutacion);
		
		mutationProbabilityTxt = new JTextField();
		mutationProbabilityTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ChangeIfValError(val.ValidateDouble(mutationProbabilityTxt.getText(), 0, 1),mutationProbabilityTxt);
			}
		});
		
			mutationProbabilityTxt.setText(GUI.mainGui.getMyProperty("mutation_probability"));
		
		mutationProbabilityTxt.setBounds(216, 171, 42, 20);
		CEPGUIJPanel.add(mutationProbabilityTxt);
		mutationProbabilityTxt.setColumns(10);
		
		lblProbabilidadDeCrecimiento = new JLabel("Probabilidad de crecimiento");
		lblProbabilidadDeCrecimiento.setBounds(10, 210, 188, 16);
		CEPGUIJPanel.add(lblProbabilidadDeCrecimiento);
		
		growProbabilityTxt = new JTextField();
		growProbabilityTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ChangeIfValError(val.ValidateDouble(growProbabilityTxt.getText(), 0, 1),growProbabilityTxt);	
							}
		});
		
			growProbabilityTxt.setText(GUI.mainGui.getMyProperty("grow_probability"));
		
		growProbabilityTxt.setBounds(216, 210, 42, 20);
		CEPGUIJPanel.add(growProbabilityTxt);
		growProbabilityTxt.setColumns(10);
		
		lblPorcentajeEnLa = new JLabel("Porcentaje en la cola");
		lblPorcentajeEnLa.setBounds(10, 249, 188, 16);
		CEPGUIJPanel.add(lblPorcentajeEnLa);
		
		tailPercentageTxt = new JTextField();
		tailPercentageTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ChangeIfValError(val.ValidateDouble(tailPercentageTxt.getText(), 0, 1),tailPercentageTxt);	
			}
		});
		
			tailPercentageTxt.setText(GUI.mainGui.getMyProperty("tail_percentage"));
		
		tailPercentageTxt.setBounds(216, 249, 42, 20);
		CEPGUIJPanel.add(tailPercentageTxt);
		tailPercentageTxt.setColumns(10);
		
		lblProfundidadMxima = new JLabel("Profundidad m\u00E1xima");
		lblProfundidadMxima.setBounds(286, 15, 188, 16);
		CEPGUIJPanel.add(lblProfundidadMxima);
		
		maxDepthTxt = new JTextField();
		maxDepthTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ChangeIfValError(val.ValidateInt(maxDepthTxt.getText(), 1, 100),maxDepthTxt);	
			}
		});
		
			maxDepthTxt.setText(GUI.mainGui.getMyProperty("max_depth"));

		maxDepthTxt.setBounds(493, 11, 42, 20);
		CEPGUIJPanel.add(maxDepthTxt);
		maxDepthTxt.setColumns(10);
		
		lblTamaoDelite = new JLabel("Tama\u00F1o de \u00E9lite");
		lblTamaoDelite.setBounds(286, 46, 188, 16);
		CEPGUIJPanel.add(lblTamaoDelite);
		
		lblTamaoDeTorneo = new JLabel("Tama\u00F1o de torneo");
		lblTamaoDeTorneo.setBounds(286, 77, 188, 16);
		CEPGUIJPanel.add(lblTamaoDeTorneo);
		
		eliteSizeTxt = new JTextField();
		eliteSizeTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ChangeIfValError(val.ValidateInt(eliteSizeTxt.getText(), 0, 1000),eliteSizeTxt);
			}
		});
		
			eliteSizeTxt.setText(GUI.mainGui.getMyProperty("elite_size"));
		
		eliteSizeTxt.setBounds(493, 42, 42, 20);
		CEPGUIJPanel.add(eliteSizeTxt);
		eliteSizeTxt.setColumns(10);
		
		tournamentSizeTxt = new JTextField();
		tournamentSizeTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ChangeIfValError(val.ValidateInt(tournamentSizeTxt.getText(), 0, 1000),tournamentSizeTxt);
			}
		});
		
			tournamentSizeTxt.setText(GUI.mainGui.getMyProperty("tournament_size"));
		
		tournamentSizeTxt.setBounds(493, 73, 42, 20);
		CEPGUIJPanel.add(tournamentSizeTxt);
		tournamentSizeTxt.setColumns(10);
		
		lblBrechaGeneracional = new JLabel("Brecha generacional");
		lblBrechaGeneracional.setBounds(286, 108, 160, 16);
		CEPGUIJPanel.add(lblBrechaGeneracional);
		
		generationGapTxt = new JTextField();
		generationGapTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ChangeIfValError(val.ValidateDouble(generationGapTxt.getText(), 0, 100),generationGapTxt);
			}
		});
		
			generationGapTxt.setText(GUI.mainGui.getMyProperty("generation_gap"));
		
		generationGapTxt.setBounds(493, 104, 42, 20);
		CEPGUIJPanel.add(generationGapTxt);
		generationGapTxt.setColumns(10);
		
		lblReusosDelCromosoma = new JLabel("Reusos del cromosoma");
		lblReusosDelCromosoma.setBounds(286, 139, 160, 16);
		CEPGUIJPanel.add(lblReusosDelCromosoma);
		
		maxWrapsTxt = new JTextField();
		maxWrapsTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ChangeIfValError(val.ValidateInt(maxWrapsTxt.getText(), 0, 100),maxWrapsTxt);
			}
		});
		
			maxWrapsTxt.setText(GUI.mainGui.getMyProperty("max_wraps"));
		
		maxWrapsTxt.setBounds(493, 135, 42, 20);
		CEPGUIJPanel.add(maxWrapsTxt);
		maxWrapsTxt.setColumns(10);
		
		fixedPointCrossoverCheckBox = new JCheckBox("");
		fixedPointCrossoverCheckBox.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);


			if(GUI.mainGui.getMyProperty("fixed_point_crossover").equals("true"))
			{
				fixedPointCrossoverCheckBox.setSelected(true);
			}
			else
			{
				fixedPointCrossoverCheckBox.setSelected(false);
			}


		
		fixedPointCrossoverCheckBox.setBounds(493, 170, 42, 24);
		CEPGUIJPanel.add(fixedPointCrossoverCheckBox);
		
		lblCruceEnPunto = new JLabel("Cruce en punto fijo");
		lblCruceEnPunto.setBounds(286, 170, 160, 16);
		CEPGUIJPanel.add(lblCruceEnPunto);
		
		lblEvaluarElites = new JLabel("Evaluar elites");
		lblEvaluarElites.setBounds(286, 201, 108, 16);
		CEPGUIJPanel.add(lblEvaluarElites);
		
		evaluateElitesCheckBox = new JCheckBox("");
		
			if(GUI.mainGui.getMyProperty("evaluate_elites").equals("true"))
			{
				evaluateElitesCheckBox.setSelected(true);
			}
			else
			{
				evaluateElitesCheckBox.setSelected(false);
			}
		
		evaluateElitesCheckBox.setBounds(514, 201, 21, 24);
		CEPGUIJPanel.add(evaluateElitesCheckBox);
		
		btnOk = new JButton("Aceptar");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				//Gestionar archivo de configuración
				GUI.mainGui.setMyProperty("mutation_probability", mutationProbabilityTxt.getText());
				GUI.mainGui.setMyProperty("tail_percentage", tailPercentageTxt.getText());
				GUI.mainGui.setMyProperty("generations",generationsTxt.getText() );
				GUI.mainGui.setMyProperty("generation_gap",generationGapTxt.getText() );
				if(evaluateElitesCheckBox.isSelected())
				   {
					GUI.mainGui.setMyProperty("evaluate_elites","true" );
				   }
				   else
				   {
					   GUI.mainGui.setMyProperty("evaluate_elites","false" );
				   }
				
				GUI.mainGui.setMyProperty("grow_probability",growProbabilityTxt.getText() );
				GUI.mainGui.setMyProperty("population_size", populationSizeTxt.getText());
				GUI.mainGui.setMyProperty("max_wraps",maxWrapsTxt.getText());
				GUI.mainGui.setMyProperty("crossover_probability", crossoverProbabilityTxt.getText());
				GUI.mainGui.setMyProperty("max_depth", maxDepthTxt.getText());
				GUI.mainGui.setMyProperty("elite_size",eliteSizeTxt.getText() );
				GUI.mainGui.setMyProperty("tournament_size",tournamentSizeTxt.getText() );
				if(fixedPointCrossoverCheckBox.isSelected())
				   {
					GUI.mainGui.setMyProperty("fixed_point_crossover","true" );
				   }
				   else
				   {
					   GUI.mainGui.setMyProperty("fixed_point_crossover","false" );
				   }
				if(evaluateElitesCheckBox.isSelected())
				   {
					GUI.mainGui.setMyProperty("evaluate_elites","true" );
				   }
				   else
				   {
					   GUI.mainGui.setMyProperty("evaluate_elites","false" );
				   }
				GUI.mainGui.setMyProperty("initial_chromosome_size",initialChromosomeSizeTxt.getText());
				setExecutions(Integer.parseInt(executionsTxt.getText()));
			  
			       
			       
			}
		});
		btnOk.setBounds(457, 284, 78, 24);
		CEPGUIJPanel.add(btnOk);
		
		separator = new JSeparator();
		separator.setBounds(286, 232, 249, 8);
		CEPGUIJPanel.add(separator);
		
		JLabel lblNmeroDeEjecuciones = new JLabel("N\u00FAmero de ejecuciones");
		lblNmeroDeEjecuciones.setBounds(286, 255, 160, 14);
		CEPGUIJPanel.add(lblNmeroDeEjecuciones);
		
		executionsTxt = new JTextField();
		executionsTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				ChangeIfValError(val.ValidateInt(executionsTxt.getText(), 1, 10),executionsTxt);
			}
		});
		executionsTxt.setText("1");
		executionsTxt.setBounds(493, 249, 42, 20);
		CEPGUIJPanel.add(executionsTxt);
		executionsTxt.setColumns(10);
	}

	protected void setExecutions(int ex) {
		this.executions=ex;
		
	}

	public static int getExecutions() {
		return executions;
	}
	

}
