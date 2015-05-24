package cafeint;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Diese Klasse stellt zum Start des Spiels ein kurzes Ladefenster dar, welches das Titelbild des Spiels anzeigt.<br>
 * <br>
 * <b>bi</b> Hier wird das angezeigte große Bild geladen.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */

public final class Startbildschirm extends JPanel {
	
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
	 * Diese Methode laedt das anzuzeigende Bild.
	 */
	private void bildLaden() {
		if(bi==null) {
			String key = "./start.png";
			try {
				URL url = new URL(BaseURL.getJarBase(Spielfeld.class), key);
				bi = ImageIO.read(url);
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}