package main;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Created by Michael on 2017-05-16.
 * This class will handle the emails that will be added in the GUI
 */

@Named
@SessionScoped
public class Email implements Serializable
{
    // Create fields
    private String email;
    private String password;
    private String adminOrUser;
    private String dbUsername;
    private String dbPassword;
    private String dbAdminOrUser;
    ResultSet rs;
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/2dv603";
    private static final String DB_USERNAME = "user";
    private static final String DB_PASSWORD = "user123";

    /**
     * This wil get the email
     * @return a email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * This will set the email
     * @param email is the email that will be set
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * This will get the password
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * This will set the password
     * @param password is the password that will be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This will get admin or user
     * @return admin or user
     */
    public String getAdminOrUser() {
        return adminOrUser;
    }

    /**
     * This will set admin or user
     * @param adminOrUser is the admin or the user that wil be set
     */
    public void setAdminOrUser(String adminOrUser) {
        this.adminOrUser = adminOrUser;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public void setDbUsername(String dbUsername) {
        this.dbUsername = dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    /**
     * This will add a email from the GUI to the database
     * @return a string containing the sql injection
     */
    public String addEmail()
    {
        String sql = "";

        try
        {
            // Create properties and a connection
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Properties user = new Properties();
            user.put("user", DB_USERNAME);
            user.put("password", DB_PASSWORD);
            Connection conn = DriverManager.getConnection(CONNECTION_URL, user);

            // Create the sql string and execute the string
            sql = "INSERT INTO 2dv603.email(email, password, adminOruser) VALUES (?,?,?);";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, this.email);
            stat.setString(2, this.password);
            stat.setString(3, this.adminOrUser);
            stat.execute();
            conn.close();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sql;
    }

    /**
     * This will delete an email from the database
     * @param str is the email that will be deleted
     */
    public void deleteEmail(String str)
    {
        String sql = "";
        Statement stat = null;
        Connection conn = null;

        try
        {
            // Create properties and a connection
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Properties user = new Properties();
            user.put("user", DB_USERNAME);
            user.put("password", DB_PASSWORD);
            conn = DriverManager.getConnection(CONNECTION_URL, user);

            // Create the sql string and execute it
            sql = "DELETE FROM 2dv603.email WHERE email ='" + str + "';";
            stat = conn.createStatement();
            stat.execute(sql);

            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                stat.close();
                conn.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * This will get all the emails from the database
     * @return a list of emails
     */
    public List<Email> getEmails()
    {
        // Create an ArrayList
        List<Email> listOfEmails = new ArrayList<>();

        try
        {
            // Create properties and a connection
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Properties user = new Properties();
            user.put("user", DB_USERNAME);
            user.put("password", DB_PASSWORD);
            Connection conn = DriverManager.getConnection(CONNECTION_URL, user);

            try
            {
                // Create the sql string and execute it
                String sql = "SELECT * FROM 2dv603.email;";
                PreparedStatement stat = conn.prepareStatement(sql);
                ResultSet result = null;
                stat.execute();
                result = stat.getResultSet();

                // This will loop through the ResultSet and save every keyword in the list
                while(result.next())
                {
                    Email newEmail = new Email();
                    newEmail.setEmail(result.getString(1));
                    newEmail.setPassword(result.getString(2));
                    newEmail.setAdminOrUser(result.getString(3));
                    listOfEmails.add(newEmail);
                }
            }

            finally
            {
                conn.close();
            }
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfEmails;
    }

    /**
     * Get information from user in database
     * @param UName is the username
     */
    public void getDbUserInfo(String UName)
    {
        String sql = "";
        Statement stat = null;
        Connection con = null;

        try
        {
            // Create properties and a connection
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Properties user = new Properties();
            user.put("user", "user");
            user.put("password", "user123");
            con = DriverManager.getConnection(CONNECTION_URL, user);
            stat = con.createStatement();

            // Create sql string
            sql = "SELECT * FROM 2dv603.email WHERE email ='" + UName + "';";

            rs = stat.executeQuery(sql);
            rs.next();

            // Set the fields to the information from the database
            dbUsername = rs.getString(1).toString();
            dbPassword = rs.getString(2).toString();
            dbAdminOrUser = rs.getString(3).toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This checks if a user is valid
     * @return
     */
    public String validUser()
    {
        getDbUserInfo(this.email);

        if(this.email.equals(dbUsername))
        {
            if(this.password.equals(dbPassword))
            {
                if(dbAdminOrUser.equals("admin"))
                {
                    return "indexForAdmin";
                }
                else
                {
                    return "index";
                }
            }
            else
            {
                return "login";
            }
        }
        else
        {
            return "login";
        }
    }

    /**
     * This checks if the person who wants to log in is an admin or a user
     * @param str is the person
     * @return admin or user
     */
    public String checkIfAdminOrUser(String str)
    {
        getDbUserInfo(this.email);

        if(dbAdminOrUser.equals("user"))
        {
            return "user";
        }
        else if(dbAdminOrUser.equals("admin"))
        {
            return "admin";
        }
        return "";
    }

    /**
     * This will change the password to a user
     * @param UName
     */
    public void changePwd(String UName)
    {
        String sql = "";
        Connection con = null;

        try
        {
            // Create properties and a connection
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Properties user = new Properties();
            user.put("user", "user");
            user.put("password", "user123");
            con = DriverManager.getConnection(CONNECTION_URL, user);

            String adOrUs = checkIfAdminOrUser(this.email);

            // Create sql string
            sql = "UPDATE 2dv603.email SET email = ?, password = ?, adminOruser = ? WHERE email ='" + UName + "';";
            PreparedStatement stat = con.prepareStatement(sql);
            stat.setString(1, this.email);
            stat.setString(2, this.password);
            stat.setString(3, adOrUs);
            stat.executeUpdate();

            con.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
