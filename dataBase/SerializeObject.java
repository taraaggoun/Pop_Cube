package dataBase;

import java.io.*;
import controller.Player;
import objects.*;

/**
 * Cette classe gere les sauvegardes de nos donnees.
 * Elle enregistre les joueurs, les niveaux sous forme de fichier <b>".txt"</b> et peut aussi recuperer les donnees.
 */
public class SerializeObject {
	
	/**
	 * Enregistre le niveau donne en argument
	 * @param l (le niveau que le joueur veut enregistrer)
	 */
	private void serializeLevel(Level l) {		
		try(FileOutputStream fos = new FileOutputStream("./dataBase/Levels/level" + l.getLvl() + ".txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(l);
			oos.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Enregistre un certain nombre de niveaux
	 * @param nbLevels (le nombre de niveaux qu'on veut enregistrer
	 */
	public void serializeLevels(int nbLevels) {
		KeepLevel keepLevel = new KeepLevel();
		for(int i=1; i<=nbLevels; i++) {
			Level l = keepLevel.getLevel(i);
			serializeLevel(l);
		}
	}
	
	/**
	 * Sauvegarde la partie d'un joueur donne en argument
	 * @param p (le joueur)
	 */
	public void serializePlayer(Player p) {
		try(FileOutputStream fos = new FileOutputStream("./dataBase/Players/" + p.getName() + ".txt");
			ObjectOutputStream oos = new ObjectOutputStream(fos)) {
			oos.writeObject(p);
			oos.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Recupere les donnees d'un joueur en lui donnant son nom
	 * @param name (le nom du joueur)
	 * @return (le joueur)
	 * @throws ClassNotFoundException
	 */
	public Player deserializePlayer(String name) throws ClassNotFoundException {
		Player p = null;
		try(FileInputStream fis = new FileInputStream("./dataBase/Players/" + name + ".txt");
			ObjectInputStream ois = new ObjectInputStream(fis)) {
			p = (Player) ois.readObject();
			ois.close();
		} catch(IOException e) {
			System.out.println("Vous n'avez pas de donnees en cours.");
		}
		return p;
	}
	
	/**
	 * Recupere les donnees d'un niveau en lui donnant son numero
	 * @param lvl (le numero du niveau)
	 * @return (le niveau)
	 * @throws ClassNotFoundException
	 */
	public Level deserializableLevel(int lvl) throws ClassNotFoundException {
		Level l = null;
		try(FileInputStream fis = new FileInputStream("./dataBase/Levels/level" + lvl +".txt");
			ObjectInputStream ois = new ObjectInputStream(fis)) {
			l = (Level) ois.readObject();
			ois.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return l;
	}
	
	/**
	 * Cree le plateau initial de jeu
	 * @param nbLvlFinal (le nombre de niveaux final)
	 * @return (un plateau de jeu)
	 * @throws ClassNotFoundException
	 */
	public BoardGame createBoardGameInitial(int nbLvlFinal) throws ClassNotFoundException {
		Level[] levelTab = new Level[nbLvlFinal + 1];
		levelTab[0] = null;
		for(int i=1; i<levelTab.length; i++) {
			Level l = deserializableLevel(i);
			levelTab[i] = l;
		}
		BoardGame bg = new BoardGame(0, nbLvlFinal, levelTab);
		return bg;
	}

}
