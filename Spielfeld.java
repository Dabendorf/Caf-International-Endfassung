package cafeint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Spielfeld extends JPanel {

	private Spielzelle spielfeldzelle[][] = new Spielzelle[11][11];
	private static JLabel warnungsbox = new JLabel();
	private static ArrayList<Spielzelle> spielfeldtisch = new ArrayList<Spielzelle>(12);
	private static ArrayList<Spielzelle> spielfeldstuhl = new ArrayList<Spielzelle>(24);
	private static Color hintgrdfarb = new Color(0x538fcb);

	public Spielfeld() {
		this.setBackground(hintgrdfarb);
		layoutgenerieren();
		eigenschaften();
	}
	
	private void layoutgenerieren() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		gbc.fill = GridBagConstraints.BOTH;
		
		for(int y=0;y<11;y++) {
			if(y==0) {
				gbc.weighty = 0.2;
			} else {
				gbc.weighty = 1.0;
			}
			for(int x=0;x<11;x++) {
				gbc.gridx = x;
				gbc.gridy = y;
				if(y<10) {
					if(x==0 || x==10) {
						gbc.weightx=0.2;
					} else {
						gbc.weightx=1.0;
					}
					spielfeldzelle[x][y] = new Spielzelle(Spielzelle.Typ.Leer);
					spielfeldzelle[x][y].setBackground(hintgrdfarb);
					spielfeldzelle[x][y].setBorder(BorderFactory.createLineBorder(hintgrdfarb, 3));
					spielfeldzelle[x][y].setOpaque(true);
					add(spielfeldzelle[x][y], gbc);
				} else if(y==10) {
					gbc.gridwidth = 11;
					gbc.weighty = 0.5;
					warnungsbox.setBackground(hintgrdfarb);
					warnungsbox.setOpaque(true);
					warnungsbox.setBorder(BorderFactory.createLineBorder(hintgrdfarb, 3));
					warnungsbox.setPreferredSize(new Dimension(0, 20));
					warnungsbox.setHorizontalAlignment(SwingConstants.CENTER);
					add(warnungsbox, gbc);
					break;
				}
			}
		}
	}
	
	private void eigenschaften() {
		int[] tischkoordx = {4,5,6,7,8,7,6,5,4,3,2,3};
		int[] tischkoordy = {3,2,3,4,5,6,7,8,7,6,5,4};
		for(int i=0;i<12;i++) {
			spielfeldtisch.add(spielfeldzelle[tischkoordx[i]][tischkoordy[i]]);
			spielfeldzelle[tischkoordx[i]][tischkoordy[i]].setTyp(Spielzelle.Typ.Tisch);
		}
		
		int[] stuhlkoordx = {4,5,6,7,8,7,6,5,4,3,2,3,4,5,6,9,8,7,6,5,4,3,2,1};
		int[] stuhlkoordy = {4,1,2,3,4,5,6,7,6,5,4,3,2,3,4,5,6,7,8,9,8,7,6,5};
		for(int i=0;i<24;i++) {
			final int j = i;
			spielfeldstuhl.add(spielfeldzelle[stuhlkoordx[i]][stuhlkoordy[i]]);
			spielfeldzelle[stuhlkoordx[i]][stuhlkoordy[i]].setTyp(Spielzelle.Typ.Stuhl);
			spielfeldzelle[stuhlkoordx[i]][stuhlkoordy[i]].addMouseListener(new MouseAdapter() {
            	@Override
				public void mouseClicked(MouseEvent e) {
            		if(Spielkartenecke.getAkthandkartnum()!=-1) {
            			if(Variablen.getStuehle().get(j).getGast()==null) {
            				new Spielzuege().legegastkarte(Spielkartenecke.getAkthandkartnum(),j);
            			} else {
            				new Spielzuege().warnungsboxtext(new Meldungen().stuhlbesetzt);
            				new Spielzuege().handkartendemarkieren();
            			}
            		}
            	}
            });
		}
	}

	public static ArrayList<Spielzelle> getSpielfeldtisch() {
		return spielfeldtisch;
	}

	public void setSpielfeldtisch(ArrayList<Spielzelle> spielfeldtisch) {
		Spielfeld.spielfeldtisch = spielfeldtisch;
	}

	public static ArrayList<Spielzelle> getSpielfeldstuhl() {
		return spielfeldstuhl;
	}

	public void setSpielfeldstuhl(ArrayList<Spielzelle> spielfeldstuhl) {
		Spielfeld.spielfeldstuhl = spielfeldstuhl;
	}

	public static JLabel getWarnungsbox() {
		return warnungsbox;
	}

	public static Color getHintgrdfarb() {
		return hintgrdfarb;
	}
	
}