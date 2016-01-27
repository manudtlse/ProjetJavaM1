
import java.sql.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emmanuel Ménat
 */
public class ConnexionBd {
    public void ConnexionBd(){
		String url = "jdbc:mysql://binary-digit.net:3305/bd_auth";
	    String login = "yahimenat";
	    String passwd = "odaime";
	    Connection con =null;
        
        try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, login, passwd);
                         Statement s = con.createStatement();
        	}
		catch (SQLException e) {
		e.printStackTrace();
		} catch (ClassNotFoundException e) {
		e.printStackTrace();
		} 
	}
}
