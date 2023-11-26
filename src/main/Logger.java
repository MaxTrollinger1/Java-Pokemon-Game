package main;

public class Logger {

    public static void LogMessage(String message)
    {
        // Log messages for game, use current time to display when the message was logged
        System.out.println("Time " + (int) (System.currentTimeMillis() / 1000) % 60 + ": " + message);
    }
}
