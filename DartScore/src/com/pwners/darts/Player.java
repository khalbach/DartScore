package com.pwners.darts;

public class Player {
	
	/* Private Data Members */
	private String m_name;
	
	/**
	 * Player representation object
	 * @param name name of player
	 */
	public Player(String name){
		m_name = name;
	}

	/**
	 * Get the name of the player
	 * @return name of player
	 */
	public String get_name() {
		return m_name;
	}

	/**
	 * Set the name of the player
	 * @param name name of player
	 */
	public void set_name(String name) {
		m_name = name;
	}

}
