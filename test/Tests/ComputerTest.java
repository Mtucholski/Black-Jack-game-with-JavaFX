package Tests;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import blackjackgame.Card;
import blackjackgame.Computer;
import blackjackgame.PlayerDecision;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dom
 */
public class ComputerTest {
    
      @Test
    public void blockCardtTaking() throws Exception {
        Computer computer = new Computer();
        computer.addCard(new Card(10, 'C'));
        computer.addCard(new Card(7, 'H'));

        boolean result = computer.getPlayersDecision() == PlayerDecision.STAND;

        assertFalse(result);

    }
}