package org.example.config;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.ext.web.Router;

import java.util.HashMap;
import java.util.Map;

public class WebSocketVerticle extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        HttpServerOptions options = new HttpServerOptions();
        options.setPort(8080);
        options.setRegisterWebSocketWriteHandlers(true);
        HttpServer server = vertx.createHttpServer(options);
        MySocket mySocket = MySocket.class.getDeclaredConstructor().newInstance();
        server.webSocketHandler(mySocket);
        server.listen();
    }
}
