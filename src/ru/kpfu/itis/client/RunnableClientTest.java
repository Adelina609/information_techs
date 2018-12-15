package ru.kpfu.itis.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RunnableClientTest implements Runnable {

    private static Socket socket;

    public RunnableClientTest() {
        try {
            socket = new Socket("localhost", 3345);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try (DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
                DataInputStream ois = new DataInputStream(socket.getInputStream())) {
            int i = 0;
            while (i < 5) {
                oos.writeUTF("Some message " + i);
                oos.flush();
                System.out.println("reading...");
                String in = ois.readUTF();
                System.out.println(in);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}