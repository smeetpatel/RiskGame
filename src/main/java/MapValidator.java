package main.java;

import java.util.HashMap;
import java.util.Iterator;

import main.java.Continent;
import main.java.Country;
import main.java.GameMap;
import org.jgrapht.*;
import org.jgrapht.graph.*;
//import org.jgrapht.io.*;
import org.jgrapht.traverse.*;
import org.jgrapht.alg.connectivity.*;

/**
 * Class holding functions to validate map.
 * 
 * @author Smeet
 */
public class MapValidator {

	 private Graph<Country, DefaultEdge> mapGraph; //JGraphT type Graph representing the game map
	
	 /**
	  * Creates a Graph object supported by JGraphT library.
	  */
	 MapValidator(){
		 this.mapGraph = new DefaultUndirectedGraph<>(DefaultEdge.class);
	 }
	 
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
	
	/**
	 * Creates a graph representation of the map using JGraphT library.
	 * Need to create the map again every time any of the Country object is altered.
	 * @param map GameMap object whose graph is to be created
	 * @return JGraphT type graph representing the GameMap object
	 */
	public Graph<Country, DefaultEdge> createGraph(GameMap map) {
		//add Country as vertices
		for(Country country : map.getCountries().values()) {
			mapGraph.addVertex(country);
		}
		
		//add Edges based on neighbors of each countries
		for(Country country : map.getCountries().values()) {
			for(Country neighbor : map.getCountries().values()) {
				mapGraph.addEdge(country, neighbor);
			}
		}
	
		return mapGraph;
	}
	
	/**
	 * Creates a graph representation of the map using JGraphT library.
	 * Used for creating sub-graph for a continent
	 * @param subGraph JGraphT type graph
	 * @param countries List of Country objects in the continent for which sub-graph is being created
	 * @return JGraphT type graph representing the argument Continent
	 */
	public Graph<Country, DefaultEdge> createGraph(Graph<Country, DefaultEdge> subGraph, HashMap<String, Country> countries) {
		//add Country as vertices
		for(Country country : countries.values()) {
			subGraph.addVertex(country);
		}
		
		//add Edges based on neighbors of each countries
		for(Country country : countries.values()) {
			for(Country neighbor : country.getNeighbours().values()) {
				if(countries.containsKey(neighbor.getCountryName().toLowerCase()))
					subGraph.addEdge(country, neighbor);
			}
		}
		//printGraph(subGraph, countries);
		return subGraph;
	}
	/**
	 * Checks if entire graph is connected
	 * @param graph JGraphT graph of the form Graph<Country, DefaultEdge>
	 * @return true if graph is connected, else false indicating that graph is invalid
	 */
	public  boolean isGraphConnected(Graph<Country, DefaultEdge> graph) {
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<>(graph);
		if(ci.isConnected())
			return true;
		else
			return false;
	}
	
	/**
	 * Checks if all continents are connected sub-graphs or not
	 * @param map GameMap object representing the game map
	 * @return true if all continents are connected sub-graph, else false indicating incorrect map
	 */
	public boolean continentConnectivityCheck(GameMap map) {
		for(Continent continent : map.getContinents().values()) {
			Graph<Country, DefaultEdge> subGraph = new DefaultUndirectedGraph<>(DefaultEdge.class);
			subGraph = createGraph(subGraph, continent.getCountries());
			//printGraph(subGraph, continent.getCountries());
			if(!isGraphConnected(subGraph)) {
				printGraph(subGraph, continent.getCountries());
				//System.out.println(continent.getContinentName() + " is not a connected sub-graph.");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Check if any continent is empty and does not hold any country.
	 * @param map GameMap object representing the game map
	 * @return true if no continent is empty, else false indicating empty continent
	 */
	public boolean notEmptyContinent(GameMap map) {
		for(Continent continent : map.getContinents().values()) {
			if(continent.getCountries().size()==0)
				return false;
		}
		return true;
	}
	
	public void printGraph(Graph<Country, DefaultEdge> graph, HashMap<String, Country> countries) {
		Country c = new Country();
		for(Country co:countries.values())
			c = co;
		Iterator<Country> iterator = new DepthFirstIterator<>(graph, c);
        while (iterator.hasNext()) {
            Country uri = iterator.next();
            System.out.println(uri.getCountryName());
        }
	}
}
	