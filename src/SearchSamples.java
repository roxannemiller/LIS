import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SearchSamples extends SearchLayout {
    private JList sampleResults = new JList();

    public SearchSamples(){
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        JScrollPane results =  createScrollableList("Results", 500, sampleResults);
        JPanel samplesUI = SearchSamplesUI();
        samplesUI.setBorder(BorderFactory.createTitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));

        setSearchLayout(layout, samplesUI, results);
    }

    private JPanel SearchSamplesUI(){
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(1000, 100));

        p.setLayout(new GridBagLayout());

        JLabel sampleType = new JLabel("Sample Type");
        addItem(p, sampleType, 0, 0, 0.2, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 80, 0, 0);

        JComboBox sampleBox = ComboBoxes.sampleTypeBox();
        addItem(p, sampleBox, 0, 1, 0.2, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 80, 0, 0);

        JLabel source = new JLabel("Source");
        addItem(p, source, 0, 2, 0.2, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 80, 0, 0);

        JComboBox sourceBox = ComboBoxes.sourceBox();
        addItem(p, sourceBox, 0, 3, 0.2, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 80, 0, 0);

        JLabel sortBy = new JLabel("Sort By");
        addItem(p, sortBy, 0, 4, 0.2, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 80, 0, 0);

        JComboBox sort = sortByOptions();
        addItem(p, sort, 0, 5, 0.2, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 80, 10, 0);

        JLabel contact = new JLabel("Contact");
        addItem(p, contact, 3, 0, 0.1, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 40, 0, 60);

        JComboBox contactBox = ComboBoxes.contactBox();
        addItem(p, contactBox, 3, 1, 0.1, 0.05, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 0, 60);

        JLabel storageLocation = new JLabel("Storage Location");
        addItem(p, storageLocation, 3, 2, 0.1, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 40, 0, 60);

        JComboBox storageBox = ComboBoxes.storageLocBox();
        addItem(p, storageBox, 3, 3, 0.1, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 0, 60);

        JLabel dateReceived = new JLabel("Date Received  MM/DD/YYYY");
        addItem(p, dateReceived, 1, 0, 0.05, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 70, 0, 0);

        JTextField dateBox = dateEntryField();
        dateBox.setPreferredSize(new Dimension(110, 26));
        addItem(p, dateBox, 1, 1, 0.05, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 70, 0, 0);

        JLabel collectionDate = new JLabel("Collection Date  MM/DD/YYYY");
        addItem(p, collectionDate, 1, 2, 0.05, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 70, 0, 0);

        JTextField cDateBox = dateEntryField();
        cDateBox.setPreferredSize(new Dimension(110, 26));
        addItem(p, cDateBox, 1, 3, 0.05, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 70, 0, 0);

        Action rDateAction = new TodaysDateAction("Today's Date", dateBox);
        JButton today = new JButton(rDateAction);
        addItem(p, today, 2, 1, 0.03, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, 10, 0, 0);

        Action cDateAction = new TodaysDateAction("Today's Date", cDateBox);
        JButton today2 = new JButton(cDateAction);
        addItem(p, today2, 2, 3, 0.03, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, 10, 0, 0);

        JCheckBox noInventory = new JCheckBox("Include Samples with No Inventory");
        addItem(p, noInventory, 1, 5, 0.02, 0.05, 2, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, 80, 0, 40);

        JButton search = new JButton("Search");
        addItem(p, search, 3, 5, 0.02, 0.05, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, 80, 0, 90);

        return p;
    }

    private JTextField dateEntryField(){
        //needs code to check format --> inputverifier/maskformatter + formatted text field
        JTextField f = new JTextField(10);
        return f;
    }

    private JComboBox sortByOptions(){
        String[] defaultTypes = {"", "Sample Type", "Source", "Contact", "Storage Location", "Received - Most Recent",
                "Received - Least Recent", "Collected - Most Recent", "Collected - Least Recent"};

        return new JComboBox<>(defaultTypes);
    }

    public JList getSearchSamplesResults(){
        return sampleResults;
    }
}
