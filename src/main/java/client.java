/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Yahi Selim
 */
import java.io.*;
import java.net.*;

public class client extends Object {

  /**
  * @param args the command line arguments
  */
  public static void main (String args[]) {
    String          reponse;
    BufferedReader  fluxEntreeStandard;
    Socket          leSocket;
    PrintStream     fluxSortieSocket;
    BufferedReader  fluxEntreeSocket;
    
    
    try {
      fluxEntreeStandard = new BufferedReader(new InputStreamReader(System.in));
    
      System.out.println("Tapez votre requete : ");
    
      // Lecture de la requete
      reponse = fluxEntreeStandard.readLine();
      
      // Connexion sur le port 2001
      leSocket = new Socket("localhost", 2001); // socket sur echo
      
      // On affiche que la connexion est réalisée
      System.err.println("Connecte sur : "+leSocket);
      
      // Partie Socket
      fluxSortieSocket = new PrintStream(leSocket.getOutputStream());
      fluxEntreeSocket = new BufferedReader(new InputStreamReader(leSocket.getInputStream()));
      
      // Envoie la requete dans la socket
      fluxSortieSocket.println(reponse);
      
      //On récupère la réponse du serveur que l'on garde dans retour
      String retour = fluxEntreeSocket.readLine();
      
      // On affiche la réponse du serveur
      System.out.println("Reponse du serveur : "+retour);
      
      // On ferme la socket
      leSocket.close();
    }
    
    
    // EXCEPTION
    catch (UnknownHostException ex)
    {
      System.err.println("Machine inconnue : "+ex);
      ex.printStackTrace();
    }
    catch (IOException ex)
    {
      System.err.println("Erreur : "+ex);
      ex.printStackTrace();
    }    
  }
  
  

}