package com.company.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientThread {
    public Socket socket;
    public String request;
    GameServer gameServer;

    public ClientThread(Socket newSocket)
    {
        this.socket=newSocket;
    }
    /*
    * Metoda ce are drept scop functionarea unui client
    * Obiectul de tip BufferedReader este utilizat pentru a citi textul din fluxul de intrare ( this.socket.getInputStream() ) , dupa care cu ajutorul metodei readLine()
    * se citeste o linie de text din fluxul de intrare al clientului , atata timp cat se pot citi comenzile  ,
    * 1- se verifica daca comanda primita este "stop" lucru ce ne permite sa trimitem clientuli mesajul "Server stopped !" ,
    * cu ajutorul unui obiect de tipul PrintWriter ce permite scrierea mesajului in fluxul de iesire, dupa care se inchide serverul prin apelarea metodei stopProcess()
    * 2-in caz contrar cu ajutorul unui obiect de tipul PrintWriter se scrie raspunsul corespunzator in fluxul de iesire catre client
    * */
    public void run() throws IOException {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            while((request = in.readLine()) != null) {
                String response = this.createRequest(request);
                if(getRequest()=="stop")
                {
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    out.println("Server stopped !");
                    out.flush();
                    gameServer.stopProcess();
                    break;
                }
                else{
                    PrintWriter out = new PrintWriter(socket.getOutputStream());
                    out.println(response);
                    out.flush();
                }
            }
            socket.close();
        } catch(Exception e) {
            System.out.println("Exception: " + e);

        }
    }
    /*
    * Aceasta metoda creaza raspunsul care urmeaza a fi trimis la client in urma primirii unei comenzi de la acesta
    * 1-In cazul in care comanda clientului este "stop" raspunsul este "Server stopped" , fiind setat si returnat , pentru a fi utilizat in metoda run()
    * 2-In cazul in care comanda este diferita de "stop " se trimite mesajul " Server received the request : " insotit de comanda introdusa , care va fi trimisa la client ,
    * in acelasi timp am afisat-o si in terminalul serverului */
    public String createRequest(String newRequest) throws IOException {
        System.out.println("Server received the request : "+ newRequest+"\n");
        String result=null;
        if(newRequest.indexOf("stop")==-1){
            this.request=newRequest;
            result= "Server received the request : "+ newRequest;
            }
        else { this.request="stop";
               result="Server stopped";
        }
        return result;
    }

    public String getRequest() {
    return request;
    }
}
