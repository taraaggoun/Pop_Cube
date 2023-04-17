package launcher;

import java.util.Map;
import java.util.Scanner;

import controller.*;
import objects.Level;
import objects.Viewable;

/**
 * Cette classe est la vue du terminal
 */
public class ViewTerminal implements Viewable {
	
	private Model model = new Model();
	private InteractionPlayerView ipv;
	
	
	// CONSTRUCTEUR
	
	/**
	 * * Construis un objet de type ViewTerminal en lui donnant un joueur et un boolean
	 */
	public ViewTerminal() throws ClassNotFoundException {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		Player p = new Human(null);
		String res1 = p.makeAnswerValid2P("o", "n", "Bonjour. Vous etes un nouveau joueur ? (o/n)");
		if(res1.equals("o")) {
			String res2 = p.makeAnswerValid2P("h", "r", "Quel type de joueur etes-vous ? Un humain (h) ou un robot (r) ?");
			System.out.println("Quel est ton nom ?");
			String res3 = sc.nextLine();
			p = (res2.equals("h") ? new Human(res3) : new Robot(res3));
			model.serializePlayer(p);
		} else {
			System.out.println("Quel est ton nom ?");
			String res3 = sc.nextLine();
			p = model.deserializablePlayer(res3);
		}
		ipv = new InteractionPlayerView(model, this, p);
		displaysOnTheTerminal();
	}
	
	
	// METHODES
	
	@Override
	public void displaysOnTheTerminal() {
		System.out.println("\n");
		System.out.println("##############################");
		System.out.println("#                            #");
		System.out.println("#       Bienvenue sur        #");
		System.out.println("#  l'interface du terminal   #");
		System.out.println("#     du jeu Pop Cubes !     #");
		System.out.println("#                            #");
		System.out.println("##############################");
		System.out.println();
		ipv.changeViewTerminal("home");
	}
	
	/**
	 * Affiche le plateau de jeu
	 */
	public void viewBoardGame() {
		model.getBoardGame().displaysOnTheTerminal(ipv.getPlayer().getLastLevelDone());
		ipv.changeViewTerminal("boardGame");
	}
	
	/**
	 * Affiche les options
	 * @param page
	 */	
	public void viewOptions(String page) {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		switch(page) {
			case "c" :
				System.out.println("Tara Aggoun et Elody Tang");
				System.out.println("Vous allez etre redirige vers la page des options.");
				viewOptions("");
				break;
			case "r" :
				System.out.println("Appuie sur les groupes de blocs pour les eliminer.");
				System.out.println("Sauve les animaux en les amenant en bas de l'ecran.");
				System.out.println("Vous allez etre redirige vers la page des options.");
				viewOptions("");
				break;
			case "b" :
				System.out.println("Boule Disco : enleve tous les blocs de la meme couleur aleatoirement (D)");
				System.out.println("Fusee : enleve les blocs d'une ligne entiere ou d'une colonne (F)");
				System.out.println("Marteau : enleve le bloc selectionne (M)");
				System.out.println("Bombe : enleve les blocs adjacentes du bloc selectionne (B)");
				System.out.println("Vous allez etre redirige vers la page des options.");
				viewOptions("");
				break;
			case "s" :
				System.out.println("\n");
				String res = ipv.getPlayer().makeAnswerValid4P("s", "r", "q", "m", "Options supplementaires :\n Vous pouvez supprimer votre partie (s), reinitialiser votre partie (r), quittez le jeu (q) ou revenir au menu (m).\nQue voulez-vous faire ?");
				if(res.equals("m")) viewOptions("");
				else ipv.changeViewTerminal("Options supplementaires");
				break;
			case "m" :
				displaysOnTheTerminal();
				break;
			case "" :
				System.out.println("\nVous pouvez voir les credits (c), les regles du jeu (r), les booters (b) ou revenir au menu (m).\nVous pouvez aussi voir les options supplementaires (s).\nQue voulez-vous faire ?");
				res = "";
				res = sc.nextLine();
				while (!res.equals("c") && !res.equals("r") && !res.equals("b") && !res.equals("s") && !res.equals("m")) {
					System.out.println("\nVous pouvez voir les credits (c), les regles du jeu (r), les booters (b) ou revenir au menu (m).\nVous pouvez aussi voir les options supplementaires (s).\nQue voulez-vous faire ?");
					res = sc.nextLine();
				}
				viewOptions(res);
				break;
			default : break;
		}
	}
	
	/**
	 * Affiche la premiere page des niveaux
	 *@param nbLevel
	 */	
	public void viewFirstPageLevel(int nbLevel) {
		Level level = model.getBoardGame().getLevelTab()[nbLevel];
		System.out.println("\n");
		System.out.println("Objectifs a atteindre : ");
		System.out.println("(1) Sauve " + level.getGoals().getAnimalToSave() + " animaux.");
		System.out.println("(2) Obtiens " + level.getGoals().getPoints() + " points.\n");
		System.out.println("Les joueurs peuvent commencer avec des booters (Bombe (b), Fusee (f), Boule Disco (b), Marteau (m)) en debut de partie. Pour mettre en avant l'interface graphique, les booters ne sont pas disponibles sur cette interface.\n");
		ipv.changeViewTerminalForLevelPage("first page level", level);
	}
	
	/**
	 * Affiche la seconde page des niveaux
	 */	
	public void viewSecondPageLevel() {
		System.out.print("\n");
		if(model.getIdAndColor().size() == 0) {
			model.addIdColor();
		}
		Map<Integer, String> idAndColor = model.getIdAndColor();
		model.getCurrentLevel().displaysOnTheTerminal(idAndColor);
		ipv.changeViewTerminalForLevelPage("level-run question", model.getCurrentLevel());
	}
	
	/**
	 * Affiche les legendes
	 */	
	public void seeLegend() {
		System.out.println("\n");
		System.out.println("Legende des lettres :");
		System.out.println("A : Animal");
		System.out.println("# : Mur");
		System.out.println("R : Bloc Rouge (red)");
		System.out.println("Y : Bloc Jaune (yellow)");
		System.out.println("P : Bloc Violet (purple)");
		System.out.println("B : Bloc Bleu (blue)");
		System.out.println("G : Bloc Vert (green)\n");
		System.out.println("h : Marteau (hammer)");
		System.out.println("r : Fusee (rocket)");
		System.out.println("d : Boule de Disco (disco-ball)");
		System.out.println("b : Bombe (bomb)");
		System.out.println();
		ipv.changeViewTerminalForLevelPage("level-run question", model.getCurrentLevel());
	}
}
