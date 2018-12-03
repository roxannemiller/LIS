import javax.swing.*;

public class ComboBoxes {
    public static JComboBox sampleTypeBox(){
        String[] defaultSampleTypes = {"", "Grain", "Tissue", "Pelleted Feed"};
        JComboBox sampleTypeBox = new JComboBox<>(defaultSampleTypes);

        return sampleTypeBox;
    }

    public static JComboBox sourceBox(){
        String[] defaultSourceTypes = {"", "Equine", "Tortoise", "009211", "319504"};
        JComboBox source = new JComboBox<>(defaultSourceTypes);

        return source;
    }

    public static JComboBox contactBox(){
        String[] defaultSourceTypes = {"", "Mark Edwards", "Matt Burd"};
        JComboBox contact = new JComboBox<>(defaultSourceTypes);

        return contact;
    }

    public static JComboBox storageLocBox(){
        String[] defaultSourceTypes = {"", "Cabinet", "Box 5"};
        JComboBox storageLocation = new JComboBox<>(defaultSourceTypes);

        return storageLocation;
    }

    public static JComboBox testTypeBox(){
        String[] defaultSourceTypes = {"", "Partial Drying", "Homogenize", "Pelleting", "Acid Digestion, Microwave", "Ash, Total (organic matter)",
            "Dry Matter (moisture)", "Particle Size, Macro, Dry", "Particle Size, Dry", "Particle Size, Wet", "Acid Insoluble Ash",
            "NDF, Amylase, Refluxing", "ADF, Non-Sequential, Refluxing", "ADF, Sequential, Refluxing",
            "ADL, Sequential, Refluxing", "NDF, Amylase, Filter Bag", "ADF, Non-Sequential, Filter Bag",
            "ADF, Sequential, Filter Bag", "ADL, Sequential, Filter Bag", "Gross Energy", "Digestibility, In-vitro, 3-stage",
            "Digestibility, In-vivo", "Digestibility, In-situ", "Density", "pH"};

        JComboBox testType = new JComboBox<>(defaultSourceTypes);

        return testType;
    }

    public static JComboBox projectBox(){
        String[] defaultSourceTypes = {"", "Allison's Thesis", "Enterprise F2018"};
        JComboBox assocProj = new JComboBox<>(defaultSourceTypes);

        return assocProj;
    }

    public static JComboBox technicianBox(){
        String[] defaultTypes = {"", "Dr. Edwards", "Allison"};

        return new JComboBox<>(defaultTypes);
    }

    public static JComboBox privilegesBox(){
        String[] defaultTypes = {"", "User", "Admin"};

        return new JComboBox<>(defaultTypes);
    }
}
