package cafeint;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;

public class Spielzuege {
	
	public void legegastkarte(int handkartennum,int stuhlNr) {
		if(Variablen.getStuehle().get(stuhlNr).setGast(Variablen.getSpieler(42).getHandkarten().get(handkartennum)) == true) {
			Variablen.getSpieler(42).getHandkarten().set(handkartennum,null);
			Statistikecke.getKartsp(Variablen.getAktSpieler(), handkartennum).repaint();
		}
		handkartendemarkieren();
		//Punktzahl bestimmen
	}
	
	public void legetischkarte(Tisch tisch) {
		tisch.setLand(Variablen.getLaenderkarten().get(0));
		Variablen.getLaenderkarten().remove(0);
		Spielkartenecke.landkstzahlLaden();
		//Laenderkartenüberprüfung bei Spielende einrichten
	}
	
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
	
	public void punktzahl(int addition) {
		int neupktz = Variablen.getSpieler(42).getPunkte()+addition;
		Variablen.getSpieler(42).setPunkte(neupktz);
		Statistikecke.getInfz(Variablen.getAktSpieler()).punktzahlschreiben();
	}
	
	public void spielerwechsel() {
		if(Variablen.getAktSpieler() == 0) {
			Variablen.setAktSpieler(1);
		} else {
			Variablen.setAktSpieler(0);
		}
		handkartendemarkieren();
	}
	
	public void handkartendemarkieren() {
		Spielkartenecke.setAkthandkartnum(-1);
		for(int i=0;i<5;i++) {
			Spielkartenecke.getHandkarte(i).setBorder(BorderFactory.createLineBorder(Color.black, 2));
			Spielkartenecke.getHandkarte(i).setGeklickt(false);
			Spielkartenecke.getHandkarte(i).repaint();
		}
	}
	
	public void tischedemarkieren() {
		for(Tisch tisch:Variablen.getTische()) {
			tisch.getSpielzelle().setBorder(BorderFactory.createLineBorder(Spielfeld.getHintgrdfarb(), 3));
		}
	}
	
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
	
	public void warnungsboxreseten() {
		Spielfeld.getWarnungsbox().setText("");
		Spielfeld.getWarnungsbox().setBorder(BorderFactory.createLineBorder(Spielfeld.getHintgrdfarb(), 2));
	}

}