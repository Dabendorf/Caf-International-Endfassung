package cafeint;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public final class Bildecke extends JPanel {
	
	private BufferedImage bi;
	
	protected void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		bildLaden();
		if(bi!=null) {
	    	gr.drawImage(bi, 0, 0, getWidth(), getHeight(), null);
	    }
	}
	
	private void bildLaden() {
		if(bi==null) {
			String key = "./icon.png";
			bi = Variablen.getTischcache().get(key);
		}
	}
}