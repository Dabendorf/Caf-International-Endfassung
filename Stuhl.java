package cafeint;

import java.util.ArrayList;
import java.util.Arrays;

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
	}*/
	
	public void addTisch(Tisch tisch) {
		ArrayList<Tisch> tischetemp = new ArrayList<Tisch>(Arrays.asList(this.tische));
		tischetemp.add(tisch);
		
		Tisch[] tischetemparr = new Tisch[tischetemp.size()];
		for(Tisch t:tischetemp) {
			tischetemparr[tischetemp.indexOf(t)] = t;
		}
		this.tische = tischetemparr;
	}
	
	/*public Spielzelle getSpielzelle() {
		return sz;
	}*/
	
	public void setSpielzelle(Spielzelle sz) {
		this.sz = sz;
	}
	
	//weitere Stuhleigenschaften folgen, wenn die Relevanz da ist

}