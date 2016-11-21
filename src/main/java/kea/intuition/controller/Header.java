package kea.intuition.controller;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import kea.intuition.Intuition;

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
                    switch (Intuition.os) {
                        case 1:
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
                    switch (Intuition.os) {
                        case 1:
                            scene.stage.setMaximized(true);
                            Intuition.setnIsMaximized(true);
                            break;
                        default:
                            Screen screen = Screen.getPrimary();
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

            Image sep = new Image("/header_sep.png");
            ImageView sepView = new ImageView();
            HBox sepPane = new HBox(1);
            sepView.setImage(sep);
            sepView.setFitWidth(3);
            sepView.setFitHeight(24);
            sepPane.getChildren().add(sepView);

            sepPane.setPadding(new Insets(13, 0, 0, 5));

            header.getChildren().addAll(logoPane, sepPane);
        }

        return header;
    }
}
