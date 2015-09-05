package cafeint;

/**
 * Diese Klasse ist eine Sammlung saemtlicher sprachlicher Elemente und Meldungen, die im Spiel enthalten sind.<br>
 * Die Ansammlung von allem in einer Klasse stellt eine gute Ordnung sicher und behebt Codierungsprobleme in verschiedenen Betriebssystemen signifikant schneller.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Sprache {
	
	public String umbruch = System.getProperty("line.separator");
	public String programmname = "Café International";
	public String nameerlaubtezeichen = "[a-zA-Z0-9ÄÖÜäöüß]*";
	public String falschessystem = "System veraltet";
	public String dateifehltTitel = "Fehlerhafte Datei";
	public String windows = "Dieses Computerspiel wurde für OS X und Linux entwickelt."+umbruch+"Es ist nicht primär mit Windows kompatibel."+umbruch+"Sollte es zu Problemen bei der Ausführung kommen,"+umbruch+"dann öffne das Spiel bitte auf einem PC"+umbruch+"mit Mac OS X oder Linux!";
	public String anleitungfehlt = "Die Speicherdatei /dateien/anleitung.pdf ist nicht vorhanden."+umbruch+"Stelle die Datei wieder her, um die Anleitung öffnen zu können.";
	public String fragespielername = "Wie heißen die Spieler?";
	public String spielername = "Name von Spieler ";
	public String spielernamevergessen = "Bitte gib beide Spielernamen ein!";
	public String titelunvollstaendig = "Unvollständige Eingabe";
	public String spielernamengleich = "Bitte benenne die Spieler unterschiedlich!";
	public String titelnamensgleichheit = "Namensgleichheit";
	public String endegastkarten = "Der letzte Gast hat das Café betreten."+umbruch;
	public String endelaenderkarten = "Alle Nationenkarten sind aufgebraucht."+umbruch;
	public String endebar = "Alle Barplätze sind besetzt."+umbruch;
	public String spielende = "Das Spiel ist vorbei";
	public String[] endoptionen = {"Neues Spiel","Beenden"};
	public String endetitel = "Spielende";
	public String endefrage = "Möchtest Du eine neue Partie starten?";
	public String[] schliessoptionen = {"Speichern","Beenden"};
	public String schliesstitel = "Spiel speichern";
	public String schliessfrage = "Möchtest Du das aktuelle Spiel abspeichern?";
	public String[] altesspieloptionen = {"Spiel wiederherstellen","Neues Spiel"};
	public String altesspieltitel = "Vorheriges Spiel wiederherstellen";
	public String altesspielfrage = "Es ist ein altes Spiel zwischengespeichert."+umbruch+"Möchtest Du ein neues Spiel starten"+umbruch+"oder das alte Spiel wiederherstellen?";
	public String gastlandfalsch = "Die Nationalität dieses Gastes stimmt nicht mit der Tischnationalität überein!";
	public String gastzuvielemaenner = "Es sitzen zu viele Männer an diesem Tisch!";
	public String gastzuvielefrauen = "Es sitzen zu viele Frauen an diesem Tisch!";
	public String gastpartnerfalsch = "Dieser Gast würde allein sitzen. Du hast keine passenden Karten auf der Hand!";
	public String gastpartnerfehlt = "Ein Gast sitzt noch allein. Bitte setze einen Partner zu diesem Gast an einen Tisch!";
	public String barjoker = "Es ist verboten, Joker in die Bar abzuwerfen. Bitte setze den Gast an einen Tisch!";
	public String gastkarteziehen = "Du hast die Maximalzahl an Karten gelegt. Bitte ziehe nun neue Gastkarten vom Stapel!";
	public String gastkartelegen = "Bitte lege erst eine Gastkarte, bevor Du neue Karten ziehst!";
	public String tischkarteziehen = "Bitte ziehe nun für die leeren Tische neue Tischkarten.";
	public String barzuspaet = "Du kannst nur zu Anfang Deines Zuges Karten in die Bar abwerfen! Lege eine Gastkarte oder beende Deinen Spielzug.";
	public String stuhlbesetzt = "Dieser Stuhl ist bereits besetzt. Bitte setze diesen Gast woanders hin.";
	
	/**
	 * Diese Methode gibt abhaengig vom Sieger und seiner Punktzahl einen String wieder, der das Spiel auswertet.
	 * @param ergebnis Nummer des eingetretenen Siegereignisses
	 * @return Gibt einen String wieder, wer das Spiel gewonnen hat.
	 */
	public String siegermeldung(int ergebnis) {
		if(ergebnis == 0) {
			return Variablen.getSpieler(0).getName()+" gewinnt die Partie hochverdient!"+umbruch+"Das Spiel endet mit "+Variablen.getSpieler(0).getPunkte()+" zu "+Variablen.getSpieler(1).getPunkte()+" Punkten."+umbruch+"Herzlichen Glückwunsch.";
		} else if(ergebnis == 1) {
			return Variablen.getSpieler(0).getName()+" gewinnt die Partie mit knappem Vorsprung!"+umbruch+"Das Spiel endet mit "+Variablen.getSpieler(0).getPunkte()+" zu "+Variablen.getSpieler(1).getPunkte()+" Punkten."+umbruch+"Herzlichen Glückwunsch.";
		} else if(ergebnis == 2) {
			return "Diese Partie endet Unentschieden."+umbruch+"Beide Spieler erreichten eine Punktzahl von "+Variablen.getSpieler(0).getPunkte()+" Punkten.";
		} else if(ergebnis == 3) {
			return Variablen.getSpieler(1).getName()+" gewinnt die Partie mit knappem Vorsprung!"+umbruch+"Das Spiel endet mit "+Variablen.getSpieler(1).getPunkte()+" zu "+Variablen.getSpieler(0).getPunkte()+" Punkten."+umbruch+"Herzlichen Glückwunsch.";
		} else {
			return Variablen.getSpieler(1).getName()+" gewinnt die Partie hochverdient!"+umbruch+"Das Spiel endet mit "+Variablen.getSpieler(1).getPunkte()+" zu "+Variablen.getSpieler(0).getPunkte()+" Punkten."+umbruch+"Herzlichen Glückwunsch.";
		}
	}
	
	/**
	 * Diese Methode gibt entweder einen WarnString zurueck, dass eine Datei zum Abspeichern von Spielstaenden nicht angelegt bzw. aus dem Ordner entfernt wurde oder
	 * dass die Datei unvollstaendig und beschaedigt ist.
	 * @param dateiname Name der fehlenden/fehlerhaften Datei.
	 * @param vorhanden Boolean, ob die Datei wenigstens vorhanden ist.
	 * @return Gibt einen String wieder, um den Spieler zu informieren.
	 */
	public String dateiFehlt(String dateiname,boolean vorhanden) {
		if(!vorhanden) {
			return "Die Speicherdatei /"+dateiname+" ist nicht vorhanden."+umbruch+"Der Spielstart wird abgebrochen."+umbruch+"Stelle die Speicherdatei wieder her und versuche es erneut.";
		} else {
			return "Die Speicherdatei /"+dateiname+" ist unvollständig oder beschädigt."+umbruch+"Der Spielstand kann nicht wiederhergestellt werden"+umbruch+"und ein neues Spiel wird gestartet.";
		}
	}
}