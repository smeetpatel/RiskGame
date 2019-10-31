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
	public FortificationCheck fortify(GameData game, Player player, String fromCountry, String toCountry, int num)
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
						game.setGamePhase(Phase.TURNEND);
						return FortificationCheck.FORTIFICATIONSUCCESS;
					} else {
						return FortificationCheck.PATHFAIL;
					}
				} else {
					return FortificationCheck.ARMYCOUNTFAIL;
				}
			} else {
				return FortificationCheck.TOCOUNTRYFAIL;
			}
		}
		else 
		{
			return FortificationCheck.FROMCOUNTRYFAIL;
		}
	}

	public void fortify(GameData game){
		game.setGamePhase((Phase.TURNEND));
	}
}
