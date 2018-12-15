package ru.kpfu.itis.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ru.kpfu.itis.client.ClientHandler;
import ru.kpfu.itis.protocol.Message;

public class SocketServerExample implements ServerExample{
  protected List<ServerEventListener> listeners;
  protected int port; 
  protected ServerSocket server;
  protected boolean started;
  protected List<Socket> sockets;
  private static ExecutorService executeIt = Executors.newFixedThreadPool(2);


  public SocketServerExample(int port){
    this.listeners = new ArrayList<>();
    this.port = port;
    this.sockets = new ArrayList<>();
    this.started = false;
  }
  
  @Override
  public void registerListener(ServerEventListener listener) throws ServerException{
    if(started){
      throw new ServerException("Server has been started already.");
    }
    listener.init(this);
    this.listeners.add(listener);
  }
  
  @Override
  public void start() throws ServerException {
      started = true;
      try (ServerSocket server = new ServerSocket(port)) {
        while (!server.isClosed()) {
          Socket client = server.accept();
          executeIt.execute(new ClientHandler(client));
          System.out.print("Connection accepted.");
        }
        executeIt.shutdown();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  protected void handleConnection(Socket socket) throws ServerException{
    sockets.add(socket);
    int socketsListLength = sockets.size();
    try{
      Message message = Message.readMessage(socket.getInputStream());
      System.out.println("New message:");
      System.out.println(Message.toString(message));
      for(ServerEventListener listener : listeners){
        if(message.getType() == listener.getType()){
          //socket incapsulated to listener
          listener.handle(socketsListLength, message);
        }
      }
    }
    catch(IOException ex){
      throw new ServerException("Problem with handling connection.", ex);
    } catch (ServerEventListenerException ex) {
      throw new ServerException("Problem with handling message.", ex);
    }
  }
  
  @Override
  public void sendMessage(int socketId, Message message) throws ServerException{
    if(!started){
      throw new ServerException("Server hasn't been started yet.");
    }
    //ToDo: check if this socket is from our pull
    Socket socket = sockets.get(socketId);
    try{
      socket.getOutputStream().write(Message.getBytes(message));
      socket.getOutputStream().flush();
    } catch (IOException ex) {
      throw new ServerException("Can't send message.", ex);
    }
  }
  
  @Override
  public void sendBroadCastMessage(Message message) throws ServerException{
    if(!started){
      throw new ServerException("Server hasn't been started yet.");
    }
    try{
      byte[] rawMessage = Message.getBytes(message);
      for(Socket socket : sockets){
        socket.getOutputStream().write(rawMessage);
        socket.getOutputStream().flush();
      }
    } catch (IOException ex) {
      throw new ServerException("Can't send message.", ex);
    }
  }
  
}
