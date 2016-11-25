package kea.intuition;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import java.util.Properties;
import kea.intuition.controller.IScene;

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
}