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

public class CafeIntMain {
	
	private JFrame spielframe = new JFrame(new Meldungen().programmname);
    
    public CafeIntMain() {
    	oberflaeche();
        ablauf();
    }
    
    private void oberflaeche() {
    	spielframe.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    	spielframe.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	schliessen();
            }
        });
    	spielframe.setPreferredSize(new Dimension(1608,780));
        spielframe.setMinimumSize(new Dimension(1072,520));
        spielframe.setResizable(true);
        try {
			spielframe.setIconImage(Toolkit.getDefaultToolkit().getImage(new URL(BaseURL.getJarBase(Spielfeld.class), "./favicon.png")));
		} catch (MalformedURLException e) {}
        spielframe.setVisible(false);
        
        Container contentPane = spielframe.getContentPane();
        contentPane.setLayout(new GridBagLayout());

        spielframe.add(new Barkartenecke(), new GridBagFelder(0, 0, 1, 1, 0.15, 0.5));
        spielframe.add(new Statistikecke(), new GridBagFelder(0, 1, 1, 1, 0.15, 0.5));
        spielframe.add(new Spielfeld(), new GridBagFelder(1, 0, 1, 2, 0.7, 1.0));
        spielframe.add(new Spielkartenecke(), new GridBagFelder(2, 0, 1, 1, 0.15, 0.5));
        spielframe.add(new Bildecke(), new GridBagFelder(2, 1, 1, 1, 0.15, 0.5));
        
        spielframe.pack();
        spielframe.setLocationRelativeTo(null);
    }
    
    private void ablauf() {
    	new Programmstart().sysWin();
    	new Spielstand().laden();
        spielframe.setVisible(true);
    }
    
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

	public static void main(String[] args) {
		new CafeIntMain();
	}

}