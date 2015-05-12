package cafeint;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Variablen {
	
	//Spieler
	private static int aktspieler = 0;
	private static Spieler[] spieler = {new Spieler(), new Spieler()};
	
	//Spielkarten
    private static List<Gastkarte> gastkarten = new ArrayList<Gastkarte>();
    private static List<Laenderkarte> laenderkarten = new ArrayList<Laenderkarte>();
    private static List<Gastkarte> barkarten = new ArrayList<Gastkarte>();

	//Spielfeld
    private static List<Tisch> tische = new ArrayList<Tisch>(12);
    private static List<Stuhl> stuehle = new ArrayList<Stuhl>(24);
    
    //Grafik
    private static Map<String,BufferedImage> tischcache = new TreeMap<String,BufferedImage>();
    private static Map<String,BufferedImage> stuhlcache = new TreeMap<String,BufferedImage>();
    private static int zustand = 0;
    //(0:Spielstart),(10:1Karte muss!),(11:1Karte legbar),(12:2Karten legbar),(21:Gastkarte ziehen),(220,221:Tischkarte ziehen),(31:SpielendeGast),(32:SpielendeTisch),(33:SpielendeBar)
    
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

}