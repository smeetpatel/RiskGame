import java.util.ArrayList;

/**
 * Country class holds the details related to any country present in the map.
 * It also maintains a list of it's neighbour 'Country' objects.
 * 
 * @author Smeet
 *
 */
public class Country {
	private int index;
	private String countryName;
	private String inContinent;
	private ArrayList<Country> neighbours;
	int xCoOrdinate;
	int yCoOrdinate;
	
	Country(String index, String countryName, String continentIndex, String xCoOrdinate, String yCoOrdinate, GameMap map){
		this.index = Integer.parseInt(index);
		this.countryName = countryName;
		this.inContinent = map.getContinent(Integer.parseInt(continentIndex)).getContinentName();
		this.neighbours = new ArrayList<Country>();
		this.xCoOrdinate = Integer.parseInt(xCoOrdinate);
		this.yCoOrdinate = Integer.parseInt(yCoOrdinate);
	}
	
	int getIndex() {
		return index;
	}
	
	String getCountryName() {
		return countryName;
	}
	
	String getContinentName() {
		return this.inContinent;
	}
	
	ArrayList<Country> getNeighbours(){
		return this.neighbours;
	}
}