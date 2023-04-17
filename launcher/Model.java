package launcher;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

import controller.Human;
import controller.Player;
import dataBase.SerializeObject;
import objects.*;

/**
 * Cette classe set a stocker les donnees en cours du jeu.
 * C'est la ou on stocke toutes les variables qui changent au cours du jeu.
 * Elle fait aussi partie du design pattern <b>Vue-Modele-Controlleur</b>.
 */
public class Model {

	private static SerializeObject so = new SerializeObject();
	private static LocalDateTime nextLife = LocalDateTime.now();
		
	// VARIABLES PLATEAU (BOARDGAME)
	private final BoardGame BOARDGAME;
	private final int NB_LVL_IN_A_PAGE = 20;
	private int nbPages = 1;
	private final int TOTAL_NB_PAGES = 1;
	
	// VARIABLES NIVEAU (LEVEL)
	private Level currentLevel;
	private Map<String, Boolean> events = new HashMap<String, Boolean>();
	private Map<Integer, String> idAndColor = new HashMap<Integer, String>();

	
	// CONSTRUCTEUR
	
	/**
	 * Construis un objet de type Model en lui donnant une vue
	 */
	Model() throws ClassNotFoundException {
		this.BOARDGAME = so.createBoardGameInitial(20);
	}
	

	// ACCESSEURS (GETTEURS & SETTEURS)
	
	/**
	 * Retourne le plateau du jeu
	 * @return boardGame le plateau du jeu
	 */
	public BoardGame getBoardGame() { return BOARDGAME; }
	/**
	 * Retourne le nombre de niveau sur une page
	 * @return NB_LVL_IN_A_PAGE le nombre de niveau sur une page
	 */
	public int getNB_LVL_IN_A_PAGE() { return NB_LVL_IN_A_PAGE; }
	/**
	 * Retourne le nombre de page
	 * @return nbPages le nombre de page
	 */
	public int getNbPages() { return nbPages; }
	/**
	 * Retourne le nombre total de page
	 * @return TotalNbPages le nombre total de page
	 */
	public int getTotalNbPages() { return TOTAL_NB_PAGES; }
	/**
	 * change le nombre page
	 * @param nbPages nombre de pages
	 */
	public void setNbPages(int nbPages) { this.nbPages = nbPages; }
	/**
	 * Retourne la map qui associe un entier a une couleur
	 * @return idAndColor la map qui associe un entier a une couleur
	 */
	public Map<Integer, String> getIdAndColor() { return idAndColor; }
	
	/**
	 * Retourne le niveau en cours
	 * @return le niveau en cours
	 */
	public Level getCurrentLevel() { return currentLevel; }

	/**
	 * Change le niveau en cours
	 * @param c nouveau niveau en cours  
	 */
	public void setCurrentLevel(Level c) { this.currentLevel = c; }
	
	/**
	 * Retourne la Map des evenements des boosters
	 * @return
	 */
	public Map<String, Boolean> getEvents() { return events; }
	
	/**
	 * Retourne le temps d'attente pour que le joueur retrouve ses 5 vies
	 * @return
	 */
	public LocalDateTime getNextLife() { return nextLife; }
	
	/**
	 * Change le temps d'attente pour que le joueur retrouve ses 5 vies
	 */
	public void setNextLife() { nextLife = LocalDateTime.now().plusMinutes(10); }
	
	
	// METHODES UTILES POUR LA VUE
	
	
	// METHODES UTILISEES POUR LA VUE POUR ENREGISTRER DES DONNEES
	
