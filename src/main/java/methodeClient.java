
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.String;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author many31
 */
public class methodeClient {
    
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
    
    public String creationCompte(Socket leSocket) throws IOException
    {
        
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        String requete="";
        c.banniere();
        c.afficher("Choisissez votre login pour l'inscription :");
        c.chevron();
        String loginInscription = entree.readLine();
        c.afficher("Entrez votre mot de passe :");
        c.chevron();
        String pwdInscription = entree.readLine();
        // Envoi de la requete
        requete = "INSCRIPTION "+loginInscription+" "+pwdInscription + " Utilisateur";
        c.afficher(requete);
        String retourCreationCompte = c.envoieData(leSocket, requete);
        return retourCreationCompte;
    } 
    
    public String connexionCompte(Socket leSocket) throws IOException
    {
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        String requete="";
        c.banniere();
        c.afficher("Pour vous connecter, entrez votre login :");
        c.chevron();
        String loginConnexion = entree.readLine();
        c.afficher("Entrez votre mot de passe :");
        c.chevron();
        String pwdConnexion = entree.readLine();
        // Mise en forme de la requete
        requete = "CONNEXION "+loginConnexion+" "+pwdConnexion;
        c.afficher(requete);
        // Envoie de la requete via la socket 
        String retourConnexion=c.envoieData(leSocket, requete);
        return retourConnexion;
    } 
    
    public String creationCompteEtudiant(int id_compte, Socket leSocket) throws IOException
    {
        BufferedReader entree   = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        Regex reg               = new Regex();
        String requete="";
        c.banniere();
        c.afficher("Entrez votre NOM (MAJUSCULES):");
        c.chevron();
        String nom = entree.readLine();
        // Regex pour le nom
        Boolean verifNom=reg.RegexNomPrenom(nom);
        c.afficher("Entrez votre Prenom :");
        c.chevron();
        String prenom = entree.readLine();
        // Regex pour le prenom
        Boolean verifPrenom=reg.RegexNomPrenom(prenom);
        c.afficher("Entrez votre date de naissance (JJ/MM/YYYY) :");
        c.chevron();
        String date_naissance = entree.readLine();
        // Regex date naissance
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
        c.afficher(" ");
        c.afficher("Entrez votre numéro de competence principal : ");
        String retourAfficherListeCompetence=c.afficherListeCompétences(leSocket);
        c.chevron();
        String id_competence = entree.readLine();
        c.afficher("verifmail :"+verifMail+" mail: "+mail);
        c.afficher("verifdate: "+verifDate+" date: "+date_naissance);
        c.afficher("verifTel: "+verifTel+" tel: "+telephone);
        c.afficher("verifPrenom: "+verifPrenom+"prenom : "+prenom);
        c.afficher("verifNom: "+verifNom+"nom : "+nom);
        c.afficher(" ");
        c.afficher(" ");
        if ((verifMail!=false) && (verifDate!=false) && (verifTel!=false) && (verifPrenom!=false) && (verifNom!=false))
        {
        String requete1 = "CREATION_PROFIL "+nom+" "+prenom+" "+date_naissance+" "+mail+" "+telephone+" "+id_competence+" "+id_compte;
        String retourCreationProfil=c.envoieData(leSocket, requete1);
        c.afficher("Reponse du serveur : "+retourCreationProfil);
        String requete2 = "CONFIDENTIALITE "+id_compte+" "+v_date_naissance+" "+v_mail+" "+v_tel;
        c.envoieData(leSocket, requete2);
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
             requete="erreur";
        }
        return requete;
    }
    
    public String modificationMail(int id_compte,Socket leSocket) throws IOException
    {
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        Regex reg               = new Regex();
        String retourModificationMail="erreur";
        String requete="";
        c.banniere();
        c.afficher("Quel est votre nouveau mail ? :");
        c.chevron();
        String nouveauMail = entree.readLine();
        Boolean verifMail=reg.RegexMail(nouveauMail);
        if ((verifMail!=false))
        {
            requete = "MODIFICATION_MAIL "+nouveauMail+" "+id_compte;
            retourModificationMail=c.envoieData(leSocket, requete);
        }
        else
        {
            c.afficher("Erreur saisie Mail. Format=xxxxx@xxxx.xx");
            retourModificationMail="erreur";
        }
        return retourModificationMail;
    }               
    
