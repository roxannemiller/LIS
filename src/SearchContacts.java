import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class SearchContacts extends SearchLayout implements ActionListener {
    private DBWrapper db_conn;
    private JTable contacts;
    private ArrayList<String[]> data;
    private JComboBox contact_box;
    private JComboBox affil_box;
    private JComboBox dept_box;
    private JComboBox sort_box;
    private SearchTableModel t_model;

    public SearchContacts(DBWrapper db_conn){
        this.db_conn = db_conn;
        this.affil_box = ComboBoxes.search_c_affil;
        this.dept_box = ComboBoxes.search_c_dept;
        this.sort_box = sortByOptions();
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        initTable();
        JScrollPane search_results = new JScrollPane(contacts);
        JPanel samplesUI = SearchContactsUI();
        samplesUI.setBorder(BorderFactory.createTitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));

        setSearchLayout(layout, samplesUI, search_results);
    }

    private void initTable(){
        String[] column_names = {"Name", "Organization", "Department", "Email"};
        data = new ArrayList<>();
        t_model = new SearchTableModel(column_names, data);
        contacts = new JTable(t_model);
    }

    private JPanel SearchContactsUI(){
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(1000, 100));

        p.setLayout(new GridBagLayout());

        JLabel contact = new JLabel("Contact Name");
        contact_box = ComboBoxes.getContactBox(db_conn);
        addItem(p, contact, 0, 0, 0.15, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 60, 0, 0);
        addItem(p, contact_box, 0, 1, 0.15, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 60, 0, 0);

        JLabel affiliation = new JLabel("Contact Affiliation");
        affil_box.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent popupMenuEvent) {
                ComboBoxes.fillOrgBox(db_conn, affil_box);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent popupMenuEvent) {}

            @Override
            public void popupMenuCanceled(PopupMenuEvent popupMenuEvent) {}
        });
        addItem(p, affiliation, 1, 0, 0.15, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 40, 0, 0);
        addItem(p, affil_box, 1, 1, 0.15, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 0, 0);

        JLabel dept = new JLabel("Contact Department");
        dept_box.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent popupMenuEvent) {
                ComboBoxes.fillDeptBox(db_conn, dept_box);
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent popupMenuEvent) {}

            @Override
            public void popupMenuCanceled(PopupMenuEvent popupMenuEvent) {}
        });
        addItem(p, dept, 2, 0, 0.15, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 40, 0, 0);
        addItem(p, dept_box, 2, 1, 0.15, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 0, 0);

        JLabel sortBy = new JLabel("Sort By");
        addItem(p, sortBy, 3, 0, 0.2, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 40, 0, 0);
        addItem(p, sort_box, 3, 1, 0.2, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 10, 0);

        JButton search = new JButton("Search");
        search.addActionListener(this);
        addItem(p, search, 4, 1, 0.05, 0.05, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, 20, 0, 40);

        return p;
    }

    private JComboBox sortByOptions(){
        String[] defaultTypes = {"", "name", "organization", "department"};
        return new JComboBox<>(defaultTypes);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ResultSet contacts;
        String affiliation = affil_box.getSelectedItem() == null ? "" : affil_box.getSelectedItem().toString();
        String department = dept_box.getSelectedItem() == null ? "" : dept_box.getSelectedItem().toString();
        String name = contact_box.getSelectedItem() == null ? "" : contact_box.getSelectedItem().toString();
        String sort = sort_box.getSelectedItem() == null ? "" : sort_box.getSelectedItem().toString();

        boolean affil_present = !affiliation.equals("");
        boolean dept_present = !department.equals("");
        boolean name_present = !name.equals("");

        String with_affil = affil_present ? "organization = \"" + affiliation + "\"" : "";
        String with_dept = dept_present ? "department = \"" + department + "\"" : "";
        String with_name = name_present ? "name = \"" + name + "\"" : "";
        String join_1 = (affil_present && dept_present) ? " or " : "";
        String join_2 = (name_present && (affil_present || dept_present))? " or " : "";
        String end = !sort.equals("") ? " order by " + sort + ";" : ";";
        String query = "select * from people";

        if (affil_present || dept_present || name_present){
            query += " where " + with_affil + join_1 + with_dept + join_2 + with_name;
        }

        contacts = db_conn.getDbEntries(query + end);
        fillTable(contacts);
    }

    private void fillTable(ResultSet contacts){
        data.clear();
        try{
            while(contacts.next()) {
                String[] contact_info = new String[4];
                contact_info[0] = contacts.getString("name");
                contact_info[1] = contacts.getString("organization");
                contact_info[2] = contacts.getString("department");
                contact_info[3] = contacts.getString("email");
                data.add(contact_info);
                t_model.insertItems(data);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
