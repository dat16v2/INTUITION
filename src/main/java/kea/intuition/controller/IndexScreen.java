package kea.intuition.controller;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import kea.intuition.Intuition;
import kea.intuition.Tools;

// ID: 1
public class IndexScreen extends IScene {

    public IndexScreen(Stage stage) {
        sceneId = 1;
        layout = new BorderPane();
        scene = new Scene(this.layout, Intuition.Config.getMINWIDTH(), Intuition.Config.getMINHEIGHT());
        
        scene.setFill(Paint.valueOf("#202936"));
        layout.setStyle("-fx-background-color: #202936");
        this.stage = stage;

        layout.setTop(Header.GetHeader(this));

        // Body

        Pane bodyPane = new StackPane();
        bodyPane.setStyle("-fx-background-color: #252f3e");

        Label authorizedText = new Label("Authorized.");
        authorizedText.setStyle("-fx-text-fill: #ffffff");

        bodyPane.getChildren().add(authorizedText);

        layout.setCenter(bodyPane);

        Tools.addDragToScene(layout, this);
    }
}