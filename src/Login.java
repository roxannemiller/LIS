import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Login extends JPanel {
    public Login() {
        setLayout(new GridBagLayout());
        JPanel login_panel = LoginUI();
        add(login_panel);
    }

    //sets the background of the screen to a specific image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image i = requestImage("images/loginbkg.png");
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

    private JPanel LoginUI(){
        JPanel loginUI = new JPanel();

        GroupLayout layout = new GroupLayout(loginUI);
        loginUI.setLayout(layout);
        loginUI.setPreferredSize(new Dimension(600, 390));
        loginUI.setBackground(Color.white);
        loginUI.setBorder(BorderFactory.createLineBorder(Color.black));

        JLabel title = new JLabel("Sign In");
        title.setFont(new Font("Dialog", Font.BOLD, 38));

        JLabel username_label = new JLabel("Username");
        username_label.setFont(new Font("Dialog", Font.PLAIN, 16));

        JLabel password_label = new JLabel("Password");
        password_label.setFont(new Font("Dialog", Font.PLAIN, 16));

        JLabel forgot_password = new JLabel("Forgot Password?");
        forgot_password.setFont(new Font("Dialog", Font.PLAIN, 10));

        JTextField username = new JTextField(20);
        JPasswordField password = new JPasswordField(15);

        JButton sign_in = new JButton("Sign In");

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sign_in)
                                .addGap(80, 80, 80))
                        .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addContainerGap(70, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(5, 135, 135)
                                                .addComponent(title))
                                        .addComponent(username_label)
                                        .addComponent(username)
                                        .addComponent(password_label)
                                        .addComponent(password)
                                        .addComponent(forgot_password))
                                .addContainerGap(150, Short.MAX_VALUE))
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addContainerGap(40, Short.MAX_VALUE)
                        .addComponent(title)
                        .addGap(50, 50, 50)
                        .addComponent(username_label)
                        .addComponent(username)
                        .addGap(35, 35, 35)
                        .addComponent(password_label)
                        .addComponent(password)
                        .addComponent(forgot_password)
                        .addGap(40, 40, 40)
                        .addComponent(sign_in)
                        .addGap(40, 40, 40)
        );

        return loginUI;
    }

    public static void main(String args[]) {
        JPanel login = new Login();
        JFrame frame = new Frame("LIS Sign In");
        frame.getContentPane().add(login);
        //frame.pack();
        frame.setVisible(true);
    }

}

