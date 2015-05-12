package cafeint;

import javax.swing.JOptionPane;

public class Spielende {
	
	public boolean keinegastkarten() {
		if(Variablen.getGastkarten().size() == 0) {
			Variablen.setZustand(31);
			siegmeldung(0);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean keinelaenderkarten() {
		if(Variablen.getLaenderkarten().size() == 0) {
			Variablen.setZustand(32);
			siegmeldung(1);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean barvoll() {
		if(Variablen.getBarkarten().size() == 21) {
			Variablen.setZustand(33);
			siegmeldung(2);
			return true;
		}
		else {
			return false;
		}
	}
	
	private void siegmeldung(int art) {
		Meldungen msgbox = new Meldungen();
		String grund;
		switch(art) {
		case 0:
			grund=msgbox.endegastkarten;
			break;
		case 1:
			grund=msgbox.endelaenderkarten;
			break;
		case 2:
			grund=msgbox.endebar;
			break;
		default:
			grund="";
			break;
		}
		
		int pkt0 = Variablen.getSpieler(0).getPunkte();
		int pkt1 = Variablen.getSpieler(1).getPunkte();
		
		if(pkt0 > pkt1+20) {
			JOptionPane.showMessageDialog(null, grund+msgbox.siegermeldung(0), msgbox.spielende, JOptionPane.INFORMATION_MESSAGE);
		} else if(pkt0 > pkt1) {
			JOptionPane.showMessageDialog(null, grund+msgbox.siegermeldung(1), msgbox.spielende, JOptionPane.INFORMATION_MESSAGE);
		} else if(pkt0 == pkt1) {
			JOptionPane.showMessageDialog(null, grund+msgbox.siegermeldung(2), msgbox.spielende, JOptionPane.INFORMATION_MESSAGE);
		} else if(pkt0 < pkt1) {
			JOptionPane.showMessageDialog(null, grund+msgbox.siegermeldung(3), msgbox.spielende, JOptionPane.INFORMATION_MESSAGE);
		} else if(pkt0+20 < pkt1) {
			JOptionPane.showMessageDialog(null, grund+msgbox.siegermeldung(4), msgbox.spielende, JOptionPane.INFORMATION_MESSAGE);
		}
		
        int menue = JOptionPane.showOptionDialog(null,msgbox.endefrage,msgbox.endetitel, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, msgbox.endoptionen, msgbox.endoptionen[0]);
        if(menue == 0) {
        	Spielstart spst = new Spielstart();
        	spst.neuesspiel();
        } else {
        	System.exit(0);
        }
	}

}