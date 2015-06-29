package cafeint;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
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
 * @version 1.0
 *
 */
public class Spielstand {
	
	private String spielstanddatei = "spielstand.txt";
	private String highscoredatei = "bestenliste.txt";
	private char[] schluessel = "Heizoelrueckstossabdaempfung".toCharArray();
	
	/**
	 * Diese Methode speichert den aktuellen Spielstand verschluesselt ab.
	 */
	public void speichern() {
		Properties spielstand = ladeProperties(spielstanddatei);
		spielstand.clear();
		spielstand.setProperty("spielangefangen",verschluesseln(String.valueOf(true)));
		spielstand.setProperty("amZug",verschluesseln(String.valueOf(Variablen.getAktSpieler())));
		spielstand.setProperty("zustand",verschluesseln(String.valueOf(Variablen.getZustand())));
		for(int i=0;i<2;i++) {
			spielstand.setProperty("spielername"+i,verschluesseln(Variablen.getSpieler(i).getName()));
			spielstand.setProperty("spielerpunkte"+i,verschluesseln(String.valueOf(Variablen.getSpieler(i).getPunkte())));
			for(int h=0;h<5;h++) {
				spielstand.setProperty("handkarte"+i+"-"+h,verschluesseln(String.valueOf(Variablen.getSpieler(i).getHandkarten().get(h))));
			}
		}
		spielstand.setProperty("anzahlGastkarten",verschluesseln(String.valueOf(Variablen.getGastkarten().size())));
		spielstand.setProperty("anzahlLaenderkarten",verschluesseln(String.valueOf(Variablen.getLaenderkarten().size())));
		spielstand.setProperty("anzahlBarkarten",verschluesseln(String.valueOf(Variablen.getBarkarten().size())));
		for(int i=0;i<Variablen.getGastkarten().size();i++) {
			spielstand.setProperty("gastkarte"+i,verschluesseln(String.valueOf(Variablen.getGastkarten().get(i))));
		}
		for(int i=0;i<Variablen.getLaenderkarten().size();i++) {
			spielstand.setProperty("laenderkarte"+i,verschluesseln(String.valueOf(Variablen.getLaenderkarten().get(i))));
		}
		for(int i=0;i<Variablen.getBarkarten().size();i++) {
			spielstand.setProperty("barkarte"+i,verschluesseln(String.valueOf(Variablen.getBarkarten().get(i))));
		}
		for(int i=0;i<Variablen.getStuehle().size();i++) {
			spielstand.setProperty("stuhl"+i,verschluesseln(String.valueOf(Variablen.getStuehle().get(i).getGast())));
			if(Variablen.getStuehle().get(i).isPartnerNoetig()) {
				int stuhlindex = Variablen.getStuehle().indexOf(Variablen.getStuehle().get(i));
				spielstand.setProperty("gastAllein",verschluesseln(String.valueOf(stuhlindex)));
			}
		}
		for(int i=0;i<Variablen.getTische().size();i++) {
			spielstand.setProperty("tisch"+i,verschluesseln(String.valueOf(Variablen.getTische().get(i).getLaenderkarte())));
		}
		
		try {
			spielstand.store(new FileWriter("dateien/"+spielstanddatei),"Spielstand gespeichert");
		} catch (IOException e) {
			absturz("dateien/"+spielstanddatei);
		}
	}
	
