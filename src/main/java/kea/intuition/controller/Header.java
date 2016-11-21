package kea.intuition.controller;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Header {
    public static HBox GetHeader(Stage stage) {
        // HEADER
        HBox header = new HBox(5);
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
                stage.setIconified(true);
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
                if (stage.isMaximized()) {
                    stage.setMaximized(false);
                } else {
                    stage.setMaximized(true);
                }
            }
        });

        header.getChildren().addAll(closeView, minimizeView, maximizeView);
        header.setPadding(new Insets(10, 0, 0, 10));

        return header;
    }
}
