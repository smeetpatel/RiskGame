import java.util.ArrayList;
import java.util.Iterator;

/**
 * GameMap holds the map of the Risk game. This map can be directly used or edited as
 * per the allowed commands.
 * 
 * GameMap holds the name of the map and an ArrayList of 'Continent' objects representing 
 * the Continents present in the current map.
 * @author Smeet
 *
 */
public class GameMap {
	private String mapName;
	private ArrayList<Continent> continents;
	
	GameMap(String mapName){
		this.mapName = mapName;
		this.continents = new ArrayList<Continent>();
	}
	
	String getMapName() {
		return this.mapName;
	}
	
	int getNumberOfContinents() {
		return continents.size();
	}
	
	void addContinent(Continent continent) {
		continents.add(continent);
	}
	
	Continent getContinent(int index) {
		return this.continents.get(index-1);
	}
	
	Continent getContinent(String continentName) {
		Iterator<Continent> itr = this.continents.iterator();
		
		while(itr.hasNext()) {
			Continent tempContinent = itr.next();
			if(tempContinent.getContinentName().contentEquals(continentName))
				return tempContinent;
		}
		return null;
	}
}