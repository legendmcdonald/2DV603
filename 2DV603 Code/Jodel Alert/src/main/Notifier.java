package main;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 * Created by Michael on 2017-05-27.
 * This will send an email to every recipient
 */
public class Notifier
{
    /**
     * This will send an email to all the registered emails within the database
     */
    public void sendMail(String theText)
    {
        // This is the mail and the it's password that we are sending the email from
        String mailFrom = "jodelalert@gmail.com";
        String password = "jodelAlert1234";

        // This is the host and port
        String host = "smtp.gmail.com";
        String port = "587";

        // Create properties
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        // Create a Session and put in authentication from jodelalert@gmail.com
        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(mailFrom, password);
            }
        });

        try
        {
            // Get the list of emails from the database
            DBhandlerJonathan handler = new DBhandlerJonathan();
            ArrayList<String> list = handler.getEmails();

            // Create MimeMessage to fill up with information
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(mailFrom));

            // Iterate over the list of emails and add it as a recipient
            for(String mails : list)
            {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(mails));
            }

            // Add subject and the actual text
            message.setSubject("You have got an Jodel Alert");
            message.setText(theText);

            // Send the message
            Transport.send(message);
        }
        catch (MessagingException m)
        {
            m.printStackTrace();
        }
    }




}
