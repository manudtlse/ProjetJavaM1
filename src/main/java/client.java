/*
 * EchoClient.java
 *
 * Created on 6 septembre 2000, 11:11
 */
 
import java.io.*;
import java.net.*;
import static java.time.Clock.system;

public class client extends Object {


    public static int menu () throws IOException
    {
        int choix = System.in.read();
        System.out.println("Tapez votre choix (chiffre) : ");
        System.out.println("1 - Modifier ses propres informations (faire plus tard)");
        System.out.println("2 - Créer profil étudiant");
        System.out.println("3 - Afficher la liste des étudiants");
        System.out.println("4 - Recherche d'un étudiant (N°Etudiant)");
        System.out.println("5 - FIN");
        return choix;
  }
      
      
  /**
  * @param args the command line arguments
  */
  public static void main (String args[]) {
    String          reponse = "";
    BufferedReader  fluxEntreeStandard;
    Socket          leSocket;
    PrintStream     fluxSortieSocket;
    BufferedReader  fluxEntreeSocket;
    int id_compte;
    
    try {
      fluxEntreeStandard = new BufferedReader(new InputStreamReader(System.in));
      leSocket = new Socket("localhost", 2001); // socket sur echo
      System.err.println("ConnectÃ© sur : "+leSocket);
      fluxSortieSocket = new PrintStream(leSocket.getOutputStream());
      fluxEntreeSocket = new BufferedReader(new InputStreamReader(leSocket.getInputStream()));
      String requete = "";
      
      while (true)
      {
            BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
            while (true)
            {
                System.out.println("==============================================================");
                System.out.println("---------------------- Projet Connect ! ----------------------");
                System.out.println("==============================================================");
                System.out.println("1/ Création de compte (Inscription)");
                System.out.println("2/ Connexion (avec compte))");
                System.out.println("3/ Connexion (sans compte) ");
                System.out.println("4/ Exit ");
                
                String ligne = entree.readLine();
                switch (ligne)
                {
                    
                    // ------------------------- CREATION DE COMPTE ------------------------------
                    case "1":
                                System.out.println("==============================================================");
                                System.out.println("---------------------- Projet Connect ! ----------------------");
                                System.out.println("==============================================================");
                                System.out.println("Entrez votre login :");
                                String loginInscription = entree.readLine();
                                System.out.println("Entrez votre mot de passe :");
                                String pwdInscription = entree.readLine();
                                requete = "INSCRIPTION "+loginInscription+" "+pwdInscription + " Utilisateur";
                                System.out.println(requete);
                                fluxSortieSocket.println(requete);	      
                                String retourInscription = fluxEntreeSocket.readLine();	      
                                System.out.println("Reponse du serveur : "+retourInscription); 
                                          break;
                     // ------------------------------- CONNEXION AVEC COMPTE ---------------------------------
                     case "2":
                                System.out.println("==============================================================");
                                System.out.println("---------------------- Projet Connect ! ----------------------");
                                System.out.println("==============================================================");
                                System.out.println("Pour vous connecter, entrez votre login :");
                                String loginConnexion = entree.readLine();
                                System.out.println("Entrez votre mot de passe :");
                                String pwdConnexion = entree.readLine();
                                requete = "CONNEXION "+loginConnexion+" "+pwdConnexion;
                                System.out.println(requete);
                                fluxSortieSocket.println(requete);	      
                                String retourConnexion = fluxEntreeSocket.readLine();	      
                                System.out.println("Reponse du serveur : "+retourConnexion);
                                String param [] = retourConnexion.split(" ");
                                if (param[0].equals("OKCONNEXION"))
                                {
                                    id_compte = Integer.parseInt(param[1]);
                                    System.out.println("==============================================================");
                                    System.out.println("---------------------- Projet Connect ! ----------------------");
                                    System.out.println("==============================================================");
                                    System.out.println("1/ Créer profil étudiant ");
                                    System.out.println("2/ Modifier informations de mon profil étudiant");
                                    System.out.println("3/ Afficher liste étudiant ");
                                    System.out.println("4/ Recherche un étudiant (N°étudiant) ");
                                    System.out.println("5/ Exit ");
                                    String ligne1 = entree.readLine();
                                    switch (ligne1) 
                                    {
                                        case "1" :
                                            System.out.println("==============================================================");
                                            System.out.println("---------------------- Projet Connect ! ----------------------");
                                            System.out.println("==============================================================");
                                            System.out.println("Entrez votre nom :");
                                            String nom = entree.readLine();
                                            System.out.println("Entrez votre prenom :");
                                            String prenom = entree.readLine();
                                            System.out.println("Entrez votre date de naissance (JJ/MM/YYYY) :");
                                            String date_naissance = entree.readLine();
                                            System.out.println("Entrez votre mail :");
                                            String mail = entree.readLine();
                                            System.out.println("Entrez votre telephone :");
                                            String telephone = entree.readLine();
                                            requete = "CREATIONPROFIL "+nom+" "+prenom+" "+date_naissance+" "+mail+" "+telephone+" "+id_compte;
                                            System.out.println(requete);
                                            fluxSortieSocket.println(requete);	      
                                            String retourCreationProfil = fluxEntreeSocket.readLine();	      
                                            System.out.println("Reponse du serveur : "+retourCreationProfil); 
                                            break;
                                          
                                         case "2" :
                                            
                                            break;
                                            
                                         case "3" :
                                            
                                            break;
                                            
                                         case "4" :
                                            
                                            break;
                                            
                                         case "5" :
                                            
                                            break;
                                            
                                         default : 
                                            
                                            break;
                                    }
                                }
                                
                        break; 
                        
                    case "3":
                                System.out.println("==============================================================");
                                System.out.println("---------------------- Projet Connect ! ----------------------");
                                System.out.println("==============================================================");
                        
                        
                        
                        
                        break; 
                        
                    case "4":
                        
                        break;
                        
                    default :
 
                }
                
                
                
                
            }

      }
      
      //leSocket.close();
    }
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