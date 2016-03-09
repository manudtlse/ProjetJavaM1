
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
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
public class TchatServer extends Thread {
    
    // Partie déclaration
    private ServerSocket sockEcoute;
    private Hashtable<Integer,ThreadTchat> listetchat;
    InstantMessage IM = new InstantMessage();
    
    // Constructeur
    public TchatServer(int id_compte) throws IOException, ClassNotFoundException
            {
                
                // Attribution des ports de manière automatique
                this.sockEcoute = new ServerSocket(0);
                // Récupère adresse IP pour BDD
                InetAddress address=this.sockEcoute.getInetAddress();
                // Récupère port pour BDD
                int port=this.sockEcoute.getLocalPort();
                // Insertion port et de l'adresse en fonction de l'id_compte
                String InsertPortAddressClient = IM.InsertPortAddressClient(id_compte, port, address);
                // Hashtable dans laquelle on liste les associations
                this.listetchat = new Hashtable<>();
            }
            
            
    // Fonction qui regarde si un tchat est deja créé entre deux clients, retourne thread correspondant
    public ThreadTchat getTchat(int id) throws TchatInconnuException{
        if (!this.listetchat.containsKey(id)) throw new TchatInconnuException();
        return this.listetchat.get(id);
    }
    
    // Fonction permettant d'ajouter une connexion socket entre deux clients
    public ThreadTchat ajoutTchat(int id, InetAddress ip, int port) throws IOException {
        Socket s = new Socket(ip, port);
        PrintStream fluxSortieSocket = new PrintStream(s.getOutputStream());
        int moi=id;
        fluxSortieSocket.println(id);
        ThreadTchat th = new ThreadTchat(s);
        this.listetchat.put(id,th);
        th.start();
        return th;
    }
    
    public void metier() throws IOException
    {
        while(true)
        {
            Socket s = sockEcoute.accept();

            BufferedReader fluxEntreeSocket = new BufferedReader(new InputStreamReader(s.getInputStream()));
            ThreadTchat th = new ThreadTchat(s);
            this.listetchat.put(Integer.parseInt(fluxEntreeSocket.readLine()),th);
            th.start();
        }
    } 
            
    // Fonction qui retourne le port local   
    public int getport()
    {
        return sockEcoute.getLocalPort();
    }

    // Fonction qui retourne l'adresse IP correspondant au socket
    public InetAddress getaddress()
    {
        return sockEcoute.getInetAddress();
    }



    // Excéuté par le thread
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