import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SearchSamples extends TabsBasePanel {
    public SearchSamples(){
        JPanel SearchUI = SearchSampleUI();
        add(SearchUI, BorderLayout.CENTER);
    }

    /*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image i = requestImage("images/pagesbkg.png");
        g.drawImage(i, 0, 0, getWidth(), getHeight(), null);
    }

    private Image requestImage(String imagePath){
        Image image = null;

        try {
            image = ImageIO.read(new File(imagePath));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }*/

    private JPanel SearchSampleUI(){
        JPanel p = new JPanel();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        p.setPreferredSize(new Dimension(1100, screenSize.height));
        p.setMaximumSize(new Dimension(1100, screenSize.height));

        p.setLayout(new GridBagLayout());

        JLabel sampleType = new JLabel("Sample Type");
        addItem(p, sampleType, 0, 0, 0.02, 0.015, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 100, 0, 0);

        JComboBox sampleBox = new JComboBox();
        addItem(p, sampleBox, 0, 1, 0.02, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 100, 70, 0);

        JLabel dateReceived = new JLabel("Date Received  MM/DD/YYYY");
        addItem(p, dateReceived, 0, 2, 0.02, 0, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 100, 0, 0);

        JTextField dateBox = dateEntryField();
        dateBox.setPreferredSize(new Dimension(110, 26));
        addItem(p, dateBox, 0, 3, 0.02, 0,GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 100, 70, 0);

        JLabel collectionDate = new JLabel("Collection Date  MM/DD/YYYY");
        addItem(p, collectionDate, 0, 4, 0.02, 0, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 100, 0, 0);

        JTextField cDateBox = dateEntryField();
        cDateBox.setPreferredSize(new Dimension(110, 26));
        addItem(p, cDateBox, 0, 5, 0.02, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 100, 0, 0);

        JCheckBox noInventory = new JCheckBox("Include Samples with No Inventory");
        addItem(p, noInventory, 0, 6, 0.02, 0.01, GridBagConstraints.LINE_START, GridBagConstraints.NONE, 100, 0, 0);

        JButton search = new JButton("Search");
        addItem(p, search, 0, 7, 0.02, 0.02, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, 100, 0, 0);

        JButton today = todaysDateButton();
        addItem(p, today, 1, 3, 0.3, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, 10, 0, 0);

        JButton today2 = todaysDateButton();
        addItem(p, today2, 1, 5, 0.3, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, 10, 0, 0);

        JLabel source = new JLabel("Source");
        addItem(p, source, 2, 0, 0.5, 0, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 50, 0, 100);

        JComboBox sourceBox = new JComboBox();
        addItem(p, sourceBox, 2, 1, 0.5, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 50, 0, 100);

        JLabel contact = new JLabel("Contact");
        addItem(p, contact, 2, 2, 0.5, 0, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 50, 0, 100);

        JComboBox contactBox = new JComboBox();
        addItem(p, contactBox, 2, 3, 0.5, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 50, 0, 100);

        JLabel storageLocation = new JLabel("Storage Location");
        addItem(p, storageLocation, 2, 4, 0.5, 0, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 50, 0, 100);

        JComboBox storageBox = new JComboBox();
        addItem(p, storageBox, 2, 5, 0.5, 0, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 50, 0, 100);

        return p;
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

    private void addItem(JPanel p, JComponent c, int x, int y, double wx, double wy, int anc, int fill, int left, int bottom, int right){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = fill;
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.weightx = wx;
        constraints.weighty = wy;
        constraints.anchor = anc;
        constraints.insets = new Insets(0, left, bottom, right);
        p.add(c, constraints);
    }

    public static void main(String args[]) {
        JFrame frame = new Frame("Search");
        JPanel samples_panel = new SearchSamples();
        frame.getContentPane().add(samples_panel);
        //frame.pack();
        frame.setVisible(true);
    }
}
