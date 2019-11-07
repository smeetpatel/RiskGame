package main.java;

import javax.swing.*;
import java.awt.*;

/**
 * Responsible for player domination view
 */
public class PlayerDominationView extends JFrame implements Observer{
    private JLabel playerNameLabel;
    private JLabel mapContolledLabel;
    private JLabel armiesLabel;
    private JLabel continentsLabel;
    private JTextField player1Text;
    private JTextField player1MapText;
    private JTextField player1ArmiesText;
    private JTextField player1ContinentsText;
    private JTextField player2Text;
    private JTextField player2MapText;
    private JTextField player2ArmiesText;
    private JTextField player2ContinentsText;
    private JTextField player3Text;
    private JTextField player3MapText;
    private JTextField player3ArmiesText;
    private JTextField player3ContinentsText;
    private JTextField player4Text;
    private JTextField player4MapText;
    private JTextField player4ArmiesText;
    private JTextField player4ContinentsText;
    private JTextField player5Text;
    private JTextField player5MapText;
    private JTextField player5ArmiesText;
    private JTextField player5ContinentsText;
    private JTextField player6Text;
    private JTextField player6MapText;
    private JTextField player6ArmiesText;
    private JTextField player6ContinentsText;

    /**
     * Initialize the components for the player domination view
     */
    public PlayerDominationView() {
        initComponents();
    }

