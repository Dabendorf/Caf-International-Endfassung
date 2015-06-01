package cafeint;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Die Klasse Kartenstapel ist dazu zustaendig die meisten Arten von Kartenanzeigen zu realisieren. Es unterteilt sich in fuenf Typen.<br>
 * Leere Zellen bleiben weiss, "Gastkartenstapel" und "Laenderkartenstapel" stellen Nachziehstapel dar.<br>
 * "Handkarte" zeigt die Anklickbaren Handkarten an, "HandkarteInfo" zeigt in der Statistikecke die Uebersicht der Spielkarten aller Spieler auf.<br>
 * <br>
 * <b>Typ</b> Dies ist der enum, welcher alle Typen dieser Klasse beinhaltet.<br>
 * <b>typ</b> Dies ist ein Feld des enums Typ, der jeder Zelle ihren Typ zuordnet.<br>
 * <b>bi</b> Dies ist die bildliche Darstellung der Zelle.<br>
 * <b>handkartnum</b> Wenn es sich um eine Handkarte handelt, wird ihr eine Nummer (0 bis 4), welche Handkarte es ist, zugeordnet.<br>
 * <b>spieler</b> Beim Typ HandkarteInfo wird festgelegt, welchem Spieler die Karten gehoeren.<br>
 * <b>key</b> Der key ist ein String, welches Bildelement aus der Map geladen werden soll.<br>
 * <b>geklickt</b> Der Boolean geklickt wird benoetig, um bei den Handkarten festzustellen, ob der Spieler sie benutzen moechte.<br>
 * <b>num</b> Die Zahl num zeigt an den Nachziehstapeln an, wie viele Karten uebrig geblieben sind.<br>
 * <b>font</b> Dies ist die Schriftart, in welcher die Punktzahl dargestellt wird.<br>
 * <b>fm</b> Dies sind die FontMetrics, die zur Schriftart dazugehoeren.<br>
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Kartenstapel extends JPanel {
	
	public enum Typ {Leer, Gastkartenstapel, Laenderkartenstapel, Handkarte, HandkarteInfo};
	
	private Typ typ;
	private BufferedImage bi;
	private int handkartnum, spieler;
	private String key;
	private boolean geklickt = false;
	private String num;
	private Font font = new Font("Arial", Font.BOLD,16);
	private FontMetrics fm;

	public Kartenstapel(Typ typ) {
		this.typ = typ;
	}
	
	/**
	 * In dieser Paintmethode wird das Bild gezeichnet, welches ueber den String key bestimmt wurde.
	 */
	@Override
	protected void paintComponent(Graphics gr) {
	    super.paintComponent(gr);
	    bildLaden();
	    if(bi!=null) {
	    	gr.drawImage(bi, 0, 0, getWidth(), getHeight(), null);
	    }
	    if(typ.equals(Typ.Gastkartenstapel) || typ.equals(Typ.Laenderkartenstapel)) {
	    	fm = gr.getFontMetrics(font);
			gr.setFont(font);
			gr.setColor(Color.black);
			restkartenzahl();
			gr.drawString(num,fm.stringWidth(num)/2,(int)(this.getHeight()*0.85));
	    }
	}
	
	/**
	 * Diese Methode ordnet die Bilder, die anzuzeigen sind je nach Typ der Zelle zu.
	 */
	private void bildLaden() {
		if(typ.equals(Typ.Leer)) {
			this.setBackground(Color.white);
		} else if(typ.equals(Typ.Gastkartenstapel)) {
			key = "./stapel_gaeste.png";
		} else if(typ.equals(Typ.Laenderkartenstapel)) {
			key = "./stapel_tische.png";
		} else if(typ.equals(Typ.Handkarte) || typ.equals(Typ.HandkarteInfo)) {
			int spielertemp;
			if(typ.equals(Typ.Handkarte)) {
				spielertemp = Variablen.getAktSpieler();
			} else {
				spielertemp = this.spieler;
			}
			if(Variablen.getSpieler(spielertemp).getHandkarten().get(handkartnum)!=null) {
				key = "./gast_"+Variablen.getSpieler(spielertemp).getHandkarten().get(handkartnum).getLand()+"_"+Variablen.getSpieler(spielertemp).getHandkarten().get(handkartnum).getGeschlecht()+".png";
			} else {
				bi = null;
				key = null;
			}
		}
		
		if(!typ.equals(Typ.Leer) && key!=null) {
			if(typ.equals(Typ.Handkarte) || typ.equals(Typ.HandkarteInfo) || typ.equals(Typ.Gastkartenstapel)) {
				bi = Variablen.getStuhlcache().get(key);
			} else if (typ.equals(Typ.Laenderkartenstapel)){
				bi = Variablen.getTischcache().get(key);
			}
		}
	}
	
	/**
	 * Diese Methode laedt die Anzahl der Restkarten an den Nachziehstapeln nachdem eine Karte gezogen wurde neu.
	 */
	private void restkartenzahl() {
		if(typ.equals(Typ.Gastkartenstapel)) {
			num = Integer.toString(Variablen.getGastkarten().size());
		} else if(typ.equals(Typ.Laenderkartenstapel)) {
			num = Integer.toString(Variablen.getLaenderkarten().size());
		}
	}
	
	/**
	 * Diese Methode ordnet im Falle der Typen Handkarte und HandkarteInfo die Nummer der Handkarte zu (0 bis 4).
	 * @param handkartnum Nimmt die Handkartennummer entgegen.
	 */
	public void setHandkartnum(int handkartnum) {
		this.handkartnum = handkartnum;
	}
	
	/**
	 * Gibt den Wert zurueck, ob die Zelle angeklickt wurde.
	 * @return Gibt den boolean geklickt aus.
	 */
	public boolean isGeklickt() {
		return geklickt;
	}

	/**
	 * Legt fest, ob eine Karte angeklickt wurde.
	 * @param geklickt Nimmt den boolean entgegen, der geklickt steuert.
	 */
	public void setGeklickt(boolean geklickt) {
		this.geklickt = geklickt;
	}
	
	/**
	 * @return Gibt das aktuell angezeigte Bild zurueck.
	 */
	public BufferedImage getImage() {
		return bi;
	}
	
	/**
	 * Legt den Spieler fest, der der Spielzelle zugeordnet wurde.
	 * @param spieler Nimmt den int spieler entgegen.
	 */
	public void setSpieler(int spieler) {
		this.spieler = spieler;
	}

}