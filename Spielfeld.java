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

/**
 * Diese Klasse ist eine von 5 Klassen, aus denen sich die graphische Oberflaeche zusammensetzt.<br>
 * Es stellt die Haupt-Spielflaeche in der Mitte dar, in welcher der Aufbau des Cafes dargestellt ist.<br>
 * Sie enthaelt alle Tische und Stuehle, an die man die Gast- und Tischkarten anlegen kann.<br>
 * <br>
 * <b>spielfeldzelle</b> Dies ist ein 2D-Array, der alle 11 mal 11 Zellen dieses Hauptfeldes in einem Raster anordnet.<br>
 * <b>warnungsbox</b> Dies ist die Box ganz unten, in welcher Informationen angezeigt werden, wenn ein Spieler ungueltige Zuege setzen moechte.<br>
 * <b>spielfeldtisch</b> Diese ArrayList speichert alle Zellelemente, die im Spiel Tische darstellen.<br>
 * <b>spielfeldstuhl</b> Diese ArrayList speichert alle Zellelemente, die im Spiel Stuehle darstellen.<br>
 * <b>hintgrdfarb</b> Das ist die Hintergrundfarbe, in welcher die Felder dargestellt werden.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */

public class Spielfeld extends JPanel {

	private Spielzelle spielfeldzelle[][] = new Spielzelle[11][11];
	private JLabel warnungsbox = new JLabel();
	private ArrayList<Spielzelle> spielfeldtisch = new ArrayList<Spielzelle>(12);
	private ArrayList<Spielzelle> spielfeldstuhl = new ArrayList<Spielzelle>(24);
	private Color hintgrdfarb = new Color(0x538fcb);

	public Spielfeld() {
		this.setBackground(hintgrdfarb);
		layoutgenerieren();
		eigenschaften();
	}
	
	/**
	 * Diese Methode erstellt das GridBagLayout, in welchem die Spielflaeche dargestellt wird.
	 */
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
	
	/**
	 * Diese Methode ordnet allen Zellen ihre Eigenschaft Tisch oder Stuhl zu oder belaesst sie als leere Zelle.
	 */
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
            		if(Variablen.getSpielkartenecke().getAkthandkartnum()!=-1) {
            			if(Variablen.getStuehle().get(j).getGast()==null) {
            				new Spielzuege().legegastkarte(Variablen.getSpielkartenecke().getAkthandkartnum(),j);
            			} else {
            				new Spielzuege().warnungsboxtext(new Meldungen().stuhlbesetzt);
            				new Spielzuege().handkartendemarkieren();
            			}
            		}
            	}
            });
		}
	}

	public ArrayList<Spielzelle> getSpielfeldtisch() {
		return spielfeldtisch;
	}

	public ArrayList<Spielzelle> getSpielfeldstuhl() {
		return spielfeldstuhl;
	}

	public JLabel getWarnungsbox() {
		return warnungsbox;
	}

	public Color getHintgrdfarb() {
		return hintgrdfarb;
	}
	
}