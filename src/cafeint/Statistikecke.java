package cafeint;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import cafeint.Kartenstapel.Typ;

/**
 * Diese Klasse ist eine von 5 Klassen, aus denen sich die graphische Oberflaeche zusammensetzt.<br>
 * Es stellt die Spielflaeche unten links dar, in welcher eine Uebersicht ueber die Karten der Spieler sowie deren Punktzahlen angezeigt wird.<br>
 * <br>
 * <b>handkarten</b> Stellt die 5 Handkarten beider Spieler in einem 2D-Array dar.<br>
 * <b>infz</b> Das sind die zwei Elemente, die die Spielerinformationen (Name und Punktzahl) anzeigen.<br>
 * <b>hintgrdfarb</b> Das ist die Hintergrundfarbe, in welcher die Felder dargestellt werden.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Statistikecke extends JPanel {
	
	private Kartenstapel handkarten[][] = new Kartenstapel[2][5];
	private Informationszelle[] infz = new Informationszelle[2];
	private Color hintgrdfarb = new Color(0x000000);
	
	public Statistikecke() {
		this.setBackground(hintgrdfarb);
		layoutgenerieren();
	}
	
	/**
	 * Diese Methode erstellt das GridLayout, in welchem die Anzeigeelemente dargestellt werden.
	 */
	private void layoutgenerieren() {
		setLayout(new GridLayout(6,2));
		for(int i=0;i<2;i++) {
			infz[i] = new Informationszelle(i);
			infz[i].setOpaque(true);
			infz[i].setPreferredSize(new Dimension(0, 20));
			infz[i].setBorder(BorderFactory.createLineBorder(Color.black));
			add(infz[i]);
		}
		for(int i=0;i<10;i++) {
			if(i%2==0) {
				handkarten[0][i/2] = new Kartenstapel(Typ.HandkarteInfo);
				handkarten[0][i/2].setOpaque(true);
				handkarten[0][i/2].setHandkartnum(i/2);
				handkarten[0][i/2].setSpieler(0);
				handkarten[0][i/2].setBorder(BorderFactory.createLineBorder(Color.black));
				add(handkarten[0][i/2]);
			} else {
				handkarten[1][(i-1)/2] = new Kartenstapel(Typ.HandkarteInfo);
				handkarten[1][(i-1)/2].setOpaque(true);
				handkarten[1][(i-1)/2].setHandkartnum((i-1)/2);
				handkarten[1][(i-1)/2].setSpieler(1);
				handkarten[1][(i-1)/2].setBorder(BorderFactory.createLineBorder(Color.black));
				add(handkarten[1][(i-1)/2]);
			}
		}
	}

	public Informationszelle getInfz(int num) {
		return infz[num];
	}
	
	public Kartenstapel getKartsp(int x, int y) {
		return handkarten[x][y];
	}

}