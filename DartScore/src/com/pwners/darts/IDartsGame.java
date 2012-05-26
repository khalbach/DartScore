package com.pwners.darts;

/**
 * Interface for handling the processing of every dart game
 * @author halbachk
 *
 */
public interface IDartsGame {
	
	/**
	 * Create game board
	 * @param scoreboard layout
	 */
	public void createBoard(int scoreboard);
	
	/**
	 * Processes a dart throw
	 * @param player player who threw dart
	 * @param dartThrow throw value
	 */
	public void processDartThrow(Player player, DartThrow dartThrow);
	
	/**
	 * Undo the previous dart throw
	 */
	public void undoDartThrow();	
	
	/**
	 * Tests to see if player has won the game
	 * @param player player who threw dart
	 * @return true if player won
	 */
	public boolean isGameOver(Player player);
	
	/**
	 * Get name of game
	 * @return name of game
	 */
	public String getGameName();
	
}
