package game;

import java.util.ArrayList;
import java.util.Random;



/**
 * @author Felix Neutatz
 * 
 * CC BY 4.0
 * http://creativecommons.org/licenses/by/4.0/
 * 
 * Copyright (c) 2014 Felix Neutatz
 */

public class PlayingField {
	private int [][] cells; //respresents the whole playing field
	
	public PlayingField(int x,int y){
		this.cells = new int[x][y];
		
		toNull(); //set all tiles to unassigned
	}
	
	public PlayingField(int [][] matrix){ //construct a playing field by yourself
		this.cells = matrix;
	}
	
	public void toNull(){
		//set everything to null - no number set
		for(int i=0;i<getX();i++){
			for(int u=0;u<getY();u++){
				this.cells[i][u]=0;
			}
		}
	}
	
	public int getX(){ //get the x length of the playing field
		return cells.length;
	}
	public int getY(){ //get the y length of the playing field
		return cells[0].length;
	}
	
	
	/**
	 * get all tiles which dont have any value assigned
	 * @return
	 */
	public ArrayList<Tile> getFreeCells(){
		ArrayList<Tile> freeCells = new ArrayList<Tile>();
		for(int i=0;i<getX();i++){
			for(int u=0;u<getY();u++){
				if(this.cells[i][u]==0){
					freeCells.add(new Tile(i,u,0));
				}
			}
		}
		return freeCells;
	}
	
	
	/**
	 * get number of tiles of the playing field
	 * @return
	 */
	public int size(){
		return getX()*getY();
	}
	
	public int getNumberOccupiedTiles(){
		return size()-getNumberOfFreeCells();
	}
	
	
	/**
	 * check whether there are any tiles which have no value assigned
	 * @return
	 */
	public Boolean cellsAvailable(){
		if(getNumberOfFreeCells()>0){
			return true;
		}else{
			return false;
		}
	}
	
