import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBWrapper {
    private Connection db_conn = null;

    public DBWrapper(){
        String user = "root";
        String pwd = <password>;
        String addr = <docker container address>;

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
            for (String statement : statements) {
                s.executeUpdate(statement);
            }
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

    public void fillComboBox(JComboBox<String> box, String query, String field) {
        ResultSet entries = getDbEntries(query);
        try{
            while(entries.next()){
                if(entries.getString(field) != null){
                    box.addItem(entries.getString(field));
                }
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
    }

    public DefaultComboBoxModel getSTypeComboBoxModel(String query) {
        ArrayList<SType> contents = new ArrayList<>();
        ResultSet entries = getDbEntries(query);

        contents.add(new SType(-1, ""));
        try{
            while(entries.next()){
                int id = entries.getInt("id");
                String type = entries.getString("subtype");
                SType t = new SType(id, type);
                contents.add(t);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return new DefaultComboBoxModel(contents.toArray());
    }

    public DefaultComboBoxModel getTTypeComboBoxModel(String query) {
        ArrayList<TType> contents = new ArrayList<>();
        ResultSet entries = getDbEntries(query);

        contents.add(new TType(-1, ""));
        try{
            while(entries.next()){
                int id = entries.getInt("id");
                String type = entries.getString("service");
                TType t = new TType(id, type);
                contents.add(t);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return new DefaultComboBoxModel(contents.toArray());
    }

    public DefaultComboBoxModel getComboBoxModel(String query, String field){
        ArrayList<String> contents = new ArrayList<>();
        ResultSet entries = getDbEntries(query);

        contents.add("");
        try{
            while(entries.next()){
                if(entries.getString(field) != null){
                    contents.add(entries.getString(field));
                }
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }

        return new DefaultComboBoxModel(contents.toArray());
    }
}
