package by.training.multithreadtunnel.entity;

import by.training.multithreadtunnel.util.IdGenerator;

public class Train extends Thread{

    private final long id;
    private int trainNumber;
    private final Direction direction;

    public Train(int trainNumber, Direction direction) {
        super("Train №: " + trainNumber + ", Direction: " + direction);
        this.id = IdGenerator.generateId();
        this.trainNumber = trainNumber;
        this.direction = direction;
    }

    public long getId() {
        return id;
    }

    public int getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(int trainNumber) {
        this.trainNumber = trainNumber;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public void run() {
        Tunnel tunnel;
        DispatchCenter dispatch = DispatchCenter.getInstance();
        tunnel = dispatch.assignTunnelToTrain(this);
        tunnel.process(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Train train = (Train) o;

        if (id != train.id){
            return false;
        }
        if (trainNumber != train.trainNumber){
            return false;
        }
        return direction == train.direction;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + trainNumber;
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Train{");
        sb.append("id=").append(id);
        sb.append(", trainNumber=").append(trainNumber);
        sb.append(", direction=").append(direction);
        sb.append('}');
        return sb.toString();
    }
}
