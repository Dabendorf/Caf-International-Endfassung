package cafeint;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Kartenstapel extends JPanel {
	
	public enum Typ {Leer, Gastkartenstapel, Laenderkartenstapel, Handkarte, HandkarteInfo};
	
	private Typ typ;
	private BufferedImage bi;
	private int handkartnum, spieler;
	private String key;
	private boolean geklickt = false;

	public Kartenstapel(Typ typ) {
		this.typ = typ;
	}
	
	@Override
	protected void paintComponent(Graphics gr) {
	    super.paintComponent(gr);
	    bildLaden();
	    if(bi!=null) {
	    	gr.drawImage(bi, 0, 0, getWidth(), getHeight(), null);
	    }
	}
	
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
				spielertemp = Variablenkammer.getAktSpieler();
			} else {
				spielertemp = this.spieler;
			}
			if(Variablenkammer.getSpieler(spielertemp).getHandkarten().get(handkartnum)!=null) {
				key = "./gast_"+Variablenkammer.getSpieler(spielertemp).getHandkarten().get(handkartnum).getLand()+"_"+Variablenkammer.getSpieler(spielertemp).getHandkarten().get(handkartnum).getGeschlecht()+".png";
			} else {
				bi = null;
				key = null;
			}
		}
		
		if(!typ.equals(Typ.Leer) && key!=null) {
			if(typ.equals(Typ.Handkarte) || typ.equals(Typ.HandkarteInfo) || typ.equals(Typ.Gastkartenstapel)) {
				bi = Variablenkammer.getStuhlcache().get(key);
			} else if (typ.equals(Typ.Laenderkartenstapel)){
				bi = Variablenkammer.getTischcache().get(key);
			}
		}
	}
	
	public void setHandkartnum(int handkartnum) {
		this.handkartnum = handkartnum;
	}
	
	public boolean isGeklickt() {
		return geklickt;
	}

	public void setGeklickt(boolean geklickt) {
		this.geklickt = geklickt;
	}
	
	public BufferedImage getImage() {
		return bi;
	}
	
	public void setSpieler(int spieler) {
		this.spieler = spieler;
	}

}

/*
public Typ getTyp() {
	return t;
}

public void setTyp(Typ t) {
	this.t = t;
}



public int getHandkartnum() {
	return handkartnum;
}

public void setHandkartnum(int handkartnum) {
	this.handkartnum = handkartnum;
}
*/