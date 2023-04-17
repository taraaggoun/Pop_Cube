package objects;

import java.util.Map;

/**
 * Cette classe represente une piece (que ce soit un bloc, un animal ou un booster).
 * Cette classe est abstraite parce qu'on ne peut pas creer un objet de ce type.
 */

public abstract class GamePiece implements Viewable {
	
	@Override
	public void displaysOnTheTerminal() {
		if(this instanceof Bloc) ((Bloc) this).displaysOnTheTerminal();
		else if(this instanceof Wall) ((Wall) this).displaysOnTheTerminal();
		else if(this instanceof Booster) ((Booster) this).displaysOnTheTerminal();
		else ((Animal) this).displaysOnTheTerminal();
	}
	
	/**
	 * Affiche un Bloc en fonction de sa couleur
	 * @param idAndColor
	 */
	public void displaysOnTheTerminal(Map<Integer, String> idAndColor) {
		if(this instanceof Bloc) ((Bloc) this).displaysOnTheTerminal(idAndColor);
	}
}
