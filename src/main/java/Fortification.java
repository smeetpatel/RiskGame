package main.java;

/**
 * Manage's task related to fortification phase of the game.
 *
 */
public class Fortification {
	
	/**
	 * Responsible for performing fortify operation if it is a valid operation.
	 * @param player Player playing the fortification move
	 * @param fromCountry Move armies from this country
	 * @param toCountry Move armies to this country
	 * @param num Number of armies to be shifted
	 * @return true if successful, else false
	 */
	public boolean fortify(Player player, String fromCountry, String toCountry, int num)
	{
		MapValidator mv = new MapValidator();
		if(player.getOwnedCountries().containsKey(fromCountry.toLowerCase()))
		{
			if(player.getOwnedCountries().containsKey(toCountry.toLowerCase()))
			{
				if((player.getOwnedCountries().get(fromCountry.toLowerCase()).getNumberOfArmies()- num)>1)
				{
					if(mv.fortificationConnectivityCheck(player, fromCountry, toCountry))
					{
						int fromArmies = player.getOwnedCountries().get(fromCountry.toLowerCase()).getNumberOfArmies();
						fromArmies -= num;
						player.getOwnedCountries().get(fromCountry.toLowerCase()).setNumberOfArmies(fromArmies);
						int toArmies = player.getOwnedCountries().get(toCountry.toLowerCase()).getNumberOfArmies();
						toArmies += num;
						player.getOwnedCountries().get(toCountry.toLowerCase()).setNumberOfArmies(toArmies);
						return true;
					}
					else
					{
						System.out.println(fromCountry + " and " + toCountry + " do not have of player owned countries.");
						return false;
					}
				}
				else
				{
					System.out.println("You don't have enough armies.");
					return false;
				}
			}
			else
			{
				System.out.println(toCountry + " does not exist.");
				return false;
			}
		}
		else 
		{
			System.out.println(fromCountry + " does not exist.");
			return false;
		}
	}
}
