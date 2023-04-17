package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import launcher.*;
import objects.Level;

/**
 * Cette classe represente les interactions entre le Joueur et la vue qu'il utilise.
 * La vue peut etre de type <b>View</b> (GUI) ou de type <b>ViewTerminal</b> (terminal).<br>
 * Cette classe sert de <em>Controleur</em> pour le design pattern ("Vue-Modele-Controleur").
 * @see ViewTerminal
 * @see View
 */
public class InteractionPlayerView {
	
	/**
	 * Le modele du design pattern ("Vue-Modele-Controleur")
	 */
	protected Model model;
	/**
	 * Le joeur du design pattern ("Vue-Modele-Controleur")
	 */
	protected Player player;
	/**
	 * La vue qui se charge de l'interface graphique (GUI) du design pattern ("Vue-Modele-Controleur")
	 */
	private View mainView = null;
	/**
	 * La vue qui se change de la vue terminale
	 */
	private ViewTerminal viewTerminal = null;
	
	
	// CONSTRUCTEUR
	
	/**
	 * Construit un objet de type InteractionPlayerView en lui donnant un Model (modele), une View (vue) et un Player (joueur)
	 */
	public InteractionPlayerView(Model m, View v, Player p) {
		this.model = m;
		this.mainView = v;
		this.player = p;
	}
	
	/**
	 * Construit un objet de type InteractionPlayerView en lui donnant un Model (modele), une ViewTerminal (vue terminale) et un Player (joueur) 
	 */
	public InteractionPlayerView(Model m, ViewTerminal v, Player p) {
		this.model = m;
		this.viewTerminal = v;
		this.player = p;
	}

	
	// ACCESSEURS (GETTEURS & SETTEURS)
	
	/**
	 * Retourne le joueur
	 * @return (Player)
	 */
	public Player getPlayer() { return player; }
	
	
	// METHODES
	
	
	// METHODES POUR CHANGER DE PAGE SUR L'INTERFACE GRAPHIQUE
	
	/**
	 * Facilite le changement de vue de l'interface graphique (GUI)
	 * @param page (la page que le Joueur veut voir)
	 */
	public void changePage(String page) {
		this.mainView.changePage(page);
	}
	
	
	// METHODES POUR CHANGER DE PAGE SUR LE TERMINAL
	
	/**
	 * Facilite le changement de vue sur le terminal
	 * @param previousPage (la page que le joueur veut voir)
	 */
	public void changeViewTerminal(String previousPage) {
		String res = "";
		switch(previousPage) {
			case "home" :
				res = player.makeAnswerValid2P("p", "o", "Que voulez-vous faire, voir le plateau (p) ou voir les options (o) ?");
				if(res.equals("p")) this.viewTerminal.viewBoardGame();
				else if(res.equals("o")) this.viewTerminal.viewOptions("");
				break;
			case"Options supplementaires" : optionsSup(res); break;
			case "boardGame" :
				res = player.makeAnswerValid2P("j", "m", "Que voulez-vous faire, jouer a un niveau (j) ou revenir sur le menu (m) ?");
				if(res.equals("j")) {
					int l = player.chooseTheLevel(model.getBoardGame());
					this.viewTerminal.viewFirstPageLevel(l);
				}
				else if(res.equals("m")) this.viewTerminal.displaysOnTheTerminal();
				break;
			default : break;
		}
	}
	
