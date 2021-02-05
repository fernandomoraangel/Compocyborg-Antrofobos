package music;
import javax.swing.JFrame;
import javax.swing.UIManager;

import GUI.mainGui;
import jm.JMC;
import music.MyNotate;
import jm.music.data.*;
import jm.util.*;
public class notator  {

    private Score theScore;
	private String file;
	private int timeSignatureDenominator;
	private int timeSignatureNumerator;
	private String title;
	public   notator(String f, int tSNumerator, int tDenominator, String t) {
        this.file=f;
        this.timeSignatureNumerator=tSNumerator;
        this.timeSignatureDenominator=tDenominator;
        this.title=t;
    }
	
	
    @SuppressWarnings("deprecation")
	public void notate()
    
    {
    	theScore = new Score();
        jm.util.Read.midi(theScore, file);  
        theScore.setTimeSignature(timeSignatureNumerator, timeSignatureDenominator);
        theScore.setTitle(title);
    	int []pos=mainGui.getMusicPosition();
    	MyView.notate(theScore,pos[0],pos[1]);
    }
}
