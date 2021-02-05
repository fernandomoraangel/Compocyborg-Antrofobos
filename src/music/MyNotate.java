

package music;

import java.awt.event.*;
import java.awt.*; 
import java.awt.RenderingHints.Key;
import java.lang.Integer;
import java.io.*;

import javax.sound.midi.*;
import javax.swing.JFrame;
import javax.swing.UIManager;

import GUI.mainGui;
import jm.gui.cpn.BassStave;
import jm.gui.cpn.CpnZoomScreen;
import jm.gui.cpn.GrandStave;
import jm.gui.cpn.JmMidiPlayer;
import jm.gui.cpn.LetterNotesEditor;
import jm.gui.cpn.Notate;
import jm.gui.cpn.ParmScreen;
import jm.gui.cpn.PhraseViewer;
import jm.gui.cpn.PianoStave;
import jm.gui.cpn.TrebleStave;
import jm.music.data.*;
import jm.midi.*;
import jm.util.Read;
import jm.util.Write;
import jm.gui.cpn.Stave;
import jm.util.Play;
import jm.JMC;

/**
* This class displays a frame with a common practice notation display
* of the score passed to it.
* The parameter and add data by text attributes only work on the first stave.
* Some GPL changes for jMusic CPN Written by Al Christians 
* (achrist@easystreet.com).
* Contributed by Trillium Resources Corporation, Oregon's
* leading provider of unvarnished software.
* @author Andrew Brown
*/

public class MyNotate extends JFrame implements  JMC {
    private Score score;
    //private Stave stave;
    //private Phrase phrase;
    private Phrase[] phraseArray;
    private Stave[] staveArray;
    private int scrollHeight = 130, locationX = 0, locationY = 0;
    private Dialog keyDialog, timeDialog;
	
    				
    public boolean timeToStop;                 				
    // the panel for all the stave panels to go in to
    private Panel scoreBG;
    // the constraints for the scoreBG layout
    private GridBagConstraints constraints;
    private GridBagLayout layout;
    // The scoreBg goes into this scroll pane to enable navigation
    private ScrollPane scroll;

    private String lastFileName   = "*.mid";
    private String lastDirectory  = "";
    private String fileNameFilter = "*.mid";

    private boolean     zoomed;
    private Phrase      beforeZoom = new Phrase();
    private Phrase      afterZoom = new Phrase();
    /* The height of the notate window */
    private int height = 0;
    private int width = 750;

    
    
    private void clearZoom() {
        zoomed = false;
        
    }        

   
    
    public MyNotate(final Score score, int locX, int locY) {
        super(score.getTitle());
        this.setIconImage(Toolkit.getDefaultToolkit().getImage(mainGui.class.getResource("/com/sun/java/swing/plaf/windows/icons/Computer.gif")));
        clearZoom();
        this.score = score;
        locationX = locX;
        locationY = locY;
        init();
    }
    
