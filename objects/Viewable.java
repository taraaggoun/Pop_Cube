package objects;

/**
 * Viewable (Affichable) est une <b>interface qui possede une seule methode</b>.<br>
 * Toutes les classes qui sont implementes par cette interface sont consideres comme "Affichable".<br>
 * Ainsi tous les objets qui sont crees <b>peuvent s'afficher sur le terminal</b>.<br>
 */

public interface Viewable {
	
	/**
	 * Affiche sur le terminal l'object d'une classe qui est implemente par Viewable
	 */
	public void displaysOnTheTerminal();

}
