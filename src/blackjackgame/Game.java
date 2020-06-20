/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackgame;

import java.util.Random;

/**
 * @author dom
 */

public class Game {


    public static final String PLAYER_KEY = "gracz";


    public static final String CROUPIER_KEY = "krupier";
    private final Player player;
    private final Computer computer;
    private final Deck deck;


    public Game() {
        player = new Player();
        computer = new Computer();
        deck = new Deck();
    }

    private Card drawACard() {
        Colors colors = new Colors();

        int highDrawingLimitForTheOrder = 13;
        int drawnOrder;
        int highDrawingLimitForColors = colors.getNumberOfCardSymbol();
        int drawnColorsIndex;

        Random random = new Random();

        do {

            drawnOrder = random.nextInt(highDrawingLimitForTheOrder) + 2;

            drawnColorsIndex = random.nextInt(highDrawingLimitForColors);

        } while (!deck.findTheCard(drawnOrder, colors.getCardSymbol(drawnColorsIndex)));
        Card card = new Card(drawnOrder, colors.getCardSymbol(drawnColorsIndex));
        deck.removeACardFromTheDeck(card);

        return card;
    }


    public void startARound() {
        player.setSplitMode(SplitMode.OFF);
        for (int i = 0; i < 2; i++) {
            player.addCard(drawACard());
            computer.addCard(drawACard());
        }
    }

    public String[] getPlayersHand(String who, boolean secondHand) {
        if (who.equals(PLAYER_KEY)) {
            String[] hand;
            if (!secondHand) {
                hand = new String[player.getHandsSize()];

                for (int i = 0; i < hand.length; i++) {
                    hand[i] = player.getCard(i, false);
                }
            } else {
                hand = new String[player.getSecondHandSize()];
                for (int i = 0; i < hand.length; i++) {
                    hand[i] = player.getCard(i, true);
                }
            }
            return hand;
        } else if (who.equals(CROUPIER_KEY)) {
            String[] hand = new String[computer.getHandsSize()];

            for (int i = 0; i < hand.length; i++) {
                hand[i] = computer.getCard(i, false);
            }
            return hand;
        }
        return null;
    }

    public String getOnesResult(String whose) {
        switch (whose) {
            case PLAYER_KEY:
                return player.getStringPlayersResult();
            case CROUPIER_KEY:
                return computer.getStringPlayersResult();
            default:
                return null;
        }
    }


    public boolean checkTheResult(String afterWhoseTurn) {
        if (afterWhoseTurn.equals(PLAYER_KEY)) {
            SplitMode playersMode = player.getSplitMode();
            int playersSumOfCards = player.getSumOfCards();
            if (playersSumOfCards == 21) {
                player.setPlayersFirstHandsResult(Result.BLACKJACK);
                return playersMode != SplitMode.SECOND_HAND;
            } else if (playersSumOfCards > 21) {
                player.setPlayersFirstHandsResult(Result.BUST);
                return playersMode != SplitMode.SECOND_HAND;
            } else {
                player.setPlayersFirstHandsResult(Result.NULL);
                return false;
            }
        } else if (afterWhoseTurn.equals(CROUPIER_KEY)) {
            if (player.getSplitMode() == SplitMode.OFF) {
                return checkTheResultWhenSplitModeOff();
            } else {
                return checkTheResultWhenSplitModeOn();
            }
        }
        return true;
    }

