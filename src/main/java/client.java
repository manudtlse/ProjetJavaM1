/*
 * EchoClient.java
 *
 * Created on 6 septembre 2000, 11:11
 */
 
import java.io.*;
import static java.lang.System.exit;
import java.net.*;
import java.awt.Color;



public class client extends Object        
{   
/**
* @param args the command line arguments
*/

    public static void main (String args[])
    {
        BufferedReader  fluxEntreeStandard;
        Socket          leSocket;
        PrintStream     fluxSortieSocket;
        BufferedReader  fluxEntreeSocket;
        int id_compte;

        try 
        {
            fluxEntreeStandard = new BufferedReader(new InputStreamReader(System.in));
            leSocket = new Socket("localhost", 2001); // socket sur echo
            System.err.println("ConnectÃ© sur : "+leSocket);
            fluxSortieSocket = new PrintStream(leSocket.getOutputStream());
            fluxEntreeSocket = new BufferedReader(new InputStreamReader(leSocket.getInputStream()));
            String requete = "";

            // Tant qu'on recoit des requetes
            while (true)
            {
                BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
                while (true)
                {
                    // Menu principal au lancement de l'application
                    System.out.println("==============================================================");
                    System.out.println("---------------------- Projet Connect ! ----------------------");
                    System.out.println("==============================================================");
                    System.out.println("1/ Création de compte (Inscription)");
                    System.out.println("2/ Connexion (avec Compte))");
                    System.out.println("3/ Connexion (Anonyme)");
                    System.out.println("4/ Exit ");

                    // On lit le choix de l'utilisateur
                    System.out.print("\u001B[31m> ");
                    String choix1 = entree.readLine();
                    // En fonction de ce choix
                    switch (choix1)
                    {                   
                        // Création d'un compte pour utiliser l'application en mode "connecté"
                        case "1":
                                    System.out.println("==============================================================");
                                    System.out.println("---------------------- Projet Connect ! ----------------------");
                                    System.out.println("==============================================================");
                                    System.out.println("Choisissez votre login pour l'inscription :");
                                    System.out.print("\u001B[31m> ");
                                    String loginInscription = entree.readLine();
                                    System.out.println("Entrez votre mot de passe :");
                                    System.out.print("\u001B[31m> ");
                                    String pwdInscription = entree.readLine();
                                    requete = "INSCRIPTION "+loginInscription+" "+pwdInscription + " Utilisateur";
                                    System.out.println(requete);
                                    fluxSortieSocket.println(requete);	

                                    String retourInscription = fluxEntreeSocket.readLine();	      
                                    System.out.println("Reponse du serveur : "+retourInscription); 
                                    break;


                        // ------------------------- UTILISATEUR CONNECTE --------------------------                      
                        // Connexion sur l'application avec un compte
                        case "2":
                                    System.out.println("==============================================================");
                                    System.out.println("---------------------- Projet Connect ! ----------------------");
                                    System.out.println("==============================================================");
                                    System.out.println("Pour vous connecter, entrez votre login :");
                                    System.out.print("\u001B[31m> ");
                                    String loginConnexion = entree.readLine();
                                    System.out.println("Entrez votre mot de passe :");
                                    System.out.print("\u001B[31m> ");
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
                                        // Tant qu'il y a des actions en mode connecté
                                        while(true)
                                        {
                                            //On affiche le menu principal
                                            System.out.println("==============================================================");
                                            System.out.println("---------------------- Projet Connect ! ----------------------");
                                            System.out.println("==============================================================");
                                            System.out.println(" ////////// Veuillez tapez votre choix  \\\\\\\\\\\\\\");
                                            System.out.println("1/ Créer profil étudiant");
                                            System.out.println("2/ Modifier informations de mon profil étudiant");
                                            System.out.println("3/ Afficher la liste des étudiants");
                                            System.out.println("4/ Rechercher un étudiant");
                                            System.out.println("5/ Modifier mot de passe");

                                            //ON lit le choix
                                            System.out.print("\u001B[31m> ");
                                            String choix2 = entree.readLine();
                                            switch (choix2) 
                                            {
                                                // Création d'un profil étudiant
                                                case "1" :
                                                    System.out.println("==============================================================");
                                                    System.out.println("---------------------- Projet Connect ! ----------------------");
                                                    System.out.println("==============================================================");
                                                    System.out.println("Entrez votre nom :");
                                                    System.out.print("\u001B[31m> ");
                                                    String nom = entree.readLine();
                                                    System.out.println("Entrez votre prenom :");
                                                    System.out.print("\u001B[31m> ");
                                                    String prenom = entree.readLine();
                                                    System.out.println("Entrez votre date de naissance (JJ/MM/YYYY) :");
                                                    System.out.print("\u001B[31m> ");
                                                    String date_naissance = entree.readLine();
                                                    System.out.println("Entrez votre mail :");
                                                    System.out.print("\u001B[31m> ");
                                                    String mail = entree.readLine();
                                                    System.out.println("Entrez votre telephone :");
                                                    System.out.print("\u001B[31m> ");
                                                    String telephone = entree.readLine();
                                                    System.out.println("Entrez votre numéro de competence : 1-Réseaux, 2-Télécoms, 3 Dévélopement : ");
                                                    System.out.print("\u001B[31m> ");
                                                    String id_competence = entree.readLine();
                                                    requete = "CREATION_PROFIL "+nom+" "+prenom+" "+date_naissance+" "+mail+" "+telephone+" "+id_competence+" "+id_compte;
                                                    System.out.println(requete);
                                                    fluxSortieSocket.println(requete);	      
                                                    String retourCreationProfil = fluxEntreeSocket.readLine();	      
                                                    System.out.println("Reponse du serveur : "+retourCreationProfil); 
                                                    break;


                                                //Modification informations profil étudiant  
                                                case "2" :
                                                    System.out.println("==============================================================");
                                                    System.out.println("---------------------- Projet Connect ! ----------------------");
                                                    System.out.println("==============================================================");

                                                    requete = "AFFICHER_PROFIL_COMPTE "+id_compte;
                                                    System.out.println(requete);
                                                    fluxSortieSocket.println(requete);	      
                                                    String retourAfficherProfilCompte = fluxEntreeSocket.readLine();
                                                    System.out.println("==============================================================");
                                                    System.out.println("---------------------- Projet Connect ! ----------------------");
                                                    System.out.println("==============================================================");
                                                    System.out.println("Que souhaitez vous modifier ? :");
                                                    System.out.println("1 - Votre mail ? ");
                                                    System.out.println("2 - Votre numero de telephone ? ");
                                                    System.out.println("3 - Vos compétences ? ");
                                                    System.out.println("Voici vos informations actuelles : ");
                                                    System.out.println(retourAfficherProfilCompte);
                                                    System.out.println(" ////////// Veuillez entrer votre choix  \\\\\\\\\\\\\\");
                                                    System.out.print("\u001B[31m> ");
                                                    String choix3 = entree.readLine();
                                                    switch (choix3) 
                                                    {
                                                        // Modification du mail 
                                                        case "1" :
                                                            System.out.println("==============================================================");
                                                            System.out.println("---------------------- Projet Connect ! ----------------------");
                                                            System.out.println("==============================================================");
                                                            System.out.println("Quel est votre nouveau mail ? :");
                                                            System.out.print("\u001B[31m> ");
                                                            String nouveauMail = entree.readLine();
                                                            requete = "MODIFICATION_MAIL "+nouveauMail+" "+id_compte;

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
                                                            System.out.print("\u001B[31m> ");
                                                            String nouveauTelephone = entree.readLine();
                                                            requete = "MODIFICATION_TEL "+nouveauTelephone+" "+id_compte;
                                                            fluxSortieSocket.println(requete);	      
                                                            String retourModificationTel = fluxEntreeSocket.readLine();	      
                                                            System.out.println("Reponse du serveur : "+retourModificationTel); 
                                                            break;

                                                        // Modification des compétences ( A REVOIIIIIIIIIIIIIIR)  
                                                        case "3" :
                                                            System.out.println("==============================================================");
                                                            System.out.println("---------------------- Projet Connect ! ----------------------");
                                                            System.out.println("==============================================================");
                                                            System.out.println("Nouvelle competence ? (1-Réseaux, 2-Telecoms) :");
                                                            System.out.print("\u001B[31m> ");
                                                            String nouveleCompetence = entree.readLine();
                                                            requete = "MODIFICATION_COMPETENCE "+nouveleCompetence+" "+id_compte;
                                                            fluxSortieSocket.println(requete);	      
                                                            String retourModificationCompetence = fluxEntreeSocket.readLine();	      
                                                            System.out.println("Reponse du serveur : "+retourModificationCompetence); 
                                                            break;
                                                    }
                                                    break;


                                                case "3" : //Afficher la liste des profils étudiants (droits utilisateur)
                                                            System.out.println("==============================================================");
                                                            System.out.println("---------------------- Projet Connect ! ----------------------");
                                                            System.out.println("==============================================================");
                                                            System.out.println("Afficher la liste des profils triée par nom (1), prenom(2), competence (3) :");
                                                            System.out.print("\u001B[31m> ");
                                                            String choixTriListe = entree.readLine();
                                                            switch (choixTriListe) 
                                                            {
                                                                // Tri par nom
                                                                case "1" :               
                                                                    requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_TRI_NOM";
                                                                    fluxSortieSocket.println(requete);	
                                                                    String retourAffichageListeProfilEtudiantTriNom = fluxEntreeSocket.readLine();
                                                                    System.out.println("Reponse du serveur : "+retourAffichageListeProfilEtudiantTriNom); 

                                                                    String[] param1= retourAffichageListeProfilEtudiantTriNom.split("  ");
                                                                    int nbLigne=Integer.parseInt(param1[1]);
                                                                    for (int i=2;i<=nbLigne+1;i++)
                                                                    {
                                                                         System.out.println(param1[i]);
                                                                    }
                                                                    
                                                                    break;
                                                                case "2" : 
                                                                    requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_TRI_PRENOM";
                                                                    fluxSortieSocket.println(requete);	
                                                                    String retourAffichageListeProfilEtudiantTriPrenom = fluxEntreeSocket.readLine();
                                                                    System.out.println("Reponse du serveur : "+retourAffichageListeProfilEtudiantTriPrenom); 

                                                                    String[] param2= retourAffichageListeProfilEtudiantTriPrenom.split("  ");
                                                                    int nbLigne2=Integer.parseInt(param2[1]);
                                                                    for (int i=2;i<=nbLigne2+1;i++)
                                                                    {
                                                                         System.out.println(param2[i]);
                                                                    }
                                                                    
                                                                    
                                                                    break;
                                                                case "3" : 
                                                                    requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_TRI_COMPETENCE";
                                                                    fluxSortieSocket.println(requete);	
                                                                    String retourAffichageListeProfilEtudiantTriCompetence = fluxEntreeSocket.readLine();
                                                                    System.out.println("Reponse du serveur : "+retourAffichageListeProfilEtudiantTriCompetence); 

                                                                    String[] param3= retourAffichageListeProfilEtudiantTriCompetence.split("  ");
                                                                    int nbLigne3=Integer.parseInt(param3[1]);
                                                                    for (int i=2;i<=nbLigne3+1;i++)
                                                                    {
                                                                         System.out.println(param3[i]);
                                                                    }   
                                                                    break;                                                                    
                                                            }
                                                            break;


                                                case "4" : //Recherche d'informations sur un étudiant (droits utilisateur)
                                                            System.out.println("==============================================================");
                                                            System.out.println("---------------------- Projet Connect ! ----------------------");
                                                            System.out.println("==============================================================");
                                                            System.out.println("Rechercher par nom (1), prenom (2), mail (3), compténce (4) ? : ");
                                                            System.out.print("\u001B[31m> ");
                                                            String choix4 = entree.readLine();
                                                            switch (choix4) 
                                                            {
                                                                // Recherche par nom
                                                                case "1" : 
                                                                    System.out.println("Entrez le nom du profil étudiant recherché :");
                                                                    System.out.print("\u001B[31m> ");
                                                                    String nomRecherche = entree.readLine();
                                                                    requete = "RECHERCHE_ETUDIANT_NOM "+nomRecherche;
                                                                    fluxSortieSocket.println(requete);	      
                                                                    String retourRechercheNom = fluxEntreeSocket.readLine();	      
                                                                    System.out.println("Reponse du serveur : "+retourRechercheNom); 
                                                                    String [] param2 = retourRechercheNom.split("  ");
                                                                    int NbLigne1=Integer.parseInt(param2[1]);
                                                                    for (int i=2;i<=NbLigne1+1;i++)
                                                                    {
                                                                         System.out.println(param2[i]);
                                                                    }
                                                                    break;

                                                                // Recherche par prenom
                                                                case "2" : 
                                                                    System.out.println("Entrez le prenom du profil étudiant recherché :");
                                                                    System.out.print("\u001B[31m> ");
                                                                    String prenomRecherche = entree.readLine();
                                                                    requete = "RECHERCHE_ETUDIANT_PRENOM "+prenomRecherche;
                                                                    fluxSortieSocket.println(requete);	      
                                                                    String retourRecherchePrenom = fluxEntreeSocket.readLine();	      
                                                                    System.out.println("Reponse du serveur : "+retourRecherchePrenom); 
                                                                    String [] param3 = retourRecherchePrenom.split("  ");
                                                                    int NbLigne2=Integer.parseInt(param3[1]);
                                                                    for (int i=2;i<=NbLigne2+1;i++)
                                                                    {
                                                                         System.out.println(param3[i]);
                                                                    }
                                                                    break;

                                                                // Recherche par mail    
                                                                case "3" : 
                                                                    System.out.println("Entrez le mail du profil étudiant recherché :");
                                                                    System.out.print("\u001B[31m> ");
                                                                    String mailRecherche = entree.readLine();
                                                                    requete = "RECHERCHE_ETUDIANT_MAIL "+mailRecherche;
                                                                    fluxSortieSocket.println(requete);	      
                                                                    String retourRechercheMail = fluxEntreeSocket.readLine();	      
                                                                    System.out.println("Reponse du serveur : "+retourRechercheMail); 
                                                                    String [] param4 = retourRechercheMail.split("  ");
                                                                    int NbLigne3=Integer.parseInt(param4[1]);
                                                                    for (int i=2;i<=NbLigne3+1;i++)
                                                                    {
                                                                         System.out.println(param4[i]);
                                                                    }
                                                                    break;


                                                                // Recherche par comptence    
                                                                case "4" : 
                                                                    System.out.println("Entrez le numéro de la compétence recherchée (1-Réseau,2-Télécoms,3-Dévelopement) :");
                                                                    System.out.print("\u001B[31m> ");
                                                                    String competenceRecherche = entree.readLine();
                                                                    requete = "RECHERCHE_ETUDIANT_COMPETENCE "+competenceRecherche;
                                                                    fluxSortieSocket.println(requete);	      
                                                                    String retourCompetenceRecherche = fluxEntreeSocket.readLine();	      
                                                                    System.out.println("Reponse du serveur : "+retourCompetenceRecherche); 
                                                                    String [] param5 = retourCompetenceRecherche.split("  ");
                                                                    int NbLigne4=Integer.parseInt(param5[1]);
                                                                    for (int i=2;i<=NbLigne4+1;i++)
                                                                    {
                                                                         System.out.println(param5[i]);
                                                                    }
                                                                    break;
                                                            }
                                                            break;

                                                case "5" : //Modifier mot de passe
                                                            System.out.println("==============================================================");
                                                            System.out.println("---------------------- Projet Connect ! ----------------------");
                                                            System.out.println("==============================================================");
                                                            System.out.println("Entrez votre login :");
                                                            System.out.print("\u001B[31m> ");
                                                            String login = entree.readLine();
                                                            System.out.println("Mot de passe actuel :");
                                                            System.out.print("\u001B[31m> ");
                                                            String mdpActuel = entree.readLine();
                                                            System.out.println("Nouveau mot de passe :");
                                                            System.out.print("\u001B[31m> ");
                                                            String mdp1 = entree.readLine();
                                                            System.out.println("Confirmez votre nouveau mot de passe :");
                                                            System.out.print("\u001B[31m> ");
                                                            String mdp2 = entree.readLine();
                                                            if (mdp1.equals(mdp2))
                                                            {
                                                                requete = "CHANGE_MDP "+login+" "+mdpActuel+" "+mdp1+" "+id_compte;
                                                                fluxSortieSocket.println(requete);	      
                                                                String retourChangeMdp = fluxEntreeSocket.readLine();	      
                                                                System.out.println("Reponse du serveur : "+retourChangeMdp);
                                                            }
                                                    break;


                                                 // Si aucun case n'est détecté
                                                 default : exit(0);
                                            }
                                        }
                                    }
                                    break; 



                        // ------------------------------- UTILISATEUR ANONYME -----------------------    
                        case "3" :
                                    System.out.println("==============================================================");
                                    System.out.println("---------------------- Projet Connect ! ----------------------");
                                    System.out.println("==============================================================");
                                    System.out.println("1/ Afficher liste étudiant ");
                                    System.out.println("2/ Rechercher un étudiant (N°étudiant) ");
                                    System.out.println("3/ Exit ");
                                    // on lit le choix
                                    System.out.print("\u001B[31m> ");
                                    String choix5 = entree.readLine();
                                    switch (choix5)
                                    {

                                        case "1" : // Afficher la liste des profils étudiants avec des droits d'un utilisateur anonyme
                                                   System.out.println("==============================================================");
                                                   System.out.println("---------------------- Projet Connect ! ----------------------");
                                                   System.out.println("==============================================================");
                                                   requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_ANONYLE";
                                                   fluxSortieSocket.println(requete);	
                                                   String retourAffichageListeProfilEtudiantAnonyme = fluxEntreeSocket.readLine();
                                                   System.out.println("Reponse du serveur : "+retourAffichageListeProfilEtudiantAnonyme); 

                                                   String[] param1= retourAffichageListeProfilEtudiantAnonyme.split("  ");
                                                   int nbLigne=Integer.parseInt(param1[1]);
                                                   for (int i=2;i<=nbLigne+1;i++)
                                                   {
                                                        System.out.println(param1[i]);
                                                   }
                                                   break;


                                        case "2" :  //Recherche d'informations sur un étudiant (droits anonyme)
                                                    System.out.println("==============================================================");
                                                    System.out.println("---------------------- Projet Connect ! ----------------------");
                                                    System.out.println("==============================================================");
                                                    System.out.println("Rechercher par nom (1), prenom (2) : ");
                                                    System.out.print("\u001B[31m> ");
                                                    String choix6 = entree.readLine();
                                                    switch (choix6) 
                                                    {
                                                        // Recherche par nom
                                                        case "1" : 
                                                                System.out.println("Entrez le nom du profil étudiant recherché :");
                                                                System.out.print("\u001B[31m> ");
                                                                String nomRecherche = entree.readLine();
                                                                requete = "RECHERCHE_ETUDIANT_NOM_ANONYME "+nomRecherche;
                                                                fluxSortieSocket.println(requete);	      
                                                                String retourRechercheNom = fluxEntreeSocket.readLine();	      
                                                                System.out.println("Reponse du serveur : "+retourRechercheNom); 
                                                                String [] param6 = retourRechercheNom.split("  ");
                                                                int NbLigne1=Integer.parseInt(param6[1]);
                                                                for (int i=2;i<=NbLigne1+1;i++)
                                                                {
                                                                     System.out.println(param6[i]);
                                                                }
                                                                break;


                                                        // Recherche par prenom
                                                        case "2" : 
                                                                System.out.println("Entrez le prenom du profil étudiant recherché :");
                                                                System.out.print("\u001B[31m> ");
                                                                String prenomRecherche = entree.readLine();
                                                                requete = "RECHERCHE_ETUDIANT_PRENOM_ANONYME "+prenomRecherche;
                                                                fluxSortieSocket.println(requete);	      
                                                                String retourRecherchePrenom = fluxEntreeSocket.readLine();	      
                                                                System.out.println("Reponse du serveur : "+retourRecherchePrenom); 
                                                                String [] param7 = retourRecherchePrenom.split("  ");
                                                                int NbLigne2=Integer.parseInt(param7[1]);
                                                                for (int i=2;i<=NbLigne2+1;i++)
                                                                {
                                                                     System.out.println(param7[i]);
                                                                }
                                                                break;
                                                    }
                                                    break;

                                         // Pour quitter le mode Anonyme   
                                        case "3" : break;

                                        default : exit(0); 
                                    }
                        break;     
                        case "4": exit(0);
                        default : break;

                    }
                }
            }
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
