package cafeint;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

public class CafeIntMain {
	
	private JFrame spielframe = new JFrame(Variablenkammer.getMsgbox().programmname);
    
    public CafeIntMain() {
    	oberflaeche();
        ablauf();
    }
    
    private void oberflaeche() {
    	spielframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        spielframe.setPreferredSize(new Dimension(1400,800)); //Nachsehen, was kommmt
        spielframe.setMinimumSize(new Dimension(1050,600)); //Nachsehen, was kommmt
        spielframe.setResizable(true);
        try {
			spielframe.setIconImage(Toolkit.getDefaultToolkit().getImage(new URL(BaseURL.getJarBase(Spielfeld.class), "./favicon.png")));
		} catch (MalformedURLException e) {}
        spielframe.setVisible(false);
        
        Container contentPane = spielframe.getContentPane();
        contentPane.setLayout(new GridBagLayout());

        //spielframe.add(new Barkartenecke(), new GridBagFelder(0, 0, 1, 1, 0.15, 0.5));
        //spielframe.add(new Statistikecke(), new GridBagFelder(0, 1, 1, 1, 0.15, 0.5));
        spielframe.add(new Spielfeld(), new GridBagFelder(1, 0, 1, 2, 0.67, 1.0));
        //spielframe.add(new Spielkartenecke(), new GridBagFelder(2, 0, 1, 1, 0.18, 0.6));
        //spielframe.add(new Bildecke(), new GridBagFelder(2, 1, 1, 1, 0.18, 0.4));
        //Auskommentiert, da noch nicht relevant
        
        spielframe.pack();
        spielframe.setLocationRelativeTo(null);
    }
    
    private void ablauf() {
    	/*Spielstart spst = new Spielstart();
        if(spst.SysWin()) {
            msgbox.windows();
        }
        //spst.namensfrage();
        spieler[0].setName("Lukas"); //KURZWEG
        spieler[1].setName("Malte"); //KURZWEG
        Uebersichtsecke.getInfz(0).punktzahlschreiben(); //KURZWEG
        Uebersichtsecke.getInfz(1).punktzahlschreiben(); //KURZWEG
        
        long zeit1 = Debug.zeitnehmen(); //DEBUG
        spst.grafikladen();
        spielframe.setVisible(true);
        long zeit2 = Debug.zeitnehmen(); //DEBUG
        Debug.laufzeitschreiben(zeit2-zeit1); //DEBUG
        Spielfeld.getMeldungsbox().setText("Startzeit: "+(zeit2-zeit1)); //DEBUG*/
    }

	public static void main(String[] args) {
		new CafeIntMain();
	}

}