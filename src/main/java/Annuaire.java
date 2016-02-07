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
                s.executeUpdate("INSERT INTO yahimenat.profil_etudiant(nom_etudiant,prenom_etudiant,date_naissance,mail,telephone,id_competence,id_compte) VALUES ('"+nom_etudiant+"','"+prenom_etudiant+"','"+date_naissance+"','"+mail+"','"+telephone+"','"+id_competence+"','"+id_compte+"');");
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
    
     
     

    public String afficherListeProfilEtudiant ()
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
            while (rs.next())
            {
                   resultat = resultat+"Num Etudiant : "+rs.getInt("num_etudiant")+", Nom de l'étudiant : "+rs.getString("nom_etudiant")+", Prenom de l'étudiant : "+rs.getString("prenom_etudiant")+", Date de naissance : "+rs.getString("date_naissance")+", Mail : "+rs.getString("mail")+", Numéro de telephone : "+rs.getString("telephone")+", Compétence (1-Réseaux, 2-Telecoms) :"+rs.getInt("id_competence")+"  ";       
            }
            return resultat;
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            return null;
        }
    }

       
    
    
    // PARTIE ANONYME ------------------------------------------------------------------------------------
    
    // Recherche par nom avec droits anonymes
    public String RechercherEtudiantAnonymeNom(String nom) 
    {
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select num_etudiant, nom_etudiant, prenom_etudiant from profil_etudiant where nom_etudiant = '"+nom+"';");
            if (rs.next()) 
            {
                return "Num Etudiant : "+rs.getInt("num_etudiant")+", Nom de l'étudiant : "+rs.getString("nom_etudiant")+", Prenom de l'étudiant : "+rs.getString("prenom_etudiant");
            } 
            else 
            {
                return null;
            }
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
            ResultSet rs = s.executeQuery("select num_etudiant, nom_etudiant, prenom_etudiant from profil_etudiant where prenom_etudiant = '"+prenom+"';");
            if (rs.next()) 
            {
                return "Num Etudiant : "+rs.getInt("num_etudiant")+", Nom de l'étudiant : "+rs.getString("nom_etudiant")+", Prenom de l'étudiant : "+rs.getString("prenom_etudiant");
            } 
            else 
            {
                return null;
            }
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
        
    public static void main(String[] args) throws Exception 
    {
        String resultat;
        Boolean result;
        Annuaire an1;
        an1 = new Annuaire();
        
        resultat=an1.RechercherEtudiantCompetence("1");
        //an1.majInfos("5:nom4:prenom4:18/12/1993:nom4.prenom4@gmail.com:0665758387:1");
        System.out.println(resultat);     
    }		
}
