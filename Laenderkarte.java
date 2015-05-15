package cafeint;

import cafeint.Gastkarte.Land;

public class Laenderkarte {
	
	private final Land land;
	 
	public Laenderkarte(Land land) {
		this.land = land;
	}
	 
	public String toString() {
		return land + "";
	}

	public Land getLand() {
		return land;
	}
	
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