
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author many31
 */
public class ThreadTchat extends Thread {
            PrintStream     fluxSortieSocket;
            BufferedReader  fluxEntreeSocket;
            
        public ThreadTchat (Socket connexionCourante) throws IOException
                {
                    //recevoir chaine caractere
                    fluxSortieSocket = new PrintStream(connexionCourante.getOutputStream());
                    fluxEntreeSocket = new BufferedReader(new InputStreamReader(connexionCourante.getInputStream()));
                }

        public void metier() throws IOException
        {
            while(true)
            {
                String requeteRecue = fluxEntreeSocket.readLine();
                //System.out.println("Requete Recu : "+requeteRecue);
                this.reception(requeteRecue);
            } 
        }

        public void reception(String Message)
        {
            System.out.println(Message);
        }  

        public void envoie(String Message)
        {
            fluxSortieSocket.println(Message);
        }    

      // Rajouter a 14:16 pour tester
        public void run()
            {
                try 
                {
                   this.metier();
                } 
                catch (IOException ex)
                {
                    Logger.getLogger(TchatServer.class.getName()).log(Level.SEVERE, null, ex);
                }
             }   
}

























































//ht newthread
//ht.put (id,thread);   id<---> ip,port
