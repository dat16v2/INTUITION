package kea.intuition;

import javafx.event.EventHandler;

public class IntuitionLockEventHandler implements EventHandler<IntuitionLockEvent> {

    @Override
    public void handle(IntuitionLockEvent event) {
        if (event.newLockState() == true) {
            Intuition.Config.setDbLock(true);
        } else if (event.newLockState() == false) {
            Intuition.Config.setDbLock(false);
        }
    }
}