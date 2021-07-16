package by.training.multithreadtunnel.entity;

import by.training.multithreadtunnel.util.IdGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tunnel {

    private static Logger logger = LogManager.getLogger();

    private final long tunnelId;
    private final Queue<Train> trains;
    private Direction direction;

    private final Lock lock = new ReentrantLock();

    public Tunnel( Direction direction) {
        this.tunnelId = IdGenerator.generateId();
        this.trains = new ArrayDeque<>();
        this.direction = direction;
    }

    public int getCountTrain(){
        try {
            lock.lock();
            return trains.size();
        }finally {
            lock.unlock();
        }
    }


    public long getTunnelId() {
        return tunnelId;
    }

    public Queue<Train> getTrains() {
        return trains;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void process(Train train){

        try {
            lock.lock();
            trains.offer(train);
            logger.info("train № " + train.getTrainNumber() + " entered the tunnel №:" + tunnelId);
        } finally {
            lock.unlock();
        }

        /* the first train rides through the tunnel and in the same time another train can enter in tunnel
        if all the code is included in try catch and locked, another train won't be able to enter the tunnel until
        the first one leaves the tunnel*/

        try{
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            logger.error("Caught an exception: ", e);
            Thread.currentThread().interrupt();
        }

        try {
            lock.lock();
            Train outTrain = trains.poll();
            logger.info("train № " + outTrain.getTrainNumber() + " drove out of the tunnel №: " + tunnelId);
        }finally {
            lock.unlock();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tunnel tunnel = (Tunnel) o;

        if (tunnelId != tunnel.tunnelId) return false;
        if (trains != null ? !trains.equals(tunnel.trains) : tunnel.trains != null) return false;
        return direction == tunnel.direction;
    }

    @Override
    public int hashCode() {
        int result = (int) (tunnelId ^ (tunnelId >>> 32));
        result = 31 * result + (trains != null ? trains.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Tunnel{");
        sb.append("tunnelId=").append(tunnelId);
        sb.append(", trains=").append(trains);
        sb.append(", direction=").append(direction);
        sb.append('}');
        return sb.toString();
    }
}
