package by.training.multithreadtunnel.reader;

import by.training.multithreadtunnel.exception.TunnelException;

import java.io.IOException;
import java.util.List;

public interface ReaderService {
    List<String> read(String path) throws IOException, TunnelException;
}
