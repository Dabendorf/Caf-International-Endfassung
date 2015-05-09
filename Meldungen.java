package cafeint;

public class Meldungen {
	
	public String umbruch = System.getProperty("line.separator");
	public String programmname = "Café International";
	public String nameerlaubtezeichen = "[a-zA-Z0-9ÄÖÜäöüß]*";
	public String falschessystem = "System veraltet";
	public String windows = "Dieses Computerspiel wurde für OS X und Linux entwickelt."+umbruch+"Es ist nicht primär mit Windows kompatibel."+umbruch+"Sollte es zu Problemen bei der Ausführung kommen,"+umbruch+"dann öffne das Spiel bitte auf einem PC"+umbruch+"mit Mac OS X oder Linux!";
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
	public String[] endoptionen = {"Neues Spiel", "Beenden"};
	public String endetitel = "Möchtest Du eine neue Partie starten?";
	public String endefrage = "Spielende";
	public String gastlandfalsch = "Die Nationalität dieses Gastes stimmt nicht mit der Tischnationalität überein!";
	public String gastzuvielemaenner = "Es sitzen zu viele Männer an diesem Tisch!";
	public String gastzuvielefrauen = "Es sitzen zu viele Frauen an diesem Tisch!";
	public String gastpartnerfalsch = "Dieser Gast würde allein sitzen. Du hast keine passenden Karten auf der Hand!";
	public String barjoker = "Es ist verboten, Joker in die Bar abzuwerfen. Bitte setze den Gast an einen Tisch!";
	public String gastkarteziehen = "Du hast die Maximalzahl an Karten gelegt. Bitte ziehe nun neue Gastkarten vom Stapel!";
	public String gastkartelegen = "Bitte lege erst eine Gastkarte, bevor Du neue Karten ziehst!";
	public String tischkarteziehen = "Bitte ziehe nun für die leeren Tische neue Tischkarten.";
	public String barzuspaet = "Du kannst nur zu Anfang Deines Zuges Karten in die Bar abwerfen! Lege eine Gastkarte oder beende Deinen Spielzug.";
	public String stuhlbesetzt = "Dieser Stuhl ist bereits besetzt. Setze diesen Gast woanders hin.";
	
	public String siegermeldung(int ergebnis) {
		if(ergebnis == 0) {
			return Variablen.getSpieler(0).getName()+" gewinnt die Partie hochverdient!"+umbruch+"Er gewinnt mit "+Variablen.getSpieler(0).getPunkte()+" zu "+Variablen.getSpieler(1).getPunkte()+" Punkten."+umbruch+"Herzlichen Glückwunsch.";
		} else if(ergebnis == 1) {
			return Variablen.getSpieler(0).getName()+" gewinnt die Partie mit knappem Vorsprung!"+umbruch+"Er gewinnt mit "+Variablen.getSpieler(0).getPunkte()+" zu "+Variablen.getSpieler(1).getPunkte()+" Punkten."+umbruch+"Herzlichen Glückwunsch.";
		} else if(ergebnis == 2) {
			return "Diese Partie endet Unentschieden."+umbruch+"Beide Spieler erreichten eine Punktzahl von "+Variablen.getSpieler(0).getPunkte()+" Punkten.";
		} else if(ergebnis == 3) {
			return Variablen.getSpieler(1).getName()+" gewinnt die Partie mit knappem Vorsprung!"+umbruch+"Er gewinnt mit "+Variablen.getSpieler(1).getPunkte()+" zu "+Variablen.getSpieler(0).getPunkte()+" Punkten."+umbruch+"Herzlichen Glückwunsch.";
		} else {
			return Variablen.getSpieler(1).getName()+" gewinnt die Partie hochverdient!"+umbruch+"Er gewinnt mit "+Variablen.getSpieler(1).getPunkte()+" zu "+Variablen.getSpieler(0).getPunkte()+" Punkten."+umbruch+"Herzlichen Glückwunsch.";
		}
	}

}