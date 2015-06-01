package cafeint;

import java.util.ArrayList;
import java.util.List;

/**
 * Diese Klasse fasst einen Spieler zusammen und speichert seinen Namen, seine Punktzahl und seine fuenf Handkarten.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Spieler {
	
	private String name;
    private int punkte = 0;
    private List<Gastkarte> handkarten = new ArrayList<Gastkarte>();
    
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getPunkte() {
		return punkte;
	}
	
	public void setPunkte(int punkte) {
		this.punkte = punkte;
	}
	
	public List<Gastkarte> getHandkarten() {
		return handkarten;
	}

}