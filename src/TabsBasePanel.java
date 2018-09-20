import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TabsBasePanel extends JPanel{
    public TabsBasePanel(){
        super();
    }

    public TabsBasePanel(LayoutManager layout){
        super (layout);
    }

    @Override
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
    }
}
