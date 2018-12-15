package ru.kpfu.itis;

import ru.kpfu.itis.server.SocketServerExample;
import ru.kpfu.itis.server.listeners.IntAdderListener;
import ru.kpfu.itis.server.listeners.IntMultiplerListener;

public class AppServer {
  private static final int PORT = 1234;
  
  public static void main(String[] args) {
    try{
      SocketServerExample server = new SocketServerExample(PORT);
      server.registerListener(new IntAdderListener());
      server.registerListener(new IntMultiplerListener());
      server.start();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
