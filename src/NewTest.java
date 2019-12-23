import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NewTest extends TabsBasePanel implements ActionListener {
    private DBWrapper db_conn;
    private JPanel end;
    private JPanel base;
    private JComboBox testType;
    private ArrayList<JTextField> accessions;
    private ArrayList<JTextField> amounts;
    private ArrayList<JRadioButton[]> units;
    private JTextField dup;
    private int testid;

    public NewTest(DBWrapper db_conn){
        this.db_conn = db_conn;
        this.accessions = new ArrayList<>();
        this.amounts = new ArrayList<>();
        this.units = new ArrayList<>();

        base = newTestUI();
        JScrollPane test_pane = new JScrollPane(base);
        test_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        test_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        test_pane.setMinimumSize(new Dimension(1000, screenSize.height));
        test_pane.setPreferredSize(new Dimension(1000, screenSize.height));
        add(test_pane, BorderLayout.CENTER);
    }

    private JPanel newTestUI(){
        JPanel p = new JPanel();

        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.setBorder(new EmptyBorder(180, 165, 450, 320));

        addJLabel(p, "Test Type");

        testType = ComboBoxes.getTestTypeBox(db_conn);
        testType.setAlignmentX(Component.LEFT_ALIGNMENT);
        testType.setMaximumSize(new Dimension(300, 26));
        p.add(testType);

        p.add(samplePanel());
        end = endPanel();
        p.add(end);

        return p;
    }

    private JPanel endPanel(){
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

        addJLabel(p, "Number of Duplicates");

        dup = new JTextField();
        dup.setAlignmentX(Component.LEFT_ALIGNMENT);
        dup.setMaximumSize(new Dimension(305, 30));
        p.add(dup);

        p.add(Box.createRigidArea(new Dimension(0, 30)));
        JButton create = new JButton("Create Test");
        create.addActionListener(this);
        p.add(create);
        p.add(Box.createRigidArea(new Dimension(0, 30)));

        return p;
    }

    private JPanel samplePanel(){
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

        p.add(Box.createRigidArea(new Dimension(0, 6)));
        addJLabel(p, "Accession Number");

        JTextField sample = new JTextField();
        sample.setAlignmentX(Component.LEFT_ALIGNMENT);
        sample.setMaximumSize(new Dimension(302, 30));
        p.add(sample);

        JLabel additionalSubtype = new JLabel("Add Another Sample");
        additionalSubtype.setAlignmentX(Component.LEFT_ALIGNMENT);
        additionalSubtype.addMouseListener(new AdditSample());
        p.add(additionalSubtype);

        JPanel sampAmt = sampleAmount();
        sampAmt.setAlignmentX(Component.LEFT_ALIGNMENT);
        sampAmt.setBorder(new EmptyBorder(50, 0, 0, 0));
        p.add(sampAmt);

        accessions.add(sample);

        return p;
    }

    private class AdditSample extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            base.remove(end);
            base.add(samplePanel());
            base.add(end);
            base.revalidate();
            base.repaint();
        }
    }

    private void addJLabel(JPanel p, String label_text){
        JLabel label = new JLabel(label_text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(50, 0, 0, 0));
        p.add(label);
    }

    private JPanel sampleAmount() {
        int i = 1;
        JPanel p = new JPanel();
        p.setMaximumSize(new Dimension(600, 100));
        p.setLayout(new GridBagLayout());

        JRadioButton[] buttons = measurementUnits();

        JLabel sampAmt = new JLabel("Sample Amount");
        addItem(p, sampAmt, 0, 0, 0, GridBagConstraints.HORIZONTAL, 0);

        JTextField amountBox = new JTextField();
        sampAmt.setMaximumSize(new Dimension(298, 26));
        addItem(p, amountBox, 0, 1, 0.1, GridBagConstraints.BOTH, 4);

        for (JRadioButton b : buttons) {
            addItem(p, b, i++, 1, 0, GridBagConstraints.HORIZONTAL, 2);
        }

        amounts.add(amountBox);

        return p;
    }

    private JRadioButton[] measurementUnits(){
        JRadioButton mg = new JRadioButton("mg");
        JRadioButton g = new JRadioButton("g");
        JRadioButton ml = new JRadioButton("mL");
        JRadioButton l = new JRadioButton("L");

        mg.addActionListener(e -> {
            g.setSelected(false);
            ml.setSelected(false);
            l.setSelected(false);
        });

        g.addActionListener(e -> {
            mg.setSelected(false);
            ml.setSelected(false);
            l.setSelected(false);
        });

        ml.addActionListener(e -> {
            mg.setSelected(false);
            g.setSelected(false);
            l.setSelected(false);
        });

        l.addActionListener(e -> {
            mg.setSelected(false);
            g.setSelected(false);
            ml.setSelected(false);
        });

        JRadioButton[] buttons = new JRadioButton[]{mg, g, ml, l};
        units.add(buttons);

        return buttons;
    }

    protected void addItem(JPanel p, JComponent c, int x, int y, double wx, int fill, int right) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = fill;
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = 1;
        constraints.weightx = wx;
        constraints.weighty = 0;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.insets = new Insets(0, 0, 0, right);
        p.add(c, constraints);
    }

    public int createNewTest(TType type, int duplicates){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDateTime todaysDate = LocalDateTime.now();
        String date = formatter.format(todaysDate);

        String query = "insert into tests (type, owner, num_dup, date_made, status) values (" + type.getID() + ", \"" +
                CurrentUser.getUser() + "\", " + duplicates + ", str_to_date(\"" + date + "\", \"%m/%d/%Y\")" +
                ", 0);";

        int result = db_conn.updateDb(query);
        if(result < 1){
            JOptionPane.showMessageDialog(null, "There was an issue adding the new test");
        }
        else{
            String q2 = "select id from tests order by id desc limit 1;";
            ResultSet new_test = db_conn.getDbEntries(q2);
            try {
                new_test.next();
                return new_test.getInt("id");
            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    private int updateSampleQuantity(String s_id, Float qty, String orig_units, Float new_amt,
                                     String smp_units, int duplicates){
        if(!orig_units.equals(smp_units)){
            JOptionPane.showMessageDialog(null, "Sample has units " + orig_units +
                    ", amount used must be in same units");
            return -1;
        }
        Float new_qty = qty - (new_amt * duplicates);
        String new_units = orig_units;

        if(new_qty < 0.00001){
            JOptionPane.showMessageDialog(null, "Not enough of sample + " + s_id +
                    " present");
            return -1;
        }

        if(orig_units.equals("L") && new_qty < 1){
            new_units = "mL";
            new_qty = new_qty * 1000;
        }
        if(orig_units.equals("g") && new_qty < 1){
            new_units = "mg";
            new_qty = new_qty * 1000;
        }

        String query = "update samples set amount = " + new_qty + ", units = \"" + new_units +
                "\" where accession_num = \"" + s_id + "\";";

        return db_conn.updateDb(query);
    }

    private int createTestSampleJoin(ArrayList<String> samples, ArrayList<Float> samp_amts,
                                      int duplicates, ArrayList<String> samp_units){
        ArrayList<String> queries = new ArrayList<>();

        for(int i = 0 ; i < samples.size() ; i++){
            try{
                String s_id = samples.get(i);
                ResultSet smp = db_conn.getDbEntries("select * from samples where accession_num = \"" +
                        samples.get(i) + "\";");
                smp.next();

                if(updateSampleQuantity(s_id, smp.getFloat("amount"), smp.getString("units"),
                        samp_amts.get(i), samp_units.get(i), duplicates) < 0){
                    return -1;
                }

                queries.add("insert into joinTestSamples(test_id, sample_id, amt_used, units, num_dup) values (" +
                        testid + ", \"" + s_id + "\", " + samp_amts.get(i) + ", \"" + samp_units.get(i) + "\", " +
                        duplicates + ");");

            } catch(SQLException e) {
                e.printStackTrace();
            }
        }

        for (String q : queries) {
            int result = db_conn.updateDb(q);
            if (result < 0) {
                JOptionPane.showMessageDialog(null, "There was an issue updating the sample");
            }
        }

        return 1;
    }

    private int createTestSteps(int test_type_id, int duplicates){
        ResultSet baseSteps = db_conn.getDbEntries("select * from baseSteps where test_type = " +
                test_type_id + ";");
        try{
            while(baseSteps.next()){
                if(baseSteps.getInt("action") != 0){
                    String query = "insert into actions(name, length) select name, length from actions where id = " +
                            baseSteps.getInt("action") + ";";
                    if(db_conn.updateDb(query) < 0) {
                        System.out.println("insertion to actions failed");
                        return -1;
                    }

                    String q2 = "select id from actions order by id desc limit 1;";
                    ResultSet new_act = db_conn.getDbEntries(q2);
                    new_act.next();

                    query = "insert into workflowSteps(test, test_type, action, ordinal, complete) values (" +
                            testid + ", " + test_type_id + ", " + new_act.getInt("id") + ", " +
                            baseSteps.getInt("ordinal") + ", " + 0 + ");";

                    if(db_conn.updateDb(query) < 0) {
                        System.out.println("new workflow step failed");
                        return -1;
                    }
                }

                if(baseSteps.getInt("measurement") != 0){
                    ResultSet base_meas = db_conn.getDbEntries("select * from measurements where id = " +
                            baseSteps.getInt("measurement") + ";");
                    base_meas.next();
                    String meas_name = base_meas.getString("name");
                    String meas_units = base_meas.getString("units");
                    int ordinal = base_meas.getInt("ordinal");

                    for(int i = 1; i <= duplicates; i++){
                        String query = "insert into measurements(test, name, ordinal, num_dup, units) values (" +
                                testid + ", \"" + meas_name + "\", " + ordinal + ", " + i + ", \"" + meas_units +
                                "\");";
                        if(db_conn.updateDb(query) < 0){
                            System.out.println("insertion to measurements failed");
                            return -1;
                        }
                    }

                    String query = "insert into workflowSteps(test, test_type, ordinal, complete) values (" +
                            testid + ", " + test_type_id + ", " +
                            baseSteps.getInt("ordinal") + ", " + 0 + ");";

                    if(db_conn.updateDb(query) < 0) {
                        System.out.println("new workflow step failed");
                        return -1;
                    }
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return 1;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ArrayList<String> samples = new ArrayList<>();
        ArrayList<Float> samp_amts = new ArrayList<>();
        ArrayList<String> samp_units = new ArrayList<>();

        TType test_type = (TType)testType.getSelectedItem();
        if(test_type == null || test_type.getType().equals("")){
            JOptionPane.showMessageDialog(null, "Test type must be selected.");
            return;
        }

        for(int i = 0 ; i < accessions.size() ; i++){
            if(!accessions.get(i).getText().isEmpty()){
                samples.add(accessions.get(i).getText());
            }

            if(!amounts.get(i).getText().isEmpty()){
                samp_amts.add(Float.parseFloat(amounts.get(i).getText()));
            }

            for (JRadioButton button : units.get(i)) {
                if (button.isSelected()) {
                    samp_units.add(button.getText());
                }
            }
        }

        if(samples.isEmpty() && samp_amts.isEmpty() && samp_units.isEmpty()){
            JOptionPane.showMessageDialog(null, "At least one sample must be selected.");
            return;
        }

        if(samples.size() != samp_amts.size() || samples.size() != samp_units.size()){
            JOptionPane.showMessageDialog(null, "Accession number, amount, and units must be selected for all samples.");
            return;
        }

        int duplicates = 1;
        if(dup.getText() != null && !dup.getText().equals("")){
            duplicates = Integer.parseInt(dup.getText());
        }

        testid = createNewTest(test_type, duplicates);
        if(testid < 0){
            return;
        }

        int success = createTestSampleJoin(samples, samp_amts, duplicates, samp_units);
        if(success < 0){
            JOptionPane.showMessageDialog(null, "Issue updating samples.");
        }

        success = createTestSteps(test_type.getID(), duplicates);
        if(success < 0) {
            JOptionPane.showMessageDialog(null, "Issue creating test steps.");
        }
        else{
            JOptionPane.showMessageDialog(null, "Test created.");
        }

        new ViewTest(db_conn, testid);
    }
}
