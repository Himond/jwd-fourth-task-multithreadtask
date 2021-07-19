package by.training.multithreadtunnel.main;

import by.training.multithreadtunnel.entity.Train;

import by.training.multithreadtunnel.exception.TunnelException;
import by.training.multithreadtunnel.parser.TrainParser;
import by.training.multithreadtunnel.reader.ReaderService;
import by.training.multithreadtunnel.reader.impl.ReaderServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws TunnelException, IOException {

        ReaderService service = new ReaderServiceImpl();
        List<Train> trains = TrainParser.parseTrain(service.read("src\\main\\resources\\trains.txt"));
        ExecutorService executorService = Executors.newFixedThreadPool(trains.size());
        trains.forEach(executorService::execute);
        executorService.shutdown();

    }
}
