package it.polimi.ingsw;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.network.server.SocketServer;

public class EriantysServer {

    public static void main(String[] args) {
        int serverPort = 1511;

        for (int i = 0; i < args.length; i++) {
            if (args.length >= 2 && args[i].equals("--port")) {
                try {
                    serverPort = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid server port specified");
                }
            }
        }

        GameController gameController = new GameController();
        Server server = new Server(gameController);
        SocketServer socketServer = new SocketServer(server, serverPort);
        Thread thread = new Thread(socketServer, "socketServer_");
        thread.start();
    }
}
