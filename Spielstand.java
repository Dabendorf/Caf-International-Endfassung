package cafeint;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import javax.swing.JOptionPane;

public class Spielstand {
	
	private String dateiname = "spielstand.txt";
	private char[] schluessel = "Heizoelrueckstossabdaempfung".toCharArray();
	
	public void speichern() {
		Properties spielstand = ladeProperties(dateiname);
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
		}
		for(int i=0;i<Variablen.getTische().size();i++) {
			spielstand.setProperty("tisch"+i,verschluesseln(String.valueOf(Variablen.getTische().get(i).getLaenderkarte())));
		}
		
		try {
			spielstand.store(new FileWriter("dateien/"+dateiname),"Spielstand gespeichert");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void laden() {
		Properties spielstand = ladeProperties(dateiname);
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
    			Statistikecke.getInfz(0).punktzahlschreiben();
    			Statistikecke.getInfz(1).punktzahlschreiben();
    			Statistikecke.getInfz(Variablen.getAktSpieler()).faerben(true);
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
    				Barkartenecke.getBarzelle(i).setGast(Variablen.getBarkarten().get(i));
    			}
    			for(int i=0;i<Variablen.getStuehle().size();i++) {
    				Variablen.getStuehle().get(i).setStartGast(Gastkarte.parseGastkarte(entschluesseln(spielstand.getProperty("stuhl"+i))));
    			}
    			for(int i=0;i<Variablen.getTische().size();i++) {
    				Variablen.getTische().get(i).setLand(Laenderkarte.parseLaenderkarte(entschluesseln(spielstand.getProperty("tisch"+i))));
    			}
            } else {
            	neuesspiel();
            }
		} else {
			neuesspiel();
		}
	}
	
	private void neuesspiel() {
		Programmstart progst = new Programmstart();
		Spielstart spstart = new Spielstart();
		progst.namensfrage();
        progst.grafikladen();
        spstart.neuesspiel();
	}
	
	public void loescheSpielstand() {
		Properties spielstand = ladeProperties(dateiname);
		spielstand.clear();
		spielstand.setProperty("spielangefangen",verschluesseln(String.valueOf(false)));
		
		try {
			spielstand.store(new FileWriter("dateien/"+dateiname),"Spielstand gespeichert");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Properties ladeProperties(String filename) {
		Reader reader = null;
		Properties prop = null;
		try {
			reader = new BufferedReader(new FileReader("dateien/"+filename));
			prop = new Properties();
			prop.load(reader);
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			
		}
		return prop;
	}
	
	private String verschluesseln(String original) {
		char[] temp = original.toCharArray();
		String crypt = new String("");
		for(int i=0;i<temp.length;i++) {
			int result = (temp[i] + schluessel[i%schluessel.length]) % 256;
			crypt += (char) result;
        }
        return crypt;
    }
	
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

}