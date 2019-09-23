import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class NewSample extends TabsBasePanel implements ActionListener {
    private DBWrapper db_conn;
    private JComboBox subBox;
    private JComboBox contactBox;
    private JTextField amt;
    private JFormattedTextField cDateBox;
    private JTextField accessBox;
    private JComboBox sourceBox;
    private JComboBox storageBox;
    private JTextField info;
    private JRadioButton mg;
    private JRadioButton g;
    private JRadioButton ml;
    private JRadioButton l;
    private JRadioButton[] buttons;

    public NewSample(DBWrapper db_conn) {
        this.db_conn = db_conn;
        add(NewSampleUI());
    }

    private JPanel NewSampleUI() {
        JPanel p = new JPanel();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        p.setPreferredSize(new Dimension(1000, screenSize.height));
        //p.setMaximumSize(new Dimension(1100, screenSize.height));

        p.setLayout(new GridBagLayout());

        JLabel sampleType = new JLabel("Sample Type");
        addItem(p, sampleType, 0, 0, 0.2, 0.015, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 60, 0, 0);

        JComboBox sampleBox = ComboBoxes.getSampleTypeBox(db_conn);
        getAutofillAction(sampleBox);
        addItem(p, sampleBox, 0, 1, 0.2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 60, 50, 0);

        JLabel amount = new JLabel("Amount");
        addItem(p, amount, 0, 4, 0.2, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 60, 0, 0);

        amt = new JTextField(5);
        amt.setPreferredSize(new Dimension(110, 26));
        addItem(p, amt, 0, 5, 0.2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 60, 50, 0);

        JPanel measurements = measurementUnits();
        addItem(p, measurements, 1, 5, 0.1, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, 10, 0, 0);

        JLabel subType = new JLabel("Sample Sub-Type");
        addItem(p, subType, 0, 2, 0.2, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 60, 0, 0);

        subBox = new JComboBox();
        addItem(p, subBox, 0, 3, 0.2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 60, 50, 0);

        JLabel collectionDate = new JLabel("Collection Date  MM/DD/YYYY");
        addItem(p, collectionDate, 0, 6, 0.2, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 60, 0, 0);

        cDateBox = dateEntryField();
        cDateBox.setPreferredSize(new Dimension(110, 26));
        addItem(p, cDateBox, 0, 7, 0.2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 60, 60, 0);

        JButton addSample = new JButton("Add Sample");
        addSample.addActionListener(this);
        addItem(p, addSample, 0, 9, 0.2, 0.025, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, 60, 0, 0);

        Action cDateAction = new TodaysDateAction("Today's Date", cDateBox);
        JButton today2 = new JButton(cDateAction);
        addItem(p, today2, 1, 7, 0.1, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, 10, 0, 0);

        JLabel accession_num = new JLabel("Accession Number");
        addItem(p, accession_num, 2, 0, 0.2, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 10, 0, 60);

        accessBox = new JTextField();
        addItem(p, accessBox, 2, 1, 0.2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 10, 0, 60);

        JLabel source = new JLabel("Source");
        addItem(p, source, 2, 2, 0.2, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 10, 0, 60);

        sourceBox = ComboBoxes.getSampleSourceBox(db_conn);
        addItem(p, sourceBox, 2, 3, 0.2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 10, 0, 60);

        JLabel storageLocation = new JLabel("Storage Location");
        addItem(p, storageLocation, 2, 4, 0.2, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 10, 0, 60);

        storageBox = ComboBoxes.getLocationBox(db_conn);
        addItem(p, storageBox, 2, 5, 0.2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 10, 0, 60);

        JLabel contact = new JLabel("Contact");
        addItem(p, contact, 2, 6, 0.2, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 10, 0, 60);

        contactBox = ComboBoxes.getContactBox(db_conn);
        addItem(p, contactBox, 2, 7, 0.2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 10, 50, 60);

        JLabel additionalInfo = new JLabel("Additional Description");
        addItem(p, additionalInfo, 2, 8, 0.2, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 10, 0, 60);

        info = new JTextField(20);
        info.setPreferredSize(new Dimension(60, 26));
        addItem(p, info, 2, 9, 0.2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 10, 50, 60);

        return p;
    }

    private void getAutofillAction(JComboBox box){
        box.addActionListener(event -> {
            JComboBox source = (JComboBox) event.getSource();
            String selected_type = source.getSelectedItem().toString();
            String query = "select * from sampleTypes where type = \"" + selected_type + "\";";

            if(!selected_type.equals("")){
                subBox.setModel(db_conn.getSTypeComboBoxModel(query));
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

    private void addItem(JPanel p, JComponent c, int x, int y, double wx, double wy, int gw, int anc, int fill, int left, int bottom, int right) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = fill;
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = gw;
        constraints.weightx = wx;
        constraints.weighty = wy;
        constraints.anchor = anc;
        constraints.insets = new Insets(0, left, bottom, right);
        p.add(c, constraints);
    }

    private JPanel measurementUnits(){
        JPanel p = new JPanel(new GridLayout(1, 0, 1, 0));
        mg = new JRadioButton("mg");
        mg.addActionListener(e -> {
            g.setSelected(false);
            ml.setSelected(false);
            l.setSelected(false);
        });

        g = new JRadioButton("g");
        g.addActionListener(e -> {
            mg.setSelected(false);
            ml.setSelected(false);
            l.setSelected(false);
        });

        ml = new JRadioButton("mL");
        ml.addActionListener(e -> {
            mg.setSelected(false);
            g.setSelected(false);
            l.setSelected(false);
        });

        l = new JRadioButton("L");
        l.addActionListener(e -> {
            mg.setSelected(false);
            g.setSelected(false);
            ml.setSelected(false);
        });

        buttons = new JRadioButton[]{mg, g, ml, l};

        for (JRadioButton b : buttons){
            p.add(b);
        }

        return p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        float amount = -1;
        int result = -500;

        try{
            amount = Float.parseFloat(amt.getText());
        } catch (NumberFormatException | NullPointerException ex){
            JOptionPane.showMessageDialog(this, "Amount must be an integer value");
        }

        SType sample_type = (SType)subBox.getSelectedItem();
        String date = cDateBox.getText();
        String accession_num = accessBox.getText();
        String source = sourceBox.getSelectedItem() == null ? "" : sourceBox.getSelectedItem().toString();
        String storage_location = storageBox.getSelectedItem() == null ? "" : storageBox.getSelectedItem().toString();
        String contact = contactBox.getSelectedItem() == null ? "" : contactBox.getSelectedItem().toString();
        String description = info.getText();
        String units = "";

        for (JRadioButton button : buttons) {
            if (button.isSelected()) {
                units = button.getText();
            }
        }

        String[] fields = new String[]{date, accession_num, source, storage_location, contact, units};
        if (allFieldsPresent(sample_type, fields)) {
            int st_id = sample_type.getID();
            int sl_id = getStorageLocId(storage_location);
            int ct_id = getContactId(contact);
            String descrip = description.equals("") ? null : "\"" + description + "\"";
            String query = "insert into samples values (\"" + accession_num + "\", " + st_id + ", \"" + source + "\", " +
                    sl_id + ", " + ct_id + ", " + descrip + ", " + amount + ", \"" + units + "\", str_to_date(\"" +
                    date + "\", \"%m/%d/%Y\"));";

            result = db_conn.updateDb(query);
            if (result < 0) {
                JOptionPane.showMessageDialog(this, "There was an issue adding the sample");
            }
            else {
                JOptionPane.showMessageDialog(this, "Sample added");

            }
         }
        else {
            JOptionPane.showMessageDialog(this, "All fields other than description are required");
        }
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

    private boolean allFieldsPresent(SType sample_type, String[] fields){
        for (String field : fields){
            if (field == null || field.equals("")){
                return false;
            }
        }

        return sample_type.getID() != -1;
    }
}