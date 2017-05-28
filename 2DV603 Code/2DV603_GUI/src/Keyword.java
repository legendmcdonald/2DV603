import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * Created by Michael on 2017-05-16.
 * This class will handle the keywords that will be added in the GUI
 */

@Named
@SessionScoped
public class Keyword implements Serializable
{
    // Create fields
    private String keyword;
    private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/2dv603";
    private static final String DB_USERNAME = "user";
    private static final String DB_PASSWORD = "user123";

    /**
     * This will get the keyword
     * @return a keyword
     */
    public String getKeyword()
    {
        return keyword;
    }

    /**
     * This will set a keyword
     * @param keyword is the keyword that will be set
     */
    public void setKeyword(String keyword)
    {
        this.keyword = keyword;
    }

    /**
     * This will add a keyword from GUI to the database
     * @return a string containing the sql injection
     */
    public String addKeyword()
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
            sql = "INSERT INTO 2dv603.keyword(nameOfKeyword) VALUES (?);";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, this.keyword);
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
     * This will delete a keyword from the database
     * @param str is the keyword that will be deleted
     */
    public void deleteKeyword(String str)
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

            // Create the sql string and execute ir
            sql = "DELETE FROM 2dv603.keyword WHERE nameOfKeyword ='" + str + "';";
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
     * This will get all the keywords in the database
     * @return a list of keywords
     */
    public List<Keyword> getKeywords()
    {
        // Create a ArrayList
        List<Keyword> listOfKeywords = new ArrayList<>();

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
                String sql = "SELECT * FROM 2dv603.keyword;";
                PreparedStatement stat = conn.prepareStatement(sql);
                ResultSet result = null;
                stat.execute();
                result = stat.getResultSet();

                // This will loop through the ResultSet and save every keyword in the list
                while(result.next())
                {
                    Keyword keyword = new Keyword();
                    keyword.setKeyword(result.getString(1));
                    listOfKeywords.add(keyword);
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

        return listOfKeywords;
    }

}
