package kea.intuition;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;

public class IntuitionSceneEvent extends Event{

    public static final EventType<IntuitionSceneEvent> ROOT_EVENT = new EventType<>(Event.ANY, "INTUITION_SCENE_EVENT_ROOT_EVENT");
    public static final EventType<IntuitionSceneEvent> SCENE_CHANGE_EVENT = new EventType<>(ROOT_EVENT, "SCENE_CHANGE_EVENT");

    private IntuitionSceneEvent intuitionSceneEvent;
    private int sceneId;

    public IntuitionSceneEvent(IntuitionSceneEvent source, EventTarget target, EventType<IntuitionSceneEvent> eventType) {
        super(source, target, eventType);

        intuitionSceneEvent = source;
    }

    public IntuitionSceneEvent getIntuitionSceneEvent() {
        return intuitionSceneEvent;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public int getSceneId() {
        return sceneId;
    }
}