    public String modificationTel(int id_compte,Socket leSocket) throws IOException
    {
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        Regex reg               = new Regex();
        String requete="";
        String retourModificationTel="erreur";
        c.banniere();
        c.afficher("Quel est votre nouveau numéro de téléphone ? :");
        c.chevron();
        String nouveauTelephone = entree.readLine();
        Boolean verifTel=reg.RegexTel(nouveauTelephone);
        if ((verifTel!=false))
        {
            requete = "MODIFICATION_TEL "+nouveauTelephone+" "+id_compte;
            retourModificationTel=c.envoieData(leSocket, requete);
        }
        else
        {
            c.afficher("Erreur saisie n° Téléphone");
            retourModificationTel="erreur";
        }
        return retourModificationTel;
    }     
    
    public String modificationCompetence(int id_compte,Socket leSocket) throws IOException
    {
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        Regex reg               = new Regex();
        String requete="";
        String retourModificationCompétence="erreur";
        c.banniere();
        c.afficher("Nouvelle competence ? (1-Réseaux, 2-Telecoms, 3-Developement) :");
        c.chevron();
        String nouveleCompetence = entree.readLine();
        requete = "MODIFICATION_COMPETENCE "+nouveleCompetence+" "+id_compte;
        retourModificationCompétence=c.envoieData(leSocket, requete);
        return retourModificationCompétence;
    }  
    
    public String Afficher_profil_complet(Socket leSocket) throws IOException
    {
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        String requete="";
        c.banniere();
        c.afficher("Quel profil souhaitez-vous voir (n°etudiant)");
        c.chevron();
        String S_id_compte=entree.readLine();
        int id_compte=Integer.parseInt(S_id_compte);
        requete = "AFFICHER_PROFIL_COMPLET "+id_compte;
        String retourAfficher_profil_complet=c.envoieData(leSocket, requete);
        c.afficher("Reponse du serveur : "+retourAfficher_profil_complet); 
        return S_id_compte;
    } 
    
    public String liker_profil(int num_etudiantLikeur,int num_etudiantLiker,Socket leSocket) throws IOException
    {
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        c.banniere();
        
        c.afficher("Likez ce profil? 1/Oui, 2/Non");
        c.chevron();
        String requete="";
        String retourliker_profil="erreur";
        int choix=Integer.parseInt(entree.readLine());
        if(choix==1)
        {
            requete = "LIKER_PROFIL "+num_etudiantLikeur+" "+num_etudiantLiker;
            retourliker_profil=c.envoieData(leSocket, requete); ;
        }
    return retourliker_profil;    
    } 
    
    public String maj_confidentialite(int id_compte,Socket leSocket) throws IOException
    {
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        c.banniere();
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
        c.afficher("Voulez-vous afficher votre numéro de Téléphone? 1-Oui, 0-Non :");
        Boolean v_tel=null;
        c.chevron();
        if ("1".equals(entree.readLine()))
        {
            v_tel=true;   
        }
        else
        {
            v_tel=false;
        }
        String requete = "MAJ_CONFIDENTIALITE "+id_compte+" "+v_date_naissance+" "+v_mail+" "+v_tel;
        String retourMajConfidentialite=c.envoieData(leSocket, requete);
        return retourMajConfidentialite; 
    }
    
