package kea.intuition.controller;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

public class LoginScreen {
    private Scene scene;
    private BorderPane layout;
    private Stage stage;
    private static double xOffset = 0;
    private static double yOffset = 0;

    public Scene getScene() {
        return scene;
    }

    public LoginScreen(Stage stage)
    {
        layout = new BorderPane();
        scene = new Scene(this.layout, 800, 500);
        layout.setStyle("-fx-background-color: #202936");
        this.stage = stage;

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


        // BODY
        VBox center = new VBox(20);
        center.setPadding(new Insets(60, 0, 0, 0));
        StackPane logoPane = new StackPane();
        StackPane loginUsernamePane = new StackPane();
        StackPane loginPasswordPane = new StackPane();
        StackPane loginButtonPane = new StackPane();

        // LOGO STUFF
        Image logo = new Image("/intuition_logo.png");
        ImageView logoView = new ImageView();
        logoView.setImage(logo);
        logoView.setFitWidth(484);
        logoView.setFitHeight(94);

        logoPane.getChildren().add(logoView);
        center.getChildren().add(logoPane);

        // INPUT TO LOGIN
        TextField usernameInput = new TextField();
        usernameInput.setMaxWidth(250);
        usernameInput.setMinHeight(30);
        usernameInput.setAlignment(Pos.CENTER);
        usernameInput.setPromptText("Username");
        usernameInput.setStyle("-fx-background-color: #232d3b; -fx-text-fill: #3e4f66");

        TextField passwordInput = new TextField();
        passwordInput.setMaxWidth(250);
        passwordInput.setMinHeight(30);
        passwordInput.setAlignment(Pos.CENTER);
        passwordInput.setPromptText("Password");
        passwordInput.setStyle("-fx-background-color: #232d3b; -fx-text-fill: #3e4f66");

        Button loginButton = new Button();
        loginButton.setText("Login");
        loginButton.setStyle("-fx-background-color: #2d3a4c; -fx-text-fill: #ffffff");

        loginButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loginButton.setStyle("-fx-background-color: #3b4e66; -fx-text-fill: #ffffff");
            }
        });

        loginButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loginButton.setStyle("-fx-background-color: #1e2632; -fx-text-fill: #ffffff");
            }
        });

        loginButton.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loginButton.setStyle("-fx-background-color: #3b4e66; -fx-text-fill: #ffffff");
            }
        });

        loginButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loginButton.setStyle("-fx-background-color: #2d3a4c; -fx-text-fill: #ffffff");
            }
        });

        loginButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Check login info. If authenticated, proceed to main logged in screen");
            }
        });

        loginUsernamePane.getChildren().add(usernameInput);
        loginPasswordPane.getChildren().add(passwordInput);
        loginButtonPane.getChildren().add(loginButton);
        center.getChildren().addAll(loginUsernamePane, loginPasswordPane, loginButtonPane);

        layout.setCenter(center);
        layout.setTop(header);

        // Add ability to drag window without any platform decoration.
        layout.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        layout.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }
}