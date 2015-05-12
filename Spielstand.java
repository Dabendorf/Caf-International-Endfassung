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
	
	public void speichern() {
		Properties spielstand = ladeProperties("spielstand.txt");
		spielstand.setProperty("spielangefangen",String.valueOf(true));
		spielstand.setProperty("amZug",String.valueOf(Variablen.getAktSpieler()));
		spielstand.setProperty("zustand",String.valueOf(Variablen.getZustand()));
		
		for(int i=0;i<2;i++) {
			spielstand.setProperty("spielername"+i,Variablen.getSpieler(i).getName());
			spielstand.setProperty("spielerpunkte"+i,String.valueOf(Variablen.getSpieler(i).getPunkte()));
			for(int h=0;h<5;h++) {
				spielstand.setProperty("handkarte"+i+"-"+h,String.valueOf(Variablen.getSpieler(i).getHandkarten().get(h)));
			}
		}
		spielstand.setProperty("anzahlGastkarten",String.valueOf(Variablen.getGastkarten().size()));
		spielstand.setProperty("anzahlLaenderkarten",String.valueOf(Variablen.getLaenderkarten().size()));
		spielstand.setProperty("anzahlBarkarten",String.valueOf(Variablen.getBarkarten().size()));
		for(int i=0;i<Variablen.getGastkarten().size();i++) {
			spielstand.setProperty("gastkarte"+i,String.valueOf(Variablen.getGastkarten().get(i)));
		}
		for(int i=0;i<Variablen.getLaenderkarten().size();i++) {
			spielstand.setProperty("laenderkarte"+i,String.valueOf(Variablen.getLaenderkarten().get(i)));
		}
		for(int i=0;i<Variablen.getBarkarten().size();i++) {
			spielstand.setProperty("barkarte"+i,String.valueOf(Variablen.getBarkarten().get(i)));
		}
		for(int i=0;i<Variablen.getStuehle().size();i++) {
			spielstand.setProperty("stuhl"+i,String.valueOf(Variablen.getStuehle().get(i).getGast()));
		}
		for(int i=0;i<Variablen.getTische().size();i++) {
			spielstand.setProperty("tisch"+i,String.valueOf(Variablen.getTische().get(i).getLaenderkarte()));
		}
		
		try {
			spielstand.store(new FileWriter("spielstand.txt"),"Spielstand gespeichert");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void laden() {
		Properties spielstand = ladeProperties("spielstand.txt");
		boolean spielgespeichert = Boolean.valueOf(spielstand.getProperty("spielangefangen","false"));
		if(spielgespeichert) {
			Meldungen msgbox = new Meldungen();
			int menue = JOptionPane.showOptionDialog(null,msgbox.altesspielfrage,msgbox.altesspieltitel, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, msgbox.altesspieloptionen, msgbox.altesspieloptionen[0]);
            if(menue == 0) {
            	Variablen.setAktSpieler(Integer.valueOf(spielstand.getProperty("amZug")));
    			Variablen.setZustand(Integer.valueOf(spielstand.getProperty("zustand")));
    			for(int i=0;i<2;i++) {
    				Variablen.getSpieler(i).setName(spielstand.getProperty("spielername"+i));
    				Variablen.getSpieler(i).setPunkte(Integer.valueOf(spielstand.getProperty("spielerpunkte"+i)));
    				for(int h=0;h<5;h++) {
    					Variablen.getSpieler(i).getHandkarten().add(Gastkarte.parseGastkarte(spielstand.getProperty("handkarte"+i+"-"+h)));
    				}
    			}
    			int anzahlGastkarten = Integer.valueOf(spielstand.getProperty("anzahlGastkarten"));
    			int anzahlLaenderkarten = Integer.valueOf(spielstand.getProperty("anzahlLaenderkarten"));
    			int anzahlBarkarten = Integer.valueOf(spielstand.getProperty("anzahlBarkarten"));
    			for(int i=0;i<anzahlGastkarten;i++) {
    				Variablen.getGastkarten().add(Gastkarte.parseGastkarte(spielstand.getProperty("gastkarte"+i)));
    			}
    			for(int i=0;i<anzahlLaenderkarten;i++) {
    				Variablen.getLaenderkarten().add(Laenderkarte.parseLaenderkarte(spielstand.getProperty("laenderkarte"+i)));
    			}
    			for(int i=0;i<anzahlBarkarten;i++) {
    				Variablen.getBarkarten().add(Gastkarte.parseGastkarte(spielstand.getProperty("barkarte"+i)));
    				Barkartenecke.getBarzelle(i).setGast(Variablen.getBarkarten().get(i));
    				Barkartenecke.getBarzelle(i).repaint(); //Mal gucken????
    			}
    			for(int i=0;i<Variablen.getStuehle().size();i++) {
    				Variablen.getStuehle().get(i).setGast(Gastkarte.parseGastkarte(spielstand.getProperty("stuhl"+i)));
    				Variablen.getStuehle().get(i).getSpielzelle().repaint();
    			}
    			for(int i=0;i<Variablen.getTische().size();i++) {
    				Variablen.getTische().get(i).setLand(Laenderkarte.parseLaenderkarte(spielstand.getProperty("tisch"+i)));
    				Variablen.getTische().get(i).getSpielzelle().repaint();
    			}
    			
    			Programmstart progst = new Programmstart();
    			progst.grafikladen();
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
    	//Variablen.getSpieler(0).setName("Lukas"); //Entfernen
    	//Variablen.getSpieler(1).setName("Malte"); //Entfernen
        progst.grafikladen();
        spstart.neuesspiel();
	}
	
	public void loescheSpielstand() {
		Properties spielstand = ladeProperties("spielstand.txt");
		spielstand.setProperty("spielangefangen",String.valueOf(false));
		
		try {
			spielstand.store(new FileWriter("spielstand.txt"),"Spielstand gespeichert");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Properties ladeProperties(String filename) {
		Reader reader = null;
		Properties prop = null;
		try {
			reader = new BufferedReader(new FileReader(filename));
			prop = new Properties();
			prop.load(reader);
			reader.close();
		} catch (FileNotFoundException e) {
			
		} catch (IOException e) {
			
		}
		return prop;
	 }

}