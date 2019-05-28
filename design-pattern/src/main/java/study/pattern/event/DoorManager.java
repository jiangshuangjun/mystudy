package study.pattern.event;

import java.util.*;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DoorManager {

    private Set<IDoorEventListener> listeners;

    public void addDoorListener(IDoorEventListener doorEventListener) {
        if (listeners == null) {
            listeners = new HashSet();
        }

        listeners.add(doorEventListener);
    }

    public void removeDoorListener(IDoorEventListener doorEventListener) {
        if (listeners == null) {
            return;
        }

        listeners.remove(doorEventListener);
    }

    public void openDoor() {
        if (listeners == null) {
            return;
        }

        DoorEvent doorEvent = new DoorEvent(this, "open");

        System.out.println(Thread.currentThread().getName() + " 触发 openDoor 事件...");

//        new Thread(() -> notifyListeners(doorEvent), "openDoor Thread").start();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new SynchronousQueue<>());
        executor.submit(new DoorThread(doorEvent, this));
        executor.shutdown();
    }

    public void closeDoor() {
        if (listeners == null) {
            return;
        }

        DoorEvent doorEvent = new DoorEvent(this, "close");

        System.out.println(Thread.currentThread().getName() + " 触发 closeDoor 事件...");

//        new Thread(() -> notifyListeners(doorEvent), "closeDoor Thread").start();

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new SynchronousQueue<>());
        executor.submit(new DoorThread(doorEvent, this));
        executor.shutdown();
    }

    public void notifyListeners(DoorEvent doorEvent) {
        if (listeners == null) {
            return;
        }

        for (IDoorEventListener listener : listeners) {
            listener.doorEvent(doorEvent);
        }
    }

    public static void main(String[] args) throws Exception {
        DoorManager doorManager = new DoorManager();

        doorManager.addDoorListener(new DoorListener1());
        doorManager.addDoorListener(new DoorListener2());

        doorManager.openDoor();

        System.out.println(Thread.currentThread().getName() + " 我进来了...");

        doorManager.closeDoor();
    }

}
