package main;

public class Logger {

    public static void LogMessage(String message)
    {
        System.out.println("Time " + (int) (System.currentTimeMillis() / 1000) % 60 + ": " + message);
    }
}
