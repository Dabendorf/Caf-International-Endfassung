package cafeint;

import java.awt.Color;

import javax.swing.BorderFactory;

public class Spielzuege {
	
	public void legetischkarte(int tischnr) {
		Variablenkammer.getTische().get(tischnr).setLand(Variablenkammer.getLaenderkarten().get(0));
		Variablenkammer.getLaenderkarten().remove(0);
	}
	
	public void legebarkarte(int handkartennum) {
		int barnum = Variablenkammer.getBarkarten().size();
		Variablenkammer.getBarkarten().add(Variablenkammer.getSpieler(42).getHandkarten().get(handkartennum));
		Barkartenecke.getBarzellen(barnum).setGast(Variablenkammer.getSpieler(42).getHandkarten().get(handkartennum));
		Variablenkammer.getSpieler(42).getHandkarten().set(handkartennum,null);
		Statistikecke.getKartsp(Variablenkammer.getAktSpieler(),handkartennum).repaint();
		punktzahl(Barkartenecke.getBarzellen(barnum).getPunkte());
		handkartendemarkieren();
		Barkartenecke.getBarzellen(barnum).setBorder(BorderFactory.createLineBorder(Color.red));
		Thread thread = new Thread(new Runnable() {
			  @Override
			  public void run() {
				  try {
					  Thread.sleep(1500);
					  Barkartenecke.getBarzellen(barnum).setBorder(BorderFactory.createLineBorder(Color.black));
					  } catch(InterruptedException e) {}
				  }
			  }
		);
		thread.start();
		//Variablenkammer.setZustand(21);
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

}