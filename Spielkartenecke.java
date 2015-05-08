package cafeint;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cafeint.Kartenstapel.Typ;

public class Spielkartenecke extends JPanel {
	
	private static Kartenstapel handkarten[] = new Kartenstapel[5];
	private static Kartenstapel gastkst = new Kartenstapel(Typ.Gastkartenstapel);
	private static Kartenstapel landkst = new Kartenstapel(Typ.Laenderkartenstapel);
	private static int akthandkartnum = -1;
	private Color hintgrdfarb = new Color(0x000000);
	
	protected Spielkartenecke() {
		this.setBackground(hintgrdfarb);
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
	            		if((Variablenkammer.getZustand()==10 || Variablenkammer.getZustand()==11 || Variablenkammer.getZustand()==12) && handkarten[index].getImage()!=null) {
	            			klickhand(index);
	            		} else if(Variablenkammer.getZustand() >=220) {
	            			new Spielzuege().warnungsboxtext(new Meldungen().tischkarteziehen);
	            		} else if(handkarten[index].getImage()!=null) {
	            			new Spielzuege().warnungsboxtext(new Meldungen().gastkarteziehen);
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
	            		if(Variablenkammer.getZustand()==21 || Variablenkammer.getZustand()==11) {
	            			klickgast();
		            		new Spielzuege().spielerwechsel();
	    				} else if(Variablenkammer.getZustand() >= 220) {
	    					new Spielzuege().warnungsboxtext(new Meldungen().tischkarteziehen);
	    				} else {
	    					new Spielzuege().warnungsboxtext(new Meldungen().gastkartelegen);
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
						if(Variablenkammer.getZustand() >= 220) {
							klicktisch();
						} else if(Variablenkammer.getZustand() > 9 && Variablenkammer.getZustand() < 13) {
							new Spielzuege().warnungsboxtext(new Meldungen().gastkartelegen);
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
	
	private void klickgast() {
		for(int i=0;i<5;i++) {
			if(handkarten[i].getImage()==null && Variablenkammer.getGastkarten().size() > 0) {
				new Spielzuege().gastkarteziehen(i);
			}
		}
	}
	
	private void klicktisch() {
		for(Tisch tisch:Variablenkammer.getTische()) {
			if(tisch.getLaenderkarte()==null) {
				new Spielzuege().legetischkarte(tisch);
			}
		}
		int zustand = Variablenkammer.getZustand();
		if(zustand >= 220) {
			Variablenkammer.setZustand(zustand-210);
		}
	}
	
	public static void gastkstzahlLaden() {
		gastkst.repaint();
	}
	
	public static void landkstzahlLaden() {
		landkst.repaint();
	}
	
	public static Kartenstapel getHandkarte(int n) {
		return handkarten[n];
	}

	public static int getAkthandkartnum() {
		return akthandkartnum;
	}

	public static void setAkthandkartnum(int akthandkartnum) {
		Spielkartenecke.akthandkartnum = akthandkartnum;
	}

}