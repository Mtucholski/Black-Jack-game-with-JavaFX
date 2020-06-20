package Tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import blackjackgame.Card;
import blackjackgame.Deck;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dom
 */
public class DeckTest {
    
    @Test
    public void isDeckExists() throws Exception {
        Deck deck = new Deck();

        boolean result = deck.getDecksSize() == 52;
        assertTrue(result);
    }

    @Test
    public void isCardDeleted() throws Exception {
        Deck deck = new Deck();
        int existingLargeness = deck.getDecksSize();

        deck.removeACardFromTheDeck(new Card(13, 'C'));
        int largeness = deck.getDecksSize();

        boolean result = largeness == existingLargeness -1;

        assertTrue(result);
    }

    @Test
    public void isCardExists() throws Exception {
        Deck deck = new Deck();

        boolean result = deck.findTheCard(2, 'S');

        assertTrue(result);
    }
}