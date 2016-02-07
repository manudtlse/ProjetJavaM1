/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emmanuel Ménat
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class gestionProtocole {
	
        Connection cn =null;
        Statement st =null;
        String url = "jdbc:mysql://binary-digit.net:3305/yahimenat";
        String url2 = "jdbc:mysql://binary-digit.net:3306/yahimenat";
        String loginBDD = "yahimenat";
        String passwordBDD = "odaime";
        Annuaire an1 = new Annuaire();
        
	public String travaille (String requete) throws SQLException, ClassNotFoundException {
            
                String [] param = requete.split(" ");      
                
                // Retour OK/NOK
                String resultat = "";
                
                Class.forName("com.mysql.jdbc.Driver");
                cn = DriverManager.getConnection(url, loginBDD, passwordBDD);
                st = cn.createStatement();
                
                switch (param[0]) {
                    
                    // Creer compte
                    case "INSCRIPTION" :
                        try 
                        {
                            String login = param[1];
                            String mdp = param[2];
                            String droits = param[3];
                            Inscription id = new Inscription (login ,mdp, droits);
                            if (id.Inscription()== -1)
                            {
                                resultat = "NON OKINSCRIPTION";
                            }
                            else 
                            {
                                resultat = "OK INSCRIPTION";
                            }
                        }
                        catch (Exception e) 
                        {
                            resultat = "ERREUR INSCRIPTION";
                        }
                        break;
                        
                        // Connexion
                    case "CONNEXION" :
                        try 
                        {
                            String login = param[1];
                            String mdp = param[2];
                            Identification id = new Identification (login ,mdp); 
                            if (id.connexion()== -1)
                            {
                                resultat = "NON OKCONNEXION";
                            }
                            else 
                            {
                                int res = id.connexion();
                                resultat = "OKCONNEXION " + res;
                            }
                        }
                        catch (Exception e) 
                        {
                            resultat = "ERREUR CONNEXION";
                        }
                        break;
                        

                    case "CREATION_PROFIL" :
                        try 
                        {
                            String nom = param[1];
                            String prenom = param[2];
                            String date_naissance = param[3];
                            String mail = param[4];
                            String telephone = param[5];
                            int id_competence = Integer.parseInt(param[6]);
                            int id_compte = Integer.parseInt(param[7]);
                            String infos=nom+" "+prenom+" "+date_naissance+" "+mail+" "+telephone+" "+id_competence+" "+id_compte;
                            int resu=an1.creationProfilEtudiant(infos);
                            
                            if (resu==-1)
                            {
                                resultat = "NON OK CREATION PROFIL ETUDIANT";
                            }
                            else 
                            {
                                resultat = "OK CREATION PROFIL ETUDIANT";
                            }
                        }    
                        catch (Exception e) 
                        {
                            resultat = "ERREUR CREATION PROFIL";
                        }
                            break;
                    
                    case "AFFICHER_PROFIL_COMPTE" :
                        try 
                        {
                            String id_compte = param[1];
                            String infos = id_compte;
                            String res=an1.AfficherProfilCompte(infos);
                            resultat = "OK AFFICHAGE PROFIL ETUDIANT DU COMPTE "+res;
                        }
                        catch (Exception e) 
                        {
                            resultat = "ERREUR AFFICHARGE PROFIL ETUDIANT DU COMPTE";
                        }
                        break;    
                    
                     case "MODIFICATION_MAIL" :
                        try 
                        {
                            String nouveauMail = param[1];
                            int id_compte = Integer.parseInt(param[2]);
                            String infos =nouveauMail+" "+id_compte;
                            an1.majInfoMail(infos);
                            resultat = "OK MODIFICATIONMAIL PROFIL ETUDIANT";
                        }
                        catch (Exception e) 
                        {
                            resultat = "ERREUR MODIFICATIONMAIL PROFIL ETUDIANT";
                        }
                        break; 
                   
                     case "MODIFICATION_TEL" :
                        try 
                        {
                            String nouveauTel = param[1];
                            int id_compte = Integer.parseInt(param[2]);
                            String infos =nouveauTel+" "+id_compte;
                            an1.majInfoTel(infos);
                            resultat = "OK MODIFICATIONTEL PROFIL ETUDIANT";
                        }
                        catch (Exception e) 
                        {
                            resultat = "ERREUR MODIFICATIONTEL PROFIL ETUDIANT";
                        }

                        break;
                        
                    case "MODIFICATION_COMPETENCE" :
                        try 
                        {
                            String nouvelleCompetence = param[1];
                            int id_compte = Integer.parseInt(param[2]);
                            String infos =nouvelleCompetence+" "+id_compte;
                            an1.majInfoCompetence(infos);
                            resultat = "OK MODIFICATION COMPETENCE DU PROFIL ETUDIANT";
                        }
                        catch (Exception e) 
                        {
                            resultat = "ERREUR MODIFICATION COMPETENCE PROFIL ETUDIANT";
                        }

                        break;
                    
                     
                     
                     case "AFFICHER_LISTE_PROFIL_ETUDIANT" :
                        try
                        {
                            String res = an1.afficherListeProfilEtudiant();
                            resultat= "OK AFFICHAGE LISTE PROFILS  "+res;
                           
                        }
                        catch (Exception e) 
                        {
                            resultat = "NON OK AFFICHAGE LISTE PROFIL ETUDIANT";
                        }
                        break;    
                        
                    // Recherche par nom
                    case "RECHERCHE_ETUDIANT_NOM" :
                        try
                        {
                            String nomRecherche = param[1];
                            String res = an1.RechercherEtudiantNom(nomRecherche);
                            resultat= "OK RECHERCHE PAR NOM ETUDIANT  "+res;
                        }
                        catch (Exception e) 
                        {
                            resultat = "NON OK RECHERCHE PAR NOM ETUDIANT";
                        }
                        break;
                    
                    // Recherche par prenom    
                    case "RECHERCHE_ETUDIANT_PRENOM" :
                        try
                        {
                            String prenomRecherche = param[1];
                            String res = an1.RechercherEtudiantPrenom(prenomRecherche);
                            resultat= "OK RECHERCHE ETUDIANT PAR PRENOM  "+res;
                        }
                        catch (Exception e) 
                        {
                            resultat = "NON OK RECHERCHE ETUDIANT PAR PRENOM";
                        }
                        break;                        
                    
                    // Recherche par mail    
                    case "RECHERCHE_ETUDIANT_MAIL" :
                        try
                        {
                            String mailRecherche = param[1];
                            String res = an1.RechercherEtudiantMail(mailRecherche);
                            resultat= "OK RECHERCHE ETUDIANT PAR MAIL  "+res;
                        }
                        catch (Exception e) 
                        {
                            resultat = "NON OK RECHERCHE ETUDIANT PAR MAIL";
                        }
                        break;                        
                     
                    // Recherche par competence    
                    case "RECHERCHE_ETUDIANT_COMPETENCE" :
                        try
                        {
                            String competenceRecherche = param[1];
                            String res = an1.RechercherEtudiantCompetence(competenceRecherche);
                            resultat= "OK RECHERCHE ETUDIANT PAR COMPETENCE  "+res;
                        }
                        catch (Exception e)
                        {
                            resultat = "NON OK RECHERCHE ETUDIANT PAR COMEPTENCE";
                        }
                        break;                       
                            
                    case "CHANGE_MDP" :
                        try
                        {
                            String login = param[1];
                            String mdpActuel = param[2];
                            String nouveauMdp = param[3];
                            int id_compte = Integer.parseInt(param[4]);
                            String infos=(nouveauMdp+" "+id_compte);
                            Identification id = new Identification (login ,mdpActuel);
                            if (id.connexion()!=1)
                            {
                                Boolean res= id.ChangeMdp(infos);
                                if (res=true)
                                {
                                    resultat = "OK CHANGEMENT MDP "+res;  
                                }
                                else
                                {
                                    resultat = "NON OK CHANGEMENT MDP "+res;
                                }    
                            }
                            else
                            {
                                resultat="Combinaison Identifiant/Mot de passe érronée";
                            }
                            
                        }
                        catch (Exception e) 
                        {
                            resultat = "ERREUR CHANGEMENT MDP";
                        }
                        break;
                        
                        
                        
                        
                        
                        
                        
                        
                        
                        // ------------------------------ PARTIE ANONYMEEEEEEEEEEEEEEE-----------------------------
                        
                        case "AFFICHER_LISTE_PROFIL_ETUDIANT_ANONYLE" :
                        try
                        {
                            String res = an1.afficherListeProfilEtudiantAnonyme();
                            resultat= "OK AFFICHAGE LISTE PROFILS  "+res;
                           
                        }
                        catch (Exception e) 
                        {
                            resultat = "NON OK AFFICHAGE LISTE PROFIL ETUDIANT";
                        }
                        break;    
                         
                         
                         case "RECHERCHE_ETUDIANT_NOM_ANONYME" :
                        try
                        {
                            String nomRecherche = param[1];
                            String res = an1.RechercherEtudiantAnonymeNom (nomRecherche);
                            resultat= "OK RECHERCHE ANONYME NOM ETUDIANT  "+res;
                        }
                        catch (Exception e) 
                        {
                            resultat = "NON OK RECHERCHE NOM ANONYME ETUDIANT";
                        }
                        break;
                        
                        
                         // Recherche par prenom (anonyme)
                        case "RECHERCHE_ETUDIANT_PRENOM_ANONYME" :
                        try
                        {
                            String prenomRecherche = param[1];
                            String res = an1.RechercherEtudiantAnonymePrenom (prenomRecherche);
                            resultat= "OK RECHERCHE ANONYME PRENOM ETUDIANT  "+res;
                        }
                        catch (Exception e) 
                        {
                            resultat = "NON OK ANONYME RECHERCHE PRENOM ETUDIANT";
                        }
                        break;
                        
                    default: resultat = "ERREUR REQUETE INCONNUE";
                    
                }
            return resultat; 
	}	
}


