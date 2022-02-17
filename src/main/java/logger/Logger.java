package logger;

public interface Logger {

    void createSuccessLog(String message, String info);
    void createErrorLog(String message, Exception error);
    void createInfoLog(String message);
    void logClose();
}
