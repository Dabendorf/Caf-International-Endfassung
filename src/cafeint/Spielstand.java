package cafeint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JOptionPane;

/**
 * Diese Klasse speichert Spielstaende von nicht beendeten Spielen und ruft sie beim naechsten Start neu auf.<br>
 * Um Spielstandmanipulation vorzubeugen, wird die Textdatei mit dem Spielstand mit Polyalphabetischer Substitution (Vigenere) verschluesselt.<br>
 * <br>
 * <b>dateiname</b> Dieser String speichert den Dateinamen, in welchem der Spielstand gespeichert wird.<br>
 * <b>schluessel</b> Dies ist der Schluessel, nach welchem die Spielstand-Datei verschluesselt wird.
 * 
 * @author Lukas Schramm
 * @version 1.1
 *
 */
public class Spielstand {
	
	/**Pfad zur Speicherdatei fuer Spieleinstellungen*/
	private String spielstanddatei = "files/spielstand.xml";
	/**Pfad zur Speicherdatei fuer Highscores*/
	private String highscoredatei = "files/bestenliste.xml";
	/**Schluessel fuer die Vigenereverschluesselung*/
	private char[] schluessel = "Heizoelrueckstossabdaempfung".toCharArray();
	/**Zu speichernde Propertieselemente*/
	private Properties props = new Properties();
	/**Die geladene Speicherdatei*/
	private File file;
	/**Objekt der Sprachspeicherklasse*/
	private Sprache spr = new Sprache();
	
	public Spielstand(int fileNum) {
		switch(fileNum) {
		case 0:file = new File(spielstanddatei);break;
		case 1:file = new File(highscoredatei);break;
		}
	}
	
