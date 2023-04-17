package controller;

import java.io.Serializable;
import java.util.Scanner;

import objects.Booster;
import objects.Level;

/**
 * Cette classe represente un joueur "humain".
 * 
 * Elle est une fille de la classe <b>"Player"</b> parce qu'un humain peut etre un joueur.<br>
 * Le deuxieme type de "Player" est le <b>Robot</b>.
 * @see Player
 * @see Robot
 */
public class Human extends Player implements Serializable {
	
	/**
	 * Nombre de vies (initialisee a 5 au debut du jeu)
	 */
	private int nbLife = 5;
	private static final long serialVersionUID = 1L;
	
	
	// CONSTRUTEUR
	
	/**
	 * Construit un objet de type "Human" en lui donnant un nom
	 * @param name (nom du Joueur)
	 */
	public Human(String name) {
		super(name);
	}
	
	
	// ACCESSEURS (GETTEURS & SETTEURS)
	
	/**
	 * Retourne le nombre de vie du joeur
	 * @return nbLife (le nombre de vie du joueur)
	 */
	public int getNbLife() { return nbLife; }
	
	
	// METHODES
	
	/**
	 * Enleve une vie au joueur
	 */
	public void decreaseNbLife() { nbLife--; }
	
	/**
	 * Le nombre de vie est renevu a 5
	 */
	public void relive() { nbLife = 5; }
	
	@Override
	public void chooseTheBoosters(Level level) {
		String res = makeAnswerValid4P("bomb", "rocket", "disco-ball", "hammer", "Vous voulez utiliser un booster, lequel ? Une fusee (rocket), une bombe (bomb), une boule de disco (disco-ball) ou un marteau (hammer) ?");
		int[] coord = chooseTheCoordinates(level);
		level.removeBlocForBooster(coord[0], coord[1], new Booster(res));
	}

	@Override
	public int[] chooseTheCoordinates(Level level) {
		@SuppressWarnings("resource")
		Scanner scanAnswer = new Scanner(System.in);
		try {
			System.out.print("Indice de l'ordonnee du bloc que vous voulez enlever : ");
			int x = Integer.parseInt(scanAnswer.nextLine())-1;
			System.out.print("Indice de l'abscisse du bloc que vous voulez enlever : ");
			int y = Integer.parseInt(scanAnswer.nextLine())-1;
		
			while(!level.isInsideTheTabVisible(x, y)) {
				System.out.print("Indice de l'ordonnee du bloc que vous voulez enlever : ");
				x = Integer.parseInt(scanAnswer.nextLine())-1;
				System.out.print("Indice de l'abscisse du bloc que vous voulez enlever : ");
				y = Integer.parseInt(scanAnswer.nextLine())-1;
			}
			
			int[] coord = {x,y};
			return coord;
		} catch(NumberFormatException e) {
			System.out.println("Rentrez un bon nombre s'il vous plait.");
			return chooseTheCoordinates(level);
		}
	}
	
}
