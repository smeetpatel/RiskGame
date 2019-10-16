package Test;

import static  org.junit.Assert.*;

import main.java.Player;
import main.java.StartUp;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestGameplayerAdd {

    StartUp stp;
    ArrayList<Player> players;
    String playerName;
    @Before
    public void before(){
        stp = new StartUp();
        players = new ArrayList<Player>();
        playerName = "Tirth";
    }

    @Test
    public void testGameplayerAdd(){
        boolean check = stp.addPlayer(players, playerName);
        System.out.println(players.get(0).getPlayerName());
        assertEquals(players.get(0).getPlayerName(), playerName);
    }
}
