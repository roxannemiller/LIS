import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ActionPanel extends JPanel {
    private DBWrapper db_conn;
    private JPanel unfinished;
    private int act_id;
    private int workflow_id;
    private JCheckBox begin;
    private ResultSet action;
    private JCheckBox finish;

    public ActionPanel (DBWrapper db_conn, int act_id, int num_step, int workflow_id){
        this.db_conn = db_conn;
        this.act_id = act_id;
        this.workflow_id = workflow_id;

        setLayout(new GridBagLayout());

        try {
            updateAction();
            JLabel instr = new JLabel(num_step + ". " + action.getString("name"));
            addItem(this, instr, 0, 0, GridBagConstraints.FIRST_LINE_START, 10, 0, 10, 0);

            ResultSet step = db_conn.getDbEntries("select * from workflowSteps where id = " + workflow_id + ";");
            step.next();

            if(step.getInt("complete") == 1) {
                if(action.getString("length") != null){
                    addItem(this, actionStats(action.getString("start"), action.getString("finish")), 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 40, 0, 0);
                }
                else {
                    addItem(this, new JLabel("✔ Complete"), 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 40, 0, 0);
                }
                return;
            }

            if(action.getString("length") != null) {
                initUnfinished();
                addItem(this, unfinished, 0, 2, GridBagConstraints.FIRST_LINE_START, 0, 40, 0, 0);

            } else {
                unfinished = new JPanel();
                finish = new JCheckBox("Finish");
                finish.addActionListener(new FinishAction());
                unfinished.add(finish);
                addItem(this, unfinished, 0, 2, GridBagConstraints.FIRST_LINE_START, 0, 40, 0, 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateAction() {
        try {
            action = db_conn.getDbEntries("select * from actions where id = " + act_id + ";");
            action.next();
        } catch(SQLException e) {
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

    private void initUnfinished(){
        unfinished = new JPanel(new GridLayout(2, 0, 0, 0));
        try{
            if(action.getString("start") == null){
                begin = new JCheckBox("begin");
                begin.addActionListener(new BeginAction());
                unfinished.add(begin);
            }
            else{
                unfinished.add(progStats());
                finish = new JCheckBox("Finish");
                finish.addActionListener(new FinishAction());
                unfinished.add(finish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class BeginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime todaysDate = LocalDateTime.now();
            String start_time = formatter.format(todaysDate);

            String query = "update actions set start = time_format(\"" + start_time + "\", \"%H:%i:%s\")" +
                    " where id = " + act_id + ";";
            if(db_conn.updateDb(query) < 0){
                JOptionPane.showMessageDialog(null, "There was an issue updating the start time.");
                return;
            }

            unfinished.remove(begin);
            unfinished.add(progStats());
            finish = new JCheckBox("Finish");
            finish.addActionListener(new FinishAction());
            unfinished.add(finish);
            unfinished.revalidate();
            unfinished.repaint();

            revalidate();
            repaint();
        }
    }

    private class FinishAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                if (action.getString("length") != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalDateTime todaysDate = LocalDateTime.now();
                    String start_time = formatter.format(todaysDate);

                    String query = "update actions set finish = time_format(\"" + start_time + "\", \"%H:%i:%s\")" +
                            " where id = " + act_id + ";";

                    if (db_conn.updateDb(query) < 0) {
                        JOptionPane.showMessageDialog(null, "There was an issue updating the finish time.");
                        return;
                    }

                    remove(unfinished);
                    addItem(null, progStats(), 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 40, 10, 0);
                }
                else {
                    remove(unfinished);
                    addItem(null, new JLabel("✔ Complete"), 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 40, 15, 0);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String query = "update workflowSteps set complete = 1 where id = " + workflow_id + ";";
            if (db_conn.updateDb(query) < 0) {
                JOptionPane.showMessageDialog(null, "There was an issue updating the workflow step's completeness.");
                return;
            }

            revalidate();
            repaint();
        }
    }

    private JPanel progStats(){
        updateAction();
        JPanel p = new JPanel(new GridLayout(3, 0, 4, 4));

        try{
            String start = action.getString("start");
            String length = action.getString("length");
            p.add(new JLabel("Start time: " + start));

            if(!length.equals("00:00:00")) {
                String query = query = "select addtime(a1.start, a2.length) as sum from actions a1, actions a2 where a1.id = " +
                        act_id + " and a2.id = " + act_id + ";";

                ResultSet sum = db_conn.getDbEntries(query);
                sum.next();
                String end = sum.getString("sum");
                p.add(new JLabel("Length: "+ length));
                p.add(new JLabel("Finish Time: " + end));
            }
            else{
                p.add(new JLabel("Length: 24:00:00"));
                p.add(new JLabel("Finish Time: " + start));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return p;
    }

    private JPanel actionStats(String start, String stop){
        JPanel p = new JPanel(new GridLayout(3, 0, 4, 0));
        p.add(new JLabel("Start time: " + start));
        p.add(new JLabel("End time: " + stop));

        try{
            String query = query = "select timediff(a1.start, a2.finish) as diff from actions a1, actions a2 where a1.id = " +
                    act_id + " and a2.id = " + act_id + ";";
            ResultSet diff = db_conn.getDbEntries(query);
            diff.next();
            String length = diff.getString("diff").replace("-", "");
            p.add(new JLabel("Length: " + length));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return p;
    }
}
