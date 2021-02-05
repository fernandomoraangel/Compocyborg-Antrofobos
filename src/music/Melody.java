/*******************************************************************************
 * 2013, All rights reserved.
 *******************************************************************************/
package music;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JOptionPane;

import jm.music.data.Note;

import org.jfugue.midi.MidiFileManager;
import org.jfugue.pattern.Intervals;
import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import Util.Statistics.StatCatcher;
import GUI.ConfigMusicPropertiesGUI;

import java.util.*;

/**
 * Description of Melody.
 * 
 * @author Fernando Mora Angel
 */

public class Melody extends Pattern {

	static final String[] sharpScale = { "C", "C#", "D", "D#", "E", "F", "F#",
			"G", "G#", "A", "A#", "B" };
	static final String[] flatScale = { "C", "Db", "D", "Eb", "E", "F", "Gb",
			"G", "Ab", "A", "Bb", "B" };
	static final String[] figures = { "w", "h", "q", "i", "s", "t", "x", "o" };
	static final String[] majorScale = { "2", "2", "1", "2", "2", "2", "1" };
	/**
	 * Description of the property
	 */
	public notator myNotator = null;
	/**
	 * Description of the property
	 */
	private MidiFileManager midiFileManager;
	/**
	 * Description of the property
	 */
	private Player myplayer = null;
	private int timeSignatureNumerator;
	private int timeSignatureDenominator;
	private String melodyString;
	private String midiFileName;
	private Properties properties;
	private String filePropertiesName;
	private Properties propertiesFile;


