package br.com.vertx.learning_vertx.chapter01;

import io.vertx.core.Vertx;
import io.vertx.core.net.NetSocket;

public class VertxEcho {
  public static int numberOfConnections = 0;

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();

    vertx.createNetServer()
      .connectHandler(VertxEcho::handleNewClient)
      .listen(3000);

    vertx.setPeriodic(5000, id -> System.out.println(howMany()));

    vertx.createHttpServer()
      .requestHandler(request -> request.response().end(howMany()))
      .listen(8080);
  }

  private static void handleNewClient(NetSocket netSocket) {
    numberOfConnections++;
    netSocket.handler(buffer -> {
      netSocket.write(buffer);
      if (buffer.toString().endsWith("/quit\n")) {
        netSocket.close();
      }
    });
    netSocket.closeHandler(v -> numberOfConnections--);
  }

  private static String howMany() {
    return "We now have " + numberOfConnections + " connections";
  }

}
