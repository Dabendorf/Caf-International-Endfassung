package cafeint;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Diese Klasse enthaelt eine Ansammlung saemtlicher globaler Variablen des gesamten Javaprojektes an einem Ort gebuendelt.<br>
 * Die Variablen enthalten Getter und Setter, um sie fuer die anderen Objekte freizugeben.<br>
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Variablen {
	
	/**
	 * Dies sind alle Variablen, die die Spieler betreffen.
	 */
	private static int aktspieler = 0;
	private static Spieler[] spieler = {new Spieler(), new Spieler()};
	
	/**
	 * Dies sind alle Variablen, die mit Spielkarten zu tun haben.
	 */
    private static List<Gastkarte> gastkarten = new ArrayList<Gastkarte>();
    private static List<Laenderkarte> laenderkarten = new ArrayList<Laenderkarte>();
    private static List<Gastkarte> barkarten = new ArrayList<Gastkarte>();

	/**
	 * Dies sind die Variablen des Spielfeldes.
	 */
    private static List<Tisch> tische = new ArrayList<Tisch>(12);
    private static List<Stuhl> stuehle = new ArrayList<Stuhl>(24);
    
    /**
     * Diese Methode enthalten die beiden TreeMaps, in denen saemtliche Bilder gespeichert sind.
     */
    private static Map<String,BufferedImage> tischcache = new TreeMap<String,BufferedImage>();
    private static Map<String,BufferedImage> stuhlcache = new TreeMap<String,BufferedImage>();
    
    /**
     * Dies sind vier der fuenf eingefuegten Designelemente.<br>
     * Die <b>Bildecke</b> hat keinerlei Steuerfunktionen und kann daher direkt ins Design eingegliedert werden.
     */
    private static Barkartenecke barkartenecke = new Barkartenecke();
    private static Statistikecke statistikecke = new Statistikecke();
    private static Spielfeld spielfeld = new Spielfeld();
    private static Spielkartenecke spielkartenecke = new Spielkartenecke();

	/**
     * Die Variable zustand ist eine essentielle Variable zur Steuerung des Spielablaufes. Sie stellt numerisch dar, in welcher Phase sich das Spiel gerade befindet.<br>
     * Sollte eine Methode ausgefuert werden, die der Nummer des zustandes widerspricht, wird sie nicht ausgefuehrt.<br>
     * Bei allen Zahlen, die mit <b>0</b> beginnen startet das Spiel, bei allen die mit <b>1</b> beginnen legt man Karten, bei mit <b>2</b> beginnenden zieht man Karten, bei mit <b>3</b> beginnenden endet das Spiel.<br>
     * <br>
     * Die Zustaende sind folgende:<br>
     * <b>0</b> Die Variable ist null, wenn das Spiel gerade gestartet wurde.<br>
     * <b>10 - 12</b> Alle Variablen, die mit <b>1</b> beginnen zeigen, dass eine Gastkarte gelegt werden kann/muss. Die <b>12</b> sagt, dass noch 2, mindestens jedoch eine gelegt werden muss.
     * <b>11</b> sagt, dass eine Gastkarte noch gelegt werden darf, jedoch aber die Runde beendet werden darf. <b>10</b> ist gleich der <b>11</b>, jedoch mit dem Unterschied, dass hier zwangsweise noch ein Gast gelegt werden.
     * Dieser Zustand trifft nur auf, wenn ein Gast allein sitzt und einen Partner benoetigt.<br>
     * <b>21</b> Zeigt an, dass nun Gastkarten vom Stapel gezogen werden muessen.<br>
     * <b>220 - 221</b> Zeigt an, dass nun Laenderkarte vom Stapel gezogen werden muessen. Es wird differenziert, weil nach Ziehen von Tischkarten der Zug noch nicht vorbei ist.
     * Es wird anschliessend <b>210</b> subtrahiert, um auf <b>10</b> oder <b>11</b>, je nach altem Zustand zu kommen.<br>
     * <b>31 - 33</b> Dies sind alle Spielendszenarien mit Grund. Bei <b>31</b> wurde das Spiel aufgrund von fehlenden Gastkarten beendet. Bei <b>32</b> aufgrund von fehlenden Laenderkarten und bei <b>33</b> aufgrund einer vollen Bar.
     */
    private static int zustand = -1;
    
	public static List<Gastkarte> getGastkarten() {
		return gastkarten;
	}

	public static List<Laenderkarte> getLaenderkarten() {
		return laenderkarten;
	}

	public static int getAktSpieler() {
		return aktspieler;
	}

	public static void setAktSpieler(int aktspieler) {
		Variablen.aktspieler = aktspieler;
	}

	/**
	 * Diese Methode gibt einen der Spieler wieder. Gibt man 42 als Parameter ein, wird automatisch der aktuelle Spieler wiedergegeben.
	 * @param num Nimmt die Spielernummer entgegen.
	 * @return Gibt den Spieler zurueck.
	 */
	public static Spieler getSpieler(int num) {
		if(num == 42) {
			return spieler[Variablen.getAktSpieler()];
		} else {
			return spieler[num];
		}
	}

	public static Map<String, BufferedImage> getTischcache() {
		return tischcache;
	}

	public static Map<String, BufferedImage> getStuhlcache() {
		return stuhlcache;
	}

	public static List<Gastkarte> getBarkarten() {
		return barkarten;
	}

	public static List<Tisch> getTische() {
		return tische;
	}

	public static List<Stuhl> getStuehle() {
		return stuehle;
	}

	public static int getZustand() {
		return zustand;
	}

	public static void setZustand(int zustand) {
		Variablen.zustand = zustand;
	}
	
	public static Barkartenecke getBarkartenecke() {
		return barkartenecke;
	}

	public static Statistikecke getStatistikecke() {
		return statistikecke;
	}

	public static Spielfeld getSpielfeld() {
		return spielfeld;
	}

	public static Spielkartenecke getSpielkartenecke() {
		return spielkartenecke;
	}

}