import javax.swing.*;

public class EditSampleType extends SampleType{
    private JComboBox sampleBox;

    public EditSampleType() {
        super();
    }

    protected JComponent getSampleTypeBox(){
        sampleBox = new JComboBox();
        return sampleBox;
    }
}

