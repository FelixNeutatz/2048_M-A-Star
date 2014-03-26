import game.PlayingField;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Felix Neutatz
 * 
 * CC BY 4.0
 * http://creativecommons.org/licenses/by/4.0/
 * 
 * Copyright (c) 2014 Felix Neutatz
 */

public class Game2048 {
	public static void main(String[] args) throws IOException {
		PlayingField pf = new PlayingField(4,4);
		pf.initialize(2);
		System.out.println(pf);
		
		while(pf.movesAvailable()){
			System.out.print("Enter direction (u,d,l,r): ");
	        // Read the char
			Scanner s = new Scanner(System.in);
			String str = s.nextLine();
			
			int moved = pf.moveByString(str);
	        if(moved>0) pf.insertRandCell();
	        System.out.println(pf);
		}
	}
}
