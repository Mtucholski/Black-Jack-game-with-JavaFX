/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackgame;

/**
 *
 * @author dom
 */
public class Computer extends Player {


    public void areYouHitting() {

        if (sumOfCards >= 16) {
            playersDecision = PlayerDecision.STAND;
        } else {
            playersDecision = PlayerDecision.HIT;
        }

    }
}
