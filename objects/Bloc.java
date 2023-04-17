package objects;

import java.io.Serializable;
import java.util.Map;

/**
 * Cette classe represente un bloc de couleur dans un niveaux.
 * <p>Cette classe un attribut <em>color</em> de type String qui peut avoir 5 valeurs differentes : "B" (bleu), "G" (vert), "P" (violet), "R" (rouge), "Y" (jaune).</p>
 * Elle extend de la classe <b>GamePiece</b> parce qu'elle est une piece insdispensable d'un niveau.
 * @see GamePiece
 */

public class Bloc extends GamePiece implements Viewable, Serializable {
	
	private static final long serialVersionUID = 1L;
	private final int idColor;
	
	
	// CONSTRUCTEUR
	
	/**
	 * Construis un objet de type Bloc en lui donnant un ID
	 * @param idColor
	 */
	public Bloc(int idColor) {
		this.idColor = idColor;
	}

	
	// ACCESSEURS (GETTEURS & SETTEURS)
	
	/**
	 * Retourne l'ID-COULEUR du bloc
	 * @return
	 */
	public int getIdColor() { return idColor; }
	
	
	// METHODES
	
	@Override
	public void displaysOnTheTerminal() {
		System.out.print(idColor);
	}
	
	/**
	 * Affiche la couleur qui a ete dediee a son ID
	 */
	public void displaysOnTheTerminal(Map<Integer, String> idAndColor) {
		System.out.print(idAndColor.get(idColor));
	}
	
}
