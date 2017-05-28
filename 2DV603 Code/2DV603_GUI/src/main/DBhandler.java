package main;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.security.Key;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Michael on 2017-05-16.
 * This will take the list of keywords and emails and return ArrayList of trings
 * to get it easier to fetch from the other classes
 */
@Named
@SessionScoped
public class DBhandler implements Serializable
{
    /**
     * This will get all the emails from the database
     * @return list of emails
     */
    public ArrayList<String> getEmails()
    {
        // Create an Email object and get the list of emails
        Email newEmail = new Email();
        List<Email> list = newEmail.getEmails();

        // Create a ArrayList of strings
        ArrayList<String> arrayOfEmails = new ArrayList<>();

        // Iterate over the list of emails and add it to the new ArrayList
        for(Email em : list)
        {
            arrayOfEmails.add(em.getEmail());
        }

        return arrayOfEmails;
    }

    /**
     * This will get all the keywords from the database
     * @return list of keywords
     */
    public ArrayList<String> getKeywords()
    {
        // Create a Keyword object and get the list of keywords
        Keyword newKey = new Keyword();
        List<Keyword> list = newKey.getKeywords();

        // Create a ArrayList of strings
        ArrayList<String> arrayOfKeywords = new ArrayList<>();

        // Iterate over the list of keywords and add it to the new ArrayList
        for(Keyword key : list)
        {
            arrayOfKeywords.add(key.getKeyword());
        }

        return arrayOfKeywords;
    }
}
