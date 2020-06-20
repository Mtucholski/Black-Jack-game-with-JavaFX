/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tests;

import blackjackgame.Card;
import blackjackgame.Player;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dom
 */
public class PlayerTest {
    
 @Test
    public void isCardAdded() throws Exception {

        Player player = new Player();
        int orders = 5;
        char cardSymbol = 'S';
        String card = "5S";
        player.addCard(new Card(orders, cardSymbol));

        boolean result = (player.getCard(0, false).equals(card));

        assertTrue(result);
    }
}