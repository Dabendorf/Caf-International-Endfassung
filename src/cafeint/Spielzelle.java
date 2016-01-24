package cafeint;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Die Klasse Spielzelle ist dazu zustaendig die Arten von Zellen im Spielfeld zu realisieren. Es unterteilt sich in drei Typen.<br>
 * Leere Zellen bleiben eigenschaftenlos, "Stuhl" und "Tisch" stellen die Hauptspielelemente dar.<br>
 * <br>
 * <b>Typ</b> Dies ist der enum, welcher alle Typen dieser Klasse beinhaltet.<br>
 * <b>typ</b> Dies ist ein Feld des enums Typ, der jeder Zelle ihren Typ zuordnet.<br>
 * <b>bi</b> Dies ist die bildliche Darstellung der Zelle.<br>
 * <b>stuhl</b> Wenn es sich um einen Stuhl handelt, wird der Zelle hier ein Stuhl zugeordnet.<br>
 * <b>tisch</b> Wenn es sich um einen Tisch handelt, wird der Zelle hier ein Tisch zugeordnet.<br>
 * <b>key</b> Der key ist ein String, welches Bildelement aus der Map geladen werden soll.<br>
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Spielzelle extends JPanel {
	
	public enum Typ {Leer, Stuhl, Tisch};
	
	private Typ typ;
	private BufferedImage bi;
	private Stuhl stuhl;
	private Tisch tisch;
	private String key;
	
	protected Spielzelle(Typ typ) {
	    this.typ = typ;
	}
	
	@Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        bildLaden();
        gr.drawImage(bi, 0, 0, getWidth(), getHeight(), null);
    }
	
	/**
	 * In dieser Paintmethode wird das Bild gezeichnet, welches ueber den String key bestimmt wurde.
	 */
	private void bildLaden() {
		if(typ.equals(Typ.Stuhl)) {
			if(this.stuhl.getGast()!=null) {
				key = "./gast_"+this.stuhl.getGast().getLand()+"_"+this.stuhl.getGast().getGeschlecht()+".png";
			} else {
				key = "./stuhl_leer.png";
			}
		} else if(typ.equals(Typ.Tisch)) {
			if(this.tisch.getLaenderkarte()!=null) {
				key = "./tisch_"+this.tisch.getLaenderkarte()+".png";
			} else {
				key = "./tisch_leer.png";
			}
		}
		
		if(!typ.equals(Typ.Leer)) {
			if(typ.equals(Typ.Stuhl)) {
				bi = Variablen.getStuhlcache().get(key);
			} else {
				bi = Variablen.getTischcache().get(key);
			}
		}
	}

	public void setTyp(Typ typ) {
		this.typ = typ;
	}
	
	public void setStuhl(Stuhl stuhl) {
		this.stuhl = stuhl;
	}

	public void setTisch(Tisch tisch) {
		this.tisch = tisch;
	}

}