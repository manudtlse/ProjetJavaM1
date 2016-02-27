
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author many31
 */
public class MessageDiffere {
    private String url1="jdbc:mysql://binary-digit.net:3305/yahimenat";//ServeurIdentification
    private String url2="jdbc:mysql://binary-digit.net:3306/yahimenat";//ServeurAnnuaire
    private String bdlogin="yahimenat";
    private String bdmdp="odaime";
    Connection con;
    
    public MessageDiffere() 
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
    
    public String LireMessage(int numEtudiant)
    {
       String resultat="";
       try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("select * from messagerie where num_etudiantDestinataire ='"+numEtudiant+"';");
            rs.last();
            int NbLignes=rs.getRow();
            rs.beforeFirst();
            resultat=NbLignes+"  ";
            while (rs.next())
            {
                   resultat = resultat+"num_etudiantExpediteur : "+rs.getInt("num_etudiantExpediteur")+", message : "+rs.getString("message")+"  ";       
            }
            
            return resultat;
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            return null;
        }
    }
    
    public void effacerMessage(int numEtudiant) throws SQLException
    {
           
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            s.executeUpdate("DELETE FROM messagerie WHERE num_etudiantDestinataire = '"+numEtudiant+"';");
            
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(MessageDiffere.class.getName()).log(Level.SEVERE, null, ex);
            
        } 
    }
    
    public boolean envoyerMessage(int numEtudiantExpediteur,int numEtudiantDest,String message)
    {
       boolean resultat=false;
       try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();  
            s.executeUpdate("INSERT INTO yahimenat.messagerie(num_etudiantExpediteur,num_etudiantDestinataire,message) VALUES ('"+numEtudiantExpediteur+"','"+numEtudiantDest+"','"+message+"');");
            resultat=true;  
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            resultat=false; 
        }
       return resultat;
    }
    
       public static void main(String[] args) throws Exception 
    {
        String resultat;
        Boolean result;
        MessageDiffere mes1;
        mes1 = new MessageDiffere();
        
        mes1.envoyerMessage(5, 5, "resultat");
        
        String message=mes1.LireMessage(5);
        System.out.println("mes1.LireMessage(5)= "+message);
          
    }		     
       
       
    
}
