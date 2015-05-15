package cafeint;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import cafeint.Gastkarte.Land;

/**
 * Diese Klasse ist eine von 5 Klassen, aus denen sich die graphische Oberflaeche zusammensetzt.<br>
 * Es stellt die Spielflaeche oben links dar, in welcher Gastkarten abgelegt werden, die der Spieler nicht sinnvoll verwenden konnte und abwerfen musste.<br>
 * Das Anklicken einer Barzelle legt eine markierte Gastkarte in selbiger ab.<br>
 * <br>
 * <b>barzellen</b> Stellt die 21 Zellen der Bar in einem Array dar.<br>
 * <b>barpunkte</b> Kongruent zum barzellen[] ist dies die Zuordnung der Punkte beim Ablegen einer Karte.<br>
 * <b>hintgrdfarb</b> Die Farbe die als Hintergrund der Klasse dient.<br>
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Barkartenecke extends JPanel {
	
	private static Barzelle[] barzellen = new Barzelle[21];
	private static int barpunkte[] = {1,2,3,4,5,-2,-4,-6,-8,-10,-4,-6,-8,-10,-12,-6,-8,-10,-12,-14,-16};
	private Color hintgrdfarb = new Color(0x000000);
	
	public Barkartenecke() {
		this.setBackground(hintgrdfarb);
		setLayout(new GridLayout(7,3));
		for(int i=0;i<21;i++) {
			barzellen[i] = new Barzelle(barpunkte[i]);
			barzellen[i].setOpaque(true);
			barzellen[i].addMouseListener(new MouseAdapter() {
            	@Override
				public void mouseClicked(MouseEvent e) {
            		if(Spielkartenecke.getAkthandkartnum()!=-1) {
            			if(Variablen.getSpieler(42).getHandkarten().get(Spielkartenecke.getAkthandkartnum()).getLand().equals(Land.JOKER)) {
            				new Spielzuege().warnungsboxtext(new Meldungen().barjoker);
            				new Spielzuege().handkartendemarkieren();
            			} else if(Variablen.getZustand() == 10 || Variablen.getZustand() == 11) {
            				new Spielzuege().warnungsboxtext(new Meldungen().barzuspaet);
            				new Spielzuege().handkartendemarkieren();
            			} else {
            				new Spielzuege().legebarkarte(Spielkartenecke.getAkthandkartnum());
            			}
            		}
            	}
            });
			add(barzellen[i]);
		}
	}

	/**
	 * @param num Stellt die Nummer der Barzelle (0 bis 22) dar, welche zurückgegeben wird.
	 * @return Gibt eine Barzelle abhängig von der Nummer zurück, welche in anderen Klassen verwendet wird.
	 */
	public static Barzelle getBarzelle(int num) {
		return barzellen[num];
	}

}