	// Get the vector representing the chosen direction
	public static int [] getVector(int direction) {
	  // Vectors representing tile movement
	  int[][] map = new int[][] {
		        new int[] { 0, -1 },      //Up
		        new int[] { 1, 0 },		  //Right
		        new int[] { 0, 1 }, 	  //Down
		        new int[] { -1, 0 }		  //Left
		    };          
	            
	  return map[direction];
	}
	
	
	/**
	 * move the tiles by string like up, down, left, right
	 * @param direction
	 * @return
	 */
	public int moveByString(String direction){
		int moved = 0;
		direction.toLowerCase();
		
		switch(direction.charAt(0)){
	    	case 'u': moved=this.up();
	    			  break;
	    	case 'd': moved=this.down();
	    			  break;
	    	case 'l': moved=this.left();
	    			  break;
	    	case 'r': moved=this.right();
	    	          break;
		}
		return moved;
	}
	
	
	/**
	 * Check whether there is a possibility to match any tiles on the playing field by any user action
	 * @return
	 */
	public Boolean tileMatchesAvailable(){
		/*
		for (int i = 0; i < getX(); i++) {
		    for (int u = 0; u < getY(); u++) {
		      if (cells[i][u]!= 0) {
		        for (int direction = 0; direction < 4; direction++) {
		          int [] vector = getVector(direction);
		          
		          int other  = cells[i+vector[0]][u+vector[1]];

		          if (other!=0 && other == cells[i][u]) {
		            return true; // These two tiles can be merged
		          }
		        }
		      }
		    }
		  }

		  return false;*/
		
		PlayingField cp1 = this.makeCopy();
		PlayingField cp2 = this.makeCopy();
		PlayingField cp3 = this.makeCopy();
		PlayingField cp4 = this.makeCopy();
		
		if(cp1.up()>0 || cp2.down() >0 || cp3.left()>0 || cp4.right()>0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * Check whether there are moves left which can be performed by the user
	 * @return
	 */
	public Boolean movesAvailable(){
		return this.cellsAvailable() || this.tileMatchesAvailable();
	}
	
	
	/**
	 * get number of unassigned tiles
	 * @return
	 */
	public int getNumberOfFreeCells(){
		int sum=0;
		for(int i=0;i<getX();i++){
			for(int u=0;u<getY();u++){
				if(this.cells[i][u]==0){
					sum++;
				}
			}
		}
		return sum;
	}
	
	
	/**
	 * set value of the playing field coordinate (x,y) to value
	 * @param x
	 * @param y
	 * @param value
	 */
	public void setValue(int x, int y, int value){
		cells[x][y]=value;
	}
	
	
	/**
	 * select randomly one unassigned tile 
	 * @return
	 */
	public Tile getRandomFreeCell(){
		return getRandomFreeCells(1).get(0);
	}
	
	/**
	 * select randomly n unassigned tiles
	 * @return
	 */
	public ArrayList<Tile> getRandomFreeCells(int n){
		ArrayList<Tile> randFreeCells = new ArrayList<Tile>();
		Random randomGenerator  = new Random();
		ArrayList<Tile> freeCells = this.getFreeCells();
		
		for(int i=0;i<n;i++){
			int index = randomGenerator.nextInt(freeCells.size());
			randFreeCells.add(freeCells.get(index));
			freeCells.remove(index);
		}
		
		return randFreeCells;
	}
	
	
	/**
	 * after each move there will occur a new tile which has either the value 2 or 4
	 * this function will determine randomly to select 2 or 4
	 * @return
	 */
	public int getRandomFirstValue(){
		return Math.random() < 0.9 ? 2 : 4;
	}
	
	
	/**
	 * check whether game is lost
	 * @return
	 */
	public Boolean isGameLost(){
		if(getFreeCells().size()==0){
			return true;
		}
		return false;
	}
	
	
	/**
	 * check whether game is one
	 * --> check whether one tile has the value 2048
	 * @return
	 */
	public Boolean isGameWon(){		
		for(int i=0;i<getX();i++){
			for(int u=0;u<getY();u++){
				if(this.cells[i][u]==2048){
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * sum all values of all tiles on the playing field
	 * @return
	 */
	public int sumAll(){
		int sum=0;
		for(int i=0;i<getX();i++){
			for(int u=0;u<getY();u++){
				sum += this.cells[i][u];
			}
		}
		return sum;
	}
	
	
	
	/**
	 * set n unassigned tiles to either 2 or 4 (randomly)
	 * default for n: 2
	 * @param n
	 */
	public void initialize(int n){
		ArrayList<Tile> randFreeCells = getRandomFreeCells(n);
		for(int i=0;i<n;i++){
			int cx = randFreeCells.get(i).getX();
			int cy = randFreeCells.get(i).getY();
			this.cells[cx][cy] = getRandomFirstValue();
		}
	}
	
	
	/**
	 * insert new tile after an user move
	 */
	public void insertRandCell(){
		initialize(1);
	}
	
	public int right(){
		int movedNr = 0;
		for(int i=0;i<getX();i++){
			ArrayList<Integer> merged = new ArrayList<Integer>(); //list of all tiles which are already merged and are not allowed to be merged again
			for(int u=getY()-1;u>0;u--){//go from right to left
				int cv = u-1;
				if(cells[i][cv] != 0){
					Boolean moved = false;
					for(int t=u;t<getY();t++){ //go from left to right
						//check if it could be merged and if we are allowed to merge
						if(cells[i][cv] == cells[i][t] && !merged.contains(t)){ 
							cells[i][t] = cells[i][cv] * 2;
							cells[i][cv] = 0;
							merged.add(t); 
							moved = true;
							break;
						}
						if(cells[i][t] == 0){
							cells[i][t] = cells[i][cv];
							cells[i][cv] = 0;
							cv++;
							moved = true;
						}
					}
					if(moved) movedNr++;
				}
			}
		}
		return movedNr;
	}
	
	public int left(){
		int movedNr = 0;
		for(int i=0;i<getX();i++){
			ArrayList<Integer> merged = new ArrayList<Integer>(); //list of all tiles which are already merged and are not allowed to be merged again
			for(int u=0;u<getY()-1;u++){//go from left to right
				int cv = u+1;
				if(cells[i][cv] != 0){
					Boolean moved = false;
					for(int t=u;t>=0;t--){ //go from current right to left
						//check if it could be merged and if we are allowed to merge
						if(cells[i][cv] == cells[i][t] && !merged.contains(t)){ 
							cells[i][t] = cells[i][cv] * 2;
							cells[i][cv] = 0;
							merged.add(t);
							moved = true;
							break;
						}
						if(cells[i][t] == 0){
							cells[i][t] = cells[i][cv];
							cells[i][cv] = 0;
							cv--;
							moved = true;
						}
					}
					if(moved) movedNr++;
				}
			}
		}
		return movedNr;
	}
	
	public int up(){
		int movedNr = 0;
		for(int i=0;i<getY();i++){
			ArrayList<Integer> merged = new ArrayList<Integer>(); //list of all tiles which are already merged and are not allowed to be merged again
			for(int u=0;u<getX()-1;u++){//go from left to right
				int cv = u+1;
				if(cells[cv][i] != 0){
					Boolean moved = false;
					for(int t=u;t>=0;t--){ //go from current right to left
						//check if it could be merged and if we are allowed to merge
						if(cells[cv][i] == cells[t][i] && !merged.contains(t)){ 
							cells[t][i] = cells[cv][i] * 2;
							cells[cv][i] = 0;
							merged.add(t); 
							moved = true;
							break;
						}
						if(cells[t][i] == 0){
							cells[t][i] = cells[cv][i];
							cells[cv][i] = 0;
							cv--;
							moved = true;
						}
					}
					if(moved) movedNr++;
				}
			}
		}
		return movedNr;
	}
	
	public int down(){
		int movedNr = 0;
		for(int i=0;i<getY();i++){
			ArrayList<Integer> merged = new ArrayList<Integer>(); //list of all tiles which are already merged and are not allowed to be merged again
			for(int u=getX()-1;u>0;u--){//go from right to left
				int cv = u-1;
				if(cells[cv][i] != 0){
					Boolean moved = false;
					for(int t=u;t<getX();t++){ //go from left to right
						//check if it could be merged and if we are allowed to merge
						if(cells[cv][i] == cells[t][i] && !merged.contains(t)){ 
							cells[t][i] = cells[cv][i] * 2;
							cells[cv][i] = 0;
							merged.add(t); 
							moved = true;
							break;
						}
						if(cells[t][i] == 0){
							cells[t][i] = cells[cv][i];
							cells[cv][i] = 0;							
							cv++;
							moved = true;
						}
					}
					if(moved) movedNr++;
				}
			}
		}
		return movedNr;
	}
	
		
	/* (non-Javadoc)
	 * generate a beautiful representation of playing field
	 */
	public String toString(){
		String s = "";
				
		for(int i=0;i<getX()+1;i++){
			
			//boundary
			for(int u=0;u<getY();u++){
				if(i==0 && u==0){
					s += "/";				
				}else if(i==getX() && u==0){
					s += "\\";
				}else{				
					s += "|";
				}
				s += "------";
			}
			if(i==0){
				s += "\\";				
			}else if(i==getX()){
				s += "/";
			}
			else{			
				s += "|";
			}
			s += "\n";
			if(i==getX()) return s;
							
			//values
			for(int u=0;u<getY();u++){
				if(u==0){
					s += "|";
				}
				if(cells[i][u]==0){
					s+= "      ";	
				}else{
					s+= String.format(" %4d ", cells[i][u]);		
				}
				s += "|";
			}
			s += "\n";
		}
		
		return null;
	}
	
	
	/**
	 * make a clone of the matrix for the playing field
	 * @return
	 */
	public int[][] getCopyOfCells(){
		int [][] myInt = new int[cells.length][];
		for(int i = 0; i < cells.length; i++)
		    myInt[i] = cells[i].clone();
		
		return myInt;
	}
	
	
	/**
	 * clone the whole object of the playing field
	 * @return
	 */
	public PlayingField makeCopy(){
		return new PlayingField(getCopyOfCells());
	}
	
	
	/**
	 * get all possible playing fields which could be occur when the tile comes into play
	 * @return
	 */
	public ArrayList<Round> getAllNextPossiblePlayingFields(){
		ArrayList<Round> allpos = new ArrayList<Round>();
		
		ArrayList<Tile> freeTiles = this.getFreeCells();	//get all unassigned tiles	
		for(int i=0;i<freeTiles.size();i++){ //iterate trough all tiles
			Tile ctile = freeTiles.get(i);
			
			PlayingField p_new_2 = (PlayingField)this.makeCopy();
			p_new_2.setValue(ctile.getX(), ctile.getY(), 2);
			allpos.add(new Round(p_new_2,0.9,"2 at x="+ctile.getX()+", y="+ctile.getY())); //set 2 with probability of 0.9
			
			PlayingField p_new_4 = (PlayingField)this.makeCopy();
			p_new_4.setValue(ctile.getX(), ctile.getY(), 4);
			allpos.add(new Round(p_new_4,0.1,"4 at x="+ctile.getX()+", y="+ctile.getY())); //set 4 with probability of 0.1
		}
		
		return allpos;
	}
	
}
