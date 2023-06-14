import database.query;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Description {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame description = new DescriptionEdit();
            description.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            description.setVisible(true);
        });
    }
}
class DescriptionEdit extends JFrame{
    public DescriptionEdit(){
        setTitle("Description");
        setSize(600, 350);
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
        contentPane.setBackground(new Color(0xFF262626, true));

        Image img2 = new ImageIcon("img/vhs.gif").getImage();
        Image newimg2 = img2.getScaledInstance(600, 420, Image.SCALE_DEFAULT);
        ImageIcon filter = new ImageIcon(newimg2);

        ImageIcon marco = new ImageIcon("img/descriptmarco.png");
        String usuario = UsuarioActual.getInstance().getUsuario();

        JTextArea descriptionarea = new JTextArea(query.ObtenerDescripcionUsuario(usuario));
        JScrollPane scrollBar = new JScrollPane(descriptionarea);
        scrollBar.setBounds(30, 30, 545, 190);
        descriptionarea.setLineWrap(true);
        descriptionarea.setWrapStyleWord(true);
        descriptionarea.setFocusable(true);
        descriptionarea.setFont(pixelfont.deriveFont(18f));
        scrollBar.setBorder(BorderFactory.createLineBorder(new Color(0x0FF74DC, true)));
        descriptionarea.setBackground(new Color(0x0FF74DC, true));
        scrollBar.setVisible(true);
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
        descriptionarea.setEditable(true);
        descriptionarea.setForeground(new Color(0xB0B0B0));
        contentPane.add(scrollBar);

        JPanel filtro = new JPanel();
        filtro.setBounds(0, -10, 600, 420);
        filtro.setBackground(new Color(50, 50, 50, 126));
        contentPane.add(filtro);

        JLabel marcodescripcion = new JLabel();
        marcodescripcion.setBounds(0, 0, 600, 250);
        marcodescripcion.setIcon(marco);
        contentPane.add(marcodescripcion);

        JButton cancel = new JButton("Cancel");
        cancel.setBounds(30,260,150,80);
        cancel.setFont(pixelfont.deriveFont(24f));
        cancel.setBorder(BorderFactory.createLineBorder(Color.decode("0x242c3c")));
        cancel.setFocusable(false);
        cancel.setForeground(Color.black);
        cancel.setBackground(new Color(0xFFC4EA));
        contentPane.add(cancel);

        JButton apply = new JButton("Submit  changes");
        apply.setBounds(220,260,350,80);
        apply.setFont(pixelfont.deriveFont(24f));
        apply.setFocusable(false);
        apply.setForeground(Color.black);
        apply.setBorder(BorderFactory.createLineBorder(Color.decode("0x242c3c")));
        apply.setBackground(new Color(0xAFFFC4));
        contentPane.add(apply);

        JLabel vhs = new JLabel();
        vhs.setBounds(0, -10, 600, 420);
        vhs.setIcon(filter);
        contentPane.add(vhs);

        apply.addActionListener(e ->{
            String descripcion = descriptionarea.getText();
            String usuario1 = UsuarioActual.getInstance().getUsuario();
            query.actualizarDescripcion(descripcion,usuario1);
        });
        cancel.addActionListener(e -> this.dispose());
    }
}
