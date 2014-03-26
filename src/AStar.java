import game.PlayingField;
import game.Round;

import java.io.IOException;

import tree.TreeNode;

/**
 * @author Felix Neutatz
 * 
 * CC BY 4.0
 * http://creativecommons.org/licenses/by/4.0/
 * 
 * Copyright (c) 2014 Felix Neutatz
 */

public class AStar {
	public static void main(String[] args) throws IOException, CloneNotSupportedException {
		
		
		PlayingField pf = new PlayingField(4,4);
		pf.initialize(2);
		
		/*
		int[][] matrix = new int[][] {
		        new int[] { 0, 0, 0, 2},
		        new int[] { 2, 0, 0, 0},
		        new int[] { 0, 0, 0, 0},
		        new int[] { 0, 0, 0, 0}
		    };
		PlayingField pf = new PlayingField(matrix);*/
		System.out.println(pf);
		
		//create root node for A* Tree
		Round start = new Round(pf,1,"init");
		TreeNode<Round> root = new TreeNode<Round>(start);
		int level=4;
		
		int numberOfRounds=0;
		while(pf.movesAvailable()){
			bruteForce(root, level); //build tree
			//printTree(root,0);
			System.out.println("Number of nodes: " + countNodes(root));
			
			String move = getDirection(root); //get the playing field of the "right" move
			System.out.println("dir: " + move);
			
			pf.moveByString(move);
			System.out.println(pf);
			
			pf.insertRandCell(); //insert new tile
			System.out.println(pf);
			
			root = new TreeNode<Round>(new Round(pf,1,"init")); //new root node for A* Tree
			
			numberOfRounds++;
		}
		System.out.println("Number of Rounds: " + numberOfRounds);
	}
	
	public static String getDirection(TreeNode<Round> root){
		double best=-1;
		Round bestMove=null;
		for(TreeNode<Round> n : root.children){
			//double c = costFunctionSumAll(n,1);
			//double c = costFunctionNumberFreeCells(n,1);
			double c = costFunctionAvgScore(n, 1); //best cost function up to now
			if(c > best){
				bestMove = n.data;
				best = c;
			}
		}
		return bestMove.getMove();
	}
	
	
	/**
	 * calculate the cost for each direction
	 * 		-> costFunction: sum of all tiles of a playing field
	 * @param root
	 * @param probability
	 * @return
	 */
	public static double costFunctionSumAll(TreeNode<Round> root, double probability){
		double sum =0;
		if(root.isLeaf()){
			return probability * root.data.getProbability() * ((double)root.data.getPf().sumAll()); //just take the sum of all tiles as optimal condition
		}else{
			for(TreeNode<Round> n : root.children){
				sum += costFunctionSumAll(n,probability*root.data.getProbability());
			}
		}
		return sum;
	}
	
	/**
	 * calculate the cost for each direction
	 * 		-> costFunction: average value of an occupied tile
	 * @param root
	 * @param probability
	 * @return
	 */
	public static double costFunctionAvgScore(TreeNode<Round> root, double probability){
		double sum =0;
		if(root.isLeaf()){
			return probability * root.data.getProbability() * (((double)root.data.getPf().sumAll())/((double)root.data.getPf().getNumberOccupiedTiles()));
		}else{
			for(TreeNode<Round> n : root.children){
				sum += costFunctionAvgScore(n,probability*root.data.getProbability());
			}
		}
		return sum;
	}
	
	/**
	 * calculate the cost for each direction
	 * 		-> costFunction: number of unassigned tiles
	 * @param root
	 * @param probability
	 * @return
	 */
	public static double costFunctionNumberFreeCells(TreeNode<Round> root, double probability){
		double sum =0;
		if(root.isLeaf()){
			return probability * root.data.getProbability() * ((double)root.data.getPf().getNumberOfFreeCells());
		}else{
			for(TreeNode<Round> n : root.children){
				sum += costFunctionNumberFreeCells(n,probability*root.data.getProbability());
			}
			sum /= root.children.size();
		}
		return sum;
	}
	
	
	/**
	 * print A* Tree
	 * @param root
	 * @param start
	 * @return
	 */
	public static int printTree(TreeNode<Round> root, int start){
		int s=1;
		for(int i=0;i<start;i++) {
			System.out.print("\t");
		}
		System.out.println(root.data.getMove()+ " : " + root.data.getPf().sumAll());
		System.out.print(root.data.getPf());
		s += root.children.size();
		for(TreeNode<Round> n : root.children){
			s += printTree(n,start+1);
		}
		return s;
	}
	
	
	/**
	 * get number of elements
	 * @param root
	 * @return
	 */
	public static int countNodes(TreeNode<Round> root){
		int s=1;
		s += root.children.size();
		for(TreeNode<Round> n : root.children){
			s += countNodes(n);
		}
		return s;
	}
	
	
	/**
	 * build A* Tree with certain number of levels
	 * @param root
	 * @param level
	 * @throws CloneNotSupportedException
	 */
	public static void bruteForce(TreeNode<Round> root, int level) throws CloneNotSupportedException{
		if(level > 0){
			PlayingField pf = root.data.getPf();
			int numMoves;
			
			PlayingField pleft = ((PlayingField)pf.makeCopy());
			numMoves = pleft.left();	
			if(numMoves>0){
				TreeNode<Round> left = root.addChild(new Round(pleft,1,"left")); //create child node for the direction left
				
				for(Round p:pleft.getAllNextPossiblePlayingFields()){ //create children nodes for all possible next playing fields
					TreeNode<Round> pnode = left.addChild(p);
					bruteForce(pnode, level-1);
				}
			}
			
			PlayingField pright = ((PlayingField)pf.makeCopy());
			numMoves = pright.right();
			if(numMoves>0){
				TreeNode<Round> right = root.addChild(new Round(pright,1,"right")); //create child node for the direction right
				
				for(Round p:pright.getAllNextPossiblePlayingFields()){ //create children nodes for all possible next playing fields
					TreeNode<Round> pnode = right.addChild(p);
					bruteForce(pnode, level-1);
				}
			}
			
			PlayingField pup = ((PlayingField)pf.makeCopy());
			numMoves = pup.up();
			if(numMoves>0){
				TreeNode<Round> up = root.addChild(new Round(pup,1,"up"));  //create child node for the direction up
				
				for(Round p:pup.getAllNextPossiblePlayingFields()){ //create children nodes for all possible next playing fields
					TreeNode<Round> pnode = up.addChild(p);
					bruteForce(pnode, level-1);
				}
			}
			
			PlayingField pdown = ((PlayingField)pf.makeCopy());
			numMoves = pdown.down();
			if(numMoves>0){
				TreeNode<Round> down = root.addChild(new Round(pdown,1,"down"));  //create child node for the direction down
				
				for(Round p:pdown.getAllNextPossiblePlayingFields()){ //create children nodes for all possible next playing fields
					TreeNode<Round> pnode = down.addChild(p);
					bruteForce(pnode, level-1);
				}
			}
		}
	}
}
