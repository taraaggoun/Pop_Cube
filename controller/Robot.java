package controller;

import java.io.Serializable;
import java.util.Random;

import objects.*;

/**
 * Cette classe represente un joueur "robot".
 * 
 * Elle est une fille de la classe <b>"Player"</b> parce qu'un robot peut etre un joueur.<br>
 * Le deuxieme type de "Player" est l'humain, <b>Human</b>.
 * @see Player
 * @see Human
 */

public class Robot extends Player implements Serializable {
	
	private static final long serialVersionUID = 1L;

	
	// CONSTRUCTEUR
	
	/**
	 * Construit un objet de type Robot en lui donnant son nom
	 * @param name (un nom)
	 */
	public Robot(String name) {
		super(name);
	}

	
	// METHODES
	
	@Override
	public int[] chooseTheCoordinates(Level level) {
		Random r = new Random();
		int x = r.nextInt(level.haveTheFirstLineNoNull()+6);
		int y = r.nextInt(level.getGamePiece()[0].length);
		
		while(!(level.isInsideTheTabVisible(x, y) && level.getGamePiece()[x][y] != null && (level.getGamePiece()[x][y] instanceof Bloc || level.getGamePiece()[x][y] instanceof Booster) && level.canRemove(x,y))) {
			x = r.nextInt(level.haveTheFirstLineNoNull()+6);
			y = r.nextInt(level.getGamePiece()[0].length);
		}
		
		int[] coord = {x,y};
		return coord;
	}


	@Override
	public void chooseTheBoosters(Level level) {
		System.out.println("Vous voulez utiliser un booster, lequel ? Une fusee, une bombe, une boule de disco ou un marteau ?");
		String[] b = {"hammer", "bomb", "rocket", "disco-ball"};
		Random r = new Random();
		int x = r.nextInt(4);
		int[] coord = chooseTheCoordinates(level);
		level.removeBlocForBooster(coord[0], coord[1], new Booster(b[x]));
	}
}