    public void changerMdp(Socket leSocket,int id_compte) throws IOException
    {
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        String requete="";
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
        }
        String retourChangeMdp=c.envoieData(leSocket, requete);
        c.afficher("Reponse du serveur : "+retourChangeMdp);
    } 
    
    public String recommanderCompetence(int num_etudiant_recommande,int num_etudiant_recommandeur,Socket leSocket) throws IOException
    {
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        String requete="";
        c.afficher("Entrez l'id de la competence à recommander");
        c.chevron();
        String id_competence = entree.readLine();
        requete = "RECOMMANDERCOMPETENCE "+id_competence+" "+num_etudiant_recommande+" "+num_etudiant_recommandeur;  
        String retourRecommanderCompetence=c.envoieData(leSocket, requete);
    return retourRecommanderCompetence;
    } 
    
    public String supprimerRecommandationCompetence(int num_etudiant_recommande,int num_etudiant_recommandeur,Socket leSocket) throws IOException
    {
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        String requete="";
        c.banniere();
        c.afficher("Entrez le numéro de la compétence dont vous souhaitez supprimer la recommandation : ");
        c.chevron();
        String id_competence = entree.readLine();
        requete = "SUPPRIMER_RECOMMANDATION_COMPETENCE "+id_competence+" "+num_etudiant_recommande+" "+num_etudiant_recommandeur;
        String retourSupprimerRecommandationCompetence=c.envoieData(leSocket, requete);
    return retourSupprimerRecommandationCompetence;
    } 
    
    public String afficherListeCompetences(Socket leSocket) throws IOException
    {
        methodeClient c = new methodeClient();
        String requete="AFFICHER_LISTE_COMPETENCE";
        String retourAfficherListeCompetence = c.envoieData(leSocket,requete);
        return retourAfficherListeCompetence;
    }
    
    /*public String RechercherParNom() throws IOException
    {
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        String requete="";
        c.afficher("Entrez le nom du profil étudiant recherché :");
        c.chevron();
        String nomRecherche = entree.readLine();
        requete = "RECHERCHE_ETUDIANT_NOM "+nomRecherche;
        return requete;
    }
    
    public String RechercherParPrenom() throws IOException
    {
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        String requete="";
        c.afficher("Entrez le prenom du profil étudiant recherché :");
        c.chevron();
        String prenomRecherche = entree.readLine();
        requete = "RECHERCHE_ETUDIANT_PRENOM "+prenomRecherche;
        return requete;
    }
    
    public String RechercherParCompetence() throws IOException
    {
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        String requete="";
        c.afficher("Entrez le numéro de la compétence recherchée :");
        c.chevron();
        String competenceRecherche = entree.readLine();
        // Mise en forme de la requete
        requete = "RECHERCHE_ETUDIANT_COMPETENCE "+competenceRecherche;
        return requete;
    }
    
     public String RechercherParMail() throws IOException
    {
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        String requete="";
        c.afficher("Entrez le mail du profil étudiant recherché :");
        c.chevron();
        String mailRecherche = entree.readLine();
        requete = "RECHERCHE_ETUDIANT_MAIL "+mailRecherche;
        return requete;
    }
    */
    

    
    public void TraitementAfficherListe (String RetourAffichageListe)
    {
        methodeClient c = new methodeClient();
        String [] AffichageRetourAffichageListe= RetourAffichageListe.split("  ");
        int nbLigne=Integer.parseInt(AffichageRetourAffichageListe[1]);
        for (int i=2;i<=nbLigne+1;i++)
        {
             c.afficher(AffichageRetourAffichageListe[i]);
        }
    }
    
    public String afficherListeCompétences (Socket leSocket) throws IOException
    {
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        String resultat="";
        String requete="AFFICHER_LISTE_COMPETENCE";
        String retourAfficherListeCompetences = c.envoieData(leSocket,requete);
        String [] afficherRetourafficherListeCompetences= retourAfficherListeCompetences.split("  ");
        int rep=Integer.parseInt(afficherRetourafficherListeCompetences[1]);
        if(0<rep)
        {
            int nbLigne=Integer.parseInt(afficherRetourafficherListeCompetences[1]);
            for (int i=2;i<=nbLigne+1;i++)
            {
                 c.afficher(afficherRetourafficherListeCompetences[i]);
            }
            c.afficher(" ");  
            resultat= "OkAffichageListeCompetences";
        }
        else
        {
            resultat="erreur";
        }
        return resultat;
    }
    
    public String afficherProfilCompte(Socket leSocket, int id_compte) throws IOException
        {
            methodeClient c=new methodeClient();
            c.banniere();
            String requete = "AFFICHER_PROFIL_COMPTE "+id_compte;
            c.afficher(requete);
            String retourAfficherProfilCompte=c.envoieData(leSocket, requete);
            return retourAfficherProfilCompte;
        }
    
    public void afficherListeProfilEtudiantTriNom(Socket leSocket) throws IOException
        {
            methodeClient c=new methodeClient();
            c.banniere();
            String requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_TRI_NOM";
            c.afficher(requete);
            String retourAffichageListeProfilEtudiantTriNom=c.envoieData(leSocket, requete);
            c.afficher("Reponse du serveur : "+retourAffichageListeProfilEtudiantTriNom); 
            c.TraitementAfficherListe(retourAffichageListeProfilEtudiantTriNom);
        }
    
    public void afficherListeProfilEtudiantTriPrenom(Socket leSocket) throws IOException
        {
            methodeClient c=new methodeClient();
            c.banniere();
            String requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_TRI_PRENOM";
            c.afficher(requete);
            String retourAffichageListeProfilEtudiantTriPrenom=c.envoieData(leSocket, requete);
            c.afficher("Reponse du serveur : "+retourAffichageListeProfilEtudiantTriPrenom); 
            c.TraitementAfficherListe(retourAffichageListeProfilEtudiantTriPrenom);
        }
    
    public void afficherListeProfilEtudiantTriCompetence(Socket leSocket) throws IOException
        {
            methodeClient c=new methodeClient();
            c.banniere();
            String requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_TRI_COMPETENCE";
            c.afficher(requete);
            String retourAffichageListeProfilEtudiantTriComptence=c.envoieData(leSocket, requete);
            c.afficher("Reponse du serveur : "+retourAffichageListeProfilEtudiantTriComptence); 
            c.TraitementAfficherListe(retourAffichageListeProfilEtudiantTriComptence);
        }
    
    public void rechercheEtudiantNom(Socket leSocket) throws IOException
        {
            methodeClient c=new methodeClient();
            BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
            c.banniere();
            c.afficher("Entrez le nom du profil étudiant recherché :");
            c.chevron();
            String nomRecherche = entree.readLine();
            String requete = "RECHERCHE_ETUDIANT_NOM "+nomRecherche;
            String retourRechercheNom=c.envoieData(leSocket, requete);
            c.afficher("Reponse du serveur : "+retourRechercheNom); 
            c.TraitementAfficherListe(retourRechercheNom);
        }
    
    public void rechercheEtudiantPrenom(Socket leSocket) throws IOException
        {
            methodeClient c=new methodeClient();
            BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
            c.banniere();
            c.afficher("Entrez le prenom du profil étudiant recherché :");
            c.chevron();
            String prenomRecherche = entree.readLine();
            String requete = "RECHERCHE_ETUDIANT_PRENOM "+prenomRecherche;
            String retourRecherchePrenom=c.envoieData(leSocket, requete);
            c.afficher("Reponse du serveur : "+retourRecherchePrenom); 
            c.TraitementAfficherListe(retourRecherchePrenom);
        }
    
    public void rechercheEtudiantMail(Socket leSocket) throws IOException
        {
            methodeClient c=new methodeClient();
            BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
            c.banniere();
            c.afficher("Entrez le mail du profil étudiant recherché :");
            c.chevron();
            String mailRecherche = entree.readLine();
            String requete = "RECHERCHE_ETUDIANT_MAIL "+mailRecherche;
            String retourRechercheMail=c.envoieData(leSocket, requete);
            c.afficher("Reponse du serveur : "+retourRechercheMail); 
            c.TraitementAfficherListe(retourRechercheMail);
        }
    
    public void rechercheEtudiantCompetence(Socket leSocket) throws IOException
        {
            methodeClient c=new methodeClient();
            BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
            c.banniere();
            c.afficher("Entrez la compétence du profil étudiant recherché :");
            c.afficherListeCompétences(leSocket);
            c.chevron();
            String competenceRecherche = entree.readLine();
            String requete = "RECHERCHE_ETUDIANT_COMPETENCE "+competenceRecherche;
            String retourRechercheCompetence=c.envoieData(leSocket, requete);
            c.afficher("Reponse du serveur : "+retourRechercheCompetence); 
            c.TraitementAfficherListe(retourRechercheCompetence);
        }
    
    public String envoieData(Socket leSocket, String requete) throws IOException
    {
        PrintStream         fluxSortieSocket;
        BufferedReader      fluxEntreeSocket;
        fluxSortieSocket = new PrintStream(leSocket.getOutputStream());
        fluxEntreeSocket = new BufferedReader(new InputStreamReader(leSocket.getInputStream())); 
        fluxSortieSocket.println(requete);	      
        String retourConnexion = fluxEntreeSocket.readLine();
        return retourConnexion;
    }
    
     
   public void notificationLike(Socket leSocket,int id_compte_notifie) throws IOException
        {
            methodeClient c=new methodeClient();
            String requete = "NUM_ETUDIANT "+id_compte_notifie;
            String retournum_etudiant=c.envoieData(leSocket, requete);
            if (Integer.parseInt(retournum_etudiant)>0)
            {
            requete = "NOTIFICATIONLIKE "+retournum_etudiant;  
            //c.afficher(requete);
            String retourNotificationLike=c.envoieData(leSocket, requete);
            c.TraitementAfficherListe(retourNotificationLike);
            }
            else
            {
                System.out.println("Vous n'avez pas de profil etudiant, veuillez en créer un");
                 
            }
        }
   
   public void notificationRecommandation(Socket leSocket,int id_compte_notifie) throws IOException
        {
            methodeClient c=new methodeClient();
            String requete = "NUM_ETUDIANT "+id_compte_notifie;
            String retournum_etudiant=c.envoieData(leSocket, requete);
            if (Integer.parseInt(retournum_etudiant)>0)
            {
            requete = "NOTIFICATIONRECOMMANDATION "+retournum_etudiant;  
            //c.afficher(requete);
            String retourNotificationRecommandation=c.envoieData(leSocket, requete);
            c.TraitementAfficherListe(retourNotificationRecommandation);
            }
            else
            {
                System.out.println("Vous n'avez pas de profil etudiant, veuillez en créer un");
                 
            }
        }
   
   public void notificationMessage(Socket leSocket,int id_compte_notifie) throws IOException
        {
            methodeClient c=new methodeClient();
            String requete = "NUM_ETUDIANT "+id_compte_notifie;
            String retournum_etudiant=c.envoieData(leSocket, requete);
            if (Integer.parseInt(retournum_etudiant)>0)
            {
            requete = "NOTIFICATIONMESSAGE "+retournum_etudiant;  
            //c.afficher(requete);
            String retourNotificationMessage=c.envoieData(leSocket, requete);
            c.TraitementAfficherListe(retourNotificationMessage);
            }
            else
            {
                System.out.println("Vous n'avez pas de profil etudiant, veuillez en créer un");
                 
            }
        }
     //___________________________________________________________ANONYME________________________________________________________________________________________________
   public String afficherListeProfilEtudiantAnonyme()
   {
        methodeClient c = new methodeClient(); 
        String requete="";
        c.banniere();
        requete = "AFFICHER_LISTE_PROFIL_ETUDIANT_ANONYME"; 
        return requete;
   } 
    
   public String RechercheParNomAnonyme() throws IOException
   {
        String requete="";
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        c.afficher("Entrez le nom du profil étudiant recherché :");
        c.chevron();
        String nomRecherche = entree.readLine();
        requete = "RECHERCHE_ETUDIANT_NOM_ANONYME "+nomRecherche;
        return requete;
   }
   
   public String RechercheParPrenomAnonyme() throws IOException
   {
        String requete="";
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        methodeClient c = new methodeClient();
        c.afficher("Entrez le prenom du profil étudiant recherché :");
        c.chevron();
        String prenomRecherche = entree.readLine();
        requete = "RECHERCHE_ETUDIANT_PRENOM_ANONYME "+prenomRecherche;
        return requete;
   }
   
   public void affichageMembreConnectes(Socket leSocket) throws IOException
   {
        methodeClient c = new methodeClient(); 
        c.banniere();
        c.afficher("Etudiant connecté: ");
        String requete = "CONNECTE";
        String retourAfficherListeConnecte=c.envoieData(leSocket, requete);
        c.afficher("Reponse du serveur : "+retourAfficherListeConnecte); 
        c.TraitementAfficherListe(retourAfficherListeConnecte);
   }
   
   public void etatConnecte(Socket leSocket,int id_compte) throws IOException
   {
        methodeClient c = new methodeClient(); 
        c.banniere();
        System.out.println();
        String requete = "ETATCONNECTE "+id_compte;
        String retourEtatConnecte=c.envoieData(leSocket, requete);
        c.afficher("Reponse du serveur : "+retourEtatConnecte);
   }
   
  
   
   
   
   
    //___________________________________________________________ADMIN________________________________________________________________________________________________
    
    public String connexionAdmin() throws IOException
    {
        methodeClient c = new methodeClient();
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        String requete="";
        c.banniere();
        c.afficher("Entrez votre login :");
        c.chevron();
        String loginConnexion = entree.readLine();
        c.afficher("Entrez votre mot de passe :");
        c.chevron();
        String pwdConnexion = entree.readLine();
        requete = "CONNEXIONADMIN "+loginConnexion+" "+pwdConnexion;  
        c.afficher(requete);
        return requete;
    }
    
    public String creationCompteAdmin() throws IOException
    {
        methodeClient c = new methodeClient();
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        String requete="";
        c.banniere(); 
        c.afficher("Choisissez votre login pour l'inscription :");
        c.chevron();
        String loginInscription = entree.readLine();
        c.afficher("Entrez votre mot de passe :");
        c.chevron();
        String pwdInscription = entree.readLine();
        requete = "INSCRIPTION "+loginInscription+" "+pwdInscription + " Utilisateur";
        return requete;
    }
    
    public String modificationMdpAdmin() throws IOException
    {
        methodeClient c = new methodeClient();
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        String requete="";
    
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
        requete = "CHANGE_MDP_ADMIN "+login+" "+mdp1;
        return requete;
    }
    
    public String modificationCompetencesAdmin(int id_compteProfilAdministre) throws IOException
    {
        methodeClient c = new methodeClient();
        BufferedReader entree = new BufferedReader(new InputStreamReader(System.in));
        String requete="";
        c.afficher("Entrez le n° de la nouvelle competences ? :");                                           
        c.chevron();
        String nouveleCompetence = entree.readLine();
        requete = "MODIFICATION_COMPETENCE "+nouveleCompetence+" "+id_compteProfilAdministre;
        return requete;
    }
    
    
}
