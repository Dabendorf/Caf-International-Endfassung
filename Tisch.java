package cafeint;

public class Tisch {
	
	private Laenderkarte laenderkarte; //Tisch leeren -> Erst feststellen, dann leeren, für Fehlervermeidung und PktBerechnung bei mehreren Tischen
    private Stuhl[] stuehle;
	private Spielzelle sz;
	private boolean zuleeren = false;
	
	public Laenderkarte getLaenderkarte() {
		return laenderkarte;
	}
	
	public void setLand(Laenderkarte land) {
		this.laenderkarte = land;
		if(sz!=null) {
			this.sz.repaint();
		}
	}
	
	public Stuhl[] getStuehle() {
		return stuehle;
	}
	
	public void setStuehle(int...stuehleNr) {
		Stuhl[] stuehletemp = new Stuhl[stuehleNr.length];
		int i=0;
		for(int stuhlNr : stuehleNr) {
			stuehletemp[i] = Variablenkammer.getStuehle().get(stuhlNr);
			int num = Variablenkammer.getStuehle().indexOf(stuehletemp[i]);
			i++;
			Variablenkammer.getStuehle().get(num).addTisch(this);
		}
		this.stuehle = stuehletemp;
	}
	
	public Spielzelle getSpielzelle() {
		return sz;
	}
	
	public void setSpielzelle(Spielzelle sz) {
		this.sz = sz;
	}

	public boolean isZuleeren() {
		return zuleeren;
	}

	public void setZuleeren(boolean zuleeren) {
		this.zuleeren = zuleeren;
	}

}