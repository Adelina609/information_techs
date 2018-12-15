package ru.kpfu.itis.server;

import java.net.Socket;
import java.util.List;

import ru.kpfu.itis.protocol.Message;

public interface ServerExample {
  public void registerListener(ServerEventListener listener) throws ServerException;
  public void sendMessage(int socketId, Message message) throws ServerException;
  public void sendBroadCastMessage(Message message) throws ServerException;
  public void start() throws ServerException;
}
