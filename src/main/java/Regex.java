/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author many31
 */
import java.io.*;
import java.util.regex.*;

public class Regex 
{

    private static Pattern pattern;
    private static Matcher matcher;

    public boolean RegexMail(String a)
    {
        pattern = Pattern.compile("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z]+\\.[a-zA-Z]{2,6}$");
        matcher = pattern.matcher(a);
        boolean result=false;
        while(matcher.find()) 
        {
            result= true ;
        
        }
    return result;
    }
    
    public boolean RegexDate(String a)
    {
        pattern = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");
        matcher = pattern.matcher(a);
        boolean result=false;
        while(matcher.find()) 
        {
            result= true ;
        
        }
    return result;
    }
    
    public boolean RegexTel(String a)
    {                               
        pattern = Pattern.compile("^0[1-9]([-. ]?[0-9]{2}){4}$");
        matcher = pattern.matcher(a);
        boolean result=false;
        while(matcher.find()) 
        {
            result= true ;
        
        }
    return result;
    }
    
    public static void main(String[] args) throws Exception 
    {
        Regex reg=new Regex();
        String Tel="abc123.aaa777@cde";
        Boolean verif=reg.RegexMail(Tel);
        
        System.out.println(verif);     
    }	
}