package cafeint;

/**
 * Diese Klasse generiert die Gastkarten, eine von zwei Arten von Spielkarten.<br>
 * Jeder Gastkarte werden ein Land und ein Geschlecht aus den zugehoerigen enums als Eigenschaften zugeordnet.<br>
 * <br>
 * <b>Land</b> Dies ist die Aufzaehlung aller Laender.<br>
 * <b>Geschlecht</b> Dies ist die Aufzaehlung der zwei Geschlechter.<br>
 * <b>land</b> Das ist das zugeordnete Land.<br>
 * <b>geschlecht</b> Das ist das zugeordnete Geschlecht.<br>
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Gastkarte {
	
	public enum Land {DE, FR, GB, US, TR, IN, CN, RU, ES, CU, CF, IT, JOKER};
	public enum Geschlecht {Mann, Frau};
	 
	private final Land land;
	private final Geschlecht geschlecht;
	 
	public Gastkarte(Land land, Geschlecht geschlecht){
		this.land = land;
		this.geschlecht= geschlecht;
	}
	
	/**
	 * Die toString()-Methode macht aus den Eigenschaften der Karte einen String, den man spaeter beim Spielstandspeichern benoetigt.
	 */
	public String toString(){
		return land + ":" + geschlecht;
	}

	/**
	 * @return Gibt das Land zurueck.
	 */
	public Land getLand() {
		return land;
	}

	/**
	 * @return Gibt das Geschlecht zurueck.
	 */
	public Geschlecht getGeschlecht() {
		return geschlecht;
	}
	
	/**
	 * Dies ist eine statische Methode nach Modell der parse-Methoden. Es macht aus einem String wieder eine Gastkarte.<br>
	 * Die Methode ist das Gegenteil zur toString()-Methode.
	 * 
	 * @param gstkstring Nimmt den String der Gastkarte entgegen, der mit toString() erstellt wurde.
	 * @return Gibt eine fertige Gastkarte zurueck.
	 */
	public static Gastkarte parseGastkarte(String gstkstring) {
		if(!gstkstring.equals("null")) {
			String[] attribut = gstkstring.split(":");
			Land land = Land.valueOf(attribut[0]);
			Geschlecht geschlecht = Geschlecht.valueOf(attribut[1]);
			Gastkarte gastkarte = new Gastkarte(land, geschlecht);
			return gastkarte;
		} else {
			return null;
		}
	}

}