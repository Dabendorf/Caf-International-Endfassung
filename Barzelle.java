package cafeint;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Diese Klasse ist die einzelne Zelle, die 21 mal in die Barkartenecke integriert wird.
 * Sie zeigt wenn sie leer ist die Punktzahl (positiv oder negativ an), die ihr zugeordnet wurde. Alternativ zeigt sie, wenn sie belegt wurde die zugehoerige abgeworfende Gastkarte an.
 * 
 * @param gast Hier wird die Gastkarte gespeichert, die der Zelle zugeordnet wurde. Ist standardmaessig leer.
 * @param bi Dies ist die bildliche Darstellung der zugeordneten Gastkarte.
 * @param punkte Dies ist die zugeordnete Punktzahl der Barzelle.
 * @param font Dies ist die Schriftart, in welcher die Punktzahl dargestellt wird.
 * @param fm Dies sind die FontMetrics, die zur Schriftart dazugehören.
 * 
 * @author Lukas
 * @version 1.0
 *
 */

public class Barzelle extends JPanel {

	private Gastkarte gast = null;
	private BufferedImage bi;
	private int punkte;
	private Font font = new Font("Arial", Font.BOLD,16);
	private FontMetrics fm;
	
	public Barzelle(int punkte) {
		this.setBackground(new Color(0x20324F));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.punkte = punkte;
	}
	
	/**
	 * In dieser Paintmethode wird die Punktzahl auf die Zelle geschrieben oder die zugeordnete Gastkarte dargestellt.
	 */
	@Override
	protected void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		if(gast != null) {
			gr.drawImage(bi, 0, 0, getWidth(), getHeight(), null);
		} else {
			fm = gr.getFontMetrics(font);
			gr.setFont(font);
			if(punkte > 0) {
				gr.setColor(Color.white);
			} else {
				gr.setColor(Color.red);
			}
			String num = Integer.toString(punkte);
			gr.drawString(num,this.getWidth()/2-fm.stringWidth(num)/2,this.getHeight()/2-(int)fm.getStringBounds(num, gr).getCenterY());
		}
	}
	
	/**
	 * In dieser Methode wird die Gastkarte eingetragen und anschliessend das Bild zur Darstellung geladen.
	 * @param gast Hier wird die Gastkarte eingetragen, die in die Barzelle gesetzt wird.
	 */
	public void setGast(Gastkarte gast) {
		this.gast = gast;
		if(gast!=null) {
			String key = "./gast_"+this.gast.getLand()+"_"+this.gast.getGeschlecht()+".png";
			bi = Variablen.getStuhlcache().get(key);
		}
	}
	
	/**
	 * @return Diese Methode gibt die Punktzahl der Zelle zurueck.
	 */
	public int getPunkte() {
		return punkte;
	}

}