package Tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import blackjackgame.Card;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dom
 */
public class CardTest {
    
   @Test
   
 public void isCardMade() {
   
     Card card = new Card(5, 'S');

   
     boolean result = card.getCard().equals("5S");

     
   assertTrue(result);

    }
}