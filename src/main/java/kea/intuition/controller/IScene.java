package kea.intuition.controller;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public abstract class IScene {
    Scene scene;
    BorderPane layout;
    public Stage stage;
    public double xOffset = 0;
    public double yOffset = 0;

    public Scene getScene()
    {
        return this.scene;
    }
}
