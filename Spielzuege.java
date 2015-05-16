package cafeint;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;

/**
 * Diese Klasse enthaelt alle moeglichen Spielzuege, die im Verlauf des Spiels intern auftreten koennen.<br>
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */

public class Spielzuege {
	
	/**
	 * Diese Methode legt eine Gastkarte auf einen Stuhl.
	 * @param handkartennum Nimmt die Nummer der Handkarte entgegen, die gelegt wird.
	 * @param stuhlNr Nimmt die Nummer des Stuhls entgegen, auf dem der Gast Platz nimmt.
	 */
	public void legegastkarte(int handkartennum,int stuhlNr) {
		if(Variablen.getStuehle().get(stuhlNr).setGast(Variablen.getSpieler(42).getHandkarten().get(handkartennum)) == true) {
			Variablen.getSpieler(42).getHandkarten().set(handkartennum,null);
			Statistikecke.getKartsp(Variablen.getAktSpieler(), handkartennum).repaint();
		}
		handkartendemarkieren();
	}
	
	/**
	 * Diese Methode legt eine Laenderkarte auf einen Tisch.
	 * @param tisch Nimmt die Nummer des Tisches entgegen, dem ein neues Land zugeordnet wird.
	 */
	public void legetischkarte(Tisch tisch) {
		tisch.setLand(Variablen.getLaenderkarten().get(0));
		Variablen.getLaenderkarten().remove(0);
		Spielkartenecke.landkstzahlLaden();
	}
	
