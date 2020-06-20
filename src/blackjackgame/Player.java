/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackgame;


import java.util.ArrayList;


public class Player {

    protected ArrayList<Card> hand;
    protected ArrayList<Card> secondHand;
    protected int sumOfCards;
    private Result playersFinalResult;
    protected int sumOfCardsAtSecondHand;
    protected PlayerDecision playersDecision;
    protected Result playersFirstHandsResult;
    protected Result playersSecondHandsResult;
    protected String stringPlayersResult;
    private SplitMode splitMode;
    private boolean hasHighAs;


    public int getSumOfCardsAtSecondHand() {
        return sumOfCardsAtSecondHand;
    }


    public Result getPlayersFirstHandsResult() {
        return playersFirstHandsResult;
    }


    public void setPlayersFirstHandsResult(Result playersFirstHandsResult) {
        this.playersFirstHandsResult = playersFirstHandsResult;

        switch (playersFirstHandsResult){
            case BLACKJACK: stringPlayersResult = "BLACKJACK"; break;
            case WIN: stringPlayersResult = "WIN"; break;
            case BUST: stringPlayersResult = "BUST"; break;
            case LOOSE: stringPlayersResult = "LOOSE"; break;
            case PUSH: stringPlayersResult = "PUSH";
        }
    }


    public String getStringPlayersResult(){
        return stringPlayersResult;
    }


    public PlayerDecision getPlayersDecision() {
        return playersDecision;
    }

    public int getHandsSize(){return hand.size();}

    public void setPlayersDecision(PlayerDecision playersDecision) {
        this.playersDecision = playersDecision;
    }

    public Player(){
        hand = new ArrayList<>();
        secondHand = new ArrayList<>();
        sumOfCards = 0;
        playersDecision = PlayerDecision.HIT;
        stringPlayersResult = "";
        hasHighAs = false;
    }

    public int getSumOfCards() {
        return sumOfCards;
    }

    public void addCard(Card card) {
        hand.add(new Card(card.getOrder(), card.getColor()));

        if(card.getOrder() == 14){
            if(sumOfCards + card.getValue() > 21){
                sumOfCards += 1;
            }
            else {
                sumOfCards += card.getValue();
                hasHighAs = true;
            }
        }
        else {
            if ((sumOfCards + card.getValue() > 21) && hasHighAs) {
                sumOfCards -= 10;
                hasHighAs = false;
            }
            sumOfCards += card.getValue();
        }
    }

    public String getCard(int index, boolean fromSecondhand){
        if(!fromSecondhand){
            return hand.get(index).getCard();
        } else return secondHand.get(index).getCard();
    }

    public boolean hasDoubleCard() {
        if(hand.size() == 2){
            return hand.get(0).getValue() == hand.get(1).getValue();
        }
        else return false;
    }

    public void split() {
        if(hasHighAs){
            sumOfCards += 10;
        }
        secondHand.add(hand.remove(hand.size()-1));
        sumOfCardsAtSecondHand = sumOfCards/2;
        sumOfCards = sumOfCards/2;
        splitMode = SplitMode.FIRST_HAND;
    }

    public int getSecondHandSize() {
        return secondHand.size();
    }

    public void setSplitMode(SplitMode splitMode) {
        this.splitMode = splitMode;
    }

    public SplitMode getSplitMode() {
        return splitMode;
    }

    public void swapHands() {
        splitMode = SplitMode.SECOND_HAND;

        int bufferForSumOfCards;
        bufferForSumOfCards = sumOfCards;
        sumOfCards = sumOfCardsAtSecondHand;
        sumOfCardsAtSecondHand = bufferForSumOfCards;

        Result bufferForResult = playersFirstHandsResult;
        playersFirstHandsResult = playersSecondHandsResult;
        playersSecondHandsResult = bufferForResult;

        ArrayList<Card> buffer = new ArrayList<>(secondHand);
        secondHand.removeAll(hand);
        secondHand.addAll(hand);

        hand.removeAll(hand);
        hand.addAll(buffer);

        buffer.removeAll(buffer);
    }
    public Result getPlayersSecondHandsResult() {
        return playersSecondHandsResult;
    }

    public void setPlayersSecondHandsResult(Result playersSecondHandsResult) {
        this.playersSecondHandsResult = playersSecondHandsResult;
    }

    public void setPlayersFinalResult(Result playersFinalResult) {
        this.playersFinalResult = playersFinalResult;
    }

    public Result getPlayersFinalResult() {
        return playersFinalResult;
    }
}
