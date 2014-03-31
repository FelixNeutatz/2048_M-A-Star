package game;

/**
 * @author Felix Neutatz
 * 
 * CC BY 4.0
 * http://creativecommons.org/licenses/by/4.0/
 * 
 * Copyright (c) 2014 Felix Neutatz
 */

public class Round {
	private PlayingField pf;    //current state of the playing field
	private double probability; //how high is the probability to go there 
	//private String move;		//what was done to get to this state - just for debugging reasons
	
	private int move;
		
	
	public Round(PlayingField pf, double probability, String move) {
		this.pf = pf;
		this.probability = probability;
		setMoveByString(move);
	}
	
	
	public void setMoveByString(String direction){
		direction.toLowerCase();
		
		switch(direction.charAt(0)){
	    	case 'u': move=1;
	    			  break;
	    	case 'd': move=2;
	    			  break;
	    	case 'l': move=3;
	    			  break;
	    	case 'r': move=4;
	    	          break;
	    	case '2': move=-2;
	    			  break;
	    	case '4': move=-4;
	    			  break;
		}
	}

	public PlayingField getPf() {
		return pf;
	}

	public void setPf(PlayingField pf) {
		this.pf = pf;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(double probability) {
		this.probability = probability;
	}

	public String getMove() {
		switch(move){
			case 1: return "up";
			case 2: return "down";
			case 3: return "left";
			case 4: return "right";
		}
		return "";
	}

	public void setMove(int move) {
		this.move = move;
	}
	
	
	
}
