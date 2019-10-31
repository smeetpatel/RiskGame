package main.java;

/**
 * Manage's task related to reinforcement phase of the game.
 *
 */
public class Reinforcement {
	
	int totalReinforcementArmies;
	/**
	 * This function is to assign armies to player for reinforcement
	 * @param player Player playing the move
	 * @return true if successful, else false
	 */
	public static boolean assignReinforcementArmies(Player player)
	{
		int totalControlValue = 0;
		int totalReinforcementArmies;
		if(player.getOwnedCountries().size() >= 9)
		{
			if(player.getOwnedContinents().size()> 0)
			{
				for(Continent c:player.getOwnedContinents().values())
				{
					totalControlValue += c.getControlValue();
				}
				totalReinforcementArmies = (int)(player.getOwnedCountries().size()/3) + totalControlValue;
			}
			else
			{
				totalReinforcementArmies = (int) (player.getOwnedCountries().size()/3);
			}
		}
		else 
		{
			totalReinforcementArmies = 3;
		}
		player.setOwnedArmies(totalReinforcementArmies);
		return true;
	}

	/**
	 * This function allow player to place armies
	 * @param player Player playing the move
	 * @param countryName Reinforce armies here
	 * @param num Reinforce this many armies
	 * @return true if successful, else false
	 */

	public boolean reinforce(GameData game, Player player, String countryName, int num)
	{
		if(player.getOwnedCountries().containsKey(countryName.toLowerCase()))
		{
			if(player.getOwnedArmies() >= num)
			{
				Country c= player.getOwnedCountries().get(countryName.toLowerCase());
				int existingArmies = c.getNumberOfArmies();
				existingArmies += num;
				c.setNumberOfArmies(existingArmies);
				player.setOwnedArmies(player.getOwnedArmies()-num);
				if(player.getOwnedArmies()==0) {
					game.setGamePhase(Phase.FORTIFICATION);
				}
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}