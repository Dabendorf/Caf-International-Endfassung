package cafeint;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

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
				bi = Variablenkammer.getStuhlcache().get(key);
			} else {
				bi = Variablenkammer.getTischcache().get(key);
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