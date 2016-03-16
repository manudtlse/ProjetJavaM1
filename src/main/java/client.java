import java.io.*;
import static java.lang.System.exit;
import java.net.*;
import java.awt.*;
import java.sql.*;
import java.util.*;



public class client extends Object        
{   


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
        methodeClient c=new methodeClient();
        
        try 
        {
            fluxEntreeStandard = new BufferedReader(new InputStreamReader(System.in));
            // Localhost pour simplifier, possible avec de vraies adresses IP
            leSocket = new Socket("localhost", 2001); 
            c.afficher("Connecté sur : "+leSocket);
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
                    c.afficher("2/ Connexion (avec Compte)");
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
                                    String retourCreationCompte=c.creationCompte(leSocket);
                                    c.afficher("Reponse du serveur : "+retourCreationCompte); 
                                    break;
                                    
                        // ------------------------- UTILISATEUR CONNECTE --------------------------                      
                        // Connexion sur l'application avec un compte
                        case "2":
                                    String retourConnexion=c.connexionCompte(leSocket);
                                    c.afficher("Reponse du serveur : "+retourConnexion);
                                    String tabRetourConnexion [] = retourConnexion.split(" ");
                                    // Si la connexion a réussie
                                    if (tabRetourConnexion[0].equals("OKCONNEXION"))
                                    {
                                        // On récupere l'id du compte connecté
                                        id_compte = Integer.parseInt(tabRetourConnexion[1]);
                                        // Tant qu'il y a des actions en mode connecté
                                        while(true)
                                        {
                                            //Démarrer threadserver
                                            TchatServer ts= new TchatServer(id_compte);
                                            ts.start();
                                            //On affiche le menu principal
                                            c.banniere();
                                            c.afficher("Notifications :");
                                            c.notificationLike(leSocket,id_compte);
                                            c.afficher("");
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
                                            //ON lit le choix
                                            c.chevron();
                                            String choix2 = entree.readLine();
                                            switch (choix2) 
                                            {
                                                // Création d'un profil étudiant
                                                case "1" :
                                                    c.creationCompteEtudiant(id_compte,leSocket);
                                                    break;
                                                    

                                                //Modification informations profil étudiant  
                                                case "2" :
                                                    String retourAfficherProfilCompte=c.afficherProfilCompte(leSocket, id_compte);
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
                                                            String retourModificationMail=c.modificationMail(id_compte,leSocket);
                                                            c.afficher("Reponse du serveur : "+retourModificationMail);
                                                            break;

                                                        // Modification du numéro de téléphone   
                                                        case "2" :
                                                            String retourModificationTel=c.modificationTel(id_compte,leSocket);
                                                            c.afficher("Reponse du serveur : "+retourModificationTel);
                                                            break;

                                                        // Modification des compétences  
                                                        case "3" :
                                                            String retourModificationCompetence=c.modificationCompetence(id_compte,leSocket);
                                                            c.afficher("Reponse du serveur : "+retourModificationCompetence); 
                                                            break;
                                                            
                                                            
                                                        case "4" : //Maj Paramètres de confidentialité
                                                           
                                                            String retourMajConfidentialite=c.maj_confidentialite(id_compte,leSocket);
                                                            c.afficher("Reponse du serveur : "+retourMajConfidentialite);  
                                                            break;    
                                                    }
                                                    break;

                                                //Afficher la liste des profils étudiants (droits utilisateur)
                                                case "3" : 
                                                            c.banniere();
                                                            c.afficher("Afficher la liste des profils triée par nom (1), prenom(2), compétence (3) :");
                                                            c.chevron();
                                                            String choixTriListe = entree.readLine();
                                                            switch (choixTriListe) 
                                                            {
                                                                // Tri par nom
                                                                case "1" :               
                                                                    c.afficherListeProfilEtudiantTriNom(leSocket);
                                                                    String retourAfficher_profil_complet=c.Afficher_profil_complet(leSocket);
                                                                    c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);
                                                                    int num_etudiantLiker= Integer.parseInt(retourAfficher_profil_complet);
                                                                    int num_etudiantLikeur=an1.numEtudiant(id_compte);
                                                                    c.afficher("Voulez vous recommander une compétence, supprimez une recommandation ou likez ce profil? 1/Oui, 2/Non");
                                                                    c.chevron();
                                                                    String choix6=entree.readLine();
                                                                    switch (choix6)
                                                                    {
                                                                        case"1":
                                                                            c.afficher("Recommander une compétence : 1 ,Supprimer une recommandation: 2 ,Liker le profil : 3");
                                                                            c.chevron();
                                                                            String choix7=entree.readLine();
                                                                            switch(choix7)
                                                                            {
                                                                                case "1":
                                                                                    String retourAfficherListeCompetences=c.afficherListeCompétences(leSocket);
                                                                                    if (retourAfficherListeCompetences.equals("OkAffichageListeCompetences"))
                                                                                    {
                                                                                        String retourRecommanderCompetence=c.recommanderCompetence(num_etudiantLiker,num_etudiantLikeur,leSocket);
                                                                                        c.afficher("Reponse du serveur : "+retourRecommanderCompetence);
                                                                                    } 
                                                                                    break;
                                                                                case "2":
                                                                                    String retourSupprimerRecommandationCompetence=c.supprimerRecommandationCompetence(num_etudiantLiker, num_etudiantLikeur,leSocket);
                                                                                    c.afficher("Reponse du serveur : "+retourSupprimerRecommandationCompetence);
                                                                                    break;
                                                                                case "3":
                                                                                    String retourliker_profil=c.liker_profil(num_etudiantLikeur, num_etudiantLiker,leSocket);
                                                                                    c.afficher("Reponse du serveur : "+retourliker_profil);
                                                                                    break;
                                                                                default: break;
                                                                            }
                                                                            break;
                                                                        case "2":
                                                                            break;
                                                                        default: break;
                                                                    }
                                                                    break;
                                                                
                                                                case "2" : 
                                                                    c.afficherListeProfilEtudiantTriPrenom(leSocket);
                                                                    retourAfficher_profil_complet=c.Afficher_profil_complet(leSocket);
                                                                    c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);
                                                                    num_etudiantLiker= Integer.parseInt(retourAfficher_profil_complet);
                                                                    num_etudiantLikeur=an1.numEtudiant(id_compte);
                                                                    c.afficher("Voulez vous recommander une compétence, supprimez une recommandation ou likez ce profil? 1/Oui, 2/Non");
                                                                    c.chevron();
                                                                    choix6=entree.readLine();
                                                                    switch (choix6)
                                                                    {
                                                                        case"1":
                                                                            c.afficher("Recommander une compétence : 1 ,Supprimer une recommandation: 2 ,Liker le profil : 3");
                                                                            c.chevron();
                                                                            String choix7=entree.readLine();
                                                                            switch(choix7)
                                                                            {
                                                                                case "1":
                                                                                    String retourAfficherListeCompetences=c.afficherListeCompétences(leSocket);
                                                                                    if (retourAfficherListeCompetences.equals("OkAffichageListeCompetences"))
                                                                                    {
                                                                                        String retourRecommanderCompetence=c.recommanderCompetence(num_etudiantLiker,num_etudiantLikeur,leSocket);
                                                                                        c.afficher("Reponse du serveur : "+retourRecommanderCompetence);
                                                                                    } 
                                                                                    break;
                                                                                case "2":
                                                                                    String retourSupprimerRecommandationCompetence=c.supprimerRecommandationCompetence(num_etudiantLiker, num_etudiantLikeur,leSocket);
                                                                                    c.afficher("Reponse du serveur : "+retourSupprimerRecommandationCompetence);
                                                                                    break;
                                                                                case "3":
                                                                                    String retourliker_profil=c.liker_profil(num_etudiantLikeur, num_etudiantLiker,leSocket);
                                                                                    c.afficher("Reponse du serveur : "+retourliker_profil);
                                                                                    break;
                                                                                default: break;
                                                                            }
                                                                            break;
                                                                        case "2":
                                                                            break;
                                                                        default: break;
                                                                    }
                                                                    break;
                                                                    
                                                                case "3" : 
                                                                    c.afficherListeProfilEtudiantTriCompetence(leSocket);
                                                                    retourAfficher_profil_complet=c.Afficher_profil_complet(leSocket);
                                                                    c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);
                                                                    num_etudiantLiker= Integer.parseInt(retourAfficher_profil_complet);
                                                                    num_etudiantLikeur=an1.numEtudiant(id_compte);
                                                                    c.afficher("Voulez vous recommander une compétence, supprimez une recommandation ou likez ce profil? 1/Oui, 2/Non");
                                                                    c.chevron();
                                                                    choix6=entree.readLine();
                                                                    switch (choix6)
                                                                    {
                                                                        case"1":
                                                                            c.afficher("Recommander une compétence : 1 ,Supprimer une recommandation: 2 ,Liker le profil : 3");
                                                                            c.chevron();
                                                                            String choix7=entree.readLine();
                                                                            switch(choix7)
                                                                            {
                                                                                case "1":
                                                                                    String retourAfficherListeCompetences=c.afficherListeCompétences(leSocket);
                                                                                    if (retourAfficherListeCompetences.equals("OkAffichageListeCompetences"))
                                                                                    {
                                                                                        String retourRecommanderCompetence=c.recommanderCompetence(num_etudiantLiker,num_etudiantLikeur,leSocket);
                                                                                        c.afficher("Reponse du serveur : "+retourRecommanderCompetence);
                                                                                    } 
                                                                                    break;
                                                                                case "2":
                                                                                    String retourSupprimerRecommandationCompetence=c.supprimerRecommandationCompetence(num_etudiantLiker, num_etudiantLikeur,leSocket);
                                                                                    c.afficher("Reponse du serveur : "+retourSupprimerRecommandationCompetence);
                                                                                    break;
                                                                                case "3":
                                                                                    String retourliker_profil=c.liker_profil(num_etudiantLikeur, num_etudiantLiker,leSocket);
                                                                                    c.afficher("Reponse du serveur : "+retourliker_profil);
                                                                                    break;
                                                                                default: break;
                                                                            }
                                                                            break;
                                                                        case "2":
                                                                            break;
                                                                        default: break;
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
                                                                    c.rechercheEtudiantNom(leSocket);
                                                                    String retourAfficher_profil_complet=c.Afficher_profil_complet(leSocket);
                                                                    c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);
                                                                    int num_etudiantLiker= Integer.parseInt(retourAfficher_profil_complet);
                                                                    int num_etudiantLikeur=an1.numEtudiant(id_compte);
                                                                    c.afficher("Voulez vous recommander une compétence, supprimez une recommandation ou likez ce profil? 1/Oui, 2/Non");
                                                                    c.chevron();
                                                                    String choix6=entree.readLine();
                                                                    switch (choix6)
                                                                    {
                                                                        case"1":
                                                                            c.afficher("Recommander une compétence : 1 ,Supprimer une recommandation: 2 ,Liker le profil : 3");
                                                                            c.chevron();
                                                                            String choix7=entree.readLine();
                                                                            switch(choix7)
                                                                            {
                                                                                case "1":
                                                                                    String retourAfficherListeCompetences=c.afficherListeCompétences(leSocket);
                                                                                    if (retourAfficherListeCompetences.equals("OkAffichageListeCompetences"))
                                                                                    {
                                                                                        String retourRecommanderCompetence=c.recommanderCompetence(num_etudiantLiker,num_etudiantLikeur,leSocket);
                                                                                        c.afficher("Reponse du serveur : "+retourRecommanderCompetence);
                                                                                    } 
                                                                                    break;
                                                                                case "2":
                                                                                    String retourSupprimerRecommandationCompetence=c.supprimerRecommandationCompetence(num_etudiantLiker, num_etudiantLikeur,leSocket);
                                                                                    c.afficher("Reponse du serveur : "+retourSupprimerRecommandationCompetence);
                                                                                    break;
                                                                                case "3":
                                                                                    String retourliker_profil=c.liker_profil(num_etudiantLikeur, num_etudiantLiker,leSocket);
                                                                                    c.afficher("Reponse du serveur : "+retourliker_profil);
                                                                                    break;
                                                                                default: break;
                                                                            }
                                                                            break;
                                                                        case "2":
                                                                            break;
                                                                        default: break;
                                                                    }
                                                                    break;

                                                                // Recherche par prenom
                                                                case "2" : 
                                                                    c.rechercheEtudiantPrenom(leSocket);
                                                                    retourAfficher_profil_complet=c.Afficher_profil_complet(leSocket);
                                                                    c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);
                                                                    num_etudiantLiker= Integer.parseInt(retourAfficher_profil_complet);
                                                                    num_etudiantLikeur=an1.numEtudiant(id_compte);
                                                                    c.afficher("Voulez vous recommander une compétence, supprimez une recommandation ou likez ce profil? 1/Oui, 2/Non");
                                                                    c.chevron();
                                                                    choix6=entree.readLine();
                                                                    switch (choix6)
                                                                    {
                                                                        case"1":
                                                                            c.afficher("Recommander une compétence : 1 ,Supprimer une recommandation: 2 ,Liker le profil : 3");
                                                                            c.chevron();
                                                                            String choix7=entree.readLine();
                                                                            switch(choix7)
                                                                            {
                                                                                case "1":
                                                                                    String retourAfficherListeCompetences=c.afficherListeCompétences(leSocket);
                                                                                    if (retourAfficherListeCompetences.equals("OkAffichageListeCompetences"))
                                                                                    {
                                                                                        String retourRecommanderCompetence=c.recommanderCompetence(num_etudiantLiker,num_etudiantLikeur,leSocket);
                                                                                        c.afficher("Reponse du serveur : "+retourRecommanderCompetence);
                                                                                    } 
                                                                                    break;
                                                                                case "2":
                                                                                    String retourSupprimerRecommandationCompetence=c.supprimerRecommandationCompetence(num_etudiantLiker, num_etudiantLikeur,leSocket);
                                                                                    c.afficher("Reponse du serveur : "+retourSupprimerRecommandationCompetence);
                                                                                    break;
                                                                                case "3":
                                                                                    String retourliker_profil=c.liker_profil(num_etudiantLikeur, num_etudiantLiker,leSocket);
                                                                                    c.afficher("Reponse du serveur : "+retourliker_profil);
                                                                                    break;
                                                                                default: break;
                                                                            }
                                                                            break;
                                                                        case "2":
                                                                            break;
                                                                        default: break;
                                                                    }
                                                                    break;

