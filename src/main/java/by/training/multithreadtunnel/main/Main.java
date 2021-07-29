package by.training.multithreadtunnel.main;

import by.training.multithreadtunnel.entity.Train;

import by.training.multithreadtunnel.exception.TunnelException;
import by.training.multithreadtunnel.parser.TrainParser;
import by.training.multithreadtunnel.reader.ReaderService;
import by.training.multithreadtunnel.reader.impl.ReaderServiceImpl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws TunnelException, IOException {

        ClassLoader classLoader = Main.class.getClassLoader();
        URL resource = classLoader.getResource("trains.txt");
        assert resource != null;
        String absolutePath = new File(resource.getFile()).getAbsolutePath();
        ReaderService service = new ReaderServiceImpl();

        List<Train> trains = TrainParser.parseTrain(service.read(absolutePath));
        ExecutorService executorService = Executors.newFixedThreadPool(trains.size());
        trains.forEach(executorService::execute);
        executorService.shutdown();

    }
}
