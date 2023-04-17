package dataBase;

import java.util.Random;

import objects.Level;

/**
 * Cette classe represente notre base de donnees.
 * On ajoute nos niveaux dans la fonction <b>getLevel(int lvl)</b> et le jeu est capable d'enregistrer les niveaux.
 */
class KeepLevel {
	
	private Random rd = new Random();
	
	/**
	 * Calcule le nombre de points necessaires pour un niveau souhaite
	 * @param s (le tableau des blocs)
	 * @return (le nombre de points qu'il faut pour gagner le niveau)
	 */
	private int pointsN(int[][] s) {
		int bloc = s.length*s[0].length;
		for(int i=0; i<s.length; i++) {
			for(int j=0; j<s[i].length; j++) {
				if(s[i][j] == 1) bloc--;
			}
		}
		return bloc*30;
	}
	
	/**
	 * Sait si le tableau de blocs donne en argument est rempli ou pas
	 * @param s (le tableau des blocs)
	 * @return (true si le tableau est plein, false sinon)
	 */
	private boolean gameIsFull(int[][] s) {
		for(int i=0; i<s.length; i++) {
			for(int j=0; j<s[i].length; j++) {
				if(s[i][j] == 0) return false;
			}
		}
		return true;
	}
	
	/**
	 * Remplit le tableau donne en argument avec le nombre de couleurs donne
	 * @param s (le tableau de blocs)
	 * @param nbColor (le nombre de couleur souhaite)
	 */
	private void setGameWithRandom(int[][] s,int nbColor) {
		int x = rd.nextInt(s.length);
		int y = rd.nextInt(s[x].length);
		int[] number = {2,3,4,5,6};
		while(!gameIsFull(s)) {
			int tmp = s[x][y];
			if(tmp == 0) {
				int c = rd.nextInt(nbColor);
				s[x][y] = number[c];
			} else {
				x = rd.nextInt(s.length);
				y = rd.nextInt(s[x].length);
			}
		}
	}
	
