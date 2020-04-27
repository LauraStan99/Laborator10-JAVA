package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        GameClient client = new GameClient();
        while (true) {
            String request = client.readCommands();
            if (request.equalsIgnoreCase("exit")) {
                break;
            }
            if(request.equalsIgnoreCase("stop"))
            {
                break;
            }
                else {
                client.sendRequestToServer(request);
                client.receiveResponseFromServer();

            }
        }
    }
}
