package cafeint;

import javax.swing.JOptionPane;

public class Meldungen {
	
	public String programmname = "Café International";
	public String nameerlaubtezeichen = "[a-zA-Z0-9ÄÖÜäöüß]*";
	public String fragespielername = "Wie heißen die Spieler?";
	public String spielernamevergessen = "Bitte gib beide Spielernamen ein!";
	public String titelunvollstaendig = "Unvollständige Eingabe";
	public String spielernamengleich = "Bitte benenne die Spieler unterschiedlich!";
	public String titelnamensgleichheit = "Namensgleichheit";
	public String endegastkarten = "Der letzte Gast hat das Café betreten.\n";
	public String endelaenderkarten = "Alle Nationenkarten sind aufgebraucht.\n";
	public String endebar = "Alle Barplätze sind besetzt.\n";
	public String spielende = "Das Spiel ist vorbei";
	public Object[] endoptionen = {"Neues Spiel", "Beenden"};
	public String endetitel = "Möchtest Du eine neue Partie starten?";
	public String endefrage = "Spielende";
	public String gastlandfalsch = "Die Nationalität dieses Gastes stimmt nicht mit der Tischnationalität überein.";
	public String gastzuvielemaenner = "Es sitzen zu viele Männer an diesem Tisch!";
	public String gastzuvielefrauen = "Es sitzen zu viele Frauen an diesem Tisch!";
	public String gastpartnerfalsch = "Dieser Gast würde allein sitzen. Du hast keine passenden Karten auf der Hand!";
	
	public void windows() {
		JOptionPane.showMessageDialog(null, "Dein System ist hoffnungslos veraltet!\nWindoof ist nicht kompatibel mit diesem Spiel.\nSollte es zu Problemen bei der Ausführung kommen,\ndann öffne das Spiel bitte auf einem PC\nmit Mac OS oder Linux!", "System veraltet", JOptionPane.WARNING_MESSAGE);
	}
	
	public String spielernameint(int n) {
		return "Name von Spieler "+n;
	}
	
	public String siegermeldung(int ergebnis) {
		if(ergebnis == 0) {
			return Variablenkammer.getSpieler(0).getName()+" gewinnt die Partie hochverdient!\nEr gewinnt mit "+Variablenkammer.getSpieler(0).getPunkte()+" zu "+Variablenkammer.getSpieler(1).getPunkte()+" Punkten.\nHerzlichen Glückwunsch.";
		} else if(ergebnis == 1) {
			return Variablenkammer.getSpieler(0).getName()+" gewinnt die Partie mit knappem Vorsprung!\nEr gewinnt mit "+Variablenkammer.getSpieler(0).getPunkte()+" zu "+Variablenkammer.getSpieler(1).getPunkte()+" Punkten.\nHerzlichen Glückwunsch.";
		} else if(ergebnis == 2) {
			return "Diese Partie endet Unentschieden.\nBeide Spieler erreichten eine Punktzahl von "+Variablenkammer.getSpieler(0).getPunkte()+" Punkten.";
		} else if(ergebnis == 3) {
			return Variablenkammer.getSpieler(1).getName()+" gewinnt die Partie mit knappem Vorsprung!\nEr gewinnt mit "+Variablenkammer.getSpieler(1).getPunkte()+" zu "+Variablenkammer.getSpieler(0).getPunkte()+" Punkten.\nHerzlichen Glückwunsch.";
		} else {
			return Variablenkammer.getSpieler(1).getName()+" gewinnt die Partie hochverdient!\nEr gewinnt mit "+Variablenkammer.getSpieler(1).getPunkte()+" zu "+Variablenkammer.getSpieler(0).getPunkte()+" Punkten.\nHerzlichen Glückwunsch.";
		}
	}

}