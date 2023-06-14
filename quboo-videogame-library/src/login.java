import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import database.*;


public class login {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame LOGIN = new LoginFrame();
            LOGIN.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            LOGIN.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    MainD.desconecta();
                    System.exit(0);
                }
            });
            LOGIN.setVisible(true);
            LOGIN.setTitle("Login");
            new MainD();
        });
    }
}

class LoginFrame extends JFrame{
    public LoginFrame() {
        setTitle("Login");
        setSize(474, 664);
        setBackground(new Color(0xeeeeee));
        setResizable(false);
        setLocationRelativeTo(null);

        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(0xeeeeee));

        JLabel username = new JLabel("Username:");
        username.setBounds(75, 160, 100, 25);
        username.setFont(new Font("Roboto", Font.BOLD, 16));
        contentPane.add(username);

        ImageIcon logo = new ImageIcon("img/Quboo.png");
        ImageIcon l1 = new ImageIcon("img/fondolabel.png");
        ImageIcon l2 = new ImageIcon("img/fondologin.png");
        ImageIcon l3 = new ImageIcon("img/fondope.png");

        JLabel imagel = new JLabel();
        imagel.setBounds(85, 10, 324, 150);
        imagel.setIcon(logo);
        contentPane.add(imagel);

        JLabel imagef = new JLabel();
        imagef.setBounds(50, 165, 374, 100);
        imagef.setIcon(l1);
        contentPane.add(imagef);

        JTextField usuarioField = new JTextField(20);
        usuarioField.setBounds(75, 190, 324, 50);
        usuarioField.setFont(new Font("Roboto", Font.BOLD, 16));
        usuarioField.setHorizontalAlignment(JTextField.CENTER);
        usuarioField.setBorder(BorderFactory.createLineBorder(Color.decode("0xffffff")));
        contentPane.add(usuarioField);

        JLabel password = new JLabel("Password:");
        password.setBounds(75, 260, 100, 25);
        password.setFont(new Font("Roboto", Font.BOLD, 16));
        contentPane.add(password);

        JLabel imagep = new JLabel();
        imagep.setBounds(50, 265, 374, 100);
        imagep.setIcon(l1);
        contentPane.add(imagep);

        JLabel password2 = new JLabel("Do you want to register?");
        password2.setBounds(20, 585, 200, 25);
        contentPane.add(password2);

        JPasswordField contrasenaField = new JPasswordField(20);
        contrasenaField.setBounds(75, 290, 324, 50);
        contrasenaField.setFont(new Font("Roboto", Font.BOLD, 16));
        contrasenaField.setHorizontalAlignment(JPasswordField.CENTER);
        contrasenaField.setBorder(BorderFactory.createLineBorder(Color.decode("0xffffff")));
        contentPane.add(contrasenaField);

        JLabel imagelg = new JLabel();
        imagelg.setBounds(102, 355, 270, 120);
        imagelg.setIcon(l2);
        contentPane.add(imagelg);

        JButton loginButton = new JButton("Log In");
        loginButton.setForeground(new Color(0xffffff));
        loginButton.setFont(new Font("Roboto", Font.PLAIN, 35));
        loginButton.setBounds(127, 380, 220, 70);
        contentPane.add(loginButton);
        loginButton.setBackground(new Color(0x242c3c));
        loginButton.setFocusable(false);
        loginButton.setBorder(BorderFactory.createLineBorder(Color.decode("0x242c3c")));

        JLabel imagerg = new JLabel();
        imagerg.setBounds(185, 550, 166, 90);
        imagerg.setIcon(l3);
        contentPane.add(imagerg);

        JButton registerButton = new JButton("Register");
        registerButton.setForeground(new Color(0xffffff));
        registerButton.setBounds(210, 575, 116, 40);
        registerButton.setFont(new Font("Roboto", Font.PLAIN, 18));
        contentPane.add(registerButton);
        registerButton.setFocusable(false);
        registerButton.setBackground(new Color(0x55A88A));
        registerButton.setBorder(BorderFactory.createLineBorder(Color.decode("0x55A88A")));

        usuarioField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    loginButton.doClick();
                }
            }
        });
        contrasenaField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    loginButton.doClick();
                }
            }
        });
        loginButton.addActionListener(e -> {
            String usuario = usuarioField.getText();
            String contrasena = new String(contrasenaField.getPassword());
            String SHA256 = Register.encrypt(contrasena);
            if (usuario.isEmpty() || contrasena.isEmpty()) {
                JOptionPane.showMessageDialog(LoginFrame.this, "Please, complete all the fields.", "Error", JOptionPane.WARNING_MESSAGE);
            } else if (!query.ComprobarUsuarioContrasena(usuario,SHA256)){
                if (!query.ComprobarUsuario(usuario)){
                    JOptionPane.showMessageDialog(LoginFrame.this, "The username is not correct.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!query.ComprobarContrasena(SHA256)){
                    JOptionPane.showMessageDialog(LoginFrame.this, "The password is not correct.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "This user does not exist... Sign in if you want.", "Error", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                UsuarioActual.getInstance().setUsuario(usuario);
                this.dispose();
                MainFrame main = new MainFrame();
                main.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                main.setVisible(true);
            }
        });
        registerButton.addActionListener(e -> {
            this.dispose();
            RegisterFrame register = new RegisterFrame();
            register.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            register.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    MainD.desconecta();
                    System.exit(0);
                }
            });
            register.setVisible(true);
        });
    }
}