package cafeint;

import cafeint.Gastkarte.Land;

public class Laenderkarte {
	
	private final Land land;
	 
	protected Laenderkarte(Land land) {
		this.land = land;
	}
	 
	public String toString() {
		return land + "";
	}

	public Land getLand() {
		return land;
	}

}