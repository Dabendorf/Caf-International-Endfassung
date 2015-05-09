package cafeint;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
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
	private String num;
	private Font font = new Font("Arial", Font.BOLD,16);
	private FontMetrics fm;

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
	    if(typ.equals(Typ.Gastkartenstapel) || typ.equals(Typ.Laenderkartenstapel)) {
	    	fm = gr.getFontMetrics(font);
			gr.setFont(font);
			gr.setColor(Color.black);
			restkartenzahl();
			gr.drawString(num,fm.stringWidth(num)/2,(int)(this.getHeight()*0.85));
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
	
	private void restkartenzahl() {
		if(typ.equals(Typ.Gastkartenstapel)) {
			num = Integer.toString(Variablen.getGastkarten().size());
		} else if(typ.equals(Typ.Laenderkartenstapel)) {
			num = Integer.toString(Variablen.getLaenderkarten().size());
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
*/