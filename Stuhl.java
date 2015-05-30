package cafeint;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;

import cafeint.Gastkarte.Geschlecht;
import cafeint.Gastkarte.Land;

/**
 * Diese Klasse stellt die Stuehle, neben den Tischen das wichtigste Element des Spiels dar.<br>
 * Jeder Stuhl grenzt an 1 bis 3 Tische.
 * Jedem Stuhl wird ein Gast nach speziellen Eigenschaften zugeordnet.<br>
 * <br>
 * <b>gast</b> Dies ist der Gast, der auf dem Stuhl sitzt.<br>
 * <b>tische</b> Dies sind die Tische, an welchen der Stuhl steht.<br>
 * <b>sz</b> Das ist die graphische Spielzelle zum Stuhl.<br>
 * <b>partnerNoetig</b> Dieser boolean zeigt an, ob ein Gast auf diesem Stuhl allein sitzt und einen Sitzpartner benoetigt.<br>
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */

public class Stuhl {
	
	private Gastkarte gast;
	private ArrayList<Tisch> tische = new ArrayList<Tisch>();
    private Spielzelle sz;
    private boolean partnerNoetig = false;
    
	public Gastkarte getGast() {
		return gast;
	}
	
	/**
	 * Diese Methode legt den Gast bei Spielstart fest, der aufgrund eines wiederhergestellten Spiels wieder hier platziert wird.
	 * @param gasttemp Hier wird der anzuzeigende Gast eingetragen.
	 */
	public void setStartGast(Gastkarte gasttemp) {
		this.gast = gasttemp;
		this.sz.repaint();
	}
	
