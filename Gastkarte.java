package cafeint;

public class Gastkarte {
	
	public enum Land {DE, FR, GB, US, TR, IN, CN, RU, ES, CU, CF, IT, JOKER};
	public enum Geschlecht {Mann, Frau};
	 
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