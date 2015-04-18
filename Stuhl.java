package cafeint;

public class Stuhl {
	
	private Gastkarte gast;
    private Tisch[] tische;
    private Spielzelle sz;
    
	public Gastkarte getGast() {
		return gast;
	}
	
	/*public void setGast(Gastkarte gast) {
		this.gast = gast;
	}
	
	public Tisch[] getTische() {
		return tische;
	}
	
	public void setTische(Tisch[] tische) {
		this.tische = tische;
	}
	
	public Spielzelle getSpielzelle() {
		return sz;
	}*/
	
	public void setSpielzelle(Spielzelle sz) {
		this.sz = sz;
	}
	
	//weitere Stuhleigenschaften folgen, wenn die Relevanz da ist

}