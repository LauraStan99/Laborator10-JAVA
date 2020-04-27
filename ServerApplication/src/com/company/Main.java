package com.company;

import com.company.server.GameServer;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        GameServer gameServer=new GameServer();
        try {
            gameServer.start();
            gameServer.waitClients();
        } catch (IOException e){
            gameServer.stopProcess();
            System.out.println("Exception: " + e);
        }

    }
    }

