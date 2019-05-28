package study.pattern.event;

import java.util.concurrent.TimeUnit;

public class DoorListener2 implements IDoorEventListener {

    @Override
    public void doorEvent(DoorEvent doorEvent) {
        if (doorEvent.getDoorState() != null && "open".equals(doorEvent.getDoorState())) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " 门2打开，并且打开走廊的灯...");
        } else {
            System.out.println(Thread.currentThread().getName() + " 门2关闭，同时关闭走廊的灯...");
        }
    }

}