    private void initComponents() {
        playerNameLabel = new JLabel();
        mapContolledLabel = new JLabel();
        armiesLabel = new JLabel();
        continentsLabel = new JLabel();
        player1Text = new JTextField();
        player1MapText = new JTextField();
        player1ArmiesText = new JTextField();
        player1ContinentsText = new JTextField();
        player2Text = new JTextField();
        player2MapText = new JTextField();
        player2ArmiesText = new JTextField();
        player2ContinentsText = new JTextField();
        player3Text = new JTextField();
        player3MapText = new JTextField();
        player3ArmiesText = new JTextField();
        player3ContinentsText = new JTextField();
        player4Text = new JTextField();
        player4MapText = new JTextField();
        player4ArmiesText = new JTextField();
        player4ContinentsText = new JTextField();
        player5Text = new JTextField();
        player5MapText = new JTextField();
        player5ArmiesText = new JTextField();
        player5ContinentsText = new JTextField();
        player6Text = new JTextField();
        player6MapText = new JTextField();
        player6ArmiesText = new JTextField();
        player6ContinentsText = new JTextField();

        setTitle("Player domination view");
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        playerNameLabel.setText("Player");
        contentPane.add(playerNameLabel, new GridBagConstraints(0, 0, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        mapContolledLabel.setText("Map");
        contentPane.add(mapContolledLabel, new GridBagConstraints(7, 0, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        armiesLabel.setText("Armies");
        contentPane.add(armiesLabel, new GridBagConstraints(15, 0, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        continentsLabel.setText("Continents");
        contentPane.add(continentsLabel, new GridBagConstraints(23, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        player1Text.setEditable(false);
        player1Text.setEnabled(false);
        contentPane.add(player1Text, new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        player1MapText.setEditable(false);
        player1MapText.setEnabled(false);
        contentPane.add(player1MapText, new GridBagConstraints(7, 2, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        player1ArmiesText.setEditable(false);
        player1ArmiesText.setEnabled(false);
        contentPane.add(player1ArmiesText, new GridBagConstraints(15, 2, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        player1ContinentsText.setEditable(false);
        player1ContinentsText.setEnabled(false);
        contentPane.add(player1ContinentsText, new GridBagConstraints(23, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        player2Text.setEditable(false);
        player2Text.setEnabled(false);
        contentPane.add(player2Text, new GridBagConstraints(0, 4, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        player2MapText.setEditable(false);
        player2MapText.setEnabled(false);
        contentPane.add(player2MapText, new GridBagConstraints(7, 4, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        player2ArmiesText.setEditable(false);
        player2ArmiesText.setEnabled(false);
        contentPane.add(player2ArmiesText, new GridBagConstraints(15, 4, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        player2ContinentsText.setEditable(false);
        player2ContinentsText.setEnabled(false);
        contentPane.add(player2ContinentsText, new GridBagConstraints(23, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        player3Text.setEditable(false);
        player3Text.setEnabled(false);
        contentPane.add(player3Text, new GridBagConstraints(0, 6, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        player3MapText.setEditable(false);
        player3MapText.setEnabled(false);
        contentPane.add(player3MapText, new GridBagConstraints(7, 6, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        player3ArmiesText.setEditable(false);
        player3ArmiesText.setEnabled(false);
        contentPane.add(player3ArmiesText, new GridBagConstraints(15, 6, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        player3ContinentsText.setEditable(false);
        player3ContinentsText.setEnabled(false);
        contentPane.add(player3ContinentsText, new GridBagConstraints(23, 6, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        player4Text.setEditable(false);
        player4Text.setEnabled(false);
        contentPane.add(player4Text, new GridBagConstraints(0, 8, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        player4MapText.setEditable(false);
        player4MapText.setEnabled(false);
        contentPane.add(player4MapText, new GridBagConstraints(7, 8, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        player4ArmiesText.setEditable(false);
        player4ArmiesText.setEnabled(false);
        contentPane.add(player4ArmiesText, new GridBagConstraints(15, 8, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        player4ContinentsText.setEditable(false);
        player4ContinentsText.setEnabled(false);
        contentPane.add(player4ContinentsText, new GridBagConstraints(23, 8, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        player5Text.setEditable(false);
        player5Text.setEnabled(false);
        contentPane.add(player5Text, new GridBagConstraints(0, 10, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        player5MapText.setEditable(false);
        player5MapText.setEnabled(false);
        contentPane.add(player5MapText, new GridBagConstraints(7, 10, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        player5ArmiesText.setEditable(false);
        player5ArmiesText.setEnabled(false);
        contentPane.add(player5ArmiesText, new GridBagConstraints(15, 10, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        player5ContinentsText.setEditable(false);
        player5ContinentsText.setEnabled(false);
        contentPane.add(player5ContinentsText, new GridBagConstraints(23, 10, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        player6Text.setEditable(false);
        player6Text.setEnabled(false);
        contentPane.add(player6Text, new GridBagConstraints(0, 12, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        player6MapText.setEditable(false);
        player6MapText.setEnabled(false);
        contentPane.add(player6MapText, new GridBagConstraints(7, 12, 2, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        player6ArmiesText.setEditable(false);
        player6ArmiesText.setEnabled(false);
        contentPane.add(player6ArmiesText, new GridBagConstraints(15, 12, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        player6ContinentsText.setEditable(false);
        player6ContinentsText.setEnabled(false);
        contentPane.add(player6ContinentsText, new GridBagConstraints(23, 12, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        pack();
        setLocationRelativeTo(getOwner());
    }

    /**
     * notifies for the player domination view
     * @param o Observable object
     */
    public void update(Observable o){
        Player player = (Player) o;
        if(player.getPlayerName().equals(player1Text.getText()) || player1Text.getText().equals("")){
            player1Text.setText(player.getPlayerName());
            player1MapText.setText(player.getMapControlled()+"%");
            player1ArmiesText.setText(Integer.toString(player.getOwnedArmies()));
            if(player.getOwnedContinents().size()==0){
                player1ContinentsText.setText("-");
            } else {
                String s = "";
                for(Continent c : player.getOwnedContinents().values()){
                    s += c.getContinentName() + ", ";
                }
                player1ContinentsText.setText(s);
            }
        } else if(player.getPlayerName().equals(player2Text.getText())  || player2Text.getText().equals("")){
            player2Text.setText(player.getPlayerName());
            player2MapText.setText(player.getMapControlled()+"%");
            player2ArmiesText.setText(Integer.toString(player.getOwnedArmies()));
            if(player.getOwnedContinents().size()==0){
                player2ContinentsText.setText("-");
            } else {
                String s = "";
                for(Continent c : player.getOwnedContinents().values()){
                    s += c.getContinentName() + ", ";
                }
                player2ContinentsText.setText(s);
            }
        } else if(player.getPlayerName().equals(player3Text.getText())  || player3Text.getText().equals("")){
            player3Text.setText(player.getPlayerName());
            player3MapText.setText(player.getMapControlled()+"%");
            player3ArmiesText.setText(Integer.toString(player.getOwnedArmies()));
            if(player.getOwnedContinents().size()==0){
                player3ContinentsText.setText("-");
            } else {
                String s = "";
                for(Continent c : player.getOwnedContinents().values()){
                    s += c.getContinentName() + ", ";
                }
                player3ContinentsText.setText(s);
            }
        } else if(player.getPlayerName().equals(player4Text.getText())  || player4Text.getText().equals("")){
            player4Text.setText(player.getPlayerName());
            player4MapText.setText(player.getMapControlled()+"%");
            player4ArmiesText.setText(Integer.toString(player.getOwnedArmies()));
            if(player.getOwnedContinents().size()==0){
                player4ContinentsText.setText("-");
            } else {
                String s = "";
                for(Continent c : player.getOwnedContinents().values()){
                    s += c.getContinentName() + ", ";
                }
                player4ContinentsText.setText(s);
            }
        } else if(player.getPlayerName().equals(player5Text.getText())  || player5Text.getText().equals("")){
            player5Text.setText(player.getPlayerName());
            player5MapText.setText(player.getMapControlled()+"%");
            player5ArmiesText.setText(Integer.toString(player.getOwnedArmies()));
            if(player.getOwnedContinents().size()==0){
                player5ContinentsText.setText("-");
            } else {
                String s = "";
                for(Continent c : player.getOwnedContinents().values()){
                    s += c.getContinentName() + ", ";
                }
                player5ContinentsText.setText(s);
            }
        } else if(player.getPlayerName().equals(player6Text.getText())  || player6Text.getText().equals("")){
            player6Text.setText(player.getPlayerName());
            player6MapText.setText(player.getMapControlled()+"%");
            player6ArmiesText.setText(Integer.toString(player.getOwnedArmies()));
            if(player.getOwnedContinents().size()==0){
                player6ContinentsText.setText("-");
            } else {
                String s = "";
                for(Continent c : player.getOwnedContinents().values()){
                    s += c.getContinentName() + ", ";
                }
                player6ContinentsText.setText(s);
            }
        }
    }

    public void update(String message){
        //System.out.println(message);
    }
}
