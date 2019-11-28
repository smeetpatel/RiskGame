package main.java.model;

import java.io.*;
import java.util.HashMap;

/**
 * Responsible for loading map from ".map" file.
 * Map is loaded as GameMap object.
 */
public class LoadConquestMap implements ConquestMap{

    /**
     * {@inheritDoc}
     * @param mapName Name of the map file to be read
     */
    public GameMap readMap(String mapName){
        GameMap map = new GameMap();
        HashMap<Integer, Country> listOfCountries = new HashMap<Integer, Country>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(mapName));
            String s;
            while ((s = reader.readLine()) != null) {
                if(s.equals("[Continents]"))
                    reader = readContinents(reader, map);
                if(s.equals("[Territories]"))
                    reader = readTerritories(reader, map);
            }
            reader.close();
            //make sure valid countries exists as neighbors
            for(Country country : map.getCountries().values()){
                for(String neighbor : country.getNeighbours().keySet()){
                    if(country.getNeighbours().get(neighbor.toLowerCase())==null){
                        if(map.getCountries().get(neighbor.toLowerCase())==null){
                            return null;
                        }
                        country.getNeighbours().put(neighbor.toLowerCase(), map.getCountries().get(neighbor.toLowerCase()));
                    }
                }
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("FileNotFoundException");
            System.out.println(e.getMessage());
        }
        catch(IOException e) {
            System.out.println("IOException");
            System.out.println(e.getMessage());
        }
        return map;
    }

    /**
     * {@inheritDoc}
     * @param reader Stream starting from continents section of ".map" file
     * @param map Represents the game map
     * @return BufferedReader stream at the point where it has finished reading continents
     */
    public BufferedReader readContinents(BufferedReader reader, GameMap map){
        String s;
        //boolean continentExists = false;
        try {
            while(!((s = reader.readLine()).equals(""))) {
                String[] continentString = s.split("=");

                //Check if continent already exists in the map
                if(Integer.parseInt(continentString[1])>=0) {
                    map.getContinents().put(continentString[0].toLowerCase(), new Continent(continentString[0], Integer.parseInt(continentString[1])));
                }
                else {
                    //terminate the program if continent already exists
                    System.out.println("Error reading the file.");
                    System.out.println("Negative control value exists.");
                    System.exit(-1);
                }
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return reader;
    }

    /**
     * Reads the countries from the ".map" files.
     * Exits the program if duplicate country or in non-existent country.
     * @param reader Stream starting from countries section of ".map" file
     * @param map Represents the game map
     * @return BufferedReader stream at the point where it has finished reading countries
     */
    public BufferedReader readTerritories(BufferedReader reader, GameMap map){
        String s;
        try {
            while((s = reader.readLine()) != null) {
                if(s.equals("")){
                    continue;
                }
                String[] countryString = s.split(",");
                Country newCountry = new Country(countryString[0], countryString[1], countryString[2], countryString[3]);
                //Country newCountry = new Country(countryString[0], countryString[1], countryString[2], countryString[3], countryString[4], map);
                try {
                    if(newCountry.getInContinent()==null)
                    {
                        System.out.println("Error reading the file.");
                        System.out.println("This continent does not exist.");
                        System.exit(-1);
                    }
                    addToContinentMap(newCountry, map);	//Add country to the appropriate continent in the map. Terminate if duplicate entry.

                    //add neighbors
                    for(int i=4; i<countryString.length; i++){
                        if(map.getCountries().containsKey(countryString[i].toLowerCase())){
                            newCountry.getNeighbours().put(countryString[i].toLowerCase(), map.getCountries().get(countryString[i].toLowerCase()));
                            map.getCountries().get(countryString[i].toLowerCase()).getNeighbours().put(newCountry.getCountryName().toLowerCase(), newCountry);
                        } else {
                            newCountry.getNeighbours().put(countryString[i].toLowerCase(), null);
                        }
                    }
                }
                catch(NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return reader;
    }

    /**
     * Registers this new country as part of its continent.
     * If duplicate country, exits the program throwing error.
     * @param newCountry Country to be registered with the corresponding continent
     * @param map Represents the map of the game
     */
    public void addToContinentMap(Country newCountry, GameMap map){
        if(!MapValidation.doesCountryExist(map, newCountry.getCountryName())) {
            Continent argumentContinent = map.getContinents().get(newCountry.getInContinent().toLowerCase());
            argumentContinent.getCountries().put(newCountry.getCountryName().toLowerCase(), newCountry);
            map.getCountries().put(newCountry.getCountryName().toLowerCase(), newCountry);
        }
        else {
            //terminate the program if same name country exists in the continent
            System.out.println("Error reading the file.");
            System.out.println("Two countries of same name exists in the same continent.");
            System.exit(-1);
        }
    }

    public boolean saveMap(GameMap map, String fileName){
        GameActions gameActions = new GameActions();
        if (gameActions.validateMap(map) == MapValidityStatus.VALIDMAP){
            try{
                BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/maps/" + fileName + ".map"));

                //write preliminary information
                writer.write("[Map]");
                writer.newLine();
                writer.newLine();
                writer.flush();

                //write information about all the continents
                writer.write("[Continents]");
                writer.newLine();
                for (Continent continent : map.getContinents().values()) {
                    writer.write(continent.getContinentName() + "=" + continent.getControlValue());
                    writer.newLine();
                    writer.flush();
                }
                writer.newLine();

                //write information about countries and its neighbors
                writer.write("[Territories]");
                writer.newLine();
                String s;
                for(Country country : map.getCountries().values()){
                    s = country.getCountryName() + "," + country.getxCoOrdinate() + "," + country.getyCoOrdinate() + "," + country.getInContinent();
                    for(Country  neighbor : country.getNeighbours().values()){
                        s += "," + neighbor.getCountryName();
                    }
                    writer.write(s);
                    writer.newLine();
                    writer.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            return true;

        } else {
            return false;
        }
    }
}