	/**
	 * Renvoie le niveau du numero donne en parametres
	 * @param lvl (le numero du niveau)
	 * @return (le niveau)
	 */
	Level getLevel(int lvl) {
		switch(lvl) {
			case 1 :
				int[][] s1 = {{7,1,1,7,1,1,7},{5,5,5,3,6,6,6},{5,5,5,3,6,6,6},{2,2,2,3,2,2,2},{2,2,2,3,2,2,2},{2,2,2,3,2,2,2}};
				return new Level(1,3,pointsN(s1),5,s1);
			case 2 :
				int[][] s2 = {{1,7,1,1,1,7,1},{2,2,4,4,4,2,2},{2,2,3,3,3,2,2},{4,4,3,3,3,5,5},{4,4,3,3,3,4,4},{2,2,6,6,6,4,4}};
				return new Level(2,2,pointsN(s2),10,s2);
			case 3 :
				int[][] s3 = {{1,2,2,3,2,2,1},{7,2,2,3,2,2,7},{2,4,4,3,4,4,2},{2,4,4,3,4,4,2},{4,2,2,3,2,2,4},{4,2,2,3,2,2,4}};
				return new Level(3,2,pointsN(s3),10,s3);
			case 4 :
				int[][] s4 = {{1,1,7,0,7,0,7},{1,1,5,3,4,3,4},{1,1,5,3,4,3,4},{1,3,4,5,3,4,5},{1,3,4,5,3,4,5},{3,4,5,3,4,5,3},{3,4,5,3,4,5,3}};
				return new Level(4,3,pointsN(s4),10,s4);
			case 5 :
				int[][] s5 = {{1,1,1,1,7,7,7},{1,1,1,2,4,5,6},{1,1,3,2,4,5,6},{1,5,3,2,4,5,1},{6,5,3,2,4,1,1},{6,5,3,2,1,1,1}};
				return new Level(5,3,pointsN(s5),10,s5);
			case 6 : // INITIATION A LA FUSEE
				int[][] s6 = {{1,7,7,1,1,1,1},{1,5,6,1,1,1,1},{6,2,6,6,6,6,6},{6,6,6,6,6,6,6},{6,6,6,6,6,6,6},{6,6,6,6,6,6,6}};
				return new Level(6,2,pointsN(s6),1,s6);
			case 7 :
				int[][] s7 = {{1,1,1,7,1,1,1},{1,1,0,0,0,1,1},{1,0,0,0,0,0,1},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};
				setGameWithRandom(s7,4);
				return new Level(7,1,pointsN(s7),20,s7);
			case 8 :
				int[][] s8 = {{1,1,7,1,7,1,1},{1,7,0,0,0,7,1},{0,0,0,0,0,0,0},{0,0,1,0,1,0,0},{0,0,0,0,0,0,0},{0,1,0,1,0,1,0},{0,1,0,1,0,1,0},{0,1,0,1,0,1,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};
				setGameWithRandom(s8,2);
				return new Level(8,4,pointsN(s8),25,s8);
			case 9 :
				int[][] s9 = {{1,1,1,1,1,1,7},{1,1,1,1,1,7,7},{1,1,1,1,0,0,0},{1,1,1,0,0,0,0},{1,1,0,0,0,0,0},{1,0,0,0,0,0,1},{0,0,0,0,0,1,1},{0,0,0,0,1,1,1},{0,0,0,1,1,1,1},{0,0,0,0,1,1,1},{0,0,0,0,0,1,1},{0,0,0,0,0,0,0},{0,0,1,0,1,0,0},{0,0,1,0,1,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};
				setGameWithRandom(s9,3);
				return new Level(9,3,pointsN(s9),30,s9);
			case 10 :
				int[][] s10 = {{1,1,1,7,1,1,1},{2,2,2,2,2,2,2},{2,2,3,6,5,2,2},{2,2,4,2,4,2,2},{2,2,5,6,3,2,2},{2,2,2,2,2,2,2}};
				return new Level(10,1,pointsN(s10),1,s10);
			case 11 :
				int[][] s11 = {{2,1,1,1,1,4,1},{2,1,1,1,1,4,1},{2,4,4,3,4,4,2},{2,4,4,3,4,4,2},{4,2,2,3,2,2,4},{4,2,2,3,2,2,4},{1,4,4,3,4,4,1},{7,4,4,3,4,4,7},{2,4,4,3,4,4,2},{2,4,4,3,4,4,2},{4,2,2,3,2,2,4},{4,2,2,3,2,2,4}};
				return new Level(11,2,pointsN(s11),10,s11);
			case 12 :
				int[][] s12 = {{1,1,1,7,1,1,1},{1,1,2,3,2,1,1},{1,7,2,2,4,7,1},{1,3,4,2,3,3,1},{3,3,3,3,3,4,4},{4,2,4,2,3,2,2}};
				return new Level(12,3,pointsN(s12),10,s12);
			case 13 :
				int[][] s13 = {{0,0,1,1,1,0,7},{0,7,0,7,0,7,0},{0,1,0,1,0,1,0},{0,1,0,1,0,1,7},{0,0,0,0,0,0,0},{0,1,0,1,0,1,0},{0,1,0,1,0,1,0},{0,0,0,0,0,0,0}};
				setGameWithRandom(s13,2);
				return new Level(13,5,pointsN(s13),15,s13);
			case 14 :
				int[][] s14 = {{1,1,1,7,1,1,1},{2,2,2,2,2,2,2},{2,2,2,2,2,2,2},{2,2,2,2,2,2,2},{2,2,2,2,2,2,2},{2,2,2,3,2,2,2}};
				return new Level(14,1,pointsN(s14),1,s14);
			case 15 :
				int[][] s15 = {{0,1,1,7,1,1,0},{1,1,1,2,1,1,1},{1,7,1,3,1,7,1},{0,6,7,3,7,5,0},{5,5,6,5,5,2,6},{3,6,5,2,2,5,5},{5,2,5,5,2,2,5},{4,5,4,4,4,5,4},{4,4,5,5,5,4,1}};
				return new Level(15,5,pointsN(s15),15,s15);
			case 16 :
				int[][] s16 = {{1,6,5,4,3,2,7},{3,1,5,2,2,7,3},{3,3,1,5,7,3,2},{2,2,5,4,1,4,6},{6,6,5,3,4,1,5},{2,6,3,3,3,2,1}};
				return new Level(16,3,pointsN(s16),15,s16);
			case 17 :
				int[][] s17 = {{1,7,7,1,7,7,1},{7,2,2,7,2,2,7},{2,3,3,2,3,3,2},{4,5,4,4,4,5,4},{5,5,4,4,4,5,5},{4,4,3,5,3,4,4},{3,4,3,4,3,4,3},{3,4,5,3,5,4,3},{5,5,4,3,4,5,5},{2,2,2,4,4,5,3},{3,4,4,4,4,4,3},{4,3,3,4,3,3,4},{3,5,2,3,2,5,3},{4,2,2,4,2,2,4},{4,3,2,4,2,3,4},{3,2,4,3,4,2,3},{3,3,2,2,2,3,3},{2,4,2,3,2,4,2},{3,4,2,2,2,4,3},{4,4,2,3,2,4,4},{4,5,3,2,3,5,4},{3,3,3,3,3,3,3},{4,2,4,2,4,2,4}};
				return new Level(17,7,pointsN(s17),40,s17);
			case 18 :
				int[][] s18 = {{1,1,7,1,7,1,1},{2,2,2,3,4,4,4},{2,2,2,3,4,4,4},{2,2,2,3,4,4,4},{2,2,2,3,4,4,4},{2,2,2,3,4,4,4}};
				return new Level(18,2,pointsN(s18),1,s18);
			case 19 :
				int[][] s19 = {{1,1,1,7,1,1,1},{1,1,4,5,4,1,1},{1,1,4,5,4,1,1},{1,7,4,4,3,7,1},{1,4,5,5,4,4,1},{4,5,4,3,4,3,2},{3,5,4,3,3,3,2}};
				return new Level(19,3,pointsN(s19),6,s19);
			case 20 :
				int[][] s20 = {{1,2,1,1,2,1,1},{3,3,4,3,2,0,0},{2,2,4,3,2,0,7},{2,2,3,2,3,1,1},{4,2,4,4,4,0,0},{4,3,3,2,2,0,7},{2,2,3,4,4,1,1},{4,2,2,2,2,0,0},{4,2,4,7,2,3,3},{5,1,1,1,5,4,5},{2,5,4,5,5,5,3},{2,5,5,7,5,5,5},{2,1,1,1,2,5,3},{2,2,2,4,2,3,3}};
				return new Level(20,4,pointsN(s20),16,s20);
//			case 0 :
//				int[][] s0 = {{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0},{0,0,0,0,0,0,0}};
//				return new Level(0,0,pointsN(s0),0,s0);
			default : return null;
		}
	}
}
