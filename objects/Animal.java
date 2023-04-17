package objects;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Cette classe represente un animal dans un niveau.
 * Le joueur doit "exploser" les blocs de couleur pour faire descendre les animaux au sol.
 * Elle extend de la classe <b>GamePiece</b> parce qu'elle est une piece insdispensable d'un niveau.
 * @see GamePiece
 */

public class Animal extends GamePiece implements Viewable, Serializable {
	
	private static final long serialVersionUID = 1L;
	public static final Map<Integer, String> imageAnimal = new HashMap<>();
	private final int i;
	
	
	// CONSTRUCTEUR
	
	/**
	 * Construis un object de type Animal
	 */
	public Animal() {
		imageAnimal.put(1, "/medias/objects/animals/filibert.png");
		imageAnimal.put(2, "/medias/objects/animals/laura.png");
		imageAnimal.put(3, "/medias/objects/animals/pauline.png");
		imageAnimal.put(4, "/medias/objects/animals/regis.png");
		imageAnimal.put(5, "/medias/objects/animals/rosalie.png");
		imageAnimal.put(6, "/medias/objects/animals/sophie.png");
		imageAnimal.put(7, "/medias/objects/animals/stefaon.png");
		Random rand = new Random();
		i = rand.nextInt(imageAnimal.size()) + 1;
	}
	
	
	// ACCESSEURS (GETTEURS & SETTEURS)
	
	/**
	 * Retourne l'ID de l'animal
	 * @return
	 */
	public int getI() { return i; }
	
	
	// METHODES
	
	@Override
	public void displaysOnTheTerminal() {
		System.out.print("A");
	}
}
