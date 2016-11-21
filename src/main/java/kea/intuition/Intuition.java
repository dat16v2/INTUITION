package kea.intuition;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kea.intuition.controller.IndexScreen;
import kea.intuition.controller.LoginScreen;

public class Intuition {
    public static IndexScreen indexScreen;
    public static LoginScreen loginScreen;
    public static void Initialize(Stage stage)
    {
        stage.setTitle("INTUITION");
        stage.setMinHeight(500);
        stage.setMinWidth(800);

        loginScreen = new LoginScreen(stage);
        stage.setScene(loginScreen.getScene());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}