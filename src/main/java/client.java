/*
 * EchoClient.java
 *
 * Created on 6 septembre 2000, 11:11
 */
 
import java.io.*;
import static java.lang.System.exit;
import java.net.*;
import java.awt.Color;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;



public class client extends Object        
{   
/**
* @param args the command line arguments
     * @throws java.io.IOException
*/

    public static void main (String args[]) throws IOException, Exception
    {
        BufferedReader      fluxEntreeStandard;
        Socket              leSocket;
        PrintStream         fluxSortieSocket;
        BufferedReader      fluxEntreeSocket;
        PrintStream         fluxSortieSocket2;
        BufferedReader      fluxEntreeSocket2;
        int id_compte;
        Regex reg           =new Regex();
        Boolean verif       =null;
        Deconnexion de1     =new Deconnexion();
        InstantMessage IM   =new InstantMessage();
        Annuaire an1        =new Annuaire();
        client c=new client();
        
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
                    c.banniere(); //Affichage banniere programme
                    c.afficher("1/ Création de compte (Inscription)");
                    c.afficher("2/ Connexion (avec Compte))");
                    c.afficher("3/ Connexion (Anonyme)");
                    c.afficher("4/ Exit ");

                    // On lit le choix de l'utilisateur
                    c.chevron();
                    String choix1 = entree.readLine();
                    // En fonction de ce choix
                    switch (choix1)
                    {                   
                        // Création d'un compte pour utiliser l'application en mode "connecté"
                        case "1":
                                    c.banniere();
                                    c.afficher("Choisissez votre login pour l'inscription :");
                                    c.chevron();
                                    String loginInscription = entree.readLine();
                                    c.afficher("Entrez votre mot de passe :");
                                    c.chevron();
                                    String pwdInscription = entree.readLine();
                                    requete = "INSCRIPTION "+loginInscription+" "+pwdInscription + " Utilisateur";
                                    c.afficher(requete);
                                    fluxSortieSocket.println(requete);	

                                    String retourInscription = fluxEntreeSocket.readLine();	      
                                    c.afficher("Reponse du serveur : "+retourInscription); 
                                    break;
                                    
                        // ------------------------- UTILISATEUR CONNECTE --------------------------                      
                        // Connexion sur l'application avec un compte
                        case "2":
                                    c.banniere();
                                    c.afficher("Pour vous connecter, entrez votre login :");
                                    c.chevron();
                                    String loginConnexion = entree.readLine();
                                    c.afficher("Entrez votre mot de passe :");
                                    c.chevron();
                                    String pwdConnexion = entree.readLine();
                                    requete = "CONNEXION "+loginConnexion+" "+pwdConnexion;
                                    c.afficher(requete);
                                    fluxSortieSocket.println(requete);	      
                                    String retourConnexion = fluxEntreeSocket.readLine();	      
                                    c.afficher("Reponse du serveur : "+retourConnexion);
                                    String param [] = retourConnexion.split(" ");
                                    // Si la connexion a réussie
                                    if (param[0].equals("OKCONNEXION"))
                                    {
                                        // On récupere l'id du compte connecté
                                        id_compte = Integer.parseInt(param[1]);
                                        // Tant qu'il y a des actions en mode connecté
                                        while(true)
                                        {
                                            
                                            //Démarrer threadserver
                                            TchatServer ts= new TchatServer(id_compte);
                                            ts.start();
                                            
                                            //On affiche le menu principal
                                            c.banniere();
                                            c.afficher(" ////////// Veuillez tapez votre choix  \\\\\\\\\\\\\\");
                                            c.afficher("1/ Créer profil étudiant");
                                            c.afficher("2/ Modifier informations de mon profil étudiant");
                                            c.afficher("3/ Afficher la liste des étudiants");
                                            c.afficher("4/ Rechercher un étudiant");
                                            c.afficher("5/ Modifier mot de passe");
                                            c.afficher("6/ Afficher la liste des étudiants connectés");
                                            c.afficher("7/ Méssagerie Instantanée");
                                            c.afficher("8/ Boite de réception");
                                            c.afficher("9/ Exit");
                                           /* String resInsertPortClient=IM.InsertPortClient(id_compte,localPort);
                                            c.afficher("");
                                            c.afficher("> Insertion Port Client : "+resInsertPortClient+" portClient= "+localPort);*/ //port=th.getlocalport
                                            //ON lit le choix
                                            c.chevron();
                                            String choix2 = entree.readLine();
                                            switch (choix2) 
                                            {
                                                // Création d'un profil étudiant
                                                case "1" :
                                                    
                                                    c.banniere();
                                                    c.afficher("Entrez votre NOM (MAJUSCULES):");
                                                    c.chevron();
                                                    String nom = entree.readLine();
                                                    Boolean verifNom=reg.RegexNomPrenom(nom);
                                                    c.afficher("Entrez votre Prenom :");
                                                    c.chevron();
                                                    String prenom = entree.readLine();
                                                    Boolean verifPrenom=reg.RegexNomPrenom(prenom);
                                                    c.afficher("Entrez votre date de naissance (JJ/MM/YYYY) :");
                                                    c.chevron();
                                                    String date_naissance = entree.readLine();
                                                    Boolean verifDate=reg.RegexDate(date_naissance);
                                                    c.afficher("Voulez-vous afficher votre date de naissance? 1-Oui, 0-Non :");
                                                    Boolean v_date_naissance=null;
                                                    c.chevron();
                                                    if ("1".equals(entree.readLine()))
                                                    {
                                                        v_date_naissance=true;   
                                                    }
                                                    else
                                                    {
                                                        v_date_naissance=false;
                                                    }
                                                    c.afficher("Entrez votre mail :");
                                                    c.chevron();
                                                    String mail = entree.readLine();
                                                    Boolean verifMail=reg.RegexMail(mail);
                                                    c.afficher("Voulez-vous afficher votre adresse mail? 1-Oui, 0-Non :");
                                                    Boolean v_mail=null;
                                                    c.chevron();
                                                    if ("1".equals(entree.readLine()))
                                                    {
                                                        v_mail=true;   
                                                    }
                                                    else
                                                    {
                                                        v_mail=false;
                                                    }
                                                    c.afficher("Entrez votre telephone :");
                                                    c.chevron();
                                                    String telephone = entree.readLine();
                                                    Boolean verifTel=reg.RegexTel(telephone);
                                                    c.afficher("Voulez-vous afficher votre numéro de Téléphone? 1-Oui, 0-Non :");
                                                    c.chevron();
                                                    Boolean v_tel=null;
                                                    if ("1".equals(entree.readLine()))
                                                    {
                                                        v_tel=true;   
                                                    }
                                                    else
                                                    {
                                                        v_tel=false;
                                                    }
                                                    c.afficher("Entrez votre numéro de competence : 1-Réseaux, 2-Télécoms, 3 Dévélopement : ");
                                                    c.chevron();
                                                    String id_competence = entree.readLine();
                                                    c.afficher("verifmail :"+verifMail+" mail: "+mail);
                                                    c.afficher("verifdate: "+verifDate+" date: "+date_naissance);
                                                    c.afficher("verifTel: "+verifTel+" tel: "+telephone);
                                                    c.afficher("verifPrenom: "+verifPrenom+"prenom : "+prenom);
                                                    c.afficher("verifNom: "+verifNom+"nom : "+nom);
                                                    c.afficher("");
                                                    c.afficher("");
                                                    if ((verifMail!=false) && (verifDate!=false) && (verifTel!=false) && (verifPrenom!=false) && (verifNom!=false))
                                                    {
                                                    requete = "CREATION_PROFIL "+nom+" "+prenom+" "+date_naissance+" "+mail+" "+telephone+" "+id_competence+" "+id_compte;
                                                    c.afficher(requete);
                                                    fluxSortieSocket.println(requete);	      
                                                    String retourCreationProfil = fluxEntreeSocket.readLine();	      
                                                    c.afficher("Reponse du serveur : "+retourCreationProfil); 
                                                    requete = "CONFIDENTIALITE "+id_compte+" "+v_date_naissance+" "+v_mail+" "+v_tel;
                                                    c.afficher(requete);
                                                    fluxSortieSocket.println(requete);	      
                                                    String retourConfidentialite = fluxEntreeSocket.readLine();	      
                                                    c.afficher("Reponse du serveur : "+retourConfidentialite); 
                                                    }
                                                    else
                                                    {
                                                        if(verifMail!=true)
                                                        {
                                                            c.afficher("Erreur saisie Mail. Format=xxxxx@xxxx.xx");
                                                        }
                                                        if(verifDate!=true)
                                                        {
                                                            c.afficher("Erreur saisie Date. Format= xx/xx/xxxx");
                                                        }
                                                        if(verifTel!=true)
                                                        {
                                                            c.afficher("Erreur saisie Numéro de téléphone");
                                                        }
                                                        if(verifPrenom!=true)
                                                        {
                                                            c.afficher("Erreur saisie Prénom");
                                                        }
                                                         if(verifNom!=true)
                                                        {
                                                            c.afficher("Erreur saisie Nom: Entrez le NOM en MAJUSCULES");
                                                        }
                                                    }
                                                    break;
                                                    

                                                //Modification informations profil étudiant  
                                                case "2" :
                                                    c.banniere();;

                                                    requete = "AFFICHER_PROFIL_COMPTE "+id_compte;
                                                    c.afficher(requete);
                                                    fluxSortieSocket.println(requete);	      
                                                    String retourAfficherProfilCompte = fluxEntreeSocket.readLine();
                                                    c.banniere();
                                                    c.afficher("Que souhaitez vous modifier ? :");
                                                    c.afficher("1 - Votre mail ? ");
                                                    c.afficher("2 - Votre numero de telephone ? ");
                                                    c.afficher("3 - Vos compétences ? ");
                                                    c.afficher("4 - Vos paramètre de confidentialité ? ");
                                                    c.afficher("Voici vos informations actuelles : ");
                                                    c.afficher(retourAfficherProfilCompte);
                                                    c.afficher(" ////////// Veuillez entrer votre choix  \\\\\\\\\\\\\\");
                                                    c.chevron();
                                                    String choix3 = entree.readLine();
                                                    switch (choix3) 
                                                    {
                                                        // Modification du mail 
                                                        case "1" :
                                                            c.banniere();
                                                            c.afficher("Quel est votre nouveau mail ? :");
                                                            c.chevron();
                                                            String nouveauMail = entree.readLine();
                                                            verifMail=reg.RegexMail(nouveauMail);
                                                            if ((verifMail!=false))
                                                            {
                                                            requete = "MODIFICATION_MAIL "+nouveauMail+" "+id_compte;
                                                            fluxSortieSocket.println(requete);	      
                                                            String retourModificationMail = fluxEntreeSocket.readLine();	      
                                                            c.afficher("Reponse du serveur : "+retourModificationMail); 
                                                            }
                                                            else
                                                            {
                                                            c.afficher("Erreur saisie Mail. Format=xxxxx@xxxx.xx");
                                                            }
                                                             
                                                            break;

                                                        // Modification du numéro de téléphone   
                                                        case "2" :
                                                            c.banniere();
                                                            c.afficher("Quel est votre nouveau numéro de téléphone ? :");
                                                            c.chevron();
                                                            String nouveauTelephone = entree.readLine();
                                                             
                                                            verifTel=reg.RegexTel(nouveauTelephone);
                                                            if ((verifTel!=false))
                                                            {
                                                            requete = "MODIFICATION_TEL "+nouveauTelephone+" "+id_compte;
                                                            fluxSortieSocket.println(requete);	      
                                                            String retourModificationTel = fluxEntreeSocket.readLine();	      
                                                            c.afficher("Reponse du serveur : "+retourModificationTel);
                                                            }
                                                            else
                                                            {
                                                            c.afficher("Erreur saisie n° Téléphone");
                                                            }
                                                            break;

                                                        // Modification des compétences  
                                                        case "3" :
                                                            c.banniere();
                                                            c.afficher("Nouvelle competence ? (1-Réseaux, 2-Telecoms, 3-Developement) :");
                                                            c.chevron();
                                                            String nouveleCompetence = entree.readLine();
                                                            requete = "MODIFICATION_COMPETENCE "+nouveleCompetence+" "+id_compte;
                                                            fluxSortieSocket.println(requete);	      
                                                            String retourModificationCompetence = fluxEntreeSocket.readLine();	      
                                                            c.afficher("Reponse du serveur : "+retourModificationCompetence); 
                                                            break;
                                                            
                                                        case "4" : //Maj Paramètres de confidentialité
                                                            c.banniere();
                                                            c.afficher("Voulez-vous afficher votre date de naissance? 1-Oui, 0-Non :");
                                                            v_date_naissance=null;
                                                            c.chevron();
                                                            if ("1".equals(entree.readLine()))
                                                            {
                                                                v_date_naissance=true;   
                                                            }
                                                            else
                                                            {
                                                                v_date_naissance=false;
                                                            }
                                                            c.afficher("Voulez-vous afficher votre adresse mail? 1-Oui, 0-Non :");
                                                            v_mail=null;
                                                            c.chevron();
                                                            if ("1".equals(entree.readLine()))
                                                            {
                                                                v_mail=true;   
                                                            }
                                                            else
                                                            {
                                                                v_mail=false;
                                                            }
                                                            c.afficher("Voulez-vous afficher votre numéro de Téléphone? 1-Oui, 0-Non :");
                                                            v_tel=null;
                                                            c.chevron();
                                                            if ("1".equals(entree.readLine()))
                                                            {
                                                                v_tel=true;   
                                                            }
                                                            else
                                                            {
                                                                v_tel=false;
                                                            }
                                                                    requete = "MAJ_CONFIDENTIALITE "+id_compte+" "+v_date_naissance+" "+v_mail+" "+v_tel;
                                                                    c.afficher(requete);
                                                                    fluxSortieSocket.println(requete);	      
                                                                    String retourMajConfidentialite = fluxEntreeSocket.readLine();	      
                                                                    c.afficher("Reponse du serveur : "+retourMajConfidentialite); 
                                                                    break;    
                                                            }
                                                            break;


                                                case "3" : //Afficher la liste des profils étudiants (droits utilisateur)
                                                            c.banniere();
                                                            c.afficher("Afficher la liste des profils triée par nom (1), prenom(2), competence (3) :");
                                                            c.chevron();
                                                            String choixTriListe = entree.readLine();
                                                            switch (choixTriListe) 
                                                            {
                                                                // Tri par nom
                                                                case "1" :               
                                                                    requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_TRI_NOM";
                                                                    fluxSortieSocket.println(requete);	
                                                                    String retourAffichageListeProfilEtudiantTriNom = fluxEntreeSocket.readLine();
                                                                    c.afficher("Reponse du serveur : "+retourAffichageListeProfilEtudiantTriNom); 

                                                                    String[] param1= retourAffichageListeProfilEtudiantTriNom.split("  ");
                                                                    int nbLigne=Integer.parseInt(param1[1]);
                                                                    for (int i=2;i<=nbLigne+1;i++)
                                                                    {
                                                                         c.afficher(param1[i]);
                                                                    }
                                                                    
                                                                    break;
                                                                case "2" : 
                                                                    requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_TRI_PRENOM";
                                                                    fluxSortieSocket.println(requete);	
                                                                    String retourAffichageListeProfilEtudiantTriPrenom = fluxEntreeSocket.readLine();
                                                                    c.afficher("Reponse du serveur : "+retourAffichageListeProfilEtudiantTriPrenom); 

                                                                    String[] param2= retourAffichageListeProfilEtudiantTriPrenom.split("  ");
                                                                    int nbLigne2=Integer.parseInt(param2[1]);
                                                                    for (int i=2;i<=nbLigne2+1;i++)
                                                                    {
                                                                         c.afficher(param2[i]);
                                                                    }
                                                                    
                                                                    
                                                                    break;
                                                                case "3" : 
                                                                    requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_TRI_COMPETENCE";
                                                                    fluxSortieSocket.println(requete);	
                                                                    String retourAffichageListeProfilEtudiantTriCompetence = fluxEntreeSocket.readLine();
                                                                    c.afficher("Reponse du serveur : "+retourAffichageListeProfilEtudiantTriCompetence); 

                                                                    String[] param3= retourAffichageListeProfilEtudiantTriCompetence.split("  ");
                                                                    int nbLigne3=Integer.parseInt(param3[1]);
                                                                    for (int i=2;i<=nbLigne3+1;i++)
                                                                    {
                                                                         c.afficher(param3[i]);
                                                                    }   
                                                                    break;                                                                    
                                                            }
                                                            break;


                                                case "4" :  //Recherche d'informations sur un étudiant (droits utilisateur)
                                                            c.banniere();
                                                            c.afficher("Rechercher par nom (1), prenom (2), mail (3), compténce (4) ? : ");
                                                            c.chevron();
                                                            String choix4 = entree.readLine();
                                                            switch (choix4) 
                                                            {
                                                                // Recherche par nom
                                                                case "1" : 
                                                                    c.afficher("Entrez le nom du profil étudiant recherché :");
                                                                    c.chevron();
                                                                    String nomRecherche = entree.readLine();
                                                                    requete = "RECHERCHE_ETUDIANT_NOM "+nomRecherche;
                                                                    fluxSortieSocket.println(requete);	      
                                                                    String retourRechercheNom = fluxEntreeSocket.readLine();	      
                                                                    c.afficher("Reponse du serveur : "+retourRechercheNom); 
                                                                    String [] param2 = retourRechercheNom.split("  ");
                                                                    int NbLigne1=Integer.parseInt(param2[1]);
                                                                    for (int i=2;i<=NbLigne1+1;i++)
                                                                    {
                                                                         c.afficher(param2[i]);
                                                                    }
                                                                    break;

                                                                // Recherche par prenom
                                                                case "2" : 
                                                                    c.afficher("Entrez le prenom du profil étudiant recherché :");
                                                                    c.chevron();
                                                                    String prenomRecherche = entree.readLine();
                                                                    requete = "RECHERCHE_ETUDIANT_PRENOM "+prenomRecherche;
                                                                    fluxSortieSocket.println(requete);	      
                                                                    String retourRecherchePrenom = fluxEntreeSocket.readLine();	      
                                                                    c.afficher("Reponse du serveur : "+retourRecherchePrenom); 
                                                                    String [] param3 = retourRecherchePrenom.split("  ");
                                                                    int NbLigne2=Integer.parseInt(param3[1]);
                                                                    for (int i=2;i<=NbLigne2+1;i++)
                                                                    {
                                                                         c.afficher(param3[i]);
                                                                    }
                                                                    break;

                                                                // Recherche par mail    
                                                                case "3" : 
                                                                    c.afficher("Entrez le mail du profil étudiant recherché :");
                                                                    c.chevron();
                                                                    String mailRecherche = entree.readLine();
                                                                    requete = "RECHERCHE_ETUDIANT_MAIL "+mailRecherche;
                                                                    fluxSortieSocket.println(requete);	      
                                                                    String retourRechercheMail = fluxEntreeSocket.readLine();	      
                                                                    c.afficher("Reponse du serveur : "+retourRechercheMail); 
                                                                    String [] param4 = retourRechercheMail.split("  ");
                                                                    int NbLigne3=Integer.parseInt(param4[1]);
                                                                    for (int i=2;i<=NbLigne3+1;i++)
                                                                    {
                                                                         c.afficher(param4[i]);
                                                                    }
                                                                    break;


                                                                // Recherche par comptence    
                                                                case "4" : 
                                                                    c.afficher("Entrez le numéro de la compétence recherchée (1-Réseau,2-Télécoms,3-Dévelopement) :");
                                                                    c.chevron();
                                                                    String competenceRecherche = entree.readLine();
                                                                    requete = "RECHERCHE_ETUDIANT_COMPETENCE "+competenceRecherche;
                                                                    fluxSortieSocket.println(requete);	      
                                                                    String retourCompetenceRecherche = fluxEntreeSocket.readLine();	      
                                                                    c.afficher("Reponse du serveur : "+retourCompetenceRecherche); 
                                                                    String [] param5 = retourCompetenceRecherche.split("  ");
                                                                    int NbLigne4=Integer.parseInt(param5[1]);
                                                                    for (int i=2;i<=NbLigne4+1;i++)
                                                                    {
                                                                         c.afficher(param5[i]);
                                                                    }
                                                                    break;
                                                            }
                                                            break;

                                                case "5" : //Modifier mot de passe
                                                            c.banniere();
                                                            c.afficher("Entrez votre login :");
                                                            c.chevron();
                                                            String login = entree.readLine();
                                                            c.afficher("Mot de passe actuel :");
                                                            c.chevron();
                                                            String mdpActuel = entree.readLine();
                                                            c.afficher("Nouveau mot de passe :");
                                                            c.chevron();
                                                            String mdp1 = entree.readLine();
                                                            c.afficher("Confirmez votre nouveau mot de passe :");
                                                            c.chevron();
                                                            String mdp2 = entree.readLine();
                                                            if (mdp1.equals(mdp2))
                                                            {
                                                                requete = "CHANGE_MDP "+login+" "+mdpActuel+" "+mdp1+" "+id_compte;
                                                                fluxSortieSocket.println(requete);	      
                                                                String retourChangeMdp = fluxEntreeSocket.readLine();	      
                                                                c.afficher("Reponse du serveur : "+retourChangeMdp);
                                                            }
                                                    break;
                                                    
                                                case "6" :  
                                                    c.banniere();
                                                    requete = "CONNECTE";
                                                    fluxSortieSocket.println(requete);	
                                                    String retourAfficherListeConnecte= fluxEntreeSocket.readLine();
                                                    c.afficher("Reponse du serveur : "+retourAfficherListeConnecte); 

                                                    String[] param1= retourAfficherListeConnecte.split("  ");
                                                    int nbLigne=Integer.parseInt(param1[1]);
                                                    for (int i=2;i<=nbLigne+1;i++)
                                                    {
                                                         c.afficher(param1[i]);
                                                    }
                                                    break;
                                                   
                                                case "7" :  
                                                    c.banniere();
                                                    System.out.println();
                                                    requete = "ETATCONNECTE "+id_compte;
                                                    fluxSortieSocket.println(requete);	
                                                    retourAfficherListeConnecte= fluxEntreeSocket.readLine();
                                                    c.afficher("Reponse du serveur : "+retourAfficherListeConnecte);
                                                    c.afficher("Etudiant connecté: ");
                                                    requete = "CONNECTE";
                                                    fluxSortieSocket.println(requete);	
                                                    retourAfficherListeConnecte= fluxEntreeSocket.readLine();
                                                    c.afficher("Reponse du serveur : "+retourAfficherListeConnecte); 
                                                    param1= retourAfficherListeConnecte.split("  ");
                                                    nbLigne=Integer.parseInt(param1[1]);
                                                    for (int i=2;i<=nbLigne+1;i++)
                                                    {
                                                         c.afficher(param1[i]);
                                                    }
                                                    c.afficher("A quel étudiant souhaitez-vous parler?");

                                                    System.out.print("\u001B[31m> Numero Etudiant? :");
                                                    int numEtudiant=Integer.parseInt(entree.readLine());
                                                    int iddest=an1.numIdCompte(numEtudiant);
                                                    ThreadTchat t;
                                                    try 
                                                    {
                                                        t = ts.getTchat(iddest);
                                                        c.afficher("getTchat = "+t);
                                                    } 
                                                    catch (TchatInconnuException ex) 
                                                    {
                                                        int PortClientDest=IM.recupPortClient(iddest);
                                                        InetAddress AdressClientDest=InetAddress.getByName("127.0.0.1");
                                                        c.afficher("InetAddress AdressClientDest = "+AdressClientDest);
                                                        c.afficher("InetAddress PortClientDest = "+PortClientDest);
                                                        t = ts.ajoutTchat(iddest, AdressClientDest /*destination*/, PortClientDest /*destination*/);
                                                        c.afficher("TchatInconnuException = "+t);
                                                    }
                                                    //Recuperation nom prenom pour mettre avant le message envoyer et informé l'autre client de qui lui parle
                                                    String identité=IM.recupIdentiteClient(id_compte);                                                    // apres cas 1 et 2
                                                    int conversation=1;
                                                    while(conversation==1)
                                                    {
                                                        System.out.print("\u001B[31m> Entree votre message :");
                                                        String message=entree.readLine();
                                                        c.afficher(message);
                                                        int numEtudiantClient=an1.numEtudiant(id_compte); //Récupère le n°Etudiant de la personne connecté
                                                        t.envoie(identité+" (n°"+numEtudiantClient+") : "+message);
                                                        c.afficher("Exit? 1/oui, 2/non");
                                                        String quitter = entree.readLine();
                                                        if(quitter.equals("1"))
                                                        {
                                                            conversation=0;
                                                        }
                                                        
                                                     }
                                                    
                                                   //Ici fichier ancien truc de messagerie instannanté client.java
                                                    break;   
                                                    
                                                /*case "7" : //Suppresion Profil*/
                                                    
                                                case "8" : 
                                                    numEtudiant=an1.numEtudiant(id_compte);
                                                    c.banniere();
                                                    c.afficher("1/ Lire Message");
                                                    c.afficher("2/ Envoyer Message");
                                                    c.chevron();
                                                    String choix5=entree.readLine();
                                                    switch (choix5)
                                                            {
                                                              case "1" :
                                                                    requete="LIREMESSAGE "+numEtudiant;
                                                                    fluxSortieSocket.println(requete);	      
                                                                    String retourLireMessage = fluxEntreeSocket.readLine();	      
                                                                    c.afficher("Reponse du serveur : "+retourLireMessage); 
                                                                    String [] param3 = retourLireMessage.split("  ");
                                                                    int NbMessage=Integer.parseInt(param3[1]);
                                                                    c.afficher("Vous avez "+NbMessage+" nouveau(x) message(s)");
                                                                    for (int i=2;i<=NbMessage+1;i++)
                                                                    {
                                                                         c.afficher(param3[i]);
                                                                    }
                                                                break;  
                                                                case "2" :
                                                                    
                                                                    c.afficher("1/ Afficher la liste des étudiants");
                                                                    c.afficher("2/ Rechercher un étudiant");
                                                                    c.chevron();
                                                                    String choix6=entree.readLine();
                                                                    switch (choix6)
                                                                    {
                                                                        case "1":
                                                                        requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_TRI_NOM";
                                                                        fluxSortieSocket.println(requete);	
                                                                        String retourAffichageListeProfilEtudiantTriNom = fluxEntreeSocket.readLine();
                                                                        c.afficher("Reponse du serveur : "+retourAffichageListeProfilEtudiantTriNom); 

                                                                        param1= retourAffichageListeProfilEtudiantTriNom.split("  ");
                                                                        nbLigne=Integer.parseInt(param1[1]);
                                                                        for (int i=2;i<=nbLigne+1;i++)
                                                                        {
                                                                            c.afficher(param1[i]);
                                                                        } 
                                                                        c.afficher("A qui voulez vous parler (n°Etudiant)");
                                                                        c.chevron();
                                                                        int numEtudiantDest=Integer.parseInt(entree.readLine());
                                                                        int numEtudiantExpediteur=an1.numEtudiant(id_compte);
                                                                        c.afficher("Entrez votre message");
                                                                        c.chevron();
                                                                        String message=entree.readLine();
                                                                        requete = "ENVOYERMESSAGEDIFF "+numEtudiantExpediteur+" "+numEtudiantDest+" "+message;
                                                                        fluxSortieSocket.println(requete);	
                                                                        String envoyerMessageDiff = fluxEntreeSocket.readLine();
                                                                        c.afficher("Reponse du serveur : "+envoyerMessageDiff); 
                                                                        break; 
                                                                        
                                                                        case "2":
                                                                                c.banniere();
                                                                                c.afficher("Rechercher par nom (1), prenom (2), mail (3), compténce (4) ? : ");
                                                                                c.chevron();
                                                                                String choix7 = entree.readLine();
                                                                                switch (choix7) 
                                                                                {
                                                                                    // Recherche par nom
                                                                                    case "1" : 
                                                                                        c.afficher("Entrez le nom du profil étudiant recherché :");
                                                                                        c.chevron();
                                                                                        String nomRecherche = entree.readLine();
                                                                                        requete = "RECHERCHE_ETUDIANT_NOM "+nomRecherche;
                                                                                        fluxSortieSocket.println(requete);	      
                                                                                        String retourRechercheNom = fluxEntreeSocket.readLine();	      
                                                                                        c.afficher("Reponse du serveur : "+retourRechercheNom); 
                                                                                        String [] param2 = retourRechercheNom.split("  ");
                                                                                        int NbLigne1=Integer.parseInt(param2[1]);
                                                                                        for (int i=2;i<=NbLigne1+1;i++)
                                                                                        {
                                                                                             c.afficher(param2[i]);
                                                                                        }
                                                                                        c.afficher("A qui voulez vous parler (n°Etudiant)");
                                                                                        c.chevron();
                                                                                        numEtudiantDest=Integer.parseInt(entree.readLine());
                                                                                        numEtudiantExpediteur=an1.numEtudiant(id_compte);
                                                                                        c.afficher("Entrez votre message");
                                                                                        c.chevron();
                                                                                        message=entree.readLine();
                                                                                        requete = "ENVOYERMESSAGEDIFF "+numEtudiantExpediteur+" "+numEtudiantDest+" "+message;
                                                                                        fluxSortieSocket.println(requete);	
                                                                                        envoyerMessageDiff = fluxEntreeSocket.readLine();
                                                                                        c.afficher("Reponse du serveur : "+envoyerMessageDiff); 
                                                                                        break;

                                                                                    // Recherche par prenom
                                                                                    case "2" : 
                                                                                        c.afficher("Entrez le prenom du profil étudiant recherché :");
                                                                                        c.chevron();
                                                                                        String prenomRecherche = entree.readLine();
                                                                                        requete = "RECHERCHE_ETUDIANT_PRENOM "+prenomRecherche;
                                                                                        fluxSortieSocket.println(requete);	      
                                                                                        String retourRecherchePrenom = fluxEntreeSocket.readLine();	      
                                                                                        c.afficher("Reponse du serveur : "+retourRecherchePrenom); 
                                                                                        param3 = retourRecherchePrenom.split("  ");
                                                                                        int NbLigne2=Integer.parseInt(param3[1]);
                                                                                        for (int i=2;i<=NbLigne2+1;i++)
                                                                                        {
                                                                                             c.afficher(param3[i]);
                                                                                        }
                                                                                        c.afficher("A qui voulez vous parler (n°Etudiant)");
                                                                                        c.chevron();
                                                                                        numEtudiantDest=Integer.parseInt(entree.readLine());
                                                                                        numEtudiantExpediteur=an1.numEtudiant(id_compte);
                                                                                        c.afficher("Entrez votre message");
                                                                                        c.chevron();
                                                                                        message=entree.readLine();
                                                                                        requete = "ENVOYERMESSAGEDIFF "+numEtudiantExpediteur+" "+numEtudiantDest+" "+message;
                                                                                        fluxSortieSocket.println(requete);	
                                                                                        envoyerMessageDiff = fluxEntreeSocket.readLine();
                                                                                        c.afficher("Reponse du serveur : "+envoyerMessageDiff); 
                                                                                        break;

                                                                                    // Recherche par mail    
                                                                                    case "3" : 
                                                                                        c.afficher("Entrez le mail du profil étudiant recherché :");
                                                                                        c.chevron();
                                                                                        String mailRecherche = entree.readLine();
                                                                                        requete = "RECHERCHE_ETUDIANT_MAIL "+mailRecherche;
                                                                                        fluxSortieSocket.println(requete);	      
                                                                                        String retourRechercheMail = fluxEntreeSocket.readLine();	      
                                                                                        c.afficher("Reponse du serveur : "+retourRechercheMail); 
                                                                                        String [] param4 = retourRechercheMail.split("  ");
                                                                                        int NbLigne3=Integer.parseInt(param4[1]);
                                                                                        for (int i=2;i<=NbLigne3+1;i++)
                                                                                        {
                                                                                             c.afficher(param4[i]);
                                                                                        }
                                                                                        c.afficher("A qui voulez vous parler (n°Etudiant)");
                                                                                        c.chevron();
                                                                                        numEtudiantDest=Integer.parseInt(entree.readLine());
                                                                                        numEtudiantExpediteur=an1.numEtudiant(id_compte);
                                                                                        c.afficher("Entrez votre message");
                                                                                        c.chevron();
                                                                                        message=entree.readLine();
                                                                                        requete = "ENVOYERMESSAGEDIFF "+numEtudiantExpediteur+" "+numEtudiantDest+" "+message;
                                                                                        fluxSortieSocket.println(requete);	
                                                                                        envoyerMessageDiff = fluxEntreeSocket.readLine();
                                                                                        c.afficher("Reponse du serveur : "+envoyerMessageDiff); 
                                                                                        break;


                                                                                    // Recherche par comptence    
                                                                                    case "4" : 
                                                                                        c.afficher("Entrez le numéro de la compétence recherchée (1-Réseau,2-Télécoms,3-Dévelopement) :");
                                                                                        c.chevron();
                                                                                        String competenceRecherche = entree.readLine();
                                                                                        requete = "RECHERCHE_ETUDIANT_COMPETENCE "+competenceRecherche;
                                                                                        fluxSortieSocket.println(requete);	      
                                                                                        String retourCompetenceRecherche = fluxEntreeSocket.readLine();	      
                                                                                        c.afficher("Reponse du serveur : "+retourCompetenceRecherche); 
                                                                                        String [] param5 = retourCompetenceRecherche.split("  ");
                                                                                        int NbLigne4=Integer.parseInt(param5[1]);
                                                                                        for (int i=2;i<=NbLigne4+1;i++)
                                                                                        {
                                                                                             c.afficher(param5[i]);
                                                                                        }
                                                                                        c.afficher("A qui voulez vous parler (n°Etudiant)");
                                                                                        c.chevron();
                                                                                        numEtudiantDest=Integer.parseInt(entree.readLine());
                                                                                        numEtudiantExpediteur=an1.numEtudiant(id_compte);
                                                                                        c.afficher("Entrez votre message");
                                                                                        c.chevron();
                                                                                        message=entree.readLine();
                                                                                        requete = "ENVOYERMESSAGEDIFF "+numEtudiantExpediteur+" "+numEtudiantDest+" "+message;
                                                                                        fluxSortieSocket.println(requete);	
                                                                                        envoyerMessageDiff = fluxEntreeSocket.readLine();
                                                                                        c.afficher("Reponse du serveur : "+envoyerMessageDiff); 
                                                                                        
                                                                                        break;
                                                                                        
                                                                                        
                                                                                }
                                                                                break;
                                                                    }
                                                                    
                                                                    
                                                                break;  
                                                            }
                                                    
                                                    break;
                                                case "9" :  
                                                    de1.deconnexion(id_compte);
                                                    leSocket.close();
                                                    exit(0);
                                                    
                                                 // Si aucun case n'est détecté
                                                default :
                                                    de1.deconnexion(id_compte); 
                                                    leSocket.close();
                                                    exit(0);
                                            }
                                        }
                                    }
                                    break; 


                        // ------------------------------- UTILISATEUR ANONYME -----------------------    
                        case "3" :
                                while(true)
                                {
                                    c.banniere();
                                    c.afficher("1/ Afficher liste étudiant ");
                                    c.afficher("2/ Rechercher un étudiant ");
                                    c.afficher("3/ Exit ");
                                    // on lit le choix
                                    c.chevron();
                                    String choix5 = entree.readLine();
                                    switch (choix5)
                                    {

                                        case "1" : // Afficher la liste des profils étudiants avec des droits d'un utilisateur anonyme
                                                   c.banniere();
                                                   requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_ANONYLE";
                                                   fluxSortieSocket.println(requete);	
                                                   String retourAffichageListeProfilEtudiantAnonyme = fluxEntreeSocket.readLine();
                                                   c.afficher("Reponse du serveur : "+retourAffichageListeProfilEtudiantAnonyme); 

                                                   String[] param1= retourAffichageListeProfilEtudiantAnonyme.split("  ");
                                                   int nbLigne=Integer.parseInt(param1[1]);
                                                   for (int i=2;i<=nbLigne+1;i++)
                                                   {
                                                        c.afficher(param1[i]);
                                                   }
                                                   break;


                                        case "2" :  //Recherche d'informations sur un étudiant (droits anonyme)
                                                    c.banniere();
                                                    c.afficher("Rechercher par Nom (1), Prenom (2) : ");
                                                    c.chevron();
                                                    String choix6 = entree.readLine();
                                                    switch (choix6) 
                                                    {
                                                        // Recherche par nom
                                                        case "1" : 
                                                                c.afficher("Entrez le nom du profil étudiant recherché :");
                                                                c.chevron();
                                                                String nomRecherche = entree.readLine();
                                                                requete = "RECHERCHE_ETUDIANT_NOM_ANONYME "+nomRecherche;
                                                                fluxSortieSocket.println(requete);	      
                                                                String retourRechercheNom = fluxEntreeSocket.readLine();	      
                                                                c.afficher("Reponse du serveur : "+retourRechercheNom); 
                                                                String [] param6 = retourRechercheNom.split("  ");
                                                                int NbLigne1=Integer.parseInt(param6[1]);
                                                                for (int i=2;i<=NbLigne1+1;i++)
                                                                {
                                                                     c.afficher(param6[i]);
                                                                }
                                                                break;


                                                        // Recherche par prenom
                                                        case "2" : 
                                                                c.afficher("Entrez le prenom du profil étudiant recherché :");
                                                                c.chevron();
                                                                String prenomRecherche = entree.readLine();
                                                                requete = "RECHERCHE_ETUDIANT_PRENOM_ANONYME "+prenomRecherche;
                                                                fluxSortieSocket.println(requete);	      
                                                                String retourRecherchePrenom = fluxEntreeSocket.readLine();	      
                                                                c.afficher("Reponse du serveur : "+retourRecherchePrenom); 
                                                                String [] param7 = retourRecherchePrenom.split("  ");
                                                                int NbLigne2=Integer.parseInt(param7[1]);
                                                                for (int i=2;i<=NbLigne2+1;i++)
                                                                {
                                                                     c.afficher(param7[i]);
                                                                }
                                                                
                                                                break;
                                                    }
                                                    break;

                                         // Pour quitter le mode Anonyme   
                                        case "3" : exit(0);
                                    
                                        default : exit(0); 
                                    
                                    }
                                }
                           
                        case "4":   
                            exit(0);
                            leSocket.close();
                        
                        
                        case "admin9876" :
                            c.banniere();
                            c.afficher("Entrez votre login :");
                            c.chevron();
                            loginConnexion = entree.readLine();
                            c.afficher("Entrez votre mot de passe :");
                            c.chevron();
                            pwdConnexion = entree.readLine();
                            requete = "CONNEXIONADMIN "+loginConnexion+" "+pwdConnexion;
                            c.afficher(requete);
                            fluxSortieSocket.println(requete);	      
                            retourConnexion = fluxEntreeSocket.readLine();	      
                            c.afficher("Reponse du serveur : "+retourConnexion);
                            String param4 [] = retourConnexion.split(" ");
                            // Si la connexion a réussie
                            if (param4[0].equals("OKCONNEXIONADMIN"))
                            {   
                               
                                while(true)
                                {
                                c.banniere();
                                c.afficher("1-Création compte"); 
                                c.afficher("2-Modification mot de passe");
                                c.afficher("3-Création profil");
                                c.afficher("4-Modification profil");
                                c.afficher("5-Affichage liste"); 
                                c.afficher("6-Recherche");
                                c.afficher("7-Exit");
                                c.chevron();
                                String choix = entree.readLine();
                                switch (choix)
                                {
                                    case "1": //1-Création compte
                                    c.banniere(); 
                                    c.afficher("Choisissez votre login pour l'inscription :");
                                    c.chevron();
                                    loginInscription = entree.readLine();
                                    c.afficher("Entrez votre mot de passe :");
                                    c.chevron();
                                    pwdInscription = entree.readLine();
                                    requete = "INSCRIPTION "+loginInscription+" "+pwdInscription + " Utilisateur";
                                    c.afficher(requete);
                                    fluxSortieSocket.println(requete);
                                    retourInscription = fluxEntreeSocket.readLine();	      
                                    c.afficher("Reponse du serveur : "+retourInscription); 
                                    break;
                                    
                                    case "2": //2-Modification mot de passe
                                    c.banniere();
                                    c.afficher("Entrez le login :");
                                    c.chevron();
                                    String login = entree.readLine();
                                    c.afficher("Nouveau mot de passe :");
                                    c.chevron();
                                    String mdp1 = entree.readLine();
                                    c.afficher("Confirmez le nouveau mot de passe :");
                                    c.chevron();
                                    String mdp2 = entree.readLine();
                                    if (mdp1.equals(mdp2))
                                    {
                                        requete = "CHANGE_MDP_ADMIN "+login+" "+mdp1;
                                        fluxSortieSocket.println(requete);	      
                                        String retourChangeMdp = fluxEntreeSocket.readLine();	      
                                        c.afficher("Reponse du serveur : "+retourChangeMdp);
                                    }
                                    break;
                                    
                                    case "3": // 3-Création profil
                                    c.banniere();
                                    c.afficher("Entrez le login du compte pour lequel vous voulez créer un profil:");
                                    c.chevron();
                                    String logAdminCompte = entree.readLine();
                                    requete = "RECUP_ID_COMPTE_ADMIN "+logAdminCompte;
                                    c.afficher(requete);
                                    fluxSortieSocket.println(requete);        
                                    String retourlogAdminCompte = fluxEntreeSocket.readLine();        
                                    c.afficher("Reponse du serveur : "+retourlogAdminCompte);
                                    String param5 [] = retourlogAdminCompte.split(" ");
                                    int id_compteProfilAdministre;
                                    id_compteProfilAdministre=Integer.parseInt(param5[1]);
                                    c.afficher("param5[0]:"+param5[0]);
                                    if (param5[0].equals("OK_RECUP_ID_COMPTE_ADMIN"))
                                    {
                                        c.afficher("Entrez votre NOM (MAJUSCULES):");
                                        c.chevron();
                                        String nom = entree.readLine();
                                        Boolean verifNom=reg.RegexNomPrenom(nom);
                                        c.afficher("Entrez votre Prenom :");
                                        c.chevron();
                                        String prenom = entree.readLine();
                                        Boolean verifPrenom=reg.RegexNomPrenom(prenom);
                                        c.afficher("Entrez votre date de naissance (JJ/MM/YYYY) :");
                                        c.chevron();
                                        String date_naissance = entree.readLine();
                                        Boolean verifDate=reg.RegexDate(date_naissance);
                                        c.afficher("Entrez votre mail :");
                                        c.chevron();
                                        String mail = entree.readLine();
                                        Boolean verifMail=reg.RegexMail(mail);
                                        c.afficher("Entrez votre telephone :");
                                        c.chevron();
                                        String telephone = entree.readLine();
                                        Boolean verifTel=reg.RegexTel(telephone);
                                        c.afficher("Entrez votre numéro de competence : 1-Réseaux, 2-Télécoms, 3 Dévélopement : ");
                                        c.chevron();
                                        String id_competence = entree.readLine();
                                        c.afficher("verifmail :"+verifMail);
                                        c.afficher("verifdate: "+verifDate);
                                        c.afficher("verifTel: "+verifTel);
                                        c.afficher("verifPrenom: "+verifPrenom+"prenom : "+prenom);
                                        c.afficher("verifNom: "+verifNom+"nom : "+nom);
                                        if ((verifMail!=false) && (verifDate!=false) && (verifTel!=false) && (verifPrenom!=false) && (verifNom!=false))
                                        {
                                        requete = "CREATION_PROFIL "+nom+" "+prenom+" "+date_naissance+" "+mail+" "+telephone+" "+id_competence+" "+id_compteProfilAdministre;
                                        c.afficher(requete);
                                        fluxSortieSocket.println(requete);        
                                        String retourCreationProfilAdmin = fluxEntreeSocket.readLine();        
                                        c.afficher("Reponse du serveur : "+retourCreationProfilAdmin); 
                                        }
                                        else
                                        {
                                            if(verifMail!=true)
                                            {
                                            c.afficher("Erreur saisie Mail. Format=xxxxx@xxxx.xx");
                                            }
                                            if(verifDate!=true)
                                            {
                                            c.afficher("Erreur saisie Date. Format= xx/xx/xxxx");
                                            }
                                            if(verifTel!=true)
                                            {
                                            c.afficher("Erreur saisie Numéro de téléphone");
                                            }
                                            if(verifPrenom!=true)
                                            {
                                            c.afficher("Erreur saisie Prénom");
                                            }
                                             if(verifNom!=true)
                                            {
                                            c.afficher("Erreur saisie Nom: Entrez le NOM en MAJUSCULES");
                                            }   
                                        }
                                    }
                                    break;
                                        
                                    case "4": // 4-Modification profil
                                        c.banniere();
                                        c.afficher("Entrez le login du compte pour lequel vous voulez créer un profil:");
                                        c.chevron();
                                        logAdminCompte = entree.readLine();
                                        requete = "RECUP_ID_COMPTE_ADMIN "+logAdminCompte;
                                        c.afficher(requete);
                                        fluxSortieSocket.println(requete);        
                                        retourlogAdminCompte = fluxEntreeSocket.readLine();        
                                        c.afficher("Reponse du serveur : "+retourlogAdminCompte);
                                        String param6 [] = retourConnexion.split(" ");
                                        id_compteProfilAdministre=Integer.parseInt(param6[1]);
                                        if (param6[0].equals("OK_RECUP_ID_COMPTE_ADMIN"))
                                        {
                                        requete = "AFFICHER_PROFIL_COMPTE "+id_compteProfilAdministre;
                                        c.afficher(requete);
                                        fluxSortieSocket.println(requete);	      
                                        String retourAfficherProfilCompte = fluxEntreeSocket.readLine();
                                        c.banniere();
                                        c.afficher("Que souhaitez vous modifier ? :");
                                        c.afficher("1 - Votre mail ? ");
                                        c.afficher("2 - Votre numero de telephone ? ");
                                        c.afficher("3 - Vos compétences ? ");
                                        c.afficher("Voici vos informations actuelles : ");
                                        c.afficher(retourAfficherProfilCompte);
                                        c.afficher(" ////////// Veuillez entrer votre choix  \\\\\\\\\\\\\\");
                                        c.chevron();
                                        String choix7 = entree.readLine();
                                        switch (choix7) 
                                        {
                                            // Modification du mail 
                                            case "1" :
                                                c.banniere();
                                                c.afficher("Quel est votre nouveau mail ? :");
                                                c.chevron();
                                                String nouveauMail = entree.readLine();
                                                Boolean verifMail=reg.RegexMail(nouveauMail);
                                                if ((verifMail!=false))
                                                {
                                                requete = "MODIFICATION_MAIL "+nouveauMail+" "+id_compteProfilAdministre;
                                                fluxSortieSocket.println(requete);	      
                                                String retourModificationMail = fluxEntreeSocket.readLine();	      
                                                c.afficher("Reponse du serveur : "+retourModificationMail); 
                                                }
                                                else
                                                {
                                                c.afficher("Erreur saisie Mail. Format=xxxxx@xxxx.xx");
                                                }

                                                break;

                                            // Modification du numéro de téléphone   
                                            case "2" :
                                                c.banniere();
                                                c.afficher("Quel est votre nouveau numéro de téléphone ? :");
                                                c.chevron();
                                                String nouveauTelephone = entree.readLine();

                                                Boolean verifTel=reg.RegexMail(nouveauTelephone);
                                                if ((verifTel!=false))
                                                {
                                                requete = "MODIFICATION_TEL "+nouveauTelephone+" "+id_compteProfilAdministre;
                                                fluxSortieSocket.println(requete);	      
                                                String retourModificationTel = fluxEntreeSocket.readLine();	      
                                                c.afficher("Reponse du serveur : "+retourModificationTel);
                                                }
                                                else
                                                {
                                                c.afficher("Erreur saisie n° Téléphone");
                                                }
                                                break;

                                            // Modification des compétences ( A REVOIIIIIIIIIIIIIIR)  
                                            case "3" :
                                                c.banniere();
                                                c.afficher("Nouvelle competence ? (1-Réseaux, 2-Telecoms) :");
                                                c.chevron();
                                                String nouveleCompetence = entree.readLine();
                                                requete = "MODIFICATION_COMPETENCE "+nouveleCompetence+" "+id_compteProfilAdministre;
                                                fluxSortieSocket.println(requete);	      
                                                String retourModificationCompetence = fluxEntreeSocket.readLine();	      
                                                c.afficher("Reponse du serveur : "+retourModificationCompetence); 
                                                break;
                                        }
                                        }
                                            default : break; 
                                
                                    
                                    case "5": // 5-Affichage liste
                                        
                                        c.banniere();
                                        c.afficher("Afficher la liste des profils triée par nom (1), prenom(2), competence (3) :");
                                        c.chevron();
                                        String choixTriListe = entree.readLine();
                                        switch (choixTriListe) 
                                        {
                                            // Tri par nom
                                            case "1" :               
                                                requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_TRI_NOM";
                                                fluxSortieSocket.println(requete);	
                                                String retourAffichageListeProfilEtudiantTriNom = fluxEntreeSocket.readLine();
                                                c.afficher("Reponse du serveur : "+retourAffichageListeProfilEtudiantTriNom); 

                                                String[] param1= retourAffichageListeProfilEtudiantTriNom.split("  ");
                                                int nbLigne=Integer.parseInt(param1[1]);
                                                for (int i=2;i<=nbLigne+1;i++)
                                                {
                                                     c.afficher(param1[i]);
                                                }

                                                break;
                                            case "2" : 
                                                requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_TRI_PRENOM";
                                                fluxSortieSocket.println(requete);	
                                                String retourAffichageListeProfilEtudiantTriPrenom = fluxEntreeSocket.readLine();
                                                c.afficher("Reponse du serveur : "+retourAffichageListeProfilEtudiantTriPrenom); 

                                                String[] param2= retourAffichageListeProfilEtudiantTriPrenom.split("  ");
                                                int nbLigne2=Integer.parseInt(param2[1]);
                                                for (int i=2;i<=nbLigne2+1;i++)
                                                {
                                                     c.afficher(param2[i]);
                                                }
                                                
                                                break;
                                            case "3" : 
                                                requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_TRI_COMPETENCE";
                                                fluxSortieSocket.println(requete);	
                                                String retourAffichageListeProfilEtudiantTriCompetence = fluxEntreeSocket.readLine();
                                                c.afficher("Reponse du serveur : "+retourAffichageListeProfilEtudiantTriCompetence); 

                                                String[] param3= retourAffichageListeProfilEtudiantTriCompetence.split("  ");
                                                int nbLigne3=Integer.parseInt(param3[1]);
                                                for (int i=2;i<=nbLigne3+1;i++)
                                                {
                                                     c.afficher(param3[i]);
                                                }   
                                                break;                                                                    
                                        }
                                        break;
                                        
                                    case "6": // 6-Recherche
                                        
                                        c.banniere();
                                        c.afficher("Rechercher par nom (1), prenom (2), mail (3), compténce (4) ? : ");
                                        c.chevron();
                                        String choix4 = entree.readLine();
                                        switch (choix4) 
                                        {
                                            // Recherche par nom
                                            case "1" : 
                                                c.afficher("Entrez le nom du profil étudiant recherché :");
                                                c.chevron();
                                                String nomRecherche = entree.readLine();
                                                requete = "RECHERCHE_ETUDIANT_NOM "+nomRecherche;
                                                fluxSortieSocket.println(requete);	      
                                                String retourRechercheNom = fluxEntreeSocket.readLine();	      
                                                c.afficher("Reponse du serveur : "+retourRechercheNom); 
                                                String [] param2 = retourRechercheNom.split("  ");
                                                int NbLigne1=Integer.parseInt(param2[1]);
                                                for (int i=2;i<=NbLigne1+1;i++)
                                                {
                                                     c.afficher(param2[i]);
                                                }
                                                break;

                                            // Recherche par prenom
                                            case "2" : 
                                                c.afficher("Entrez le prenom du profil étudiant recherché :");
                                                c.chevron();
                                                String prenomRecherche = entree.readLine();
                                                requete = "RECHERCHE_ETUDIANT_PRENOM "+prenomRecherche;
                                                fluxSortieSocket.println(requete);	      
                                                String retourRecherchePrenom = fluxEntreeSocket.readLine();	      
                                                c.afficher("Reponse du serveur : "+retourRecherchePrenom); 
                                                String [] param3 = retourRecherchePrenom.split("  ");
                                                int NbLigne2=Integer.parseInt(param3[1]);
                                                for (int i=2;i<=NbLigne2+1;i++)
                                                {
                                                     c.afficher(param3[i]);
                                                }
                                                break;

                                            // Recherche par mail    
                                            case "3" : 
                                                c.afficher("Entrez le mail du profil étudiant recherché :");
                                                c.chevron();
                                                String mailRecherche = entree.readLine();
                                                requete = "RECHERCHE_ETUDIANT_MAIL "+mailRecherche;
                                                fluxSortieSocket.println(requete);	      
                                                String retourRechercheMail = fluxEntreeSocket.readLine();	      
                                                c.afficher("Reponse du serveur : "+retourRechercheMail); 
                                                String [] param9 = retourRechercheMail.split("  ");
                                                int NbLigne3=Integer.parseInt(param9[1]);
                                                for (int i=2;i<=NbLigne3+1;i++)
                                                {
                                                     c.afficher(param9[i]);
                                                }
                                                break;


                                            // Recherche par comptence    
                                            case "4" : 
                                                c.afficher("Entrez le numéro de la compétence recherchée (1-Réseau,2-Télécoms,3-Dévelopement) :");
                                                c.chevron();
                                                String competenceRecherche = entree.readLine();
                                                requete = "RECHERCHE_ETUDIANT_COMPETENCE "+competenceRecherche;
                                                fluxSortieSocket.println(requete);	      
                                                String retourCompetenceRecherche = fluxEntreeSocket.readLine();	      
                                                c.afficher("Reponse du serveur : "+retourCompetenceRecherche); 
                                                String [] param8 = retourCompetenceRecherche.split("  ");
                                                int NbLigne4=Integer.parseInt(param8[1]);
                                                for (int i=2;i<=NbLigne4+1;i++)
                                                {
                                                     c.afficher(param8[i]);
                                                }
                                                break;
                                        }                   
                                        break;
                                    case "7": // 7-Exit
                                        exit(0);
                                        break;     
                                }
                                }
                            }        
                        leSocket.close();
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
    
    public void banniere()
    {
        System.out.println("==============================================================");
        System.out.println("---------------------- Projet Connect ! ----------------------");
        System.out.println("==============================================================");
        System.out.println("");
    }
    
    public void afficher(String text)
    {
        System.out.println(text);
    }
    
    public void chevron()
    {
        System.out.print("\u001B[31m> ");
    }
    
    
}