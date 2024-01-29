package org.example.config;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;

public class WebSocketVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        HttpServerOptions options = new HttpServerOptions();
        options.setPort(8080);
        options.setRegisterWebSocketWriteHandlers(true);
        HttpServer server = vertx.createHttpServer(options);
        SocketHandler mySocket = SocketHandler.class.getDeclaredConstructor().newInstance();
        server.webSocketHandler(mySocket);
        server.listen();
    }
}
