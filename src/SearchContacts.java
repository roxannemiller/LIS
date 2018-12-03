import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SearchContacts extends SearchLayout {
    private JScrollPane contactResults;

    public SearchContacts(){
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        contactResults =  createScrollableList("Results", 500);
        JPanel samplesUI = SearchContactsUI();
        samplesUI.setBorder(BorderFactory.createTitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));

        setSearchLayout(layout, samplesUI, contactResults);
    }

    private JPanel SearchContactsUI(){
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(1000, 100));

        p.setLayout(new GridBagLayout());

        JLabel contact = new JLabel("Contact Name");
        addItem(p, contact, 0, 0, 0.15, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 60, 0, 0);
        JComboBox contactBox = ComboBoxes.contactBox();
        addItem(p, contactBox, 0, 1, 0.15, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 60, 0, 0);

        JLabel affiliation = new JLabel("Contact Affiliation");
        addItem(p, affiliation, 1, 0, 0.15, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 40, 0, 0);
        JComboBox affiliationBox = cAffiliationBox();
        addItem(p, affiliationBox, 1, 1, 0.15, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 0, 0);

        JLabel sortBy = new JLabel("Sort By");
        addItem(p, sortBy, 2, 0, 0.2, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 40, 0, 0);
        JComboBox statusBox = sortByOptions();
        addItem(p, statusBox, 2, 1, 0.2, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 10, 0);

        JButton search = new JButton("Search");
        addItem(p, search, 3, 1, 0.05, 0.05, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, 20, 0, 40);

        return p;
    }

    private JComboBox cAffiliationBox(){
        String[] defaultTypes = {"", "Cal Poly"};

        return new JComboBox<>(defaultTypes);
    }

    private JComboBox sortByOptions(){
        String[] defaultTypes = {"", "Contact Name", "Contact Affiliation"};

        return new JComboBox<>(defaultTypes);
    }

}
