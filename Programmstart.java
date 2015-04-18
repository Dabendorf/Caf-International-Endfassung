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
    	
    	//13*2 = 26 Gaeste + 2 Platzhalter
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
	
	private void spielfeldgenerieren() {
		for(int n=0;n<12;n++) {
			Variablenkammer.getTische().add(new Tisch());
			//new Spielzuege().legetischkarte(n); //hier muss dann noch eine Karte abgelegt werden
		}
		for(int n=0;n<24;n++) {
			Variablenkammer.getStuehle().add(new Stuhl());
		}
	}
	
	private void zellelementzuordnung() {
		for(int n=0;n<Spielfeld.getSpielfeldtisch().size();n++) {
			Spielfeld.getSpielfeldtisch().get(n).setTisch(Variablenkammer.getTische().get(n));
			Variablenkammer.getTische().get(n).setSpielzelle(Spielfeld.getSpielfeldtisch().get(n));
		}
		for(int n=0;n<Spielfeld.getSpielfeldstuhl().size();n++) {
			Spielfeld.getSpielfeldstuhl().get(n).setStuhl(Variablenkammer.getStuehle().get(n));
			Variablenkammer.getStuehle().get(n).setSpielzelle(Spielfeld.getSpielfeldstuhl().get(n));
		}
	}
	
	private void tischstuhlzuordnung() {
		int[] stuhl1 = {11, 1, 2, 3, 4, 5, 6, 7, 7, 8, 9, 9};
		int[] stuhl2 = {12, 2, 3, 4, 5, 6, 7,18, 8, 9,10,10};
		int[] stuhl3 = {13,12,13, 5,15,16,17,19,20,21,22,11};
		int[] stuhl4 = { 0,13,14,14,16,17,18,20,21,22,23, 0};
		for(int i=0;i<12;i++) {
			Variablenkammer.getTische().get(i).setStuehle(stuhl1[i],stuhl2[i],stuhl3[i],stuhl4[i]);
		}
	}

}