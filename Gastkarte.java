package cafeint;

public class Gastkarte {
	
	public enum Geschlecht {Mann, Frau};
	public enum Land {DE, FR, GB, US, TR, IN, CN, RU, ES, CU, CF, IT, JOKER};
	 
	private final Land land;
	private final Geschlecht geschlecht;
	 
	public Gastkarte(Land land, Geschlecht geschlecht){
		this.land = land;
		this.geschlecht= geschlecht;
	}
	 
	public String toString(){
		return land + ":" + geschlecht;
	}

	public Land getLand() {
		return land;
	}

	public Geschlecht getGeschlecht() {
		return geschlecht;
	}

}