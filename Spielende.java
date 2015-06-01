package cafeint;

import javax.swing.JOptionPane;

/**
 * Diese Klasse nimmt alle Spielszenarien auf, nach welchen eine Partie beendet werden muss.<br>
 * Ausserdem steuert sie den Ablauf der Auswertung und fragt, ob man ein weiteres Spiel spielen moechte.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Spielende {
	
	/**
	 * Diese Methode beendet das Spiel aufgrund des leeren Gastkartenstapels.<br>
	 * Eine Ueberpruefung mit boolean-Rueckgabewert, analog zur barvoll()-Methode muss nicht existieren, weil sie bereits zum Aufruf dieser Methode impliziert wird.
	 */
	public void keinegastkarten() {
		Variablen.setZustand(31);
		siegmeldung(0);
	}
	
	/**
	 * Diese Methode beendet das Spiel aufgrund des leeren Laenderkartenstapels.<br>
	 * Eine Ueberpruefung mit boolean-Rueckgabewert, analog zur barvoll()-Methode muss nicht existieren, weil sie bereits zum Aufruf dieser Methode impliziert wird.
	 */
	public void keinelaenderkarten() {
		Variablen.setZustand(31);
		siegmeldung(1);
	}
	
	/**
	 * Diese Methode prueft, ob noch freie Plaetze in der Bar uebrig sind.
	 * @return Gibt True zurueck, wenn das Spiel beendet werden muss.
	 */
	public boolean barvoll() {
		if(Variablen.getBarkarten().size() == 21) {
			Variablen.setZustand(31);
			siegmeldung(2);
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Diese Methode prueft, warum das Spiel beendet wurde, gibt eine Meldung fuer die Auswertung aus und zeigt anschliessend die Bestenliste der Punktzahlen an.
	 * @param art Hier traegt man einen der drei Gruende ein, aus dem das Spiel beendet wurde.
	 */
	private void siegmeldung(int art) {
		Meldungen msgbox = new Meldungen();
		String grund;
		switch(art) {
		case 0:
			grund=msgbox.endegastkarten;
			break;
		case 1:
			grund=msgbox.endelaenderkarten;
			break;
		case 2:
			grund=msgbox.endebar;
			break;
		default:
			grund="";
			break;
		}
		
		int pkt0 = Variablen.getSpieler(0).getPunkte();
		int pkt1 = Variablen.getSpieler(1).getPunkte();
		
		if(pkt0 > pkt1+20) {
			JOptionPane.showMessageDialog(null, grund+msgbox.siegermeldung(0), msgbox.spielende, JOptionPane.INFORMATION_MESSAGE);
			bestenlisteFuellen(0);
		} else if(pkt0 > pkt1) {
			JOptionPane.showMessageDialog(null, grund+msgbox.siegermeldung(1), msgbox.spielende, JOptionPane.INFORMATION_MESSAGE);
			bestenlisteFuellen(0);
		} else if(pkt0 == pkt1) {
			JOptionPane.showMessageDialog(null, grund+msgbox.siegermeldung(2), msgbox.spielende, JOptionPane.INFORMATION_MESSAGE);
			bestenlisteFuellen(-1);
		} else if(pkt0 < pkt1) {
			JOptionPane.showMessageDialog(null, grund+msgbox.siegermeldung(3), msgbox.spielende, JOptionPane.INFORMATION_MESSAGE);
			bestenlisteFuellen(1);
		} else if(pkt0+20 < pkt1) {
			JOptionPane.showMessageDialog(null, grund+msgbox.siegermeldung(4), msgbox.spielende, JOptionPane.INFORMATION_MESSAGE);
			bestenlisteFuellen(1);
		}
		
	}
	
	/**
	 * Diese Methode ergaenzt abhaengig vom siegreichen Spieler die Bestenliste um seine Punktzahl.
	 * @param spieler Dies ist der siegreiche Spieler
	 */
	private void bestenlisteFuellen(int spieler) {
		Bestenliste bestenliste = new Bestenliste();
		if(spieler!=-1) {
			bestenliste.highscorehinzufuegen(System.currentTimeMillis(),Variablen.getSpieler(spieler).getPunkte(),Variablen.getSpieler(spieler).getName());
		}
		bestenliste.sortiere();
    	bestenliste.anzeigen(true);
	}
	
	/**
	 * Diese Methode fragt den Spieler, ob er ein neues Spiel starten oder das Programm beenden moechte.
	 */
	public void abfrageNeuesspiel() {
		Meldungen msgbox = new Meldungen();
		int menue = JOptionPane.showOptionDialog(null,msgbox.endefrage,msgbox.endetitel, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, msgbox.endoptionen, msgbox.endoptionen[0]);
        if(menue == 0) {
        	Spielstart spst = new Spielstart();
        	spst.neuesspiel();
        } else {
        	new Spielstand().loescheSpielstand();
        	System.exit(0);
        }
	}
}