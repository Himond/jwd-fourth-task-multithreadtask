package by.training.multithreadtunnel.reader.impl;

import by.training.multithreadtunnel.exception.TunnelException;
import by.training.multithreadtunnel.reader.ReaderService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReaderServiceImpl implements ReaderService {

    private static Logger logger = LogManager.getLogger();

    @Override
    public List<String> read(String path) throws TunnelException {

        List<String> trainList;
        Path getPath = Paths.get(path);

        try (Stream<String> lineStream = Files.newBufferedReader(getPath).lines()) {
            trainList = lineStream.collect(Collectors.toList());
        } catch (IOException e) {
            logger.log(Level.ERROR, "File not found: " + getPath.getFileName());
            throw new TunnelException(e);
        }

        return trainList;
    }
}
