package com.pwners.darts;

/**
 * Interface for handling the processing of every dart game
 * @author halbachk
 *
 */
public interface IDartsGame {
	
	// game to create
	public void createBoard();
	
	// processes a dart
	public void processDart(Player player, int dartScore);
	
	// updates specified players score
	public void updateScore(Player player, int points);
	
	// tests to see if a player has won the game
	public boolean isGameOver(Player player);
	
	// returns name of the game
	public String getGameName();
	
}
