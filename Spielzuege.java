package cafeint;

public class Spielzuege {
	
	public void legetischkarte(int tischnr) {
		Variablenkammer.getTische().get(tischnr).setLand(Variablenkammer.getLaenderkarten().get(0));
		Variablenkammer.getLaenderkarten().remove(0);
	}

}