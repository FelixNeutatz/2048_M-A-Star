package Run;

import game.PlayingField;
import game.Round;
import game.RunTree;

import java.io.IOException;
import java.util.List;
import java.util.*;

/**
 * @author Felix Neutatz
 * 
 * CC BY 4.0
 * http://creativecommons.org/licenses/by/4.0/
 * 
 * Copyright (c) 2014 Felix Neutatz
 */

public class Tree {
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
		
		int level=3;
		
		int numberOfRounds=0;
		while(pf.movesAvailable()){
			int index = (int)bruteForce(pf, level, 1, 1,level);

			pf.moveByInt(index);
			System.out.println(pf);
			
			
			pf.insertRandCell(); //insert new tile
			System.out.println(pf);
			
			numberOfRounds++;
			
		}
		System.out.println("Number of Rounds: " + numberOfRounds);
	}

	
	public static double currentCostFunction(PlayingField pf,double probability){
		return pf.costFunctionAvgScorePow(probability);
		//return ((double)pf.getNumberOfFreeCells())*probability;
	}
	
	
	public static double bruteForce(PlayingField pf, int level, double probability, int type, int rootlvl){
		double sum = 0;
		if(level > 0){			
			if(type==1){
				RunTree R1 = null;
				RunTree R2 = null;
				RunTree R3 = null;
				RunTree R4 = null;
			
				int numMoves;
				int dirs = 0;
				
				PlayingField pleft = ((PlayingField)pf.makeCopy());
				numMoves = pleft.left();	
				if(numMoves>0){
					if(rootlvl == level){
						R1 = new RunTree(pleft, level);
					    R1.start();					    
					}else{
						sum += bruteForce(pleft, level, probability*1,2,rootlvl);	
						dirs++;
					}
				}
				
				PlayingField pright = ((PlayingField)pf.makeCopy());
				numMoves = pright.right();
				if(numMoves>0){
					if(rootlvl == level){
						R2 = new RunTree(pright, level);
					    R2.start();					    
					}else{
						sum += bruteForce(pright, level, probability*1,2,rootlvl);
						dirs++;
					}
				}
				
				PlayingField pup = ((PlayingField)pf.makeCopy());
				numMoves = pup.up();
				if(numMoves>0){
					if(rootlvl == level){
						R3 = new RunTree(pup, level);
					    R3.start();					    
					}else{
						sum += bruteForce(pup, level, probability*1,2,rootlvl);
						dirs++;
					}
				}
				
				PlayingField pdown = ((PlayingField)pf.makeCopy());
				numMoves = pdown.down();
				if(numMoves>0){
					if(rootlvl == level){
						R4 = new RunTree(pdown, level);
					    R4.start();					    
					}else{
						sum += bruteForce(pdown, level, probability*1,2,rootlvl);
						dirs++;
					}
				}
				
				if(level == rootlvl){
					while((R1!=null && R1.isAlive()) || (R2!=null && R2.isAlive()) || (R3!=null &&R3.isAlive()) || (R4!=null &&R4.isAlive())){
						
					}
					double a = 0; if(R1 != null) a = R1.getResult();
					double b = 0; if(R2 != null) b = R2.getResult();
					double c = 0; if(R3 != null) c = R3.getResult();
					double d = 0; if(R4 != null) d = R4.getResult();
					
					double [] sums = new double []{ a, b, c, d };
					double best=-1;
					int index=-1;
					for(int i=0;i<sums.length;i++){
						if(best<sums[i]){
							best = sums [i];
							index=i;
							
						}
					}
					
					
					
					//List b = Arrays.asList(sums);
					
					//System.out.println(Collections.max(b).toString());
					
					System.out.println("left: "+ a);
					System.out.println("right: "+ b);
					System.out.println("up: "+ c);
					System.out.println("down: "+ d);
					
					return index;
				}else{
					if(dirs > 0){
						sum /= (double)dirs;
					}else{
						return currentCostFunction(pf, probability);
					}
				}
			}
			else{
				ArrayList<Round> rs = pf.getAllNextPossiblePlayingFields();
				for(Round p:rs){ //create children nodes for all possible next playing fields
					sum += bruteForce(p.getPf(), level-1, probability*p.getProbability(),1,rootlvl);
				}
				if(rs.size()>0){
					sum /= (double)rs.size();
				}else{
					return currentCostFunction(pf, probability);
				}
			}
		}
		else{
			return currentCostFunction(pf, probability);
		}
		return sum;
	}
	

}
