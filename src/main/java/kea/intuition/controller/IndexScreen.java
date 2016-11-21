package kea.intuition.controller;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kea.intuition.Tools;

public class IndexScreen extends IScene {

    public IndexScreen(Stage stage) {
        layout = new BorderPane();
        scene = new Scene(this.layout, 800, 500);
        layout.setStyle("-fx-background-color: #202936");
        this.stage = stage;

        layout.setTop(Header.GetHeader(stage));
        Label authorizedText = new Label("Authorized.");
        authorizedText.setStyle("-fx-text-fill: #ffffff");
        layout.setCenter(authorizedText);

        Tools.addDragToScene(layout, this);
    }
}