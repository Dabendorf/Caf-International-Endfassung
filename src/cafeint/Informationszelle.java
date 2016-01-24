package cafeint;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Diese Klasse zeigt den Namen des Spielers und seine Punktzahl an. Ausserdem faerbt sie sich um, wenn der Spieler an der Reihe ist.<br>
 * <br>
 * <b>spieler</b> Ordnet der Zelle ihre Spielernummer zu.<br>
 * <b>labelname</b> Das ist das JLabel, welches den Namen des Spielers anzeigt.<br>
 * <b>labelpunkte</b> Das ist das JLabel, welches die Punktzahl des Spielers anzeigt.
 * 
 * @author Lukas Schramm
 * @version 1.0
 */
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
	
	/**
	 * Diese Methode aktualisiert die Punktzahl des Spielers.
	 */
	public void punktzahlschreiben() {
		String spnm = Variablen.getSpieler(spieler).getName();
		String pkt = Integer.toString(Variablen.getSpieler(spieler).getPunkte());
		labelname.setText(spnm);
		labelpunkte.setText(pkt);
	}
	
	/**
	 * Diese Methode faerbt die Zelle um und zeigt auf, ob der Spieler am Zug ist.
	 * @param farbig boolean der aufzeigt, ob der Spieler am Zug ist und ob die Zelle umgefaerbt werden muss.
	 */
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