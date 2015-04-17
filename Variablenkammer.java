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

	public static Meldungen getMsgbox() {
		return msgbox;
	}

}