	/**
	 * Diese Methode legt eine nutzlose Gaestekarte in der Bar ab.
	 * @param handkartennum Nimmt die Nummer der Handkarte entgegen, die gelegt wird.
	 */
	public void legebarkarte(int handkartennum) {
		final int barnum = Variablen.getBarkarten().size();
		Variablen.getBarkarten().add(Variablen.getSpieler(42).getHandkarten().get(handkartennum));
		Barkartenecke.getBarzelle(barnum).setGast(Variablen.getSpieler(42).getHandkarten().get(handkartennum));
		Variablen.getSpieler(42).getHandkarten().set(handkartennum,null);
		Statistikecke.getKartsp(Variablen.getAktSpieler(),handkartennum).repaint();
		punktzahl(Barkartenecke.getBarzelle(barnum).getPunkte());
		handkartendemarkieren();
		Barkartenecke.getBarzelle(barnum).setBorder(BorderFactory.createLineBorder(Color.red));
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1500);
				} catch(InterruptedException e) {}
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						Barkartenecke.getBarzelle(barnum).setBorder(BorderFactory.createLineBorder(Color.black));
					}
				});
			}
		});
		thread.setDaemon(true);
		thread.start();
		if(!new Spielende().barvoll()) {
			Variablen.setZustand(21);
		}
	}
	
	/**
	 * Diese Methode wird aufgerufen, wenn ein Spieler neue Gastkarten vom Stapel zieht.
	 * @param handkartennum Nimmt die Handkartennummer entgegen, welcher die neu gezogene Karte zugeordnet wird.
	 */
	public void gastkarteziehen(final int handkartennum) {
		Variablen.getSpieler(42).getHandkarten().set(handkartennum,Variablen.getGastkarten().get(0));
		Variablen.getGastkarten().remove(0);
		
		Statistikecke.getKartsp(Variablen.getAktSpieler(),handkartennum).repaint();
		Statistikecke.getKartsp(Variablen.getAktSpieler(),handkartennum).setBorder(BorderFactory.createLineBorder(Color.red, 2));
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1500);
				} catch(InterruptedException e) {}
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						Statistikecke.getKartsp(0, handkartennum).setBorder(BorderFactory.createLineBorder(Color.black, 2));
						Statistikecke.getKartsp(1, handkartennum).setBorder(BorderFactory.createLineBorder(Color.black, 2));
					}
				});
			}
		});
		thread.setDaemon(true);
		thread.start();
		
		handkartendemarkieren();
		Spielkartenecke.gastkstzahlLaden();
		Variablen.setZustand(12);
	}
	
	/**
	 * Diese Methode fuegt dem Spieler der am Zug ist eine Punktzahl zum Punktestand hinzu.
	 * @param addition Dies ist die Anzahl der Punkte, die addiert werden.
	 */
	public void punktzahl(int addition) {
		int neupktz = Variablen.getSpieler(42).getPunkte()+addition;
		Variablen.getSpieler(42).setPunkte(neupktz);
		Statistikecke.getInfz(Variablen.getAktSpieler()).punktzahlschreiben();
	}
	
	/**
	 * Diese Methode wechselt den Spieler, der am Zug ist und markiert dies farblich.
	 */
	public void spielerwechsel() {
		if(Variablen.getAktSpieler() == 0) {
			Variablen.setAktSpieler(1);
			Statistikecke.getInfz(1).faerben(true);
			Statistikecke.getInfz(0).faerben(false);
		} else {
			Variablen.setAktSpieler(0);
			Statistikecke.getInfz(0).faerben(true);
			Statistikecke.getInfz(1).faerben(false);
		}
		handkartendemarkieren();
	}
	
	/**
	 * Diese Methode demarkiert alle Handkarten, die gerade durch einen roten Rand hervorgehoben sind.
	 */
	public void handkartendemarkieren() {
		Spielkartenecke.setAkthandkartnum(-1);
		for(int i=0;i<5;i++) {
			Spielkartenecke.getHandkarte(i).setBorder(BorderFactory.createLineBorder(Color.black, 2));
			Spielkartenecke.getHandkarte(i).setGeklickt(false);
			Spielkartenecke.getHandkarte(i).repaint();
		}
	}
	
	/**
	 * Diese Methode demarkiert alle Tische, die gerade wegen ungueltiger Zuege rot aufleuchten.
	 */
	public void tischedemarkieren() {
		for(Tisch tisch:Variablen.getTische()) {
			tisch.getSpielzelle().setBorder(BorderFactory.createLineBorder(Spielfeld.getHintgrdfarb(), 3));
		}
	}
	
	/**
	 * Diese Methode demarkiert alle Stuehle, die gerade wegen ungueltiger Zuege rot aufleuchten.
	 * @param zugende Abhaengig davon, ob der Zug vorbei ist oder nicht kann ein Stuhl auch Gruen gefaerbt werden, um aufzuzeigen, dass ein Partner benoetigt wird.
	 */
	public void stuehledemarkieren(boolean zugende) {
		for(Stuhl stuhl:Variablen.getStuehle()) {
			if(!stuhl.isPartnerNoetig() || zugende) {
				stuhl.getSpielzelle().setBorder(BorderFactory.createLineBorder(Spielfeld.getHintgrdfarb(), 3));
				stuhl.setPartnerNoetig(false);
			} else {
				stuhl.gruenfaerben();
			}
			
		}
	}
	
	/**
	 * Diese Methode aendert den Text, der in der Warnbox unten angezeigt wird.<br>
	 * Er wird nach 5 Sekunden automatisch wieder entfernt.
	 * @param text Hier wird der anzuzeigende Text eingetragen.
	 */
	public void warnungsboxtext(String text) {
		Spielfeld.getWarnungsbox().setText(text);
		Spielfeld.getWarnungsbox().setBorder(BorderFactory.createLineBorder(Color.red, 2));
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch(InterruptedException e) {}
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						warnungsboxreseten();
					}
				});
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
	
	/**
	 * Diese Methode loescht saemtliche Inhalte der Warnungsbox sofort.
	 */
	public void warnungsboxreseten() {
		Spielfeld.getWarnungsbox().setText("");
		Spielfeld.getWarnungsbox().setBorder(BorderFactory.createLineBorder(Spielfeld.getHintgrdfarb(), 2));
	}

}