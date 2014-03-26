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
	private String move;		//what was done to get to this state - just for debugging reasons
		
	
	public Round(PlayingField pf, double probability, String move) {
		super();
		this.pf = pf;
		this.probability = probability;
		this.move = move;
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
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}
	
	
	
}
