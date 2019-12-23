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
        NotificationBoxes.initTables(db_conn);
        CurrTestBoxes.initTables(db_conn);
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
                "email varchar(50)," +
                "unique (name));";
        String spec_nm = "create table if not exists speciesName(" +
                "id int auto_increment primary key, " +
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
                "accession_num char(10) primary key, " +
                "type int not null, " +
                "source char(10) not null, " +
                "storage int not null, " +
                "foreign key (type) references sampleTypes(id), " +
                "foreign key (source) references sources(source_id), " +
                "foreign key (storage) references storageLocations(id), " +
                "collected date, " +
                "units char(5), " +
                "quantity float);";

        String notifs = "create table if not exists notifications(" +
                "id int auto_increment primary key, " +
                "due date, " +
                "critical boolean, " +
                "notif text not null);";

        String testTypes = "create table if not exists testTypes(" +
                "id int auto_increment primary key, " +
                "service blob, " +
                "primary_category char(15), " +
                "sub_category char(15));";

        String projects = "create table if not exists projects(" +
                "name varchar(50) primary key, " +
                "owner varchar(50)," +
                "foreign key (owner) references users(email));";

        String tests = "create table if not exists tests(" +
                "id int auto_increment primary key, " +
                "type int not null, " +
                "num_dup int not null, " +
                "status int, " +
                "project varchar(50), " +
                "owner varchar(50) not null, " +
                "date_made date not null, " +
                "foreign key (project) references projects(name), " +
                "foreign key (owner) references users(email), " +
                "foreign key (type) references testTypes(id));";

        String results = "create table if not exists results(" +
                "id int auto_increment primary key, " +
                "test int, " +
                "result float, " +
                "num_dup int, " +
                "foreign key (test) references tests(id));";

        String measurements = "create table if not exists measurements(" +
                "id int auto_increment primary key, " +
                "test int, " +
                "name blob not null, " +
                "measurement float, " +
                "num_dup int, " +
                "units char(5), " +
                "ordinal int not null, "+
                "foreign key (test) references tests(id));";

        String actions = "create table if not exists actions(" +
                "id int auto_increment primary key, " +
                "name blob not null, " +
                "length time, " +
                "start time);";

        String calculations = "create table if not exists calculations(" +
                "id int auto_increment primary key, " +
                "name tinyblob, " +
                "calculation mediumblob, " +
                "quality_restraint mediumblob);";

        String joinTestTypesCalcs = "create table if not exists joinTestTypesCalcs(" +
                "id int auto_increment primary key, " +
                "calc int, " +
                "test_type int, " +
                "foreign key (calc) references calculations(id), " +
                "foreign key (test_type) references testTypes(id));";

        String baseSteps = "create table if not exists baseSteps(" +
                "id int auto_increment primary key, " +
                "test_type int, " +
                "action int, " +
                "measurement int, " +
                "ordinal int, " +
                "foreign key (test_type) references testTypes(id), " +
                "foreign key (action) references actions(id), " +
                "foreign key (measurement) references measurements(id));";

        String workflowSteps = "create table if not exists workflowSteps(" +
                "id int auto_increment primary key, " +
                "test int, " +
                "test_type int, " +
                "action int, " +
                "measurement int, " +
                "ordinal int, " +
                "complete tinyint, " +
                "foreign key (test) references tests(id), " +
                "foreign key (test_type) references testTypes(id), " +
                "foreign key (action) references actions(id), " +
                "foreign key (measurement) references measurements(id));";

        String joinTestSamples = "create table if not exists joinTestSamples(" +
                "id int auto_increment primary key, " +
                "test_id int, " +
                "sample_id char(10), " +
                "amt_used float, " +
                "units char(5), " +
                "num_dup int, " +
                "foreign key (test_id) references tests(id), " +
                "foreign key (sample_id) references samples(accession_num));";

        return Arrays.asList(users, sample_ts, store_locs, people, spec_nm, source, samples, notifs,
                measurements, actions, calculations, testTypes, joinTestTypesCalcs, results, projects, tests,
                joinTestSamples, baseSteps, workflowSteps, joinTestSamples);
    }
}
