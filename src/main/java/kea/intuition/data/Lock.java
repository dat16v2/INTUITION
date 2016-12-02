package kea.intuition.data;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import kea.intuition.controller.CompanySingularDisplay;

public class Lock {
    private boolean locked;
    private Label label;
    private CompanySingularDisplay companySingularDisplay;

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

    public Lock(CompanySingularDisplay companySingularDisplay) {
        this.label = new Label();
        this.companySingularDisplay = companySingularDisplay;
        setLocked(true);

        setOnClick();
    }

    private void setOnClick() {
        label.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (isLocked()) {
                    setLocked(false);
                    companySingularDisplay.setUnlockedLayout();
                }else {
                    setLocked(true);
                    companySingularDisplay.setLockedLayout();
                }
            }
        });
    }

    public Label getLabel() {
        return label;
    }
}