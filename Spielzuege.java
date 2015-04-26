package cafeint;

import java.awt.Color;

import javax.swing.BorderFactory;

public class Spielzuege {
	
	public void legegastkarte(int handkartennum,int stuhlNr) {
		if(Variablenkammer.getStuehle().get(stuhlNr).setGast(Variablenkammer.getSpieler(42).getHandkarten().get(handkartennum)) == true) {
			Variablenkammer.getSpieler(42).getHandkarten().set(handkartennum,null);
			Statistikecke.getKartsp(Variablenkammer.getAktSpieler(), handkartennum).repaint();
		}
		handkartendemarkieren();
		//Punktzahl bestimmen
	}
	
	public void legetischkarte(int tischnr) {
		Variablenkammer.getTische().get(tischnr).setLand(Variablenkammer.getLaenderkarten().get(0));
		Variablenkammer.getLaenderkarten().remove(0);
		//Laenderkartenüberprüfung bei Spielende einrichten
	}
	
	public void legebarkarte(int handkartennum) {
		int barnum = Variablenkammer.getBarkarten().size();
		Variablenkammer.getBarkarten().add(Variablenkammer.getSpieler(42).getHandkarten().get(handkartennum));
		Barkartenecke.getBarzelle(barnum).setGast(Variablenkammer.getSpieler(42).getHandkarten().get(handkartennum));
		Variablenkammer.getSpieler(42).getHandkarten().set(handkartennum,null);
		Statistikecke.getKartsp(Variablenkammer.getAktSpieler(),handkartennum).repaint();
		punktzahl(Barkartenecke.getBarzelle(barnum).getPunkte());
		handkartendemarkieren();
		Barkartenecke.getBarzelle(barnum).setBorder(BorderFactory.createLineBorder(Color.red));
		Thread thread = new Thread(new Runnable() {
			  @Override
			  public void run() {
				  try {
					  Thread.sleep(1500);
					  Barkartenecke.getBarzelle(barnum).setBorder(BorderFactory.createLineBorder(Color.black));
					  } catch(InterruptedException e) {}
				  }
			  }
		);
		thread.start();
		Variablenkammer.setZustand(21);
	}
	
	public void gastkarteziehen(int handkartennum) {
		Variablenkammer.getSpieler(42).getHandkarten().set(handkartennum,Variablenkammer.getGastkarten().get(0));
		Variablenkammer.getGastkarten().remove(0);
		
		Statistikecke.getKartsp(Variablenkammer.getAktSpieler(),handkartennum).repaint();
		Statistikecke.getKartsp(Variablenkammer.getAktSpieler(),handkartennum).setBorder(BorderFactory.createLineBorder(Color.red, 2));
		
		Thread thread = new Thread(new Runnable() {
			  @Override
			  public void run() {
				  try {
					  Thread.sleep(1500);
					  Statistikecke.getKartsp(0, handkartennum).setBorder(BorderFactory.createLineBorder(Color.black, 2));
					  Statistikecke.getKartsp(1, handkartennum).setBorder(BorderFactory.createLineBorder(Color.black, 2));
					  } catch(InterruptedException e) {}
				  }
			  }
		);
		thread.start();
		handkartendemarkieren();
		Spielkartenecke.gastkstzahlLaden();
		if(!new Spielende().spielvorbei()) {
			Variablenkammer.setZustand(12);
		}
	}
	
	public void punktzahl(int addition) {
		int neupktz = Variablenkammer.getSpieler(42).getPunkte()+addition;
		Variablenkammer.getSpieler(42).setPunkte(neupktz);
		Statistikecke.getInfz(Variablenkammer.getAktSpieler()).punktzahlschreiben();
	}
	
	public void spielerwechsel() {
		if(Variablenkammer.getAktSpieler() == 0) {
			Variablenkammer.setAktSpieler(1);
		} else {
			Variablenkammer.setAktSpieler(0);
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
		for(Tisch tisch:Variablenkammer.getTische()) {
			tisch.getSpielzelle().setBorder(BorderFactory.createLineBorder(Spielfeld.getHintgrdfarb(), 3));
		}
	}
	
	public void stuehledemarkieren() {
		for(Stuhl stuhl:Variablenkammer.getStuehle()) {
			stuhl.getSpielzelle().setBorder(BorderFactory.createLineBorder(Spielfeld.getHintgrdfarb(), 3));
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
					  warnungsboxreseten();
					  } catch(InterruptedException e) {}
				  }
			  }
		);
		thread.start();
	}
	
	public void warnungsboxreseten() {
		Spielfeld.getWarnungsbox().setText("");
		Spielfeld.getWarnungsbox().setBorder(BorderFactory.createLineBorder(Spielfeld.getHintgrdfarb(), 2));
	}

}