                                                                // Recherche par mail    
                                                                case "3" : 
                                                                    c.rechercheEtudiantMail(leSocket);
                                                                    retourAfficher_profil_complet=c.Afficher_profil_complet(leSocket);
                                                                    c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);
                                                                    num_etudiantLiker= Integer.parseInt(retourAfficher_profil_complet);
                                                                    num_etudiantLikeur=an1.numEtudiant(id_compte);
                                                                    c.afficher("Voulez vous recommander une compétence, supprimez une recommandation ou likez ce profil? 1/Oui, 2/Non");
                                                                    c.chevron();
                                                                    choix6=entree.readLine();
                                                                    switch (choix6)
                                                                    {
                                                                        case"1":
                                                                            c.afficher("Recommander une compétence : 1 ,Supprimer une recommandation: 2 ,Liker le profil : 3");
                                                                            c.chevron();
                                                                            String choix7=entree.readLine();
                                                                            switch(choix7)
                                                                            {
                                                                                case "1":
                                                                                    String retourAfficherListeCompetences=c.afficherListeCompétences(leSocket);
                                                                                    if (retourAfficherListeCompetences.equals("OkAffichageListeCompetences"))
                                                                                    {
                                                                                        String retourRecommanderCompetence=c.recommanderCompetence(num_etudiantLiker,num_etudiantLikeur,leSocket);
                                                                                        c.afficher("Reponse du serveur : "+retourRecommanderCompetence);
                                                                                    } 
                                                                                    break;
                                                                                case "2":
                                                                                    String retourSupprimerRecommandationCompetence=c.supprimerRecommandationCompetence(num_etudiantLiker, num_etudiantLikeur,leSocket);
                                                                                    c.afficher("Reponse du serveur : "+retourSupprimerRecommandationCompetence);
                                                                                    break;
                                                                                case "3":
                                                                                    String retourliker_profil=c.liker_profil(num_etudiantLikeur, num_etudiantLiker,leSocket);
                                                                                    c.afficher("Reponse du serveur : "+retourliker_profil);
                                                                                    break;
                                                                                default: break;
                                                                            }
                                                                            break;
                                                                        case "2":
                                                                            break;
                                                                        default: break;
                                                                    }
                                                                    break;


                                                                // Recherche par comptence    
                                                                case "4" : 
                                                                    /*c.afficher("Entrez le numéro de la compétence recherchée :");
                                                                    requete = "AFFICHER_LISTE_COMPETENCE ";
                                                                    fluxSortieSocket.println(requete);	      
                                                                    String retourAfficherListeCompetences = fluxEntreeSocket.readLine();
                                                                    c.TraitementAfficherListe(retourAfficherListeCompetences);
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
                                                                    requete=c.Afficher_profil_complet(leSocket);
                                                                    fluxSortieSocket.println(requete);	
                                                                    retourAfficher_profil_complet = fluxEntreeSocket.readLine();
                                                                    c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);
                                                                    param4 = requete.split(" ");
                                                                    num_etudiantLiker= Integer.parseInt(param4[1]);
                                                                    num_etudiantLikeur=an1.numEtudiant(id_compte);
                                                                    c.afficher("Voulez vous recommander une compétence, supprimez une recommandation ou likez ce profil? 1/Oui, 2/Non");
                                                                    c.chevron();
                                                                    choix6=entree.readLine();*/
                                                                    
                                                                    c.rechercheEtudiantCompetence(leSocket);
                                                                    retourAfficher_profil_complet=c.Afficher_profil_complet(leSocket);
                                                                    c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);
                                                                    num_etudiantLiker= Integer.parseInt(retourAfficher_profil_complet);
                                                                    num_etudiantLikeur=an1.numEtudiant(id_compte);
                                                                    c.afficher("Voulez vous recommander une compétence, supprimez une recommandation ou likez ce profil? 1/Oui, 2/Non");
                                                                    c.chevron();
                                                                    choix6=entree.readLine();
                                                                    switch (choix6)
                                                                    {
                                                                        case"1":
                                                                            c.afficher("Recommander une compétence : 1 ,Supprimer une recommandation: 2 ,Liker le profil : 3");
                                                                            c.chevron();
                                                                            String choix7=entree.readLine();
                                                                            switch(choix7)
                                                                            {
                                                                                case "1":
                                                                                    String retourAfficherListeCompetences=c.afficherListeCompétences(leSocket);
                                                                                    if (retourAfficherListeCompetences.equals("OkAffichageListeCompetences"))
                                                                                    {
                                                                                        String retourRecommanderCompetence=c.recommanderCompetence(num_etudiantLiker,num_etudiantLikeur,leSocket);
                                                                                        c.afficher("Reponse du serveur : "+retourRecommanderCompetence);
                                                                                    } 
                                                                                    break;
                                                                                case "2":
                                                                                    String retourSupprimerRecommandationCompetence=c.supprimerRecommandationCompetence(num_etudiantLiker, num_etudiantLikeur,leSocket);
                                                                                    c.afficher("Reponse du serveur : "+retourSupprimerRecommandationCompetence);
                                                                                    break;
                                                                                case "3":
                                                                                    String retourliker_profil=c.liker_profil(num_etudiantLikeur, num_etudiantLiker,leSocket);
                                                                                    c.afficher("Reponse du serveur : "+retourliker_profil);
                                                                                    break;
                                                                                default: break;
                                                                            }
                                                                            break;
                                                                        case "2":
                                                                            break;
                                                                        default: break;
                                                                    }
                                                                    break;
                                                              }
                                                            break;             
                                                case "5" : //Modifier mot de passe
                                                            c.changerMdp(leSocket,id_compte);
                                                    break;
                                                    
                                                case "6" :  
                                                    c.affichageMembreConnectes(leSocket);
                                                    break;
                                                   
                                                case "7" :  
                                                    c.etatConnecte(leSocket, id_compte);
                                                    c.affichageMembreConnectes(leSocket);
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
                                                    String identité=IM.recupIdentiteClient(id_compte);     // apres cas 1 et 2
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
                                                                        c.TraitementAfficherListe(retourAffichageListeProfilEtudiantTriNom);
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
                                                                                        c.TraitementAfficherListe(retourRechercheNom);
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
                                                                                        c.TraitementAfficherListe(retourRecherchePrenom);
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
                                                                                        c.TraitementAfficherListe(retourRechercheMail);
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
                                                                                        c.TraitementAfficherListe(retourCompetenceRecherche);
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
                                    String choixMenuAnonyme = entree.readLine();
                                    switch (choixMenuAnonyme)
                                    {

                                        case "1" : // Afficher la liste des profils étudiants avec des droits d'un utilisateur anonyme
                                                   requete=c.afficherListeProfilEtudiantAnonyme();
                                                   fluxSortieSocket.println(requete);	
                                                   String retourAffichageListeProfilEtudiantAnonyme = fluxEntreeSocket.readLine();
                                                   c.afficher("Reponse du serveur : "+retourAffichageListeProfilEtudiantAnonyme);
                                                   c.TraitementAfficherListe(retourAffichageListeProfilEtudiantAnonyme);
                                                   break;

                                        case "2" :  //Recherche d'informations sur un étudiant (droits anonyme)
                                                    c.banniere();
                                                    c.afficher("Rechercher par Nom (1), Prénom (2) : ");
                                                    c.chevron();
                                                    String choix6 = entree.readLine();
                                                    switch (choix6) 
                                                    {
                                                        // Recherche par nom
                                                        case "1" : 
                                                                requete=c.RechercheParNomAnonyme();
                                                                fluxSortieSocket.println(requete);	      
                                                                String retourRechercheNom = fluxEntreeSocket.readLine();	      
                                                                c.afficher("Reponse du serveur : "+retourRechercheNom); 
                                                                c.TraitementAfficherListe(retourRechercheNom);
                                                                break;


                                                        // Recherche par prenom
                                                        case "2" : 
                                                                requete=c.RechercheParPrenomAnonyme();
                                                                fluxSortieSocket.println(requete);	      
                                                                String retourRecherchePrenom = fluxEntreeSocket.readLine();	      
                                                                c.afficher("Reponse du serveur : "+retourRecherchePrenom); 
                                                                c.TraitementAfficherListe(retourRecherchePrenom);
                                                                break;
                                                    }
                                                    break;

                                         // Pour quitter le mode Anonyme   
                                        case "3" : exit(0);
                                        default : exit(0); 
                                    }
                                }
                           
                        case "4": 
                            leSocket.close();
                            exit(0);
                            
                        
       //_______________________________________________________________ADMIN_____________________________________                 
                        case "admin9876" :
                            requete=c.connexionAdmin();
                            fluxSortieSocket.println(requete);	      
                            retourConnexion = fluxEntreeSocket.readLine();	      
                            c.afficher("Reponse du serveur : "+retourConnexion);
                            String TabRetourConnexion [] = retourConnexion.split(" ");
                            // Si la connexion a réussie
                            if (TabRetourConnexion[0].equals("OKCONNEXIONADMIN"))
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
                                    requete=c.creationCompteAdmin();
                                    c.afficher(requete);
                                    fluxSortieSocket.println(requete);
                                    retourCreationCompte = fluxEntreeSocket.readLine();	      
                                    c.afficher("Reponse du serveur : "+retourCreationCompte); 
                                    break;
                                    
                                    case "2": //2-Modification mot de passe
                                    requete=c.modificationMdpAdmin();
                                    if (!requete.equals(""))
                                    {
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
                                        c.afficher("Entrez votre numéro de compétence: ");
                                        String retourAfficherListeCompetences=c.afficherListeCompétences(leSocket);
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
                                                    c.afficher("Competences: ");
                                                    String retourAfficherListeCompetences=c.afficherListeCompétences(leSocket);
                                                    requete=c.modificationCompetencesAdmin(id_compteProfilAdministre);
                                                    fluxSortieSocket.println(requete);	      
                                                    String retourModificationCompetence = fluxEntreeSocket.readLine();	      
                                                    c.afficher("Reponse du serveur : "+retourModificationCompetence); 
                                                    break;
                                            }
                                        }
                                            default : break; 
                                
                                    
                                    case "5": // 5-Affichage liste
                                        
                                        c.banniere();
                                        c.afficher("Afficher la liste des profils triée par nom (1), prenom(2), compétence (3) :");
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
                                                c.TraitementAfficherListe(retourAffichageListeProfilEtudiantTriNom);
                                                requete=c.Afficher_profil_complet(leSocket);
                                                fluxSortieSocket.println(requete);	
                                                String retourAfficher_profil_complet = fluxEntreeSocket.readLine();
                                                c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);
                                                break;
                                                
                                            case "2" : 
                                                requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_TRI_PRENOM";
                                                fluxSortieSocket.println(requete);	
                                                String retourAffichageListeProfilEtudiantTriPrenom = fluxEntreeSocket.readLine();
                                                c.afficher("Reponse du serveur : "+retourAffichageListeProfilEtudiantTriPrenom); 
                                                c.TraitementAfficherListe(retourAffichageListeProfilEtudiantTriPrenom);
                                                requete=c.Afficher_profil_complet(leSocket);
                                                fluxSortieSocket.println(requete);	
                                                retourAfficher_profil_complet = fluxEntreeSocket.readLine();
                                                c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);
                                                break;
                                            case "3" : 
                                                requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_TRI_COMPETENCE";
                                                fluxSortieSocket.println(requete);	
                                                String retourAffichageListeProfilEtudiantTriCompetence = fluxEntreeSocket.readLine();
                                                c.afficher("Reponse du serveur : "+retourAffichageListeProfilEtudiantTriCompetence); 
                                                c.TraitementAfficherListe(retourAffichageListeProfilEtudiantTriCompetence);
                                                requete=c.Afficher_profil_complet(leSocket);
                                                fluxSortieSocket.println(requete);	
                                                retourAfficher_profil_complet = fluxEntreeSocket.readLine();
                                                c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);
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
                                                c.rechercheEtudiantNom(leSocket);
                                                String retourAfficher_profil_complet=c.Afficher_profil_complet(leSocket);
                                                c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);
                                                /*requete=c.RechercherParNom();
                                                fluxSortieSocket.println(requete);	      
                                                String retourRechercheNom = fluxEntreeSocket.readLine();	      
                                                c.afficher("Reponse du serveur : "+retourRechercheNom); 
                                                c.TraitementAfficherListe(retourRechercheNom);
                                                requete=c.Afficher_profil_complet(leSocket);
                                                fluxSortieSocket.println(requete);	
                                                String retourAfficher_profil_complet = fluxEntreeSocket.readLine();
                                                c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);*/
                                                break;

                                            // Recherche par prenom
                                            case "2" : 
                                                c.rechercheEtudiantPrenom(leSocket);
                                                retourAfficher_profil_complet=c.Afficher_profil_complet(leSocket);
                                                c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);
                                                /*requete=c.RechercherParPrenom();
                                                fluxSortieSocket.println(requete);	      
                                                String retourRecherchePrenom = fluxEntreeSocket.readLine();	      
                                                c.afficher("Reponse du serveur : "+retourRecherchePrenom); 
                                                c.TraitementAfficherListe(retourRecherchePrenom);
                                                requete=c.Afficher_profil_complet(leSocket);
                                                fluxSortieSocket.println(requete);	
                                                retourAfficher_profil_complet = fluxEntreeSocket.readLine();
                                                c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);*/
                                                break;

                                            // Recherche par mail    
                                            case "3" : 
                                                c.rechercheEtudiantMail(leSocket);
                                                retourAfficher_profil_complet=c.Afficher_profil_complet(leSocket);
                                                c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);
                                                /*requete=c.RechercherParMail();
                                                fluxSortieSocket.println(requete);	      
                                                String retourRechercheMail = fluxEntreeSocket.readLine();	      
                                                c.afficher("Reponse du serveur : "+retourRechercheMail); 
                                                c.TraitementAfficherListe(retourRechercheMail);
                                                requete=c.Afficher_profil_complet(leSocket);
                                                fluxSortieSocket.println(requete);	
                                                retourAfficher_profil_complet = fluxEntreeSocket.readLine();
                                                c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);*/
                                                break;


                                            // Recherche par competence    
                                            case "4" : 
                                                c.rechercheEtudiantCompetence(leSocket);
                                                retourAfficher_profil_complet=c.Afficher_profil_complet(leSocket);
                                                c.afficher("Reponse du serveur : "+retourAfficher_profil_complet);
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
}