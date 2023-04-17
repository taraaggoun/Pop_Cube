package objects;

/**
 * Cette classe represente le plateau du jeu (le tableau ou le joueur accede aux niveaux)
 * <p>Ici, le joueur accede a tous les niveaux accessibles. Le plateau est unique a chaque joueur.</p>
 */

public class BoardGame implements Viewable {
	
	private int lvlDone;
	private final int totalNbLvls;
	private final Level[] levelTab;
	
	
	// CONSTRUCTEUR
	
	/**
	 * Construis un objet de type BoardGame (Plateau) en lui donnant le dernier niveau accessible au joueur, le nombre total de niveaux et le tableau des niveaux
	 * @param lvlDone le dernier niveau disponible du joueur
	 * @param totalNbLvls le nombre total de niveaux du jeu
	 * @param l le tableau de niveaux
	 */
	public BoardGame(int lvlDone, int totalNbLvls, Level[] l) {		
		this.lvlDone = lvlDone;
		this.totalNbLvls = totalNbLvls;
		this.levelTab = l;
	}

	
	// ACCESSEURS (GETTEURS & SETTEURS)
	
	/**
	 *  Retourne le dernier niveau accessible du joueur
	 * @return le dernier niveau accessible du joueur
	 */
	public int getLvlDone() { return lvlDone; }
	
	/**
	 * Met a jour le dernier niveau accessible du joueur
	 * @param lvlDone
	 */
	public void setLvlDone(int lvlDone) { this.lvlDone = lvlDone; }
	
	/**
	 * Retourne le nombre total de niveaux
	 * @return le nombre total de niveaux
	 */
	public int getTotalNbLvls() { return totalNbLvls; }
	
	/**
	 * Retourne le tableau de Level (niveaux)
	 * @return le tableau de Level (niveaux)
	 */
	public Level[] getLevelTab() { return levelTab; }
	
	
	// METHODES
	
	@Override
	public void displaysOnTheTerminal() {
		System.out.println("\n");
		System.out.println("Voici les niveaux disponibles.\n");
		System.out.println("Niveau\n" + lvlDone + " / " + totalNbLvls + "\n");
	}
	
	/**
	 * Affiche le plateau jusqu'au dernier niveau enregistre
	 * @param lvlMax
	 */
	public void displaysOnTheTerminal(int lvlMax) {
		displaysOnTheTerminal();
		
		for(int i=1; i<this.levelTab.length; i++) {
			if(levelTab[i].isAvailable() || i-1 <= lvlMax) System.out.println(levelTab[i].getLvl() + " : Disponible");
		}
		
		System.out.println();
	}
}
