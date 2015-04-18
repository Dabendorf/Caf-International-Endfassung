package cafeint;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Variablenkammer {
	
	//System
	private static Meldungen msgbox = new Meldungen();
	
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
    private static Map<String, BufferedImage> tischcache = new TreeMap<>();
    private static Map<String, BufferedImage> stuhlcache = new TreeMap<>();
    
    //Getter und Setter
	public static Meldungen getMsgbox() {
		return msgbox;
	}
	
	public static List<Gastkarte> getGastkarten() {
		return gastkarten;
	}

	public static List<Laenderkarte> getLaenderkarten() {
		return laenderkarten;
	}

	public static int getAktspieler() {
		return aktspieler;
	}

	public static void setAktspieler(int aktspieler) {
		Variablenkammer.aktspieler = aktspieler;
	}

	public static Spieler getSpieler(int num) {
		if(num == 42) {
			return spieler[Variablenkammer.getAktspieler()];
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

}