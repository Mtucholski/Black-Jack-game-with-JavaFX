/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackgame.javaFX;




import blackjackgame.Game;
import blackjackgame.PlayerDecision;
import blackjackgame.Result;
import blackjackgame.SplitMode;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author dom
 */
public class Display extends Application implements EventHandler<ActionEvent>{

    private BorderPane borderPane;
    private Button hitButton;
    private Button standButton;
    private Button splitButton;
    private Button betButton;
    private Game game;
    private Label userLabel;
    private Label croupierLabel;
    private Label userAction;
    private Label croupierAction;
    private Label usersMoney;
    private Label ruleLabel;
    private Text userHand;
    private Text croupierHand;
    private TextField betField;
    private Stage window;
    private int sumOfUsersMoney;
    private int betSum;
    private boolean hasNewRoundStarted;
    private int wonMoney;
    private Text secondUserHand;
    private Label secondHandLabel;
    private Label currentBetLabel;
    private Button doubleDownButton;


    public static void main(String[] args){
    launch(args);
}

    /**
     *
     * @param primaryStage money for player on the begin
     */
    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        sumOfUsersMoney = 50;

        setStage();

        waitForBetToStart();
    }

    private void updateUsersMoney() {
        usersMoney.setText(String.format("Player money: %s $", sumOfUsersMoney));
    }

    private void updateCurrentBet(){ currentBetLabel.setText(String.format("bet: %s $", betSum));}

    /**
     * method which starts new round
     */
    public void startNewRound() {
        setButtonsAsDisabled(false);
        splitButton.setDisable(true);

        if(sumOfUsersMoney < betSum){
            doubleDownButton.setDisable(true);
        }

        game = new Game();
        game.startARound();

        showPlayersHand(game.getPlayersHand(Game.PLAYER_KEY, false), false);

        showCroupiersHand(game.getPlayersHand(Game.CROUPIER_KEY, false), false);

        if(game.checkTheResult(Game.PLAYER_KEY)){
            if(game.whoWon().equals(Game.PLAYER_KEY)){
                wonMoney = (betSum * 5)/2;
                sumOfUsersMoney += wonMoney;
                updateUsersMoney();
                finishTheRound(Game.PLAYER_KEY, wonMoney);
            }
        }else{
            if(game.canPlayerSplit()){
                if(sumOfUsersMoney>=betSum) {
                    splitButton.setDisable(false);
                }
            }
        }
    }

    private void setButtonsAsDisabled(boolean decision) {
        hitButton.setDisable(decision);
        standButton.setDisable(decision);
        splitButton.setDisable(decision);
        doubleDownButton.setDisable(decision);
    }

    private void showCroupiersHand(String[] croupiersHand, boolean isGameOver) {
        StringBuilder text = new StringBuilder();
        int i;

        if(isGameOver){
            i = 0;
        } else i = 1;

        for (; i < croupiersHand.length; i++) {
            text.append(croupiersHand[i]);
            text.append("\n");
        }
        croupierHand.setText(text.toString());
    }

    private void showPlayersHand(String[] playersHand, boolean secondHand) {
        StringBuilder text = new StringBuilder();
        for (String playersHand1 : playersHand) {
            text.append(playersHand1);
            text.append("\n");
        }
        if(!secondHand) {
            userHand.setText(text.toString());
        }else secondUserHand.setText(text.toString());
    }

    private void setStage() {
        setComponents();

        setBoardPane();

        Scene scene = new Scene(borderPane, 500, 400);
        window.setTitle("Blackjack");
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

    private void setBoardPane() {
        VBox usersSide = new VBox(5);
        usersSide.setPadding(new Insets(30,0,0,60));
        usersSide.getChildren().addAll(userAction, userLabel, userHand, secondHandLabel, secondUserHand);

        VBox croupiersSide = new VBox(4);
        croupiersSide.setPadding(new Insets(30, 60,0,0));
        croupiersSide.getChildren().addAll(croupierAction, croupierLabel, croupierHand);

        VBox buttons = new VBox(3);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(hitButton, standButton, splitButton, doubleDownButton);

        HBox betLayout = new HBox(3);
        betLayout.setAlignment(Pos.CENTER);
        betLayout.setPadding(new Insets(0,0,30,0));
        betLayout.getChildren().addAll(currentBetLabel, usersMoney, betField, betButton);

        HBox ruleLayout = new HBox(1);
        ruleLayout.setAlignment(Pos.CENTER);
        ruleLayout.setPadding(new Insets(20,0,0,0));
        ruleLayout.getChildren().add(ruleLabel);

        borderPane = new BorderPane();
        borderPane.setLeft(usersSide);
        borderPane.setRight(croupiersSide);
        borderPane.setCenter(buttons);
        borderPane.setBottom(betLayout);
        borderPane.setTop(ruleLayout);
    }

    private void setComponents() {
        hitButton = new Button("HIT");
        hitButton.setOnAction(this);

        standButton = new Button("STAND");
        standButton.setOnAction(this);

        splitButton = new Button("SPLIT");
        splitButton.setOnAction(this);

        doubleDownButton = new Button("DOUBLE DOWN");
        doubleDownButton.setOnAction(this);

        betButton = new Button("BET");
        betButton.setOnAction(this);

        userLabel = new Label("Player:");
        userAction = new Label();
        userHand = new Text();
        secondUserHand = new Text();
        secondHandLabel = new Label("secind hand:");


        croupierLabel = new Label("Computer:");
        croupierAction = new Label();
        croupierHand = new Text();

        currentBetLabel = new Label(String.format("bet: %s $", betSum));
        usersMoney = new Label(String.format("Player's money: %s $", sumOfUsersMoney));
        usersMoney.setPadding(new Insets(0,25,0,50));
        betField = new TextField();
        betField.setMaxWidth(50);

        ruleLabel = new Label("The lowest bet is: 1$. \nYou must pay money if you want play");
    }

    /**
     *
     * @param event implement action event
     */
    @Override
    public void handle(ActionEvent event) {
        if(event.getSource() == hitButton){
            hitButtonClicked();
        }
        else if(event.getSource() == standButton){
            standButtonClicked();
        }
        else if(event.getSource() == splitButton){
            splitButtonClicked();
        }
        else if(event.getSource() == betButton){
            betButtonClicked();
        }
        else if(event.getSource() == doubleDownButton){
            doubleDownButtonClicked();
        }
    }

    private void doubleDownButtonClicked() {
        sumOfUsersMoney -=betSum;
        betSum *= 2;
        updateCurrentBet();
        updateUsersMoney();

        userAction.setText("Doubled Down!");

        game.executePlayersDecision(PlayerDecision.HIT);

        showPlayersHand(game.getPlayersHand(Game.PLAYER_KEY, false), false);

        croupiersTurn();
        game.checkTheResult(Game.CROUPIER_KEY);
        sumUpAndFinishRound();
    }

    private void splitButtonClicked() {
        game.executePlayersDecision(PlayerDecision.SPLIT);
        showPlayersHand(game.getPlayersHand(Game.PLAYER_KEY, false), false);
        secondHandLabel.setVisible(true);
        showPlayersHand(game.getPlayersHand(Game.PLAYER_KEY, true), true);
        splitButton.setDisable(true);
        sumOfUsersMoney -= betSum;
        updateUsersMoney();
    }

    private void betButtonClicked() {
        String text = betField.getText();
        try {

            int newBet = Integer.parseInt(text);
            if (newBet <= sumOfUsersMoney) {
                if (newBet >= 1) {
                    sumOfUsersMoney -= newBet;
                    updateUsersMoney();
                    betSum += newBet;
                    if (!hasNewRoundStarted) {
                        hasNewRoundStarted = true;
                        hideRuleLabel();
                        updateCurrentBet();
                        betButton.setDisable(true);
                        startNewRound();
                    }
                }
            }
        }
        catch (NumberFormatException e){

            System.out.println(e.getCause() + ":" + "" + e.getMessage());
        }
    }

    private void hideRuleLabel() {
        ruleLabel.setVisible(false);
    }

    private void standButtonClicked() {
        userAction.setText("STAND!");
        userAction.setTextFill(Color.RED);

        game.executePlayersDecision(PlayerDecision.STAND);
        if(game.checkTheResult(Game.PLAYER_KEY)){
            if(game.checkPlayersSplitMode() != SplitMode.FIRST_HAND) {
                sumUpAndFinishRound();
            }
        }else {
            if(game.checkPlayersSplitMode() == SplitMode.FIRST_HAND){
                swapPlayersHands();
            }else {
                croupiersTurn();
                game.checkTheResult(Game.CROUPIER_KEY);
                sumUpAndFinishRound();
            }
        }
    }

    private void croupiersTurn() {
        PlayerDecision croupiersDecision;

        do {
            croupiersDecision = game.croupiersTurn();
            if (croupiersDecision == PlayerDecision.HIT) {
                croupierAction.setText("HIT!");
                croupierAction.setTextFill(Color.GREEN);
            } else {
                croupierAction.setText("STAND!");
                croupierAction.setTextFill(Color.RED);
            }
        }while(croupiersDecision != PlayerDecision.STAND);
    }

    private void finishTheRound(String whoWon, int howMany) {
        showCroupiersHand(game.getPlayersHand(Game.CROUPIER_KEY, false), true);
        showTheWindowWithScores(whoWon, howMany);
    }

    private void hitButtonClicked() {
        userAction.setText("HIT!");
        userAction.setTextFill(Color.GREEN);

        splitButton.setDisable(true);
        doubleDownButton.setDisable(true);

        game.executePlayersDecision(PlayerDecision.HIT);

        showPlayersHand(game.getPlayersHand(Game.PLAYER_KEY, false), false);

            if (game.checkTheResult(Game.PLAYER_KEY)) {
                if(game.checkPlayersSplitMode() == SplitMode.OFF) {
                    sumUpAndFinishRound();
                }else if(game.checkPlayersSplitMode() == SplitMode.FIRST_HAND){
                    swapPlayersHands();
                }
            }

    }

    private void swapPlayersHands() {
        game.swapPlayersHands();
        showPlayersHand(game.getPlayersHand(Game.PLAYER_KEY, false), false);
        showPlayersHand(game.getPlayersHand(Game.PLAYER_KEY, true), true);
        userAction.setText("SWAP HANDS!");
    }

    private void sumUpAndFinishRound() {
        wonMoney = 2*betSum;
        String whoWon = game.whoWon();
        if(whoWon.equals(Game.PLAYER_KEY)){
            if(game.getPlayersFinalResult() == Result.DOUBLEWIN){
                wonMoney *=2;
            }
            sumOfUsersMoney += wonMoney;
            updateUsersMoney();
        }else{
            if(game.checkPlayersSplitMode() == SplitMode.SECOND_HAND){
                wonMoney *=2;
            }
        }
        finishTheRound(whoWon, wonMoney);
    }

    private void showTheWindowWithScores(String whoWon, int howMany) {
        String result = "";
        if(game.checkPlayersSplitMode() == SplitMode.OFF) {
            if (whoWon.equals(Game.PLAYER_KEY)) {
                result = game.getOnesResult(Game.PLAYER_KEY);
            } else {
                result = game.getOnesResult(Game.CROUPIER_KEY);
            }
        }
        if(ResultWindow.display( howMany, result)){
            waitForBetToStart();
        }
    }

    private void waitForBetToStart() {
        betButton.setDisable(false);

        checkIfPlayerHasMoney();

        betSum = 0;
        wonMoney = 0;
        secondHandLabel.setVisible(false);

        userAction.setText("");
        croupierAction.setText("");

        setButtonsAsDisabled(true);

        ruleLabel.setVisible(true);

        resetPlayersHands();
        updateCurrentBet();

        hasNewRoundStarted = false;
    }

    private void checkIfPlayerHasMoney() {
        if(sumOfUsersMoney <= 0){
            if(OnCloseWindow.display()){
                Platform.exit();
            }
        }
    }

    private void resetPlayersHands() {
        userHand.setText("");
        secondUserHand.setText("");
        croupierHand.setText("");
    }
}
