package kea.intuition;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import kea.intuition.controller.IScene;

import java.util.Properties;

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

    public static int determineOS() {
        int os = 0;
        String osString;
        Properties osProperties = System.getProperties();
        osString = osProperties.getProperty("os.name");

        if (osString.contains("windows")) {
            os = 1;
        }

        if (osString.contains("mac")) {
            os = 2;
        }

        if (osString.contains("linux")) {
            os = 0;
        }

        return os;
    }
}