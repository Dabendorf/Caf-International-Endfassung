package cafeint;

import java.util.Collections;

import cafeint.Gastkarte.Geschlecht;
import cafeint.Gastkarte.Land;

/**
 * Die Klasse Spielstart ist eine von zwei Klassen, die bei Aufruf des Spiels relevant sind.
 * Sie laedt saemtliche Elemente des Spielalgorithmus, die für einen reibungslosen Ablauf relevant sind.<br>
 * Sie muss bei Start eines jeden neuen Spiels neu geladen werden.
 * 
 * @author Lukas Schramm
 * @version 1.0
 * 
 */

public class Spielstart {
	
	/**
	 * Diese Methode laedt ein neues Spiel. Sie loescht saemtliche Rueckstaende vergangener Partien und ruft alle Methoden einer neuen Spielgenerierung auf.
	 */
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
		for(Tisch tisch:Variablen.getTische()) {
			new Spielzuege().legetischkarte(tisch);
		}
		for(Stuhl stuhl:Variablen.getStuehle()) {
			stuhl.gastNachHause();
		}
		
		Variablen.setAktSpieler(0);
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
		Statistikecke.getInfz(0).faerben(true);
		Statistikecke.getInfz(1).faerben(false);
	}
	
	/**
	 * Diese Methode generiert die 100 Gastkarten, mischt diese durch und gibt jedem Spieler zum Start fuenf Handkarten.
	 */
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
	
	/**
	 * Diese Methode generiert die 24 Tischkarten und mischt sie.
	 */
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