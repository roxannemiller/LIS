import javax.swing.*;
import java.awt.*;

public class NewSample extends TabsBasePanel{
    public NewSample() {
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

        JComboBox sampleBox = new JComboBox();
        addItem(p, sampleBox, 0, 1, 0.2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 60, 50, 0);

        JLabel amount = new JLabel("Amount");
        addItem(p, amount, 0, 2, 0.2, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 60, 0, 0);

        JTextField amt = new JTextField(5);
        amt.setPreferredSize(new Dimension(110, 26));
        addItem(p, amt, 0, 3, 0.2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 60, 50, 0);

        JPanel measurements = measurementUnits();
        addItem(p, measurements, 1, 3, 0.1, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, 10, 0, 0);

        JLabel dateReceived = new JLabel("Date Received  MM/DD/YYYY");
        addItem(p, dateReceived, 0, 4, 0.2, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 60, 0, 0);

        JTextField dateBox = dateEntryField();
        dateBox.setPreferredSize(new Dimension(110, 26));
        addItem(p, dateBox, 0, 5, 0.2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 60, 50, 0);

        JLabel collectionDate = new JLabel("Collection Date  MM/DD/YYYY");
        addItem(p, collectionDate, 0, 6, 0.2, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 60, 0, 0);

        JTextField cDateBox = dateEntryField();
        cDateBox.setPreferredSize(new Dimension(110, 26));
        addItem(p, cDateBox, 0, 7, 0.2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 60, 60, 0);

        JButton addSample = new JButton("Add Sample");
        addItem(p, addSample, 0, 8, 0.2, 0.025, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, 60, 0, 0);

        JButton today = todaysDateButton();
        addItem(p, today, 1, 5, 0.1, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, 10, 0, 0);

        JButton today2 = todaysDateButton();
        addItem(p, today2, 1, 7, 0.1, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, 10, 0, 0);

        JLabel source = new JLabel("Source");
        addItem(p, source, 2, 0, 0.2, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 10, 0, 60);

        JComboBox sourceBox = new JComboBox();
        addItem(p, sourceBox, 2, 1, 0.2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 10, 0, 60);

        JLabel contact = new JLabel("Contact");
        addItem(p, contact, 2, 2, 0.2, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 10, 0, 60);

        JComboBox contactBox = new JComboBox();
        addItem(p, contactBox, 2, 3, 0.2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 10, 0, 60);

        JLabel storageLocation = new JLabel("Storage Location");
        addItem(p, storageLocation, 2, 4, 0.2, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 10, 0, 60);

        JComboBox storageBox = new JComboBox();
        addItem(p, storageBox, 2, 5, 0.2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 10, 0, 60);

        JLabel additionalInfo = new JLabel("Additional Description");
        addItem(p, additionalInfo, 2, 6, 0.2, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 10, 0, 60);

        JTextField info = new JTextField(20);
        info.setPreferredSize(new Dimension(60, 26));
        addItem(p, info, 2, 7, 0.2, 0, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 10, 50, 60);

        return p;
    }

    private JButton todaysDateButton() {
        // needs code to add today's date
        JButton b = new JButton("Today's Date");
        return b;
    }

    private JTextField dateEntryField() {
        //needs code to check format --> inputverifier/maskformatter + formatted text field
        JTextField f = new JTextField(10);
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
        JRadioButton[] buttons = {new JRadioButton("mg"), new JRadioButton("g"), new JRadioButton("mL"), new JRadioButton("L")};

        for (JRadioButton b : buttons){
            p.add(b);
        }

        return p;
    }
}