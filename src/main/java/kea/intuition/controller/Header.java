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
        closeView.setFitWidth(13);
        closeView.setFitHeight(12);

        closeView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                closeView.setStyle("-fx-opacity: 50%");
            }
        });

        closeView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                closeView.setStyle("-fx-opacity: 100%");
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
        minimizeView.setFitWidth(13);
        minimizeView.setFitHeight(12);

        minimizeView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                minimizeView.setStyle("-fx-opacity: 50%");
            }
        });

        minimizeView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                minimizeView.setStyle("-fx-opacity: 100%");
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
        maximizeView.setFitWidth(13);
        maximizeView.setFitHeight(12);

        maximizeView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                maximizeView.setStyle("-fx-opacity: 50%");
            }
        });

        maximizeView.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                maximizeView.setStyle("-fx-opacity: 100%");
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
                            scene.stage.setWidth(800);
                            scene.stage.setHeight(500);
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
                            Screen screen = Screen.getScreensForRectangle(scene.stage.getX(), scene.stage.getY(), scene.stage.getWidth(), scene.stage.getHeight()).get(0);
                            Rectangle2D bounds = screen.getVisualBounds();
                            scene.stage.setX(bounds.getMinX());
                            scene.stage.setY(bounds.getMinY());
                            scene.stage.setWidth(bounds.getWidth());
                            scene.stage.setHeight(bounds.getHeight());
                            Intuition.setnIsMaximized(true);
                            break;
                    }
                }
            }
        });

        controlButtons.getChildren().addAll(closeView, minimizeView, maximizeView);
        controlButtons.setPadding(new Insets(20, 0, 0, 10));
        header.getChildren().add(controlButtons);

        if (Intuition.isLoggedIn()) {
            // Header logo
            Image logo = new Image("/intuition_logo_header.png");
            ImageView logoView = new ImageView();
            HBox logoPane = new HBox(1);
            logoView.setImage(logo);
            logoView.setFitWidth(180);
            logoView.setFitHeight(35);
            logoPane.getChildren().add(logoView);

            logoPane.setPadding(new Insets(8, 0, 0, 7));

            // Separator between logo and menu
            Image sep = new Image("/header_sep.png");
            ImageView sepView = new ImageView();
            HBox sepPane = new HBox(1);
            sepView.setImage(sep);
            sepView.setFitWidth(3);
            sepView.setFitHeight(24);
            sepPane.getChildren().add(sepView);

            sepPane.setPadding(new Insets(13, 10, 0, 5));

            header.getChildren().addAll(logoPane, sepPane);

            // Menu bar

            HBox menuBarPane = new HBox(0);
            menuBarPane.setPadding(new Insets(0, 0, 0, 0));

            addLabel("Company", 1, menuBarPane, scene);

            addLabel("Events", 2, menuBarPane, scene);

            addLabel("Placeholder", 3, menuBarPane, scene);

            header.getChildren().add(menuBarPane);
        }

        return header;
    }

    static void addLabel(String labelText, int sceneId, Pane payload, IScene scene) {
        Label label = new Label();
        label.setText(labelText);

        if (scene.sceneId == sceneId) {
            label.setStyle("-fx-text-fill: #b182e9; -fx-font-size: 23px; -fx-font-family: 'Open Sans Semibold'; -fx-font-weight: 600; -fx-background-color: #252f3e; -fx-padding: 9 5 7 5;");
        } else {
            label.setStyle("-fx-text-fill: #858c97; -fx-font-size: 23px; -fx-font-family: 'Open Sans Semibold'; -fx-font-weight: 600; -fx-padding: 9 5 7 5;");
            label.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.setStyle("-fx-text-fill: #b182e9; -fx-font-size: 23px; -fx-font-family: 'Open Sans Semibold'; -fx-font-weight: 600; -fx-background-color: #252f3e; -fx-padding: 9 5 7 5;");
                }
            });

            label.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.setStyle("-fx-text-fill: #9c73ce; -fx-font-size: 23px; -fx-font-family: 'Open Sans Semibold'; -fx-font-weight: 600; -fx-background-color: #202735; -fx-padding: 9 5 7 5;");
                }
            });

            label.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.setStyle("-fx-text-fill: #b182e9; -fx-font-size: 23px; -fx-font-family: 'Open Sans Semibold'; -fx-font-weight: 600; -fx-background-color: #252f3e; -fx-padding: 9 5 7 5;");
                }
            });

            label.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    label.setStyle("-fx-text-fill: #858c97; -fx-font-size: 23px; -fx-font-family: 'Open Sans Semibold'; -fx-font-weight: 600; -fx-padding: 9 5 7 5;");
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