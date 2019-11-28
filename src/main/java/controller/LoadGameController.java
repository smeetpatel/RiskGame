package main.java.controller;

import main.java.model.*;
import main.java.view.PhaseView;
import main.java.view.PlayerDominationView;

/**
 * Manages game loading related commands for the game.
 */
public class LoadGameController extends Controller{

    /**
     * Represents the 'Player domination view'
     */
    public PlayerDominationView playerDominationView;

    /**
     * Represents the 'Phase view'
     */
    public PhaseView phaseView;

    public LoadGameController(){
        super();
        game = new GameData();
        gameAction = new GameActions();
    }
    /**
     * Function responsible for parsing user command and calling appropriate method.
     *
     * @param player Player playing the move
     * @param newCommand Command to be interpreted
     * @return appropriate response message
     */
    @Override
    public String parseCommand(Player player, String newCommand){

        String[] data = newCommand.split("\\s+");
        String commandName = data[0];
        String fileName;
        String message = "";

        switch(commandName){
            case "loadgame":
                try{
                    if(data.length == 2){
                        if(isFileNameValid(data[1])) {
                            fileName = data[1];
                            GameDataBuilder gameDataBuilder = gameAction.loadGame(fileName);
                            createGameData(gameDataBuilder);
                            createPlayerDominationView();
                            createPhaseView();
                            message = "Loaded successfully";
                            this.game.getLogger().info("Game loaded successfully.");
                        }
                        //method call for load game and parse this filename as argument
                    }else{
                        message = "Invalid command. Enter file name to save a game.";
                        this.game.getLogger().info(newCommand + " - Invalid command. Enter file name to save a game.");
                    }

                }catch (ArrayIndexOutOfBoundsException e){
                    message = "Invalid Command. It should be 'loadgame filename'";
                    this.game.getLogger().info(newCommand + " - Invalid command. It should be 'loadgame filename'");
                }
                break;
            default:
                message = "Invalid Command. It should be of the form 'loadgame filename'";
                this.game.getLogger().info(newCommand + " - Invalid command. It should be 'loadgame filename'");
                break;
        }
        return message;
    }

    /**
     * Checks if file name is valid or not.
     * @param s File name.
     * @return true if valid file name, else false.
     */
    public boolean isFileNameValid(String s) {
        return s != null && s.matches("^[a-zA-Z.]*$");
    }

    /**
     * Creates a game data object.
     * @param gameDataBuilder Helps build GameData object.
     */
    public void createGameData(GameDataBuilder gameDataBuilder){
        this.game = gameDataBuilder.buildGameData();
    }

    /**
     * Creates player domination view and updates the records of the player domination view.
     */
    public void createPlayerDominationView(){
        playerDominationView = new PlayerDominationView();
        playerDominationView.setVisible(true);
        playerDominationView.setSize(600, 600);
        playerDominationView.setDefaultCloseOperation(3);
        for(Player p : game.getPlayers()){
            p.attach(playerDominationView);
        }
        gameAction.initalizaMapContolValue(game);
    }

    /**
     * Creates phase view and updates the records of the phase view.
     */
    public void createPhaseView(){
        phaseView = new PhaseView();
        phaseView.setVisible(true);
        phaseView.setSize(600, 600);
        phaseView.setDefaultCloseOperation(3);
        for(Player p : game.getPlayers()){
            p.attach(phaseView);
        }
        this.game.attach(phaseView);
        phaseView.update(this.game);
    }

    /**
     * Returns the game state.
     * @return returns the game state as GameData object.
     */
    @Override
    public GameData getGame() {
        return this.game;
    }
}