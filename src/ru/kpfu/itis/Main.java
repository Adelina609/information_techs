package ru.kpfu.itis;

import ru.kpfu.itis.client.RunnableClientTest;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {

        ExecutorService exec = Executors.newFixedThreadPool(5);

        int j = 0;
        while (j < 5) {
            j++;
            exec.execute(new RunnableClientTest());
        }
        exec.shutdown();
    }
}
