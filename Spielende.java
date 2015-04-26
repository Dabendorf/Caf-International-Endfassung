package cafeint;

import javax.swing.JOptionPane;

public class Spielende {
	public boolean spielvorbei() {
		if(keinegastkarten()) {
			siegmeldung(0);
			return true;
		} else if(keinelaenderkarten()) {
			siegmeldung(1);
			return true;
		} else if(barvoll()) {
			siegmeldung(2);
			return true;
		} else {
			return false;
		}
	}
	
	private boolean keinegastkarten() {
		if(Variablenkammer.getGastkarten().size() == 0) {
			Variablenkammer.setZustand(31);
			return true;
		} else {
			return false;
		}
	}
	
	private boolean keinelaenderkarten() {
		if(Variablenkammer.getLaenderkarten().size() == 0) {
			Variablenkammer.setZustand(32);
			return true;
		} else {
			return false;
		}
	}
	
	private boolean barvoll() {
		if(Variablenkammer.getBarkarten().size() == 21) {
			Variablenkammer.setZustand(33);
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
		
		int pkt0 = Variablenkammer.getSpieler(0).getPunkte();
		int pkt1 = Variablenkammer.getSpieler(1).getPunkte();
		
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
		
        int menue = JOptionPane.showOptionDialog(null,msgbox.endetitel,msgbox.endefrage, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, msgbox.endoptionen, msgbox.endoptionen[0]);
        if(menue == 0) {
        	Spielstart spst = new Spielstart();
        	spst.neuesspiel();
        } else {
        	System.exit(0);
        }
	}

}