package kea.intuition;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class IntuitionLockEvent extends Event {

    public static final EventType<IntuitionLockEvent> ROOT_EVENT = new EventType<>(Event.ANY, "INTUITION_LOCK_EVENT_ROOT_EVENT");
    public static final EventType<IntuitionLockEvent> LOCK_CHANGED_EVENT = new EventType<>(ROOT_EVENT, "LOCK_CHANGED_EVENT");
    public static final EventType<IntuitionLockEvent> LOCK_CHANGED_ROOT_EVENT = new EventType<>(ROOT_EVENT, "LOCK_CHANGED_ROOT_EVENT");

    private IntuitionLockEvent intuitionLockEvent;
    private boolean newLockState;

    public boolean newLockState() {
        return newLockState;
    }

    public IntuitionLockEvent(@NamedArg("source") IntuitionLockEvent source, @NamedArg("target") EventTarget target, @NamedArg("eventType") EventType<IntuitionLockEvent> eventType, boolean newLockState) {
        super(source, target, eventType);

        intuitionLockEvent = source;
        this.newLockState = newLockState;
    }

    public IntuitionLockEvent getIntuitionLockEvent() {
        return intuitionLockEvent;
    }
}