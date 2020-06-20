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
public class Colors {
   private final char[] cardSymbol;


    public Colors() {
    
    cardSymbol = new char[]{'S', 'H', 'D', 'C'};
   
 }

    public char getCardSymbol(int index){
     
   return cardSymbol[index];
    }

    public int getNumberOfCardSymbol(){
    
    return cardSymbol.length;
    }

}



