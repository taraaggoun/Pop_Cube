package launcher;

import java.util.Scanner;

import dataBase.SerializeObject;

/**
 * Cette classe est la classe principale : c'est le lanceur du jeu.
 */
public class Launcher {
	
	/**
	 *  Methode MAIN
	 *  @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main (String[] args) throws ClassNotFoundException {
		SerializeObject so = new SerializeObject();
		so.serializeLevels(20);
		
		if(args.length == 0) {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			System.out.println("Bonjour. Bienvenue. Quelle interface voulez-vous utiliser ?");
			System.out.println("Vous pouvez utiliser l'interface graphique (GUI) ou le terminal (terminal).");
			String res = sc.nextLine();
			
			while(!(res.equals("GUI") || res.equals("terminal"))) {
				System.out.println("Bonjour. Bienvenue. Quelle interface vouslez-vous utiliser ?");
				System.out.println("Vous pouvez utiliser l'interface graphique (GUI) ou le terminal (terminal).");
				res = sc.nextLine();
			}
			
			launchTheGame(res);
		} else if(args.length == 1) {
			// FORMAT DES ARGUMENTS ACCEPTES : --typeOfView=[...] --help
			String res = makeConfigArgs(args);
			if(res != null) launchTheGame(res);
		} else displayHelpAndExit(args);
	}
	
	/**
	 * Methode de lancement, elle gere la vue si le joueur veut jouer avec l'interface graphique ou avec le terminal
	 * @param res
	 * @throws ClassNotFoundException
	 */
	static void launchTheGame(String res) throws ClassNotFoundException {
		if(res.equals("GUI")) {
			View window = new View();
			window.setSize(600, 830);
			window.setVisible(true);
		} else new ViewTerminal();
	}
	
	/**
	 * Recupere les arguments de la commande et gere ses utilisations
	 * @param args (tableau des arguments de la commande)
	 * @return
	 */	
	static String makeConfigArgs(String[] args) {
		String res = null;
		if (args[0].startsWith("--typeOfView")) {
			String[] parts = args[0].split("=");
			if (parts.length == 2) {
                String pValue = parts[1];
                if(pValue.equals("GUI")) res = "GUI";
                else if(pValue.contentEquals("terminal")) res = "terminal";
                else displayHelpAndExit(args);
            } else displayHelpAndExit(args);
		} else displayHelpAndExit(args);
		return res;
	}
	
	/**
	 * Affiche le message d'erreur ou les aides aux commandes
	 */	
	static void displayHelpAndExit(String[] args) {
		System.out.print("Bonjour. ");
		if(!(args.length != 0 || args[0].equals("help"))) System.out.println("Si vous arrivez a voir ce message, vous avez sans doute saisi une commande incorrecte.\n");
		System.out.println("Il y a deux possibilites pour jouer a notre jeu :\n");
		System.out.println("- Si vous voulez jouer a notre jeu sur l'interface graphique, alors ecrivez cette commande :");
		System.out.println("java launcher.Launcher --typeOfView=GUI");
		System.out.println("Si vous voulez jouer sur le terminal, alors ecrivez cette commande :");
		System.out.println("java launcher.Launcher --typeOfView=terminal\n\n");
	}
}

