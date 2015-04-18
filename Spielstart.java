package cafeint;

import java.util.Collections;

import cafeint.Gastkarte.Geschlecht;
import cafeint.Gastkarte.Land;

public class Spielstart {
	
	private void gastkartenmischen() {
		for(int l=0;l<2;l++){
	    	for(Land land : Land.values()) {
	            int anzahl = 2;
	            if(land == Land.JOKER) {
	               anzahl = 1;
	            }
	            for(int g=0;g<anzahl;g++) {
	                for(Geschlecht geschlecht : Geschlecht.values()) {
	                    Variablenkammer.getGastkarten().add(new Gastkarte(land, geschlecht));
	                }    
	            }
	        }
	    }
		Collections.shuffle(Variablenkammer.getGastkarten());
		
		for(int p=0;p<5;p++) {
			Variablenkammer.getSpieler(0).getHandkarten().add(Variablenkammer.getGastkarten().get(0));
			Variablenkammer.getSpieler(1).getHandkarten().add(Variablenkammer.getGastkarten().get(1));
			Variablenkammer.getGastkarten().remove(0);
			Variablenkammer.getGastkarten().remove(0);
	    }
	}
	
	private void laenderkartenmischen() {
		for(int l=0;l<2;l++) {
			 for(Land land : Land.values()) {
				 if(land != Land.JOKER) {
					 Variablenkammer.getLaenderkarten().add(new Laenderkarte(land));
				 }
			 }
		}
		Collections.shuffle(Variablenkammer.getLaenderkarten());
	}

}