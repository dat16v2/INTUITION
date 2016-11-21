package kea.intuition;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kea.intuition.controller.IndexScreen;
import kea.intuition.controller.LoginScreen;

public class Intuition {
    public static IndexScreen indexScreen;
    public static LoginScreen loginScreen;
    public static int os;
    private static boolean isLoggedIn;
    private static boolean nIsMaximized;

    public static boolean isLoggedIn() {
        return isLoggedIn;
    }

    public static void setIsLoggedIn(boolean isLoggedIn) {
        Intuition.isLoggedIn = isLoggedIn;
    }

    public static boolean isnIsMaximized() {
        return nIsMaximized;
    }

    public static void setnIsMaximized(boolean nIsMaximized) {
        Intuition.nIsMaximized = nIsMaximized;
    }

    public static void Initialize(Stage stage)
    {
        stage.setTitle("INTUITION");
        stage.setMinHeight(500);
        stage.setMinWidth(800);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setMaxHeight(bounds.getHeight());
        stage.setMaxWidth(bounds.getWidth());

        os = Tools.determineOS();
        nIsMaximized = false;

        loginScreen = new LoginScreen(stage);
        stage.setScene(loginScreen.getScene());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}