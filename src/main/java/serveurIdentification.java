import java.net.*;
import java.io.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/** 
 * @author  Emmanuel Menat - Selim Yahi
 */
public class serveurIdentification extends Thread 
{ /** Port par defaut */
    public final static int portEcho = 2001;
    Socket          connexionCourante;
    
    
    serveurIdentification(Socket connexionCourante) 
    {
        this.connexionCourante = connexionCourante; 
    }
    public static void main (String args[]) throws Exception
    {
    ServerSocket leServeur =null;
    Socket          connexionCourante;
    PrintStream     fluxSortieSocket;
    BufferedReader  fluxEntreeSocket;
    gestionProtocole gestion = new gestionProtocole();
        try 
        {
            leServeur = new ServerSocket(portEcho);
        }
        catch (IOException ex)
        {
        // fin de connexion
        System.err.println("Impossible de creer un socket serveur sur ce port : "+ex);
            try 
            {
                // on demande un port anonyme 
                leServeur = new ServerSocket(0);
            }
            catch (IOException ex2)
            {
            // fin de connexion
            System.err.println("Impossible de creer un socket serveur : "+ex);
            }
        }
        if(leServeur != null)
        {
            try 
            {
                System.err.println("En attente de connexion sur le port : "+leServeur.getLocalPort());
                while (true) 
                {
                connexionCourante = leServeur.accept();
                    
                System.err.println("Nouvelle connexion : " + connexionCourante);
                new Thread(new serveurIdentification(connexionCourante)).start();
                }   
            }
            catch (Exception ex)
            {
                // erreur de connexion
                System.err.println("Une erreur est survenue : "+ex);
                ex.printStackTrace();
            }
        }
    }
    
    public void fermer() throws Exception 
    {		
            try 
            {
                connexionCourante.close();
            } 
            catch(Exception ex) 
            {
                // il y a eu une erreur
                ex.printStackTrace();
            }
    }
    
    public void run()
    {
        try {
            PrintStream     fluxSortieSocket;
            BufferedReader  fluxEntreeSocket;
            gestionProtocole gestion = new gestionProtocole();
            
            //recevoir chaine caractere
            fluxSortieSocket = new PrintStream(connexionCourante.getOutputStream());
            fluxEntreeSocket = new BufferedReader(new InputStreamReader(connexionCourante.getInputStream()));
            while(true)
            {
            String requeteRecue = fluxEntreeSocket.readLine();
            System.out.println("Requete Recu : "+requeteRecue);
            String resultat = gestion.travaille(requeteRecue);
            //Afficher requete de resultat
            System.out.println("Réponse : "+resultat);
            System.out.println(" ");
            fluxSortieSocket.println(resultat);
            } 
        } catch (SQLException ex) {
            Logger.getLogger(serveurIdentification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(serveurIdentification.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(serveurIdentification.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
} 