    protected boolean checkTheResultWhenSplitModeOn() {
        int croupiersSumOfCards = computer.getSumOfCards();
        Result playersFirstHandsResult = player.getPlayersFirstHandsResult();
        Result playersSecondHandsResult = player.getPlayersSecondHandsResult();
        if (croupiersSumOfCards == 21) {
            computer.setPlayersFirstHandsResult(Result.BLACKJACK);
            player.setPlayersFirstHandsResult(Result.LOOSE);
            player.setPlayersSecondHandsResult(Result.LOOSE);
        } else if (croupiersSumOfCards > 21) {
            computer.setPlayersFirstHandsResult(Result.BUST);

            if (playersFirstHandsResult == Result.NULL) player.setPlayersFirstHandsResult(Result.WIN);

            if (playersSecondHandsResult == Result.NULL) player.setPlayersSecondHandsResult(Result.WIN);
        } else {

            if (croupiersSumOfCards >= player.getSumOfCards()) {
                player.setPlayersFirstHandsResult(Result.LOOSE);
                if (croupiersSumOfCards >= player.getSumOfCardsAtSecondHand()) {
                    player.setPlayersSecondHandsResult(Result.LOOSE);
                    computer.setPlayersFirstHandsResult(Result.WIN);
                } else {
                    player.setPlayersSecondHandsResult(Result.WIN);
                    computer.setPlayersFirstHandsResult(Result.LOOSE);
                }
            } else {
                if (playersFirstHandsResult != Result.BUST) {
                    player.setPlayersFirstHandsResult(Result.WIN);
                    computer.setPlayersFirstHandsResult(Result.LOOSE);
                } else {
                    player.setPlayersFirstHandsResult(Result.LOOSE);
                    computer.setPlayersFirstHandsResult(Result.WIN);
                }
                if (player.getSumOfCardsAtSecondHand() > croupiersSumOfCards && playersSecondHandsResult != Result.BUST) {
                    player.setPlayersSecondHandsResult(Result.WIN);
                    computer.setPlayersFirstHandsResult(Result.LOOSE);
                } else player.setPlayersSecondHandsResult(Result.LOOSE);
            }
        }

        return true;
    }

    private boolean checkTheResultWhenSplitModeOff() {
        int croupiersSumOfCards = computer.getSumOfCards();
        if (croupiersSumOfCards == 21) {
            computer.setPlayersFirstHandsResult(Result.BLACKJACK);
            return true;
        } else if (croupiersSumOfCards > 21) {
            computer.setPlayersFirstHandsResult(Result.BUST);
            player.setPlayersFirstHandsResult(Result.WIN);
            return true;
        } else {
            if (croupiersSumOfCards > player.getSumOfCards()) {
                computer.setPlayersFirstHandsResult(Result.WIN);
                player.setPlayersFirstHandsResult(Result.LOOSE);
                return true;
            } else if (croupiersSumOfCards == player.getSumOfCards()) {
                computer.setPlayersFirstHandsResult(Result.PUSH);
                player.setPlayersFirstHandsResult(Result.LOOSE);
                return true;
            } else if (player.getSumOfCards() > croupiersSumOfCards) {
                computer.setPlayersFirstHandsResult(Result.LOOSE);
                player.setPlayersFirstHandsResult(Result.WIN);
                return true;
            }
        }
        return false;
    }


    public String whoWon() {
        Result playersResult = player.getPlayersFirstHandsResult();
        if (player.getSplitMode() == SplitMode.OFF) {
            if (playersResult == Result.BLACKJACK || playersResult == Result.WIN) {
                player.setPlayersFinalResult(Result.WIN);
                return PLAYER_KEY;
            } else return CROUPIER_KEY;
        } else {
            Result playersSecondHandResult = player.getPlayersSecondHandsResult();

            if (playersResult == Result.BLACKJACK || playersResult == Result.WIN) {
                if (playersSecondHandResult == Result.BLACKJACK || playersSecondHandResult == Result.WIN) {
                    player.setPlayersFinalResult(Result.DOUBLEWIN);
                } else {
                    player.setPlayersFinalResult(Result.WIN);
                }
                return PLAYER_KEY;
            } else {
                if (playersSecondHandResult == Result.BLACKJACK || playersSecondHandResult == Result.WIN) {
                    player.setPlayersFinalResult(Result.WIN);
                    return PLAYER_KEY;
                } else {
                    player.setPlayersFinalResult(Result.LOOSE);
                    return CROUPIER_KEY;
                }
            }
        }
    }

    public void executePlayersDecision(PlayerDecision playersDecision) {
        player.setPlayersDecision(playersDecision);

        if (playersDecision == PlayerDecision.HIT) {
            player.addCard(drawACard());
        } else if (playersDecision == PlayerDecision.SPLIT) {
            player.split();
        }
    }

    public PlayerDecision croupiersTurn() {
        computer.areYouHitting();

        if (computer.getPlayersDecision() == PlayerDecision.HIT) {
            computer.addCard(drawACard());
        }
        return computer.getPlayersDecision();
    }


    public boolean canPlayerSplit() {
        return player.hasDoubleCard();
    }


    public SplitMode checkPlayersSplitMode() {
        return player.getSplitMode();
    }


    public void swapPlayersHands() {
        player.swapHands();
    }


    public Result getPlayersFinalResult() {
        return player.getPlayersFinalResult();
    }
}


