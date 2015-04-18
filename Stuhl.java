package cafeint;

import java.util.ArrayList;

public class Stuhl {
	
	private Gastkarte gast;
	private ArrayList<Tisch> tische = new ArrayList<Tisch>();
    private Spielzelle sz;
    
	public Gastkarte getGast() {
		return gast;
	}
	
	/*public void setGast(Gastkarte gast) {
		this.gast = gast;
	}
	
	public Tisch[] getTische() {
		return tische;
	}*/
	
	public void addTisch(Tisch tisch) {
		this.tische.add(tisch);
	}
	
	/*public Spielzelle getSpielzelle() {
		return sz;
	}*/
	
	public void setSpielzelle(Spielzelle sz) {
		this.sz = sz;
	}
	
	//weitere Stuhleigenschaften folgen, wenn die Relevanz da ist

}