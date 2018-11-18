import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

abstract class SampleType extends JPanel {
    public SampleType(){
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(90, 165, 10, 10));

        JLabel sampleType = new JLabel("Sample Type");
        sampleType.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(sampleType);

        JComponent sampleBox = getSampleTypeBox();
        sampleBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        sampleBox.setMaximumSize(new Dimension(300, 26));
        add(sampleBox);

        JLabel subtype = new JLabel("Sub-type");
        subtype.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtype.setBorder(new EmptyBorder(50, 0, 0, 0));
        add(subtype);

        JTextField subtypeBox = new JTextField();
        subtypeBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        subtypeBox.setMaximumSize(new Dimension(300, 26));
        add(subtypeBox);

        JLabel nestedSubtype = new JLabel("Add Nested Subtype");
        nestedSubtype.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(nestedSubtype);

        JLabel additionalSubtype = new JLabel("Add Another Subtype");
        additionalSubtype.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(additionalSubtype);
    }

    abstract protected JComponent getSampleTypeBox();
}
