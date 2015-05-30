package cafeint;

/**
 * Diese Klasse fasst Highscores mit Spielername, Rekordpunktzahl und Systemzeit zusammen.
 * 
 * @author Lukas Schramm
 * @version 1.0
 * 
 */
public class Highscore implements Comparable<Highscore> {
	
	private long systemzeit;
	private int punktzahl;
	private String name;
	
	public Highscore(long systemzeit, int punktzahl, String name) {
		this.systemzeit = systemzeit;
		this.punktzahl = punktzahl;
		this.name = name;
	}

	public long getSystemzeit() {
		return systemzeit;
	}

	public int getPunktzahl() {
		return punktzahl;
	}

	public String getName() {
		return name;
	}

	/**
	 * Diese compareTo-Methode vergleicht die Highscores anhand ihrer Punktzahl.
	 */
	public int compareTo(Highscore o) {
		return ((Integer)punktzahl).compareTo((Integer)o.punktzahl);
	}

}