	/**
	 * In diesem Schleifenbereich landet man nur, wenn ein neues Spiel gestartet wird.<br>
	 * Es dient der Ueberpruefung, ob der Startspieler Handkarten legen kann.<br>
	 * Sollte er dies nicht koennen, werden die Karten neu gemischt, bis er das kann.<br>
	 * Dies verhindert, dass der Spieler im ersten Zug aus ungluecklicher Kartenmischung an die Bar abwerfen muss.<br>
	 * @param gasttemp Hier wird der Gast eingetragen, der sich auf den Stuhl setzen moechte.
	 * @return Gibt einen Boolean zurueck, ob der Gast sich auf diesen Stuhl setzen koennte.
	 */
	public boolean setGastMischTest(Gastkarte gasttemp) {
		if(gastLandKorrekt(gasttemp) && gastGeschlechtKorrekt(gasttemp) && gastPartnerKorrekt(gasttemp)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Diese Methode steuert die gesamte Spielalgorithmik, nach welcher ein Gast am Tisch Platz nehmen darf.
	 * @param gasttemp Hier wird der Gast eingetragen, der sich auf den Stuhl setzen moechte.
	 * @return Gibt einen Boolean zurueck, ob ein Gast sich setzen durfte oder nicht.
	 */
	public boolean setGast(Gastkarte gasttemp) {
		Meldungen msgbox = new Meldungen();
		Spielzuege spz = new Spielzuege();
		if(!gastLandKorrekt(gasttemp)) {
			spz.warnungsboxtext(msgbox.gastlandfalsch);
			partnerNoetig = false;
			spz.stuehledemarkieren(false);
			return false;
		} else if(!gastGeschlechtKorrekt(gasttemp)) {
			if(gasttemp.getGeschlecht().equals(Geschlecht.Mann)) {
				spz.warnungsboxtext(msgbox.gastzuvielemaenner);
			} else {
				spz.warnungsboxtext(msgbox.gastzuvielefrauen);
			}
			partnerNoetig = false;
			spz.stuehledemarkieren(false);
			return false;
		} else if(!gastPartnerKorrekt(gasttemp)) {
			spz.warnungsboxtext(msgbox.gastpartnerfalsch);
			return false;
		} else {
			if(Variablen.getZustand() == 12) {
				this.gast = gasttemp;
				this.sz.repaint();
				spz.tischedemarkieren();
				spz.warnungsboxreseten();
				if(partnerNoetig) {
					Variablen.setZustand(10);
					spz.stuehledemarkieren(false);
					gruenfaerben();
				} else {
					Variablen.setZustand(11);
					spz.stuehledemarkieren(true);
				}
				punkteBerechnen(gasttemp);
				tischVollPruefen(false);
				return true;
			} else if(Variablen.getZustand() == 10 || Variablen.getZustand() == 11) {
				this.gast = gasttemp;
				this.sz.repaint();
				spz.tischedemarkieren();
				spz.warnungsboxreseten();
				spz.stuehledemarkieren(true);
				punkteBerechnen(gasttemp);
				tischVollPruefen(true);
				return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * Diese Methode leert die Variable gast und schickt den Gast nach Hause.
	 */
	public void gastNachHause() {
		this.gast = null;
		this.sz.repaint();
	}
	
	public ArrayList<Tisch> getTische() {
		return tische;
	}
	
	public void addTisch(Tisch tisch) {
		this.tische.add(tisch);
	}
	
	public Spielzelle getSpielzelle() {
		return sz;
	}
	
	public void setSpielzelle(Spielzelle sz) {
		this.sz = sz;
	}

	public boolean isPartnerNoetig() {
		return partnerNoetig;
	}

	public void setPartnerNoetig(boolean partnerNoetig) {
		this.partnerNoetig = partnerNoetig;
	}
	
	/**
	 * Diese Methode faerbt den Rand der Spielzelle des Stuhls gruen, um darauf aufmerkam zu machen, dass der naechste Spielzug verlangt, diesem Gast einen Partner hinzusetzen.
	 */
	public void gruenfaerben() {
		this.getSpielzelle().setBorder(BorderFactory.createLineBorder(new Color(0x3ADF00), 3));
	}
	
	/**
	 * Diese Methode ueberprueft, ob ein Gast sich auf einen Stuhl setzt, der an mindestens einem Tisch seiner Nationalitaet entspricht.<br>
	 * Joker koennen alle Nationalitaeten besitzen.
	 * @param gasttemp Hier wird der Gast eingetragen, der sich auf den Stuhl setzen moechte.
	 * @return Gibt einen boolean zurueck, ob die Bedingung erfuellt ist.
	 */
	private boolean gastLandKorrekt(Gastkarte gasttemp) {
		new Spielzuege().tischedemarkieren();
		boolean korr = false;
		for(Tisch tisch:this.tische) {
			if(tisch.getLaenderkarte().getLand().equals(gasttemp.getLand()) || gasttemp.getLand().equals(Land.JOKER)) {
				korr = true;
				break;
			}
		}
		int zustand = Variablen.getZustand();
		if(!(zustand == 0 || zustand == 31 || zustand == 32 || zustand == 33)) {
			if(korr == false) {
				for(final Tisch tisch:this.tische) {
					Thread thread = new Thread(new Runnable() {
				        public void run() {
				            try {
				            	SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										tisch.getSpielzelle().setBorder(BorderFactory.createLineBorder(Color.red, 3));
									}
								});
				            	Thread.sleep(5000);
				            	SwingUtilities.invokeLater(new Runnable() {
									@Override
									public void run() {
										tisch.getSpielzelle().setBorder(BorderFactory.createLineBorder(Variablen.getSpielfeld().getHintgrdfarb(), 3));
									}
								});
							} catch (Exception e) {
								e.printStackTrace();
							}
				        }
				    });
					thread.setDaemon(true);
					thread.start();
				}
			}
		}
		return korr;
	}
	
	/**
	 * Diese Methode ueberprueft, ob ein Gast, wenn er sich setzen wuerde an irgendeinem Tisch fuer geschlechtliche Ungleichheit sorgen wuerde.
	 * @param gasttemp Hier wird der Gast eingetragen, der sich auf den Stuhl setzen moechte.
	 * @return Gibt einen boolean zurueck, ob die Bedingung erfuellt ist.
	 */
	private boolean gastGeschlechtKorrekt(Gastkarte gasttemp) {
		new Spielzuege().tischedemarkieren();
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
				int zustand = Variablen.getZustand();
				if(!(zustand == 0 || zustand == 31 || zustand == 32 || zustand == 33)) {
					for(final Stuhl stuhl:tisch.getStuehle()) {
						if(stuhl.getGast()!=null && !stuhl.isPartnerNoetig()) {
							Thread thread = new Thread(new Runnable() {
						        public void run() {
						            try {
										SwingUtilities.invokeAndWait(new Runnable() {
										    public void run() {
										    	stuhl.getSpielzelle().setBorder(BorderFactory.createLineBorder(Color.red, 3));
										    }
										});
										Thread.sleep(5000);
										SwingUtilities.invokeAndWait(new Runnable() {
										    public void run() {
										    	stuhl.getSpielzelle().setBorder(BorderFactory.createLineBorder(Variablen.getSpielfeld().getHintgrdfarb(), 3));
										    }
										});
									} catch (Exception e) {
										e.printStackTrace();
									}
						        }
						    });
							thread.setDaemon(true);
							thread.start();
						}
					}
				}
			}
		}
		return korr;
	}
	
