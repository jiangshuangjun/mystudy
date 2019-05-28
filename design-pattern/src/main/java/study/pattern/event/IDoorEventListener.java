package study.pattern.event;

import java.util.EventListener;

public interface IDoorEventListener extends EventListener {

    void doorEvent(DoorEvent doorEvent);

}
