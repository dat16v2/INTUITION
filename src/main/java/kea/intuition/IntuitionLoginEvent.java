package kea.intuition;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import kea.intuition.model.User;

public class IntuitionLoginEvent extends Event {

    public static final EventType<IntuitionLoginEvent> ROOT_EVENT = new EventType<>(Event.ANY, "INTUITION_LOGIN_EVENT_ROOT_EVENT");
    public static final EventType<IntuitionLoginEvent> LOGIN_EVENT = new EventType<>(ROOT_EVENT, "LOGIN_EVENT");

    private IntuitionLoginEvent intuitionLoginEvent;
    private User user;

    public User getUser() {
        return user;
    }


    public IntuitionLoginEvent(@NamedArg("source") IntuitionLoginEvent source, @NamedArg("target") EventTarget target, @NamedArg("eventType") EventType<IntuitionLoginEvent> eventType, User user) {
        super(source, target, eventType);

        this.user = user;
        intuitionLoginEvent = source;
    }

    public IntuitionLoginEvent getIntuitionLoginEvent() {
        return intuitionLoginEvent;
    }
}