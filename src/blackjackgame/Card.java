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
public class Card {
    private final char color;
    private final int value;
    private final int order;
    String card;
    String symbol;

    /**
     *
     * @return color
     */
    public char getColor() {
        return color;
    }

    /**
     *
     * @return value of the card
     */
    public int getValue() {
        return value;
    }

    /**
     *
     * @return card
     */
    public String getCard() {
        return card;
    }

    /**
     *
     * @return order
     */
    public int getOrder() {
        return order;
    }

    /**
     *
     * @param order marks card
     * @param color color of the card
     */
    public Card(int order, char color){
        this.order = order;
        this.color = color;

        if(order > 1 && order <=10){
            this.value = this.order;
            this.symbol = String.valueOf(this.value);
        }
        else if(order == 14){
            value = 11;
            this.symbol = "A";
        }
        else {
            value = 10;

            if(order == 11){
                this.symbol = "J";
            }
            else if(order == 12){
                this.symbol = "Q";
            }
            else if(order == 13){
                this.symbol = "K";
            }
        }
        this.card = symbol + color;
    }
}


