import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SearchProjects extends SearchLayout{
    private JScrollPane projectResults;

    public SearchProjects(){
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        projectResults =  createScrollableList("Results", 500);
        JPanel samplesUI = SearchProjectsUI();
        samplesUI.setBorder(BorderFactory.createTitledBorder(null, "Filters", TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));

        setSearchLayout(layout, samplesUI, projectResults);
    }

    private JPanel SearchProjectsUI(){
        JPanel p = new JPanel();
        p.setPreferredSize(new Dimension(1000, 100));

        p.setLayout(new GridBagLayout());

        JLabel project = new JLabel("Project Name");
        addItem(p, project, 0, 0, 0.15, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 60, 0, 0);
        JComboBox projBox = new JComboBox();
        addItem(p, projBox, 0, 1, 0.15, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 60, 0, 0);

        JLabel owner = new JLabel("Project Owner");
        addItem(p, owner, 1, 0, 0.15, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 40, 0, 0);
        JComboBox ownerBox = new JComboBox();
        addItem(p, ownerBox, 1, 1, 0.15, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 0, 0);

        JLabel sortBy = new JLabel("Sort By");
        addItem(p, sortBy, 2, 0, 0.2, 0.1, 1, GridBagConstraints.LAST_LINE_START, GridBagConstraints.NONE, 40, 0, 0);
        JComboBox statusBox = new JComboBox();
        addItem(p, statusBox, 2, 1, 0.2, 0.1, 1, GridBagConstraints.FIRST_LINE_START, GridBagConstraints.HORIZONTAL, 40, 10, 0);

        JButton search = new JButton("Search");
        addItem(p, search, 3, 1, 0.05, 0.05, 1, GridBagConstraints.PAGE_START, GridBagConstraints.NONE, 20, 0, 40);

        return p;
    }
}
