package cafeint;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Barkartenecke extends JPanel {
	
	private static ArrayList<Barzelle> barzellen = new ArrayList<Barzelle>(21);
	private static int barpunkte[] = {1,2,3,4,5,-2,-4,-6,-8,-10,-4,-6,-8,-10,-12,-6,-8,-10,-12,-14,-16};
	private Color hintgrdfarb = new Color(0x000000);
	
	public Barkartenecke() {
		this.setBackground(hintgrdfarb);
		setLayout(new GridLayout(7,3));
		for(int i=0;i<21;i++) {
			Barzelle bz = new Barzelle(barpunkte[i]);
			bz.setOpaque(true);
			bz.zelltext();
			bz.addMouseListener(new MouseAdapter() {
            	@Override
				public void mouseClicked(MouseEvent e) {
            		if(Spielkartenecke.getAkthandkartnum()!=-1) {
            			//new Spielzuege().legebarkarte(Spielkartenecke.getAkthandkartnum()); //dieser Zug muss noch umgesetzt werden
            		}
            	}
            });
			barzellen.add(bz);
			add(bz);
		}
	}

	public static Barzelle getBarzellen(int num) {
		return barzellen.get(num);
	}

}