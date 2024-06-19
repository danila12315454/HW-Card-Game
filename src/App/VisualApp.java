package App;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import CardHolders.Deck;
import CardHolders.Hand;
import Cards.Card;
import CustomComponents.CardCanvas;
import VisualEngine.CardLoader;

import javax.imageio.ImageIO;

public class VisualApp {
    Deck deck = new Deck();
    List<Hand> hands = deck.getRandomHands();
    Hand hand1 = hands.getFirst();
    Hand hand2 = hands.getLast();
    Settings settings = new Settings();
    Frame frame = new Frame(settings.getTitle());

    CardCanvas cardCanvas1 = new CardCanvas();
    CardCanvas cardCanvas2 = new CardCanvas();

    Label handCount1 = new Label("Player 1: " + this.hand1.getCardAmount() + " cards");
    Label handCount2 = new Label("Player 2: " + this.hand2.getCardAmount() + " cards");

    Label statusLabel = new Label("Player 1's turn");

    List<Card> cardsOnTable1 = new ArrayList<Card>();
    List<Card> cardsOnTable2 = new ArrayList<Card>();

    Button button = new Button("Pull Cards");

    private int getCardsSum(List<Card> cards) {
        int sum = 0;
        for (Card card : cards) {
            sum += card.getValue();
        }
        return sum;
    }

    public void drawCard() {
        try {
            if (this.button.getLabel().equals("Next round")) {
                this.button.setLabel("Pull Cards");
                this.statusLabel.setText("Game in progress...");
                this.cardCanvas1.clearAndSetBack();
                this.cardCanvas2.clearAndSetBack();
                handCount1.setText("Player 1: " + this.hand1.getCardAmount() + " cards");
                handCount2.setText("Player 2: " + this.hand2.getCardAmount() + " cards");
                return;
            }

            // draw new cards
            this.cardsOnTable1.add(this.hand1.drawCard());
            this.cardsOnTable2.add(this.hand2.drawCard());

            this.cardCanvas1.addCardImage(cardsOnTable1.getLast().getImage());
            this.cardCanvas2.addCardImage(cardsOnTable2.getLast().getImage());

            System.out.println(this.getCardsSum(this.cardsOnTable1));
            System.out.println(this.getCardsSum(this.cardsOnTable2));
            if (this.getCardsSum(this.cardsOnTable1) != this.getCardsSum(this.cardsOnTable2)) {
                if (this.getCardsSum(this.cardsOnTable1) > this.getCardsSum(this.cardsOnTable2)) {
                    this.statusLabel.setText("Player 1 wins the round!");
                    for(Card card : this.cardsOnTable1)
                        this.hand1.putCard(card);
                    this.cardsOnTable1.clear();
                    for(Card card : this.cardsOnTable2)
                        this.hand1.putCard(card);
                    this.cardsOnTable2.clear();
                } else {
                    this.statusLabel.setText("Player 2 wins the round!");
                    for(Card card : this.cardsOnTable1)
                        this.hand2.putCard(card);
                    this.cardsOnTable1.clear();
                    for(Card card : this.cardsOnTable2)
                        this.hand2.putCard(card);
                    this.cardsOnTable2.clear();
                }
                this.button.setLabel("Next round");
            } else {
                this.statusLabel.setText("Draw! Each player gets one more card.");
            }


//            System.out.println(card.getCardInfo());
        } catch (Exception e) {
            System.out.println("No more cards in hand");
        }
    }

    public void runGame() {
        // ############################### Main GUI setup ###################################
        try {
            Image icon = ImageIO.read(new File("Images/gameIcon.png")); // Replace with your icon file path
            this.frame.setIconImage(icon);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.cardCanvas1.setBackground(settings.getBgColor());
        this.cardCanvas1.setLocation(50, 150);
        this.cardCanvas1.clearAndSetBack();
        this.frame.add(this.cardCanvas1);

        this.cardCanvas2.setBackground(settings.getBgColor());
        this.cardCanvas2.setLocation(550, 150);
        this.cardCanvas2.clearAndSetBack();
        this.frame.add(this.cardCanvas2);

        this.handCount1.setSize(200, 50);
        this.handCount1.setLocation(50, 100);
        this.handCount1.setFont(new Font("Yu Gothic UI", Font.PLAIN, 20));
        this.handCount1.setBackground(settings.getBgColor());
        this.handCount1.setForeground(settings.getTextColor());
        this.frame.add(this.handCount1);

        this.handCount2.setSize(200, 50);
        this.handCount2.setLocation(550, 100);
        this.handCount2.setFont(new Font("Yu Gothic UI", Font.PLAIN, 20));
        this.handCount2.setBackground(settings.getBgColor());
        this.handCount2.setForeground(settings.getTextColor());
        this.frame.add(this.handCount2);

        this.statusLabel.setSize(400, 50);
        this.statusLabel.setLocation(200, 670);
        this.statusLabel.setFont(new Font("Yu Gothic UI", Font.PLAIN, 20));
        this.statusLabel.setBackground(settings.getBgColor());
        this.statusLabel.setForeground(settings.getTextColor());
        this.frame.add(this.statusLabel);

        this.frame.setSize(this.settings.getWidth(), this.settings.getHeight());
        this.frame.setLayout(null);
        this.frame.setVisible(true);

        this.frame.setBackground(settings.getBgColor());


        this.button.setSize(400, 50);
        this.button.setLocation(170, 730);
        this.button.setFont(new Font("Arial", Font.PLAIN, 30));
        this.button.setBackground(Color.RED);
        this.button.setForeground(Color.BLACK);
        this.button.addActionListener(e -> this.drawCard());


        this.frame.add(button);
        // ############################### End of Main GUI setup ###################################


        // ############################### Main Logic ###################################


        // ############################### End of Main Logic ###################################


        // add window listener to handle window closing event
        this.frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        VisualApp app = new VisualApp();
        app.runGame();
    }
}
