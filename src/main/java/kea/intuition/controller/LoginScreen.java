package kea.intuition.controller;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
import kea.intuition.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginScreen extends IScene{

    public LoginScreen(Stage stage) {
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
        TextField usernameInput = createTextField("Username");
        TextField passwordInput = createTextField("Password");

        usernameInput.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    User user = new User( usernameInput.getText(), passwordInput.getText() );
                    login( user );
                }
            }
        });

        passwordInput.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    User user = new User( usernameInput.getText(), passwordInput.getText() );
                    login( user );
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
                User user = new User( usernameInput.getText(), passwordInput.getText() );
                login( user );
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

    private TextField createTextField( String promptText ) {
        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);
        textField.setPromptText(promptText);
        textField.getStyleClass().add("input-field");

        return textField;
    }

    private void login( User user ) {
        if( checkUser( user.getUsername(), user.getPassword() ) ) {
            // Fire custom login event
            IntuitionLoginEvent.fireEvent(stage, new IntuitionLoginEvent(null, null, IntuitionLoginEvent.LOGIN_EVENT));
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Wrong username or password!");

            alert.showAndWait();
        }
    }

    private boolean checkUser( String username, String password ) {
        ResultSet rs = Intuition.Config.getDb().select("*", "login", "login_id > 0");
        boolean triedValidUsername = false;

        try {
            while (rs.next()) {
                if( username.equals(rs.getString(2)) ) {
                    triedValidUsername = true;

                    if( password.equals(rs.getString(3)) ) {
                        logAttempt( rs.getInt(1), true );
                        return true;
                    }

                    logAttempt( rs.getInt(1), false );
                }
            }
        } catch (Exception ex) {
        }

        if( triedValidUsername == false ) {
            logAttempt( -1, false );
        }

        return false;
    }

    private void logAttempt( int id, boolean success ) {
        Intuition.Config.getDb().insertLogAttempt(id, success);
    }
}