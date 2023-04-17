package objects;

import java.io.Serializable;

/**
 * Cette classe represente un Mur.
 */
public class Wall extends GamePiece implements Viewable, Serializable {
	
	private static final long serialVersionUID = 1L;

	
	// METHODES
	
	@Override
	public void displaysOnTheTerminal() {
		System.out.print("#");
	}
	
}
