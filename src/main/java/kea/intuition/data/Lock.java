package kea.intuition.data;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class Lock {
    private boolean locked;
    private Label label;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;

        if (this.locked) {
            label.setText("locked");
        } else {
            label.setText("unlocked");
        }
    }

    public Lock() {
        this.label = new Label();
        setLocked(true);

        setOnClick();
    }

    private void setOnClick() {
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isLocked()) {
                    setLocked(false);
                }else {
                    setLocked(true);
                }
            }
        });
    }

    public Label getLabel() {
        return label;
    }
}