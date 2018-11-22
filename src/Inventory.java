import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Inventory extends TabsBasePanel {
    public Inventory(){
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        JScrollPane samples = createScrollableList("Samples", 650);
        JPanel filters = createFilterPanel();

        setInventoryLayout(layout, filters, samples);
    }

    private JScrollPane createScrollableList(String title, int width){
        JList scrollable_list = new JList();
        JScrollPane pane = new JScrollPane();
        pane.setViewportView(scrollable_list);
        pane.setPreferredSize(new Dimension(width, 600));
        pane.setBorder(BorderFactory.createTitledBorder(null, title, TitledBorder.LEADING, TitledBorder.ABOVE_TOP));

        return pane;
    }

    private JPanel createFilterPanel(){
        JPanel p = new JPanel();
        p.setLayout(new GridBagLayout());
        p.setPreferredSize(new Dimension(550, 600));
        p.setBorder(BorderFactory.createTitledBorder(null, "Filter By:", TitledBorder.LEADING, TitledBorder.ABOVE_TOP));

        JRadioButton received = new JRadioButton("Date Received");
        addItem(p, received, 0, 0, 0, 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 0, 20, 0);

        JRadioButton receivedRecentFirst = new JRadioButton("Most Recent First");
        addItem(p, receivedRecentFirst, 0, 1, 0, 0, 1, GridBagConstraints.FIRST_LINE_END, 0, 45, 30, 30);

        JRadioButton receivedRecentLast = new JRadioButton("Most Recent Last");
        addItem(p, receivedRecentLast, 1, 1, 0, 0, 1, GridBagConstraints.PAGE_START, 0, 0, 30, 60);

        JRadioButton collection = new JRadioButton("Collection Date");
        addItem(p, collection, 0, 2, 0, 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 0, 20, 0);

        JRadioButton collectRecentFirst = new JRadioButton("Most Recent First");
        addItem(p, collectRecentFirst, 0, 3, 0, 0, 1, GridBagConstraints.FIRST_LINE_END, 0, 45, 30, 30);

        JRadioButton collectRecentLast = new JRadioButton("Most Recent Last");
        addItem(p, collectRecentLast, 1, 3, 0, 0, 1, GridBagConstraints.PAGE_START, 0, 0, 30, 50);

        JRadioButton accessionNum = new JRadioButton("Accession Number");
        addItem(p, accessionNum, 0, 4, 0, 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 0, 30, 0);

        JRadioButton storageLoc = new JRadioButton("Storage Location");
        addItem(p, storageLoc, 0, 5, 0, 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 0, 30, 0);

        JRadioButton contact = new JRadioButton("Contact");
        addItem(p, contact, 0, 6, 0, 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 0, 30, 0);

        JRadioButton source = new JRadioButton("Source");
        addItem(p, source, 0, 7, 0, 0, 1, GridBagConstraints.FIRST_LINE_START, 0, 0, 95, 0);

        return p;
    }

    private void addItem(JPanel p, JComponent c, int x, int y, double wy, double wx, int gw, int anc, int top, int left, int bottom, int right){
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = gw;
        constraints.weightx = wx;
        constraints.weighty = wy;
        constraints.anchor = anc;
        constraints.insets = new Insets(top, left, bottom, right);
        p.add(c, constraints);
    }

    private void setInventoryLayout(GroupLayout layout, JPanel filters, JScrollPane samples){
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGap(5, 100, 1000)
                        .addComponent(filters)
                        .addGap(5, 100, 1000)
                        .addComponent(samples)
                        .addContainerGap(100, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(5, 90, 1000)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, true)
                                .addComponent(filters)
                                .addComponent(samples))
                        .addContainerGap(90, Short.MAX_VALUE)

        );
    }
}