    public void init() {
    	
        // menus

        // components
        scroll = new ScrollPane(1);
        scroll.getHAdjustable().setUnitIncrement(10);
        scroll.getVAdjustable().setUnitIncrement(10);
        scoreBG = new Panel();
        addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		int key = e.getKeyCode();
        		if(key==80)
        		{
        			Play.midi(score);	
        		}
        		else if(key==83)
        		{
        			Play.stopMidiCycle();
        		}

        	}
        });
        
 
        layout = new GridBagLayout();
        scoreBG.setLayout(layout); //new GridLayout(score.size(), 1));
        constraints = new GridBagConstraints();
        setupConstraints();
        
        scroll.add(scoreBG);
        getContentPane().add(scroll); 
        
        setupArrays();
        makeAppropriateStaves();
        
        this.pack();
        this.setLocation(locationX, locationY);
       
        this.show();
    }
    
    private void setupArrays() {
        // set up arrays
        phraseArray = new Phrase[score.size()];
        staveArray = new Stave[score.size()];

        for (int i=0; i<staveArray.length; i++) {
            phraseArray[i] = score.getPart(i).getPhrase(0);
            staveArray[i] = new PianoStave();
            staveArray[i].setKeySignature(score.getKeySignature());
            staveArray[i].setMetre(score.getNumerator());
            staveArray[i].setBarNumbers(true);
        }
    }
    
    private void setupConstraints() {
        constraints.weightx = 100;
        constraints.weighty = 0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        //constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.BOTH;
    }
    
    private void calcHeight() {
        // work out the height
        height = 0;
        for (int i=0; i<staveArray.length; i++) {
            height += staveArray[i].getSize().height;
        }

    }
    
    private void makeAppropriateStaves(){
        Stave[] tempStaveArray  = new Stave[staveArray.length];
        for(int i=0; i<score.size(); i++) {
            Phrase currentPhrase = score.getPart(i).getPhrase(0);
            tempStaveArray[i] = new PianoStave();
            if(currentPhrase.getHighestPitch() < A6 &&
                currentPhrase.getLowestPitch() > FS3) tempStaveArray[i] = new TrebleStave();
            else if(currentPhrase.getHighestPitch() < F4 &&
                currentPhrase.getLowestPitch() > B1) tempStaveArray[i] = new BassStave();
            else if(currentPhrase.getHighestPitch() > A6 ||
                currentPhrase.getLowestPitch() < B1) tempStaveArray[i] = new GrandStave();
        }
        updateAllStaves(tempStaveArray);

    }   

    private void makeTrebleStave() {
        Stave[] tempStaveArray  = new Stave[score.size()];
        for(int i=0; i<staveArray.length; i++) {
             tempStaveArray[i] = new TrebleStave();
        }
        updateAllStaves(tempStaveArray);
    }
    
    private void updateAllStaves(Stave[] tempStaveArray) {
        int gridyVal = 0;
        int gridheightVal = 0;
        int totalHeight = 0;
        scoreBG.removeAll();
        for(int i=0; i<staveArray.length; i++) {
            // store current phrase parameters in new stave object
            tempStaveArray[i].setKeySignature(staveArray[i].getKeySignature());
            tempStaveArray[i].setMetre(staveArray[i].getMetre());
            tempStaveArray[i].setBarNumbers(staveArray[i].getBarNumbers());
            tempStaveArray[i].setPhrase(phraseArray[i]);
            // create new stave panel
            staveArray[i] = tempStaveArray[i];
            tempStaveArray[i] = null;
            // set and add constraints
            constraints.gridy = gridyVal;
            if(staveArray[i].getClass().isInstance(new TrebleStave()) || 
                staveArray[i].getClass().isInstance(new BassStave())) {
                    gridheightVal = 1;
            } else if(staveArray[i].getClass().isInstance(new PianoStave())) {
                gridheightVal = 2;
            } else {
                gridheightVal = 3;
            }
            constraints.gridheight = gridheightVal;
            // add to display
            scoreBG.add(staveArray[i], constraints);
            gridyVal += gridheightVal;
            totalHeight += staveArray[i].getPanelHeight();
        }
        //calcHeight();
        scroll.setSize(new Dimension(width, totalHeight));
        // check window size against screen
       Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension d = tk.getScreenSize();
        this.setSize(new Dimension(this.width, Math.min(d.height-40, totalHeight+40)));
        //this.setResizable(true);
        this.pack();
    }        

    private void makeBassStave() {
        Stave[] tempStaveArray  = new Stave[score.size()];
        for(int i=0; i<staveArray.length; i++) {
             tempStaveArray[i] = new BassStave();
        }
        updateAllStaves(tempStaveArray);
    }
 
     
    
    private void makePianoStave() {
        Stave[] tempStaveArray  = new Stave[score.size()];
        for(int i=0; i<tempStaveArray.length; i++) {
             tempStaveArray[i] = new PianoStave();
        }
        updateAllStaves(tempStaveArray);
    }
    
    /*
            // store current phrase
            phrase = stave.getPhrase().copy();
            int tempKey = stave.getKeySignature();
            double tempTime = stave.getMetre();
            boolean tempBarNumbers = stave.getBarNumbers();
            // create new stave panel
            stave = new PianoStave();
            scoreBG.removeAll();
            scoreBG.add(stave);
            scroll.setSize(width, stave.getSize().height + 20);
            this.pack();
            // replace stave
            stave.setPhrase(phrase);
            stave.setKeySignature(tempKey);
            stave.setMetre(tempTime);
            stave.setBarNumbers(tempBarNumbers);
    }    
    */
        
    private void makeGrandStave() {
        Stave[] tempStaveArray  = new Stave[score.size()];
        for(int i=0; i<tempStaveArray.length; i++) {
             tempStaveArray[i] = new GrandStave();
        }
        updateAllStaves(tempStaveArray);
    }
    
    /*
            // store current phrase
            phrase = stave.getPhrase().copy();
            int tempKey = stave.getKeySignature();
            double tempTime = stave.getMetre();
            boolean tempBarNumbers = stave.getBarNumbers();
            // create new stave panel
            stave = new GrandStave();
            scoreBG.removeAll();
            scoreBG.add(stave);
            scroll.setSize(width, stave.getSize().height + 20);
            this.pack();
            // replace stave
            stave.setPhrase(phrase);
            stave.setKeySignature(tempKey);
            stave.setMetre(tempTime);
            stave.setBarNumbers(tempBarNumbers);
    }       
    */ 

    class PlayRepeater extends Thread {
        
        JmMidiPlayer midiPlayer;
        Notate       n; 
        
        public PlayRepeater(         
                String       str,
                Notate       nParm,                
                JmMidiPlayer midiPlayerParm ) {
            super(str);           
            n         = nParm;         
            midiPlayer = midiPlayerParm;                    
        }                    
        
        public void run() {
            do {
                midiPlayer.play();                                                        
            } while(!n.timeToStop);                
        }
    }        
    

    
    /**
    * Dialog to import a MIDI file
    */
     public void openMidi() {
        Score s = new Score();
        FileDialog loadMidi = new FileDialog(this, "Select a MIDI file.", FileDialog.LOAD);
        loadMidi.setDirectory( lastDirectory );
        loadMidi.setFile( lastFileName );
        loadMidi.show();
        String fileName = loadMidi.getFile();
        if (fileName != null) {
            lastFileName = fileName;
            lastDirectory = loadMidi.getDirectory();                        
            Read.midi(s, lastDirectory + fileName);  
            setNewScore(s);
        }
    }
    
    
    private void setNewScore(Score score) {
        this.score = score;
        // set up arrays
        setupArrays();        
        makeAppropriateStaves();
    }
    
    /**
     * Dialog to import a jm file
     */
     
     public void openJM() {
        FileDialog loadjm = new FileDialog(this, "Select a jm score file.", FileDialog.LOAD);
        loadjm.setDirectory( lastDirectory );
        loadjm.show();
        String fileName = loadjm.getFile();
        if (fileName != null) {
            Score s = new Score();
            lastDirectory = loadjm.getDirectory();  
            Read.jm(s, lastDirectory + fileName);
            setNewScore(s);
        }
    }
    
    /**
     * Dialog to import a jm XML file
     */
     
     @SuppressWarnings("deprecation")
	public void openJMXML() {
        FileDialog loadjmxml = new FileDialog(this, "Select a jMusic XML score file.", FileDialog.LOAD);
        loadjmxml.setDirectory( lastDirectory );
        loadjmxml.show();
        String fileName = loadjmxml.getFile();
        if (fileName != null) {
            Score s = new Score();
            lastDirectory = loadjmxml.getDirectory(); 
            Read.xml(s, lastDirectory + fileName);
            setNewScore(s);
        }
    }
    

    
    /**
     * Dialog to save phrase as a MIDI file.
     */
    public void saveMidi() {
        FileDialog fd = new FileDialog(this, "Save as a MIDI file...",FileDialog.SAVE);
                fd.show();
                            
        //write a MIDI file and stave properties to disk
        if ( fd.getFile() != null) {
            Write.midi(score, fd.getDirectory()+fd.getFile());
            /*
            for(int i=0; i<staveArray.length; i++){
                System.out.println(i);
                StavePhraseProperties props =
                    new StavePhraseProperties(
                            staveArray[i], staveArray[i].getPhrase());
                try {    
                    System.out.println("props");
                    props.writeToFile(                                         
                        fd.getDirectory()+fd.getFile());   
                }
                catch ( Throwable e) {
                    System.out.println(
                        "Error Saving Properties " +
                        e.getMessage() );                                                
                }
            } 
            */
                                              
        }
    }
    
    /**
     * Dialog to save score as a jMusic serialized jm file.
     */
    public void saveJM() {
        FileDialog fd = new FileDialog(this, "Save as a jm file...",FileDialog.SAVE);
                fd.show();
                            
        //write a MIDI file to disk
        if ( fd.getFile() != null) {
            Write.jm(score, fd.getDirectory()+fd.getFile());
        }
    }
    
    /**
     * Dialog to save score as a jMusic XML file.
     */
    public void saveJMXML() {
        FileDialog fd = new FileDialog(this, "Save as a jMusic XML file...",FileDialog.SAVE);
                fd.show();
                            
        //write an XML file to disk
        if ( fd.getFile() != null) {
            Write.xml(score, fd.getDirectory()+fd.getFile());
        }
    }

    
    /**
    * Get the first phrase from a MIDI file.
    */
    public Phrase readMidiPhrase() {
        FileDialog loadMidi = new FileDialog(this, "Select a MIDI file.", FileDialog.LOAD);
        loadMidi.show();
        String fileName = loadMidi.getFile();
        Phrase phr = new Phrase(0.0);
        Score scr = new Score();
        if (fileName != null) {
            Read.midi(scr, loadMidi.getDirectory() + fileName); 
        }
        scr.clean();
        if (scr.size() > 0 && scr.getPart(0).size() > 0) phr = scr.getPart(0).getPhrase(0);
        //System.out.println("Size = " + phr.size());
        return phr;
    }
    
    private Score getLastMeasure() {
        double beats = phraseArray[0].getNumerator();
        double endTime = score.getEndTime();
        int numbOfCompleteBars = (int)(endTime / beats);
        double startOflastBar = beats * numbOfCompleteBars;
        if (startOflastBar == endTime) startOflastBar -= beats;
        Score oneBar = score.copy(startOflastBar, endTime);
        
        for(int i=0; i<oneBar.size();i++){
            oneBar.getPart(i).getPhrase(0).setStartTime(0.0);
        }
        return oneBar;
    }
    
    private static double getRhythmAdjustment(
                        double  beats,
                        double  beatIncrement ) {
        double increments;
        increments = beats/beatIncrement;
        double tolerance;                                   
        tolerance = 0.00001;
        double answer;  
        answer = 0.0;
        double n;
        n = Math.floor(increments);
        while(( Math.floor(increments+tolerance) > n ) 
                && (tolerance > 0.00000000000001)) {
            answer = tolerance;
            tolerance = tolerance / 2;
        }                        
        return answer * beatIncrement;                            
    }                            
    
    private static void adjustTimeValues(Phrase phr) {
        int i;
        double t, dt, st;
        for( i = 0; i < phr.size(); ++i) {        
            t  = phr.getNote(i).getRhythmValue();
            dt = getRhythmAdjustment( t, 1.0 / 256.0 ); 
            phr.getNote(i).setRhythmValue(t+dt);
        }
        
        st = 0.0;
        for( i = 0; i < phr.size(); ++i) {        
            t  = phr.getNote(i).getRhythmValue();
            st = st + t;
            dt = getRhythmAdjustment( st, 1.0 ); 
            phr.getNote(i).setRhythmValue(t+dt);
            st = st + dt;
        }
    }  
    
    /**
    * Toggle the phrase title display
    */
    public void toggleDisplayTitle() {
        for(int i=0; i<staveArray.length; i++) {
            staveArray[i].setDisplayTitle(!staveArray[i].getDisplayTitle());
        }
    }
    
    /**
     * Invoked when a window has been opened.
     */
    public void windowOpened(WindowEvent e) {
    }

    /**
     * Invoked when a window is in the process of being closed.
     * The close operation can be overridden at this point.
     */
    public void windowClosing(WindowEvent e) {
        if(e.getSource() == this) dispose();
        if(e.getSource() == keyDialog) keyDialog.dispose();
        if(e.getSource() == timeDialog) timeDialog.dispose();
    }

    /**
     * Invoked when a window has been closed.
     */
    public void windowClosed(WindowEvent e) {
    	GUI.mainGui.setMusicPosition();
    }

    /**
     * Invoked when a window is iconified.
     */
    public void windowIconified(WindowEvent e) {
    }

    /**
     * Invoked when a window is de-iconified.
     */
    public void windowDeiconified(WindowEvent e) {
    }

    /**
     * Invoked when a window is activated.
     */
    public void windowActivated(WindowEvent e) {
    	
    	
    }

    /**
     * Invoked when a window is de-activated.
     */
    public void windowDeactivated(WindowEvent e) {
    }



	
}
