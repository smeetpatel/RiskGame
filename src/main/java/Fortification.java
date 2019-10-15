package main.java;

public class Fortification {
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