	/**
	 * Change la partie en cours en prenant une sauvegarde de niveaux
	 * @param lvl
	 */
	public void changeCurrentLevel(int lvl) {
		try {
			this.currentLevel = clone(this.BOARDGAME.getLevelTab()[lvl].getLvl());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retourne un niveau (Level) a partir de la deSerialisation
	 * @param l nombre de niveau
	 * @return
	 */
	public Level clone(int l) throws ClassNotFoundException {
		return so.deserializableLevel(l);
	}
	
	/**
	 * Enregistre les donnees d'un joueur lorsqu'il gagne une partie
	 * @param p
	 */
	public void playerWin(Player p) {
		if(p.getPointsLvl(currentLevel.getLvl()) == 0) p.addNbPiece(1000);
		else p.addNbPiece(10);
		p.setLastLevelDone(currentLevel.getLvl());
		p.changePoints(currentLevel.getLvl(), currentLevel.getGoals().getPointsObtained());
		serializePlayer(p);
	}
	
	/**
	 * Enregistre les donnees d'un joueur lorsqu'il perd une partie
	 * @param p
	 */
	public void playerLose(Player p) {
		if(p instanceof Human) {
			((Human) p).decreaseNbLife();
			if(((Human) p).getNbLife() == 0) setNextLife();
		}
	}
	
	/**
	 * Verifie si un booster a ete debloque ou non
	 * @param p
	 * @param b
	 * @return
	 */
	public boolean boostersIsLock(Player p, String b) {
		if(p.getBoosters().containsKey(b) && p.getBoosters().get(b)) return true;
		return false;
	}
	
	/**
	 * Enregistre les donnees d'un joueur
	 * @param p (un joueur)
	 */
	public void serializePlayer(Player p) {
		so.serializePlayer(p);
	}
	
	/**
	 * Recupere les donnees d'un joueur
	 * @param name
	 * @return
	 */
	public Player deserializablePlayer(String name) {
		try {
			return so.deserializePlayer(name);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	// METHODES POUR LES NIVEAUX
	
	/**
	 * Verifie si le joueur donne en parametres a debloque des boosters
	 * @param p
	 * @return
	 */
	public String checkIfBoosterUnLock(Player p) {
		if(currentLevel.getLvl() == 5) {
			p.getBoosters().put("rocket", true);
			return "rocket";
		} else if(currentLevel.getLvl() == 9) {
			p.getBoosters().put("bomb", true);
			return "bomb";
		} else if(currentLevel.getLvl() == 13) {
			p.getBoosters().put("hammer", true);
			return "hammer";
		} else if(currentLevel.getLvl() == 18) {
			p.getBoosters().put("disco-ball", true);
			return "disco-ball";
		} else return null;
	}
	
	/**
	 * Ajoute des couleurs aux ID des couleurs des blocs
	 */
	public void addIdColor() {
		String[] color = {"B", "Y", "R", "G", "P"};
		int[] idDone = new int[color.length];
		Random rd = new Random();
		for(int k=0; k<color.length; k++) {
			int c = rd.nextInt(5) + 2;
			while(in(idDone, c)) {
				c = rd.nextInt(5) + 2;
			}	
			idDone[k] = c;
			idAndColor.put(k+2, color[c-2]);
		}
	}
	
	/**
	 * Verifie si la couleur a deja un ID ou pas
	 * @param s
	 * @param idColor 
	 * @return boolean
	 */
	public boolean in(int[] s, int idColor) {
		for(int i=0; i<s.length; i++) {
			if(s[i] == idColor) return true;
		}
		return false;
	}
	
	/**
	 * Utilise les boosters dans les niveaux au lancement de la partie
	 * @param b
	 */
	public void useBoostersAtTheBeginning(Booster b) {
		Random r = new Random();
		int x = r.nextInt(currentLevel.haveTheFirstLineNoNull()+6);
		int y = r.nextInt(currentLevel.getGamePiece()[0].length);
		
		while(!(currentLevel.isInsideTheTabVisible(x, y) && currentLevel.getGamePiece()[x][y] != null && !(currentLevel.getGamePiece()[x][y] instanceof Wall) && !(currentLevel.getGamePiece()[x][y] instanceof Animal) && !(currentLevel.getGamePiece()[x][y] instanceof Booster))) {
			x = r.nextInt(currentLevel.haveTheFirstLineNoNull()+6);
			y = r.nextInt(currentLevel.getGamePiece()[0].length);
		}
		
		currentLevel.getGamePiece()[x][y] = b;
	}
	
	/**
	 * Retourne la difference entre deux LocalDateTime
	 * @return
	 */
	public int diffLocalDateTime() {
		return (int) ChronoUnit.SECONDS.between(LocalDateTime.now(), nextLife)/60;
	}
	
}
