package by.epam.task5.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class Ferry implements Runnable{

    private static final Logger logger = LogManager.getLogger();
    private final int CAPACITY_MAX = 20000;
    private final int AREA_MAX = 20000;
    private static final ConcurrentLinkedQueue<Car> carQueue = new ConcurrentLinkedQueue<>();
    private static final AtomicBoolean isInit = new AtomicBoolean(false);
    private static final ReentrantLock lock = new ReentrantLock();
    private final CountDownLatch latch = new CountDownLatch(1);
    private final AtomicLong size = new AtomicLong();
    private final AtomicLong weight = new AtomicLong();
    private static final List<Car> listCars = new ArrayList<>();
    private static Ferry instance;
    private boolean ferryMoving;

    public Ferry() {
    }

    public static Ferry getInstance() {
        if (!isInit.get()) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new Ferry();
                    isInit.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public boolean ferryIsMoving() {
        return ferryMoving;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public void addCar(Car car) {
        latch.countDown();
        if (weight.get() <= CAPACITY_MAX && size.get() <= AREA_MAX) {
            car.setOnBoard(true);
            size.addAndGet(car.getCarSize());
            weight.addAndGet(car.getCarWeight());
            listCars.add(car);
        } else {
            carQueue.add(car);
        }
    }

    @Override
    public void run() {
        while (carQueue.size() < 1) {
            try {
                TimeUnit.SECONDS.sleep(2);
                if (listCars.size() >= 2) {
                    ferryMoving = true;
                    while (!listCars.isEmpty()) {
                        TimeUnit.SECONDS.sleep(2);
                        ferryUnload();
                        ferryMoving = false;
                        TimeUnit.SECONDS.sleep(1);
                    }
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("Queue: " + listCars.size());
    }

    private void ferryUnload() {
        size.set(0);
        weight.set(0);
        listCars.clear();
    }
}
