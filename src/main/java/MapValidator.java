package main.java;

/**
 * Class holding functions to validate map.
 * 
 * @author Smeet
 */
public class MapValidator {

	/**
	 * Checks if continent with same name already exists in the map
	 * @param map GameMap object holding record of all the existing continents and countries
	 * @param continentName name of the continent to be checked
	 * @return true if continent already exists, else false
	 */
	public static boolean doesContinentExist(GameMap map, String continentName) {
		if(map.getContinents().containsKey(continentName.toLowerCase()))
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if country with same name already exists in the same continent.
	 * @param map GameMap object holding record of all the existing continents and countries
	 * @param countryName name of the country to be checked
	 * @return true if country already exists, else false
	 */
	public static boolean doesCountryExist(GameMap map, String countryName) {
		if(map.getCountries().containsKey(countryName.toLowerCase()))
			return true;
		else
			return false;
	}
}