	/**
	 * Facilite le changement de vue, au niveau des niveaux, sur le terminal
	 * @param previousPage (la page que le joueur veut voir)
	 * @param level (un niveau du jeu)
	 */
	public void changeViewTerminalForLevelPage(String previousPage, Level level) {
		String res = "";
		switch(previousPage) {
			case "first page level" :
				res = player.makeAnswerValid2P("j", "p", "Que voulez-vous faire, jouer au niveau (j) ou retourner au plateau (p) ?");
				if(res.equals("j")) {
					model.changeCurrentLevel(level.getLvl());
					this.viewTerminal.viewSecondPageLevel();
				}
				else if(res.equals("p")) this.viewTerminal.viewBoardGame();
				break;
			case "level-run question" :
				while(!(model.getCurrentLevel().levelDone() || model.getCurrentLevel().lose())) {
					res = player.makeAnswerValid4P("l", "e", "b", "a", "Que voulez-vous faire, voir la legende des lettres (l), enlever des blocs (e), utiliser un bonus (b) ou abandonner (a) ?");
					if(res.equals("a")) {
						model.setCurrentLevel(null);
						model.getIdAndColor().clear();
						this.viewTerminal.viewBoardGame();
					} else {
						if(res.equals("l")) this.viewTerminal.seeLegend();
						else if(res.equals("e")) {
							int[] coord = player.chooseTheCoordinates(model.getCurrentLevel());
							model.getCurrentLevel().removeBloc(coord[0], coord[1]);
						} else player.chooseTheBoosters(model.getCurrentLevel());
						
						System.out.print("\n");
						Map<Integer, String> idAndColor = model.getIdAndColor();
						model.getCurrentLevel().displaysOnTheTerminal(idAndColor);
					}
				}
				
				if(model.getCurrentLevel().levelDone()) {
					System.out.println("Bravo ! Vous avez gagne !");
					model.playerWin(player);
					res = player.makeAnswerValid2P("next", "p", "Que voulez-vous faire ensuite, faire le niveau suivant (next) ou revenir au plateau (p)?");
					if(res.equals("next")) {
						if(model.getCurrentLevel().getLvl() < model.getBoardGame().getTotalNbLvls()) {
							Level l = model.getBoardGame().getLevelTab()[model.getCurrentLevel().getLvl()+1];
							model.getBoardGame().getLevelTab()[model.getCurrentLevel().getLvl()+1].setAvailable(true);
							model.changeCurrentLevel(l.getLvl());
							this.viewTerminal.viewFirstPageLevel(l.getLvl());
						} else System.out.println("Il n'y a plus de niveaux dispo.");
					} else this.viewTerminal.viewBoardGame();
				} else if(model.getCurrentLevel().lose()) {
					System.out.println("\n");
					System.out.println("Oh non ! Vous avez perdu !");
					model.playerLose(player);
					res = player.makeAnswerValid2P("r", "p", "Que voulez-vous faire, rejouer (r) ou revenir au plateau (p) ?");
					if(res.equals("r")) {
						model.setCurrentLevel(model.getBoardGame().getLevelTab()[model.getCurrentLevel().getLvl()]);
						viewTerminal.viewFirstPageLevel(model.getCurrentLevel().getLvl());
					} else this.viewTerminal.viewBoardGame();
				}
				break;
			default : break;
		}
	}
	
	/**
	 * Gere les options supplementaires en fonction de ce que veut le joueur
	 * Comme options supplementaires, il y a : "Reinitialiser sa partie", "Supprimer sa partie", et "Quitter le jeu".
	 * @param ans
	 */
	public void optionsSup(String ans) {
		switch(ans) {
			case "r" :
				Player p = (player instanceof Human ? new Human(player.getName()) : new Robot(player.getName()));
				model.serializePlayer(p);
				if(viewTerminal != null) System.exit(0);
				else mainView.dispose();
				break;
			case "s" :
				Path path = Paths.get("./dataBase/Players/" + player.getName() + ".txt");
				try {
					Files.delete(path);
					System.out.println("Donnees supprimees !");
					if(viewTerminal != null) System.exit(0);
					else mainView.dispose();
				} catch (IOException e) {
					System.out.println("Erreur, donnees introuvables !");
				}
				break;
			case "q" :
				if(viewTerminal != null) System.exit(0);
				else mainView.dispose();
				break;
			default : break;
		}
	}
}
