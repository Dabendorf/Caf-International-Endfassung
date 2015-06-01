package cafeint;

/**
 * Diese Klasse stellt die Tische, neben den Stuehlen das wichtigste Element des Spiels dar.<br>
 * Jeder Tisch grenzt an vier Stuehle.
 * Jedem Tisch wird eine Nationalitaet zugeordnet.<br>
 * <br>
 * <b>laenderkarte</b> Dies ist die Nationalitaet, die dem Tisch zugeordnet wurde.<br>
 * <b>stuehle</b> Dies sind die vier Stuehle, an welchen der Tisch steht.<br>
 * <b>sz</b> Das ist die graphische Spielzelle zum Tisch.<br>
 * <b>zuleeren</b> Dieser boolean zeigt an, ob ein Tisch vormarkiert wurde, um gleich geleert zu werden.<br>
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Tisch {
	
	private Laenderkarte laenderkarte;
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
	
	/**
	 * Diese Methode ist kein einfacher Setter wie die anderen Methoden. Sie nimmt die Menge an Stuehlen entgegen, die zum Tisch gehoeren.<br>
	 * Ausserdem fuert sie automatisch die gegenteilige Aktion durch und ordnet sich selbst als Tisch dem jeweiligen Stuhl zu.<br>
	 * Dies beugt Fehler bei der Zuordnung vor.
	 * @param stuehleNr Dies ist die Menge an Stuehlen, die hinzugefuegt wird.
	 */
	public void setStuehle(int...stuehleNr) {
		Stuhl[] stuehletemp = new Stuhl[stuehleNr.length];
		int i=0;
		for(int stuhlNr : stuehleNr) {
			stuehletemp[i] = Variablen.getStuehle().get(stuhlNr);
			int num = Variablen.getStuehle().indexOf(stuehletemp[i]);
			i++;
			Variablen.getStuehle().get(num).addTisch(this);
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