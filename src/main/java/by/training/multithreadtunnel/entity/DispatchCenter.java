package by.training.multithreadtunnel.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DispatchCenter {

    private static Logger logger = LogManager.getLogger();
    private static final int MAX_IN_TUNNEL_TRAIN_COUNT = 4;
    private static final int MAX_ROW_TRAIN_COUNT = 3;

    private final AtomicInteger countRowFirstTunnel = new AtomicInteger(0);
    private final AtomicInteger countRowSecondTunnel = new AtomicInteger(0);

    private final Tunnel firstTunnel;
    private final Tunnel secondTunnel;

    private final Lock lock = new ReentrantLock();

    private DispatchCenter() {
        this.firstTunnel = new Tunnel(Direction.UNUSED);
        this.secondTunnel = new Tunnel(Direction.UNUSED);
    }

    private static class SingletonHolder{
        static final DispatchCenter INSTANCE = new DispatchCenter();
    }

    public static DispatchCenter getInstance(){
        return SingletonHolder.INSTANCE;
    }

    public Tunnel assignTunnelToTrain(Train train){
        try{
            lock.lock();
            while (true){
                if (checkTunnel(train, firstTunnel, countRowFirstTunnel, countRowSecondTunnel)) {
                    return firstTunnel;
                }
                if (checkTunnel(train, secondTunnel, countRowSecondTunnel, countRowFirstTunnel)) {
                    return secondTunnel;
                }
            }
        } finally {
            lock.unlock();
        }
    }

    private boolean checkTunnel(Train train, Tunnel tunnel, AtomicInteger firstCounter, AtomicInteger secondCounter) {
        if (checkTrainInTunnel(tunnel) && checkDirectionTunnel(tunnel, train)){
            if (firstCounter.get() < MAX_ROW_TRAIN_COUNT){
                firstCounter.incrementAndGet();
                secondCounter.set(0);
                logger.info("train № " + train.getTrainNumber() + " got access to tunnel №:" + tunnel.getTunnelId());
                return true;
            }
        }
        return false;
    }

    private boolean checkTrainInTunnel(Tunnel tunnel){
        if(tunnel.getCountTrain() == 0){
            tunnel.setDirection(Direction.UNUSED);
            return true;
        }else {
            return tunnel.getCountTrain() < MAX_IN_TUNNEL_TRAIN_COUNT;
        }
    }

    private boolean checkDirectionTunnel(Tunnel tunnel, Train train){
        if(tunnel.getDirection() == Direction.UNUSED){
            tunnel.setDirection(train.getDirection());
            return true;
        }else {
            return tunnel.getDirection() == train.getDirection();
        }
    }

}
