package cafeint;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * Diese Klasse ist eine von 5 Klassen, aus denen sich die graphische Oberflaeche zusammensetzt.
 * Es stellt die Flaeche unten rechts dar. In ihr wird aus Gruenden der Aesthetik lediglich ein schickes Bild eines Eisbechers, das Symbol des Spiels angezeigt.
 * 
 * @param bi Hier wird das angezeigte Bilder geladen.
 * 
 * @author Lukas
 * @version 1.0
 *
 */

public final class Bildecke extends JPanel {
	
	private BufferedImage bi;
	
	/**
	 * In dieser Paintmethode wird nur das Bild geladen, welches das gesamte Spiel ueber gezeigt wird.
	 */
	@Override
	protected void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		bildLaden();
		if(bi!=null) {
	    	gr.drawImage(bi, 0, 0, getWidth(), getHeight(), null);
	    }
	}
	
	/**
	 * Diese Methode laedt das anzuzeigende Bild aus dem Bilderspeicher.
	 */
	private void bildLaden() {
		if(bi==null) {
			String key = "./icon.png";
			bi = Variablen.getTischcache().get(key);
		}
	}
}