package ru.kpfu.itis.server.listeners;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import ru.kpfu.itis.protocol.Message;
import ru.kpfu.itis.server.AbstractServerEventListener;
import ru.kpfu.itis.server.ServerEventListenerException;
import ru.kpfu.itis.server.ServerException;

public class IntAdderListener extends AbstractServerEventListener{

  @Override
  public void handle(int socketId, Message message) throws ServerEventListenerException, ServerException {
    if(!this.init){
      throw new ServerEventListenerException("Listener has not been initiated yet.");
    }
    IntBuffer buffer = ByteBuffer.wrap(message.getData()).asIntBuffer();
    int summ = buffer.get(0) + buffer.get(1);
    Message answer = Message.createMessage(Message.TYPE1, ByteBuffer.allocate(4).putInt(summ).array());
    try{
      this.server.sendMessage(socketId, answer);
    } catch (ServerException ex) {
      throw new ServerException("Problem with handling...", ex);
    }
  }

  @Override
  public int getType() {
    return Message.TYPE1;
  }

}
