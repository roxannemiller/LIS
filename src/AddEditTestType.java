import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEditTestType extends JPanel implements ActionListener {
    private static final int IS_NEW = 1;
    private DBWrapper db_conn;
    private JTextField new_category;
    private JComboBox category_box;
    private JTextField new_sub;
    private JComboBox sub_box;
    private JComboBox typeBox;
    private JCheckBox inactive;


    public AddEditTestType(DBWrapper db_conn){
        this.db_conn = db_conn;
        this.new_category= new JTextField();
        this.category_box = new JComboBox();
        this.new_sub = new JTextField();
        this.sub_box = new JComboBox();
        this.inactive = new JCheckBox("Inactivate");

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(90, 165, 10, 10));

        buildSelf();
    }

    private void buildSelf(){
        addJLabel("Test Type", 0);

        typeBox = ComboBoxes.getTestTypeBox(db_conn);
        typeBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        typeBox.setMaximumSize(new Dimension(500, 28));
        add(typeBox);

        addJLabel("Category", 50);
        category_box.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                ComboBoxes.fillCategoryBox(db_conn, category_box, "primary_category");
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });

        JPanel cat_panel = multipleControlPanel("Choose Category", "New Category", new_category, category_box);
        cat_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        cat_panel.setMaximumSize(new Dimension(500, 55));
        add(cat_panel);

        addJLabel("Sub-Category", 50);
        sub_box.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                ComboBoxes.fillCategoryBox(db_conn, sub_box, "sub_category");
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });

        JPanel sub_panel = multipleControlPanel("Choose Sub-Category", "New Sub-Category", new_sub, sub_box);
        sub_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sub_panel.setMaximumSize(new Dimension(500, 55));
        add(sub_panel);

        add(Box.createRigidArea(new Dimension(0, 60)));
        add(inactive);

        add(Box.createRigidArea(new Dimension(0, 60)));
        addSaveButton();
    }

    private void addJLabel(String label_text, int top_spacing){
        JLabel label = new JLabel(label_text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(top_spacing, 0, 0, 0));
        add(label);
    }

    private JPanel multipleControlPanel(String b1, String b2, JTextField tf, JComboBox cb){
        CardLayout base_layout = new CardLayout();
        JPanel base_panel = new JPanel(base_layout);

        JPanel choose_affil = new JPanel();
        choose_affil.setLayout(new BoxLayout(choose_affil, BoxLayout.PAGE_AXIS));

        cb.setAlignmentX(Component.LEFT_ALIGNMENT);
        cb.setMaximumSize(new Dimension(500, 26));
        choose_affil.add(cb);

        JButton to_add = new JButton(new ButtonAction(b2, base_layout, base_panel));
        to_add.setBorderPainted(false);
        choose_affil.add(to_add);

        JPanel new_affil = new JPanel();
        new_affil.setLayout(new BoxLayout(new_affil, BoxLayout.PAGE_AXIS));

        tf.setAlignmentX(Component.LEFT_ALIGNMENT);
        tf.setMaximumSize(new Dimension(500, 26));
        new_affil.add(tf);

        JButton to_choose = new JButton(new ButtonAction(b1, base_layout, base_panel));
        to_choose.setBorderPainted(false);
        new_affil.add(to_choose);

        base_panel.add(b1, choose_affil);
        base_panel.add(b2, new_affil);

        return base_panel;
    }

    private void addSaveButton(){
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(this);
        add(saveButton);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String test_type = "";
        String cat = "";
        String sub = "";

        test_type = typeBox.getSelectedItem() == null ? "" : typeBox.getSelectedItem().toString();
        if(test_type.equals("")){
            JOptionPane.showMessageDialog(null, "No test type selected");
        }

        if(new_category.getText().equals("")){
            cat = category_box.getSelectedItem() == null ? "" : category_box.getSelectedItem().toString();
        }
        else {
            cat = new_category.getText();
        }

        if(new_sub.getText().equals("")){
            sub = sub_box.getSelectedItem() == null ? "" : sub_box.getSelectedItem().toString();
        }
        else {
            sub = new_sub.getText();
        }

        updateTestType(test_type, cat, sub);
    }

    private void updateTestType(String type, String cat, String sub){
        boolean inactivate = inactive.isSelected();

        if(cat.equals("") && sub.equals("") && !inactivate){
            JOptionPane.showMessageDialog(null, "No changes made.");
            return;
        }

        String pt2 = cat.equals("") ? "" :  "primary_category = \"" + cat + "\"";
        String pt3 = sub.equals("") ? "" : "sub_category = \"" + sub + "\"";
        String join1 = !cat.equals("") && !sub.equals("") ? ", " : "";
        String pt4 = inactivate ? "active = 0" : "";
        String join2 = inactivate && (!cat.equals("") || !sub.equals("")) ? ", " : "";

        String query = "update testTypes set " + pt2 + join1 + pt3 + join2 + pt4 + " where service = \"" + type + "\";";
        System.out.println(query);

        int success = db_conn.updateDb(query);
        if(success < 1){
            JOptionPane.showMessageDialog(null, "Issue updating service.");
        }
        else{
            JOptionPane.showMessageDialog(null, "Service was updated.");
            resetBoxes();
        }
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

    private void resetBoxes(){
        new_sub.setText("");
        new_category.setText("");
        sub_box.setSelectedIndex(0);
        category_box.setSelectedIndex(0);
        typeBox.setSelectedItem(0);
        inactive.setSelected(false);
    }
}
