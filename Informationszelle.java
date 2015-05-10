package cafeint;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Informationszelle extends JPanel {
	
	private int spieler;
	private JLabel labelname = new JLabel();
	private JLabel labelpunkte = new JLabel();
	
	public Informationszelle(int spielernum) {
		this.spieler = spielernum;
		
		setLayout(new GridLayout(2,1));
		labelname.setHorizontalAlignment(SwingConstants.CENTER);
		labelname.setOpaque(true);
		add(labelname);
		
		labelpunkte.setHorizontalAlignment(SwingConstants.CENTER);
		labelpunkte.setOpaque(true);
		add(labelpunkte);
	}
	
	public void punktzahlschreiben() {
		String spnm = Variablen.getSpieler(spieler).getName();
		String pkt = Integer.toString(Variablen.getSpieler(spieler).getPunkte());
		labelname.setText(spnm);
		labelpunkte.setText(pkt);
	}
	
	public void faerben(boolean farbig) {
		if(farbig) {
			labelname.setBackground(Color.orange);
			labelpunkte.setBackground(Color.orange);
		} else {
			labelname.setBackground(new Color(0xEEEEEE));
			labelpunkte.setBackground(new Color(0xEEEEEE));
		}
	}

}