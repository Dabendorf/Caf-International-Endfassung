package cafeint;

import java.util.Collections;

import cafeint.Gastkarte.Geschlecht;
import cafeint.Gastkarte.Land;

public class Spielstart {
	
	public void neuesspiel() {
		for(int i=0;i<2;i++) {
			Variablen.getSpieler(i).setPunkte(0);
			Variablen.getSpieler(i).getHandkarten().clear();
		}
		for(int i=0;i<21;i++) {
			Barkartenecke.getBarzelle(i).setGast(null);
			Barkartenecke.getBarzelle(i).repaint();
		}
		
		Variablen.getGastkarten().clear();
		Variablen.getLaenderkarten().clear();
		Variablen.getBarkarten().clear();
		gastkartenmischen();
		laenderkartenmischen();
		for(int i=0;i<12;i++) {
			new Spielzuege().legetischkarte(Variablen.getTische().get(i));
		}
		
		Variablen.setAktSpieler(0); //muss man noch Ändern
		new Spielzuege().handkartendemarkieren();
		Spielkartenecke.gastkstzahlLaden();
		Spielkartenecke.landkstzahlLaden();
		Variablen.setZustand(12);
		
		for(int i=0;i<2;i++) {
			Statistikecke.getInfz(i).punktzahlschreiben();
			for(int n=0;n<5;n++) {
				Statistikecke.getKartsp(i,n).repaint();
				Spielkartenecke.getHandkarte(n).repaint();
			}
		}
	}
	
	private void gastkartenmischen() {
		for(int l=0;l<2;l++){
	    	for(Land land : Land.values()) {
	            int anzahl = 2;
	            if(land == Land.JOKER) {
	               anzahl = 1;
	            }
	            for(int g=0;g<anzahl;g++) {
	                for(Geschlecht geschlecht : Geschlecht.values()) {
	                    Variablen.getGastkarten().add(new Gastkarte(land, geschlecht));
	                }    
	            }
	        }
	    }
		Collections.shuffle(Variablen.getGastkarten());
		
		for(int p=0;p<5;p++) {
			Variablen.getSpieler(0).getHandkarten().add(Variablen.getGastkarten().get(0));
			Variablen.getSpieler(1).getHandkarten().add(Variablen.getGastkarten().get(1));
			Variablen.getGastkarten().remove(0);
			Variablen.getGastkarten().remove(0);
	    }
	}
	
	private void laenderkartenmischen() {
		for(int l=0;l<2;l++) {
			 for(Land land : Land.values()) {
				 if(land != Land.JOKER) {
					 Variablen.getLaenderkarten().add(new Laenderkarte(land));
				 }
			 }
		}
		Collections.shuffle(Variablen.getLaenderkarten());
	}

}