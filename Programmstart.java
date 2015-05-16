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

/**
 * Die Klasse Programmstart ist eine von zwei Klassen, die bei Aufruf des Spiels relevant sind.
 * Sie laedt saemtliche graphischen Spielelemente und muss auch bei Neustart eines Spiels nicht neu aufgerufen werden.<br>
 * 
 * @author Lukas Schramm
 * @version 1.0
 * 
 */

public class Programmstart {
	
	/**
	 * Diese Methode ruft alle anderen Methoden zum Start des Programmes auf.
	 */
	public void grafikladen() {
		
		bilderladen();
		spielfeldgenerieren();
		tischstuhlzuordnung();
		zellelementzuordnung();
	}
	
	/**
	 * Diese Methode ueberprueft das Betriebssystem des Nutzers und gibt eine Warnmeldung fuer alle Nutzer von Windows aus,
	 * da das Programm expliziet auf und fuer Unix-basierende Systeme entwickelt wurde und auf Windows moeglicherweise zu Problemen fueren kann.
	 */
	public void sysWin() {
		if(System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
			Meldungen msgbox = new Meldungen();
			JOptionPane.showMessageDialog(null, msgbox.windows, msgbox.falschessystem, JOptionPane.WARNING_MESSAGE);
		}
    }
	
	/**
	 * Diese Methode fragt bei Programmstart, wenn man kein altes Spiel wiederhergestellt hat nach den Spielernamen.<br>
	 * Es gibt auch Fehlermeldungen aus, wenn die beiden Namen gleich sind oder ein Name nicht eingegeben wurde.
	 */
	public void namensfrage() {
		Meldungen msgbox = new Meldungen();
		
		JTextField spielername00 = new JTextField(new Feldbegrenzung(12), "", 0);
		JTextField spielername01 = new JTextField(new Feldbegrenzung(12), "", 0);
		
		Object[] namensfrage = {msgbox.spielername+"1", spielername00, msgbox.spielername+"2", spielername01};
	    JOptionPane pane = new JOptionPane(namensfrage, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
	    pane.createDialog(null, msgbox.fragespielername).setVisible(true);
	    
	    Variablen.getSpieler(0).setName(spielername00.getText());
	    Variablen.getSpieler(1).setName(spielername01.getText());
	    
	    if(Variablen.getSpieler(0).getName().equals("") || Variablen.getSpieler(1).getName().equals("")) {
	    	JOptionPane.showMessageDialog(null, msgbox.spielernamevergessen, msgbox.titelunvollstaendig, JOptionPane.ERROR_MESSAGE);
	    	namensfrage();
	    } else if(Variablen.getSpieler(0).getName().equalsIgnoreCase(Variablen.getSpieler(1).getName())) {
	    	JOptionPane.showMessageDialog(null, msgbox.spielernamengleich, msgbox.titelnamensgleichheit, JOptionPane.ERROR_MESSAGE);
	    	namensfrage();
	    }
	}
	
	/**
	 * Diese Methode laedt bei Programmstart saemtliche im Spiel benoetigten Bilder in zwei TreeMaps,
	 * sodass sie nur durch Eingabe des Keys jederzeit von ueberall aufgerufen werden koennen.
	 */
	private void bilderladen() {
    	String key = null;
    	BufferedImage bitisch = null;
		BufferedImage bistuhl = null;
		
        for(Land land : Land.values()) {
        	try {
        		key = "./tisch_"+land+".png";
                URL url = new URL(BaseURL.getJarBase(Spielfeld.class), key);
                bitisch = ImageIO.read(url);
                Variablen.getTischcache().put(key, bitisch);
            } catch (MalformedURLException e) {} catch (IOException e) {}
        	
        	for(Geschlecht geschlecht : Geschlecht.values()) {
    			try {
    				key = "./gast_"+land+"_"+geschlecht+".png";
	                URL url = new URL(BaseURL.getJarBase(Spielfeld.class), key);
	                bistuhl = ImageIO.read(url);
	                Variablen.getStuhlcache().put(key, bistuhl);
	            } catch (MalformedURLException e) {} catch (IOException e) {}
    		}
        }
        
        String[] tischbilder = {"./tisch_leer.png", "./stapel_tische.png", "./icon.png"};
        String[] stuhlbilder = {"./stuhl_leer.png", "./stapel_gaeste.png"};
        
        for(String str:tischbilder) {
        	try {
        		key = str;
                URL url = new URL(BaseURL.getJarBase(Spielfeld.class), key);
                bitisch = ImageIO.read(url);
                Variablen.getTischcache().put(key, bitisch); 
            } catch (MalformedURLException e) {} catch (IOException e) {}
        }
        
        for(String str:stuhlbilder) {
        	try {
        		key = str;
                URL url = new URL(BaseURL.getJarBase(Spielfeld.class), key);
                bistuhl = ImageIO.read(url);
                Variablen.getStuhlcache().put(key, bistuhl);
            } catch (MalformedURLException e) {} catch (IOException e) {}
        }
    }
	
	/**
	 * Diese Methode erstellt die 12 Tische und 24 Stuehle, mit denen intern gearbeitet wird.
	 */
	private void spielfeldgenerieren() {
		for(int n=0;n<12;n++) {
			Variablen.getTische().add(new Tisch());
		}
		for(int n=0;n<24;n++) {
			Variablen.getStuehle().add(new Stuhl());
		}
	}
	
	/**
	 * Diese Methode ordnet jedem Tisch seine vier Stuehle zu. Ausserdem loest die aufgerufene Methode setStuehle auch die gegenteilige Aktion,
	 * die Zuordnung aller Tische zu ihren Stuehlen vollautomatisch aus.
	 */
	private void tischstuhlzuordnung() {
		int[] stuhl1 = {11, 1, 2, 3, 4, 5, 6, 7, 7, 8, 9, 9};
		int[] stuhl2 = {12, 2, 3, 4, 5, 6, 7,18, 8, 9,10,10};
		int[] stuhl3 = {13,12,13, 5,15,16,17,19,20,21,22,11};
		int[] stuhl4 = { 0,13,14,14,16,17,18,20,21,22,23, 0};
		for(int i=0;i<12;i++) {
			Variablen.getTische().get(i).setStuehle(stuhl1[i],stuhl2[i],stuhl3[i],stuhl4[i]);
		}
	}
	
	/**
	 * Diese Methode dient als Verbindungselement zwischen Grafik und Internem Spielalgorithmus.
	 * Sie ordnet jedem Tisch und jedem Stuhl seine graphische Spielzelle zu und andersherum.
	 */
	private void zellelementzuordnung() {
		for(int n=0;n<Spielfeld.getSpielfeldtisch().size();n++) {
			Spielfeld.getSpielfeldtisch().get(n).setTisch(Variablen.getTische().get(n));
			Variablen.getTische().get(n).setSpielzelle(Spielfeld.getSpielfeldtisch().get(n));
		}
		for(int n=0;n<Spielfeld.getSpielfeldstuhl().size();n++) {
			Spielfeld.getSpielfeldstuhl().get(n).setStuhl(Variablen.getStuehle().get(n));
			Variablen.getStuehle().get(n).setSpielzelle(Spielfeld.getSpielfeldstuhl().get(n));
		}
	}

}