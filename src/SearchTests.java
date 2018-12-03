import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SearchTests extends SearchLayout{
    private JScrollPane testResults;

    public SearchTests(){
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        JPanel testUI = SearchTestsUI();
        testResults =  createScrollableList("Results", 500);
        testUI.setBorder(BorderFactory.createTitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));

        setSearchLayout(layout, testUI, testResults);
    }

    private JPanel SearchTestsUI(){
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(1000, 100));

        p.setLayout(new GridBagLayout());

        JLabel testType = new JLabel("Test Type");
        addItem(p, testType, 0, 0, 0.3, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 60, 0, 0);
        JComboBox testBox = ComboBoxes.testTypeBox();
        addItem(p, testBox, 0, 1, 0.3, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 60, 0, 0);

        JLabel sampleType = new JLabel("Sample Type Used");
        addItem(p, sampleType, 0, 2, 0.3, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 60, 0, 0);
        JComboBox sampleBox = ComboBoxes.sampleTypeBox();
        addItem(p, sampleBox, 0, 3, 0.3, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 60, 0, 0);

        JLabel testStatus = new JLabel("Test Status");
        addItem(p, testStatus, 1, 0, 0.5, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 40, 0, 0);
        JComboBox statusBox = testStatusBox();
        addItem(p, statusBox, 1, 1, 0.5, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 10, 0);

        JLabel subject = new JLabel("Subject");
        addItem(p, subject, 1, 2, 0.5, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 0, 0);
        JComboBox subjectBox = ComboBoxes.sourceBox();
        addItem(p, subjectBox, 1, 3, 0.5, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 0, 0);

        JLabel assocProject = new JLabel("Associated Project");
        addItem(p, assocProject, 2, 0, 0.4, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 40, 0, 40);
        JComboBox projBox = ComboBoxes.projectBox();
        addItem(p, projBox, 2, 1, 0.4, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 0, 40);

        JLabel tech = new JLabel("Technician");
        addItem(p, tech, 2, 2, 0.4, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 40, 0, 40);
        JComboBox techBox = ComboBoxes.technicianBox();
        addItem(p, techBox, 2, 3, 0.4, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 0, 40);

        JLabel sort = new JLabel("Sort By");
        addItem(p, sort, 3, 2, 0.2, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 0, 0, 0);

        JComboBox sortBox = sortByOptions();
        addItem(p, sortBox, 3, 3, 0.2, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 0, 0, 0);

        JLabel testDate = new JLabel("Test Date MM/DD/YYYY");
        addItem(p, testDate, 3, 0, 0.05, 0, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 0, 0, 0);

        JTextField dateBox = dateEntryField();
        dateBox.setPreferredSize(new Dimension(110, 26));
        addItem(p, dateBox, 3, 1, 0.05, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 0, 0, 0);

        Action dateAction = new TodaysDateAction("Today's Date", dateBox);
        JButton today = new JButton(dateAction);
        addItem(p, today, 4, 1, 0.05, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.NONE, 10, 0, 40);

        JButton search = new JButton("Search");
        addItem(p, search, 4, 3, 0.02, 0.05, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, 20, 0, 40);

        return p;
    }

    private JTextField dateEntryField(){
        //needs code to check format --> inputverifier/maskformatter + formatted text field
        JTextField f = new JTextField(10);
        return f;
    }

    private JComboBox testStatusBox(){
        String[] defaultTypes = {"", "In-Progress", "Completed", "Failed"};

        return new JComboBox<>(defaultTypes);
    }

    private JComboBox sortByOptions(){
        String[] defaultTypes = {"", "Sample Type", "Test Type", "Test Status", "Subject", "Associated Project",
                "Technician", "Date - Most Recent", "Date - Least Recent"};

        return new JComboBox<>(defaultTypes);
    }
}
