/*
 * EchoClient.java
 *
 * Created on 6 septembre 2000, 11:11
 */
 
import java.io.*;
import java.net.*;
public class client extends Object {


    
      
      
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
                
                String choix1 = entree.readLine();
                switch (choix1)
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
                                
                                // Si la connexion a réussie
                                if (param[0].equals("OKCONNEXION"))
                                {
                                    // On récupere l'id du compte connecté
                                    id_compte = Integer.parseInt(param[1]);
                                    while(true){
                                    //On affiche le menu principal
                                    System.out.println("==============================================================");
                                    System.out.println("---------------------- Projet Connect ! ----------------------");
                                    System.out.println("==============================================================");
                                    System.out.println("1/ Créer profil étudiant ");
                                    System.out.println("2/ Modifier informations de mon profil étudiant");
                                    System.out.println("3/ Afficher liste étudiant ");
                                    System.out.println("4/ Recherche un étudiant (N°étudiant) ");
                                    System.out.println("5/ Exit ");
                                    //ON lit le choix
                                    String choix2 = entree.readLine();
                                    switch (choix2) 
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
                                            System.out.println("Entrez votre competence, 1: Réseaux, 2: Télécoms, 3: Development :");
                                            String id_competence = entree.readLine();
                                            requete = "CREATIONPROFIL "+nom+" "+prenom+" "+date_naissance+" "+mail+" "+telephone+" "+id_competence+" "+id_compte;
                                            System.out.println(requete);
                                            fluxSortieSocket.println(requete);	      
                                            String retourCreationProfil = fluxEntreeSocket.readLine();	      
                                            System.out.println("Reponse du serveur : "+retourCreationProfil); 
                                            break;
                                          
                                         case "2" :
                                            System.out.println("==============================================================");
                                            System.out.println("---------------------- Projet Connect ! ----------------------");
                                            System.out.println("==============================================================");
                                            
                                            requete = "AFFICHERPROFILCOMPTE "+id_compte;
                                            System.out.println(requete);
                                            fluxSortieSocket.println(requete);	      
                                            String retourAfficherListeProfilCompte = fluxEntreeSocket.readLine();
                                             System.out.println(retourAfficherListeProfilCompte);
                                            System.out.println("==============================================================");
                                            System.out.println("---------------------- Projet Connect ! ----------------------");
                                            System.out.println("==============================================================");
                                            System.out.println("Que souhaitez vous modifier ? :");
                                            System.out.println("1 - Votre mail ? ");
                                            System.out.println("2 - Votre numero de telephone ? ");
                                            System.out.println("3 - Vos compétences ? ");
                                            String choix3 = entree.readLine();
                                            switch (choix3) 
                                            {
                                                // Modification du mail 
                                                 case "1" :
                                                    System.out.println("==============================================================");
                                                    System.out.println("---------------------- Projet Connect ! ----------------------");
                                                    System.out.println("==============================================================");
                                                    System.out.println("Quel est votre nouveau mail ? :");
                                                    String nouveauMail = entree.readLine();
                                                    requete = "MODIFICATIONMAIL "+nouveauMail+" "+id_compte;
                                                   
                                                    fluxSortieSocket.println(requete);	      
                                                    String retourModificationMail = fluxEntreeSocket.readLine();	      
                                                    System.out.println("Reponse du serveur : "+retourModificationMail); 
                                                    break;
                                                 // Modification du numéro de téléphone   
                                                 case "2" :
                                                    System.out.println("==============================================================");
                                                    System.out.println("---------------------- Projet Connect ! ----------------------");
                                                    System.out.println("==============================================================");
                                                    System.out.println("Quel est votre nouveau numéro de téléphone ? :");
                                                    String nouveauTelephone = entree.readLine();
                                                    requete = "MODIFICATIONTEL "+nouveauTelephone+" "+id_compte;
                                                    fluxSortieSocket.println(requete);	      
                                                    String retourModificationTel = fluxEntreeSocket.readLine();	      
                                                    System.out.println("Reponse du serveur : "+retourModificationTel); 
                                                    break;
                                                    
                                                 // Modification des compétences ( A REVOIIIIIIIIIIIIIIR)  
                                                 case "3" :
                                                    System.out.println("==============================================================");
                                                    System.out.println("---------------------- Projet Connect ! ----------------------");
                                                    System.out.println("==============================================================");
                                                    System.out.println("Competence(s) ? :");
                                                    String nouveleCompetence = entree.readLine();
                                                    requete = "MODIFICATIONCOMPETENCE "+nouveleCompetence+" "+id_compte;
                                                    fluxSortieSocket.println(requete);	      
                                                    String retourModificationCompetence = fluxEntreeSocket.readLine();	      
                                                    System.out.println("Reponse du serveur : "+retourModificationCompetence); 
                                                    break;
                                            }
                                            break;
                                            
                                         case "3" :
                                            
                                            break;
                                            
                                         case "4" : //Recherche d'informations sur un étudiant (droits utilisateur)
                                                    System.out.println("==============================================================");
                                                    System.out.println("---------------------- Projet Connect ! ----------------------");
                                                    System.out.println("==============================================================");
                                                    System.out.println("Entrez le nom de l'étudiant recherché :");
                                                    String nomRecherche = entree.readLine();
                                                    requete = "RECHERCHE_ETUDIANT "+nomRecherche;
                                                    fluxSortieSocket.println(requete);	      
                                                    String retourRecherche = fluxEntreeSocket.readLine();	      
                                                    System.out.println("Reponse du serveur : "+retourRecherche); 
                                            
                                            break;
                                            
                                         case "5" :
                                            
                                            break;
                                            
                                         default : 
                                            
                                            break;
                                    }
                                    }
                                }
                                break; 
                        
                        
                    case "3":// ------------------------------- CONNEXION SANS COMPTE (ANONYME)-----------------------
                                System.out.println("==============================================================");
                                System.out.println("---------------------- Projet Connect ! ----------------------");
                                System.out.println("==============================================================");
                                System.out.println("1/ Afficher liste étudiant ");
                                System.out.println("2/ Recherche un étudiant (N°étudiant) ");
                                System.out.println("3/ Exit ");
                                //ON lit le choix
                                String choix4 = entree.readLine();
                                
                        
                        
                        
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
