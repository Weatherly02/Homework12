package edu.dmacc.codedsm.bjhw11;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import static sun.security.krb5.internal.crypto.Nonce.value;

public class BlackJack<sumOfDealerHand> {

    private static String dealerInput;

    public static void main(String[] args) {
        Map<String, List<Integer>> deck = initializeDeck();
        List<Card> playerHand = new ArrayList<>();
        List<Card> dealerHand = new ArrayList<>();
        int playerSum = 0;
        int dealerSum = 0;

        List<Card> playerCards = DeckRandomizer.chooseRandomCards(deck, 2);
        playerHand.addAll(playerCards);
        for (Card card : playerCards) {
            int cardVal = card.value;
            if (card.value > 10) {
                cardVal = 10;
            }
            playerSum = playerSum + cardVal;

            removeCardFromDeck(deck, card);
        }
        System.out.println("Player's hand is: ");
        showHand(playerHand);

        List<Card> dealerCards = DeckRandomizer.chooseRandomCards(deck, 2);
        dealerHand.addAll(dealerCards);
        for (Card card : dealerCards) {
            int cardVal = card.value;
            if (card.value > 10) {
                cardVal = 10;
            }
            dealerSum = dealerSum + cardVal;

            removeCardFromDeck(deck, card);
        }
        List<Card> showOneCardList = new ArrayList<>();
        showOneCardList.add(dealerCards.get(0));
        System.out.println("Dealer's hand is: ");
        showHand(showOneCardList);

        boolean continueDealerGame = true;
        boolean continueGame = true;
        Scanner in = new Scanner(System.in);
        while (continueGame) {
            System.out.printf("Player\'s Hand is %d points.\n", playerSum);

            if (playerSum > 21) {
                System.out.println("BUST");
                continueGame = false;
                continueDealerGame = false;
            }

            if (continueGame == false) break;

            System.out.println("Player enter 1 to Hit, 2 to Stand");
            String Input = in.next();
            if (Input.equals("1")) {
                List<Card> nextPlayerCard = DeckRandomizer.chooseRandomCards(deck, 1);

                int cardVal = nextPlayerCard.get(0).value;
                if (nextPlayerCard.get(0).value > 10) {
                    cardVal = 10;
                }
                playerSum = playerSum + cardVal;

                playerHand.addAll(nextPlayerCard);
                removeCardFromDeck(deck, nextPlayerCard.get(0));
            } else if (Input.equals("2")) {
                continueGame = false;
            } else {
                showErrorMessage();
            }

            System.out.println("Player's hand is: ");
            showHand(playerHand);

        }

        while (continueDealerGame) {
            System.out.printf("Dealer\'s Hand is %d points.\n", dealerSum);

            if (dealerSum < 17) {
                List<Card> nextDealerCard = DeckRandomizer.chooseRandomCards(deck, 1);

                int cardVal = nextDealerCard.get(0).value;
                if (nextDealerCard.get(0).value > 10) {
                    cardVal = 10;
                }
                dealerSum = dealerSum + cardVal;

                dealerHand.addAll(nextDealerCard);
                removeCardFromDeck(deck, nextDealerCard.get(0));
            } else if (dealerSum == 21) {
                System.out.println("Dealer wins blackjack");
                System.exit(0);
            } else {
                continueDealerGame = false;
            }


            System.out.println("Dealer's hand is: ");
            showHand(dealerHand);

        }

        // decide winner
        boolean isPlayerTheWinner = false;
        boolean isTie = false;
        if (playerSum > 21) {
            System.out.println("Player BUST, Dealer WINS");
        } else if (dealerSum > 21) {
            System.out.println("Player WINS, Dealer BUST");
            isPlayerTheWinner = true;
        } else if (dealerSum == playerSum) {
            System.out.println("Player and Dealer Tie");
            isTie = true;
        } else if (dealerSum > playerSum) {
            System.out.println("Dealer WINS");
        } else {
            System.out.println("Player WINS");
            isPlayerTheWinner = true;
        }

        try {
            writeToFile(playerHand, dealerHand, playerSum, dealerSum, isTie, isPlayerTheWinner);
        } catch (Exception ex) {
            System.out.println(1);
        }
    }

    private static Map<String, List<Integer>> initializeDeck() {
        Map<String, List<Integer>> deck = new HashMap<>();
        deck.put("Clubs", createCards());
        deck.put("Diamonds", createCards());
        deck.put("Spades", createCards());
        deck.put("Hearts", createCards());

        return deck;
    }

    private static List<Integer> createCards() {
        List<Integer> cards = new ArrayList<>();

        for (int i = 1; i < 14; i++) {
            cards.add(i);
        }
        return cards;

    }

    private static void removeCardFromDeck(Map<String, List<Integer>> deck, Card card) {
        List<Integer> cardsInSuit = deck.get(card.suit);
        cardsInSuit.remove(card.value);
    }

    private static void showHand(List<Card> playerHand) {
        for (Card card : playerHand) {
            System.out.printf("%s - %d, ", card.suit, card.value);
        }
        System.out.println("\n");
    }

    private static void showErrorMessage() {
        System.out.println("Invalid input");
    }

    public static void writeToFile(List<Card> playerCards, List<Card> dealerCards, Integer playerSumOfHand, Integer dealerSumOfHand, Boolean isTie, Boolean isPlayerTheWinner)
            throws IOException {
        FileWriter fileWriter = new FileWriter("blackjack_log.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        //printWriter.println("test");

        if (isTie) {
            printWriter.printf("It\'s a tie!");
        } else if (isPlayerTheWinner) {
            printWriter.printf("Player\'s Hand was %d points.\n", playerSumOfHand);
        } else {
            printWriter.printf("Dealer\'s Hand was %d points.\n", dealerSumOfHand);
        }

        for (Card card : playerCards) {
            printWriter.printf("%s - %d, ", card.suit, card.value);
        }

        for (Card card : dealerCards) {
            printWriter.printf("%s - %d, ", card.suit, card.value);
        }

        printWriter.close();


    }
}