	/**
	 * Diese Methode ueberprueft, ob ein Gast alleine an einem Tisch sitzt und ein adaequater Partner im Handkartenvorrat existieren wuerde.<br>
	 * Sollte dies der Fall sein, wird die Zelle gruen gefaerbt und als naechster Spielzug verlangt, dem Gast einen Partner hinzusetzen.
	 * @param gasttemp Hier wird der Gast eingetragen, der sich auf den Stuhl setzen moechte.
	 * @return Gibt einen boolean zurueck, ob die Bedingung erfuellt ist.
	 */
	private boolean gastPartnerKorrekt(Gastkarte gasttemp) {
		boolean korr = false;
		int zustand = Variablen.getZustand();
		tischschleife:for(Tisch tisch:this.tische) {
			for(Stuhl stuhl:tisch.getStuehle()) {
				if(!stuhl.equals(this)) {
					if(stuhl.getGast()!=null) {
						partnerNoetig = false;
						korr = true;
						break tischschleife;
					} else if(zustand==12 || zustand == 0 || zustand == 31 || zustand == 32 || zustand == 33) {
						for(Gastkarte handtemp:Variablen.getSpieler(42).getHandkarten()) {
							if(handtemp!=null) {
								if(!handtemp.equals(gasttemp)) {
									if(tempLandKorrekt(handtemp,stuhl) == true && tempGeschlechtKorrekt(gasttemp,handtemp,stuhl)) {
										if(zustand == 12) {
											partnerNoetig = true;
										}
										korr = true;
									}
								}
							}
						}
					}
				}
			}
		}
		return korr;
	}
	
	/**
	 * Ueberprueft im Zuge der Methode gastPartnerKorrekt(), ob ein Partner aus dem Handkartenvorrat die Bedingung der Nationalitaet erfuellen wuerde.
	 * @param handtemp Dies ist eine Handkarte aus dem Handkartenvorrat, die ueberprueft wird.
	 * @param stuhltemp Dies ist ein Stuhl, der fuer diese Bedingung getestet wird.
	 * @return Gibt einen boolean zurueck, ob die Bedingung erfuellt ist.
	 */
	private boolean tempLandKorrekt(Gastkarte handtemp,Stuhl stuhltemp) {
		boolean korr = false;
		for(Tisch tisch:stuhltemp.getTische()) {
			if(tisch.getLaenderkarte().getLand().equals(handtemp.getLand()) || handtemp.getLand().equals(Land.JOKER)) {
				korr = true;
				break;
			}
		}
		return korr;
	}
	
