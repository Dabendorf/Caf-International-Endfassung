package cafeint;

import java.util.Collections;

import cafeint.Gastkarte.Geschlecht;
import cafeint.Gastkarte.Land;

public class Spielstart {
	
	/*public void neuesspiel() {
		for(int i=0;i<2;i++) {
			CafeMain.getSpieler(i).setPunkte(0);
			CafeMain.getSpieler(i).getHandkarten().clear();
		}
		for(int i=0;i<21;i++) {
			Barkartenecke.getBarzellen(i).setGast(null);
			Barkartenecke.getBarzellen(i).repaint();
		}
		for(int i=0;i<12;i++) {
			if(CafeMain.getLaenderkarten().size()!= 0) {
				new Spielzuege().legetischkarte(i);
			}
		}
		
		CafeMain.setAktSpieler(0);
		new Spielzuege().handkartendemarkieren();
		CafeMain.setZustand(12);
		
		CafeMain.getGastkarten().clear();
		CafeMain.getLaenderkarten().clear();
		CafeMain.getBarkarten().clear();
		gastkartenmischen();
		laenderkartenmischen();
		
		for(int i=0;i<2;i++) {
			Uebersichtsecke.getInfz(i).punktzahlschreiben();
			for(int n=0;n<5;n++) {
				Uebersichtsecke.getKartsp(i,n).repaint();
				Spielkartenecke.getHandkarte(n).repaint();
			}
		}
	}*/
	
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