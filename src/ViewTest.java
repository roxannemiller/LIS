import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewTest {
    private Integer test_id;
    private DBWrapper db_conn;
    private boolean complete;
    private JPanel result_panel;

    public ViewTest(DBWrapper db_conn, int test_id){
        this.test_id = test_id;
        this.db_conn = db_conn;
        this.complete = true;

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width, screenSize.height);
        frame.getContentPane().add(testInfo());

        frame.setVisible(true);
    }

    private JScrollPane testInfo(){
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        p.setBorder(BorderFactory.createEmptyBorder(150, 0, 300, 800));

        try{
            ResultSet test = db_conn.getDbEntries("select * from testTypes join tests where tests.id = " +
                    test_id + ";");
            test.next();
            String name = test.getString("service");
            JLabel title = new JLabel(name);
            title.setFont(title.getFont().deriveFont(20f));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            addItem(p, title, 0, 0, GridBagConstraints.FIRST_LINE_START, 0, 0, 25, 0);

            ResultSet steps = db_conn.getDbEntries("select * from workflowSteps where test = " + test_id +
                    " order by ordinal;");

            int y = 1;
            while(steps.next()){
                if(steps.getInt("action") != 0){
                    JPanel panel = new ActionPanel(db_conn, steps.getInt("action"), steps.getInt("ordinal"), steps.getInt("id"));
                    addItem(p, panel, 0, y++, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0);
                } else{
                    JPanel panel = new MeasurementPanel(db_conn, steps.getInt("ordinal"), test_id, steps.getInt("id"));
                    addItem(p, panel, 0, y++, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0);
                }

                if(steps.getInt("complete") != 1){
                    complete = false;
                }
            }

            addItem(p, getFinishingPanel(), 0, y, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        JScrollPane test_pane = new JScrollPane(p);
        test_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        test_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        test_pane.setPreferredSize(screenSize);

        return test_pane;
    }

    private JPanel getFinishingPanel(){
        result_panel = new JPanel();
        result_panel.setLayout(new GridLayout(0, 1, 0, 0));

        if(!complete) {
            result_panel.add(new JLabel("Results: "));
            JButton get_results = new JButton("Get Results");
            get_results.addActionListener(new GetResults());
            result_panel.add(get_results);
        } else {
            showResults();
        }

        return result_panel;
    }

    private void addItem(JPanel p, JComponent c, int x, int y, int anc, int top, int left, int bottom, int right) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = 0;
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.anchor = anc;
        constraints.insets = new Insets(top, left, bottom, right);
        p.add(c, constraints);
    }

    private void showResults(){
        String query = "select max(distinct num_dup) as dupl from measurements where test = " + test_id + ";";
        String q2 = "select * from measurements where test = " + test_id + " order by ordinal, num_dup;";
        ArrayList<Double> results1;
        ArrayList<Double> results2;
        ArrayList<ArrayList<Double>> inputs;

        try {
            ResultSet duplicates = db_conn.getDbEntries(query);
            ResultSet measurements = db_conn.getDbEntries(q2);

            duplicates.next();
            int dups = duplicates.getInt("dupl");
            inputs = new ArrayList<>();
            for(int i = 0; i < dups; i++){
                inputs.add(new ArrayList<>());
            }

            int i = 1;
            while(measurements.next()){
                inputs.get(i - 1).add(measurements.getDouble("measurement"));
                if(i == dups){
                    i = 1;
                } else {
                    i++;
                }
            }
            results1 = getFirstCalculations(inputs);
            results2 = getSecondCalculations(inputs);

            result_panel.removeAll();
            result_panel.add(new JLabel("Laboratory Dry Matter: "));
            for(Double r : results1){
                result_panel.add(new JLabel("   " + Double.toString(r)));
            }

            result_panel.add(new JLabel("Total Ash: "));
            for(Double r : results2){
                result_panel.add(new JLabel("   " + Double.toString(r)));
            }

            result_panel.revalidate();
            result_panel.repaint();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Double> getFirstCalculations(ArrayList<ArrayList<Double>> inputs) {
        ArrayList<Double> result = new ArrayList<>();
        for(ArrayList<Double> input : inputs) {
            Double r = ((input.get(2) - input.get(0)) / input.get(1)) * 100;
            result.add(r);
        }
        return result;
    }

    private ArrayList<Double> getSecondCalculations(ArrayList<ArrayList<Double>> inputs) {
        ArrayList<Double> result = new ArrayList<>();
        for(ArrayList<Double> input : inputs) {
            Double r = ((input.get(3) - input.get(0)) / (input.get(2) - input.get(0))) * 100;
            result.add(r);
        }
        return result;
    }

    private class GetResults implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            boolean complete = true;
            String query = "select distinct complete from workflowSteps where test = " + test_id + ";";

            try {
                ResultSet unique = db_conn.getDbEntries(query);
                while (unique.next()) {
                    if(unique.getInt("complete") == 0) {
                        complete = false;
                    }
                }
            } catch(SQLException e) {
                e.printStackTrace();
            }

            if(complete) {
                showResults();
            } else {
                JOptionPane.showMessageDialog(null, "Test is not complete");
            }
        }
    }
}

