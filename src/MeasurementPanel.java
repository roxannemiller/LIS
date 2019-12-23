import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MeasurementPanel extends JPanel {
    private DBWrapper db_conn;
    private String meas_query;
    private int test_id;
    private int workflow_id;
    private int num_step;
    private JPanel progress;
    private ArrayList<JTextField> data;
    private ArrayList<JRadioButton[]> data_units;

    public MeasurementPanel(DBWrapper db_conn, int num_step, int test_id, int workflow_id){
        this.db_conn = db_conn;
        this.test_id = test_id;
        this.workflow_id = workflow_id;
        this.num_step = num_step;
        this.data = new ArrayList<>();
        this.data_units = new ArrayList<>();

        setLayout(new GridBagLayout());

        try{
            meas_query = "select * from measurements where test = " + test_id + " and ordinal = " + num_step +
                    " order by num_dup;";
            ResultSet measurements = db_conn.getDbEntries(meas_query);
            measurements.next();

            JLabel instr = new JLabel(num_step + ". " + measurements.getString("name"));
            addItem(this, instr, 0, 0, GridBagConstraints.FIRST_LINE_START, 10, 0, 10, 0);

            ResultSet step = db_conn.getDbEntries("select * from workflowSteps where id = " + workflow_id + ";");
            step.next();

            if(step.getInt("complete") == 1) {
                addItem(this, measurementData(), 0, 1, GridBagConstraints.LAST_LINE_START, 0, 40, 10, 0);
               }

            else{
                progress = measurementProg();
                addItem(this, progress, 0, 1, GridBagConstraints.LAST_LINE_START, 0, 40, 10, 0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addItem(JPanel p, JComponent c, int x, int y, int anc, int top, int left, int bottom, int right) {
        if (p == null) {
            p = this;
        }

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = 0;
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.anchor = anc;
        constraints.insets = new Insets(top, left, bottom, right);
        p.add(c, constraints);
    }

    private JPanel measurementData() {
        JPanel p = new JPanel(new GridLayout(0, 1, 0, 5));

        try {
            ResultSet measurements = db_conn.getDbEntries(meas_query);

            int i = 1;
            while(measurements.next()) {
                p.add(new JLabel("Trial " + i + ": " + measurements.getFloat("measurement") +
                        " " + measurements.getString("units")));
                i++;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return p;
    }

    private JPanel measurementProg() {
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());

        try {
            ResultSet measurements = db_conn.getDbEntries(meas_query);

            int i = 1;
            int y = 0;
            while(measurements.next()) {
                if(measurements.getFloat("measurement") != 0) {
                    addItem(p, new JLabel("Trial " + i + ": " + measurements.getFloat("measurement") +
                            " " + measurements.getString("units")), 0, y++, GridBagConstraints.FIRST_LINE_START, 5, 0, 10, 0);
                }
                else{
                    addItem(p, new JLabel("Trial " + i + ": "), 0, y++, GridBagConstraints.FIRST_LINE_START, 5, 0, 5, 0);
                    addItem(p, measurementFields(measurements.getString("units")), 0, y++, GridBagConstraints.FIRST_LINE_START, 0, 0, 10, 0);
                }
                i++;
            }

            JButton save_button = new JButton("Save");
            save_button.addActionListener(new SaveAction());
            addItem(p, save_button, 0, y, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0);

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return p;
    }

    private JPanel measurementFields(String units){
        JPanel p = new JPanel(new GridLayout(1, 2, 3, 0));
        JTextField data_field = new JTextField();
        data_field.setPreferredSize(new Dimension(100, 26));
        data.add(data_field);
        p.add(data_field);
        p.add(new JLabel(units));

        return p;
    }

    private class SaveAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ArrayList<String> queries = new ArrayList<>();
            Float m;
            String u = "";

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getText() == null || data.get(i).equals("")) {
                    JOptionPane.showMessageDialog(null, "All duplicates must be measured.");
                    return;
                } else {
                    m = Float.parseFloat(data.get(i).getText());
                }

//                for (JRadioButton button : data_units.get(i)) {
//                    if (button.isSelected()) {
//                        u = button.getText();
//                    }
//                }

//                if(u == null){
//                    JOptionPane.showMessageDialog(null, "All measurements must have units.");
//                    return;
//                }
                queries.add("update measurements set measurement = " + m +
                        " where test = " + test_id + " and ordinal = " + num_step + " and num_dup = ");
            }

            System.out.println(queries.size());
            for (int i = 1; i <= queries.size(); i++) {
                int success = db_conn.updateDb(queries.get(i - 1) + i + ";");

                if (success < 0) {
                    JOptionPane.showMessageDialog(null, "Issue adding measurements to database");
                    return;
                }
            }

            String query = "update workflowSteps set complete = 1 where id = " + workflow_id + ";";
            if (db_conn.updateDb(query) < 0) {
                JOptionPane.showMessageDialog(  null, "There was an issue updating the workflow step's completeness.");
                return;
            }

            remove(progress);
            addItem(null, measurementData(), 0, 1, GridBagConstraints.LAST_LINE_START, 0, 40, 10, 0);
            revalidate();
            repaint();
        }
    }
}
