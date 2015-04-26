package cafeint;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;

import cafeint.Gastkarte.Geschlecht;
import cafeint.Gastkarte.Land;

public class Stuhl {
	
	private Gastkarte gast;
	private ArrayList<Tisch> tische = new ArrayList<Tisch>();
    private Spielzelle sz;
    
	public Gastkarte getGast() {
		return gast;
	}
	
	public boolean setGast(Gastkarte gasttemp) {
		Meldungen msgbox = new Meldungen();
		if(!gastLandKorrekt(gasttemp)) {
			new Spielzuege().warnungsboxtext(msgbox.gastlandfalsch);
			return false;
		} else if(!gastGeschlechtKorrekt(gasttemp)) {
			if(gasttemp.getGeschlecht().equals(Geschlecht.Mann)) {
				new Spielzuege().warnungsboxtext(msgbox.gastzuvielemaenner);
			} else {
				new Spielzuege().warnungsboxtext(msgbox.gastzuvielefrauen);
			}
			return false;
		} else if(!gastPartnerKorrekt(gasttemp)) {
			new Spielzuege().warnungsboxtext(msgbox.gastpartnerfalsch);
			return false;
		} else {
			if(Variablenkammer.getZustand() == 12) {
				this.gast = gasttemp;
				this.sz.repaint();
				Variablenkammer.setZustand(11);
				new Spielzuege().tischedemarkieren();
				new Spielzuege().warnungsboxreseten();
				return true;
			} else if(Variablenkammer.getZustand() == 11) {
				this.gast = gasttemp;
				this.sz.repaint();
				Variablenkammer.setZustand(21);
				new Spielzuege().tischedemarkieren();
				new Spielzuege().warnungsboxreseten();
				return true;
			} else {
				return false;
			}
		}
	}
	
	/*public Tisch[] getTische() {
		return tische;
	}*/
	
	public void addTisch(Tisch tisch) {
		this.tische.add(tisch);
	}
	
	public Spielzelle getSpielzelle() {
		return sz;
	}
	
	public void setSpielzelle(Spielzelle sz) {
		this.sz = sz;
	}
	
	private boolean gastLandKorrekt(Gastkarte gasttemp) {
		boolean korr = false;
		for(Tisch tisch:this.tische) {
			if(tisch.getLaenderkarte().getLand().equals(gasttemp.getLand()) || gasttemp.getLand().equals(Land.JOKER)) {
				korr = true;
			}
		}
		if(korr == false) {
			for(Tisch tisch:this.tische) {
				tisch.getSpielzelle().setBorder(BorderFactory.createLineBorder(Color.red, 3));
				Thread thread = new Thread(new Runnable() {
					  @Override
					  public void run() {
						  try {
							  Thread.sleep(5000);
							  tisch.getSpielzelle().setBorder(BorderFactory.createLineBorder(Spielfeld.getHintgrdfarb(), 3));
							  } catch(InterruptedException e) {}
						  }
					  }
				);
				thread.start();
			}
		}
		return korr;
	}
	
	private boolean gastGeschlechtKorrekt(Gastkarte gasttemp) {
		boolean korr = true;
		for(Tisch tisch:this.tische) {
			int mann=0, frau=0;
			for(Stuhl stuhl:tisch.getStuehle()) {
				if(stuhl.getGast()!=null) {
					if(stuhl.getGast().getGeschlecht().equals(Geschlecht.Mann)) {
						mann++;
					} else {
						frau++;
					}
				}
			}
			if((gasttemp.getGeschlecht().equals(Geschlecht.Mann)) && (mann > frau) || (gasttemp.getGeschlecht().equals(Geschlecht.Frau)) && (frau > mann)) {
				korr = false;
				for(Stuhl stuhl:tisch.getStuehle()) {
					if(stuhl.getGast()!=null) {
						stuhl.getSpielzelle().setBorder(BorderFactory.createLineBorder(Color.red, 3));
						Thread thread = new Thread(new Runnable() {
							  @Override
							  public void run() {
								  try {
									  Thread.sleep(5000);
									  stuhl.getSpielzelle().setBorder(BorderFactory.createLineBorder(Spielfeld.getHintgrdfarb(), 3));
									  } catch(InterruptedException e) {}
								  }
							  }
						);
						thread.start();
					}
				}
			}
		}
		return korr;
	}
	
	private boolean gastPartnerKorrekt(Gastkarte gasttemp) {
		return true; //Vorerst immer true
		//Beachte auch, dass der Spieler im Ersten Zug auch Einzelkarten legen darf
	}

}