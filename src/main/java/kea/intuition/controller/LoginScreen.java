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
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import kea.intuition.Intuition;
import kea.intuition.IntuitionLoginEvent;
import kea.intuition.Tools;

public class LoginScreen extends IScene{

    public LoginScreen(Stage stage)
    {
        layout = new BorderPane();
        layout.setBackground(new Background(new BackgroundFill(Paint.valueOf("#252f3e"), CornerRadii.EMPTY, Insets.EMPTY)));
        Intuition.setIsLoggedIn(false);
        scene = new Scene(layout, Intuition.Config.getMINWIDTH(), Intuition.Config.getMINHEIGHT(), Paint.valueOf("#252f3e"));
        scene.setFill(Paint.valueOf("#202936"));
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
        logoView.setId("logo");

        logoPane.getChildren().add(logoView);
        center.getChildren().add(logoPane);

        // INPUT TO LOGIN
        TextField usernameInput = new TextField();
        usernameInput.setAlignment(Pos.CENTER);
        usernameInput.setPromptText("Username");
        usernameInput.getStyleClass().add("input-field");

        usernameInput.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    login();
                }
            }
        });

        TextField passwordInput = new TextField();
        passwordInput.setAlignment(Pos.CENTER);
        passwordInput.setPromptText("Password");
        passwordInput.getStyleClass().add("input-field");

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
        loginButton.setId("login-button");

        loginButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loginButton.setId("login-button-entered");
            }
        });

        loginButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loginButton.setId("login-button-pressed");
            }
        });

        loginButton.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loginButton.setId("login-button-released");
            }
        });

        loginButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loginButton.setId("login-button-exited");
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

        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        scene.getStylesheets().add(contextClassLoader.getResource("css/login_screen.css").toExternalForm());
    }

    private void login() {
        // Fire custom login event
        IntuitionLoginEvent.fireEvent(stage, new IntuitionLoginEvent(null, null, IntuitionLoginEvent.LOGIN_EVENT));
    }
}