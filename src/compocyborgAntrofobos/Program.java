package compocyborgAntrofobos;

import music.Melody;
import evolution.Evolutionator;

public class Program {
    Program(){
        for (int k = 0; k < 1; k++) 
        {
        Evolutionator evolutionator = new Evolutionator();
        
		evolutionator.experiment();
        try {
			Melody myMelody =new Melody(evolutionator.getPhenotipe());
			myMelody.saveToMidi(k,"");
			myMelody.notate(k, 0, 0);
			myMelody.play();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }//end for
    }
}
