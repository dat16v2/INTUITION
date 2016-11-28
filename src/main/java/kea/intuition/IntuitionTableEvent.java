package kea.intuition;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class IntuitionTableEvent extends Event{

    public static final EventType<IntuitionTableEvent> ROOT_EVENT = new EventType<>(Event.ANY, "INTUITION_TABLE_EVENT");
    public static final EventType<IntuitionTableEvent> TABLE_SELECT_EVENT = new EventType<>(ROOT_EVENT, "TABLE_SELECT_EVENT");

    private IntuitionTableEvent intuitionTableEvent;

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    private int businessId;

    public IntuitionTableEvent(@NamedArg("source") IntuitionTableEvent source, @NamedArg("target") EventTarget target, @NamedArg("eventType") EventType<IntuitionTableEvent> eventType) {
        super(source, target, eventType);

        intuitionTableEvent = source;
    }

    public IntuitionTableEvent getIntuitionTableEvent() {
        return intuitionTableEvent;
    }


}