package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;

import Utilities.Validator;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
public class ConfigMusicPropertiesGUI extends JDialog {

	/**
	 * Versión 1.2
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField melodyExpectedLengthTxt;
	private JCheckBox makeSeriesChkBox;
	
private Color am=new Color(246, 245, 197);
	
	
	private Validator val  = new Validator();
	@SuppressWarnings("rawtypes")
	private JComboBox avoidInterval1CBox;
	@SuppressWarnings("rawtypes")
	private JComboBox avoidInterval2CBox;
	private JTextField onlyNClimaxTxt;
	private JTextField onlyNCenitTxt;
	private JSlider flatResolutionPSld;
	private JComboBox activeDegree1CBox;
	private JComboBox activeDegree1DirectionCBox;
	private JCheckBox activeDegree1SaveChkBox;
	private JCheckBox activeDegree2SaveChkBox;
	private JComboBox activeDegree2CBox;
	private JComboBox activeDegree2DirectionCBox;
	private JComboBox activeDegree3CBox;
	private JComboBox activeDegree3DirectionCBox;
	private JCheckBox activeDegree3SaveChkBox;
	private JComboBox activeDegree4CBox;
	private JComboBox activeDegree4DirectionCBox;
	private JCheckBox activeDegree4SaveChkBox;
	private JComboBox flatDirectionCBox;
	private JComboBox sharpDirectionCBox;
	private JComboBox lowestNoteCBox;
	private JComboBox lowestNoteOctaveCBox;
	private JComboBox highestNoteCBox;
	private JComboBox highestNoteOctaveCBox;

	
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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			ConfigMusicPropertiesGUI dialog = new ConfigMusicPropertiesGUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ConfigMusicPropertiesGUI() {
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(mainGui.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
		setTitle("Configuraci\u00F3n de funci\u00F3n de ajuste");
		setModal(true);
		setBounds(100, 100, 810, 537);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblTimeSignature = new JLabel("Comp\u00E1s");
		lblTimeSignature.setToolTipText("Indicaci\u00F3n de comp\u00E1s para la transcripci\u00F3n");
		lblTimeSignature.setBounds(12, 35, 55, 16);
		contentPanel.add(lblTimeSignature);
		
		JLabel lbltonic = new JLabel("T\u00F3nica (finalis)");
		lbltonic.setToolTipText("T\u00F3nica empleada para los c\u00E1lculos");
		lbltonic.setBounds(12, 61, 127, 16);
		contentPanel.add(lbltonic);
		
		JLabel lblDominant = new JLabel("Dominante (repercusa)");
		lblDominant.setToolTipText("Dominante empleada para los c\u00E1lculos");
		lblDominant.setBounds(12, 88, 143, 16);
		contentPanel.add(lblDominant);
		
		JLabel LblMelodyExpectedLength = new JLabel("Longitud esperada");
		LblMelodyExpectedLength.setToolTipText("Longitud deseada medida en compases");
		LblMelodyExpectedLength.setBounds(14, 177, 114, 16);
		contentPanel.add(LblMelodyExpectedLength);
		
		melodyExpectedLengthTxt = new JTextField();
		melodyExpectedLengthTxt.setEnabled(false);
		melodyExpectedLengthTxt.setText(GUI.mainGui.getMyProperty("Melody_expected_length"));
		melodyExpectedLengthTxt.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				ChangeIfValError(val.ValidateInt(melodyExpectedLengthTxt.getText(), 1, 32),melodyExpectedLengthTxt);
			}
		});
		melodyExpectedLengthTxt.setBounds(128, 177, 51, 20);
		contentPanel.add(melodyExpectedLengthTxt);
		melodyExpectedLengthTxt.setColumns(10);
		
		JLabel lblLowestNote = new JLabel("Rango deseado");
		lblLowestNote.setToolTipText("Rango comprendido entre la nota m\u00E1s grave y la m\u00E1s aguda de la melod\u00EDa");
		lblLowestNote.setFont(new Font("Dialog", Font.PLAIN, 12));
		lblLowestNote.setBounds(12, 199, 108, 16);
		contentPanel.add(lblLowestNote);
		
		JLabel lblProhibitTheRepetitionOfSounds = new JLabel("Serie dodecaf\u00F3nica");
		lblProhibitTheRepetitionOfSounds.setToolTipText("Procurar serie que utillice los 12 sonidos sin repetirlos");
		lblProhibitTheRepetitionOfSounds.setBounds(12, 118, 114, 16);
		contentPanel.add(lblProhibitTheRepetitionOfSounds);
		
			makeSeriesChkBox = new JCheckBox("");
			if(GUI.mainGui.getMyProperty("Make_series").equals("true"))
			{
				makeSeriesChkBox.setSelected(true);
			}
			else
			{
				makeSeriesChkBox.setSelected(false);
			}
		
		makeSeriesChkBox.setBounds(231, 118, 25, 16);
		contentPanel.add(makeSeriesChkBox);
		
		
		JSeparator separator = new JSeparator();
		separator.setBounds(313, 222, -108, -7);
		contentPanel.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(12, 140, 244, 12);
		contentPanel.add(separator_1);
		
		JLabel lblRestricciones = new JLabel("RESTRICCIONES");
		lblRestricciones.setFont(new Font("Dialog", Font.BOLD, 12));
		lblRestricciones.setBounds(12, 149, 114, 16);
		contentPanel.add(lblRestricciones);
		
		JLabel lblAvoidTritoneJump = new JLabel("Evitar intervalo de tritono");
		lblAvoidTritoneJump.setBounds(12, 245, 143, 16);
		contentPanel.add(lblAvoidTritoneJump);
		
		JLabel lblAvoidInterval1 = new JLabel("Evitar intervalo");
		lblAvoidInterval1.setBounds(12, 287, 84, 16);
		contentPanel.add(lblAvoidInterval1);
		
		JLabel lblEvitarIntervalo_1 = new JLabel("Evitar intervalo");
		lblEvitarIntervalo_1.setBounds(12, 329, 82, 16);
		contentPanel.add(lblEvitarIntervalo_1);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(283, 119, 2, 289);
		contentPanel.add(separator_3);
		
		JLabel lblAspectosGenerales = new JLabel("ASPECTOS GENERALES");
		lblAspectosGenerales.setFont(new Font("Dialog", Font.BOLD, 12));
		lblAspectosGenerales.setBounds(12, 3, 171, 16);
		contentPanel.add(lblAspectosGenerales);
		
		JLabel lblAspectosAPrivilegiar = new JLabel("ASPECTOS A PRIVILEGIAR");
		lblAspectosAPrivilegiar.setFont(new Font("Dialog", Font.BOLD, 12));
		lblAspectosAPrivilegiar.setBounds(272, 3, 171, 16);
		contentPanel.add(lblAspectosAPrivilegiar);
		
		JLabel lblMelodicVariety = new JLabel("Variedad mel\u00F3dica");
		lblMelodicVariety.setToolTipText("Privilegia el uso de la mayor cantidad de sonidos diferentes");
		lblMelodicVariety.setBounds(272, 35, 133, 16);
		contentPanel.add(lblMelodicVariety);
		
		JLabel lblRhythmicVariety = new JLabel("Variedad r\u00EDtmica");
		lblRhythmicVariety.setToolTipText("Privilegia el uso de la mayor cantidad de valores r\u00EDtmicos");
		lblRhythmicVariety.setBounds(272, 70, 133, 16);
		contentPanel.add(lblRhythmicVariety);
		
		JLabel lblInitialPickupBeat = new JLabel("Iniciar en anacruza");
		lblInitialPickupBeat.setBounds(272, 175, 133, 16);
		contentPanel.add(lblInitialPickupBeat);
		
		JLabel lblVariedadIntervlica = new JLabel("Variedad interv\u00E1lica");
		lblVariedadIntervlica.setToolTipText("Privilegia el uso de la mayor cantidad de intervalos");
		lblVariedadIntervlica.setBounds(272, 105, 133, 16);
		contentPanel.add(lblVariedadIntervlica);
		
		JLabel lblInitialContretemp = new JLabel("Iniciar a contratiempo");
		lblInitialContretemp.setBounds(272, 210, 133, 16);
		contentPanel.add(lblInitialContretemp);
		
		JLabel lblRestAbusse = new JLabel("Evitar silencios excesivos");
		lblRestAbusse.setBounds(12, 371, 171, 16);
		contentPanel.add(lblRestAbusse);
		
		JLabel lblJointDegreesMovement = new JLabel("Evitar saltos mel\u00F3dicos");
		lblJointDegreesMovement.setToolTipText("Procurar grados conjuntos, un valor negativo hace lo contrario");
		lblJointDegreesMovement.setBounds(12, 413, 133, 16);
		contentPanel.add(lblJointDegreesMovement);
		
		JLabel lblTonicStart = new JLabel("Iniciar en t\u00F3nica");
		lblTonicStart.setBounds(272, 280, 133, 16);
		contentPanel.add(lblTonicStart);
		
		JLabel lblReiterateOfTheTonicNote = new JLabel("Reiterar t\u00F3nica");
		lblReiterateOfTheTonicNote.setToolTipText("Privilegia la utilizaci\u00F3n reiterada de la t\u00F3nica");
		lblReiterateOfTheTonicNote.setBounds(272, 350, 133, 16);
		contentPanel.add(lblReiterateOfTheTonicNote);
		
		JLabel lblDominantStart = new JLabel("Iniciar en dominante");
		lblDominantStart.setBounds(272, 385, 133, 16);
		contentPanel.add(lblDominantStart);
		
		JLabel lblReiterateOfTheDominantNote = new JLabel("Reiterar dominante");
		lblReiterateOfTheDominantNote.setBounds(272, 420, 133, 16);
		contentPanel.add(lblReiterateOfTheDominantNote);
		
		JLabel lblPeso_1 = new JLabel("PESO");
		lblPeso_1.setFont(new Font("Dialog", Font.BOLD, 12));
		lblPeso_1.setBounds(436, 3, 37, 16);
		contentPanel.add(lblPeso_1);
		
		JLabel lbLupwardMovement = new JLabel("Movimiento ascendente");
		lbLupwardMovement.setToolTipText("Privilegia el uso de dise\u00F1os mel\u00F3dicos ascendentes");
		lbLupwardMovement.setBounds(272, 455, 140, 16);
		contentPanel.add(lbLupwardMovement);
		
		JLabel lblDownwardMovement = new JLabel("Movimiento descendente");
		lblDownwardMovement.setToolTipText("Privilegia el uso de dise\u00F1os mel\u00F3dicos descendentes");
		lblDownwardMovement.setBounds(509, 38, 140, 16);
		contentPanel.add(lblDownwardMovement);
		
		JLabel lblResolveActiveDegrees = new JLabel("RESOLUCI\u00D3N DE GRADOS ACTIVOS");
		lblResolveActiveDegrees.setToolTipText("Permite seleccionar los grados que se considerar\u00E1n activos y su tratamiento");
		lblResolveActiveDegrees.setFont(new Font("Dialog", Font.BOLD, 12));
		lblResolveActiveDegrees.setBounds(509, 218, 237, 16);
		contentPanel.add(lblResolveActiveDegrees);
		
		JLabel lblAlterationsCount = new JLabel("Producir abundantes alteraciones");
		lblAlterationsCount.setToolTipText("Privilegia la aparici\u00F3n de abundantes # y b");
		lblAlterationsCount.setBounds(509, 128, 191, 16);
		contentPanel.add(lblAlterationsCount);
		
		final JLabel lblSharpResolution = new JLabel("Resolver #");
		lblSharpResolution.setToolTipText("Permite privilegiar la resoluci\u00F3n de # en el sentido deseado");
		lblSharpResolution.setBounds(509, 158, 191, 16);
		contentPanel.add(lblSharpResolution);
		
		JLabel lblOtrasMtricas = new JLabel("OTRAS M\u00C9TRICAS");
		lblOtrasMtricas.setVisible(false);
		lblOtrasMtricas.setFont(new Font("Dialog", Font.BOLD, 12));
		lblOtrasMtricas.setBounds(509, 375, 116, 16);
		contentPanel.add(lblOtrasMtricas);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setVisible(false);
		separator_2.setBounds(509, 395, 279, 3);
		contentPanel.add(separator_2);
		
		JLabel lblZipfLawValues = new JLabel("Valor Zipf");
		lblZipfLawValues.setVisible(false);
		lblZipfLawValues.setBounds(509, 403, 116, 16);
		contentPanel.add(lblZipfLawValues);
		
		JLabel lblComplexityOfMelodyVsBuildingProcessComplexity = new JLabel("Complejidad melod\u00EDa vs proceso");
		lblComplexityOfMelodyVsBuildingProcessComplexity.setVisible(false);
		lblComplexityOfMelodyVsBuildingProcessComplexity.setBounds(509, 433, 191, 16);
		contentPanel.add(lblComplexityOfMelodyVsBuildingProcessComplexity);
		
		JLabel lblPeso_2 = new JLabel("PESO");
		lblPeso_2.setFont(new Font("Dialog", Font.BOLD, 12));
		lblPeso_2.setBounds(731, 10, 37, 16);
		contentPanel.add(lblPeso_2);
		
		JLabel lblJustTimeInit = new JLabel("Iniciar a tiempo");
		lblJustTimeInit.setBounds(272, 140, 114, 16);
		contentPanel.add(lblJustTimeInit);
		
		JLabel lblTonicEnd = new JLabel("Terminar en t\u00F3nica");
		lblTonicEnd.setBounds(272, 315, 133, 16);
		contentPanel.add(lblTonicEnd);
		
		JLabel lblOnlyNclimax1 = new JLabel("Alcanzar");
		lblOnlyNclimax1.setBounds(509, 68, 48, 16);
		contentPanel.add(lblOnlyNclimax1);
		
		JLabel lblOnlyNCenit1 = new JLabel("Alcanzar");
		lblOnlyNCenit1.setBounds(509, 94, 48, 16);
		contentPanel.add(lblOnlyNCenit1);
		
		JLabel lblSncopas = new JLabel("S\u00EDncopas");
		lblSncopas.setToolTipText("Privilegia el desplazamiento de acentos");
		lblSncopas.setBounds(272, 245, 55, 16);
		contentPanel.add(lblSncopas);
		
		JLabel lblEvitarRepetirNotas = new JLabel("Evitar notas repetidas consec.");
		lblEvitarRepetirNotas.setToolTipText("Evitar repicar notas");
		lblEvitarRepetirNotas.setBounds(12, 455, 171, 16);
		contentPanel.add(lblEvitarRepetirNotas);
		
		JLabel lblFlatResolution = new JLabel("Resolver b");
		lblFlatResolution.setToolTipText("Permite privilegiar la resoluci\u00F3n de b en el sentido deseado");
		lblFlatResolution.setBounds(509, 188, 67, 16);
		contentPanel.add(lblFlatResolution);
		
		JLabel lblGrado = new JLabel("GRADO");
		lblGrado.setBounds(509, 238, 55, 16);
		contentPanel.add(lblGrado);
		
		JLabel lblDireccin = new JLabel("DIRECCI\u00D3N");
		lblDireccin.setBounds(570, 238, 79, 16);
		contentPanel.add(lblDireccin);
		
		JLabel lblSalvar = new JLabel("SALVAR");
		lblSalvar.setBounds(653, 238, 55, 16);
		contentPanel.add(lblSalvar);
		
		JLabel lblPeso_3 = new JLabel("PESO");
		lblPeso_3.setFont(new Font("Dialog", Font.BOLD, 12));
		lblPeso_3.setBounds(731, 232, 37, 16);
		contentPanel.add(lblPeso_3);
		
		activeDegree1SaveChkBox = new JCheckBox("");
		activeDegree1SaveChkBox.setToolTipText("Privilegia el movimiento doble en el sentido de la resoluci\u00F3n, seguido de un paso en movimiento contrario");
		activeDegree1SaveChkBox.setEnabled(false);
		activeDegree1SaveChkBox.setBounds(669, 256, 31, 24);
		contentPanel.add(activeDegree1SaveChkBox);
		
		activeDegree2SaveChkBox = new JCheckBox("");
		activeDegree2SaveChkBox.setEnabled(false);
		activeDegree2SaveChkBox.setBounds(669, 286, 28, 24);
		contentPanel.add(activeDegree2SaveChkBox);
		
		activeDegree3SaveChkBox = new JCheckBox("");
		activeDegree3SaveChkBox.setEnabled(false);
		activeDegree3SaveChkBox.setBounds(669, 316, 25, 24);
		contentPanel.add(activeDegree3SaveChkBox);
		
		activeDegree4SaveChkBox = new JCheckBox("");
		activeDegree4SaveChkBox.setEnabled(false);
		activeDegree4SaveChkBox.setBounds(669, 346, 25, 24);
		contentPanel.add(activeDegree4SaveChkBox);
		
		final JSlider melodicVarietySld = new JSlider();
		melodicVarietySld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				melodicVarietySld.setToolTipText(melodicVarietySld.getValue()+"");
			}
		});
		melodicVarietySld.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				melodicVarietySld.setToolTipText(melodicVarietySld.getValue()+"");
			}
		});
		
			melodicVarietySld.setToolTipText(GUI.mainGui.getMyProperty("Melodic_variety_W"));
			melodicVarietySld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Melodic_variety_W")));
			
		melodicVarietySld.setPaintLabels(true);
		melodicVarietySld.setPaintTicks(true);
		melodicVarietySld.setSnapToTicks(true);
		melodicVarietySld.setMinorTickSpacing(1);
		melodicVarietySld.setMinimum(-10);
		melodicVarietySld.setMaximum(10);
		melodicVarietySld.setBounds(414, 27, 77, 39);
		contentPanel.add(melodicVarietySld);
		
		final JSlider rhythmicVarietySld = new JSlider();
		rhythmicVarietySld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				rhythmicVarietySld.setToolTipText(rhythmicVarietySld.getValue()+"");
			}
		});
		
			rhythmicVarietySld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Rhythmic_variety_W")));
			rhythmicVarietySld.setToolTipText(GUI.mainGui.getMyProperty("Rhythmic_variety_W"));
		
		rhythmicVarietySld.setSnapToTicks(true);
		rhythmicVarietySld.setPaintTicks(true);
		rhythmicVarietySld.setPaintLabels(true);
		rhythmicVarietySld.setMinorTickSpacing(1);
		rhythmicVarietySld.setMinimum(-10);
		rhythmicVarietySld.setMaximum(10);
		rhythmicVarietySld.setBounds(414, 62, 77, 39);
		contentPanel.add(rhythmicVarietySld);
		
		final JSlider intervalicVarietySld = new JSlider();
		intervalicVarietySld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				intervalicVarietySld.setToolTipText(intervalicVarietySld.getValue()+"");
			}
		});
		
			intervalicVarietySld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Intervalic_variety_W")));
			intervalicVarietySld.setToolTipText(GUI.mainGui.getMyProperty("Intervalic_variety_W"));
		
		intervalicVarietySld.setSnapToTicks(true);
		intervalicVarietySld.setPaintTicks(true);
		intervalicVarietySld.setPaintLabels(true);
		intervalicVarietySld.setMinorTickSpacing(1);
		intervalicVarietySld.setMinimum(-10);
		intervalicVarietySld.setMaximum(10);
		intervalicVarietySld.setBounds(414, 97, 77, 39);
		contentPanel.add(intervalicVarietySld);
		
		final JSlider justTimeInitSld = new JSlider();
		justTimeInitSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				justTimeInitSld.setToolTipText(justTimeInitSld.getValue()+"");
			}
		});
		
			justTimeInitSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Just_time_init_W")));
			justTimeInitSld.setToolTipText(GUI.mainGui.getMyProperty("Just_time_init_W"));
			
		justTimeInitSld.setSnapToTicks(true);
		justTimeInitSld.setPaintTicks(true);
		justTimeInitSld.setPaintLabels(true);
		justTimeInitSld.setMinorTickSpacing(1);
		justTimeInitSld.setMinimum(-10);
		justTimeInitSld.setMaximum(10);
		justTimeInitSld.setBounds(414, 132, 77, 39);
		contentPanel.add(justTimeInitSld);
		
		final JSlider initialPickupBeatSld = new JSlider();
		initialPickupBeatSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				initialPickupBeatSld.setToolTipText(initialPickupBeatSld.getValue()+"");
			}
		});
		
			initialPickupBeatSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Initial_pickup_beat_W")));
			initialPickupBeatSld.setToolTipText(GUI.mainGui.getMyProperty("Initial_pickup_beat_W"));
		
		initialPickupBeatSld.setSnapToTicks(true);
		initialPickupBeatSld.setPaintTicks(true);
		initialPickupBeatSld.setPaintLabels(true);
		initialPickupBeatSld.setMinorTickSpacing(1);
		initialPickupBeatSld.setMinimum(-10);
		initialPickupBeatSld.setMaximum(10);
		initialPickupBeatSld.setBounds(414, 167, 77, 39);
		contentPanel.add(initialPickupBeatSld);
		
		final JSlider initialContretempSld = new JSlider();
		initialContretempSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				initialContretempSld.setToolTipText(initialContretempSld.getValue()+"");
			}
		});
		
			initialContretempSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Initial_contretemp_W")));
			initialContretempSld.setToolTipText(GUI.mainGui.getMyProperty("Initial_contretemp_W"));
		
		initialContretempSld.setSnapToTicks(true);
		initialContretempSld.setPaintTicks(true);
		initialContretempSld.setPaintLabels(true);
		initialContretempSld.setMinorTickSpacing(1);
		initialContretempSld.setMinimum(-10);
		initialContretempSld.setMaximum(10);
		initialContretempSld.setBounds(414, 202, 77, 39);
		contentPanel.add(initialContretempSld);
		
		final JSlider syncopesPSld = new JSlider();
		syncopesPSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				syncopesPSld.setToolTipText(syncopesPSld.getValue()+"");
			}
		});
		
			syncopesPSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Syncopes_W")));
			syncopesPSld.setToolTipText(GUI.mainGui.getMyProperty("Syncopes_W"));
		
		syncopesPSld.setSnapToTicks(true);
		syncopesPSld.setPaintTicks(true);
		syncopesPSld.setPaintLabels(true);
		syncopesPSld.setMinorTickSpacing(1);
		syncopesPSld.setMinimum(-10);
		syncopesPSld.setMaximum(10);
		syncopesPSld.setBounds(414, 237, 77, 39);
		contentPanel.add(syncopesPSld);
		
		final JSlider tonicStartSld = new JSlider();
		tonicStartSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				tonicStartSld.setToolTipText(tonicStartSld.getValue()+"");
			}
		});
		
			tonicStartSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Tonic_start_W")));
			tonicStartSld.setToolTipText(GUI.mainGui.getMyProperty("Tonic_start_W"));
		
		tonicStartSld.setSnapToTicks(true);
		tonicStartSld.setPaintTicks(true);
		tonicStartSld.setPaintLabels(true);
		tonicStartSld.setMinorTickSpacing(1);
		tonicStartSld.setMinimum(-10);
		tonicStartSld.setMaximum(10);
		tonicStartSld.setBounds(414, 272, 77, 39);
		contentPanel.add(tonicStartSld);
		
		final JSlider tonicEndSld = new JSlider();
		tonicEndSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				tonicEndSld.setToolTipText(tonicEndSld.getValue()+"");
			}
		});
		
			tonicEndSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Tonic_end_W")));
			tonicEndSld.setToolTipText(GUI.mainGui.getMyProperty("Tonic_end_W"));
		
		tonicEndSld.setSnapToTicks(true);
		tonicEndSld.setPaintTicks(true);
		tonicEndSld.setPaintLabels(true);
		tonicEndSld.setMinorTickSpacing(1);
		tonicEndSld.setMinimum(-10);
		tonicEndSld.setMaximum(10);
		tonicEndSld.setBounds(414, 307, 77, 39);
		contentPanel.add(tonicEndSld);
		
		final JSlider reiterateOfTheTonicNoteSld = new JSlider();
		reiterateOfTheTonicNoteSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				reiterateOfTheTonicNoteSld.setToolTipText(reiterateOfTheTonicNoteSld.getValue()+"");
			}
		});
		
			reiterateOfTheTonicNoteSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Reiterate_of_the_tonic_note_W")));
			reiterateOfTheTonicNoteSld.setToolTipText(GUI.mainGui.getMyProperty("Reiterate_of_the_tonic_note_W"));
		
		reiterateOfTheTonicNoteSld.setSnapToTicks(true);
		reiterateOfTheTonicNoteSld.setPaintTicks(true);
		reiterateOfTheTonicNoteSld.setPaintLabels(true);
		reiterateOfTheTonicNoteSld.setMinorTickSpacing(1);
		reiterateOfTheTonicNoteSld.setMinimum(-10);
		reiterateOfTheTonicNoteSld.setMaximum(10);
		reiterateOfTheTonicNoteSld.setBounds(414, 342, 77, 39);
		contentPanel.add(reiterateOfTheTonicNoteSld);
		
		final JSlider dominantStartSld = new JSlider();
		dominantStartSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				dominantStartSld.setToolTipText(dominantStartSld.getValue()+"");
			}
		});
		
			dominantStartSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Dominant_start_W")));
			dominantStartSld.setToolTipText(GUI.mainGui.getMyProperty("Dominant_start_W"));
		
		dominantStartSld.setSnapToTicks(true);
		dominantStartSld.setPaintTicks(true);
		dominantStartSld.setPaintLabels(true);
		dominantStartSld.setMinorTickSpacing(1);
		dominantStartSld.setMinimum(-10);
		dominantStartSld.setMaximum(10);
		dominantStartSld.setBounds(414, 377, 77, 39);
		contentPanel.add(dominantStartSld);
		
		final JSlider reiterateOfTheDominantNoteSld = new JSlider();
		reiterateOfTheDominantNoteSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				reiterateOfTheDominantNoteSld.setToolTipText(reiterateOfTheDominantNoteSld.getValue()+"");
			}
		});
		
			reiterateOfTheDominantNoteSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Reiterate_of_the_dominant_note_W")));
			reiterateOfTheDominantNoteSld.setToolTipText(GUI.mainGui.getMyProperty("Reiterate_of_the_dominant_note_W"));
		
		reiterateOfTheDominantNoteSld.setSnapToTicks(true);
		reiterateOfTheDominantNoteSld.setPaintTicks(true);
		reiterateOfTheDominantNoteSld.setPaintLabels(true);
		reiterateOfTheDominantNoteSld.setMinorTickSpacing(1);
		reiterateOfTheDominantNoteSld.setMinimum(-10);
		reiterateOfTheDominantNoteSld.setMaximum(10);
		reiterateOfTheDominantNoteSld.setBounds(414, 412, 77, 39);
		contentPanel.add(reiterateOfTheDominantNoteSld);
		
		final JSlider upwardMovementSld = new JSlider();
		upwardMovementSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				upwardMovementSld.setToolTipText(upwardMovementSld.getValue()+"");
			}
		});
		
			upwardMovementSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Upward_movement_W")));
			upwardMovementSld.setToolTipText(GUI.mainGui.getMyProperty("Upward_movement_W"));
		
		upwardMovementSld.setSnapToTicks(true);
		upwardMovementSld.setPaintTicks(true);
		upwardMovementSld.setPaintLabels(true);
		upwardMovementSld.setMinorTickSpacing(1);
		upwardMovementSld.setMinimum(-10);
		upwardMovementSld.setMaximum(10);
		upwardMovementSld.setBounds(414, 447, 77, 39);
		contentPanel.add(upwardMovementSld);
		
		final JSlider avoidTritoneJumpSld = new JSlider();
		avoidTritoneJumpSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				avoidTritoneJumpSld.setToolTipText(avoidTritoneJumpSld.getValue()+"");
			}
		});
	

		
			avoidTritoneJumpSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Avoid_tritone_jump_W")));
			avoidTritoneJumpSld.setToolTipText(GUI.mainGui.getMyProperty("Avoid_tritone_jump_W"));
		
		avoidTritoneJumpSld.setSnapToTicks(true);
		avoidTritoneJumpSld.setPaintTicks(true);
		avoidTritoneJumpSld.setPaintLabels(true);
		avoidTritoneJumpSld.setMinorTickSpacing(1);
		avoidTritoneJumpSld.setMinimum(-10);
		avoidTritoneJumpSld.setMaximum(10);
		avoidTritoneJumpSld.setBounds(177, 238, 77, 39);
		contentPanel.add(avoidTritoneJumpSld);
		
		final JSlider avoidInterval1PSld = new JSlider();
		avoidInterval1PSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				avoidInterval1PSld.setToolTipText(avoidInterval1PSld.getValue()+"");
				if(avoidInterval1PSld.getValue()==0)
				{
					avoidInterval1CBox.setEnabled(false);
				}
				else
				{
					avoidInterval1CBox.setEnabled(true);
				}
					
				
			}
		});
		
		
			avoidInterval1PSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Avoid_interval_1_W")));
			avoidInterval1PSld.setToolTipText(GUI.mainGui.getMyProperty("Avoid_interval_1_W"));
		
		avoidInterval1PSld.setSnapToTicks(true);
		avoidInterval1PSld.setPaintTicks(true);
		avoidInterval1PSld.setPaintLabels(true);
		avoidInterval1PSld.setMinorTickSpacing(1);
		avoidInterval1PSld.setMinimum(-10);
		avoidInterval1PSld.setMaximum(10);
		avoidInterval1PSld.setBounds(177, 280, 77, 39);
		contentPanel.add(avoidInterval1PSld);
		
		final JSlider avoidInterval2PSld = new JSlider();
		avoidInterval2PSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				avoidInterval2PSld.setToolTipText(avoidInterval2PSld.getValue()+"");
				if(avoidInterval2PSld.getValue()==0)
				{
					avoidInterval2CBox.setEnabled(false);
				}
				else
				{
					avoidInterval2CBox.setEnabled(true);	
				}
				
			}
		});
		
		
			avoidInterval2PSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Avoid_interval_2_W")));
			avoidInterval2PSld.setToolTipText(GUI.mainGui.getMyProperty("Avoid_interval_2_W"));
		
		avoidInterval2PSld.setSnapToTicks(true);
		avoidInterval2PSld.setPaintTicks(true);
		avoidInterval2PSld.setPaintLabels(true);
		avoidInterval2PSld.setMinorTickSpacing(1);
		avoidInterval2PSld.setMinimum(-10);
		avoidInterval2PSld.setMaximum(10);
		avoidInterval2PSld.setBounds(177, 322, 77, 39);
		contentPanel.add(avoidInterval2PSld);
		
		final JSlider restAbusseSld = new JSlider();
		restAbusseSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				restAbusseSld.setToolTipText(restAbusseSld.getValue()+"");
			}
		});
		
			restAbusseSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Rest_abusse_W")));
			restAbusseSld.setToolTipText(GUI.mainGui.getMyProperty("Rest_abusse_W"));
		
		restAbusseSld.setSnapToTicks(true);
		restAbusseSld.setPaintTicks(true);
		restAbusseSld.setPaintLabels(true);
		restAbusseSld.setMinorTickSpacing(1);
		restAbusseSld.setMinimum(-10);
		restAbusseSld.setMaximum(10);
		restAbusseSld.setBounds(177, 364, 77, 39);
		contentPanel.add(restAbusseSld);
		
		final JSlider jointDegreesMovementSld = new JSlider();
		jointDegreesMovementSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				jointDegreesMovementSld.setToolTipText(jointDegreesMovementSld.getValue()+"");
			}
		});
		
			jointDegreesMovementSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Joint_degrees_movement_W")));
			jointDegreesMovementSld.setToolTipText(GUI.mainGui.getMyProperty("Joint_degrees_movement_W"));
		
		jointDegreesMovementSld.setSnapToTicks(true);
		jointDegreesMovementSld.setPaintTicks(true);
		jointDegreesMovementSld.setPaintLabels(true);
		jointDegreesMovementSld.setMinorTickSpacing(1);
		jointDegreesMovementSld.setMinimum(-10);
		jointDegreesMovementSld.setMaximum(10);
		jointDegreesMovementSld.setBounds(177, 406, 77, 39);
		contentPanel.add(jointDegreesMovementSld);
		
		final JSlider prohibitTheRepetitionOfSoundsSld = new JSlider();
		prohibitTheRepetitionOfSoundsSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				prohibitTheRepetitionOfSoundsSld.setToolTipText(prohibitTheRepetitionOfSoundsSld.getValue()+"");
			}
		});
		
			prohibitTheRepetitionOfSoundsSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Prohibit_the_repetition_of_sounds_W")));
			prohibitTheRepetitionOfSoundsSld.setToolTipText(GUI.mainGui.getMyProperty("Prohibit_the_repetition_of_sounds_W"));
		
		prohibitTheRepetitionOfSoundsSld.setSnapToTicks(true);
		prohibitTheRepetitionOfSoundsSld.setPaintTicks(true);
		prohibitTheRepetitionOfSoundsSld.setPaintLabels(true);
		prohibitTheRepetitionOfSoundsSld.setMinorTickSpacing(1);
		prohibitTheRepetitionOfSoundsSld.setMinimum(-10);
		prohibitTheRepetitionOfSoundsSld.setMaximum(10);
		prohibitTheRepetitionOfSoundsSld.setBounds(177, 448, 77, 39);
		contentPanel.add(prohibitTheRepetitionOfSoundsSld);
		
		final JSlider downwardMovementSld = new JSlider();
		downwardMovementSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				downwardMovementSld.setToolTipText(downwardMovementSld.getValue()+"");
			}
		});
		
			downwardMovementSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Downward_movement_W")));
			downwardMovementSld.setToolTipText(GUI.mainGui.getMyProperty("Downward_movement_W"));
		
		downwardMovementSld.setSnapToTicks(true);
		downwardMovementSld.setPaintTicks(true);
		downwardMovementSld.setPaintLabels(true);
		downwardMovementSld.setMinorTickSpacing(1);
		downwardMovementSld.setMinimum(-10);
		downwardMovementSld.setMaximum(10);
		downwardMovementSld.setBounds(718, 35, 77, 31);
		contentPanel.add(downwardMovementSld);
		
		final JSlider onlyNClimaxPSld = new JSlider();
		onlyNClimaxPSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				onlyNClimaxPSld.setToolTipText(onlyNClimaxPSld.getValue()+"");
				if(onlyNClimaxPSld.getValue()==0)
				{
					onlyNClimaxTxt.setEnabled(false);
				}
				else
				{
					onlyNClimaxTxt.setEnabled(true);
				}	
			}
		});
		
			onlyNClimaxPSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Only_n_climax_W")));
			onlyNClimaxPSld.setToolTipText(GUI.mainGui.getMyProperty("Only_n_climax_W"));
		
		onlyNClimaxPSld.setSnapToTicks(true);
		onlyNClimaxPSld.setPaintTicks(true);
		onlyNClimaxPSld.setPaintLabels(true);
		onlyNClimaxPSld.setMinorTickSpacing(1);
		onlyNClimaxPSld.setMinimum(-10);
		onlyNClimaxPSld.setMaximum(10);
		onlyNClimaxPSld.setBounds(718, 65, 77, 31);
		contentPanel.add(onlyNClimaxPSld);
		
		final JSlider onlyNCenitPSld = new JSlider();
		onlyNCenitPSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				onlyNCenitPSld.setToolTipText(onlyNCenitPSld.getValue()+"");
				if(onlyNCenitPSld.getValue()==0)
				{
					onlyNCenitTxt.setEnabled(false);
				}
				else
				{
					onlyNCenitTxt.setEnabled(true);
				}
				
			}
		});
		
			onlyNCenitPSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Only_n_cenit_W")));
			onlyNCenitPSld.setToolTipText(GUI.mainGui.getMyProperty("Only_n_cenit_W"));
		
		onlyNCenitPSld.setSnapToTicks(true);
		onlyNCenitPSld.setPaintTicks(true);
		onlyNCenitPSld.setPaintLabels(true);
		onlyNCenitPSld.setMinorTickSpacing(1);
		onlyNCenitPSld.setMinimum(-10);
		onlyNCenitPSld.setMaximum(10);
		onlyNCenitPSld.setBounds(718, 95, 77, 31);
		contentPanel.add(onlyNCenitPSld);
		final JSlider alterationsCountPSld = new JSlider();
		alterationsCountPSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				alterationsCountPSld.setToolTipText(alterationsCountPSld.getValue()+"");
			}
		});
		
			alterationsCountPSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Alterations_count_W")));
			alterationsCountPSld.setToolTipText(GUI.mainGui.getMyProperty("Alterations_count_W"));
		
		alterationsCountPSld.setSnapToTicks(true);
		alterationsCountPSld.setPaintTicks(true);
		alterationsCountPSld.setPaintLabels(true);
		alterationsCountPSld.setMinorTickSpacing(1);
		alterationsCountPSld.setMinimum(-10);
		alterationsCountPSld.setMaximum(10);
		alterationsCountPSld.setBounds(718, 125, 77, 31);
		contentPanel.add(alterationsCountPSld);
		
		final JSlider sharpResolutionPSld = new JSlider();
		sharpResolutionPSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				sharpResolutionPSld.setToolTipText(sharpResolutionPSld.getValue()+"");
				if(sharpResolutionPSld.getValue()==0)
				{
					sharpDirectionCBox.setEnabled(false);
				}
				else
				{
					sharpDirectionCBox.setEnabled(true);
				}
				
			}
		});
		
			sharpResolutionPSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Sharp_resolution_W")));
			sharpResolutionPSld.setToolTipText(GUI.mainGui.getMyProperty("Sharp_resolution_W"));
		
		sharpResolutionPSld.setSnapToTicks(true);
		sharpResolutionPSld.setPaintTicks(true);
		sharpResolutionPSld.setPaintLabels(true);
		sharpResolutionPSld.setMinorTickSpacing(1);
		sharpResolutionPSld.setMinimum(-10);
		sharpResolutionPSld.setMaximum(10);
		sharpResolutionPSld.setBounds(718, 155, 77, 27);
		contentPanel.add(sharpResolutionPSld);
		
		final JSlider resolveActiveDegrees1PSld = new JSlider();
		resolveActiveDegrees1PSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				resolveActiveDegrees1PSld.setToolTipText(resolveActiveDegrees1PSld.getValue()+"");
				if(resolveActiveDegrees1PSld.getValue()==0)
				{
					activeDegree1CBox.setEnabled(false);
					activeDegree1DirectionCBox.setEnabled(false);
					activeDegree1SaveChkBox.setEnabled(false);
				}
				else
				{
					activeDegree1CBox.setEnabled(true);
					activeDegree1DirectionCBox.setEnabled(true);
					activeDegree1SaveChkBox.setEnabled(true);
				}

				
			}
		});
		
			resolveActiveDegrees1PSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Resolve_active_degrees_1_W")));
			resolveActiveDegrees1PSld.setToolTipText(GUI.mainGui.getMyProperty("Resolve_active_degrees_1_W"));
		
		resolveActiveDegrees1PSld.setSnapToTicks(true);
		resolveActiveDegrees1PSld.setPaintTicks(true);
		resolveActiveDegrees1PSld.setPaintLabels(true);
		resolveActiveDegrees1PSld.setMinorTickSpacing(1);
		resolveActiveDegrees1PSld.setMinimum(-10);
		resolveActiveDegrees1PSld.setMaximum(10);
		resolveActiveDegrees1PSld.setBounds(718, 257, 77, 31);
		contentPanel.add(resolveActiveDegrees1PSld);
		
		final JSlider resolveActiveDegrees2PSld = new JSlider();
		resolveActiveDegrees2PSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				resolveActiveDegrees2PSld.setToolTipText(resolveActiveDegrees2PSld.getValue()+"");
				if(resolveActiveDegrees2PSld.getValue()==0)
				{
					activeDegree2CBox.setEnabled(false);
					activeDegree2DirectionCBox.setEnabled(false);
					activeDegree2SaveChkBox.setEnabled(false);
				}
				else
				{
					activeDegree2CBox.setEnabled(true);
					activeDegree2DirectionCBox.setEnabled(true);
					activeDegree2SaveChkBox.setEnabled(true);
				}

			}
		});
		
			resolveActiveDegrees2PSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Resolve_active_degrees_2_W")));
			resolveActiveDegrees2PSld.setToolTipText(GUI.mainGui.getMyProperty("Resolve_active_degrees_2_W"));
		
		resolveActiveDegrees2PSld.setSnapToTicks(true);
		resolveActiveDegrees2PSld.setPaintTicks(true);
		resolveActiveDegrees2PSld.setPaintLabels(true);
		resolveActiveDegrees2PSld.setMinorTickSpacing(1);
		resolveActiveDegrees2PSld.setMinimum(-10);
		resolveActiveDegrees2PSld.setMaximum(10);
		resolveActiveDegrees2PSld.setBounds(718, 288, 77, 30);
		contentPanel.add(resolveActiveDegrees2PSld);
		
		flatResolutionPSld = new JSlider();
		flatResolutionPSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				flatResolutionPSld.setToolTipText(flatResolutionPSld.getValue()+"");
				if(flatResolutionPSld.getValue()==0)
				{
					flatDirectionCBox.setEnabled(false);
				}
				else
				{
					flatDirectionCBox.setEnabled(true);
				}
				
			}
		});
		
			flatResolutionPSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Flat_resolution_W")));
			flatResolutionPSld.setToolTipText(GUI.mainGui.getMyProperty("Flat_resolution_W"));
		
		flatResolutionPSld.setSnapToTicks(true);
		flatResolutionPSld.setPaintTicks(true);
		flatResolutionPSld.setPaintLabels(true);
		flatResolutionPSld.setMinorTickSpacing(1);
		flatResolutionPSld.setMinimum(-10);
		flatResolutionPSld.setMaximum(10);
		flatResolutionPSld.setBounds(718, 185, 77, 39);
		contentPanel.add(flatResolutionPSld);
		
		final JSlider resolveActiveDegrees3PSld = new JSlider();
		resolveActiveDegrees3PSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				resolveActiveDegrees3PSld.setToolTipText(resolveActiveDegrees3PSld.getValue()+"");
				if(resolveActiveDegrees3PSld.getValue()==0)
				{
					activeDegree3CBox.setEnabled(false);
					activeDegree3DirectionCBox.setEnabled(false);
					activeDegree3SaveChkBox.setEnabled(false);
				}
				else
				{
					activeDegree3CBox.setEnabled(true);
					activeDegree3DirectionCBox.setEnabled(true);
					activeDegree3SaveChkBox.setEnabled(true);
				}

			}
		});
		
			resolveActiveDegrees3PSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Resolve_active_degrees_3_W")));
			resolveActiveDegrees3PSld.setToolTipText(GUI.mainGui.getMyProperty("Resolve_active_degrees_3_W"));
		
		resolveActiveDegrees3PSld.setSnapToTicks(true);
		resolveActiveDegrees3PSld.setPaintTicks(true);
		resolveActiveDegrees3PSld.setPaintLabels(true);
		resolveActiveDegrees3PSld.setMinorTickSpacing(1);
		resolveActiveDegrees3PSld.setMinimum(-10);
		resolveActiveDegrees3PSld.setMaximum(10);
		resolveActiveDegrees3PSld.setBounds(718, 317, 77, 31);
		contentPanel.add(resolveActiveDegrees3PSld);
		 
		final JSlider resolveActiveDegrees4PSld = new JSlider();
		resolveActiveDegrees4PSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				resolveActiveDegrees4PSld.setToolTipText(resolveActiveDegrees4PSld.getValue()+"");
				if(resolveActiveDegrees4PSld.getValue()==0)
				{
					activeDegree4CBox.setEnabled(false);
					activeDegree4DirectionCBox.setEnabled(false);
					activeDegree4SaveChkBox.setEnabled(false);
				}
				else
				{
					activeDegree4CBox.setEnabled(true);
					activeDegree4DirectionCBox.setEnabled(true);
					activeDegree4SaveChkBox.setEnabled(true);
				}

			}
		});
		
			resolveActiveDegrees4PSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Resolve_active_degrees_4_W")));
			resolveActiveDegrees4PSld.setToolTipText(GUI.mainGui.getMyProperty("Resolve_active_degrees_4_W"));
		
		resolveActiveDegrees4PSld.setSnapToTicks(true);
		resolveActiveDegrees4PSld.setPaintTicks(true);
		resolveActiveDegrees4PSld.setPaintLabels(true);
		resolveActiveDegrees4PSld.setMinorTickSpacing(1);
		resolveActiveDegrees4PSld.setMinimum(-10);
		resolveActiveDegrees4PSld.setMaximum(10);
		resolveActiveDegrees4PSld.setBounds(718, 347, 77, 39);
		contentPanel.add(resolveActiveDegrees4PSld);
		
		final JSlider zipfLawValueSld = new JSlider();
		zipfLawValueSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				zipfLawValueSld.setToolTipText(zipfLawValueSld.getValue()+"");
			}
		});
		zipfLawValueSld.setVisible(false);
		zipfLawValueSld.setValue(0);
		zipfLawValueSld.setToolTipText("0");
		zipfLawValueSld.setSnapToTicks(true);
		zipfLawValueSld.setPaintTicks(true);
		zipfLawValueSld.setPaintLabels(true);
		zipfLawValueSld.setMinorTickSpacing(1);
		zipfLawValueSld.setMinimum(-10);
		zipfLawValueSld.setMaximum(10);
		zipfLawValueSld.setBounds(718, 400, 77, 31);
		contentPanel.add(zipfLawValueSld);
		
		final JSlider complexityOfMelodyVsBuildingProcessComplexitySld = new JSlider();
		complexityOfMelodyVsBuildingProcessComplexitySld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				complexityOfMelodyVsBuildingProcessComplexitySld.setToolTipText(complexityOfMelodyVsBuildingProcessComplexitySld.getValue()+"");
			}
		});
		complexityOfMelodyVsBuildingProcessComplexitySld.setVisible(false);
		complexityOfMelodyVsBuildingProcessComplexitySld.setValue(0);
		complexityOfMelodyVsBuildingProcessComplexitySld.setToolTipText("0");
		complexityOfMelodyVsBuildingProcessComplexitySld.setSnapToTicks(true);
		complexityOfMelodyVsBuildingProcessComplexitySld.setPaintTicks(true);
		complexityOfMelodyVsBuildingProcessComplexitySld.setPaintLabels(true);
		complexityOfMelodyVsBuildingProcessComplexitySld.setMinorTickSpacing(1);
		complexityOfMelodyVsBuildingProcessComplexitySld.setMinimum(-10);
		complexityOfMelodyVsBuildingProcessComplexitySld.setMaximum(10);
		complexityOfMelodyVsBuildingProcessComplexitySld.setBounds(718, 430, 77, 39);
		contentPanel.add(complexityOfMelodyVsBuildingProcessComplexitySld);
		
		if(GUI.mainGui.getMyProperty("Active_degree_1_save").equals("true"))
		{
			activeDegree1SaveChkBox.setEnabled(true);
		}
		else
		{
			activeDegree1SaveChkBox.setEnabled(false);
		}
		
		if(GUI.mainGui.getMyProperty("Active_degree_2_save").equals("true"))
		{
			activeDegree2SaveChkBox.setEnabled(true);
		}
		else
		{
			activeDegree2SaveChkBox.setEnabled(false);
		}
		
		if(GUI.mainGui.getMyProperty("Active_degree_3_save").equals("true"))
		{
			activeDegree3SaveChkBox.setEnabled(true);
		}
		else
		{
			activeDegree3SaveChkBox.setEnabled(false);
		}
		
		if(GUI.mainGui.getMyProperty("Active_degree_4_save").equals("true"))
		{
			activeDegree4SaveChkBox.setEnabled(true);
		}
		else
		{
			activeDegree4SaveChkBox.setEnabled(false);
		}
		
		avoidInterval1CBox = new JComboBox();
		avoidInterval1CBox.setEnabled(false);
		avoidInterval1CBox.setModel(new DefaultComboBoxModel(new String[] {"2m", "2M", "3m", "3M", "4", "5", "6m", "6M", "7m", "7M"}));
		avoidInterval1CBox.setBounds(99, 285, 67, 20);
		contentPanel.add(avoidInterval1CBox);
		
		avoidInterval2CBox = new JComboBox();
		avoidInterval2CBox.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				avoidInterval2CBox.setEnabled(true);
			}
		});
		avoidInterval2CBox.setEnabled(false);
		avoidInterval2CBox.setModel(new DefaultComboBoxModel(new String[] {"2m", "2M", "3m", "3M", "4", "5", "6m", "6M", "7m", "7M"}));
		avoidInterval2CBox.setBounds(99, 327, 67, 20);
		contentPanel.add(avoidInterval2CBox);
		
		activeDegree1DirectionCBox = new JComboBox();
		activeDegree1DirectionCBox.setToolTipText("Sentido y paso de resoluci\u00F3n");
		activeDegree1DirectionCBox.setEnabled(false);
		activeDegree1DirectionCBox.setModel(new DefaultComboBoxModel(new String[] {"ST Ascendente", "T Ascendente", "ST Descendente", "T Descendente"}));
		activeDegree1DirectionCBox.setSelectedIndex(0);
		activeDegree1DirectionCBox.setBounds(551, 258, 98, 20);
		contentPanel.add(activeDegree1DirectionCBox);
		
		activeDegree2DirectionCBox = new JComboBox();
		activeDegree2DirectionCBox.setEnabled(false);
		activeDegree2DirectionCBox.setModel(new DefaultComboBoxModel(new String[] {"ST Ascendente", "T Ascendente", "ST Descendente", "T Descendente"}));
		activeDegree2DirectionCBox.setSelectedIndex(2);
		activeDegree2DirectionCBox.setBounds(551, 288, 98, 20);
		contentPanel.add(activeDegree2DirectionCBox);
		
		activeDegree3DirectionCBox = new JComboBox();
		activeDegree3DirectionCBox.setEnabled(false);
		activeDegree3DirectionCBox.setModel(new DefaultComboBoxModel(new String[] {"ST Ascendente", "T Ascendente", "ST Descendente", "T Descendente"}));
		activeDegree3DirectionCBox.setSelectedIndex(3);
		activeDegree3DirectionCBox.setBounds(551, 318, 98, 20);
		contentPanel.add(activeDegree3DirectionCBox);
		
		activeDegree4DirectionCBox = new JComboBox();
		activeDegree4DirectionCBox.setEnabled(false);
		activeDegree4DirectionCBox.setModel(new DefaultComboBoxModel(new String[] {"ST Ascendente", "T Ascendente", "ST Descendente", "T Descendente"}));
		activeDegree4DirectionCBox.setSelectedIndex(3);
		activeDegree4DirectionCBox.setBounds(551, 348, 98, 20);
		contentPanel.add(activeDegree4DirectionCBox);
		
		final JComboBox tonicCBox = new JComboBox();
		tonicCBox.setModel(new DefaultComboBoxModel(new String[] {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"}));
		tonicCBox.setSelectedIndex(0);
		tonicCBox.setBounds(208, 61, 48, 20);
		contentPanel.add(tonicCBox);
		
		final JComboBox dominantCBox = new JComboBox();
		dominantCBox.setModel(new DefaultComboBoxModel(new String[] {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"}));
		dominantCBox.setSelectedIndex(7);
		dominantCBox.setBounds(208, 88, 48, 20);
		contentPanel.add(dominantCBox);
		
		final JComboBox melodyExpectedTimeSignatureNumeratorCBox = new JComboBox();
		melodyExpectedTimeSignatureNumeratorCBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		melodyExpectedTimeSignatureNumeratorCBox.setSelectedIndex(3);
		melodyExpectedTimeSignatureNumeratorCBox.setBounds(157, 35, 48, 20);
		contentPanel.add(melodyExpectedTimeSignatureNumeratorCBox);
		
		final JComboBox melodyExpectedTimeSignatureDenominatorCBox = new JComboBox();
		melodyExpectedTimeSignatureDenominatorCBox.setModel(new DefaultComboBoxModel(new String[] {"32", "16", "8", "4", "2", "1"}));
		melodyExpectedTimeSignatureDenominatorCBox.setSelectedIndex(3);
		melodyExpectedTimeSignatureDenominatorCBox.setBounds(208, 35, 48, 20);
		contentPanel.add(melodyExpectedTimeSignatureDenominatorCBox);
		
		activeDegree1CBox = new JComboBox();
		activeDegree1CBox.setToolTipText("Selecci\u00F3n de grado activo");
		activeDegree1CBox.setEnabled(false);
		activeDegree1CBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7"}));
		activeDegree1CBox.setSelectedIndex(6);
		activeDegree1CBox.setBounds(509, 258, 37, 20);
		contentPanel.add(activeDegree1CBox);
		
		activeDegree2CBox = new JComboBox();
		activeDegree2CBox.setEnabled(false);
		activeDegree2CBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7"}));
		activeDegree2CBox.setSelectedIndex(3);
		activeDegree2CBox.setBounds(509, 288, 37, 20);
		contentPanel.add(activeDegree2CBox);
		
		activeDegree3CBox = new JComboBox();
		activeDegree3CBox.setEnabled(false);
		activeDegree3CBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7"}));
		activeDegree3CBox.setSelectedIndex(5);
		activeDegree3CBox.setBounds(509, 318, 37, 20);
		contentPanel.add(activeDegree3CBox);
		
		activeDegree4CBox = new JComboBox();
		activeDegree4CBox.setEnabled(false);
		activeDegree4CBox.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7"}));
		activeDegree4CBox.setSelectedIndex(1);
		activeDegree4CBox.setBounds(509, 348, 37, 20);
		contentPanel.add(activeDegree4CBox);
		
		JLabel label = new JLabel("PESO");
		label.setToolTipText("Factor del criterio en la funci\u00F3n de ajuste");
		label.setFont(new Font("Dialog", Font.BOLD, 12));
		label.setBounds(197, 148, 37, 16);
		contentPanel.add(label);
		
		final JSlider melodyExpectedLengthPSld = new JSlider();
		melodyExpectedLengthPSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				melodyExpectedLengthPSld.setToolTipText(melodyExpectedLengthPSld.getValue()+"");
				if(melodyExpectedLengthPSld.getValue()==0)
				{
					melodyExpectedLengthTxt.setEnabled(false);
				}
				else
				{
					melodyExpectedLengthTxt.setEnabled(true);
				}
				
			}
		});
		
			melodyExpectedLengthPSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Melody_expected_length_W")));
			melodyExpectedLengthPSld.setToolTipText(GUI.mainGui.getMyProperty("Melody_expected_length_W"));
		
		melodyExpectedLengthPSld.setSnapToTicks(true);
		melodyExpectedLengthPSld.setPaintTicks(true);
		melodyExpectedLengthPSld.setPaintLabels(true);
		melodyExpectedLengthPSld.setMinorTickSpacing(1);
		melodyExpectedLengthPSld.setMinimum(-10);
		melodyExpectedLengthPSld.setMaximum(10);
		melodyExpectedLengthPSld.setBounds(179, 177, 77, 24);
		contentPanel.add(melodyExpectedLengthPSld);
		
		final JSlider specifyRangePSld = new JSlider();
		specifyRangePSld.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				specifyRangePSld.setToolTipText(specifyRangePSld.getValue()+"");
				if(specifyRangePSld.getValue()==0)
				{
					lowestNoteCBox.setEnabled(false);
					lowestNoteOctaveCBox.setEnabled(false);
					highestNoteCBox.setEnabled(false);
					highestNoteOctaveCBox.setEnabled(false);
				}
				else
				{
					lowestNoteCBox.setEnabled(true);
					lowestNoteOctaveCBox.setEnabled(true);
					highestNoteCBox.setEnabled(true);
					highestNoteOctaveCBox.setEnabled(true);
				}

			}
		});
		
			specifyRangePSld.setValue(Integer.parseInt(GUI.mainGui.getMyProperty("Melodic_range_W")));
			specifyRangePSld.setToolTipText(GUI.mainGui.getMyProperty("Melodic_range_W"));
		
		specifyRangePSld.setSnapToTicks(true);
		specifyRangePSld.setPaintTicks(true);
		specifyRangePSld.setPaintLabels(true);
		specifyRangePSld.setMinorTickSpacing(1);
		specifyRangePSld.setMinimum(-10);
		specifyRangePSld.setMaximum(10);
		specifyRangePSld.setBounds(179, 216, 77, 24);
		contentPanel.add(specifyRangePSld);
		
		sharpDirectionCBox = new JComboBox();
		sharpDirectionCBox.setModel(new DefaultComboBoxModel(new String[] {"Ascendente", "Descendente"}));
		sharpDirectionCBox.setSelectedIndex(0);
		sharpDirectionCBox.setEnabled(false);
		sharpDirectionCBox.setBounds(602, 156, 98, 20);
		contentPanel.add(sharpDirectionCBox);
		
		flatDirectionCBox = new JComboBox();
		flatDirectionCBox.setModel(new DefaultComboBoxModel(new String[] {"Descendente", "Ascendente"}));
		flatDirectionCBox.setSelectedIndex(0);
		flatDirectionCBox.setEnabled(false);
		flatDirectionCBox.setBounds(602, 186, 98, 20);
		contentPanel.add(flatDirectionCBox);
		
		onlyNClimaxTxt = new JTextField();
		onlyNClimaxTxt.setText("1");
		onlyNClimaxTxt.setEnabled(false);
		
		onlyNClimaxTxt.setBounds(560, 68, 25, 20);
		contentPanel.add(onlyNClimaxTxt);
		onlyNClimaxTxt.setColumns(10);
		
		JLabel lblOnlyNclimax2 = new JLabel("cl\u00EDmax");
		lblOnlyNclimax2.setToolTipText("Se refiere a la nota m\u00E1s aguda de la melod\u00EDa");
		lblOnlyNclimax2.setBounds(594, 70, 55, 16);
		contentPanel.add(lblOnlyNclimax2);
		
		onlyNCenitTxt = new JTextField();
		onlyNCenitTxt.setText("1");
		onlyNCenitTxt.setEnabled(false);
		
		onlyNCenitTxt.setColumns(10);
		onlyNCenitTxt.setBounds(560, 94, 25, 20);
		contentPanel.add(onlyNCenitTxt);
		
		JLabel lblOnlyNCenit2 = new JLabel("c\u00E9nit");
		lblOnlyNCenit2.setToolTipText("Se refiere a la nota m\u00E1s grave de la melod\u00EDa");
		lblOnlyNCenit2.setBounds(594, 94, 55, 16);
		contentPanel.add(lblOnlyNCenit2);
		
		lowestNoteCBox = new JComboBox();
		lowestNoteCBox.setEnabled(false);
		lowestNoteCBox.setModel(new DefaultComboBoxModel(new String[] {"C", "C#", "D", "D#", "E", "F", "F #", "G", "G#", "A", "A#", "B"}));
		lowestNoteCBox.setBounds(12, 214, 43, 20);
		contentPanel.add(lowestNoteCBox);
		
		lowestNoteOctaveCBox = new JComboBox();
		lowestNoteOctaveCBox.setEnabled(false);
		lowestNoteOctaveCBox.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));
		lowestNoteOctaveCBox.setSelectedIndex(5);
		lowestNoteOctaveCBox.setBounds(53, 214, 43, 20);
		contentPanel.add(lowestNoteOctaveCBox);
		
		highestNoteCBox = new JComboBox();
		highestNoteCBox.setEnabled(false);
		highestNoteCBox.setModel(new DefaultComboBoxModel(new String[] {"C", "C#", "D", "D#", "E", "F", "F #", "G", "G#", "A", "A#", "B"}));
		highestNoteCBox.setSelectedIndex(11);
		highestNoteCBox.setBounds(98, 214, 41, 20);
		contentPanel.add(highestNoteCBox);
		
		highestNoteOctaveCBox = new JComboBox();
		highestNoteOctaveCBox.setEnabled(false);
		highestNoteOctaveCBox.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"}));
		highestNoteOctaveCBox.setSelectedIndex(5);
		highestNoteOctaveCBox.setBounds(138, 214, 41, 20);
		contentPanel.add(highestNoteOctaveCBox);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Aceptar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {		
						GUI.mainGui.setMyProperty("Melodic_variety_W", melodicVarietySld.getValue()+"");
					GUI.mainGui.setMyProperty("Rhythmic_variety_W",rhythmicVarietySld.getValue() +"");
					GUI.mainGui.setMyProperty("Initial_pickup_beat_W",initialPickupBeatSld.getValue() +"");
					GUI.mainGui.setMyProperty("Initial_contretemp_W", initialContretempSld.getValue()+"");
					GUI.mainGui.setMyProperty("Just_time_init_W", justTimeInitSld.getValue()+"");
					GUI.mainGui.setMyProperty("Intervalic_variety_W", intervalicVarietySld.getValue()+"");
					GUI.mainGui.setMyProperty("Rest_abusse_W", restAbusseSld.getValue()+"");
					GUI.mainGui.setMyProperty("Joint_degrees_movement_W", jointDegreesMovementSld.getValue()+"");
					GUI.mainGui.setMyProperty("Reiterate_of_the_tonic_note_W", reiterateOfTheTonicNoteSld.getValue()+"");
					GUI.mainGui.setMyProperty("Reiterate_of_the_dominant_note_W", reiterateOfTheDominantNoteSld.getValue()+"");
					GUI.mainGui.setMyProperty("Tonic_start_W",tonicStartSld.getValue() +"");
					GUI.mainGui.setMyProperty("Tonic_end_W", tonicEndSld.getValue()+"");
					GUI.mainGui.setMyProperty("Dominant_start_W", dominantStartSld.getValue()+"");
					GUI.mainGui.setMyProperty("Upward_movement_W", upwardMovementSld.getValue()+"");
					GUI.mainGui.setMyProperty("Downward_movement_W",downwardMovementSld.getValue() +"");
					GUI.mainGui.setMyProperty("Sharp_resolution_W", sharpResolutionPSld.getValue()+"");
					GUI.mainGui.setMyProperty("Flat_resolution_W", flatResolutionPSld.getValue()+"");
					GUI.mainGui.setMyProperty("Sharp_direction_index", sharpDirectionCBox.getSelectedIndex()+"");
					GUI.mainGui.setMyProperty("Flat_direction_index",flatDirectionCBox.getSelectedIndex() +"");
					GUI.mainGui.setMyProperty("Alterations_count_W", alterationsCountPSld.getValue()+"");
					GUI.mainGui.setMyProperty("Only_n_climax_W", onlyNClimaxPSld.getValue()+"");
					GUI.mainGui.setMyProperty("Only_n_cenit_W", onlyNCenitPSld.getValue()+"");
					GUI.mainGui.setMyProperty("Only_n_climax_N", onlyNClimaxTxt.getText()+"");
					GUI.mainGui.setMyProperty("Only_n_cenit_N", onlyNCenitTxt.getText()+"");
					GUI.mainGui.setMyProperty("Avoid_tritone_jump_W", avoidTritoneJumpSld.getValue()+"");
					GUI.mainGui.setMyProperty("Avoid_interval_1_index", avoidInterval1CBox.getSelectedIndex()+"");
					GUI.mainGui.setMyProperty("Avoid_interval_1_name", avoidInterval1CBox.getSelectedItem().toString()+"");
					GUI.mainGui.setMyProperty("Avoid_interval_2_index", avoidInterval2CBox.getSelectedIndex()+"");
					GUI.mainGui.setMyProperty("Avoid_interval_2_name", avoidInterval2CBox.getSelectedItem().toString()+"");
					GUI.mainGui.setMyProperty("Avoid_interval_1_W", avoidInterval1PSld.getValue()+"");
					GUI.mainGui.setMyProperty("Avoid_interval_2_W", avoidInterval2PSld.getValue()+"");
					GUI.mainGui.setMyProperty("Melody_expected_length", melodyExpectedLengthTxt.getText()+"");
					GUI.mainGui.setMyProperty("Melody_expected_length_W", melodyExpectedLengthPSld.getValue()+"");
					GUI.mainGui.setMyProperty("Melody_expected_time_signature_numerator", (String)melodyExpectedTimeSignatureNumeratorCBox.getSelectedItem());
					GUI.mainGui.setMyProperty("Melody_expected_time_signature_denominator", (String)melodyExpectedTimeSignatureDenominatorCBox.getSelectedItem());
					GUI.mainGui.setMyProperty("Lowest_note", lowestNoteCBox.getSelectedItem().toString());
					GUI.mainGui.setMyProperty("Lowest_note_octave", (String)lowestNoteOctaveCBox.getSelectedItem());
					GUI.mainGui.setMyProperty("Highest_note", highestNoteCBox.getSelectedItem().toString());
					GUI.mainGui.setMyProperty("HighestNoteOctave", (String)highestNoteOctaveCBox.getSelectedItem());
					GUI.mainGui.setMyProperty("Melodic_range_W", specifyRangePSld.getValue()+"");
					GUI.mainGui.setMyProperty("Tonic", tonicCBox.getSelectedItem().toString());
					GUI.mainGui.setMyProperty("Dominant", dominantCBox.getSelectedItem().toString());
					GUI.mainGui.setMyProperty("Prohibit_the_repetition_of_sounds_W", prohibitTheRepetitionOfSoundsSld.getValue()+"");
					GUI.mainGui.setMyProperty("Syncopes_W", syncopesPSld.getValue()+"");
					GUI.mainGui.setMyProperty("Active_degree_1", (String)activeDegree1CBox.getSelectedItem());
					GUI.mainGui.setMyProperty("Active_degree_1_direction", activeDegree1DirectionCBox.getSelectedIndex()+"");
					if(activeDegree1SaveChkBox.isSelected())
					{
						GUI.mainGui.setMyProperty("Active_degree_1_save", "true");
					}
					else
					{
						GUI.mainGui.setMyProperty("Active_degree_1_save", "false");
					}
					GUI.mainGui.setMyProperty("Resolve_active_degrees_1_W", resolveActiveDegrees1PSld.getValue()+"");
					
					GUI.mainGui.setMyProperty("Active_degree_2", (String)activeDegree2CBox.getSelectedItem());
					GUI.mainGui.setMyProperty("Active_degree_2_direction", activeDegree2DirectionCBox.getSelectedIndex()+"");
					if(activeDegree2SaveChkBox.isSelected())
					{
						GUI.mainGui.setMyProperty("Active_degree_2_save", "true");
					}
					else
					{
						GUI.mainGui.setMyProperty("Active_degree_2_save", "false");
					}
					GUI.mainGui.setMyProperty("Resolve_active_degrees_2_W", resolveActiveDegrees2PSld.getValue()+"");
					
					GUI.mainGui.setMyProperty("Active_degree_3", (String)activeDegree3CBox.getSelectedItem());
					GUI.mainGui.setMyProperty("Active_degree_3_direction", activeDegree3DirectionCBox.getSelectedIndex()+"");
					if(activeDegree3SaveChkBox.isSelected())
					{
						GUI.mainGui.setMyProperty("Active_degree_3_save", "true");
					}
					else
					{
						GUI.mainGui.setMyProperty("Active_degree_3_save", "false");
					}
					GUI.mainGui.setMyProperty("Resolve_active_degrees_3_W", resolveActiveDegrees3PSld.getValue()+"");
					
					GUI.mainGui.setMyProperty("Active_degree_4", (String)activeDegree4CBox.getSelectedItem());
					GUI.mainGui.setMyProperty("Active_degree_4_direction", activeDegree4DirectionCBox.getSelectedIndex()+"");
					if(activeDegree4SaveChkBox.isSelected())
					{
						GUI.mainGui.setMyProperty("Active_degree_4_save", "true");
					}
					else
					{
						GUI.mainGui.setMyProperty("Active_degree_4_save", "false");
					}
					GUI.mainGui.setMyProperty("Resolve_active_degrees_4_W", resolveActiveDegrees4PSld.getValue()+"");
					if(makeSeriesChkBox.isSelected())
					{
						GUI.mainGui.setMyProperty("Make_series", "true");
					}
					else
					{
						GUI.mainGui.setMyProperty("Make_series", "false");
					}

					setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
}
