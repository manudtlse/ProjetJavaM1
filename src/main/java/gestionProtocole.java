/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emmanuel Ménat
 */
import java.util.Date;

public class gestionProtocole {
	

	public String travaille (String requete) {
		
		String [] param = requete.split(" ");
		
		// Retour OK/NOK
		String resultat = "";
		
		
		switch (param[0]) {

        // Creer compte 
        case "INSCRIPTION" :
        		try {
                            
            		//creerCompte(param[1], Double.parseDouble(param[2]));
                       
                        // ce qui correspond à mettre en base de données
                          // Soit on met les foncion ici, soit on les met dans annuiaire et ici on appel les fonction                       
            		resultat = "OK CREATION";
        		} catch (Exception e) {
        			resultat = "ERREUR CREATION";
        		}
        		break;
                        
   
	    		
         default: resultat = "ERREUR REQUETE INCONNUE";
        	 
		}
		return resultat;
		
	}
	
}
