package cafeint;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/**
 * Das ist die Hauptklasse des ganzen Spiels, welche die Mainmethode enthaelt. Sie generiert die gesamte graphische Oberflaeche und laedt den Spielablauf, der das Spiel startet.<br>
 * <br>
 * <b>spielframe</b> Dies ist der JFrame, der die gesamte graphische Oberflaeche enthaelt.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */

public class CafeIntMain {
	
	private JFrame spielframe = new JFrame(new Meldungen().programmname);
    
    public CafeIntMain() {
    	oberflaeche();
        ablauf();
    }
    
    /**
     * Diese Methode laedt die graphische Oberflaeche. Sie ist in einem GridBagLayout angeordnet und enthaelt fuenf verschiedene Spielelemente.
     */
    private void oberflaeche() {
    	spielframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    	spielframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	schliessen();
            }
        });
    	spielframe.setPreferredSize(new Dimension(1608,780));
        spielframe.setMinimumSize(new Dimension(1072,520));
    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    	if(dim.getWidth()<1608) {
    		spielframe.setPreferredSize(new Dimension((int)dim.getWidth(),(int)(dim.getWidth()/2.06)));
    	}
        spielframe.setResizable(true);
        try {
			spielframe.setIconImage(Toolkit.getDefaultToolkit().getImage(new URL(BaseURL.getJarBase(Spielfeld.class), "./favicon.png")));
		} catch (MalformedURLException e) {}
        spielframe.setVisible(false);
        
        Container contentPane = spielframe.getContentPane();
        contentPane.setLayout(new GridBagLayout());

        spielframe.add(Variablen.getBarkartenecke(), new GridBagFelder(0, 0, 1, 1, 0.15, 0.5));
        spielframe.add(Variablen.getStatistikecke(), new GridBagFelder(0, 1, 1, 1, 0.15, 0.5));
        spielframe.add(Variablen.getSpielfeld(), new GridBagFelder(1, 0, 1, 2, 0.7, 1.0));
        spielframe.add(Variablen.getSpielkartenecke(), new GridBagFelder(2, 0, 1, 1, 0.15, 0.5));
        spielframe.add(new Bildecke(), new GridBagFelder(2, 1, 1, 1, 0.15, 0.5));
        
        spielframe.pack();
        spielframe.setLocationRelativeTo(null);
    }
    
    /**
     * Diese Methode leitet den erstmaligen Spielablauf beim Start. Es wird die Spielablaufroutine aus der Klasse Spielstand geladen und der spielframe sichtbar gemacht.
     */
    private void ablauf() {
    	new Programmstart().sysWin();
    	new Spielstand().laden();
        spielframe.setVisible(true);
    }
    
    /**
     * Diese Methode wird automatisch aufgerufen, wenn der Spieler das Programm beendet. Er wird gefragt, ob er das aktuelle Spiel abspeichern moechte.<br/>
     * Sollte er dies bejahen, werden alle aktuellen Informationen in einer Textdatei abgelegt und bei Spielstart geladen.<br/>
     * Sollte er dies verneinen, wird die Textdatei geleert und der Spielstand geht unwiderruflich verloren.<br/>
     * Abschließend werden das Programm und die Javaengine geschlossen.
     */
    private void schliessen() {
    	Meldungen msgbox = new Meldungen();
    	int menue = JOptionPane.showOptionDialog(null,msgbox.schliessfrage,msgbox.schliesstitel, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, msgbox.schliessoptionen, msgbox.schliessoptionen[0]);
        if(menue == 0) {
        	new Spielstand().speichern();
        } else {
        	new Spielstand().loescheSpielstand();
        }
        System.exit(0);
    }

    /**
     * Die main-Methode startet das gesamte Programm.
     * @param args Der Standardparameter der Main-Methode.
     */
	public static void main(String[] args) {
		new CafeIntMain();
	}

}