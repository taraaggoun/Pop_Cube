package objects;

/**
 * Cette classe represente <b>un Bonus/un Booster</b> du plateau de jeu.
 */
public class Booster extends GamePiece implements Viewable {
	
	public String type;
	
	
	// CONSTRUCTEUR
	/**
	 * Construis un object de type Booster en lui donnant un type
	 * @param type
	 */
	public Booster(String type) {
		this.type = type;
	}
	
	
	// ACCESSEURS (GETTEURS & SETTEURS)
	
	/**
	 * Retourne le type du Booster
	 * @return
	 */
	public String getType() {
		return type;
	}
	
	
	// METHODES
	
	@Override
	public void displaysOnTheTerminal() {
		System.out.println(this.type.substring(0, 1));
	}
	
	/**
	 * Convertis le type du booster en français
	 * @param type
	 * @return
	 */
	public String convertEnglishFrench(String type) {
		switch(type) {
		 case "rocket" : return "Fusee";
		 case "hammer" : return "Marteau";
		 case "disco-ball" : return "Boule de Disco";
		 default : return "Bombe";
		}
	}
	
	/**
	 * Convertis le type du booster en anglais
	 * @param type
	 * @return
	 */
	public String convertFrenchEnglish(String type) {
		switch(type) {
		 case "Fusee" : return "rocket";
		 case "Marteau" : return "hammer";
		 case "Boule de Disco" : return "disco-ball";
		 default : return "bomb";
		}
	}
}
