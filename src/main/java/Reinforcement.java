package main.java;

public class Reinforcement {
	
	int totalReinforcementArmies;
	/**
	 * this function is to assign armies to player for reinforcement
	 * @param player
	 * @return
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
	 * this function allow player to place armies
	 * @param player
	 * @param countryName
	 * @param num
	 * @return
	 */

	public boolean reinforce(Player player, String countryName, int num)
	{
		if(player.getOwnedCountries().containsKey(countryName.toLowerCase()))
		{
			//System.out.println("In reinforce, player.getOwnedArmies(): " + player.getOwnedArmies());
			if(player.getOwnedArmies() >= num)
			{
				Country c= player.getOwnedCountries().get(countryName.toLowerCase());
				int existingArmies = c.getNumberOfArmies();
				existingArmies += num;
				c.setNumberOfArmies(existingArmies);
				player.setOwnedArmies(player.getOwnedArmies()-num);
				return true;
			}
			else
			{
				System.out.println("You don't have enough armies");
				return false;
			}
		}
		else
		{
			System.out.println("You don't own this country");
			return false;
		}
	}
}