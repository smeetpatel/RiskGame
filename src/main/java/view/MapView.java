package main.java.view;

import main.java.model.Continent;
import main.java.model.Country;
import main.java.model.GameMap;
import main.java.model.Player;

import java.util.ArrayList;

/**
 * Responsible for printing map depending on the phase of the game.
 */
public class MapView {
    /**
     * Prints the continents, countries, and neighbors for each country present in the map.
     * @param map GameMap object representing the map to be shown.
     */
    public void showMap(GameMap map) {
        if(map==null)
            return;
        System.out.format("%25s%25s%35s\n", "Continents", "Country", "Country's neighbors");
        System.out.format("%85s\n", "-------------------------------------------------------------------------------------------");
        boolean printContinentName = true;
        boolean printCountryName = true;
        for(Continent continent : map.getContinents().values()) {
            if(continent.getCountries().size()==0) {
                System.out.format("\n%25s%25s%25s\n", continent.getContinentName()+"-"+continent.getControlValue(), "", "");
            }
            for(Country country : continent.getCountries().values()) {
                if(country.getNeighbours().size()==0) {
                    if(printContinentName && printCountryName) {
                        System.out.format("\n%25s%25s%25s\n", continent.getContinentName()+"-"+continent.getControlValue(), country.getCountryName(), "");
                        printContinentName = false;
                        printCountryName = false;
                    }
                    else if(printCountryName) {
                        System.out.format("\n%25s%25s%25s\n", "", country.getCountryName(), "");
                        printCountryName = false;
                    }
                }
                for(Country neighbor : country.getNeighbours().values()) {
                    if(printContinentName && printCountryName) {
                        System.out.format("\n%25s%25s%25s\n", continent.getContinentName()+"-"+continent.getControlValue(), country.getCountryName(), neighbor.getCountryName());
                        printContinentName = false;
                        printCountryName = false;
                    }
                    else if(printCountryName) {
                        System.out.format("\n%25s%25s%25s\n", "", country.getCountryName(), neighbor.getCountryName());
                        printCountryName = false;
                    }
                    else {
                        System.out.format("%25s%25s%25s\n", "", "", neighbor.getCountryName());
                    }
                }
                printCountryName = true;
            }
            printContinentName = true;
            printCountryName = true;
        }
    }

    /**
     * Shows map in tabular form.
     * @param map Game map
     * @param players List of players in the game
     */
    public void showMap(GameMap map, ArrayList<Player> players) {
        if(map==null)
            return;
        if(players.size()==0 || players.get(0).getOwnedCountries().size()==0) {
            this.showMap(map);
            return;
        }
        System.out.format("%25s%25s%35s%25s%10s\n", "Owner", "Country", "Neighbors", "Continent", "#Armies");
        System.out.format("%85s\n", "---------------------------------------------------------------------------------------------------------------------------");
        boolean printPlayerName = true;
        boolean printContinentName = true;
        boolean printCountryName = true;
        boolean printNumberOfArmies = true;

        for(int i=0; i<players.size(); i++){
            Player p = players.get(i);
            for(Country country : p.getOwnedCountries().values()) {
                for(Country neighbor : country.getNeighbours().values()) {
                    if(printPlayerName && printContinentName && printCountryName) {
                        System.out.format("\n%25s%25s%35s%25s%10d\n", p.getPlayerName(), country.getCountryName(), neighbor.getCountryName(), country.getInContinent(), country.getNumberOfArmies());
                        printPlayerName = false;
                        printContinentName = false;
                        printCountryName = false;
                        printNumberOfArmies = false;
                    }
                    else if(printContinentName && printCountryName && printNumberOfArmies) {
                        System.out.format("\n%25s%25s%35s%25s%10d\n", "", country.getCountryName(), neighbor.getCountryName(), country.getInContinent(), country.getNumberOfArmies());
                        printContinentName = false;
                        printCountryName = false;
                        printNumberOfArmies = false;
                    }
                    else {
                        System.out.format("\n%25s%25s%35s%25s%10s\n", "", "", neighbor.getCountryName(), "", "");
                    }
                }
                printContinentName = true;
                printCountryName = true;
                printNumberOfArmies = true;
            }
            printPlayerName = true;
            printContinentName = true;
            printCountryName = true;
            printNumberOfArmies = true;
        }
    }
}
