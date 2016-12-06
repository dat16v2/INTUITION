package kea.intuition;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;
import kea.intuition.controller.IScene;
import kea.intuition.model.Company;

public class Tools {
    public static void addDragToScene(Node primaryNode, IScene scene) {
        // Add ability to drag window without any platform decoration.
        primaryNode.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.xOffset = event.getSceneX();
                scene.yOffset = event.getSceneY();
            }
        });
        primaryNode.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                scene.stage.setX(event.getScreenX() - scene.xOffset);
                scene.stage.setY(event.getScreenY() - scene.yOffset);
            }
        });
    }

    public static Os determineOS() {
        Os os = Os.LINUX;
        String osString;
        Properties osProperties = System.getProperties();
        osString = osProperties.getProperty("os.name");

        if (osString.contains("windows")) {
            os = Os.WINDOWS;
        }

        if (osString.contains("mac")) {
            os = Os.MACOS;
        }

        if (osString.contains("linux")) {
            os = Os.LINUX;
        }

        return os;
    }

    // SHA512 hash
    private static String getHash(String value) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Arrgh, this shouldn't happen at all!!!!!!");
            System.exit(1);
        }

        String encodedHash = Base64.getEncoder().encodeToString(messageDigest.digest(value.getBytes()));

        return encodedHash;
    }

    public static String getCompanyHash(Company company) {
        String payload = String.format("%s;%s;%s;%s", company.getName(), company.getEmail(), company.getPhoneNumber(), company.getId());
        return getHash(payload);
    }

    public static boolean validateCompanyHash(Company company, String hash) {
        return hash.equals(getCompanyHash(company));
    }
}