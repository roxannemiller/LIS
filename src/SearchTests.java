import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class SearchTests extends SearchLayout implements ActionListener {
    private DBWrapper db_conn;
    private ArrayList<String[]> data;
    private SearchTableModel t_model;
    private JComboBox testBox;
    private JComboBox sampleBox;
    private JComboBox subjectBox;
    private JComboBox statusBox;
    private JFormattedTextField dateBox;
    private JComboBox techBox;
    private JComboBox sortBox;
    private JComboBox projBox;
    private JTable tests;

    public SearchTests(DBWrapper db_conn){
        this.db_conn = db_conn;

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        initTable();
        JPanel testUI = SearchTestsUI();
        JScrollPane results =  new JScrollPane(tests);
        testUI.setBorder(BorderFactory.createTitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));

        setSearchLayout(layout, testUI, results);
    }

    private void initTable(){
        String[] column_names = {"Type", "Date"};
        data = new ArrayList<>();
        t_model = new SearchTableModel(column_names, data);
        tests = new JTable(t_model);
    }

    private JPanel SearchTestsUI(){
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(1000, 100));

        p.setLayout(new GridBagLayout());

        JLabel testType = new JLabel("Test Type");
        addItem(p, testType, 0, 0, 0.3, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 60, 0, 0);
        testBox = ComboBoxes.getTestTypeBox(db_conn);
        addItem(p, testBox, 0, 1, 0.3, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 60, 0, 0);

        JLabel sampleType = new JLabel("Sample Type Used");
        addItem(p, sampleType, 0, 2, 0.3, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 60, 0, 0);
        sampleBox = new JComboBox();
        sampleBox.setModel(db_conn.getSTypeComboBoxModel("select * from sampleTypes;"));
        addItem(p, sampleBox, 0, 3, 0.3, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 60, 0, 0);

        JLabel testStatus = new JLabel("Test Status");
        addItem(p, testStatus, 1, 0, 0.5, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 40, 0, 0);
        statusBox = testStatusBox();
        addItem(p, statusBox, 1, 1, 0.5, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 10, 0);

        JLabel subject = new JLabel("Subject");
        addItem(p, subject, 1, 2, 0.5, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 0, 0);
        subjectBox = ComboBoxes.getSampleSourceBox(db_conn);
        addItem(p, subjectBox, 1, 3, 0.5, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 0, 0);

        JLabel assocProject = new JLabel("Associated Project");
        addItem(p, assocProject, 2, 0, 0.4, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 40, 0, 40);
        projBox = ComboBoxes.getProjectBox(db_conn);
        addItem(p, projBox, 2, 1, 0.4, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 0, 40);

        JLabel tech = new JLabel("Technician");
        addItem(p, tech, 2, 2, 0.4, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 40, 0, 40);
        techBox = ComboBoxes.getTechnicianBox(db_conn);
        addItem(p, techBox, 2, 3, 0.4, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 0, 40);

        JLabel sort = new JLabel("Sort By");
        addItem(p, sort, 3, 2, 0.2, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 0, 0, 0);

        sortBox = sortByOptions();
        addItem(p, sortBox, 3, 3, 0.2, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 0, 0, 0);

        JLabel testDate = new JLabel("Test Date MM/DD/YYYY");
        addItem(p, testDate, 3, 0, 0.05, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 0, 0, 0);

        dateBox = dateEntryField();
        dateBox.setPreferredSize(new Dimension(110, 26));
        addItem(p, dateBox, 3, 1, 0.05, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 0, 0, 0);

        Action dateAction = new TodaysDateAction("Today's Date", dateBox);
        JButton today = new JButton(dateAction);
        addItem(p, today, 4, 1, 0.05, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, 10, 0, 40);

        JButton search = new JButton("Search");
        search.addActionListener(this);
        addItem(p, search, 4, 3, 0.02, 0.05, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, 20, 0, 40);

        return p;
    }

    private JFormattedTextField dateEntryField() {
        JFormattedTextField f = new JFormattedTextField();
        try{
            MaskFormatter mask = new MaskFormatter("##/##/####");
            mask.setPlaceholderCharacter(' ');
            f = new JFormattedTextField(mask);
        }
        catch (ParseException e){
            JOptionPane.showMessageDialog(this, "Enter date in valid format");
        }

        return f;
    }

    private JComboBox testStatusBox(){
        String[] defaultTypes = {"", "In-Progress", "Completed", "Failed"};

        return new JComboBox<>(defaultTypes);
    }

    private JComboBox sortByOptions(){
        String[] defaultTypes = {"", "Sample Type", "Test Type", "Test Status", "Subject", "Associated Project",
                "Technician", "Date - Most Recent", "Date - Least Recent"};

        return new JComboBox<>(defaultTypes);
    }

    private void fillTable(ResultSet tests) {
        data.clear();
        try {
            while(tests.next()) {
                String[] info = new String[2];
                String service = tests.getString("service");
                info[0] = service;
                String date = tests.getString("date_made");
                info[1] = date;

                data.add(info);
                t_model.insertItems(data);
            }
        } catch(SQLException e) {
                e.printStackTrace();
            }
    }

    private boolean itemInvalid(Object item){
        if(item != null){
            return item.toString().isEmpty();
        }
        return true;
    }

    private int getStatusNum(String status) {
        if(status.equals("In-Progress")) {
            return 0;
        } else if(status.equals("Completed")) {
            return 1;
        } else {
            return 2;
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
//        String query = "select * from tests join testTypes where tests.type = testTypes.id";
//        ResultSet tests = db_conn.getDbEntries(query);
//        fillTable(tests);

        int test_type_id = itemInvalid(testBox.getSelectedItem()) ? -1 : ((TType)testBox.getSelectedItem()).getID();
        int sample = itemInvalid(sampleBox.getSelectedItem()) ? -1 : ((SType)sampleBox.getSelectedItem()).getID();
        int t_status = itemInvalid(statusBox.getSelectedItem()) ? -1 : getStatusNum(statusBox.getSelectedItem().toString());
        String subj = itemInvalid(subjectBox.getSelectedItem()) ? "" : subjectBox.getSelectedItem().toString();
        String tech = itemInvalid(techBox.getSelectedItem()) ? "" : techBox.getSelectedItem().toString();
        String date = dateBox.getText();
        String proj = itemInvalid(projBox.getSelectedItem()) ? "" : projBox.getSelectedItem().toString();
        String sort_by = itemInvalid(sortBox.getSelectedItem()) ? "" : sortBox.getSelectedItem().toString();

        boolean test_present = test_type_id != -1;
        boolean samp_present = sample != -1;
        boolean stat_present = t_status != -1;
        boolean subj_present = !subj.equals("");
        boolean tech_present = !tech.equals("");
        boolean date_present = !date.equals("  /  /    ");
        boolean proj_present = !proj.equals("");
        boolean[] field_presence = new boolean[]{test_present, samp_present, stat_present, subj_present, tech_present, date_present, proj_present};

        String with_type = test_present ? "tests.type = " + test_type_id : "";
        String with_samp = samp_present ? "samples.type = " + sample : "";
        String with_status = stat_present ? "tests.status = " + t_status : "";
        String with_subj = subj_present ? "samples.source = \"" + subj + "\"" : "";
        String with_tech = tech_present ? "tests.owner = \"" + tech + "\"" : "";
        String with_date = date_present ? "tests.date_made = " + "str_to_date(\"" + date + "\", \"%m/%d/%Y\")" : "";
        String with_proj = proj_present ? "tests.project = \"" + proj + "\"" : "";

        String join1 = (test_present && samp_present) ? " or " : "";
        String join2 = (stat_present && (test_present || samp_present)) ? " or " : "";
        String join3 = (subj_present && (test_present || samp_present || stat_present)) ? " or " : "";
        String join4 = (tech_present && (test_present || samp_present || stat_present || subj_present)) ? " or " : "";
        String join5 = (date_present && (test_present || samp_present || stat_present || subj_present || tech_present)) ? " or " : "";
        String join6 = (proj_present && (test_present || samp_present || stat_present || subj_present || tech_present || date_present)) ? " or " : "";

        String end = !sort_by.equals("") ? " order by " + sort_by + ";" : ";";

        String query = "select *, tests.type as ttype from " +
                "tests join joinTestSamples on tests.id = joinTestSamples.test_id " +
                "join samples on samples.accession_num = joinTestSamples.sample_id " +
                "join testTypes on tests.type = testTypes.id";
        if(fieldsPresent(field_presence)){
            query += " where (" + with_type + join1 + with_samp + join2 + with_status + join3 + with_subj +
                    join4 + with_tech + join5 + with_date + join6 + with_proj + ")";
        }

        ResultSet samples = db_conn.getDbEntries(query + end);
        fillTable(samples);
    }

    private boolean fieldsPresent(boolean[] fields){
        for (boolean field : fields){
            if (field){
                return true;
            }
        }
        return false;
    }
}
