import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

abstract class SearchLayout extends TabsBasePanel{
    public void setSearchLayout(GroupLayout layout, JPanel searchUI, JScrollPane results){
        //layout.setAutoCreateGaps(true);
        //layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGap(5, 100, 1000)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, true)
                                .addComponent(searchUI)
                                .addComponent(results))
                        .addContainerGap(100, Short.MAX_VALUE)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(0, 0, 1000)
                        .addComponent(searchUI)
                        .addComponent(results)
                        .addContainerGap(0, Short.MAX_VALUE)

        );
    }

    protected JScrollPane createScrollableList(String title, int height, JList content){
        JScrollPane pane = new JScrollPane();
        pane.setViewportView(content);
        pane.setPreferredSize(new Dimension(1000, height));
        pane.setBorder(BorderFactory.createTitledBorder(null, title, TitledBorder.LEADING, TitledBorder.DEFAULT_POSITION));

        return pane;
    }

    protected void addItem(JPanel p, JComponent c, int x, int y, double wx, double wy, int gw, int anc, int fill, int left, int bottom, int right){
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
}
