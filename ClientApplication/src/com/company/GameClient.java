package com.company;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {
        /*
        * am setat portul la care va rula serverul , cel prestabilit si in proiectul serverului , precum si adresa IP a serverului (localhost)
        */
        public static String SERVER_ADDRESS = "127.0.0.1";
        public static int PORT = 8100;
        Socket socket = null;

        /*
        * Constructor prin care se creeaza un socket*/
        public GameClient() throws IOException {
            this.socket = new Socket(SERVER_ADDRESS, PORT);
        }

        /*
        * Aceasta functie are drept scop citirea comenzilor introduse de catre utilizator de la tastatura
        * Cu ajutorul unui obiect de tipul Scanner ,apelam metoda netLine() utilizata pentru citirea de String-uri
        */
        public String readCommands () {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        }

        /*
        * Metoda este utilizata pentru a trimite comenzile introduse de la tastatura catre server
        * Comunicarea cu serverul se face prin intermediul unui socket caruia i s-au atasat adresa IP a serverului (127.0.0.1) si portul la care acesta ruleaza
        * Cu ajutorul unui obiect de tipul OutputStreamWriter (subclasă a clasei Java Writer) se converteste fluxul de caractere în flux de octeți,
        * si se da fluxul de iesire ca parametru ( socket.getOutputStream() )
        * apoi obiectul de tipul BufferedWriter este utilizat pentru a oferi buffering pentru instanțele Writer ( output ) *, apeland metoda write() , se scrie String-ul
        * oferit ca si parametru iar metoda flush() transmite catre destinatia prestabilita buffer-ul cu informatia  */
        public void sendRequestToServer(String request) throws IOException {
            OutputStreamWriter output = new OutputStreamWriter(socket.getOutputStream());
            BufferedWriter out = new BufferedWriter(output);
            out.write(request + "\n");
            out.flush();
        }

        /*
        * Metoda este utilizata pentru a prelua informatii de la server
        * Comunicarea cu serverul se face prin intermediul unui socket caruia i s-au atasat adresa IP a serverului (127.0.0.1) si portul la care acesta ruleaza
        * Obiectul de tip BufferedReader este utilizat pentru a citi textul din fluxul de intrare ( socket.getInputStream() ) , dupa care cu ajutorul metodei readLine()
        * se citeste cate o linie de text din fluxul de intrare , iar raspunsul este pus intr-un obiect String care urmeaza a fi afisat pe ecran
        * */
        public void receiveResponseFromServer() throws IOException {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String rsp = in.readLine();
            if (rsp != null) {
               System.out.println(rsp);
            }
        }

    }
