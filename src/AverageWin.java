import game.PlayingField;
import game.Round;

import java.io.IOException;

import tree.TreeNode;


public class AverageWin {
public static void main(String[] args) throws IOException, CloneNotSupportedException {
		
		
		int level=2;
		
		double avg=0;
		for(int i=0;i<100;i++){
			PlayingField pf = new PlayingField(4,4);
			pf.initialize(2);
			
			//create root node for A* Tree
			Round start = new Round(pf,1,"init");
			TreeNode<Round> root = new TreeNode<Round>(start);
			
			int numberOfRounds=0;
			while(!pf.isGameWon()){
				AStar.bruteForce(root, level); //build tree
				
				String move = AStar.getDirection(root); //get the playing field of the "right" move
				
				pf.moveByString(move);
				
				pf.insertRandCell(); //insert new tile
				
				root = new TreeNode<Round>(new Round(pf,1,"init")); //new root node for A* Tree
				
				numberOfRounds++;
			}
			System.out.println(i + ": Number of Rounds = " + numberOfRounds);
			avg += numberOfRounds;
		}
		avg /=100;
		System.out.println("Average rounds to win: " + avg);
	}
}
