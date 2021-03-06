package main.java.view;

import main.java.model.GameData;
import main.java.model.Observable;
import main.java.model.Observer;
import main.java.model.Phase;

import javax.swing.*;
import java.awt.*;

/**
 * Responsible for phase view.
 */
public class PhaseView extends JFrame implements Observer {

    private JLabel phaseLabel;
    private JTextField phaseText;
    private JLabel playerLabel;
    private JTextField playerText;
    private JLabel logLabel;
    private JTextArea logText;
    Phase currentPhase;

    /**
     * Initialize the components for the phase view
     */
    public PhaseView() {

        initComponents();
    }

    /**
     * Initialize the view
     */
    private void initComponents() {
        phaseLabel = new JLabel();
        phaseText = new JTextField();
        playerLabel = new JLabel();
        playerText = new JTextField();
        logLabel = new JLabel();
        logText = new JTextArea();

        setTitle("Phase view");
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        phaseLabel.setText("Phase:");
        contentPane.add(phaseLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        phaseText.setEditable(false);
        phaseText.setEnabled(false);
        contentPane.add(phaseText, new GridBagConstraints(1, 0, 6, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        playerLabel.setText("Player:");
        contentPane.add(playerLabel, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        playerText.setEditable(false);
        playerText.setEnabled(false);
        contentPane.add(playerText, new GridBagConstraints(1, 2, 6, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        logLabel.setText("Log:");
        contentPane.add(logLabel, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        logText.setEditable(false);
        logText.setEnabled(false);
        contentPane.add(logText, new GridBagConstraints(1, 4, 6, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        pack();
        setLocationRelativeTo(getOwner());
    }

    /**
     * notifies by the phase change
     * @param o Observable object
     */
    public void update(Observable o){
        if(!(o instanceof GameData)){
            return;
        }

        GameData game = (GameData) o;
        if(game.getGamePhase()==Phase.CARDEXCHANGE){
            currentPhase = game.getGamePhase();
            phaseText.setText("Reinforcement");
            playerText.setText(game.getActivePlayer().getPlayerName());
            logText.setText(null);
            logText.insert("Reinforcement armies before card exchange: " + Integer.toString(game.getActivePlayer().getOwnedArmies()) + "\n", 0);
        } else if(game.getGamePhase()==Phase.REINFORCEMENT){
            phaseText.setText("Reinforcement");
            playerText.setText(game.getActivePlayer().getPlayerName());
            logText.insert("Final reinforcement armies gained: " + Integer.toString(game.getActivePlayer().getOwnedArmies()) + "\n", 0);
        } else if(game.getGamePhase()==Phase.ATTACK){
            phaseText.setText("Attack");
            playerText.setText(game.getActivePlayer().getPlayerName());
            logText.setText(null);
        } else if(game.getGamePhase()==Phase.FORTIFICATION){
            phaseText.setText("Fortification");
            playerText.setText(game.getActivePlayer().getPlayerName());
            logText.setText(null);
        }

    }

    /**
     * Update the observers
     * @param message Message to display on receive updates.
     */
    public void update(String message){

        logText.insert(message, 0);
    }
}