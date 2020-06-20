/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackgame;

import java.util.ArrayList;

/**
 *
 * @author dom
 */
public class Deck {

    private final ArrayList<Card> deck;

    public Deck() {

        Colors colors = new Colors();

        deck = new ArrayList<>();

        for (int i = 0; i < colors.getNumberOfCardSymbol(); i++) {

            for (int j = 2; j <= 14; j++) {

                deck.add(new Card(j, colors.getCardSymbol(i)));

            }
        }
    }


    public int getDecksSize() {
        return deck.size();
    }


    public void removeACardFromTheDeck(Card card) {

        deck.removeIf(t -> t.getCard().equals(card.getCard()));

    }


    public boolean findTheCard(int order, char color) {

        return deck.stream().anyMatch((card) -> (card.getOrder() == order && card.getColor() == color));
    }
}
