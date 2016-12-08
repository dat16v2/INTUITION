package kea.intuition;

import javafx.event.EventHandler;

public class IntuitionLockEventHandler implements EventHandler<IntuitionLockEvent> {
    @Override
    public void handle(IntuitionLockEvent event) {
        Intuition.Config.setDbLock(event.newLockState());
        IntuitionLockEvent.fireEvent(Intuition.Screens.getIndexScreen().getScene(), new IntuitionLockEvent(null, null, IntuitionLockEvent.LOCK_CHANGED_EVENT, event.newLockState()));
        }
    }