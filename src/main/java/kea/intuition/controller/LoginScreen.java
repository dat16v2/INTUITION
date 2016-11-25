package kea.intuition.controller;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import kea.intuition.Intuition;
import kea.intuition.IntuitionLoginEvent;
import kea.intuition.Tools;

public class LoginScreen extends IScene{

    public LoginScreen(Stage stage)
    {
        layout = new BorderPane();
        Intuition.setIsLoggedIn(false);
        scene = new Scene(layout, Intuition.Config.getMINWIDTH(), Intuition.Config.getMINHEIGHT());
        layout.setStyle("-fx-background-color: #202936");
        this.stage = stage;

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

        usernameInput.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    login();
                }
            }
        });

        TextField passwordInput = new TextField();
        passwordInput.setMaxWidth(250);
        passwordInput.setMinHeight(30);
        passwordInput.setAlignment(Pos.CENTER);
        passwordInput.setPromptText("Password");
        passwordInput.setStyle("-fx-background-color: #232d3b; -fx-text-fill: #3e4f66");

        passwordInput.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    login();
                }
            }
        });

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
                login();
            }
        });

        loginUsernamePane.getChildren().add(usernameInput);
        loginPasswordPane.getChildren().add(passwordInput);
        loginButtonPane.getChildren().add(loginButton);
        center.getChildren().addAll(loginUsernamePane, loginPasswordPane, loginButtonPane);

        layout.setCenter(center);
        layout.setTop(Header.GetHeader(this));

        // Add ability to drag window without any platform decoration.
        Tools.addDragToScene(layout, this);
    }

    private void login() {
        // Fire custom login event
        IntuitionLoginEvent.fireEvent(stage, new IntuitionLoginEvent(null, null, IntuitionLoginEvent.LOGIN_EVENT));
    }
}