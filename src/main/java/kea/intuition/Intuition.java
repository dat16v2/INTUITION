package kea.intuition;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kea.intuition.controller.EventScreen;
import kea.intuition.controller.IndexScreen;
import kea.intuition.controller.LoginScreen;

public class Intuition {
    public final static int MINHEIGHT = 500;
    public final static int MINWIDTH = 800;
    public static IndexScreen indexScreen;
    public static EventScreen eventScreen;
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
        stage.setMinHeight(MINHEIGHT);
        stage.setMinWidth(MINWIDTH);

        Fonts.Init();
        os = Tools.determineOS();
        nIsMaximized = false;

        loginScreen = new LoginScreen(stage);
        stage.setScene(loginScreen.getScene());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}