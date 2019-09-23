import sun.misc.JavaxCryptoSealedObjectAccess;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComboBoxes {
    public static JComboBox<String> delete_user = new JComboBox<>();
    public static JComboBox<String> update_privileges = new JComboBox<>();
    public static JComboBox<String> search_c_affil = new JComboBox<>();
    public static JComboBox<String> search_c_dept = new JComboBox<>();

    public static void initBoxes(DBWrapper db_conn){
        fillTechnicianBoxes(db_conn);
    }

    public static JComboBox getSampleTypeBox(DBWrapper db_conn) {
        JComboBox box = new JComboBox();
        box.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent popupMenuEvent) {
                fillSampleTypeBox(db_conn, box);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent popupMenuEvent) {}

            @Override
            public void popupMenuCanceled(PopupMenuEvent popupMenuEvent) {}
        });

        return box;
    }

    public static JComboBox getContactBox(DBWrapper db_conn){
        JComboBox box = new JComboBox();
        box.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent popupMenuEvent) {
                fillContactBox(db_conn, box);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent popupMenuEvent) {}

            @Override
            public void popupMenuCanceled(PopupMenuEvent popupMenuEvent) {}
        });

        return box;
    }

    public static JComboBox getSampleSourceBox(DBWrapper db_conn) {
        JComboBox box = new JComboBox();
        box.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent popupMenuEvent) {
                fillSampleSourceBox(db_conn, box);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent popupMenuEvent) {}

            @Override
            public void popupMenuCanceled(PopupMenuEvent popupMenuEvent) {}
        });

        return box;
    }

    public static JComboBox getLocationBox(DBWrapper db_conn) {
        JComboBox box = new JComboBox();
        box.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent popupMenuEvent) {
                fillLocationBox(db_conn, box);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent popupMenuEvent) {}

            @Override
            public void popupMenuCanceled(PopupMenuEvent popupMenuEvent) {}
        });

        return box;
    }

    public static JComboBox sampleTypeBox(){
        String[] defaultSampleTypes = {"", "Grain", "Tissue", "Pelleted Feed"};
        JComboBox sampleTypeBox = new JComboBox<>(defaultSampleTypes);

        return sampleTypeBox;
    }

    public static JComboBox sourceBox(){
        String[] defaultSourceTypes = {"", "Equine", "Tortoise", "009211", "319504"};
        JComboBox source = new JComboBox<>(defaultSourceTypes);

        return source;
    }

    public static JComboBox contactBox(){
        String[] defaultSourceTypes = {"", "Mark Edwards", "Matt Burd"};
        JComboBox contact = new JComboBox<>(defaultSourceTypes);

        return contact;
    }

    public static JComboBox storageLocBox(){
        String[] defaultSourceTypes = {"", "Cabinet", "Box 5"};
        JComboBox storageLocation = new JComboBox<>(defaultSourceTypes);

        return storageLocation;
    }

    public static JComboBox testTypeBox(){
        String[] defaultSourceTypes = {"", "Partial Drying", "Homogenize", "Pelleting", "Acid Digestion, Microwave", "Ash, Total (organic matter)",
            "Dry Matter (moisture)", "Particle Size, Macro, Dry", "Particle Size, Dry", "Particle Size, Wet", "Acid Insoluble Ash",
            "NDF, Amylase, Refluxing", "ADF, Non-Sequential, Refluxing", "ADF, Sequential, Refluxing",
            "ADL, Sequential, Refluxing", "NDF, Amylase, Filter Bag", "ADF, Non-Sequential, Filter Bag",
            "ADF, Sequential, Filter Bag", "ADL, Sequential, Filter Bag", "Gross Energy", "Digestibility, In-vitro, 3-stage",
            "Digestibility, In-vivo", "Digestibility, In-situ", "Density", "pH"};

        JComboBox testType = new JComboBox<>(defaultSourceTypes);

        return testType;
    }

    public static JComboBox projectBox(){
        String[] defaultSourceTypes = {"", "Allison's Thesis", "Enterprise F2018"};
        JComboBox assocProj = new JComboBox<>(defaultSourceTypes);

        return assocProj;
    }

    public static JComboBox technicianBox(){
        String[] defaultTypes = {"", "Dr. Edwards", "Allison"};

        return new JComboBox<>(defaultTypes);
    }

    private static void fillTechnicianBoxes(DBWrapper db_conn){
        delete_user.addItem("");
        update_privileges.addItem("");
        String query = "select * from users;";
        db_conn.fillComboBox(delete_user, query, "email");
        db_conn.fillComboBox(update_privileges, query, "email");
    }

    public static void removeFromTechnicianBoxes(Object to_remove){
        delete_user.removeItem(to_remove);
        update_privileges.removeItem(to_remove);
    }

    public static void addToTechnicianBoxes(String to_add){
        delete_user.addItem(to_add);
        update_privileges.addItem(to_add);
    }

    public static void fillOrgBox(DBWrapper db_conn, JComboBox box){
        String query = "select distinct organization from people;";
        box.setModel(db_conn.getComboBoxModel(query, "organization"));
    }

    public static void fillDeptBox(DBWrapper db_conn, JComboBox box){
        String query = "select distinct department from people;";
        box.setModel(db_conn.getComboBoxModel(query, "department"));
    }

    private static void fillContactBox(DBWrapper db_conn, JComboBox box){
        String query = "select name from people";
        box.setModel(db_conn.getComboBoxModel(query, "name"));
    }

    private static void fillSampleTypeBox(DBWrapper db_conn, JComboBox box){
        String query = "select distinct type from sampleTypes";
        box.setModel(db_conn.getComboBoxModel(query, "type"));
    }

    private static void fillSampleSourceBox(DBWrapper db_conn, JComboBox box){
        String query = "select source_id from sources";
        box.setModel(db_conn.getComboBoxModel(query, "source_id"));
    }

    private static void fillLocationBox(DBWrapper db_conn, JComboBox box){
        String query = "select location from storageLocations";
        box.setModel(db_conn.getComboBoxModel(query, "location"));
    }

    public static JComboBox privilegesBox(){
        String[] defaultTypes = {"", "User", "Admin"};

        return new JComboBox<>(defaultTypes);
    }
}
