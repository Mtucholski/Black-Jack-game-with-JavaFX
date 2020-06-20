/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackjackgame.javaFX;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author dom
 */
public class ResultWindow {

    private static Stage resultWindow;
    private static Label titleLabel;
    private static Label whoWonLabel;
    private static Button tryAgainButton;
    private static VBox layout;
    static boolean wannaTryAgain;
    private static Label resultLabel;


    public static boolean display(int howMany, String result){
        wannaTryAgain = false;
        setStage( howMany, result);
        return wannaTryAgain;
    }

    private static void setComponents(int howMany, String result) {
        titleLabel = new Label();
        titleLabel.setText("Game over!");

        whoWonLabel = new Label();
        whoWonLabel.setText("You won!" +""+ howMany);

        resultLabel = new Label();
        if(result.equals("BLACKJACK") || result.equals("PUSH")) {
            resultLabel.setText(result);
        }

        tryAgainButton = new Button("Play again");
        tryAgainButton.setOnAction(action->{
            wannaTryAgain = true;
            resultWindow.close();
        });
    }

    private static void setStage(int howMany, String result) {
        resultWindow = new Stage();
        resultWindow.initModality(Modality.APPLICATION_MODAL);
        resultWindow.setTitle("Koniec gry!");
        resultWindow.setMinWidth(350);
        resultWindow.setMinHeight(200);
        resultWindow.setResizable(false);
        resultWindow.setOnCloseRequest(action-> wannaTryAgain = true);

        setComponents(howMany, result);

        setLayout();

        Scene scene = new Scene(layout);

        resultWindow.setScene(scene);
        resultWindow.showAndWait();
    }

    private static void setLayout() {
        layout = new VBox(4);
        layout.getChildren().addAll(titleLabel, whoWonLabel, resultLabel, tryAgainButton);
        layout.setAlignment(Pos.CENTER);
    }
}


