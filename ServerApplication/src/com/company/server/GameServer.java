package com.company.server;

import com.company.server.ClientThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {
    public static final int PORT = 8100;
    public ServerSocket socketServer=null;
    public Boolean execute = false;
    public List<ClientThread> clients;

    public GameServer(){
        this.clients= new ArrayList<>();
    }

    /*
    * Metoda ce semnalizeaza pornirea serverului
    * odata apelata se creeaza un socket de server legat de portul specificat , atata timp cat variabila execute este true ,
    *  adica nu s-a cerut incetarea rularii serverului
    */
    public void start() throws IOException {
        if (!this.execute){
            socketServer=new ServerSocket(PORT);
            this.execute = true;
            System.out.println("Server started!");
        }

    }
    /*
     * Metoda ce semnalizeaza oprirea serverului
     * odata apelata se inchide socketul serverului (prin metoda close() ) , lucru ce impiedica crearea de noi conexiuni ,
     * si se afiseaza mesajul "Server stopped!"*/
    public void stopProcess() throws IOException {
        if(this.execute) {
            this.execute = false;
            socketServer.close();
            System.out.println("Server stopped!");
            System.exit(1);
        }
    }
    /*
    * Metoda face ca serverul sa fie in asteptare de noi clienti , iar in momentul in care s-a realizat conexiune , fiecare client sa poate rula
    * pentru fiecare client se creeaza un nou socket si prin intermediul metodei accept()  se asteapta un client care sa intre intr-o conexiune, această metodă
    * se blochează până când un client se conectează la serverul din portul specificat  , odata intrat un client se creeaza un obiect de tipul  ClientThread pentru a putea
    * apela metoda run() , aceea de rulare a clientului (de a-i prelua si prelucra comenzile) si fiecare nou client este adaugat in lista de clienti (cea oferita ca atribut clasei)
    * */
    public void waitClients() throws IOException {
        while(this.execute) {
            Socket socket=socketServer.accept();
            System.out.println("A client has connected");
            ClientThread newClient = new ClientThread(socket);
            newClient.run();
            clients.add(newClient);
        }
    }




}
