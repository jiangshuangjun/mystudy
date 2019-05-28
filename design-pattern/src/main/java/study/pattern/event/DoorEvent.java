package study.pattern.event;

import lombok.Data;

import java.util.EventObject;

@Data
public class DoorEvent extends EventObject {

    private String doorState;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public DoorEvent(Object source, String doorState) {
        super(source);
        this.doorState = doorState;
    }

}
