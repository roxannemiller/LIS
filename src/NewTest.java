import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class NewTest extends TabsBasePanel {
    public NewTest(){
        add(newTestUI(), BorderLayout.CENTER);
    }

    private JPanel newTestUI(){
        JPanel p = new JPanel();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        p.setPreferredSize(new Dimension(1000, screenSize.height));

        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.setBorder(new EmptyBorder(180, 165, 450, 370));

        addJLabel(p, "Test Type");

        JComboBox testType = new JComboBox();
        testType.setAlignmentX(Component.LEFT_ALIGNMENT);
        testType.setMaximumSize(new Dimension(300, 26));
        p.add(testType);

        p.add(Box.createRigidArea(new Dimension(0, 10)));
        addJLabel(p, "Sample Accession Number");

        JTextField sample = new JTextField();
        sample.setAlignmentX(Component.LEFT_ALIGNMENT);
        sample.setMaximumSize(new Dimension(300, 26));
        p.add(sample);

        JPanel getSamp = getSample();
        getSamp.setAlignmentX(Component.LEFT_ALIGNMENT);
        getSamp.setBorder(new EmptyBorder(25, 0, 20, 185));
        p.add(getSamp);

        p.add(Box.createRigidArea(new Dimension(0, 5)));
        JPanel sampAmt = sampleAmount();
        sampAmt.setAlignmentX(Component.LEFT_ALIGNMENT);
        sampAmt.setBorder(new EmptyBorder(10, 0, 10, 0));
        p.add(sampAmt);

        p.add(Box.createRigidArea(new Dimension(0, 20)));
        JButton create = new JButton("Create Test");
        p.add(create);

        return p;
    }

    private void addJLabel(JPanel p, String label_text){
        JLabel label = new JLabel(label_text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        label.setBorder(new EmptyBorder(50, 0, 0, 0));
        p.add(label);
    }

    private JPanel getSample() {
        JPanel p = new JPanel(new GridLayout(1, 0, 7, 0));
        p.setMaximumSize(new Dimension(600, 73));
        JButton search = new JButton("Search Samples");
        JButton create = new JButton("New Sample");

        search.setFont(new Font("Sans Serif", Font.BOLD, 11));
        create.setFont(new Font("Sans Serif", Font.BOLD, 11));

        p.add(search);
        p.add(create);

        return p;
    }

    private JPanel sampleAmount() {
        int i = 1;
        JPanel p = new JPanel();
        p.setMaximumSize(new Dimension(600, 100));
        p.setLayout(new GridBagLayout());

        JRadioButton[] buttons = {new JRadioButton("mg"), new JRadioButton("g"), new JRadioButton("mL"), new JRadioButton("L")};

        JLabel sampAmt = new JLabel("Sample Amount");
        addItem(p, sampAmt, 0, 0, 0, GridBagConstraints.HORIZONTAL, 0);

        JTextField amountBox = new JTextField();
        sampAmt.setMaximumSize(new Dimension(300, 26));
        addItem(p, amountBox, 0, 1, 0.1, GridBagConstraints.BOTH, 4);

        for (JRadioButton b : buttons) {
            addItem(p, b, i++, 1, 0, GridBagConstraints.HORIZONTAL, 0);
        }

        return p;
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
}
