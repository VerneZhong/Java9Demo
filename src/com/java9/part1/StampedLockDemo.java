package com.java9.part1;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.StampedLock;

/**
 * @author Mr.zxb
 * @date 2019-04-26 15:51:42
 */
public class StampedLockDemo {

    public static AtomicInteger ri = new AtomicInteger(1);

    private static final StampedLock lock = new StampedLock();

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(10);

    public static void save() {
        long stamp = lock.writeLock();
        try {
            write(stamp);
        } finally {
            lock.unlock(stamp);
        }
    }

    public static void query() {
        long stamp = lock.tryOptimisticRead();
        read();
        if(!lock.validate(stamp)){
            stamp = lock.readLock();
            try {
                read();
            } finally {
                lock.unlock(stamp);
            }
        }
    }

    public static void write(long i) {
        System.out.println("Writing data " + i);
    }

    public static void read() {
        System.out.println("Read data " + ri.getAndIncrement());
    }


    public static void main(String[] args) {

        for (int i = 0; i < 4; i++) {
            EXECUTOR_SERVICE.execute(() -> save());
            EXECUTOR_SERVICE.schedule(() -> query(), 3, TimeUnit.SECONDS);
        }
        EXECUTOR_SERVICE.shutdown();
    }
}
