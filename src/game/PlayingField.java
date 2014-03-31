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
		ArrayList<Tile> freeCells = new ArrayList<Tile>(14);
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
	
	public int moveByInt(int direction){
		int moved = 0;
		
		switch(direction){
	    	case 0: moved=this.left();
	    			  break;
	    	case 1: moved=this.right();
	    			  break;
	    	case 2: moved=this.up();
	    			  break;
	    	case 3: moved=this.down();
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
		ArrayList<Tile> randFreeCells = new ArrayList<Tile>(2);
		Random randomGenerator  = new Random();
		ArrayList<Tile> freeCells = this.getFreeCells();
		
		for(int i=0;i<n;i++){
			if(freeCells.size()>0){
				int index = randomGenerator.nextInt(freeCells.size());
				randFreeCells.add(freeCells.get(index));
				freeCells.remove(index);
			}
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
	
	
	public double sumPow(){
		double sum=0;
		for(int i=0;i<getX();i++){
			for(int u=0;u<getY();u++){
				sum += Math.pow(this.cells[i][u],2);
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
		for(int i=0;i<randFreeCells.size();i++){
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
			ArrayList<Integer> merged = new ArrayList<Integer>(2); //list of all tiles which are already merged and are not allowed to be merged again
			for(int u=getY()-1;u>0;u--){//go from right to left
				int cv = u-1;
				if(cells[i][cv] != 0){
					Boolean moved = false;
					for(int t=u;t<getY();t++){ //go from left to right
						if(cells[i][t] == 0){
							cells[i][t] = cells[i][cv];
							cells[i][cv] = 0;
							cv++;
							moved = true;
						}else{
							//check if it could be merged and if we are allowed to merge
							if(cells[i][cv] == cells[i][t] && !merged.contains(t)){ 
								cells[i][t] = cells[i][cv] * 2;
								cells[i][cv] = 0;
								merged.add(t); 
								moved = true;
							}
							break;
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
			ArrayList<Integer> merged = new ArrayList<Integer>(2); //list of all tiles which are already merged and are not allowed to be merged again
			for(int u=0;u<getY()-1;u++){//go from left to right
				int cv = u+1;
				if(cells[i][cv] != 0){
					Boolean moved = false;
					for(int t=u;t>=0;t--){ //go from current right to left
						if(cells[i][t] == 0){
							cells[i][t] = cells[i][cv];
							cells[i][cv] = 0;
							cv--;
							moved = true;
						}
						else{
							//check if it could be merged and if we are allowed to merge
							if(cells[i][cv] == cells[i][t] && !merged.contains(t)){ 
								cells[i][t] = cells[i][cv] * 2;
								cells[i][cv] = 0;
								merged.add(t);
								moved = true;								
							}
							break;
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
			ArrayList<Integer> merged = new ArrayList<Integer>(2); //list of all tiles which are already merged and are not allowed to be merged again
			for(int u=0;u<getX()-1;u++){//go from left to right
				int cv = u+1;
				if(cells[cv][i] != 0){
					Boolean moved = false;
					for(int t=u;t>=0;t--){ //go from current right to left
						//check if it could be merged and if we are allowed to merge
						if(cells[t][i] == 0){
							cells[t][i] = cells[cv][i];
							cells[cv][i] = 0;
							cv--;
							moved = true;
						}else{
							if(cells[cv][i] == cells[t][i] && !merged.contains(t)){ 
								cells[t][i] = cells[cv][i] * 2;
								cells[cv][i] = 0;
								merged.add(t); 
								moved = true;
							}
							break;
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
			ArrayList<Integer> merged = new ArrayList<Integer>(2); //list of all tiles which are already merged and are not allowed to be merged again
			for(int u=getX()-1;u>0;u--){//go from right to left
				int cv = u-1;
				if(cells[cv][i] != 0){
					Boolean moved = false;
					for(int t=u;t<getX();t++){ //go from left to right
						if(cells[t][i] == 0){
							cells[t][i] = cells[cv][i];
							cells[cv][i] = 0;							
							cv++;
							moved = true;
						}else{
							//check if it could be merged and if we are allowed to merge
							if(cells[cv][i] == cells[t][i] && !merged.contains(t)){ 
								cells[t][i] = cells[cv][i] * 2;
								cells[cv][i] = 0;
								merged.add(t); 
								moved = true;								
							}
							break;
						}
					}
					if(moved) movedNr++;
				}
			}
		}
		return movedNr;
	}
	
	public double getMaxWeightedSum(){
		/*
		int[][][] weights = new int[][][] {
				new int[][]{
			        new int[] { 8, 16, 32, 64},
			        new int[] { 4 , 8 , 16 , 32},
			        new int[] { 2  , 4  , 8  , 16},
			        new int[] { 1   , 2   , 4   , 8}
				},
				new int[][]{
				        new int[] { 64, 32, 16, 8},
				        new int[] { 32 , 16 , 8 , 4},
				        new int[] { 16  , 8  , 4  , 2},
				        new int[] { 8   , 4   , 2   , 1}
				},
				new int[][]{
				        new int[] { 8   , 4   , 2   , 1},
				        new int[] { 16  , 8  , 4  , 2},
				        new int[] { 32 , 16 , 8 , 4},
				        new int[] { 64, 32, 16, 8}
				},
				new int[][]{
						new int[] { 1   , 2   , 4   , 8},
				        new int[] { 2  , 4  , 8  , 16},
				        new int[] { 4 , 8 , 16 , 32},
				        new int[] { 8, 16, 32, 64}
				}
		    };
		*/
		/*
		int[][][] weights = new int[][][] {
				new int[][]{
			        new int[] { 1, 2, 4, 8},
			        new int[] { 1, 2, 4, 4},
			        new int[] { 1, 2, 2, 2},
			        new int[] { 1, 1, 1, 1}
				},
				new int[][]{
						new int[] { 8, 4, 2, 1},
				        new int[] { 4, 4, 2, 1},
				        new int[] { 2, 2, 2, 1},
				        new int[] { 1, 1, 1, 1}
				},
				new int[][]{
						new int[] { 1, 1, 1, 1},
				        new int[] { 2, 2, 2, 1},
				        new int[] { 4, 4, 2, 1},
				        new int[] { 8, 4, 2, 1}
				},
				new int[][]{
						new int[] { 1, 1, 1, 1},
				        new int[] { 1, 2, 2, 2},
				        new int[] { 1, 2, 4, 4},
				        new int[] { 1, 2, 4, 8}
				}
		    };*/
		/*
		int[][][] weights = new int[][][] {
				new int[][]{
			        new int[] { 1, 10, 100, 1000},
			        new int[] { 1, 10, 100, 100},
			        new int[] { 1, 10, 10, 10},
			        new int[] { 1, 1, 1, 1}
				},
				new int[][]{
						new int[] { 1000, 100, 10, 1},
				        new int[] { 100, 100, 10, 1},
				        new int[] { 10, 10, 10, 1},
				        new int[] { 1, 1, 1, 1}
				},
				new int[][]{
						new int[] { 1, 1, 1, 1},
				        new int[] { 10, 10, 10, 1},
				        new int[] { 100, 100, 10, 1},
				        new int[] { 1000, 100, 10, 1}
				},
				new int[][]{
						new int[] { 1, 1, 1, 1},
				        new int[] { 1, 10, 10, 10},
				        new int[] { 1, 10, 100, 100},
				        new int[] { 1, 10, 100, 1000}
				}
		    };
		*/
		
		int[][][] weights = new int[][][] {
				new int[][]{
			        new int[] { 16, 15, 14, 13},
			        new int[] { 9, 10, 11, 12},
			        new int[] { 8, 7, 6, 5},
			        new int[] { 1, 2, 3, 4}
				},
				new int[][]{
						new int[] { 13, 14, 15, 16},
				        new int[] { 12, 11, 10, 9},
				        new int[] { 5, 6, 7, 8},
				        new int[] { 4, 3, 2, 1}
				},
				new int[][]{
						new int[] { 4, 3, 2, 1},
				        new int[] { 5, 6, 7, 8},
				        new int[] { 12, 11, 10, 9},
				        new int[] { 13, 14, 15, 16}
				},
				new int[][]{
						new int[] { 1, 2, 3, 4},
				        new int[] { 8, 7, 6, 5},
				        new int[] { 9, 10, 11, 12},
				        new int[] { 16, 15, 14, 13}
				},
				
				new int[][]{
				        new int[] { 16, 9, 8, 1},
				        new int[] { 15, 10, 7, 2},
				        new int[] { 14, 11, 6, 3},
				        new int[] { 13, 12, 5, 4}
					},
				new int[][]{
							new int[] { 13, 12, 5, 4},
					        new int[] { 14, 11, 6, 3},
					        new int[] { 15, 10, 7, 2},
					        new int[] { 16, 9, 8, 1}
					},
				new int[][]{
							new int[] { 1, 8, 9, 16},
					        new int[] { 2, 7, 10, 15},
					        new int[] { 3, 6, 11, 14},
					        new int[] { 4, 5, 12, 13}
					},
				new int[][]{
							new int[] { 4, 5, 12, 13},
					        new int[] { 3, 6, 11, 14},
					        new int[] { 2, 7, 10, 15},
					        new int[] { 1, 8, 9, 16}
					}
		    };
		/*
		int[][][] weights = new int[][][] {
				new int[][]{
			        new int[] { 16, 15, 14, 13},
			        new int[] { 12, 11, 10, 9},
			        new int[] { 8, 7, 6, 5},
			        new int[] { 4, 3, 2, 1}
				},
				new int[][]{
						new int[] { 13, 14, 15, 16},
				        new int[] { 9, 10, 11, 12},
				        new int[] { 5, 6, 7, 8},
				        new int[] { 1, 2, 3, 4}
				},
				new int[][]{
						new int[] { 1, 2, 3, 4},
				        new int[] { 5, 6, 7, 8},
				        new int[] { 9, 10, 11, 12},
				        new int[] { 13, 14, 15, 16}
				},
				new int[][]{
						new int[] { 4, 3, 2, 1},
				        new int[] { 8, 7, 6, 5},
				        new int[] { 12, 11, 10, 9},
				        new int[] { 16, 15, 14, 13}
				},
				
				new int[][]{
				        new int[] { 16, 12, 8, 4},
				        new int[] { 15, 11, 7, 3},
				        new int[] { 14, 10, 6, 2},
				        new int[] { 13, 9, 5, 1}
					},
				new int[][]{
							new int[] { 13, 9, 5, 1},
					        new int[] { 14, 10, 6, 2},
					        new int[] { 15, 11, 7, 3},
					        new int[] { 16, 12, 8, 4}
					},
				new int[][]{
							new int[] { 4, 8, 12, 16},
					        new int[] { 3, 7, 11, 15},
					        new int[] { 2, 6, 10, 14},
					        new int[] { 1, 5, 9, 13}
					},
				new int[][]{
							new int[] { 1, 5, 9, 13},
					        new int[] { 2, 6, 10, 14},
					        new int[] { 3, 7, 11, 15},
					        new int[] { 4, 8, 12, 16}
					}
		    };*/
		    
		
		int oldsum=-1;
		for(int j=0;j<weights.length;j++){
			int sum =0;
			for(int i=0;i<getX();i++){
				for(int u=0;u<getY();u++){
					sum += cells[i][u] * Math.pow(2,weights[j][i][u]);
				}
			}
			if(sum > oldsum){
				oldsum = sum;
			}
		}
		return oldsum;
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
	
	
	public ArrayList<PlayingField> getAllNextPossiblePlayingFieldsPF(){
		ArrayList<PlayingField> allpos = new ArrayList<PlayingField>(14*2);
		
		ArrayList<Tile> freeTiles = this.getFreeCells();	//get all unassigned tiles	
		for(int i=0;i<freeTiles.size();i++){ //iterate trough all tiles
			Tile ctile = freeTiles.get(i);
			
			PlayingField p_new_2 = (PlayingField)this.makeCopy();
			p_new_2.setValue(ctile.getX(), ctile.getY(), 2);
			allpos.add(p_new_2);
			
			PlayingField p_new_4 = (PlayingField)this.makeCopy();
			p_new_4.setValue(ctile.getX(), ctile.getY(), 4);
			allpos.add(p_new_4);
		}
		
		return allpos;
	}
	
	/**
	 * get all possible playing fields which could be occur when the tile comes into play
	 * @return
	 */
	public ArrayList<Round> getAllNextPossiblePlayingFields(){
		ArrayList<Round> allpos = new ArrayList<Round>(14*2);
		
		ArrayList<Tile> freeTiles = this.getFreeCells();	//get all unassigned tiles	
		for(int i=0;i<freeTiles.size();i++){ //iterate trough all tiles
			Tile ctile = freeTiles.get(i);
			
			PlayingField p_new_2 = (PlayingField)this.makeCopy();
			p_new_2.setValue(ctile.getX(), ctile.getY(), 2);
			allpos.add(new Round(p_new_2,0.9,"2("+ctile.getX()+","+ctile.getY()+")")); //set 2 with probability of 0.9
			
			PlayingField p_new_4 = (PlayingField)this.makeCopy();
			p_new_4.setValue(ctile.getX(), ctile.getY(), 4);
			allpos.add(new Round(p_new_4,0.1,"4("+ctile.getX()+","+ctile.getY()+")" )); //set 4 with probability of 0.1
		}
		
		return allpos;
	}
	
	/*
	public double getSmoothness() {
		  double smoothness = 0;
		  for (int x=0; x<getX(); x++) {
		    for (int y=0; y<getY(); y++) {
		      if ( cells[x][y] > 0) {
		        double value = Math.log(cells[x][y]) / Math.log(2);
		        for (int direction=1; direction<=2; direction++) {
		          int[] vector = this.getVector(direction);
		          var targetCell = this.findFarthestPosition(this.indexes[x][y], vector).next;

		          if (this.cellOccupied(targetCell)) {
		            var target = this.cellContent(targetCell);
		            var targetValue = Math.log(target.value) / Math.log(2);
		            smoothness -= Math.abs(value - targetValue);
		          }
		        }
		      }
		    }
		  }
		  return smoothness;
		}
	*/
	
	public int MaxTile(){
		int best=-1;
		for(int i=0;i<getX();i++){
			for(int u=0;u<getY();u++){
				 if(cells[i][u]>best){
					 best = cells[i][u];
				 }
			}
		}
		return best;
	}
	
	
	public double costFunctionAvgScorePow(double probability){		
		return ((double)sumPow()/((double)getNumberOccupiedTiles()));
	}
	
}
