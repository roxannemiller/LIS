import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class EditTestSteps extends JPanel implements ActionListener{
    private JComboBox typeBox;
    private JTextField new_type_box;
    private DBWrapper db_conn;
    private JRadioButton action_step;
    private JRadioButton meas_step;
    private JPanel new_step;
    private JPanel savePanel;
    private JPanel content;
    private TType test_type;
    private Boolean failure;

    public EditTestSteps(DBWrapper db_conn){
        this.db_conn = db_conn;
        this.failure = false;
        setLayout(new GridLayout(0, 1, 5, 5));

        JPanel base = buildSelf();
        base.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(base);
    }

    private JPanel buildSelf() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

        initStepRadioButtons();

        JLabel type = new JLabel("Test Type");
        type.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(type);

        typeBox = ComboBoxes.getTestTypeBox(db_conn);
        new_type_box = new JTextField();

        JPanel first_panel = multipleControlPanel("Choose Type", "New Type", new_type_box, typeBox);
        first_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        addAutoformatAction();
        p.add(first_panel);

        JScrollPane cPane = getContentPane();
        cPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(cPane);

        p.setBorder(new EmptyBorder(85, 165, 100, 0));
        return p;
    }

    private JScrollPane getContentPane(){
        content = new JPanel(new GridLayout(0, 1, 0, 0));
        savePanel = savePanel();
        content.add(savePanel);
        content.setAlignmentX(Component.LEFT_ALIGNMENT);

        JScrollPane steps_pane = new JScrollPane(content);
        steps_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        steps_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        steps_pane.setMinimumSize(new Dimension(1000, 500));
        steps_pane.setPreferredSize(new Dimension(1000, 500));
        steps_pane.setViewportBorder(null);
        add(steps_pane, BorderLayout.CENTER);

        return steps_pane;
    }

    private JPanel multipleControlPanel(String b1, String b2, JTextField tf, JComboBox cb){
        CardLayout base_layout = new CardLayout();
        JPanel base_panel = new JPanel(base_layout);

        JPanel choose = new JPanel();
        choose.setLayout(new BoxLayout(choose, BoxLayout.PAGE_AXIS));

        cb.setAlignmentX(Component.LEFT_ALIGNMENT);
        cb.setMaximumSize(new Dimension(500, 28));
        choose.add(cb);

        JButton to_add = new JButton(new ButtonAction(b2, base_layout, base_panel));
        to_add.setBorderPainted(false);
        choose.add(to_add);

        JPanel new_affil = new JPanel();
        new_affil.setLayout(new BoxLayout(new_affil, BoxLayout.PAGE_AXIS));

        tf.setAlignmentX(Component.LEFT_ALIGNMENT);
        tf.setMaximumSize(new Dimension(500, 28));
        new_affil.add(tf);

        JButton to_choose = new JButton(new ButtonAction(b1, base_layout, base_panel));
        to_choose.setBorderPainted(false);
        new_affil.add(to_choose);
        JButton start = new JButton("Add steps");
        start.addActionListener(e -> {
            typeBox.setSelectedItem(0);
            reset();
            new_step = newStep();
            content.add(new_step);
            content.add(savePanel);
            reRender();
        });
        new_affil.add(start);

        base_panel.add(b1, choose);
        base_panel.add(b2, new_affil);

        return base_panel;
    }

    private class ButtonAction extends AbstractAction {
        CardLayout layout;
        JPanel panel;

        public ButtonAction(String buttonLabel, CardLayout layout, JPanel panel){
            super(buttonLabel);
            this.layout = layout;
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e){
            layout.show(panel, e.getActionCommand());
        }
    }

    private void reRender() {
        content.revalidate();
        content.repaint();
    }

    private void reset() {
        content.removeAll();
        reRender();
    }

    private void initStepRadioButtons(){
        action_step = new JRadioButton("Action Step");
        meas_step = new JRadioButton("Measurement Step");

        action_step.addActionListener(e -> meas_step.setSelected(false));
        meas_step.addActionListener(e -> action_step.setSelected(false));
    }

    private void addAutoformatAction() {
        typeBox.addActionListener(event -> {
            JComboBox source = (JComboBox) event.getSource();
            test_type = (TType)source.getSelectedItem();
            new_type_box.setText(null);

            reset();
            if(test_type != null && !test_type.getType().equals("")) {
                displaySteps(test_type);
            } else {
                new_step = newStep();
                content.remove(savePanel);
                content.add(new_step);
                content.add(savePanel);
                reRender();
            }
        });
    }

    private void displaySteps(TType type) {
        String query = "select * from baseSteps where test_type = " + type.getID() + " order by ordinal;";
        content.remove(savePanel);

        try {
            ResultSet steps = db_conn.getDbEntries(query);
            while (steps.next()) {
                if(steps.getInt("action") == 0) {
                    query = "select * from measurements where id = " + steps.getInt("measurement") + ";";
                    ResultSet measurement = db_conn.getDbEntries(query);
                    measurement.next();
                    JPanel mstep = getMeasurementPanel(measurement.getString("name"), measurement.getString("units"));
                    content.add(mstep);
                }
                else {
                    query = "select * from actions where id = " + steps.getInt("action") + ";";
                    ResultSet action = db_conn.getDbEntries(query);
                    action.next();
                    JPanel astep = getActionPanel(action.getString("name"), action.getString("length"));
                    content.add(astep);
                }
            }

            new_step = newStep();
            content.add(new_step);
            content.add(savePanel);
            reRender();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private JPanel newStep() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(new JLabel("New Step: "));
        p.add(Box.createRigidArea(new Dimension(0, 10)));

        p.add(action_step);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(meas_step);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        JButton add_step = new JButton("Add Step");
        add_step.addActionListener(new StepAction());
        p.add(add_step);

        return p;
    }

    private JPanel savePanel(){
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        JButton save = new JButton("Save Steps");
        save.addActionListener(this);
        save.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(Box.createRigidArea(new Dimension(0, 10)));
        p.add(save);

        return p;
    }

    private JPanel getMeasurementPanel(String name_text, String units) {
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        addItem(p, new JLabel("Name: "), 0, 0, 0, GridBagConstraints.LAST_LINE_START, 15, 0, 0, 0);
        JTextField name = new JTextField();
        if(name_text != null) {
            name.setText(name_text);
        }
        name.setPreferredSize(new Dimension(500, 28));
        addItem(p, name, 0, 1, 0, GridBagConstraints.FIRST_LINE_START, 0, 0, 10, 0);
        addItem(p, new JLabel("Units: "), 0, 2, 0, GridBagConstraints.LAST_LINE_START, 0, 0, 0, 0);
        addItem(p, measurementUnits(units), 0, 3, 0, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0);

        JButton deleteButton = new JButton("Delete Step");
        deleteButton.addActionListener(new DeleteAction());
        addItem(p, deleteButton, 0, 5, 0.01, GridBagConstraints.FIRST_LINE_START, 5, 0, 10, 0);

        return p;
    }

    private JPanel getActionPanel(String name_text, String length) {
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        addItem(p, new JLabel("Name: "), 0, 0, 0, GridBagConstraints.LAST_LINE_START, 15, 0, 0, 0);
        JTextField name = new JTextField();
        if(name_text != null) {
            name.setText(name_text);
        }
        name.setPreferredSize(new Dimension(500, 28));
        addItem(p, name, 0, 1, 0, GridBagConstraints.FIRST_LINE_START, 0, 0, 10, 0);
        addItem(p, new JLabel("Duration: "), 0, 2, 0, GridBagConstraints.LAST_LINE_START, 0, 0, 0, 0);

        String time_entry = null;
        if(length != null) {
            time_entry = length;
        }
        addItem(p, timeEntryField(time_entry), 0, 3, 0, GridBagConstraints.FIRST_LINE_START, 0, 0, 0, 0);

        JButton deleteButton = new JButton("Delete Step");
        deleteButton.addActionListener(new DeleteAction());
        addItem(p, deleteButton, 0, 5, 0.01, GridBagConstraints.FIRST_LINE_START, 5, 0, 10, 0);

        return p;
    }

    private void addItem(JPanel p, JComponent c, int x, int y, double wy, int anc, int top, int left, int bottom, int right) {
        if (p == null) {
            p = this;
        }

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = 0;
        constraints.weightx = 0.02;
        constraints.weighty = wy;
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.anchor = anc;
        constraints.insets = new Insets(top, left, bottom, right);
        p.add(c, constraints);
    }

    private JFormattedTextField timeEntryField(String time) {
        JFormattedTextField f = new JFormattedTextField();
        try{
            MaskFormatter mask = new MaskFormatter("##:##:##");
            mask.setPlaceholderCharacter(' ');
            f = new JFormattedTextField(mask);

            if(time != null) {
                f.setText(time);
            }

            f.setPreferredSize(new Dimension(150, 28));
        }
        catch (ParseException e){
            JOptionPane.showMessageDialog(this, "Enter time in valid format");
        }

        return f;
    }

    private JPanel measurementUnits(String units){
        JPanel p = new JPanel(new GridLayout(1, 0, 1, 0));
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

        for (JRadioButton b : buttons){
            if(b.getText().equals(units)) {
                b.setSelected(true);
            }
            p.add(b);
        }

        return p;
    }

    private class StepAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            content.remove(new_step);
            content.remove(savePanel);

            if (action_step.isSelected()) {
                JPanel astep = getActionPanel(null, null);
                content.add(astep);
            } else {
                JPanel mstep = getMeasurementPanel(null, null);
                content.add(mstep);
            }

            action_step.setSelected(false);
            meas_step.setSelected(false);
            content.add(new_step);
            content.add(savePanel);
            reRender();
        }
    }

    private class DeleteAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            content.remove(((JButton)actionEvent.getSource()).getParent());
            reRender();
        }
    }

    private void extractSteps(Component c, int ordinal, int new_test_id){
        if(((JPanel) c).getComponents().length < 4) {
            return;
        } else if (((JPanel) c).getComponents()[3] instanceof JPanel) {
            String uts = null;
            String nm = ((JTextField)((JPanel) c).getComponents()[1]).getText();

            if(nm == null) {
                JOptionPane.showMessageDialog(null, "Give names to all measurement steps");
                failure = true;
            }

            for(Component comp : ((JPanel)((JPanel) c).getComponents()[3]).getComponents()){
                if(((JRadioButton)comp).isSelected()) {
                    uts = ((JRadioButton)comp).getText();
                }
            }
            if (uts == null) {
                JOptionPane.showMessageDialog(null, "Select units for all measurement steps");
                failure = true;
            }

            String query = "insert into measurements(name, units, ordinal) values (\"" + nm
                     + "\", \"" + uts + "\", " + ordinal + ");";
            int success = db_conn.updateDb(query);

            if (success < 0) {
                JOptionPane.showMessageDialog(null, "Issue adding measurement step");
                failure = true;
            }

            // update baseSteps
            try {
                ResultSet new_meas = db_conn.getDbEntries("select * from measurements order by id desc limit 1;");
                new_meas.next();
                query = "insert into baseSteps(test_type, measurement, ordinal) values (" + new_test_id +
                        ", " + new_meas.getInt("id") + ", " + ordinal + ");";
                success = db_conn.updateDb(query);

                if (success < 0) {
                    JOptionPane.showMessageDialog(null, "Issue adding measurement base step");
                    failure = true;
                }

            } catch(SQLException e) {
                e.printStackTrace();
            }

        } else if (((JPanel) c).getComponents()[3] instanceof JFormattedTextField){
            String len = ((JFormattedTextField)((JPanel) c).getComponents()[3]).getText();
            String nm = ((JTextField)((JPanel) c).getComponents()[1]).getText();

            if(nm == null) {
                JOptionPane.showMessageDialog(null, "Give names to all action steps");
                failure = true;
            }

            String q2 = "null";
            if(!len.equals("  :  :  ")) {
                q2 = "time_format(\"" + len + "\", \"%H:%i:%s\")";
            }

            String query = "insert into actions(name, length) values (\"" + nm + "\", " + q2 + ");";
            int success = db_conn.updateDb(query);

            if (success < 0) {
                JOptionPane.showMessageDialog(null, "Issue adding action step");
                failure = true;
            }

            // update baseSteps
            try {
                ResultSet new_act = db_conn.getDbEntries("select * from actions order by id desc limit 1;");
                new_act.next();
                query = "insert into baseSteps(test_type, action, ordinal) values (" + new_test_id +
                        ", " + new_act.getInt("id") + ", " + ordinal + ");";
                success = db_conn.updateDb(query);

                if (success < 0) {
                    JOptionPane.showMessageDialog(null, "Issue adding action base step");
                    failure = true;
                }

            } catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int i = 0;
        int new_test_id = 0;

        try {
            if(!new_type_box.getText().equals("")) {
                String query = "insert into testTypes(service) values(\"" + new_type_box.getText() + "\");";
                int success = db_conn.updateDb(query);

                if(success < 0) {
                    JOptionPane.showMessageDialog(null, "Issue creating test type");
                    failure = true;
                } else {
                    ResultSet new_test = db_conn.getDbEntries("select * from testTypes order by id desc limit 1");
                    new_test.next();
                    new_test_id = new_test.getInt("id");
                }
            } else {
                TType type = ((TType)typeBox.getSelectedItem());
                if(type == null || type.getType() == null) {
                    JOptionPane.showMessageDialog(null, "Choose test type to modify or create new");
                    failure = true;
                }
                String query = "select * from testTypes where id = " + type.getID() + ";";
                ResultSet curr = db_conn.getDbEntries(query);
                curr.next();

                query = "update testTypes set active = 0 where id = " + type.getID() + ";";
                db_conn.updateDb(query);
                query = "insert into testTypes(service, primary_category, sub_category) values (\"" +
                        type.getType() + "\", \"" + curr.getString("primary_category") + "\", \"" +
                        curr.getString("sub_category") + "\");";
                int success = db_conn.updateDb(query);

                if(success < 0) {
                    JOptionPane.showMessageDialog(null, "Issue creating test type");
                    failure = true;
                } else {
                    ResultSet new_test = db_conn.getDbEntries("select * from testTypes order by id desc limit 1");
                    new_test.next();
                    new_test_id = new_test.getInt("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Component c : content.getComponents()) {
            extractSteps(c, i++, new_test_id);
        }

        if(!failure) {
            reset();
            reRender();
        }
    }
}
