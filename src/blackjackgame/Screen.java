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
public class Screen {

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}


