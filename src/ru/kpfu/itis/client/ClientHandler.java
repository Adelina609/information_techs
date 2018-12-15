package ru.kpfu.itis.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private static Socket clientDialog;

    public ClientHandler(Socket client) {
        ClientHandler.clientDialog = client;
    }

    @Override
    public void run() {

        try (DataInputStream in = new DataInputStream(clientDialog.getInputStream());
            DataOutputStream out = new DataOutputStream(clientDialog.getOutputStream())){

            while (!clientDialog.isClosed()) {
                String entry = in.readUTF();
                System.out.println("Client: " + entry);
                if (entry.equalsIgnoreCase("quit")) {
                    out.writeUTF("Server: " + entry + " - OK");
                    break;
                }
                out.writeUTF("Server reply - " + entry + " - OK");
                out.flush();
            }
            clientDialog.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}