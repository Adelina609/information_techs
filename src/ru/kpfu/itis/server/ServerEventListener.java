package ru.kpfu.itis.server;

import java.net.Socket;
import ru.kpfu.itis.protocol.Message;

public interface ServerEventListener {
  public void init(ServerExample server);
  public void handle(int socketId, Message message) throws ServerEventListenerException, ServerException;
  public int getType();
}
