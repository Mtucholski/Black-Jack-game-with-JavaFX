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
 * Created by mtucholski
 * */
public class OnCloseWindow {

    private static boolean isOk;
    private static Stage onCloseWindow;
    private static VBox layout;
    private static Label titleLabel;
    private static Button closeButton;

    /**
     *
     * @return if can be displayed
     */
    public static boolean display(){
        isOk = false;
        setStage();
        return isOk;
    }

    private static void setStage() {
        onCloseWindow = new Stage();
        onCloseWindow.initModality(Modality.APPLICATION_MODAL);
        onCloseWindow.setTitle("Koniec gry!");
        onCloseWindow.setMinWidth(350);
        onCloseWindow.setMinHeight(200);
        onCloseWindow.setResizable(false);

        setComponents();

        setLayout();

        Scene scene = new Scene(layout);

        onCloseWindow.setScene(scene);
        onCloseWindow.showAndWait();

        onCloseWindow.setOnCloseRequest(action-> {
            isOk = true;
            onCloseWindow.close();
        });
    }

    private static void setLayout() {
        layout = new VBox(2);
        layout.getChildren().addAll(titleLabel, closeButton);
        layout.setAlignment(Pos.CENTER);
    }

    private static void setComponents() {
        titleLabel = new Label("Wydales juz wszystkie pieniadze! \nCzas zakonczyc gre...");

        closeButton = new Button("OK");
        closeButton.setOnAction(action->{
            isOk = true;
            onCloseWindow.close();
        });
    }
}
