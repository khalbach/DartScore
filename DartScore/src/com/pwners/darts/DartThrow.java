package com.pwners.darts;

/**
 * Throw represents a single dart throw in a game
 * @author halbachk
 *
 */
public class DartThrow {
	
	 public enum Multiplier { SINGLE, DOUBLE, TRIPLE };
	 private final int m_score;
	 private final Multiplier m_multiplier;

	 /**
	  * Throw object constructor
	  * @param score dart throw score
	  * @param multiplier dart throw multiplier
	  */
	public DartThrow(int score, Multiplier multiplier){
		 m_score = score;
		 m_multiplier = multiplier;
	 }

	/**
	 * 
	 * @return dart throw score
	 */
	public int get_score() {
		return m_score;
	}

	/**
	 * 
	 * @return dart throw multiplier
	 */
	public Multiplier get_multiplier() {
		return m_multiplier;
	}
}
