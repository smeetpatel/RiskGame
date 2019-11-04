package main.java;

public class AttackView {
    public void canAttack(Player player){
        System.out.format("%25s%25s%35s\n", "Continents", "Country", "Country's neighbors");
        System.out.format("%85s\n", "-------------------------------------------------------------------------------------------");
        boolean printContinentName = true;
        boolean printCountryName = true;
        for(Country country : player.getOwnedCountries().values()){
            for(Country neighbor : country.getNeighbours().values()) {
                if(country.getNumberOfArmies()>1 && !(player.getOwnedCountries().containsKey(neighbor.getCountryName().toLowerCase()))){
                    if(printContinentName && printCountryName){
                        System.out.format("%25s%25s%35s\n", country.getInContinent(), country.getCountryName()+" - "+country.getNumberOfArmies(), neighbor.getCountryName()+" - "+neighbor.getNumberOfArmies());
                        printContinentName = false;
                        printCountryName = false;
                    } else {
                        System.out.format("%25s%25s%35s\n", "", "", neighbor.getCountryName()+" - "+neighbor.getNumberOfArmies());
                    }
                }
            }
            printContinentName = true;
            printCountryName = true;
        }
    }
}
