/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emmanuel Ménat
 */



import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Annuaire 
{
    private String url1="jdbc:mysql://binary-digit.net:3305/yahimenat";//ServeurIdentification
    private String url2="jdbc:mysql://binary-digit.net:3306/yahimenat";//ServeurAnnuaire
    private String bdlogin="yahimenat";
    private String bdmdp="odaime";
    Connection con;
    
    public Annuaire() 
    {
            try 
            {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
                // on cree un objet Statement qui va permettre l'execution des requetes
                Statement s = con.createStatement();
            } 
            catch(Exception e)
            {
                // il y a eu une erreur
                e.printStackTrace();
            }
    }
    

    // Fonction pour créer un profil étudiant (renvoie 1 si insertion OK, -1 si NONOK)  
    public int creationProfilEtudiant(String infos) 
    {
        int resultat = 0;
        try 
        {
            String [] tab = infos.split(" ");
            if(tab.length != 7) 
                    return resultat=-1;
            String nom_etudiant = tab[0];
            String prenom_etudiant = tab[1];
            String date_naissance = tab[2];
            String mail = tab[3];
            String telephone = tab[4];
            int id_competence=Integer.parseInt(tab[5]);
            int id_compte = Integer.parseInt(tab[6]);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();                    
            ResultSet Result;
            Result = s.executeQuery("SELECT * FROM yahimenat.profil_etudiant WHERE id_compte ='"+id_compte+"';");
            if (!Result.next())
            {
                s.executeUpdate("INSERT INTO yahimenat.profil_etudiant(nom_etudiant,prenom_etudiant,date_naissance,mail,telephone,id_competence,id_compte,connecte) VALUES ('"+nom_etudiant+"','"+prenom_etudiant+"','"+date_naissance+"','"+mail+"','"+telephone+"','"+id_competence+"','"+id_compte+"','1');");
               InstantMessage im1 = new InstantMessage();
                        im1.Connecte(id_compte);
                resultat=1;
            }
            else
                resultat=-1;
            } 
        catch(Exception ex) 
        {
        // il y a eu une erreur
        ex.printStackTrace();
        }
        return resultat;
    }

    // Fonction pour modifier le mail      
    public boolean majInfoMail(String infos) 
    {
        try 
        {
            String [] tab = infos.split(" ");
            if(tab.length != 2) 
                    return false;
            String mail = tab[0];
            int id_compte = Integer.parseInt(tab[1]);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();

            // on modifie le mail du profil_etudiant qui a le numéro de compte associé
            if (s.executeUpdate("update profil_etudiant set  mail='"+mail+"' where id_compte='"+id_compte+"';")==1)
                    return true;
            else
                    return false;	
        } 
        catch(Exception ex) 
        {
            // il y a eu une erreur
            ex.printStackTrace();
            return false;
        }
    }
        
    // Fonction pour modifier le tel
    public boolean majInfoTel(String infos) 
    {
        try 
        {
            String [] tab = infos.split(" ");
            if(tab.length != 2) 
                    return false;
            String nouveauTelephone = tab[0];
            int id_compte = Integer.parseInt(tab[1]);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();

            // on modifie le mail du profil_etudiant qui a le numéro de compte associé
            if (s.executeUpdate("update profil_etudiant set  telephone='"+nouveauTelephone+"' where id_compte='"+id_compte+"';")==1)
                    return true;
            else
                    return false;	
        } 
        catch(Exception ex) 
        {
            // il y a eu une erreur
            ex.printStackTrace();
            return false;
        }
    }
    
    // Fonction pour modifier la competence             
    public boolean majInfoCompetence(String infos) 
    {
        try 
        {
            String [] tab = infos.split(" ");
            if(tab.length != 2) 
                    return false;
            String id_competence = tab[0];
            int id_compte = Integer.parseInt(tab[1]);

            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();

            // on modifie le mail du profil_etudiant qui a le numéro de compte associé
            if (s.executeUpdate("update profil_etudiant set  id_competence='"+id_competence+"' where id_compte='"+id_compte+"';")==1)
                    return true;
            else
                    return false;	
        } 
        catch(Exception ex) 
        {
            // il y a eu une erreur
            ex.printStackTrace();
            return false;
        }
    }
        
    // Fonction qui affiche les informations d'un profil
    public String AfficherProfilCompte(String infos) 
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();

            int id_compte = Integer.parseInt(infos);
            ResultSet rs = s.executeQuery("select * from profil_etudiant where id_compte = '"+id_compte+"';");
            if (rs.next()) {

                return rs.getInt("num_etudiant")+" "+rs.getString("nom_etudiant")+" "+rs.getString("prenom_etudiant")+" "+rs.getString("date_naissance")+" "+rs.getString("mail")+" "+rs.getString("telephone")+" "+rs.getInt("id_competence")+" "+rs.getInt("id_compte");
            } 
            else 
            {
                return null;

            }
        } 
        catch(Exception ex) 
        {
                // il y a eu une erreur
                ex.printStackTrace();
                return null;
        }
    }
    
    
    // Recherche par Nom 
    public String RechercherEtudiantNom(String nom) 
    {
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from profil_etudiant where nom_etudiant = '"+nom+"';");
           String resultat="";
                rs.last();
                int NbLignes=rs.getRow();
                rs.beforeFirst();
                resultat=NbLignes+"  ";
            while (rs.next()) 
            {  
                resultat=resultat+"Num Etudiant : "+rs.getInt("num_etudiant")+", Nom de l'étudiant : "+rs.getString("nom_etudiant")+", Prenom de l'étudiant : "+rs.getString("prenom_etudiant")+", Date de naissance : "+rs.getString("date_naissance")+", Mail : "+rs.getString("mail")+", Numéro de telephone : "+rs.getString("telephone")+", Compétence (1-Réseaux, 2-Telecoms, 3-Developement) :"+rs.getInt("id_competence")+"  ";
            } 
            return resultat;
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            // il y a eu une erreur
            ex.printStackTrace();
            return null;
        }
    }
    
    // Recherche par prenom
    public String RechercherEtudiantPrenom(String prenom) 
    {
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from profil_etudiant where prenom_etudiant = '"+prenom+"';");
            String resultat="";
                rs.last();
                int NbLignes=rs.getRow();
                rs.beforeFirst();
                resultat=NbLignes+"  ";
            while (rs.next()) 
            {  
                resultat=resultat+"Num Etudiant : "+rs.getInt("num_etudiant")+", Nom de l'étudiant : "+rs.getString("nom_etudiant")+", Prenom de l'étudiant : "+rs.getString("prenom_etudiant")+", Date de naissance : "+rs.getString("date_naissance")+", Mail : "+rs.getString("mail")+", Numéro de telephone : "+rs.getString("telephone")+", Compétence (1-Réseaux, 2-Telecoms, 3-Developement) :"+rs.getInt("id_competence")+"  ";
            } 
            return resultat;
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            // il y a eu une erreur
            ex.printStackTrace();
            return null;
        }
    }
    
    // Recherche par mail
    public String RechercherEtudiantMail(String mail) 
    {
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from profil_etudiant where mail = '"+mail+"';");
            String resultat="";
                rs.last();
                int NbLignes=rs.getRow();
                rs.beforeFirst();
                resultat=NbLignes+"  ";
            while (rs.next()) 
            {  
                resultat=resultat+"Num Etudiant : "+rs.getInt("num_etudiant")+", Nom de l'étudiant : "+rs.getString("nom_etudiant")+", Prenom de l'étudiant : "+rs.getString("prenom_etudiant")+", Date de naissance : "+rs.getString("date_naissance")+", Mail : "+rs.getString("mail")+", Numéro de telephone : "+rs.getString("telephone")+", Compétence (1-Réseaux, 2-Telecoms, 3-Developement) :"+rs.getInt("id_competence")+"  ";
            } 
            return resultat;
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            // il y a eu une erreur
            ex.printStackTrace();
            return null;
        }
    }   
    
    // Recherche par competence
    public String RechercherEtudiantCompetence(String Competence)
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from profil_etudiant where id_competence = '"+Competence+"';");
            String resultat="";
            rs.last();
            int NbLignes=rs.getRow();
            rs.beforeFirst();
            resultat=NbLignes+"  ";
            while (rs.next())
            { 
                resultat=resultat+"Num Etudiant : "+rs.getInt("num_etudiant")+", Nom de l'étudiant : "+rs.getString("nom_etudiant")+", Prenom de l'étudiant : "+rs.getString("prenom_etudiant")+", Date de naissance : "+rs.getString("date_naissance")+", Mail : "+rs.getString("mail")+", Numéro de telephone : "+rs.getString("telephone")+", Compétence (1-Réseaux, 2-Telecoms, 3-Developement) :"+rs.getInt("id_competence")+"  ";
            } 
            return resultat;
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            // il y a eu une erreur
            ex.printStackTrace();
            return null;
        }
    }   
    
    // Affichage de la liste des profils étudiants triée par nom
    public String afficherListeProfilEtudiantTriNom ()
    {
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from profil_etudiant ORDER BY nom_etudiant");
            
            String resultat="";
            rs.last();
            int NbLignes=rs.getRow();
            rs.beforeFirst();
            resultat=NbLignes+"  ";
            while (rs.next())
            {
                   resultat = resultat+"Num Etudiant : "+rs.getInt("num_etudiant")+", Nom de l'étudiant : "+rs.getString("nom_etudiant")+", Prenom de l'étudiant : "+rs.getString("prenom_etudiant")/*+", Date de naissance : "+rs.getString("date_naissance")+", Mail : "+rs.getString("mail")+", Numéro de telephone : "+rs.getString("telephone")*/+", Compétence (1-Réseaux, 2-Telecoms,3-Dev) :"+rs.getInt("id_competence")+", Connecté (1-Oui,2-Non) :"+rs.getInt("Connecte")+"  ";       
            }
            return resultat;
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            return null;
        }
    }
    
    // Affichage de la liste des profils étudiants triée par prenom
    public String afficherListeProfilEtudiantTriPrenom ()
    {
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from profil_etudiant ORDER BY prenom_etudiant");
            
            String resultat="";
            rs.last();
            int NbLignes=rs.getRow();
            rs.beforeFirst();
            resultat=NbLignes+"  ";
            while (rs.next())
            {
                   resultat = resultat+"Num Etudiant : "+rs.getInt("num_etudiant")+", Nom de l'étudiant : "+rs.getString("nom_etudiant")+", Prenom de l'étudiant : "+rs.getString("prenom_etudiant")/*+", Date de naissance : "+rs.getString("date_naissance")+", Mail : "+rs.getString("mail")+", Numéro de telephone : "+rs.getString("telephone")*/+", Compétence (1-Réseaux, 2-Telecoms, 3-Dev) :"+rs.getInt("id_competence")+", Connecté (1-Oui,2-Non) :"+rs.getInt("Connecte")+"  ";       
            }
            return resultat;
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            return null;
        }
    }
      
    // Affichage de la liste des profils étudiants triée par compétence
    public String afficherListeProfilEtudiantTriCompetence ()
    {
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from profil_etudiant ORDER BY id_competence");
            
            String resultat="";
            rs.last();
            int NbLignes=rs.getRow();
            rs.beforeFirst();
            resultat=NbLignes+"  ";
            while (rs.next())
            {
                   resultat = resultat+"Num Etudiant : "+rs.getInt("num_etudiant")+", Nom de l'étudiant : "+rs.getString("nom_etudiant")+", Prenom de l'étudiant : "+rs.getString("prenom_etudiant")/*+", Date de naissance : "+rs.getString("date_naissance")+", Mail : "+rs.getString("mail")+", Numéro de telephone : "+rs.getString("telephone")*/+", Compétence (1-Réseaux, 2-Telecoms, 3-Dev) :"+rs.getInt("id_competence")+", Connecté (1-Oui,2-Non) :"+rs.getInt("Connecte")+"  ";       
            }
            return resultat;
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            return null;
        }
    }
    
    // Affichage de la liste des profils étudiants triée par compétence
    public String afficherEtudiantConnecte()
    {
        try 
        {   
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from profil_etudiant where connecte='1'");
            
            String resultat="";
            rs.last();
            int NbLignes=rs.getRow();
            rs.beforeFirst();
            resultat=NbLignes+"  ";
            while (rs.next())
            {
                   resultat = resultat+"Num Etudiant : "+rs.getInt("num_etudiant")+", Nom de l'étudiant : "+rs.getString("nom_etudiant")+", Prenom de l'étudiant : "+rs.getString("prenom_etudiant")/*+", Date de naissance : "+rs.getString("date_naissance")+", Mail : "+rs.getString("mail")+", Numéro de telephone : "+rs.getString("telephone")*/+", Compétence (1-Réseaux, 2-Telecoms, 3-Dev) :"+rs.getInt("id_competence")+"  ";       
            }
            return resultat;
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            return null;
        }
    }
    
    // Permet de gerer la confidentialité
    public boolean confidentialite(String infos) throws ClassNotFoundException    //Met les parametre de confidentialité dans la BD
    {
        boolean resultat = false;
        try 
        {
            String [] tab = infos.split(" ");
            /*if(tab.length != 2)
            return false;*/
            int id_compte = Integer.parseInt(tab[0]);
            Boolean v_date_naissance =Boolean.parseBoolean(tab[1]);
            Boolean v_mail = Boolean.parseBoolean(tab[2]);
            Boolean v_telephone = Boolean.parseBoolean(tab[3]);
            /*System.out.println("idcompte"+id_compte);
            System.out.println("v_date_naissance"+v_date_naissance);
            System.out.println("v_mail"+v_mail);
            System.out.println("v_telephone"+v_telephone);*/
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            resultat = s.executeUpdate("INSERT INTO confidentialite(v_date_naissance,v_mail,v_téléphone,id_compte) VALUES ("+v_date_naissance+","+v_mail+","+v_telephone+",'"+id_compte+"');")==1;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Annuaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultat;
    }
    
    // Permet de mettre à jour la confidentialité
    public boolean maj_confidentialite(String infos) throws ClassNotFoundException    //Met les parametre de confidentialité dans la BD
    {
        boolean resultat = false;
        try 
        {
            String [] tab = infos.split(" ");
            /*if(tab.length != 2)
            return false;*/
            int id_compte = Integer.parseInt(tab[0]);
            Boolean v_date_naissance =Boolean.parseBoolean(tab[1]);
            Boolean v_mail = Boolean.parseBoolean(tab[2]);
            Boolean v_telephone = Boolean.parseBoolean(tab[3]);
            /*System.out.println("idcompte"+id_compte);
            System.out.println("v_date_naissance"+v_date_naissance);
            System.out.println("v_mail"+v_mail);
            System.out.println("v_telephone"+v_telephone);*/
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            resultat = s.executeUpdate("UPDATE confidentialite set v_date_naissance="+v_date_naissance+",v_mail="+v_mail+",v_téléphone="+v_telephone+" where id_compte='"+id_compte+"';")==1;
        } 
        catch (SQLException ex) 
        {
            Logger.getLogger(Annuaire.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultat;
    }
    
    
    public int numIdCompte(int numEtudiant)
    {
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select id_compte from profil_etudiant where num_etudiant='"+numEtudiant+"';");
            
            int resultat=-1;
           
            while (rs.next())
            {
                   resultat = rs.getInt("id_compte");
            }
            return resultat;
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            return -1;
        }
        
    }
    
    // Retour le numéro étudiant en fonction de l'id du compte
    public int numEtudiant(int id_compte)
    {
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select num_etudiant from profil_etudiant where id_compte='"+id_compte+"';");
            
            int resultat=-1;
           
            while (rs.next())
            {
                   resultat = rs.getInt("num_etudiant");
            }
            return resultat;
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            return -1;
        }
        
    }
    
    // Retour le numéro étudiant en fonction de l'id du compte
    public int liker_profil(int num_etudiantLikeur, int num_etudiantLiker)
    {
        int resultat=4;
        try 
        { 
            int nb_like=0;
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            
            ResultSet rs2 = s.executeQuery("select * from Like_profil where num_etudiantLiker='"+num_etudiantLiker+"' AND num_etudiantLikeur='"+num_etudiantLikeur+"';");
            if(!rs2.next())
            {
                ResultSet rs = s.executeQuery("select nb_like from profil_etudiant where num_etudiant='"+num_etudiantLiker+"';");
                if (rs.next())
                {
                    nb_like = rs.getInt("nb_like")+1;
                    
                    s.executeUpdate("UPDATE profil_etudiant set nb_like='"+nb_like+"' where num_etudiant='"+num_etudiantLiker+"';");
                    s.executeUpdate("INSERT INTO Like_profil (num_etudiantLikeur,num_etudiantLiker) values ('"+num_etudiantLikeur+"','"+num_etudiantLiker+"');");
                    resultat= 1;
                }
            }
            else
            {
                resultat=2;
            }   
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            resultat=-1;
        }
        System.out.println("resultat: "+resultat);
        return resultat;
    }
    
    //Retourn la description complete du profil en fonction des parametres de confidentialités
    public String afficherProfilEtudiantComplet (int num_etudiant)
    {
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            Statement s2 = con.createStatement();
            ResultSet rs = s.executeQuery("select * from profil_etudiant WHERE num_etudiant='"+num_etudiant+"';");
            ResultSet rs3;
            String resultat="";
            if (rs.next())
            {
                    String nom_etudiant= rs.getString("nom_etudiant");
                    System.out.println("nom_etudiant"+nom_etudiant);
                    String prenom_etudiant= rs.getString("prenom_etudiant");
                    String date_naiss = rs.getString("date_naissance");
                    String mail =rs.getString("mail");
                    String tel = rs.getString("telephone");
                    int id_competence = rs.getInt("id_competence");
                    System.out.println("id_competence : "+id_competence);
                    rs3=s2.executeQuery("select label_competence from competence where id_competence='"+id_competence+"';");
                    String label_competence="";
                    while(rs3.next())
                    {
                        label_competence=rs3.getString("label_competence");
                    }
                    Annuaire an1= new Annuaire();
                    int id_compte=an1.numIdCompte(num_etudiant);
                    ResultSet rs2=s.executeQuery("select * from confidentialite where id_compte='"+id_compte+"';");
                    if (rs2.next())
                    {
                        int v_date_naissance= rs2.getInt("v_date_naissance");
                        int v_mail= rs2.getInt("v_mail");
                        int v_tel= rs2.getInt("v_téléphone");
                        
                        if (v_date_naissance==0) 
                            {
                                date_naiss="Confidentiel";
                            }
                        if (v_mail==0) 
                            {
                                mail="Confidentiel";
                            }
                        if (v_tel==0) 
                            {
                                tel="Confidentiel";
                            }
                    }
                    String ListeCompetenceProfil=an1.afficherListeCompetencesProfil(num_etudiant);
                    resultat="N°Etu: "+num_etudiant+", Nom: "+nom_etudiant+", Prénom: "+prenom_etudiant+", Date de naissance: "+date_naiss+", Mail: "+mail+", Téléphone: "+tel+", Compétences: "+label_competence+ "("+id_competence+") "+ListeCompetenceProfil;
            }
           return resultat; 
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            return "Exception";
        }
    }
    
    // Affichage de la liste des profils étudiants triée par nom
    public String afficherListeCompetences()
    {
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from competence ORDER BY id_competence;");
            rs.last();
            int NbLignes=rs.getRow();
            rs.beforeFirst();
            String resultat=NbLignes+"  ";
            while (rs.next())
            {
                   resultat = resultat+rs.getInt("id_competence")+": "+rs.getString("label_competence")+",  ";       
            }
            return resultat;
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            return null;
        }
    }
    
    public int recommanderCompetence(int id_competence,int numEtudiantRecommander,int numEtudiantRecommandeur)
    {
        int resultat=-1; //Initialisation sur une erreur
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs=s.executeQuery("SELECT * FROM profil_competence where num_etudiant='"+numEtudiantRecommander+"' AND id_competence='"+id_competence+"' AND num_etudiant_recommandeur='"+numEtudiantRecommandeur+"';");
            if(!rs.next())
            {
                s.executeUpdate("INSERT INTO profil_competence (num_etudiant,id_competence,nb_recommandation,num_etudiant_recommandeur) values ('"+numEtudiantRecommander+"','"+id_competence+"','1','"+numEtudiantRecommandeur+"');");
                resultat=1; //Recommander competence OK
            }
            else
            {
                //competence deja recommander pour ce profil
                resultat=2;
            }
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            resultat=-1; 
        }
        return resultat;
    }
    
    public int SupprimerRecommandationCompetence(int id_competence,int numEtudiantRecommander,int numEtudiantRecommandeur)
    {
        int resultat=-1; //Initialisation sur une erreur
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs=s.executeQuery("SELECT * FROM profil_competence where num_etudiant="+numEtudiantRecommander+" AND id_competence="+id_competence+" AND num_etudiant_recommandeur="+numEtudiantRecommandeur+";");
            if(rs.next())
            {
                s.executeUpdate("DELETE FROM profil_competence WHERE num_etudiant="+numEtudiantRecommander+" AND id_competence="+id_competence+" AND num_etudiant_recommandeur="+numEtudiantRecommandeur+";");
                resultat=1; //Suppresion Recommandation competence OK
            }
            else
            {
                resultat=2;
            }
              
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            resultat=-1; 
        }
        return resultat;
    }
    
    //Retourn la liste de toutes les compétences d'un étudiant 
    public String afficherListeCompetencesProfil(int num_etudiant)
    {
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            Statement s2 = con.createStatement();
            ResultSet rs = s.executeQuery("select id_competence from profil_competence WHERE num_etudiant='"+num_etudiant+"' ORDER BY id_competence ;");
            ResultSet rs2;
            /*rs.last();
            int NbLignes=rs.getRow();
            rs.beforeFirst();*/
            String resultat=/*NbLignes+"  "*/"";
            int id_competence=0;
            String S_id_competence="";
            String label_competence="";
            while (rs.next())
            {
                id_competence=rs.getInt("id_competence");
                rs2=s2.executeQuery("select label_competence from competence WHERE id_competence='"+id_competence+"';");
                if(rs2.next());
                {
                    label_competence=rs2.getString("label_competence");
                }
                resultat = resultat+label_competence+"("+id_competence+")"+" ";
                rs2.beforeFirst();
                
            } 
            return resultat;
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            return null;
        }
    }
    // PARTIE ANONYME ------------------------------------------------------------------------------------
    // Affiche la liste des profils étudiant anonyme
    public String afficherListeProfilEtudiantAnonyme ()
    {
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from profil_etudiant");
            
            String resultat="";
            rs.last();
            int NbLignes=rs.getRow();
            rs.beforeFirst();
            resultat=NbLignes+"  ";
            // Tant qu'il y a des lignes dans le Resultset
            while (rs.next())
            {
                   resultat = resultat+"Num Etudiant : "+rs.getInt("num_etudiant")+", Nom de l'étudiant : "+rs.getString("nom_etudiant")+", Prenom de l'étudiant : "+rs.getString("prenom_etudiant")+"  ";       
            }
            return resultat;
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            return null;
        }
    }
    
    
    
    // Recherche par nom avec droits anonymes
    public String RechercherEtudiantAnonymeNom(String nom) 
    {
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select num_etudiant, nom_etudiant, prenom_etudiant, id_competence from profil_etudiant where nom_etudiant = '"+nom+"';");
                        
            String resultat="";
            rs.last();
            int NbLignes=rs.getRow();
            rs.beforeFirst();
            resultat=NbLignes+"  ";
            while (rs.next())
            {
                   resultat = resultat+"Num Etudiant : "+rs.getInt("num_etudiant")+", Nom de l'étudiant : "+rs.getString("nom_etudiant")+", Prenom de l'étudiant : "+rs.getString("prenom_etudiant")+", Competence (1-Réseaux, 2-Telecoms, 3-Developement) : "+rs.getInt("id_competence")+"  ";       
            }
            return resultat;
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            // il y a eu une erreur
            ex.printStackTrace();
            return null;
        }
    }
    // Recherche par prenom avec droits anonymes
    public String RechercherEtudiantAnonymePrenom(String prenom) 
    {
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select num_etudiant, nom_etudiant, prenom_etudiant, id_competence from profil_etudiant where prenom_etudiant = '"+prenom+"';");
            String resultat="";
            rs.last();
            int NbLignes=rs.getRow();
            rs.beforeFirst();
            resultat=NbLignes+"  ";
            while (rs.next())
            {
                   resultat = resultat+"Num Etudiant : "+rs.getInt("num_etudiant")+", Nom de l'étudiant : "+rs.getString("nom_etudiant")+", Prenom de l'étudiant : "+rs.getString("prenom_etudiant")+", Competence (1-Réseaux, 2-Telecoms, 3-Developement) : "+rs.getInt("id_competence")+"  ";       
            }
            return resultat;
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            // il y a eu une erreur
            ex.printStackTrace();
            return null;
        }
    }

   
    
    public void fermer() throws Exception 
    {		
            try 
            {
                con.close();
            } 
            catch(Exception ex) 
            {
                // il y a eu une erreur
                ex.printStackTrace();
            }
    }
    
   
    // Pour test
    public static void main(String[] args) throws Exception 
    {
        String resultat;
        Boolean result;
        Annuaire an1;
        an1 = new Annuaire();
        String res=an1.afficherProfilEtudiantComplet(1);
        System.out.println(res);
    }		
}