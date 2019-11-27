package main.java.controller;

import main.java.model.GameActions;
import main.java.model.GameData;
import main.java.model.Phase;
import main.java.model.Player;
import main.java.view.MapView;
import main.java.view.PhaseView;
import main.java.view.PlayerDominationView;

/**
 * Manages start-up related commands for the game.
 */
public class StartUpController extends Controller{

    /**
     * Helps access methods to view map.
     */
    public MapView mapView;

    /**
     * Represents the 'Player domination view'
     */
    public PlayerDominationView playerDominationView;

    /**
     * Represents the 'Phase view'
     */
    public PhaseView phaseView;

    /**
     * Intializes required class variables.
     */
    public StartUpController(GameData game){
        this.game = game;
        gameAction = new GameActions();
        mapView = new MapView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String parseCommand(Player player, String newCommand) {
        String message = "";
        String playerName;
        String playerStrategy;
        String countryName;
        String[] data = newCommand.split("\\s+");
        String commandName = data[0];

        if (game.getGamePhase().equals(Phase.STARTUP)) {
            switch (commandName) {
                case "gameplayer":
                    try {
                        for (int i = 1; i < data.length; i++) {
                            if (data[i].equals("-add")) {
                                if (data[i + 1].matches("[a-zA-Z0-9]+") && (data[i+2].matches("human") ||
                                        data[i+2].matches("aggressive") || data[i+2].matches("benevolent")
                                        || data[i+2].matches("random") || data[i+2].matches("cheater"))){
                                    playerName = data[i + 1];
                                    playerStrategy = data[i+2];
                                    boolean check = gameAction.addPlayer(game.getPlayers(), playerName, playerStrategy);
                                    if (check) {
                                        message = "Player " + playerName +"  with Strategy "+playerStrategy+ " successfully added";
                                        this.game.getLogger().info("Player " + playerName +"  with Strategy "+playerStrategy+ " successfully added");
                                    } else {
                                        if (game.getPlayers().size() == 6) {
                                            message = "Can not add any more player. Maximum 6 players can play.";
                                            this.game.getLogger().info("Can not add any more player. Maximum 6 players can play.");
                                        } else {
                                            message = "Player with same name already exists. Add player with different name.";
                                            this.game.getLogger().info("Player with same name already exists. Add player with different name.");
                                        }
                                    }
                                } else {
                                    message = "Invalid player name or player strategy";
                                    this.game.getLogger().info("Invalid player name or player strategy");
                                }
                            } else if (data[i].equals("-remove")) {
                                if (data[i + 1].matches("[a-zA-Z0-9]+")) {
                                    playerName = data[i + 1];
                                    boolean check = gameAction.removePlayer(game.getPlayers(), playerName);
                                    if (check){
                                        message = "Player " + playerName + " successfully removed";
                                        this.game.getLogger().info("Player " + playerName + " successfully removed.");
                                    }
                                    else {
                                        message = "Player " + playerName + " does not exist";
                                        this.game.getLogger().info("Player " + playerName + " does not exist");
                                    }
                                } else{
                                    message = "Invalid player name";
                                    this.game.getLogger().info("Invalid player name");
                                }
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command - it should be of the form 'gameplayer -add playername -remove playername'";
                        this.game.getLogger().info("Invalid command - it should be of the form 'gameplayer -add playername -remove playername'");
                    } catch (Exception e) {
                        message = "Invalid command - it should be of the form 'gameplayer -add playername -remove playername'";
                        this.game.getLogger().info("Invalid command - it should be of the form 'gameplayer -add playername -remove playername'");
                    }
                    break;

                case "populatecountries":
                    boolean check = gameAction.populateCountries(game, game.getPlayers());
                    if (check) {
                        message = "Countries allocated amongst the players";
                        this.game.getLogger().info("Countries allocated amongst the players");
                        playerDominationView = new PlayerDominationView();
                        playerDominationView.setVisible(true);
                        playerDominationView.setSize(600, 600);
                        playerDominationView.setDefaultCloseOperation(3);
                        attachToPlayers();
                        gameAction.initalizaMapContolValue(game);
                    } else {
                        message = "Minimum two players are required to play the game. Maximum six players.";
                        this.game.getLogger().info("Minimum two players are required to play the game. Maximum six players.");
                    }
                    break;

                case "showmap":
                    mapView.showMap(game.getMap(), game.getPlayers());
                    break;

                default:
                    message = "Invalid command - use gameplayer/populatecountries/placearmy/placeall/showmap commands in start up phase.";
                    this.game.getLogger().info("Invalid command - use gameplayer/populatecountries/placearmy/placeall/showmap commands in start up phase.");
                    break;
            }
            return message;
        } else if(game.getGamePhase().equals(Phase.ARMYALLOCATION)) {
            switch (commandName) {
                case "placearmy":
                    try {
                        if (!(data[1] == "")) {
                            if (this.isAlpha(data[1])) {
                                countryName = data[1];
                                if (!gameAction.placeArmy(player, countryName)) {
                                    if (player.getOwnedArmies() <= 0) {
                                        message = "Invalid command - player does not own armies to assign.";
                                        this.game.getLogger().info("Invalid command - player does not own armies to assign.");
                                    } else {
                                        message = "You don't own this country. Please allocate army in your country.";
                                        this.game.getLogger().info("You don't own this country. Please allocate army in your country.");
                                    }
                                }
                                if(gameAction.isAllArmyPlaced(game)) {
                                    message = "Armies placed successfully";
                                    this.game.getLogger().info("Armies placed successfully");
                                    phaseView = new PhaseView();
                                    phaseView.setVisible(true);
                                    phaseView.setSize(600, 600);
                                    phaseView.setDefaultCloseOperation(3);
                                    for(Player p : game.getPlayers()){
                                        p.attach(phaseView);
                                    }
                                    //player.attach(phaseView);
                                    game.attach(phaseView);
                                }
                            } else {
                                message = "Invalid country name";
                                this.game.getLogger().info("Invalid country name");
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        message = "Invalid command - it should be of the form 'placearmy countryname'";
                        this.game.getLogger().info("Invalid command - it should be of the form 'placearmy countryname'");
                    } catch (Exception e) {
                        message = "Invalid command - it should be of the form 'placearmy countryname'";
                        this.game.getLogger().info("Invalid command - it should be of the form 'placearmy countryname'");
                    }
                    break;

                case "placeall":
                    if (gameAction.placeAll(game)) {
                        message = "Armies placed successfully";
                        this.game.getLogger().info("Armies placed successfully");
                        phaseView = new PhaseView();
                        phaseView.setVisible(true);
                        phaseView.setSize(600, 600);
                        phaseView.setDefaultCloseOperation(3);
                        for(Player p : game.getPlayers()){
                            p.attach(phaseView);
                        }
                        game.attach(phaseView);
                    }

                    break;

                case "showmap":
                    mapView.showMap(game.getMap(), game.getPlayers());
                    break;

                default:
                    message = "Invalid command - use placearmy/placeall/showmap commands to first allocate the assigned armies.";
                    this.game.getLogger().info("Invalid command - use placearmy/placeall/showmap commands to first allocate the assigned armies.");
                    break;
            }
        }
        return message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameData getGame() {
        return game;
    }

    /**
     * Stores the player for the domination view
     */
    private void attachToPlayers() {
        for(Player player : game.getPlayers()){
            player.attach(playerDominationView);
        }
    }

    /**
     * Ensures string matches the defined criteria.
     *
     * @param s input string
     * @return true if valid match, else false
     */
    public boolean isAlpha(String s) {

        return s != null && s.matches("^[a-zA-Z-_]*$");
    }
}