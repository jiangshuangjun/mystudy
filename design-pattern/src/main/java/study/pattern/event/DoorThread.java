package study.pattern.event;

import java.util.concurrent.TimeUnit;

public class DoorThread implements Runnable {

    private DoorEvent doorEvent;
    private DoorManager doorManager;

    public DoorThread(DoorEvent doorEvent, DoorManager doorManager) {
        this.doorManager = doorManager;
        this.doorEvent = doorEvent;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        doorManager.notifyListeners(doorEvent);
    }
}
