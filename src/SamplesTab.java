import javax.swing.*;
import java.awt.*;

public class SamplesTab extends JPanel {
    public SamplesTab(){
        JLabel sample_type = new JLabel("Sample Type");
        JComboBox sample_box = new JComboBox();

        JLabel date_received = new JLabel("Date Received  MM/DD/YYYY");
        JTextField dr_box = dateEntryField();

        JLabel collection_date = new JLabel("Collection Date  MM/DD/YYYY");
        JTextField cd_box = dateEntryField();

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
        constraints.weighty = 0.03;
        constraints.weightx = 0.1;

        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        add(sample_type, constraints);

        constraints.weighty = 0;
        constraints.gridy++;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(sample_box, constraints);

        constraints.gridy++;
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        add(date_received, constraints);

        constraints.gridy++;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(dr_box, constraints);

        constraints.gridy++;
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        add(collection_date, constraints);

        constraints.gridy++;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(cd_box, constraints);

        constraints.weighty = 0.05;
        constraints.anchor = GridBagConstraints.LINE_START;

        constraints.gridy++;
        add(no_inventory, constraints);

        constraints.gridy++;
        add(search, constraints);

        constraints.weighty = 0;
        constraints.weightx = 0.3;
        constraints.gridy = 3;
        constraints.gridx = 1;

        constraints.anchor = GridBagConstraints.LINE_START;

        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        add(today, constraints);
        constraints.gridy = 5;
        add(today2, constraints);

        constraints.gridy = 0;
        constraints.gridx = 2;
        constraints.weightx = 0.5;

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

    public JButton todaysDateButton(){
        // needs code to add today's date
        JButton b = new JButton("Today's Date");
        return b;
    }

    public JTextField dateEntryField(){
        //needs code to check format --> inputverifier/maskformatter + formatted text field
        JTextField f = new JTextField(10);
        return f;
    }

    public static void main(String args[]) {
        JFrame frame = new Frame("Samples");
        JPanel samples_panel = new SamplesTab();
        //frame.setLayout(new GridBagLayout());
        frame.getContentPane().add(samples_panel);
        //frame.pack();
        frame.setVisible(true);
    }
}
