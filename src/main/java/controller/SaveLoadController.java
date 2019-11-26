package main.java.controller;

public class SaveLoadController {

    public void parseCommand(String newCommand) {

        String[] data = newCommand.split("\\s+");
        String commandName = data[0];

        switch(commandName){
            case "savegame":
                try{
                    if(data.length == 2){
                        String fileName = data[1];
                        //method call for save game and parse this filename as argument
                    }else{
                        System.out.println("Invalid command. enter file name to save a game.");
                    }

                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("Invalid Command, It should be 'savegame filename'");
                }

            case "loadgame":
                try{
                    if(data.length == 2){
                        String fileName = data[1];
                        //method call for load game and parse this filename as argument
                    }else{
                        System.out.println("Invalid command. enter file name to save a game.");
                    }

                }catch (ArrayIndexOutOfBoundsException e){
                    System.out.println("Invalid Command, It should be 'loadgame filename'");
                }

        }
    }
}