	/**
	 * Diese Methode speichert den aktuellen Spielstand verschluesselt ab.
	 */
	public void speichern() {
		try {
			props.setProperty("spielangefangen",verschluesseln(String.valueOf(true)));
			props.setProperty("amZug",verschluesseln(String.valueOf(Variablen.getAktSpieler())));
			props.setProperty("zustand",verschluesseln(String.valueOf(Variablen.getZustand())));
			for(int i=0;i<2;i++) {
				props.setProperty("spielername"+i,verschluesseln(Variablen.getSpieler(i).getName()));
				props.setProperty("spielerpunkte"+i,verschluesseln(String.valueOf(Variablen.getSpieler(i).getPunkte())));
				for(int h=0;h<5;h++) {
					props.setProperty("handkarte"+i+"-"+h,verschluesseln(String.valueOf(Variablen.getSpieler(i).getHandkarten().get(h))));
				}
			}
			props.setProperty("anzahlGastkarten",verschluesseln(String.valueOf(Variablen.getGastkarten().size())));
			props.setProperty("anzahlLaenderkarten",verschluesseln(String.valueOf(Variablen.getLaenderkarten().size())));
			props.setProperty("anzahlBarkarten",verschluesseln(String.valueOf(Variablen.getBarkarten().size())));
			for(int i=0;i<Variablen.getGastkarten().size();i++) {
				props.setProperty("gastkarte"+i,verschluesseln(String.valueOf(Variablen.getGastkarten().get(i))));
			}
			for(int i=0;i<Variablen.getLaenderkarten().size();i++) {
				props.setProperty("laenderkarte"+i,verschluesseln(String.valueOf(Variablen.getLaenderkarten().get(i))));
			}
			for(int i=0;i<Variablen.getBarkarten().size();i++) {
				props.setProperty("barkarte"+i,verschluesseln(String.valueOf(Variablen.getBarkarten().get(i))));
			}
			for(int i=0;i<Variablen.getStuehle().size();i++) {
				props.setProperty("stuhl"+i,verschluesseln(String.valueOf(Variablen.getStuehle().get(i).getGast())));
				if(Variablen.getStuehle().get(i).isPartnerNoetig()) {
					int stuhlindex = Variablen.getStuehle().indexOf(Variablen.getStuehle().get(i));
					props.setProperty("gastAllein",verschluesseln(String.valueOf(stuhlindex)));
				}
			}
			for(int i=0;i<Variablen.getTische().size();i++) {
				props.setProperty("tisch"+i,verschluesseln(String.valueOf(Variablen.getTische().get(i).getLaenderkarte())));
			}
			
			FileOutputStream fileOut = new FileOutputStream(file);
			props.storeToXML(fileOut, "Cafe International Gespeicherter Spielstand");
			fileOut.close();
		} catch(IOException e) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				spr.dateiKaputt(spielstanddatei);
			}
		}
	}
	
	/**
	 * Diese Methode liest den gespeicherten Spielstand aus und entschluesselt ihn.
	 */
	public void laden() {
		try {
			FileInputStream fileInput = new FileInputStream(file);
			props.loadFromXML(fileInput);
			fileInput.close();
			boolean spielgespeichert = Boolean.valueOf(entschluesseln(props.getProperty("spielangefangen","false")));
			if(spielgespeichert) {
				Sprache msgbox = new Sprache();
				int menue = JOptionPane.showOptionDialog(null,msgbox.altesspielfrage,msgbox.altesspieltitel, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, msgbox.altesspieloptionen, msgbox.altesspieloptionen[0]);
	            if(menue == 0) {
	            	Variablen.setAktSpieler(Integer.valueOf(entschluesseln(props.getProperty("amZug"))));
	    			Variablen.setZustand(Integer.valueOf(entschluesseln(props.getProperty("zustand"))));
	    			for(int i=0;i<2;i++) {
	    				Variablen.getSpieler(i).setName(entschluesseln(props.getProperty("spielername"+i)));
	    				Variablen.getSpieler(i).setPunkte(Integer.valueOf(entschluesseln(props.getProperty("spielerpunkte"+i))));
	    				for(int h=0;h<5;h++) {
	    					Variablen.getSpieler(i).getHandkarten().add(Gastkarte.parseGastkarte(entschluesseln(props.getProperty("handkarte"+i+"-"+h))));
	    				}
	    			}
	    			Programmstart progst = new Programmstart();
	    			progst.grafikladen();
	    			Variablen.getStatistikecke().getInfz(0).punktzahlschreiben();
	    			Variablen.getStatistikecke().getInfz(1).punktzahlschreiben();
	    			Variablen.getStatistikecke().getInfz(Variablen.getAktSpieler()).faerben(true);
	    			int anzahlGastkarten = Integer.valueOf(entschluesseln(props.getProperty("anzahlGastkarten")));
	    			int anzahlLaenderkarten = Integer.valueOf(entschluesseln(props.getProperty("anzahlLaenderkarten")));
	    			int anzahlBarkarten = Integer.valueOf(entschluesseln(props.getProperty("anzahlBarkarten")));
	    			for(int i=0;i<anzahlGastkarten;i++) {
	    				Variablen.getGastkarten().add(Gastkarte.parseGastkarte(entschluesseln(props.getProperty("gastkarte"+i))));
	    			}
	    			for(int i=0;i<anzahlLaenderkarten;i++) {
	    				Variablen.getLaenderkarten().add(Laenderkarte.parseLaenderkarte(entschluesseln(props.getProperty("laenderkarte"+i))));
	    			}
	    			for(int i=0;i<anzahlBarkarten;i++) {
	    				Variablen.getBarkarten().add(Gastkarte.parseGastkarte(entschluesseln(props.getProperty("barkarte"+i))));
	    				Variablen.getBarkartenecke().getBarzelle(i).setGast(Variablen.getBarkarten().get(i));
	    			}
	    			for(int i=0;i<Variablen.getStuehle().size();i++) {
	    				Variablen.getStuehle().get(i).setStartGast(Gastkarte.parseGastkarte(entschluesseln(props.getProperty("stuhl"+i))));
	    			}
	    			for(int i=0;i<Variablen.getTische().size();i++) {
	    				Variablen.getTische().get(i).setLand(Laenderkarte.parseLaenderkarte(entschluesseln(props.getProperty("tisch"+i))));
	    			}
	    			String temp = props.getProperty("gastAllein","null");
	    			if(temp!="null") {
	    				int stuhlindex = Integer.valueOf(entschluesseln(temp));
	    				Variablen.getStuehle().get(stuhlindex).setPartnerNoetig(true);
	    				Variablen.getStuehle().get(stuhlindex).gruenfaerben();
	    			}
	    			CafeIntMain.getSpielframe().setVisible(true);
	            } else {
	            	neuesspiel();
	            }
			} else {
				neuesspiel();
			}
		} catch(Exception e) {
			neuesspiel();
		}
	}
	
	/**
	 * Diese Methode generiert ein neues Spiel fuer den fall, dass kein Spielstand vorhanden ist oder der Spieler ein neues Spiel starten moechte.
	 */
	private void neuesspiel() {
		Programmstart progst = new Programmstart();
		progst.startbildschirm();
	}
	
	/**
	 * Diese Methode loescht den Inhalt der Speicherdatei.
	 */
	public void loescheSpielstand() {
		try {
			props.setProperty("spielangefangen",verschluesseln(String.valueOf(false)));
			
			FileOutputStream fileOut = new FileOutputStream(file);
			props.storeToXML(fileOut, "Cafe International Leerer Spielstand");
			fileOut.close();
		} catch(IOException e) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				spr.dateiKaputt(spielstanddatei);
			}
		}
	}
	
	
	/**
	 * Diese Methode fuegt einen Highscore zur HighscoreDatei hinzu.
	 * @param hsc Der hinzugefuegte Highscore.
	 * @param num Die Nummer des Highscores.
	 */
	public void highscorehinzufuegen(Highscore hsc,int num) {
		try {
			String temp = String.valueOf(hsc.getSystemzeit())+","+hsc.getPunktzahl()+","+hsc.getName();
			props.setProperty("highscore"+num, verschluesseln(temp));
			props.setProperty("anzahlHighscores", verschluesseln(String.valueOf(num+1)));
			FileOutputStream fileOut = new FileOutputStream(file);
			props.storeToXML(fileOut, "Cafe International Highscores");
			fileOut.close();
		} catch(IOException e) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				spr.dateiKaputt(highscoredatei);
			}
		}
	}
	
	/**
	 * Diese Methode gibt einen Array aller abgespeicherten Highscores zurueck.
	 * @return Gibt Highscore-Array zurueck.
	 */
	public Highscore[] allesHighscoresLaden() {
		try {
			FileInputStream fileInput = new FileInputStream(file);
			props.loadFromXML(fileInput);
			fileInput.close();
		} catch(IOException e) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				spr.dateiKaputt(highscoredatei);
			}
		}
		
		int anz;
		try {
			anz = Integer.valueOf(entschluesseln(props.getProperty("anzahlHighscores")));
		} catch(Exception e) {
			anz = 0;
		}
		
		Highscore[] highscores = new Highscore[anz];
		for(int i=0;i<highscores.length;i++) {
			highscores[i] = highscoreAufrufen("highscore"+i);
		}
		return highscores;
	}
	
	/**
	 * Diese Methode gibt einen einzelnen Highscore zurueck.
	 * @param key Key unter dem der Highscore intern abgespeichert ist.
	 * @return Gibt Highscore zurueck.
	 */
	private Highscore highscoreAufrufen(String key) {
		try {
			String temp = entschluesseln(props.getProperty(key));
			String[] temp2 = temp.split(",");
			Highscore hsc = new Highscore(Long.valueOf(temp2[0]),Integer.valueOf(temp2[1]),temp2[2]);
			return hsc;
		} catch (NullPointerException np) { 
			return null;
		}
	}
	
	/**
	 * Diese Methode verschluesselt den eingegebenen String.
	 * @param original Nimmt den Originalstring entgegen.
	 * @return Gibt den verschluesselten String aus.
	 */
	private String verschluesseln(String original) {
		char[] temp = original.toCharArray();
		String crypt = new String("");
		for(int i=0;i<temp.length;i++) {
			int result = (temp[i] + schluessel[i%schluessel.length]) % 256;
			crypt += (char) result;
		}
		return crypt;
	}
 	
 	/**
	 * Diese Methode entschluesselt den eingegebenen String.
	 * @param encrypted Nimmt den verschluesselten String entgegen.
	 * @return Gibt den entschluesselten String aus.
	 */
 	private String entschluesseln(String encrypted) {
  		char[] temp = encrypted.toCharArray();
  		String decrypt = new String("");
  		for(int i=0;i<temp.length;i++) {
  			int result;
  			if(temp[i] - schluessel[i%schluessel.length] < 0) {
  				result =  (temp[i] - schluessel[i%schluessel.length]) + 256;
  			} else {
  				result = (temp[i] - schluessel[i%schluessel.length]) % 256;
  			}
  			decrypt += (char) result;
  		}
  		return decrypt;
 	}
}