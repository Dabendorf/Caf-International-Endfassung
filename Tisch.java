package cafeint;

public class Tisch {
	
	private Laenderkarte land; //Tisch leeren -> Erst feststellen, dann leeren, für Fehlervermeidung und PktBerechnung bei mehreren Tischen
    private Stuhl[] stuehle;
	private Spielzelle sz;
	
	public Laenderkarte getLand() {
		return land;
	}
	
	/*public void setLand(Laenderkarte land) {
		this.land = land;
	}
	
	public Stuhl[] getStuehle() {
		return stuehle;
	}
	
	public void setStuehle(Stuhl[] stuehle) {
		this.stuehle = stuehle;
	}
	
	public Spielzelle getSpielzelle() {
		return sz;
	}*/
	
	public void setSpielzelle(Spielzelle sz) {
		this.sz = sz;
	}
	
	//weitere Tischeigenschaften folgen, wenn die Relevanz da ist

}