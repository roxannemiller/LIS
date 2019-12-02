import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Inventory extends TabsBasePanel implements ActionListener {
    private DBWrapper db_conn;
    private ArrayList<String[]> data;
    private SearchableTable t_model;
    private JTable samples;
    private JRadioButton receivedRecentFirst;
    private JRadioButton receivedRecentLast;
    private JRadioButton[] buttons;

    public Inventory(DBWrapper db_conn){
        this.db_conn = db_conn;
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        JScrollPane samples = createScrollableList("Samples", 650);
        JPanel filters = createFilterPanel();

        setInventoryLayout(layout, filters, samples);
    }

    private void initTable(){
        String[] column_names = {"Accession Num", "Amount", "Type", "Source", "Contact", "Storage Loc", "Collection"};
        data = new ArrayList<>();
        t_model = new SearchableTable(column_names, data);
        samples = new JTable(t_model);
        t_model.addTableModelListener(new TableChanges());
    }

    private JScrollPane createScrollableList(String title, int width){
        initTable();
        JScrollPane pane = new JScrollPane(samples, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        samples.setPreferredScrollableViewportSize(new Dimension(width, 600));
        pane.setBorder(BorderFactory.createTitledBorder(null, title, TitledBorder.LEADING, TitledBorder.ABOVE_TOP));

        return pane;
    }

    private JPanel createFilterPanel(){
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        p.setPreferredSize(new Dimension(550, 600));
        p.setBorder(BorderFactory.createTitledBorder(null, "Group By:", TitledBorder.LEADING, TitledBorder.ABOVE_TOP));

        JRadioButton collected = new JRadioButton("Collection Date");
        collected.addActionListener(new ClearRadioButtons());
        addItem(p, collected, 0, 0, 0, 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 0, 20, 0);

        receivedRecentFirst = new JRadioButton("Most Recent First");
        addItem(p, receivedRecentFirst, 0, 1, 0, 0, 1, GridBagConstraints.FIRST_LINE_END, 0, 45, 30, 30);

        receivedRecentLast = new JRadioButton("Most Recent Last");
        addItem(p, receivedRecentLast, 1, 1, 0, 0, 1, GridBagConstraints.PAGE_START, 0, 0, 30, 60);

        receivedRecentFirst.addActionListener(e -> {
            receivedRecentLast.setSelected(false);
        });

        receivedRecentLast.addActionListener(e -> {
            receivedRecentFirst.setSelected(false);
        });

        JRadioButton type = new JRadioButton("Type");
        addItem(p, type, 0, 2, 0, 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 0, 30, 0);

        JRadioButton amt = new JRadioButton("Amount");
        addItem(p, amt, 0, 3, 0, 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 0, 30, 30);

        JRadioButton accessionNum = new JRadioButton("Accession Number");
        addItem(p, accessionNum, 0, 4, 0, 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 0, 30, 0);

        JRadioButton storageLoc = new JRadioButton("Storage Location");
        addItem(p, storageLoc, 0, 5, 0, 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 0, 30, 0);

        JRadioButton contact = new JRadioButton("Contact");
        addItem(p, contact, 0, 6, 0, 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 0, 30, 0);

        JRadioButton source = new JRadioButton("Source");
        addItem(p, source, 0, 7, 0, 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 0, 30, 0);

        JRadioButton m_type = new JRadioButton("Measurement Type");
        addItem(p, m_type, 0, 8, 0, 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 0, 40, 0);

        JButton apply = new JButton("Apply");
        apply.addActionListener(this);
        addItem(p, apply, 0, 9, 0, 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 0, 40, 0);

        buttons = new JRadioButton[]{collected, type, amt, accessionNum, storageLoc, contact, source, m_type};
        collected.addActionListener(new ClearRadioButtons());
        type.addActionListener(new ClearRadioButtons());
        amt.addActionListener(new ClearRadioButtons());
        accessionNum.addActionListener(new ClearRadioButtons());
        storageLoc.addActionListener(new ClearRadioButtons());
        contact.addActionListener(new ClearRadioButtons());
        source.addActionListener(new ClearRadioButtons());
        m_type.addActionListener(new ClearRadioButtons());

        return p;
    }

    private void addItem(JPanel p, JComponent c, int x, int y, double wy, double wx, int gw, int anc, int top, int left, int bottom, int right){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = gw;
        constraints.weightx = wx;
        constraints.weighty = wy;
        constraints.anchor = anc;
        constraints.insets = new Insets(top, left, bottom, right);
        p.add(c, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        ResultSet samples;
        String sort_by = "";
        String query = "select * from samples";

        for (JRadioButton button : buttons) {
            if (button.isSelected()) {
                sort_by = button.getText();
            }
        }

        switch (sort_by) {
            case "Collection Date":
                query += " order by collected";
                if (receivedRecentFirst.isSelected()) {
                    query += " asc;";
                } else if (receivedRecentLast.isSelected()) {
                    query += " desc;";
                } else {
                    query += ";";
                }
                break;
            case "Accession Number":
                query += " order by accession_num;";
                break;
            case "Storage Location":
                query += " order by storage;";
                break;
            case "Measurement Type":
                query += " order by units;";
                break;
            case "":
                query += ";";
                break;
            default:
                query += " order by " + sort_by.toLowerCase() + ";";
        }

        samples = db_conn.getDbEntries(query);
        fillTable(samples);
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
                sample_info[1] = samples.getFloat("amount") + " " + samples.getString("units");
                sample_info[2] = type.getString("subtype");
                sample_info[3] = samples.getString("source");
                contact.next();
                sample_info[4] = contact.getString("name");
                storage.next();
                sample_info[5] = storage.getString("room") + ", " + storage.getString("location");
                sample_info[6] = samples.getString("collected");

                data.add(sample_info);
                t_model.insertItems(data);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    private void setInventoryLayout(GroupLayout layout, JPanel filters, JScrollPane samples){
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGap(5, 100, 1000)
                        .addComponent(filters)
                        .addGap(5, 100, 1000)
                        .addComponent(samples)
                        .addContainerGap(100, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(5, 90, 1000)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, true)
                                .addComponent(filters)
                                .addComponent(samples))
                        .addContainerGap(90, Short.MAX_VALUE)

        );
    }

    private class ClearRadioButtons implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for(JRadioButton btn : buttons){
                if (e.getSource() != btn){
                    btn. setSelected(false);
                }
            }
        }
    }

    private class SaveNewAmount implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int row = samples.getSelectedRow();
            String accession_num = samples.getModel().getValueAt(row, 0).toString();
            float  new_amt = Float.parseFloat(samples.getModel().getValueAt(row, 1).toString());
            String query = "update table samples set amount = " + new_amt + " where accession_num = \"" +
                    accession_num + "\";";
            int success = db_conn.updateDb(query);

            if(success < 0){
                JOptionPane.showMessageDialog(null, "There was an issue updating the inventory");
            }
            else {
                JOptionPane.showMessageDialog(null, "Inventory of sample " + accession_num +
                        " was updated to " + new_amt);
            }
        }
    }

    private class SearchableTable extends SearchTableModel {
        public SearchableTable(String[] column_names, ArrayList<String[]> data) {
            super(column_names, data);
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == 1;
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            data.get(row)[col] = value.toString();
            fireTableCellUpdated(row, col);
        }
    }

    private class TableChanges implements TableModelListener {
        public TableChanges() {
            super();
        }

        @Override
        public void tableChanged(TableModelEvent e) {
            int row = samples.getSelectedRow();

            if(row > -1) {
                String accession_num = samples.getModel().getValueAt(row, 0).toString();
                String new_amt_str = samples.getModel().getValueAt(row, 1).toString();
                float new_amt;

                try {
                   new_amt = Float.parseFloat(new_amt_str.split("\\s+")[0]);
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null, "There must be a space between the quantity and the units");
                    return;
                }

                String query = "update samples set amount = " + new_amt + " where accession_num = \"" +
                        accession_num + "\";";
                int success = db_conn.updateDb(query);

                if(success < 0){
                    JOptionPane.showMessageDialog(null, "There was an issue updating the inventory");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Inventory of sample " + accession_num +
                            " was updated to " + new_amt);
                }
            }
        }
    }
}
