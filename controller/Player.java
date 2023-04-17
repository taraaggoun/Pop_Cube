package controller;

import java.io.Serializable;
import java.util.Scanner;

import objects.BoardGame;
import objects.Level;
import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe represente un joueur.
 * Elle implemente l'interface <b>"Serializable"</b> car le jeu sauvegarde les parties des differents joueurs.<br>
 * Grâce a cette interface, le jeu peut recuperer les joueur de type <b>Human</b> et continuer leur progression de jeu.<br>
 */
public abstract class Player implements Serializable {
	
	/**
	 * Le nom du joeur
	 */
	private final String name;
	
	/**
	 * Le tableau des points maximaux que le joueur a obtenu sur les niveaux du jeu
	 */
	private int[] points = new int[20+1]; // PHRASE QUI RISQUE DE CHANGER
	
	/**
	 * Le nombre de pieces que le joueur possede
	 */
	private int nbPiece = 0;
	
	/**
	 * Dernier niveau que le joueur a termine
	 */
	private int lastLevelDone = 0;
	
	/**
	 * La liste des boosters
	 */
	private Map<String, Boolean> boosters = new HashMap<String, Boolean>();
	
	private static final long serialVersionUID = 1L;
	
	
	// CONSTRUCTEUR
	
	/**
	 * (Ne peut pas etre utilisee : <b>Player</b> est une classe abstraite)
	 * @param name
	 */
	public Player(String name) {
		this.name = name;
		boosters.put("rocket", false);
		boosters.put("bomb", false);
		boosters.put("hammer", false);
		boosters.put("disco-ball", false);
	}
	
	
	// ACCESSEURS (GETTEURS & SETTEURS)
	
	/**
	 * Retourne le nom du joueur
	 * @return name
	 */
	public String getName() { return name; }
	
	/**
	 * Retourne le nombre de pieces
	 * @return (le nombre de pieces)
	 */
	public int getNbPiece() { return nbPiece; }
	
	/**
	 * Retourne le nombre de points maximum que le joueur a obtenu sur un niveau en particulier
	 * @param lvl (le numero du niveau)
	 * @return (le nombre de points maximum du joueur sur le niveau en particulier)
	 */
	public int getPointsLvl(int lvl) { return points[lvl]; }
	
	/**
	 * Retourne le dernier niveau que le joueur a termine
	 * @return (le dernier niveau que le joueur a termine)
	 */
	public int getLastLevelDone() { return lastLevelDone; }
	
	/**
	 * Change la valeur du dernier niveau que le joueur a termine
	 * L'incremente de 1
	 */
	public void setLastLevelDone(int lvl) { if(lvl > lastLevelDone) lastLevelDone++; }
	
	/**
	 * Retourne la liste des boosters
	 */
	public Map<String, Boolean> getBoosters() { return boosters; }


	// METHODES
	/**
	 * Change le nombre de points maximal d'un niveau
	 * @param lvl (le numero du niveau)
	 * @param newScore (le nouveau score)
	 */
	public void changePoints(int lvl, int newScore) {
		if(newScore > points[lvl]) points[lvl] = newScore;
	}
	
	/**
	 * Rajoute/Enleve des pieces avec un montant donne en parametres
	 * @param diff (montant)
	 */
	public void addNbPiece(int diff) {
		nbPiece += diff;
	}
	
	/**
	 * Retourne les coordonees de la case que le joueur veut enlever
	 * @return un tableau de type int[] avec deux valeurs : la premiere : l'indice de l'abcisse, la deuxieme : l'indice de l'ordonee
	 */
	public abstract int[] chooseTheCoordinates(Level level);

	/**
	 * Choisis les boosters
	 * @param level
	 */
	public abstract void chooseTheBoosters(Level level);
	
	/**
	 * Sert a choisir le niveau sur le terminal
	 * @return (le numero du niveau que le joueur a choisi)
	 */
	public int chooseTheLevel(BoardGame g) {
		@SuppressWarnings("resource")
		Scanner scanAnswer = new Scanner(System.in);
		try {
			System.out.println("Quel niveau voulez-vous faire ?");
			String res = scanAnswer.nextLine();
			Level l = null;
			if(Integer.parseInt(res) <= g.getTotalNbLvls() && Integer.parseInt(res) > 0) {
				if(g.getLevelTab()[Integer.parseInt(res)].isAvailable() || Integer.parseInt(res)+1 >= lastLevelDone) {
					l = g.getLevelTab()[Integer.parseInt(res)];
				}
			}
			
			while (l == null) {
				System.out.println("Niveau indisponible !!");
				System.out.println("Quel niveau voulez-vous faire ?");
				res = scanAnswer.nextLine();
				if(Integer.parseInt(res) <= g.getTotalNbLvls() && Integer.parseInt(res) > 0) {
					if(g.getLevelTab()[Integer.parseInt(res)].isAvailable()) {
						l = g.getLevelTab()[Integer.parseInt(res)];
					}
				}
			}
			return l.getLvl();
		} catch (NumberFormatException e) {
			System.out.println();
			System.out.println("Rentrez un bon nombre s'il vous plait.");
			return chooseTheLevel(g);
		}
		
	}

	/**
	 * Retourne la reponse du joueur entre deux reponses possibles
	 * @param a1
	 * @param a2
	 * @param question
	 * @return
	 */
	public String makeAnswerValid2P(String a1, String a2, String question) {
		@SuppressWarnings("resource")
		Scanner scanAnswer = new Scanner(System.in);
		System.out.println(question);
		String res = "";
		res = scanAnswer.nextLine();
		while (!res.equals(a1) && !res.equals(a2)) {
			System.out.println(question);
			res = scanAnswer.nextLine();
		}
		return res;
	}
	
	/**
	 * Retourne la reponse du joueur entre quatre reponses possibles
	 * @param a1
	 * @param a2
	 * @param a3
	 * @param a4
	 * @param question
	 * @return
	 */
	public String makeAnswerValid4P(String a1, String a2, String a3, String a4, String question) {
		@SuppressWarnings("resource")
		Scanner scanAnswer = new Scanner(System.in);
		System.out.println(question);
		String res = "";
		res = scanAnswer.nextLine();
		while (!res.equals(a1) && !res.equals(a2) && !res.equals(a3) && !res.equals(a4)) {
			System.out.println(question);
			res = scanAnswer.nextLine();
		}
		return res;
	}
}
