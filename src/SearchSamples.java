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

public class SearchSamples extends SearchLayout implements ActionListener {
    private ArrayList<String[]> data;
    private SearchTableModel t_model;
    private JTable samples;
    private DBWrapper db_conn;
    private JComboBox subType;
    private JComboBox contactBox;
    private JComboBox storageBox;
    private JComboBox sourceBox;
    private JCheckBox noInventory;
    private JTextField dateBox;
    private JComboBox sort;

    public SearchSamples(DBWrapper db_conn){
        this.db_conn = db_conn;
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        initTable();
        JScrollPane results =  new JScrollPane(samples);
        JPanel samplesUI = SearchSamplesUI();
        samplesUI.setBorder(BorderFactory.createTitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));

        setSearchLayout(layout, samplesUI, results);
    }

    private void initTable(){
        String[] column_names = {"Accession Num", "Type", "Subtype", "Source", "Contact", "Storage Loc",
                        "Description", "Amount", "Collect Date"};
        data = new ArrayList<>();
        t_model = new SearchTableModel(column_names, data);
        samples = new JTable(t_model);
    }

    private JPanel SearchSamplesUI(){
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(1000, 100));

        p.setLayout(new GridBagLayout());

        JLabel sampleType = new JLabel("Sample Type");
        addItem(p, sampleType, 0, 0, 0.2, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 80, 0, 0);

        JComboBox sampleBox = ComboBoxes.getSampleTypeBox(db_conn);
        getAutofillAction(sampleBox);
        addItem(p, sampleBox, 0, 1, 0.2, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 80, 0, 0);

        JLabel source = new JLabel("Source");
        addItem(p, source, 0, 2, 0.2, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 80, 0, 0);

        sourceBox = ComboBoxes.getSampleSourceBox(db_conn);
        addItem(p, sourceBox, 0, 3, 0.2, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 80, 0, 0);

        JLabel sortBy = new JLabel("Sort By");
        addItem(p, sortBy, 0, 4, 0.2, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 80, 0, 0);

        sort = sortByOptions();
        addItem(p, sort, 0, 5, 0.2, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 80, 10, 0);

        JLabel contact = new JLabel("Contact");
        addItem(p, contact, 3, 0, 0.1, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 40, 0, 60);

        contactBox = ComboBoxes.getContactBox(db_conn);
        addItem(p, contactBox, 3, 1, 0.1, 0.05, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 0, 60);

        JLabel storageLocation = new JLabel("Storage Location");
        addItem(p, storageLocation, 3, 2, 0.1, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 40, 0, 60);

        storageBox = ComboBoxes.getLocationBox(db_conn);
        addItem(p, storageBox, 3, 3, 0.1, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 0, 60);

        JLabel sTypeLabel = new JLabel("Sample Subtype");
        addItem(p, sTypeLabel, 1, 0, 0.05, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 70, 0, 0);

        dateBox = dateEntryField();
        addItem(p, dateBox, 1, 3, 0.05, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 70, 0, 0);

        JLabel collectionDate = new JLabel("Collection Date  MM/DD/YYYY");
        addItem(p, collectionDate, 1, 2, 0.05, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 70, 0, 0);

        subType = new JComboBox();
        addItem(p, subType, 1, 1, 0.05, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 70, 0, 0);

        Action cDateAction = new TodaysDateAction("Today's Date", dateBox);
        JButton today2 = new JButton(cDateAction);
        addItem(p, today2, 2, 3, 0.03, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, 10, 0, 0);

        noInventory = new JCheckBox("Include Samples with No Inventory");
        addItem(p, noInventory, 1, 5, 0.02, 0.05, 2, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, 80, 0, 40);

        JButton search = new JButton("Search");
        search.addActionListener(this);
        addItem(p, search, 3, 5, 0.02, 0.05, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, 80, 0, 90);

        return p;
    }

    private void getAutofillAction(JComboBox box) {
        box.addActionListener(event -> {
            JComboBox source = (JComboBox) event.getSource();
            String selected_type = source.getSelectedItem().toString();
            String query = "select * from sampleTypes where type = \"" + selected_type + "\";";

            if (!selected_type.equals("")) {
                subType.setModel(db_conn.getSTypeComboBoxModel(query));
            }
        });
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

    private JComboBox sortByOptions(){
        String[] defaultTypes = {"", "type", "source", "contact", "storage", "description", "amount", "collected",
                                    "accession num"};

        return new JComboBox<>(defaultTypes);
    }

    private int getContactId(String contact){
        int id = -1;
        String query = "select id from people where name = \"" + contact + "\";";
        ResultSet ct = db_conn.getDbEntries(query);
        try{
            ct.next();
            id = ct.getInt("id");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }

    private int getStorageLocId(String storage_location){
        int id = -1;
        String query = "select id from storageLocations where location = \"" + storage_location + "\";";
        ResultSet loc = db_conn.getDbEntries(query);
        try{
            loc.next();
            id = loc.getInt("id");
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }

    private boolean itemInvalid(Object item){
        if(item != null){
            return item.toString().isEmpty();
        }
        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int sample_type_id = itemInvalid(subType.getSelectedItem()) ? -1 : ((SType)subType.getSelectedItem()).getID();
        int contact = itemInvalid(contactBox.getSelectedItem()) ? -1 : getContactId(contactBox.getSelectedItem().toString());
        int storage = itemInvalid(storageBox.getSelectedItem()) ? -1 : getStorageLocId(storageBox.getSelectedItem().toString());
        String source = itemInvalid(sourceBox.getSelectedItem()) ? "" : sourceBox.getSelectedItem().toString();
        String sort_by = itemInvalid(sort.getSelectedItem()) ? "" : sort.getSelectedItem().toString();
        String date = dateBox.getText();
        boolean no_inv_okay = noInventory.isSelected();

        boolean st_present = sample_type_id != -1;
        boolean contact_present = contact != -1;
        boolean stor_present = storage != -1;
        boolean sourc_present = !source.equals("");
        boolean date_present = !date.equals("  /  /    ");
        boolean[] field_presence = new boolean[]{st_present, contact_present, stor_present, sourc_present, date_present};

        String with_type = st_present ? "type = " + sample_type_id : "";
        String with_contact = contact_present ? "contact = " + contact : "";
        String with_stor = stor_present ? "storage = " + storage : "";
        String with_source = sourc_present ? "source = \"" + source + "\"" : "";
        String with_date = date_present ? "collected = " + "str_to_date(\"" + date + "\", \"%m/%d/%Y\")" : "";

        String join1 = (st_present && contact_present) ? " or " : "";
        String join2 = (stor_present && (st_present || contact_present)) ? " or " : "";
        String join3 = (sourc_present && (st_present || contact_present || stor_present)) ? " or " : "";
        String join4 = (date_present && (st_present || contact_present || stor_present || sourc_present)) ? " or " : "";

        String end = !sort_by.equals("") ? " order by " + sort_by + ";" : ";";
        String excl_no_inv = !no_inv_okay ? " where amount != 0.0" : "";
        String query = "select * from samples";

        if(fieldsPresent(field_presence)){
            query += " where (" + with_type + join1 + with_contact + join2 + with_stor + join3 + with_source +
                    join4 + with_date + ")";
            excl_no_inv = !no_inv_okay ? " and amount != 0.0" : "";
        }

        ResultSet samples = db_conn.getDbEntries(query + excl_no_inv + end);
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

    private void fillTable(ResultSet samples){
        data.clear();
        try{
            while(samples.next()) {
                ResultSet type = db_conn.getDbEntries("select * from sampleTypes where id = " +
                        samples.getInt("type") + ";");
                ResultSet storage = db_conn.getDbEntries("select * from storageLocations where id = " +
                        samples.getInt("storage") + ";");
                ResultSet contact = db_conn.getDbEntries("select * from people where id = " +
                        samples.getInt("contact") + ";");
                String[] sample_info = new String[9];
                sample_info[0] = samples.getString("accession_num");
                type.next();
                sample_info[1] = type.getString("type");
                sample_info[2] = type.getString("subtype");
                sample_info[3] = samples.getString("source");
                contact.next();
                sample_info[4] = contact.getString("name");
                storage.next();
                sample_info[5] = storage.getString("room") + ", " + storage.getString("location");
                sample_info[6] = samples.getString("description");
                sample_info[7] = samples.getFloat("amount") + " " + samples.getString("units");
                sample_info[8] = samples.getString("collected");

                data.add(sample_info);
                t_model.insertItems(data);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