	/**
	 * The constructor.
	 * 
	 * @param timeSignatureDenominator
	 * @param timeSignatureNumerator
	 */
	public void inicialize()
	{
		try {
			propertiesFile=new Properties();
			propertiesFile.load(new FileInputStream("propertiesFile.properties"));
			properties=new Properties();
			properties.load(new FileInputStream(propertiesFile.getProperty("File_properties_name")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public Melody(String s, int tempo, int tSNum, int tSDen) {
		super();
		String s1 = null;
		// Dar formmato a la melodia
		s1 = s.replaceAll(" ", "");
		s = "";
		for (int i = 0; i < s1.length(); i++) {
			if (Character.isLowerCase(s1.charAt(i))) {
				s = s + s1.charAt(i) + " ";
			} else {
				s = s + s1.charAt(i) + "";
			}
		}
		s1 = s.trim();
		melodyString = s;
		this.add("T" + tempo + " " + s1);
		timeSignatureNumerator = tSNum;
		timeSignatureDenominator = tSDen;
		inicialize();
	}

	public Melody(String s) {
		super();
		String s1 = null;

		// Dar formmato a la melodia
		s1 = s.replaceAll(" ", "");
		s = "";
		for (int i = 0; i < s1.length(); i++) {
			if (Character.isLowerCase(s1.charAt(i))) {
				s = s + s1.charAt(i) + " ";
			} else {
				s = s + s1.charAt(i) + "";
			}
		}
		s1 = s1.trim();
		melodyString = s;
		this.add(s1);
		inicialize();
	}

	public double scaleValue(double v, double scala) {
		// Valor por escala deseada/escala actual
		return (v * 1) / scala;

	}

	public double CountNotes() {
		double p = 0;
		String s = this.toString();
		p = p + (s.split("C").length - 1);
		p = p + (s.split("D").length - 1);
		p = p + (s.split("E").length - 1);
		p = p + (s.split("F").length - 1);
		p = p + (s.split("G").length - 1);
		p = p + (s.split("A").length - 1);
		p = p + (s.split("B").length - 1);
		System.out.print(p);
		return p;

	}

	private double countBeats()// Y los puntillos????????
	{
		double p = 0.0;
		String s = this.toString();
		p = p + (s.split("w").length - 1) * 4;
		p = p + (s.split("h").length - 1) * 2;
		p = p + (s.split("q").length - 1) * 1;
		p = p + (s.split("i").length - 1) * 0.5;
		p = p + (s.split("s").length - 1) * 0.25;
		p = p + (s.split("t").length - 1) * 0.125;
		p = p + (s.split("x").length - 1) * 0.0625;
		p = p + (s.split("o").length - 1) * 0.03125;
		return p;
	}

	public double countMeasures() {
		int num = Integer.parseInt(properties.getProperty("Melody_expected_time_signature_numerator","4"));
		int den = Integer.parseInt(properties.getProperty("Melody_expected_time_signature_denominator","4"));
		return countBeats() / num;// arreglar para 8,16
	}

	private double CountFigures() {
		double p = 0.0;
		String s = this.toString();
		p = p + (s.split("w").length - 1);
		p = p + (s.split("h").length - 1);
		p = p + (s.split("q").length - 1);
		p = p + (s.split("i").length - 1);
		p = p + (s.split("t").length - 1);
		p = p + (s.split("x").length - 1);
		p = p + (s.split("o").length - 1);
		return p;
	}

	private double CountRests() {
		double p = 0.0;
		String s = this.toString();
		p = (s.split("R").length - 1);
		return p;
	}

	public int[] calculeIntervals() {
		double p = 0;
		String s = this.melodyString;
		String ss[] = s.split(" ");
		int[] intervalsofMelody = new int[(ss.length)];
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}

		for (int i = 0; i < ss.length; i++) {
			String note1 = "";
			int note1Index = 0;
			String note2 = "";
			int note2Index = 0;
			// Primera nota
			for (int j = 0; j < ss[i].length(); j++) {
				if (!Character.isLowerCase(ss[i].charAt(j))) {
					note1 = note1 + ss[i].charAt(j);
				}
			}
			for (int k = 0; k < sharpScale.length; k++) {
				if (sharpScale[k].toString().equals(note1)) {
					note1Index = k;
				} else if (flatScale[k].toString().equals(note1)) {
					note1Index = k;
				}
			}

			// Segunda nota
			if (i < ss.length - 1) {
				for (int j = 0; j < ss[i + 1].length(); j++) {
					if (!Character.isLowerCase(ss[i + 1].charAt(j))) {
						note2 = note2 + ss[i + 1].charAt(j);
					}

				}
				for (int k = 0; k < sharpScale.length; k++) {
					if (sharpScale[k].toString().equals(note2)) {
						note2Index = k;
					} else if (flatScale[k].toString().equals(note2)) {
						note2Index = k;
					}
				}
			}
			// Calcular intervalo
			if (!note1.equals("R") && !note2.equals("R")) {
				intervalsofMelody[i] = -(note1Index - note2Index);
			} else {
				intervalsofMelody[i] = -2000;
			}

		}
		intervalsofMelody[intervalsofMelody.length - 1] = intervalsofMelody.length - 1;
		return intervalsofMelody;
	}

	private int intervalCount(int[] intervalsOfMelody, int searchInterval)

	{
		int intervalCountResult = 0;
		for (int i = 0; i < intervalsOfMelody.length - 1; i++) {
			if (Math.abs(intervalsOfMelody[i]) == searchInterval) {
				intervalCountResult++;
			}
		}
		return intervalCountResult;
	}

	/**
	 * Description of the method evalMelodicRichness.
	 * 
	 * @return
	 */
	public double EvalMelodicVariety() {
		double p = 0.0;
		String s = this.toString();
		double notes = CountNotes();
		double diferentNotes = 0;
		if (s.split("C").length != 0) {
			diferentNotes++;
		}
		if (s.split("D").length != 0) {
			diferentNotes++;
		}
		if (s.split("E").length != 0) {
			diferentNotes++;
		}
		if (s.split("F").length != 0) {
			diferentNotes++;
		}
		if (s.split("G").length != 0) {
			diferentNotes++;
		}
		if (s.split("A").length != 0) {
			diferentNotes++;
		}
		if (s.split("B").length != 0) {
			diferentNotes++;
		}

		return diferentNotes / notes;
	}

	/**
	 * Description of the method evalRithmRichness.
	 */
	public double EvalRhythmicVariety() {

		double p = 0.0;
		String s = this.toString();

		double diferentFigures = 0;
		if (s.split("w").length != 0) {
			diferentFigures++;
		}
		if (s.split("h").length != 0) {
			diferentFigures++;
		}
		if (s.split("q").length != 0) {
			diferentFigures++;
		}
		if (s.split("i").length != 0) {
			diferentFigures++;
		}
		if (s.split("t").length != 0) {
			diferentFigures++;
		}
		if (s.split("x").length != 0) {
			diferentFigures++;
		}
		if (s.split("o").length != 0) {
			diferentFigures++;
		}

		return diferentFigures / CountFigures();
	}

	public double EvalInitialPickupBeat() {
		int num = Integer.parseInt(properties.getProperty("Melody_expected_time_signature_numerator"));
		int den = Integer.parseInt(properties.getProperty("Melody_expected_time_signature_denominator"));
		String s = this.melodyString;
		String ss[] = s.split(" ");
		double initialPickupBeat = 0;
		int inRest = 0;
		double beatsInRest = 0;
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}

		for (int i = 0; i < ss.length; i++) {
			String note1 = "";
			String figure1 = "";

			for (int j = 0; j < ss[i].length(); j++) {
				if (!Character.isLowerCase(ss[i].charAt(j))) {
					note1 = note1 + ss[i].charAt(j);
				}

				if (Character.isLowerCase(ss[i].charAt(j))) {
					figure1 = figure1 + ss[i].charAt(j);
				}
			}

			if (!note1.equals("R")) {
				inRest = 1;

			}

			if (inRest == 0) {
				switch (figure1) {
				case "w":
					beatsInRest = beatsInRest + 4;
					break;
				case "h":
					beatsInRest = beatsInRest + 2;
					break;
				case "q":
					beatsInRest = beatsInRest + 1;
					break;
				case "i":
					beatsInRest = beatsInRest + 0.5;
					break;
				case "s":
					beatsInRest = beatsInRest + 0.25;
					break;
				case "t":
					beatsInRest = beatsInRest + 0.125;
					break;
				}
			}
			if (beatsInRest < num && beatsInRest > 2 && den == 2) {
				initialPickupBeat = 1;
			} else if (beatsInRest < num && beatsInRest > 1 && den == 4) {
				initialPickupBeat = 1;
			} else if (beatsInRest < num && beatsInRest > 0.5 && den == 8) {
				initialPickupBeat = 1;
			} else if (beatsInRest < num && beatsInRest > 0.25 && den == 16) {
				initialPickupBeat = 1;
			} else if (beatsInRest < num && beatsInRest > 0.125 && den == 32) {
				initialPickupBeat = 1;
			}
		}
		return initialPickupBeat;

	}

	public double EvalInitialContretemp() {
		int den = Integer.parseInt(properties.getProperty("Melody_expected_time_signature_denominator"));;
		String s = this.melodyString;
		String ss[] = s.split(" ");
		double initialContretemp = 0;
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}

		for (int i = 0; i < ss.length; i++) {
			String note1 = "";
			String figure1 = "";
			for (int j = 0; j < ss[i].length(); j++) {
				if (!Character.isLowerCase(ss[i].charAt(j))) {
					note1 = note1 + ss[i].charAt(j);
				}

				if (Character.isLowerCase(ss[i].charAt(j))) {
					figure1 = figure1 + ss[i].charAt(j);
				}
			}

			if (note1.equals("R")) {
				if (den == 2 && figure1.equals("q")) {
					initialContretemp = 1;
				}
			}

			if (note1.equals("R")) {
				if (den == 4 && figure1.equals("i")) {
					initialContretemp = 1;
				}
			}
			if (note1.equals("R")) {
				if (den == 8 && figure1.equals("t")) {
					initialContretemp = 1;
				}
			}
		}
		return initialContretemp;
	}

	public double EvalJustTimeInit() {
		String s = this.melodyString;
		String ss[] = s.split(" ");
		double justTimeInit = 0;
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}

		for (int i = 0; i < 1; i++) {
			String note1 = "";
			// Primera nota
			for (int j = 0; j < ss[i].length(); j++) {
				if (!Character.isLowerCase(ss[i].charAt(j))) {
					note1 = note1 + ss[i].charAt(j);
				}
			}
			if (!note1.equals("R")) {
				justTimeInit = 1;
			}
		}
		return justTimeInit;
	}

