package com.put.battleship.server;

import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String[] args) throws Exception {
        Dotenv dotenv = Dotenv.configure().directory("../").load();

        String host = dotenv.get("HOST");
        int port = Integer.parseInt(dotenv.get("PORT"));

        new WebSocketServer(host, port).run();
    }
}