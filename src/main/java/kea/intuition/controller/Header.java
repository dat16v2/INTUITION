package kea.intuition.controller;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import kea.intuition.Fonts;
import kea.intuition.Intuition;
import kea.intuition.IntuitionSceneEvent;

public class Header {
    public static Pane GetHeader(IScene scene) {
        // HEADER
        FlowPane header = new FlowPane();
        HBox controlButtons = new HBox(5);
        Image close = new Image("/close.png");
        ImageView closeView = new ImageView();
        closeView.setImage(close);
        closeView.getStyleClass().add("window-button");

        closeView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                closeView.getStyleClass().clear();
                closeView.getStyleClass().add("window-button-hover");
            }
        });

        closeView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                closeView.getStyleClass().clear();
                closeView.getStyleClass().add("window-button");
            }
        });

        closeView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.exit(1);
            }
        });

        Image minimize = new Image("/minimize.png");
        ImageView minimizeView = new ImageView();
        minimizeView.setImage(minimize);
        minimizeView.getStyleClass().add("window-button");

        minimizeView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                minimizeView.getStyleClass().clear();
                minimizeView.getStyleClass().add("window-button-hover");
            }
        });

        minimizeView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                minimizeView.getStyleClass().clear();
                minimizeView.getStyleClass().add("window-button");
            }
        });

        minimizeView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.stage.setIconified(true);
            }
        });

        Image maximize = new Image("/maximize.png");
        ImageView maximizeView = new ImageView();
        maximizeView.setImage(maximize);
        maximizeView.getStyleClass().add("window-button");

        maximizeView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                maximizeView.getStyleClass().clear();
                maximizeView.getStyleClass().add("window-button-hover");
            }
        });

        maximizeView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                maximizeView.getStyleClass().clear();
                maximizeView.getStyleClass().add("window-button");
            }
        });

        maximizeView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (Intuition.isnIsMaximized()) {
                    switch (Intuition.Config.getOs()) {
                        case WINDOWS:
                            scene.stage.setMaximized(false);
                            Intuition.setnIsMaximized(false);
                            break;
                        default:
                            // Set the default width and height of window and afterwards center it on the current screen.
                            scene.stage.setWidth(Intuition.Config.getMINWIDTH());
                            scene.stage.setHeight(Intuition.Config.getMINHEIGHT());
                            scene.stage.centerOnScreen();
                            Intuition.setnIsMaximized(false);
                            break;
                    }
                } else {
                    switch (Intuition.Config.getOs()) {
                        case WINDOWS:
                            scene.stage.setMaximized(true);
                            Intuition.setnIsMaximized(true);
                            break;
                        default:
                            // Get the screen where the window currently resides
                            Screen screenWithCurrentWindow = Screen.getScreensForRectangle(scene.stage.getX(), scene.stage.getY(), scene.stage.getWidth(), scene.stage.getHeight()).get(0);
                            // Get the actual available space available on the screen, e.g. not where the bar with Windows logo resides nor the OSX top menu bar.
                            Rectangle2D bounds = screenWithCurrentWindow.getVisualBounds();
                            // Set the window to the starting point of the available space.
                            scene.stage.setX(bounds.getMinX());
                            scene.stage.setY(bounds.getMinY());
                            // Resize the window to fill the available space.
                            scene.stage.setWidth(bounds.getWidth());
                            scene.stage.setHeight(bounds.getHeight());
                            Intuition.setnIsMaximized(true);
                            break;
                    }
                }
            }
        });

        controlButtons.getChildren().addAll(closeView, minimizeView, maximizeView);
        controlButtons.setId("control-button");
        header.getChildren().add(controlButtons);

        if (Intuition.isLoggedIn()) {
            // Header logo
            Image logo = new Image("/intuition_logo_header.png");
            ImageView logoView = new ImageView();
            HBox logoPane = new HBox(1);
            logoView.setImage(logo);
            logoView.setId("logo-view");
            logoPane.getChildren().add(logoView);

            logoPane.setId("logo-pane");

            // Separator between logo and menu
            Image sep = new Image("/header_sep.png");
            ImageView sepView = new ImageView();
            HBox sepPane = new HBox(1);
            sepView.setImage(sep);
            sepView.getStyleClass().add("sep-view");
            sepPane.getChildren().add(sepView);

            sepPane.getStyleClass().add("sep-pane");

            header.getChildren().addAll(logoPane, sepPane);

            // Menu bar

            HBox menuBarPane = new HBox(0);
            menuBarPane.setPadding(new Insets(0, 0, 0, 0));

            addLabel("Company", 1, menuBarPane, scene);

            addLabel("Events", 2, menuBarPane, scene);

            addLabel("Placeholder", 3, menuBarPane, scene);

            header.getChildren().add(menuBarPane);
        }

        // contextClassLoader is used to get the working directory of the current thread.
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        header.getStylesheets().add(contextClassLoader.getResource("css/header.css").toExternalForm());

        return header;
    }

    static void addLabel(String labelText, int sceneId, Pane payload, IScene scene) {
        Label label = new Label();
        label.setText(labelText);

        if (scene.sceneId == sceneId) {
            label.getStyleClass().clear();
            label.getStyleClass().add("menu-button-active");
        } else {
            label.getStyleClass().clear();
            label.getStyleClass().add("menu-button");
            label.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.getStyleClass().clear();
                    label.getStyleClass().add("menu-button-entered");
                }
            });

            label.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.getStyleClass().clear();
                    label.getStyleClass().add("menu-button-pressed");
                }
            });

            label.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.getStyleClass().clear();
                    label.getStyleClass().add("menu-button-released");
                }
            });

            label.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.getStyleClass().clear();
                    label.getStyleClass().add("menu-button-exited");
                }
            });

            label.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    IntuitionSceneEvent intuitionSceneEvent = new IntuitionSceneEvent(null, null, IntuitionSceneEvent.SCENE_CHANGE_EVENT);
                    intuitionSceneEvent.setSceneId(sceneId);

                    IntuitionSceneEvent.fireEvent(scene.stage, intuitionSceneEvent);
                }
            });
        }

        label.setFont(Fonts.OpenSansBold);

        payload.getChildren().add(label);
    }
}