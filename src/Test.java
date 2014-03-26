import game.PlayingField;

/**
 * @author Felix Neutatz
 * 
 * CC BY 4.0
 * http://creativecommons.org/licenses/by/4.0/
 * 
 * Copyright (c) 2014 Felix Neutatz
 */

public class Test {
	public static void main(String[] args) {
		PlayingField pf = new PlayingField(4,4);
		pf.initialize(2);
		System.out.println(pf);
		pf.up();
		System.out.println(pf);
		
		
		int[][] matrix = new int[][] {
		        new int[] { 4, 16, 2, 2},
		        new int[] { 4, 8, 0, 2},
		        new int[] { 8, 4, 4, 4},
		        new int[] { 16, 4, 8, 16}
		    };

		PlayingField pf1 = new PlayingField(matrix);
		System.out.println(pf1);
		pf1.up();
		System.out.println(pf1);
	}
}
