package main.java;

public class RunCommandTester {

	public static void main(String[] args) {
		LoadMap l = new LoadMap();
		GameMap map = l.readMap("maps/ameroki.map");
		RunCommand rc = new RunCommand();
		
		//check validate map
		if(rc.validateMap(map))
			System.out.println("Valid map");
		
		//check load map
		map = rc.loadMap("ameroki.map");
		if(map.getValid())
			System.out.println("Valid map");
		
		
		//check edit map
		map = rc.editMap("ameroki.map");
		if(map==null)
			System.out.println("Null map");
		else
			System.out.println("Map found and loaded");
		
		//save map
		if(rc.saveMap(map, "dummy"))
			System.out.println("Saved file");
		else
			System.out.println("Saving file failed");
						
				
		//check add continent
		System.out.println("#continents: " + map.getContinents().size());
		for(Continent c : map.getContinents().values())
			System.out.println(c.getContinentName());
		if(rc.addContinent(map, "aziol", 5))
			System.out.println("Continent added");
		else
			System.out.println("Continent adding failed");
		System.out.println("#continents: " + map.getContinents().size());
		for(Continent c : map.getContinents().values())
			System.out.println(c.getContinentName());
		
		//check remove continent
		/*System.out.println("Remove continent");
		if(rc.removeContinent(map, "azio"))
			System.out.println("Continent removed");
		else
			System.out.println("Continent removal failed");
		System.out.println("#continents: " + map.getContinents().size());
		for(Continent c : map.getContinents().values())
			System.out.println(c.getContinentName());*/
		
		//check add country
		System.out.println("Add country");
		if(rc.addCountry(map, "Mhysa", "aziol"))
			System.out.println("Country added");
		else
			System.out.println("Country adding failed");
		Continent con = map.getContinents().get("aziol");
		for(Country c : con.getCountries().values())
			System.out.println(c.getCountryName());
		
		//check remove country
		System.out.println("Add country");
		if(rc.removeCountry(map, "Mhysa"))
			System.out.println("Country added");
		else
			System.out.println("Country adding failed");
		Continent cono = map.getContinents().get("aziol");
		for(Country c : cono.getCountries().values())
			System.out.println(c.getCountryName());
		
		//check neighbor
		System.out.println("Add neighbor");
		System.out.println(map.getCountries().containsKey("new_guinia"));
		if(rc.addNeighbor(map, "new_guinia", "eastern_ulstarilia"))
			System.out.println("Neighbor added");
		else
			System.out.println("Neighbor adding failed");
		Country c = map.getCountries().get("new_guinia");
		for(Country n : c.getNeighbours().values())
			System.out.println(n.getCountryName());
		
		//remove neighbor
		System.out.println("Remove Neighbor");
		if(rc.removeNeighbor(map, "new_guinia", "eastern_ulstarilia"))
			System.out.println("Neighbor added");
		else
			System.out.println("Neighbor adding failed");
		c = map.getCountries().get("new_guinia");
		for(Country n : c.getNeighbours().values())
			System.out.println(n.getCountryName());
	}
}