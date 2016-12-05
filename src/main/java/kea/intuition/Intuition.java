package kea.intuition;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kea.intuition.controller.EventScreen;
import kea.intuition.controller.IndexScreen;
import kea.intuition.controller.LoginScreen;
import kea.intuition.data.Database;

public class Intuition {
    private static boolean isLoggedIn;
    private static boolean nIsMaximized;

    public static class Config {
        private final static int MINHEIGHT = 500;
        private final static int MINWIDTH = 800;
        static Os os;
        private static Database db;

        public static Database getDb() {
            return db;
        }

        public static void setDb(Database db) {
            Config.db = db;
        }

        static void setOs(Os os) {
            Config.os = os;
        }

        public static Os getOs() {
            return os;
        }

        public static int getMINHEIGHT() {
            return MINHEIGHT;
        }

        public static int getMINWIDTH() {
            return MINWIDTH;
        }
    }

    public static class Screens {
        private static IndexScreen indexScreen;
        private static EventScreen eventScreen;
        private static LoginScreen loginScreen;

        static IndexScreen getIndexScreen() {
            return indexScreen;
        }

        static void setIndexScreen(IndexScreen indexScreen) {
            Screens.indexScreen = indexScreen;
        }

        static EventScreen getEventScreen() {
            return eventScreen;
        }

        static void setEventScreen(EventScreen eventScreen) {
            Screens.eventScreen = eventScreen;
        }

        static LoginScreen getLoginScreen() {
            return loginScreen;
        }

        static void setLoginScreen(LoginScreen loginScreen) {
            Screens.loginScreen = loginScreen;
        }
    }

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
        stage.setMinHeight(Intuition.Config.getMINHEIGHT());
        stage.setMinWidth(Intuition.Config.getMINWIDTH());

        Config.setDb(new Database());
        Fonts.Init();
        Config.setOs(Tools.determineOS());
        setnIsMaximized(false);

        stage.addEventHandler(IntuitionLoginEvent.LOGIN_EVENT, new EventHandler<IntuitionLoginEvent>() {
            @Override
            public void handle(IntuitionLoginEvent event) {
                System.out.println("Check login info. If authenticated, proceed to main logged in screen");
                Intuition.setIsLoggedIn(true);
                Intuition.Screens.setIndexScreen(new IndexScreen(stage));
                Intuition.Screens.setEventScreen(new EventScreen(stage));
                stage.setScene(Intuition.Screens.getIndexScreen().getScene());
            }
        });

        stage.addEventHandler(IntuitionSceneEvent.SCENE_CHANGE_EVENT, new EventHandler<IntuitionSceneEvent>() {
            @Override
            public void handle(IntuitionSceneEvent event) {
                switch (event.getSceneId()) {
                    case 1:
                        stage.setScene(Intuition.Screens.getIndexScreen().getScene());
                        break;
                    case 2:
                        stage.setScene(Intuition.Screens.getEventScreen().getScene());
                        break;
                }
            }
        });

        Screens.setLoginScreen(new LoginScreen(stage));
        stage.setScene(Screens.getLoginScreen().getScene());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }
}