package cafeint;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Diese Klasse erstellt eine Bestenliste fuer das Spiel und ordnet selbige in eine primitive Tabelle ein.
 * 
 * @author Lukas Schramm
 * @version 1.0
 * 
 */
public class Bestenliste {

	private JFrame frame1 = new JFrame("Bestenliste");
	private JTable tabelle1 = new JTable();
	private ArrayList<Highscore> highscoreliste = new ArrayList<Highscore>();

	public Bestenliste() {
		frame1.setPreferredSize(new Dimension(450,300));
        frame1.setMinimumSize(new Dimension(450,300));
	    frame1.setResizable(true);
	    try {
			frame1.setIconImage(Toolkit.getDefaultToolkit().getImage(new URL(BaseURL.getJarBase(Spielfeld.class), "./favicon.png")));
		} catch (MalformedURLException e) {}
	    frame1.addWindowListener(new WindowAdapter() {
			@Override
            public void windowClosing(WindowEvent e) {
				if(Variablen.getZustand()==31 || Variablen.getZustand()==32 || Variablen.getZustand()==33) {
					new Spielende().abfrageNeuesspiel();
				} else {
					frame1.dispose();
				}
            }
        });
	    
	    Container cp = frame1.getContentPane();
	    cp.setLayout(new GridLayout());
	    
	    highscoresladen();
	}
	
	/**
	 * Hier werden alle alten Highscores aus der Highscorelistentabelle geladen und in die Highscoreliste integriert.
	 */
	private void highscoresladen() {
		Spielstand spst = new Spielstand();
		for(Highscore hsc:spst.allesHighscoresLaden()) {
			highscoreliste.add(hsc);
		}
	}
	
	/**
	 * Hier wird ein neuer Highscore eingefuegt und in die Highscoreliste integriert.
	 * @param systemzeit Dies ist die Zeit zu welcher der Rekord aufgestellt wurde.
	 * @param punktzahl Dies sind die Punkte, die der Spieler im Spiel erreicht hat.
	 * @param name Dies ist der Name des Spielers.
	 */
	public void highscorehinzufuegen(long systemzeit, int punktzahl, String name) {
		highscoreliste.add(new Highscore(systemzeit,punktzahl,name));
	}
	
	/**
	 * Diese Methode sortiert die Highscores und ueberfuert sie in die Tabelle.
	 */
	public void sortiere() {
		Collections.sort(highscoreliste);
		Collections.reverse(highscoreliste);
		Vector<Object> eintraege = new Vector<Object>();
		for(Highscore hsc:highscoreliste) {
			if(highscoreliste.indexOf(hsc)<25) {
				Vector<Object> zeile = new Vector<Object>();
				zeile.add(hsc.getName());
				zeile.add(hsc.getPunktzahl());
				long tempSystem = hsc.getSystemzeit();
				Date date = new Date(tempSystem);
			    Format format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
				zeile.add(format.format(date));
				eintraege.add(zeile);
				new Spielstand().highscorehinzufuegen(hsc,highscoreliste.indexOf(hsc));
			}
		}

		Vector<String> titel = new Vector<String>();
		titel.add("Name");
		titel.add("Punktzahl");
		titel.add("Erreicht");
		tabelle1 = new JTable(eintraege, titel);
		
		tabelle1.getColumn("Name").setPreferredWidth(37);
	    tabelle1.getColumn("Punktzahl").setPreferredWidth(26);
	    tabelle1.getColumn("Erreicht").setPreferredWidth(37);
	    tabelle1.getTableHeader().setBackground(Color.lightGray);
	    tabelle1.setEnabled(false);
	    
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
	    for(int x=0;x<tabelle1.getColumnCount();x++) {
	    	tabelle1.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
	    	tabelle1.getTableHeader().getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
	    }
	    
	    tabelle1.setDefaultRenderer(String.class, centerRenderer);
	    
	    frame1.pack();
	    frame1.setLocationRelativeTo(null);
	    tabelle1.setVisible(true);
	    frame1.getContentPane().add(new JScrollPane(tabelle1));
	}
	
	/**
	 * Diese Methode kann die Bestenliste anzeigen oder wieder ausblenden.
	 * @param anzeigen Boolean, ob die Tabelle angezeigt oder ausgeblendet wird.
	 */
	public void anzeigen(boolean anzeigen) {
		frame1.setVisible(anzeigen);
	}

}