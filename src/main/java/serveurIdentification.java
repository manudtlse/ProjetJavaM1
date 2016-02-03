import java.net.*;
import java.io.*;

/** 
 * @author  Emmanuel Menat - Selim Yahi
 */
public class serveurIdentification extends Thread {

  /** Port par defaut */
  public final static int portEcho = 2001;
    
  /**
  * @param args the command line arguments
  */
  public static void main (String args[]) {
    ServerSocket    leServeur = null;
    Socket          connexionCourante;
    PrintStream     fluxSortieSocket;
    BufferedReader  fluxEntreeSocket;
    gestionProtocole gestion = new gestionProtocole();
    gestionProtocoleEtudiants gestion2 = new gestionProtocoleEtudiants();
    
    try {
      leServeur = new ServerSocket(portEcho);
    }
    catch (IOException ex)
    {
      // fin de connexion
      System.err.println("Impossible de creer un socket serveur sur ce port : "+ex);
      
      try {
        // on demande un port anonyme 
        leServeur = new ServerSocket(0);
      }
      catch (IOException ex2)
      {
        // fin de connexion
        System.err.println("Impossible de creer un socket serveur : "+ex);
      }
    }
     
    if
      (leServeur != null)
    {
     try {
      System.err.println("En attente de connexion sur le port : "+leServeur.getLocalPort());
      connexionCourante = leServeur.accept();
      System.err.println("Nouvelle connexion : " + connexionCourante);
      while (true) {
       
        
       
        
        //recevoir chaine caractere
        fluxSortieSocket = new PrintStream(connexionCourante.getOutputStream());
        fluxEntreeSocket = new BufferedReader(new InputStreamReader(connexionCourante.getInputStream()));
        
        System.out.println("Requete recue :");
        String requeteRecue = fluxEntreeSocket.readLine();  
        System.out.println(requeteRecue);
        
        
        String resultat = gestion.travaille(requeteRecue);
        
        //Afficher requete de resultat
        fluxSortieSocket.println(resultat);

       
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

}