	/**
	 * Diese Methode guckt, ob ein gespeichertes Spiel vorliegt und fragt den Spieler, ob er dieses Spiel weiterspielen moechte.<br>
	 * Wenn er dies moechte, wird der Spielstand geladen. Andererseits wird ein neues Spiel generiert.
	 */
	public void laden() {
		Properties spielstand = ladeProperties(spielstanddatei);
		boolean spielgespeichert = Boolean.valueOf(entschluesseln(spielstand.getProperty("spielangefangen","false")));
		if(spielgespeichert) {
			Meldungen msgbox = new Meldungen();
			int menue = JOptionPane.showOptionDialog(null,msgbox.altesspielfrage,msgbox.altesspieltitel, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, msgbox.altesspieloptionen, msgbox.altesspieloptionen[0]);
            if(menue == 0) {
            	Variablen.setAktSpieler(Integer.valueOf(entschluesseln(spielstand.getProperty("amZug"))));
    			Variablen.setZustand(Integer.valueOf(entschluesseln(spielstand.getProperty("zustand"))));
    			for(int i=0;i<2;i++) {
    				Variablen.getSpieler(i).setName(entschluesseln(spielstand.getProperty("spielername"+i)));
    				Variablen.getSpieler(i).setPunkte(Integer.valueOf(entschluesseln(spielstand.getProperty("spielerpunkte"+i))));
    				for(int h=0;h<5;h++) {
    					Variablen.getSpieler(i).getHandkarten().add(Gastkarte.parseGastkarte(entschluesseln(spielstand.getProperty("handkarte"+i+"-"+h))));
    				}
    			}
    			Programmstart progst = new Programmstart();
    			progst.grafikladen();
    			Variablen.getStatistikecke().getInfz(0).punktzahlschreiben();
    			Variablen.getStatistikecke().getInfz(1).punktzahlschreiben();
    			Variablen.getStatistikecke().getInfz(Variablen.getAktSpieler()).faerben(true);
    			int anzahlGastkarten = Integer.valueOf(entschluesseln(spielstand.getProperty("anzahlGastkarten")));
    			int anzahlLaenderkarten = Integer.valueOf(entschluesseln(spielstand.getProperty("anzahlLaenderkarten")));
    			int anzahlBarkarten = Integer.valueOf(entschluesseln(spielstand.getProperty("anzahlBarkarten")));
    			for(int i=0;i<anzahlGastkarten;i++) {
    				Variablen.getGastkarten().add(Gastkarte.parseGastkarte(entschluesseln(spielstand.getProperty("gastkarte"+i))));
    			}
    			for(int i=0;i<anzahlLaenderkarten;i++) {
    				Variablen.getLaenderkarten().add(Laenderkarte.parseLaenderkarte(entschluesseln(spielstand.getProperty("laenderkarte"+i))));
    			}
    			for(int i=0;i<anzahlBarkarten;i++) {
    				Variablen.getBarkarten().add(Gastkarte.parseGastkarte(entschluesseln(spielstand.getProperty("barkarte"+i))));
    				Variablen.getBarkartenecke().getBarzelle(i).setGast(Variablen.getBarkarten().get(i));
    			}
    			for(int i=0;i<Variablen.getStuehle().size();i++) {
    				Variablen.getStuehle().get(i).setStartGast(Gastkarte.parseGastkarte(entschluesseln(spielstand.getProperty("stuhl"+i))));
    			}
    			for(int i=0;i<Variablen.getTische().size();i++) {
    				Variablen.getTische().get(i).setLand(Laenderkarte.parseLaenderkarte(entschluesseln(spielstand.getProperty("tisch"+i))));
    			}
    			String temp = spielstand.getProperty("gastAllein","null");
    			if(temp!="null") {
    				int stuhlindex = Integer.valueOf(entschluesseln(temp));
    				Variablen.getStuehle().get(stuhlindex).setPartnerNoetig(true);
    				Variablen.getStuehle().get(stuhlindex).gruenfaerben();
    			}
            } else {
            	neuesspiel();
            }
		} else {
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
		Properties spielstand = ladeProperties(spielstanddatei);
		spielstand.clear();
		spielstand.setProperty("spielangefangen",verschluesseln(String.valueOf(false)));
		try {
			spielstand.store(new FileWriter("dateien/"+spielstanddatei),"Spielstand gespeichert");
		} catch (IOException e) {
			absturz("dateien/"+spielstanddatei);
		}
	}
	
	/**
	 * Diese Methode laedt die Properties, in welchen die Inhalte der Textdatei gespeichert werden.
	 * @param dateiname Nimmt den Namen der Datei entgegen.
	 * @return Gibt die erstellten Properties zurueck.
	 */
	private Properties ladeProperties(String dateiname) {
		Properties prop = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("dateien/"+dateiname), Charset.forName("UTF-8")));
			prop = new Properties();
			prop.load(br);
			br.close();
		} catch (FileNotFoundException e) {
			absturz("dateien/"+spielstanddatei);
		} catch (IOException e) {
			absturz("dateien/"+spielstanddatei);
		}
		return prop;
	}
	
	/**
	 * Diese Methode schreibt einen neuen Highscore und gibt ihm eine Nummer.
	 * @param hsc Der zu uebergebende Highscore.
	 * @param num Die Nummer des Highscores.
	 */
	public void highscorehinzufuegen(Highscore hsc,int num) {
		try {
			Properties bestenliste = ladeProperties(highscoredatei);
			String temp = (String.valueOf(hsc.getSystemzeit())+","+hsc.getPunktzahl()+","+hsc.getName());
			bestenliste.setProperty("highscore"+num, verschluesseln(temp));
			bestenliste.setProperty("anzahlHighscores", verschluesseln(String.valueOf(num+1)));
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("dateien/"+highscoredatei), Charset.forName("UTF-8")));
			bestenliste.store(bw, "Gespeicherte Highscores");
		} catch(IOException e) {
			absturz("dateien/"+highscoredatei);
		}
	}
	
	/**
	 * Diese Methode laedt alle verfuegbaren Highscores aus der Datei und gibt sie in einem Array aus.
	 * @return Gibt einen Highscore[] zurueck.
	 */
	public Highscore[] allesHighscoresLaden() {
		Properties bestenliste = ladeProperties(highscoredatei);
		int anz;
		try {
			anz = Integer.valueOf(entschluesseln(bestenliste.getProperty("anzahlHighscores")));
		} catch(NullPointerException e) {
			anz = 0;
		}
		
		Highscore[] highscores = new Highscore[anz];
		for(int i=0;i<anz;i++) {
			highscores[i] = highscoreAufrufen("highscore"+i);
		}
		return highscores;
	}
	
	/**
	 * Gibt einen Highscore anhand seines Keys aus.
	 * @param key Hier gibt man den key ein.
	 * @return Gibt den Highscore zurueck.
	 */
	private Highscore highscoreAufrufen(String key) {
		try {
			Properties bestenliste = ladeProperties(highscoredatei);
			String temp = entschluesseln(bestenliste.getProperty(key));
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
	 * @param verschluesselt Nimmt den verschluesselten String entgegen.
	 * @return Gibt den entschluesselten String aus.
	 */
	private String entschluesseln(String verschluesselt) {
		char[] temp = verschluesselt.toCharArray();
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
	
	/**
	 * Diese Methode gibt eine Absturzmeldung zurueck, weil eine Datei fehlt und schliesst danach das Programm.
	 * @param dateiname Nimmt den Dateinamen entgegen.
	 */
	private void absturz(String dateiname) {
		Meldungen msgbox = new Meldungen();
		JOptionPane.showMessageDialog(null, msgbox.dateiFehlt(dateiname), msgbox.dateifehltTitel, JOptionPane.WARNING_MESSAGE);
		System.exit(0);
	}

}