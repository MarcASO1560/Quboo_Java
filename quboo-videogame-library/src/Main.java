import database.MainD;
import database.query;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame MENU = new MainFrame();
            MENU.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            MENU.setVisible(true);
        });
    }
}
class MainFrame extends JFrame {
    boolean hahaha = true;
    boolean jajaja = true;
    int c1 = 1;
    int c2 = 300;
    boolean click_enter = true;
    boolean usercheck = false;

    public MainFrame() {
        setTitle("Quboo-Menu");
        setSize(720, 720);
        setBackground(new Color(0x131313));
        setResizable(false);
        setLocationRelativeTo(null);

        Font pixelfont = null;
        try {
            pixelfont = Font.createFont(Font.TRUETYPE_FONT, new File("font/PixelSplitter-Bold.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("font/PixelSplitter-Bold.ttf")));
        } catch (IOException | FontFormatException ignored) {}

        assert pixelfont != null;

        Container contentPane = getContentPane();
        contentPane.setLayout(null);
        contentPane.setBackground(new Color(0x131313));

        Image img = new ImageIcon("img/arcade.png").getImage();
        Image newimg = img.getScaledInstance(720, 720, Image.SCALE_SMOOTH);
        ImageIcon fondo = new ImageIcon(newimg);

        Image img2 = new ImageIcon("img/vhs.gif").getImage();
        Image newimg2 = img2.getScaledInstance(720, 500, Image.SCALE_DEFAULT);

        ImageIcon filter = new ImageIcon(newimg2);

        JLabel arcade = new JLabel();
        arcade.setBounds(0, 0, 720, 720);
        arcade.setIcon(fondo);
        contentPane.add(arcade);

        String usuario = UsuarioActual.getInstance().getUsuario();

        try {
            PrintWriter wr = new PrintWriter("src/usuario.txt", StandardCharsets.UTF_8);
            wr.println(usuario);
            wr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JTextArea descriptionarea = new JTextArea(query.ObtenerDescripcionUsuario(usuario));
        JScrollPane scrollBar=new JScrollPane(descriptionarea);
        scrollBar.setBounds(100, 400, 500, 90);
        descriptionarea.setLineWrap(true);
        descriptionarea.setWrapStyleWord(true);
        descriptionarea.setFocusable(false);
        descriptionarea.setFont(pixelfont.deriveFont(14f));
        scrollBar.setBorder(BorderFactory.createLineBorder(new Color(0x0FF74DC, true)));
        descriptionarea.setBackground(new Color(0x0FF74DC, true));
        scrollBar.setVisible(false);
        scrollBar.getVerticalScrollBar().setBackground(new Color(0x0FF74DC, true));
        UIManager.put("ScrollBarUI", new Color(0x000000));
        scrollBar.getVerticalScrollBar().setUI(new BasicScrollBarUI(){
            @Override
            protected void configureScrollBarColors(){
                this.trackColor = new Color(0x0000000, true);
                this.thumbColor = new Color(0xB0B0B0);
            }
            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton decreaseButton = super.createDecreaseButton(orientation);
                decreaseButton.setBorder(new EmptyBorder(0, 0, 0, 0));
                decreaseButton.setBackground(new Color(0xB0B0B0));
                return decreaseButton;
            }
            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton increaseButton = super.createIncreaseButton(orientation);
                increaseButton.setBorder(new EmptyBorder(0, 0, 0, 0));
                increaseButton.setBackground(new Color(0xB0B0B0));
                return increaseButton;
            }
        });
        scrollBar.setBackground(new Color(0x0FF74DC, true));
        scrollBar.setBorder(BorderFactory.createLineBorder(new Color(0x0FF74DC, true)));
        descriptionarea.setEditable(false);
        descriptionarea.setForeground(new Color(0xB0B0B0));
        contentPane.add(scrollBar);

        JPanel filtro = new JPanel();
        filtro.setBounds(0, 76, 720, 500);
        filtro.setBackground(new Color(50, 50, 50, 126));
        contentPane.add(filtro);

        ImageIcon QExit = new ImageIcon("img/GitHub.gif");

        JLabel gh = new JLabel("Press  G  for ");
        gh.setBounds(410, 500, 200, 32);
        gh.setFont(pixelfont.deriveFont(18f));
        gh.setForeground(Color.yellow);
        gh.setVisible(true);
        contentPane.add(gh);

        JLabel qe = new JLabel();
        qe.setBounds(570,500,70,32);
        qe.setVisible(true);
        qe.setIcon(QExit);
        contentPane.add(qe);

//------------------------------------------normal-menu------------------------------------------

        ImageIcon cursorimg = new ImageIcon("img/cursor.png");

        Image LoCk = new ImageIcon("img/lock.png").getImage();
        Image nlk = LoCk.getScaledInstance(16, 16, Image.SCALE_DEFAULT);
        ImageIcon lk = new ImageIcon(nlk);

        JLabel imgcursor = new JLabel();
        imgcursor.setBounds(50,c2,50,50);
        imgcursor.setVisible(true);
        imgcursor.setIcon(cursorimg);
        contentPane.add(imgcursor);

        JLabel menuname = new JLabel("VIDEOGAMES MENU");
        menuname.setBounds(200, 210, 400, 100);
        menuname.setFont(pixelfont.deriveFont(30f));
        menuname.setForeground(Color.WHITE);
        menuname.setVisible(hahaha);
        contentPane.add(menuname);

        JLabel quboo = new JLabel("quboo: ");
        quboo.setBounds(200, 200, 200, 50);
        quboo.setFont(pixelfont.deriveFont(25f));
        quboo.setForeground(new Color(0x55A88A));
        quboo.setVisible(hahaha);
        contentPane.add(quboo);

        JLabel score = new JLabel("total max score: ");
        score.setBounds(175, 80, 200, 50);
        score.setFont(pixelfont.deriveFont(18f));
        score.setForeground(new Color(0xFFF8CA));
        score.setVisible(hahaha);
        contentPane.add(score);

        JLabel scoren = new JLabel(String.valueOf(query.obtenerTotalPuntos(usuario)));
        scoren.setBounds(375, 80, 200, 50);
        scoren.setFont(pixelfont.deriveFont(18f));
        scoren.setForeground(Color.green);
        scoren.setVisible(hahaha);
        contentPane.add(scoren);

        JLabel info = new JLabel("Press  |||||  for  key  usages .");
        info.setBounds(165,530,300,50);
        info.setForeground(Color.WHITE);
        info.setFont(pixelfont.deriveFont(15f));
        info.setVisible(hahaha);
        contentPane.add(info);

        JLabel infotab = new JLabel("F2");
        infotab.setBounds(238,529,300,50);
        infotab.setForeground(new Color(0xFF74DC));
        infotab.setFont(pixelfont.deriveFont(16f));
        infotab.setVisible(hahaha);
        contentPane.add(infotab);

        JLabel maxscore1 = new JLabel("max  score :");
        maxscore1.setBounds(460,270,200,50);
        maxscore1.setForeground(Color.yellow);
        maxscore1.setFont(pixelfont.deriveFont(16f));
        maxscore1.setVisible(hahaha);
        contentPane.add(maxscore1);

        JLabel pong_title = new JLabel("PONG");
        pong_title.setBounds(100,300,200,50);
        pong_title.setForeground(Color.WHITE);
        pong_title.setFont(pixelfont.deriveFont(20f));
        pong_title.setVisible(hahaha);
        contentPane.add(pong_title);

        JLabel lock1 = new JLabel();
        lock1.setBounds(170,300,50,50);
        lock1.setVisible(true);
        lock1.setIcon(lk);
        contentPane.add(lock1);

        JLabel maxscore1n = new JLabel(String.valueOf(query.obtenerPuntosPong(usuario)));
        maxscore1n.setBounds(500,300,200,50);
        maxscore1n.setForeground(Color.green);
        maxscore1n.setFont(pixelfont.deriveFont(16f));
        maxscore1n.setVisible(hahaha);
        contentPane.add(maxscore1n);

        JLabel snake_title = new JLabel("SNAKE");
        snake_title.setBounds(100,330,200,50);
        snake_title.setForeground(Color.WHITE);
        snake_title.setFont(pixelfont.deriveFont(20f));
        snake_title.setVisible(hahaha);
        contentPane.add(snake_title);

        JLabel lock2 = new JLabel();
        lock2.setBounds(185,330,50,50);
        lock2.setVisible(true);
        lock2.setIcon(lk);
        contentPane.add(lock2);

        JLabel maxscore2n = new JLabel(String.valueOf(query.obtenerPuntosSnake(usuario)));
        maxscore2n.setBounds(500,330,200,50);
        maxscore2n.setForeground(Color.green);
        maxscore2n.setFont(pixelfont.deriveFont(16f));
        maxscore2n.setVisible(hahaha);
        contentPane.add(maxscore2n);

        JLabel pacman_title = new JLabel("PAC-MAN");
        pacman_title.setBounds(100,360,200,50);
        pacman_title.setForeground(Color.WHITE);
        pacman_title.setFont(pixelfont.deriveFont(20f));
        pacman_title.setVisible(hahaha);
        contentPane.add(pacman_title);

        JLabel lock3 = new JLabel();
        lock3.setBounds(215,360,50,50);
        lock3.setVisible(true);
        lock3.setIcon(lk);
        contentPane.add(lock3);

        JLabel maxscore3n = new JLabel(String.valueOf(query.obtenerPuntosPacman(usuario)));
        maxscore3n.setBounds(500,360,200,50);
        maxscore3n.setForeground(Color.green);
        maxscore3n.setFont(pixelfont.deriveFont(16f));
        maxscore3n.setVisible(hahaha);
        contentPane.add(maxscore3n);

        JLabel jumpman_title = new JLabel("JUMP-MAN");
        jumpman_title.setBounds(100,390,200,50);
        jumpman_title.setForeground(Color.WHITE);
        jumpman_title.setFont(pixelfont.deriveFont(20f));
        jumpman_title.setVisible(hahaha);
        contentPane.add(jumpman_title);

        JLabel lock4 = new JLabel();
        lock4.setBounds(230,390,50,50);
        lock4.setVisible(true);
        lock4.setIcon(lk);
        contentPane.add(lock4);

        JLabel maxscore4n = new JLabel(String.valueOf(query.obtenerPuntosJumpan(usuario)));
        maxscore4n.setBounds(500,390,200,50);
        maxscore4n.setForeground(Color.green);
        maxscore4n.setFont(pixelfont.deriveFont(16f));
        maxscore4n.setVisible(hahaha);
        contentPane.add(maxscore4n);

        JLabel typingtest_title = new JLabel("TYPING-TEST");
        typingtest_title.setBounds(100,420,200,50);
        typingtest_title.setForeground(Color.WHITE);
        typingtest_title.setFont(pixelfont.deriveFont(20f));
        typingtest_title.setVisible(hahaha);
        contentPane.add(typingtest_title);

        JLabel lock5 = new JLabel();
        lock5.setBounds(270,420,50,50);
        lock5.setVisible(true);
        lock5.setIcon(lk);
        contentPane.add(lock5);

        JLabel maxscore5n = new JLabel(String.valueOf(query.obtenerPuntosTypingtest(usuario)));
        maxscore5n.setBounds(500,420,200,50);
        maxscore5n.setForeground(Color.green);
        maxscore5n.setFont(pixelfont.deriveFont(16f));
        maxscore5n.setVisible(hahaha);
        contentPane.add(maxscore5n);

        if (query.obtenerPongComprado(usuario)==0) {
            lock1.setVisible(true);
        } else if (query.obtenerPongComprado(usuario)==1) {
            lock1.setVisible(false);
        }
        if (query.obtenerSnakeComprado(usuario)==0) {
            lock2.setVisible(true);
        } else if (query.obtenerSnakeComprado(usuario)==1) {
            lock2.setVisible(false);
        }
        if (query.obtenerPacmanComprado(usuario)==0) {
            lock3.setVisible(true);
        } else if (query.obtenerPacmanComprado(usuario)==1) {
            lock3.setVisible(false);
        }
        if (query.obtenerJumpanComprado(usuario)==0) {
            lock4.setVisible(true);
        } else if (query.obtenerJumpanComprado(usuario)==1) {
            lock4.setVisible(false);
        }
        if (query.obtenerTypingtestComprado(usuario)==0) {
            lock5.setVisible(true);
        } else if (query.obtenerTypingtestComprado(usuario)==1) {
            lock5.setVisible(false);
        }

//--------------------------------------------------------------------------------------

//----------------------------------------Information-tab-------------------------------------------

        ImageIcon rowup = new ImageIcon("img/rowup.png");
        ImageIcon rowdown = new ImageIcon("img/rowdown.png");
        ImageIcon keyenter = new ImageIcon("img/keyenter.png");
        ImageIcon keyq = new ImageIcon("img/Q.png");
        ImageIcon keyi = new ImageIcon("img/I.png");

        JLabel uprow_image = new JLabel();
        uprow_image.setBounds(200,200,50,50);
        uprow_image.setIcon(rowup);
        uprow_image.setVisible(false);
        contentPane.add(uprow_image);

        JLabel downrow_image = new JLabel();
        downrow_image.setBounds(200,250,50,50);
        downrow_image.setIcon(rowdown);
        downrow_image.setVisible(false);
        contentPane.add(downrow_image);

        JLabel enterkey_image = new JLabel();
        enterkey_image.setBounds(200,300,50,50);
        enterkey_image.setIcon(keyenter);
        enterkey_image.setVisible(false);
        contentPane.add(enterkey_image);

        JLabel Qkey_image = new JLabel();
        Qkey_image.setBounds(200,350,50,50);
        Qkey_image.setIcon(keyq);
        Qkey_image.setVisible(false);
        contentPane.add(Qkey_image);

        JLabel Ikey_image = new JLabel();
        Ikey_image.setBounds(200,400,50,50);
        Ikey_image.setIcon(keyi);
        Ikey_image.setVisible(false);
        contentPane.add(Ikey_image);

        JLabel keyusage = new JLabel("Key  usages");
        keyusage.setBounds(275, 100, 400, 100);
        keyusage.setFont(pixelfont.deriveFont(30f));
        keyusage.setForeground(Color.WHITE);
        keyusage.setVisible(false);
        keyusage.repaint();
        contentPane.add(keyusage);

        JLabel flecha_arriba = new JLabel("MOVE  UP.");
        flecha_arriba.setBounds(300,200,400,50);
        flecha_arriba.setForeground(Color.WHITE);
        flecha_arriba.setFont(pixelfont.deriveFont(20f));
        flecha_arriba.setVisible(false);
        contentPane.add(flecha_arriba);

        JLabel flecha_abajo = new JLabel("MOVE  DOWN.");
        flecha_abajo.setBounds(300,250,400,50);
        flecha_abajo.setForeground(Color.WHITE);
        flecha_abajo.setFont(pixelfont.deriveFont(20f));
        flecha_abajo.setVisible(false);
        contentPane.add(flecha_abajo);

        JLabel tecla_enter = new JLabel("START  THE  GAME.");
        tecla_enter.setBounds(300,300,400,50);
        tecla_enter.setForeground(Color.WHITE);
        tecla_enter.setFont(pixelfont.deriveFont(20f));
        tecla_enter.setVisible(false);
        contentPane.add(tecla_enter);

        JLabel tecla_q = new JLabel("QUIT  THE  MAIN  MENU.");
        tecla_q.setBounds(300,350,400,50);
        tecla_q.setForeground(Color.WHITE);
        tecla_q.setFont(pixelfont.deriveFont(20f));
        tecla_q.setVisible(false);
        contentPane.add(tecla_q);

        JLabel tecla_i = new JLabel("CHECK  USER  INFO.");
        tecla_i.setBounds(300,400,400,50);
        tecla_i.setForeground(Color.WHITE);
        tecla_i.setFont(pixelfont.deriveFont(20f));
        tecla_i.setVisible(false);
        contentPane.add(tecla_i);

        JLabel info2 = new JLabel("Press  |||||||  to  return  to  the  menu .");
        info2.setBounds(165,530,450,50);
        info2.setForeground(Color.WHITE);
        info2.setFont(pixelfont.deriveFont(15f));
        info2.setVisible(false);
        contentPane.add(info2);

        JLabel infoesc = new JLabel("ESC");
        infoesc.setBounds(238,529,300,50);
        infoesc.setForeground(new Color(0xFF74DC));
        infoesc.setFont(pixelfont.deriveFont(16f));
        infoesc.setVisible(false);
        contentPane.add(infoesc);

//--------------------------------------------------------------------------------------

//----------------------------------------Information-user-------------------------------------------

        ImageIcon userimg = new ImageIcon("img/user.png");

        JLabel menuuser = new JLabel("USER  MENU");
        menuuser.setBounds(275, 80, 400, 100);
        menuuser.setFont(pixelfont.deriveFont(30f));
        menuuser.setForeground(Color.WHITE);
        menuuser.setVisible(false);
        contentPane.add(menuuser);

        JLabel imguser = new JLabel();
        imguser.setBounds(100,200,160,160);
        imguser.setIcon(userimg);
        imguser.setVisible(false);
        contentPane.add(imguser);

        JLabel nameuser = new JLabel("Username : ");
        nameuser.setBounds(280, 170, 400, 100);
        nameuser.setFont(pixelfont.deriveFont(20f));
        nameuser.setForeground(Color.WHITE);
        nameuser.setVisible(false);
        contentPane.add(nameuser);

        JLabel nameuserq = new JLabel(UsuarioActual.getInstance().getUsuario());
        nameuserq.setBounds(430, 170, 400, 100);
        nameuserq.setFont(pixelfont.deriveFont(20f));
        nameuserq.setForeground(Color.WHITE);
        nameuserq.setVisible(false);
        contentPane.add(nameuserq);

        JLabel level = new JLabel("Rank : ");
        level.setBounds(280, 230, 400, 100);
        level.setFont(pixelfont.deriveFont(20f));
        level.setForeground(Color.WHITE);
        level.setVisible(false);
        contentPane.add(level);

        JLabel rankq = new JLabel(query.ObtenerRangoUsuario(usuario));
        rankq.setBounds(370, 230, 400, 100);
        rankq.setFont(pixelfont.deriveFont(20f));
        rankq.setForeground(Color.WHITE);
        rankq.setVisible(false);
        contentPane.add(rankq);

        JLabel rankinfo = new JLabel(query.ObtenerInfoRangoUsuario(usuario));
        rankinfo.setBounds(370, 250, 400, 100);
        rankinfo.setFont(pixelfont.deriveFont(12f));
        rankinfo.setForeground(Color.WHITE);
        rankinfo.setVisible(false);
        contentPane.add(rankinfo);

        JLabel coins = new JLabel("Coins : ");
        coins.setBounds(280, 290, 400, 100);
        coins.setFont(pixelfont.deriveFont(20f));
        coins.setForeground(Color.WHITE);
        coins.setVisible(false);
        contentPane.add(coins);

        ImageIcon coIMG = new ImageIcon("img/moneda.gif");

        JLabel coinimg = new JLabel();
        coinimg.setBounds(375,331,16,16);
        coinimg.setVisible(false);
        coinimg.setIcon(coIMG);
        contentPane.add(coinimg);

        JLabel coinsn = new JLabel(String.valueOf(query.obtenerMonedasUsuario(usuario)));
        coinsn.setBounds(400, 290, 400, 100);
        coinsn.setFont(pixelfont.deriveFont(20f));
        coinsn.setForeground(Color.yellow);
        coinsn.setVisible(false);
        contentPane.add(coinsn);

        JLabel description = new JLabel("Description : ");
        description.setBounds(100, 330, 350, 100);
        description.setFont(pixelfont.deriveFont(20f));
        description.setForeground(Color.WHITE);
        description.setVisible(false);
        contentPane.add(description);

        JLabel f5press = new JLabel("PRESS  F5  TO  CHANGE  DESCRIPTION");
        f5press.setBounds(100,480,350,50);
        f5press.setFont(pixelfont.deriveFont(13f));
        f5press.setForeground(new Color(0x91FF54));
        f5press.setVisible(false);
        contentPane.add(f5press);

//--------------------------------------------------------------------------------------

        Timer timer = new Timer(2000, event -> {
            descriptionarea.setText(query.ObtenerDescripcionUsuario(usuario));
            maxscore1n.setText(String.valueOf(query.obtenerPuntosPong(usuario)));
            maxscore2n.setText(String.valueOf(query.obtenerPuntosSnake(usuario)));
            maxscore3n.setText(String.valueOf(query.obtenerPuntosPacman(usuario)));
            maxscore4n.setText(String.valueOf(query.obtenerPuntosJumpan(usuario)));
            maxscore5n.setText(String.valueOf(query.obtenerPuntosTypingtest(usuario)));
            scoren.setText(String.valueOf(query.obtenerTotalPuntos(usuario)));
            rankq.setText(String.valueOf(query.ObtenerRangoUsuario(usuario)));
            rankinfo.setText(String.valueOf(query.ObtenerInfoRangoUsuario(usuario)));
            coinsn.setText(String.valueOf(query.obtenerMonedasUsuario(usuario)));
            jajaja = true;
        });
        timer.start();
        addKeyListener(new KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent e) {
                if (e.getKeyCode()==KeyEvent.VK_Q){
                    String[] botones = {"Yes"};
                    int ventana = JOptionPane.showOptionDialog(null,
                            "Do you want to exit?",
                            "Exit",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null,
                            botones, botones[0]);
                    if(ventana == 0) {
                        MainD.desconecta();
                        LoginFrame login = new LoginFrame();
                        login.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                        login.addWindowListener(new WindowAdapter() {
                            public void windowClosing(WindowEvent e) {
                                MainD.desconecta();
                                System.exit(0);
                            }
                        });
                        dispose();
                        login.setVisible(true);
                        new MainD();
                    }
                }
                if (e.getKeyCode()==KeyEvent.VK_F2){
                    menuname.setVisible(false);
                    quboo.setVisible(false);
                    score.setVisible(false);
                    info.setVisible(false);
                    infotab.setVisible(false);
                    pong_title.setVisible(false);
                    maxscore1.setVisible(false);
                    maxscore1n.setVisible(false);
                    maxscore2n.setVisible(false);
                    maxscore3n.setVisible(false);
                    maxscore4n.setVisible(false);
                    maxscore5n.setVisible(false);
                    lock1.setVisible(false);
                    lock2.setVisible(false);
                    lock3.setVisible(false);
                    lock4.setVisible(false);
                    lock5.setVisible(false);
                    scoren.setVisible(false);
                    snake_title.setVisible(false);
                    pacman_title.setVisible(false);
                    jumpman_title.setVisible(false);
                    typingtest_title.setVisible(false);
                    imgcursor.setVisible(false);
                    //------------------------
                    keyusage.setVisible(true);
                    flecha_arriba.setVisible(true);
                    uprow_image.setVisible(true);
                    flecha_abajo.setVisible(true);
                    downrow_image.setVisible(true);
                    tecla_i.setVisible(true);
                    Ikey_image.setVisible(true);
                    tecla_q.setVisible(true);
                    Qkey_image.setVisible(true);
                    tecla_enter.setVisible(true);
                    enterkey_image.setVisible(true);
                    info2.setVisible(true);
                    infoesc.setVisible(true);
                    click_enter = false;
                    //--------------------------
                    menuuser.setVisible(false);
                    imguser.setVisible(false);
                    nameuser.setVisible(false);
                    nameuserq.setVisible(false);
                    coins.setVisible(false);
                    coinsn.setVisible(false);
                    coinimg.setVisible(false);
                    level.setVisible(false);
                    rankq.setVisible(false);
                    rankinfo.setVisible(false);
                    description.setVisible(false);
                    scrollBar.setVisible(false);
                    f5press.setVisible(false);
                    usercheck = false;
                    jajaja = false;
                }
                if (e.getKeyCode()==KeyEvent.VK_ESCAPE){
                    keyusage.setVisible(false);
                    flecha_arriba.setVisible(false);
                    uprow_image.setVisible(false);
                    flecha_abajo.setVisible(false);
                    downrow_image.setVisible(false);
                    tecla_i.setVisible(false);
                    Ikey_image.setVisible(false);
                    tecla_q.setVisible(false);
                    Qkey_image.setVisible(false);
                    tecla_enter.setVisible(false);
                    enterkey_image.setVisible(false);
                    info2.setVisible(false);
                    infoesc.setVisible(false);
                    //----------------------------
                    menuuser.setVisible(false);
                    imguser.setVisible(false);
                    nameuser.setVisible(false);
                    nameuserq.setVisible(false);
                    coins.setVisible(false);
                    coinsn.setVisible(false);
                    coinimg.setVisible(false);
                    level.setVisible(false);
                    rankq.setVisible(false);
                    rankinfo.setVisible(false);
                    description.setVisible(false);
                    scrollBar.setVisible(false);
                    f5press.setVisible(false);
                    //----------------------------
                    menuname.setVisible(true);
                    quboo.setVisible(true);
                    score.setVisible(true);
                    info.setVisible(true);
                    infotab.setVisible(true);
                    maxscore1.setVisible(true);
                    maxscore1n.setVisible(true);
                    maxscore2n.setVisible(true);
                    maxscore3n.setVisible(true);
                    maxscore4n.setVisible(true);
                    maxscore5n.setVisible(true);
                    scoren.setVisible(true);
                    pong_title.setVisible(true);
                    snake_title.setVisible(true);
                    pacman_title.setVisible(true);
                    jumpman_title.setVisible(true);
                    typingtest_title.setVisible(true);
                    imgcursor.setVisible(true);
                    click_enter = true;
                    usercheck = false;
                    jajaja = true;
                    if (query.obtenerPongComprado(usuario)==0) {
                        lock1.setVisible(true);
                    } else if (query.obtenerPongComprado(usuario)==1) {
                        lock1.setVisible(false);
                    }
                    if (query.obtenerSnakeComprado(usuario)==0) {
                        lock2.setVisible(true);
                    } else if (query.obtenerSnakeComprado(usuario)==1) {
                        lock2.setVisible(false);
                    }
                    if (query.obtenerPacmanComprado(usuario)==0) {
                        lock3.setVisible(true);
                    } else if (query.obtenerPacmanComprado(usuario)==1) {
                        lock3.setVisible(false);
                    }
                    if (query.obtenerJumpanComprado(usuario)==0) {
                        lock4.setVisible(true);
                    } else if (query.obtenerJumpanComprado(usuario)==1) {
                        lock4.setVisible(false);
                    }
                    if (query.obtenerTypingtestComprado(usuario)==0) {
                        lock5.setVisible(true);
                    } else if (query.obtenerTypingtestComprado(usuario)==1) {
                        lock5.setVisible(false);
                    }
                }
                if (e.getKeyCode()==KeyEvent.VK_I){
                    menuname.setVisible(false);
                    quboo.setVisible(false);
                    score.setVisible(false);
                    info.setVisible(false);
                    infotab.setVisible(false);
                    maxscore1.setVisible(false);
                    maxscore1n.setVisible(false);
                    maxscore2n.setVisible(false);
                    maxscore3n.setVisible(false);
                    maxscore4n.setVisible(false);
                    maxscore5n.setVisible(false);
                    lock1.setVisible(false);
                    lock2.setVisible(false);
                    lock3.setVisible(false);
                    lock4.setVisible(false);
                    lock5.setVisible(false);
                    scoren.setVisible(false);
                    pong_title.setVisible(false);
                    snake_title.setVisible(false);
                    pacman_title.setVisible(false);
                    jumpman_title.setVisible(false);
                    typingtest_title.setVisible(false);
                    imgcursor.setVisible(false);
                    click_enter = false;
                    //--------------------------------
                    keyusage.setVisible(false);
                    flecha_arriba.setVisible(false);
                    uprow_image.setVisible(false);
                    flecha_abajo.setVisible(false);
                    downrow_image.setVisible(false);
                    tecla_i.setVisible(false);
                    Ikey_image.setVisible(false);
                    tecla_q.setVisible(false);
                    Qkey_image.setVisible(false);
                    tecla_enter.setVisible(false);
                    enterkey_image.setVisible(false);
                    info2.setVisible(true);
                    infoesc.setVisible(true);
                    //--------------------------------
                    menuuser.setVisible(true);
                    imguser.setVisible(true);
                    nameuser.setVisible(true);
                    nameuserq.setVisible(true);
                    coins.setVisible(true);
                    coinsn.setVisible(true);
                    coinimg.setVisible(true);
                    level.setVisible(true);
                    rankq.setVisible(true);
                    rankinfo.setVisible(true);
                    description.setVisible(true);
                    scrollBar.setVisible(true);
                    f5press.setVisible(true);
                    usercheck = true;
                    jajaja = false;
                }
                if (e.getKeyCode()==KeyEvent.VK_F5 && usercheck){
                    JFrame Description = new DescriptionEdit();
                    Description.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                    Description.setVisible(true);
                }
                if (e.getKeyCode()==KeyEvent.VK_DOWN && c1 <= 4 || e.getKeyCode()==KeyEvent.VK_S && c1 <= 4){
                    imgcursor.setBounds(50,c2+=30,50,50);
                    c1 += 1;
                }
                if (e.getKeyCode()==KeyEvent.VK_UP && c1 >= 2 || e.getKeyCode()==KeyEvent.VK_W && c1 >= 2){
                    imgcursor.setBounds(50,c2-=30,50,50);
                    c1 -= 1;
                }
                if (e.getKeyCode()==KeyEvent.VK_G){
                    try {
                        Desktop.getDesktop().browse(new URI("https://github.com/MarcASO1560"));
                    } catch (IOException | URISyntaxException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                if (e.getKeyCode()==KeyEvent.VK_E){
                    try {
                        Desktop.getDesktop().browse(new URI("https://www.youtube.com/watch?v=dQw4w9WgXcQ&t"));
                    } catch (IOException | URISyntaxException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                if (e.getKeyCode()==KeyEvent.VK_ENTER && click_enter){
                    if (c1==1){
                        if (query.obtenerPongComprado(usuario) == 0) {
                            int res = JOptionPane.showConfirmDialog(MainFrame.this, "You can't play this game, do you want to buyit?", "Pong", JOptionPane.YES_NO_OPTION);
                            if (res == JOptionPane.YES_OPTION){
                                if (query.obtenerMonedasUsuario(usuario)>=20){
                                    query.JuegoCompra(usuario);
                                    query.CambiarComprado(usuario,"pong");
                                    lock1.setVisible(false);
                                    jajaja = false;
                                } else if (query.obtenerMonedasUsuario(usuario)<20) {
                                    JOptionPane.showMessageDialog(MainFrame.this,"You don't have the required coins...","Bank", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        } else if (query.obtenerPongComprado(usuario) == 1 && jajaja){
                            setVisible(false);
                            System.out.println("Has escogido la opción 1");
                            ProcessBuilder pb = new ProcessBuilder("python3", "games/pong.py");
                            pb.redirectErrorStream(true);
                            try {
                                Process p = pb.start();
                                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                                String line;
                                while ((line = br.readLine()) != null) {
                                    System.out.println(line);
                                }
                                p.waitFor();
                            } catch (IOException | InterruptedException event) {
                                event.printStackTrace();
                            }
                            setVisible(true);
                        }
                    }
                    if (c1==2){
                        if (query.obtenerSnakeComprado(usuario) == 0) {
                            int res = JOptionPane.showConfirmDialog(MainFrame.this, "You can't play this game, do you want to buyit?", "Snake", JOptionPane.YES_NO_OPTION);
                            if (res == JOptionPane.YES_OPTION){
                                if (query.obtenerMonedasUsuario(usuario)>=20){
                                    query.JuegoCompra(usuario);
                                    query.CambiarComprado(usuario,"snake");
                                    lock2.setVisible(false);
                                    jajaja = false;
                                } else if (query.obtenerMonedasUsuario(usuario)<20) {
                                    JOptionPane.showMessageDialog(MainFrame.this,"You don't have the required coins...","Bank", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        } else if (query.obtenerSnakeComprado(usuario) == 1 && jajaja) {
                            setVisible(false);
                            System.out.println("Has escogido la opción 2");
                            ProcessBuilder pb = new ProcessBuilder("python3", "games/snake.py");
                            pb.redirectErrorStream(true);
                            try {
                                Process p = pb.start();
                                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                                String line;
                                while ((line = br.readLine()) != null) {
                                    System.out.println(line);
                                }
                                p.waitFor();
                            } catch (IOException | InterruptedException event) {
                                event.printStackTrace();
                            }
                            setVisible(true);
                        }
                    }
                    if (c1==3){
                        if (query.obtenerPacmanComprado(usuario) == 0) {
                            int res = JOptionPane.showConfirmDialog(MainFrame.this, "You can't play this game, do you want to buyit?", "Pacman", JOptionPane.YES_NO_OPTION);
                            if (res == JOptionPane.YES_OPTION){
                                if (query.obtenerMonedasUsuario(usuario)>=20){
                                    query.JuegoCompra(usuario);
                                    query.CambiarComprado(usuario,"pacman");
                                    lock3.setVisible(false);
                                    jajaja = false;
                                } else if (query.obtenerMonedasUsuario(usuario)<20) {
                                    JOptionPane.showMessageDialog(MainFrame.this,"You don't have the required coins...","Bank", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        } else if (query.obtenerPacmanComprado(usuario) == 1 && jajaja) {
                            setVisible(false);
                            System.out.println("Has escogido la opción 3");
                            ProcessBuilder pb = new ProcessBuilder("python3", "games/pacman.py");
                            pb.redirectErrorStream(true);
                            try {
                                Process p = pb.start();
                                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                                String line;
                                while ((line = br.readLine()) != null) {
                                    System.out.println(line);
                                }
                                p.waitFor();
                            } catch (IOException | InterruptedException event) {
                                event.printStackTrace();
                            }
                            setVisible(true);
                        }
                    }
                    if (c1==4){
                        if (query.obtenerJumpanComprado(usuario) == 0) {
                            int res = JOptionPane.showConfirmDialog(MainFrame.this, "You can't play this game, do you want to buyit?", "Jumpman", JOptionPane.YES_NO_OPTION);
                            if (res == JOptionPane.YES_OPTION){
                                if (query.obtenerMonedasUsuario(usuario)>=20){
                                    query.JuegoCompra(usuario);
                                    query.CambiarComprado(usuario,"jumpman");
                                    lock4.setVisible(false);
                                    jajaja = false;
                                } else if (query.obtenerMonedasUsuario(usuario)<20) {
                                    JOptionPane.showMessageDialog(MainFrame.this,"You don't have the required coins...","Bank", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        } else if (query.obtenerJumpanComprado(usuario) == 1 && jajaja) {
                            setVisible(false);
                            System.out.println("Has escogido la opción 4");
                            ProcessBuilder pb = new ProcessBuilder("python3", "games/jumpnam.py");
                            pb.redirectErrorStream(true);
                            try {
                                Process p = pb.start();
                                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                                String line;
                                while ((line = br.readLine()) != null) {
                                    System.out.println(line);
                                }
                                p.waitFor();
                            } catch (IOException | InterruptedException event) {
                                event.printStackTrace();
                            }
                            setVisible(true);
                        }
                    }
                    if (c1==5){
                        if (query.obtenerTypingtestComprado(usuario) == 0) {
                            int res = JOptionPane.showConfirmDialog(MainFrame.this, "You can't play this game, do you want to buyit?", "Typing test", JOptionPane.YES_NO_OPTION);
                            if (res == JOptionPane.YES_OPTION){
                                if (query.obtenerMonedasUsuario(usuario)>=20){
                                    query.JuegoCompra(usuario);
                                    query.CambiarComprado(usuario,"typingtest");
                                    lock5.setVisible(false);
                                    jajaja = false;
                                } else if (query.obtenerMonedasUsuario(usuario)<20) {
                                    JOptionPane.showMessageDialog(MainFrame.this,"You don't have the required coins...","Bank", JOptionPane.WARNING_MESSAGE);
                                }
                            }
                        } else if (query.obtenerTypingtestComprado(usuario) == 1 && jajaja) {
                            setVisible(false);
                            System.out.println("Has escogido la opción 5");
                            ProcessBuilder pb = new ProcessBuilder("python3", "games/typingtest.py");
                            pb.redirectErrorStream(true);
                            try {
                                Process p = pb.start();
                                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
                                String line;
                                while ((line = br.readLine()) != null) {
                                    System.out.println(line);
                                }
                                p.waitFor();
                            } catch (IOException | InterruptedException event) {
                                event.printStackTrace();
                            }
                            setVisible(true);
                        }
                    }
                }
            }
        });
        JLabel vhs = new JLabel();
        vhs.setBounds(0, 76, 720, 500);
        vhs.setIcon(filter);
        contentPane.add(vhs);
    }
}