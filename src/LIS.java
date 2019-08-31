import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class LIS {
    public static void main(String args[]) {
        JFrame frame = new Frame("LIS Sign In");
        DBWrapper db_conn = new DBWrapper();
        List<String> setup_statements = dbSetup();
        db_conn.setupDatabase(setup_statements);
        ComboBoxes.initBoxes(db_conn);
        JPanel login = new Login(frame, db_conn);
        frame.setSize(new Dimension(1505, 1142));
        frame.getContentPane().add(login);
        frame.setVisible(true);
    }

    private static List<String> dbSetup(){
        String users = "create table if not exists users(" +
                "name varchar(30), " +
                "email varchar(50) primary key, " +
                "password varchar(15)," +
                "type int);";
        String sample_ts = "create table if not exists sampleTypes(" +
                "id int auto_increment primary key, " +
                "type varchar(50));";
        String store_locs = "create table if not exists storageLocations(" +
                "id int auto_increment primary key, " +
                "location varchar(50), " +
                "room varchar(20));";
        String people = "create table if not exists people(" +
                "id int auto_increment primary key, " +
                "organization varchar(50), " +
                "department varchar(50), " +
                "name varchar(50), " +
                "email varchar(50));";
        String spec_nm = "create table if not exists speciesName(" +
                "id int auto_increment primary key," +
                "sci_name varchar(100), " +
                "common_name varchar(50));";
        String source = "create table if not exists sources(" +
                "source_id char(10) primary key, " +
                "name varchar(50), " +
                "species int not null, " +
                "contact int not null, " +
                "foreign key (species) references speciesName(id), " +
                "foreign key (contact) references people(id));";
        String samples = "create table if not exists samples(" +
                "association_num char(10) primary key, " +
                "type int not null, " +
                "source char(10) not null, " +
                "storage int not null, " +
                "foreign key (type) references sampleTypes(id), " +
                "foreign key (source) references sources(source_id), " +
                "foreign key (storage) references storageLocations(id), " +
                "acquired datetime, " +
                "quantity int);";

        return Arrays.asList(users, sample_ts, store_locs, people, spec_nm, source, samples);
    }
}
