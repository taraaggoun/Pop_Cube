package objects;

import java.io.Serializable;
import java.util.Map;
import java.util.Random;

/**
 * Cette classe represente un niveau <em>(dans le jeu de Pet Rescue Saga)</em>.
 * <p>Les evenements qui sont lies au jeu et toutes ses methodes correspondantes sont ainsi decrites dans cette classe.
 * Le joueur, en utilisant les bons parametres, peut supprimer des blocs.
 * Le jeu va automatiquement deplacer les blocs (soit en les descendant vers le sol (le bas du tableau) soit en les decalant vers la gauche).</p>
 */

public class Level implements Viewable, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// VARIABLES PROPRES AU NIVEAU
	private final int lvl;
	private boolean available = false;
	private GamePiece[][] gamePiece;
	
	// OBJECTIFS ET COMPARATEUR
	private boolean[][] getAdja;
	private Goals goals;
	
	
	// CONSTRUCTEUR	
	
	/**
	 * Construis un objet de type Level
	 * en lui donnant le numero du niveau, le nombre d'animaux a sauver, le nombre de points a obtenir, le nombre de mouvements et le tableau des blocs
	 * @param lvl le numero du niveau
	 * @param animalToSave le nombre d'animaux a sauver
	 * @param points le nombre de points
	 * @param nbMouv le nombre de mouvements
	 * @param gamePieceInInt le tableau des blocs
	 */
	public Level(int lvl, int animalToSave, int points, int nbMouv, int[][] gamePieceInInt) {
		this.lvl = lvl;
		this.goals = new Goals(animalToSave, points, nbMouv);		
		this.gamePiece = haveBlocTab(gamePieceInInt);
		this.getAdja = new boolean[gamePiece.length][gamePiece[0].length];
		
		if(lvl == 1) available = true;
	}
	
	
	// ACCESSEURS (GETTEURS & SETTEURS)
	
	/**
	 * Retourne le numero du level (niveau)
	 * @return le numero du niveau
	 */
	public int getLvl() { return lvl; }
	
	/**
	 * Retourne true si le level (niveau) est disponible
	 * @return si le level (niveau) est disponible
	 */
	public boolean isAvailable() { return available; }
	
	/**
	 * Met a jour la disponibilite du level (niveau)
	 * @param available
	 */
	public void setAvailable(boolean available) { this.available = available; }
	
	/**
	 * Retourne les objectifs du level (niveau) que le joueur doit atteindre
	 * @return les objectifs du level (niveau)
	 */
	public Goals getGoals() { return goals; }
	
	/**
	 * Retourne le tableau de tableau des GamePiece (Pieces) qui sont contenues dans le niveau
	 * @return le tableau de tableau des GamePiece
	 * @see GamePiece
	 */
	public GamePiece[][] getGamePiece() { return gamePiece; }
	
	/**
	 * Met a jour le tableau de tableau des GamePiece (Pieces) qui sont contenues dans le niveau
	 * @param gamePiece
	 */
	public void setGamePiece(GamePiece[][] gamePiece) { this.gamePiece = gamePiece; }
	
	
	// METHODES POUR LA CREATION D'UN LEVEL
	
	/**
	 * Convertis un tableau de int[][] en GamePiece[][]
	 * @param gamePieceInInt
	 * @return
	 */	
	public GamePiece[][] haveBlocTab(int[][] gamePieceInInt) {
		GamePiece[][] g = new GamePiece[gamePieceInInt.length][gamePieceInInt[0].length];
		for(int i=0; i<gamePieceInInt.length; i++) {
			for(int j=0; j<gamePieceInInt[i].length; j++) {
				if(gamePieceInInt[i][j] != 0) {
					if(gamePieceInInt[i][j] == 7) {
						g[i][j] = new Animal();
					} else if(gamePieceInInt[i][j] == 1) {
						g[i][j] = new Wall();
					} else {
						g[i][j] = new Bloc(gamePieceInInt[i][j]);
					}
				} else {
					g[i][j] = null;
				}
			}
		}
		return g;
	}
	
	/**
	 * Verifie si une ligne est vide
	 * @param line
	 * @return (true si elle est vide, false sinon)
	 */
	public boolean lineIsEmpty(int line) {
		for(int i=0; i<gamePiece[line].length; i++) {
			if(gamePiece[line][i] instanceof Bloc) {
				return false;
			} else if(gamePiece[line][i] instanceof Animal) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Retourne le premiere ligne vide
	 * @return
	 */
	public int haveTheFirstLineNoNull() {
		for(int i=0; i<gamePiece.length; i++) {
			if(!lineIsEmpty(i)) {
				return (i+6 > gamePiece.length ? gamePiece.length-6 : i);
			}
		}
		return gamePiece.length-6;
	}
	
	
	// METHODES EVENEMENT
	
	/**
	 * Fait deplacer toutes les pieces
	 */
	public void gamePieceMove() {
		this.fall();
		this.left();
		int max = this.haveTheFirstLineNoNull() + 6 >= gamePiece.length ? gamePiece.length : this.haveTheFirstLineNoNull() + 6;
		if(max == gamePiece.length && this.rescueAnimal()) gamePieceMove();
	}
	
	/**
	 * Verifie si la piece aux coordonnees (x, y) peut etre supprimee
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean canRemove(int x, int y) {
		if(gamePiece[x][y] instanceof Bloc) {
			int nbAdjacentsGamePiece = getNbAdjacentsGamePiece(x, y);
			if(nbAdjacentsGamePiece >= 2) return true;
		}
		return false;
	}
	
	/**
	 * Enleve le bloc que le joueur veut enlever
	 * @param x coordonnees de la piece
	 * @param y coordonnees de la piece
	 */
	public void removeBloc(int x, int y) {
		if(canRemove(x,y)) {
			int color = ((Bloc) gamePiece[x][y]).getIdColor();
			int nbAdjacentsGamePiece = getNbAdjacentsGamePiece(x, y);
			this.removeAll(x, y, color);
			goals.nbMouv--;
			goals.pointsObtained += nbAdjacentsGamePiece*nbAdjacentsGamePiece*10;
			gamePieceMove();
		}
	}
	
	/**
	 * Enleve les blocs contigües (fonction auxiliaire de removeBloc(int x, int y))
	 * @param x coordonnees de la piece
	 * @param y coordonnees de la piece
	 * @param color couleur de la piece
	 */
	public void removeAll(int x, int y, int color) {
		gamePiece[x][y] = null;
		if(isInsideTheTabVisible(x-1, y)) {
			if((gamePiece[x-1][y] != null) && (gamePiece[x-1][y] instanceof Bloc) && (((Bloc) gamePiece[x-1][y]).getIdColor() == color)) removeAll(x-1, y, color);
		}
		if(isInsideTheTabVisible(x+1, y)) {
			if((gamePiece[x+1][y] != null) && (gamePiece[x+1][y] instanceof Bloc) && (((Bloc) gamePiece[x+1][y]).getIdColor() == color)) removeAll(x+1, y, color);
		}
		if(isInsideTheTabVisible(x, y-1)) {
			if((gamePiece[x][y-1] != null) && (gamePiece[x][y-1] instanceof Bloc) && (((Bloc) gamePiece[x][y-1]).getIdColor() == color)) removeAll(x, y-1, color);
		}
		if(isInsideTheTabVisible(x, y+1)) {
			if((gamePiece[x][y+1] != null) && (gamePiece[x][y+1] instanceof Bloc) && (((Bloc) gamePiece[x][y+1]).getIdColor() == color)) removeAll(x, y+1, color);
		}
	}
	
	/**
	 * Enleve les blocs avec les boosters
	 * @param x
	 * @param y
	 */
	public void removeBlocForBooster(int x, int y, Booster booster) {
		goals.pointsObtained += 1000;
		if(booster.getType().equals("bomb")) {
			gamePiece[x][y] = null;
			for(int i=x-1; i<=x+1; i++) {
				for(int j=y-1; j<=y+1; j++) {
					if(isInsideTheTabVisible(i, j)) {
						if(gamePiece[i][j] instanceof Bloc) gamePiece[i][j] = null;
						else if(gamePiece[i][j] instanceof Booster) removeBlocForBooster(i, j, (Booster) gamePiece[i][j]);
					}
				}
			}
		} else if(booster.getType().equals("rocket")) {
			gamePiece[x][y] = null;
			for(int i=0; i<gamePiece.length; i++) {
				if(isInsideTheTabVisible(i,y)) {
					if(gamePiece[i][y] instanceof Bloc) gamePiece[i][y] = null;
					else if(gamePiece[i][y] instanceof Booster) removeBlocForBooster(i, y, (Booster) gamePiece[i][y]);
				}
			}
		} else if(booster.getType().equals("disco-ball")) {
			if(gamePiece[x][y] instanceof Bloc) {
				int color = ((Bloc) gamePiece[x][y]).getIdColor();
				int max = haveTheFirstLineNoNull() + 6 >= gamePiece.length ? gamePiece.length : haveTheFirstLineNoNull() + 6;
				for(int i=this.haveTheFirstLineNoNull(); i<max; i++) {
					for(int j=0; j<gamePiece[i].length; j++) {
						if(gamePiece[i][j] != null && gamePiece[i][j] instanceof Bloc && ((Bloc) gamePiece[i][j]).getIdColor() == color) gamePiece[i][j] = null;
					}
				}
			} else {
				Random r = new Random();
				int i = r.nextInt(haveTheFirstLineNoNull()+6);
				int j = r.nextInt(gamePiece[0].length);
				while(!(isInsideTheTabVisible(i, j) && gamePiece[i][j] != null && gamePiece[i][j] instanceof Bloc)) {
					i = r.nextInt(gamePiece.length);
					j = r.nextInt(gamePiece.length);
				}
				gamePiece[x][y] = null;
				removeBlocForBooster(i, j, new Booster("disco-ball"));
			}
		}
		gamePiece[x][y] = null;
		this.gamePieceMove();
	}
	
	/**
	 * Descend les blocs vers le sol
	 */
	public void fall() {
		for(int j = 0; j < gamePiece[0].length; j++) {
			for(int i = gamePiece.length-1; i >= 0; i--) {
				gamePieceFall(i,j);
			}
		}
	}
	
	/**
	 * Peut savoir si le bloc aux coordonnees (i,j) peut descendre
	 * @param i (coordonnees du bloc)
	 * @param j (coordonnees du bloc)
	 * @return true si le bloc aux coordonnees (i,j) peut descendre
	 */
	private boolean canFall(int i, int j) {
		int max = haveTheFirstLineNoNull() + 6 >= gamePiece.length ? gamePiece.length : haveTheFirstLineNoNull() + 6;
		return (i < max-1 && gamePiece[i][j] != null && !(gamePiece[i][j] instanceof Wall) && gamePiece[i+1][j] == null);
	}

	/**
	 * Descend un bloc jusqu'a ce qu'il rencontre un obstacle
	 * @param i (coordonnees du bloc)
	 * @param j (coordonnees du bloc)
	 */
	private void gamePieceFall(int i, int j) {
		while(canFall(i, j)) {
			GamePiece tmp = gamePiece[i][j];
			gamePiece[i][j] = null;
			gamePiece[i+1][j] = tmp;
			i++;
		}
	}

	/**
	 * Decale les blocs vers la gauche s'il y a un obtacle sur leur chemin
	 * S'il y a rien a cote d'un mur (Mur), alors on deplace le bloc sur la gauche
	 * Si plusieurs blocs mur (Mur) sont cote a cote, alors on deplace le bloc en dessus (en diagonale)
	 * @return true si le decalage a ete fait
	 */
	private boolean left1() {
		boolean b = false;
		int max = haveTheFirstLineNoNull() + 6 > gamePiece.length ? gamePiece.length : haveTheFirstLineNoNull() + 6;
		for(int i=0; i<max; i++) {
			for(int j=0; j<gamePiece[i].length; j++) {
				if(gamePiece[i][j] instanceof Wall) {
					if((isInsideTheTabVisible(i, j-1) && gamePiece[i][j-1] == null) || (isInsideTheTabVisible(i, j-1) && gamePiece[i][j-1] instanceof Wall && isInsideTheTabVisible(i-1,j-1) && gamePiece[i-1][j-1] == null)) {
						b = moveToLeft(i-1, j) || b;
					}
				}
			}
		}
		return b;
	}
	
	/**
	 * Decale les blocs de la derniere ligne.
	 * Si il y a une colonne vide, alors les blocs sont decales vers la gauche.
	 * @return true si le decalage a ete fait
	 */
	private boolean left2() {
		boolean b = false;
		int max = this.haveTheFirstLineNoNull()+6 > gamePiece.length ? gamePiece.length : this.haveTheFirstLineNoNull() + 6;
		for(int j = 1; j < gamePiece[0].length; j++) {
			if(gamePiece[max-1][j-1] == null && gamePiece[max-1][j] != null && !(gamePiece[max-1][j] instanceof Wall)) {
				b = moveToLeft(max-1,j) || b;
			}
		}
		return b;
	}

	/**
	 * Decale les blocs vers la gauche
	 * @param x (coordonnees du bloc)
	 * @param y (coordonnees du bloc)
	 * @return true si le decalage a ete fait
	 */
	private boolean moveToLeft(int x, int y) {
		boolean b = false;
		for(int i = x; i >= 0; i--) {
			if(gamePiece[i][y] instanceof Wall || gamePiece[i][y] == null) break;
			if(gamePiece[i][y-1] == null) {
				GamePiece tmp = gamePiece[i][y];
				gamePiece[i][y] = null;
				gamePiece[i][y-1] = tmp;
				b = true;
			}		
		}
		fall();
		return b;
	}
		
	/**
	 * Decale les blocs vers la gauche tant qu'il y a un deplacement a faire
	 */
	public void left() {
		boolean l1 = true;
		boolean l2 = true;
		while(l1 || l2) {
			l1 = left1();
			l2 = left2();
		}
	}
	
	/**
	 * Sauve des animaux et fait gagner des points
	 * @return
	 */
	public boolean rescueAnimal() {
		boolean b = false;
		for(int j = 0; j < gamePiece[0].length; j++) {
			if(gamePiece[gamePiece.length - 1][j] instanceof Animal) {
				goals.pointsObtained += 100;
				gamePiece[gamePiece.length - 1][j] = null;
				goals.animalSaved++;
				b = true;
			}
		}
		return b;
	}

	/**
	 * Verifie que le joueur a gagne
	 * @return
	 */
	public boolean levelDone() {
		return (goals.gAnimal() && goals.gPoints());
	}
	
	/**
	 * Verifie que le joueur a perdu
	 * @return
	 */
	public boolean lose() {
		if(levelDone()) return false;
		if(! goals.haveMovement()) return true;
	
		int max = this.haveTheFirstLineNoNull()+6 > gamePiece.length ? gamePiece.length : this.haveTheFirstLineNoNull() + 6;
		for(int i=haveTheFirstLineNoNull(); i<max; i++) {
			for(int j=0; j<gamePiece[0].length; j++) {
				if(gamePiece[i][j] != null && gamePiece[i][j] instanceof Bloc) {
					if(getNbAdjacentsGamePiece(i, j) >= 2) return false;
				}
			}
		}
		return true;
	}
			
	/**
	 * Verifie si la piece de coordonnees (x,y) est dans le tableau
	 * @param x (coordonnees du bloc)
	 * @param y (coordonnees du bloc)
	 * @return Retourne true si la piece est dans le tableau
	 * @deprecated
	 */
	@Deprecated
	private boolean isInsideTheTab(int x, int y) {
		return (x>=0 && x<gamePiece.length && y>=0 && y<gamePiece[x].length);
	}
	
	/**
	 * Verifie si les coordonnees (x,y) existent dans la partie visible du jeu
	 * @param x coordonnees de la piece
	 * @param y coordonnees de la piece
	 * @return si les coordonnees (x,y) existent dans la partie visible du jeu
	 */
	public boolean isInsideTheTabVisible(int x, int y) {
		int min = (haveTheFirstLineNoNull()+6 > gamePiece.length ? gamePiece.length : haveTheFirstLineNoNull()+6);
		return !(y < 0 || y >= gamePiece[0].length || x < this.haveTheFirstLineNoNull() || x >= min);
	}
	
	/**
	 * Retourne le nombre de cases adjacentes de la case donnee en parametres
	 * @param x coordonnees de la piece
	 * @param y coordonnees de la piece
	 * @return le nombre de cases adjacentes de la case donnee en parametre
	 */
	public int getNbAdjacentsGamePiece(int x, int y) {
		this.setGetAdja(x, y);
		int cmp = 0;
		for(int i=0; i<getAdja.length; i++) {
			for(int j=0; j<getAdja[i].length; j++) {
				if(getAdja[i][j]) cmp++;
			}
		}
		
		for(int i=0; i<getAdja.length; i++) {
			for(int j=0; j<getAdja[i].length; j++) {
				getAdja[i][j] = false;
			}
		}
		return cmp;
	}
	
	/**
	 * Change les valeurs des cases adjacentes de la case mis en parametre
	 * @param x 
	 * @param y
	 */
	public void setGetAdja(int x, int y) {
		int color = ((Bloc) gamePiece[x][y]).getIdColor();
		getAdja[x][y] = true;
		
		if(isInsideTheTabVisible(x-1, y)) {
			if((gamePiece[x-1][y] != null) && (gamePiece[x-1][y] instanceof Bloc) && (((Bloc) gamePiece[x-1][y]).getIdColor() == color) && (!getAdja[x-1][y])) setGetAdja(x-1,y);
		}
		if(isInsideTheTabVisible(x+1, y)) {
			if((gamePiece[x+1][y] != null) && (gamePiece[x+1][y] instanceof Bloc) && (((Bloc) gamePiece[x+1][y]).getIdColor() == color) && (!getAdja[x+1][y])) setGetAdja(x+1,y);
		}
		if(isInsideTheTabVisible(x, y-1)) {
			if((gamePiece[x][y-1] != null) && (gamePiece[x][y-1] instanceof Bloc) && (((Bloc) gamePiece[x][y-1]).getIdColor() == color) && (!getAdja[x][y-1])) setGetAdja(x,y-1);
		}
		if(isInsideTheTabVisible(x, y+1)) {
			if((gamePiece[x][y+1] != null) && (gamePiece[x][y+1] instanceof Bloc) && (((Bloc)gamePiece[x][y+1]).getIdColor() == color) && (!getAdja[x][y+1])) setGetAdja(x,y+1);
		}
		
		
	}
	
	@Override
	public void displaysOnTheTerminal() {
		System.out.println("################" + (this.lvl < 10 ? "": "#"));
		System.out.println("#   Niveau " + this.lvl + "   #");
		System.out.println("################"+ (this.lvl < 10 ? "": "#"));
		System.out.println();
		System.out.println("Il reste " + goals.nbMouv + " mouvements.");
		System.out.println("Tu as sauve " + goals.animalSaved + " / " + goals.ANIMAL_TO_SAVE + " animaux.");
		System.out.println("Tu as obtenu " + goals.pointsObtained + " / " + goals.POINTS + " points.");
		System.out.println();
		
		System.out.print("   ");
		for(int i=0; i<gamePiece[0].length; i++) {
			System.out.print((i+1) + " ");
		}
		System.out.println();
	}
	
	/**
	 * Affiche le niveau avec un code Couleur
	 * @param idAndColor
	 */
	public void displaysOnTheTerminal(Map<Integer, String> idAndColor) {
		this.displaysOnTheTerminal();
		
		int max = haveTheFirstLineNoNull() + 6 >= gamePiece.length ? gamePiece.length : haveTheFirstLineNoNull() + 6;
		for(int i=haveTheFirstLineNoNull(); i<max; i++) {
			System.out.print((i+1) + "  ");
			for(int j=0; j<gamePiece[i].length; j++) {
				if(gamePiece[i][j] == null) System.out.print(" ");
				else {
					if(gamePiece[i][j] instanceof Bloc) gamePiece[i][j].displaysOnTheTerminal(idAndColor);
					else gamePiece[i][j].displaysOnTheTerminal();
				}
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
		
		int nbLines = gamePiece.length - max;
		System.out.println("Il reste encore " + nbLines + " ligne" + (nbLines > 1 ? "s" : "") +" avant la fin du niveau.");
		System.out.println();
	}
	
	/**
	 * Goals (Objectifs) est une <b>classe interne</b> de la classe Level, elle decrit tous les objectifs que le joueur doit atteindre pour terminer le niveau.
	 * @see Level
	 */
	
	public class Goals implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private final int ANIMAL_TO_SAVE;
		private final int POINTS;
		private int animalSaved = 0;
		private int pointsObtained = 0;
		private int nbMouv;
		
		
		// CONTRUCTEUR
		
		/**
		 * Construis un object de type Goals en lui donnant un nombre d'animaux a sauver, et un nombre de points a atteindre
		 * @param animalToSave nombre d'animaux a sauver
		 * @param points nombre de points a obtenir
		 */
		public Goals(int animalToSave, int points, int nbMouv){
			this.ANIMAL_TO_SAVE = animalToSave;
			this.POINTS = points;
			this.nbMouv = nbMouv;
		}
		
		
		// ACCESSEURS (GETTEURS & SETTEURS)
		
		/**
		 * Retourne le nombre d'animaux a sauver
		 * @return le nombre d'animaux a sauver
		 */
		public int getAnimalToSave() { return ANIMAL_TO_SAVE; }
		
		/**
		 * Retourne le nombre de points a obtenir
		 * @return retourne le nombre de points a obtenir
		 */
		public int getPoints() { return POINTS; }
		
		/**
		 * Retourne le nombre d'animaux a sauver
		 * @return le nombre d'animaux a sauver
		 */
		public int getNbMouv() { return nbMouv; }
		
		/**
		 * Retourne le nombre de points a obtenir
		 */
		public void setNbMouv(int m) { nbMouv = m; }
		
		/**
		 * Retourne le nombre de points obtenus
		 * @return le nombre de points obtenus
		 */
		public int getPointsObtained() { return pointsObtained; }
		
		/**
		 * Retourne le nombre d'animaux sauves
		 * @return le nombre d'animaux sauves
		 */
		public int getNbAnimalSave() { return animalSaved; }
		
		
		// METHODES
		
		/**
		 * Verifie si le joueur a sauve tous les animaux ou pas
		 * @return
		 */
		public boolean gAnimal() {
			return (ANIMAL_TO_SAVE == animalSaved);
		}

		/**
		 * Verifie si le joueur a les points necessaires pour gagner ou pas
		 * @return
		 */
		public boolean gPoints() {
			return (POINTS <= pointsObtained);
		}
		
		/**
		 * Verifie si le joueur a encore des mouvements ou pas
		 * @return
		 */
		public boolean haveMovement() {
			return(nbMouv > 0);
		}
	}
}