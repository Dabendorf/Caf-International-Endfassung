package cafeint;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import cafeint.Gastkarte.Geschlecht;
import cafeint.Gastkarte.Land;

public class Programmstart {
	
	public boolean SysWin() {
        return (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0);
    }
	
	public void namensfrage() { //Namensfrage muss ausgeweitet werden
		Meldungen msgbox = new Meldungen();
		
		JTextField spielername00 = new JTextField(new Feldbegrenzung(12), "", 0);
		JTextField spielername01 = new JTextField(new Feldbegrenzung(12), "", 0);
		
		/*Object[] namensfrage = {msgbox.spielernameint(1), spielername00, msgbox.spielernameint(2), spielername01};
	    JOptionPane pane = new JOptionPane(namensfrage, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
	    pane.createDialog(null, msgbox.fragespielername).setVisible(true);
	    
	    CafeMain.getSpieler(0).setName(spielername00.getText());
	    CafeMain.getSpieler(1).setName(spielername01.getText());
	    
	    if(CafeMain.getSpieler(0).getName().equals("") || CafeMain.getSpieler(1).getName().equals("")) {
	    	JOptionPane.showMessageDialog(null, msgbox.spielernamevergessen, msgbox.titelunvollstaendig, JOptionPane.ERROR_MESSAGE);
	    	namensfrage();
	    } else if(CafeMain.getSpieler(0).getName().equalsIgnoreCase(CafeMain.getSpieler(1).getName())) {
	    	JOptionPane.showMessageDialog(null, msgbox.spielernamengleich, msgbox.titelnamensgleichheit, JOptionPane.ERROR_MESSAGE);
	    	namensfrage();
	    }
	    Uebersichtsecke.getInfz(0).punktzahlschreiben();
        Uebersichtsecke.getInfz(1).punktzahlschreiben();*/
	}
	
	private void bilderladen() {
    	String key = null;
    	BufferedImage bitisch = null;
		BufferedImage bistuhl = null;
		
		//12 Laender + 2 Platzhalter
        for(Land land : Land.values()) {
        	try {
        		key = "./tisch_"+land+".png";
                URL url = new URL(BaseURL.getJarBase(Spielfeld.class), key);
                bitisch = ImageIO.read(url);
                Variablenkammer.getTischcache().put(key, bitisch);
            } catch (MalformedURLException e) {} catch (IOException e) {}
        }
        
    	try {
    		key = "./tisch_leer.png";
            URL url = new URL(BaseURL.getJarBase(Spielfeld.class), key);
            bitisch = ImageIO.read(url);
            Variablenkammer.getTischcache().put(key, bitisch); 
        } catch (MalformedURLException e) {} catch (IOException e) {}
    	
    	try {
			key = "./stapel_tische.png";
            URL url = new URL(BaseURL.getJarBase(Spielfeld.class), key);
            bitisch = ImageIO.read(url);
            Variablenkammer.getTischcache().put(key, bitisch);
        } catch (MalformedURLException e) {} catch (IOException e) {}
    	
    	//13*2 = 26 Gäste + 2 Platzhalter
    	for(Land land : Land.values()) {
    		for(Geschlecht geschlecht : Geschlecht.values()) {
    			try {
    				key = "./gast_"+land+"_"+geschlecht+".png";
	                URL url = new URL(BaseURL.getJarBase(Spielfeld.class), key);
	                bistuhl = ImageIO.read(url);
	                Variablenkammer.getStuhlcache().put(key, bistuhl);
	            } catch (MalformedURLException e) {} catch (IOException e) {}
    		}
    	}
    	
    	try {
    		key = "./stuhl_leer.png";
            URL url = new URL(BaseURL.getJarBase(Spielfeld.class), key);
            bistuhl = ImageIO.read(url);
            Variablenkammer.getStuhlcache().put(key, bistuhl);
        } catch (MalformedURLException e) {} catch (IOException e) {}
        
        try {
        	key = "./stapel_gaeste.png";
            URL url = new URL(BaseURL.getJarBase(Spielfeld.class), key);
            bistuhl = ImageIO.read(url);
            Variablenkammer.getStuhlcache().put(key, bistuhl);
        } catch (MalformedURLException e) {} catch (IOException e) {}
        
        //Bild unten Rechts in Tischcache
        try {
        	key = "./icon.png";
            URL url = new URL(BaseURL.getJarBase(Spielfeld.class), key);
            bitisch = ImageIO.read(url);
            Variablenkammer.getTischcache().put(key, bitisch);
        } catch (MalformedURLException e) {} catch (IOException e) {}
    }
	
	//private void spielfeldgenerieren() {
	//private void zellelementzuordnung() {
	//private void tischstuhlzuordnung() {

}