package cafeint;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cafeint.Kartenstapel.Typ;

/**
 * Diese Klasse ist eine von 5 Klassen, aus denen sich die graphische Oberflaeche zusammensetzt.<br>
 * Es stellt die Spielflaeche oben rechts dar, in welcher die Handkarten des aktuellen Spielers zum Legen angezeigt werden.<br>
 * Ausserdem kann man hier Gast- und Laenderkarten nachziehen.<br>
 * <br>
 * <b>handkarten</b> Stellt die 5 Handkarten des aktuellen Spielers in einem Array dar.<br>
 * <b>gastkst</b> Das ist das Element, in welchem man Gastkarten nachziehen kann.<br>
 * <b>landkst</b> Das ist das Element, in welchem man Laenderkarten nachziehen kann.<br>
 * <b>akthandkartnum</b> Gibt die Nummer der aktuell angeklickten Handkarte an.<br>
 * <b>hintgrdfarb</b> Das ist die Hintergrundfarbe, in welcher die Felder dargestellt werden.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Spielkartenecke extends JPanel {
	
	private Kartenstapel handkarten[] = new Kartenstapel[5];
	private Kartenstapel gastkst = new Kartenstapel(Typ.Gastkartenstapel);
	private Kartenstapel landkst = new Kartenstapel(Typ.Laenderkartenstapel);
	private int akthandkartnum = -1;
	private Color hintgrdfarb = new Color(0x000000);
	
	public Spielkartenecke() {
		this.setBackground(hintgrdfarb);
		layoutgenerieren();
	}
	
	/**
	 * Diese Methode erstellt das GridLayout, in welchem die Spielelemente dargestellt werden.<br>
	 * Ausserdem wird hier jedem Element ein MouseListener zugeordnet, nach welchem es zu reagieren hat.
	 */
	private void layoutgenerieren() {
		setLayout(new GridLayout(5,2));
		for(int i=0;i<10;i++) {
			if(i%2==0) {
				handkarten[i/2] = new Kartenstapel(Typ.Handkarte);
				handkarten[i/2].setOpaque(true);
				handkarten[i/2].setHandkartnum(i/2);
				handkarten[i/2].setBorder(BorderFactory.createLineBorder(Color.black, 2));
				final int index = i/2;
				handkarten[i/2].addMouseListener(new MouseAdapter() {
	            	@Override
	            	public void mouseClicked(MouseEvent e) {
	            		Spielzuege spz = new Spielzuege();
	            		if((Variablen.getZustand()==10 || Variablen.getZustand()==11 || Variablen.getZustand()==12) && handkarten[index].getImage()!=null) {
	            			klickhand(index);
	            		} else if(Variablen.getZustand() >=220) {
	            			spz.warnungsboxtext(new Sprache().tischkarteziehen);
	            			spz.handkartendemarkieren();
            				spz.stuehledemarkieren(false);
            				spz.tischedemarkieren();
	            		} else if(handkarten[index].getImage()!=null) {
	            			spz.warnungsboxtext(new Sprache().gastkarteziehen);
	            			spz.handkartendemarkieren();
            				spz.stuehledemarkieren(false);
            				spz.tischedemarkieren();
	            		}
	            	}
	            });
				add(handkarten[i/2]);
			} else if(i==3) {
				gastkst = new Kartenstapel(Typ.Gastkartenstapel);
				gastkst.setOpaque(true);
				gastkst.setBorder(BorderFactory.createLineBorder(Color.black, 2));
				gastkst.addMouseListener(new MouseAdapter() {
	            	@Override
	            	public void mouseClicked(MouseEvent e) {
	            		Spielzuege spz = new Spielzuege();
	            		if(Variablen.getZustand()==21 || Variablen.getZustand()==11) {
	            			klickgast();
	            			spz.warnungsboxreseten();
	    				} else if(Variablen.getZustand() >= 220) {
	    					new Spielzuege().warnungsboxtext(new Sprache().tischkarteziehen);
	    					spz.handkartendemarkieren();
            				spz.stuehledemarkieren(false);
            				spz.tischedemarkieren();
	    				} else {
	    					spz.warnungsboxtext(new Sprache().gastkartelegen);
	    					spz.handkartendemarkieren();
            				spz.stuehledemarkieren(false);
            				spz.tischedemarkieren();
	    				}
	            	}
	            });
				add(gastkst);
			} else if(i==7) {
				landkst = new Kartenstapel(Typ.Laenderkartenstapel);
				landkst.setOpaque(true);
				landkst.setBorder(BorderFactory.createLineBorder(Color.black, 2));
				landkst.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						Spielzuege spz = new Spielzuege();
						if(Variablen.getZustand() >= 220) {
							klicktisch();
							spz.warnungsboxreseten();
							spz.handkartendemarkieren();
							spz.tischedemarkieren();
						} else if(Variablen.getZustand() > 9 && Variablen.getZustand() < 13) {
							spz.warnungsboxtext(new Sprache().gastkartelegen);
							spz.handkartendemarkieren();
            				spz.stuehledemarkieren(false);
            				spz.tischedemarkieren();
						}
					}
				});
				add(landkst);
			} else {
				Kartenstapel kst = new Kartenstapel(Typ.Leer);
				kst.setOpaque(true);
				kst.setBorder(BorderFactory.createLineBorder(Color.black, 2));
				add(kst);
			}
		}
	}
	
	/**
	 * Diese Methode wird ausgefuehrt, wenn eine Handkarte angeklickt wird.
	 * Sie markiert und demarkiert sich und andere Handkarten mit einem roten Rand,
	 * um dem Spieler zu zeigen, welche Karte er gerade ausgewaehlt hat.
	 * @param num Dieser Parameter wird uebergeben, um die Nummer der Handkarte (0 bis 4) zu uebergeben.
	 */
	private void klickhand(int num) {
		if(handkarten[num].isGeklickt()) {
			handkarten[num].setBorder(BorderFactory.createLineBorder(Color.black, 2));
			handkarten[num].setGeklickt(false);
			akthandkartnum = -1;
		} else {
			handkarten[num].setBorder(BorderFactory.createLineBorder(Color.red, 2));
			handkarten[num].setGeklickt(true);
			akthandkartnum = num;
			for(int i=0;i<5;i++) {
				if(i!=num) {
					handkarten[i].setBorder(BorderFactory.createLineBorder(Color.black, 2));
					handkarten[i].setGeklickt(false);
				}
			}
		}
	}
	
	/**
	 * Diese Methode wird aufgerufen, wenn neue Gastkarten gezogen werden muessen.<br>
	 * Sollte hierbei der Gastkartenvorrat aufgebraucht werden, springt der boolean rundenwechsel auf false und das Spiel wird beendet.
	 */
	private void klickgast() {
		Spielzuege spz = new Spielzuege();
		boolean rundenwechsel = true;
		for(int i=0;i<5;i++) {
			if(handkarten[i].getImage()==null && Variablen.getGastkarten().size() > 0) {
				spz.gastkarteziehen(i,Variablen.getAktSpieler());
				if(Variablen.getGastkarten().size() == 0) {
					rundenwechsel = false;
					new Spielende().keinegastkarten();
					break;
				}
			}
		}
		if(rundenwechsel) {
			spz.spielerwechsel();
		}
		spz.stuehledemarkieren(false);
		spz.tischedemarkieren();
	}
	
	/**
	 * Diese Methode wird aufgerufen, wenn neue Laenderkarten gezogen werden muessen.<br>
	 * Sollte hierbei der Laenderkartenvorrat aufgebraucht werden, springt der boolean spielende auf true und das Spiel wird beendet.<br>
	 * Ausserdem wird hier die Variable zustand genauestens berechnet, um beim Ziehen neuer Tischkarten nicht den Spielablauf durcheinander zu bringen.
	 */
	private void klicktisch() {
		boolean spielende = false;
		for(Tisch tisch:Variablen.getTische()) {
			if(tisch.getLaenderkarte()==null) {
				if(Variablen.getLaenderkarten().size() == 0) {
					spielende = true;
					new Spielende().keinelaenderkarten();
					break;
				} else {
					new Spielzuege().legetischkarte(tisch);
				}
			}
		}
		if(!spielende) {
			int zustand = Variablen.getZustand();
			Variablen.setZustand(zustand-210);
			int anzahl = 0;
			for(Gastkarte gstk:Variablen.getSpieler(42).getHandkarten()) {
				if(gstk == null) {
					anzahl++;
				}
			}
			if(anzahl>1) {
				Variablen.setZustand(21);
			}
		}
	}
	
	/**
	 * Laedt die Anzahl der Gastkarten im Gastkartenstapel neu.
	 */
	public void gastkstzahlLaden() {
		gastkst.repaint();
	}
	
	/**
	 * Laedt die Anzahl der Laenderkarten im Gastkartenstapel neu.
	 */
	public void landkstzahlLaden() {
		landkst.repaint();
	}
	
	public Kartenstapel getHandkarte(int n) {
		return handkarten[n];
	}

	public int getAkthandkartnum() {
		return akthandkartnum;
	}

	public void setAkthandkartnum(int akthandkartnum) {
		this.akthandkartnum = akthandkartnum;
	}

}