
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
    
    private ServerSocket sockEcoute;
    private Hashtable<Integer,ThreadTchat> listetchat;
    
    InstantMessage IM = new InstantMessage();
    public TchatServer(int id_compte) throws IOException, ClassNotFoundException
            {
                
                this.sockEcoute = new ServerSocket(0);
                InetAddress address=this.sockEcoute.getInetAddress();
                int port=this.sockEcoute.getLocalPort();
                String InsertPortAddressClient = IM.InsertPortAddressClient(id_compte, port, address);
                this.listetchat = new Hashtable<>();
            }
            
            
    
    public ThreadTchat getTchat(int id) throws TchatInconnuException{
        if (!this.listetchat.containsKey(id)) throw new TchatInconnuException();
        return this.listetchat.get(id);
    }
    
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
            
            
            public int getport()
            {
                return sockEcoute.getLocalPort();
            }
            
            public InetAddress getaddress()
            {
                return sockEcoute.getInetAddress();
            }
            
            
    
           
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