	/**
	 * Ueberprueft im Zuge der Methode gastPartnerKorrekt(), ob ein Partner aus dem Handkartenvorrat mit der zu legenden Gastkarte die Bedingung des Geschlechtes erfuellen wuerde.
	 * @param gasttemp Dies ist die Gastkarte, um die es geht, die zu legen ist.
	 * @param @param handtemp Dies ist eine Handkarte aus dem Handkartenvorrat, die ueberprueft wird.
	 * @param stuhltemp Dies ist ein Stuhl, der fuer diese Bedingung getestet wird.
	 * @return Gibt einen boolean zurueck, ob die Bedingung erfuellt ist.
	 */
	private boolean tempGeschlechtKorrekt(Gastkarte gasttemp, Gastkarte handtemp,Stuhl stuhltemp) {
		boolean korr = true;
		for(Tisch tisch:stuhltemp.getTische()) {
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
			if(gasttemp.getGeschlecht().equals(Geschlecht.Mann)) {
				mann++;
			} else {
				frau++;
			}
			if(handtemp.getGeschlecht().equals(Geschlecht.Mann)) {
				mann++;
			} else {
				frau++;
			}
			if((gasttemp.getGeschlecht().equals(Geschlecht.Mann)) && (mann > frau) || (gasttemp.getGeschlecht().equals(Geschlecht.Frau)) && (frau > mann)) {
				korr = false;
			}
		}
		return korr;
	}
	
	/**
	 * Diese Methode ueberprueft, ob ein Tisch ueber vier Gaeste verfuegt und geleert werden muss.
	 * @param letzteKarte Dieser Boolean ist fuer die Variable zustand relevant und ueberprueft nach Abschluss der Tischleerung,
	 * ob der Spielzug abgeschlossen ist und der naechste Spieler am Zug sein muss.
	 */
	private void tischVollPruefen(boolean letzteKarte) {
		boolean leeren = false;
		for(Tisch tisch:this.getTische()) {
			int i=0;
			for(Stuhl stuhl:tisch.getStuehle()) {
				if(stuhl.getGast()!=null) {
					i++;
				}
			}
			if(i==4) {
				tisch.setZuleeren(true);
			}
		}
		for(Tisch tisch:this.getTische()) {
			if(tisch.isZuleeren()) {
				leeren = true;
				tisch.setLand(null);
				for(Stuhl stuhl:tisch.getStuehle()) {
					stuhl.gastNachHause();
				}
				if(Variablen.getZustand() == 11) {
					Variablen.setZustand(221);
				} else if(Variablen.getZustand() == 10) {
					Variablen.setZustand(220);
				}
				tisch.setZuleeren(false);
			}
		}
		if(letzteKarte && !leeren) {
			Variablen.setZustand(21);
		}
		leeren = false;
	}
	
	/**
	 * Diese Methode berechnet die Punktzahl, die eine Karte nach Erfuellung aller Bedingungen wert ist und dem Spieler gutgeschrieben wird.<br>
	 * Jeder Gast, ist an jedem Tisch, an dem er gemeinsam mit dem gekommenen Gast sitzt einen Punkt wert.<br>
	 * Sollte er die gleiche Nationalitaet besitzen, ist er zwei Punkte wert.<br>
	 * Joker besitzen automatisch alle Nationalitaeten und bringen jeweils zwei Punkte ein.
	 * @param gasttemp Dies ist die Gastkarte, die gelegt wurde.
	 */
	private void punkteBerechnen(Gastkarte gasttemp) {
		int summe = 0;
		if(!gasttemp.getLand().equals(Land.JOKER)) {
			for(Tisch tisch:this.getTische()) {
				int anzGleich = 0;
				int anzUngleich = 0;
				for(Stuhl stuhl:tisch.getStuehle()) {
					if(stuhl.getGast()!=null) {
						if(stuhl.getGast()!=gasttemp) {
							if(stuhl.getGast().getLand().equals(gasttemp.getLand()) || stuhl.getGast().getLand().equals(Land.JOKER)) {
								anzGleich++;
							} else {
								anzUngleich++;
							}
						}
					}
				}
				summe += (anzGleich * 2);
				summe += anzUngleich;
			}
		} else {
			for(Tisch tisch:this.getTische()) {
				int anzGleich = 0;
				for(Stuhl stuhl:tisch.getStuehle()) {
					if(stuhl.getGast()!=null) {
						if(stuhl.getGast()!=gasttemp) {
							anzGleich++;
						}
					}
				}
				summe += (anzGleich * 2);
			}
		}
		new Spielzuege().punktzahl(summe);
	}
	
}