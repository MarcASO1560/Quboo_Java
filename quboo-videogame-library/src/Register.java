import database.MainD;
import database.query;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
public class Register {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame REGISTER = new RegisterFrame();
            REGISTER.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            REGISTER.setVisible(true);
        });
    }
    public static String encrypt(String contrasena) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] message = md.digest(contrasena.getBytes());
            BigInteger bigInt = new BigInteger(1, message);
            return bigInt.toString(16);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("ERROR: " + e);
            return "";
        }

    }
}
class RegisterFrame extends JFrame {
    public RegisterFrame() {
        setTitle("Register");
        setSize(474, 664);
        setBackground(new Color(0xeeeeee));
        setResizable(false);
        setLocationRelativeTo(null);

        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(0xeeeeee));

        JLabel username = new JLabel("Set a username:");
        username.setBounds(75, 160, 200, 25);
        username.setFont(new Font("Roboto",Font.BOLD,16));
        contentPane.add(username);

        ImageIcon logo = new ImageIcon("img/Quboo.png");
        ImageIcon l1 = new ImageIcon("img/fondolabel.png");
        ImageIcon l2 = new ImageIcon("img/fondologin.png");
        ImageIcon l3 = new ImageIcon("img/fondope.png");

        JLabel imagel = new JLabel();
        imagel.setBounds(85,10,324,150);
        imagel.setIcon(logo);
        contentPane.add(imagel);

        JLabel imagef = new JLabel();
        imagef.setBounds(50,165,374,100);
        imagef.setIcon(l1);
        contentPane.add(imagef);

        JTextField usuarioField = new JTextField(20);
        usuarioField.setBounds(75, 190, 324, 50);
        usuarioField.setFont(new Font("Roboto",Font.BOLD,16));
        usuarioField.setHorizontalAlignment(JTextField.CENTER);
        usuarioField.setBorder(BorderFactory.createLineBorder(Color.decode("0xffffff")));
        contentPane.add(usuarioField);

        JLabel password = new JLabel("Set a password:");
        password.setBounds(75, 260, 200, 25);
        password.setFont(new Font("Roboto",Font.BOLD,16));
        contentPane.add(password);

        JLabel imagep = new JLabel();
        imagep.setBounds(50,265,374,100);
        imagep.setIcon(l1);
        contentPane.add(imagep);

        JLabel Rpassword = new JLabel("Repeat password:");
        Rpassword.setBounds(75, 360, 200, 25);
        Rpassword.setFont(new Font("Roboto",Font.BOLD,16));
        contentPane.add(Rpassword);

        JLabel Rimagep = new JLabel();
        Rimagep.setBounds(50,365,374,100);
        Rimagep.setIcon(l1);
        contentPane.add(Rimagep);

        JPasswordField RcontrasenaField = new JPasswordField(20);
        RcontrasenaField.setBounds(75, 390, 324, 50);
        RcontrasenaField.setFont(new Font("Roboto",Font.BOLD,16));
        RcontrasenaField.setHorizontalAlignment(JPasswordField.CENTER);
        RcontrasenaField.setBorder(BorderFactory.createLineBorder(Color.decode("0xffffff")));
        contentPane.add(RcontrasenaField);

        JLabel password2 = new JLabel("Do you have an account?");
        password2.setBounds(20, 585, 200, 25);
        contentPane.add(password2);

        JPasswordField contrasenaField = new JPasswordField(20);
        contrasenaField.setBounds(75, 290, 324, 50);
        contrasenaField.setFont(new Font("Roboto",Font.BOLD,16));
        contrasenaField.setHorizontalAlignment(JPasswordField.CENTER);
        contrasenaField.setBorder(BorderFactory.createLineBorder(Color.decode("0xffffff")));
        contentPane.add(contrasenaField);

        JLabel imagelg = new JLabel();
        imagelg.setBounds(102,455,270,120);
        imagelg.setIcon(l2);
        contentPane.add(imagelg);

        JButton loginButton = new JButton("Sign In");
        loginButton.setForeground(new Color(0xffffff));
        loginButton.setFont(new Font("Roboto",Font.PLAIN,35));
        loginButton.setBounds(127, 480, 220, 70);
        contentPane.add(loginButton);
        loginButton.setBackground(new Color(0x242c3c));
        loginButton.setFocusable(false);
        loginButton.setBorder(BorderFactory.createLineBorder(Color.decode("0x242c3c")));

        JLabel imagerg = new JLabel();
        imagerg.setBounds(185,550,166,90);
        imagerg.setIcon(l3);
        contentPane.add(imagerg);

        JButton registerButton = new JButton("Log In");
        registerButton.setForeground(new Color(0xffffff));
        registerButton.setBounds(210, 575, 116, 40);
        registerButton.setFont(new Font("Roboto",Font.PLAIN,18));
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
        RcontrasenaField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_ENTER){
                    loginButton.doClick();
                }
            }
        });

        loginButton.addActionListener(e -> {
            String usuario = usuarioField.getText();
            String contrasena = new String(contrasenaField.getPassword());
            String rcontrasena = new String(RcontrasenaField.getPassword());
            String SHA256 = Register.encrypt(contrasena);
            if (usuario.isEmpty() || contrasena.isEmpty() || rcontrasena.isEmpty() ) {
                JOptionPane.showMessageDialog(RegisterFrame.this, "Please, complete all the fields.", "Error", JOptionPane.WARNING_MESSAGE);
            } else if (!contrasena.equals(rcontrasena)) {
                JOptionPane.showMessageDialog(RegisterFrame.this, "You have to write the same password in both fields.", "Error", JOptionPane.WARNING_MESSAGE);
            } else if (query.ComprobarUsuario(usuario)){
                JOptionPane.showMessageDialog(RegisterFrame.this, "This user alredy exists, write an another username or try to log in.", "Error", JOptionPane.WARNING_MESSAGE);
            } else {
                query.CrearJugador(usuario,SHA256);
                this.dispose();
                LoginFrame login = new LoginFrame();
                login.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                login.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        MainD.desconecta();
                        System.exit(0);
                    }
                });
                login.setVisible(true);
                JOptionPane.showMessageDialog(RegisterFrame.this, "Welcome "+usuario+". You have been registered to the qoboo Data Base. :)", "Register", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            this.dispose();
            LoginFrame login = new LoginFrame();
            login.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            login.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    MainD.desconecta();
                    System.exit(0);
                }
            });
            login.setVisible(true);
        });
    }
}