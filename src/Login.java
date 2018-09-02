import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    public Login() {
        setSize(1500, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        JPanel login_panel = new JPanel();
        GroupLayout login_layout = new GroupLayout(login_panel);

        login_panel.setLayout(login_layout);
        login_panel.setPreferredSize(new Dimension(600, 390));
        login_panel.setBorder(BorderFactory.createLineBorder(Color.black));

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

        login_layout.setAutoCreateGaps(true);
        login_layout.setAutoCreateContainerGaps(true);

        login_layout.setHorizontalGroup(
                login_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, login_layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(sign_in)
                                .addGap(80, 80, 80))
                        .addGroup(GroupLayout.Alignment.LEADING, login_layout.createSequentialGroup()
                                .addGap(70, 70, 70)
                                .addGroup(login_layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(login_layout.createSequentialGroup()
                                                .addGap(162, 162, 162)
                                                .addComponent(title))
                                        .addComponent(username_label)
                                        .addComponent(username, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(password_label)
                                        .addComponent(password, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(forgot_password))
                                .addContainerGap(150, Short.MAX_VALUE))
        );

        login_layout.setVerticalGroup(
                login_layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(title)
                        .addGap(50, 50, 50)
                        .addComponent(username_label)
                        .addComponent(username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(password_label)
                        .addComponent(password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(forgot_password)
                        .addGap(45, 45, 45)
                        .addComponent(sign_in)
        );

        getContentPane().add(login_panel);
    }

    public static void main(String args[]) {
        JFrame frame = new Login();
        //frame.pack();
        frame.setVisible(true);
    }

}

