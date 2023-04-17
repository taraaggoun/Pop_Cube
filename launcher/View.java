package launcher;

import java.awt.*;
import java.util.Map;

import javax.swing.*;

import controller.*;
import objects.*;
import objects.Level.Goals;

/**
 * Vue de l'interface graphique (GUI)
 */
public class View extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Model model = new Model();
	private InteractionPlayerView ipv;
	private CardLayout cardLayout = new CardLayout();
	private JPanel mainPanelBold = new JPanel(cardLayout);
	
	
	// CONSTRUCTEUR
	
	/**
	 * Construis un objet de type View
	 */
	View() throws ClassNotFoundException {
		UIManager.put("ProgressBar.selectionBackground", Color.black);
        UIManager.put("ProgressBar.selectionForeground", Color.white);
        UIManager.put("ProgressBar.foreground", new Color(81,123,113,200));
		
		this.setTitle("Pop Cubes");
		this.setResizable(false);
		this.setAlwaysOnTop(true);
		
		this.getContentPane().add(mainPanelBold);
		
		this.mainPanelBold.add("param", paramAndPlayer());
		cardLayout.show(mainPanelBold, "param");
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	
	// METHODES & FONCTIONS
	
	
	// METHODES QUI INTERAGIT AVEC LE CONTROLLEUR
	/**
	 * Change la vue du JPanel principal
	 * @param page (le nom de la page)
	 */
	public void changePage(String page) {
		cardLayout.show(mainPanelBold, page);
	}
	
	
	// METHODES POUR AJOUTER FACILEMENT DES JPANELS DANS LE CARDLAYOUT
	
	/**
	 * Ajoute plusieurs elements dans le CardLayout pour l'affichage
	 */
	private void addElementSimple() {
		mainPanelBold.add("home", toTheHomePage());
		mainPanelBold.add("options1", toTheOptionsPage(true));
		mainPanelBold.add("options2", toTheOptionsPage(false));
		
		JPanel rules = toSimplePage("/medias/view/rules-page.png", makeTheText(1), "options1", true);
		mainPanelBold.add("rules", rules);
		
		JPanel credits = toSimplePage("/medias/view/credits-page.png", makeTheText(2), "options1", true);
		mainPanelBold.add("credits", credits);
		
		JPanel help = toSimplePage("/medias/view/help-page.png", makeTheText(0), "options1", false);
		mainPanelBold.add("help", help);
		
		JPanel boardGame = toTheBoardGame();
		mainPanelBold.add("boardGame", boardGame);
		
		JPanel maj = toSimplePage("/medias/view/maJ-page.png", makeTheText(3), "boardGame", true);
		mainPanelBold.add("maj", maj);
	}
	
	/**
	 * Renvoie le texte que le JPanel doit afficher
	 * @param version (un numero d'ID)
	 * @return
	 */
	private String[] makeTheText(int version) {
		String text;
		switch(version) {
			case 1 : text = "Regles du jeu,Objectifs,<html><p align=\"center\">Sauve les animaux en les amenant en bas de l'ecran.</p></html>,<html><p align=\"center\">Clique sur les groupes de blocs pour les eliminer !</p></html>"; break;
			case 2 : text = "Credits,Production,<html><p align=\"center\">Tara Aggoun<br> taraggoun@gmail.com</p></html>,<html><p align=\"center\">Elody Tang<br>elodytang@hotmail.fr</p></html>"; break;
			case 3 : text = "Mise a jour !,PLUS DE NIVEAUX ?,<html><p align=\"center\">De nouveaux niveaux <br>arrivent bientot !</p></html>,<html><p align=\"center\">Ce jeu est en cours de developpement. Restez a l'ecoute !</p></html>"; break;
			case 4 : text = "Regles du jeu,Explication des boosters,Credits"; break;
			case 5 : text = "Reinitialiser sa partie,Supprimer sa partie,Quitter le jeu"; break;
			default : text = "Explications des boosters,Fusee,Enleve tous les blocs sur une meme colonne,Boule de Disco,Enleve tous les blocs de la meme couleur (choisie).,Bombe,Enleve tous les blocs adjacents d'un bloc.,Marteau,Enleve uniquement un bloc."; break;
		}
		return text.split(",");
	}
	
	
	// METHODES POUR FACILITER LA CREATION DES VUES (TOUT LE TEMPS UTILISES)
	
	/**
	 * Creation d'un beau bouton avec une Icon en fond et un texte
	 * @param text (texte qui apparait sur le bouton)
	 * @param pathIcon (chemin de l'image en fond)
	 * @return
	 */
	private JButton makeABeautifulButton(String text, String pathIcon) {
		JButton res = new JButton(text, new ImageIcon(getClass().getResource(pathIcon)));
		res.setBackground(new Color(0,0,0,0));
		res.setFont(new Font("SansSerif", Font.PLAIN, 20));
		res.setForeground(new Color(86,86,86));
		res.setBorderPainted(false);
		res.setVerticalTextPosition(SwingConstants.CENTER);
		res.setHorizontalTextPosition(SwingConstants.CENTER);
		res.setDisabledIcon(new ImageIcon(getClass().getResource(pathIcon)));
		return res;
	}
	
	/**
	 * Creation d'un JLabel qui sera le fond d'ecran de la vue 
	 * @param pathIcon (c'est le chemin de l'image en fond)
	 * @param p (panneau)
	 */
	private void makeABeautifulBackground(String pathIcon, JPanel p) {
		JLabel res = makeABeautifulLabel(null,pathIcon);
		res.setBounds(0, 0, 600, 800);
		p.add(res);
	}
	
	/**
	 * Creation d'un JLabel avec une image ou un texte (ou les deux) 
	 * @param pathIcon (c'est le chemin de l'image en fond)
	 * @return
	 */
	private JLabel makeABeautifulLabel(String text, String pathIcon) {
		JLabel res = new JLabel(text, 0);
		res.setIcon(new ImageIcon(getClass().getResource(pathIcon)));
		res.setForeground(Color.white);
		res.setFont(new Font("SansSerif", Font.BOLD, 20));
		res.setVerticalTextPosition(SwingConstants.CENTER);
		res.setHorizontalTextPosition(SwingConstants.CENTER);
		return res;
	}
	
	/**
	 * Creation d'un bouton X 
	 * @param out (page de l'event quand on clique dessus)
	 * @return
	 */
	private JButton makeAXButton(String out) {
		JButton X = makeABeautifulButton(null, "/medias/view/X.png");
		X.setBounds(20, 530, 84, 60);
		X.addActionListener ((event) -> {
			if(out != null) {
				if(out.equals("boardGame")) this.mainPanelBold.add("boardGame", toTheBoardGame());
				ipv.changePage(out);
			} else {
				this.dispose();
			}
		});
		return X;
	}
	
	/**
	 * Creation d'un JPanel utile avec un rectangle blanc au milieu
	 * @param title (le titre du panneau
	 * @param out (la sortie)
	 * @return
	 */
	private JPanel makeTheRectangleWhite(String title, String out) {
		JPanel mainPanel = new JPanel(null);
		mainPanel.setBackground(Color.WHITE);
		mainPanel.setBounds(50, 100, 500, 600);
		
		JButton X = makeAXButton(out);
		if(!(out != null && out.equals(""))) mainPanel.add(X);
		
		JLabel t = new JLabel(title, 0);
		t.setFont(new Font("SansSerif", Font.BOLD, 22));
		t.setForeground(Color.white);
		t.setOpaque(true);
		t.setBackground(new Color(88,188,169));
		t.setBounds(0, 0, 500, 80);
		mainPanel.add(t);
		
		JLabel end = new JLabel();
		end.setOpaque(true);
		end.setBackground(new Color(88,188,169));
		end.setBounds(0, 520, 500, 80);
		mainPanel.add(end);
		
		return mainPanel;
	}
	
	/**
	 * Creation d'un Radiobutton
	 * @param name
	 * @param b
	 * @return
	 */
	private JRadioButton makeBeautifulRadioButton(String name, ButtonGroup b) {
		JRadioButton res = new JRadioButton(name);
		res.setFont(new Font("SansSerif", Font.PLAIN, 15));
		res.setHorizontalAlignment(0);
		res.setOpaque(false);
		b.add(res);
		return res;
	}
	
	
	// METHODES POUR LES ELEMENTS PLUS RARES 
	
	/**
	 * Creation d'un JPanel qui sera la grille de boosters
	 * @param c (coueur du background)
	 * @param page (true = 'first page level', false = 'second page level')
	 * @return
	 */
	private JPanel makeGridBoosters(Color c, boolean page) {
		GridLayout grid = new GridLayout(1,4);
		grid.setHgap(10);
		JPanel booster = new JPanel(grid);
		booster.setBackground(c);
		String[] boosters = {"bomb", "rocket", "disco-ball", "hammer"};
		
		for(int i=0; i<boosters.length; i++) {
			int x = i;
			JButton b = makeABeautifulButton(null, "/medias/objects/boosters/" + boosters[i] + (page ? "_2" : "") + ".jpg");
			b.setBorderPainted(true);
			
			boolean lock = model.boostersIsLock(ipv.getPlayer(), boosters[i]);
			
			if(!lock) {
				b.setBackground(new Color(250, 248, 225));
				b.setEnabled(false);
				b.setDisabledIcon(new ImageIcon(getClass().getResource("/medias/objects/boosters/lock" + (page ? "_2" : "") + ".png")));
			} else {
				if(!page) {
					b.setBackground(Color.white);
					if(model.getEvents().containsKey(boosters[x])) {
						b.setBackground(new Color(250, 248, 225));
						b.addActionListener((event) -> {
							model.getEvents().remove(boosters[x]);
							mainPanelBold.add("firstPageL" + model.getCurrentLevel().getLvl(), toFirstPageOfLevel(model.getCurrentLevel()));
							ipv.changePage("firstPageL" + model.getCurrentLevel().getLvl());
						});
					} else b.addActionListener((event) -> {
						model.getEvents().put(boosters[x], true);
						mainPanelBold.add("firstPageL" + model.getCurrentLevel().getLvl(), toFirstPageOfLevel(model.getCurrentLevel()));
						ipv.changePage("firstPageL" + model.getCurrentLevel().getLvl());
					});
				} else {
					b.setBorder(BorderFactory.createLineBorder(Color.white, 1));
					if(model.getEvents().size() != 0) {
						if(!model.getEvents().containsKey(boosters[i])) {
							b.setEnabled(false);
							b.setDisabledIcon(null);
						} else {
							b.addActionListener((event) -> {
								model.getEvents().clear();
								mainPanelBold.add("secondPageL" + model.getCurrentLevel().getLvl(), toLevelPage());
								ipv.changePage("secondPageL" + model.getCurrentLevel().getLvl());
							});
						}
					} else {
						b.addActionListener((event) -> {
							model.getEvents().put(boosters[x], true);
							mainPanelBold.add("secondPageL" + model.getCurrentLevel().getLvl(), toLevelPage());
							ipv.changePage("secondPageL" + model.getCurrentLevel().getLvl());
						});
					}
				}
			}		
			b.setBorder(BorderFactory.createLineBorder(Color.white, 1));
			booster.add(b);
		}
		return booster;
	}
	
	/**
	 * Creation d'une page simple, avec un JPanel "rectangle" au milieu 
	 * @param pathImage (path de l'image)
	 * @param text (texte de la page)
	 * @param out (nom de la page de sortie)
	 * @param page (true = page des aides, false = page des boosters)
	 * @return
	 */
	private JPanel toSimplePage(String pathImage, String[] text, String out, boolean page) {
		JPanel mainPanel = new JPanel(null);
		
		JPanel rectangle = makeTheRectangleWhite(text[0], out);
		mainPanel.add(rectangle);
		JPanel content = simplePageParam(pathImage, text, page);
		rectangle.add(content);

		this.makeABeautifulBackground("/medias/view/backgroundSimple2.png", mainPanel);
		return mainPanel;
	}
	
	/**
	 * Renvoie le contenu de la page des parametres en fonction de ses arguments
	 * @param pathImage (path de l'image)
	 * @param text (textes de la page)
	 * @param page (true = page des aides, false = page des boosters)
	 * @return
	 */
	private JPanel simplePageParam(String pathImage, String[] text, boolean page) {
		JPanel content = new JPanel(null);
		if(page) {
			GridLayout grid = new GridLayout(1,2);
			grid.setHgap(20);
			content.setLayout(grid);
			simplePageContentTrue(content, text, 200, 260, 0);
			JLabel image = makeABeautifulLabel(null,pathImage);
			content.add(image);
		} else {
			GridLayout grid = new GridLayout(4,1);
			grid.setVgap(20);
			content.setLayout(grid);
			
			for(int i=-1; i<=5; i += 2) {
				String[] res = new String[3];
				res[0] = text[i+2];
				res[1] = text[i+1+2];
				res[2] = (new Booster(null)).convertFrenchEnglish(res[0]);
				simplePageContentFalse(content, res);
			}
		}
		content.setBounds(50, 150, 400, 300);
		content.setBackground(Color.white);
		
		return content;
	}
	
	/**
	 * Creer le contenu de la page des parametres (credits, regles...)
	 * @param content (le panneau qui le lit)
	 * @param text (textes de la page)
	 * @param width (taille de l'image)
	 * @param height (taille de l'image)
	 * @param diff (difference entre les panneaux)
	 */
	private void simplePageContentTrue(JPanel content, String[] text, int width, int height, int diff) {
		JPanel groupText = new JPanel(null);
		groupText.setBackground(new Color(0,0,0,0));
		groupText.setBounds(diff, 0, width, 300);
		content.add(groupText);
		
		JLabel t1 = new JLabel(text[1], 0);
		t1.setBounds(diff, 0, width, 30);
		t1.setOpaque(true);
		t1.setFont(new Font("SansSerif", Font.PLAIN, 15));
		t1.setBackground(new Color(238,229,203));
		groupText.add(t1);
		
		GridLayout grid1 = new GridLayout(2,1);
		grid1.setVgap(10);
		JPanel textG = new JPanel(grid1);
		textG.setBackground(Color.white);
		textG.setBounds(0, 50, width, height);
		groupText.add(textG);
		
		String[] txtString = {text[2], text[3]};
		for(int i=0; i<txtString.length; i++) {
			JLabel txt = new JLabel(txtString[i], 0);
			txt.setFont(new Font("SansSerif", Font.PLAIN, 13));
			txt.setOpaque(true);
			txt.setBackground(new Color(250, 248, 225));
			textG.add(txt);
		}
	}
	
	/**
	 * Creer le contenu de la page des parametres (Explication des boosters)
	 * @param content (le panneau qui le lit)
	 * @param parts (le texte de la page)
	 */
	private void simplePageContentFalse(JPanel content, String[] parts) {
		JPanel groupText = new JPanel(null);
		groupText.setBackground(new Color(0,0,0,0));
		content.add(groupText);
		
		JLabel image = makeABeautifulLabel(null, "/medias/objects/boosters/" + parts[2] + ".png");
		image.setBounds(0, 0, 65, 65);
		groupText.add(image);
		
		JLabel t3 = new JLabel(parts[0], 0);
		t3.setBounds(80, 0, 100, 20);
		t3.setOpaque(true);
		t3.setFont(new Font("SansSerif", Font.PLAIN, 10));
		t3.setBackground(new Color(238,229,203));
		groupText.add(t3);
		
		JLabel t4 = new JLabel(parts[1], 0);
		t4.setBounds(80, 30, 320, 30);
		t4.setOpaque(true);
		t4.setFont(new Font("SansSerif", Font.PLAIN, 10));
		t4.setBackground(new Color(250, 248, 225));
		groupText.add(t4);
	}
	
	
	// METHODES POUR CREER DES PANNEAUX FACILEMENT
	
	
	// PAGE D'ACCUEIL (HOME)

	/**
	 * Creation de la page d'accueil et son affichage
	 * @return
	 */
	private JPanel toTheHomePage() {
		JPanel mainPanel = new JPanel(null);
		
		JButton playNow = makeABeautifulButton("Jouer", "/medias/view/template-button-bold.png");
		playNow.setFont(new Font("SansSerif", Font.BOLD, 25));
		playNow.setForeground(Color.white);
		playNow.setBounds(140, 675, 320, 65);
		mainPanel.add(playNow);
		playNow.addActionListener ((event) -> {
			ipv.changePage("boardGame");
		});
	
		JButton options = makeABeautifulButton(null, "/medias/view/button-options.png");
		options.setBounds(10, 10, 40, 40);
		mainPanel.add(options);
		options.addActionListener ((event) -> {
			ipv.changePage("options1");
		});
		
		this.makeABeautifulBackground("/medias/view/background-with-logo.png", mainPanel);
		return mainPanel;
	}
	
	
	// PAGE POUR LES PARAMETRES ET L'ENREGISTREMENT DES JOUEURS
	
	/**
	 * Creation de la page de l'enregistrement des donnees du joueur
	 * @return
	 */
	private JPanel paramAndPlayer() {
		JPanel mainPanel = new JPanel(null);
		
		JButton play = makeABeautifulButton("Valider", "/medias/view/template-button.png");
		play.setForeground(Color.white);
		play.setFont(new Font("SansSerif", Font.BOLD, 25));
		play.setBounds(210, 630, 180, 60);
		mainPanel.add(play);
		
		JPanel rectangle = this.makeTheRectangleWhite("Recuperation des donnees", null);
		mainPanel.add(rectangle);
		
		// Creation d'un panneau pour connaitre les parametres
		JPanel content = new JPanel(null);
		content.setBounds(50, 130, 400, 350);
		content.setBackground(Color.white);
		rectangle.add(content);
		
		ButtonGroup typeOfPlayer = new ButtonGroup();
		JRadioButton human = makeBeautifulRadioButton("Humain", typeOfPlayer);
		human.setEnabled(false);
		ButtonGroup newPlayer = new ButtonGroup();
		JRadioButton yes = makeBeautifulRadioButton("Oui", newPlayer);
		JRadioButton no = makeBeautifulRadioButton("Non", newPlayer);
		
		// Nouveau joueur ou pas ?
		pageParamSubTitle(content, "Nouveau joueur ?", 0);
		
		JPanel yesNo = pageParamGridLayout(40);
		yesNo.add(yes);
		yesNo.add(no);
		content.add(yesNo);
		
		yes.addActionListener((event) -> {
			human.setEnabled(true);
			human.setSelected(true);
		});
		
		no.addActionListener((event) -> {
			human.setEnabled(false);
		});
		

		// Type de joueur (humain ou robot)
		pageParamSubTitle(content, "Type de joueur ?", 130);
		
		JPanel humanRobot = pageParamGridLayout(170);
		humanRobot.add(human);
		content.add(humanRobot);
		
		// Nom du joueur ?
		pageParamSubTitle(content, "Nom ?", 260);
		
		JTextField nameField = new JTextField();
		nameField.setBounds(0, 300, 400, 30);
		nameField.setHorizontalAlignment(0);
		nameField.setBorder(BorderFactory.createLineBorder(new Color(250,248,225), 2));
		content.add(nameField);
		
		play.addActionListener((event) -> {
			if(!nameField.getText().equals("")) {
				Player p;
				if(yes.isSelected()) {
					p = new Human(nameField.getText());
					model.serializePlayer(p);
					ipv = new InteractionPlayerView(model, this, p);
					this.addElementSimple();
					ipv.changePage("home");
				} else if(no.isSelected()) {
					p = model.deserializablePlayer(nameField.getText());
					if(p != null && p instanceof Human) {
						ipv = new InteractionPlayerView(model, this, p);
						this.addElementSimple();
						ipv.changePage("home");
					}
				}
			}
		});
		
		this.makeABeautifulBackground("/medias/view/backgroundSimple2.png", mainPanel);
		return mainPanel;
	}
	
	/**
	 * Creer des sous-titres et les lit au JPanel donne en argument
	 * @param content (le panneau)
	 * @param title (le titre)
	 * @param diff (la difference entre les panneaux)
	 */
	private void pageParamSubTitle(JPanel content, String title, int diff) {
		JLabel t = new JLabel(title, 0);
		if(title.equals("0bjectifs")) t.setBounds(0, diff, 350, 30);
		else if(title.equals("Nouveau Booster debloque !"))  t.setBounds(0, diff, 350, 60);
		else t.setBounds(0, diff, 400, 30);
		t.setOpaque(true);
		t.setFont(new Font("SansSerif", Font.PLAIN, 17));
		t.setBackground(new Color(238,229,203));
		content.add(t);
	}
	
	/**
	 * Creer un GridLayout(1,2)
	 * @param diff (difference entre les panneaux)
	 * @return
	 */
	private JPanel pageParamGridLayout(int diff) {
		GridLayout grid = new GridLayout(1,2);
		grid.setHgap(30);
		JPanel panel = new JPanel(grid);
		panel.setBackground(new Color(250, 248, 225));
		panel.setBounds(0, diff, 400, 60);
		return panel;
	}
	
	
	// PAGE DES OPTIONS
	
	/**
	 * Creation de la page des options
	 * @return
	 */
	private JPanel toTheOptionsPage(boolean page) {
		JPanel mainPanel = new JPanel(null);
		
		JPanel rectangle = makeTheRectangleWhite("Options", "home");
		mainPanel.add(rectangle);
		
		GridLayout grid = new GridLayout(3,1);
		grid.setVgap(10);
		JPanel content = new JPanel(grid);
		content.setBounds(50, 130, 400, 350);
		content.setBackground(Color.white);
		rectangle.add(content);
		
		JButton[] button;
		if(page) button = makeButtonsForTheOptionsPage(true);
		else button = makeButtonsForTheOptionsPage(false);
		for(int i=0; i<button.length; i++) {
			content.add(button[i]);
		}
		
		JButton changePage;
		if(page) {
			changePage = new JButton(">");
			changePage.setBounds(450, 275, 50, 50);
			changePage.addActionListener((event) -> {
				ipv.changePage("options2");
			});
		} else {
			changePage = new JButton("<");
			changePage.setBounds(0, 275, 50, 50);
			changePage.addActionListener((event) -> {
				ipv.changePage("options1");
			});
		}
		changePage.setBackground(new Color(0,0,0,0));
		changePage.setBorderPainted(false);
		changePage.setFont(new Font("SansSerif", Font.BOLD, 15));
		changePage.setForeground(new Color(81, 123, 113));
		rectangle.add(changePage);
		
		this.makeABeautifulBackground("/medias/view/backgroundSimple2.png", mainPanel);
		return mainPanel;
	}
	
	
	/**
	 * Creer les boutons de la pages des options
	 * @param page (true = "options1, false = options2)
	 * @return
	 */
	private JButton[] makeButtonsForTheOptionsPage(boolean page) {
		JButton[] button = new JButton[3];
		String[] parts = (page ? makeTheText(4) : makeTheText(5));
		
		for(int i=0; i<parts.length; i++) {
			int x = i;
			button[i] = makeABeautifulButton(parts[i], "/medias/view/template-button-simple.png");
			button[i].addActionListener((event) -> {
				String out = null;
				if(parts[x].equals("Regles du jeu")) out = "rules";
				else if(parts[x].equals("Explication des boosters")) out = "help";
				else if(parts[x].equals("Credits")) out = "credits";
				else if(parts[x].equals("Reinitialiser sa partie")) parts[x] = "r";
				else if(parts[x].equals("Supprimer sa partie")) parts[x] = "s";
				else parts[x] = "q";
				
				if(out != null) ipv.changePage(out);
				else ipv.optionsSup(parts[x]); 
			});
		}
		return button;
	}
	
	
	// PAGE DU PLATEAU DE JEU (BOARDGAME)
	
	/**
	 * Creation de la page du plateau
	 */
	private JPanel toTheBoardGame() {
		JPanel mainPanel = new JPanel(null);
		
		makeButtonUpPanel(mainPanel);
		
		int lvlMin = (model.getNbPages() == 1 ? 1 : model.getNbPages() * model.getNB_LVL_IN_A_PAGE() - model.getNB_LVL_IN_A_PAGE() + 1);
		makeGridLevels(mainPanel, lvlMin);		
		
		JButton[] button = makeButtonForTheBoardGamePage(lvlMin);
		mainPanel.add(button[0]);
		if(model.getNbPages() != 1) mainPanel.add(button[1]);
		
		this.makeABeautifulBackground("/medias/view/backgroundSimple2.png", mainPanel);
		return mainPanel;
	}
	
	/**
	 * Creation du JPanel en haut de l'ecran du plateau
	 * @param up
	 */
	private void makeButtonUpPanel(JPanel up) {
		Player p = ipv.getPlayer();
		
		JPanel content = new JPanel(null);
		content.setBounds(10, 20, 590, 45);
		content.setBackground(new Color(0,0,0,0));
		up.add(content);
		
		for(int i=0; i<2; i++) {
			JLabel icon;
			JLabel text;
			if(i == 0) {
				icon = this.makeABeautifulLabel(null, "/medias/view/life.png");
				icon.setBounds(0, 0, 40, 40);
				text = new JLabel(Integer.toString(((Human) p).getNbLife()), SwingConstants.RIGHT);
				text.setBounds(10, 10, 120, 25);
			} else {
				icon = this.makeABeautifulLabel(null, "/medias/view/diamond.png");
				icon.setBounds(140, 0, 40, 40);
				text = new JLabel(Integer.toString(p.getNbPiece()) + "   ", SwingConstants.RIGHT);
				text.setBounds(150, 10, 120, 25);
			}
			content.add(icon);
			text.setBackground(Color.white);
			text.setFont(new Font("SansSerif", Font.BOLD, 12));
			text.setOpaque(true);
			content.add(text);
		}
		
		JButton options = makeABeautifulButton(null, "/medias/view/button-options.png");
		options.setBounds(520, 0, 40, 40);
		content.add(options);
		options.addActionListener ((event) -> ipv.changePage("options1"));
	}
	
	/**
	 * Creation des boutons pour atteindre les niveaux (boardGame)
	 * @param center (le panneau qui va le contenir)
	 * @param lvlMin (le niveau minimal qui sera affiche)
	 */
	private void makeGridLevels(JPanel center, int lvlMin) {
		JLabel titleLabel = this.makeABeautifulLabel("Monde " + model.getNbPages(), "/medias/view/banner-label.png");
		titleLabel.setBounds(140, 100, 320, 61);
		center.add(titleLabel);
		
		GridLayout grid = new GridLayout(5,4);
		grid.setHgap(40);
		grid.setVgap(40);
		JPanel panelBut = new JPanel(grid);
		panelBut.setBackground(new Color(0,0,0,0));
		panelBut.setBounds(100, 200, 400, 400);
		center.add(panelBut);
		
		int lvlMax = lvlMin + model.getNB_LVL_IN_A_PAGE();
		
		for(int i=lvlMin; i<lvlMax; i++) {
			JButton n = new JButton(Integer.toString(i));
			n.setBackground(new Color(0,0,0,0));
			n.setBorder(BorderFactory.createLineBorder(new Color(247,101,99), 2));
			n.setFont(new Font("SansSerif", Font.TRUETYPE_FONT, 15));
			n.setForeground(new Color(81, 123, 113));
			n.setEnabled(false);
			if(i <= model.getBoardGame().getTotalNbLvls()) {
				Level level = model.getBoardGame().getLevelTab()[i];
				if(level.isAvailable() || i-1 <= ipv.getPlayer().getLastLevelDone()) {
					n.setEnabled(true);
					n.setBackground(Color.white);
				}
				n.addActionListener((event) -> {
					model.changeCurrentLevel(level.getLvl()); 
					mainPanelBold.add("firstPageL" + level.getLvl(), toFirstPageOfLevel(level));
					ipv.changePage("firstPageL" + level.getLvl());
				});
			}
			panelBut.add(n);
		}
	}
	
	/**
	 * Creation des boutons "Page Suivante" et "Page Precedente" de la page BoardGame
	 * @param niveauMin
	 * @return
	 */
	private JButton[] makeButtonForTheBoardGamePage(int niveauMin) {
		JButton[] button = new JButton[2];
		for(int i=0; i<2; i++) {
			if(i == 0) {
				button[i] = new JButton(">");
				button[i].setBounds(550, 375, 50, 50);
				button[i].addActionListener((event) -> {
					if(model.getNbPages() != model.getTotalNbPages()) {
						model.setNbPages(model.getNbPages() + 1);
						this.mainPanelBold.add("boardGame", toTheBoardGame());
						ipv.changePage("boardGame");
					} else {
						ipv.changePage("maj");
					}
				});
			} else {
				button[i] = new JButton("<");
				button[i].setBounds(0, 375, 50, 50);
				button[i].addActionListener((event) -> {
					model.setNbPages(model.getNbPages() - 1);
					this.mainPanelBold.add("boardGame", toTheBoardGame());
					ipv.changePage("boardGame");
				});
			}
			button[i].setBackground(new Color(0,0,0,0));
			button[i].setBorderPainted(false);
			button[i].setFont(new Font("SansSerif", Font.BOLD, 15));
			button[i].setForeground(new Color(81, 123, 113));
		}
		return button;
	}
	
	
	// PREMIERE PAGE DE LES NIVEAUX (PRESENTATION DU NIVEAU)
	
	/**
	 * Creation de la premiere page du niveau
	 * Lorsqu'on clique sur le bouton du niveau, cette page apparait.
	 * Le joueur peut choisir d'y jouer ou pas
	 * @param level
	 */
	private JPanel toFirstPageOfLevel(Level level) {
		JPanel mainPanel = new JPanel(null);
		
		JButton play = makeABeautifulButton("Jouer", "/medias/view/template-button.png");
		play.setForeground(Color.white);
		play.setFont(new Font("SansSerif", Font.BOLD, 25));
		play.setBounds(210, 630, 180, 60);
		mainPanel.add(play);
		play.addActionListener ((event) -> {
			Player p = ipv.getPlayer();
			if((p instanceof Human && ((Human) p).getNbLife() > 0)) {
				model.changeCurrentLevel(level.getLvl());
				if(model.getEvents().size() != 0) {
					String[] boosters = {"bomb", "rocket", "disco-ball", "hammer"};
					for(String s : boosters) {
						if(model.getEvents().containsKey(s)) model.useBoostersAtTheBeginning(new Booster(s));
					}
					model.getEvents().clear();
				}
				mainPanelBold.add("secondPageL" + model.getCurrentLevel().getLvl(), toLevelPage());
				ipv.changePage("secondPageL" + model.getCurrentLevel().getLvl());
			} else {
				this.mainPanelBold.add("noLife", playerNoMoreLife());
				ipv.changePage("noLife");
			}
		});
		
		JPanel rectangle = makeTheRectangleWhite("Niveau " + level.getLvl(), "boardGame");
		mainPanel.add(rectangle);
		
		JPanel panelCentral = makeCenterPanelFirstPageLevel(level);
		rectangle.add(panelCentral);
		
		this.makeABeautifulBackground("/medias/view/backgroundSimple2.png", mainPanel);
		return mainPanel;
	}
	
	/**
	 * Retourne le contenu de la premiere page des niveaux
	 * @param level
	 * @return
	 */
	private JPanel makeCenterPanelFirstPageLevel(Level level) {
		JPanel cPanel = new JPanel(new GridLayout(2,1));
		cPanel.setBounds(50, 130, 400, 350);
		cPanel.setBackground(Color.white);
		
		JPanel goals = makeGoals(level);
		cPanel.add(goals);
		
		JPanel panneauBooster = makeGridBoostersFirstPageLevel();	
		cPanel.add(panneauBooster);
	
		return cPanel;
	}

	/**
	 * Creation du contenu des objectifs de la premiere page des niveaux
	 * @param level
	 * @return
	 */
	private JPanel makeGoals(Level level) {
		JPanel goals = new JPanel(null);
		goals.setBackground(Color.white);
		
		this.pageParamSubTitle(goals, "Objectifs", 0);
		
		GridLayout grid = new GridLayout(2,1);
		grid.setVgap(10);
		JPanel goalsPanel = new JPanel(grid);
		goalsPanel.setBackground(Color.white);
		goalsPanel.setBounds(0, 40, 400, 135);
		goals.add(goalsPanel);
		
		String[] txtObj = {"Sauve " + level.getGoals().getAnimalToSave() + " animaux.", "Obtiens " + level.getGoals().getPoints() + " points."};
		for(int i=0; i<txtObj.length; i++) {
			JLabel txt = new JLabel(txtObj[i], 0);
			txt.setFont(new Font("SansSerif", Font.PLAIN, 15));
			txt.setOpaque(true);
			txt.setBackground(new Color(250, 248, 225));
			goalsPanel.add(txt);
		}
		return goals;
	}
	
	/**
	 * Creation du contenu des boosters de la premiere page des niveaux
	 * @param
	 * @return
	 */
	private JPanel makeGridBoostersFirstPageLevel() {
		JPanel boosters = new JPanel(null);
		boosters.setBackground(Color.white);
		
		this.pageParamSubTitle(boosters, "Boosters", 30);
		
		JPanel bPanel = makeGridBoosters(Color.white, false);
		bPanel.setBounds(0, 70, 400, 100);
		boosters.add(bPanel);
		
		return boosters;
	}
	
	
	// DEUXIEME PAGE DES NIVEAUX
	
	/**
	 * Creation de la deuxieme page de niveau
	 * Lorsqu'on clique sur "Jouer" de la premiere page, alors celle-ci apparait.
	 * @return
	 */
	private JPanel toLevelPage() {
		JPanel mainPanel = new JPanel(null);
		
		JPanel upPanel = makeGridUpLevel(model.getCurrentLevel());
		mainPanel.add(upPanel);
		
		JPanel game = gridLevel(model.getCurrentLevel());
		mainPanel.add(game);
		int max = (model.getCurrentLevel().haveTheFirstLineNoNull() + 6 > model.getCurrentLevel().getGamePiece().length ? model.getCurrentLevel().getGamePiece().length : model.getCurrentLevel().haveTheFirstLineNoNull() + 6);
		JLabel linesH = new JLabel("Nombre de lignes cachees : " + Integer.toString(model.getCurrentLevel().getGamePiece().length-max), 0);
		linesH.setFont(new Font("SansSerif", Font.BOLD, 15));
		linesH.setForeground(Color.white);
		linesH.setBounds(150, 530, 300, 40);
		mainPanel.add(linesH);
		
		JPanel booster = makeGridBoosters(new Color(0,0,0,0), true);
		booster.setBounds(200, 735, 200, 40);
		mainPanel.add(booster);
		
		JButton out = this.makeABeautifulButton(null, "/medias/view/button-back.png");
		out.setBounds(25, 735, 56, 40);
		out.addActionListener((event) -> {
			this.mainPanelBold.add("giveUp", playerGiveUp());
			ipv.changePage("giveUp");
		});
		mainPanel.add(out);
		
		this.makeABeautifulBackground("/medias/view/backgroundSimple.png", mainPanel);
		
		return mainPanel;
	}
	
	/**
	 * Creation de la grille de niveau
	 * @param level
	 * @return game
	 */
	private JPanel gridLevel(Level level) {
		if(model.getIdAndColor().size() == 0) model.addIdColor();
		Map<Integer, String> idAndColor = model.getIdAndColor();
		
		GamePiece[][] g = model.getCurrentLevel().getGamePiece();
		JPanel game = new JPanel(new GridLayout(6,7));
		game.setBorder(BorderFactory.createLineBorder(new Color(81,123,113), 5));
		game.setBounds(72, 150, 455, 390);
		game.setBackground(new Color(0,0,0,100));

		int max = (level.haveTheFirstLineNoNull() + 6 >= level.getGamePiece().length ? level.getGamePiece().length : level.haveTheFirstLineNoNull() + 6);
		for (int i=level.haveTheFirstLineNoNull(); i<max; i++) {
			for(int j=0; j<g[i].length; j++) {
				JButton buttonBlocAnimal;
				if(g[i][j] != null) {
					if(g[i][j] instanceof Bloc) {
						String c = idAndColor.get(((Bloc) g[i][j]).getIdColor());
						buttonBlocAnimal = makeABeautifulButton(null, "/medias/objects/blocs/bloc-" + c + ".png");
					} else if(g[i][j] instanceof Wall) {
						buttonBlocAnimal = makeABeautifulButton(null, "/medias/objects/blocs/wall.jpg");
						buttonBlocAnimal.setEnabled(false);
					} else if(g[i][j] instanceof Booster) {
						Booster b = (Booster) g[i][j];
						buttonBlocAnimal = makeABeautifulButton("", "/medias/objects/boosters/"+ b.getType() +".png");
					} else {
						buttonBlocAnimal = makeABeautifulButton(null, Animal.imageAnimal.get(((Animal) g[i][j]).getI()));
						buttonBlocAnimal.setEnabled(false);
					}
				} else {
					buttonBlocAnimal = new JButton("NULL");
					buttonBlocAnimal.setEnabled(false);
					buttonBlocAnimal.setVisible(false);
				}
				
				int x = i;
				int y = j;
				
				buttonBlocAnimal.addActionListener((event) -> {
					if(g[x][y] instanceof Bloc) {
						if(model.getEvents().size() != 0) {
							String[] booster = {"bomb", "hammer", "rocket", "disco-ball"};
							for(String b : booster) {
								if(model.getEvents().get(b) != null && model.getEvents().get(b)) {
									level.removeBlocForBooster(x, y, new Booster(b));
									model.getEvents().remove(b);
								}
							} 
						} else level.removeBloc(x, y);
					} else if(g[x][y] instanceof Booster) level.removeBlocForBooster(x, y, (Booster) g[x][y]);
						
					this.mainPanelBold.add("secondPageL" + level.getLvl(), this.toLevelPage());
					ipv.changePage("secondPageL" + level.getLvl());
									
					
					if(level.levelDone()) {
						model.playerWin(ipv.getPlayer());
						this.mainPanelBold.add("thirdPageL" + level.getLvl(), this.makeJPanelPlayerWin(true));
						ipv.changePage("thirdPageL" + level.getLvl());
					} else if (level.lose()) {
						this.mainPanelBold.add("thirdPageL" + level.getLvl(), this.makeJPanelPlayerWin(false));
						this.mainPanelBold.add("noMouv" + level.getLvl(), noMoreMovement());
						ipv.changePage("noMouv" + level.getLvl());
					}
				});
				game.add(buttonBlocAnimal);
			}
		}
		return game;
	}
	
	/**
	 * Creation des objectifs de la deuxieme page du niveau
	 * @param level
	 * @return upPanel
	 */
	private JPanel makeGridUpLevel(Level level) {
		JPanel upPanel = new JPanel(new BorderLayout());
		upPanel.setBounds(50, 0, 500, 120);
		upPanel.setBackground(new Color(0,0,0,0));
		
		JPanel goals = makePanelGoalsAndPoints(level, "OBJECTIFS", 0);
		upPanel.add(goals, BorderLayout.WEST);
		
		JPanel m = makeCenterPanel(level);
		upPanel.add(m, BorderLayout.CENTER);
		
		JPanel points = makePanelGoalsAndPoints(level, "POINTS", 20);
		upPanel.add(points, BorderLayout.EAST);
		
		return upPanel;
	}
	
	/**
	 * Creation du panneau des objectifs et points
	 * @param level
	 * @param t
	 * @param diff
	 * @return p
	 */
	private JPanel makePanelGoalsAndPoints(Level level, String t, int diff) {
		JPanel p = new JPanel(null);
		p.setPreferredSize(new Dimension(140, 100));
		p.setBackground(new Color(0,0,0,0));
				
		JLabel title = new JLabel(t, 0);
		title.setForeground(Color.white);
		title.setFont(new Font("SansSerif", Font.PLAIN, 10));
		title.setBackground(new Color(49, 109, 120));
		title.setBounds(25 + diff, 10, 70, 30);
		title.setOpaque(true);
		p.add(title);
		
		JPanel frame = new JPanel(null);
		frame.setBackground(Color.white);
		frame.setBounds(0 + diff, 20, 120, 80);
		frame.setBorder(BorderFactory.createLineBorder(new Color(49, 109, 120), 2));
		p.add(frame);
		
		if(diff != 0) {
			JProgressBar bar = new JProgressBar(0, level.getGoals().getPoints());
			frame.add(bar, BorderLayout.CENTER);
			bar.setBounds(10, 10, 100, 60);
			bar.setStringPainted(true);
			bar.setFont(new Font("SansSerif", Font.PLAIN, 10));
			bar.setValue(level.getGoals().getPointsObtained());
			frame.add(bar);
		} else {
			JLabel image = this.makeABeautifulLabel(null,"/medias/objects/animals/stefaon.png");
			image.setBounds(15, 15, 55, 55);
			JLabel txt = new JLabel(Integer.toString(level.getGoals().getAnimalToSave()-level.getGoals().getNbAnimalSave()));
			txt.setBounds(80, 15, 55,  55);
			txt.setFont(new Font("SansSerif", Font.PLAIN, 10));
			frame.add(image);
			frame.add(txt);
		}

		return p;
	}

	/**
	 * Creation du paneau au milieu de la page
	 * @param level
	 * @return m
	 */
	private JPanel makeCenterPanel(Level level) {
		JPanel m = new JPanel(new BorderLayout());
		m.setBackground(new Color(42, 109, 120));
		
		JLabel mTitle = new JLabel("MOUVEMENTS", 0);
		mTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		mTitle.setFont(new Font("SansSerif", Font.PLAIN, 15));
		mTitle.setForeground(Color.white);
		m.add(mTitle, BorderLayout.NORTH);
		
		int movements = level.getGoals().getNbMouv();
		JLabel mTxt = new JLabel(Integer.toString(movements), 0);
		mTxt.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		mTxt.setFont(new Font("SansSerif", Font.PLAIN, 50));
		mTxt.setForeground(Color.white);
		m.add(mTxt, BorderLayout.CENTER);
		
		return m;
	}
	
	
	// PANNEAU QUAND LE JOUEUR A GAGNE OU A PERDU
	
	/**
	 * Creation du JPanel quand le joueur gagne/perd un niveau en fonction de ses arguments
	 * @param win (true = il a gagne, false = il a perdu
	 * @return
	 */
	private JPanel makeJPanelPlayerWin(boolean win) {
		JPanel mainPanel = new JPanel(null);
		String path = "banner-label-lose";
		if(win) path = "banner-label-win";
		
		JLabel bannerWin = this.makeABeautifulLabel(null,"/medias/view/" + path +".png");
		if(win) bannerWin.setBounds(100, 100, 400, 108);
		else bannerWin.setBounds(100, 80, 400, 136);
		mainPanel.add(bannerWin);
		
		JPanel rectangle = new JPanel(null);
		rectangle.setBounds(75, 250, 450, 350);
		rectangle.setBackground(Color.white);
		rectangle.setBorder(BorderFactory.createLineBorder(new Color(68,118,165), 6));
		mainPanel.add(rectangle);
		
		JPanel content = new JPanel(null);
		content.setBounds(50, 50, 350, 250);
		content.setBackground(new Color(0,0,0,0));
		rectangle.add(content);
		makeContentPaneWhenPlayerWin(content, win);
		
		JButton button;
		if(win) button = this.makeABeautifulButton("Continuer", "/medias/view/template-button.png");
		else button = this.makeABeautifulButton("Recommencer", "/medias/view/template-button.png");
		button.setBounds(210, 650, 180, 60);
		button.setForeground(Color.white);
		mainPanel.add(button);
		button.addActionListener((event) -> {
			model.getIdAndColor().clear();
			if(win) {
				if(model.checkIfBoosterUnLock(ipv.getPlayer()) != null) {
					String[] boosters = {"rocket", "hammer", "disco-ball", "bomb"};
					for(String b : boosters) {
						if(b.equals(model.checkIfBoosterUnLock(ipv.getPlayer()))) {
							model.serializePlayer(ipv.getPlayer());
							mainPanelBold.add("unlockBoosters", makeBoosterPage(b));
							ipv.changePage("unlockBoosters");
						}
					}
				} else {
					if(model.getCurrentLevel().getLvl() < model.getBoardGame().getTotalNbLvls()) {
						Level l = model.getBoardGame().getLevelTab()[model.getCurrentLevel().getLvl()+1];
						model.getBoardGame().getLevelTab()[model.getCurrentLevel().getLvl()+1].setAvailable(true);
						model.changeCurrentLevel(l.getLvl());
						mainPanelBold.add("firstPageL" + l.getLvl(), toFirstPageOfLevel(l));
						ipv.changePage("firstPageL" + l.getLvl());
					} else ipv.changePage("maj");
				}
			} else {
				Level again = model.getBoardGame().getLevelTab()[model.getCurrentLevel().getLvl()];
				model.changeCurrentLevel(again.getLvl());
				mainPanelBold.add("firstPageL" + again.getLvl(), toFirstPageOfLevel(again));				
				ipv.changePage("firstPageL" + again.getLvl());
			}
		});
		
		JPanel filter = new JPanel();
		filter.setBackground(new Color(0,0,0,100));
		filter.setBounds(0, 0, 600, 800);
		mainPanel.add(filter);
		
		makeABeautifulBackground("/medias/view/backgroundSimple.png", mainPanel);
		return mainPanel;
	}
	
	/**
	 * Contenu du JPanel quand le joueur gagne/perd un niveau en fonction de ses arguments
	 * @param content
	 * @param win
	 */
	private void makeContentPaneWhenPlayerWin(JPanel content, boolean win) {
		this.pageParamSubTitle(content, "0bjectifs", 0);
		
		Player p = ipv.getPlayer();
		Level l = model.getCurrentLevel();
		Goals g = l.getGoals();
		
		if(!win) model.playerLose(p);
		
		String scorePoints = Integer.toString(g.getPointsObtained());
		int max = (p.getPointsLvl(l.getLvl()) >= g.getPointsObtained() ? p.getPointsLvl(l.getLvl()) : g.getPointsObtained());
		String scoreMax = "Meilleur score : " + max + " points";
		String scoreAnimals = Integer.toString(g.getAnimalToSave());
		
		String[] text1 = {"", "", "Animaux sauves :", "Points Obtenus :"};
		simplePageContentTrue(content, text1, 170, 60, 0);
		String[] text2 = {"", "", scoreAnimals, scorePoints};
		simplePageContentTrue(content, text2, 170, 60, 180);
		
		JLabel t = new JLabel(scoreMax, 0);
		if(win) t.setBounds(0, 150, 350, 40);
		else t.setBounds(0, 160, 350, 60);
		t.setOpaque(true);
		t.setFont(new Font("SansSerif", Font.BOLD, 15));
		t.setBackground(new Color(238,229,203));
		content.add(t);
		
		if(win) {
			String rec = (l.getLvl() <= p.getLastLevelDone() ? "1000": "10");
			JLabel t3 = new JLabel("Recompense :                  " + rec);
			t3.setBounds(0, 220, 350, 30);
			t3.setOpaque(true);
			t3.setBackground(new Color(0,0,0,0));
			t3.setFont(new Font("SansSerif", Font.PLAIN, 12));
			content.add(t3);
			
			JLabel icon = this.makeABeautifulLabel(null,"/medias/view/diamond.png");
			icon.setBounds(120, 220, 35, 35);
			content.add(icon);
		}
	}
	
	
	// PANNEAU POUR L'ABANDON DE NIVEAU
	
	/**
	 * Creation du panneau d'abandon (le joueur abandonne le niveau)
	 * @return
	 */
	private JPanel playerGiveUp() {
		JPanel mainPanel = new JPanel(null);
		
		JPanel rectangle = this.makeTheRectangleWhite("Confirmation", "");
		mainPanel.add(rectangle);
		
		JPanel content = new JPanel(null);
		content.setBounds(50, 130, 400, 350);
		content.setBackground(Color.white);
		rectangle.add(content);
		
		this.pageParamSubTitle(content, "Vous voulez vraiment abandonner ?", 0);
		
		JButton playAgain = this.makeABeautifulButton("Continuer", "/medias/view/template-button-simple.png");
		playAgain.setBounds(25, 70, 350, 100);
		content.add(playAgain);
		playAgain.addActionListener((event) -> {
			ipv.changePage("secondPageL" + model.getCurrentLevel().getLvl());
		});
		
		JButton giveUp = this.makeABeautifulButton("Abandonner", "/medias/view/template-button-simple.png");
		giveUp.setBounds(25, 200, 350, 100);
		content.add(giveUp);
		giveUp.addActionListener((event) -> {
			model.setCurrentLevel(null);
			model.getIdAndColor().clear();
			this.mainPanelBold.add("boardGame", this.toTheBoardGame());
			ipv.changePage("boardGame");
		});
		
		this.makeABeautifulBackground("/medias/view/backgroundSimple2.png", mainPanel);
		return mainPanel;
	}
	
	
	// PANNEAU LORSQUE LE JOUEUR N'A PLUS DE VIES
	
	/**
	 * Creation du panneau lorsque le joueur n'a plus de vies
	 * @return
	 */
	private JPanel playerNoMoreLife() {
		if(model.diffLocalDateTime() <= 0) ((Human) ipv.getPlayer()).relive();
			
		JPanel mainPanel = new JPanel(null);
			
		JButton pay = makeABeautifulButton("Payer", "/medias/view/template-button.png");
		pay.setForeground(Color.white);
		pay.setFont(new Font("SansSerif", Font.BOLD, 25));
		pay.setBounds(210, 630, 180, 60);
		mainPanel.add(pay);
		pay.addActionListener ((event) -> {
			((Human) ipv.getPlayer()).relive();
			ipv.getPlayer().addNbPiece(-1200);
			this.mainPanelBold.add("boardGame", toTheBoardGame());
			ipv.changePage("boardGame");
			model.serializePlayer(ipv.getPlayer());
		});
		
		JPanel rectangle = this.makeTheRectangleWhite("Vous n'avez plus de vies !", "boardGame");
		mainPanel.add(rectangle);
		
		JPanel content = new JPanel(null);
		content.setBounds(50, 130, 400, 300);
		content.setBackground(Color.white);
		rectangle.add(content);
		
		this.pageParamSubTitle(content, "A court de vies !", 0);
		
		JPanel iconText = new JPanel(new GridLayout(1,4));
		iconText.setBounds(50, 40, 300, 100);
		iconText.setBackground(Color.white);
		content.add(iconText);
		
		JLabel icon = this.makeABeautifulLabel(null, "/medias/view/life.png");
		iconText.add(icon);
		
		JLabel txt1 = this.makeABeautifulLabel("0", "");
		txt1.setForeground(Color.black);
		iconText.add(txt1);
		
		JLabel icon2 = this.makeABeautifulLabel(null, "/medias/view/diamond.png");
		iconText.add(icon2);
		
		JLabel txt2 = this.makeABeautifulLabel(Integer.toString(ipv.getPlayer().getNbPiece()), "");
		txt2.setForeground(Color.black);
		iconText.add(txt2);
				
		JLabel t = new JLabel("Toutes vos vies seront revenus dans : " + model.diffLocalDateTime() + " min", 0);
		t.setBounds(0, 150, 400, 60);
		t.setOpaque(true);
		t.setFont(new Font("SansSerif", Font.PLAIN, 17));
		t.setBackground(new Color(238,229,203));
		content.add(t);
		
		JLabel t2 = new JLabel("<html><p align=center>Vous pouvez payer 1200 pieces pour<br>recuperer vos 5 vies.</p><html>", 0);
		t2.setBounds(0, 220, 400, 80);
		t2.setOpaque(true);
		t2.setFont(new Font("SansSerif", Font.PLAIN, 17));
		t2.setBackground(new Color(238,229,203));
		content.add(t2);
			
		this.makeABeautifulBackground("/medias/view/backgroundSimple2.png", mainPanel);
		return mainPanel;
	}
	
	
	// PANNEAU LORSQUE LE JOUEUR N'A PLUS DE MOUVEMENTS POSSIBLES
	
	/**
	 * Creation du panneau lorsque le joueur n'a plus de mouvements possibles !
	 * @return
	 */
	private JPanel noMoreMovement() {
		JPanel mainPanel = new JPanel(null);
		
		JButton pay = makeABeautifulButton("Payer", "/medias/view/template-button.png");
		pay.setForeground(Color.white);
		pay.setFont(new Font("SansSerif", Font.BOLD, 25));
		pay.setBounds(210, 630, 180, 60);
		mainPanel.add(pay);
		pay.addActionListener ((event) -> {
			ipv.getPlayer().addNbPiece(-800);
			model.getCurrentLevel().getGoals().setNbMouv(5 + model.getCurrentLevel().getGoals().getNbMouv());
			this.mainPanelBold.add("secondPageL" + model.getCurrentLevel().getLvl(), toLevelPage());
			ipv.changePage("secondPageL" + model.getCurrentLevel().getLvl());
			model.serializePlayer(ipv.getPlayer());
		});
			
		JPanel rectangle = this.makeTheRectangleWhite("Au secours !", "thirdPageL" + model.getCurrentLevel().getLvl());
		mainPanel.add(rectangle);

		JPanel content = new JPanel(null);
		content.setBounds(50, 150, 400, 300);
		content.setBackground(Color.white);
		rectangle.add(content);

		this.pageParamSubTitle(content, "Il n'y a plus de mouvements possibles !", 0);
		
		JPanel iconText = new JPanel(new GridLayout(1,2));
		iconText.setBounds(100, 40, 200, 150);
		iconText.setBackground(Color.white);
		content.add(iconText);
							
		JLabel icon = this.makeABeautifulLabel(null, "/medias/view/diamond.png");
		iconText.add(icon);
				
		JLabel txt = this.makeABeautifulLabel(Integer.toString(ipv.getPlayer().getNbPiece()), "");
		txt.setForeground(Color.black);
		iconText.add(txt);
				
		JLabel t2 = new JLabel("<html><p align=center>Vous pouvez payer<br>800 pieces pour recuperer 5<br>mouvements supplementaires. Pensez a vos<br>boosters !</p><html>", 0);
		t2.setBounds(0, 200, 400, 100);
		t2.setOpaque(true);
		t2.setFont(new Font("SansSerif", Font.PLAIN, 17));
		t2.setBackground(new Color(238,229,203));
		content.add(t2);
			
		this.makeABeautifulBackground("/medias/view/backgroundSimple2.png", mainPanel);
		return mainPanel;
	}

	
	// PANNEAU LORSQUE LE JOUEUR DEBLOQUE DES BOOSTERS

	/**
	 * Creation du panneau lorsque le joueur debloque un booster
	 * @param booster
	 * @return
	 */
	private JPanel makeBoosterPage(String booster) {
		JPanel mainPanel = new JPanel(null);
		
		JLabel bannerWin = this.makeABeautifulLabel(null,"/medias/view/banner-label-win.png");
		bannerWin.setBounds(100, 100, 400, 108);
		mainPanel.add(bannerWin);
		
		JPanel rectangle = new JPanel(null);
		rectangle.setBounds(75, 250, 450, 350);
		rectangle.setBackground(Color.white);
		rectangle.setBorder(BorderFactory.createLineBorder(new Color(88,188,169), 6));
		mainPanel.add(rectangle);
		
		JPanel content = new JPanel(null);
		content.setBounds(50, 50, 350, 250);
		content.setBackground(new Color(0,0,0,0));
		rectangle.add(content);
		makeContentBoosters(content, new Booster(booster));
		
		JButton button;
		button = this.makeABeautifulButton("Essayer", "/medias/view/template-button.png");
		button.setBounds(210, 650, 180, 60);
		button.setForeground(Color.white);
		mainPanel.add(button);
		button.addActionListener((event) -> {
			Level l = model.getBoardGame().getLevelTab()[model.getCurrentLevel().getLvl()+1];
			model.getBoardGame().getLevelTab()[model.getCurrentLevel().getLvl()+1].setAvailable(true);
			model.changeCurrentLevel(l.getLvl());
			mainPanelBold.add("secondPageL" + l.getLvl(), this.toLevelPage());
			ipv.changePage("secondPageL" + l.getLvl());
		});
		
		makeABeautifulBackground("/medias/view/backgroundSimple2.png", mainPanel);
		return mainPanel;
	}
	
	/**
	 * Contenu de la page lorsque le jour debloque un booster
	 * @param content
	 * @param b
	 */
	private void makeContentBoosters(JPanel content, Booster b) {
		String[] parts = makeTheText(0);
		String title = b.convertEnglishFrench(b.getType());
		
		pageParamSubTitle(content, "Nouveau Booster debloque !", 0);
		
		JPanel iconText = new JPanel(new GridLayout(1,2));
		iconText.setBackground(Color.white);
		iconText.setBounds(25, 90, 300, 70);
		content.add(iconText);
		
		JLabel icon = this.makeABeautifulLabel(null, "/medias/objects/boosters/" + b.getType() + ".png");
		iconText.add(icon);
		
		JLabel text = this.makeABeautifulLabel(title, "");
		text.setForeground(Color.black);
		text.setFont(new Font("SansSerif", Font.BOLD, 18));
		iconText.add(text);
		
		int indice = 0;
		for(int i=0; i<parts.length; i++) {
			if(parts[i].equals(title)) indice = i;
		}
		
		JLabel explain = this.makeABeautifulLabel("<html><p align=center>" + parts[indice+1] + "</p></html>", "");
		explain.setForeground(Color.black);
		explain.setFont(new Font("SansSerif", Font.PLAIN, 15));
		explain.setBackground(new Color(238,229,203));
		explain.setBounds(0, 180, 350, 50);
		content.add(explain);
	}
	
}
