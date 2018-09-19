import javax.swing.*;
import java.awt.*;

public class SamplesTab extends JPanel {
    public SamplesTab(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setPreferredSize(new Dimension(1000, screenSize.height));

        JLabel sampleType = new JLabel("Sample Type");
        JComboBox sampleBox = new JComboBox();

        JLabel dateReceived = new JLabel("Date Received  MM/DD/YYYY");
        JTextField dateBox = dateEntryField();

        JLabel collectionDate = new JLabel("Collection Date  MM/DD/YYYY");
        JTextField cDateBox = dateEntryField();

        JLabel source = new JLabel("Source");
        JComboBox source_box = new JComboBox();

        JLabel contact = new JLabel("Contact");
        JComboBox contact_box = new JComboBox();

        JLabel storage_location = new JLabel("Storage Location");
        JComboBox storage_box = new JComboBox();

        JButton today = todaysDateButton();
        JButton today2 = todaysDateButton();
        JButton search = new JButton("Search");

        JCheckBox no_inventory = new JCheckBox("Include Samples with No Inventory");

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weighty = 0.02;
        constraints.weightx = 0.1;

        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        constraints.insets = new Insets(0, 120, 0, 0);
        add(sampleType, constraints);

        constraints.weighty = 0;
        constraints.gridy++;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.insets = new Insets(0, 120, 50, 0);
        add(sampleBox, constraints);

        constraints.gridy++;
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        constraints.insets = new Insets(0, 120, 0, 0);
        add(dateReceived, constraints);

        constraints.gridy++;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.insets = new Insets(0, 120, 50, 0);
        add(dateBox, constraints);

        constraints.gridy++;
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        constraints.insets = new Insets(0, 120, 0, 0);
        add(collectionDate, constraints);

        constraints.gridy++;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        constraints.insets = new Insets(0, 120, 0, 0);
        add(cDateBox, constraints);

        constraints.weighty = 0.01;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridy++;
        constraints.insets = new Insets(0, 120, 0, 0);
        add(no_inventory, constraints);

        constraints.weighty = 0.02;
        constraints.gridy++;
        constraints.anchor = GridBagConstraints.PAGE_START;
        add(search, constraints);

        constraints.weighty = 0;
        constraints.weightx = 0.1;

        constraints.insets = new Insets(0, 20, 0, 0);
        constraints.gridy = 3;
        add(today, constraints);
        constraints.gridy = 5;
        add(today2, constraints);

        constraints.gridy = 0;
        constraints.gridx = 1;
        constraints.weightx = 0.5;

        constraints.insets = new Insets(0, 0, 0, 0);
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        add(source, constraints);

        constraints.gridy++;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(source_box, constraints);

        constraints.gridy++;
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        add(contact, constraints);

        constraints.gridy++;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(contact_box, constraints);

        constraints.gridy++;
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        add(storage_location, constraints);

        constraints.gridy++;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(storage_box, constraints);


    }

    private JButton todaysDateButton(){
        // needs code to add today's date
        JButton b = new JButton("Today's Date");
        return b;
    }

    private JTextField dateEntryField(){
        //needs code to check format --> inputverifier/maskformatter + formatted text field
        JTextField f = new JTextField(10);
        return f;
    }

    private void addItem(JPanel p, JComponent c, int x, int y, int wx, int wy, int anc, int insets){

    }

    public static void main(String args[]) {
        JFrame frame = new Frame("Samples");
        JPanel samples_panel = new SamplesTab();
        frame.getContentPane().add(samples_panel);
        //frame.pack();
        frame.setVisible(true);
    }
}