	public double EvalSyncopes() {
		double p = 0;
		String s = this.melodyString;
		String ss[] = s.split(" ");
		double syncopesCount = 0;
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}

		for (int i = 0; i < ss.length; i++) {
			String figure1 = "";
			String figure2 = "";
			int figure1Index = 0;
			int figure2Index = 0;
			// Primera figura rítmica
			for (int j = 0; j < ss[i].length(); j++) {
				if (Character.isLowerCase(ss[i].charAt(j))) {
					figure1 = figure1 + ss[i].charAt(j);
				}
			}

			for (int k = 0; k < figures.length; k++) {
				if (figures[k].toString().equals(figure1)) {
					figure1Index = k;
				}

			}

			// Segunda figura
			if (i < ss.length - 1) {
				for (int j = 0; j < ss[i + 1].length(); j++) {
					if (Character.isLowerCase(ss[i + 1].charAt(j))) {
						figure2 = figure2 + ss[i + 1].charAt(j);
					}

				}
				for (int k = 0; k < figures.length; k++) {
					if (figures[k].toString().equals(figure2)) {
						figure2Index = k;
					}

				}

			}
			// Calcular síncopa
			if (figure1Index - figure2Index == 1) {
				syncopesCount++;
			}

		}

		return syncopesCount / ss.length;
	}

	public double EvalIntervalicVariety() {
		double differentIntervals = 0;
		int[] intervals = this.calculeIntervals();
		for (int i = 1; i < 12; i++) {
			if (this.intervalCount(intervals, i) > 0) {
				differentIntervals++;
			}
		}
		return differentIntervals / intervals[intervals.length - 1];

	}

	public double EvalRestAbusse() {
		String s = this.melodyString;
		String ss[] = s.split(" ");
		double restCount = 0;
		double notes = 0;
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}

		for (int i = 0; i < ss.length; i++) {
			String note1 = "";
			String figure1 = "";
			for (int j = 0; j < ss[i].length(); j++) {
				if (!Character.isLowerCase(ss[i].charAt(j))) {
					note1 = note1 + ss[i].charAt(j);
				}

				if (Character.isLowerCase(ss[i].charAt(j))) {
					figure1 = figure1 + ss[i].charAt(j);
				}
			}

			if (note1.equals("R")) {
				restCount++;
			} else {

				notes++;

			}
		}
		return restCount / notes;
	}

	public double EvalJointDegreesMovement() {
		int i2m = this.intervalCount(this.calculeIntervals(), 2);
		int i2M = this.intervalCount(this.calculeIntervals(), 3);
		return (i2m * 0.1) + (i2M * 0.1);

	}

	public String degreeSearch(int degree, String tonic) {
		int DegreeSemitones = 0;
		int tonicIndex = 0;
		int degreeIndex = 0;
		String degreeNote = "";
		// Calcular cual nota es el grado activo requerido
		for (int i = 0; i < degree - 1; i++) {
			DegreeSemitones = DegreeSemitones + Integer.parseInt(majorScale[i]);
		}

		for (int k = 0; k < sharpScale.length; k++) {
			if (sharpScale[k].toString().equals(tonic)) {
				tonicIndex = k;
			} else if (flatScale[k].toString().equals(tonic)) {
				tonicIndex = k;
			}
		}
		degreeIndex = tonicIndex;
		int step = 0;

		while (step != DegreeSemitones) {
			step++;
			degreeIndex++;

			if (degreeIndex == sharpScale.length) {
				degreeIndex = 0;
			}
		}
		return degreeNote = sharpScale[degreeIndex];
	}

	public String calculeResolution(int degree, int direction, String tonic) {
		String degreeNote = degreeSearch(degree, tonic);
		int degreeIndex = searchNoteIndex(degreeNote);
		// directions 0, st asc, 1, t as, 2, st desc, 3 t des
		if (direction == 0) {
			int step = 0;
			while (step < 1) {
				step++;
				degreeIndex++;
				if (degreeIndex == sharpScale.length) {
					degreeIndex = 0;
				}
			}
		}
		if (direction == 1) {
			int step = 0;
			while (step < 2) {
				step++;
				degreeIndex++;
				if (degreeIndex == sharpScale.length) {
					degreeIndex = 0;
				}
			}
		}

		if (direction == 2) {
			int step = 0;
			while (step > -1) {
				step--;
				degreeIndex--;
				if (degreeIndex == sharpScale.length) {
					degreeIndex = 0;
				}
			}
		}

		if (direction == 3) {
			int step = 0;
			while (step > -2) {
				step--;
				degreeIndex--;
				if (degreeIndex == sharpScale.length) {
					degreeIndex = 0;
				}
			}
		}

		return sharpScale[degreeIndex];
	}

	public String calculeSaveNote(int degree, int direction, String tonic) {
		String degreeNote = degreeSearch(degree, tonic);
		int degreeIndex = searchNoteIndex(degreeNote);
		// directions 0, st asc, 1, t as, 2, st desc, 3 t des
		// PAra st ascendente, avanza dos semitonos más
		if (direction == 0) {
			int step = 0;
			while (step < 3) {
				step++;
				degreeIndex++;
				if (degreeIndex == sharpScale.length) {
					degreeIndex = 0;
				}
			}
		}
		if (direction == 1) {
			int step = 0;
			while (step < 3) {
				step++;
				degreeIndex++;
				if (degreeIndex == sharpScale.length) {
					degreeIndex = 0;
				}
			}
		}

		if (direction == 2) {
			int step = 0;
			while (step > -3) {
				step--;
				degreeIndex--;
				if (degreeIndex == sharpScale.length) {
					degreeIndex = 0;
				}
			}
		}

		if (direction == 3) {
			int step = 0;
			while (step > -3) {
				step--;
				degreeIndex--;
				if (degreeIndex == sharpScale.length) {
					degreeIndex = 0;
				}
			}
		}

		return sharpScale[degreeIndex];

	}

	public int searchNoteIndex(String note) {
		int noteIndex = 0;
		for (int k = 0; k < sharpScale.length; k++) {

			if (sharpScale[k].toString().equals(note)) {
				noteIndex = k;
			} else if (flatScale[k].toString().equals(note)) {
				noteIndex = k;
			}
		}
		return noteIndex;
	}

	public double EvalResolveActiveDegrees(int activeDegree, int direction,
			String save) {
		double correctResolutions = 0;
		String tonic = properties.getProperty("tonic");
		String activeDegreeNote = degreeSearch(activeDegree, tonic);
		String saveNote = "";
		if (save.equals("true")) {
			saveNote = calculeSaveNote(activeDegree, direction, tonic);
		}
		String resolutionNote = calculeResolution(activeDegree, direction,
				tonic);

		String s = this.melodyString;
		String ss[] = s.split(" ");
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}

		for (int i = 0; i < ss.length; i++) {
			String note1 = "";
			int note1Index = 0;
			String note2 = "";
			int note2Index = 0;
			String note3 = "";
			int note3Index = 0;
			// Primera nota
			for (int j = 0; j < ss[i].length(); j++) {
				if (!Character.isLowerCase(ss[i].charAt(j))) {
					note1 = note1 + ss[i].charAt(j);
				}
			}
			for (int k = 0; k < sharpScale.length; k++) {
				if (sharpScale[k].toString().equals(note1)) {
					note1Index = k;
				} else if (flatScale[k].toString().equals(note1)) {
					note1Index = k;
				}
			}

			// Segunda nota
			if (i < ss.length - 1) {
				for (int j = 0; j < ss[i + 1].length(); j++) {
					if (!Character.isLowerCase(ss[i + 1].charAt(j))) {
						note2 = note2 + ss[i + 1].charAt(j);
					}

				}
				for (int k = 0; k < sharpScale.length; k++) {
					if (sharpScale[k].toString().equals(note2)) {
						note2Index = k;
					} else if (flatScale[k].toString().equals(note2)) {
						note2Index = k;
					}
				}
			}

			// tercera nota
			if (i < ss.length - 2) {
				for (int j = 0; j < ss[i + 1].length(); j++) {
					if (!Character.isLowerCase(ss[i + 1].charAt(j))) {
						note2 = note2 + ss[i + 1].charAt(j);
					}

				}
				for (int k = 0; k < sharpScale.length; k++) {
					if (sharpScale[k].toString().equals(note2)) {
						note2Index = k;
					} else if (flatScale[k].toString().equals(note2)) {
						note2Index = k;
					}
				}
			}
			// Calcular resolución
			if (!save.equals("true") && note1.equals(activeDegreeNote)
					&& note2.equals(resolutionNote)) {
				correctResolutions++;
			}
			if (save.equals("true") && note1.equals(activeDegreeNote)
					&& note2.equals(saveNote) && note3.equals(resolutionNote)) {
				correctResolutions = correctResolutions + 0.5;
			}
		}
		return correctResolutions / (ss.length / 2);
	}

	public double EvalReiterateOfTheTonicNote() {
		String tonic = properties.getProperty("Tonic");
		String s = this.melodyString;
		String ss[] = s.split(" ");
		double occurencesOfTonic = 0;
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}

		for (int i = 0; i < ss.length; i++) {
			String note1 = "";
			// Primera nota
			for (int j = 0; j < ss[i].length(); j++) {
				if (!Character.isLowerCase(ss[i].charAt(j))) {
					note1 = note1 + ss[i].charAt(j);
				}

			}
			if (note1.equals(tonic)) {
				occurencesOfTonic++;
			}
		}
		return occurencesOfTonic / ss.length;
	}

	public double EvalReiterateOfTheDominantNote() {
		String dominant = properties.getProperty("Dominant");
		String s = this.melodyString;
		String ss[] = s.split(" ");
		double occurencesOfDominant = 0;
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}

		for (int i = 0; i < ss.length; i++) {
			String note1 = "";
			// Primera nota
			for (int j = 0; j < ss[i].length(); j++) {
				if (!Character.isLowerCase(ss[i].charAt(j))) {
					note1 = note1 + ss[i].charAt(j);
				}

			}
			if (note1.equals(dominant)) {
				occurencesOfDominant++;
			}
		}
		return occurencesOfDominant / ss.length;

	}

	public double EvalTonicStart() {
		double p = 0;
		String tonic = properties.getProperty("Tonic");
		String note1 = "";
		String s = this.melodyString;

		String ss[] = s.split(" ");
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}

		if (!Character.isLowerCase(ss[0].charAt(0))) {
			note1 = note1 + ss[0].charAt(0);
		}

		if (note1.equals(tonic)) {
			return 1.0;
		} else {
			return -1.0;
		}
	}

	public double EvalTonicEnd() {
		double p = 0;
		String tonic = properties.getProperty("Tonic");
		String note1 = "";
		String s = this.melodyString;

		String ss[] = s.split(" ");
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}

		if (!Character.isLowerCase(ss[ss.length - 1].charAt(0))) {
			note1 = note1 + ss[ss.length - 1].charAt(0);
		}

		if (note1.equals(tonic)) {
			return 1.0;
		} else {
			return -1.0;
		}

	}

	public double EvalDominantStart() {
		double p = 0;
		String dominant = properties.getProperty("Dominant");
		String note1 = "";
		String s = this.melodyString;

		String ss[] = s.split(" ");
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}

		if (!Character.isLowerCase(ss[0].charAt(0))) {
			note1 = note1 + ss[0].charAt(0);
		}

		if (note1.equals(dominant)) {
			return 1.0;
		} else {
			return -1.0;
		}

	}

	public double EvalupwardMovement() {
		int[] intervals = this.calculeIntervals();
		double upwardMovements = 0;
		for (int i = 0; i < intervals.length - 2; i++) {
			if (intervals[i] > 0) {
				upwardMovements++;
			}
		}

		return upwardMovements / intervals[intervals.length - 1];

	}

	public double EvalDownwardMovement() {
		int[] intervals = this.calculeIntervals();
		double downwardMovements = 0;
		for (int i = 0; i < intervals.length - 2; i++) {
			if (intervals[i] < 0) {
				downwardMovements++;
			}
		}
		return downwardMovements / intervals[intervals.length - 1];

	}

	public double EvalAlterationsCount() {
		double p = 0;
		String s = this.melodyString;
		String ss[] = s.split(" ");
		double alterationsCount = 0;
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}

		for (int i = 0; i < ss.length; i++) {
			String note1 = "";
			// Primera nota
			for (int j = 0; j < ss[i].length(); j++) {
				if (!Character.isLowerCase(ss[i].charAt(j))) {
					note1 = note1 + ss[i].charAt(j);
				}
			}

			// Contar alteraciones
			if (note1.split("#").length > 0 | note1.split("b").length > 0) {
				alterationsCount++;
			}

		}

		return alterationsCount / ss.length;
	}

	public double EvalSharpResolution() {
		int direction = Integer.parseInt(properties.getProperty("Sharp_direction_index"));
		double correctResolutions = 0;
		String s = this.melodyString;
		String ss[] = s.split(" ");
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}

		for (int i = 0; i < ss.length; i++) {
			String note1 = "";
			int note1Index = 0;
			String note2 = "";
			int note2Index = 0;
			// Primera nota
			for (int j = 0; j < ss[i].length(); j++) {
				if (!Character.isLowerCase(ss[i].charAt(j))) {
					note1 = note1 + ss[i].charAt(j);
				}
			}
			for (int k = 0; k < sharpScale.length; k++) {
				if (sharpScale[k].toString().equals(note1)) {
					note1Index = k;
				} else if (flatScale[k].toString().equals(note1)) {
					note1Index = k;
				}
			}

			// Segunda nota
			if (i < ss.length - 1) {
				for (int j = 0; j < ss[i + 1].length(); j++) {
					if (!Character.isLowerCase(ss[i + 1].charAt(j))) {
						note2 = note2 + ss[i + 1].charAt(j);
					}

				}
				for (int k = 0; k < sharpScale.length; k++) {
					if (sharpScale[k].toString().equals(note2)) {
						note2Index = k;
					} else if (flatScale[k].toString().equals(note2)) {
						note2Index = k;
					}
				}
			}
			// Calcular resolución
			if (note1.contains("#")) {
				if (direction == 0) {
					if (note2Index - note1Index == 1) {
						correctResolutions++;
					} else {
						correctResolutions--;
					}
				} else if (direction == 1) {
					if (note2Index - note1Index == -1) {
						correctResolutions++;
					} else {
						correctResolutions--;
					}
				}
			}
		}
		return correctResolutions / ss.length;
	}

	public double EvalFlatResolution() {
		double p = 0;
		int direction = Integer.parseInt(properties.getProperty("Flat_direction_index"));
		double correctResolutions = 0;
		String s = this.melodyString;
		String ss[] = s.split(" ");
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}

		for (int i = 0; i < ss.length; i++) {
			String note1 = "";
			int note1Index = 0;
			String note2 = "";
			int note2Index = 0;
			// Primera nota
			for (int j = 0; j < ss[i].length(); j++) {
				if (!Character.isLowerCase(ss[i].charAt(j))) {
					note1 = note1 + ss[i].charAt(j);
				}
			}
			for (int k = 0; k < sharpScale.length; k++) {
				if (sharpScale[k].toString().equals(note1)) {
					note1Index = k;
				} else if (flatScale[k].toString().equals(note1)) {
					note1Index = k;
				}
			}

			// Segunda nota
			if (i < ss.length - 1) {
				for (int j = 0; j < ss[i + 1].length(); j++) {
					if (!Character.isLowerCase(ss[i + 1].charAt(j))) {
						note2 = note2 + ss[i + 1].charAt(j);
					}

				}
				for (int k = 0; k < sharpScale.length; k++) {
					if (sharpScale[k].toString().equals(note2)) {
						note2Index = k;
					} else if (flatScale[k].toString().equals(note2)) {
						note2Index = k;
					}
				}
			}
			// Calcular resolución
			if (note1.contains("b")) {
				if (direction == 0) {
					if (note2Index - note1Index == -1) {
						correctResolutions++;
					} else {
						correctResolutions--;
					}
				} else if (direction == 1) {
					if (note2Index - note1Index == 1) {
						correctResolutions++;
					} else {
						correctResolutions--;
					}
				}
			}
		}
		return correctResolutions / ss.length;
	}

	public double EvalProhibitTheRepetitionOfSounds() {
		String s = this.melodyString;
		int repeatedNotes = 0;
		String ss[] = s.split(" ");
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}

		for (int i = 0; i < ss.length; i++) {
			String note1 = "";
			String note2 = "";

			// Primera nota
			for (int j = 0; j < ss[i].length(); j++) {
				if (!Character.isLowerCase(ss[i].charAt(j))) {
					note1 = note1 + ss[i].charAt(j);
				}
			}

			// Segunda nota
			if (i < ss.length - 1) {
				for (int j = 0; j < ss[i + 1].length(); j++) {
					if (!Character.isLowerCase(ss[i + 1].charAt(j))) {
						note2 = note2 + ss[i + 1].charAt(j);
					}
				}
			}
			// Calcular intervalo
			if (!note1.equals(note2)) {
				repeatedNotes++;
			}
		}

		return repeatedNotes * -0.1;
	}

	public double EvalAvoidTritoneJump() {
		int interval = this.intervalCount(this.calculeIntervals(), 6);
		return interval * -0.1;
	}

	public double EvalAvoidInterval1() {
		int avoidInterval1 = Integer.parseInt(properties.getProperty("Avoid_interval_1_index"));
		int interval = this.intervalCount(this.calculeIntervals(),
				avoidInterval1);
		return interval * -0.1;

	}

	public double EvalAvoidInterval2() {
		int avoidInterval2 = Integer.parseInt(properties.getProperty("Avoid_interval_2_index"));
		int interval = this.intervalCount(this.calculeIntervals(),
				avoidInterval2);
		return interval * -0.1;

	}

	public double EvalMelodyExpectedLength() {
		double mel = Integer.parseInt(properties.getProperty("Melody_expected_length","8"));
		double obtainMelodyLenght = (mel / countMeasures());
		return obtainMelodyLenght;
	}

	

	

	public double calculeFitness() {
		double p = 0;
		p = Integer.parseInt(properties.getProperty("Melody_expected_length_W","0"))
				* this.EvalMelodyExpectedLength();
		p = p
				+ (Integer.parseInt(properties.getProperty("Melodic_variety_W","0")) * this
						.EvalMelodicVariety());
		p = p
				+ (Integer.parseInt(properties.getProperty("Rhythmic_variety_W","0")) * this
						.EvalRhythmicVariety());
		p = p
				+ (Integer.parseInt(properties.getProperty("Intervalic_variety_W","0")) * this
						.EvalIntervalicVariety());
		p = p
				+ (Integer.parseInt(properties.getProperty("Avoid_tritone_jump_W","0")) * this
						.EvalAvoidTritoneJump());
		p = p
				+ (Integer.parseInt(properties.getProperty("Avoid_interval_1_W","0")) * this
						.EvalAvoidInterval1());
		p = p
				+ (Integer.parseInt(properties.getProperty("Avoid_interval_2_W","0")) * this
						.EvalAvoidInterval2());
		p = p
				+ (Integer.parseInt(properties.getProperty("Joint_degrees_movement_W","0")) * this
						.EvalJointDegreesMovement());
		p = p
				+ (Integer.parseInt(properties.getProperty("Prohibit_the_repetition_of_sounds_W","0")) * this
						.EvalProhibitTheRepetitionOfSounds());
		p = p
				+ (Integer.parseInt(properties.getProperty("Upward_movement_W","0")) * this
						.EvalupwardMovement());
		p = p
				+ (Integer.parseInt(properties.getProperty("Downward_movement_W","0")) * this
						.EvalDownwardMovement());
		p = p
				+ (Integer.parseInt(properties.getProperty("Tonic_start_W","0")) * this
						.EvalTonicStart());
		p = p
				+ (Integer.parseInt(properties.getProperty("Dominant_start_W","0")) * this
						.EvalDominantStart());
		p = p
				+ (Integer.parseInt(properties.getProperty("Tonic_end_W","0")) * this
						.EvalTonicEnd());
		p = p
				+ (Integer.parseInt(properties.getProperty("Reiterate_of_the_tonic_note_W","0")) * this
						.EvalReiterateOfTheTonicNote());
		p = p
				+ (Integer.parseInt(properties.getProperty("Reiterate_of_the_dominant_note_W","0")) * this
						.EvalReiterateOfTheDominantNote());
		p = p
				+ (Integer.parseInt(properties.getProperty("Rest_abusse_W","0"))* this
						.EvalRestAbusse());
		p = p
				+ (Integer.parseInt(properties.getProperty("Just_time_init_W","0")) * this
						.EvalJustTimeInit());
		p = p
				+ (Integer.parseInt(properties.getProperty("Initial_contretemp_W","0")) * this
						.EvalInitialContretemp());
		p = p
				+ (Integer.parseInt(properties.getProperty("Initial_pickup_beat_W","0")) * this
						.EvalInitialPickupBeat());
		p = p
				+ (Integer.parseInt(properties.getProperty("Syncopes_W","0")) * this
						.EvalSyncopes());
		p = p
				+ (Integer.parseInt(properties.getProperty("Alterations_count_W","0")) * this
						.EvalAlterationsCount());
		p = p
				+ (Integer.parseInt(properties.getProperty("Sharp_resolution_W","0")) * this
						.EvalSharpResolution());
		p = p
				+ (Integer.parseInt(properties.getProperty("Flat_resolution_W","0")) * this
						.EvalFlatResolution());
		p = p
				+ (Integer.parseInt(properties.getProperty("Resolve_active_degrees_1_W","0")) * this
						.EvalResolveActiveDegrees(
								Integer.parseInt(properties.getProperty("Active_degree_1","7")),
								Integer.parseInt(properties.getProperty("Active_degree_1_direction","1")),
								properties.getProperty("Active_degree_1_save","false")));
		
		p = p
				+ (Integer.parseInt(properties.getProperty("Resolve_active_degrees_1_W","0")) * this
						.EvalResolveActiveDegrees(
								Integer.parseInt(properties.getProperty("Active_degree_2","4")),
								Integer.parseInt(properties.getProperty("Active_degree_2_direction","1")),
								properties.getProperty("Active_degree_2_save","false")));
		
		p = p
				+ (Integer.parseInt(properties.getProperty("Resolve_active_degrees_1_W","0")) * this
						.EvalResolveActiveDegrees(
								Integer.parseInt(properties.getProperty("Active_degree_3","6")),
								Integer.parseInt(properties.getProperty("Active_degree_3_direction","1")),
								properties.getProperty("Active_degree_3_save","false")));
		
		p = p
				+ (Integer.parseInt(properties.getProperty("Resolve_active_degrees_1_W","0")) * this
						.EvalResolveActiveDegrees(
								Integer.parseInt(properties.getProperty("Active_degree_4","2")),
								Integer.parseInt(properties.getProperty("Active_degree_4_direction","1")),
								properties.getProperty("Active_degree_4_save","false")));

		p = p
				+ (Integer.parseInt(properties.getProperty("Melodic_range_W","0")) * this
						.EvalMelodicRange(properties.getProperty("Lowest_note"),
								Integer.parseInt(properties.getProperty("Lowest_note_octave","5")),
								properties.getProperty("Highest_note"),
								Integer.parseInt(properties.getProperty("HighestNoteOctave","5"))));

		p = p
				+ (Integer.parseInt(properties.getProperty("Only_n_climax_W","0")) * this
						.EvaOnlyNClimax(Integer.parseInt(properties.getProperty("Only_n_climax_N"))));

		p = p
				+ (Integer.parseInt(properties.getProperty("Only_n_cenit_W","0")) * this
						.EvaOnlyNClimax(Integer.parseInt(properties.getProperty("Only_n_cenit_N"))));
		
		if (properties.getProperty("Make_Series","true").equals("true")) {
			p = p + EvalSeries();
		}

		
		return p;
	}

	public double EvalSeries() {
		Boolean isSerie = true;
		int countNote = 0;
		String s = this.melodyString;
		String ss[] = s.split(" ");
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}
		String currentNote = "";
		String compareNote = "";
		for (int i = 0; i < ss.length; i++) {
			currentNote = "";
			countNote = 0;
			for (int j = 0; j < ss[i].length(); j++) {
				if (!Character.isLowerCase(ss[i].charAt(j))) {
					currentNote = currentNote + ss[i].charAt(j);
				}
			}
			// Comparar
			for (int k = 0; k < ss.length; k++) {
				compareNote = "";
				for (int j = 0; j < ss[k].length(); j++) {
					if (!Character.isLowerCase(ss[k].charAt(j))) {
						compareNote = compareNote + ss[k].charAt(j);
					}

				}
				if (currentNote.equals(compareNote)) {
					countNote++;
				}
			}
			if (countNote > 1) {
				isSerie = false;
			}
		}
		// TODO Auto-generated method stub
		if (isSerie) {
			return 1;
		} else {
			return 0;
		}
	}

	public int EvaOnlyNCenit(int onlyNCenit) {
		int NCenitCount = 0;
		Note cenit = this.LowestNote();

		double p = 0;
		String s = this.melodyString;
		String ss[] = s.split(" ");
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}
		String currentNote = "";
		int currentNoteOctave = 5;

		// Determinar cada nota
		for (int i = 0; i < ss.length; i++) {
			currentNote = "";
			for (int j = 0; j < ss[i].length(); j++) {
				if (!Character.isLowerCase(ss[i].charAt(j))) {
					currentNote = currentNote + ss[i].charAt(j);
				}
				if (Character.isDigit(ss[i].charAt(j))) {
					currentNoteOctave = ss[i].charAt(j);
				}
			}
			// comparar
			if (currentNote.equals(cenit.getNoteName())
					&& currentNoteOctave == cenit.getNoteOctave())

			{
				NCenitCount++;
			}

		}

		if (NCenitCount == onlyNCenit) {
			return 1;
		} else {
			return 0;
		}
	}

	public int EvaOnlyNClimax(int onlyNClimax) {
		int NClimaxCount = 0;
		Note climax = this.HighestNote();

		double p = 0;
		String s = this.melodyString;
		String ss[] = s.split(" ");
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}
		String currentNote = "";
		int currentNoteOctave = 5;

		// Determinar cada nota
		for (int i = 0; i < ss.length; i++) {
			currentNote = "";
			for (int j = 0; j < ss[i].length(); j++) {
				if (!Character.isLowerCase(ss[i].charAt(j))) {
					currentNote = currentNote + ss[i].charAt(j);
				}
				if (Character.isDigit(ss[i].charAt(j))) {
					currentNoteOctave = ss[i].charAt(j);
				}
			}
			// comparar
			if (currentNote.equals(climax.getNoteName())
					&& currentNoteOctave == climax.getNoteOctave())

			{
				NClimaxCount++;
			}

		}

		if (NClimaxCount == onlyNClimax) {
			return 1;
		} else {
			return 0;
		}
	}

	private class Note {
		private String noteName;
		private int noteOctave;

		public Note(String name, int octave) {
			noteName = name;
			noteOctave = octave;
		}

		public String getNoteName() {
			return noteName;
		}

		public int getNoteOctave() {
			return noteOctave;
		}
	}

	public Note LowestNote() {
		double p = 0;
		String s = this.melodyString;
		String ss[] = s.split(" ");
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}
		String currentNote = "";
		int currentNoteIndex = 0;
		int currentNoteOctave = 5;
		String lowestNote = "";
		int lowestNoteIndex = 0;
		int lowestNoteOctave = 5;
		// Determinar primera nota como candidata inicial
		for (int j = 0; j < ss[0].length(); j++) {
			if (!Character.isLowerCase(ss[0].charAt(j))) {
				lowestNote = lowestNote + ss[0].charAt(j);
			}
			if (Character.isDigit(ss[0].charAt(j))) {
				lowestNoteOctave = ss[0].charAt(j);
			}
		}
		if (currentNote.equals("R")) {
			lowestNoteIndex = 1000;
		} else {
			lowestNoteIndex = searchNoteIndex(lowestNote);
		}

		// Determinar cada nota
		for (int i = 1; i < ss.length; i++) {
			currentNote = "";
			for (int j = 0; j < ss[i].length(); j++) {
				if (!Character.isLowerCase(ss[i].charAt(j))) {
					currentNote = currentNote + ss[i].charAt(j);
				}
				if (Character.isDigit(ss[i].charAt(j))) {
					currentNoteOctave = ss[i].charAt(j);
				}
			}
			if (currentNote.equals("R")) {
				currentNoteIndex = 1000;
			} else {
				currentNoteIndex = searchNoteIndex(currentNote);
			}

			// comparar
			if (currentNoteOctave < lowestNoteOctave) {
				lowestNote = currentNote;
				lowestNoteOctave = currentNoteOctave;
			}
			if (currentNoteOctave == lowestNoteOctave) {
				if (currentNoteIndex < lowestNoteIndex) {
					lowestNote = currentNote;
					lowestNoteOctave = currentNoteOctave;
				}
			}
		}
		Note lw = new Note(lowestNote, lowestNoteOctave);
		return lw;
	}

	public Note HighestNote() {
		double p = 0;
		String s = this.melodyString;
		String ss[] = s.split(" ");
		if (s.charAt(0) == 'T') {
			String[] ss1 = {};
			System.arraycopy(ss, 1, ss1, 0, ss.length - 2);
			ss = ss1;
		}
		String currentNote = "";
		int currentNoteIndex = 0;
		int currentNoteOctave = 5;
		String highestNote = "";
		int highestNoteIndex = 0;
		int highestNoteOctave = 5;
		// Determinar primera nota como candidata inicial
		for (int j = 0; j < ss[0].length(); j++) {
			if (!Character.isLowerCase(ss[0].charAt(j))) {
				highestNote = highestNote + ss[0].charAt(j);
			}
			if (Character.isDigit(ss[0].charAt(j))) {
				highestNoteOctave = ss[0].charAt(j);
			}
		}
		if (currentNote.equals("R")) {
			highestNoteIndex = -1;
		} else {
			highestNoteIndex = searchNoteIndex(highestNote);
		}

		// Determinar cada nota
		for (int i = 1; i < ss.length; i++) {
			currentNote = "";
			for (int j = 0; j < ss[i].length(); j++) {
				if (!Character.isLowerCase(ss[i].charAt(j))) {
					currentNote = currentNote + ss[i].charAt(j);
				}
				if (Character.isDigit(ss[i].charAt(j))) {
					currentNoteOctave = ss[i].charAt(j);
				}
			}
			if (currentNote.equals("R")) {
				currentNoteIndex = -1;
			} else {
				currentNoteIndex = searchNoteIndex(currentNote);
			}

			// comparar
			if (currentNoteOctave > highestNoteOctave) {
				highestNote = currentNote;
				highestNoteOctave = currentNoteOctave;
			}
			if (currentNoteOctave == highestNoteOctave) {
				if (currentNoteIndex > highestNoteIndex) {
					highestNote = currentNote;
					highestNoteOctave = currentNoteOctave;
				}
			}
		}
		Note hg = new Note(highestNote, highestNoteOctave);
		return hg;
	}

	private double EvalMelodicRange(String lowestNote, int lowestNoteOctave,
			String highestNote, int highestNoteOctave) {
		// TODO Auto-generated method stub
		Note ln = this.LowestNote();
		Note hn = this.HighestNote();
		if (lowestNote == ln.getNoteName()
				&& lowestNoteOctave == ln.getNoteOctave()
				&& highestNote == hn.getNoteName()
				&& highestNoteOctave == hn.getNoteOctave()) {
			return 1.0;
		} else {
			return 0.0;
		}

	}

	/**
	 * Description of the method play.
	 */
	public void play() {

		ThreadPlayer tplay = new ThreadPlayer();
		tplay.run(this);
		tplay.start();
	}

	public void playAndNotate(int k, boolean pl, String title) {

		ThreadNotator tnotator = new ThreadNotator();
		tnotator.run(midiFileName, title);
		tnotator.start();
		if (pl) {
			ThreadPlayer tplay = new ThreadPlayer();
			tplay.run(this);
			tplay.start();
		}

	}

	/**
	 * Description of the method saveToMidi.
	 * 
	 * @param prefix
	 * @throws FileNotFoundException
	 */
	@SuppressWarnings("static-access")
	public void saveToMidi(int k, String prefix) {
		midiFileManager = new MidiFileManager();
		midiFileName = prefix + " " + k + ".mid";
		try {
			OutputStream out = new FileOutputStream(midiFileName);
			midiFileManager.savePatternToMidi(this, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("static-access")
	public void saveToMidi(String midiFileName) {
		
		try {
			OutputStream out = new FileOutputStream(midiFileName);
			midiFileManager.savePatternToMidi(this, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Description of the method notate.
	 */
	public void notate(int k, int x, int y) {
		ThreadNotator tnotator = new ThreadNotator();
		tnotator.run(midiFileName, "");
		tnotator.start();
	}

	public class ThreadPlayer extends Thread {
		ThreadPlayer() {
			super();
		}

		public void run(Melody p) {
			myplayer = new Player();
			myplayer.play(p);
		}

	}

	public class ThreadNotator extends Thread {
		ThreadNotator() {
			super();
		}

		public void run(String file, String title) {
			myNotator = new notator(file, timeSignatureNumerator,
					timeSignatureDenominator, title);
			myNotator.notate();

		}

	}

}
