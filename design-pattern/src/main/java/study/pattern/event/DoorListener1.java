package study.pattern.event;

import java.util.concurrent.TimeUnit;

public class DoorListener1 implements IDoorEventListener {


    @Override
    public void doorEvent(DoorEvent doorEvent) {
        if (doorEvent.getDoorState() != null && "open".equals(doorEvent.getDoorState())) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " 门1打开...");
        } else {
            System.out.println(Thread.currentThread().getName() + " 门1关闭...");
        }
    }

}
