package main.java.view;

import main.java.model.Card;
import main.java.model.Observable;
import main.java.model.Observer;
import main.java.model.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Represents the card exchange view
 */
public class CardExchangeView extends JFrame implements Observer {

    private JLabel cardsLabel;
    private JLabel card1Label;
    private JTextField card1Text;
    private JLabel card6Label;
    private JTextField card6Text;
    private JLabel card2Label;
    private JTextField card2Text;
    private JLabel card7Label;
    private JTextField card7Text;
    private JLabel card3Label;
    private JTextField card3Text;
    private JLabel card8Label;
    private JTextField card8Text;
    private JLabel card4Label;
    private JTextField card4Text;
    private JLabel card9Label;
    private JTextField card9Text;
    private JLabel card5Label;
    private JTextField card5Text;
    private JLabel card10Label;
    private JTextField card10Text;

    /**
     * default constructor to access the methods of this class
     */
    public CardExchangeView() {
        initComponents();
    }

    /**
     * initialize the components for the view
     */
    private void initComponents() {
        cardsLabel = new JLabel();
        card1Label = new JLabel();
        card1Text = new JTextField();
        card6Label = new JLabel();
        card6Text = new JTextField();
        card2Label = new JLabel();
        card2Text = new JTextField();
        card7Label = new JLabel();
        card7Text = new JTextField();
        card3Label = new JLabel();
        card3Text = new JTextField();
        card8Label = new JLabel();
        card8Text = new JTextField();
        card4Label = new JLabel();
        card4Text = new JTextField();
        card9Label = new JLabel();
        card9Text = new JTextField();
        card5Label = new JLabel();
        card5Text = new JTextField();
        card10Label = new JLabel();
        card10Text = new JTextField();

        setTitle("Card exchange view");
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        cardsLabel.setText("Cards:");
        contentPane.add(cardsLabel, new GridBagConstraints(0, 1, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        card1Label.setText("1.");
        contentPane.add(card1Label, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        card1Text.setEditable(false);
        card1Text.setEnabled(false);
        contentPane.add(card1Text, new GridBagConstraints(1, 3, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        card6Label.setText("6.");
        contentPane.add(card6Label, new GridBagConstraints(12, 3, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        card6Text.setEditable(false);
        card6Text.setEnabled(false);
        contentPane.add(card6Text, new GridBagConstraints(13, 3, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        card2Label.setText("2.");
        contentPane.add(card2Label, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        card2Text.setEditable(false);
        card2Text.setEnabled(false);
        contentPane.add(card2Text, new GridBagConstraints(1, 5, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        card7Label.setText("7.");
        contentPane.add(card7Label, new GridBagConstraints(12, 5, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        card7Text.setEditable(false);
        card7Text.setEnabled(false);
        contentPane.add(card7Text, new GridBagConstraints(13, 5, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        card3Label.setText("3.");
        contentPane.add(card3Label, new GridBagConstraints(0, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        card3Text.setEditable(false);
        card3Text.setEnabled(false);
        contentPane.add(card3Text, new GridBagConstraints(1, 7, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        card8Label.setText("8.");
        contentPane.add(card8Label, new GridBagConstraints(12, 7, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        card8Text.setEditable(false);
        card8Text.setEnabled(false);
        contentPane.add(card8Text, new GridBagConstraints(13, 7, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        card4Label.setText("4.");
        contentPane.add(card4Label, new GridBagConstraints(0, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        card4Text.setEditable(false);
        card4Text.setEnabled(false);
        contentPane.add(card4Text, new GridBagConstraints(1, 9, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        card9Label.setText("9.");
        contentPane.add(card9Label, new GridBagConstraints(12, 9, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 5), 0, 0));

        card9Text.setEditable(false);
        card9Text.setEnabled(false);
        contentPane.add(card9Text, new GridBagConstraints(13, 9, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 5, 0), 0, 0));

        card5Label.setText("5.");
        contentPane.add(card5Label, new GridBagConstraints(0, 11, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        card5Text.setEditable(false);
        card5Text.setEnabled(false);
        contentPane.add(card5Text, new GridBagConstraints(1, 11, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        card10Label.setText("10.");
        contentPane.add(card10Label, new GridBagConstraints(12, 11, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 5), 0, 0));

        card10Text.setEditable(false);
        card10Text.setEnabled(false);
        contentPane.add(card10Text, new GridBagConstraints(13, 11, 3, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        pack();
        setLocationRelativeTo(getOwner());
    }

    /**
     * This method is to show no of cards player owned
     */
    public void update(Observable o){
        Player player = (Player) o;
        int i = 1;
        for(Card c : player.getOwnedCards()){
            if(i==1){
                if(c.getCardCountry()==null){
                    card1Text.setText(c.getCardType());
                } else {
                    card1Text.setText(c.getCardType() + " - " + c.getCardCountry().getCountryName());
                }
            } else if(i==2){
                if(c.getCardCountry()==null){
                    card2Text.setText(c.getCardType());
                } else {
                    card2Text.setText(c.getCardType() + " - " + c.getCardCountry().getCountryName());
                }
            } else if(i==3){
                if(c.getCardCountry()==null){
                    card3Text.setText(c.getCardType());
                } else {
                    card3Text.setText(c.getCardType() + " - " + c.getCardCountry().getCountryName());
                }
            } else if(i==4){
                if(c.getCardCountry()==null){
                    card4Text.setText(c.getCardType());
                } else {
                    card4Text.setText(c.getCardType() + " - " + c.getCardCountry().getCountryName());
                }
            } else if(i==5){
                if(c.getCardCountry()==null){
                    card5Text.setText(c.getCardType());
                } else {
                    card5Text.setText(c.getCardType() + " - " + c.getCardCountry().getCountryName());
                }
            } else if(i==6){
                if(c.getCardCountry()==null){
                    card6Text.setText(c.getCardType());
                } else {
                    card6Text.setText(c.getCardType() + " - " + c.getCardCountry().getCountryName());
                }
            } else if(i==7){
                if(c.getCardCountry()==null){
                    card7Text.setText(c.getCardType());
                } else {
                    card7Text.setText(c.getCardType() + " - " + c.getCardCountry().getCountryName());
                }
            } else if(i==8){
                if(c.getCardCountry()==null){
                    card8Text.setText(c.getCardType());
                } else {
                    card8Text.setText(c.getCardType() + " - " + c.getCardCountry().getCountryName());
                }
            } else if(i==9){
                if(c.getCardCountry()==null){
                    card9Text.setText(c.getCardType());
                } else {
                    card9Text.setText(c.getCardType() + " - " + c.getCardCountry().getCountryName());
                }
            } else if(i==10){
                if(c.getCardCountry()==null){
                    card10Text.setText(c.getCardType());
                } else {
                    card10Text.setText(c.getCardType() + " - " + c.getCardCountry().getCountryName());
                }
            }
            i++;
        }
    }

    public void update(String message){

    }
}