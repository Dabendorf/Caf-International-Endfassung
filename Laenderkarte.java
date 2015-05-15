package cafeint;

import cafeint.Gastkarte.Land;

/**
 * Diese Klasse generiert die Laenderkarten, eine von zwei Arten von Spielkarten.<br>
 * Jeder Laenderkarte wird ein Land aus dem zugehoerigen enum als Eigenschaft zugeordnet.<br>
 * <br>
 * <b>land</b> Das ist das zugeordnete Land.<br>
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */

public class Laenderkarte {
	
	private final Land land;
	 
	public Laenderkarte(Land land) {
		this.land = land;
	}
	
	/**
	 * Die toString()-Methode macht aus der Eigenschaft der Karte einen String, den man spaeter beim Spielstandspeichern benoetigt.
	 */
	public String toString() {
		return land + "";
	}

	/**
	 * @return Gibt das Land zurueck.
	 */
	public Land getLand() {
		return land;
	}
	
	/**
	 * Dies ist eine statische Methode nach Modell der parse-Methoden. Es macht aus einem String wieder eine Laenderkarte.<br>
	 * Die Methode ist das Gegenteil zur toString()-Methode.
	 * 
	 * @param landkstring Nimmt den String der Gastkarte entgegen, der mit toString() erstellt wurde.
	 * @return Gibt eine fertige Laenderkarte zurueck.
	 */
	public static Laenderkarte parseLaenderkarte(String landkstring) {
		if(!landkstring.equals("null")) {
			Land land = Land.valueOf(landkstring);
			Laenderkarte laenderkarte = new Laenderkarte(land);
			return laenderkarte;
		} else {
			return null;
		}
	}

}