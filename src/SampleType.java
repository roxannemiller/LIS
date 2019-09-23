import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

abstract class SampleType extends JPanel implements ActionListener {
    private static final int IS_NEW = 1;
    private int type;
    private DBWrapper db_conn;
    private JComponent sampleBox;
    private JButton saveButton;
    private ArrayList<JTextField> subtypes;
    private Component spacing;

    public SampleType(DBWrapper db_conn, int type){
        this.db_conn = db_conn;
        this.type = type;
        buildSelf();
    }

    private void buildSelf(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(90, 165, 10, 10));
        subtypes = new ArrayList<>();

        JLabel sampleType = new JLabel("Sample Type");
        sampleType.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(sampleType);

        sampleBox = getSampleTypeBox(db_conn);
        sampleBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        sampleBox.setMaximumSize(new Dimension(300, 26));
        add(sampleBox);

        add(SubtypePanel());

        JLabel additionalSubtype = new JLabel("Add Another Subtype");
        additionalSubtype.setAlignmentX(Component.LEFT_ALIGNMENT);
        additionalSubtype.addMouseListener(new AdditSubtype());
        add(additionalSubtype);

        spacing = Box.createRigidArea(new Dimension(0, 60));
        add(spacing);
        saveButton = new JButton("Save Changes");
        saveButton.addActionListener(this);
        add(saveButton);
    }

    private JPanel SubtypePanel(){
        JPanel base_panel = new JPanel();
        base_panel.setLayout(new BoxLayout(base_panel, BoxLayout.PAGE_AXIS));

        JLabel subtype = new JLabel("Sub-type");
        subtype.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtype.setBorder(new EmptyBorder(50, 0, 0, 0));
        base_panel.add(subtype);

        JTextField subtypeBox = new JTextField();
        subtypeBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtypeBox.setMaximumSize(new Dimension(300, 26));
        base_panel.add(subtypeBox);

        subtypes.add(subtypeBox);
        return base_panel;
    }

    private class AdditSubtype extends MouseAdapter{
        public void mouseClicked(MouseEvent e) {
            if (subtypes.size() < 4) {
                remove(spacing);
                remove(saveButton);
                add(SubtypePanel());

                JLabel additionalSubtype = new JLabel("Add Another Subtype");
                additionalSubtype.setAlignmentX(Component.LEFT_ALIGNMENT);
                additionalSubtype.addMouseListener(new AdditSubtype());
                add(additionalSubtype);

                add(spacing);
                add(saveButton);
                revalidate();
                repaint();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String super_type;

        if(type == IS_NEW){
            super_type = ((JTextField)sampleBox).getText();
        }
        else{
            super_type = ((JComboBox)sampleBox).getSelectedItem() == null ? "" : ((JComboBox)sampleBox).getSelectedItem().toString();
        }

        for(JTextField stype : subtypes){
            if(!stype.getText().isEmpty()){
                addNewSampleType(stype.getText(), super_type);
            }
        }

        resetBoxes();
    }

    private void addNewSampleType(String sub_type, String super_type){
        if(super_type.isEmpty()){
            JOptionPane.showMessageDialog(null, "Type field required");
            return;
        }

        String query = "insert into sampleTypes (type, subtype) values (\"" + super_type + "\", \"" +
                sub_type + "\");";
        int success = db_conn.updateDb(query);

        if(success < 1){
            JOptionPane.showMessageDialog(null, "Type and Subtype must be a unique combination");
        }
        else{
            JOptionPane.showMessageDialog(null, "Sample type added.");
        }
    }

    private void resetBoxes(){
        for(JTextField stype : subtypes){
            stype.setText("");
        }

        if(type == IS_NEW){
            ((JTextField)sampleBox).setText("");
        }
        else{
            ((JComboBox)sampleBox).setSelectedIndex(0);
        }
    }

    abstract protected JComponent getSampleTypeBox(DBWrapper db_conn);
}
