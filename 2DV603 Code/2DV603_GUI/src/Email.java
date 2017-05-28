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
 */

@Named
@SessionScoped
public class Email implements Serializable
{
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

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdminOrUser() {
        return adminOrUser;
    }

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

public String addEmail()
    {
        String sql = "";

        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Properties user = new Properties();
            user.put("user", DB_USERNAME);
            user.put("password", DB_PASSWORD);

            Connection conn = DriverManager.getConnection(CONNECTION_URL, user);
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

public void deleteEmail(String str)
    {
        String sql = "";
        Statement stat = null;
        Connection conn = null;

        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Properties user = new Properties();
            user.put("user", DB_USERNAME);
            user.put("password", DB_PASSWORD);

            sql = "DELETE FROM 2dv603.email WHERE email ='" + str + "';";

            conn = DriverManager.getConnection(CONNECTION_URL, user);
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

public List<Email> getEmails()
    {
        List<Email> listOfEmails = new ArrayList<>();

        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Properties user = new Properties();
            user.put("user", DB_USERNAME);
            user.put("password", DB_PASSWORD);

            Connection conn = DriverManager.getConnection(CONNECTION_URL, user);

            try
            {
                String sql = "SELECT * FROM 2dv603.email;";
                PreparedStatement stat = conn.prepareStatement(sql);
                ResultSet result = null;
                stat.execute();
                result = stat.getResultSet();

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

public void getDbUserInfo(String UName)
    {
        String sql = "";
        Statement stat = null;
        Connection con = null;

        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Properties user = new Properties();
            user.put("user", "user");
            user.put("password", "user123");
            con = DriverManager.getConnection(CONNECTION_URL, user);
            stat = con.createStatement();

            sql = "SELECT * FROM 2dv603.email WHERE email ='" + UName + "';";

            rs = stat.executeQuery(sql);
            rs.next();

            dbUsername = rs.getString(1).toString();
            dbPassword = rs.getString(2).toString();
            dbAdminOrUser = rs.getString(3).toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

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

    public void changePwd(String UName)
    {
        String sql = "";
        Connection con = null;

        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            Properties user = new Properties();
            user.put("user", "user");
            user.put("password", "user123");

            con = DriverManager.getConnection(CONNECTION_URL, user);

            String adOrUs = checkIfAdminOrUser(this.email);

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

    @PostConstruct
    public void pageLoad()
    {
        setEmail("");
        setPassword("");
        setAdminOrUser("");
    }
}
