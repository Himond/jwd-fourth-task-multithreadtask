package by.training.multithreadtunnel.exception;

public class TunnelException extends Exception{
    public TunnelException() {
    }

    public TunnelException(String message) {
        super(message);
    }

    public TunnelException(String message, Throwable cause) {
        super(message, cause);
    }

    public TunnelException(Throwable cause) {
        super(cause);
    }
}
