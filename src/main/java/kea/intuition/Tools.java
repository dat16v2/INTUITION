package kea.intuition;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
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
}