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
                          /* Soit on met les foncion ici, soit on les met dans annuiaire et ici on appel les fonction 
                          Je pense que vu que cest gestion protocole qui decoupe les chaines qu'on recoit, ca allege si
                          on appel juste les fonctions qu'on laisse dans annuaire, comme ca les fonctions concernant l'annuaire
                          Restent dans annuaire, et celles de l'identification on les met dans une classe identification et on fait
                          2 gestion protocols, 1 pour l'annuaire et 1 pour l'identification (Tessier a conseillé a ludo de faire 2
                          Gestion protocol, il aime bien donc si on le fait on est bien)*/
                     
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
