import javax.swing.*;
import java.sql.*;
import java.util.List;

public class DBWrapper {
    private Connection db_conn = null;

    public DBWrapper(){
        String user = "root";
        String pwd = "sT4rw#alE";
        String addr = "jdbc:mysql://localhost:3306/LIS";

        try{
            this.db_conn = DriverManager.getConnection(addr, user, pwd);
        }
        catch (SQLException ex){
            System.out.println("Database driver issues");
            ex.printStackTrace();
        }
    }

    public void closeConnection(){
        try{
            db_conn.close();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void setupDatabase(List<String> statements){
        try{
            Statement s = null;
            s = db_conn.createStatement();
            s.executeUpdate(statements.get(0));
            s.executeUpdate(statements.get(1));
            s.executeUpdate(statements.get(2));
            s.executeUpdate(statements.get(3));
            s.executeUpdate(statements.get(4));
            s.executeUpdate(statements.get(5));
            s.executeUpdate(statements.get(6));

        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public ResultSet getDbEntries(String query){
        ResultSet entries = null;
        try {
            Statement s = db_conn.createStatement();
            entries = s.executeQuery(query);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }

        return entries;
    }

    public int updateDb(String query){
        int result = -500;
        try{
            Statement s = db_conn.createStatement();
            result = s.executeUpdate(query);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return result;
    }

    public void fillComboBox(JComboBox<String> box, String query, String field){
        ResultSet entries = getDbEntries(query);
        try{
            while(entries.next()){
                box.addItem(entries.getString(field));
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
    }
}